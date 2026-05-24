package com.twitter.follow_recommendations.service.exceptions

import com.twitter.finatra.thrift.exceptions.ExceptionMapper
import com.twitter.inject.Logging
import com.twitter.util.Future
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
