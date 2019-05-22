package webex

import cats.effect.{ExitCode, IO, IOApp}
import com.softwaremill.sttp.SttpBackend
import com.softwaremill.sttp.asynchttpclient.cats.AsyncHttpClientCatsBackend
import com.typesafe.config.{Config, ConfigFactory}

object Run extends IOApp {

  def config: Config = ConfigFactory.parseResources("credentials/webex.conf")

  def token: String = config.getString("token")
  def userId: String = config.getString("userId")
  def space: String = config.getString("space")

  implicit val backend: SttpBackend[IO, Nothing] = AsyncHttpClientCatsBackend[cats.effect.IO]()
  val webexApi = new Api[IO](token)

  def run(args: List[String]): IO[ExitCode] =
    for {
      directMessages <- webexApi.listDirectMessages(userId)
      spaceMessages <- webexApi.listMessages(space)
      _ <- IO(println(directMessages))
      _ <- IO(println(spaceMessages))
      _ <- IO(backend.close())
    } yield ExitCode.Success
}
