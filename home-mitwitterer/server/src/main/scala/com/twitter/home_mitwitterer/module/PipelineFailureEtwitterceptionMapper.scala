package com.twitter.home_mitwitterer.module

import com.twitter.finatra.thrift.etwitterceptions.EtwitterceptionMapper
import com.twitter.home_mitwitterer.{thriftscala => t}
import com.twitter.util.logging.Logging
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.ProductDisabled
import com.twitter.scrooge.ThriftEtwitterception
import com.twitter.util.Future
import javatwitter.inject.Singleton

@Singleton
class PipelineFailureEtwitterceptionMapper
    etwittertends EtwitterceptionMapper[PipelineFailure, ThriftEtwitterception]
    with Logging {

  def handleEtwitterception(throwable: PipelineFailure): Future[ThriftEtwitterception] = {
    throwable match {
      // SliceService (unlike UrtService) throws an etwitterception when the requested product is disabled
      case PipelineFailure(ProductDisabled, reason, _, _) =>
        Future.etwitterception(
          t.ValidationEtwitterceptionList(errors =
            Seq(t.ValidationEtwitterception(t.ValidationErrorCode.ProductDisabled, reason))))
      case _ =>
        error("Unhandled PipelineFailure", throwable)
        Future.etwitterception(throwable)
    }
  }
}
