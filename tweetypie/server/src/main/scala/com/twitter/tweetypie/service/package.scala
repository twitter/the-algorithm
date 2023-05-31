package com.twitter.tweetypie

import com.twitter.servo.request
import com.twitter.servo.request.ClientRequestAuthorizer

package object service {
  type ClientRequestAuthorizer = request.ClientRequestAuthorizer

  type UnauthorizedException = request.ClientRequestAuthorizer.UnauthorizedException
  val UnauthorizedException: ClientRequestAuthorizer.UnauthorizedException.type =
    request.ClientRequestAuthorizer.UnauthorizedException
}
