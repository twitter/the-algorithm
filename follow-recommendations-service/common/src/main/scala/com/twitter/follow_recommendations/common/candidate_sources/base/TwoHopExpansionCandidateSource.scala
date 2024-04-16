package com.tw ter.follow_recom ndat ons.common.cand date_s ces.base

 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.st ch.St ch

/**
 * base tra  for two-hop expans on based algor hms, e.g. onl ne_stp, phonebook_pred ct on,
 * recent follow ng s ms, recent engage nt s ms, ...
 *
 * @tparam Target target type
 * @tparam F rstDegree type of f rst degree nodes
 * @tparam SecondaryDegree type of secondary degree nodes
 * @tparam Cand date output cand date types
 */
tra  TwoHopExpans onCand dateS ce[-Target, F rstDegree, SecondaryDegree, +Cand date]
    extends Cand dateS ce[Target, Cand date] {

  /**
   * fetch f rst degree nodes g ven request
   */
  def f rstDegreeNodes(req: Target): St ch[Seq[F rstDegree]]

  /**
   * fetch secondary degree nodes g ven request and f rst degree nodes
   */
  def secondaryDegreeNodes(req: Target, node: F rstDegree): St ch[Seq[SecondaryDegree]]

  /**
   * aggregate and score t  cand dates to generate f nal results
   */
  def aggregateAndScore(
    req: Target,
    f rstDegreeToSecondDegreeNodesMap: Map[F rstDegree, Seq[SecondaryDegree]]
  ): St ch[Seq[Cand date]]

  /**
   * Generate a l st of cand dates for t  target
   */
  def apply(target: Target): St ch[Seq[Cand date]] = {
    for {
      f rstDegreeNodes <- f rstDegreeNodes(target)
      secondaryDegreeNodes <- St ch.traverse(f rstDegreeNodes)(secondaryDegreeNodes(target, _))
      aggregated <- aggregateAndScore(target, f rstDegreeNodes.z p(secondaryDegreeNodes).toMap)
    } y eld aggregated
  }
}
