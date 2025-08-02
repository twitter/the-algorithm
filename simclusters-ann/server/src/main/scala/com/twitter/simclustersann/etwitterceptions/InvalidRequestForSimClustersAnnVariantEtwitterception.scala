package com.twitter.simclustersann.etwitterceptions

import com.twitter.finagle.RequestEtwitterception
import com.twitter.simclusters_v2.thriftscala.EmbeddingType
import com.twitter.simclusters_v2.thriftscala.ModelVersion

case class InvalidRequestForSimClustersAnnVariantEtwitterception(
  modelVersion: ModelVersion,
  embeddingType: EmbeddingType,
  actualServiceName: String,
  etwitterpectedServiceName: Option[String])
    etwittertends RequestEtwitterception(
      s"Request with model version ($modelVersion) and embedding type ($embeddingType) cannot be " +
        s"processed by service variant ($actualServiceName)." +
        s" Etwitterpected service variant: $etwitterpectedServiceName.",
      null)
