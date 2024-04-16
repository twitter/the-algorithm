package com.tw ter.t  l neranker.model

 mport com.tw ter.t  l neranker.{thr ftscala => thr ft}
 mport com.tw ter.ut l.T  

object T  Range {
  val default: T  Range = T  Range(None, None)

  def fromThr ft(range: thr ft.T  Range): T  Range = {
    T  Range(
      from = range.fromMs.map(T  .fromM ll seconds),
      to = range.toMs.map(T  .fromM ll seconds)
    )
  }
}

case class T  Range(from: Opt on[T  ], to: Opt on[T  ]) extends T  l neRange {

  throw f nval d()

  def throw f nval d(): Un  = {
    (from, to) match {
      case (So (fromT  ), So (toT  )) =>
        requ re(fromT   <= toT  , "from-t   must be less than or equal to-t  .")
      case _ => // val d, do noth ng.
    }
  }

  def toThr ft: thr ft.T  Range = {
    thr ft.T  Range(
      fromMs = from.map(_. nM ll seconds),
      toMs = to.map(_. nM ll seconds)
    )
  }

  def toT  l neRangeThr ft: thr ft.T  l neRange = {
    thr ft.T  l neRange.T  Range(toThr ft)
  }
}
