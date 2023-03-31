package com.twitter.simclustersann.exceptions

import com.twitter.finatra.thrift.exceptions.ExceptionMapper
import com.twitter.finatra.thrift.thriftscala.ClientError
import com.twitter.finatra.thrift.thriftscala.ClientErrorCause
import com.twitter.util.Future
import com.twitter.util.logging.Logging
import javax.inject.Singleton

/**
 * An exception mapper designed to handle
 * [[com.twitter.simclustersann.exceptions.InvalidRequestForSimClustersAnnVariantException]]
 * by returning a Thrift IDL defined Client Error.
 */
@Singleton
class InvalidRequestForSimClustersAnnVariantExceptionMapper
    extends ExceptionMapper[InvalidRequestForSimClustersAnnVariantException, Nothing]
    with Logging {

  override def handleException(
    throwable: InvalidRequestForSimClustersAnnVariantException
  ): Future[Nothing] = {
    error("Invalid Request For SimClusters Ann Variant Exception", throwable)

    Future.exception(ClientError(ClientErrorCause.BadRequest, throwable.getMessage()))
  }
}
