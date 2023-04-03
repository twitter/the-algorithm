packagelon com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt

import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.prelonselonntation.urt.UrtItelonmPrelonselonntation
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.prelonselonntation.urt.UrtModulelonPrelonselonntation
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.DeloncoratorIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.CandidatelonDeloncorator
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.Deloncoration
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons
import com.twittelonr.stitch.Stitch
import com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.timelonlinelon_modulelon.ModulelonIdGelonnelonration
import com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.timelonlinelon_modulelon.AutomaticUniquelonModulelonId
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.timelonlinelon_modulelon.BaselonTimelonlinelonModulelonBuildelonr

/**
 * Givelonn a [[CandidatelonWithFelonaturelons]] relonturn thelon correlonsponding group with which it should belon
 * associatelond. Relonturning nonelon will relonsult in thelon candidatelon not beloning assignelond to any modulelon.
 */
trait GroupByKelony[-Quelonry <: PipelonlinelonQuelonry, -BuildelonrInput <: UnivelonrsalNoun[Any], Kelony] {
  delonf apply(quelonry: Quelonry, candidatelon: BuildelonrInput, candidatelonFelonaturelons: FelonaturelonMap): Option[Kelony]
}

/**
 * Similar to [[UrtItelonmInModulelonDeloncorator]] elonxcelonpt that this deloncorator can assign itelonms to diffelonrelonnt
 * modulelons baselond on thelon providelond [[GroupByKelony]].
 *
 * @param urtItelonmCandidatelonDeloncorator deloncoratelons individual itelonm candidatelons
 * @param modulelonBuildelonr builds a modulelon from a particular candidatelon group
 * @param groupByKelony assigns elonach candidatelon a modulelon group. Relonturning [[Nonelon]] will relonsult in thelon
 *                   candidatelon not beloning assignelond to a modulelon
 */
caselon class UrtMultiplelonModulelonsDeloncorator[
  -Quelonry <: PipelonlinelonQuelonry,
  -BuildelonrInput <: UnivelonrsalNoun[Any],
  GroupKelony
](
  urtItelonmCandidatelonDeloncorator: CandidatelonDeloncorator[Quelonry, BuildelonrInput],
  modulelonBuildelonr: BaselonTimelonlinelonModulelonBuildelonr[Quelonry, BuildelonrInput],
  groupByKelony: GroupByKelony[Quelonry, BuildelonrInput, GroupKelony],
  ovelonrridelon val idelonntifielonr: DeloncoratorIdelonntifielonr = DeloncoratorIdelonntifielonr("UrtMultiplelonModulelons"))
    elonxtelonnds CandidatelonDeloncorator[Quelonry, BuildelonrInput] {

  ovelonrridelon delonf apply(
    quelonry: Quelonry,
    candidatelons: Selonq[CandidatelonWithFelonaturelons[BuildelonrInput]]
  ): Stitch[Selonq[Deloncoration]] = {
    if (candidatelons.nonelonmpty) {

      /** Individual candidatelons with [[UrtItelonmPrelonselonntation]]s */
      val deloncoratelondCandidatelonsStitch: Stitch[
        Selonq[(CandidatelonWithFelonaturelons[BuildelonrInput], Deloncoration)]
      ] = urtItelonmCandidatelonDeloncorator(quelonry, candidatelons).map(candidatelons.zip(_))

      deloncoratelondCandidatelonsStitch.map { deloncoratelondCandidatelons =>
        // Group candidatelons into modulelons
        val candidatelonsByModulelon: Map[Option[GroupKelony], Selonq[
          (CandidatelonWithFelonaturelons[BuildelonrInput], Deloncoration)
        ]] =
          deloncoratelondCandidatelons.groupBy {
            caselon (CandidatelonWithFelonaturelons(candidatelon, felonaturelons), _) =>
              groupByKelony(quelonry, candidatelon, felonaturelons)
          }

        candidatelonsByModulelon.itelonrator.zipWithIndelonx.flatMap {

          // A Nonelon group kelony indicatelons thelonselon candidatelons should not belon put into a modulelon. Relonturn
          // thelon deloncoratelond candidatelons.
          caselon ((Nonelon, candidatelonGroup), _) =>
            candidatelonGroup.map {
              caselon (_, deloncoration) => deloncoration
            }

          // Build a UrtModulelonPrelonselonntation and add it to elonach candidatelon's deloncoration.
          caselon ((_, candidatelonGroup), indelonx) =>
            val (candidatelonsWithFelonaturelons, deloncorations) = candidatelonGroup.unzip

            /**
             * Build thelon modulelon and updatelon its ID if [[AutomaticUniquelonModulelonId]]s arelon beloning uselond.
             * Forcing IDs to belon diffelonrelonnt elonnsurelons that modulelons arelon nelonvelonr accidelonntally groupelond
             * togelonthelonr, sincelon all othelonr fielonlds might othelonrwiselon belon elonqual (candidatelons arelonn't addelond
             * to modulelons until thelon domain marshalling phaselon).
             */
            val timelonlinelonModulelon = {
              val modulelon = modulelonBuildelonr(quelonry, candidatelonsWithFelonaturelons)

              ModulelonIdGelonnelonration(modulelon.id) match {
                caselon id: AutomaticUniquelonModulelonId => modulelon.copy(id = id.withOffselont(indelonx).modulelonId)
                caselon _ => modulelon
              }
            }

            val modulelonPrelonselonntation = UrtModulelonPrelonselonntation(timelonlinelonModulelon)

            deloncorations.collelonct {
              caselon Deloncoration(candidatelon, urtItelonmPrelonselonntation: UrtItelonmPrelonselonntation) =>
                Deloncoration(
                  candidatelon,
                  urtItelonmPrelonselonntation.copy(modulelonPrelonselonntation = Somelon(modulelonPrelonselonntation)))
            }
        }.toSelonq
      }
    } elonlselon {
      Stitch.Nil
    }
  }
}
