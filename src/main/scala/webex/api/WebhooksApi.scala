package webex.api

import cats.Functor
import cats.implicits._
import webex.clients.WebexClient
import webex.methods.webhooks._
import webex.model.{Webhook, Webhooks}
import io.circe.generic.auto._

class WebhooksApi[F[_]: Functor](client: WebexClient[F]) {
  def listWebhooks(max: Option[Int] = None): F[List[Webhook]] = {
    client
      .execute[ListWebhooks, Webhooks](ListWebhooks(max))
      .map(_.items)
  }

  def createWebhook(name: String,
                    targetUrl: String,
                    resource: String,
                    event: String,
                    filter: Option[String] = None,
                    secret: Option[String] = None): F[Webhook] =
    client.execute(CreateWebhook(name, targetUrl, resource, event, filter, secret))

  def getWebhookDetails(webhookId: String): F[Webhook] =
    client.execute(GetWebhookDetails(webhookId))

  def updateWebhook(webhookId: String,
                    name: String,
                    targetUrl: String,
                    secret: Option[String] = None,
                    status: Option[String] = None): F[Webhook] =
    client.execute(UpdateWebhook(webhookId, name, targetUrl, secret, status))

  def deleteWebhook(webhookId: String): F[Unit] =
    client.execute(DeleteWebhook(webhookId))
}
