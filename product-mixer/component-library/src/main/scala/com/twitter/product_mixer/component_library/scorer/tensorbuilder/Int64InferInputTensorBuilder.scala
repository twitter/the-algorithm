package com.tw ter.product_m xer.component_l brary.scorer.tensorbu lder

 mport com.tw ter.ml.featurestore.l b.D screte
 mport  nference.GrpcServ ce.Model nferRequest. nfer nputTensor

case object  nt64 nfer nputTensorBu lder extends  nfer nputTensorBu lder[AnyVal] {

  pr vate def toLong(x: AnyVal): Long = {
    x match {
      case y:  nt => y.toLong
      case y: Long => y
      case y: D screte => y.value
      case y => throw new UnexpectedDataTypeExcept on(y, t )
    }
  }
  def apply(
    featureNa : Str ng,
    featureValues: Seq[AnyVal]
  ): Seq[ nfer nputTensor] = {
    val tensorShape = Seq(featureValues.s ze, 1)
     nfer nputTensorBu lder.bu ld nt64 nfer nputTensor(
      featureNa ,
      featureValues.map(toLong),
      tensorShape)
  }
}
