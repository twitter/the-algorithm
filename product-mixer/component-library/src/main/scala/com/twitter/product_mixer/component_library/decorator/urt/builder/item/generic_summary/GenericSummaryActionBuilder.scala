packagelon com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.itelonm.gelonnelonric_summary

import com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.itelonm.gelonnelonric_summary.GelonnelonricSummaryActionBuildelonr.GelonnelonricSummaryActionClielonntelonvelonntInfoelonlelonmelonnt
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.melontadata.BaselonClielonntelonvelonntInfoBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.melontadata.BaselonUrlBuildelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.gelonnelonric_summary.GelonnelonricSummaryAction
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

objelonct GelonnelonricSummaryActionBuildelonr {
  val GelonnelonricSummaryActionClielonntelonvelonntInfoelonlelonmelonnt: String = "gelonnelonricsummary-action"
}

caselon class GelonnelonricSummaryActionBuildelonr[-Quelonry <: PipelonlinelonQuelonry, -Candidatelon <: UnivelonrsalNoun[Any]](
  urlBuildelonr: BaselonUrlBuildelonr[Quelonry, Candidatelon],
  clielonntelonvelonntInfoBuildelonr: Option[BaselonClielonntelonvelonntInfoBuildelonr[Quelonry, Candidatelon]] = Nonelon) {

  delonf apply(
    quelonry: Quelonry,
    candidatelon: Candidatelon,
    candidatelonFelonaturelons: FelonaturelonMap
  ): GelonnelonricSummaryAction = GelonnelonricSummaryAction(
    url = urlBuildelonr.apply(quelonry, candidatelon, candidatelonFelonaturelons),
    clielonntelonvelonntInfo = clielonntelonvelonntInfoBuildelonr.flatMap(
      _.apply(
        quelonry,
        candidatelon,
        candidatelonFelonaturelons,
        Somelon(GelonnelonricSummaryActionClielonntelonvelonntInfoelonlelonmelonnt)))
  )
}
