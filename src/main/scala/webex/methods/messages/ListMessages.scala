package webex.methods.messages

import webex.methods._
import webex.model.Messages

case class ListMessages(roomId: String,
                        mentionedPeople: Option[List[String]] = None,
                        before: Option[String] = None,
                        beforeMessage: Option[String] = None,
                        max: Option[Int] = None) extends Method[Messages] {
  def requestMethod: RequestMethod = Get

  def route: String = s"/v1/messages"
}
