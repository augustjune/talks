package webex.api.specific

import webex.clients.WebexClient
import webex.methods.webhooks._
import webex.model.{Webhook, Webhooks}
import io.circe.generic.auto._

trait WebhooksApi[F[_]] {

  def client: WebexClient[F]

  def listWebhooks(max: Option[Int] = None): F[Webhooks] =
    client.execute(ListWebhooks(max))

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
