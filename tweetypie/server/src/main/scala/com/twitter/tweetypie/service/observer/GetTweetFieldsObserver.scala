package com.tw ter.t etyp e
package serv ce
package observer

 mport com.tw ter.servo.except on.thr ftscala.Cl entError
 mport com.tw ter.t etyp e.thr ftscala._

pr vate[serv ce] object GetT etF eldsObserver {
  type Type = ObserveExchange[GetT etF eldsRequest, Seq[GetT etF eldsResult]]

  def observeExchange(statsRece ver: StatsRece ver): Effect[Type] = {
    val resultStateStats = ResultStateStats(statsRece ver)

    val stats = statsRece ver.scope("results")
    val t etResultFa led = stats.counter("t et_result_fa led")
    val quoteResultFa led = stats.counter("quote_result_fa led")
    val overCapac y = stats.counter("over_capac y")

    def observeFa ledResult(r: GetT etF eldsResult): Un  = {
      r.t etResult match {
        case T etF eldsResultState.Fa led(fa led) =>
          t etResultFa led. ncr()

           f (fa led.overCapac y) overCapac y. ncr()
        case _ =>
      }

       f (r.quotedT etResult.ex sts(_. s nstanceOf[T etF eldsResultState.Fa led]))
        quoteResultFa led. ncr()
    }

    Effect {
      case (request, response) =>
        response match {
          case Return(xs) =>
            xs foreach {
              case x  f  sFa ledResult(x) =>
                observeFa ledResult(x)
                resultStateStats.fa led()
              case _ =>
                resultStateStats.success()
            }
          case Throw(Cl entError(_)) =>
            resultStateStats.success(request.t et ds.s ze)
          case Throw(_) =>
            resultStateStats.fa led(request.t et ds.s ze)
        }
    }
  }

  def observeRequest(stats: StatsRece ver, byCl ent: Boolean): Effect[GetT etF eldsRequest] = {
    val requestS zeStat = stats.stat("request_s ze")
    val opt onsScope = stats.scope("opt ons")
    val t etF eldsScope = opt onsScope.scope("t et_f eld")
    val countsF eldsScope = opt onsScope.scope("counts_f eld")
    val  d aF eldsScope = opt onsScope.scope(" d a_f eld")
    val  ncludeRet etedT etCounter = opt onsScope.counter(" nclude_ret eted_t et")
    val  ncludeQuotedT etCounter = opt onsScope.counter(" nclude_quoted_t et")
    val forUser dCounter = opt onsScope.counter("for_user_ d")
    val cardsPlatformKeyCounter = opt onsScope.counter("cards_platform_key")
    val cardsPlatformKeyScope = opt onsScope.scope("cards_platform_key")
    val extens onsArgsCounter = opt onsScope.counter("extens ons_args")
    val doNotCac Counter = opt onsScope.counter("do_not_cac ")
    val s mpleQuotedT etCounter = opt onsScope.counter("s mple_quoted_t et")
    val v s b l yPol cyScope = opt onsScope.scope("v s b l y_pol cy")
    val userV s bleCounter = v s b l yPol cyScope.counter("user_v s ble")
    val noF lter ngCounter = v s b l yPol cyScope.counter("no_f lter ng")
    val noSafetyLevelCounter = opt onsScope.counter("no_safety_level")
    val safetyLevelCounter = opt onsScope.counter("safety_level")
    val safetyLevelScope = opt onsScope.scope("safety_level")

    Effect {
      case GetT etF eldsRequest(t et ds, opt ons) =>
        requestS zeStat.add(t et ds.s ze)
        opt ons.t et ncludes.foreach {
          case T et nclude.T etF eld d( d) => t etF eldsScope.counter( d.toStr ng). ncr()
          case T et nclude.CountsF eld d( d) => countsF eldsScope.counter( d.toStr ng). ncr()
          case T et nclude. d aEnt yF eld d( d) =>  d aF eldsScope.counter( d.toStr ng). ncr()
          case _ =>
        }
         f (opt ons. ncludeRet etedT et)  ncludeRet etedT etCounter. ncr()
         f (opt ons. ncludeQuotedT et)  ncludeQuotedT etCounter. ncr()
         f (opt ons.forUser d.nonEmpty) forUser dCounter. ncr()
         f (opt ons.cardsPlatformKey.nonEmpty) cardsPlatformKeyCounter. ncr()
         f (!byCl ent) {
          opt ons.cardsPlatformKey.foreach { cardsPlatformKey =>
            cardsPlatformKeyScope.counter(cardsPlatformKey). ncr()
          }
        }
         f (opt ons.extens onsArgs.nonEmpty) extens onsArgsCounter. ncr()
         f (opt ons.safetyLevel.nonEmpty) {
          safetyLevelCounter. ncr()
        } else {
          noSafetyLevelCounter. ncr()
        }
        opt ons.v s b l yPol cy match {
          case T etV s b l yPol cy.UserV s ble => userV s bleCounter. ncr()
          case T etV s b l yPol cy.NoF lter ng => noF lter ngCounter. ncr()
          case _ =>
        }
        opt ons.safetyLevel.foreach { level => safetyLevelScope.counter(level.toStr ng). ncr() }
         f (opt ons.doNotCac ) doNotCac Counter. ncr()
         f (opt ons.s mpleQuotedT et) s mpleQuotedT etCounter. ncr()
    }
  }

  def observeResults(stats: StatsRece ver): Effect[Seq[GetT etF eldsResult]] = {
    val resultsCounter = stats.counter("results")
    val resultsScope = stats.scope("results")
    val observeState = GetT etF eldsObserver.observeResultState(resultsScope)

    Effect { results =>
      resultsCounter. ncr(results.s ze)
      results.foreach { r =>
        observeState(r.t etResult)
        r.quotedT etResult.foreach { qtResult =>
          resultsCounter. ncr()
          observeState(qtResult)
        }
      }
    }
  }

  /**
   * G ven a GetT etF eldsResult result, do   observe t  result as a fa lure or not.
   */
  pr vate def  sFa ledResult(result: GetT etF eldsResult): Boolean = {
    result.t etResult. s nstanceOf[T etF eldsResultState.Fa led] ||
    result.quotedT etResult.ex sts(_. s nstanceOf[T etF eldsResultState.Fa led])
  }

  pr vate def observeResultState(stats: StatsRece ver): Effect[T etF eldsResultState] = {
    val foundCounter = stats.counter("found")
    val notFoundCounter = stats.counter("not_found")
    val fa ledCounter = stats.counter("fa led")
    val f lteredCounter = stats.counter("f ltered")
    val f lteredReasonScope = stats.scope("f ltered_reason")
    val ot rCounter = stats.counter("ot r")
    val observeT et = Observer
      .countT etAttr butes(stats.scope("found"), byCl ent = false)

    Effect {
      case T etF eldsResultState.Found(found) =>
        foundCounter. ncr()
        observeT et(found.t et)
        found.ret etedT et.foreach(observeT et)

      case T etF eldsResultState.NotFound(_) => notFoundCounter. ncr()
      case T etF eldsResultState.Fa led(_) => fa ledCounter. ncr()
      case T etF eldsResultState.F ltered(f) =>
        f lteredCounter. ncr()
        // S nce reasons have para ters, eg. AuthorBlockV e r(true) and   don't
        // need t  "(true)" part,   do .getClass.getS mpleNa  to get r d of that
        f lteredReasonScope.counter(f.reason.getClass.getS mpleNa ). ncr()

      case _ => ot rCounter. ncr()
    }
  }

}
