package com.tw ter.t  l neranker.model

 mport com.tw ter.t  l neranker.{thr ftscala => thr ft}
 mport com.tw ter.t  l nes.model.T et d

object T et dRange {
  val default: T et dRange = T et dRange(None, None)
  val empty: T et dRange = T et dRange(So (0L), So (0L))

  def fromThr ft(range: thr ft.T et dRange): T et dRange = {
    T et dRange(from d = range.from d, to d = range.to d)
  }

  def fromT  l neRange(range: T  l neRange): T et dRange = {
    range match {
      case r: T et dRange => r
      case _ =>
        throw new  llegalArgu ntExcept on(s"Only T et  D range  s supported. Found: $range")
    }
  }
}

/**
 * A range of T et  Ds w h exclus ve bounds.
 */
case class T et dRange(from d: Opt on[T et d] = None, to d: Opt on[T et d] = None)
    extends T  l neRange {

  throw f nval d()

  def throw f nval d(): Un  = {
    (from d, to d) match {
      case (So (fromT et d), So (toT et d)) =>
        requ re(fromT et d <= toT et d, "from d must be less than or equal to to d.")
      case _ => // val d, do noth ng.
    }
  }

  def toThr ft: thr ft.T et dRange = {
    thr ft.T et dRange(from d = from d, to d = to d)
  }

  def toT  l neRangeThr ft: thr ft.T  l neRange = {
    thr ft.T  l neRange.T et dRange(toThr ft)
  }

  def  sEmpty: Boolean = {
    (from d, to d) match {
      case (So (from d), So (to d))  f from d == to d => true
      case _ => false
    }
  }
}
