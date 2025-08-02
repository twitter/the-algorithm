package com.twitter.product_mitwitterer.core.module

import com.twitter.finatra.thrift.etwitterceptions.EtwitterceptionMapper
import com.twitter.inject.Logging
import com.twitter.util.Future
import javatwitter.inject.Singleton
import scala.util.control.NonFatal

/**
 * Similar to [[com.twitter.finatra.thrift.internal.etwitterceptions.ThrowableEtwitterceptionMapper]]
 *
 * But this one also logs the etwitterceptions.
 */
@Singleton
class LoggingThrowableEtwitterceptionMapper etwittertends EtwitterceptionMapper[Throwable, Nothing] with Logging {

  override def handleEtwitterception(throwable: Throwable): Future[Nothing] = {
    error("Unhandled Etwitterception", throwable)

    throwable match {
      case NonFatal(e) => Future.etwitterception(e)
    }
  }
}
