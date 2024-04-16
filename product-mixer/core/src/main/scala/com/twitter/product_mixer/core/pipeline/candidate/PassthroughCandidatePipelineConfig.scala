package com.tw ter.product_m xer.core.p pel ne.cand date

 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.PassthroughCand dateS ce
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateExtractor
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.Cand dateDecorator
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateP pel neQueryTransfor r
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateP pel neResultsTransfor r
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

object PassthroughCand dateP pel neConf g {

  /**
   * Bu ld a [[PassthroughCand dateP pel neConf g]] w h a [[PassthroughCand dateS ce]] bu lt from
   * a [[Cand dateExtractor]]
   */
  def apply[Query <: P pel neQuery, Cand date <: Un versalNoun[Any]](
     dent f er: Cand dateP pel ne dent f er,
    extractor: Cand dateExtractor[Query, Cand date],
    decorator: Opt on[Cand dateDecorator[Query, Cand date]] = None
  ): PassthroughCand dateP pel neConf g[Query, Cand date] = {

    // Renam ng var ables to keep t   nterface clean, but avo d nam ng coll s ons w n creat ng
    // t  anonymous class.
    val _ dent f er =  dent f er
    val _extractor = extractor
    val _decorator = decorator

    new PassthroughCand dateP pel neConf g[Query, Cand date] {
      overr de val  dent f er = _ dent f er
      overr de val cand dateS ce =
        PassthroughCand dateS ce(Cand dateS ce dent f er(_ dent f er.na ), _extractor)
      overr de val decorator = _decorator
    }
  }
}

tra  PassthroughCand dateP pel neConf g[Query <: P pel neQuery, Cand date <: Un versalNoun[Any]]
    extends Cand dateP pel neConf g[Query, Query, Cand date, Cand date] {

  overr de val queryTransfor r: Cand dateP pel neQueryTransfor r[Query, Query] =  dent y

  overr de val resultTransfor r: Cand dateP pel neResultsTransfor r[Cand date, Cand date] =
     dent y
}
