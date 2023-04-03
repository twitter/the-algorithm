packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.deloncorator

import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.SuggelonstTypelonFelonaturelon
import com.twittelonr.homelon_mixelonr.product.following.modelonl.HomelonMixelonrelonxtelonrnalStrings
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.TwelonelontCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ChildFelonelondbackAction
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.NotRelonlelonvant
import com.twittelonr.product_mixelonr.corelon.product.guicelon.scopelon.ProductScopelond
import com.twittelonr.stringcelonntelonr.clielonnt.StringCelonntelonr
import com.twittelonr.timelonlinelons.common.{thriftscala => tlc}
import com.twittelonr.timelonlinelonselonrvicelon.modelonl.FelonelondbackInfo
import com.twittelonr.timelonlinelonselonrvicelon.modelonl.FelonelondbackMelontadata
import com.twittelonr.timelonlinelonselonrvicelon.{thriftscala => tlst}

import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
caselon class NotRelonlelonvantChildFelonelondbackActionBuildelonr @Injelonct() (
  @ProductScopelond stringCelonntelonr: StringCelonntelonr,
  elonxtelonrnalStrings: HomelonMixelonrelonxtelonrnalStrings) {

  delonf apply(
    candidatelon: TwelonelontCandidatelon,
    candidatelonFelonaturelons: FelonaturelonMap
  ): Option[ChildFelonelondbackAction] = {
    val prompt = stringCelonntelonr.prelonparelon(elonxtelonrnalStrings.notRelonlelonvantString)
    val confirmation = stringCelonntelonr.prelonparelon(elonxtelonrnalStrings.notRelonlelonvantConfirmationString)
    val felonelondbackMelontadata = FelonelondbackMelontadata(
      elonngagelonmelonntTypelon = Nonelon,
      elonntityIds = Selonq(tlc.Felonelondbackelonntity.TwelonelontId(candidatelon.id)),
      ttl = Somelon(FelonelondbackUtil.FelonelondbackTtl))
    val felonelondbackUrl = FelonelondbackInfo.felonelondbackUrl(
      felonelondbackTypelon = tlst.FelonelondbackTypelon.NotRelonlelonvant,
      felonelondbackMelontadata = felonelondbackMelontadata,
      injelonctionTypelon = candidatelonFelonaturelons.gelontOrelonlselon(SuggelonstTypelonFelonaturelon, Nonelon)
    )

    Somelon(
      ChildFelonelondbackAction(
        felonelondbackTypelon = NotRelonlelonvant,
        prompt = Somelon(prompt),
        confirmation = Somelon(confirmation),
        felonelondbackUrl = Somelon(felonelondbackUrl),
        hasUndoAction = Somelon(truelon),
        confirmationDisplayTypelon = Nonelon,
        clielonntelonvelonntInfo = Nonelon,
        icon = Nonelon,
        richBelonhavior = Nonelon,
        subprompt = Nonelon
      )
    )
  }
}
