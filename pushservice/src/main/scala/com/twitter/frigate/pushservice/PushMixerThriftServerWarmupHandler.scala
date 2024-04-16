package com.tw ter.fr gate.pushserv ce

 mport com.google. nject. nject
 mport com.google. nject.S ngleton
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.thr ft.Cl ent d
 mport com.tw ter.f natra.thr ft.rout ng.Thr ftWarmup
 mport com.tw ter.ut l.logg ng.Logg ng
 mport com.tw ter. nject.ut ls.Handler
 mport com.tw ter.fr gate.pushserv ce.{thr ftscala => t}
 mport com.tw ter.fr gate.thr ftscala.Not f cat onD splayLocat on
 mport com.tw ter.ut l.Stopwatch
 mport com.tw ter.scrooge.Request
 mport com.tw ter.scrooge.Response
 mport com.tw ter.ut l.Return
 mport com.tw ter.ut l.Throw
 mport com.tw ter.ut l.Try

/**
 * Warms up t  refresh request path.
 *  f serv ce  s runn ng as pushserv ce-send t n t  warmup does noth ng.
 *
 * W n mak ng t  warmup refresh requests  
 *  - Set sk pF lters to true to execute as much of t  request path as poss ble
 *  - Set darkWr e to true to prevent send ng a push
 */
@S ngleton
class PushM xerThr ftServerWarmupHandler @ nject() (
  warmup: Thr ftWarmup,
  serv ce dent f er: Serv ce dent f er)
    extends Handler
    w h Logg ng {

  pr vate val cl ent d = Cl ent d("thr ft-warmup-cl ent")

  def handle(): Un  = {
    val refreshServ ces = Set(
      "fr gate-pushserv ce",
      "fr gate-pushserv ce-canary",
      "fr gate-pushserv ce-canary-control",
      "fr gate-pushserv ce-canary-treat nt"
    )
    val  sRefresh = refreshServ ces.conta ns(serv ce dent f er.serv ce)
     f ( sRefresh && !serv ce dent f er. sLocal) refreshWarmup()
  }

  def refreshWarmup(): Un  = {
    val elapsed = Stopwatch.start()
    val test ds = Seq(
      1,
      2,
      3
    )
    try {
      cl ent d.asCurrent {
        test ds.foreach {  d =>
          val warmupReq = warmupQuery( d)
           nfo(s"Send ng warm-up request to serv ce w h query: $warmupReq")
          warmup.sendRequest(
             thod = t.PushServ ce.Refresh,
            req = Request(t.PushServ ce.Refresh.Args(warmupReq)))(assertWarmupResponse)
        }
      }
    } catch {
      case e: Throwable =>
        error(e.get ssage, e)
    }
     nfo(s"Warm up complete. T   taken: ${elapsed().toStr ng}")
  }

  pr vate def warmupQuery(user d: Long): t.RefreshRequest = {
    t.RefreshRequest(
      user d = user d,
      not f cat onD splayLocat on = Not f cat onD splayLocat on.PushToMob leDev ce,
      context = So (
        t.PushContext(
          sk pF lters = So (true),
          darkWr e = So (true)
        ))
    )
  }

  pr vate def assertWarmupResponse(
    result: Try[Response[t.PushServ ce.Refresh.SuccessType]]
  ): Un  = {
    result match {
      case Return(_) => // ok
      case Throw(except on) =>
        warn("Error perform ng warm-up request.")
        error(except on.get ssage, except on)
    }
  }
}
