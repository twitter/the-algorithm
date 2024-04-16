package com.tw ter.s mclustersann.cand date_s ce

 mport com.tw ter.s mclusters_v2.common.Cluster d
 mport com.tw ter.s mclusters_v2.common.S mClustersEmbedd ng
 mport com.tw ter.s mclusters_v2.common.T et d
 mport com.tw ter.s mclusters_v2.thr ftscala. nternal d
 mport com.tw ter.s mclusters_v2.thr ftscala.S mClustersEmbedd ng d
 mport com.tw ter.s mclustersann.thr ftscala.Scor ngAlgor hm
 mport com.tw ter.s mclustersann.thr ftscala.S mClustersANNConf g
 mport com.tw ter.snowflake. d.Snowflake d
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.T  
 mport scala.collect on.mutable

/**
 * T  store looks for t ets whose s m lar y  s close to a S ce S mClustersEmbedd ng d.
 *
 * Approx mate cos ne s m lar y  s t  core algor hm to dr ve t  store.
 *
 * Step 1 - 4 are  n "fetchCand dates"  thod.
 * 1. Retr eve t  S mClusters Embedd ng by t  S mClustersEmbedd ng d
 * 2. Fetch top N clusters' top t ets from t  clusterT etCand datesStore (TopT etsPerCluster  ndex).
 * 3. Calculate all t  t et cand dates' dot-product or approx mate cos ne s m lar y to s ce t ets.
 * 4. Take top M t et cand dates by t  step 3's score
 */
tra  Approx mateCos neS m lar y {
  type ScoredT et = (Long, Double)
  def apply(
    s ceEmbedd ng: S mClustersEmbedd ng,
    s ceEmbedd ng d: S mClustersEmbedd ng d,
    conf g: S mClustersANNConf g,
    cand dateScoresStat:  nt => Un ,
    clusterT etsMap: Map[Cluster d, Opt on[Seq[(T et d, Double)]]],
    clusterT etsMapArray: Map[Cluster d, Opt on[Array[(T et d, Double)]]] = Map.empty
  ): Seq[ScoredT et]
}

object Approx mateCos neS m lar y extends Approx mateCos neS m lar y {

  f nal val  n  alCand dateMapS ze = 16384
  val MaxNumResultsUpperBound = 1000
  f nal val MaxT etCand dateAgeUpperBound = 175200

  pr vate class HashMap[A, B]( n S ze:  nt) extends mutable.HashMap[A, B] {
    overr de def  n  alS ze:  nt =  n S ze // 16 - by default
  }

  pr vate def parseT et d(embedd ng d: S mClustersEmbedd ng d): Opt on[T et d] = {
    embedd ng d. nternal d match {
      case  nternal d.T et d(t et d) =>
        So (t et d)
      case _ =>
        None
    }
  }

  overr de def apply(
    s ceEmbedd ng: S mClustersEmbedd ng,
    s ceEmbedd ng d: S mClustersEmbedd ng d,
    conf g: S mClustersANNConf g,
    cand dateScoresStat:  nt => Un ,
    clusterT etsMap: Map[Cluster d, Opt on[Seq[(T et d, Double)]]] = Map.empty,
    clusterT etsMapArray: Map[Cluster d, Opt on[Array[(T et d, Double)]]] = Map.empty
  ): Seq[ScoredT et] = {
    val now = T  .now
    val earl estT et d =
       f (conf g.maxT etCand dateAgeH s >= MaxT etCand dateAgeUpperBound)
        0L // D sable max t et age f lter
      else
        Snowflake d.f rst dFor(now - Durat on.fromH s(conf g.maxT etCand dateAgeH s))
    val latestT et d =
      Snowflake d.f rst dFor(now - Durat on.fromH s(conf g.m nT etCand dateAgeH s))

    // Use Mutable map to opt m ze performance. T   thod  s thread-safe.

    // Set  n  al map s ze to around p75 of map s ze d str but on to avo d too many copy ng
    // from extend ng t  s ze of t  mutable hashmap
    val cand dateScoresMap =
      new HashMap[T et d, Double]( n  alCand dateMapS ze)
    val cand dateNormal zat onMap =
      new HashMap[T et d, Double]( n  alCand dateMapS ze)

    clusterT etsMap.foreach {
      case (cluster d, So (t etScores))  f s ceEmbedd ng.conta ns(cluster d) =>
        val s ceClusterScore = s ceEmbedd ng.getOrElse(cluster d)

        for (  <- 0 unt l Math.m n(t etScores.s ze, conf g.maxTopT etsPerCluster)) {
          val (t et d, score) = t etScores( )

           f (!parseT et d(s ceEmbedd ng d).conta ns(t et d) &&
            t et d >= earl estT et d && t et d <= latestT et d) {
            cand dateScoresMap.put(
              t et d,
              cand dateScoresMap.getOrElse(t et d, 0.0) + score * s ceClusterScore)
            cand dateNormal zat onMap
              .put(t et d, cand dateNormal zat onMap.getOrElse(t et d, 0.0) + score * score)
          }
        }
      case _ => ()
    }

    cand dateScoresStat(cand dateScoresMap.s ze)

    // Re-Rank t  cand date by conf gurat on
    val processedCand dateScores: Seq[(T et d, Double)] = cand dateScoresMap.map {
      case (cand date d, score) =>
        // Enable Part al Normal zat on
        val processedScore = {
          //   appl ed t  "log" vers on of part al normal zat on w n   rank cand dates
          // by log cos ne s m lar y
          conf g.annAlgor hm match {
            case Scor ngAlgor hm.LogCos neS m lar y =>
              score / s ceEmbedd ng.logNorm / math.log(1 + cand dateNormal zat onMap(cand date d))
            case Scor ngAlgor hm.Cos neS m lar y =>
              score / s ceEmbedd ng.l2norm / math.sqrt(cand dateNormal zat onMap(cand date d))
            case Scor ngAlgor hm.Cos neS m lar yNoS ceEmbedd ngNormal zat on =>
              score / math.sqrt(cand dateNormal zat onMap(cand date d))
            case Scor ngAlgor hm.DotProduct => score
          }
        }
        cand date d -> processedScore
    }.toSeq

    processedCand dateScores
      .f lter(_._2 >= conf g.m nScore)
      .sortBy(-_._2)
      .take(Math.m n(conf g.maxNumResults, MaxNumResultsUpperBound))
  }
}
