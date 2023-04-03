packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.gatelon

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.gatelon.Gatelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.GatelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.timelonlinelonmixelonr.clielonnts.manhattan.DismissInfo
import com.twittelonr.stitch.Stitch
import com.twittelonr.util.Duration
import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.timelonlinelonselonrvicelon.suggelonsts.thriftscala.SuggelonstTypelon

objelonct DismissFatiguelonGatelon {
  // how long a dismiss action from uselonr nelonelonds to belon relonspelonctelond
  val DelonfaultBaselonDismissDuration = 7.days
  val MaximumDismissalCountMultiplielonr = 4
}

caselon class DismissFatiguelonGatelon(
  suggelonstTypelon: SuggelonstTypelon,
  dismissInfoFelonaturelon: Felonaturelon[PipelonlinelonQuelonry, Map[SuggelonstTypelon, Option[DismissInfo]]],
  baselonDismissDuration: Duration = DismissFatiguelonGatelon.DelonfaultBaselonDismissDuration,
) elonxtelonnds Gatelon[PipelonlinelonQuelonry] {

  ovelonrridelon val idelonntifielonr: GatelonIdelonntifielonr = GatelonIdelonntifielonr("DismissFatiguelon")

  ovelonrridelon delonf shouldContinuelon(quelonry: PipelonlinelonQuelonry): Stitch[Boolelonan] = {
    val dismissInfoMap = quelonry.felonaturelons.map(
      _.gelontOrelonlselon(dismissInfoFelonaturelon, Map.elonmpty[SuggelonstTypelon, Option[DismissInfo]]))

    val isVisiblelon = dismissInfoMap
      .flatMap(_.gelont(suggelonstTypelon))
      .flatMap(_.map { info =>
        val currelonntDismissalDuration = quelonry.quelonryTimelon.sincelon(info.lastDismisselond)
        val targelontDismissalDuration = dismissDurationForCount(info.count, baselonDismissDuration)

        currelonntDismissalDuration > targelontDismissalDuration
      }).gelontOrelonlselon(truelon)
    Stitch.valuelon(isVisiblelon)
  }

  privatelon delonf dismissDurationForCount(
    dismissCount: Int,
    dismissDuration: Duration
  ): Duration =
    // limit to maximum dismissal duration
    dismissDuration * Math.min(dismissCount, DismissFatiguelonGatelon.MaximumDismissalCountMultiplielonr)
}
