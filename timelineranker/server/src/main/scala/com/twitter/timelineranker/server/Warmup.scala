package com.tw ter.t  l neranker.server

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.thr ft.Cl ent d
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.t  l neranker.model._
 mport com.tw ter.t  l nes.warmup.Tw terServerWarmup
 mport com.tw ter.t  l neserv ce.model.T  l ne d
 mport com.tw ter.t  l neserv ce.model.core.T  l neK nd
 mport com.tw ter.t  l neranker.conf g.T  l neRankerConstants
 mport com.tw ter.t  l neranker.thr ftscala.{T  l neRanker => Thr ftT  l neRanker}
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.Durat on

object Warmup {
  val WarmupForward ngT  : Durat on = 25.seconds
}

class Warmup(
  local nstance: T  l neRanker,
  forward ngCl ent: Thr ftT  l neRanker. thodPerEndpo nt,
  overr de val logger: Logger)
    extends Tw terServerWarmup {
  overr de val WarmupCl ent d: Cl ent d = Cl ent d(T  l neRankerConstants.WarmupCl entNa )
  overr de val NumWarmupRequests = 20
  overr de val M nSuccessfulRequests = 10

  pr vate[t ] val warmupUser d = Math.abs(scala.ut l.Random.nextLong())
  pr vate[server] val reverseChronQuery = ReverseChronT  l neQuery(
     d = new T  l ne d(warmupUser d, T  l neK nd.ho ),
    maxCount = So (20),
    range = So (T et dRange.default)
  ).toThr ft
  pr vate[server] val recapQuery = RecapQuery(
    user d = warmupUser d,
    maxCount = So (20),
    range = So (T et dRange.default)
  ).toThr ftRecapQuery

  overr de def sendS ngleWarmupRequest(): Future[Un ] = {
    val localWarmups = Seq(
      local nstance.getT  l nes(Seq(reverseChronQuery)),
      local nstance.getRecycledT etCand dates(Seq(recapQuery))
    )

    // send forward ng requests but  gnore fa lures
    forward ngCl ent.getT  l nes(Seq(reverseChronQuery)).un .handle {
      case e => logger.warn ng(e, "foward ng request fa led")
    }

    Future.jo n(localWarmups).un 
  }
}
