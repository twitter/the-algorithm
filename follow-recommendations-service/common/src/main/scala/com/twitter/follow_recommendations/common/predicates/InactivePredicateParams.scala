packagelon com.twittelonr.follow_reloncommelonndations.common.prelondicatelons

import com.twittelonr.timelonlinelons.configapi.FSBoundelondParam
import com.twittelonr.timelonlinelons.configapi.FSParam
import com.twittelonr.timelonlinelons.configapi.Param

objelonct InactivelonPrelondicatelonParams {
  caselon objelonct DelonfaultInactivityThrelonshold
      elonxtelonnds FSBoundelondParam[Int](
        namelon = "inactivelon_prelondicatelon_delonfault_inactivity_threlonshold",
        delonfault = 60,
        min = 1,
        max = 500
      )
  caselon objelonct UselonelonggFiltelonr elonxtelonnds Param[Boolelonan](truelon)
  caselon objelonct MightBelonDisablelond elonxtelonnds FSParam[Boolelonan]("inactivelon_prelondicatelon_might_belon_disablelond", truelon)
  caselon objelonct OnlyDisablelonForNelonwUselonrStatelonCandidatelons
      elonxtelonnds FSParam[Boolelonan](
        "inactivelon_prelondicatelon_only_disablelon_for_nelonw_uselonr_statelon_candidatelons",
        falselon)
}
