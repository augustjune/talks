package webex.api.syntax

import webex.api.specific.MembershipsApi
import webex.model.Membership

final class RichMembership(membership: Membership) {

  /**
    * Updates person moderator state in the room
    */
  def update[F[_]](isModerator: Boolean)
                  (implicit membershipsApi: MembershipsApi[F]): F[Membership] =
    membershipsApi.updateMembership(membership.id, isModerator)

  /**
    * Deletes this membership, removing the person from the room
    */
  def delete[F[_]](implicit membershipsApi: MembershipsApi[F]): F[Unit] =
    membershipsApi.deleteMembership(membership.id)
}
