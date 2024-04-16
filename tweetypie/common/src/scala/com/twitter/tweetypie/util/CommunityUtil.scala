package com.tw ter.t etyp e.ut l

 mport com.tw ter.t etyp e.thr ftscala.Commun  es

object Commun yUt l {

  def commun y ds(maybeCommun  es: Opt on[Commun  es]): Seq[Long] = {
    maybeCommun  es match {
      case None =>
        N l
      case So (Commun  es(seq)) =>
        seq
    }
  }

  def hasCommun y(maybeCommun  es: Opt on[Commun  es]): Boolean = {
    maybeCommun  es.ex sts(_.commun y ds.nonEmpty)
  }
}
