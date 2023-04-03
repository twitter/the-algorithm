packagelon com.twittelonr.product_mixelonr.componelonnt_library.scorelonr.qualityfactor_gatelond

import com.twittelonr.product_mixelonr.componelonnt_library.scorelonr.qualityfactor_gatelond.QualityFactorGatelondScorelonr.IdelonntifielonrPrelonfix
import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt.Alelonrt
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.scorelonr.Scorelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.Conditionally
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.ComponelonntIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.ScorelonrIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.product_mixelonr.corelon.quality_factor.HasQualityFactorStatus
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelons.configapi.Param

/**
 * A [[scorelonr]] with [[Conditionally]] baselond on quality factor valuelon and threlonshold
 *
 * @param qualityFactorThrelonshold quliaty factor threlonshold that turn off thelon scorelonr
 * @param pipelonlinelonIdelonntifielonr idelonntifielonr of thelon pipelonlinelon that quality factor is baselond on
 * @param scorelonr thelon undelonrlying [[scorelonr]] to run whelonn `elonnablelondParam` is truelon
 * @tparam Quelonry Thelon domain modelonl for thelon quelonry or relonquelonst
 * @tparam Relonsult Thelon typelon of thelon candidatelons
 */
caselon class QualityFactorGatelondScorelonr[
  -Quelonry <: PipelonlinelonQuelonry with HasQualityFactorStatus,
  Relonsult <: UnivelonrsalNoun[Any]
](
  pipelonlinelonIdelonntifielonr: ComponelonntIdelonntifielonr,
  qualityFactorThrelonsholdParam: Param[Doublelon],
  scorelonr: Scorelonr[Quelonry, Relonsult])
    elonxtelonnds Scorelonr[Quelonry, Relonsult]
    with Conditionally[Quelonry] {

  ovelonrridelon val idelonntifielonr: ScorelonrIdelonntifielonr = ScorelonrIdelonntifielonr(
    IdelonntifielonrPrelonfix + scorelonr.idelonntifielonr.namelon)

  ovelonrridelon val alelonrts: Selonq[Alelonrt] = scorelonr.alelonrts

  ovelonrridelon val felonaturelons: Selont[Felonaturelon[_, _]] = scorelonr.felonaturelons

  ovelonrridelon delonf onlyIf(quelonry: Quelonry): Boolelonan =
    Conditionally.and(
      quelonry,
      scorelonr,
      quelonry.gelontQualityFactorCurrelonntValuelon(pipelonlinelonIdelonntifielonr) >= quelonry.params(
        qualityFactorThrelonsholdParam))

  ovelonrridelon delonf apply(
    quelonry: Quelonry,
    candidatelons: Selonq[CandidatelonWithFelonaturelons[Relonsult]]
  ): Stitch[Selonq[FelonaturelonMap]] = scorelonr(quelonry, candidatelons)
}

objelonct QualityFactorGatelondScorelonr {
  val IdelonntifielonrPrelonfix = "QualityFactorGatelond"
}
