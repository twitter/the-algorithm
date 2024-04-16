package com.tw ter.product_m xer.component_l brary.cand date_s ce.l sts

 mport com.tw ter.product_m xer.component_l brary.model.cand date.Tw terL stCand date
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.strato.StratoKeyFetc rS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.strato.cl ent.Fetc r
 mport com.tw ter.strato.generated.cl ent.recom ndat ons. nterests_d scovery.recom ndat ons_mh.Organ cPopgeoL stsCl entColumn
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Organ cPopGeoL stsCand dateS ce @ nject() (
  organ cPopgeoL stsCl entColumn: Organ cPopgeoL stsCl entColumn)
    extends StratoKeyFetc rS ce[
      Organ cPopgeoL stsCl entColumn.Key,
      Organ cPopgeoL stsCl entColumn.Value,
      Tw terL stCand date
    ] {

  overr de val  dent f er: Cand dateS ce dent f er = Cand dateS ce dent f er(
    "Organ cPopGeoL sts")

  overr de val fetc r: Fetc r[
    Organ cPopgeoL stsCl entColumn.Key,
    Un ,
    Organ cPopgeoL stsCl entColumn.Value
  ] =
    organ cPopgeoL stsCl entColumn.fetc r

  overr de def stratoResultTransfor r(
    stratoResult: Organ cPopgeoL stsCl entColumn.Value
  ): Seq[Tw terL stCand date] = {
    stratoResult.recom ndedL stsByAlgo.flatMap { topL sts =>
      topL sts.l sts.map { l st =>
        Tw terL stCand date(l st.l st d)
      }
    }
  }
}
