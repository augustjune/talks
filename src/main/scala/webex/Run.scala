package webex

import concurrent.duration._
import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import com.softwaremill.sttp.SttpBackend
import com.softwaremill.sttp.asynchttpclient.cats.AsyncHttpClientCatsBackend
import com.typesafe.config.{Config, ConfigFactory}
import webex.api._
import webex.api.syntax._
import webex.clients.{SttpClient, WebexClient}

object Run extends IOApp {

  def config: Config = ConfigFactory.parseResources("credentials/webex.conf")

  def token: String = config.getString("token")
  def userId: String = config.getString("userId")
  def space: String = config.getString("space")

  implicit val backend: SttpBackend[IO, Nothing] = AsyncHttpClientCatsBackend[IO]()
  val client: WebexClient[IO] = new SttpClient[IO](token)

  implicit val messageApi = new MessagesApi[IO](client)
  implicit val roomsApi = new RoomsApi[IO](client)
  implicit val membershipApi = new MembershipsApi[IO](client)
  implicit val peopleApi = new PeopleApi[IO](client)

  def run(args: List[String]): IO[ExitCode] =
    for {
      me <- peopleApi.me
      _ = println("Me: " + me.displayName)
      people <- peopleApi.listPeople(email = Some("jurij.jurich@gmail.com"))
      _ = println("Others:")
      _ = people.items.map(_.displayName).foreach(println)
      _ <- IO(backend.close())
    } yield ExitCode.Success
}
