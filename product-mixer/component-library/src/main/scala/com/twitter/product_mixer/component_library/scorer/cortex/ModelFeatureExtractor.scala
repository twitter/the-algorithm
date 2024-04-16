package com.tw ter.product_m xer.component_l brary.scorer.cortex

 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport  nference.GrpcServ ce.Model nferResponse. nferOutputTensor

/**
 * Extractor def n ng how a Scorer should go from outputted tensors to t   nd v dual results
 * for each cand date be ng scored.
 *
 * @tparam Result t  type of t  Value be ng returned.
 * Users can pass  n an anonymous funct on
 */
tra  ModelFeatureExtractor[-Query <: P pel neQuery, Result] {
  def apply(query: Query, tensorOutput: Seq[ nferOutputTensor]): Seq[Result]
}
