package com.tw ter.t  l neranker.model

 mport com.tw ter.t  l neranker.{thr ftscala => thr ft}
 mport com.tw ter.t  l nes.model.T et d

object Pr orSeenEntr es {
  def fromThr ft(entr es: thr ft.Pr orSeenEntr es): Pr orSeenEntr es = {
    Pr orSeenEntr es(seenEntr es = entr es.seenEntr es)
  }
}

case class Pr orSeenEntr es(seenEntr es: Seq[T et d]) {

  throw f nval d()

  def toThr ft: thr ft.Pr orSeenEntr es = {
    thr ft.Pr orSeenEntr es(seenEntr es = seenEntr es)
  }

  def throw f nval d(): Un  = {
    // No val dat on perfor d.
  }
}
