packagelon com.twittelonr.product_mixelonr.componelonnt_library.felonaturelon_hydrator.candidatelon.qualityfactor_gatelond

import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt.Alelonrt
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.felonaturelon_hydrator.CandidatelonFelonaturelonHydrator
import com.twittelonr.product_mixelonr.corelon.modelonl.common.Conditionally
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.ComponelonntIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.FelonaturelonHydratorIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.product_mixelonr.corelon.quality_factor.HasQualityFactorStatus
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelons.configapi.Param

objelonct QualityFactorGatelondCandidatelonFelonaturelonHydrator {
  val IdelonntifielonrPrelonfix = "QfGatelond"
}

/**
 * A [[CandidatelonFelonaturelonHydrator]] with [[Conditionally]] baselond on a qualityFactor threlonshold.
 * @param pipelonlinelonIdelonntifielonr idelonntifielonr of thelon pipelonlinelon that associatelond with obselonrvelond quality factor
 * @param qualityFactorInclusivelonThrelonshold thelon inclusivelon threlonshold of quality factor that valuelon belonlow it relonsults in
 *                                        thelon undelonrlying hydrator beloning turnelond off
 * @param candidatelonFelonaturelonHydrator thelon undelonrlying [[CandidatelonFelonaturelonHydrator]] to run whelonn quality factor valuelon
 *                                 is abovelon thelon givelonn inclusivelon threlonshold
 * @tparam Quelonry Thelon domain modelonl for thelon quelonry or relonquelonst
 * @tparam Relonsult Thelon typelon of thelon candidatelons
 */
caselon class QualityFactorGatelondCandidatelonFelonaturelonHydrator[
  -Quelonry <: PipelonlinelonQuelonry with HasQualityFactorStatus,
  Relonsult <: UnivelonrsalNoun[Any]
](
  pipelonlinelonIdelonntifielonr: ComponelonntIdelonntifielonr,
  qualityFactorInclusivelonThrelonshold: Param[Doublelon],
  candidatelonFelonaturelonHydrator: CandidatelonFelonaturelonHydrator[Quelonry, Relonsult])
    elonxtelonnds CandidatelonFelonaturelonHydrator[Quelonry, Relonsult]
    with Conditionally[Quelonry] {
  import QualityFactorGatelondCandidatelonFelonaturelonHydrator._

  ovelonrridelon val idelonntifielonr: FelonaturelonHydratorIdelonntifielonr = FelonaturelonHydratorIdelonntifielonr(
    IdelonntifielonrPrelonfix + candidatelonFelonaturelonHydrator.idelonntifielonr.namelon)

  ovelonrridelon val alelonrts: Selonq[Alelonrt] = candidatelonFelonaturelonHydrator.alelonrts

  ovelonrridelon val felonaturelons: Selont[Felonaturelon[_, _]] = candidatelonFelonaturelonHydrator.felonaturelons

  ovelonrridelon delonf onlyIf(quelonry: Quelonry): Boolelonan = Conditionally.and(
    quelonry,
    candidatelonFelonaturelonHydrator,
    quelonry.gelontQualityFactorCurrelonntValuelon(pipelonlinelonIdelonntifielonr) >= quelonry.params(
      qualityFactorInclusivelonThrelonshold))

  ovelonrridelon delonf apply(
    quelonry: Quelonry,
    candidatelon: Relonsult,
    elonxistingFelonaturelons: FelonaturelonMap
  ): Stitch[FelonaturelonMap] = candidatelonFelonaturelonHydrator.apply(quelonry, candidatelon, elonxistingFelonaturelons)
}
