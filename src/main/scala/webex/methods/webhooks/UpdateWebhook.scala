package webex.methods.webhooks

import webex.methods.{Method, Put, RequestMethod}
import webex.model.Webhook

case class UpdateWebhook(webhookId: String,
                         name: String,
                         targetUrl: String,
                         secret: Option[String],
                         status: Option[String]) extends Method[Webhook] {
  def requestMethod: RequestMethod = Put

  def route: String = "/v1/webhooks/"
}
