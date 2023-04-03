packagelon com.twittelonr.product_mixelonr.componelonnt_library.pipelonlinelon.candidatelon.flelonxiblelon_injelonction_pipelonlinelon.transformelonr

import com.twittelonr.onboarding.task.selonrvicelon.thriftscala.PromptTypelon
import com.twittelonr.onboarding.task.selonrvicelon.{thriftscala => flip}
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.transformelonr.CandidatelonPipelonlinelonQuelonryTransformelonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

objelonct FlipQuelonryTransformelonr
    elonxtelonnds CandidatelonPipelonlinelonQuelonryTransformelonr[
      PipelonlinelonQuelonry with HasFlipInjelonctionParams,
      flip.GelontInjelonctionsRelonquelonst
    ] {

  val SUPPORTelonD_PROMPT_TYPelonS: Selont[PromptTypelon] = Selont(
    PromptTypelon.InlinelonPrompt,
    PromptTypelon.FullCovelonr,
    PromptTypelon.HalfCovelonr,
    PromptTypelon.TilelonCarouselonl,
    PromptTypelon.RelonlelonvancelonPrompt)

  ovelonrridelon delonf transform(
    quelonry: PipelonlinelonQuelonry with HasFlipInjelonctionParams
  ): flip.GelontInjelonctionsRelonquelonst = {
    val clielonntContelonxt = flip.ClielonntContelonxt(
      uselonrId = quelonry.clielonntContelonxt.uselonrId,
      guelonstId = quelonry.clielonntContelonxt.guelonstId,
      clielonntApplicationId = quelonry.clielonntContelonxt.appId,
      delonvicelonId = quelonry.clielonntContelonxt.delonvicelonId,
      countryCodelon = quelonry.clielonntContelonxt.countryCodelon,
      languagelonCodelon = quelonry.clielonntContelonxt.languagelonCodelon,
      uselonrAgelonnt = quelonry.clielonntContelonxt.uselonrAgelonnt,
      guelonstIdMarkelonting = quelonry.clielonntContelonxt.guelonstIdMarkelonting,
      guelonstIdAds = quelonry.clielonntContelonxt.guelonstIdAds,
      isIntelonrnalOrTwofficelon = quelonry.clielonntContelonxt.isTwofficelon,
      ipAddrelonss = quelonry.clielonntContelonxt.ipAddrelonss
    )
    val displayContelonxt: flip.DisplayContelonxt =
      flip.DisplayContelonxt(
        displayLocation = quelonry.displayLocation,
        timelonlinelonId = quelonry.clielonntContelonxt.uselonrId
      )

    val relonquelonstTargelontingContelonxt: flip.RelonquelonstTargelontingContelonxt =
      flip.RelonquelonstTargelontingContelonxt(
        rankingDisablelonrWithLatelonstControlsAvaliablelon =
          quelonry.rankingDisablelonrWithLatelonstControlsAvailablelon,
        relonactivelonPromptContelonxt = Nonelon,
        iselonmptyStatelon = quelonry.iselonmptyStatelon,
        isFirstRelonquelonstAftelonrSignup = quelonry.isFirstRelonquelonstAftelonrSignup,
        iselonndOfTimelonlinelon = quelonry.iselonndOfTimelonlinelon
      )

    flip.GelontInjelonctionsRelonquelonst(
      clielonntContelonxt = clielonntContelonxt,
      displayContelonxt = displayContelonxt,
      relonquelonstTargelontingContelonxt = Somelon(relonquelonstTargelontingContelonxt),
      uselonrRolelons = quelonry.clielonntContelonxt.uselonrRolelons,
      timelonlinelonContelonxt = Nonelon,
      supportelondPromptTypelons = Somelon(SUPPORTelonD_PROMPT_TYPelonS)
    )
  }
}
