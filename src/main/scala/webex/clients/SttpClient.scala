package webex.clients

import cats.Functor
import com.softwaremill.sttp.{UriContext, sttp, SttpBackend, Method => SttpMethod}
import webex.methods._
import io.circe.{Decoder, Encoder}
import io.circe.parser.decode
import webex.marshalling._

import cats.implicits._

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

    request
      .mapResponse(emptyOrSame)
      .mapResponse(r => decode[R](r).getOrElse(throw new RuntimeException(s"Failed to decode the response: $r")))
      .send()
      .map{r => println(r.header("Retry-After")); r.unsafeBody}
  }

  private def emptyOrSame(s: String): String =
    if (s.isEmpty) "{}"
    else s
}
