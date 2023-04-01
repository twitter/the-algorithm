package com.twitter.interaction_graph.scio.common

import com.twitter.interaction_graph.thriftscala.FeatureName

object FeatureGroups {

  val HEALTH_FEATURE_LIST: Set[FeatureName] = Set(
    FeatureName.NumMutes,
    FeatureName.NumBlocks,
    FeatureName.NumReportAsSpams,
    FeatureName.NumReportAsAbuses
  )

  val STATUS_FEATURE_LIST: Set[FeatureName] = Set(
    FeatureName.AddressBookEmail,
    FeatureName.AddressBookPhone,
    FeatureName.AddressBookInBoth,
    FeatureName.AddressBookMutualEdgeEmail,
    FeatureName.AddressBookMutualEdgePhone,
    FeatureName.AddressBookMutualEdgeInBoth,
    FeatureName.NumFollows,
    FeatureName.NumUnfollows,
    FeatureName.NumMutualFollows
  ) ++ HEALTH_FEATURE_LIST

  val DWELL_TIME_FEATURE_LIST: Set[FeatureName] = Set(
    FeatureName.TotalDwellTime,
    FeatureName.NumInspectedStatuses
  )
}
