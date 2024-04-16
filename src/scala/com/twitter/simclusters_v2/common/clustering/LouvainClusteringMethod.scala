package com.tw ter.s mclusters_v2.common.cluster ng

 mport com.tw ter.eventdetect on.common.louva n.Louva nDr ver
 mport com.tw ter.eventdetect on.common.louva n.NetworkFactory
 mport com.tw ter.eventdetect on.common.model.Ent y
 mport com.tw ter.eventdetect on.common.model.Network nput
 mport com.tw ter.eventdetect on.common.model.TextEnt yValue
 mport com.tw ter.ut l.Stopwatch
 mport scala.collect on.JavaConverters._
 mport scala.math.max

/**
 * Groups ent  es by t  Louva n cluster ng  thod.
 * @param s m lar yThreshold: W n bu ld ng t  edges bet en ent  es, edges w h   ght
 * less than or equal to t  threshold w ll be f ltered out.
 * @param appl edResolut onFactor:  f present, w ll be used to mult ply t  appl ed resolut on
 * para ter of t  Louva n  thod by t  factor.
 * Note that t  DEFAULT_MAX_RESOLUT ON w ll not be appl ed.
 */
class Louva nCluster ng thod(
  s m lar yThreshold: Double,
  appl edResolut onFactor: Opt on[Double])
    extends Cluster ng thod {

   mport Cluster ngStat st cs._

  def cluster[T](
    embedd ngs: Map[Long, T],
    s m lar yFn: (T, T) => Double,
    recordStatCallback: (Str ng, Long) => Un  = (_, _) => ()
  ): Set[Set[Long]] = {

    // 1. Bu ld t  graph on wh ch to run Louva n:
    //   -   gh edges by t  s m lar y bet en t  2 embedd ngs,
    //   - F lter out edges w h   ght <= threshold.
    val t  S nceGraphBu ldStart = Stopwatch.start()
    val edges: Seq[((Long, Long), Double)] = embedd ngs.toSeq
      .comb nat ons(2)
      .map { pa r: Seq[(Long, T)] => // pa r of 2
        val (user1, embedd ng1) = pa r. ad
        val (user2, embedd ng2) = pa r(1)
        val s m lar y = s m lar yFn(embedd ng1, embedd ng2)

        recordStatCallback(
          StatComputedS m lar yBeforeF lter,
          (s m lar y * 100).toLong // preserve up to two dec mal places
        )

        ((user1, user2), s m lar y)
      }
      .f lter(_._2 > s m lar yThreshold)
      .toSeq

    recordStatCallback(StatS m lar yGraphTotalBu ldT  , t  S nceGraphBu ldStart(). nM ll seconds)

    // c ck  f so  ent  es do not have any  ncom ng / outgo ng edge
    // t se are s ze-1 clusters ( .e. t  r own)
    val  nd v dualClusters: Set[Long] = embedd ngs.keySet -- edges.flatMap {
      case ((user1, user2), _) => Set(user1, user2)
    }.toSet

    // 2. Louva nDr ver uses "Ent y" as  nput, so bu ld 2 mapp ngs
    // - Long (ent y  d) -> Ent y
    // - Ent y -> Long (ent y  d)
    val embedd ng dToEnt y: Map[Long, Ent y] = embedd ngs.map {
      case ( d, _) =>  d -> Ent y(TextEnt yValue( d.toStr ng, So ( d.toStr ng)), None)
    }
    val ent yToEmbedd ng d: Map[Ent y, Long] = embedd ng dToEnt y.map {
      case ( d, e) => e ->  d
    }

    // 3. Create t  l st of Network nput on wh ch to run Louva nDr ver
    val network nputL st = edges
      .map {
        case ((fromUser d: Long, toUser d: Long),   ght: Double) =>
          new Network nput(embedd ng dToEnt y(fromUser d), embedd ng dToEnt y(toUser d),   ght)
      }.toL st.asJava

    val t  S nceCluster ngAlgRunStart = Stopwatch.start()
    val networkD ct onary = NetworkFactory.bu ldD ct onary(network nputL st)
    val network = NetworkFactory.bu ldNetwork(network nputL st, networkD ct onary)

     f (network nputL st.s ze() == 0) {
      // handle case  f no edge at all (only one ent y or all ent  es are too far apart)
      embedd ngs.keySet.map(e => Set(e))
    } else {
      // 4. Run cluster ng algor hm
      val clustered ds = appl edResolut onFactor match {
        case So (res) =>
          Louva nDr ver.clusterAppl edResolut onFactor(network, networkD ct onary, res)
        case None => Louva nDr ver.cluster(network, networkD ct onary)
      }

      recordStatCallback(
        StatCluster ngAlgor hmRunT  ,
        t  S nceCluster ngAlgRunStart(). nM ll seconds)

      // 5. Post-process ng
      val atLeast2 mbersClusters: Set[Set[Long]] = clustered ds.asScala
        .groupBy(_._2)
        .mapValues(_.map { case (e, _) => ent yToEmbedd ng d(e) }.toSet)
        .values.toSet

      atLeast2 mbersClusters ++  nd v dualClusters.map { e => Set(e) }

    }
  }

  def clusterW hS lhouette[T](
    embedd ngs: Map[Long, T],
    s m lar yFn: (T, T) => Double,
    s m lar yFnForS l: (T, T) => Double,
    recordStatCallback: (Str ng, Long) => Un  = (_, _) => ()
  ): (Set[Set[Long]], Set[Set[(Long, Double)]]) = {

    // 1. Bu ld t  graph on wh ch to run Louva n:
    //   -   gh edges by t  s m lar y bet en t  2 embedd ngs,
    //   - F lter out edges w h   ght <= threshold.
    val t  S nceGraphBu ldStart = Stopwatch.start()
    val edgesS m lar yMap = collect on.mutable.Map[(Long, Long), Double]()

    val edges: Seq[((Long, Long), Double)] = embedd ngs.toSeq
      .comb nat ons(2)
      .map { pa r: Seq[(Long, T)] => // pa r of 2
        val (user1, embedd ng1) = pa r. ad
        val (user2, embedd ng2) = pa r(1)
        val s m lar y = s m lar yFn(embedd ng1, embedd ng2)
        val s m lar yForS l = s m lar yFnForS l(embedd ng1, embedd ng2)
        edgesS m lar yMap.put((user1, user2), s m lar yForS l)
        edgesS m lar yMap.put((user2, user1), s m lar yForS l)

        recordStatCallback(
          StatComputedS m lar yBeforeF lter,
          (s m lar y * 100).toLong // preserve up to two dec mal places
        )

        ((user1, user2), s m lar y)
      }
      .f lter(_._2 > s m lar yThreshold)
      .toSeq

    recordStatCallback(StatS m lar yGraphTotalBu ldT  , t  S nceGraphBu ldStart(). nM ll seconds)

    // c ck  f so  ent  es do not have any  ncom ng / outgo ng edge
    // t se are s ze-1 clusters ( .e. t  r own)
    val  nd v dualClusters: Set[Long] = embedd ngs.keySet -- edges.flatMap {
      case ((user1, user2), _) => Set(user1, user2)
    }.toSet

    // 2. Louva nDr ver uses "Ent y" as  nput, so bu ld 2 mapp ngs
    // - Long (ent y  d) -> Ent y
    // - Ent y -> Long (ent y  d)
    val embedd ng dToEnt y: Map[Long, Ent y] = embedd ngs.map {
      case ( d, _) =>  d -> Ent y(TextEnt yValue( d.toStr ng, So ( d.toStr ng)), None)
    }
    val ent yToEmbedd ng d: Map[Ent y, Long] = embedd ng dToEnt y.map {
      case ( d, e) => e ->  d
    }

    // 3. Create t  l st of Network nput on wh ch to run Louva nDr ver
    val network nputL st = edges
      .map {
        case ((fromUser d: Long, toUser d: Long),   ght: Double) =>
          new Network nput(embedd ng dToEnt y(fromUser d), embedd ng dToEnt y(toUser d),   ght)
      }.toL st.asJava

    val t  S nceCluster ngAlgRunStart = Stopwatch.start()
    val networkD ct onary = NetworkFactory.bu ldD ct onary(network nputL st)
    val network = NetworkFactory.bu ldNetwork(network nputL st, networkD ct onary)

    val clusters =  f (network nputL st.s ze() == 0) {
      // handle case  f no edge at all (only one ent y or all ent  es are too far apart)
      embedd ngs.keySet.map(e => Set(e))
    } else {
      // 4. Run cluster ng algor hm
      val clustered ds = appl edResolut onFactor match {
        case So (res) =>
          Louva nDr ver.clusterAppl edResolut onFactor(network, networkD ct onary, res)
        case None => Louva nDr ver.cluster(network, networkD ct onary)
      }

      recordStatCallback(
        StatCluster ngAlgor hmRunT  ,
        t  S nceCluster ngAlgRunStart(). nM ll seconds)

      // 5. Post-process ng
      val atLeast2 mbersClusters: Set[Set[Long]] = clustered ds.asScala
        .groupBy(_._2)
        .mapValues(_.map { case (e, _) => ent yToEmbedd ng d(e) }.toSet)
        .values.toSet

      atLeast2 mbersClusters ++  nd v dualClusters.map { e => Set(e) }

    }

    // Calculate s lhouette  tr cs
    val contact dW hS lhouette = clusters.map {
      case cluster =>
        val ot rClusters = clusters - cluster

        cluster.map {
          case contact d =>
             f (ot rClusters. sEmpty) {
              (contact d, 0.0)
            } else {
              val ot rSa ClusterContacts = cluster - contact d

               f (ot rSa ClusterContacts. sEmpty) {
                (contact d, 0.0)
              } else {
                // calculate s m lar y of g ven user d w h all ot r users  n t  sa  cluster
                val a_  = ot rSa ClusterContacts.map {
                  case sa ClusterContact =>
                    edgesS m lar yMap((contact d, sa ClusterContact))
                }.sum / ot rSa ClusterContacts.s ze

                // calculate s m lar y of g ven user d to all ot r clusters, f nd t  best nearest cluster
                val b_  = ot rClusters.map {
                  case ot rCluster =>
                    ot rCluster.map {
                      case ot rClusterContact =>
                        edgesS m lar yMap((contact d, ot rClusterContact))
                    }.sum / ot rCluster.s ze
                }.max

                // s lhouette (value) of one user d  
                val s_  = (a_  - b_ ) / max(a_ , b_ )
                (contact d, s_ )
              }
            }
        }
    }

    (clusters, contact dW hS lhouette)
  }
}
