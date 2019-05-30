package webex.methods.people

import webex.methods.{Method, Put, RequestMethod}
import webex.model.Person

case class UpdatePerson(personId: String,
                        displayName: String,
                        emails: Option[String],
                        firstName: Option[String],
                        lastName: Option[String],
                        avatar: Option[String],
                        orgId: Option[String],
                        roles: Option[String],
                        licenses: Option[String]) extends Method[Person] {
  def requestMethod: RequestMethod = Put

  def route: String = "/v1/people/"
}
