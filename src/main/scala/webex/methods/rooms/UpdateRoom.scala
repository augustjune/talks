package webex.methods.rooms

import webex.methods.{Method, Put, RequestMethod}
import webex.model.Room

case class UpdateRoom(roomId: String, title: String) extends Method[Room] {
  def requestMethod: RequestMethod = Put

  def route: String = "/v1/rooms/"
}
