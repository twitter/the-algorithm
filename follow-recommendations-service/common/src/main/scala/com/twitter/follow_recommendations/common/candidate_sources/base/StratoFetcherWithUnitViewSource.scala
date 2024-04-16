package com.tw ter.follow_recom ndat ons.common.cand date_s ces.base

 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.strato.cl ent.Fetc r

abstract class StratoFetc rW hUn V ewS ce[K, V](
  fetc r: Fetc r[K, Un , V],
  overr de val  dent f er: Cand dateS ce dent f er)
    extends StratoFetc rS ce[K, Un , V](fetc r, Un ,  dent f er)
