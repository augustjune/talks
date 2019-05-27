package webex.methods.messages

import webex.methods._

case class DeleteMessage(messageId: String) extends Method[Unit] {
  def requestMethod: RequestMethod = Delete

  def route: String = s"/v1/messages/$messageId"

  def body: Option[String] = None
}
