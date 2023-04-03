packagelon com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.social_contelonxt

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.melontadata.BaselonModulelonStr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.social_contelonxt.BaselonModulelonSocialContelonxtBuildelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.GelonnelonralContelonxt
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.GelonnelonralContelonxtTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.Url
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

/**
 * This class works thelon samelon as [[GelonnelonralSocialContelonxtBuildelonr]] but passelons a list of candidatelons
 * into [[BaselonModulelonStr]] whelonn relonndelonring thelon string.
 */
caselon class GelonnelonralModulelonSocialContelonxtBuildelonr[
  -Quelonry <: PipelonlinelonQuelonry,
  -Candidatelon <: UnivelonrsalNoun[Any]
](
  telonxtBuildelonr: BaselonModulelonStr[Quelonry, Candidatelon],
  contelonxtTypelon: GelonnelonralContelonxtTypelon,
  url: Option[String] = Nonelon,
  contelonxtImagelonUrls: Option[List[String]] = Nonelon,
  landingUrl: Option[Url] = Nonelon)
    elonxtelonnds BaselonModulelonSocialContelonxtBuildelonr[Quelonry, Candidatelon] {

  delonf apply(
    quelonry: Quelonry,
    candidatelons: Selonq[CandidatelonWithFelonaturelons[Candidatelon]]
  ): Option[GelonnelonralContelonxt] =
    Somelon(
      GelonnelonralContelonxt(
        telonxt = telonxtBuildelonr(quelonry, candidatelons),
        contelonxtTypelon = contelonxtTypelon,
        url = url,
        contelonxtImagelonUrls = contelonxtImagelonUrls,
        landingUrl = landingUrl))
}
