packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.scorelonr

import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.felonaturelon_hydrator.BaselonBulkCandidatelonFelonaturelonHydrator
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.SupportsConditionally
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.ScorelonrIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch

/** Scorelons thelon providelond `candidatelons` */
trait Scorelonr[-Quelonry <: PipelonlinelonQuelonry, -Candidatelon <: UnivelonrsalNoun[Any]]
    elonxtelonnds BaselonBulkCandidatelonFelonaturelonHydrator[Quelonry, Candidatelon, Felonaturelon[_, _]]
    with SupportsConditionally[Quelonry] {

  /** @selonelon [[ScorelonrIdelonntifielonr]] */
  ovelonrridelon val idelonntifielonr: ScorelonrIdelonntifielonr

  /**
   * Felonaturelons relonturnelond by thelon Scorelonr
   */
  delonf felonaturelons: Selont[Felonaturelon[_, _]]

  /**
   * Scorelons thelon providelond `candidatelons`
   *
   * @notelon thelon relonturnelond Selonq of [[FelonaturelonMap]] must contain all thelon input 'candidatelons'
   * and belon in thelon samelon ordelonr as thelon input 'candidatelons'
   **/
  delonf apply(
    quelonry: Quelonry,
    candidatelons: Selonq[CandidatelonWithFelonaturelons[Candidatelon]]
  ): Stitch[Selonq[FelonaturelonMap]]
}
