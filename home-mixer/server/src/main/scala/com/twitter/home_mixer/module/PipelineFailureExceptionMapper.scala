package com.ExTwitter.home_mixer.module

import com.ExTwitter.finatra.thrift.exceptions.ExceptionMapper
import com.ExTwitter.home_mixer.{thriftscala => t}
import com.ExTwitter.util.logging.Logging
import com.ExTwitter.product_mixer.core.pipeline.pipeline_failure.PipelineFailure
import com.ExTwitter.product_mixer.core.pipeline.pipeline_failure.ProductDisabled
import com.ExTwitter.scrooge.ThriftException
import com.ExTwitter.util.Future
import javax.inject.Singleton

@Singleton
class PipelineFailureExceptionMapper
    extends ExceptionMapper[PipelineFailure, ThriftException]
    with Logging {

  def handleException(throwable: PipelineFailure): Future[ThriftException] = {
    throwable match {
      // SliceService (unlike UrtService) throws an exception when the requested product is disabled
      case PipelineFailure(ProductDisabled, reason, _, _) =>
        Future.exception(
          t.ValidationExceptionList(errors =
            Seq(t.ValidationException(t.ValidationErrorCode.ProductDisabled, reason))))
      case _ =>
        error("Unhandled PipelineFailure", throwable)
        Future.exception(throwable)
    }
  }
}
