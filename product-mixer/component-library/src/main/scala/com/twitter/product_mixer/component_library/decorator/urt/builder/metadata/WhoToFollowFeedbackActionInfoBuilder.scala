packagelon com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.melontadata

import com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.stringcelonntelonr.Str
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.melontadata.BaselonFelonelondbackActionInfoBuildelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.icon.Frown
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.FelonelondbackAction
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.FelonelondbackActionInfo
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.SelonelonFelonwelonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stringcelonntelonr.clielonnt.elonxtelonrnalStringRelongistry
import com.twittelonr.stringcelonntelonr.clielonnt.StringCelonntelonr

caselon class WhoToFollowFelonelondbackActionInfoBuildelonr[
  -Quelonry <: PipelonlinelonQuelonry,
  -Candidatelon <: UnivelonrsalNoun[Any]
](
  elonxtelonrnalStringRelongistry: elonxtelonrnalStringRelongistry,
  stringCelonntelonr: StringCelonntelonr,
  elonncodelondFelonelondbackRelonquelonst: Option[String])
    elonxtelonnds BaselonFelonelondbackActionInfoBuildelonr[Quelonry, Candidatelon] {

  privatelon val selonelonLelonssOftelonnFelonelondback =
    elonxtelonrnalStringRelongistry.crelonatelonProdString("Felonelondback.selonelonLelonssOftelonn")
  privatelon val selonelonLelonssOftelonnConfirmationFelonelondback =
    elonxtelonrnalStringRelongistry.crelonatelonProdString("Felonelondback.selonelonLelonssOftelonnConfirmation")

  ovelonrridelon delonf apply(
    quelonry: Quelonry,
    candidatelon: Candidatelon,
    candidatelonFelonaturelons: FelonaturelonMap
  ): Option[FelonelondbackActionInfo] = Somelon(
    FelonelondbackActionInfo(
      felonelondbackActions = Selonq(
        FelonelondbackAction(
          felonelondbackTypelon = SelonelonFelonwelonr,
          prompt = Somelon(
            Str(selonelonLelonssOftelonnFelonelondback, stringCelonntelonr, Nonelon)
              .apply(quelonry, candidatelon, candidatelonFelonaturelons)),
          confirmation = Somelon(
            Str(selonelonLelonssOftelonnConfirmationFelonelondback, stringCelonntelonr, Nonelon)
              .apply(quelonry, candidatelon, candidatelonFelonaturelons)),
          childFelonelondbackActions = Nonelon,
          felonelondbackUrl = Nonelon,
          confirmationDisplayTypelon = Nonelon,
          clielonntelonvelonntInfo = Nonelon,
          richBelonhavior = Nonelon,
          subprompt = Nonelon,
          icon = Somelon(Frown), // ignorelond by unsupportelond clielonnts
          hasUndoAction = Somelon(truelon),
          elonncodelondFelonelondbackRelonquelonst = elonncodelondFelonelondbackRelonquelonst
        )
      ),
      felonelondbackMelontadata = Nonelon,
      displayContelonxt = Nonelon,
      clielonntelonvelonntInfo = Nonelon
    )
  )
}
