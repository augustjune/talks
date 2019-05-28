package webex.clients

import cats.implicits._
import io.circe.{Decoder, Encoder}
import webex.methods.Method

trait WebexClient[F[_]] {

  def basicRoute: String = "https://api.ciscospark.com"

  def execute[M <: Method[R], R](method: M)(implicit requestEncoder: Encoder[M], responseDecoder: Decoder[R]): F[R]
}
