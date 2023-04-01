package com.twitter.product_mixer.component_library.scorer.cortex

import com.twitter.product_mixer.core.pipeline.PipelineQuery
import inference.GrpcService.ModelInferResponse.InferOutputTensor

/**
 * Extractor defining how a Scorer should go from outputted tensors to the individual results
 * for each candidate being scored.
 *
 * @tparam Result the type of the Value being returned.
 * Users can pass in an anonymous function
 */
trait ModelFeatureExtractor[-Query <: PipelineQuery, Result] {
  def apply(query: Query, tensorOutput: Seq[InferOutputTensor]): Seq[Result]
}
