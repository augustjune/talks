package webex.methods.people

import webex.methods.{Get, Method, RequestMethod}
import webex.model.People

case class ListPeople(email: Option[String],
                      displayName: Option[String],
                      id: Option[String],
                      orgId: Option[String],
                      max: Option[Int]) extends Method[People] {
  def requestMethod: RequestMethod = Get

  def route: String = "/v1/people"
}
