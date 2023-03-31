package com.twitter.product_mixer.component_library.scorer.tensorbuilder

import inference.GrpcService.ModelInferRequest.InferInputTensor

case object BooleanInferInputTensorBuilder extends InferInputTensorBuilder[Boolean] {
  def apply(
    featureName: String,
    featureValues: Seq[Boolean]
  ): Seq[InferInputTensor] = {
    val tensorShape = Seq(featureValues.size, 1)
    InferInputTensorBuilder.buildBoolInferInputTensor(featureName, featureValues, tensorShape)
  }
}
