package webex.methods.memberships

import webex.methods.{Delete, Method, RequestMethod}

case class DeleteMembership(membershipId: String) extends Method[Unit] {
  def requestMethod: RequestMethod = Delete

  def route: String = "/v1/memberships/"
}
