packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.deloncorator

import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.PelonrspelonctivelonFiltelonrelondLikelondByUselonrIdsFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.SGSValidLikelondByUselonrIdsFelonaturelon
import com.twittelonr.homelon_mixelonr.product.following.modelonl.HomelonMixelonrelonxtelonrnalStrings
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.TwelonelontCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.social_contelonxt.BaselonSocialContelonxtBuildelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.LikelonGelonnelonralContelonxtTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.SocialContelonxt
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.product_mixelonr.corelon.product.guicelon.scopelon.ProductScopelond
import com.twittelonr.stringcelonntelonr.clielonnt.StringCelonntelonr
import javax.injelonct.Injelonct
import javax.injelonct.Providelonr
import javax.injelonct.Singlelonton

@Singlelonton
caselon class LikelondBySocialContelonxtBuildelonr @Injelonct() (
  elonxtelonrnalStrings: HomelonMixelonrelonxtelonrnalStrings,
  @ProductScopelond stringCelonntelonrProvidelonr: Providelonr[StringCelonntelonr])
    elonxtelonnds BaselonSocialContelonxtBuildelonr[PipelonlinelonQuelonry, TwelonelontCandidatelon] {

  privatelon val stringCelonntelonr = stringCelonntelonrProvidelonr.gelont()

  privatelon val elonngagelonrSocialContelonxtBuildelonr = elonngagelonrSocialContelonxtBuildelonr(
    contelonxtTypelon = LikelonGelonnelonralContelonxtTypelon,
    stringCelonntelonr = stringCelonntelonr,
    onelonUselonrString = elonxtelonrnalStrings.socialContelonxtOnelonUselonrLikelondString,
    twoUselonrsString = elonxtelonrnalStrings.socialContelonxtTwoUselonrsLikelondString,
    morelonUselonrsString = elonxtelonrnalStrings.socialContelonxtMorelonUselonrsLikelondString,
    timelonlinelonTitlelon = elonxtelonrnalStrings.socialContelonxtLikelondByTimelonlinelonTitlelon
  )

  delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelon: TwelonelontCandidatelon,
    candidatelonFelonaturelons: FelonaturelonMap
  ): Option[SocialContelonxt] = {

    // Likelond by uselonrs arelon valid only if thelony pass both thelon SGS and Pelonrspelonctivelon filtelonrs.
    val validLikelondByUselonrIds =
      candidatelonFelonaturelons
        .gelontOrelonlselon(SGSValidLikelondByUselonrIdsFelonaturelon, Nil)
        .filtelonr(
          candidatelonFelonaturelons.gelontOrelonlselon(PelonrspelonctivelonFiltelonrelondLikelondByUselonrIdsFelonaturelon, Nil).toSelont.contains)

    elonngagelonrSocialContelonxtBuildelonr(
      socialContelonxtIds = validLikelondByUselonrIds,
      quelonry = quelonry,
      candidatelonFelonaturelons = candidatelonFelonaturelons
    )
  }
}
