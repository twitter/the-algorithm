package com.tw ter.recos.user_v deo_graph.ut l

 mport com.tw ter.graphjet.b part e.ap .B part eGraph
 mport com.tw ter.recos.user_v deo_graph.thr ftscala._
 mport com.tw ter.recos.features.t et.thr ftscala.GraphFeaturesForT et
 mport com.tw ter.graphjet.algor hms.T et DMask

object GetRelatedT etCand datesUt l {
  pr vate val t et DMask = new T et DMask

  /**
   * calculate scores for each RHS t et that   get back
   * for t etBasedRelatedT et, scorePreFactor = queryT etDegree / log(queryT etDegree) / LHSuserS ze
   * and t  f nal score w ll be a log-cos ne score
   * for non-t etBasedRelatedT et,   don't have a query t et, to keep scor ng funct on cons stent,
   * scorePreFactor = 1000.0 / LHSuserS ze (queryT etDegree's average  s ~10k, 1000 ~= 10k/log(10k))
   * Though scorePreFactor  s appl ed for all results w h n a request,  's st ll useful to make score comparable across requests,
   * so   can have a un fed m n_score and  lp w h downstream score normal zat on
   * **/
  def getRelatedT etCand dates(
    relatedT etCand dates: Seq[Long],
    m nCooccurrence:  nt,
    m nResultDegree:  nt,
    scorePreFactor: Double,
    b part eGraph: B part eGraph
  ): Seq[RelatedT et] = {
    relatedT etCand dates
      .groupBy(t et d => t et d)
      .f lterKeys(t et d => b part eGraph.getR ghtNodeDegree(t et d) > m nResultDegree)
      .mapValues(_.s ze)
      .f lter { case (_, cooccurrence) => cooccurrence >= m nCooccurrence }
      .toSeq
      .map {
        case (relatedT et d, cooccurrence) =>
          val relatedT etDegree = b part eGraph.getR ghtNodeDegree(relatedT et d)

          val score = scorePreFactor * cooccurrence / math.log(relatedT etDegree)
          toRelatedT et(relatedT et d, score, relatedT etDegree, cooccurrence)
      }
      .sortBy(-_.score)
  }

  def toRelatedT et(
    relatedT et d: Long,
    score: Double,
    relatedT etDegree:  nt,
    cooccurrence:  nt
  ): RelatedT et = {
    RelatedT et(
      t et d = t et DMask.restore(relatedT et d),
      score = score,
      relatedT etGraphFeatures = So (
        GraphFeaturesForT et(cooccurrence = So (cooccurrence), degree = So (relatedT etDegree)))
    )
  }
}
