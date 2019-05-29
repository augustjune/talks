package webex.methods.rooms

import webex.methods.{Get, Method, RequestMethod}
import webex.model.Room

case class GetRoomDetails(roomId: String) extends Method[Room] {
  def requestMethod: RequestMethod = Get

  def route: String = "/v1/rooms/"
}