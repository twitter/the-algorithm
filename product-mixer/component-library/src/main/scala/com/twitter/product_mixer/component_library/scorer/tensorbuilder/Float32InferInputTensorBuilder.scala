package com.twitter.product_mixer.component_library.scorer.tensorbuilder

import inference.GrpcService.ModelInferRequest.InferInputTensor

case object Float32InferInputTensorBuilder extends InferInputTensorBuilder[AnyVal] {

  private def toFloat(x: AnyVal): Float = {
    x match {
      case y: Float => y
      case y: Int => y.toFloat
      case y: Long => y.toFloat
      case y: Double => y.toFloat
      case y => throw new UnexpectedDataTypeException(y, this)
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
