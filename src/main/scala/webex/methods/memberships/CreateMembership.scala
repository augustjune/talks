package webex.methods.memberships

import webex.methods.{Method, Post, RequestMethod}
import webex.model.Membership

case class CreateMembership(roomId: String,
                            personId: Option[String],
                            personEmail: Option[String],
                            isModerator: Boolean) extends Method[Membership] {
  def requestMethod: RequestMethod = Post

  def route: String = "/v1/memberships"
}
