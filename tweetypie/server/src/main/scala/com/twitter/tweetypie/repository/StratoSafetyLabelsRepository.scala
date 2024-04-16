package com.tw ter.t etyp e
package repos ory

 mport com.tw ter.search.blender.serv ces.strato.UserSearchSafetySett ngs
 mport com.tw ter.spam.rtf.thr ftscala.SafetyLabel
 mport com.tw ter.spam.rtf.thr ftscala.SafetyLabelMap
 mport com.tw ter.spam.rtf.thr ftscala.SafetyLabelType
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.cl ent.Fetc r
 mport com.tw ter.strato.cl ent.{Cl ent => StratoCl ent}
 mport com.tw ter.strato.thr ft.ScroogeConv mpl c s._
 mport com.tw ter.v s b l y.common.UserSearchSafetyS ce

object StratoSafetyLabelsRepos ory {
  type Type = (T et d, SafetyLabelType) => St ch[Opt on[SafetyLabel]]

  def apply(cl ent: StratoCl ent): Type = {
    val safetyLabelMapRepo = StratoSafetyLabelMapRepos ory(cl ent)

    (t et d, safetyLabelType) =>
      safetyLabelMapRepo(t et d).map(
        _.flatMap(_.labels).flatMap(_.get(safetyLabelType))
      )
  }
}

object StratoSafetyLabelMapRepos ory {
  type Type = T et d => St ch[Opt on[SafetyLabelMap]]

  val column = "v s b l y/baseT etSafetyLabelMap"

  def apply(cl ent: StratoCl ent): Type = {
    val fetc r: Fetc r[T et d, Un , SafetyLabelMap] =
      cl ent.fetc r[T et d, SafetyLabelMap](column)

    t et d => fetc r.fetch(t et d).map(_.v)
  }
}

object StratoUserSearchSafetyS ceRepos ory {
  type Type = User d => St ch[UserSearchSafetySett ngs]

  def apply(cl ent: StratoCl ent): Type = {
    val fetc r: Fetc r[User d, Un , UserSearchSafetySett ngs] =
      cl ent.fetc r[User d, UserSearchSafetySett ngs](UserSearchSafetyS ce.Column)

    user d => fetc r.fetch(user d).map(_.v.getOrElse(UserSearchSafetyS ce.DefaultSett ng))
  }
}
