package com.tw ter.t  l neranker.mon or ng

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.search.earlyb rd.thr ftscala.Thr ftSearchResult
 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.t  l neranker.core.Cand dateEnvelope
 mport com.tw ter.t  l neranker.model.RecapQuery.DependencyProv der
 mport com.tw ter.ut l.Future

/**
 * Captures t et counts pre and post transformat on for a l st of users
 */
class UsersSearchResultMon or ngTransform(
  na : Str ng,
  underly ngTransfor r: FutureArrow[Cand dateEnvelope, Cand dateEnvelope],
  statsRece ver: StatsRece ver,
  debugAuthorL stDependencyProv der: DependencyProv der[Seq[Long]])
    extends FutureArrow[Cand dateEnvelope, Cand dateEnvelope] {

  pr vate val scopedStatsRece ver = statsRece ver.scope(na )
  pr vate val preTransformCounter = (user d: Long) =>
    scopedStatsRece ver
      .scope("pre_transform").scope(user d.toStr ng).counter("debug_author_l st")
  pr vate val postTransformCounter = (user d: Long) =>
    scopedStatsRece ver
      .scope("post_transform").scope(user d.toStr ng).counter("debug_author_l st")

  overr de def apply(envelope: Cand dateEnvelope): Future[Cand dateEnvelope] = {
    val debugAuthorL st = debugAuthorL stDependencyProv der.apply(envelope.query)
    envelope.searchResults
      .f lter( sT etFromDebugAuthorL st(_, debugAuthorL st))
      .flatMap(_. tadata)
      .foreach( tadata => preTransformCounter( tadata.fromUser d). ncr())

    underly ngTransfor r
      .apply(envelope)
      .map { result =>
        envelope.searchResults
          .f lter( sT etFromDebugAuthorL st(_, debugAuthorL st))
          .flatMap(_. tadata)
          .foreach( tadata => postTransformCounter( tadata.fromUser d). ncr())
        result
      }
  }

  pr vate def  sT etFromDebugAuthorL st(
    searchResult: Thr ftSearchResult,
    debugAuthorL st: Seq[Long]
  ): Boolean =
    searchResult. tadata.ex sts( tadata => debugAuthorL st.conta ns( tadata.fromUser d))

}
