package webex

import cats.Semigroupal

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

  implicit val webexApi: WebexApi[IO] = new WebexApi(client)

  def run(args: List[String]): IO[ExitCode] =
    for {
      rooms <- webexApi.listRooms()
      roomsWithMembers <- rooms.items.traverse(r => Semigroupal[IO].product(IO.pure(r.title), r.members[IO].map(_.map(_.displayName))))
      _ = roomsWithMembers.foreach(println)
      _ <- IO(backend.close())
    } yield ExitCode.Success
}
