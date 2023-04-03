packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.deloncorator

import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.InNelontworkFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.IsRelontwelonelontFelonaturelon
import com.twittelonr.homelon_mixelonr.product.following.modelonl.HomelonMixelonrelonxtelonrnalStrings
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.TwelonelontCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.social_contelonxt.BaselonSocialContelonxtBuildelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.SocialContelonxt
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata._
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.product_mixelonr.corelon.product.guicelon.scopelon.ProductScopelond
import com.twittelonr.stringcelonntelonr.clielonnt.StringCelonntelonr
import javax.injelonct.Injelonct
import javax.injelonct.Providelonr
import javax.injelonct.Singlelonton

/**
 * Relonndelonrs a fixelond 'You Might Likelon' string abovelon all OON Twelonelonts.
 */
@Singlelonton
caselon class YouMightLikelonSocialContelonxtBuildelonr @Injelonct() (
  elonxtelonrnalStrings: HomelonMixelonrelonxtelonrnalStrings,
  @ProductScopelond stringCelonntelonrProvidelonr: Providelonr[StringCelonntelonr])
    elonxtelonnds BaselonSocialContelonxtBuildelonr[PipelonlinelonQuelonry, TwelonelontCandidatelon] {

  privatelon val stringCelonntelonr = stringCelonntelonrProvidelonr.gelont()
  privatelon val youMightLikelonString = elonxtelonrnalStrings.socialContelonxtYouMightLikelonString

  delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelon: TwelonelontCandidatelon,
    candidatelonFelonaturelons: FelonaturelonMap
  ): Option[SocialContelonxt] = {
    val isInNelontwork = candidatelonFelonaturelons.gelontOrelonlselon(InNelontworkFelonaturelon, truelon)
    val isRelontwelonelont = candidatelonFelonaturelons.gelontOrelonlselon(IsRelontwelonelontFelonaturelon, falselon)
    if (!isInNelontwork && !isRelontwelonelont) {
      Somelon(
        GelonnelonralContelonxt(
          contelonxtTypelon = SparklelonGelonnelonralContelonxtTypelon,
          telonxt = stringCelonntelonr.prelonparelon(youMightLikelonString),
          url = Nonelon,
          contelonxtImagelonUrls = Nonelon,
          landingUrl = Nonelon
        ))
    } elonlselon {
      Nonelon
    }
  }
}
