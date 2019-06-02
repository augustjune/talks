package webex.api

import cats.Functor
import cats.implicits._
import io.circe.generic.auto._
import webex.clients.WebexClient
import webex.methods.messages._
import webex.model._

class MessagesApi[F[_] : Functor](client: WebexClient[F]) {

  def sendMessage(roomId: String,
                  text: String,
                  file: Option[String] = None): F[Message] = {
    client.execute(CreateMessage(roomId = Some(roomId), text = text, files = file))
  }

  def sendDirectMessage(userId: String,
                        text: String,
                        file: Option[String] = None): F[Message] = {
    client.execute(CreateMessage(toPersonId = Some(userId), text = text, files = file))
  }

  def listMessages(roomId: String,
                   mentionedPeople: Option[String] = None,
                   before: Option[String] = None,
                   beforeMessage: Option[String] = None,
                   max: Option[Int] = None): F[List[Message]] = {
    client
      .execute[ListMessages, Messages](ListMessages(roomId, mentionedPeople, before, beforeMessage, max))
      .map(_.items)
  }

  def listDirectMessages(userId: String): F[List[Message]] = {
    client
      .execute[ListDirectMessages, Messages](ListDirectMessages(personId = Some(userId)))
      .map(_.items)
  }

  def deleteMessage(messageId: String): F[Unit] = {
    client.execute(DeleteMessage(messageId))
  }

  def getMessage(messageId: String): F[Message] = {
    client.execute(GetMessageDetails(messageId))
  }
}
