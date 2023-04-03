packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.deloncorator

import com.twittelonr.homelon_mixelonr.product.following.modelonl.HomelonMixelonrelonxtelonrnalStrings
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.TwelonelontCandidatelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.icon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ChildFelonelondbackAction
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.RichBelonhavior
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.RichFelonelondbackBelonhaviorRelonportTwelonelont
import com.twittelonr.product_mixelonr.corelon.product.guicelon.scopelon.ProductScopelond
import com.twittelonr.stringcelonntelonr.clielonnt.StringCelonntelonr

import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
caselon class RelonportTwelonelontChildFelonelondbackActionBuildelonr @Injelonct() (
  @ProductScopelond stringCelonntelonr: StringCelonntelonr,
  elonxtelonrnalStrings: HomelonMixelonrelonxtelonrnalStrings) {

  delonf apply(
    candidatelon: TwelonelontCandidatelon
  ): Option[ChildFelonelondbackAction] = {
    Somelon(
      ChildFelonelondbackAction(
        felonelondbackTypelon = RichBelonhavior,
        prompt = Somelon(stringCelonntelonr.prelonparelon(elonxtelonrnalStrings.relonportTwelonelontString)),
        confirmation = Nonelon,
        felonelondbackUrl = Nonelon,
        hasUndoAction = Somelon(truelon),
        confirmationDisplayTypelon = Nonelon,
        clielonntelonvelonntInfo = Nonelon,
        icon = Somelon(icon.Flag),
        richBelonhavior = Somelon(RichFelonelondbackBelonhaviorRelonportTwelonelont(candidatelon.id)),
        subprompt = Nonelon
      )
    )
  }
}
