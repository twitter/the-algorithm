package com.tw ter.product_m xer.component_l brary.scorer.common

 mport com.tw ter.f nagle.Http
 mport com.tw ter.f nagle.grpc.F nagleChannelBu lder
 mport com.tw ter.f nagle.grpc.FutureConverters
 mport com.tw ter.mlserv ng.frontend.TFServ ng nferenceServ ce mpl
 mport com.tw ter.st ch.St ch
 mport tensorflow.serv ng.Pred ct onServ ceGrpc
 mport  nference.GrpcServ ce.Model nferRequest
 mport  nference.GrpcServ ce.Model nferResponse
 mport  o.grpc.ManagedChannel
 mport  o.grpc.Status

/**
 * Cl ent wrapper for call ng a Nav   nference Serv ce (go/nav ).
 * @param httpCl ent F nagle HTTP Cl ent to use for connect on.
 * @param modelPath W ly path to t  ML Model serv ce (e.g. /s/role/serv ce).
 */
case class Nav ModelCl ent(
  httpCl ent: Http.Cl ent,
  modelPath: Str ng)
    extends MLModel nferenceCl ent {

  pr vate val channel: ManagedChannel =
    F nagleChannelBu lder
      .forTarget(modelPath)
      .httpCl ent(httpCl ent)
      // Nav  enforces an author y na .
      .overr deAuthor y("rustserv ng")
      // certa n GRPC errors need to be retr ed.
      .enableRetryForStatus(Status.UNKNOWN)
      .enableRetryForStatus(Status.RESOURCE_EXHAUSTED)
      // t   s requ red at channel level as mTLS  s enabled at httpCl ent level
      .usePla ntext()
      .bu ld()

  pr vate val  nferenceServ ceStub = Pred ct onServ ceGrpc.newFutureStub(channel)

  def score(request: Model nferRequest): St ch[Model nferResponse] = {
    val tfServ ngRequest = TFServ ng nferenceServ ce mpl.adaptModel nferRequest(request)
    St ch
      .callFuture(
        FutureConverters
          .R chL stenableFuture( nferenceServ ceStub.pred ct(tfServ ngRequest)).toTw ter
          .map { response =>
            TFServ ng nferenceServ ce mpl.adaptModel nferResponse(response)
          }
      )
  }
}
