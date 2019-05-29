package webex.methods.webhooks

import webex.methods.{Method, Post, RequestMethod}
import webex.model.Webhook

case class CreateWebhook(name: String,
                         targetUrl: String,
                         resource: String,
                         event: String,
                         filter: Option[String],
                         secret: Option[String]) extends Method[Webhook] {
  def requestMethod: RequestMethod = Post

  def route: String = "/v1/webhooks"
}
