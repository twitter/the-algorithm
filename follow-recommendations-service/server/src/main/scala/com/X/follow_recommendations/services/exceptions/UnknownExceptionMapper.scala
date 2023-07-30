package com.X.follow_recommendations.service.exceptions

import com.X.finatra.thrift.exceptions.ExceptionMapper
import com.X.inject.Logging
import com.X.util.Future
import javax.inject.Singleton

@Singleton
class UnknownLoggingExceptionMapper extends ExceptionMapper[Exception, Throwable] with Logging {
  def handleException(throwable: Exception): Future[Throwable] = {
    error(
      s"Unmapped Exception: ${throwable.getMessage} - ${throwable.getStackTrace.mkString(", \n\t")}",
      throwable
    )

    Future.exception(throwable)
  }
}
