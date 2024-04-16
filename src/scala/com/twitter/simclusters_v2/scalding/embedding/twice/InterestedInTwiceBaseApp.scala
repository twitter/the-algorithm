package com.tw ter.s mclusters_v2.scald ng.embedd ng.tw ce

 mport com.tw ter.b ject on. nject on
 mport com.tw ter.dal.cl ent.dataset.KeyValDALDataset
 mport com.tw ter.scald ng.DateRange
 mport com.tw ter.scald ng.Days
 mport com.tw ter.scald ng.Execut on
 mport com.tw ter.scald ng.Stat
 mport com.tw ter.scald ng.TypedTsv
 mport com.tw ter.scald ng.Un que D
 mport com.tw ter.scald ng.typed.TypedP pe
 mport com.tw ter.scald ng_ nternal.dalv2.DAL
 mport com.tw ter.scald ng_ nternal.dalv2.DALWr e._
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.AllowCrossDC
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal
 mport com.tw ter.s mclusters_v2.common.S mClustersEmbedd ng
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.s mclusters_v2.common.cluster ng.Cluster ng thod
 mport com.tw ter.s mclusters_v2.common.cluster ng.Cluster ngStat st cs._
 mport com.tw ter.s mclusters_v2.common.cluster ng.ClusterRepresentat veSelect on thod
 mport com.tw ter.s mclusters_v2.common.cluster ng.ClusterRepresentat veSelect onStat st cs._
 mport com.tw ter.s mclusters_v2.hdfs_s ces.ProducerEmbedd ngS ces
 mport com.tw ter.s mclusters_v2.hdfs_s ces.UserUserGraphScalaDataset
 mport com.tw ter.s mclusters_v2.scald ng.common.Ut l
 mport com.tw ter.s mclusters_v2.scald ng.embedd ng.common.Embedd ngUt l
 mport com.tw ter.s mclusters_v2.thr ftscala.Embedd ngType
 mport com.tw ter.s mclusters_v2.thr ftscala. nternal d
 mport com.tw ter.s mclusters_v2.thr ftscala.ModelVers on
 mport com.tw ter.s mclusters_v2.thr ftscala.Mult Embedd ngType
 mport com.tw ter.s mclusters_v2.thr ftscala.Ne ghborW h  ghts
 mport com.tw ter.s mclusters_v2.thr ftscala.OrderedClustersAnd mbers
 mport com.tw ter.s mclusters_v2.thr ftscala.Cluster mbers
 mport com.tw ter.s mclusters_v2.thr ftscala.S mClustersEmbedd ng dW hScore
 mport com.tw ter.s mclusters_v2.thr ftscala.S mClustersMult Embedd ng
 mport com.tw ter.s mclusters_v2.thr ftscala.S mClustersMult Embedd ng. ds
 mport com.tw ter.s mclusters_v2.thr ftscala.S mClustersMult Embedd ngBy ds
 mport com.tw ter.s mclusters_v2.thr ftscala.S mClustersMult Embedd ng d
 mport com.tw ter.s mclusters_v2.thr ftscala.UserAndNe ghbors
 mport com.tw ter.s mclusters_v2.thr ftscala.{
  S mClustersEmbedd ng d => S mClustersEmbedd ng dThr ft
}
 mport com.tw ter.ut l.Stopwatch
 mport java.ut l.T  Zone
 mport scala.ut l.Random.shuffle

/**
 * Base app for comput ng User  nterested n mult -embedd ng representat on.
 * TW CE: Captur ng users’ long-term  nterests us ng mult ple S mClusters embedd ngs.
 * T  job w ll
 * - Randomly select K follow/fav act ons for each user,
 * - cluster t  follow/fav act ons for each user,
 * - for each cluster, construct a representat on (e.g. average or  do d).
 *
 * @tparam T type of producer embedd ng. e.g. S mClustersEmbedd ng
 */
tra   nterested nTw ceBaseApp[T] {

   mport  nterested nTw ceBaseApp._

  def modelVers on: ModelVers on = ModelVers on.Model20m145k2020

  /**
   * funct on to output s m lar y (>=0, t  larger, more s m lar), g ven two producer embedd ngs.
   */
  def producerProducerS m lar yFnForCluster ng: (T, T) => Double
  def producerProducerS m lar yFnForClusterRepresentat ve: (T, T) => Double

  // Sort clusters by decreas ng s ze, fall back to ent y  D to break t e
  val clusterOrder ng: Order ng[Set[Long]] = math.Order ng.by(c => (-c.s ze, c.m n))

  /**
   * Read user-user graph.
   */
  def getUserUserGraph(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone
  ): TypedP pe[UserAndNe ghbors] = {
    DAL
      .readMostRecentSnapshot(
        UserUserGraphScalaDataset
      )
      .w hRemoteReadPol cy(AllowCrossDC)
      .toTypedP pe
  }

  /**
   * Randomly select up to maxNe ghborsByUser ne ghbors for each user.
   * Attempts to equally sample both follow and fav edges (e.g. maxNe ghborsByUser/2 for each).
   * Ho ver,  f one type of edge  s  nsuff c ent, backf ll w h ot r type up to maxNe ghborsByUser ne ghb s.
   * @param userUserGraph User-User follow/fav graph.
   * @param maxNe ghborsByUser How many ne ghbors to keep for each user.
   */
  def selectMaxProducersPerUser(
    userUserGraph: TypedP pe[UserAndNe ghbors],
    maxNe ghborsByUser:  nt = MaxNe ghborsByUser
  )(
     mpl c  un que D: Un que D
  ): TypedP pe[UserAndNe ghbors] = {

    val numOfFollowEdgesStat = Stat(StatNumOfFollowEdges)
    val numOfFavEdgesStat = Stat(StatNumOfFavEdges)
    val numOfEdgesCumulat veFrequencyBeforeF lter = Ut l.Cumulat veStat(
      StatCFNumProducersPerConsu rBeforeF lter,
      StatCFNumProducersPerConsu rBeforeF lterBuckets)

    userUserGraph.map { userAndNe ghbors: UserAndNe ghbors =>
      numOfEdgesCumulat veFrequencyBeforeF lter. ncForValue(userAndNe ghbors.ne ghbors.s ze)

      val (followEdges, favEdges) =
        userAndNe ghbors.ne ghbors.part  on(_. sFollo d.conta ns(true))
      val randomFollowEdges = shuffle(followEdges)
      val randomFavEdges = shuffle(favEdges)

      //  nterleave follow and fav edges, and select top k
      val  nterleavedTopKEdges: Seq[Ne ghborW h  ghts] = randomFollowEdges
        .map(So (_))
        .z pAll(
          randomFavEdges.map(So (_)),
          None,
          None
        ) // default None value w n one edge Seq  s shorter than anot r
        .flatMap {
          case (followEdgeOpt, favEdgeOpt) =>
            Seq(followEdgeOpt, favEdgeOpt)
        }.flatten
        .take(maxNe ghborsByUser)

      // edge stats
       nterleavedTopKEdges
        .foreach { edge =>
           f (edge. sFollo d.conta ns(true)) numOfFollowEdgesStat. nc()
          else numOfFavEdgesStat. nc()
        }

      userAndNe ghbors.copy(ne ghbors =  nterleavedTopKEdges)
    }
  }

  /**
   * Get mult  embedd ng for each user:
   * - For each user, jo n t  r follow / fav - based ne ghbors to producer embedd ngs,
   * - Group t se ne ghbors  nto clusters us ng t  spec f ed cluster ng thod,
   * - For each cluster, select t   do d as t  representat on.
   *
   * @param userUserGraph User-User follow/fav graph.
   * @param producerEmbedd ng producer embedd ng dataset. e.g. s mclusters embedd ngs, s mhash, etc.
   * @param cluster ng thod A  thod to group embedd ngs toget r.
   * @param maxClustersPerUser How many clusters to keep per user.
   * @param clusterRepresentat veSelect on thod A  thod to select a cluster representat ve.
   * @param numReducers How many reducers to use for sketch operat on.
   */
  def getMult Embedd ngPerUser(
    userUserGraph: TypedP pe[UserAndNe ghbors],
    producerEmbedd ng: TypedP pe[(User d, T)],
    cluster ng thod: Cluster ng thod,
    maxClustersPerUser:  nt = MaxClustersPerUser,
    clusterRepresentat veSelect on thod: ClusterRepresentat veSelect on thod[T],
    numReducers:  nt
  )(
     mpl c  un que D: Un que D
  ): TypedP pe[(User d, Seq[Set[User d]], S mClustersMult Embedd ng)] = {

    val truncatedUserUserGraph: TypedP pe[UserAndNe ghbors] = selectMaxProducersPerUser(
      userUserGraph)
    val val dEdges: TypedP pe[(User d, Ne ghborW h  ghts)] =
      truncatedUserUserGraph.flatMap {
        case UserAndNe ghbors(src d, ne ghborsW h  ghts) =>
          ne ghborsW h  ghts.map { ne ghborW h  ghts =>
            (
              ne ghborW h  ghts.ne ghbor d, // producer d
              ne ghborW h  ghts.copy(ne ghbor d = src d))
          }
      }

     mpl c  val l2b: User d => Array[Byte] =  nject on.long2B gEnd an

    val totalEdgesNonEmptyProducerEmbedd ngsStat = Stat(StatTotalEdgesNonEmptyProducerEmbedd ngs)
    val userClusterPa rsBeforeTruncat on = Stat(StatNumUserClusterPa rsBeforeTruncat on)
    val userClusterPa rsAfterTruncat on = Stat(StatNumUserClusterPa rsAfterTruncat on)
    val numUsers = Stat(StatNumUsers)
    val numOfClustersCumulat veFrequencyBeforeF lter =
      Ut l.Cumulat veStat(StatCFNumOfClustersBeforeF lter, StatCFNumOfClustersBeforeF lterBuckets)

    // map each cluster ng stat st c to a scald ng.Stat
    val cluster ngStatsMap: Map[Str ng, Stat] = Map(
      StatS m lar yGraphTotalBu ldT   -> Stat(StatS m lar yGraphTotalBu ldT  ),
      StatCluster ngAlgor hmRunT   -> Stat(StatCluster ngAlgor hmRunT  ),
      Stat do dSelect onT   -> Stat(Stat do dSelect onT  )
    )
    val cos neS m lar yCumulat veFrequencyBeforeF lter = Ut l.Cumulat veStat(
      StatCFCos neS m lar yBeforeF lter,
      StatCFCos neS m lar yBeforeF lterBuckets)

    val clusterRepresentat veSelect onT   = Stat(StatClusterRepresentat veSelect onT  )

    val dEdges
      .sketch(numReducers)
      .jo n(producerEmbedd ng)
      .map {
        case (producer d: User d, (srcW h  ghts: Ne ghborW h  ghts, embedd ng)) =>
          totalEdgesNonEmptyProducerEmbedd ngsStat. nc()
          (srcW h  ghts.ne ghbor d, (srcW h  ghts.copy(ne ghbor d = producer d), embedd ng))
      }
      .group
      .toL st
      .map {
        case (user d: User d, embedd ngs: Seq[(Ne ghborW h  ghts, T)]) =>
          numUsers. nc()
          val embedd ngsMap: Map[Long, T] = embedd ngs.map {
            case (n: Ne ghborW h  ghts, e) => (n.ne ghbor d, e)
          }.toMap
          val   ghtsMap: Map[Long, Ne ghborW h  ghts] = embedd ngs.map {
            case (n: Ne ghborW h  ghts, _) => (n.ne ghbor d, n)
          }.toMap
          // 1. Cluster embedd ngs
          val clusters: Set[Set[User d]] =
            cluster ng thod
              .cluster[T](
                embedd ngsMap,
                producerProducerS m lar yFnForCluster ng,
                // Map.get() returns an Opt on, so w ll not throw.
                // Use .foreach() to f lter out potent al Nones.
                (na ,  ncr) => {
                  cluster ngStatsMap.get(na ).foreach(ctr => ctr. ncBy( ncr))
                   f (na  == StatComputedS m lar yBeforeF lter)
                    cos neS m lar yCumulat veFrequencyBeforeF lter. ncForValue( ncr)
                }
              )

          // 2. Sort clusters
          val sortedClusters: Seq[Set[User d]] = clusters.toSeq.sorted(clusterOrder ng)

          // 3. Keep only a max number of clusters (avo d OOM)
          userClusterPa rsBeforeTruncat on. ncBy(sortedClusters.s ze)
          numOfClustersCumulat veFrequencyBeforeF lter. ncForValue(sortedClusters.s ze)
          val truncatedClusters = sortedClusters.take(maxClustersPerUser)
          userClusterPa rsAfterTruncat on. ncBy(truncatedClusters.s ze)

          // 4. Get l st of cluster representat ves
          val truncated dW hScoreL st: Seq[S mClustersEmbedd ng dW hScore] =
            truncatedClusters.map {  mbers: Set[User d] =>
              val clusterRepresentat onSelect onElapsed = Stopwatch.start()
              val  do d: User d = clusterRepresentat veSelect on thod.selectClusterRepresentat ve(
                 mbers.map( d =>   ghtsMap( d)),
                embedd ngsMap)
              clusterRepresentat veSelect onT  . ncBy(
                clusterRepresentat onSelect onElapsed(). nM ll seconds)

              S mClustersEmbedd ng dW hScore(
                 d = S mClustersEmbedd ng dThr ft(
                  Embedd ngType.Tw ceUser nterested n,
                  modelVers on,
                   nternal d.User d( do d)),
                score =  mbers.s ze)
            }

          (
            user d,
            sortedClusters,
            S mClustersMult Embedd ng. ds(
              S mClustersMult Embedd ngBy ds( ds = truncated dW hScoreL st)))
      }
  }

  /**
   * Wr e t  output to d sk as a TypedTsv.
   */
  def wr eOutputToTypedTSV(
    output: TypedP pe[(User d, Seq[Set[User d]], S mClustersMult Embedd ng)],
    userToClusterRepresentat ves ndexOutputPath: Str ng,
    userToCluster mbers ndexOutputPath: Str ng
  ): Execut on[(Un , Un )] = {

    // wr e t  user -> cluster representat ves  ndex
    val wr eClusterRepresentat ves = output
      .collect {
        case (user d: Long, _,  ds( ds)) => (user d,  ds. ds)
      }
      //.shard(part  ons = 1)
      .wr eExecut on(TypedTsv[(User d, Seq[S mClustersEmbedd ng dW hScore])](
        userToClusterRepresentat ves ndexOutputPath))

    // wr e t  user -> cluster  mbers  ndex
    val wr eCluster mbers = output
      .collect {
        case (user d: Long, clusters: Seq[Set[User d]], _) => (user d, clusters)
      }
      //.shard(part  ons = 1)
      .wr eExecut on(TypedTsv[(User d, Seq[Set[User d]])](userToCluster mbers ndexOutputPath))

    Execut on.z p(wr eClusterRepresentat ves, wr eCluster mbers)

  }

  /**
   * Wr e t  output to d sk as a KeyValDataset.
   */
  def wr eOutputToKeyValDataset(
    output: TypedP pe[(User d, Seq[Set[User d]], S mClustersMult Embedd ng)],
    embedd ngType: Mult Embedd ngType,
    userToClusterRepresentat ves ndexDataset: KeyValDALDataset[
      KeyVal[S mClustersMult Embedd ng d, S mClustersMult Embedd ng]
    ],
    userToCluster mbers ndexDataset: KeyValDALDataset[KeyVal[User d, OrderedClustersAnd mbers]],
    userToClusterRepresentat ves ndexOutputPath: Str ng,
    userToCluster mbers ndexOutputPath: Str ng
  )(
     mpl c  dateRange: DateRange
  ): Execut on[(Un , Un )] = {
    // wr e t  user -> cluster representat ves  ndex
    val wr eClusterRepresentat ves = output
      .map {
        case (user d: User d, _, embedd ngs: S mClustersMult Embedd ng) =>
          KeyVal(
            key = S mClustersMult Embedd ng d(
              embedd ngType = embedd ngType,
              modelVers on = modelVers on,
               nternal d =  nternal d.User d(user d)
            ),
            value = embedd ngs
          )
      }
      .wr eDALVers onedKeyValExecut on(
        userToClusterRepresentat ves ndexDataset,
        D.Suff x(userToClusterRepresentat ves ndexOutputPath),
        Expl c EndT  (dateRange.end)
      )

    // wr e t  user -> cluster  mbers  ndex
    val wr eCluster mbers = output
      .map {
        case (user d: User d, clusters: Seq[Set[User d]], _) =>
          KeyVal(
            key = user d,
            value = OrderedClustersAnd mbers(clusters, So (clusters.map(Cluster mbers(_)))))
      }
      .wr eDALVers onedKeyValExecut on(
        userToCluster mbers ndexDataset,
        D.Suff x(userToCluster mbers ndexOutputPath),
        Expl c EndT  (dateRange.end)
      )

    Execut on.z p(wr eClusterRepresentat ves, wr eCluster mbers)
  }

  /**
   * Ma n  thod for sc duled jobs.
   */
  def runSc duledApp(
    cluster ng thod: Cluster ng thod,
    clusterRepresentat veSelect on thod: ClusterRepresentat veSelect on thod[T],
    producerEmbedd ng: TypedP pe[(User d, T)],
    userToClusterRepresentat ves ndexPathSuff x: Str ng,
    userToCluster mbers ndexPathSuff x: Str ng,
    userToClusterRepresentat ves ndexDataset: KeyValDALDataset[
      KeyVal[S mClustersMult Embedd ng d, S mClustersMult Embedd ng]
    ],
    userToCluster mbers ndexDataset: KeyValDALDataset[KeyVal[User d, OrderedClustersAnd mbers]],
    numReducers:  nt
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que d: Un que D
  ): Execut on[Un ] = {

    val userToClusterRepresentat ves ndexOutputPath: Str ng = Embedd ngUt l.getHdfsPath(
       sAdhoc = false,
       sManhattanKeyVal = true,
      modelVers on = modelVers on,
      pathSuff x = userToClusterRepresentat ves ndexPathSuff x
    )

    val userToCluster mbers ndexOutputPath: Str ng = Embedd ngUt l.getHdfsPath(
       sAdhoc = false,
       sManhattanKeyVal = true,
      modelVers on = modelVers on,
      pathSuff x = userToCluster mbers ndexPathSuff x
    )

    val execut on = Execut on.w h d {  mpl c  un que d =>
      val output: TypedP pe[(User d, Seq[Set[User d]], S mClustersMult Embedd ng)] =
        getMult Embedd ngPerUser(
          userUserGraph = getUserUserGraph(dateRange.prepend(Days(30)),  mpl c ly),
          producerEmbedd ng = producerEmbedd ng,
          cluster ng thod = cluster ng thod,
          clusterRepresentat veSelect on thod = clusterRepresentat veSelect on thod,
          numReducers = numReducers
        )

      wr eOutputToKeyValDataset(
        output = output,
        embedd ngType = Mult Embedd ngType.Tw ceUser nterested n,
        userToClusterRepresentat ves ndexDataset = userToClusterRepresentat ves ndexDataset,
        userToCluster mbers ndexDataset = userToCluster mbers ndexDataset,
        userToClusterRepresentat ves ndexOutputPath = userToClusterRepresentat ves ndexOutputPath,
        userToCluster mbers ndexOutputPath = userToCluster mbers ndexOutputPath
      )

    }

    execut on.un 
  }

  /**
   * Ma n  thod for adhoc jobs.
   */
  def runAdhocApp(
    cluster ng thod: Cluster ng thod,
    clusterRepresentat veSelect on thod: ClusterRepresentat veSelect on thod[T],
    producerEmbedd ng: TypedP pe[(User d, T)],
    userToClusterRepresentat ves ndexPathSuff x: Str ng,
    userToCluster mbers ndexPathSuff x: Str ng,
    numReducers:  nt
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que d: Un que D
  ): Execut on[Un ] = {

    val userToClusterRepresentat ves ndexOutputPath: Str ng = Embedd ngUt l.getHdfsPath(
       sAdhoc = true,
       sManhattanKeyVal = false,
      modelVers on = modelVers on,
      pathSuff x = userToClusterRepresentat ves ndexPathSuff x
    )

    val userToCluster mbers ndexOutputPath: Str ng = Embedd ngUt l.getHdfsPath(
       sAdhoc = true,
       sManhattanKeyVal = false,
      modelVers on = modelVers on,
      pathSuff x = userToCluster mbers ndexPathSuff x
    )

    val execut on = Execut on.w h d {  mpl c  un que d =>
      val output: TypedP pe[(User d, Seq[Set[User d]], S mClustersMult Embedd ng)] =
        getMult Embedd ngPerUser(
          userUserGraph = getUserUserGraph(dateRange.prepend(Days(30)),  mpl c ly),
          producerEmbedd ng = producerEmbedd ng,
          cluster ng thod = cluster ng thod,
          clusterRepresentat veSelect on thod = clusterRepresentat veSelect on thod,
          numReducers = numReducers
        )

      wr eOutputToTypedTSV(
        output,
        userToClusterRepresentat ves ndexOutputPath,
        userToCluster mbers ndexOutputPath)
    }

    execut on.un 
  }

}

object  nterested nTw ceBaseApp {

  // Stat st cs
  val StatNumOfFollowEdges = "num_of_follow_edges"
  val StatNumOfFavEdges = "num_of_fav_edges"
  val StatTotalEdgesNonEmptyProducerEmbedd ngs = "total_edges_w h_non_empty_producer_embedd ngs"
  val StatNumUserClusterPa rsBeforeTruncat on = "num_user_cluster_pa rs_before_truncat on"
  val StatNumUserClusterPa rsAfterTruncat on = "num_user_cluster_pa rs_after_truncat on"
  val StatNumUsers = "num_users"
  // Cumulat ve Frequency
  val StatCFNumProducersPerConsu rBeforeF lter = "num_producers_per_consu r_cf_before_f lter"
  val StatCFNumProducersPerConsu rBeforeF lterBuckets: Seq[Double] =
    Seq(0, 10, 20, 50, 100, 500, 1000)
  val StatCFCos neS m lar yBeforeF lter = "cos ne_s m lar y_cf_before_f lter"
  val StatCFCos neS m lar yBeforeF lterBuckets: Seq[Double] =
    Seq(0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100)
  val StatCFNumOfClustersBeforeF lter = "num_of_clusters_cf_before_f lter"
  val StatCFNumOfClustersBeforeF lterBuckets: Seq[Double] =
    Seq(1, 3, 5, 10, 15, 20, 50, 100, 200, 300, 500)

  val MaxClustersPerUser:  nt = 10
  val MaxNe ghborsByUser:  nt = 500

  object ProducerEmbedd ngS ce {

    /**
     * Read log-fav based Aggregatable Producer embedd ngs dataset.
     */
    def getAggregatableProducerEmbedd ngs(
       mpl c  dateRange: DateRange,
      t  Zone: T  Zone
    ): TypedP pe[(User d, S mClustersEmbedd ng)] =
      ProducerEmbedd ngS ces
        .producerEmbedd ngS ce(
          Embedd ngType.AggregatableLogFavBasedProducer,
          ModelVers on.Model20m145k2020)(dateRange.prepend(Days(30)))
        .mapValues(s => S mClustersEmbedd ng(s))

  }

}
