packagelon com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.itelonm.relonlelonvancelon_prompt

import com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.itelonm.relonlelonvancelon_prompt.RelonlelonvancelonPromptCandidatelonUrtItelonmStringCelonntelonrBuildelonr.RelonlelonvancelonPromptClielonntelonvelonntInfoelonlelonmelonnt
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.RelonlelonvancelonPromptCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.CandidatelonUrtelonntryBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.melontadata.BaselonClielonntelonvelonntInfoBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.melontadata.BaselonStr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.prompt.PromptItelonm
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.prompt.RelonlelonvancelonPromptContelonnt
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.prompt.RelonlelonvancelonPromptDisplayTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.prompt.RelonlelonvancelonPromptFollowUpFelonelondbackTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.Callback
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

objelonct RelonlelonvancelonPromptCandidatelonUrtItelonmStringCelonntelonrBuildelonr {
  val RelonlelonvancelonPromptClielonntelonvelonntInfoelonlelonmelonnt: String = "relonlelonvancelon_prompt"
}

caselon class RelonlelonvancelonPromptCandidatelonUrtItelonmStringCelonntelonrBuildelonr[-Quelonry <: PipelonlinelonQuelonry](
  clielonntelonvelonntInfoBuildelonr: BaselonClielonntelonvelonntInfoBuildelonr[Quelonry, RelonlelonvancelonPromptCandidatelon],
  titlelonTelonxtBuildelonr: BaselonStr[Quelonry, RelonlelonvancelonPromptCandidatelon],
  confirmationTelonxtBuildelonr: BaselonStr[Quelonry, RelonlelonvancelonPromptCandidatelon],
  isRelonlelonvantTelonxtBuildelonr: BaselonStr[Quelonry, RelonlelonvancelonPromptCandidatelon],
  notRelonlelonvantTelonxtBuildelonr: BaselonStr[Quelonry, RelonlelonvancelonPromptCandidatelon],
  displayTypelon: RelonlelonvancelonPromptDisplayTypelon,
  isRelonlelonvantCallback: Callback,
  notRelonlelonvantCallback: Callback,
  isRelonlelonvantFollowUp: Option[RelonlelonvancelonPromptFollowUpFelonelondbackTypelon] = Nonelon,
  notRelonlelonvantFollowUp: Option[RelonlelonvancelonPromptFollowUpFelonelondbackTypelon] = Nonelon,
  imprelonssionCallbacks: Option[List[Callback]] = Nonelon)
    elonxtelonnds CandidatelonUrtelonntryBuildelonr[Quelonry, RelonlelonvancelonPromptCandidatelon, PromptItelonm] {

  ovelonrridelon delonf apply(
    quelonry: Quelonry,
    relonlelonvancelonPromptCandidatelon: RelonlelonvancelonPromptCandidatelon,
    candidatelonFelonaturelons: FelonaturelonMap
  ): PromptItelonm =
    PromptItelonm(
      id = relonlelonvancelonPromptCandidatelon.id,
      sortIndelonx = Nonelon,
      clielonntelonvelonntInfo = clielonntelonvelonntInfoBuildelonr(
        quelonry,
        relonlelonvancelonPromptCandidatelon,
        candidatelonFelonaturelons,
        Somelon(RelonlelonvancelonPromptClielonntelonvelonntInfoelonlelonmelonnt)),
      felonelondbackActionInfo = Nonelon,
      contelonnt = RelonlelonvancelonPromptContelonnt(
        titlelon = titlelonTelonxtBuildelonr(quelonry, relonlelonvancelonPromptCandidatelon, candidatelonFelonaturelons),
        confirmation = confirmationTelonxtBuildelonr(quelonry, relonlelonvancelonPromptCandidatelon, candidatelonFelonaturelons),
        isRelonlelonvantTelonxt = isRelonlelonvantTelonxtBuildelonr(quelonry, relonlelonvancelonPromptCandidatelon, candidatelonFelonaturelons),
        notRelonlelonvantTelonxt =
          notRelonlelonvantTelonxtBuildelonr(quelonry, relonlelonvancelonPromptCandidatelon, candidatelonFelonaturelons),
        isRelonlelonvantCallback = isRelonlelonvantCallback,
        notRelonlelonvantCallback = notRelonlelonvantCallback,
        displayTypelon = displayTypelon,
        isRelonlelonvantFollowUp = isRelonlelonvantFollowUp,
        notRelonlelonvantFollowUp = notRelonlelonvantFollowUp,
      ),
      imprelonssionCallbacks = imprelonssionCallbacks
    )
}
