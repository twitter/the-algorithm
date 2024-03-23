package com.ExTwitter.ann.service.query_server.common

import com.ExTwitter.ann.common.thriftscala.BadRequest
import com.ExTwitter.mediaservices.commons._

object RuntimeExceptionTransform extends ExceptionTransformer {
  override def transform = {
    case e: BadRequest =>
      MisuseExceptionInfo(e)
  }

  override def getStatName: PartialFunction[Exception, String] = {
    case e: BadRequest => exceptionName(e, e.code.name)
  }
}
