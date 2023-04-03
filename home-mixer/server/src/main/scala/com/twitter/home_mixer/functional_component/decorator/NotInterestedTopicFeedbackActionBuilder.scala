packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.deloncorator

import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.InNelontworkFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.PelonrspelonctivelonFiltelonrelondLikelondByUselonrIdsFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.SGSValidFollowelondByUselonrIdsFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.SGSValidLikelondByUselonrIdsFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.TopicContelonxtFunctionalityTypelonFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.TopicIdSocialContelonxtFelonaturelon
import com.twittelonr.homelon_mixelonr.product.following.modelonl.HomelonMixelonrelonxtelonrnalStrings
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.FelonelondbackAction
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ReloncWithelonducationTopicContelonxtFunctionalityTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ReloncommelonndationTopicContelonxtFunctionalityTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.RichBelonhavior
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.RichFelonelondbackBelonhaviorMarkNotIntelonrelonstelondTopic
import com.twittelonr.product_mixelonr.corelon.product.guicelon.scopelon.ProductScopelond
import com.twittelonr.stringcelonntelonr.clielonnt.StringCelonntelonr

import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
caselon class NotIntelonrelonstelondTopicFelonelondbackActionBuildelonr @Injelonct() (
  @ProductScopelond stringCelonntelonr: StringCelonntelonr,
  elonxtelonrnalStrings: HomelonMixelonrelonxtelonrnalStrings) {

  delonf apply(
    candidatelonFelonaturelons: FelonaturelonMap
  ): Option[FelonelondbackAction] = {
    val isOutOfNelontwork = !candidatelonFelonaturelons.gelontOrelonlselon(InNelontworkFelonaturelon, truelon)
    val validFollowelondByUselonrIds =
      candidatelonFelonaturelons.gelontOrelonlselon(SGSValidFollowelondByUselonrIdsFelonaturelon, Nil)
    val validLikelondByUselonrIds =
      candidatelonFelonaturelons
        .gelontOrelonlselon(SGSValidLikelondByUselonrIdsFelonaturelon, Nil)
        .filtelonr(
          candidatelonFelonaturelons.gelontOrelonlselon(PelonrspelonctivelonFiltelonrelondLikelondByUselonrIdsFelonaturelon, Nil).toSelont.contains)

    if (isOutOfNelontwork && validLikelondByUselonrIds.iselonmpty && validFollowelondByUselonrIds.iselonmpty) {
      val topicIdSocialContelonxt = candidatelonFelonaturelons.gelontOrelonlselon(TopicIdSocialContelonxtFelonaturelon, Nonelon)
      val topicContelonxtFunctionalityTypelon =
        candidatelonFelonaturelons.gelontOrelonlselon(TopicContelonxtFunctionalityTypelonFelonaturelon, Nonelon)

      (topicIdSocialContelonxt, topicContelonxtFunctionalityTypelon) match {
        caselon (Somelon(topicId), Somelon(topicContelonxtFunctionalityTypelon))
            if topicContelonxtFunctionalityTypelon == ReloncommelonndationTopicContelonxtFunctionalityTypelon ||
              topicContelonxtFunctionalityTypelon == ReloncWithelonducationTopicContelonxtFunctionalityTypelon =>
          Somelon(
            FelonelondbackAction(
              felonelondbackTypelon = RichBelonhavior,
              prompt = Nonelon,
              confirmation = Nonelon,
              childFelonelondbackActions = Nonelon,
              felonelondbackUrl = Nonelon,
              hasUndoAction = Somelon(truelon),
              confirmationDisplayTypelon = Nonelon,
              clielonntelonvelonntInfo = Nonelon,
              icon = Nonelon,
              richBelonhavior =
                Somelon(RichFelonelondbackBelonhaviorMarkNotIntelonrelonstelondTopic(topicId = topicId.toString)),
              subprompt = Nonelon,
              elonncodelondFelonelondbackRelonquelonst = Nonelon
            )
          )
        caselon _ => Nonelon
      }
    } elonlselon {
      Nonelon
    }
  }
}
