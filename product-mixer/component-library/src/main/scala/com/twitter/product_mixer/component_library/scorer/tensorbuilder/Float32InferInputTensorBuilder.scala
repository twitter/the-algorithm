package com.tw ter.product_m xer.component_l brary.scorer.tensorbu lder

 mport  nference.GrpcServ ce.Model nferRequest. nfer nputTensor

case object Float32 nfer nputTensorBu lder extends  nfer nputTensorBu lder[AnyVal] {

  pr vate def toFloat(x: AnyVal): Float = {
    x match {
      case y: Float => y
      case y:  nt => y.toFloat
      case y: Long => y.toFloat
      case y: Double => y.toFloat
      case y => throw new UnexpectedDataTypeExcept on(y, t )
    }
  }

  def apply(
    featureNa : Str ng,
    featureValues: Seq[AnyVal]
  ): Seq[ nfer nputTensor] = {
    val tensorShape = Seq(featureValues.s ze, 1)
     nfer nputTensorBu lder.bu ldFloat32 nfer nputTensor(
      featureNa ,
      featureValues.map(toFloat),
      tensorShape)
  }
}
