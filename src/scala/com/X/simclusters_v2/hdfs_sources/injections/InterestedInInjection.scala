package com.X.simclusters_v2.hdfs_sources.injections

import com.X.scalding_internal.multiformat.format.keyval.KeyValInjection
import com.X.scalding_internal.multiformat.format.keyval.KeyValInjection.StringUtf8
import com.X.scalding_internal.multiformat.format.keyval.KeyValInjection.Long2BigEndian
import com.X.scalding_internal.multiformat.format.keyval.KeyValInjection.ScalaCompactThrift
import com.X.simclusters_v2.thriftscala.ClustersUserIsInterestedIn

object InterestedInInjection {
  val injection = KeyValInjection(Long2BigEndian, ScalaCompactThrift(ClustersUserIsInterestedIn))
  val languageInjection =
    KeyValInjection(StringUtf8, ScalaCompactThrift(ClustersUserIsInterestedIn))
}
