package com.tw ter.product_m xer.component_l brary.scorer.tensorbu lder

 mport com.tw ter.ml.ap .thr ftscala.FloatTensor
 mport  nference.GrpcServ ce.Model nferRequest. nfer nputTensor

case object FloatTensor nfer nputTensorBu lder extends  nfer nputTensorBu lder[FloatTensor] {

  pr vate[tensorbu lder] def extractTensorShape(featureValues: Seq[FloatTensor]): Seq[ nt] = {
    val  adFloatTensor = featureValues. ad
     f ( adFloatTensor.shape. sEmpty) {
      Seq(
        featureValues.s ze,
        featureValues. ad.floats.s ze
      )
    } else {
      Seq(featureValues.s ze) ++  adFloatTensor.shape.get.map(_.to nt)
    }
  }

  def apply(
    featureNa : Str ng,
    featureValues: Seq[FloatTensor]
  ): Seq[ nfer nputTensor] = {
     f (featureValues. sEmpty) throw new EmptyFloatTensorExcept on(featureNa )
    val tensorShape = extractTensorShape(featureValues)
    val floatValues = featureValues.flatMap { featureValue =>
      featureValue.floats.map(_.toFloat)
    }
     nfer nputTensorBu lder.bu ldFloat32 nfer nputTensor(featureNa , floatValues, tensorShape)
  }
}
class EmptyFloatTensorExcept on(featureNa : Str ng)
    extends Runt  Except on(s"FloatTensor  n feature $featureNa   s empty!")
