package com.twitter.product_mixer.component_library.scorer.tensorbuilder

import inference.GrpcService.ModelInferRequest.InferInputTensor

case object BytesInferInputTensorBuilder extends InferInputTensorBuilder[String] {
  def apply(
    featureName: String,
    featureValues: Seq[String]
  ): Seq[InferInputTensor] = {
    val tensorShape = Seq(featureValues.size, 1)
    InferInputTensorBuilder.buildBytesInferInputTensor(featureName, featureValues, tensorShape)
  }
}
