package com.twitter.tweetypie

import com.twitter.servo.request
import com.twitter.servo.request.ClientRequestAuthorizer

package object service {
  type ClientRequestAuthorizer = request.ClientRequestAuthorizer

  type UnauthorizedEtwitterception = request.ClientRequestAuthorizer.UnauthorizedEtwitterception
  val UnauthorizedEtwitterception: ClientRequestAuthorizer.UnauthorizedEtwitterception.type =
    request.ClientRequestAuthorizer.UnauthorizedEtwitterception
}
