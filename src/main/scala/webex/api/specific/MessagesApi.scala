package webex.api.specific

import webex.clients.WebexClient
import webex.methods.messages._
import webex.model.{Message, Messages}
import io.circe.generic.auto._

trait MessagesApi[F[_]] {

  def client: WebexClient[F]

  def sendMessage(roomId: String, text: String): F[Message] = {
    client.execute(CreateMessage(roomId = Some(roomId), text = text))
  }

  def sendDirectMessage(userId: String,
                        text: String,
                        file: Option[String] = None): F[Message] = {
    client.execute(CreateMessage(toPersonId = Some(userId), text = text, files = file))
  }

  def listMessages(roomId: String): F[Messages] = {
    client.execute(ListMessages(roomId, Some("me")))
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
