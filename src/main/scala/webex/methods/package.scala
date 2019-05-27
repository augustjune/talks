package webex

package object methods {
  type File = String

  object RouteBuilder {
    def arg(name: Option[String], value: Option[String]): String = {
      (name, value) match {
        case (_, None) => ""
        case (Some(n), Some(v)) => s"$n=$v"
        case (None, Some(v)) => v
      }
    }
  }
}
