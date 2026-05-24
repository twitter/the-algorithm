package com.twitter.servo.util

import com.twitter.finagle.thrift.ClientId

/**
 * A trait defining contextual information necessary to authorize
 * and observe a request.
 */
trait Observable {
  val requestName: String
  val clientId: Option[ClientId]

  /**
   * An Option[String] representation of the request-issuer's ClientId.
   */
  lazy val clientIdString: Option[String] =
    // It's possible for `ClientId.name` to be `null`, so we wrap it in
    // `Option()` to force such cases to be None.
    clientId flatMap { cid =>
      Option(cid.name)
    }
}
