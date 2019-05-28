package webex

import cats.Functor
import webex.clients.WebexClient
import webex.methods.messages.{CreateMessage, ListDirectMessages, ListMessages}
import webex.model.{Message, Messages}

import cats.implicits._

import io.circe.generic.auto._

class MessagesApi[F[_] : Functor](client: WebexClient[F]) {

  def sendGroupMessage(groupId: String, text: String): F[Message] = {
    client.execute(CreateMessage(roomId = Some(groupId), text = text))
  }

  def sendDirectMessage(userId: String, text: String): F[Message] = {
    client.execute(CreateMessage(toPersonId = Some(userId), text = text))
  }

  def listMessages(groupId: String): F[Messages] = {
    client.execute(ListMessages(groupId, Some(List("me"))))
  }

  def listDirectMessages(userId: String): F[Messages] = {
    client.execute(ListDirectMessages(personId = Some(userId)))
  }
}
