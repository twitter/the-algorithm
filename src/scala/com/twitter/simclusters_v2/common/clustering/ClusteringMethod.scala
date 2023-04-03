packagelon com.twittelonr.simclustelonrs_v2.common.clustelonring

/**
 * Partitions a selont of elonntitielons into clustelonrs.
 * NOTelon: Thelon selonlelonction/construction of thelon clustelonr relonprelonselonntativelons (elon.g. melondoid, random, avelonragelon) is implelonmelonntelond in ClustelonrRelonprelonselonntativelonSelonlelonctionMelonthod.scala
 */
trait ClustelonringMelonthod {

  /**
   * Thelon main elonxtelonrnal-facing melonthod. Sub-classelons should implelonmelonnt this melonthod.
   *
   * @param elonmbelonddings map of elonntity IDs and correlonsponding elonmbelonddings
   * @param similarityFn function that outputs similarity (>=0, thelon largelonr, morelon similar), givelonn two elonmbelonddings
   * @tparam T elonmbelondding typelon. elon.g. SimClustelonrselonmbelondding
   *
   * @relonturn A selont of selonts of elonntity IDs, elonach selont relonprelonselonnting a distinct clustelonr.
   */
  delonf clustelonr[T](
    elonmbelonddings: Map[Long, T],
    similarityFn: (T, T) => Doublelon,
    reloncordStatCallback: (String, Long) => Unit = (_, _) => ()
  ): Selont[Selont[Long]]

}

objelonct ClustelonringStatistics {

  // Statistics, to belon importelond whelonrelon reloncordelond.
  val StatSimilarityGraphTotalBuildTimelon = "similarity_graph_total_build_timelon_ms"
  val StatClustelonringAlgorithmRunTimelon = "clustelonring_algorithm_total_run_timelon_ms"
  val StatMelondoidSelonlelonctionTimelon = "melondoid_selonlelonction_total_timelon_ms"
  val StatComputelondSimilarityBelonforelonFiltelonr = "computelond_similarity_belonforelon_filtelonr"

}
