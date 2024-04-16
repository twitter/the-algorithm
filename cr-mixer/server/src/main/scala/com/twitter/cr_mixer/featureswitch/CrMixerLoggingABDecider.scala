package com.tw ter.cr_m xer
package featuresw ch

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.abdec der.Logg ngABDec der
 mport com.tw ter.abdec der.Rec p ent
 mport com.tw ter.abdec der.Bucket
 mport com.tw ter.fr gate.common.ut l.StatsUt l
 mport com.tw ter.ut l.Local
 mport scala.collect on.concurrent.{Map => ConcurrentMap}

/**
 * Wraps a Logg ngABDec der, so all  mpressed buckets are recorded to a 'LocalContext' on a g ven request.
 *
 * Contexts (https://tw ter.g hub. o/f nagle/gu de/Contexts.html) are F nagle's  chan sm for
 * stor ng state/var ables w hout hav ng to pass t se var ables all around t  request.
 *
 *  n order for t  class to be used t  [[Set mpressedBucketsLocalContextF lter]] must be appl ed
 * at t  beg nn ng of t  request, to  n  al ze a concurrent map used to store  mpressed buckets.
 *
 * W never   get an a/b  mpress on, t  bucket  nformat on  s logged to t  concurrent hashmap.
 */
case class CrM xerLogg ngABDec der(
  logg ngAbDec der: Logg ngABDec der,
  statsRece ver: StatsRece ver)
    extends Logg ngABDec der {

  pr vate val scopedStatsRece ver = statsRece ver.scope("cr_logg ng_ab_dec der")

  overr de def  mpress on(
    exper  ntNa : Str ng,
    rec p ent: Rec p ent
  ): Opt on[Bucket] = {

    StatsUt l.trackNonFutureBlockStats(scopedStatsRece ver.scope("log_ mpress on")) {
      val maybeBuckets = logg ngAbDec der. mpress on(exper  ntNa , rec p ent)
      maybeBuckets.foreach { b =>
        scopedStatsRece ver.counter(" mpress ons"). ncr()
        CrM xer mpressedBuckets.record mpressedBucket(b)
      }
      maybeBuckets
    }
  }

  overr de def track(
    exper  ntNa : Str ng,
    eventNa : Str ng,
    rec p ent: Rec p ent
  ): Un  = {
    logg ngAbDec der.track(exper  ntNa , eventNa , rec p ent)
  }

  overr de def bucket(
    exper  ntNa : Str ng,
    rec p ent: Rec p ent
  ): Opt on[Bucket] = {
    logg ngAbDec der.bucket(exper  ntNa , rec p ent)
  }

  overr de def exper  nts: Seq[Str ng] = logg ngAbDec der.exper  nts

  overr de def exper  nt(exper  ntNa : Str ng) =
    logg ngAbDec der.exper  nt(exper  ntNa )
}

object CrM xer mpressedBuckets {
  pr vate[featuresw ch] val local mpressedBucketsMap = new Local[ConcurrentMap[Bucket, Boolean]]

  /**
   * Gets all  mpressed buckets for t  request.
   **/
  def getAll mpressedBuckets: Opt on[L st[Bucket]] = {
    local mpressedBucketsMap.apply().map(_.map { case (k, _) => k }.toL st)
  }

  pr vate[featuresw ch] def record mpressedBucket(bucket: Bucket) = {
    local mpressedBucketsMap().foreach { m => m += bucket -> true }
  }
}
