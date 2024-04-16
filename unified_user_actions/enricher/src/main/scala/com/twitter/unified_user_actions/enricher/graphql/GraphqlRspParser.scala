package com.tw ter.un f ed_user_act ons.enr c r.graphql

 mport com.google.common.ut l.concurrent.RateL m er
 mport com.tw ter.dynmap.DynMap
 mport com.tw ter.dynmap.json.DynMapJson
 mport com.tw ter.f nagle.stats.Counter
 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.ut l.logg ng.Logg ng
 mport com.tw ter.ut l.Return
 mport com.tw ter.ut l.Throw
 mport com.tw ter.ut l.Try

/**
 * @param dm T  DynMap parsed from t  returned Json str ng
 */
case class GraphqlRspErrors(dm: DynMap) extends Except on {
  overr de def toStr ng: Str ng = dm.toStr ng()
}

object GraphqlRspParser extends Logg ng {
  pr vate val rateL m er = RateL m er.create(1.0) // at most 1 log  ssage per second
  pr vate def rateL m edLogError(e: Throwable): Un  =
     f (rateL m er.tryAcqu re()) {
      error(e.get ssage, e)
    }

  /**
   * GraphQL's response  s a Json str ng.
   * T  funct on f rst parses t  raw response as a Json str ng, t n   c cks  f t  returned
   * object has t  "data" f eld wh ch  ans t  response  s expected. T  response could also
   * return a val d Json str ng but w h errors  ns de   as a l st of "errors".
   */
  def toDynMap(
    rsp: Str ng,
     nval dRspCounter: Counter = NullStatsRece ver.NullCounter,
    fa ledReqCounter: Counter = NullStatsRece ver.NullCounter
  ): Try[DynMap] = {
    val rawRsp: Try[DynMap] = DynMapJson.fromJsonStr ng(rsp)
    rawRsp match {
      case Return(r) =>
         f (r.getMapOpt("data"). sDef ned) Return(r)
        else {
           nval dRspCounter. ncr()
          rateL m edLogError(GraphqlRspErrors(r))
          Throw(GraphqlRspErrors(r))
        }
      case Throw(e) =>
        rateL m edLogError(e)
        fa ledReqCounter. ncr()
        Throw(e)
    }
  }

  /**
   * S m lar to `toDynMap` above, but returns an Opt on
   */
  def toDynMapOpt(
    rsp: Str ng,
     nval dRspCounter: Counter = NullStatsRece ver.NullCounter,
    fa ledReqCounter: Counter = NullStatsRece ver.NullCounter
  ): Opt on[DynMap] =
    toDynMap(
      rsp = rsp,
       nval dRspCounter =  nval dRspCounter,
      fa ledReqCounter = fa ledReqCounter).toOpt on
}
