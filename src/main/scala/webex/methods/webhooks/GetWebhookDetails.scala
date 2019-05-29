package webex.methods.webhooks

import webex.methods.{Get, Method, RequestMethod}
import webex.model.Webhook

case class GetWebhookDetails(webhookId: String) extends Method[Webhook] {
  def requestMethod: RequestMethod = Get

  def route: String = "/v1/webhooks/"
}
