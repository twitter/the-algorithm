package com.twitter.ann.service.query_server.common

import com.twitter.ann.common.thriftscala.BadRequest
import com.twitter.mediaservices.commons._

object RuntimeExceptionTransform extends ExceptionTransformer {
  override def transform = {
    case e: BadRequest =>
      MisuseExceptionInfo(e)
  }

  override def getStatName: PartialFunction[Exception, String] = {
    case e: BadRequest => exceptionName(e, e.code.name)
  }
}
