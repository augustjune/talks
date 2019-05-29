package webex.methods.webhooks

import webex.methods.{Get, Method, RequestMethod}
import webex.model.Webhooks

case class ListWebhooks(max: Option[Int]) extends Method[Webhooks] {
  def requestMethod: RequestMethod = Get

  def route: String = "/v1/webhooks"
}
