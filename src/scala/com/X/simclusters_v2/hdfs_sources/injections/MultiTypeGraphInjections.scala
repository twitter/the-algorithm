package com.X.simclusters_v2.hdfs_sources.injections

import com.X.scalding_internal.multiformat.format.keyval.KeyValInjection
import com.X.scalding_internal.multiformat.format.keyval.KeyValInjection.ScalaCompactThrift
import com.X.simclusters_v2.thriftscala.LeftNode
import com.X.simclusters_v2.thriftscala.NounWithFrequencyList
import com.X.simclusters_v2.thriftscala.RightNode
import com.X.simclusters_v2.thriftscala.RightNodeTypeStruct
import com.X.simclusters_v2.thriftscala.RightNodeWithEdgeWeightList
import com.X.simclusters_v2.thriftscala.SimilarRightNodes
import com.X.simclusters_v2.thriftscala.CandidateTweetsList
import com.X.scalding_internal.multiformat.format.keyval.KeyValInjection.Long2BigEndian

object MultiTypeGraphInjections {
  final val truncatedMultiTypeGraphInjection =
    KeyValInjection(ScalaCompactThrift(LeftNode), ScalaCompactThrift(RightNodeWithEdgeWeightList))
  final val topKRightNounListInjection =
    KeyValInjection(
      ScalaCompactThrift(RightNodeTypeStruct),
      ScalaCompactThrift(NounWithFrequencyList))
  final val similarRightNodesInjection =
    KeyValInjection[RightNode, SimilarRightNodes](
      ScalaCompactThrift(RightNode),
      ScalaCompactThrift(SimilarRightNodes)
    )
  final val tweetRecommendationsInjection =
    KeyValInjection[Long, CandidateTweetsList](
      Long2BigEndian,
      ScalaCompactThrift(CandidateTweetsList)
    )
}
