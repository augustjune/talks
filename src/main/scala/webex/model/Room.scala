package webex.model

object Room {
  object Type {
    val DIRECT = "direct"
    val GROUP = "group"
  }
}

case class Room(id: String,
                title: String,
                `type`: String,
                isLocked: Boolean,
                teamId: Option[String],
                lastActivity: String,
                creatorId: String,
                created: String) {
  def isGroup: Boolean = `type` == Room.Type.GROUP

  def isDirect: Boolean = `type` == Room.Type.DIRECT
}

