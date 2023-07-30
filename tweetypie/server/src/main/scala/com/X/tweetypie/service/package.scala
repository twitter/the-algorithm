package com.X.tweetypie

import com.X.servo.request
import com.X.servo.request.ClientRequestAuthorizer

package object service {
  type ClientRequestAuthorizer = request.ClientRequestAuthorizer

  type UnauthorizedException = request.ClientRequestAuthorizer.UnauthorizedException
  val UnauthorizedException: ClientRequestAuthorizer.UnauthorizedException.type =
    request.ClientRequestAuthorizer.UnauthorizedException
}
