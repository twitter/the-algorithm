package com.tw ter.tsp.stores

 mport com.tw ter.tsp.stores.Top cT etsCos neS m lar yAggregateStore.ScoreKey
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.ut l.StatsUt l
 mport com.tw ter.s mclusters_v2.thr ftscala._
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.s mclusters_v2.common.T et d
 mport com.tw ter.tsp.stores.Semant cCoreAnnotat onStore._
 mport com.tw ter.tsp.stores.Top cSoc alProofStore.Top cSoc alProof
 mport com.tw ter.ut l.Future

/**
 * Prov des a sess on-less Top c Soc al Proof  nformat on wh ch doesn't rely on any User  nfo.
 * T  store  s used by  mCac  and  n- mory cac  to ach eve a h g r performance.
 * One Consu r embedd ng and Producer embedd ng are used to calculate raw score.
 */
case class Top cSoc alProofStore(
  representat onScorerStore: ReadableStore[Score d, Score],
  semant cCoreAnnotat onStore: ReadableStore[T et d, Seq[Top cAnnotat on]]
)(
  statsRece ver: StatsRece ver)
    extends ReadableStore[Top cSoc alProofStore.Query, Seq[Top cSoc alProof]] {
   mport Top cSoc alProofStore._

  // Fetc s t  t et's top c annotat ons from Semant cCore's Annotat on AP 
  overr de def get(query: Top cSoc alProofStore.Query): Future[Opt on[Seq[Top cSoc alProof]]] = {
    StatsUt l.trackOpt onStats(statsRece ver) {
      for {
        annotat ons <-
          StatsUt l.track emsStats(statsRece ver.scope("semant cCoreAnnotat onStore")) {
            semant cCoreAnnotat onStore.get(query.cac ableQuery.t et d).map(_.getOrElse(N l))
          }

        f lteredAnnotat ons = f lterAnnotat onsByAllowL st(annotat ons, query)

        scoredTop cs <-
          StatsUt l.track emMapStats(statsRece ver.scope("scoreTop cT etsT etLanguage")) {
            // de-dup  dent cal top c ds
            val un queTop c ds = f lteredAnnotat ons.map { annotat on =>
              Top c d(annotat on.top c d, So (query.cac ableQuery.t etLanguage), country = None)
            }.toSet

             f (query.cac ableQuery.enableCos neS m lar yScoreCalculat on) {
              scoreTop cT ets(query.cac ableQuery.t et d, un queTop c ds)
            } else {
              Future.value(un queTop c ds.map( d =>  d -> Map.empty[ScoreKey, Double]).toMap)
            }
          }

      } y eld {
         f (scoredTop cs.nonEmpty) {
          val vers onedTop cProofs = f lteredAnnotat ons.map { annotat on =>
            val top c d =
              Top c d(annotat on.top c d, So (query.cac ableQuery.t etLanguage), country = None)

            Top cSoc alProof(
              top c d,
              scores = scoredTop cs.getOrElse(top c d, Map.empty),
              annotat on. gnoreS mClustersF lter,
              annotat on.modelVers on d
            )
          }
          So (vers onedTop cProofs)
        } else {
          None
        }
      }
    }
  }

  /***
   * W n t  allowL st  s not empty (e.g., TSP handler call, CrTop c handler call),
   * t  f lter w ll be enabled and   w ll only keep annotat ons that have vers on ds ex st ng
   *  n t   nput allo dSemant cCoreVers on ds set.
   * But w n t  allowL st  s empty (e.g., so  debugger calls),
   *   w ll not f lter anyth ng and pass.
   *   l m  t  number of vers on ds to be K = MaxNumberVers on ds
   */
  pr vate def f lterAnnotat onsByAllowL st(
    annotat ons: Seq[Top cAnnotat on],
    query: Top cSoc alProofStore.Query
  ): Seq[Top cAnnotat on] = {

    val tr m dVers on ds = query.allo dSemant cCoreVers on ds.take(MaxNumberVers on ds)
    annotat ons.f lter { annotat on =>
      tr m dVers on ds. sEmpty || tr m dVers on ds.conta ns(annotat on.modelVers on d)
    }
  }

  pr vate def scoreTop cT ets(
    t et d: T et d,
    top c ds: Set[Top c d]
  ): Future[Map[Top c d, Map[ScoreKey, Double]]] = {
    Future.collect {
      top c ds.map { top c d =>
        val scoresFut = Top cT etsCos neS m lar yAggregateStore.getRawScoresMap(
          top c d,
          t et d,
          Top cT etsCos neS m lar yAggregateStore.DefaultScoreKeys,
          representat onScorerStore
        )
        top c d -> scoresFut
      }.toMap
    }
  }
}

object Top cSoc alProofStore {

  pr vate val MaxNumberVers on ds = 9

  case class Query(
    cac ableQuery: Cac ableQuery,
    allo dSemant cCoreVers on ds: Set[Long] = Set.empty) // overr dden by FS

  case class Cac ableQuery(
    t et d: T et d,
    t etLanguage: Str ng,
    enableCos neS m lar yScoreCalculat on: Boolean = true)

  case class Top cSoc alProof(
    top c d: Top c d,
    scores: Map[ScoreKey, Double],
     gnoreS mClusterF lter ng: Boolean,
    semant cCoreVers on d: Long)
}
