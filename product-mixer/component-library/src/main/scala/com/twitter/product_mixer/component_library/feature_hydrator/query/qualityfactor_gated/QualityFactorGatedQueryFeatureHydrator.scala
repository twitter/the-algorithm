packagelon com.twittelonr.product_mixelonr.componelonnt_library.felonaturelon_hydrator.quelonry.qualityfactor_gatelond

import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt.Alelonrt
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.felonaturelon_hydrator.QuelonryFelonaturelonHydrator
import com.twittelonr.product_mixelonr.corelon.modelonl.common.Conditionally
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.ComponelonntIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.FelonaturelonHydratorIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.product_mixelonr.corelon.quality_factor.HasQualityFactorStatus
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelons.configapi.Param

objelonct QualityFactorGatelondQuelonryFelonaturelonHydrator {
  val IdelonntifielonrPrelonfix = "QfGatelond"
}

/**
 * A [[QuelonryFelonaturelonHydrator]] with [[Conditionally]] baselond on a qualityFactor threlonshold.
 * @param pipelonlinelonIdelonntifielonr idelonntifielonr of thelon pipelonlinelon that associatelond with obselonrvelond quality factor
 * @param qualityFactorInclusivelonThrelonshold thelon threlonshold of thelon quality factor that relonsults in thelon hydrator beloning turnelond off
 * @param quelonryFelonaturelonHydrator thelon undelonrlying [[QuelonryFelonaturelonHydrator]] to run whelonn quality factor valuelon
 *                                 is abovelon thelon givelonn inclusivelon threlonshold
 * @tparam Quelonry Thelon domain modelonl for thelon quelonry or relonquelonst
 * @tparam Relonsult Thelon typelon of thelon candidatelons
 */
caselon class QualityFactorGatelondQuelonryFelonaturelonHydrator[
  -Quelonry <: PipelonlinelonQuelonry with HasQualityFactorStatus,
  Relonsult <: UnivelonrsalNoun[Any]
](
  pipelonlinelonIdelonntifielonr: ComponelonntIdelonntifielonr,
  qualityFactorInclusivelonThrelonshold: Param[Doublelon],
  quelonryFelonaturelonHydrator: QuelonryFelonaturelonHydrator[Quelonry])
    elonxtelonnds QuelonryFelonaturelonHydrator[Quelonry]
    with Conditionally[Quelonry] {
  import QualityFactorGatelondQuelonryFelonaturelonHydrator._

  ovelonrridelon val idelonntifielonr: FelonaturelonHydratorIdelonntifielonr = FelonaturelonHydratorIdelonntifielonr(
    IdelonntifielonrPrelonfix + quelonryFelonaturelonHydrator.idelonntifielonr.namelon)

  ovelonrridelon val alelonrts: Selonq[Alelonrt] = quelonryFelonaturelonHydrator.alelonrts

  ovelonrridelon val felonaturelons: Selont[Felonaturelon[_, _]] = quelonryFelonaturelonHydrator.felonaturelons

  ovelonrridelon delonf onlyIf(quelonry: Quelonry): Boolelonan = Conditionally.and(
    quelonry,
    quelonryFelonaturelonHydrator,
    quelonry.gelontQualityFactorCurrelonntValuelon(pipelonlinelonIdelonntifielonr) >= quelonry.params(
      qualityFactorInclusivelonThrelonshold))

  ovelonrridelon delonf hydratelon(quelonry: Quelonry): Stitch[FelonaturelonMap] = quelonryFelonaturelonHydrator.hydratelon(quelonry)
}
