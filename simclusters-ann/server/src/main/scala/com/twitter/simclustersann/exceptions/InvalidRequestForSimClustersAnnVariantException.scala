package com.twitter.simclustersann.exceptions

import com.twitter.finagle.RequestException
import com.twitter.simclusters_v2.thriftscala.EmbeddingType
import com.twitter.simclusters_v2.thriftscala.ModelVersion

case class InvalidRequestForSimClustersAnnVariantException(
  modelVersion: ModelVersion,
  embeddingType: EmbeddingType,
  actualServiceName: String,
  expectedServiceName: Option[String])
    extends RequestException(
      s"Request with model version ($modelVersion) and embedding type ($embeddingType) cannot be " +
        s"processed by service variant ($actualServiceName)." +
        s" Expected service variant: $expectedServiceName.",
      null)
