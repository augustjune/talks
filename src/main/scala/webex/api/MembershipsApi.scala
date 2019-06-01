package webex.api

import cats.Functor
import cats.implicits._
import webex.clients.WebexClient
import webex.methods.memberships._
import webex.model.{Membership, Memberships}
import io.circe.generic.auto._

class MembershipsApi[F[_]: Functor](client: WebexClient[F]) {
  def listMemberships(roomId: Option[String] = None,
                      personId: Option[String] = None,
                      personEmail: Option[String] = None,
                      max: Option[Int] = None): F[List[Membership]] = {
    client
      .execute[ListMemberships, Memberships](ListMemberships(roomId, personId, personEmail, max))
      .map(_.items)
  }

  def createMembership(roomId: String,
                       personId: Option[String] = None,
                       personEmail: Option[String] = None,
                       isModerator: Boolean = false): F[Membership] =
    client.execute(CreateMembership(roomId, personId, personEmail, isModerator))

  def getMembershipDetails(membershipId: String): F[Membership] =
    client.execute(GetMembershipDetails(membershipId))

  def updateMembership(membershipId: String, isModerator: Boolean): F[Membership] =
    client.execute(UpdateMembership(membershipId, isModerator))

  def deleteMembership(membershipId: String): F[Unit] =
    client.execute(DeleteMembership(membershipId))
}
