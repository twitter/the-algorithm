package com.tw ter.t etyp e.ut l

 mport com.tw ter.esc rb rd.thr ftscala.T etEnt yAnnotat on
 mport com.tw ter.t etyp e.thr ftscala.Esc rb rdEnt yAnnotat ons
 mport com.tw ter.t etyp e.thr ftscala.T et

object Commun yAnnotat on {

  val group d: Long = 8
  val doma n d: Long = 31

  def apply(commun y d: Long): T etEnt yAnnotat on =
    T etEnt yAnnotat on(group d, doma n d, ent y d = commun y d)

  def unapply(annotat on: T etEnt yAnnotat on): Opt on[Long] =
    annotat on match {
      case T etEnt yAnnotat on(`group d`, `doma n d`, ent y d) => So (ent y d)
      case _ => None
    }

  // Returns None  nstead of So (Seq()) w n t re are non-commun y annotat ons present
  def add  onalF eldsToCommun y Ds(add  onalF elds: T et): Opt on[Seq[Long]] = {
    add  onalF elds.esc rb rdEnt yAnnotat ons
      .map {
        case Esc rb rdEnt yAnnotat ons(ent yAnnotat ons) =>
          ent yAnnotat ons.flatMap(Commun yAnnotat on.unapply)
      }.f lter(_.nonEmpty)
  }
}
