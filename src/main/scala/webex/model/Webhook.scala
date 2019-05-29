package webex.model

case class Webhook(id: String,
                   name: String,
                   targetUrl: String,
                   resource: String,
                   event: String,
                   filter: String,
                   secret: String,
                   status: String,
                   created: String)
