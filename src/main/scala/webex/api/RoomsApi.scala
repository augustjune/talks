package webex.api

import cats.Functor
import cats.implicits._
import webex.clients.WebexClient
import webex.methods.rooms._
import webex.model.{Room, Rooms}
import io.circe.generic.auto._

class RoomsApi[F[_] : Functor](client: WebexClient[F]) {

  def listRooms(teamId: Option[String] = None,
                roomType: Option[String] = None,
                sortBy: Option[String] = None,
                max: Option[Int] = None): F[List[Room]] = {
    client
      .execute[ListRooms, Rooms](ListRooms(teamId, roomType, sortBy, max))
      .map(_.items)
  }

  def createRoom(title: String,
                 teamId: Option[String] = None): F[Room] =
    client.execute(CreateRoom(title, teamId))

  def getRoomDetails(roomId: String): F[Room] =
    client.execute(GetRoomDetails(roomId))

  def updateRoom(roomId: String, title: String): F[Room] =
    client.execute(UpdateRoom(roomId, title))

  def deleteRoom(roomId: String): F[Unit] =
    client.execute(DeleteRoom(roomId))
}
