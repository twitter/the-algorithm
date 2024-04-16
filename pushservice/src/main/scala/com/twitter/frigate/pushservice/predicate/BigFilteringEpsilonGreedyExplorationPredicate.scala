package com.tw ter.fr gate.pushserv ce.pred cate

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f nagle.trac ng.Trace
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.Target
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
 mport com.tw ter.hash ng.KeyHas r
 mport com.tw ter. rm .pred cate.Na dPred cate
 mport com.tw ter. rm .pred cate.Pred cate
 mport com.tw ter.ut l.Future

/*
 * A pred cate for eps lon-greedy explorat on;
 *   def ned   as a cand date level pred cate to avo d chang ng t  pred cate and scr b ng p pel ne,
 * but    s actually a post-rank ng target level pred cate:
 *   f a target user  S ENABLED for \eps lon-greedy explorat on,
 *  t n w h probab l y eps lon, t  user (and thus all cand dates) w ll be blocked
 */
object B gF lter ngEps lonGreedyExplorat onPred cate {

  val na  = "B gF lter ngEps lonGreedyExplorat onPred cate"

  pr vate def shouldF lterBasedOnEps lonGreedyExplorat on(
    target: Target
  ): Boolean = {
    val seed = KeyHas r.FNV1A_64.hashKey(s"${target.target d}".getBytes("UTF8"))
    val hashKey = KeyHas r.FNV1A_64
      .hashKey(
        s"${Trace. d.trace d.toStr ng}:${seed.toStr ng}".getBytes("UTF8")
      )

    math.abs(hashKey).toDouble / Long.MaxValue <
      target.params(PushFeatureSw chParams.MrRequestScr b ngEpsGreedyExplorat onRat o)
  }

  def apply()( mpl c  statsRece ver: StatsRece ver): Na dPred cate[PushCand date] = {
    val stats = statsRece ver.scope(s"pred cate_$na ")

    val enabledForEps lonGreedyCounter = stats.counter("enabled_for_eps_greedy")

    new Pred cate[PushCand date] {
      def apply(cand dates: Seq[PushCand date]): Future[Seq[Boolean]] = {
        val results = cand dates.map { cand date =>
           f (!cand date.target.sk pF lters && cand date.target.params(
              PushFeatureSw chParams.EnableMrRequestScr b ngForEpsGreedyExplorat on)) {
            enabledForEps lonGreedyCounter. ncr()
            !shouldF lterBasedOnEps lonGreedyExplorat on(cand date.target)
          } else {
            true
          }
        }
        Future.value(results)
      }
    }.w hStats(stats)
      .w hNa (na )
  }
}
