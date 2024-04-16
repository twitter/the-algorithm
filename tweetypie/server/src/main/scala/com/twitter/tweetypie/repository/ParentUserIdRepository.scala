package com.tw ter.t etyp e
package repos ory

 mport com.tw ter.st ch.NotFound
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.core.F lteredState.Unava lable.BounceDeleted
 mport com.tw ter.t etyp e.core.F lteredState.Unava lable.S ceT etNotFound
 mport com.tw ter.t etyp e.core.F lteredState.Unava lable.T etDeleted

object ParentUser dRepos ory {
  type Type = T et => St ch[Opt on[User d]]

  case class ParentT etNotFound(t et d: T et d) extends Except on

  def apply(t etRepo: T etRepos ory.Type): Type = {
    val opt ons = T etQuery.Opt ons(T etQuery. nclude(Set(T et.CoreDataF eld. d)))

    t et =>
      getShare(t et) match {
        case So (share)  f share.s ceStatus d == share.parentStatus d =>
          St ch.value(So (share.s ceUser d))
        case So (share) =>
          t etRepo(share.parentStatus d, opt ons)
            .map(t et => So (getUser d(t et)))
            .rescue {
              case NotFound | T etDeleted | BounceDeleted | S ceT etNotFound(_) =>
                St ch.except on(ParentT etNotFound(share.parentStatus d))
            }
        case None =>
          St ch.None
      }
  }
}
