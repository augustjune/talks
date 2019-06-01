package webex.api.syntax

import cats.implicits._
import cats.{Functor, Monad}
import webex.api.{MembershipsApi, MessagesApi, PeopleApi, RoomsApi}
import webex.model._

final class RichRoom(room: Room) {

  /**
    * Sends message in this room
    */
  def sendMessage[F[_]](text: String)
                       (implicit messagesApi: MessagesApi[F]): F[Message] =
    messagesApi.sendMessage(room.id, text)

  /**
    * Lists people in the rooms
    */
  def members[F[_]](implicit F: Monad[F],
                    membershipsApi: MembershipsApi[F],
                    peopleApi: PeopleApi[F]): F[List[Person]] = {
    membershipsApi.listMemberships(roomId = Some(room.id)).map(_.map(_.personId))
      .flatMap(_.traverse(personId => peopleApi.getPerson(personId)))
  }

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
    membershipsApi
      .listMemberships(roomId = Some(room.id), personId = Some(personId))
      .map(_.foldLeft(false) {
        case (_, m) => membershipsApi.deleteMembership(m.id); true
      })


  /**
    * Adds person of given id to the room moderators
    *
    * @return true if the person was present in the room
    *         false if the person was not present in the room
    */
  def makeAdmin[F[_]](personId: String)
                     (implicit F: Functor[F], membershipsApi: MembershipsApi[F]): F[Boolean] =
    membershipsApi
      .listMemberships(roomId = Some(room.id), personId = Some(personId))
      .map(_.foldLeft(false) {
        case (_, m) => membershipsApi.updateMembership(m.id, isModerator = true); true
      })
}
