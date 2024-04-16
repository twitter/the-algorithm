package com.tw ter.product_m xer.component_l brary.scorer.common

 mport com.tw ter.f nagle.Http
 mport com.tw ter.f nagle.grpc.F nagleChannelBu lder
 mport com.tw ter.f nagle.grpc.FutureConverters
 mport com.tw ter.st ch.St ch
 mport  nference.GRPC nferenceServ ceGrpc
 mport  nference.GrpcServ ce.Model nferRequest
 mport  nference.GrpcServ ce.Model nferResponse
 mport  o.grpc.ManagedChannel

/**
 * Cl ent wrapper for call ng a Cortex Managed  nference Serv ce (go/cm s) ML Model us ng GRPC.
 * @param httpCl ent F nagle HTTP Cl ent to use for connect on.
 * @param modelPath W ly path to t  ML Model serv ce (e.g. /cluster/local/role/serv ce/ nstance).
 */
case class ManagedModelCl ent(
  httpCl ent: Http.Cl ent,
  modelPath: Str ng)
    extends MLModel nferenceCl ent {

  pr vate val channel: ManagedChannel =
    F nagleChannelBu lder.forTarget(modelPath).httpCl ent(httpCl ent).bu ld()

  pr vate val  nferenceServ ceStub = GRPC nferenceServ ceGrpc.newFutureStub(channel)

  def score(request: Model nferRequest): St ch[Model nferResponse] = {
    St ch
      .callFuture(
        FutureConverters
          .R chL stenableFuture( nferenceServ ceStub.model nfer(request)).toTw ter)
  }
}
