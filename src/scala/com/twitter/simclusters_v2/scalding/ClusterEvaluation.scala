package com.tw ter.s mclusters_v2.scald ng

 mport com.tw ter.algeb rd.Mono d
 mport com.tw ter.algeb rd.mutable.Pr or yQueueMono d
 mport com.tw ter.dal.cl ent.dataset.KeyValDALDataset
 mport com.tw ter.pluck.s ce.cassowary.Follow ngsCos neS m lar  esManhattanS ce
 mport com.tw ter.scald ng._
 mport com.tw ter.scald ng_ nternal.dalv2.DAL
 mport com.tw ter.scald ng_ nternal.job.Tw terExecut onApp
 mport com.tw ter.scald ng_ nternal.job.analyt cs_batch._
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal
 mport com.tw ter.s mclusters_v2.common.ModelVers ons
 mport com.tw ter.s mclusters_v2.hdfs_s ces._
 mport com.tw ter.s mclusters_v2.scald ng.common.TypedR chP pe._
 mport com.tw ter.s mclusters_v2.scald ng.common.Ut l
 mport com.tw ter.s mclusters_v2.scald ng.common.Ut l.D str but on
 mport com.tw ter.s mclusters_v2.thr ftscala.ClusterQual y
 mport com.tw ter.s mclusters_v2.thr ftscala.ClustersUser sKnownFor
 mport com.tw ter.users ce.snapshot.flat.Users ceFlatScalaDataset
 mport java.ut l.Pr or yQueue
 mport scala.collect on.JavaConverters._

object ClusterEvaluat on {

  val samplerMono d: Pr or yQueueMono d[((Long, Long), (Double, Double))] =
    Ut l.reservo rSamplerMono dForPa rs[(Long, Long), (Double, Double)](5000)(Ut l.edgeOrder ng)

  case class ClusterResults(
    numEdges ns deCluster:  nt,
    wtOfEdges ns deCluster: Double,
    numEdgesOuts deCluster:  nt,
    wtOfEdgesOuts deCluster: Double,
    or g nalWtAndProductOfNodeScoresSample: Pr or yQueue[((Long, Long), (Double, Double))]) {
    def clusterQual y(clusterS ze:  nt, averagePrec s onWholeGraph: Double): ClusterQual y = {
      val un  ghtedRecallDenom nator = numEdges ns deCluster + numEdgesOuts deCluster
      val un  ghtedRecall =  f (un  ghtedRecallDenom nator > 0) {
        numEdges ns deCluster.toDouble / un  ghtedRecallDenom nator.toDouble
      } else 0.0

      val   ghtedRecallDenom nator = wtOfEdges ns deCluster + wtOfEdgesOuts deCluster
      val   ghtedRecall =  f (  ghtedRecallDenom nator > 0) {
        wtOfEdges ns deCluster /   ghtedRecallDenom nator
      } else 0.0

      val prec s on =  f (clusterS ze > 1) {
        So (wtOfEdges ns deCluster / (clusterS ze * (clusterS ze - 1)))
      } else So (0.0)

      val relat vePrec s on =  f (averagePrec s onWholeGraph > 0) {
        prec s on.flatMap { p => So (p / averagePrec s onWholeGraph) }
      } else So (0.0)

      ClusterQual y(
        un  ghtedRecall = So (un  ghtedRecall),
          ghtedRecall = So (  ghtedRecall),
        un  ghtedRecallDenom nator = So (un  ghtedRecallDenom nator),
          ghtedRecallDenom nator = So (  ghtedRecallDenom nator),
        relat vePrec s onNu rator = prec s on,
        relat vePrec s on = relat vePrec s on,
          ghtAndProductOfNodeScoresCorrelat on = So (
          Ut l.computeCorrelat on(
            or g nalWtAndProductOfNodeScoresSample. erator.asScala.map(_._2)))
      )
    }
  }

  object ClusterResultsMono d extends Mono d[ClusterResults] {
    overr de def zero = ClusterResults(0, 0, 0, 0, samplerMono d.zero)
    overr de def plus(l: ClusterResults, r: ClusterResults) = ClusterResults(
      l.numEdges ns deCluster + r.numEdges ns deCluster,
      l.wtOfEdges ns deCluster + r.wtOfEdges ns deCluster,
      l.numEdgesOuts deCluster + r.numEdgesOuts deCluster,
      l.wtOfEdgesOuts deCluster + r.wtOfEdgesOuts deCluster,
      samplerMono d
        .plus(l.or g nalWtAndProductOfNodeScoresSample, r.or g nalWtAndProductOfNodeScoresSample)
    )
  }

  /**
   * Evaluate t  qual y of a cluster.
   * @param  mberScores A map w h t   mbers of t  cluster as t  keys and t  r scores
   *                      ns de t  cluster as values. T  more central a  mber  s  ns de t  score,
   *                     t  h g r  's score  s.
   * @param  mbersAdjL sts A map that g ves t    ghted ne ghbors of each  mber  n t  cluster.
   */
  def evaluateCluster(
     mberScores: Map[Long, Double],
     mbersAdjL sts: Map[Long, Map[Long, Float]]
  ): ClusterResults = {
    val results er =  mbersAdjL sts.flatMap {
      case (fromNode d, adjL st) =>
        val fromNodeWt =  mberScores.getOrElse(fromNode d, 0.0)
        adjL st.map {
          case (toNode d, edgeWt) =>
             f ( mberScores.conta ns(toNode d)) {
              val productOf mbersh pScores = fromNodeWt *  mberScores(toNode d)
              ClusterResults(
                1,
                edgeWt,
                0,
                0,
                samplerMono d.bu ld(
                  ((fromNode d, toNode d), (edgeWt.toDouble, productOf mbersh pScores))))
            } else {
              ClusterResults(0, 0, 1, edgeWt, samplerMono d.zero)
            }
        }
    }
    Mono d.sum(results er)(ClusterResultsMono d)
  }

  /**
   * Evaluate each cluster w h respect to t  prov ded graph.
   * @param graph graph represented v a t  adjacency l sts of each node, needs to be sym tr zed  .e.  f u  s  n v's adjl st, t n v needs to be  n u's adjl st as  ll
   * @param clusters cluster  mbersh ps of each node.
   * @param statsPref x conven ence argu nt to act as pref x for stats counters
   * @return key-value p pe w h cluster d as key and (s ze of t  cluster, qual y struct) as value
   */
  def clusterLevelEvaluat on(
    graph: TypedP pe[(Long, Map[Long, Float])],
    clusters: TypedP pe[(Long, Array[( nt, Float)])],
    statsPref x: Str ng = ""
  )(
     mpl c  un que d: Un que D
  ): Execut on[TypedP pe[( nt, ( nt, ClusterQual y))]] = {
    val numRealClusters = Stat(s"${statsPref x}/numRealClusters")
    val numFakeClusters = Stat(s"${statsPref x}/numFakeClusters")

    val numNodesAndEdgesExec = graph
      .map {
        case (n d, nbrMap) =>
          (1L, nbrMap.s ze.toLong, nbrMap.values.sum.toDouble)
      }.sum.getExecut on

    numNodesAndEdgesExec.map {
      case (numNodes, numEdges, sumOfAllEdgeWts) =>
        pr ntln("numNodes " + numNodes)
        pr ntln("numEdges " + numEdges)
        pr ntln("sumOfAllEdgeWts " + sumOfAllEdgeWts)

        val numFakeClustersForUnass gnedNodes = numNodes / 1e4

        val averagePrec s onWholeGraph = sumOfAllEdgeWts / (numNodes * (numNodes - 1))
        graph
          .leftJo n(clusters)
          // uncom nt for adhoc job
          .w hReducers(200)
          .flatMap {
            case (node d, (adjL st, ass gnedClustersOpt)) =>
              val nodeDegree = adjL st.s ze.toLong
              val node  ghtedDegree = adjL st.values.sum
              ass gnedClustersOpt match {
                case So (ass gnedClusters)  f ass gnedClusters.nonEmpty =>
                  ass gnedClusters.toL st.map {
                    case (cluster d, scoreOfNode nCluster) =>
                      (
                        cluster d,
                        (
                          Map(node d -> (scoreOfNode nCluster.toDouble, adjL st)),
                          1,
                          nodeDegree,
                          node  ghtedDegree))
                  }
                case _ =>
                  // For nodes that don't belong to any cluster, create a fake cluster d (0 or lesser)
                  // and add t  node's stat st cs to that cluster d.   don't need t  adjacency l sts for
                  // unass gned nodes,  'll s mply track how many edges are  nc dent on those nodes and t  r   ghted sum etc
                  val fakeCluster d =
                    (-1 * (math.abs(
                      Ut l.hashToLong(node d)) % numFakeClustersForUnass gnedNodes)).to nt
                  L st(
                    (
                      fakeCluster d,
                      (
                        Map.empty[Long, (Double, Map[Long, Float])],
                        1,
                        nodeDegree,
                        node  ghtedDegree)))
              }
          }
          .sumByKey
          // uncom nt for adhoc job
          .w hReducers(60)
          .map {
            case (cluster d, ( mbersMap, clusterS ze, volu OfCluster,   ghtedVolu OfCluster)) =>
               f (cluster d > 0) {
                numRealClusters. nc()

                val scoresMap =
                   f (cluster d > 0)  mbersMap.mapValues(_._1) else Map.empty[Long, Double]
                val adjL stsMap =  mbersMap.mapValues(_._2)

                val qual y = evaluateCluster(scoresMap, adjL stsMap)
                  .clusterQual y(clusterS ze, averagePrec s onWholeGraph)

                (cluster d, (clusterS ze, qual y))
              } else {
                // cluster d <= 0  ans that t   s a fake cluster.
                numFakeClusters. nc()
                (
                  cluster d,
                  (
                    clusterS ze,
                    ClusterQual y(
                      un  ghtedRecallDenom nator = So (volu OfCluster),
                        ghtedRecallDenom nator = So (  ghtedVolu OfCluster)
                    )
                  )
                )
              }
          }
    }
  }

  case class OverallResults(
    un  ghtedRecall: Double,
    edges ns deClusters: Long,
    allEdges: Long,
    allNodes:  nt,
      ghtedRecall: Double,
    wtOnEdges ns deClusters: Double,
    wtOnAllEdges: Double,
      ghtCorrelat on: Double,
    relat vePrec s on: Double,
    numUnass gnedNodes:  nt,
    numAss gnedNodes:  nt,
    s zeD st: D str but on,
    recallD st: D str but on,
      ghtedRecallD st: D str but on,
    relat vePrec s onD st: D str but on,
      ghtCorrelat onD st: D str but on,
    numClustersW hNegat veCorrelat on: Double,
    numClustersW hZeroRecall: Double,
    numClustersW hLessThanOneRelat vePrec s on: Double,
    numS ngletonClusters:  nt)

  def summar zePerClusterResults(
    perClusterResults: TypedP pe[( nt, ( nt, ClusterQual y))]
  ): Execut on[Opt on[OverallResults]] = {
    perClusterResults
      .map {
        case (cluster d, (s ze, qual y)) =>
          val un  ghtedRecallDen = qual y.un  ghtedRecallDenom nator.getOrElse(0.0)
          val un  ghtedRecallNum = qual y.un  ghtedRecall.getOrElse(0.0) * un  ghtedRecallDen
          val   ghtedRecallDen = qual y.  ghtedRecallDenom nator.getOrElse(0.0)
          val   ghtedRecallNum = qual y.  ghtedRecall.getOrElse(0.0) *   ghtedRecallDen

          val   ghtCorrelat onDen = s ze
          val   ghtCorrelat onNum =
              ghtCorrelat onDen * qual y.  ghtAndProductOfNodeScoresCorrelat on
              .getOrElse(0.0)

          val relat vePrec s onDen = s ze
          val relat vePrec s onNum = relat vePrec s onDen * qual y.relat vePrec s on.getOrElse(0.0)

          val numClustersW hNegat veCorrelat on =
             f (  ghtCorrelat onNum < 0 && cluster d > 0) 1 else 0
          val numClustersW hLessThanOneRelat vePrec s on =
             f (qual y.relat vePrec s on.getOrElse(0.0) < 1 && cluster d > 0) 1 else 0
          val numClustersW hZeroRecall =  f (  ghtedRecallNum < 1e-5 && cluster d > 0) 1 else 0
          val numUnass gnedNodes =  f (cluster d < 1) s ze else 0
          val numAss gnedNodes =  f (cluster d > 0) s ze else 0
          val numS ngletonClusters =  f (cluster d > 0 && s ze == 1) 1 else 0

          (
            un  ghtedRecallDen,
            un  ghtedRecallNum,
              ghtedRecallDen,
              ghtedRecallNum,
              ghtCorrelat onDen,
              ghtCorrelat onNum,
            relat vePrec s onDen,
            relat vePrec s onNum,
            numClustersW hNegat veCorrelat on,
            numClustersW hLessThanOneRelat vePrec s on,
            numClustersW hZeroRecall,
            L st(s ze.toDouble),
            L st(qual y.un  ghtedRecall.getOrElse(0.0)),
            L st(qual y.  ghtedRecall.getOrElse(0.0)),
            L st(qual y.relat vePrec s on.getOrElse(0.0)),
            L st(qual y.  ghtAndProductOfNodeScoresCorrelat on.getOrElse(0.0)),
            numUnass gnedNodes,
            numAss gnedNodes,
            numS ngletonClusters
          )
      }
      .sum
      .toOpt onExecut on
      .map { opt =>
        opt.map {
          case (
                un  ghtedRecallDen,
                un  ghtedRecallNum,
                  ghtedRecallDen,
                  ghtedRecallNum,
                  ghtCorrelat onDen,
                  ghtCorrelat onNum,
                relat vePrec s onDen,
                relat vePrec s onNum,
                numClustersW hNegat veCorrelat on,
                numClustersW hLessThanOneRelat vePrec s on,
                numClustersW hZeroRecall,
                s zeL st,
                un  ghtedRecallL st,
                  ghtedRecallL st,
                relat vePrec s onL st,
                  ghtCorrelat onL st,
                numUnass gnedNodes,
                numAss gnedNodes,
                numS ngletonClusters) =>
            OverallResults(
              un  ghtedRecall = un  ghtedRecallNum / un  ghtedRecallDen,
              edges ns deClusters = un  ghtedRecallNum.toLong,
              allEdges = un  ghtedRecallDen.toLong,
              allNodes = numAss gnedNodes + numUnass gnedNodes,
                ghtedRecall =   ghtedRecallNum /   ghtedRecallDen,
              wtOnEdges ns deClusters =   ghtedRecallNum,
              wtOnAllEdges =   ghtedRecallDen,
                ghtCorrelat on =   ghtCorrelat onNum /   ghtCorrelat onDen,
              relat vePrec s on = relat vePrec s onNum / relat vePrec s onDen,
              numAss gnedNodes = numAss gnedNodes,
              numUnass gnedNodes = numUnass gnedNodes,
              s zeD st = Ut l.d str but onFromArray(s zeL st.toArray),
              recallD st = Ut l.d str but onFromArray(un  ghtedRecallL st.toArray),
                ghtedRecallD st = Ut l.d str but onFromArray(  ghtedRecallL st.toArray),
                ghtCorrelat onD st = Ut l.d str but onFromArray(  ghtCorrelat onL st.toArray),
              relat vePrec s onD st = Ut l.d str but onFromArray(relat vePrec s onL st.toArray),
              numClustersW hNegat veCorrelat on = numClustersW hNegat veCorrelat on,
              numClustersW hLessThanOneRelat vePrec s on =
                numClustersW hLessThanOneRelat vePrec s on,
              numClustersW hZeroRecall = numClustersW hZeroRecall,
              numS ngletonClusters = numS ngletonClusters
            )
        }
      }
  }

  /**
   * @param graph  nput s m lar y graph, needs to be sym tr zed  .e.  f u  s  n v's adjl st, t n v needs to be  n u's adjl st as  ll
   * @param clusters cluster ass gn nts to be evaluated
   * @return summary of results
   */
  def overallEvaluat on(
    graph: TypedP pe[(Long, Map[Long, Float])],
    clusters: TypedP pe[(Long, Array[( nt, Float)])],
    statsPref x: Str ng
  )(
     mpl c  un que d: Un que D
  ): Execut on[Opt on[OverallResults]] = {
    clusterLevelEvaluat on(graph, clusters, statsPref x).flatMap(summar zePerClusterResults)
  }
}

/**
 * ./bazel bundle src/scala/com/tw ter/s mclusters_v2/scald ng:cluster_evaluat on && \
 * oscar hdfs --user fr gate --host hadoopnest1.atla.tw ter.com --bundle cluster_evaluat on \
 * --tool com.tw ter.s mclusters_v2.scald ng.ClusterEvaluat onAdhoc --screen --screen-detac d \
 * --tee logs/clusterQual yFor_updatedUnnormal zed nputScores_us ngS ms20190318  -- \
 * --s ms nputD r /user/fr gate/y _ldap/commonD rForClusterEvaluat on/class f edS ms_20190314_cop edFromAtlaProc \
 * --topK 20000000 --date 2019-03-18 --m nAct veFollo rs 400 \
 * --topUsersD r /user/fr gate/y _ldap/commonD rForClusterEvaluat on/top20MUsers_m nAct veFollo rs400_20190215 \
 * --maxS msNe ghborsForEval 40 \
 * --preparedS msGraph /user/fr gate/y _ldap/commonD rForClusterEvaluat on/sym tr zed_class f edS ms20190318_top20MUsers \
 * --outputD r /user/fr gate/y _ldap/d rFor_updatedKnownFor20M_145K_dec11_us ngS ms20190127_unnormal zed nputScores/knownForClusterEvaluat on \
 * --knownForD r /user/fr gate/y _ldap/d rFor_updatedKnownFor20M_145K_dec11_us ngS ms20190127_unnormal zed nputScores/knownFor
 */
object ClusterEvaluat onAdhoc extends Tw terExecut onApp {
   mpl c  val tz: java.ut l.T  Zone = DateOps.UTC
   mpl c  val dp = DateParser.default

  def job: Execut on[Un ] =
    Execut on.getConf gMode.flatMap {
      case (conf g, mode) =>
        Execut on.w h d {  mpl c  un que d =>
          val args = conf g.getArgs
          val knownFor = args
            .opt onal("knownForD r").map { locat on =>
              KnownForS ces.readKnownFor(locat on)
            }.getOrElse(KnownForS ces.knownFor_20M_Dec11_145K)

          val m nAct veFollo rs = args. nt("m nAct veFollo rs", 400)
          val topK = args. nt("topK")
          val date = DateRange.parse(args("date"))

          val topUsersExec =
            TopUsersS m lar yGraph
              .topUsers(
                DAL.readMostRecentSnapshot(Users ceFlatScalaDataset, date).toTypedP pe,
                m nAct veFollo rs,
                topK
              )
              .map(_. d)
              .count("num_top_users")
              .make(TypedTsv(args("topUsersD r")))

          val s msGraphExec = topUsersExec.flatMap { topUsers =>
            TopUsersS m lar yGraph.makeGraph(
              TopUsersS m lar yGraph.getSubgraphFromUserGrouped nput(
                TypedP pe.from(WTFCand datesS ce(args("s ms nputD r"))),
                topUsers,
                args. nt("maxS msNe ghborsForEval", 40),
                degreeThresholdForStat = 5
              ),
              args("preparedS msGraph")
            )
          }

          val fullExec = s msGraphExec.flatMap { s ms =>
            ClusterEvaluat on
              .clusterLevelEvaluat on(s ms, knownFor, "eval")
              .flatMap { clusterResultsP pe =>
                val clusterResults = clusterResultsP pe.forceToD skExecut on
                val outputExec = clusterResults.flatMap { p pe =>
                  p pe
                    .map {
                      case (cluster d, (clusterS ze, qual y)) =>
                        "%d\t%d\t%.2g\t%.2g\t%.1f\t%.2g\t%.2f\t%.2g\t%.2g"
                          .format(
                            cluster d,
                            clusterS ze,
                            qual y.un  ghtedRecall.getOrElse(0.0),
                            qual y.  ghtedRecall.getOrElse(0.0),
                            qual y.un  ghtedRecallDenom nator.getOrElse(0.0),
                            qual y.  ghtedRecallDenom nator.getOrElse(0.0),
                            qual y.relat vePrec s on.getOrElse(0.0),
                            qual y.relat vePrec s onNu rator.getOrElse(0.0),
                            qual y.  ghtAndProductOfNodeScoresCorrelat on.getOrElse(0.0)
                          )
                    }.wr eExecut on(TypedTsv(args("outputD r")))
                }

                val pr ntExec = clusterResults.flatMap { p pe =>
                  ClusterEvaluat on.summar zePerClusterResults(p pe).map {
                    case So (res) =>
                      pr ntln("Overall results: " + Ut l.prettyJsonMapper.wr eValueAsStr ng(res))
                    case None =>
                      pr ntln("No overall results!!! Probably cluster results p pe  s empty.")
                  }
                }

                Execut on.z p(outputExec, pr ntExec)
              }
          }

          Ut l.pr ntCounters(fullExec)
        }
    }
}

tra  ClusterEvaluat onBatch extends Tw terSc duledExecut onApp {
   mpl c  val tz: java.ut l.T  Zone = DateOps.UTC
   mpl c  val dp = DateParser.default

  def f rstT  : Str ng

  def batchDescr pt on: Str ng

  def batch ncre nt: Durat on

  pr vate lazy val execArgs = Analyt csBatchExecut onArgs(
    batchDesc = BatchDescr pt on(batchDescr pt on),
    f rstT   = BatchF rstT  (R chDate(f rstT  )),
    lastT   = None,
    batch ncre nt = Batch ncre nt(batch ncre nt)
  )

  val ema lAddress: Str ng = "no-reply@tw ter.com"

  def knownForDALDataset: KeyValDALDataset[KeyVal[Long, ClustersUser sKnownFor]]

  def knownForModelVers on: Str ng

  def basel neKnownForDALDataset: KeyValDALDataset[KeyVal[Long, ClustersUser sKnownFor]]

  def basel neKnownForModelVers on: Str ng

  overr de def sc duledJob: Execut on[Un ] =
    Analyt csBatchExecut on(execArgs) {  mpl c  dateRange =>
      Execut on.w h d {  mpl c  un que d =>
        Execut on.w hArgs { args =>
          val basel neKnownFor =
            KnownForS ces.fromKeyVal(
              DAL
                .readMostRecentSnapshot(basel neKnownForDALDataset, dateRange.prepend(Days(7)))
                .toTypedP pe,
              basel neKnownForModelVers on
            )

          val knownFor =
            KnownForS ces.fromKeyVal(
              DAL
                .readMostRecentSnapshot(knownForDALDataset, dateRange.prepend(Days(7)))
                .toTypedP pe,
              knownForModelVers on
            )

          val  nputS msGraph = TypedP pe
            .from(Follow ngsCos neS m lar  esManhattanS ce())
            .map(_._2)

          val m nAct veFollo rs = args. nt("m nAct veFollo rs")
          val topK = args. nt("topK")
          val maxS msNe ghborsForEval =
            args. nt("maxS msNe ghborsForEval", 40)

          val topUsers = TopUsersS m lar yGraph
            .topUsers(
              DAL
                .readMostRecentSnapshot(Users ceFlatScalaDataset, dateRange)
                .toTypedP pe,
              m nAct veFollo rs,
              topK
            )
            .map(_. d)
            .count("num_top_users")

          TopUsersS m lar yGraph
            .getSubgraphFromUserGrouped nput(
              fullGraph =  nputS msGraph,
              usersTo nclude = topUsers,
              maxNe ghborsPerNode = maxS msNe ghborsForEval,
              degreeThresholdForStat = 2
            )
            .forceToD skExecut on
            .flatMap { sym tr zedS ms =>
              val basel neResultsExec = ClusterEvaluat on
                .overallEvaluat on(sym tr zedS ms, basel neKnownFor, "basel neKnownForEval")
              val newResultsExec = ClusterEvaluat on
                .overallEvaluat on(sym tr zedS ms, knownFor, "newKnownForEval")
              val m nS zeOfB ggerClusterForCompar son = 10
              val compareExec = CompareClusters.summar ze(
                CompareClusters.compare(
                  KnownForS ces.transpose(basel neKnownFor),
                  KnownForS ces.transpose(knownFor),
                  m nS zeOfB ggerCluster = m nS zeOfB ggerClusterForCompar son
                ))

              Execut on
                .z p(basel neResultsExec, newResultsExec, compareExec)
                .map {
                  case (oldResults, newResults, compareResults) =>
                    val ema lText =
                      s"Evaluat on Results for basel ne knownFor: $basel neKnownForModelVers on \n" +
                        Ut l.prettyJsonMapper.wr eValueAsStr ng(oldResults) +
                        "\n\n-------------------\n\n" +
                        s"Evaluat on Results for new knownFor:$knownForModelVers on\n" +
                        Ut l.prettyJsonMapper.wr eValueAsStr ng(newResults) +
                        "\n\n-------------------\n\n" +
                        s"Cos ne s m lar y d str but on bet en $basel neKnownForModelVers on and " +
                        s"$knownForModelVers on cluster  mbersh p vectors for " +
                        s"clusters w h at least $m nS zeOfB ggerClusterForCompar son  mbers:\n" +
                        Ut l.prettyJsonMapper
                          .wr eValueAsStr ng(compareResults)

                    Ut l
                      .sendEma l(
                        ema lText,
                        s"Evaluat on results compar ng $knownForModelVers on w h basel ne $basel neKnownForModelVers on",
                        ema lAddress)
                    ()
                }
            }
        }
      }
    }
}

/**
 * capesospy-v2 update --bu ld_locally --start_cron cluster_evaluat on_for_20M_145k \
 * src/scala/com/tw ter/s mclusters_v2/capesos_conf g/atla_proc.yaml
 */
object ClusterEvaluat onFor20M145K extends ClusterEvaluat onBatch {
  overr de val f rstT  : Str ng = "2019-06-11"

  overr de val batch ncre nt: Durat on = Days(7)

  overr de val batchDescr pt on = "com.tw ter.s mclusters_v2.scald ng.ClusterEvaluat onFor20M145K"

  overr de val knownForDALDataset = S mclustersV2KnownFor20M145KUpdatedScalaDataset

  overr de val knownForModelVers on = ModelVers ons.Model20M145KUpdated

  overr de val basel neKnownForDALDataset = S mclustersV2KnownFor20M145KDec11ScalaDataset

  overr de val basel neKnownForModelVers on = ModelVers ons.Model20M145KDec11
}

/**
 * capesospy-v2 update --bu ld_locally --start_cron cluster_evaluat on_for_20M_145k_2020 \
 * src/scala/com/tw ter/s mclusters_v2/capesos_conf g/atla_proc.yaml
 */
object ClusterEvaluat onFor20M145K2020 extends ClusterEvaluat onBatch {
  overr de val f rstT  : Str ng = "2021-01-25"

  overr de val batch ncre nt: Durat on = Days(7)

  overr de val batchDescr pt on =
    "com.tw ter.s mclusters_v2.scald ng.ClusterEvaluat onFor20M145K2020"

  overr de val knownForDALDataset = S mclustersV2KnownFor20M145K2020ScalaDataset

  overr de val knownForModelVers on = ModelVers ons.Model20M145K2020

  overr de val basel neKnownForDALDataset = S mclustersV2KnownFor20M145KUpdatedScalaDataset

  overr de val basel neKnownForModelVers on = ModelVers ons.Model20M145KUpdated
}
