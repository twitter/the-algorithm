package com.tw ter.fr gate.pushserv ce.ut l

 mport com.tw ter.f nagle.stats.BroadcastStatsRece ver
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.Target
 mport com.tw ter.fr gate.pushserv ce.thr ftscala.PushResponse
 mport com.tw ter.fr gate.pushserv ce.thr ftscala.PushStatus
 mport com.tw ter.fr gate.thr ftscala.CommonRecom ndat onType

object ResponseStatsTrackUt ls {
  def trackStatsForResponseToRequest(
    crt: CommonRecom ndat onType,
    target: Target,
    response: PushResponse,
    rece vers: Seq[StatsRece ver]
  )(
    or g nalStats: StatsRece ver
  ): Un  = {
    val newRece vers = Seq(
      or g nalStats
        .scope(" s_model_tra n ng_data")
        .scope(target. sModelTra n ngData.toStr ng),
      or g nalStats.scope("scr be_target").scope( b sScr beTargets.crtToScr beTarget(crt))
    )

    val broadcastStats = BroadcastStatsRece ver(rece vers)
    val broadcastStatsW hExpts = BroadcastStatsRece ver(newRece vers ++ rece vers)

     f (response.status == PushStatus.Sent) {
       f (target. sModelTra n ngData) {
        broadcastStats.counter("num_tra n ng_data_recs_sent"). ncr()
      }
    }
    broadcastStatsW hExpts.counter(response.status.toStr ng). ncr()
     f (response.status == PushStatus.F ltered) {
      broadcastStats
        .scope(response.status.toStr ng)
        .counter(response.f lteredBy.getOrElse("None"))
        . ncr()
    }
  }
}
