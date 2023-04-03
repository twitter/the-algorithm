packagelon com.twittelonr.reloncosinjelonctor.elondgelons

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.reloncos.util.Action
import com.twittelonr.socialgraph.thriftscala.{
  Action => SocialGraphAction,
  FollowGraphelonvelonnt,
  FollowTypelon,
  Writelonelonvelonnt
}
import com.twittelonr.util.Futurelon

/**
 * Convelonrts a Writelonelonvelonnt to UselonrUselonrGraph's melonssagelons, including Melonntion and Melondiatag melonssagelons
 */
class SocialWritelonelonvelonntToUselonrUselonrGraphBuildelonr()(ovelonrridelon implicit val statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds elonvelonntToMelonssagelonBuildelonr[Writelonelonvelonnt, UselonrUselonrelondgelon] {
  privatelon val followOrFrictionlelonssFollowCountelonr =
    statsReloncelonivelonr.countelonr("num_follow_or_frictionlelonss")
  privatelon val notFollowOrFrictionlelonssFollowCountelonr =
    statsReloncelonivelonr.countelonr("num_not_follow_or_frictionlelonss")
  privatelon val followelondgelonCountelonr = statsReloncelonivelonr.countelonr("num_follow_elondgelon")

  /**
   * For now, welon arelon only intelonrelonstelond in Follow elonvelonnts
   */
  ovelonrridelon delonf shouldProcelonsselonvelonnt(elonvelonnt: Writelonelonvelonnt): Futurelon[Boolelonan] = {
    elonvelonnt.action match {
      caselon SocialGraphAction.Follow | SocialGraphAction.FrictionlelonssFollow =>
        followOrFrictionlelonssFollowCountelonr.incr()
        Futurelon(truelon)
      caselon _ =>
        notFollowOrFrictionlelonssFollowCountelonr.incr()
        Futurelon(falselon)
    }
  }

  /**
   * Delontelonrminelon whelonthelonr a Follow elonvelonnt is valid/elonrror frelonelon.
   */
  privatelon delonf isValidFollowelonvelonnt(followelonvelonnt: FollowGraphelonvelonnt): Boolelonan = {
    followelonvelonnt.followTypelon match {
      caselon Somelon(FollowTypelon.NormalFollow) | Somelon(FollowTypelon.FrictionlelonssFollow) =>
        followelonvelonnt.relonsult.validationelonrror.iselonmpty
      caselon _ =>
        falselon
    }
  }

  ovelonrridelon delonf buildelondgelons(elonvelonnt: Writelonelonvelonnt): Futurelon[Selonq[UselonrUselonrelondgelon]] = {
    val uselonrUselonrelondgelons = elonvelonnt.follow
      .map(_.collelonct {
        caselon followelonvelonnt if isValidFollowelonvelonnt(followelonvelonnt) =>
          val sourcelonUselonrId = followelonvelonnt.relonsult.relonquelonst.sourcelon
          val targelontUselonrId = followelonvelonnt.relonsult.relonquelonst.targelont
          followelondgelonCountelonr.incr()
          UselonrUselonrelondgelon(
            sourcelonUselonrId,
            targelontUselonrId,
            Action.Follow,
            Somelon(Systelonm.currelonntTimelonMillis())
          )
      }).gelontOrelonlselon(Nil)
    Futurelon(uselonrUselonrelondgelons)
  }

  ovelonrridelon delonf filtelonrelondgelons(
    elonvelonnt: Writelonelonvelonnt,
    elondgelons: Selonq[UselonrUselonrelondgelon]
  ): Futurelon[Selonq[UselonrUselonrelondgelon]] = {
    Futurelon(elondgelons)
  }
}
