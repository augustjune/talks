package webex.methods.people

import webex.methods.{Method, Post, RequestMethod}
import webex.model.Person

case class CreatePerson(emails: String,
                        displayName: Option[String],
                        firstName: Option[String],
                        lastName: Option[String],
                        avatar: Option[String],
                        orgId: Option[String],
                        roles: Option[String],
                        licenses: Option[String],
                        created: Option[String],
                        lastModified: Option[String],
                        status: Option[String],
                        invitePending: Option[Boolean],
                        loginEnabled: Option[Boolean],
                        `type`: Option[String]) extends Method[Person] {
  def requestMethod: RequestMethod = Post

  def route: String = "/v1/people"
}
