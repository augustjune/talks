package webex

import java.time.{LocalDateTime, ZoneId, ZonedDateTime}
import java.time.format.DateTimeFormatter

import cats.Semigroupal
import fs2._
import cats.effect.{IO, Timer}
import webex.api.{MessagesApi, RoomsApi}
import webex.model.{Message, Room}
import webex.api.syntax._
import cats.implicits._

object Bot {
  type F[A] = IO[A]
}

import Bot.F

import scala.concurrent.duration._

class Bot(roomsApi: RoomsApi[F], messagesApi: MessagesApi[F])
         (implicit T: Timer[F]) {

  def incoming: Stream[F, Message] = ???

  def rooms: Stream[F, Room] = {
    Stream
      .repeatEval(roomsApi.listRooms())
      .metered(1 second)
      .mapAccumulate(0) {
        case (seen, current) => (current.size, current.dropRight(seen)) // (all, diff)
      }.map(_._2)
      .flatMap(rooms => Stream.emits(rooms.reverse))
  }

  def roomMessages(roomId: String): Stream[F, Message] = {
    Stream
      .repeatEval(messagesApi.listMessages(roomId))
      .mapAccumulate[ZonedDateTime, List[Message]](ZonedDateTime.now()) {
      case (lastCreated, messages) =>
        val newMessages = messages.takeWhile(m => parseTimestamp(m.created).compareTo(lastCreated) > 0)
        val newLast = if (newMessages.isEmpty) lastCreated else parseTimestamp(newMessages.head.created)
        (newLast, newMessages)

    }.flatMap { case (_, messages) => Stream.emits(messages.reverse) }
  }

  private def parseTimestamp(timestamp: String): ZonedDateTime =
    ZonedDateTime.parse(timestamp).withZoneSameInstant(ZoneId.systemDefault())
}
