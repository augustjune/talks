package webex.api

import webex.model._

package object syntax {
  implicit def richRoom(room: Room): RichRoom = new RichRoom(room)
  implicit def richMessage(message: Message): RichMessage = new RichMessage(message)
  implicit def richMembership(membership: Membership): RichMembership = new RichMembership(membership)
}
