package webex.clients

import cats.Functor
import com.softwaremill.sttp.{UriContext, sttp, SttpBackend, Method => SttpMethod}
import webex.methods._
import cats.implicits._
import io.circe.{Decoder, Encoder}
import io.circe.parser.decode
import webex.marshalling._

class SttpClient[F[_] : Functor](token: String)(implicit backend: SttpBackend[F, Nothing]) extends WebexClient[F] {

  private val client = sttp.auth.bearer(token).contentType("application/json")

  private val methodMapping: Map[RequestMethod, SttpMethod] =
    Map(
      Get -> SttpMethod.GET,
      Post -> SttpMethod.POST,
      Delete -> SttpMethod.DELETE,
      Put -> SttpMethod.PUT)


  def execute[M <: Method[R], R](method: M)(implicit requestEncoder: Encoder[M], responseDecoder: Decoder[R]): F[R] = {
    val path = s"$basicRoute${method.routeWithParameters}"
    val request = client
      .method(methodMapping(method.requestMethod), uri"$path")
      .body(method.body)

    request.send().map(_.body.map(emptyOrSame) match {
      case Right(r) => decode[R](r).getOrElse(throw new RuntimeException(s"Failed to decode the response: $r"))
      case Left(error) => throw new RuntimeException(s"Failed request to ${request.uri} $error")
    })
  }

  private def emptyOrSame(s: String): String =
    if (s.isEmpty) "{}"
    else s
}
