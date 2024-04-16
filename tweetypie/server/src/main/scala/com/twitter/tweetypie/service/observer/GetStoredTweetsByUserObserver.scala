package com.tw ter.t etyp e
package serv ce
package observer

 mport com.tw ter.t etyp e.thr ftscala.GetStoredT etsByUserRequest
 mport com.tw ter.t etyp e.thr ftscala.GetStoredT etsByUserResult

pr vate[serv ce] object GetStoredT etsByUserObserver extends StoredT etsObserver {

  type Type = ObserveExchange[GetStoredT etsByUserRequest, GetStoredT etsByUserResult]
  val f rstT etT  stamp: Long = 1142974200L

  def observeRequest(stats: StatsRece ver): Effect[GetStoredT etsByUserRequest] = {
    val opt onsScope = stats.scope("opt ons")
    val bypassV s b l yF lter ngCounter = opt onsScope.counter("bypass_v s b l y_f lter ng")
    val forUser dCounter = opt onsScope.counter("set_for_user_ d")
    val t  RangeStat = opt onsScope.stat("t  _range_seconds")
    val cursorCounter = opt onsScope.counter("cursor")
    val startFromOldestCounter = opt onsScope.counter("start_from_oldest")
    val add  onalF eldsScope = opt onsScope.scope("add  onal_f elds")

    Effect { request =>
       f (request.opt ons. sDef ned) {
        val opt ons = request.opt ons.get

         f (opt ons.bypassV s b l yF lter ng) bypassV s b l yF lter ngCounter. ncr()
         f (opt ons.setForUser d) forUser dCounter. ncr()
         f (opt ons.cursor. sDef ned) {
          cursorCounter. ncr()
        } else {
          //   only add a t   range stat once, w n t re's no cursor  n t  request ( .e. t 
          //  sn't a repeat request for a subsequent batch of results)
          val startT  Seconds: Long =
            opt ons.startT  Msec.map(_ / 1000).getOrElse(f rstT etT  stamp)
          val endT  Seconds: Long = opt ons.endT  Msec.map(_ / 1000).getOrElse(T  .now. nSeconds)
          t  RangeStat.add(endT  Seconds - startT  Seconds)

          //   use t  startFromOldest para ter w n t  cursor  sn't def ned
           f (opt ons.startFromOldest) startFromOldestCounter. ncr()
        }
        opt ons.add  onalF eld ds.foreach {  d =>
          add  onalF eldsScope.counter( d.toStr ng). ncr()
        }
      }
    }
  }

  def observeResult(stats: StatsRece ver): Effect[GetStoredT etsByUserResult] = {
    val resultScope = stats.scope("result")

    Effect { result =>
      observeStoredT ets(result.storedT ets, resultScope)
    }
  }

  def observeExchange(stats: StatsRece ver): Effect[Type] = {
    val resultStateStats = ResultStateStats(stats)

    Effect {
      case (request, response) =>
        response match {
          case Return(_) => resultStateStats.success()
          case Throw(_) => resultStateStats.fa led()
        }
    }
  }
}
