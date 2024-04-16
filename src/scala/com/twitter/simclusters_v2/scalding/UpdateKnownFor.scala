package com.tw ter.s mclusters_v2.scald ng

 mport com.tw ter.algeb rd.{Mono d, Sem group}
 mport com.tw ter.scald ng._

object UpdateKnownFor {

  /**
   * Conven ence datastructure that can summar ze key stats about a node's set of
   *  m d ate ne ghbors.
   *
   * @param nodeCount                          number of nodes
   * @param sumOfEdge  ghts                   sum of   ghts on edges  n t  ne ghborhood.
   * @param sumOf mbersh p  ghtedEdge  ghts sum of { edge   ght *  mbersh p   ght } for each node
   *                                            n t  ne ghborhood.  mbersh p   ght to what  s not
   *                                           spec f ed  n t  case class and  s  nstead part of t 
   *                                           context.
   * @param sumOf mbersh p  ghts             sum of  mbersh p   ght for each node  n t 
   *                                           ne ghborhood.  mbersh p   ght to what  s not
   *                                           spec f ed  n t  case class and  s  nstead part of
   *                                           t  context.
   */
  case class Ne ghborhood nformat on(
    nodeCount:  nt,
    sumOfEdge  ghts: Float,
    sumOf mbersh p  ghtedEdge  ghts: Float,
    sumOf mbersh p  ghts: Float)

  object Ne ghborhood nformat onMono d extends Mono d[Ne ghborhood nformat on] {
    overr de val zero: Ne ghborhood nformat on = Ne ghborhood nformat on(0, 0f, 0f, 0f)
    overr de def plus(l: Ne ghborhood nformat on, r: Ne ghborhood nformat on) =
      Ne ghborhood nformat on(
        l.nodeCount + r.nodeCount,
        l.sumOfEdge  ghts + r.sumOfEdge  ghts,
        l.sumOf mbersh p  ghtedEdge  ghts + r.sumOf mbersh p  ghtedEdge  ghts,
        l.sumOf mbersh p  ghts + r.sumOf mbersh p  ghts
      )
  }

  case class Node nformat on(
    or g nalClusters: L st[ nt],
    overallStats: Ne ghborhood nformat on,
    statsOfClusters nNe ghborhood: Map[ nt, Ne ghborhood nformat on])

  object Node nformat onSem group extends Sem group[Node nformat on] {
     mpl c  val ctsMono d: Mono d[Ne ghborhood nformat on] = Ne ghborhood nformat onMono d

    overr de def plus(l: Node nformat on, r: Node nformat on) =
      Node nformat on(
        l.or g nalClusters ++ r.or g nalClusters,
        ctsMono d.plus(l.overallStats, r.overallStats),
        Mono d
          .mapMono d[ nt, Ne ghborhood nformat on].plus(
            l.statsOfClusters nNe ghborhood,
            r.statsOfClusters nNe ghborhood)
      )
  }

  case class ClusterScoresForNode(
    sumScore gnor ng mbersh pScores: Double,
    rat oScore gnor ng mbersh pScores: Double,
    rat oScoreUs ng mbersh pScores: Double)

  /**
   * G ven a user and a cluster:
   * True pos  ve   ght = sum of edge   ghts to ne ghbors who belong to that cluster.
   * False negat ve   ght = sum of edge   ghts to ne ghbors who don’t belong to that cluster.
   * False pos  ve   ght = (number of users  n t  cluster who are not ne ghbors of t  node) * globalAvgEdge  ght
   *  mbersh p-  ghted true pos  ve   ght = for ne ghbors who are also  n t  cluster, sum of edge   ght t  s user  mbersh p score  n t  cluster.
   *  mbersh p-  ghted false negat ve   ght = for ne ghbors who are not  n t  cluster, sum of edge   ght t  s avg  mbersh p score across t  whole knownFor  nput.
   *  mbersh p-  ghted false pos  ve   ght = for users  n t  cluster who are not ne ghbors of t  node, avg global edge   ght t  s user  mbersh p score for t  cluster.
   *
   *  gnor ng  mbersh p scores, sum formula:
   * truePos  veWtFactor*(True pos  ve   ght) - false negat ve   ght - false pos  ve   ght
   *  gnor ng  mbersh p scores, rat o formula:
   * True pos  ve   ght / (true pos  ve   ght + false negat ve   ght + false pos  ve   ght)
   * Us ng  mbersh p scores
   *  mbersh p-  ghted true pos  ve   ght / ( mbersh p-  ghted true pos  ve   ght +  mbersh p-  ghted false negat ve   ght +  mbersh p-  ghted false pos  ve   ght)
   *
   * @param overallNe ghborhoodStats
   * @param statsForCluster
   * @param clusterS ze
   * @param sumOfCluster mbersh pScores
   * @param globalAvgEdge  ght
   * @param truePos  veWtFactor
   *
   * @return
   */
  def getScoresForCluster(
    overallNe ghborhoodStats: Ne ghborhood nformat on,
    statsForCluster: Ne ghborhood nformat on,
    clusterS ze:  nt,
    sumOfCluster mbersh pScores: Double,
    globalAvgEdge  ght: Double,
    truePos  veWtFactor: Double
  ): ClusterScoresForNode = {
    val truePos  veWt = statsForCluster.sumOfEdge  ghts
    val falseNegat veWt = overallNe ghborhoodStats.sumOfEdge  ghts - truePos  veWt
    val falsePos  veWt = (clusterS ze - statsForCluster.nodeCount) * globalAvgEdge  ght
    val  mbersh p  ghtedTruePos  veWt = statsForCluster.sumOf mbersh p  ghtedEdge  ghts
    val  mbersh p  ghtedFalseNegat veWt =
      overallNe ghborhoodStats.sumOf mbersh p  ghtedEdge  ghts -  mbersh p  ghtedTruePos  veWt
    val  mbersh p  ghtedFalsePos  veWt =
      (sumOfCluster mbersh pScores - statsForCluster.sumOf mbersh p  ghts) * globalAvgEdge  ght
    val sumScore =
      truePos  veWtFactor * statsForCluster.sumOfEdge  ghts - falseNegat veWt - falsePos  veWt
    val rat oScore = truePos  veWt / (truePos  veWt + falseNegat veWt + falsePos  veWt)
    val rat oUs ng mbersh ps =
       mbersh p  ghtedTruePos  veWt / ( mbersh p  ghtedTruePos  veWt +
         mbersh p  ghtedFalsePos  veWt +  mbersh p  ghtedFalseNegat veWt)
    ClusterScoresForNode(sumScore, rat oScore, rat oUs ng mbersh ps)
  }

  def p ckBestCluster(
    overallNe ghborhoodStats: Ne ghborhood nformat on,
    statsOfClusters nNe ghborhood: Map[ nt, Ne ghborhood nformat on],
    clusterOverallStatsMap: Map[ nt, Ne ghborhood nformat on],
    globalAvgEdge  ght: Double,
    truePos  veWtFactor: Double,
    clusterScoresToF nalScore: ClusterScoresForNode => Double,
    m nNe ghbors nCluster:  nt
  ): Opt on[( nt, Double)] = {
    val clusterToScores = statsOfClusters nNe ghborhood.toL st.flatMap {
      case (cluster d, stats nNe ghborhood) =>
        val clusterOverallStats = clusterOverallStatsMap(cluster d)
         f (stats nNe ghborhood.nodeCount >= m nNe ghbors nCluster) {
          So (
            (
              cluster d,
              clusterScoresToF nalScore(
                getScoresForCluster(
                  overallNe ghborhoodStats,
                  stats nNe ghborhood,
                  clusterOverallStats.nodeCount,
                  clusterOverallStats.sumOf mbersh p  ghts,
                  globalAvgEdge  ght,
                  truePos  veWtFactor
                )
              )
            )
          )
        } else {
          None
        }
    }
     f (clusterToScores.nonEmpty) {
      So (clusterToScores.maxBy(_._2))
    } else None
  }

  def updateGener c(
    graph: TypedP pe[(Long, Map[Long, Float])],
     nputUserToClusters: TypedP pe[(Long, Array[( nt, Float)])],
    clusterOverallStatsMap: Map[ nt, Ne ghborhood nformat on],
    m nNe ghbors nCluster:  nt,
    globalAvg  ght: Double,
    avg mbersh pScore: Double,
    truePos  veWtFactor: Double,
    clusterScoresToF nalScore: ClusterScoresForNode => Double
  )(
     mpl c  un q d: Un que D
  ): TypedP pe[(Long, Array[( nt, Float)])] = {
    val emptyToSo th ng = Stat("no_ass gn nt_to_so ")
    val so th ngToEmpty = Stat("so _ass gn nt_to_none")
    val emptyToEmpty = Stat("empty_to_empty")
    val sa Cluster = Stat("sa _cluster")
    val d ffCluster = Stat("d ff_cluster")
    val nodesW hSmallDegree = Stat("nodes_w h_degree_lt_" + m nNe ghbors nCluster)

    collect nformat onPerNode(graph,  nputUserToClusters, avg mbersh pScore)
      .mapValues {
        case Node nformat on(or g nalClusters, overallStats, statsOfClusters nNe ghborhood) =>
          val newClusterW hScoreOpt =  f (overallStats.nodeCount < m nNe ghbors nCluster) {
            nodesW hSmallDegree. nc()
            None
          } else {
            p ckBestCluster(
              overallStats,
              statsOfClusters nNe ghborhood,
              clusterOverallStatsMap,
              globalAvg  ght,
              truePos  veWtFactor,
              clusterScoresToF nalScore,
              m nNe ghbors nCluster
            )
          }
          newClusterW hScoreOpt match {
            case So ((newCluster d, score)) =>
               f (or g nalClusters. sEmpty) {
                emptyToSo th ng. nc()
              } else  f (or g nalClusters.conta ns(newCluster d)) {
                sa Cluster. nc()
              } else {
                d ffCluster. nc()
              }
              Array((newCluster d, score.toFloat))
            case None =>
               f (or g nalClusters. sEmpty) {
                emptyToEmpty. nc()
              } else {
                so th ngToEmpty. nc()
              }
              Array.empty[( nt, Float)]
          }
      }
  }

  /**
   * Assembles t   nformat on   need at a node  n order to dec de what t  new cluster should be.
   * So t   s w re   assemble what t  overall
   *
   * T  funct on  s w re all t  cruc al steps take place. F rst get t  cluster that each
   * node belongs to, and t n broadcast  nformat on about t  node and cluster  mbersh p to each
   * of  's ne ghbors. Now br ng toget r all records w h t  sa  node d as t  key and create
   * t  Node nformat on dataset.
   * @param graph sym tr c graph  .e.  f u  s  n v's adj l st, t n v  s  n u's adj l st.
   * @param userToClusters current knownFor.
   * @param avg mbersh pScore avg.  mbersh p score of a node  n t  knownFor  're updat ng.
   *                           Useful to deal w h nodes wh ch don't belong to any knownFor.
   * @return p pe w h node  nformat on for each node
   */
  def collect nformat onPerNode(
    graph: TypedP pe[(Long, Map[Long, Float])],
    userToClusters: TypedP pe[(Long, Array[( nt, Float)])],
    avg mbersh pScore: Double
  ): TypedP pe[(Long, Node nformat on)] = {
     mpl c  val n sg: Sem group[Node nformat on] = Node nformat onSem group
    graph
      .leftJo n(userToClusters)
      // uncom nt for adhoc job
      //.w hReducers(200)
      .flatMap {
        case (node d, (adjL st, ass gnedClustersOpt)) =>
          val ass gnedClusters =
            ass gnedClustersOpt.map(_.toL st).getOrElse(N l)
          val res = adjL st.toL st.flatMap {
            case (ne ghbor d, ne ghbor  ght) =>
               f (ass gnedClusters.nonEmpty) {
                ass gnedClusters.map {
                  case (cluster d,  mbersh pScore) =>
                    val ne ghborhood nformat onForCluster = Ne ghborhood nformat on(
                      1,
                      ne ghbor  ght,
                       mbersh pScore * ne ghbor  ght,
                       mbersh pScore)
                    val or g nalClusters =
                       f (ne ghbor d == node d) L st(cluster d)
                      else L st.empty[ nt]
                    (
                      ne ghbor d,
                      Node nformat on(
                        or g nalClusters,
                        ne ghborhood nformat onForCluster,
                        Map(cluster d -> ne ghborhood nformat onForCluster)))
                }
              } else {
                L st(
                  (
                    ne ghbor d,
                    Node nformat on(
                      N l,
                      Ne ghborhood nformat on(
                        1,
                        ne ghbor  ght,
                        (avg mbersh pScore * ne ghbor  ght).toFloat,
                        avg mbersh pScore.toFloat),
                      Map.empty[ nt, Ne ghborhood nformat on]
                    )))
              }
          }
          res
      }
      .sumByKey
    // uncom nt for adhoc job
    //.w hReducers(100)
  }

  /**
   * Replace  ncom ng knownFor scores w h rat oScore gnor ng mbersh pScores
   * @param knownFor
   * @param s msGraphW houtSelfLoops
   * @param globalAvg  ght
   * @param clusterStats
   * @param avg mbersh pScore
   * @return
   */
  def newKnownForScores(
    knownFor: TypedP pe[(Long, Array[( nt, Float)])],
    s msGraphW houtSelfLoops: TypedP pe[(Long, Map[Long, Float])],
    globalAvg  ght: Double,
    clusterStats: Map[ nt, Ne ghborhood nformat on],
    avg mbersh pScore: Double
  ): TypedP pe[(Long, Array[( nt, Float)])] = {
    collect nformat onPerNode(s msGraphW houtSelfLoops, knownFor, avg mbersh pScore)
      .mapValues {
        case Node nformat on(or g nalClusters, overallStats, statsOfClusters nNe ghborhood) =>
          or g nalClusters.map { cluster d =>
            (
              cluster d,
              getScoresForCluster(
                overallStats,
                statsOfClusters nNe ghborhood(cluster d),
                clusterStats(cluster d).nodeCount,
                clusterStats(cluster d).sumOf mbersh p  ghts,
                globalAvg  ght,
                0
              ).rat oScore gnor ng mbersh pScores.toFloat)
          }.toArray
      }
  }
}
