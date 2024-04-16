package com.tw ter.s mclusters_v2.common.cluster ng

/**
 * Part  ons a set of ent  es  nto clusters.
 * NOTE: T  select on/construct on of t  cluster representat ves (e.g.  do d, random, average)  s  mple nted  n ClusterRepresentat veSelect on thod.scala
 */
tra  Cluster ng thod {

  /**
   * T  ma n external-fac ng  thod. Sub-classes should  mple nt t   thod.
   *
   * @param embedd ngs map of ent y  Ds and correspond ng embedd ngs
   * @param s m lar yFn funct on that outputs s m lar y (>=0, t  larger, more s m lar), g ven two embedd ngs
   * @tparam T embedd ng type. e.g. S mClustersEmbedd ng
   *
   * @return A set of sets of ent y  Ds, each set represent ng a d st nct cluster.
   */
  def cluster[T](
    embedd ngs: Map[Long, T],
    s m lar yFn: (T, T) => Double,
    recordStatCallback: (Str ng, Long) => Un  = (_, _) => ()
  ): Set[Set[Long]]

}

object Cluster ngStat st cs {

  // Stat st cs, to be  mported w re recorded.
  val StatS m lar yGraphTotalBu ldT   = "s m lar y_graph_total_bu ld_t  _ms"
  val StatCluster ngAlgor hmRunT   = "cluster ng_algor hm_total_run_t  _ms"
  val Stat do dSelect onT   = " do d_select on_total_t  _ms"
  val StatComputedS m lar yBeforeF lter = "computed_s m lar y_before_f lter"

}
