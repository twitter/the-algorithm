package com.tw ter.product_m xer.component_l brary.scorer.tensorbu lder

 mport  nference.GrpcServ ce.Model nferRequest. nfer nputTensor

case object Bytes nfer nputTensorBu lder extends  nfer nputTensorBu lder[Str ng] {
  def apply(
    featureNa : Str ng,
    featureValues: Seq[Str ng]
  ): Seq[ nfer nputTensor] = {
    val tensorShape = Seq(featureValues.s ze, 1)
     nfer nputTensorBu lder.bu ldBytes nfer nputTensor(featureNa , featureValues, tensorShape)
  }
}
