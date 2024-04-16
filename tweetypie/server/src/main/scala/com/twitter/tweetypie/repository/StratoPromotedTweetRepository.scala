package com.tw ter.t etyp e.repos ory

 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.cl ent.Fetc r
 mport com.tw ter.t etyp e.T et d
 mport com.tw ter.strato.cl ent.{Cl ent => StratoCl ent}

object StratoPromotedT etRepos ory {
  type Type = T et d => St ch[Boolean]

  val column = "t etyp e/ sPromoted.T et"

  def apply(cl ent: StratoCl ent): Type = {
    val fetc r: Fetc r[T et d, Un , Boolean] =
      cl ent.fetc r[T et d, Boolean](column)

    t et d => fetc r.fetch(t et d).map(f => f.v.getOrElse(false))
  }
}
