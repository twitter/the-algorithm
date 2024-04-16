package com.tw ter.follow_recom ndat ons.logg ng

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.constants.Gu ceNa dConstants
 mport com.tw ter.follow_recom ndat ons.common.models.Has sSoftUser
 mport com.tw ter.follow_recom ndat ons.conf gap .params.GlobalParams
 mport com.tw ter.follow_recom ndat ons.logg ng.thr ftscala.Recom ndat onLog
 mport com.tw ter.follow_recom ndat ons.models.DebugParams
 mport com.tw ter.follow_recom ndat ons.models.Recom ndat onFlowData
 mport com.tw ter.follow_recom ndat ons.models.Recom ndat onRequest
 mport com.tw ter.follow_recom ndat ons.models.Recom ndat onResponse
 mport com.tw ter.follow_recom ndat ons.models.Scor ngUserRequest
 mport com.tw ter.follow_recom ndat ons.models.Scor ngUserResponse
 mport com.tw ter. nject.annotat ons.Flag
 mport com.tw ter.logg ng.LoggerFactory
 mport com.tw ter.product_m xer.core.model.marshall ng.request.Cl entContext
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport com.tw ter.scr bel b.marshallers.Cl entDataProv der
 mport com.tw ter.scr bel b.marshallers.ExternalRefererDataProv der
 mport com.tw ter.scr bel b.marshallers.Scr beSer al zat on
 mport com.tw ter.t  l nes.conf gap .HasParams
 mport com.tw ter.ut l.T  
 mport javax. nject. nject
 mport javax. nject.Na d
 mport javax. nject.S ngleton

/**
 * T   s t  standard logg ng class   use to log data  nto:
 * 1) logs.follow_recom ndat ons_logs
 *
 * T  logger logs data for 2 endpo nts: getRecom ndat ons, scoreUserCand dates
 * All data scr bed v a t  logger have to be converted  nto t  sa  thr ft type: Recom ndat onLog
 *
 * 2) logs.frs_recom ndat on_flow_logs
 *
 * T  logger logs recom ndat on flow data for getRecom ndat ons requests
 * All data scr bed v a t  logger have to be converted  nto t  sa  thr ft type: FrsRecom ndat onFlowLog
 */
@S ngleton
class FrsLogger @ nject() (
  @Na d(Gu ceNa dConstants.REQUEST_LOGGER) loggerFactory: LoggerFactory,
  @Na d(Gu ceNa dConstants.FLOW_LOGGER) flowLoggerFactory: LoggerFactory,
  stats: StatsRece ver,
  @Flag("log_results") serv ceShouldLogResults: Boolean)
    extends Scr beSer al zat on {
  pr vate val logger = loggerFactory.apply()
  pr vate val flowLogger = flowLoggerFactory.apply()
  pr vate val logRecom ndat onCounter = stats.counter("scr be_recom ndat on")
  pr vate val logScor ngCounter = stats.counter("scr be_scor ng")
  pr vate val logRecom ndat onFlowCounter = stats.counter("scr be_recom ndat on_flow")

  def logRecom ndat onResult(
    request: Recom ndat onRequest,
    response: Recom ndat onResponse
  ): Un  = {
     f (!request. sSoftUser) {
      val log =
        Recom ndat onLog(request.toOffl neThr ft, response.toOffl neThr ft, T  .now. nM ll s)
      logRecom ndat onCounter. ncr()
      logger. nfo(
        ser al zeThr ft(
          log,
          FrsLogger.LogCategory,
          FrsLogger.mkProv der(request.cl entContext)
        ))
    }
  }

  def logScor ngResult(request: Scor ngUserRequest, response: Scor ngUserResponse): Un  = {
     f (!request. sSoftUser) {
      val log =
        Recom ndat onLog(
          request.toRecom ndat onRequest.toOffl neThr ft,
          response.toRecom ndat onResponse.toOffl neThr ft,
          T  .now. nM ll s)
      logScor ngCounter. ncr()
      logger. nfo(
        ser al zeThr ft(
          log,
          FrsLogger.LogCategory,
          FrsLogger.mkProv der(request.toRecom ndat onRequest.cl entContext)
        ))
    }
  }

  def logRecom ndat onFlowData[Target <: HasCl entContext w h Has sSoftUser w h HasParams](
    request: Target,
    flowData: Recom ndat onFlowData[Target]
  ): Un  = {
     f (!request. sSoftUser && request.params(GlobalParams.EnableRecom ndat onFlowLogs)) {
      val log = flowData.toRecom ndat onFlowLogOffl neThr ft
      logRecom ndat onFlowCounter. ncr()
      flowLogger. nfo(
        ser al zeThr ft(
          log,
          FrsLogger.FlowLogCategory,
          FrsLogger.mkProv der(request.cl entContext)
        ))
    }
  }

  //   prefer t  sett ngs g ven  n t  user request, and  f none prov ded   default to t 
  // aurora serv ce conf gurat on.
  def shouldLog(debugParamsOpt: Opt on[DebugParams]): Boolean =
    debugParamsOpt match {
      case So (debugParams) =>
        debugParams.debugOpt ons match {
          case So (debugOpt ons) =>
            !debugOpt ons.doNotLog
          case None =>
            serv ceShouldLogResults
        }
      case None =>
        serv ceShouldLogResults
    }

}

object FrsLogger {
  val LogCategory = "follow_recom ndat ons_logs"
  val FlowLogCategory = "frs_recom ndat on_flow_logs"

  def mkProv der(cl entContext: Cl entContext) = new Cl entDataProv der {

    /** T   d of t  current user. W n t  user  s logged out, t   thod should return None. */
    overr de val user d: Opt on[Long] = cl entContext.user d

    /** T   d of t  guest, wh ch  s present  n logged- n or loged-out states */
    overr de val guest d: Opt on[Long] = cl entContext.guest d

    /** T  personal zat on  d (p d) of t  user, used to personal ze Tw ter serv ces */
    overr de val personal zat on d: Opt on[Str ng] = None

    /** T   d of t   nd v dual dev ce t  user  s currently us ng. T   d w ll be un que for d fferent users' dev ces. */
    overr de val dev ce d: Opt on[Str ng] = cl entContext.dev ce d

    /** T  OAuth appl cat on  d of t  appl cat on t  user  s currently us ng */
    overr de val cl entAppl cat on d: Opt on[Long] = cl entContext.app d

    /** T  OAuth parent appl cat on  d of t  appl cat on t  user  s currently us ng */
    overr de val parentAppl cat on d: Opt on[Long] = None

    /** T  two-letter, upper-case country code used to des gnate t  country from wh ch t  scr be event occurred */
    overr de val countryCode: Opt on[Str ng] = cl entContext.countryCode

    /** T  two-letter, lo r-case language code used to des gnate t  probably language spoken by t  scr be event  n  ator */
    overr de val languageCode: Opt on[Str ng] = cl entContext.languageCode

    /** T  user-agent  ader used to  dent fy t  cl ent browser or dev ce that t  user  s currently act ve on */
    overr de val userAgent: Opt on[Str ng] = cl entContext.userAgent

    /** W t r t  user  s access ng Tw ter v a a secured connect on */
    overr de val  sSsl: Opt on[Boolean] = So (true)

    /** T  referr ng URL to t  current page for  b-based cl ents,  f appl cable */
    overr de val referer: Opt on[Str ng] = None

    /**
     * T  external s e, partner, or ema l that lead to t  current Tw ter appl cat on. Returned value cons sts of a
     * tuple  nclud ng t  encrypted referral data and t  type of referral
     */
    overr de val externalReferer: Opt on[ExternalRefererDataProv der] = None
  }
}
