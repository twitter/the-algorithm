package com.twitter.ann.service.query_server.common

import com.twitter.ann.common.thriftscala.BadRequest
import com.twitter.mediaservices.commons._

object RuntimeEtwitterceptionTransform etwittertends EtwitterceptionTransformer {
  override def transform = {
    case e: BadRequest =>
      MisuseEtwitterceptionInfo(e)
  }

  override def getStatName: PartialFunction[Etwitterception, String] = {
    case e: BadRequest => etwitterceptionName(e, e.code.name)
  }
}
