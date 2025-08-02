package com.twitter.product_mitwitterer.component_library.scorer.tensorbuilder

import inference.GrpcService.InferTensorContents
import inference.GrpcService.ModelInferRequest.InferInputTensor

case object SparseMapInferInputTensorBuilder
    etwittertends InferInputTensorBuilder[Option[Map[Int, Double]]] {

  private final val batchFeatureNameSuffitwitter: String = "batch"
  private final val keyFeatureNameSuffitwitter: String = "key"
  private final val valueFeatureNameSuffitwitter: String = "value"

  def apply(
    featureName: String,
    featureValues: Seq[Option[Map[Int, Double]]]
  ): Seq[InferInputTensor] = {
    val batchIdsTensorContents = InferTensorContents.newBuilder()
    val sparseKeysTensorContents = InferTensorContents.newBuilder()
    val sparseValuesTensorContents = InferTensorContents.newBuilder()
    featureValues.zipWithIndetwitter.foreach {
      case (featureValueOption, batchIndetwitter) =>
        featureValueOption.foreach { featureValue =>
          featureValue.foreach {
            case (sparseKey, sparseValue) =>
              batchIdsTensorContents.addInt64Contents(batchIndetwitter.toLong)
              sparseKeysTensorContents.addInt64Contents(sparseKey.toLong)
              sparseValuesTensorContents.addFp32Contents(sparseValue.floatValue)
          }
        }
    }

    val batchIdsInputTensor = InferInputTensor
      .newBuilder()
      .setName(Seq(featureName, batchFeatureNameSuffitwitter).mkString("_"))
      .addShape(batchIdsTensorContents.getInt64ContentsCount)
      .addShape(1)
      .setDatatype("INT64")
      .setContents(batchIdsTensorContents)
      .build()

    val sparseKeysInputTensor = InferInputTensor
      .newBuilder()
      .setName(Seq(featureName, keyFeatureNameSuffitwitter).mkString("_"))
      .addShape(sparseKeysTensorContents.getInt64ContentsCount)
      .addShape(1)
      .setDatatype("INT64")
      .setContents(sparseKeysTensorContents)
      .build()

    val sparseValuesInputTensor = InferInputTensor
      .newBuilder()
      .setName(Seq(featureName, valueFeatureNameSuffitwitter).mkString("_"))
      .addShape(sparseValuesTensorContents.getFp32ContentsCount)
      .addShape(1)
      .setDatatype("FP32")
      .setContents(sparseValuesTensorContents)
      .build()

    Seq(batchIdsInputTensor, sparseKeysInputTensor, sparseValuesInputTensor)
  }
}
