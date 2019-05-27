package webex.methods

/**
  * @tparam R type of expected response
  */
trait Method[R] {

  def requestMethod: RequestMethod

  def route: String

  def body: Option[String]
}
