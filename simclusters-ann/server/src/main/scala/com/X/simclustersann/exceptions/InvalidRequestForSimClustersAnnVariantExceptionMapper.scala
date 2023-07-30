package com.X.simclustersann.exceptions

import com.X.finatra.thrift.exceptions.ExceptionMapper
import com.X.finatra.thrift.thriftscala.ClientError
import com.X.finatra.thrift.thriftscala.ClientErrorCause
import com.X.util.Future
import com.X.util.logging.Logging
import javax.inject.Singleton

/**
 * An exception mapper designed to handle
 * [[com.X.simclustersann.exceptions.InvalidRequestForSimClustersAnnVariantException]]
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
