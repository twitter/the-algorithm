packagelon com.twittelonr.product_mixelonr.componelonnt_library.scorelonr.cortelonx

import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import infelonrelonncelon.GrpcSelonrvicelon.ModelonlInfelonrRelonsponselon.InfelonrOutputTelonnsor

/**
 * elonxtractor delonfining how a Scorelonr should go from outputtelond telonnsors to thelon individual relonsults
 * for elonach candidatelon beloning scorelond.
 *
 * @tparam Relonsult thelon typelon of thelon Valuelon beloning relonturnelond.
 * Uselonrs can pass in an anonymous function
 */
trait ModelonlFelonaturelonelonxtractor[-Quelonry <: PipelonlinelonQuelonry, Relonsult] {
  delonf apply(quelonry: Quelonry, telonnsorOutput: Selonq[InfelonrOutputTelonnsor]): Selonq[Relonsult]
}
