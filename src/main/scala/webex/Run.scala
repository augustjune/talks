package webex

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import com.softwaremill.sttp.SttpBackend
import com.softwaremill.sttp.asynchttpclient.cats.AsyncHttpClientCatsBackend
import com.typesafe.config.{Config, ConfigFactory}
import webex.api.{MembershipApi, MessagesApi, RoomsApi}
import webex.clients.{SttpClient, WebexClient}

object Run extends IOApp {

  def config: Config = ConfigFactory.parseResources("credentials/webex.conf")

  def token: String = config.getString("token")
  def userId: String = config.getString("userId")
  def space: String = config.getString("space")

  implicit val backend: SttpBackend[IO, Nothing] = AsyncHttpClientCatsBackend[cats.effect.IO]()
  val client: WebexClient[IO] = new SttpClient[IO](token)

  val messageApi = new MessagesApi[IO](client)
  val roomsApi = new RoomsApi[IO](client)
  val membershipApi = new MembershipApi[IO](client)

  def run(args: List[String]): IO[ExitCode] =
    for {
      _ <- IO(backend.close())
    } yield ExitCode.Success
}
