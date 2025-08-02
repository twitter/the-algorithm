package com.twitter.product_mitwitterer.core.pipeline

import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.model.marshalling.request.HasClientContetwittert
import com.twitter.product_mitwitterer.core.model.marshalling.request.HasDebugOptions
import com.twitter.product_mitwitterer.core.model.marshalling.request.HasProduct
import com.twitter.timelines.configapi.HasParams
import com.twitter.timelines.configapi.Param
import com.twitter.util.Time

trait PipelineQuery etwittertends HasParams with HasClientContetwittert with HasProduct with HasDebugOptions {
  self =>

  /** Set a query time val that is constant for the duration of the query lifecycle */
  val queryTime: Time = self.debugOptions.flatMap(_.requestTimeOverride).getOrElse(Time.now)

  /** The requested matwitter results is specified, or not specified, by the thrift client */
  def requestedMatwitterResults: Option[Int]

  /** Retrieves the matwitter results with a default Param, if not specified by the thrift client */
  def matwitterResults(defaultRequestedMatwitterResultParam: Param[Int]): Int =
    requestedMatwitterResults.getOrElse(params(defaultRequestedMatwitterResultParam))

  /** Optional [[FeatureMap]], this may be updated later using [[withFeatureMap]] */
  def features: Option[FeatureMap]

  /**
   * Since Query-Level features can be hydrated later, we need this method to update the PipelineQuery
   * usually this will be implemented via `copy(features = Some(features))`
   */
  def withFeatureMap(features: FeatureMap): PipelineQuery
}
