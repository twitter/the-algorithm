package com.tw ter.t  l neranker.model

 mport com.tw ter.t  l neranker.{thr ftscala => thr ft}

object T  l neQueryOpt ons {
  def fromThr ft(opt ons: thr ft.T  l neQueryOpt ons): T  l neQueryOpt ons = {
    opt ons match {
      case thr ft.T  l neQueryOpt ons.RankedT  l neQueryOpt ons(r) =>
        RankedT  l neQueryOpt ons.fromThr ft(r)
      case thr ft.T  l neQueryOpt ons.ReverseChronT  l neQueryOpt ons(r) =>
        ReverseChronT  l neQueryOpt ons.fromThr ft(r)
      case _ => throw new  llegalArgu ntExcept on(s"Unsupported type: $opt ons")
    }
  }
}

tra  T  l neQueryOpt ons {
  def toT  l neQueryOpt onsThr ft: thr ft.T  l neQueryOpt ons
  def throw f nval d(): Un 
}
