package webex

import io.circe.{Encoder, Printer}
import io.circe.syntax._
import webex.methods.{Delete, Get, Method}

package object marshalling {

  val printer: Printer = Printer.spaces2.copy(dropNullValues = true)

  implicit class EncodableMethod[M <: Method[_] : Encoder](method: M) {
    def toJsonString: String = method.asJson.pretty(printer)

    def routeWithParameters: String =
      if (method.route.endsWith("/")) s"${method.route}$anonymousParameterList"
      else method.requestMethod match {
        case Get =>
          val options = namedParameterList
          if (options.isEmpty) method.route
          else s"${method.route}?$options"

        case Delete => s"${method.route}/$anonymousParameterList"

        case _ => method.route
      }

    private def namedParameterList: String = {
      val js = method.asJson.pretty(Printer.noSpaces.copy(dropNullValues = true))
      List("\"", "{", "}")
        .foldLeft(js) { case (acc, el) => acc.replace(el, "") }
        .replace(":", "=")
        .replace(",", "&")
    }

    private def anonymousParameterList: String = {
      val cursor = method.asJson.hcursor
      cursor.keys.getOrElse(Nil)
        .flatMap(key => cursor.get[String](key).toOption)
        .mkString(",")
    }
  }

}
