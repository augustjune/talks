package webex

import cats.Functor
import cats.implicits._
import com.typesafe.config.{ConfigFactory, ConfigRenderOptions}
import webex.clients.{Decoder, WebexClient}
import webex.methods.messages.{CreateMessage, ListDirectMessages, ListMessages}
import webex.model.Message

import scala.collection.JavaConverters._

class MessagesApi[F[_] : Functor](client: WebexClient[F]) {

  import Message.decoder

  implicit val unitDecoder: Decoder[Unit] = (s: String) => ()
  implicit val listMessagesDecoder: Decoder[List[Message]] =
    (s: String) => ConfigFactory.parseString(s)
      .getObjectList("items").asScala.toList
      .map(o => Decoder[Message](o.render(ConfigRenderOptions.concise())))


  def sendGroupMessage(groupId: String, text: String): F[Unit] = {
    client.execute(CreateMessage(roomId = Some(groupId), text = text)).void
  }

  def sendDirectMessage(userId: String, text: String): F[Unit] = {
    client.execute(CreateMessage(toPersonId = Some(userId), text = text)).void
  }

  def listMessages(groupId: String): F[List[Message]] = {
    client.execute(ListMessages(groupId, Some(List("me"))))
  }

  def listDirectMessages(userId: String): F[List[Message]] = {
    client.execute(ListDirectMessages(personId = Some(userId)))
  }
}
