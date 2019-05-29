package webex.methods.webhooks

import webex.methods.{Delete, Method, RequestMethod}

case class DeleteWebhook(webhookId: String) extends Method[Unit] {
  def requestMethod: RequestMethod = Delete

  def route: String = "/v1/webhooks/"
}
