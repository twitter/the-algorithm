package com.tw ter.graph_feature_serv ce.server.handlers

 mport com.tw ter.f natra.thr ft.rout ng.Thr ftWarmup
 mport com.tw ter.graph_feature_serv ce.thr ftscala.EdgeType.Favor edBy
 mport com.tw ter.graph_feature_serv ce.thr ftscala.EdgeType.Follo dBy
 mport com.tw ter.graph_feature_serv ce.thr ftscala.EdgeType.Follow ng
 mport com.tw ter.graph_feature_serv ce.thr ftscala.Server.Get ntersect on
 mport com.tw ter.graph_feature_serv ce.thr ftscala.FeatureType
 mport com.tw ter.graph_feature_serv ce.thr ftscala.Gfs ntersect onRequest
 mport com.tw ter. nject.ut ls.Handler
 mport com.tw ter.scrooge.Request
 mport com.tw ter.ut l.logg ng.Logger
 mport javax. nject. nject
 mport javax. nject.S ngleton
 mport scala.ut l.Random

@S ngleton
class ServerWarmupHandler @ nject() (warmup: Thr ftWarmup) extends Handler {

  val logger: Logger = Logger("WarmupHandler")

  // TODO: Add t  test ng accounts to warm-up t  serv ce.
  pr vate val test ngAccounts: Array[Long] = Seq.empty.toArray

  pr vate def getRandomRequest: Gfs ntersect onRequest = {
    Gfs ntersect onRequest(
      test ngAccounts(Random.next nt(test ngAccounts.length)),
      test ngAccounts,
      Seq(FeatureType(Follow ng, Follo dBy), FeatureType(Follow ng, Favor edBy))
    )
  }

  overr de def handle(): Un  = {
    warmup.sendRequest(
      Get ntersect on,
      Request(
        Get ntersect on.Args(
          getRandomRequest
        )),
      10
    )()

    logger. nfo("Warmup Done!")
  }
}
