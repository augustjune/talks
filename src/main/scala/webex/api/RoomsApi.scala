package webex.api

import webex.clients.WebexClient
import webex.methods.rooms._
import webex.model.{Room, Rooms}
import io.circe.generic.auto._

class RoomsApi[F[_]](client: WebexClient[F]) {

  def listRooms(teamId: Option[String] = None,
                roomType: Option[String] = None,
                sortBy: Option[String] = None,
                max: Option[Int] = None): F[Rooms] =
    client.execute(ListRooms(teamId, roomType, sortBy, max))

  def createRoom(title: String,
                 teamId: Option[String] = None): F[Room] =
    client.execute(CreateRoom(title, teamId))

  def getRoomDetails(roomId: String): F[Room] =
    client.execute(GetRoomDetails(roomId))

  def updateRoom(roomId: String, title: String): F[Room] =
    client.execute(UpdateRoom(roomId))

  def deleteRoom(roomId: String): F[Unit] =
    client.execute(DeleteRoom(roomId))
}