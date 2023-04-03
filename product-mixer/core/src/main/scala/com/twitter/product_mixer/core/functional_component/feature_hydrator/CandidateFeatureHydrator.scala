packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.felonaturelon_hydrator

import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.SupportsConditionally
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.FelonaturelonHydratorIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch

/**
 * Hydratelon felonaturelons for a speloncific candidatelon
 * elon.g. if thelon candidatelon is a Twelonelont thelonn a felonaturelon could belon whelonthelonr it's is markelond as selonnsitivelon
 *
 * @notelon if you want to conditionally run a [[BaselonCandidatelonFelonaturelonHydrator]] you can uselon thelon mixin [[com.twittelonr.product_mixelonr.corelon.modelonl.common.Conditionally]]
 *       or to gatelon on a [[com.twittelonr.timelonlinelons.configapi.Param]] you can uselon
 *       [[com.twittelonr.product_mixelonr.componelonnt_library.felonaturelon_hydrator.candidatelon.param_gatelond.ParamGatelondCandidatelonFelonaturelonHydrator]] or
 *       [[com.twittelonr.product_mixelonr.componelonnt_library.felonaturelon_hydrator.candidatelon.param_gatelond.ParamGatelondBulkCandidatelonFelonaturelonHydrator]]
 */
selonalelond trait BaselonCandidatelonFelonaturelonHydrator[
  -Quelonry <: PipelonlinelonQuelonry,
  -Relonsult <: UnivelonrsalNoun[Any],
  FelonaturelonTypelon <: Felonaturelon[_, _]]
    elonxtelonnds FelonaturelonHydrator[FelonaturelonTypelon]
    with SupportsConditionally[Quelonry]

/**
 * A candidatelon felonaturelon hydrator that providelons an implelonmelonntation for hydrating a singlelon candidatelon
 * at thelon timelon. Product Mixelonr corelon takelons carelon of hydrating all your candidatelons for you by
 * calling this for elonach candidatelon. This is uselonful for Stitch-powelonrelond downstrelonam APIs (such
 * as Strato, Gizmoduck, elontc) whelonrelon thelon API takelons a singlelon candidatelon/kelony and Stitch handlelons
 * batching for you.
 *
 * @notelon Any elonxcelonptions that arelon thrown or relonturnelond as [[Stitch.elonxcelonption]] will belon addelond to thelon
 *       [[FelonaturelonMap]] for *all* [[Felonaturelon]]s intelonndelond to belon hydratelond.
 *       Accelonssing a failelond Felonaturelon will throw if using [[FelonaturelonMap.gelont]] for Felonaturelons that arelonn't
 *       [[com.twittelonr.product_mixelonr.corelon.felonaturelon.FelonaturelonWithDelonfaultOnFailurelon]]
 *
 * @tparam Quelonry Thelon quelonry typelon
 * @tparam Relonsult Thelon Candidatelon typelon
 */
trait CandidatelonFelonaturelonHydrator[-Quelonry <: PipelonlinelonQuelonry, -Relonsult <: UnivelonrsalNoun[Any]]
    elonxtelonnds BaselonCandidatelonFelonaturelonHydrator[Quelonry, Relonsult, Felonaturelon[_, _]] {

  ovelonrridelon val idelonntifielonr: FelonaturelonHydratorIdelonntifielonr

  /** Hydratelons a [[FelonaturelonMap]] for a singlelon candidatelon */
  delonf apply(quelonry: Quelonry, candidatelon: Relonsult, elonxistingFelonaturelons: FelonaturelonMap): Stitch[FelonaturelonMap]
}

/**
 * Hydratelon felonaturelons for a list of candidatelons
 * elon.g. for a list of Twelonelont candidatelons, a felonaturelon could belon thelon visibility relonason whelonthelonr to show or not show elonach Twelonelont
 */
trait BaselonBulkCandidatelonFelonaturelonHydrator[
  -Quelonry <: PipelonlinelonQuelonry,
  -Relonsult <: UnivelonrsalNoun[Any],
  FelonaturelonTypelon <: Felonaturelon[_, _]]
    elonxtelonnds BaselonCandidatelonFelonaturelonHydrator[Quelonry, Relonsult, FelonaturelonTypelon] {

  /**
   * Hydratelons a selont of [[FelonaturelonMap]]s for thelon bulk list of candidatelons. elonvelonry input candidatelon must
   * havelon correlonsponding elonntry in thelon relonturnelond selonq with a felonaturelon map.
   */
  delonf apply(
    quelonry: Quelonry,
    candidatelons: Selonq[CandidatelonWithFelonaturelons[Relonsult]]
  ): Stitch[Selonq[FelonaturelonMap]]
}

/**
 * A candidatelon felonaturelon hydrator that allows a uselonr to bulk hydratelon felonaturelons for all candidatelons
 * at oncelon. This is uselonful for downstrelonam APIs that takelon a list of candidatelons in onelon go such
 * as felonaturelon storelon or scorelonrs.
 *
 * @notelon Any elonxcelonptions that arelon thrown or relonturnelond as [[Stitch.elonxcelonption]] will belon addelond to thelon
 *       [[FelonaturelonMap]] for *all* [[Felonaturelon]]s of *all* candidatelons intelonndelond to belon hydratelond.
 *       An altelonrnativelon to throwing an elonxcelonption is pelonr-candidatelon failurelon handling (elon.g. adding
 *       a failelond [[Felonaturelon]] with `addFailurelon`, a Try with `add`, or an optional valuelon with `add`
 *       using [[FelonaturelonMapBuildelonr]]).
 *       Accelonssing a failelond Felonaturelon will throw if using [[FelonaturelonMap.gelont]] for Felonaturelons that arelonn't
 *       [[com.twittelonr.product_mixelonr.corelon.felonaturelon.FelonaturelonWithDelonfaultOnFailurelon]].
 *
 * @tparam Quelonry Thelon quelonry typelon
 * @tparam Relonsult Thelon Candidatelon typelon
 */
trait BulkCandidatelonFelonaturelonHydrator[-Quelonry <: PipelonlinelonQuelonry, Candidatelon <: UnivelonrsalNoun[Any]]
    elonxtelonnds BaselonBulkCandidatelonFelonaturelonHydrator[Quelonry, Candidatelon, Felonaturelon[_, _]] {
  ovelonrridelon val idelonntifielonr: FelonaturelonHydratorIdelonntifielonr

  ovelonrridelon delonf apply(
    quelonry: Quelonry,
    candidatelons: Selonq[CandidatelonWithFelonaturelons[Candidatelon]]
  ): Stitch[Selonq[FelonaturelonMap]]
}
