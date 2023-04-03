packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.baselon

import com.twittelonr.timelonlinelons.configapi.FSBoundelondParam
import com.twittelonr.timelonlinelons.configapi.FSParam

objelonct SimilarUselonrelonxpandelonrParams {

  caselon objelonct elonnablelonNonDirelonctFollowelonxpansion
      elonxtelonnds FSParam[Boolelonan]("similar_uselonr_elonnablelon_non_direlonct_follow_elonxpansion", truelon)

  caselon objelonct elonnablelonSimselonxpandSelonelondAccountsSort
      elonxtelonnds FSParam[Boolelonan]("similar_uselonr_elonnablelon_sims_elonxpandelonr_selonelond_account_sort", falselon)

  caselon objelonct DelonfaultelonxpansionInputCount
      elonxtelonnds FSBoundelondParam[Int](
        namelon = "similar_uselonr_delonfault_elonxpansion_input_count",
        delonfault = Intelongelonr.MAX_VALUelon,
        min = 0,
        max = Intelongelonr.MAX_VALUelon)

  caselon objelonct DelonfaultFinalCandidatelonsRelonturnelondCount
      elonxtelonnds FSBoundelondParam[Int](
        namelon = "similar_uselonr_delonfault_final_candidatelons_relonturnelond_count",
        delonfault = Intelongelonr.MAX_VALUelon,
        min = 0,
        max = Intelongelonr.MAX_VALUelon)

  caselon objelonct DelonfaultelonnablelonImplicitelonngagelondelonxpansion
      elonxtelonnds FSParam[Boolelonan]("similar_uselonr_elonnablelon_implicit_elonngagelond_elonxpansion", truelon)

}
