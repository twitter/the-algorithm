package com.tw ter.t etyp e
package serv ce
package observer

 mport com.tw ter.esc rb rd.thr ftscala.T etEnt yAnnotat on
 mport com.tw ter.t etyp e.thr ftscala.BatchComposeMode
 mport com.tw ter.t etyp e.thr ftscala.PostT etRequest
 mport com.tw ter.t etyp e.thr ftscala.PostT etResult
 mport com.tw ter.t etyp e.thr ftscala.T etCreateState
 mport com.tw ter.ut l. mo ze

pr vate[serv ce] object PostT etObserver {
  def observeResults(stats: StatsRece ver, byCl ent: Boolean): Effect[PostT etResult] = {
    val stateScope = stats.scope("state")
    val t etObserver = Observer.countT etAttr butes(stats, byCl ent)

    val stateCounters =
       mo ze { st: T etCreateState => stateScope.counter(Observer.ca lToUnderscore(st.na )) }

    Effect { result =>
      stateCounters(result.state). ncr()
       f (result.state == T etCreateState.Ok) result.t et.foreach(t etObserver)
    }
  }

  pr vate def  sCommun y(req: PostT etRequest): Boolean = {
    val Commun yGroup d = 8L
    val Commun yDoma n d = 31L
    req.add  onalF elds
      .flatMap(_.esc rb rdEnt yAnnotat ons).ex sts { e =>
        e.ent yAnnotat ons.collect {
          case T etEnt yAnnotat on(Commun yGroup d, Commun yDoma n d, _) => true
        }.nonEmpty
      }
  }

  def observerRequest(stats: StatsRece ver): Effect[PostT etRequest] = {
    val opt onsScope = stats.scope("opt ons")
    val narrowcastCounter = opt onsScope.counter("narrowcast")
    val nullcastCounter = opt onsScope.counter("nullcast")
    val  nReplyToStatus dCounter = opt onsScope.counter(" n_reply_to_status_ d")
    val place dCounter = opt onsScope.counter("place_ d")
    val geoCoord natesCounter = opt onsScope.counter("geo_coord nates")
    val place tadataCounter = opt onsScope.counter("place_ tadata")
    val  d aUpload dCounter = opt onsScope.counter(" d a_upload_ d")
    val darkCounter = opt onsScope.counter("dark")
    val t etToNarrowcast ngCounter = opt onsScope.counter("t et_to_narrowcast ng")
    val autoPopulateReply tadataCounter = opt onsScope.counter("auto_populate_reply_ tadata")
    val attach ntUrlCounter = opt onsScope.counter("attach nt_url")
    val excludeReplyUser dsCounter = opt onsScope.counter("exclude_reply_user_ ds")
    val excludeReplyUser dsStat = opt onsScope.stat("exclude_reply_user_ ds")
    val un queness dCounter = opt onsScope.counter("un queness_ d")
    val batchModeScope = opt onsScope.scope("batch_mode")
    val batchModeF rstCounter = batchModeScope.counter("f rst")
    val batchModeSubsequentCounter = batchModeScope.counter("subsequent")
    val commun  esCounter = opt onsScope.counter("commun  es")

    Effect { request =>
       f (request.narrowcast.nonEmpty) narrowcastCounter. ncr()
       f (request.nullcast) nullcastCounter. ncr()
       f (request. nReplyToT et d.nonEmpty)  nReplyToStatus dCounter. ncr()
       f (request.geo.flatMap(_.place d).nonEmpty) place dCounter. ncr()
       f (request.geo.flatMap(_.coord nates).nonEmpty) geoCoord natesCounter. ncr()
       f (request.geo.flatMap(_.place tadata).nonEmpty) place tadataCounter. ncr()
       f (request. d aUpload ds.nonEmpty)  d aUpload dCounter. ncr()
       f (request.dark) darkCounter. ncr()
       f (request.enableT etToNarrowcast ng) t etToNarrowcast ngCounter. ncr()
       f (request.autoPopulateReply tadata) autoPopulateReply tadataCounter. ncr()
       f (request.attach ntUrl.nonEmpty) attach ntUrlCounter. ncr()
       f (request.excludeReplyUser ds.ex sts(_.nonEmpty)) excludeReplyUser dsCounter. ncr()
       f ( sCommun y(request)) commun  esCounter. ncr()
       f (request.un queness d.nonEmpty) un queness dCounter. ncr()
      request.trans entContext.flatMap(_.batchCompose).foreach {
        case BatchComposeMode.BatchF rst => batchModeF rstCounter. ncr()
        case BatchComposeMode.BatchSubsequent => batchModeSubsequentCounter. ncr()
        case _ =>
      }

      excludeReplyUser dsStat.add(request.excludeReplyUser ds.s ze)
    }
  }
}
