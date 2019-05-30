package webex.api.syntax

import cats.Functor
import webex.api.{MembershipsApi, MessagesApi, RoomsApi}
import webex.model.{Membership, Memberships, Message, Room}
import cats.implicits._

final class RichRoom(room: Room) {

  /**
    * Sends message in this room
    */
  def sendMessage[F[_]](text: String)
                       (implicit messagesApi: MessagesApi[F]): F[Message] =
    messagesApi.sendMessage(room.id, text)

  /**
    * Renames the room with new title
    */
  def rename[F[_]](title: String)
                  (implicit roomsApi: RoomsApi[F]): F[Room] =
    roomsApi.updateRoom(room.id, title)

  /**
    * Deletes the room
    */
  def delete[F[_]](implicit roomsApi: RoomsApi[F]): F[Unit] =
    roomsApi.deleteRoom(room.id)

  /**
    * Invites person of given id to the room
    *
    * @return Newly created membership
    */
  def invite[F[_]](personId: String, isModerator: Boolean = false)
                  (implicit membershipsApi: MembershipsApi[F]): F[Membership] =
    membershipsApi
      .createMembership(room.id, Some(personId), isModerator = isModerator)

  /**
    * Removes the person of given id from the room
    *
    * @return true if the person was present in the room
    *         false if the person was not present in the room
    */
  def remove[F[_]](personId: String)
                  (implicit F: Functor[F], membershipsApi: MembershipsApi[F]): F[Boolean] =
    membershipsApi.listMemberships(roomId = Some(room.id), personId = Some(personId)).map {
      case Memberships(items) => items.foldLeft(false) {
        case (_, m) => membershipsApi.deleteMembership(m.id); true
      }
    }


  /**
    * Adds person of given id to the room moderators
    *
    * @return true if the person was present in the room
    *         false if the person was not present in the room
    */
  def makeAdmin[F[_]](personId: String)
                     (implicit F: Functor[F], membershipsApi: MembershipsApi[F]): F[Boolean] =
    membershipsApi.listMemberships(roomId = Some(room.id), personId = Some(personId)).map {
      case Memberships(items) => items.foldLeft(false) {
        case (_, m) => membershipsApi.updateMembership(m.id, isModerator = true); true
      }
    }
}
