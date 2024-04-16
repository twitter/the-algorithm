package com.tw ter.t  l neranker.model

 mport com.tw ter.t  l neranker.{thr ftscala => thr ft}
 mport com.tw ter.t  l neserv ce.model.T  l ne d

object ReverseChronT  l neQuery {
  def fromT  l neQuery(query: T  l neQuery): ReverseChronT  l neQuery = {
    query match {
      case q: ReverseChronT  l neQuery => q
      case _ => throw new  llegalArgu ntExcept on(s"Unsupported query type: $query")
    }
  }
}

case class ReverseChronT  l neQuery(
  overr de val  d: T  l ne d,
  overr de val maxCount: Opt on[ nt] = None,
  overr de val range: Opt on[T  l neRange] = None,
  overr de val opt ons: Opt on[ReverseChronT  l neQueryOpt ons] = None)
    extends T  l neQuery(thr ft.T  l neQueryType.ReverseChron,  d, maxCount, range, opt ons) {

  throw f nval d()
}
