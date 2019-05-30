package webex.model


case class Membership(id: String,
                      roomId: String,
                      roomType: Option[String],
                      personId: String,
                      personEmail: String,
                      personDisplayName: String,
                      personOrgId: String,
                      isModerator: Boolean,
                      isMonitor: Boolean,
                      isRoomHidden: Option[Boolean],
                      created: String)
