package webex.api.syntax

import webex.api.WebhooksApi
import webex.model.Webhook

final class RichWebhook(webhook: Webhook) {

  /**
    * Deletes this webhook
    */
  def delete[F[_]](implicit webhooksApi: WebhooksApi[F]): F[Unit] =
    webhooksApi.deleteWebhook(webhook.id)

  /**
    * Updates this webhook with new name
    */
  def rename[F[_]](name: String)
                  (implicit webhooksApi: WebhooksApi[F]): F[Webhook] =
    webhooksApi.updateWebhook(webhook.id, name, webhook.targetUrl)

  /**
    * Changes target url of this webhook
    */
  def changeTarget[F[_]](targetUrl: String)
                        (implicit webhooksApi: WebhooksApi[F]): F[Webhook] =
    webhooksApi.updateWebhook(webhook.id, webhook.name, targetUrl)
}
