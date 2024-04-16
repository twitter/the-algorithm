package com.tw ter.s mclusters_v2.common.cluster ng

 mport com.tw ter.sbf.graph.ConnectedComponents
 mport com.tw ter.sbf.graph.Graph
 mport com.tw ter.ut l.Stopwatch
 mport  .un m .ds .fastut l. nts. ntSet
 mport scala.collect on.SortedMap
 mport scala.jdk.Collect onConverters._

/**
 * Aggregate ent  es  nto clusters such that a cluster conta ns all embedd ngs w h a s m lar y
 * above a conf gurable threshold to any ot r embedd ng.
 *
 * @param s m lar yThreshold: W n bu ld ng t  edges bet en ent  es, edges w h   ght
 * less than or equal to t  threshold w ll be f ltered out.
 */
class ConnectedComponentsCluster ng thod(
  s m lar yThreshold: Double)
    extends Cluster ng thod {

   mport Cluster ngStat st cs._

  def cluster[T](
    embedd ngs: Map[Long, T],
    s m lar yFn: (T, T) => Double,
    recordStatCallback: (Str ng, Long) => Un  = (_, _) => ()
  ): Set[Set[Long]] = {

    val t  S nceGraphBu ldStart = Stopwatch.start()
    // com.tw ter.sbf.graph.Graph expects ne ghbors to be sorted  n ascend ng order.
    val s cesBy d = SortedMap(embedd ngs.z pW h ndex.map {
      case (s ce,  dx) =>  dx -> s ce
    }.toSeq: _*)

    val ne ghb s = s cesBy d.map {
      case (src dx, (_, src)) =>
        s cesBy d
          .collect {
            case (dst dx, (_, dst))  f src dx != dst dx => // avo d self-edges
              val s m lar y = s m lar yFn(src, dst)
              recordStatCallback(
                StatComputedS m lar yBeforeF lter,
                (s m lar y * 100).toLong // preserve up to two dec mal po nts
              )
               f (s m lar y > s m lar yThreshold)
                So (dst dx)
              else None
          }.flatten.toArray
    }.toArray

    recordStatCallback(StatS m lar yGraphTotalBu ldT  , t  S nceGraphBu ldStart(). nM ll seconds)

    val t  S nceCluster ngAlgRunStart = Stopwatch.start()
    val nEdges = ne ghb s.map(_.length).sum / 2 // Graph expects count of und rected edges
    val graph = new Graph(s cesBy d.s ze, nEdges, ne ghb s)

    val clusters = ConnectedComponents
      .connectedComponents(graph).asScala.toSet
      .map {  :  ntSet =>  .asScala.map(s cesBy d(_)._1).toSet }

    recordStatCallback(
      StatCluster ngAlgor hmRunT  ,
      t  S nceCluster ngAlgRunStart(). nM ll seconds)

    clusters
  }
}
