package webex

import io.circe.{Encoder, Printer}
import io.circe.syntax._
import webex.methods._

package object marshalling {

  val printer: Printer = Printer.spaces2.copy(dropNullValues = true)

  implicit class EncodableMethod[M <: Method[_] : Encoder](method: M) {
    def body: String = method.requestMethod match {
      case Post => method.asJson.pretty(printer)

      case Put =>
        val json = method.asJson.asObject.get
        json.remove(json.keys.head).asJson.pretty(printer)

      case _ => "{}"
    }

    def routeWithParameters: String =
      if (method.route.endsWith("/")) s"${method.route}$firstArgumentValue"
      else method.requestMethod match {
        case Get =>
          val options = namedParameterList
          if (options.isEmpty) method.route
          else s"${method.route}?$options"

        case Delete => s"${method.route}/$anonymousParameterList"

        case _ => method.route
      }

    private def namedParameterList: String = {
      method.asJson
        .pretty(Printer.noSpaces.copy(dropNullValues = true))
        .replace("\":\"", "=")
        .replace("\",\"", "&")
        .drop(2)        // {"
        .dropRight(2)   // "}
    }

    private def firstArgumentValue: String = {
      val cursor = method.asJson.hcursor
      cursor.keys.getOrElse(Nil)
        .flatMap(key => cursor.get[String](key).toOption)
        .head
    }

    private def anonymousParameterList: String = {
      val cursor = method.asJson.hcursor
      cursor.keys.getOrElse(Nil)
        .flatMap(key => cursor.get[String](key).toOption)
        .mkString(",")
    }
  }

}
