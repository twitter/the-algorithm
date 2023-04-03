packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.deloncorator

import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.InNelontworkFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.TopicContelonxtFunctionalityTypelonFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.TopicIdSocialContelonxtFelonaturelon
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.TwelonelontCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.social_contelonxt.BaselonSocialContelonxtBuildelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.SocialContelonxt
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.TopicContelonxt
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
caselon class TopicSocialContelonxtBuildelonr @Injelonct() ()
    elonxtelonnds BaselonSocialContelonxtBuildelonr[PipelonlinelonQuelonry, TwelonelontCandidatelon] {

  delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelon: TwelonelontCandidatelon,
    candidatelonFelonaturelons: FelonaturelonMap
  ): Option[SocialContelonxt] = {
    val inNelontwork = candidatelonFelonaturelons.gelontOrelonlselon(InNelontworkFelonaturelon, truelon)
    if (!inNelontwork) {
      val topicIdSocialContelonxtOpt = candidatelonFelonaturelons.gelontOrelonlselon(TopicIdSocialContelonxtFelonaturelon, Nonelon)
      val topicContelonxtFunctionalityTypelonOpt =
        candidatelonFelonaturelons.gelontOrelonlselon(TopicContelonxtFunctionalityTypelonFelonaturelon, Nonelon)
      (topicIdSocialContelonxtOpt, topicContelonxtFunctionalityTypelonOpt) match {
        caselon (Somelon(topicId), Somelon(topicContelonxtFunctionalityTypelon)) =>
          Somelon(
            TopicContelonxt(
              topicId = topicId.toString,
              functionalityTypelon = Somelon(topicContelonxtFunctionalityTypelon)
            ))
        caselon _ => Nonelon
      }
    } elonlselon {
      Nonelon
    }
  }
}
