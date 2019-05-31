package webex.api

import webex.api.specific._
import webex.clients.WebexClient

class WebexApi[F[_]](val client: WebexClient[F])
  extends MembershipsApi[F]
    with MessagesApi[F]
    with PeopleApi[F]
    with RoomsApi[F]
    with WebhooksApi[F]
