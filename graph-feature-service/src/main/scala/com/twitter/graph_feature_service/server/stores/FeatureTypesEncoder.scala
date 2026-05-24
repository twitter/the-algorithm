package com.twitter.graph_feature_service.server.stores

import com.twitter.graph_feature_service.common.Configs.RandomSeed
import com.twitter.graph_feature_service.thriftscala.FeatureType
import scala.util.hashing.MurmurHash3

object FeatureTypesEncoder {

  def apply(featureTypes: Seq[FeatureType]): String = {
    val byteArray = featureTypes.flatMap { featureType =>
      Array(featureType.leftEdgeType.getValue.toByte, featureType.rightEdgeType.getValue.toByte)
    }.toArray
    (MurmurHash3.bytesHash(byteArray, RandomSeed) & 0x7fffffff).toString // keep positive
  }

}
