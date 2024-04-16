package com.tw ter.t  l nes.pred ct on.features.two_hop_features

 mport com.tw ter.graph_feature_serv ce.thr ftscala.EdgeType
 mport com.tw ter.ml.ap .Feature._
 mport scala.collect on.JavaConverters._
 mport TwoHopFeaturesConf g.personalDataTypesMap

object TwoHopFeaturesDescr ptor {
  val pref x = "two_hop"
  val normal zedPostf x = "normal zed"
  val leftNodeDegreePostf x = "left_degree"
  val r ghtNodeDegreePostf x = "r ght_degree"

  type TwoHopFeatureMap = Map[(EdgeType, EdgeType), Cont nuous]
  type TwoHopFeatureNodeDegreeMap = Map[EdgeType, Cont nuous]

  def apply(edgeTypePa rs: Seq[(EdgeType, EdgeType)]): TwoHopFeaturesDescr ptor = {
    new TwoHopFeaturesDescr ptor(edgeTypePa rs)
  }
}

class TwoHopFeaturesDescr ptor(edgeTypePa rs: Seq[(EdgeType, EdgeType)]) {
   mport TwoHopFeaturesDescr ptor._

  def getLeftEdge(edgeTypePa r: (EdgeType, EdgeType)): EdgeType = {
    edgeTypePa r._1
  }

  def getLeftEdgeNa (edgeTypePa r: (EdgeType, EdgeType)): Str ng = {
    getLeftEdge(edgeTypePa r).or g nalNa .toLo rCase
  }

  def getR ghtEdge(edgeTypePa r: (EdgeType, EdgeType)): EdgeType = {
    edgeTypePa r._2
  }

  def getR ghtEdgeNa (edgeTypePa r: (EdgeType, EdgeType)): Str ng = {
    getR ghtEdge(edgeTypePa r).or g nalNa .toLo rCase
  }

  val rawFeaturesMap: TwoHopFeatureMap = edgeTypePa rs.map(edgeTypePa r => {
    val leftEdgeType = getLeftEdge(edgeTypePa r)
    val leftEdgeNa  = getLeftEdgeNa (edgeTypePa r)
    val r ghtEdgeType = getR ghtEdge(edgeTypePa r)
    val r ghtEdgeNa  = getR ghtEdgeNa (edgeTypePa r)
    val personalDataTypes = (
      personalDataTypesMap.getOrElse(leftEdgeType, Set.empty) ++
        personalDataTypesMap.getOrElse(r ghtEdgeType, Set.empty)
    ).asJava
    val rawFeature = new Cont nuous(s"$pref x.$leftEdgeNa .$r ghtEdgeNa ", personalDataTypes)
    edgeTypePa r -> rawFeature
  })(collect on.breakOut)

  val leftNodeDegreeFeaturesMap: TwoHopFeatureNodeDegreeMap = edgeTypePa rs.map(edgeTypePa r => {
    val leftEdgeType = getLeftEdge(edgeTypePa r)
    val leftEdgeNa  = getLeftEdgeNa (edgeTypePa r)
    val personalDataTypes = personalDataTypesMap.getOrElse(leftEdgeType, Set.empty).asJava
    val leftNodeDegreeFeature =
      new Cont nuous(s"$pref x.$leftEdgeNa .$leftNodeDegreePostf x", personalDataTypes)
    leftEdgeType -> leftNodeDegreeFeature
  })(collect on.breakOut)

  val r ghtNodeDegreeFeaturesMap: TwoHopFeatureNodeDegreeMap = edgeTypePa rs.map(edgeTypePa r => {
    val r ghtEdgeType = getR ghtEdge(edgeTypePa r)
    val r ghtEdgeNa  = getR ghtEdgeNa (edgeTypePa r)
    val personalDataTypes = personalDataTypesMap.getOrElse(r ghtEdgeType, Set.empty).asJava
    val r ghtNodeDegreeFeature =
      new Cont nuous(s"$pref x.$r ghtEdgeNa .$r ghtNodeDegreePostf x", personalDataTypes)
    r ghtEdgeType -> r ghtNodeDegreeFeature
  })(collect on.breakOut)

  val normal zedFeaturesMap: TwoHopFeatureMap = edgeTypePa rs.map(edgeTypePa r => {
    val leftEdgeType = getLeftEdge(edgeTypePa r)
    val leftEdgeNa  = getLeftEdgeNa (edgeTypePa r)
    val r ghtEdgeType = getR ghtEdge(edgeTypePa r)
    val r ghtEdgeNa  = getR ghtEdgeNa (edgeTypePa r)
    val personalDataTypes = (
      personalDataTypesMap.getOrElse(leftEdgeType, Set.empty) ++
        personalDataTypesMap.getOrElse(r ghtEdgeType, Set.empty)
    ).asJava
    val normal zedFeature =
      new Cont nuous(s"$pref x.$leftEdgeNa .$r ghtEdgeNa .$normal zedPostf x", personalDataTypes)
    edgeTypePa r -> normal zedFeature
  })(collect on.breakOut)

  pr vate val rawFeaturesSeq: Seq[Cont nuous] = rawFeaturesMap.values.toSeq
  pr vate val leftNodeDegreeFeaturesSeq: Seq[Cont nuous] = leftNodeDegreeFeaturesMap.values.toSeq
  pr vate val r ghtNodeDegreeFeaturesSeq: Seq[Cont nuous] = r ghtNodeDegreeFeaturesMap.values.toSeq
  pr vate val normal zedFeaturesSeq: Seq[Cont nuous] = normal zedFeaturesMap.values.toSeq

  val featuresSeq: Seq[Cont nuous] =
    rawFeaturesSeq ++ leftNodeDegreeFeaturesSeq ++ r ghtNodeDegreeFeaturesSeq ++ normal zedFeaturesSeq
}
