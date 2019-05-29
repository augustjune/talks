package webex.model

case class Room(id: String,
                title: String,
                `type`: String,
                isLocked: Boolean,
                teamId: Option[String],
                lastActivity: String,
                creatorId: String,
                created: String) {
  def isGroup: Boolean = `type` == "group"

  def isDirect: Boolean = !isGroup
}

