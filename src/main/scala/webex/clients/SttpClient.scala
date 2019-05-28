package webex.clients

import cats.Functor
import com.softwaremill.sttp._
import webex.methods.{Delete, Get, Method, Post, RequestMethod}
import cats.implicits._
import io.circe.{Decoder, Encoder, Printer}
import io.circe.parser.decode
import io.circe.syntax._
import webex.marshalling._


class SttpClient[F[_] : Functor](token: String)(implicit backend: SttpBackend[F, Nothing]) extends WebexClient[F] {

  private val client = sttp.auth.bearer(token).contentType("application/json")

  private val methodMapping: Map[RequestMethod, com.softwaremill.sttp.Method] =
    Map(
      Get -> com.softwaremill.sttp.Method.GET,
      Post -> com.softwaremill.sttp.Method.POST,
      Delete -> com.softwaremill.sttp.Method.DELETE)


  def execute[M <: Method[R], R](method: M)(implicit requestEncoder: Encoder[M], responseDecoder: Decoder[R]): F[R] = {
    val route = s"$basicRoute${method.route}"
    val request = client
      .method(methodMapping(method.requestMethod), uri"${getFullRoute(method)}")
      .body(method.toJsonString)

    request.send().map(_.body match {
      case Right(r) => decode[R](r).getOrElse(throw new RuntimeException(s"Failed to decode the response: $r"))
      case Left(error) => throw new RuntimeException(s"Failed request to ${request.uri} $error")
    })
  }

  private def getFullRoute[M <: Method[_]](method: M)(implicit encoder: Encoder[M]): String = {
    if (method.requestMethod == Get) {
      val js = method.asJson.pretty(Printer.noSpaces.copy(dropNullValues = true))
      val options = List("\"", "{", "}").foldLeft(js){
        case (acc, el) => acc.replace(el, "").replace(":", "=")
      }

      if (options.isEmpty) method.route
      else s"${method.route}?$options"
    } else method.route
  }
}
