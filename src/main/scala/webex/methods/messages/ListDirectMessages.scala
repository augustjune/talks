package webex.methods.messages

import webex.methods._
import webex.model.Messages

case class ListDirectMessages(personId: Option[String] = None,
                              personEmail: Option[String] = None) extends Method[Messages] {
  def requestMethod: RequestMethod = Get

  def route: String = "/v1/messages/direct"
}
