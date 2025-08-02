package com.twitter.follow_recommendations.service.etwitterceptions

import com.twitter.finatra.thrift.etwitterceptions.EtwitterceptionMapper
import com.twitter.inject.Logging
import com.twitter.util.Future
import javatwitter.inject.Singleton

@Singleton
class UnknownLoggingEtwitterceptionMapper etwittertends EtwitterceptionMapper[Etwitterception, Throwable] with Logging {
  def handleEtwitterception(throwable: Etwitterception): Future[Throwable] = {
    error(
      s"Unmapped Etwitterception: ${throwable.getMessage} - ${throwable.getStackTrace.mkString(", \n\t")}",
      throwable
    )

    Future.etwitterception(throwable)
  }
}
