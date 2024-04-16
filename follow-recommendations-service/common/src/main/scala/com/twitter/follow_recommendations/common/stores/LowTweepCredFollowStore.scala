package com.tw ter.follow_recom ndat ons.common.stores

 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.HasRecentFollo dUser ds
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.generated.cl ent.onboard ng.userrecs.T epCredOnUserCl entColumn
 mport javax. nject. nject
 mport javax. nject.S ngleton

// Not a cand date s ce s nce  's a  nter d ary.
@S ngleton
class LowT epCredFollowStore @ nject() (t epCredOnUserCl entColumn: T epCredOnUserCl entColumn) {

  def getLowT epCredUsers(target: HasRecentFollo dUser ds): St ch[Seq[Cand dateUser]] = {
    val newFollow ngs =
      target.recentFollo dUser ds.getOrElse(N l).take(LowT epCredFollowStore.NumFlockToRetr eve)

    val val dT epScoreUser dsSt ch: St ch[Seq[Long]] = St ch
      .traverse(newFollow ngs) { newFollow ngUser d =>
        val t epCredScoreOptSt ch = t epCredOnUserCl entColumn.fetc r
          .fetch(newFollow ngUser d)
          .map(_.v)
        t epCredScoreOptSt ch.map(_.flatMap(t epCred =>
           f (t epCred < LowT epCredFollowStore.T epCredThreshold) {
            So (newFollow ngUser d)
          } else {
            None
          }))
      }.map(_.flatten)

    val dT epScoreUser dsSt ch
      .map(_.map(Cand dateUser(_, So (Cand dateUser.DefaultCand dateScore))))
  }
}

object LowT epCredFollowStore {
  val NumFlockToRetr eve = 500
  val T epCredThreshold = 40
}
