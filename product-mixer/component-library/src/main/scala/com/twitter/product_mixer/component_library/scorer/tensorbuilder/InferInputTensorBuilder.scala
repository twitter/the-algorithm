package com.tw ter.product_m xer.component_l brary.scorer.tensorbu lder

 mport com.google.protobuf.ByteStr ng
 mport com.tw ter.product_m xer.core.feature.Feature
 mport  nference.GrpcServ ce. nferTensorContents
 mport  nference.GrpcServ ce.Model nferRequest. nfer nputTensor

// T  class conta ns most of common vers ons at Tw ter, but  n t  future   can add more:
// https://g hub.com/kserve/kserve/blob/master/docs/pred ct-ap /v2/requ red_ap .md#tensor-data-1

tra   nfer nputTensorBu lder[Value] {

  def apply(
    featureNa : Str ng,
    featureValues: Seq[Value]
  ): Seq[ nfer nputTensor]

}

object  nfer nputTensorBu lder {

  def c ckTensorShapeMatc sValueLength(
    featureNa : Str ng,
    featureValues: Seq[Any],
    tensorShape: Seq[ nt]
  ): Un  = {
    val featureValuesS ze = featureValues.s ze
    val tensorShapeS ze = tensorShape.product
     f (featureValuesS ze != tensorShapeS ze) {
      throw new FeatureValuesAndShapeM smatchExcept on(
        featureNa ,
        featureValuesS ze,
        tensorShapeS ze)
    }
  }

  def bu ldBool nfer nputTensor(
    featureNa : Str ng,
    featureValues: Seq[Boolean],
    tensorShape: Seq[ nt]
  ): Seq[ nfer nputTensor] = {

    c ckTensorShapeMatc sValueLength(featureNa , featureValues, tensorShape)

    val  nputTensorBu lder =  nfer nputTensor.newBu lder().setNa (featureNa )
    tensorShape.foreach { shape =>
       nputTensorBu lder.addShape(shape)
    }
    val  nputTensor =  nputTensorBu lder
      .setDatatype("BOOL")
      .setContents {
        val contents =  nferTensorContents.newBu lder()
        featureValues.foreach { featureValue =>
          contents.addBoolContents(featureValue)
        }
        contents
      }
      .bu ld()
    Seq( nputTensor)
  }

  def bu ldBytes nfer nputTensor(
    featureNa : Str ng,
    featureValues: Seq[Str ng],
    tensorShape: Seq[ nt]
  ): Seq[ nfer nputTensor] = {

    c ckTensorShapeMatc sValueLength(featureNa , featureValues, tensorShape)

    val  nputTensorBu lder =  nfer nputTensor.newBu lder().setNa (featureNa )
    tensorShape.foreach { shape =>
       nputTensorBu lder.addShape(shape)
    }
    val  nputTensor =  nputTensorBu lder
      .setDatatype("BYTES")
      .setContents {
        val contents =  nferTensorContents.newBu lder()
        featureValues.foreach { featureValue =>
          val featureValueBytes = ByteStr ng.copyFromUtf8(featureValue)
          contents.addByteContents(featureValueBytes)
        }
        contents
      }
      .bu ld()
    Seq( nputTensor)
  }

  def bu ldFloat32 nfer nputTensor(
    featureNa : Str ng,
    featureValues: Seq[Float],
    tensorShape: Seq[ nt]
  ): Seq[ nfer nputTensor] = {

    c ckTensorShapeMatc sValueLength(featureNa , featureValues, tensorShape)

    val  nputTensorBu lder =  nfer nputTensor.newBu lder().setNa (featureNa )
    tensorShape.foreach { shape =>
       nputTensorBu lder.addShape(shape)
    }
    val  nputTensor =  nputTensorBu lder
      .setDatatype("FP32")
      .setContents {
        val contents =  nferTensorContents.newBu lder()
        featureValues.foreach { featureValue =>
          contents.addFp32Contents(featureValue.floatValue)
        }
        contents
      }
      .bu ld()
    Seq( nputTensor)
  }

  def bu ld nt64 nfer nputTensor(
    featureNa : Str ng,
    featureValues: Seq[Long],
    tensorShape: Seq[ nt]
  ): Seq[ nfer nputTensor] = {

    c ckTensorShapeMatc sValueLength(featureNa , featureValues, tensorShape)

    val  nputTensorBu lder =  nfer nputTensor.newBu lder().setNa (featureNa )
    tensorShape.foreach { shape =>
       nputTensorBu lder.addShape(shape)
    }
    val  nputTensor =  nputTensorBu lder
      .setDatatype(" NT64")
      .setContents {
        val contents =  nferTensorContents.newBu lder()
        featureValues.foreach { featureValue =>
          contents.add nt64Contents(featureValue)
        }
        contents
      }
      .bu ld()
    Seq( nputTensor)
  }
}

class UnexpectedFeatureTypeExcept on(feature: Feature[_, _])
    extends UnsupportedOperat onExcept on(s"Unsupported Feature type passed  n $feature")

class FeatureValuesAndShapeM smatchExcept on(
  featureNa : Str ng,
  featureValuesS ze:  nt,
  tensorShapeS ze:  nt)
    extends UnsupportedOperat onExcept on(
      s"Feature $featureNa  has m smatch ng FeatureValues (s ze: $featureValuesS ze) and TensorShape (s ze: $tensorShapeS ze)!")

class UnexpectedDataTypeExcept on[T](value: T, bu lder:  nfer nputTensorBu lder[_])
    extends UnsupportedOperat onExcept on(
      s"Unsupported data type ${value} passed  n at ${bu lder.getClass.toStr ng}")
