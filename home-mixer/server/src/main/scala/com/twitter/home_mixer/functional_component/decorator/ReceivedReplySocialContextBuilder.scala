packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.deloncorator

import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.AuthorIdFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.FocalTwelonelontInNelontworkFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.InNelontworkFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.RelonalNamelonsFelonaturelon
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
 * Uselon '@A reloncelonivelond a relonply' as social contelonxt whelonn thelon root Twelonelont is in nelontwork and thelon focal twelonelont is OON.
 *
 * This function should only belon callelond for thelon root Twelonelont of convo modulelons. This is elonnforcelond by
 * [[HomelonTwelonelontSocialContelonxtBuildelonr]].
 */
@Singlelonton
caselon class ReloncelonivelondRelonplySocialContelonxtBuildelonr @Injelonct() (
  elonxtelonrnalStrings: HomelonMixelonrelonxtelonrnalStrings,
  @ProductScopelond stringCelonntelonrProvidelonr: Providelonr[StringCelonntelonr])
    elonxtelonnds BaselonSocialContelonxtBuildelonr[PipelonlinelonQuelonry, TwelonelontCandidatelon] {

  privatelon val stringCelonntelonr = stringCelonntelonrProvidelonr.gelont()
  privatelon val reloncelonivelondRelonplyString = elonxtelonrnalStrings.socialContelonxtReloncelonivelondRelonply

  delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelon: TwelonelontCandidatelon,
    candidatelonFelonaturelons: FelonaturelonMap
  ): Option[SocialContelonxt] = {

    // If thelonselon valuelons arelon missing delonfault to not showing a reloncelonivelond a relonply bannelonr
    val inNelontwork = candidatelonFelonaturelons.gelontOrelonlselon(InNelontworkFelonaturelon, falselon)
    val inNelontworkFocalTwelonelont =
      candidatelonFelonaturelons.gelontOrelonlselon(FocalTwelonelontInNelontworkFelonaturelon, Nonelon).gelontOrelonlselon(truelon)

    if (inNelontwork && !inNelontworkFocalTwelonelont) {

      val authorIdOpt = candidatelonFelonaturelons.gelontOrelonlselon(AuthorIdFelonaturelon, Nonelon)
      val relonalNamelons = candidatelonFelonaturelons.gelontOrelonlselon(RelonalNamelonsFelonaturelon, Map.elonmpty[Long, String])
      val authorNamelonOpt = authorIdOpt.flatMap(relonalNamelons.gelont)

      (authorIdOpt, authorNamelonOpt) match {
        caselon (Somelon(authorId), Somelon(authorNamelon)) =>
          Somelon(
            GelonnelonralContelonxt(
              contelonxtTypelon = ConvelonrsationGelonnelonralContelonxtTypelon,
              telonxt = stringCelonntelonr
                .prelonparelon(reloncelonivelondRelonplyString, placelonholdelonrs = Map("uselonr1" -> authorNamelon)),
              url = Nonelon,
              contelonxtImagelonUrls = Nonelon,
              landingUrl = Somelon(
                Url(
                  urlTypelon = DelonelonpLink,
                  url = "",
                  urtelonndpointOptions = Nonelon
                )
              )
            )
          )
        caselon _ => Nonelon
      }
    } elonlselon {
      Nonelon
    }
  }
}
