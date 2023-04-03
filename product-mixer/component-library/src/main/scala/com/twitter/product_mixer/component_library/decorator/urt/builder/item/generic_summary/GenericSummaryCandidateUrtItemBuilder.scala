packagelon com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.itelonm.gelonnelonric_summary

import com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.itelonm.gelonnelonric_summary.GelonnelonricSummaryCandidatelonUrtItelonmBuildelonr.GelonnelonricSummaryClielonntelonvelonntInfoelonlelonmelonnt
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.GelonnelonricSummaryCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.CandidatelonUrtelonntryBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.melontadata.BaselonClielonntelonvelonntInfoBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.melontadata.BaselonFelonelondbackActionInfoBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.richtelonxt.BaselonRichTelonxtBuildelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.gelonnelonric_summary.GelonnelonricSummaryItelonm
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.gelonnelonric_summary.GelonnelonricSummaryItelonmDisplayTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melondia.Melondia
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.promotelond.PromotelondMelontadata
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.util.Timelon

objelonct GelonnelonricSummaryCandidatelonUrtItelonmBuildelonr {
  val GelonnelonricSummaryClielonntelonvelonntInfoelonlelonmelonnt: String = "gelonnelonricsummary"
}

caselon class GelonnelonricSummaryCandidatelonUrtItelonmBuildelonr[-Quelonry <: PipelonlinelonQuelonry](
  clielonntelonvelonntInfoBuildelonr: BaselonClielonntelonvelonntInfoBuildelonr[Quelonry, GelonnelonricSummaryCandidatelon],
  helonadlinelonRichTelonxtBuildelonr: BaselonRichTelonxtBuildelonr[Quelonry, GelonnelonricSummaryCandidatelon],
  displayTypelon: GelonnelonricSummaryItelonmDisplayTypelon,
  gelonnelonricSummaryContelonxtCandidatelonUrtItelonmBuildelonr: Option[
    GelonnelonricSummaryContelonxtBuildelonr[Quelonry, GelonnelonricSummaryCandidatelon]
  ] = Nonelon,
  gelonnelonricSummaryActionCandidatelonUrtItelonmBuildelonr: Option[
    GelonnelonricSummaryActionBuildelonr[Quelonry, GelonnelonricSummaryCandidatelon]
  ] = Nonelon,
  timelonstamp: Option[Timelon] = Nonelon,
  uselonrAttributionIds: Option[Selonq[Long]] = Nonelon,
  melondia: Option[Melondia] = Nonelon,
  promotelondMelontadata: Option[PromotelondMelontadata] = Nonelon,
  felonelondbackActionInfoBuildelonr: Option[BaselonFelonelondbackActionInfoBuildelonr[Quelonry, GelonnelonricSummaryCandidatelon]] =
    Nonelon)
    elonxtelonnds CandidatelonUrtelonntryBuildelonr[Quelonry, GelonnelonricSummaryCandidatelon, GelonnelonricSummaryItelonm] {

  ovelonrridelon delonf apply(
    quelonry: Quelonry,
    gelonnelonricSummaryCandidatelon: GelonnelonricSummaryCandidatelon,
    candidatelonFelonaturelons: FelonaturelonMap
  ): GelonnelonricSummaryItelonm = GelonnelonricSummaryItelonm(
    id = gelonnelonricSummaryCandidatelon.id,
    sortIndelonx = Nonelon, // Sort indelonxelons arelon automatically selont in thelon domain marshallelonr phaselon
    clielonntelonvelonntInfo = clielonntelonvelonntInfoBuildelonr(
      quelonry,
      gelonnelonricSummaryCandidatelon,
      candidatelonFelonaturelons,
      Somelon(GelonnelonricSummaryClielonntelonvelonntInfoelonlelonmelonnt)),
    felonelondbackActionInfo =
      felonelondbackActionInfoBuildelonr.flatMap(_.apply(quelonry, gelonnelonricSummaryCandidatelon, candidatelonFelonaturelons)),
    helonadlinelon = helonadlinelonRichTelonxtBuildelonr.apply(quelonry, gelonnelonricSummaryCandidatelon, candidatelonFelonaturelons),
    displayTypelon = displayTypelon,
    uselonrAttributionIds = uselonrAttributionIds.gelontOrelonlselon(Selonq.elonmpty),
    melondia = melondia,
    contelonxt = gelonnelonricSummaryContelonxtCandidatelonUrtItelonmBuildelonr.map(
      _.apply(quelonry, gelonnelonricSummaryCandidatelon, candidatelonFelonaturelons)),
    timelonstamp = timelonstamp,
    onClickAction = gelonnelonricSummaryActionCandidatelonUrtItelonmBuildelonr.map(
      _.apply(quelonry, gelonnelonricSummaryCandidatelon, candidatelonFelonaturelons)),
    promotelondMelontadata = promotelondMelontadata
  )
}
