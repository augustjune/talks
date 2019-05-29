package webex.methods

sealed trait RequestMethod

case object Get extends RequestMethod

case object Post extends RequestMethod

case object Delete extends RequestMethod

case object Put extends RequestMethod
