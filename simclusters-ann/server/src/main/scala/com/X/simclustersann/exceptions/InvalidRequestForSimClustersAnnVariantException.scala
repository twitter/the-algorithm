package com.X.simclustersann.exceptions

import com.X.finagle.RequestException
import com.X.simclusters_v2.thriftscala.EmbeddingType
import com.X.simclusters_v2.thriftscala.ModelVersion

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
