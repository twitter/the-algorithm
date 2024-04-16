package com.tw ter.t etyp e.j m ny.t etyp e

 mport com.tw ter.f nagle.stats.Categor z ngExcept onStatsHandler
 mport com.tw ter.f nagle.stats.Stat
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. ncent ves.j m ny.thr ftscala._
 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.servo.ut l.Gate
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.thr ft.ScroogeConv mpl c s._
 mport com.tw ter.strato.cl ent.{Cl ent => StratoCl ent}
 mport com.tw ter.t etyp e.core.T etCreateFa lure
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.Return
 mport com.tw ter.ut l.Throw

case class NudgeBu lderRequest(
  text: Str ng,
   nReplyToT et d: Opt on[NudgeBu lder.T et d],
  conversat on d: Opt on[NudgeBu lder.T et d],
  hasQuotedT et: Boolean,
  nudgeOpt ons: Opt on[CreateT etNudgeOpt ons],
  t et d: Opt on[NudgeBu lder.T et d])

tra  NudgeBu lder extends FutureArrow[NudgeBu lderRequest, Un ] {

  /**
   * C ck w t r t  user should rece ve a nudge  nstead of creat ng
   * t  T et.  f nudgeOpt ons  s None, t n no nudge c ck w ll be
   * perfor d.
   *
   * @return a Future.except on conta n ng a [[T etCreateFa lure]]  f t 
   *   user should be nudged, or Future.Un   f t  user should not be
   *   nudged.
   */
  def apply(
    request: NudgeBu lderRequest
  ): Future[Un ]
}

object NudgeBu lder {
  type Type = FutureArrow[NudgeBu lderRequest, Un ]
  type T et d = Long

  // darkTraff cCreateNudgeOpt ons ensure that   dark traff c sends a request that w ll
  // accurately test t  J m ny backend.  n t  case,   spec fy that   want c cks for all
  // poss ble nudge types
  pr vate[t ] val darkTraff cCreateNudgeOpt ons = So (
    CreateT etNudgeOpt ons(
      requestedNudgeTypes = So (
        Set(
          T etNudgeType.Potent allyTox cT et,
          T etNudgeType.Rev seOrMute,
          T etNudgeType.Rev seOrH deT nBlock,
          T etNudgeType.Rev seOrBlock
        )
      )
    )
  )

  pr vate[t ] def mkJ m nyRequest(
    request: NudgeBu lderRequest,
     sDarkRequest: Boolean = false
  ): CreateT etNudgeRequest = {
    val t etType =
       f (request. nReplyToT et d.nonEmpty) T etType.Reply
      else  f (request.hasQuotedT et) T etType.QuoteT et
      else T etType.Or g nalT et

    CreateT etNudgeRequest(
      t etText = request.text,
      t etType = t etType,
       nReplyToT et d = request. nReplyToT et d,
      conversat on d = request.conversat on d,
      createT etNudgeOpt ons =
         f ( sDarkRequest) darkTraff cCreateNudgeOpt ons else request.nudgeOpt ons,
      t et d = request.t et d
    )
  }

  /**
   * NudgeBu lder  mple nted by call ng t  strato column ` ncent ves/createNudge`.
   *
   * Stats recorded:
   *   - latency_ms: Latency  togram (also  mpl c ly number of
   *      nvocat ons). T   s counted only  n t  case that a nudge
   *     c ck was requested (`nudgeOpt ons`  s non-empty)
   *
   *   - nudge: T  nudge c ck succeeded and a nudge was created.
   *
   *   - no_nudge: T  nudge c ck succeeded, but no nudge was created.
   *
   *   - fa lures: Call ng strato to create a nudge fa led. Broken out
   *     by except on.
   */

  def apply(
    nudgeArrow: FutureArrow[CreateT etNudgeRequest, CreateT etNudgeResponse],
    enableDarkTraff c: Gate[Un ],
    stats: StatsRece ver
  ): NudgeBu lder = {
    new NudgeBu lder {
      pr vate[t ] val nudgeLatencyStat = stats.stat("latency_ms")
      pr vate[t ] val nudgeCounter = stats.counter("nudge")
      pr vate[t ] val noNudgeCounter = stats.counter("no_nudge")
      pr vate[t ] val darkRequestCounter = stats.counter("dark_request")
      pr vate[t ] val nudgeExcept onHandler = new Categor z ngExcept onStatsHandler

      overr de def apply(
        request: NudgeBu lderRequest
      ): Future[Un ] =
        request.nudgeOpt ons match {
          case None =>
             f (enableDarkTraff c()) {
              darkRequestCounter. ncr()
              Stat
                .t  Future(nudgeLatencyStat) {
                  nudgeArrow(mkJ m nyRequest(request,  sDarkRequest = true))
                }
                .transform { _ =>
                  //  gnore t  response s nce    s a dark request
                  Future.Done
                }
            } else {
              Future.Done
            }

          case So (_) =>
            Stat
              .t  Future(nudgeLatencyStat) {
                nudgeArrow(mkJ m nyRequest(request))
              }
              .transform {
                case Throw(e) =>
                  nudgeExcept onHandler.record(stats, e)
                  //  f   fa led to  nvoke t  nudge column, t n
                  // just cont nue on w h t  T et creat on.
                  Future.Done

                case Return(CreateT etNudgeResponse(So (nudge))) =>
                  nudgeCounter. ncr()
                  Future.except on(T etCreateFa lure.Nudged(nudge = nudge))

                case Return(CreateT etNudgeResponse(None)) =>
                  noNudgeCounter. ncr()
                  Future.Done
              }
        }
    }
  }

  def apply(
    strato: StratoCl ent,
    enableDarkTraff c: Gate[Un ],
    stats: StatsRece ver
  ): NudgeBu lder = {
    val executer =
      strato.executer[CreateT etNudgeRequest, CreateT etNudgeResponse](
        " ncent ves/createT etNudge")
    val nudgeArrow: FutureArrow[CreateT etNudgeRequest, CreateT etNudgeResponse] = { req =>
      St ch.run(executer.execute(req))
    }
    apply(nudgeArrow, enableDarkTraff c, stats)
  }
}
