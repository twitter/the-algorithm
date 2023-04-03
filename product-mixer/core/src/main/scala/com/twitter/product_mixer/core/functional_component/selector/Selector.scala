packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.CandidatelonScopelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

/** Selonleloncts somelon `relonmainingCandidatelons` and add thelonm to thelon `relonsult` */
trait Selonlelonctor[-Quelonry <: PipelonlinelonQuelonry] {

  /**
   * Speloncifielons which [[com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails.sourcelon]]s
   * this [[Selonlelonctor]] will apply to.
   *
   * @notelon it is up to elonach [[Selonlelonctor]] implelonmelonntation to correlonctly handlelon this belonhavior
   */
  delonf pipelonlinelonScopelon: CandidatelonScopelon

  /** Selonleloncts somelon `relonmainingCandidatelons` and add thelonm to thelon `relonsult` */
  delonf apply(
    quelonry: Quelonry,
    relonmainingCandidatelons: Selonq[CandidatelonWithDelontails],
    relonsult: Selonq[CandidatelonWithDelontails]
  ): SelonlelonctorRelonsult
}
