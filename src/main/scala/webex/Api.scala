package webex

import cats.Functor
import com.softwaremill.sttp._
import com.typesafe.config.ConfigFactory
import cats.implicits._

import scala.collection.JavaConverters._

class Api[F[_]: Functor](token: String)(implicit backend: SttpBackend[F, _]) {

  def sendGroupMessage(groupId: String, text: String): F[Unit] = {
    sttp.auth.bearer(token)
      .post(uri"https://api.ciscospark.com/v1/messages")
      .contentType("application/json")
      .body(s"""{ "roomId" : "$groupId", "text" : "$text" }""")
      .send().void
  }

  def sendDirectMessage(userId: String, text: String): F[Unit] = {
    sttp.auth.bearer(token)
      .post(uri"https://api.ciscospark.com/v1/messages")
      .contentType("application/json")
      .body(s"""{ "toPersonId" : "$userId", "text" : "$text" }""")
      .send().void
  }

  def listMessages(groupId: String): F[List[String]] = {
    sttp.auth.bearer(token)
      .get(uri"https://api.ciscospark.com/v1/messages?roomId=$groupId&mentionedPeople=me")
      .send().map(response => getMessages(response.unsafeBody))
  }

  def listDirectMessages(userId: String): F[List[String]] = {
    sttp.auth.bearer(token)
      .get(uri"https://api.ciscospark.com/v1/messages/direct?personId=$userId")
      .send().map(response => getMessages(response.unsafeBody))
  }

  private def getMessages(response: String): List[String] = {
    ConfigFactory.parseString(response)
      .getConfigList("items").asScala.toList
      .map(_.getString("text"))
  }
}
