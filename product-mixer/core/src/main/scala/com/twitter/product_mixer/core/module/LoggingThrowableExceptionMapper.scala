package com.twitter.product_mixer.core.module

import com.twitter.finatra.thrift.exceptions.ExceptionMapper
import com.twitter.inject.Logging
import com.twitter.util.Future
import javax.inject.Singleton
import scala.util.control.NonFatal

/**
 * Similar to [[com.twitter.finatra.thrift.internal.exceptions.ThrowableExceptionMapper]]
 *
 * But this one also logs the exceptions.
 */
@Singleton
class LoggingThrowableExceptionMapper extends ExceptionMapper[Throwable, Nothing] with Logging {

  override def handleException(throwable: Throwable): Future[Nothing] = {
    error("Unhandled Exception", throwable)

    throwable match {
      case NonFatal(e) => Future.exception(e)
    }
  }
}
