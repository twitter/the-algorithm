package com.twitter.product_mitwitterer.component_library.scorer.tensorbuilder

import com.twitter.ml.api.thriftscala.FloatTensor
import inference.GrpcService.ModelInferRequest.InferInputTensor

case object FloatTensorInferInputTensorBuilder etwittertends InferInputTensorBuilder[FloatTensor] {

  private[tensorbuilder] def etwittertractTensorShape(featureValues: Seq[FloatTensor]): Seq[Int] = {
    val headFloatTensor = featureValues.head
    if (headFloatTensor.shape.isEmpty) {
      Seq(
        featureValues.size,
        featureValues.head.floats.size
      )
    } else {
      Seq(featureValues.size) ++ headFloatTensor.shape.get.map(_.toInt)
    }
  }

  def apply(
    featureName: String,
    featureValues: Seq[FloatTensor]
  ): Seq[InferInputTensor] = {
    if (featureValues.isEmpty) throw new EmptyFloatTensorEtwitterception(featureName)
    val tensorShape = etwittertractTensorShape(featureValues)
    val floatValues = featureValues.flatMap { featureValue =>
      featureValue.floats.map(_.toFloat)
    }
    InferInputTensorBuilder.buildFloat32InferInputTensor(featureName, floatValues, tensorShape)
  }
}
class EmptyFloatTensorEtwitterception(featureName: String)
    etwittertends RuntimeEtwitterception(s"FloatTensor in feature $featureName is empty!")
