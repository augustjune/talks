package webex.api.syntax

import webex.api.{MessagesApi, PeopleApi}
import webex.model.{Message, Messages, Person}

final class RichPerson(person: Person) {

  /**
    * Deletes this person account
    */
  def delete[F[_]](implicit peopleApi: PeopleApi[F]): F[Unit] =
    peopleApi.deletePerson(person.id)

  /**
    * Sends direct message to this person
    */
  def sendMessage[F[_]](text: String, fileUrl: Option[String] = None)
                       (implicit messagesApi: MessagesApi[F]): F[Message] =
    messagesApi.sendDirectMessage(person.id, text, fileUrl)

  /**
    * Lists messages received from this person in direct chat
    */
  def receivedMessages[F[_]](implicit messagesApi: MessagesApi[F]): F[Messages] =
    messagesApi.listDirectMessages(person.id)
}
