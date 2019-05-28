package webex.marshalling

import io.circe.{Decoder, HCursor}
import webex.model.Message
import io.circe.generic.auto._

object CirceDecoders {

  implicit val messageDecoder: Decoder[Message] = Decoder[Message]

  implicit val messageListDecoder: Decoder[List[Message]] = (c: HCursor) => c.get[List[Message]]("items")
}
