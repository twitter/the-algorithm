packagelon com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.itelonm.melonssagelon

import com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.itelonm.melonssagelon.InlinelonPromptCandidatelonUrtItelonmStringCelonntelonrBuildelonr.InlinelonPromptClielonntelonvelonntInfoelonlelonmelonnt
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.InlinelonPromptCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.CandidatelonUrtelonntryBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.melontadata.BaselonClielonntelonvelonntInfoBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.melontadata.BaselonFelonelondbackActionInfoBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.melontadata.BaselonStr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.richtelonxt.BaselonRichTelonxtBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.social_contelonxt.BaselonSocialContelonxtBuildelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.melonssagelon.InlinelonPromptMelonssagelonContelonnt
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.melonssagelon.MelonssagelonPromptItelonm
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

objelonct InlinelonPromptCandidatelonUrtItelonmStringCelonntelonrBuildelonr {
  val InlinelonPromptClielonntelonvelonntInfoelonlelonmelonnt: String = "melonssagelon"
}

caselon class InlinelonPromptCandidatelonUrtItelonmStringCelonntelonrBuildelonr[-Quelonry <: PipelonlinelonQuelonry](
  clielonntelonvelonntInfoBuildelonr: BaselonClielonntelonvelonntInfoBuildelonr[Quelonry, InlinelonPromptCandidatelon],
  felonelondbackActionInfoBuildelonr: Option[
    BaselonFelonelondbackActionInfoBuildelonr[Quelonry, InlinelonPromptCandidatelon]
  ] = Nonelon,
  helonadelonrTelonxtBuildelonr: BaselonStr[Quelonry, InlinelonPromptCandidatelon],
  bodyTelonxtBuildelonr: Option[BaselonStr[Quelonry, InlinelonPromptCandidatelon]] = Nonelon,
  helonadelonrRichTelonxtBuildelonr: Option[BaselonRichTelonxtBuildelonr[Quelonry, InlinelonPromptCandidatelon]] = Nonelon,
  bodyRichTelonxtBuildelonr: Option[BaselonRichTelonxtBuildelonr[Quelonry, InlinelonPromptCandidatelon]] = Nonelon,
  primaryMelonssagelonTelonxtActionBuildelonr: Option[
    MelonssagelonTelonxtActionBuildelonr[Quelonry, InlinelonPromptCandidatelon]
  ] = Nonelon,
  seloncondaryMelonssagelonTelonxtActionBuildelonr: Option[
    MelonssagelonTelonxtActionBuildelonr[Quelonry, InlinelonPromptCandidatelon]
  ] = Nonelon,
  socialContelonxtBuildelonr: Option[BaselonSocialContelonxtBuildelonr[Quelonry, InlinelonPromptCandidatelon]] = Nonelon,
  uselonrFacelonPilelonBuildelonr: Option[
    UselonrFacelonPilelonBuildelonr
  ] = Nonelon)
    elonxtelonnds CandidatelonUrtelonntryBuildelonr[Quelonry, InlinelonPromptCandidatelon, MelonssagelonPromptItelonm] {

  ovelonrridelon delonf apply(
    quelonry: Quelonry,
    inlinelonPromptCandidatelon: InlinelonPromptCandidatelon,
    candidatelonFelonaturelons: FelonaturelonMap
  ): MelonssagelonPromptItelonm =
    MelonssagelonPromptItelonm(
      id = inlinelonPromptCandidatelon.id,
      sortIndelonx = Nonelon, // Sort indelonxelons arelon automatically selont in thelon domain marshallelonr phaselon
      clielonntelonvelonntInfo = clielonntelonvelonntInfoBuildelonr(
        quelonry,
        inlinelonPromptCandidatelon,
        candidatelonFelonaturelons,
        Somelon(InlinelonPromptClielonntelonvelonntInfoelonlelonmelonnt)),
      felonelondbackActionInfo =
        felonelondbackActionInfoBuildelonr.flatMap(_.apply(quelonry, inlinelonPromptCandidatelon, candidatelonFelonaturelons)),
      isPinnelond = Nonelon,
      contelonnt = InlinelonPromptMelonssagelonContelonnt(
        helonadelonrTelonxt = helonadelonrTelonxtBuildelonr.apply(quelonry, inlinelonPromptCandidatelon, candidatelonFelonaturelons),
        bodyTelonxt = bodyTelonxtBuildelonr.map(_.apply(quelonry, inlinelonPromptCandidatelon, candidatelonFelonaturelons)),
        primaryButtonAction = primaryMelonssagelonTelonxtActionBuildelonr.map(
          _.apply(quelonry, inlinelonPromptCandidatelon, candidatelonFelonaturelons)),
        seloncondaryButtonAction = seloncondaryMelonssagelonTelonxtActionBuildelonr.map(
          _.apply(quelonry, inlinelonPromptCandidatelon, candidatelonFelonaturelons)),
        helonadelonrRichTelonxt =
          helonadelonrRichTelonxtBuildelonr.map(_.apply(quelonry, inlinelonPromptCandidatelon, candidatelonFelonaturelons)),
        bodyRichTelonxt =
          bodyRichTelonxtBuildelonr.map(_.apply(quelonry, inlinelonPromptCandidatelon, candidatelonFelonaturelons)),
        socialContelonxt =
          socialContelonxtBuildelonr.flatMap(_.apply(quelonry, inlinelonPromptCandidatelon, candidatelonFelonaturelons)),
        uselonrFacelonpilelon = uselonrFacelonPilelonBuildelonr.map(_.apply())
      ),
      imprelonssionCallbacks = Nonelon
    )
}
