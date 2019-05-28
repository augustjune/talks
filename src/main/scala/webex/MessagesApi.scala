package webex

import cats.Functor
import cats.implicits._
import webex.clients.WebexClient
import webex.methods.messages.{CreateMessage, ListDirectMessages, ListMessages}
import webex.model.Message

//import webex.marshalling.CirceEncoders._
import io.circe.generic.auto._
import webex.marshalling.CirceDecoders._

class MessagesApi[F[_] : Functor](client: WebexClient[F]) {

  def sendGroupMessage(groupId: String, text: String): F[Unit] = {
    client.execute[CreateMessage, Message](CreateMessage(roomId = Some(groupId), text = text)).void
  }

  def sendDirectMessage(userId: String, text: String): F[Unit] = {
    client.execute[CreateMessage, Message](CreateMessage(toPersonId = Some(userId), text = text)).void
  }

  def listMessages(groupId: String): F[List[Message]] = {
    client.execute(ListMessages(groupId, Some(List("me"))))
  }

  def listDirectMessages(userId: String): F[List[Message]] = {
    client.execute(ListDirectMessages(personId = Some(userId)))
  }
}
