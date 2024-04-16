package com.tw ter.ho _m xer.module

 mport com.google. nject.Prov des
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.Http
 mport com.tw ter.f nagle.grpc.F nagleChannelBu lder
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.mtls.cl ent.MtlsStackCl ent.MtlsStackCl entSyntax
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.t  l nes.cl ents.pred ct onserv ce.Pred ct onGRPCServ ce
 mport com.tw ter.ut l.Durat on
 mport  o.grpc.ManagedChannel
 mport javax. nject.S ngleton

object Nav ModelCl entModule extends Tw terModule {

  @S ngleton
  @Prov des
  def prov desPred ct onGRPCServ ce(
    serv ce dent f er: Serv ce dent f er,
  ): Pred ct onGRPCServ ce = {
    //  W ly path to t  ML Model serv ce (e.g. /s/ml-serv ng/nav -explore-ranker).
    val modelPath = "/s/ml-serv ng/nav _ho _recap_onnx"

    val MaxPred ct onT  outMs: Durat on = 500.m ll s
    val ConnectT  outMs: Durat on = 200.m ll s
    val Acqu s  onT  outMs: Durat on = 500.m ll s
    val MaxRetryAttempts:  nt = 2

    val cl ent = Http.cl ent
      .w hLabel(modelPath)
      .w hMutualTls(serv ce dent f er)
      .w hRequestT  out(MaxPred ct onT  outMs)
      .w hTransport.connectT  out(ConnectT  outMs)
      .w hSess on.acqu s  onT  out(Acqu s  onT  outMs)
      .w hHttpStats

    val channel: ManagedChannel = F nagleChannelBu lder
      .forTarget(modelPath)
      .overr deAuthor y("rustserv ng")
      .maxRetryAttempts(MaxRetryAttempts)
      .enableRetryForStatus( o.grpc.Status.RESOURCE_EXHAUSTED)
      .enableRetryForStatus( o.grpc.Status.UNKNOWN)
      .enableUnsafeFullyBuffer ngMode()
      .httpCl ent(cl ent)
      .bu ld()

    new Pred ct onGRPCServ ce(channel)
  }
}
