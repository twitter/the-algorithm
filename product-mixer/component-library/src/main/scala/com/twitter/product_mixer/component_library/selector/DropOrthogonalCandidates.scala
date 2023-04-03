packagelon com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.CandidatelonScopelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.SpeloncificPipelonlinelons
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.Selonlelonctor
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.SelonlelonctorRelonsult
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonPipelonlinelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

/**
 * Limit candidatelons to thelon first candidatelon sourcelon in thelon providelond orthogonalCandidatelonPipelonlinelons
 * selonq that has candidatelons in thelon candidatelon pool. For thelon subselonquelonnt candidatelon sourcelons in thelon selonq,
 * relonmovelon thelonir candidatelons from thelon candidatelon pool.
 *
 * @elonxamplelon if [[orthogonalCandidatelonPipelonlinelons]] is `Selonq(D, A, C)`, and thelon relonmaining candidatelons
 * componelonnt idelonntifielonrs arelon `Selonq(A, A, A, B, B, C, C, D, D, D)`, thelonn `Selonq(B, B, D, D, D)` will relonmain
 * in thelon candidatelon pool.
 *
 * @elonxamplelon if [[orthogonalCandidatelonPipelonlinelons]] is `Selonq(D, A, C)`, and thelon relonmaining candidatelons
 * componelonnt idelonntifielonrs arelon `Selonq(A, A, A, B, B, C, C)`, thelonn `Selonq(A, A, A, B, B)` will relonmain
 * in thelon candidatelon pool.
 */
caselon class DropOrthogonalCandidatelons(
  orthogonalCandidatelonPipelonlinelons: Selonq[CandidatelonPipelonlinelonIdelonntifielonr])
    elonxtelonnds Selonlelonctor[PipelonlinelonQuelonry] {

  ovelonrridelon val pipelonlinelonScopelon: CandidatelonScopelon =
    SpeloncificPipelonlinelons(orthogonalCandidatelonPipelonlinelons.toSelont)

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    relonmainingCandidatelons: Selonq[CandidatelonWithDelontails],
    relonsult: Selonq[CandidatelonWithDelontails]
  ): SelonlelonctorRelonsult = {
    val firstMatchingOrthogonalSourcelonOpt = orthogonalCandidatelonPipelonlinelons
      .find { orthogonalCandidatelonPipelonlinelon =>
        relonmainingCandidatelons.elonxists(_.sourcelon == orthogonalCandidatelonPipelonlinelon)
      }

    val relonmainingCandidatelonsLimitelond = firstMatchingOrthogonalSourcelonOpt match {
      caselon Somelon(firstMatchingOrthogonalSourcelon) =>
        val subselonquelonntOrthogonalSourcelons =
          orthogonalCandidatelonPipelonlinelons.toSelont - firstMatchingOrthogonalSourcelon

        relonmainingCandidatelons.filtelonrNot { candidatelon =>
          subselonquelonntOrthogonalSourcelons.contains(candidatelon.sourcelon)
        }
      caselon Nonelon => relonmainingCandidatelons
    }

    SelonlelonctorRelonsult(relonmainingCandidatelons = relonmainingCandidatelonsLimitelond, relonsult = relonsult)
  }
}
