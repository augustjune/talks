package webex.methods.rooms

import webex.methods.{Method, Put, RequestMethod}
import webex.model.Room

// ToDo - handle additional body parameter 'titleId'
case class UpdateRoom(roomId: String) extends Method[Room] {
  def requestMethod: RequestMethod = Put

  def route: String = "/v1/rooms/"
}
