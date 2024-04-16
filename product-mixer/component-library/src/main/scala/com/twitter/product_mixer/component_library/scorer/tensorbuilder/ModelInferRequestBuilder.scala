package com.tw ter.product_m xer.component_l brary.scorer.tensorbu lder

 mport com.tw ter.product_m xer.component_l brary.scorer.common.ModelSelector
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport  nference.GrpcServ ce. nferPara ter
 mport  nference.GrpcServ ce.Model nferRequest
 mport scala.collect on.JavaConverters._

class Model nferRequestBu lder[-Query <: P pel neQuery, -Cand date <: Un versalNoun[Any]](
  query nfer nputTensorBu lders: Seq[Query nfer nputTensorBu lder[Query, Any]],
  cand date nfer nputTensorBu lders: Seq[
    Cand date nfer nputTensorBu lder[Cand date, Any]
  ],
  modelS gnatureNa : Str ng,
  modelSelector: ModelSelector[Query]) {

  pr vate val modelS gnature:  nferPara ter =
     nferPara ter.newBu lder().setStr ngParam(modelS gnatureNa ).bu ld()

  def apply(
    query: Query,
    cand dates: Seq[Cand dateW hFeatures[Cand date]],
  ): Model nferRequest = {
    val  nferRequest = Model nferRequest
      .newBu lder()
      .putPara ters("s gnature_na ", modelS gnature)
    modelSelector.apply(query).foreach { modelNa  =>
       nferRequest.setModelNa (modelNa )
    }
    query nfer nputTensorBu lders.foreach { bu lder =>
       nferRequest.addAll nputs(bu lder(query).asJava)
    }
    cand date nfer nputTensorBu lders.foreach { bu lder =>
       nferRequest.addAll nputs(bu lder(cand dates).asJava)
    }
     nferRequest.bu ld()
  }
}
