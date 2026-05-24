package com.twitter.tweetypie
package core

import scala.util.control.NoStackTrace

case class InternalServerError(message: String) extends Exception(message) with NoStackTrace

case class OverCapacity(message: String) extends Exception(message) with NoStackTrace

case class RateLimited(message: String) extends Exception(message) with NoStackTrace

case class TweetHydrationError(message: String, cause: Option[Throwable] = None)
    extends Exception(message, cause.getOrElse(null))
    with NoStackTrace
