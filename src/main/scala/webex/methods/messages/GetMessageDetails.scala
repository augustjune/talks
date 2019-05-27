package webex.methods.messages

import webex.methods._
import webex.model.Message

case class GetMessageDetails(messageId: String) extends Method[Message] {
  def requestMethod: RequestMethod = Get

  def route: String = s"/v1/messages/$messageId"

  def body: Option[String] = None
}
