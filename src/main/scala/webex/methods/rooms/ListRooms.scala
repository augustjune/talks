package webex.methods.rooms

import webex.methods.{Get, Method, RequestMethod}
import webex.model.Rooms

case class ListRooms(teamId: Option[String],
                     `type`: Option[String],
                     sortBy: Option[String],
                     max: Option[Int]) extends Method[Rooms] {
  def requestMethod: RequestMethod = Get

  def route: String = "/v1/rooms"
}
