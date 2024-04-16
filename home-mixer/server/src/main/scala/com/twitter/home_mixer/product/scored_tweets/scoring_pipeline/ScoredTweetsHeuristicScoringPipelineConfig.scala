package com.tw ter.ho _m xer.product.scored_t ets.scor ng_p pel ne

 mport com.tw ter.ho _m xer.product.scored_t ets.model.ScoredT etsQuery
 mport com.tw ter.ho _m xer.product.scored_t ets.scorer. ur st cScorer
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.component_l brary.selector. nsertAppendResults
 mport com.tw ter.product_m xer.core.funct onal_component.common.AllP pel nes
 mport com.tw ter.product_m xer.core.funct onal_component.scorer.Scorer
 mport com.tw ter.product_m xer.core.funct onal_component.selector.Selector
 mport com.tw ter.product_m xer.core.model.common. dent f er.Scor ngP pel ne dent f er
 mport com.tw ter.product_m xer.core.p pel ne.scor ng.Scor ngP pel neConf g

object ScoredT ets ur st cScor ngP pel neConf g
    extends Scor ngP pel neConf g[ScoredT etsQuery, T etCand date] {

  overr de val  dent f er: Scor ngP pel ne dent f er =
    Scor ngP pel ne dent f er("ScoredT ets ur st c")

  overr de val selectors: Seq[Selector[ScoredT etsQuery]] = Seq( nsertAppendResults(AllP pel nes))

  overr de val scorers: Seq[Scorer[ScoredT etsQuery, T etCand date]] =
    Seq( ur st cScorer)
}
