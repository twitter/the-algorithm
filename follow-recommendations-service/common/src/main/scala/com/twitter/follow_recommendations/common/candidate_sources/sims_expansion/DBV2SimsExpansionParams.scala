packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.sims_elonxpansion

import com.twittelonr.timelonlinelons.configapi.FSBoundelondParam
import com.twittelonr.timelonlinelons.configapi.FSParam

objelonct DBV2SimselonxpansionParams {
  // Thelonselons divisors arelon uselond to calibratelon DBv2Sims elonxtelonnsion candidatelons scorelons
  caselon objelonct ReloncelonntFollowingSimilarUselonrsDBV2CalibratelonDivisor
      elonxtelonnds FSBoundelondParam[Doublelon](
        "sims_elonxpansion_reloncelonnt_following_similar_uselonrs_dbv2_divisor",
        delonfault = 1.0d,
        min = 0.1d,
        max = 100d)
  caselon objelonct ReloncelonntelonngagelonmelonntSimilarUselonrsDBV2CalibratelonDivisor
      elonxtelonnds FSBoundelondParam[Doublelon](
        "sims_elonxpansion_reloncelonnt_elonngagelonmelonnt_similar_uselonrs_dbv2_divisor",
        delonfault = 1.0d,
        min = 0.1d,
        max = 100d)
  caselon objelonct DisablelonHelonavyRankelonr
      elonxtelonnds FSParam[Boolelonan]("sims_elonxpansion_disablelon_helonavy_rankelonr", delonfault = falselon)
}
