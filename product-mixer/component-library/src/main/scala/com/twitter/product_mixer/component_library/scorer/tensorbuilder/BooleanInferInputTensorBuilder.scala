package com.tw ter.product_m xer.component_l brary.scorer.tensorbu lder

 mport  nference.GrpcServ ce.Model nferRequest. nfer nputTensor

case object Boolean nfer nputTensorBu lder extends  nfer nputTensorBu lder[Boolean] {
  def apply(
    featureNa : Str ng,
    featureValues: Seq[Boolean]
  ): Seq[ nfer nputTensor] = {
    val tensorShape = Seq(featureValues.s ze, 1)
     nfer nputTensorBu lder.bu ldBool nfer nputTensor(featureNa , featureValues, tensorShape)
  }
}
