package com.X.tweetypie.util

import com.X.finagle.Backoff
import com.X.finagle.service.RetryPolicy
import com.X.finagle.service.RetryPolicy.RetryableWriteException
import com.X.servo.exception.thriftscala.ServerError
import com.X.util.Duration
import com.X.util.Throw
import com.X.util.TimeoutException
import com.X.util.Try

object RetryPolicyBuilder {

  /**
   * Retry on any exception.
   */
  def anyFailure[A](backoffs: Stream[Duration]): RetryPolicy[Try[A]] =
    RetryPolicy.backoff[Try[A]](Backoff.fromStream(backoffs)) {
      case Throw(_) => true
    }

  /**
   * Retry on com.X.util.TimeoutException
   */
  def timeouts[A](backoffs: Stream[Duration]): RetryPolicy[Try[A]] =
    RetryPolicy.backoff[Try[A]](Backoff.fromStream(backoffs)) {
      case Throw(_: TimeoutException) => true
    }

  /**
   * Retry on com.X.finagle.service.RetryableWriteExceptions
   */
  def writes[A](backoffs: Stream[Duration]): RetryPolicy[Try[A]] =
    RetryPolicy.backoff[Try[A]](Backoff.fromStream(backoffs)) {
      case Throw(RetryableWriteException(_)) => true
    }

  /**
   * Retry on com.X.servo.exception.thriftscala.ServerError
   */
  def servoServerError[A](backoffs: Stream[Duration]): RetryPolicy[Try[A]] =
    RetryPolicy.backoff[Try[A]](Backoff.fromStream(backoffs)) {
      case Throw(ServerError(_)) => true
    }
}
