package com.tw ter.t etyp e.repos ory

 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.Commun y d
 mport com.tw ter.strato.cl ent.Fetc r
 mport com.tw ter.strato.cl ent.{Cl ent => StratoCl ent}

object StratoCommun y mbersh pRepos ory {
  type Type = Commun y d => St ch[Boolean]

  val column = "commun  es/ s mber.Commun y"

  def apply(cl ent: StratoCl ent): Type = {
    val fetc r: Fetc r[Commun y d, Un , Boolean] =
      cl ent.fetc r[Commun y d, Boolean](column)

    commun y d => fetc r.fetch(commun y d).map(_.v.getOrElse(false))
  }
}
