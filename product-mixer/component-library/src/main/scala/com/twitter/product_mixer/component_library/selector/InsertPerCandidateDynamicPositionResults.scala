packagelon com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.CandidatelonScopelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.SpeloncificPipelonlinelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.SpeloncificPipelonlinelons
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.Selonlelonctor
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.SelonlelonctorRelonsult
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonPipelonlinelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

objelonct InselonrtPelonrCandidatelonDynamicPositionRelonsults {
  delonf apply[Quelonry <: PipelonlinelonQuelonry](
    candidatelonPipelonlinelon: CandidatelonPipelonlinelonIdelonntifielonr,
    candidatelonPositionInRelonsults: CandidatelonPositionInRelonsults[Quelonry]
  ): InselonrtPelonrCandidatelonDynamicPositionRelonsults[Quelonry] =
    InselonrtPelonrCandidatelonDynamicPositionRelonsults[Quelonry](
      SpeloncificPipelonlinelon(candidatelonPipelonlinelon),
      candidatelonPositionInRelonsults)

  delonf apply[Quelonry <: PipelonlinelonQuelonry](
    candidatelonPipelonlinelons: Selont[CandidatelonPipelonlinelonIdelonntifielonr],
    candidatelonPositionInRelonsults: CandidatelonPositionInRelonsults[Quelonry]
  ): InselonrtPelonrCandidatelonDynamicPositionRelonsults[Quelonry] =
    InselonrtPelonrCandidatelonDynamicPositionRelonsults[Quelonry](
      SpeloncificPipelonlinelons(candidatelonPipelonlinelons),
      candidatelonPositionInRelonsults)
}

/**
 * Inselonrt elonach candidatelon in thelon [[CandidatelonScopelon]] at thelon indelonx relonlativelon to thelon original candidatelon in thelon `relonsult`
 * at that indelonx using thelon providelond [[CandidatelonPositionInRelonsults]] instancelon. If thelon currelonnt relonsults arelon shortelonr
 * lelonngth than thelon computelond position, thelonn thelon candidatelon will belon appelonndelond to thelon relonsults.
 *
 * Whelonn thelon [[CandidatelonPositionInRelonsults]] relonturns a `Nonelon`, that candidatelon is not
 * addelond to thelon relonsult. Nelongativelon position valuelons arelon trelonatelond as 0 (front of thelon relonsults).
 *
 * @elonxamplelon if [[CandidatelonPositionInRelonsults]] relonsults in a candidatelon mapping from indelonx to candidatelon of
 *          `{0 -> a, 0 -> b, 0 -> c, 1 -> elon, 2 -> g, 2 -> h} ` with  original `relonsults` = `[D, F]`,
 *          thelonn thelon relonsulting output would look likelon `[a, b, c, D, elon, F, g, h]`
 */
caselon class InselonrtPelonrCandidatelonDynamicPositionRelonsults[-Quelonry <: PipelonlinelonQuelonry](
  pipelonlinelonScopelon: CandidatelonScopelon,
  candidatelonPositionInRelonsults: CandidatelonPositionInRelonsults[Quelonry])
    elonxtelonnds Selonlelonctor[Quelonry] {

  ovelonrridelon delonf apply(
    quelonry: Quelonry,
    relonmainingCandidatelons: Selonq[CandidatelonWithDelontails],
    relonsult: Selonq[CandidatelonWithDelontails]
  ): SelonlelonctorRelonsult = {
    val (candidatelonsToInselonrt, othelonrRelonmainingCandidatelonsTuplelons) = relonmainingCandidatelons
      .map { candidatelon: CandidatelonWithDelontails =>
        val position =
          if (pipelonlinelonScopelon.contains(candidatelon))
            candidatelonPositionInRelonsults(quelonry, candidatelon, relonsult)
          elonlselon
            Nonelon
        (position, candidatelon)
      }.partition { caselon (indelonx, _) => indelonx.isDelonfinelond }

    val othelonrRelonmainingCandidatelons = othelonrRelonmainingCandidatelonsTuplelons.map {
      caselon (_, candidatelon) => candidatelon
    }

    val positionAndCandidatelonList = candidatelonsToInselonrt.collelonct {
      caselon (Somelon(position), candidatelon) => (position, candidatelon)
    }

    val melonrgelondRelonsult = DynamicPositionSelonlelonctor.melonrgelonByIndelonxIntoRelonsult(
      positionAndCandidatelonList,
      relonsult,
      DynamicPositionSelonlelonctor.RelonlativelonIndicelons
    )

    SelonlelonctorRelonsult(relonmainingCandidatelons = othelonrRelonmainingCandidatelons, relonsult = melonrgelondRelonsult)
  }
}
