package webex.methods.messages

import webex.methods._
import webex.model.Message

case class ListDirectMessages(personId: Option[String] = None,
                              personEmail: Option[String] = None) extends Method[List[Message]] {
  def requestMethod: RequestMethod = Get

  def route: String = s"/v1/messages/direct?$args"

  private def args: String =
    List(
      "personId" -> personId,
      "personEmail" -> personEmail
    ).map { case (n, v) => RouteBuilder.arg(Some(n), v) }.mkString(",")

  def body: Option[String] = None
}
