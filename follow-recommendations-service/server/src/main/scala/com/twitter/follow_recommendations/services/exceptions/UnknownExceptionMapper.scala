package com.ExTwitter.follow_recommendations.service.exceptions

import com.ExTwitter.finatra.thrift.exceptions.ExceptionMapper
import com.ExTwitter.inject.Logging
import com.ExTwitter.util.Future
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
