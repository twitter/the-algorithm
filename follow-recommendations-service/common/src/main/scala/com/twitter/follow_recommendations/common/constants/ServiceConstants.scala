package com.twitter.follow_recommendations.common.constants

import com.twitter.conversions.StorageUnitOps._

object ServiceConstants {

  /** thrift client response size limits
   *  these were estimated using monitoring dashboard
   *  3MB network usage per second / 25 rps ~ 120KB/req << 1MB
   *  we give some buffer here in case some requests require more data than others
   */
  val StringLengthLimit: Long =
    10.megabyte.inBytes
  val ContainerLengthLimit: Long = 1.megabyte.inBytes
}
