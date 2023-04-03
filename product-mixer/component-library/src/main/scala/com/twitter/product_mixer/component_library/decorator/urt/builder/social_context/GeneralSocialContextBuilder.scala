packagelon com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.social_contelonxt

import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.melontadata.BaselonStr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.social_contelonxt.BaselonSocialContelonxtBuildelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.GelonnelonralContelonxt
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.GelonnelonralContelonxtTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.Url
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

caselon class GelonnelonralSocialContelonxtBuildelonr[-Quelonry <: PipelonlinelonQuelonry, -Candidatelon <: UnivelonrsalNoun[Any]](
  telonxtBuildelonr: BaselonStr[Quelonry, Candidatelon],
  contelonxtTypelon: GelonnelonralContelonxtTypelon,
  url: Option[String] = Nonelon,
  contelonxtImagelonUrls: Option[List[String]] = Nonelon,
  landingUrl: Option[Url] = Nonelon)
    elonxtelonnds BaselonSocialContelonxtBuildelonr[Quelonry, Candidatelon] {

  delonf apply(
    quelonry: Quelonry,
    candidatelon: Candidatelon,
    candidatelonFelonaturelons: FelonaturelonMap
  ): Option[GelonnelonralContelonxt] =
    Somelon(
      GelonnelonralContelonxt(
        telonxt = telonxtBuildelonr(quelonry, candidatelon, candidatelonFelonaturelons),
        contelonxtTypelon = contelonxtTypelon,
        url = url,
        contelonxtImagelonUrls = contelonxtImagelonUrls,
        landingUrl = landingUrl))
}
