package webex

import cats.Functor
import cats.implicits._
import com.softwaremill.sttp._
import com.typesafe.config.{ConfigFactory, ConfigRenderOptions}
import io.circe.generic.auto._
import io.circe.parser.decode

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

  def listMessages(groupId: String): F[List[Message]] = {
    sttp.auth.bearer(token)
      .get(uri"https://api.ciscospark.com/v1/messages?roomId=$groupId&mentionedPeople=me")
      .send().map(response => getMessages(response.unsafeBody))
  }

  def listDirectMessages(userId: String): F[List[Message]] = {
    sttp.auth.bearer(token)
      .get(uri"https://api.ciscospark.com/v1/messages/direct?personId=$userId")
      .send().map(response => getMessages(response.unsafeBody))
  }

  private def getMessages(response: String): List[Message] = {
    ConfigFactory.parseString(response)
      .getObjectList("items").asScala.toList
      .map(o => getMessage(o.render(ConfigRenderOptions.concise())))
  }

  private def getMessage(json: String): Message = {
    decode[Message](json).getOrElse(throw new RuntimeException("Something wrong with decoding message"))
  }
}
