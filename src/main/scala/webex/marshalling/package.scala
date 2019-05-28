package webex

import io.circe.{Encoder, Printer}
import io.circe.syntax._
import webex.methods.{Delete, Get, Method, Post}

package object marshalling {

  val printer: Printer = Printer.spaces2.copy(dropNullValues = true)

  implicit class EncodableMethod[M <: Method[_] : Encoder](method: M) {
    def toJsonString: String = method.asJson.pretty(printer)

    def routeWithParameters: String = method.requestMethod match {
      case Get =>
        val js = method.asJson.pretty(Printer.noSpaces.copy(dropNullValues = true))
        val options = List("\"", "{", "}").foldLeft(js) {
          case (acc, el) => acc.replace(el, "")
        }
          .replace(":", "=")
          .replace(",", "&")

        if (options.isEmpty) method.route
        else s"${method.route}?$options"

      case Delete =>
        val cursor = method.asJson.hcursor
        val params = cursor.keys.getOrElse(Nil)
          .flatMap(key => cursor.get[String](key).toOption)
          .mkString(",")

        s"${method.route}/$params"

      case _ => method.route
    }
  }

}
