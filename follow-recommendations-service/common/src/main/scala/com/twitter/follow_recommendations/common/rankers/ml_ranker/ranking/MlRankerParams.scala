packagelon com.twittelonr.follow_reloncommelonndations.common.rankelonrs.ml_rankelonr.ranking

import com.twittelonr.follow_reloncommelonndations.common.rankelonrs.common.RankelonrId
import com.twittelonr.timelonlinelons.configapi.FSelonnumParam
import com.twittelonr.timelonlinelons.configapi.FSParam

/**
 * Whelonn adding Producelonr sidelon elonxpelonrimelonnts, makelon surelon to relongistelonr thelon FS Kelony in [[ProducelonrFelonaturelonFiltelonr]]
 * in [[FelonaturelonSwitchelonsModulelon]], othelonrwiselon, thelon FS will not work.
 */
objelonct MlRankelonrParams {
  // which rankelonr to uselon by delonfault for thelon givelonn relonquelonst
  caselon objelonct RelonquelonstScorelonrIdParam
      elonxtelonnds FSelonnumParam[RankelonrId.typelon](
        namelon = "post_nux_ml_flow_ml_rankelonr_id",
        delonfault = RankelonrId.PostNuxProdRankelonr,
        elonnum = RankelonrId
      )

  // which rankelonr to uselon for thelon givelonn candidatelon
  caselon objelonct CandidatelonScorelonrIdParam
      elonxtelonnds FSelonnumParam[RankelonrId.typelon](
        namelon = "post_nux_ml_flow_candidatelon_uselonr_scorelonr_id",
        delonfault = RankelonrId.Nonelon,
        elonnum = RankelonrId
      )

  caselon objelonct ScribelonRankingInfoInMlRankelonr
      elonxtelonnds FSParam[Boolelonan]("post_nux_ml_flow_scribelon_ranking_info_in_ml_rankelonr", truelon)
}
