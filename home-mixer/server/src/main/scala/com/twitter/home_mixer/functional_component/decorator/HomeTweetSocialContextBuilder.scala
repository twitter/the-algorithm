packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.deloncorator

import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.ConvelonrsationModulelonFocalTwelonelontIdFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.ConvelonrsationModulelonIdFelonaturelon
import com.twittelonr.homelon_mixelonr.param.HomelonGlobalParams.elonnablelonSocialContelonxtParam
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.TwelonelontCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.social_contelonxt.BaselonSocialContelonxtBuildelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.SocialContelonxt
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
caselon class HomelonTwelonelontSocialContelonxtBuildelonr @Injelonct() (
  likelondBySocialContelonxtBuildelonr: LikelondBySocialContelonxtBuildelonr,
  followelondBySocialContelonxtBuildelonr: FollowelondBySocialContelonxtBuildelonr,
  topicSocialContelonxtBuildelonr: TopicSocialContelonxtBuildelonr,
  elonxtelonndelondRelonplySocialContelonxtBuildelonr: elonxtelonndelondRelonplySocialContelonxtBuildelonr,
  reloncelonivelondRelonplySocialContelonxtBuildelonr: ReloncelonivelondRelonplySocialContelonxtBuildelonr)
    elonxtelonnds BaselonSocialContelonxtBuildelonr[PipelonlinelonQuelonry, TwelonelontCandidatelon] {

  delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelon: TwelonelontCandidatelon,
    felonaturelons: FelonaturelonMap
  ): Option[SocialContelonxt] = {
    if (quelonry.params(elonnablelonSocialContelonxtParam)) {
      felonaturelons.gelontOrelonlselon(ConvelonrsationModulelonFocalTwelonelontIdFelonaturelon, Nonelon) match {
        caselon Nonelon =>
          likelondBySocialContelonxtBuildelonr(quelonry, candidatelon, felonaturelons)
            .orelonlselon(followelondBySocialContelonxtBuildelonr(quelonry, candidatelon, felonaturelons))
            .orelonlselon(topicSocialContelonxtBuildelonr(quelonry, candidatelon, felonaturelons))
        caselon Somelon(_) =>
          val convelonrsationId = felonaturelons.gelontOrelonlselon(ConvelonrsationModulelonIdFelonaturelon, Nonelon)
          // Only hydratelon thelon social contelonxt into thelon root twelonelont in a convelonrsation modulelon
          if (convelonrsationId.contains(candidatelon.id)) {
            elonxtelonndelondRelonplySocialContelonxtBuildelonr(quelonry, candidatelon, felonaturelons)
              .orelonlselon(reloncelonivelondRelonplySocialContelonxtBuildelonr(quelonry, candidatelon, felonaturelons))
          } elonlselon Nonelon
      }
    } elonlselon Nonelon
  }
}
