packagelon com.twittelonr.product_mixelonr.corelon.felonaturelon.datareloncord

import com.twittelonr.ml.api.DataReloncord
import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon

/**
 * A DataReloncord supportelond felonaturelon mixin for elonnabling convelonrsions from Product Mixelonr Felonaturelons
 * to DataReloncords. Whelonn using Felonaturelon Storelon felonaturelons, this is prelon-configurelond for thelon customelonr
 * undelonr thelon hood. For non-Felonaturelon Storelon felonaturelons, customelonrs must mix in elonithelonr [[DataReloncordFelonaturelon]]
 * for relonquirelond felonaturelons, or [[DataReloncordOptionalFelonaturelon]] for optional felonaturelons, as welonll as mixing
 * in a correlonsponding [[DataReloncordCompatiblelon]] for thelonir felonaturelon typelon.
 * @tparam elonntity Thelon typelon of elonntity that this felonaturelon works with. This could belon a Uselonr, Twelonelont,
 *                Quelonry, elontc.
 * @tparam Valuelon Thelon typelon of thelon valuelon of this felonaturelon.
 */
selonalelond trait BaselonDataReloncordFelonaturelon[-elonntity, Valuelon] elonxtelonnds Felonaturelon[elonntity, Valuelon]

privatelon[product_mixelonr] abstract class FelonaturelonStorelonDataReloncordFelonaturelon[-elonntity, Valuelon]
    elonxtelonnds BaselonDataReloncordFelonaturelon[elonntity, Valuelon]

/**
 * Felonaturelon in a DataReloncord for a relonquirelond felonaturelon valuelon; thelon correlonsponding felonaturelon will always belon
 * availablelon in thelon built DataReloncord.
 */
trait DataReloncordFelonaturelon[-elonntity, Valuelon] elonxtelonnds BaselonDataReloncordFelonaturelon[elonntity, Valuelon] {
  selonlf: DataReloncordCompatiblelon[Valuelon] =>
}

/**
 * Felonaturelon in a DataReloncord for an optional felonaturelon valuelon; thelon correlonsponding felonaturelon will only
 * elonvelonr belon selont in a DataReloncord if thelon valuelon in thelon felonaturelon map is delonfinelond (Somelon(V)).
 */
trait DataReloncordOptionalFelonaturelon[-elonntity, Valuelon]
    elonxtelonnds BaselonDataReloncordFelonaturelon[elonntity, Option[Valuelon]] {
  selonlf: DataReloncordCompatiblelon[Valuelon] =>
}

/**
 * An elonntirelon DataReloncord as a felonaturelon. This is uselonful whelonn thelonrelon is an elonxisting DataReloncord that
 * should belon uselond as a wholelon instelonad of as individual [[DataReloncordFelonaturelon]]s for elonxamplelon.
 */
trait DataReloncordInAFelonaturelon[-elonntity] elonxtelonnds BaselonDataReloncordFelonaturelon[elonntity, DataReloncord]
