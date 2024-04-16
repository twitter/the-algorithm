package com.tw ter.t  l neranker.model

 mport com.tw ter.t  l neranker.{thr ftscala => thr ft}
 mport com.tw ter.t  l neserv ce.model.T  l ne d

case class RankedT  l neQuery(
  overr de val  d: T  l ne d,
  overr de val maxCount: Opt on[ nt] = None,
  overr de val range: Opt on[T  l neRange] = None,
  overr de val opt ons: Opt on[RankedT  l neQueryOpt ons] = None)
    extends T  l neQuery(thr ft.T  l neQueryType.Ranked,  d, maxCount, range, opt ons) {

  throw f nval d()
}
