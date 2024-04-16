package com.tw ter.follow_recom ndat ons.common.cand date_s ces.salsa

 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.st ch.St ch

abstract class SalsaExpans onBasedCand dateS ce[Target](salsaExpander: SalsaExpander)
    extends Cand dateS ce[Target, Cand dateUser] {

  // Def ne f rst/second degree as empty sequences  n cases of subclasses
  // that don't  mple nt one or t  ot r.
  // Example: Mag cRecs only uses f rst degree nodes, and can  gnore  mple nt ng secondDegreeNodes
  //
  // T  allows apply(target) to comb ne both  n t  base class
  def f rstDegreeNodes(target: Target): St ch[Seq[Long]] = St ch.value(Seq())

  def secondDegreeNodes(target: Target): St ch[Seq[Long]] = St ch.value(Seq())

  // max number output results
  def maxResults(target: Target):  nt

  overr de def apply(target: Target): St ch[Seq[Cand dateUser]] = {
    val nodes = St ch.jo n(f rstDegreeNodes(target), secondDegreeNodes(target))

    nodes.flatMap {
      case (f rstDegreeCand dates, secondDegreeCand dates) => {
        salsaExpander(f rstDegreeCand dates, secondDegreeCand dates, maxResults(target))
          .map(_.map(_.w hCand dateS ce( dent f er)).sortBy(-_.score.getOrElse(0.0)))
      }
    }
  }
}
