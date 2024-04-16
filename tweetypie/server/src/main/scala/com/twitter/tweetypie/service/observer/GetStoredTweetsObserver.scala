package com.tw ter.t etyp e
package serv ce
package observer

 mport com.tw ter.t etyp e.thr ftscala.GetStoredT etsRequest
 mport com.tw ter.t etyp e.thr ftscala.GetStoredT etsResult

pr vate[serv ce] object GetStoredT etsObserver extends StoredT etsObserver {
  type Type = ObserveExchange[GetStoredT etsRequest, Seq[GetStoredT etsResult]]

  def observeRequest(stats: StatsRece ver): Effect[GetStoredT etsRequest] = {
    val requestS zeStat = stats.stat("request_s ze")

    val opt onsScope = stats.scope("opt ons")
    val bypassV s b l yF lter ngCounter = opt onsScope.counter("bypass_v s b l y_f lter ng")
    val forUser dCounter = opt onsScope.counter("for_user_ d")
    val add  onalF eldsScope = opt onsScope.scope("add  onal_f elds")

    Effect { request =>
      requestS zeStat.add(request.t et ds.s ze)

       f (request.opt ons. sDef ned) {
        val opt ons = request.opt ons.get
         f (opt ons.bypassV s b l yF lter ng) bypassV s b l yF lter ngCounter. ncr()
         f (opt ons.forUser d. sDef ned) forUser dCounter. ncr()
        opt ons.add  onalF eld ds.foreach {  d =>
          add  onalF eldsScope.counter( d.toStr ng). ncr()
        }
      }
    }
  }

  def observeResult(stats: StatsRece ver): Effect[Seq[GetStoredT etsResult]] = {
    val resultScope = stats.scope("result")

    Effect { result =>
      observeStoredT ets(result.map(_.storedT et), resultScope)
    }
  }

  def observeExchange(stats: StatsRece ver): Effect[Type] = {
    val resultStateStats = ResultStateStats(stats)

    Effect {
      case (request, response) =>
        response match {
          case Return(_) => resultStateStats.success(request.t et ds.s ze)
          case Throw(_) => resultStateStats.fa led(request.t et ds.s ze)
        }
    }
  }
}
