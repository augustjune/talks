package webex

import io.circe.{Encoder, Printer}
import io.circe.syntax._

package object marshalling {

  val printer: Printer = Printer.spaces2.copy(dropNullValues = true)

  implicit class Encodable[A: Encoder](a: A) {
    def toJsonString: String = a.asJson.pretty(printer)
  }
}
