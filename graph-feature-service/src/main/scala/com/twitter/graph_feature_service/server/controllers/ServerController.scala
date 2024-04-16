package com.tw ter.graph_feature_serv ce.server.controllers

 mport com.tw ter.d scovery.common.stats.D scoveryStatsF lter
 mport com.tw ter.f nagle.Serv ce
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f natra.thr ft.Controller
 mport com.tw ter.graph_feature_serv ce.server.handlers.ServerGet ntersect onHandler.Get ntersect onRequest
 mport com.tw ter.graph_feature_serv ce.server.handlers.ServerGet ntersect onHandler
 mport com.tw ter.graph_feature_serv ce.thr ftscala
 mport com.tw ter.graph_feature_serv ce.thr ftscala.Server.Get ntersect on
 mport com.tw ter.graph_feature_serv ce.thr ftscala.Server.GetPreset ntersect on
 mport com.tw ter.graph_feature_serv ce.thr ftscala._
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class ServerController @ nject() (
  serverGet ntersect onHandler: ServerGet ntersect onHandler
)(
   mpl c  statsRece ver: StatsRece ver)
    extends Controller(thr ftscala.Server) {

  pr vate val get ntersect onServ ce: Serv ce[Get ntersect onRequest, Gfs ntersect onResponse] =
    new D scoveryStatsF lter(statsRece ver.scope("srv").scope("get_ ntersect on"))
      .andT n(Serv ce.mk(serverGet ntersect onHandler))

  val get ntersect on: Serv ce[Get ntersect on.Args, Gfs ntersect onResponse] = { args =>
    // TODO: D sable updateCac  after HTL sw ch to use Preset ntersect on endpo nt.
    get ntersect onServ ce(
      Get ntersect onRequest.fromGfs ntersect onRequest(args.request, cac able = true))
  }
  handle(Get ntersect on) { get ntersect on }

  def getPreset ntersect on: Serv ce[
    GetPreset ntersect on.Args,
    Gfs ntersect onResponse
  ] = { args =>
    // TODO: Refactor after HTL sw ch to Preset ntersect on
    val cac able = args.request.presetFeatureTypes == PresetFeatureTypes.HtlTwoHop
    get ntersect onServ ce(
      Get ntersect onRequest.fromGfsPreset ntersect onRequest(args.request, cac able))
  }

  handle(GetPreset ntersect on) { getPreset ntersect on }

}
