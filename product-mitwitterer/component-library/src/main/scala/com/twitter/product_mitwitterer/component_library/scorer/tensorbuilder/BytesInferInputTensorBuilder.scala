package com.twitter.product_mitwitterer.component_library.scorer.tensorbuilder

import inference.GrpcService.ModelInferRequest.InferInputTensor

case object BytesInferInputTensorBuilder etwittertends InferInputTensorBuilder[String] {
  def apply(
    featureName: String,
    featureValues: Seq[String]
  ): Seq[InferInputTensor] = {
    val tensorShape = Seq(featureValues.size, 1)
    InferInputTensorBuilder.buildBytesInferInputTensor(featureName, featureValues, tensorShape)
  }
}
