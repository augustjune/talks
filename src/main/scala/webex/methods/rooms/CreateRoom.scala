package webex.methods.rooms

import webex.methods.{Method, Post, RequestMethod}
import webex.model.Room

case class CreateRoom(title: String,
                      teamId: Option[String]) extends Method[Room] {
  def requestMethod: RequestMethod = Post

  def route: String = "/v1/rooms"
}