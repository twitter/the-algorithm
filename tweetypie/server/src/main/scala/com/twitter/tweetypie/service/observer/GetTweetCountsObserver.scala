package com.tw ter.t etyp e
package serv ce
package observer

 mport com.tw ter.servo.except on.thr ftscala.Cl entError
 mport com.tw ter.snowflake. d.Snowflake d
 mport com.tw ter.t etyp e.thr ftscala.GetT etCountsRequest
 mport com.tw ter.t etyp e.thr ftscala.GetT etCountsResult

pr vate[serv ce] object GetT etCountsObserver {
  type Type = ObserveExchange[GetT etCountsRequest, Seq[GetT etCountsResult]]

  def observeExchange(stats: StatsRece ver): Effect[Type] = {
    val resultStateStats = ResultStateStats(stats)

    Effect {
      case (request, response) =>
        response match {
          case Return(_) | Throw(Cl entError(_)) =>
            resultStateStats.success(request.t et ds.s ze)
          case Throw(_) =>
            resultStateStats.fa led(request.t et ds.s ze)
        }
    }
  }

  def observeResults(stats: StatsRece ver): Effect[Seq[GetT etCountsResult]] = {
    val ret etCounter = stats.counter("ret ets")
    val replyCounter = stats.counter("repl es")
    val favor eCounter = stats.counter("favor es")

    Effect { counts =>
      counts.foreach { c =>
         f (c.ret etCount. sDef ned) ret etCounter. ncr()
         f (c.replyCount. sDef ned) replyCounter. ncr()
         f (c.favor eCount. sDef ned) favor eCounter. ncr()
      }
    }
  }

  def observeRequest(stats: StatsRece ver): Effect[GetT etCountsRequest] = {
    val requestS zesStat = stats.stat("request_s ze")
    val opt onsScope = stats.scope("opt ons")
    val  ncludeRet etCounter = opt onsScope.counter("ret et_counts")
    val  ncludeReplyCounter = opt onsScope.counter("reply_counts")
    val  ncludeFavor eCounter = opt onsScope.counter("favor e_counts")
    val t etAgeStat = stats.stat("t et_age_seconds")

    Effect { request =>
      val s ze = request.t et ds.s ze
      requestS zesStat.add(s ze)

      //  asure T et.get_t et_counts t et age of requested T ets.
      // T et counts are stored  n cac , fall ng back to TFlock on cac  m sses.
      // Track cl ent T et d age to understand how that affects cl ents response latenc es.
      for {
         d <- request.t et ds
        t  stamp <- Snowflake d.t  From dOpt( d)
        age = T  .now.s nce(t  stamp)
      } t etAgeStat.add(age. nSeconds)

       f (request. ncludeRet etCount)  ncludeRet etCounter. ncr(s ze)
       f (request. ncludeReplyCount)  ncludeReplyCounter. ncr(s ze)
       f (request. ncludeFavor eCount)  ncludeFavor eCounter. ncr(s ze)
    }
  }
}
