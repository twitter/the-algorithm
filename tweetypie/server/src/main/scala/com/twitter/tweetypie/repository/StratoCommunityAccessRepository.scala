package com.tw ter.t etyp e.repos ory

 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.Commun y d
 mport com.tw ter.strato.cl ent.Fetc r
 mport com.tw ter.strato.cl ent.{Cl ent => StratoCl ent}

object StratoCommun yAccessRepos ory {
  type Type = Commun y d => St ch[Opt on[Commun yAccess]]

  sealed tra  Commun yAccess
  object Commun yAccess {
    case object Publ c extends Commun yAccess
    case object Closed extends Commun yAccess
    case object Pr vate extends Commun yAccess
  }

  val column = "commun  es/access.Commun y"

  def apply(cl ent: StratoCl ent): Type = {
    val fetc r: Fetc r[Commun y d, Un , Commun yAccess] =
      cl ent.fetc r[Commun y d, Commun yAccess](column)

    commun y d => fetc r.fetch(commun y d).map(_.v)
  }
}
