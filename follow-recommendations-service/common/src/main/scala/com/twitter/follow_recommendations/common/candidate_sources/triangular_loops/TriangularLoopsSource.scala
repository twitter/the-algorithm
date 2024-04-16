package com.tw ter.follow_recom ndat ons.common.cand date_s ces.tr angular_loops

 mport com.tw ter.follow_recom ndat ons.common.models.AccountProof
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.FollowProof
 mport com.tw ter.follow_recom ndat ons.common.models.HasRecentFollo dByUser ds
 mport com.tw ter.follow_recom ndat ons.common.models.Reason
 mport com.tw ter. rm .model.Algor hm
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.generated.cl ent.onboard ng.userrecs.Tr angularLoopsV2OnUserCl entColumn
 mport com.tw ter.t  l nes.conf gap .HasParams
 mport com.tw ter.wtf.tr angular_loop.thr ftscala.Cand dates
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Tr angularLoopsS ce @ nject() (
  tr angularLoopsV2Column: Tr angularLoopsV2OnUserCl entColumn)
    extends Cand dateS ce[
      HasParams w h HasCl entContext w h HasRecentFollo dByUser ds,
      Cand dateUser
    ] {

  overr de val  dent f er: Cand dateS ce dent f er = Tr angularLoopsS ce. dent f er

  overr de def apply(
    target: HasParams w h HasCl entContext w h HasRecentFollo dByUser ds
  ): St ch[Seq[Cand dateUser]] = {
    val cand dates = target.getOpt onalUser d
      .map { user d =>
        val fetc r = tr angularLoopsV2Column.fetc r
        fetc r
          .fetch(user d)
          .map { result =>
            result.v
              .map(Tr angularLoopsS ce.mapCand datesToCand dateUsers)
              .getOrElse(N l)
          }
      }.getOrElse(St ch.N l)
    // Make sure recentFollo dByUser ds  s populated w h n t  RequestBu lder before enable  
     f (target.params(Tr angularLoopsParams.KeepOnlyCand datesWhoFollowTargetUser))
      f lterOutCand datesNotFollow ngTargetUser(cand dates, target.recentFollo dByUser ds)
    else
      cand dates
  }

  def f lterOutCand datesNotFollow ngTargetUser(
    cand datesSt ch: St ch[Seq[Cand dateUser]],
    recentFollow ngs: Opt on[Seq[Long]]
  ): St ch[Seq[Cand dateUser]] = {
    cand datesSt ch.map { cand dates =>
      val recentFollow ng dsSet = recentFollow ngs.getOrElse(N l).toSet
      cand dates.f lter(cand date => recentFollow ng dsSet.conta ns(cand date. d))
    }
  }
}

object Tr angularLoopsS ce {

  val  dent f er = Cand dateS ce dent f er(Algor hm.Tr angularLoop.toStr ng)
  val NumResults = 100

  def mapCand datesToCand dateUsers(cand dates: Cand dates): Seq[Cand dateUser] = {
    cand dates.cand dates
      .map { cand date =>
        Cand dateUser(
           d = cand date. ncom ngUser d,
          score = So (1.0 / math
            .max(1, cand date.numFollo rs.getOrElse(0) + cand date.numFollow ngs.getOrElse(0))),
          reason = So (
            Reason(
              So (
                AccountProof(
                  followProof =
                     f (cand date.soc alProofUser ds. sEmpty) None
                    else
                      So (
                        FollowProof(
                          cand date.soc alProofUser ds,
                          cand date.numSoc alProof.getOrElse(cand date.soc alProofUser ds.s ze)))
                )
              )
            )
          )
        ).w hCand dateS ce( dent f er)
      }.sortBy(-_.score.getOrElse(0.0)).take(NumResults)
  }
}
