package com.tw ter. nteract on_graph.sc o.common

 mport com.tw ter. nteract on_graph.thr ftscala.FeatureNa 

object FeatureGroups {

  val HEALTH_FEATURE_L ST: Set[FeatureNa ] = Set(
    FeatureNa .NumMutes,
    FeatureNa .NumBlocks,
    FeatureNa .NumReportAsSpams,
    FeatureNa .NumReportAsAbuses
  )

  val STATUS_FEATURE_L ST: Set[FeatureNa ] = Set(
    FeatureNa .AddressBookEma l,
    FeatureNa .AddressBookPhone,
    FeatureNa .AddressBook nBoth,
    FeatureNa .AddressBookMutualEdgeEma l,
    FeatureNa .AddressBookMutualEdgePhone,
    FeatureNa .AddressBookMutualEdge nBoth,
    FeatureNa .NumFollows,
    FeatureNa .NumUnfollows,
    FeatureNa .NumMutualFollows
  ) ++ HEALTH_FEATURE_L ST

  val DWELL_T ME_FEATURE_L ST: Set[FeatureNa ] = Set(
    FeatureNa .TotalD llT  ,
    FeatureNa .Num nspectedStatuses
  )
}
