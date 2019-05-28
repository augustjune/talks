package webex

import cats.effect.{ExitCode, IO, IOApp}
import com.softwaremill.sttp.SttpBackend
import com.softwaremill.sttp.asynchttpclient.cats.AsyncHttpClientCatsBackend
import com.typesafe.config.{Config, ConfigFactory}
import webex.clients.{SttpClient, WebexClient}

object Run extends IOApp {

  def config: Config = ConfigFactory.parseResources("credentials/webex.conf")

  def token: String = config.getString("token")
  def userId: String = config.getString("userId")
  def space: String = config.getString("space")

  implicit val backend: SttpBackend[IO, Nothing] = AsyncHttpClientCatsBackend[cats.effect.IO]()
  val client: WebexClient[IO] = new SttpClient[IO](token)

  val webexApi = new MessagesApi[IO](client)

  def run(args: List[String]): IO[ExitCode] =
    for {
      directMessages <- webexApi.listMessages(space)
      _ = println(directMessages)
      _ <- IO(backend.close())
    } yield ExitCode.Success
}
