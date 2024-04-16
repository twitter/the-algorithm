package com.tw ter.t  l neranker.model

 mport com.tw ter.t  l neranker.{thr ftscala => thr ft}

object RankedT  l neQueryOpt ons {
  def fromThr ft(opt ons: thr ft.RankedT  l neQueryOpt ons): RankedT  l neQueryOpt ons = {
    RankedT  l neQueryOpt ons(
      seenEntr es = opt ons.seenEntr es.map(Pr orSeenEntr es.fromThr ft)
    )
  }
}

case class RankedT  l neQueryOpt ons(seenEntr es: Opt on[Pr orSeenEntr es])
    extends T  l neQueryOpt ons {

  throw f nval d()

  def toThr ft: thr ft.RankedT  l neQueryOpt ons = {
    thr ft.RankedT  l neQueryOpt ons(seenEntr es = seenEntr es.map(_.toThr ft))
  }

  def toT  l neQueryOpt onsThr ft: thr ft.T  l neQueryOpt ons = {
    thr ft.T  l neQueryOpt ons.RankedT  l neQueryOpt ons(toThr ft)
  }

  def throw f nval d(): Un  = {
    seenEntr es.foreach(_.throw f nval d)
  }
}
