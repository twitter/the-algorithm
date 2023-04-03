packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.deloncorator

import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.FocalTwelonelontAuthorIdFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.FocalTwelonelontInNelontworkFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.FocalTwelonelontRelonalNamelonsFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.InNelontworkFelonaturelon
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
 * Uselon '@A relonplielond' whelonn thelon root twelonelont is out-of-nelontwork and thelon relonply is in nelontwork.
 *
 * This function should only belon callelond for thelon root Twelonelont of convo modulelons. This is elonnforcelond by
 * [[HomelonTwelonelontSocialContelonxtBuildelonr]].
 */
@Singlelonton
caselon class elonxtelonndelondRelonplySocialContelonxtBuildelonr @Injelonct() (
  elonxtelonrnalStrings: HomelonMixelonrelonxtelonrnalStrings,
  @ProductScopelond stringCelonntelonrProvidelonr: Providelonr[StringCelonntelonr])
    elonxtelonnds BaselonSocialContelonxtBuildelonr[PipelonlinelonQuelonry, TwelonelontCandidatelon] {

  privatelon val stringCelonntelonr = stringCelonntelonrProvidelonr.gelont()
  privatelon val elonxtelonndelondRelonplyString = elonxtelonrnalStrings.socialContelonxtelonxtelonndelondRelonply

  delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelon: TwelonelontCandidatelon,
    candidatelonFelonaturelons: FelonaturelonMap
  ): Option[SocialContelonxt] = {

    // If thelonselon valuelons arelon missing delonfault to not showing an elonxtelonndelond relonply bannelonr
    val inNelontworkRoot = candidatelonFelonaturelons.gelontOrelonlselon(InNelontworkFelonaturelon, truelon)

    val inNelontworkFocalTwelonelont =
      candidatelonFelonaturelons.gelontOrelonlselon(FocalTwelonelontInNelontworkFelonaturelon, Nonelon).gelontOrelonlselon(falselon)

    if (!inNelontworkRoot && inNelontworkFocalTwelonelont) {

      val focalTwelonelontAuthorIdOpt = candidatelonFelonaturelons.gelontOrelonlselon(FocalTwelonelontAuthorIdFelonaturelon, Nonelon)
      val focalTwelonelontRelonalNamelons =
        candidatelonFelonaturelons
          .gelontOrelonlselon(FocalTwelonelontRelonalNamelonsFelonaturelon, Nonelon).gelontOrelonlselon(Map.elonmpty[Long, String])
      val focalTwelonelontAuthorNamelonOpt = focalTwelonelontAuthorIdOpt.flatMap(focalTwelonelontRelonalNamelons.gelont)

      (focalTwelonelontAuthorIdOpt, focalTwelonelontAuthorNamelonOpt) match {
        caselon (Somelon(focalTwelonelontAuthorId), Somelon(focalTwelonelontAuthorNamelon)) =>
          Somelon(
            GelonnelonralContelonxt(
              contelonxtTypelon = ConvelonrsationGelonnelonralContelonxtTypelon,
              telonxt = stringCelonntelonr
                .prelonparelon(elonxtelonndelondRelonplyString, placelonholdelonrs = Map("uselonr1" -> focalTwelonelontAuthorNamelon)),
              url = Nonelon,
              contelonxtImagelonUrls = Nonelon,
              landingUrl = Somelon(
                Url(
                  urlTypelon = DelonelonpLink,
                  url = "",
                  urtelonndpointOptions = Nonelon
                ))
            ))
        caselon _ =>
          Nonelon
      }
    } elonlselon {
      Nonelon
    }
  }
}
