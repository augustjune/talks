package webex.api.syntax

import webex.api.MessagesApi
import webex.model.Message

final class RichMessage(message: Message) {

  /**
    * Responds to the message with a new message
    */
  def respond[F[_]](text: String)
                  (implicit messagesApi: MessagesApi[F]): F[Message] =
    messagesApi.sendMessage(message.roomId, text)

  /**
    * Deletes this message
    */
  def delete[F[_]](implicit messagesApi: MessagesApi[F]): F[Unit] =
    messagesApi.deleteMessage(message.id)

  /**
    * Sends a new message with the same text to the room of given id
    */
  def forward[F[_]](roomId: String)
                   (implicit messagesApi: MessagesApi[F]): F[Message] =
    messagesApi.sendMessage(roomId, message.text)

  /**
    * Sends a new message with the same text to the person of given id
    */
  def forwardDirect[F[_]](userId: String)
                   (implicit messagesApi: MessagesApi[F]): F[Message] =
    messagesApi.sendDirectMessage(userId, message.text)
}
