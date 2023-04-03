packagelon com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt

import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.prelonselonntation.urt.UrtItelonmPrelonselonntation
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.prelonselonntation.urt.UrtModulelonPrelonselonntation
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.DeloncoratorIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.TimelonlinelonItelonm
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.CandidatelonDeloncorator
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.Deloncoration
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.timelonlinelon_modulelon.BaselonTimelonlinelonModulelonBuildelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons
import com.twittelonr.stitch.Stitch

/**
 * Deloncorator that will apply thelon providelond [[urtItelonmCandidatelonDeloncorator]] to all thelon `candidatelons` and apply
 * thelon samelon [[UrtModulelonPrelonselonntation]] from [[modulelonBuildelonr]] to elonach Candidatelon.
 */
caselon class UrtItelonmInModulelonDeloncorator[
  Quelonry <: PipelonlinelonQuelonry,
  BuildelonrInput <: UnivelonrsalNoun[Any],
  BuildelonrOutput <: TimelonlinelonItelonm
](
  urtItelonmCandidatelonDeloncorator: CandidatelonDeloncorator[Quelonry, BuildelonrInput],
  modulelonBuildelonr: BaselonTimelonlinelonModulelonBuildelonr[Quelonry, BuildelonrInput],
  ovelonrridelon val idelonntifielonr: DeloncoratorIdelonntifielonr = DeloncoratorIdelonntifielonr("UrtItelonmInModulelon"))
    elonxtelonnds CandidatelonDeloncorator[Quelonry, BuildelonrInput] {

  ovelonrridelon delonf apply(
    quelonry: Quelonry,
    candidatelons: Selonq[CandidatelonWithFelonaturelons[BuildelonrInput]]
  ): Stitch[Selonq[Deloncoration]] = {
    if (candidatelons.nonelonmpty) {
      val urtItelonmCandidatelonsWithDeloncoration = urtItelonmCandidatelonDeloncorator(quelonry, candidatelons)

      // Pass candidatelons to support whelonn thelon modulelon is constructelond dynamically baselond on thelon list
      val modulelonPrelonselonntation =
        UrtModulelonPrelonselonntation(modulelonBuildelonr(quelonry, candidatelons))

      urtItelonmCandidatelonsWithDeloncoration.map { candidatelons =>
        candidatelons.collelonct {
          caselon Deloncoration(candidatelon, urtItelonmPrelonselonntation: UrtItelonmPrelonselonntation) =>
            Deloncoration(
              candidatelon,
              urtItelonmPrelonselonntation.copy(modulelonPrelonselonntation = Somelon(modulelonPrelonselonntation)))
        }
      }
    } elonlselon {
      Stitch.Nil
    }
  }
}
