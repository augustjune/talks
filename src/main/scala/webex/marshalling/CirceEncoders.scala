package webex.marshalling
import io.circe._
import io.circe.generic.auto._
import webex.methods.messages._

object CirceEncoders {
  implicit val createMessageEncoder: Encoder[CreateMessage] = Encoder[CreateMessage]
  implicit val listMessagesEncoder: Encoder[ListMessages] = Encoder[ListMessages]
  implicit val listDirectMessagesEncoder: Encoder[ListDirectMessages] = Encoder[ListDirectMessages]
}
