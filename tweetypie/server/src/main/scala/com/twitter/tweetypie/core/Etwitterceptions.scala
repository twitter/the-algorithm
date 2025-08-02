package com.twitter.tweetypie
package core

import scala.util.control.NoStackTrace

case class InternalServerError(message: String) etwittertends Etwitterception(message) with NoStackTrace

case class OverCapacity(message: String) etwittertends Etwitterception(message) with NoStackTrace

case class RateLimited(message: String) etwittertends Etwitterception(message) with NoStackTrace

case class TweetHydrationError(message: String, cause: Option[Throwable] = None)
    etwittertends Etwitterception(message, cause.getOrElse(null))
    with NoStackTrace
