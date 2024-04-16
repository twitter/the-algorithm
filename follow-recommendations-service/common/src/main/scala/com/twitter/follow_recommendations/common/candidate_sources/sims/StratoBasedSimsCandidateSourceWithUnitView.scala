package com.tw ter.follow_recom ndat ons.common.cand date_s ces.s ms

 mport com.tw ter. rm .cand date.thr ftscala.Cand dates
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.strato.cl ent.Fetc r

abstract class StratoBasedS msCand dateS ceW hUn V ew(
  fetc r: Fetc r[Long, Un , Cand dates],
  overr de val  dent f er: Cand dateS ce dent f er)
    extends StratoBasedS msCand dateS ce[Un ](fetc r, Un ,  dent f er)
