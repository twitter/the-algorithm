package com.tw ter.t  l nes.pred ct on.features.two_hop_features

 mport com.tw ter.dal.personal_data.thr ftjava.PersonalDataType
 mport com.tw ter.graph_feature_serv ce.thr ftscala.{EdgeType, FeatureType}

object TwoHopFeaturesConf g {
  val leftEdgeTypes = Seq(EdgeType.Follow ng, EdgeType.Favor e, EdgeType.MutualFollow)
  val r ghtEdgeTypes = Seq(
    EdgeType.Follo dBy,
    EdgeType.Favor edBy,
    EdgeType.Ret etedBy,
    EdgeType. nt onedBy,
    EdgeType.MutualFollow)

  val edgeTypePa rs: Seq[(EdgeType, EdgeType)] = {
    for (leftEdgeType <- leftEdgeTypes; r ghtEdgeType <- r ghtEdgeTypes)
      y eld (leftEdgeType, r ghtEdgeType)
  }

  val featureTypes: Seq[FeatureType] = edgeTypePa rs.map(pa r => FeatureType(pa r._1, pa r._2))

  val personalDataTypesMap: Map[EdgeType, Set[PersonalDataType]] = Map(
    EdgeType.Follow ng -> Set(PersonalDataType.CountOfFollo rsAndFollo es),
    EdgeType.Favor e -> Set(
      PersonalDataType.CountOfPr vateL kes,
      PersonalDataType.CountOfPubl cL kes),
    EdgeType.MutualFollow -> Set(PersonalDataType.CountOfFollo rsAndFollo es),
    EdgeType.Follo dBy -> Set(PersonalDataType.CountOfFollo rsAndFollo es)
  )
}
