package com.tw ter.graph_feature_serv ce.server.stores

 mport com.tw ter.graph_feature_serv ce.common.Conf gs.RandomSeed
 mport com.tw ter.graph_feature_serv ce.thr ftscala.FeatureType
 mport scala.ut l.hash ng.MurmurHash3

object FeatureTypesEncoder {

  def apply(featureTypes: Seq[FeatureType]): Str ng = {
    val byteArray = featureTypes.flatMap { featureType =>
      Array(featureType.leftEdgeType.getValue.toByte, featureType.r ghtEdgeType.getValue.toByte)
    }.toArray
    (MurmurHash3.bytesHash(byteArray, RandomSeed) & 0x7fffffff).toStr ng // keep pos  ve
  }

}
