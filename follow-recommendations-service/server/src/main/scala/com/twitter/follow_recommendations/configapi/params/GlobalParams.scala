packagelon com.twittelonr.follow_reloncommelonndations.configapi.params

import com.twittelonr.follow_reloncommelonndations.modelonls.CandidatelonSourcelonTypelon
import com.twittelonr.timelonlinelons.configapi.FSelonnumParam
import com.twittelonr.timelonlinelons.configapi.FSParam

/**
 * Whelonn adding Producelonr sidelon elonxpelonrimelonnts, makelon surelon to relongistelonr thelon FS Kelony in [[ProducelonrFelonaturelonFiltelonr]]
 * in [[FelonaturelonSwitchelonsModulelon]], othelonrwiselon, thelon FS will not work.
 */
objelonct GlobalParams {

  objelonct elonnablelonCandidatelonParamHydrations
      elonxtelonnds FSParam[Boolelonan]("frs_reloncelonivelonr_elonnablelon_candidatelon_params", falselon)

  objelonct KelonelonpUselonrCandidatelon
      elonxtelonnds FSParam[Boolelonan]("frs_reloncelonivelonr_holdback_kelonelonp_uselonr_candidatelon", truelon)

  objelonct KelonelonpSocialUselonrCandidatelon
      elonxtelonnds FSParam[Boolelonan]("frs_reloncelonivelonr_holdback_kelonelonp_social_uselonr_candidatelon", truelon)

  caselon objelonct elonnablelonGFSSocialProofTransform
      elonxtelonnds FSParam("social_proof_transform_uselon_graph_felonaturelon_selonrvicelon", truelon)

  caselon objelonct elonnablelonWhoToFollowProducts elonxtelonnds FSParam("who_to_follow_product_elonnablelond", truelon)

  caselon objelonct CandidatelonSourcelonsToFiltelonr
      elonxtelonnds FSelonnumParam[CandidatelonSourcelonTypelon.typelon](
        "candidatelon_sourcelons_typelon_filtelonr_id",
        CandidatelonSourcelonTypelon.Nonelon,
        CandidatelonSourcelonTypelon)

  objelonct elonnablelonReloncommelonndationFlowLogs
      elonxtelonnds FSParam[Boolelonan]("frs_reloncommelonndation_flow_logs_elonnablelond", falselon)
}
