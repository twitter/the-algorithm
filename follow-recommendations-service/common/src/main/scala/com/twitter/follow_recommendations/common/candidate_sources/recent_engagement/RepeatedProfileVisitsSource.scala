package com.tw ter.follow_recom ndat ons.common.cand date_s ces.recent_engage nt

 mport com.google. nject. nject
 mport com.google. nject.S ngleton
 mport com.tw ter.dds.jobs.repeated_prof le_v s s.thr ftscala.Prof leV s or nfo
 mport com.tw ter.exper  nts.general_ tr cs.thr ftscala. dType
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.cl ents.real_t  _real_graph.Engage nt
 mport com.tw ter.follow_recom ndat ons.common.cl ents.real_t  _real_graph.RealT  RealGraphCl ent
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.t  l nes.conf gap .HasParams
 mport com.tw ter.t  l nes.conf gap .Params
 mport com.tw ter. rm .model.Algor hm
 mport com.tw ter. nject.Logg ng
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.generated.cl ent.rux.RepeatedProf leV s sAggregateCl entColumn

@S ngleton
class RepeatedProf leV s sS ce @ nject() (
  repeatedProf leV s sAggregateCl entColumn: RepeatedProf leV s sAggregateCl entColumn,
  realT  RealGraphCl ent: RealT  RealGraphCl ent,
  statsRece ver: StatsRece ver)
    extends Cand dateS ce[HasParams w h HasCl entContext, Cand dateUser]
    w h Logg ng {

  val  dent f er: Cand dateS ce dent f er =
    RepeatedProf leV s sS ce. dent f er

  val s ceStatsRece ver = statsRece ver.scope("repeated_prof le_v s s_s ce")
  val offl neFetchErrorCounter = s ceStatsRece ver.counter("offl ne_fetch_error")
  val offl neFetchSuccessCounter = s ceStatsRece ver.counter("offl ne_fetch_success")
  val onl neFetchErrorCounter = s ceStatsRece ver.counter("onl ne_fetch_error")
  val onl neFetchSuccessCounter = s ceStatsRece ver.counter("onl ne_fetch_success")
  val noRepeatedProf leV s sAboveBucket ngThresholdCounter =
    s ceStatsRece ver.counter("no_repeated_prof le_v s s_above_bucket ng_threshold")
  val hasRepeatedProf leV s sAboveBucket ngThresholdCounter =
    s ceStatsRece ver.counter("has_repeated_prof le_v s s_above_bucket ng_threshold")
  val noRepeatedProf leV s sAboveRecom ndat onsThresholdCounter =
    s ceStatsRece ver.counter("no_repeated_prof le_v s s_above_recom ndat ons_threshold")
  val hasRepeatedProf leV s sAboveRecom ndat onsThresholdCounter =
    s ceStatsRece ver.counter("has_repeated_prof le_v s s_above_recom ndat ons_threshold")
  val  ncludeCand datesCounter = s ceStatsRece ver.counter(" nclude_cand dates")
  val no ncludeCand datesCounter = s ceStatsRece ver.counter("no_ nclude_cand dates")

  // Returns v s ed user -> v s  count, v a off dataset.
  def applyW hOffl neDataset(targetUser d: Long): St ch[Map[Long,  nt]] = {
    repeatedProf leV s sAggregateCl entColumn.fetc r
      .fetch(Prof leV s or nfo( d = targetUser d,  dType =  dType.User)).map(_.v)
      .handle {
        case e: Throwable =>
          logger.error("Strato fetch for RepeatedProf leV s sAggregateCl entColumn fa led: " + e)
          offl neFetchErrorCounter. ncr()
          None
      }.onSuccess { result =>
        offl neFetchSuccessCounter. ncr()
      }.map { resultOpt on =>
        resultOpt on
          .flatMap { result =>
            result.prof leV s Set.map { prof leV s Set =>
              prof leV s Set
                .f lter(prof leV s  => prof leV s .totalTargetV s s nLast14Days.getOrElse(0) > 0)
                .f lter(prof leV s  => !prof leV s .doesS ce dFollowTarget d.getOrElse(false))
                .flatMap { prof leV s  =>
                  (prof leV s .target d, prof leV s .totalTargetV s s nLast14Days) match {
                    case (So (target d), So (totalV s s nLast14Days)) =>
                      So (target d -> totalV s s nLast14Days)
                    case _ => None
                  }
                }.toMap[Long,  nt]
            }
          }.getOrElse(Map.empty)
      }
  }

  // Returns v s ed user -> v s  count, v a onl ne dataset.
  def applyW hOnl neData(targetUser d: Long): St ch[Map[Long,  nt]] = {
    val v s edUserToEngage ntsSt ch: St ch[Map[Long, Seq[Engage nt]]] =
      realT  RealGraphCl ent.getRecentProf leV ewEngage nts(targetUser d)
    v s edUserToEngage ntsSt ch
      .onFa lure { f =>
        onl neFetchErrorCounter. ncr()
      }.onSuccess { result =>
        onl neFetchSuccessCounter. ncr()
      }.map { v s edUserToEngage nts =>
        v s edUserToEngage nts
          .mapValues(engage nts => engage nts.s ze)
      }
  }

  def getRepeatedV s edAccounts(params: Params, targetUser d: Long): St ch[Map[Long,  nt]] = {
    var results: St ch[Map[Long,  nt]] = St ch.value(Map.empty)
     f (params.getBoolean(RepeatedProf leV s sParams.UseOnl neDataset)) {
      results = applyW hOnl neData(targetUser d)
    } else {
      results = applyW hOffl neDataset(targetUser d)
    }
    // Only keep users that had non-zero engage nt counts.
    results.map(_.f lter( nput =>  nput._2 > 0))
  }

  def getRecom ndat ons(params: Params, user d: Long): St ch[Seq[Cand dateUser]] = {
    val recom ndat onThreshold = params.get nt(RepeatedProf leV s sParams.Recom ndat onThreshold)
    val bucket ngThreshold = params.get nt(RepeatedProf leV s sParams.Bucket ngThreshold)

    // Get t  l st of repeatedly v s ed prof lts. Only keep accounts w h >= bucket ngThreshold v s s.
    val repeatedV s edAccountsSt ch: St ch[Map[Long,  nt]] =
      getRepeatedV s edAccounts(params, user d).map(_.f lter(kv => kv._2 >= bucket ngThreshold))

    repeatedV s edAccountsSt ch.map { cand dates =>
      // Now c ck  f   should  ncludeCand dates (e.g. w t r user  s  n control bucket or treat nt buckets).
       f (cand dates. sEmpty) {
        // User has not v s ed any accounts above bucket ng threshold.   w ll not bucket user  nto exper  nt. Just
        // don't return no cand dates.
        noRepeatedProf leV s sAboveBucket ngThresholdCounter. ncr()
        Seq.empty
      } else {
        hasRepeatedProf leV s sAboveBucket ngThresholdCounter. ncr()
         f (!params.getBoolean(RepeatedProf leV s sParams. ncludeCand dates)) {
          // User has reac d bucket ng cr er a.   c ck w t r to  nclude cand dates (e.g. c ck ng wh ch bucket
          // t  user  s  n for t  exper  nt).  n t  case t  user  s  n a bucket to not  nclude any cand dates.
          no ncludeCand datesCounter. ncr()
          Seq.empty
        } else {
           ncludeCand datesCounter. ncr()
          //   should  nclude cand dates.  nclude any cand dates above recom ndat on thresholds.
          val outputCand datesSeq = cand dates
            .f lter(kv => kv._2 >= recom ndat onThreshold).map { kv =>
              val user = kv._1
              val v s Count = kv._2
              Cand dateUser(user, So (v s Count.toDouble))
                .w hCand dateS ce(RepeatedProf leV s sS ce. dent f er)
            }.toSeq
           f (outputCand datesSeq. sEmpty) {
            noRepeatedProf leV s sAboveRecom ndat onsThresholdCounter. ncr()
          } else {
            hasRepeatedProf leV s sAboveRecom ndat onsThresholdCounter. ncr()
          }
          outputCand datesSeq
        }
      }
    }
  }

  overr de def apply(request: HasParams w h HasCl entContext): St ch[Seq[Cand dateUser]] = {
    request.getOpt onalUser d
      .map { user d =>
        getRecom ndat ons(request.params, user d)
      }.getOrElse(St ch.N l)
  }
}

object RepeatedProf leV s sS ce {
  val  dent f er = Cand dateS ce dent f er(Algor hm.RepeatedProf leV s s.toStr ng)
}
