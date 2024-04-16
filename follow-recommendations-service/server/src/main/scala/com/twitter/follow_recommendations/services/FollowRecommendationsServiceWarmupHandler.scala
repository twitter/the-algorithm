package com.tw ter.follow_recom ndat ons.serv ces

 mport com.tw ter.f nagle.thr ft.Cl ent d
 mport com.tw ter.f natra.thr ft.rout ng.Thr ftWarmup
 mport com.tw ter.follow_recom ndat ons.thr ftscala.FollowRecom ndat onsThr ftServ ce.GetRecom ndat ons
 mport com.tw ter.follow_recom ndat ons.thr ftscala.Cl entContext
 mport com.tw ter.follow_recom ndat ons.thr ftscala.DebugParams
 mport com.tw ter.follow_recom ndat ons.thr ftscala.D splayContext
 mport com.tw ter.follow_recom ndat ons.thr ftscala.D splayLocat on
 mport com.tw ter.follow_recom ndat ons.thr ftscala.Prof le
 mport com.tw ter.follow_recom ndat ons.thr ftscala.Recom ndat onRequest
 mport com.tw ter. nject.Logg ng
 mport com.tw ter. nject.ut ls.Handler
 mport com.tw ter.scrooge.Request
 mport com.tw ter.scrooge.Response
 mport com.tw ter.ut l.Return
 mport com.tw ter.ut l.Throw
 mport com.tw ter.ut l.Try
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class FollowRecom ndat onsServ ceWarmupHandler @ nject() (warmup: Thr ftWarmup)
    extends Handler
    w h Logg ng {

  pr vate val cl ent d = Cl ent d("thr ft-warmup-cl ent")

  overr de def handle(): Un  = {
    val test ds = Seq(1L)
    def warmupQuery(user d: Long, d splayLocat on: D splayLocat on): Recom ndat onRequest = {
      val cl entContext = Cl entContext(
        user d = So (user d),
        guest d = None,
        app d = So (258901L),
         pAddress = So ("0.0.0.0"),
        userAgent = So ("FAKE_USER_AGENT_FOR_WARMUPS"),
        countryCode = So ("US"),
        languageCode = So ("en"),
         sTwoff ce = None,
        userRoles = None,
        dev ce d = So ("FAKE_DEV CE_ D_FOR_WARMUPS")
      )
      Recom ndat onRequest(
        cl entContext = cl entContext,
        d splayLocat on = d splayLocat on,
        d splayContext = None,
        maxResults = So (3),
        fetchPromotedContent = So (false),
        debugParams = So (DebugParams(doNotLog = So (true)))
      )
    }

    // Add FRS d splay locat ons  re  f t y should be targeted for warm-up
    // w n FRS  s start ng from a fresh state after a deploy
    val d splayLocat onsToWarmUp: Seq[D splayLocat on] = Seq(
      D splayLocat on.Ho T  l ne,
      D splayLocat on.Ho T  l neReverseChron,
      D splayLocat on.Prof leS debar,
      D splayLocat on.Nux nterests,
      D splayLocat on.NuxPymk
    )

    try {
      cl ent d.asCurrent {
        //  erate over each user  D created for test ng
        test ds foreach {  d =>
          //  erate over each d splay locat on targeted for warm-up
          d splayLocat onsToWarmUp foreach { d splayLocat on =>
            val warmupReq = warmupQuery( d, d splayLocat on)
             nfo(s"Send ng warm-up request to serv ce w h query: $warmupReq")
            warmup.sendRequest(
               thod = GetRecom ndat ons,
              req = Request(GetRecom ndat ons.Args(warmupReq)))(assertWarmupResponse)
            // send t  request one more t   so that   goes through cac  h s
            warmup.sendRequest(
               thod = GetRecom ndat ons,
              req = Request(GetRecom ndat ons.Args(warmupReq)))(assertWarmupResponse)
          }
        }
      }
    } catch {
      case e: Throwable =>
        //   don't want a warmup fa lure to prevent start-up
        error(e.get ssage, e)
    }
     nfo("Warm-up done.")
  }

  /* Pr vate */

  pr vate def assertWarmupResponse(result: Try[Response[GetRecom ndat ons.SuccessType]]): Un  = {
    //   collect and log any except ons from t  result.
    result match {
      case Return(_) => // ok
      case Throw(except on) =>
        warn()
        error(s"Error perform ng warm-up request: ${except on.get ssage}", except on)
    }
  }
}
