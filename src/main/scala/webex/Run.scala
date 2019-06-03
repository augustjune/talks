package webex

import cats.{Semigroupal, Show}

import concurrent.duration._
import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import com.softwaremill.sttp.SttpBackend
import com.softwaremill.sttp.asynchttpclient.cats.AsyncHttpClientCatsBackend
import com.typesafe.config.{Config, ConfigFactory}
import webex.api._
import webex.api.syntax._
import webex.clients.{SttpClient, WebexClient}
import webex.model.Message

object Run extends IOApp {

  def config: Config = ConfigFactory.parseResources("credentials/webex.conf")

  def token: String = config.getString("token")
  def userId: String = config.getString("userId")
  def space: String = config.getString("space")
  def direct: String = config.getString("direct")

  implicit val backend: SttpBackend[IO, Nothing] = AsyncHttpClientCatsBackend[IO]()
  val client: WebexClient[IO] = new SttpClient[IO](token)

  implicit val messageApi = new MessagesApi[IO](client)
  implicit val roomsApi = new RoomsApi[IO](client)
  implicit val membershipApi = new MembershipsApi[IO](client)
  implicit val peopleApi = new PeopleApi[IO](client)

  val bot = new Bot(roomsApi, messageApi)

  implicit val messageShow: Show[Message] = (t: Message) => s"${t.personId} [${t.created}]: ${t.text}"

  def run(args: List[String]): IO[ExitCode] =
    for {
      _ <- bot.roomMessages(direct)(1.second).showLinesStdOut.compile.drain
      _ <- IO(backend.close())
    } yield ExitCode.Success
}
