package webex.methods.memberships

import webex.methods.{Method, Put, RequestMethod}
import webex.model.Membership

case class UpdateMembership(membershipId: String, isModerator: Boolean) extends Method[Membership] {
  def requestMethod: RequestMethod = Put

  def route: String = "/v1/memberships/"
}
