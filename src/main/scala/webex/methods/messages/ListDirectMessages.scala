package webex.methods.messages

import webex.methods._
import webex.model.Messages

case class ListDirectMessages(personId: Option[String] = None,
                              personEmail: Option[String] = None) extends Method[Messages] {
  def requestMethod: RequestMethod = Get

  def route: String = s"/v1/messages/direct"

  /*
    case class User(name: String, age: Option[Int])
    val user = User("dassd", Some(12))
    val js = user.asJson.pretty(Printer.noSpaces.copy(dropNullValues = true))

    val route = "/v1/messages"
    val options = List("\"", "{", "}").foldLeft(js)((acc, el) => acc.replace(el, "")).replace(":", "=")

    val res = if (options.isEmpty) route else s"$route?$options"
   */

  def body: Option[String] = None
}
