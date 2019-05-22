package webex

case class Message(id: String,
                   roomId: String,
                   roomType: String,
                   text: String,
                   personId: String,
                   personEmail: String,
                   mentionedPeople: Option[List[String]],
                   created: String)
