package com.tw ter.product_m xer.component_l brary.cand date_s ce.top cs

 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.strato.StratoKeyV ewFetc rSeqS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.strato.cl ent.Fetc r
 mport com.tw ter.strato.generated.cl ent. nterests.Follo dTop csGetterCl entColumn
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Follo dTop csCand dateS ce @ nject() (
  column: Follo dTop csGetterCl entColumn)
    extends StratoKeyV ewFetc rSeqS ce[
      Long,
      Un ,
      Long
    ] {
  overr de val  dent f er: Cand dateS ce dent f er = Cand dateS ce dent f er("Follo dTop cs")

  overr de val fetc r: Fetc r[Long, Un , Seq[Long]] = column.fetc r
}
