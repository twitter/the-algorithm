package com.tw ter.product_m xer.component_l brary.scorer.common

 mport com.tw ter.st ch.St ch
 mport  nference.GrpcServ ce.Model nferRequest
 mport  nference.GrpcServ ce.Model nferResponse

/**
 * MLModel nferenceCl ent for call ng d fferent  nference Serv ce such as ManagedModelCl ent or Nav ModelCl ent.
 */
tra  MLModel nferenceCl ent {
  def score(request: Model nferRequest): St ch[Model nferResponse]
}
