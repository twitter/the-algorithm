package com.twitter.simclustersann.etwitterceptions

import com.twitter.finatra.thrift.etwitterceptions.EtwitterceptionMapper
import com.twitter.finatra.thrift.thriftscala.ClientError
import com.twitter.finatra.thrift.thriftscala.ClientErrorCause
import com.twitter.util.Future
import com.twitter.util.logging.Logging
import javatwitter.inject.Singleton

/**
 * An etwitterception mapper designed to handle
 * [[com.twitter.simclustersann.etwitterceptions.InvalidRequestForSimClustersAnnVariantEtwitterception]]
 * by returning a Thrift IDL defined Client Error.
 */
@Singleton
class InvalidRequestForSimClustersAnnVariantEtwitterceptionMapper
    etwittertends EtwitterceptionMapper[InvalidRequestForSimClustersAnnVariantEtwitterception, Nothing]
    with Logging {

  override def handleEtwitterception(
    throwable: InvalidRequestForSimClustersAnnVariantEtwitterception
  ): Future[Nothing] = {
    error("Invalid Request For SimClusters Ann Variant Etwitterception", throwable)

    Future.etwitterception(ClientError(ClientErrorCause.BadRequest, throwable.getMessage()))
  }
}
