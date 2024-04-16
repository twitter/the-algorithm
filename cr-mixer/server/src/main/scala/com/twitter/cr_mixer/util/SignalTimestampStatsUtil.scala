package com.tw ter.cr_m xer.ut l

 mport com.tw ter.cr_m xer.model.Cand dateGenerat on nfo
 mport com.tw ter.cr_m xer.model.RankedCand date
 mport com.tw ter.cr_m xer.model.S ce nfo
 mport com.tw ter.cr_m xer.thr ftscala.S ceType
 mport com.tw ter.cr_m xer.thr ftscala.T etRecom ndat on
 mport javax. nject. nject
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport javax. nject.S ngleton
 mport com.tw ter.relevance_platform.common.stats.BucketT  stampStats

@S ngleton
class S gnalT  stampStatsUt l @ nject() (statsRece ver: StatsRece ver) {
   mport S gnalT  stampStatsUt l._

  pr vate val s gnalDelayAgePerDayStats =
    new BucketT  stampStats[T etRecom ndat on](
      BucketT  stampStats.M ll secondsPerDay,
      _.latestS ceS gnalT  stamp nM ll s.getOrElse(0),
      So (S gnalT  stampMaxDays))(
      statsRece ver.scope("s gnal_t  stamp_per_day")
    ) // only stats past 90 days
  pr vate val s gnalDelayAgePerH Stats =
    new BucketT  stampStats[T etRecom ndat on](
      BucketT  stampStats.M ll secondsPerH ,
      _.latestS ceS gnalT  stamp nM ll s.getOrElse(0),
      So (S gnalT  stampMaxH s))(
      statsRece ver.scope("s gnal_t  stamp_per_h ")
    ) // only stats past 24 h s
  pr vate val s gnalDelayAgePerM nStats =
    new BucketT  stampStats[T etRecom ndat on](
      BucketT  stampStats.M ll secondsPerM nute,
      _.latestS ceS gnalT  stamp nM ll s.getOrElse(0),
      So (S gnalT  stampMaxM ns))(
      statsRece ver.scope("s gnal_t  stamp_per_m n")
    ) // only stats past 60 m nutes

  def statsS gnalT  stamp(
    t ets: Seq[T etRecom ndat on],
  ): Seq[T etRecom ndat on] = {
    s gnalDelayAgePerM nStats.count(t ets)
    s gnalDelayAgePerH Stats.count(t ets)
    s gnalDelayAgePerDayStats.count(t ets)
  }
}

object S gnalT  stampStatsUt l {
  val S gnalT  stampMaxM ns = 60 // stats at most 60 m ns
  val S gnalT  stampMaxH s = 24 // stats at most 24 h s
  val S gnalT  stampMaxDays = 90 // stats at most 90 days

  def bu ldLatestS ceS gnalT  stamp(cand date: RankedCand date): Opt on[Long] = {
    val t  stampSeq = cand date.potent alReasons
      .collect {
        case Cand dateGenerat on nfo(So (S ce nfo(s ceType, _, So (s ceEventT  ))), _, _)
             f s ceType == S ceType.T etFavor e =>
          s ceEventT  . nM ll seconds
      }
     f (t  stampSeq.nonEmpty) {
      So (t  stampSeq.max(Order ng.Long))
    } else {
      None
    }
  }
}
