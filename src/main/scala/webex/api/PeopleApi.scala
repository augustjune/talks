package webex.api

import cats.Functor
import cats.implicits._
import webex.clients.WebexClient
import webex.methods.people._
import webex.model.{People, Person}
import io.circe.generic.auto._

class PeopleApi[F[_]: Functor](client: WebexClient[F]) {

  def me: F[Person] = {
    client.execute(GetMe)
  }

  /**
    * Email, displayName, or id list should be specified.
    */
  def listPeople(email: Option[String] = None,
                 displayName: Option[String] = None,
                 id: Option[String] = None,
                 orgId: Option[String] = None,
                 max: Option[Int] = None): F[List[Person]] = {
    client
      .execute[ListPeople, People](ListPeople(email, displayName, id, orgId, max))
      .map(_.items)
  }

  def createPerson(emails: String,
                   displayName: Option[String] = None,
                   firstName: Option[String] = None,
                   lastName: Option[String] = None,
                   avatar: Option[String] = None,
                   orgId: Option[String] = None,
                   roles: Option[String] = None,
                   licenses: Option[String] = None,
                   created: Option[String] = None,
                   lastModified: Option[String] = None,
                   status: Option[String] = None,
                   invitePending: Option[Boolean] = None,
                   loginEnabled: Option[Boolean] = None,
                   `type`: Option[String] = None): F[Person] = {
    client.execute(CreatePerson(emails, displayName, firstName,
      lastName, avatar, orgId, roles, licenses, created,
      lastModified, status, invitePending, loginEnabled, `type`))
  }

  def getPerson(personId: String): F[Person] = {
    client.execute(GetPersonDetails(personId))
  }

  def updatePerson(personId: String,
                   displayName: String,
                   emails: Option[String] = None,
                   firstName: Option[String] = None,
                   lastName: Option[String] = None,
                   avatar: Option[String] = None,
                   orgId: Option[String] = None,
                   roles: Option[String] = None,
                   licenses: Option[String] = None): F[Person] = {
    client.execute(UpdatePerson(personId, displayName, emails, firstName,
      lastName, avatar, orgId, roles, licenses))
  }

  def deletePerson(personId: String): F[Unit] = {
    client.execute(DeletePerson(personId))
  }
}
