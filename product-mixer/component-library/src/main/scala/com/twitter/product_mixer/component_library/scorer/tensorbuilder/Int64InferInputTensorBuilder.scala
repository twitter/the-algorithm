package com.twitter.product_mixer.component_library.scorer.tensorbuilder

import com.twitter.ml.featurestore.lib.Discrete
import inference.GrpcService.ModelInferRequest.InferInputTensor

case object Int64InferInputTensorBuilder extends InferInputTensorBuilder[AnyVal] {

  private def toLong(x: AnyVal): Long = {
    x match {
      case y: Int => y.toLong
      case y: Long => y
      case y: Discrete => y.value
      case y => throw new UnexpectedDataTypeException(y, this)
    }
  }
  def apply(
    featureName: String,
    featureValues: Seq[AnyVal]
  ): Seq[InferInputTensor] = {
    val tensorShape = Seq(featureValues.size, 1)
    InferInputTensorBuilder.buildInt64InferInputTensor(
      featureName,
      featureValues.map(toLong),
      tensorShape)
  }
}
