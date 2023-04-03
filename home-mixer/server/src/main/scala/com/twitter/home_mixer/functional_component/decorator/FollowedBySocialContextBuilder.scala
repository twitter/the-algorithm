packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.deloncorator

import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.InNelontworkFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.SGSValidFollowelondByUselonrIdsFelonaturelon
import com.twittelonr.homelon_mixelonr.product.following.modelonl.HomelonMixelonrelonxtelonrnalStrings
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.TwelonelontCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.social_contelonxt.BaselonSocialContelonxtBuildelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata._
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.product_mixelonr.corelon.product.guicelon.scopelon.ProductScopelond
import com.twittelonr.stringcelonntelonr.clielonnt.StringCelonntelonr
import javax.injelonct.Injelonct
import javax.injelonct.Providelonr
import javax.injelonct.Singlelonton

@Singlelonton
caselon class FollowelondBySocialContelonxtBuildelonr @Injelonct() (
  elonxtelonrnalStrings: HomelonMixelonrelonxtelonrnalStrings,
  @ProductScopelond stringCelonntelonrProvidelonr: Providelonr[StringCelonntelonr])
    elonxtelonnds BaselonSocialContelonxtBuildelonr[PipelonlinelonQuelonry, TwelonelontCandidatelon] {

  privatelon val stringCelonntelonr = stringCelonntelonrProvidelonr.gelont()

  privatelon val elonngagelonrSocialContelonxtBuildelonr = elonngagelonrSocialContelonxtBuildelonr(
    contelonxtTypelon = FollowGelonnelonralContelonxtTypelon,
    stringCelonntelonr = stringCelonntelonr,
    onelonUselonrString = elonxtelonrnalStrings.socialContelonxtOnelonUselonrFollowsString,
    twoUselonrsString = elonxtelonrnalStrings.socialContelonxtTwoUselonrsFollowString,
    morelonUselonrsString = elonxtelonrnalStrings.socialContelonxtMorelonUselonrsFollowString,
    timelonlinelonTitlelon = elonxtelonrnalStrings.socialContelonxtFollowelondByTimelonlinelonTitlelon
  )

  delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelon: TwelonelontCandidatelon,
    candidatelonFelonaturelons: FelonaturelonMap
  ): Option[SocialContelonxt] = {
    // Only apply followelond-by social contelonxt for OON Twelonelonts
    val inNelontwork = candidatelonFelonaturelons.gelontOrelonlselon(InNelontworkFelonaturelon, truelon)
    if (!inNelontwork) {
      val validFollowelondByUselonrIds =
        candidatelonFelonaturelons.gelontOrelonlselon(SGSValidFollowelondByUselonrIdsFelonaturelon, Nil)
      elonngagelonrSocialContelonxtBuildelonr(
        socialContelonxtIds = validFollowelondByUselonrIds,
        quelonry = quelonry,
        candidatelonFelonaturelons = candidatelonFelonaturelons
      )
    } elonlselon {
      Nonelon
    }
  }
}
