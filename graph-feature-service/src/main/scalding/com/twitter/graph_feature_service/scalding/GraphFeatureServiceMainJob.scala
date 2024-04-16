package com.tw ter.graph_feature_serv ce.scald ng

 mport com.tw ter.b ject on. nject on
 mport com.tw ter.fr gate.common.constdb_ut l. nject ons
 mport com.tw ter.fr gate.common.constdb_ut l.Scald ngUt l
 mport com.tw ter.graph_feature_serv ce.common.Conf gs
 mport com.tw ter.graph_feature_serv ce.common.Conf gs._
 mport com.tw ter. nteract on_graph.sc o.agg_all. nteract onGraph toryAggregatedEdgeSnapshotScalaDataset
 mport com.tw ter. nteract on_graph.sc o.ml.scores.RealGraph nScoresScalaDataset
 mport com.tw ter. nteract on_graph.thr ftscala.FeatureNa 
 mport com.tw ter. nteract on_graph.thr ftscala.{EdgeFeature => TEdgeFeature}
 mport com.tw ter.pluck.s ce.user_aud s.UserAud F nalScalaDataset
 mport com.tw ter.scald ng.DateRange
 mport com.tw ter.scald ng.Days
 mport com.tw ter.scald ng.Execut on
 mport com.tw ter.scald ng.Stat
 mport com.tw ter.scald ng.Un que D
 mport com.tw ter.scald ng.typed.TypedP pe
 mport com.tw ter.scald ng_ nternal.dalv2.DAL
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.AllowCrossClusterSa DC
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal
 mport com.tw ter.ut l.T  
 mport com.tw ter.wtf.cand date.thr ftscala.Cand dateSeq
 mport java.n o.ByteBuffer
 mport java.ut l.T  Zone

tra  GraphFeatureServ ceMa nJob extends GraphFeatureServ ceBaseJob {

  // keep ng hdfsPath as a separate var able  n order to overr de    n un  tests
  protected val hdfsPath: Str ng = BaseHdfsPath

  protected def getShard dForUser(user d: Long):  nt = shardForUser(user d)

  protected  mpl c  val key nj:  nject on[Long, ByteBuffer] =  nject ons.long2Var nt

  protected  mpl c  val value nj:  nject on[Long, ByteBuffer] =  nject ons.long2ByteBuffer

  protected val bufferS ze:  nt = 1 << 26

  protected val maxNumKeys:  nt = 1 << 24

  protected val numReducers:  nt = NumGraphShards

  protected val outputStreamBufferS ze:  nt = 1 << 26

  protected f nal val shard ngByKey = { (k: Long, _: Long) =>
    getShard dForUser(k)
  }

  protected f nal val shard ngByValue = { (_: Long, v: Long) =>
    getShard dForUser(v)
  }

  pr vate def wr eGraphToDB(
    graph: TypedP pe[(Long, Long)],
    shard ngFunct on: (Long, Long) =>  nt,
    path: Str ng
  )(
     mpl c  dateRange: DateRange
  ): Execut on[TypedP pe[( nt, Un )]] = {
    Scald ngUt l
      .wr eConstDB[Long, Long](
        graph.w hDescr pt on(s"shard ng $path"),
        shard ngFunct on,
        shard d =>
          getT  dHdfsShardPath(
            shard d,
            getHdfsPath(path, So (hdfsPath)),
            T  .fromM ll seconds(dateRange.end.t  stamp)
          ),
         nt.MaxValue,
        bufferS ze,
        maxNumKeys,
        numReducers,
        outputStreamBufferS ze
      )(
        key nj,
        value nj,
        Order ng[(Long, Long)]
      )
      .forceToD skExecut on
  }

  def extractFeature(
    featureL st: Seq[TEdgeFeature],
    featureNa : FeatureNa 
  ): Opt on[Float] = {
    featureL st
      .f nd(_.na  == featureNa )
      .map(_.tss.ewma.toFloat)
      .f lter(_ > 0.0)
  }

  /**
   * Funct on to extract a subgraph (e.g., follow graph) from real graph and take top K by real graph
   *   ght.
   *
   * @param  nput  nput real graph
   * @param edgeF lter f lter funct on to only get t  edges needed (e.g., only follow edges)
   * @param counter counter
   * @return a subgroup that conta ns topK, e.g., follow graph for each user.
   */
  pr vate def getSubGraph(
     nput: TypedP pe[(Long, Long, EdgeFeature)],
    edgeF lter: EdgeFeature => Boolean,
    counter: Stat
  ): TypedP pe[(Long, Long)] = {
     nput
      .f lter(c => edgeF lter(c._3))
      .map {
        case (src d, dest d, features) =>
          (src d, (dest d, features.realGraphScore))
      }
      .group
      // auto reducer est mat on only allocates 15 reducers, so sett ng an expl c  number  re
      .w hReducers(2000)
      .sortedReverseTake(TopKRealGraph)(Order ng.by(_._2))
      .flatMap {
        case (src d, topKNe ghbors) =>
          counter. nc()
          topKNe ghbors.map {
            case (dest d, _) =>
              (src d, dest d)
          }
      }
  }

  def getMau ds()( mpl c  dateRange: DateRange, un que D: Un que D): TypedP pe[Long] = {
    val numMAUs = Stat("NUM_MAUS")
    val un queMAUs = Stat("UN QUE_MAUS")

    DAL
      .read(UserAud F nalScalaDataset)
      .w hRemoteReadPol cy(AllowCrossClusterSa DC)
      .toTypedP pe
      .collect {
        case user_aud   f user_aud . sVal d =>
          numMAUs. nc()
          user_aud .user d
      }
      .d st nct
      .map { u =>
        un queMAUs. nc()
        u
      }
  }

  def getRealGraphW hMAUOnly(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): TypedP pe[(Long, Long, EdgeFeature)] = {
    val numMAUs = Stat("NUM_MAUS")
    val un queMAUs = Stat("UN QUE_MAUS")

    val monthlyAct veUsers = DAL
      .read(UserAud F nalScalaDataset)
      .w hRemoteReadPol cy(AllowCrossClusterSa DC)
      .toTypedP pe
      .collect {
        case user_aud   f user_aud . sVal d =>
          numMAUs. nc()
          user_aud .user d
      }
      .d st nct
      .map { u =>
        un queMAUs. nc()
        u
      }
      .asKeys

    val realGraphAggregates = DAL
      .readMostRecentSnapshot(
         nteract onGraph toryAggregatedEdgeSnapshotScalaDataset,
        dateRange.emb ggen(Days(5)))
      .w hRemoteReadPol cy(AllowCrossClusterSa DC)
      .toTypedP pe
      .map { edge =>
        val featureL st = edge.features
        val edgeFeature = EdgeFeature(
          edge.  ght.getOrElse(0.0).toFloat,
          extractFeature(featureL st, FeatureNa .NumMutualFollows),
          extractFeature(featureL st, FeatureNa .NumFavor es),
          extractFeature(featureL st, FeatureNa .NumRet ets),
          extractFeature(featureL st, FeatureNa .Num nt ons)
        )
        (edge.s ce d, (edge.dest nat on d, edgeFeature))
      }
      .jo n(monthlyAct veUsers)
      .map {
        case (src d, ((dest d, feature), _)) =>
          (dest d, (src d, feature))
      }
      .jo n(monthlyAct veUsers)
      .map {
        case (dest d, ((src d, feature), _)) =>
          (src d, dest d, feature)
      }
    realGraphAggregates
  }

  def getTopKFollowGraph(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): TypedP pe[(Long, Long)] = {
    val followGraphMauStat = Stat("NumFollowEdges_MAU")
    val mau: TypedP pe[Long] = getMau ds()
    DAL
      .readMostRecentSnapshot(RealGraph nScoresScalaDataset, dateRange.emb ggen(Days(7)))
      .w hRemoteReadPol cy(AllowCrossClusterSa DC)
      .toTypedP pe
      .groupBy(_.key)
      .jo n(mau.asKeys)
      .w hDescr pt on("f lter ng src d by mau")
      .flatMap {
        case (_, (KeyVal(src d, Cand dateSeq(cand dates)), _)) =>
          followGraphMauStat. nc()
          val topK = cand dates.sortBy(-_.score).take(TopKRealGraph)
          topK.map { c => (src d, c.user d) }
      }
  }

  overr de def runOnDateRange(
    enableValueGraphs: Opt on[Boolean],
    enableKeyGraphs: Opt on[Boolean]
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): Execut on[Un ] = {

    val processValueGraphs = enableValueGraphs.getOrElse(Conf gs.EnableValueGraphs)
    val processKeyGraphs = enableKeyGraphs.getOrElse(Conf gs.EnableKeyGraphs)

     f (!processKeyGraphs && !processValueGraphs) {
      // Sk p t  batch job
      Execut on.un 
    } else {
      // val favor eGraphStat = Stat("NumFavor eEdges")
      // val ret etGraphStat = Stat("NumRet etEdges")
      // val  nt onGraphStat = Stat("Num nt onEdges")

      // val realGraphAggregates = getRealGraphW hMAUOnly

      val followGraph = getTopKFollowGraph
      // val mutualFollowGraph = followGraph.asKeys.jo n(followGraph.swap.asKeys).keys

      // val favor eGraph =
      //   getSubGraph(realGraphAggregates, _.favor eScore. sDef ned, favor eGraphStat)

      // val ret etGraph =
      //   getSubGraph(realGraphAggregates, _.ret etScore. sDef ned, ret etGraphStat)

      // val  nt onGraph =
      //   getSubGraph(realGraphAggregates, _. nt onScore. sDef ned,  nt onGraphStat)

      val wr eValDataSetExecut ons =  f (processValueGraphs) {
        Seq(
          (followGraph, shard ngByValue, FollowOutValPath),
          (followGraph.swap, shard ngByValue, Follow nValPath)
          // (mutualFollowGraph, shard ngByValue, MutualFollowValPath),
          // (favor eGraph, shard ngByValue, Favor eOutValPath),
          // (favor eGraph.swap, shard ngByValue, Favor e nValPath),
          // (ret etGraph, shard ngByValue, Ret etOutValPath),
          // (ret etGraph.swap, shard ngByValue, Ret et nValPath),
          // ( nt onGraph, shard ngByValue,  nt onOutValPath),
          // ( nt onGraph.swap, shard ngByValue,  nt on nValPath)
        )
      } else {
        Seq.empty
      }

      val wr eKeyDataSetExecut ons =  f (processKeyGraphs) {
        Seq(
          (followGraph, shard ngByKey, FollowOutKeyPath),
          (followGraph.swap, shard ngByKey, Follow nKeyPath)
          // (favor eGraph, shard ngByKey, Favor eOutKeyPath),
          // (favor eGraph.swap, shard ngByKey, Favor e nKeyPath),
          // (ret etGraph, shard ngByKey, Ret etOutKeyPath),
          // (ret etGraph.swap, shard ngByKey, Ret et nKeyPath),
          // ( nt onGraph, shard ngByKey,  nt onOutKeyPath),
          // ( nt onGraph.swap, shard ngByKey,  nt on nKeyPath),
          // (mutualFollowGraph, shard ngByKey, MutualFollowKeyPath)
        )
      } else {
        Seq.empty
      }

      Execut on
        .sequence((wr eValDataSetExecut ons ++ wr eKeyDataSetExecut ons).map {
          case (graph, shard ng thod, path) =>
            wr eGraphToDB(graph, shard ng thod, path)
        }).un 
    }
  }
}
