package com.tw ter.product_m xer.core.p pel ne.cand date

 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Stat cCand dateS ce
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateP pel neQueryTransfor r
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateP pel neResultsTransfor r
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.Cand dateDecorator

object Stat cCand dateP pel neConf g {

  /**
   * Bu ld a [[Stat cCand dateP pel neConf g]] w h a [[Cand dateS ce]] that returns t  [[cand date]]
   */
  def apply[Query <: P pel neQuery, Cand date <: Un versalNoun[Any]](
     dent f er: Cand dateP pel ne dent f er,
    cand date: Cand date,
    decorator: Opt on[Cand dateDecorator[Query, Cand date]] = None
  ): Stat cCand dateP pel neConf g[Query, Cand date] = {

    // Renam ng var ables to keep t   nterface clean, but avo d nam ng coll s ons w n creat ng
    // t  anonymous class.
    val _ dent f er =  dent f er
    val _cand date = cand date
    val _decorator = decorator

    new Stat cCand dateP pel neConf g[Query, Cand date] {
      overr de val  dent f er = _ dent f er
      overr de val cand date = _cand date
      overr de val decorator = _decorator
    }
  }
}

tra  Stat cCand dateP pel neConf g[Query <: P pel neQuery, Cand date <: Un versalNoun[Any]]
    extends Cand dateP pel neConf g[Query, Un , Un , Cand date] {

  val cand date: Cand date

  overr de def cand dateS ce: Cand dateS ce[Un , Un ] = Stat cCand dateS ce[Un ](
     dent f er = Cand dateS ce dent f er( dent f er.na ),
    result = Seq(()))

  overr de val queryTransfor r: Cand dateP pel neQueryTransfor r[Query, Un ] = _ => Un 

  overr de val resultTransfor r: Cand dateP pel neResultsTransfor r[Un , Cand date] = _ =>
    cand date
}
