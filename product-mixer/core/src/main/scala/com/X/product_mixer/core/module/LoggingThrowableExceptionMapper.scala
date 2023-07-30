package com.X.product_mixer.core.module

import com.X.finatra.thrift.exceptions.ExceptionMapper
import com.X.inject.Logging
import com.X.util.Future
import javax.inject.Singleton
import scala.util.control.NonFatal

/**
 * Similar to [[com.X.finatra.thrift.internal.exceptions.ThrowableExceptionMapper]]
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
