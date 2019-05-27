package webex.methods.messages

import webex.methods._
import webex.model.Message

case class ListMessages(roomId: String,
                        mentionedPeople: Option[List[String]] = None,
                        before: Option[String] = None,
                        beforeMessage: Option[String] = None,
                        max: Option[Int] = None) extends Method[List[Message]] {
  def requestMethod: RequestMethod = Get

  def route: String = s"/v1/messages?$roomId$otherArgs"

  private def otherArgs: String = List(
    "mentionedPeople" -> mentionedPeople.map(_.mkString(",")),
    "before" -> before,
    "beforeMEssage" -> beforeMessage,
    "max" -> max.map(_.toString))
    .map { case (n, v) => RouteBuilder.arg(Some(n), v) }.mkString(",")


  def body: Option[String] = None
}
