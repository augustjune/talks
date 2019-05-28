package webex.methods.messages

import webex.methods._

case class DeleteMessage(messageId: String) extends Method[Unit] {
  def requestMethod: RequestMethod = Delete

  def route: String = "/v1/messages"
}
