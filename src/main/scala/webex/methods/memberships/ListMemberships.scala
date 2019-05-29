package webex.methods.memberships

import webex.methods.{Get, Method, RequestMethod}
import webex.model.Memberships

case class ListMemberships(roomId: Option[String],
                           personId: Option[String],
                           personEmail: Option[String],
                           max: Option[Int]) extends Method[Memberships] {
  def requestMethod: RequestMethod = Get

  def route: String = "/v1/memberships"
}
