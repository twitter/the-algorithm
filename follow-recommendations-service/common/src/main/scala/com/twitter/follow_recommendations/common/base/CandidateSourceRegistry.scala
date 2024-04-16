package com.tw ter.follow_recom ndat ons.common.base

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.base.Enr c dCand dateS ce.toEnr c d
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er

// a  lper structure to reg ster and select cand date s ces based on  dent f ers
tra  Cand dateS ceReg stry[Target, Cand date] {

  val statsRece ver: StatsRece ver

  def s ces: Set[Cand dateS ce[Target, Cand date]]

  f nal lazy val cand dateS ces: Map[
    Cand dateS ce dent f er,
    Cand dateS ce[Target, Cand date]
  ] = {
    val map = s ces.map { c =>
      c. dent f er -> c.observe(statsRece ver)
    }.toMap

     f (map.s ze != s ces.s ze) {
      throw new  llegalArgu ntExcept on("Dupl cate Cand date S ce  dent f ers")
    }

    map
  }

  def select(
     dent f ers: Set[Cand dateS ce dent f er]
  ): Set[Cand dateS ce[Target, Cand date]] = {
    // fa ls loud  f t  cand date s ce  s not reg stered
     dent f ers.map(cand dateS ces(_))
  }
}
