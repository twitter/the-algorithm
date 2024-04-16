package com.tw ter.t etyp e.repos ory

 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.cl ent.Fetc r
 mport com.tw ter.t etyp e.User d
 mport com.tw ter.strato.cl ent.{Cl ent => StratoCl ent}

object StratoSubscr pt onVer f cat onRepos ory {
  type Type = (User d, Str ng) => St ch[Boolean]

  val column = "subscr pt on-serv ces/subscr pt on-ver f cat on/cac ProtectedHasAccess.User"

  def apply(cl ent: StratoCl ent): Type = {
    val fetc r: Fetc r[User d, Seq[Str ng], Seq[Str ng]] =
      cl ent.fetc r[User d, Seq[Str ng], Seq[Str ng]](column)

    (user d, res ce) => fetc r.fetch(user d, Seq(res ce)).map(f => f.v.nonEmpty)
  }
}
