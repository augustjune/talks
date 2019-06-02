package webex

import java.time.{LocalDateTime, ZoneId}
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
         (implicit timer: Timer[F]) {

  def incoming: Stream[F, Message] = ???

  def rooms(pollingTimeout: FiniteDuration): Stream[F, Room] = {
    Stream
      .fixedRate(pollingTimeout)
      .evalMap(_ => roomsApi.listRooms())
      .scan1 {
        case (rooms1, rooms2) =>
      }

    ???
  }


  def roomMessages(roomId: String)(pollingTimeout: FiniteDuration): Stream[F, Message] = {
    Stream
      .fixedRate(pollingTimeout)
      .evalMap[F, List[Message]](_ => messagesApi.listMessages(roomId))
      //DateTimeFormatter.ISO_ZONED_DATE_TIME.format(LocalDateTime.now().atZone(ZoneId.of("Z")))
      // ToDo - add initial time comparison
      .mapAccumulate[String, List[Message]]("") {
      case (lastCreated, messages) =>
        val newMessages = messages.takeWhile(_.created != lastCreated)
        val newLast = if (newMessages.isEmpty) lastCreated else newMessages.head.created
        (newLast, newMessages)

    }.flatMap { case (_, messages) => Stream.emits(messages.reverse) }
  }
}
