package webex.methods.rooms

import webex.methods.{Delete, Method, RequestMethod}
import webex.model.Room

case class DeleteRoom(roomId: String) extends Method[Unit] {
  def requestMethod: RequestMethod = Delete

  def route: String = "/v1/rooms/"
}
