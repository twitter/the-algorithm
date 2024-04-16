package com.tw ter.s mclusters_v2.summ ngb rd.storm

 mport com.tw ter.s mclusters_v2.common.T et d
 mport com.tw ter.s mclusters_v2.summ ngb rd.common. mpl c s
 mport com.tw ter.s mclusters_v2.summ ngb rd.common.Mono ds.Pers stentS mClustersEmbedd ngLongestL2NormMono d
 mport com.tw ter.s mclusters_v2.summ ngb rd.common.StatsUt l
 mport com.tw ter.s mclusters_v2.summ ngb rd.stores.Pers stentT etEmbedd ngStore.{
  LatestEmbedd ngVers on,
  LongestL2Embedd ngVers on,
  Pers stentT etEmbedd ng d
}
 mport com.tw ter.s mclusters_v2.thr ftscala.{
  Pers stentS mClustersEmbedd ng,
  S mClustersEmbedd ng,
  S mClustersEmbedd ng tadata
}
 mport com.tw ter.summ ngb rd.opt on.Job d
 mport com.tw ter.summ ngb rd.{Platform, Producer, Ta lProducer}
 mport com.tw ter.t  l neserv ce.thr ftscala.Event
 mport com.tw ter.t etyp e.thr ftscala.StatusCounts

/**
 * T  job to save t  qual f ed t et S mClustersEmbedd ng  nto Strato Store(Back by Manhattan).
 *
 * T  steps
 * 1. Read from Favor e Stream.
 * 2. Jo n w h T et Status Count Serv ce.
 * 3. F lter out t  t ets whose favor e count < 8.
 *      cons der t se t ets' S mClusters embedd ng  s too no sy and untrustable.
 * 4. Update t  S mClusters T et embedd ng w h t  stamp 0L.
 *    0L  s reserved for t  latest t et embedd ng.  's also used to ma nta n t  t et count.
 * 5.  f t  S mClusters T et embedd ng's update count  s 2 po r N & N >= 3.
 *    Pers stent t  embedd ngs w h t  t  stamp as part of t  LK.
 **/
pr vate[storm] object Pers stentT etJob {
   mport StatsUt l._

  pr vate val M nFavor eCount = 8
  type T  stamp = Long

  val longestL2NormMono d = new Pers stentS mClustersEmbedd ngLongestL2NormMono d()

  def generate[P <: Platform[P]](
    t  l neEventS ce: Producer[P, Event],
    t etStatusCountServ ce: P#Serv ce[T et d, StatusCounts],
    t etEmbedd ngServ ce: P#Serv ce[T et d, S mClustersEmbedd ng],
    pers stentT etEmbedd ngStoreW hLatestAggregat on: P#Store[
      Pers stentT etEmbedd ng d,
      Pers stentS mClustersEmbedd ng
    ],
    pers stentT etEmbedd ngStoreW hLongestL2NormAggregat on: P#Store[
      Pers stentT etEmbedd ng d,
      Pers stentS mClustersEmbedd ng
    ]
  )(
     mpl c  job d: Job d
  ): Ta lProducer[P, Any] = {

    val t  l neEvents: Producer[P, (T et d, T  stamp)] = t  l neEventS ce
      .collect {
        case Event.Favor e(favor eEvent) =>
          (favor eEvent.t et d, favor eEvent.eventT  Ms)
      }

    val f lteredEvents = t  l neEvents
      .leftJo n[StatusCounts](t etStatusCountServ ce)
      .f lter {
        case (_, (_, So (statusCounts))) =>
          // Only cons der t ets wh ch has more than 8 favor e
          statusCounts.favor eCount.ex sts(_ >= M nFavor eCount)
        case _ =>
          false
      }
      .leftJo n[S mClustersEmbedd ng](t etEmbedd ngServ ce)

    val latestAndPers stentEmbedd ngProducer = f lteredEvents
      .collect {
        case (t et d, ((eventT  Ms, _), So (t etEmbedd ng))) =>
          (
            // T  spec al t  stamp  s a reserved space for t  latest t et embedd ng.
            Pers stentT etEmbedd ng d(t et d, t  stamp nMs = LatestEmbedd ngVers on),
            Pers stentS mClustersEmbedd ng(
              t etEmbedd ng,
              S mClustersEmbedd ng tadata(updatedAtMs = So (eventT  Ms), updatedCount = So (1))
            ))
      }
      .observe("num_of_embedd ng_updates")
      .sumByKey(pers stentT etEmbedd ngStoreW hLatestAggregat on)(
         mpl c s.pers stentS mClustersEmbedd ngMono d)
      .na ("latest_embedd ng_producer")
      .flatMap {
        case (pers stentT etEmbedd ng d, (maybeEmbedd ng, deltaEmbedd ng)) =>
          lastQual f edUpdatedCount(
            maybeEmbedd ng.flatMap(_. tadata.updatedCount),
            deltaEmbedd ng. tadata.updatedCount
          ).map { newUpdateCount =>
            (
              pers stentT etEmbedd ng d.copy(t  stamp nMs =
                deltaEmbedd ng. tadata.updatedAtMs.getOrElse(0L)),
              deltaEmbedd ng.copy( tadata =
                deltaEmbedd ng. tadata.copy(updatedCount = So (newUpdateCount)))
            )
          }
      }
      .observe("num_of_extra_embedd ng")
      .sumByKey(pers stentT etEmbedd ngStoreW hLatestAggregat on)(
         mpl c s.pers stentS mClustersEmbedd ngMono d)
      .na ("pers stent_embedd ngs_producer")

    val longestL2NormEmbedd ngProducer = f lteredEvents
      .collect {
        case (t et d, ((eventT  Ms, So (statusCounts)), So (t etEmbedd ng))) =>
          (
            // T  spec al t  stamp  s a reserved space for t  latest t et embedd ng.
            Pers stentT etEmbedd ng d(t et d, t  stamp nMs = LongestL2Embedd ngVers on),
            Pers stentS mClustersEmbedd ng(
              t etEmbedd ng,
              S mClustersEmbedd ng tadata(
                updatedAtMs = So (eventT  Ms),
                //  're not aggregat ng t  ex st ng embedd ng,  're replac ng  . T  count
                // t refore needs to be t  absolute fav count for t  t et, not t  delta.
                updatedCount = statusCounts.favor eCount.map(_ + 1)
              )
            ))
      }
      .observe("num_longest_l2_norm_updates")
      .sumByKey(pers stentT etEmbedd ngStoreW hLongestL2NormAggregat on)(longestL2NormMono d)
      .na ("longest_l2_norm_embedd ng_producer")

    latestAndPers stentEmbedd ngProducer.also(longestL2NormEmbedd ngProducer)
  }

  /*
     f t  change  n counts crosses one or more po rs of 2 (8,16,32...), return t  last boundary
    that was crossed.  n t  case w re a count delta  s large,   may sk p a po r of 2, and
    thus   may not store embedd ngs for all 2^( +3) w re 0 <=   <= t etFavCount.
   */
  pr vate def lastQual f edUpdatedCount(
    ex st ngUpdateCount: Opt on[Long],
    deltaUpdateCount: Opt on[Long]
  ): Opt on[ nt] = {
    val ex st ng = ex st ngUpdateCount.getOrElse(0L)
    val sum = ex st ng + deltaUpdateCount.getOrElse(0L)
    qual f edSet.f lter {   => (ex st ng <  ) && (  <= sum) }.lastOpt on
  }

  // Only 2 Po r n wh le n >= 3  s qual f ed for Pers stent. T  max = 16,777,216
  pr vate lazy val qual f edSet = 3
    .unt l(25).map {   => Math.pow(2,  ).to nt }.toSet

}
