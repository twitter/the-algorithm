package com.tw ter.t  l neranker.model

 mport com.tw ter.t  l neranker.{thr ftscala => thr ft}

object T  l neRange {
  def fromThr ft(range: thr ft.T  l neRange): T  l neRange = {
    range match {
      case thr ft.T  l neRange.T  Range(r) => T  Range.fromThr ft(r)
      case thr ft.T  l neRange.T et dRange(r) => T et dRange.fromThr ft(r)
      case _ => throw new  llegalArgu ntExcept on(s"Unsupported type: $range")
    }
  }
}

tra  T  l neRange {
  def toT  l neRangeThr ft: thr ft.T  l neRange
  def throw f nval d(): Un 
}
