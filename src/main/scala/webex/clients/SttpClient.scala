package webex.clients

import cats.Functor
import com.softwaremill.sttp._
import webex.methods.{Delete, Get, Method, Post, RequestMethod}

import cats.implicits._


class SttpClient[F[_] : Functor](token: String)(implicit backend: SttpBackend[F, Nothing]) extends WebexClient[F] {

  private val client = sttp.auth.bearer(token).contentType("application/json")

  private val methodMapping: Map[RequestMethod, com.softwaremill.sttp.Method] =
    Map(
      Get -> com.softwaremill.sttp.Method.GET,
      Post -> com.softwaremill.sttp.Method.POST,
      Delete -> com.softwaremill.sttp.Method.DELETE)

  def execute[R: Decoder](method: Method[R]): F[R] = {
    val route = s"$basicRoute${method.route}"
    val request = client.method(methodMapping(method.requestMethod), uri = uri"$route")

    request.send().map(_.body match {
      case Right(r) => Decoder(r)
      case Left(error) => throw new RuntimeException(s"Failed request to ${request.uri} $error")
    })
  }


}
