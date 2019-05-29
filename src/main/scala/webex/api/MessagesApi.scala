package webex.api

import io.circe.generic.auto._
import webex.clients.WebexClient
import webex.methods.messages._
import webex.model._

class MessagesApi[F[_]](client: WebexClient[F]) {

  def sendGroupMessage(groupId: String, text: String): F[Message] = {
    client.execute(CreateMessage(roomId = Some(groupId), text = text))
  }

  def sendDirectMessage(userId: String, text: String): F[Message] = {
    client.execute(CreateMessage(toPersonId = Some(userId), text = text))
  }

  def listGroupMessages(groupId: String): F[Messages] = {
    client.execute(ListMessages(groupId, Some("me")))
  }

  def listDirectMessages(userId: String): F[Messages] = {
    client.execute(ListDirectMessages(personId = Some(userId)))
  }

  def deleteMessage(messageId: String): F[Unit] = {
    client.execute(DeleteMessage(messageId))
  }

  def getMessage(messageId: String): F[Message] = {
    client.execute(GetMessageDetails(messageId))
  }
}
