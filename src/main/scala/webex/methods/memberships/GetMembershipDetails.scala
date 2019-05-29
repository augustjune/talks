package webex.methods.memberships

import webex.methods.{Get, Method, RequestMethod}
import webex.model.Membership

case class GetMembershipDetails(membershipId: String) extends Method[Membership] {
  def requestMethod: RequestMethod = Get

  def route: String = "/v1/memberships/"
}
