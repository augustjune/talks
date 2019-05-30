package webex.methods.people

import webex.methods.{Get, Method, RequestMethod}
import webex.model.Person

case class GetPersonDetails(personId: String) extends Method[Person] {
  def requestMethod: RequestMethod = Get

  def route: String = "/v1/people/"
}
