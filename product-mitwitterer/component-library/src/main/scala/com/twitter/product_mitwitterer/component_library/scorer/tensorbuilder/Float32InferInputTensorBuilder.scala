package com.twitter.product_mitwitterer.component_library.scorer.tensorbuilder

import inference.GrpcService.ModelInferRequest.InferInputTensor

case object Float32InferInputTensorBuilder etwittertends InferInputTensorBuilder[AnyVal] {

  private def toFloat(twitter: AnyVal): Float = {
    twitter match {
      case y: Float => y
      case y: Int => y.toFloat
      case y: Long => y.toFloat
      case y: Double => y.toFloat
      case y => throw new UnetwitterpectedDataTypeEtwitterception(y, this)
    }
  }

  def apply(
    featureName: String,
    featureValues: Seq[AnyVal]
  ): Seq[InferInputTensor] = {
    val tensorShape = Seq(featureValues.size, 1)
    InferInputTensorBuilder.buildFloat32InferInputTensor(
      featureName,
      featureValues.map(toFloat),
      tensorShape)
  }
}
