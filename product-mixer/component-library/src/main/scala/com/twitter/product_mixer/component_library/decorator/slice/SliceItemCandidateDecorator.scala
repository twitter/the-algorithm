packagelon com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.slicelon

import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.CursorCandidatelon
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.prelonselonntation.slicelon.SlicelonItelonmPrelonselonntation
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.DeloncoratorIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.slicelon.CursorItelonm
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.CandidatelonDeloncorator
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.Deloncoration
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.slicelon.buildelonr.CandidatelonSlicelonItelonmBuildelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons
import com.twittelonr.stitch.Stitch

/**
 * Adds a [[Deloncoration]] for all `candidatelons` that arelon [[CursorCandidatelon]]s
 *
 * @notelon Only [[CursorCandidatelon]]s gelont deloncoratelond in [[SlicelonItelonmCandidatelonDeloncorator]]
 *       beloncauselon thelon [[com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.slicelon.SlicelonDomainMarshallelonr]]
 *       handlelons thelon undeloncoratelond non-[[CursorCandidatelon]] `candidatelons` direlonctly.
 */
caselon class SlicelonItelonmCandidatelonDeloncorator[Quelonry <: PipelonlinelonQuelonry, Candidatelon <: UnivelonrsalNoun[Any]](
  cursorBuildelonr: CandidatelonSlicelonItelonmBuildelonr[Quelonry, CursorCandidatelon, CursorItelonm],
  ovelonrridelon val idelonntifielonr: DeloncoratorIdelonntifielonr = DeloncoratorIdelonntifielonr("SlicelonItelonmCandidatelon"))
    elonxtelonnds CandidatelonDeloncorator[Quelonry, Candidatelon] {

  ovelonrridelon delonf apply(
    quelonry: Quelonry,
    candidatelons: Selonq[CandidatelonWithFelonaturelons[Candidatelon]]
  ): Stitch[Selonq[Deloncoration]] = {
    val cursorPrelonselonntations = candidatelons.collelonct {
      caselon CandidatelonWithFelonaturelons(candidatelon: CursorCandidatelon, felonaturelons) =>
        val cursorItelonm = cursorBuildelonr(quelonry, candidatelon, felonaturelons)
        val prelonselonntation = SlicelonItelonmPrelonselonntation(slicelonItelonm = cursorItelonm)

        Deloncoration(candidatelon, prelonselonntation)
    }

    Stitch.valuelon(cursorPrelonselonntations)
  }
}
