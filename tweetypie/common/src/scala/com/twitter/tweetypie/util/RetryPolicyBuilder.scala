package com.twitter.tweetypie.util

import com.twitter.finagle.Backoff
import com.twitter.finagle.service.RetryPolicy
import com.twitter.finagle.service.RetryPolicy.RetryableWriteEtwitterception
import com.twitter.servo.etwitterception.thriftscala.ServerError
import com.twitter.util.Duration
import com.twitter.util.Throw
import com.twitter.util.TimeoutEtwitterception
import com.twitter.util.Try

object RetryPolicyBuilder {

  /**
   * Retry on any etwitterception.
   */
  def anyFailure[A](backoffs: Stream[Duration]): RetryPolicy[Try[A]] =
    RetryPolicy.backoff[Try[A]](Backoff.fromStream(backoffs)) {
      case Throw(_) => true
    }

  /**
   * Retry on com.twitter.util.TimeoutEtwitterception
   */
  def timeouts[A](backoffs: Stream[Duration]): RetryPolicy[Try[A]] =
    RetryPolicy.backoff[Try[A]](Backoff.fromStream(backoffs)) {
      case Throw(_: TimeoutEtwitterception) => true
    }

  /**
   * Retry on com.twitter.finagle.service.RetryableWriteEtwitterceptions
   */
  def writes[A](backoffs: Stream[Duration]): RetryPolicy[Try[A]] =
    RetryPolicy.backoff[Try[A]](Backoff.fromStream(backoffs)) {
      case Throw(RetryableWriteEtwitterception(_)) => true
    }

  /**
   * Retry on com.twitter.servo.etwitterception.thriftscala.ServerError
   */
  def servoServerError[A](backoffs: Stream[Duration]): RetryPolicy[Try[A]] =
    RetryPolicy.backoff[Try[A]](Backoff.fromStream(backoffs)) {
      case Throw(ServerError(_)) => true
    }
}
