package com.tw ter.t etyp e.repos ory

 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.cl ent.Fetc r
 mport com.tw ter.strato.cl ent.{Cl ent => StratoCl ent}
 mport com.tw ter.t etyp e.User d

object StratoSuperFollowEl g bleRepos ory {
  type Type = User d => St ch[Boolean]

  val column = "aud encerewards/aud enceRewardsServ ce/getSuperFollowEl g b l y.User"

  def apply(cl ent: StratoCl ent): Type = {
    val fetc r: Fetc r[User d, Un , Boolean] =
      cl ent.fetc r[User d, Boolean](column)

    user d => fetc r.fetch(user d).map(_.v.getOrElse(false))
  }
}
