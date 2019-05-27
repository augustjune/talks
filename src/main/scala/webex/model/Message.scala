package webex.model

import webex.clients.Decoder
import io.circe.generic.auto._
import io.circe.parser.decode

case class Message(id: String,
                   roomId: String,
                   roomType: String,
                   text: String,
                   personId: String,
                   personEmail: String,
                   mentionedPeople: Option[List[String]],
                   created: String)

object Message {
  implicit val decoder: Decoder[Message] =
    (s: String) => decode[Message](s).getOrElse(throw new RuntimeException("Could not decode message"))
}
