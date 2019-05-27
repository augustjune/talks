package webex.methods.messages

import com.bot4s.telegram.models.File
import webex.methods._
import webex.model.Message

case class CreateMessage(roomId: Option[String] = None,
                         toPersonId: Option[String] = None,
                         toPersonEmail: Option[String] = None,
                         text: String,
                         markdown: Option[String] = None,
                         files: Option[List[File]] = None) extends Method[Message] {
  def requestMethod: RequestMethod = Post

  def route: String = "/v1/messages"

  def body: Option[String] =
    Some(
      s"""
        |{
        | "roomId" : "$roomId",
        | "toPersonId" : "$toPersonId",
        | "toPersonEmail" : "$toPersonEmail",
        | "text" : "$text",
        | "markdown" : "$markdown"
        |}
      """.stripMargin)
}
