package webex.methods.messages

import webex.methods._
import webex.model.Messages

case class ListMessages(roomId: String,
                        mentionedPeople: Option[String] = None,
                        before: Option[String] = None,
                        beforeMessage: Option[String] = None,
                        max: Option[Int] = None) extends Method[Messages] {
  def requestMethod: RequestMethod = Get

  def route: String = "/v1/messages"
}
