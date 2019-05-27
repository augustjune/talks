package webex.clients

import cats.implicits._
import webex.methods.Method

trait WebexClient[F[_]] {

  def basicRoute: String = "https://api.ciscospark.com"

  def execute[R: Decoder](method: Method[R]): F[R]
}

trait Decoder[A] {
  def decode(s: String): A
}

object Decoder {
  def apply[A](s: String)(implicit decoder: Decoder[A]): A = decoder.decode(s)
}
