package com.twitter.product_mitwitterer.component_library.scorer.cortetwitter

import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import inference.GrpcService.ModelInferResponse.InferOutputTensor

/**
 * Etwittertractor defining how a Scorer should go from outputted tensors to the individual results
 * for each candidate being scored.
 *
 * @tparam Result the type of the Value being returned.
 * Users can pass in an anonymous function
 */
trait ModelFeatureEtwittertractor[-Query <: PipelineQuery, Result] {
  def apply(query: Query, tensorOutput: Seq[InferOutputTensor]): Seq[Result]
}
