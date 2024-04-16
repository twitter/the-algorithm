package com.tw ter.t etyp e
package serv ce
package observer

 mport com.tw ter.servo.except on.thr ftscala.Cl entError
 mport com.tw ter.t etyp e.thr ftscala.GetT etOpt ons
 mport com.tw ter.t etyp e.thr ftscala.GetT etResult
 mport com.tw ter.t etyp e.thr ftscala.GetT etsRequest

pr vate[serv ce] object GetT etsObserver {
  type Type = ObserveExchange[GetT etsRequest, Seq[GetT etResult]]

  def observeExchange(stats: StatsRece ver): Effect[Type] = {
    val resultStateStats = ResultStateStats(stats)

    Effect {
      case (request, response) =>
        response match {
          case Return(xs) =>
            xs.foreach {
              case result  f Observer.successStatusStates(result.t etState) =>
                resultStateStats.success()
              case _ =>
                resultStateStats.fa led()
            }
          case Throw(Cl entError(_)) =>
            resultStateStats.success(request.t et ds.s ze)
          case Throw(_) =>
            resultStateStats.fa led(request.t et ds.s ze)
        }
    }
  }

  def observeResults(stats: StatsRece ver, byCl ent: Boolean): Effect[Seq[GetT etResult]] =
    countStates(stats).also(countT etReadAttr butes(stats, byCl ent))

  def observeRequest(stats: StatsRece ver, byCl ent: Boolean): Effect[GetT etsRequest] = {
    val requestS zeStat = stats.stat("request_s ze")
    val opt onsScope = stats.scope("opt ons")
    val languageScope = opt onsScope.scope("language")
    val  ncludeS ceT etCounter = opt onsScope.counter("s ce_t et")
    val  ncludeQuotedT etCounter = opt onsScope.counter("quoted_t et")
    val  ncludePerspect veCounter = opt onsScope.counter("perspect ve")
    val  ncludeConversat onMutedCounter = opt onsScope.counter("conversat on_muted")
    val  ncludePlacesCounter = opt onsScope.counter("places")
    val  ncludeCardsCounter = opt onsScope.counter("cards")
    val  ncludeRet etCountsCounter = opt onsScope.counter("ret et_counts")
    val  ncludeReplyCountsCounter = opt onsScope.counter("reply_counts")
    val  ncludeFavor eCountsCounter = opt onsScope.counter("favor e_counts")
    val  ncludeQuoteCountsCounter = opt onsScope.counter("quote_counts")
    val bypassV s b l yF lter ngCounter = opt onsScope.counter("bypass_v s b l y_f lter ng")
    val excludeReportedCounter = opt onsScope.counter("exclude_reported")
    val cardsPlatformKeyScope = opt onsScope.scope("cards_platform_key")
    val extens onsArgsCounter = opt onsScope.counter("extens ons_args")
    val doNotCac Counter = opt onsScope.counter("do_not_cac ")
    val add  onalF eldsScope = opt onsScope.scope("add  onal_f elds")
    val safetyLevelScope = opt onsScope.scope("safety_level")
    val  ncludeProf leGeoEnr ch nt = opt onsScope.counter("prof le_geo_enr ch nt")
    val  nclude d aAdd  onal tadata = opt onsScope.counter(" d a_add  onal_ tadata")
    val s mpleQuotedT et = opt onsScope.counter("s mple_quoted_t et")
    val forUser dCounter = opt onsScope.counter("for_user_ d")

    def  ncludesPerspect vals(opt ons: GetT etOpt ons) =
      opt ons. ncludePerspect vals && opt ons.forUser d.nonEmpty

    Effect {
      case GetT etsRequest(t et ds, _, So (opt ons), _) =>
        requestS zeStat.add(t et ds.s ze)
         f (!byCl ent) languageScope.counter(opt ons.languageTag). ncr()
         f (opt ons. ncludeS ceT et)  ncludeS ceT etCounter. ncr()
         f (opt ons. ncludeQuotedT et)  ncludeQuotedT etCounter. ncr()
         f ( ncludesPerspect vals(opt ons))  ncludePerspect veCounter. ncr()
         f (opt ons. ncludeConversat onMuted)  ncludeConversat onMutedCounter. ncr()
         f (opt ons. ncludePlaces)  ncludePlacesCounter. ncr()
         f (opt ons. ncludeCards)  ncludeCardsCounter. ncr()
         f (opt ons. ncludeRet etCount)  ncludeRet etCountsCounter. ncr()
         f (opt ons. ncludeReplyCount)  ncludeReplyCountsCounter. ncr()
         f (opt ons. ncludeFavor eCount)  ncludeFavor eCountsCounter. ncr()
         f (opt ons. ncludeQuoteCount)  ncludeQuoteCountsCounter. ncr()
         f (opt ons.bypassV s b l yF lter ng) bypassV s b l yF lter ngCounter. ncr()
         f (opt ons.excludeReported) excludeReportedCounter. ncr()
         f (opt ons.extens onsArgs.nonEmpty) extens onsArgsCounter. ncr()
         f (opt ons.doNotCac ) doNotCac Counter. ncr()
         f (opt ons. ncludeProf leGeoEnr ch nt)  ncludeProf leGeoEnr ch nt. ncr()
         f (opt ons. nclude d aAdd  onal tadata)  nclude d aAdd  onal tadata. ncr()
         f (opt ons.s mpleQuotedT et) s mpleQuotedT et. ncr()
         f (opt ons.forUser d.nonEmpty) forUser dCounter. ncr()
         f (!byCl ent) {
          opt ons.cardsPlatformKey.foreach { cardsPlatformKey =>
            cardsPlatformKeyScope.counter(cardsPlatformKey). ncr()
          }
        }
        opt ons.add  onalF eld ds.foreach {  d =>
          add  onalF eldsScope.counter( d.toStr ng). ncr()
        }
        opt ons.safetyLevel.foreach { level => safetyLevelScope.counter(level.toStr ng). ncr() }
    }
  }

  /**
   *   count t  number of t  s each t et state  s returned as a
   * general  asure of t   alth of T etyP e. part al and not_found
   * t et states should be close to zero.
   */
  pr vate def countStates(stats: StatsRece ver): Effect[Seq[GetT etResult]] = {
    val state = Observer.observeStatusStates(stats)
    Effect { results => results.foreach { t etResult => state(t etResult.t etState) } }
  }

  pr vate def countT etReadAttr butes(
    stats: StatsRece ver,
    byCl ent: Boolean
  ): Effect[Seq[GetT etResult]] = {
    val t etObserver = Observer.countT etAttr butes(stats, byCl ent)
    Effect { results =>
      results.foreach { t etResult => t etResult.t et.foreach(t etObserver) }
    }
  }

}
