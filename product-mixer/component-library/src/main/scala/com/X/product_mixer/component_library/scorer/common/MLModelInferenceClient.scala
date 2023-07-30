package com.X.product_mixer.component_library.scorer.common

import com.X.stitch.Stitch
import inference.GrpcService.ModelInferRequest
import inference.GrpcService.ModelInferResponse

/**
 * MLModelInferenceClient for calling different Inference Service such as ManagedModelClient or NaviModelClient.
 */
trait MLModelInferenceClient {
  def score(request: ModelInferRequest): Stitch[ModelInferResponse]
}
