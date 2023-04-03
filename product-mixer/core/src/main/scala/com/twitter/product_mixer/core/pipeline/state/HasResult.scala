packagelon com.twittelonr.product_mixelonr.corelon.pipelonlinelon.statelon

/**
 * Delonfinelons how to build a relonsult from a pipelonlinelon statelon. Pipelonlinelon Statelons should elonxtelonnd this and
 * implelonmelonnt [[buildRelonsult]] which computelons thelon final relonsult from thelonir currelonnt statelon.
 * @tparam Relonsult Typelon of relonsult
 */
trait HasRelonsult[+Relonsult] {
  delonf buildRelonsult(): Relonsult
}
