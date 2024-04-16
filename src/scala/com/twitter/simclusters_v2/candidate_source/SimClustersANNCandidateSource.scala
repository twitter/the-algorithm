package com.tw ter.s mclusters_v2.cand date_s ce

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base.Cand dateS ce
 mport com.tw ter.fr gate.common.base.Stats
 mport com.tw ter.s mclusters_v2.cand date_s ce. avyRanker.Un formScoreStoreRanker
 mport com.tw ter.s mclusters_v2.cand date_s ce.S mClustersANNCand dateS ce.S mClustersANNConf g
 mport com.tw ter.s mclusters_v2.cand date_s ce.S mClustersANNCand dateS ce.S mClustersT etCand date
 mport com.tw ter.s mclusters_v2.common.ModelVers ons._
 mport com.tw ter.s mclusters_v2.common.Cluster d
 mport com.tw ter.s mclusters_v2.common.S mClustersEmbedd ng
 mport com.tw ter.s mclusters_v2.common.T et d
 mport com.tw ter.s mclusters_v2.summ ngb rd.stores.ClusterKey
 mport com.tw ter.s mclusters_v2.thr ftscala.Embedd ngType
 mport com.tw ter.s mclusters_v2.thr ftscala. nternal d
 mport com.tw ter.s mclusters_v2.thr ftscala.Score nternal d
 mport com.tw ter.s mclusters_v2.thr ftscala.Scor ngAlgor hm
 mport com.tw ter.s mclusters_v2.thr ftscala.S mClustersEmbedd ng d
 mport com.tw ter.s mclusters_v2.thr ftscala.S mClustersEmbedd ngPa rScore d
 mport com.tw ter.s mclusters_v2.thr ftscala.{Score => Thr ftScore}
 mport com.tw ter.s mclusters_v2.thr ftscala.{Score d => Thr ftScore d}
 mport com.tw ter.snowflake. d.Snowflake d
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Future
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
 * Step 5-6 are  n "rerank ng"  thod.
 * 5. Calculate t  s m lar y score bet en s ce and cand dates.
 * 6. Return top N cand dates by t  step 5's score.
 *
 * Warn ng: Only turn off t  step 5 for User  nterested n cand date generat on.  's t  only use
 * case  n Recos that   use dot-product to rank t  t et cand dates.
 */
case class S mClustersANNCand dateS ce(
  clusterT etCand datesStore: ReadableStore[ClusterKey, Seq[(T et d, Double)]],
  s mClustersEmbedd ngStore: ReadableStore[S mClustersEmbedd ng d, S mClustersEmbedd ng],
   avyRanker:  avyRanker. avyRanker,
  conf gs: Map[Embedd ngType, S mClustersANNConf g],
  statsRece ver: StatsRece ver)
    extends Cand dateS ce[S mClustersANNCand dateS ce.Query, S mClustersT etCand date] {

   mport S mClustersANNCand dateS ce._

  overr de val na : Str ng = t .getClass.getNa 
  pr vate val stats = statsRece ver.scope(t .getClass.getNa )

  pr vate val fetchS ceEmbedd ngStat = stats.scope("fetchS ceEmbedd ng")
  protected val fetchCand dateEmbedd ngsStat = stats.scope("fetchCand dateEmbedd ngs")
  pr vate val fetchCand datesStat = stats.scope("fetchCand dates")
  pr vate val rerank ngStat = stats.scope("rerank ng")

  overr de def get(
    query: S mClustersANNCand dateS ce.Query
  ): Future[Opt on[Seq[S mClustersT etCand date]]] = {
    val s ceEmbedd ng d = query.s ceEmbedd ng d
    loadConf g(query) match {
      case So (conf g) =>
        for {
          maybeS mClustersEmbedd ng <- Stats.track(fetchS ceEmbedd ngStat) {
            s mClustersEmbedd ngStore.get(query.s ceEmbedd ng d)
          }
          maybeF lteredCand dates <- maybeS mClustersEmbedd ng match {
            case So (s ceEmbedd ng) =>
              for {
                rawCand dates <- Stats.trackSeq(fetchCand datesStat) {
                  fetchCand dates(s ceEmbedd ng d, conf g, s ceEmbedd ng)
                }
                rankedCand dates <- Stats.trackSeq(rerank ngStat) {
                  rerank ng(s ceEmbedd ng d, conf g, rawCand dates)
                }
              } y eld {
                fetchCand datesStat
                  .stat(
                    s ceEmbedd ng d.embedd ngType.na ,
                    s ceEmbedd ng d.modelVers on.na ).add(rankedCand dates.s ze)
                So (rankedCand dates)
              }
            case None =>
              fetchCand datesStat
                .stat(
                  s ceEmbedd ng d.embedd ngType.na ,
                  s ceEmbedd ng d.modelVers on.na ).add(0)
              Future.None
          }
        } y eld {
          maybeF lteredCand dates
        }
      case _ =>
        // Sk p over quer es whose conf g  s not def ned
        Future.None
    }
  }

  pr vate def fetchCand dates(
    s ceEmbedd ng d: S mClustersEmbedd ng d,
    conf g: S mClustersANNConf g,
    s ceEmbedd ng: S mClustersEmbedd ng
  ): Future[Seq[S mClustersT etCand date]] = {
    val now = T  .now
    val earl estT et d = Snowflake d.f rst dFor(now - conf g.maxT etCand dateAge)
    val latestT et d = Snowflake d.f rst dFor(now - conf g.m nT etCand dateAge)
    val cluster ds =
      s ceEmbedd ng
        .truncate(conf g.maxScanClusters).cluster ds
        .map { cluster d: Cluster d =>
          ClusterKey(cluster d, s ceEmbedd ng d.modelVers on, conf g.cand dateEmbedd ngType)
        }.toSet

    Future
      .collect {
        clusterT etCand datesStore.mult Get(cluster ds)
      }.map { clusterT etsMap =>
        // Use Mutable map to opt m ze performance. T   thod  s thread-safe.
        // Set  n  al map s ze to around p75 of map s ze d str but on to avo d too many copy ng
        // from extend ng t  s ze of t  mutable hashmap
        val cand dateScoresMap =
          new S mClustersANNCand dateS ce.HashMap[T et d, Double]( n  alCand dateMapS ze)
        val cand dateNormal zat onMap =
          new S mClustersANNCand dateS ce.HashMap[T et d, Double]( n  alCand dateMapS ze)

        clusterT etsMap.foreach {
          case (ClusterKey(cluster d, _, _, _), So (t etScores))
               f s ceEmbedd ng.conta ns(cluster d) =>
            val s ceClusterScore = s ceEmbedd ng.getOrElse(cluster d)

            for (  <- 0 unt l Math.m n(t etScores.s ze, conf g.maxTopT etsPerCluster)) {
              val (t et d, score) = t etScores( )

               f (!parseT et d(s ceEmbedd ng d).conta ns(t et d) &&
                t et d >= earl estT et d && t et d <= latestT et d) {
                cand dateScoresMap.put(
                  t et d,
                  cand dateScoresMap.getOrElse(t et d, 0.0) + score * s ceClusterScore)
                 f (conf g.enablePart alNormal zat on) {
                  cand dateNormal zat onMap
                    .put(t et d, cand dateNormal zat onMap.getOrElse(t et d, 0.0) + score * score)
                }
              }
            }
          case _ => ()
        }

        stats.stat("cand dateScoresMap").add(cand dateScoresMap.s ze)
        stats.stat("cand dateNormal zat onMap").add(cand dateNormal zat onMap.s ze)

        // Re-Rank t  cand date by conf gurat on
        val processedCand dateScores = cand dateScoresMap.map {
          case (cand date d, score) =>
            // Enable Part al Normal zat on
            val processedScore =
               f (conf g.enablePart alNormal zat on) {
                //   appl ed t  "log" vers on of part al normal zat on w n   rank cand dates
                // by log cos ne s m lar y
                 f (conf g.rank ngAlgor hm == Scor ngAlgor hm.Pa rEmbedd ngLogCos neS m lar y) {
                  score / s ceEmbedd ng.l2norm / math.log(
                    1 + cand dateNormal zat onMap(cand date d))
                } else {
                  score / s ceEmbedd ng.l2norm / math.sqrt(cand dateNormal zat onMap(cand date d))
                }
              } else score
            S mClustersT etCand date(cand date d, processedScore, s ceEmbedd ng d)
        }.toSeq

        processedCand dateScores
          .sortBy(-_.score)
      }
  }

  pr vate def rerank ng(
    s ceEmbedd ng d: S mClustersEmbedd ng d,
    conf g: S mClustersANNConf g,
    cand dates: Seq[S mClustersT etCand date]
  ): Future[Seq[S mClustersT etCand date]] = {
    val rankedCand dates =  f (conf g.enable avyRank ng) {
       avyRanker
        .rank(
          scor ngAlgor hm = conf g.rank ngAlgor hm,
          s ceEmbedd ng d = s ceEmbedd ng d,
          cand dateEmbedd ngType = conf g.cand dateEmbedd ngType,
          m nScore = conf g.m nScore,
          cand dates = cand dates.take(conf g.maxReRank ngCand dates)
        ).map(_.sortBy(-_.score))
    } else {
      Future.value(cand dates)
    }
    rankedCand dates.map(_.take(conf g.maxNumResults))
  }

  pr vate[cand date_s ce] def loadConf g(query: Query): Opt on[S mClustersANNConf g] = {
    conf gs.get(query.s ceEmbedd ng d.embedd ngType).map { baseConf g =>
      // apply overr des  f any
      query.overr deConf g match {
        case So (overr des) =>
          baseConf g.copy(
            maxNumResults = overr des.maxNumResults.getOrElse(baseConf g.maxNumResults),
            maxT etCand dateAge =
              overr des.maxT etCand dateAge.getOrElse(baseConf g.maxT etCand dateAge),
            m nScore = overr des.m nScore.getOrElse(baseConf g.m nScore),
            cand dateEmbedd ngType =
              overr des.cand dateEmbedd ngType.getOrElse(baseConf g.cand dateEmbedd ngType),
            enablePart alNormal zat on =
              overr des.enablePart alNormal zat on.getOrElse(baseConf g.enablePart alNormal zat on),
            enable avyRank ng =
              overr des.enable avyRank ng.getOrElse(baseConf g.enable avyRank ng),
            rank ngAlgor hm = overr des.rank ngAlgor hm.getOrElse(baseConf g.rank ngAlgor hm),
            maxReRank ngCand dates =
              overr des.maxReRank ngCand dates.getOrElse(baseConf g.maxReRank ngCand dates),
            maxTopT etsPerCluster =
              overr des.maxTopT etsPerCluster.getOrElse(baseConf g.maxTopT etsPerCluster),
            maxScanClusters = overr des.maxScanClusters.getOrElse(baseConf g.maxScanClusters),
            m nT etCand dateAge =
              overr des.m nT etCand dateAge.getOrElse(baseConf g.m nT etCand dateAge)
          )
        case _ => baseConf g
      }
    }
  }
}

object S mClustersANNCand dateS ce {

  f nal val Product onMaxNumResults = 200
  f nal val  n  alCand dateMapS ze = 16384

  def apply(
    clusterT etCand datesStore: ReadableStore[ClusterKey, Seq[(T et d, Double)]],
    s mClustersEmbedd ngStore: ReadableStore[S mClustersEmbedd ng d, S mClustersEmbedd ng],
    un formScor ngStore: ReadableStore[Thr ftScore d, Thr ftScore],
    conf gs: Map[Embedd ngType, S mClustersANNConf g],
    statsRece ver: StatsRece ver
  ) = new S mClustersANNCand dateS ce(
    clusterT etCand datesStore = clusterT etCand datesStore,
    s mClustersEmbedd ngStore = s mClustersEmbedd ngStore,
     avyRanker = new Un formScoreStoreRanker(un formScor ngStore, statsRece ver),
    conf gs = conf gs,
    statsRece ver = statsRece ver
  )

  pr vate def parseT et d(embedd ng d: S mClustersEmbedd ng d): Opt on[T et d] = {
    embedd ng d. nternal d match {
      case  nternal d.T et d(t et d) =>
        So (t et d)
      case _ =>
        None
    }
  }

  case class Query(
    s ceEmbedd ng d: S mClustersEmbedd ng d,
    // Only overr de t  conf g  n DDG and Debuggers.
    // Use Post-f lter for t  holdbacks for better cac  h  rate.
    overr deConf g: Opt on[S mClustersANNConf gOverr de] = None)

  case class S mClustersT etCand date(
    t et d: T et d,
    score: Double,
    s ceEmbedd ng d: S mClustersEmbedd ng d)

  class HashMap[A, B]( n S ze:  nt) extends mutable.HashMap[A, B] {
    overr de def  n  alS ze:  nt =  n S ze // 16 - by default
  }

  /**
   * T  Conf gurat on of Each S mClusters ANN Cand date S ce.
   * Expect One S mClusters Embedd ng Type mapp ng to a S mClusters ANN Conf gurat on  n Product on.
   */
  case class S mClustersANNConf g(
    // T  max number of cand dates for a ANN Query
    // Please don't overr de t  value  n Product on.
    maxNumResults:  nt = Product onMaxNumResults,
    // T  max t et cand date durat on from now.
    maxT etCand dateAge: Durat on,
    // T  m n score of t  cand dates
    m nScore: Double,
    // T  Cand date Embedd ng Type of T et.
    cand dateEmbedd ngType: Embedd ngType,
    // Enables normal zat on of approx mate S mClusters vectors to remove popular y b as
    enablePart alNormal zat on: Boolean,
    // W t r to enable Embedd ng S m lar y rank ng
    enable avyRank ng: Boolean,
    // T  rank ng algor hm for S ce Cand date S m lar y
    rank ngAlgor hm: Scor ngAlgor hm,
    // T  max number of cand dates  n ReRank ng Step
    maxReRank ngCand dates:  nt,
    // T  max number of Top T ets from every cluster t et  ndex
    maxTopT etsPerCluster:  nt,
    // T  max number of Clusters  n t  s ce Embedd ngs.
    maxScanClusters:  nt,
    // T  m n t et cand date durat on from now.
    m nT etCand dateAge: Durat on)

  /**
   * Conta ns sa  f elds as [[S mClustersANNConf g]], to spec fy wh ch f elds are to be overr den
   * for exper  ntal purposes.
   *
   * All f elds  n t  class must be opt onal.
   */
  case class S mClustersANNConf gOverr de(
    maxNumResults: Opt on[ nt] = None,
    maxT etCand dateAge: Opt on[Durat on] = None,
    m nScore: Opt on[Double] = None,
    cand dateEmbedd ngType: Opt on[Embedd ngType] = None,
    enablePart alNormal zat on: Opt on[Boolean] = None,
    enable avyRank ng: Opt on[Boolean] = None,
    rank ngAlgor hm: Opt on[Scor ngAlgor hm] = None,
    maxReRank ngCand dates: Opt on[ nt] = None,
    maxTopT etsPerCluster: Opt on[ nt] = None,
    maxScanClusters: Opt on[ nt] = None,
    m nT etCand dateAge: Opt on[Durat on] = None,
    enableLookbackS ce: Opt on[Boolean] = None)

  f nal val DefaultMaxTopT etsPerCluster = 200
  f nal val DefaultEnable avyRank ng = false
  object S mClustersANNConf g {
    val DefaultS mClustersANNConf g: S mClustersANNConf g =
      S mClustersANNConf g(
        maxT etCand dateAge = 1.days,
        m nScore = 0.7,
        cand dateEmbedd ngType = Embedd ngType.LogFavBasedT et,
        enablePart alNormal zat on = true,
        enable avyRank ng = false,
        rank ngAlgor hm = Scor ngAlgor hm.Pa rEmbedd ngCos neS m lar y,
        maxReRank ngCand dates = 250,
        maxTopT etsPerCluster = 200,
        maxScanClusters = 50,
        m nT etCand dateAge = 0.seconds
      )
  }

  val Lookback d aM nDays:  nt = 0
  val Lookback d aMaxDays:  nt = 2
  val Lookback d aMaxT etsPerDay:  nt = 2000
  val maxTopT etsPerCluster:  nt =
    (Lookback d aMaxDays - Lookback d aM nDays + 1) * Lookback d aMaxT etsPerDay

  val Lookback d aT etConf g: Map[Embedd ngType, S mClustersANNConf g] = {
    val cand dateEmbedd ngType = Embedd ngType.LogFavLongestL2Embedd ngT et
    val m nT etAge = Lookback d aM nDays.days
    val maxT etAge =
      Lookback d aMaxDays.days - 1.h  // To compensate for t  cac  TTL that m ght push t  t et age beyond max age
    val rank ngAlgor hm = Scor ngAlgor hm.Pa rEmbedd ngCos neS m lar y

    val maxScanClusters = 50
    val m nScore = 0.5
    Map(
      Embedd ngType.FavBasedProducer -> S mClustersANNConf g(
        m nT etCand dateAge = m nT etAge,
        maxT etCand dateAge = maxT etAge,
        m nScore =
          m nScore, // for tw stly cand dates. To spec fy a h g r threshold, use a post-f lter
        cand dateEmbedd ngType = cand dateEmbedd ngType,
        enablePart alNormal zat on = true,
        enable avyRank ng = DefaultEnable avyRank ng,
        rank ngAlgor hm = rank ngAlgor hm,
        maxReRank ngCand dates = 250,
        maxTopT etsPerCluster = maxTopT etsPerCluster,
        maxScanClusters = maxScanClusters,
      ),
      Embedd ngType.LogFavLongestL2Embedd ngT et -> S mClustersANNConf g(
        m nT etCand dateAge = m nT etAge,
        maxT etCand dateAge = maxT etAge,
        m nScore =
          m nScore, // for tw stly cand dates. To spec fy a h g r threshold, use a post-f lter
        cand dateEmbedd ngType = cand dateEmbedd ngType,
        enablePart alNormal zat on = true,
        enable avyRank ng = DefaultEnable avyRank ng,
        rank ngAlgor hm = rank ngAlgor hm,
        maxReRank ngCand dates = 250,
        maxTopT etsPerCluster = maxTopT etsPerCluster,
        maxScanClusters = maxScanClusters,
      ),
      Embedd ngType.FavTfgTop c -> S mClustersANNConf g(
        m nT etCand dateAge = m nT etAge,
        maxT etCand dateAge = maxT etAge,
        m nScore = m nScore,
        cand dateEmbedd ngType = cand dateEmbedd ngType,
        enablePart alNormal zat on = true,
        enable avyRank ng = DefaultEnable avyRank ng,
        rank ngAlgor hm = rank ngAlgor hm,
        maxReRank ngCand dates = 400,
        maxTopT etsPerCluster = 200,
        maxScanClusters = maxScanClusters,
      ),
      Embedd ngType.LogFavBasedKgoApeTop c -> S mClustersANNConf g(
        m nT etCand dateAge = m nT etAge,
        maxT etCand dateAge = maxT etAge,
        m nScore = m nScore,
        cand dateEmbedd ngType = cand dateEmbedd ngType,
        enablePart alNormal zat on = true,
        enable avyRank ng = DefaultEnable avyRank ng,
        rank ngAlgor hm = rank ngAlgor hm,
        maxReRank ngCand dates = 400,
        maxTopT etsPerCluster = 200,
        maxScanClusters = maxScanClusters,
      ),
    )
  }

  val DefaultConf gMapp ngs: Map[Embedd ngType, S mClustersANNConf g] = Map(
    Embedd ngType.FavBasedProducer -> S mClustersANNConf g(
      maxT etCand dateAge = 1.days,
      m nScore = 0.0, // for tw stly cand dates. To spec fy a h g r threshold, use a post-f lter
      cand dateEmbedd ngType = Embedd ngType.LogFavBasedT et,
      enablePart alNormal zat on = true,
      enable avyRank ng = DefaultEnable avyRank ng,
      rank ngAlgor hm = Scor ngAlgor hm.Pa rEmbedd ngCos neS m lar y,
      maxReRank ngCand dates = 250,
      maxTopT etsPerCluster = DefaultMaxTopT etsPerCluster,
      maxScanClusters = 50,
      m nT etCand dateAge = 0.seconds
    ),
    Embedd ngType.LogFavBasedUser nterestedMaxpool ngAddressBookFrom  APE -> S mClustersANNConf g(
      maxT etCand dateAge = 1.days,
      m nScore = 0.0, // for tw stly cand dates. To spec fy a h g r threshold, use a post-f lter
      cand dateEmbedd ngType = Embedd ngType.LogFavBasedT et,
      enablePart alNormal zat on = true,
      enable avyRank ng = DefaultEnable avyRank ng,
      rank ngAlgor hm = Scor ngAlgor hm.Pa rEmbedd ngCos neS m lar y,
      maxReRank ngCand dates = 250,
      maxTopT etsPerCluster = DefaultMaxTopT etsPerCluster,
      maxScanClusters = 50,
      m nT etCand dateAge = 0.seconds
    ),
    Embedd ngType.LogFavBasedUser nterestedAverageAddressBookFrom  APE -> S mClustersANNConf g(
      maxT etCand dateAge = 1.days,
      m nScore = 0.0, // for tw stly cand dates. To spec fy a h g r threshold, use a post-f lter
      cand dateEmbedd ngType = Embedd ngType.LogFavBasedT et,
      enablePart alNormal zat on = true,
      enable avyRank ng = DefaultEnable avyRank ng,
      rank ngAlgor hm = Scor ngAlgor hm.Pa rEmbedd ngCos neS m lar y,
      maxReRank ngCand dates = 250,
      maxTopT etsPerCluster = DefaultMaxTopT etsPerCluster,
      maxScanClusters = 50,
      m nT etCand dateAge = 0.seconds
    ),
    Embedd ngType.LogFavBasedUser nterestedBooktypeMaxpool ngAddressBookFrom  APE -> S mClustersANNConf g(
      maxT etCand dateAge = 1.days,
      m nScore = 0.0, // for tw stly cand dates. To spec fy a h g r threshold, use a post-f lter
      cand dateEmbedd ngType = Embedd ngType.LogFavBasedT et,
      enablePart alNormal zat on = true,
      enable avyRank ng = DefaultEnable avyRank ng,
      rank ngAlgor hm = Scor ngAlgor hm.Pa rEmbedd ngCos neS m lar y,
      maxReRank ngCand dates = 250,
      maxTopT etsPerCluster = DefaultMaxTopT etsPerCluster,
      maxScanClusters = 50,
      m nT etCand dateAge = 0.seconds
    ),
    Embedd ngType.LogFavBasedUser nterestedLargestD mMaxpool ngAddressBookFrom  APE -> S mClustersANNConf g(
      maxT etCand dateAge = 1.days,
      m nScore = 0.0, // for tw stly cand dates. To spec fy a h g r threshold, use a post-f lter
      cand dateEmbedd ngType = Embedd ngType.LogFavBasedT et,
      enablePart alNormal zat on = true,
      enable avyRank ng = DefaultEnable avyRank ng,
      rank ngAlgor hm = Scor ngAlgor hm.Pa rEmbedd ngCos neS m lar y,
      maxReRank ngCand dates = 250,
      maxTopT etsPerCluster = DefaultMaxTopT etsPerCluster,
      maxScanClusters = 50,
      m nT etCand dateAge = 0.seconds
    ),
    Embedd ngType.LogFavBasedUser nterestedLouva nMaxpool ngAddressBookFrom  APE -> S mClustersANNConf g(
      maxT etCand dateAge = 1.days,
      m nScore = 0.0, // for tw stly cand dates. To spec fy a h g r threshold, use a post-f lter
      cand dateEmbedd ngType = Embedd ngType.LogFavBasedT et,
      enablePart alNormal zat on = true,
      enable avyRank ng = DefaultEnable avyRank ng,
      rank ngAlgor hm = Scor ngAlgor hm.Pa rEmbedd ngCos neS m lar y,
      maxReRank ngCand dates = 250,
      maxTopT etsPerCluster = DefaultMaxTopT etsPerCluster,
      maxScanClusters = 50,
      m nT etCand dateAge = 0.seconds
    ),
    Embedd ngType.LogFavBasedUser nterestedConnectedMaxpool ngAddressBookFrom  APE -> S mClustersANNConf g(
      maxT etCand dateAge = 1.days,
      m nScore = 0.0, // for tw stly cand dates. To spec fy a h g r threshold, use a post-f lter
      cand dateEmbedd ngType = Embedd ngType.LogFavBasedT et,
      enablePart alNormal zat on = true,
      enable avyRank ng = DefaultEnable avyRank ng,
      rank ngAlgor hm = Scor ngAlgor hm.Pa rEmbedd ngCos neS m lar y,
      maxReRank ngCand dates = 250,
      maxTopT etsPerCluster = DefaultMaxTopT etsPerCluster,
      maxScanClusters = 50,
      m nT etCand dateAge = 0.seconds
    ),
    Embedd ngType.RelaxedAggregatableLogFavBasedProducer -> S mClustersANNConf g(
      maxT etCand dateAge = 1.days,
      m nScore = 0.25, // for tw stly cand dates. To spec fy a h g r threshold, use a post-f lter
      cand dateEmbedd ngType = Embedd ngType.LogFavBasedT et,
      enablePart alNormal zat on = true,
      enable avyRank ng = DefaultEnable avyRank ng,
      rank ngAlgor hm = Scor ngAlgor hm.Pa rEmbedd ngCos neS m lar y,
      maxReRank ngCand dates = 250,
      maxTopT etsPerCluster = DefaultMaxTopT etsPerCluster,
      maxScanClusters = 50,
      m nT etCand dateAge = 0.seconds
    ),
    Embedd ngType.LogFavLongestL2Embedd ngT et -> S mClustersANNConf g(
      maxT etCand dateAge = 1.days,
      m nScore = 0.3, // for tw stly cand dates. To spec fy a h g r threshold, use a post-f lter
      cand dateEmbedd ngType = Embedd ngType.LogFavBasedT et,
      enablePart alNormal zat on = true,
      enable avyRank ng = DefaultEnable avyRank ng,
      rank ngAlgor hm = Scor ngAlgor hm.Pa rEmbedd ngCos neS m lar y,
      maxReRank ngCand dates = 400,
      maxTopT etsPerCluster = DefaultMaxTopT etsPerCluster,
      maxScanClusters = 50,
      m nT etCand dateAge = 0.seconds
    ),
    Embedd ngType.F lteredUser nterested nFromPE -> S mClustersANNConf g(
      maxT etCand dateAge = 1.days,
      m nScore = 0.7, // unused,  avy rank ng d sabled
      cand dateEmbedd ngType = Embedd ngType.LogFavBasedT et,
      enablePart alNormal zat on = false,
      enable avyRank ng = DefaultEnable avyRank ng,
      rank ngAlgor hm =
        Scor ngAlgor hm.Pa rEmbedd ngCos neS m lar y, // Unused,  avy rank ng d sabled
      maxReRank ngCand dates = 150, // unused,  avy rank ng d sabled
      maxTopT etsPerCluster = DefaultMaxTopT etsPerCluster,
      maxScanClusters = 50,
      m nT etCand dateAge = 0.seconds
    ),
    Embedd ngType.F lteredUser nterested n -> S mClustersANNConf g(
      maxT etCand dateAge = 1.days,
      m nScore = 0.7, // unused,  avy rank ng d sabled
      cand dateEmbedd ngType = Embedd ngType.LogFavBasedT et,
      enablePart alNormal zat on = false,
      enable avyRank ng = DefaultEnable avyRank ng,
      rank ngAlgor hm =
        Scor ngAlgor hm.Pa rEmbedd ngCos neS m lar y, // Unused,  avy rank ng d sabled
      maxReRank ngCand dates = 150, // unused,  avy rank ng d sabled
      maxTopT etsPerCluster = DefaultMaxTopT etsPerCluster,
      maxScanClusters = 50,
      m nT etCand dateAge = 0.seconds
    ),
    Embedd ngType.Unf lteredUser nterested n -> S mClustersANNConf g(
      maxT etCand dateAge = 1.days,
      m nScore = 0.0,
      cand dateEmbedd ngType = Embedd ngType.LogFavBasedT et,
      enablePart alNormal zat on = true,
      enable avyRank ng = DefaultEnable avyRank ng,
      rank ngAlgor hm = Scor ngAlgor hm.Pa rEmbedd ngLogCos neS m lar y,
      maxReRank ngCand dates = 400,
      maxTopT etsPerCluster = DefaultMaxTopT etsPerCluster,
      maxScanClusters = 50,
      m nT etCand dateAge = 0.seconds
    ),
    Embedd ngType.FollowBasedUser nterested nFromAPE -> S mClustersANNConf g(
      maxT etCand dateAge = 1.days,
      m nScore = 0.0,
      cand dateEmbedd ngType = Embedd ngType.LogFavBasedT et,
      enablePart alNormal zat on = true,
      enable avyRank ng = DefaultEnable avyRank ng,
      rank ngAlgor hm = Scor ngAlgor hm.Pa rEmbedd ngCos neS m lar y,
      maxReRank ngCand dates = 200,
      maxTopT etsPerCluster = DefaultMaxTopT etsPerCluster,
      maxScanClusters = 50,
      m nT etCand dateAge = 0.seconds
    ),
    Embedd ngType.LogFavBasedUser nterested nFromAPE -> S mClustersANNConf g(
      maxT etCand dateAge = 1.days,
      m nScore = 0.0,
      cand dateEmbedd ngType = Embedd ngType.LogFavBasedT et,
      enablePart alNormal zat on = true,
      enable avyRank ng = DefaultEnable avyRank ng,
      rank ngAlgor hm = Scor ngAlgor hm.Pa rEmbedd ngCos neS m lar y,
      maxReRank ngCand dates = 200,
      maxTopT etsPerCluster = DefaultMaxTopT etsPerCluster,
      maxScanClusters = 50,
      m nT etCand dateAge = 0.seconds
    ),
    Embedd ngType.FavTfgTop c -> S mClustersANNConf g(
      maxT etCand dateAge = 1.days,
      m nScore = 0.5,
      cand dateEmbedd ngType = Embedd ngType.LogFavBasedT et,
      enablePart alNormal zat on = true,
      enable avyRank ng = DefaultEnable avyRank ng,
      rank ngAlgor hm = Scor ngAlgor hm.Pa rEmbedd ngCos neS m lar y,
      maxReRank ngCand dates = 400,
      maxTopT etsPerCluster = DefaultMaxTopT etsPerCluster,
      maxScanClusters = 50,
      m nT etCand dateAge = 0.seconds
    ),
    Embedd ngType.LogFavBasedKgoApeTop c -> S mClustersANNConf g(
      maxT etCand dateAge = 1.days,
      m nScore = 0.5,
      cand dateEmbedd ngType = Embedd ngType.LogFavBasedT et,
      enablePart alNormal zat on = true,
      enable avyRank ng = DefaultEnable avyRank ng,
      rank ngAlgor hm = Scor ngAlgor hm.Pa rEmbedd ngCos neS m lar y,
      maxReRank ngCand dates = 400,
      maxTopT etsPerCluster = DefaultMaxTopT etsPerCluster,
      maxScanClusters = 50,
      m nT etCand dateAge = 0.seconds
    ),
    Embedd ngType.UserNext nterested n -> S mClustersANNConf g(
      maxT etCand dateAge = 1.days,
      m nScore = 0.0,
      cand dateEmbedd ngType = Embedd ngType.LogFavBasedT et,
      enablePart alNormal zat on = true,
      enable avyRank ng = DefaultEnable avyRank ng,
      rank ngAlgor hm = Scor ngAlgor hm.Pa rEmbedd ngCos neS m lar y,
      maxReRank ngCand dates = 200,
      maxTopT etsPerCluster = DefaultMaxTopT etsPerCluster,
      maxScanClusters = 50,
      m nT etCand dateAge = 0.seconds
    )
  )

  /**
   * Only cac  t  cand dates  f  's not Consu r-s ce. For example, T etS ce, ProducerS ce,
   * Top cS ce.   don't cac  consu r-s ces (e.g. User nterested n) s nce a cac d consu r
   * object  s go ng rarely h , s nce   can't be shared by mult ple users.
   */
  val Cac ableShortTTLEmbedd ngTypes: Set[Embedd ngType] =
    Set(
      Embedd ngType.FavBasedProducer,
      Embedd ngType.LogFavLongestL2Embedd ngT et,
    )

  val Cac ableLongTTLEmbedd ngTypes: Set[Embedd ngType] =
    Set(
      Embedd ngType.FavTfgTop c,
      Embedd ngType.LogFavBasedKgoApeTop c
    )
}
