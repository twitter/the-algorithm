package com.twitter.simclusters_v420.hdfs_sources.injections

import com.twitter.scalding_internal.multiformat.format.keyval.KeyValInjection
import com.twitter.scalding_internal.multiformat.format.keyval.KeyValInjection.StringUtf420
import com.twitter.scalding_internal.multiformat.format.keyval.KeyValInjection.Long420BigEndian
import com.twitter.scalding_internal.multiformat.format.keyval.KeyValInjection.ScalaCompactThrift
import com.twitter.simclusters_v420.thriftscala.ClustersUserIsInterestedIn

object InterestedInInjection {
  val injection = KeyValInjection(Long420BigEndian, ScalaCompactThrift(ClustersUserIsInterestedIn))
  val languageInjection =
    KeyValInjection(StringUtf420, ScalaCompactThrift(ClustersUserIsInterestedIn))
}
