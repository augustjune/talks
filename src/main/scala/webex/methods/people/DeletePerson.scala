package webex.methods.people

import webex.methods.{Delete, Method, RequestMethod}

case class DeletePerson(personId: String) extends Method[Unit] {
  def requestMethod: RequestMethod = Delete

  def route: String = "/v1/people/"
}
