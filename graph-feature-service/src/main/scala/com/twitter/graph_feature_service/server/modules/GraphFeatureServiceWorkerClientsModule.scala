package com.tw ter.graph_feature_serv ce.server.modules

 mport com.google. nject.Prov des
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.mtls.cl ent.MtlsStackCl ent._
 mport com.tw ter.f nagle.Thr ftMux
 mport com.tw ter.f nagle.serv ce.RetryBudget
 mport com.tw ter.graph_feature_serv ce.thr ftscala
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter. nject.annotat ons.Flag
 mport com.tw ter.ut l.{Awa , Durat on}
 mport javax. nject.S ngleton

case class GraphFeatureServ ceWorkerCl ents(
  workers: Seq[thr ftscala.Worker. thodPerEndpo nt])

object GraphFeatureServ ceWorkerCl entsModule extends Tw terModule {
  pr vate[t ] val closeableGracePer od: Durat on = 1.second
  pr vate[t ] val requestT  out: Durat on = 25.m ll s

  @Prov des
  @S ngleton
  def prov deGraphFeatureServ ceWorkerCl ent(
    @Flag(ServerFlagNa s.NumWorkers) numWorkers:  nt,
    @Flag(ServerFlagNa s.Serv ceRole) serv ceRole: Str ng,
    @Flag(ServerFlagNa s.Serv ceEnv) serv ceEnv: Str ng,
    serv ce dent f er: Serv ce dent f er
  ): GraphFeatureServ ceWorkerCl ents = {

    val workers: Seq[thr ftscala.Worker. thodPerEndpo nt] =
      (0 unt l numWorkers).map {  d =>
        val dest = s"/srv#/$serv ceEnv/local/$serv ceRole/graph_feature_serv ce-worker-$ d"

        val cl ent = Thr ftMux.cl ent
          .w hRequestT  out(requestT  out)
          .w hRetryBudget(RetryBudget.Empty)
          .w hMutualTls(serv ce dent f er)
          .bu ld[thr ftscala.Worker. thodPerEndpo nt](dest, s"worker-$ d")

        onEx  {
          val closeable = cl ent.asClosable
          Awa .result(closeable.close(closeableGracePer od), closeableGracePer od)
        }

        cl ent
      }

    GraphFeatureServ ceWorkerCl ents(workers)
  }
}
