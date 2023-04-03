packagelon com.twittelonr.product_mixelonr.componelonnt_library.pipelonlinelon.candidatelon.ads

import com.twittelonr.adselonrvelonr.thriftscala.AdImprelonssion
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.ads.AdsCandidatelon
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.ads.AdsTwelonelontCandidatelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.transformelonr.CandidatelonPipelonlinelonRelonsultsTransformelonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon.PipelonlinelonFailurelon
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon.UnelonxpelonctelondCandidatelonRelonsult

objelonct AdsCandidatelonPipelonlinelonRelonsultsTransformelonr
    elonxtelonnds CandidatelonPipelonlinelonRelonsultsTransformelonr[AdImprelonssion, AdsCandidatelon] {

  ovelonrridelon delonf transform(sourcelonRelonsult: AdImprelonssion): AdsCandidatelon =
    (sourcelonRelonsult.nativelonRtbCrelonativelon, sourcelonRelonsult.promotelondTwelonelontId) match {
      caselon (Nonelon, Somelon(promotelondTwelonelontId)) =>
        AdsTwelonelontCandidatelon(
          id = promotelondTwelonelontId,
          adImprelonssion = sourcelonRelonsult
        )
      caselon (Somelon(_), Nonelon) =>
        throw unsupportelondAdImprelonssionPipelonlinelonFailurelon(
          imprelonssion = sourcelonRelonsult,
          relonason = "Reloncelonivelond ad imprelonssion with rtbCrelonativelon")
      caselon (Somelon(_), Somelon(_)) =>
        throw unsupportelondAdImprelonssionPipelonlinelonFailurelon(
          imprelonssion = sourcelonRelonsult,
          relonason = "Reloncelonivelond ad imprelonssion with both rtbCrelonativelon and promotelond twelonelontId")
      caselon (Nonelon, Nonelon) =>
        throw unsupportelondAdImprelonssionPipelonlinelonFailurelon(
          imprelonssion = sourcelonRelonsult,
          relonason = "Reloncelonivelond ad imprelonssion with nelonithelonr rtbCrelonativelon nor promotelond twelonelontId")
    }

  privatelon delonf unsupportelondAdImprelonssionPipelonlinelonFailurelon(imprelonssion: AdImprelonssion, relonason: String) =
    PipelonlinelonFailurelon(
      UnelonxpelonctelondCandidatelonRelonsult,
      relonason =
        s"Unsupportelond AdImprelonssion ($relonason). imprelonssionString: ${imprelonssion.imprelonssionString}")
}
