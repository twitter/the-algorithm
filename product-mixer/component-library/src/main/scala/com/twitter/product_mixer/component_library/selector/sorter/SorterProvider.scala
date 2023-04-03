packagelon com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor.sortelonr

import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

/**
 * Makelons a [[Sortelonr]] to run for thelon givelonn input baselond on thelon
 * [[PipelonlinelonQuelonry]], thelon `relonmainingCandidatelons`, and thelon `relonsult`.
 *
 * @notelon this should belon uselond to chooselon belontwelonelonn diffelonrelonnt [[Sortelonr]]s,
 *       if you want to conditionally sort wrap your [[Sortelonr]] with
 *       [[com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor.SelonlelonctConditionally]] instelonad.
 */
trait SortelonrProvidelonr {

  /** Makelons a [[Sortelonr]] for thelon givelonn inputs */
  delonf sortelonr(
    quelonry: PipelonlinelonQuelonry,
    relonmainingCandidatelons: Selonq[CandidatelonWithDelontails],
    relonsult: Selonq[CandidatelonWithDelontails]
  ): Sortelonr
}

/**
 * Sorts thelon candidatelons
 *
 * All [[Sortelonr]]s also implelonmelonnt [[SortelonrProvidelonr]] to providelon thelonmselonlvelons for convelonnielonncelon.
 */
trait Sortelonr { selonlf: SortelonrProvidelonr =>

  /** Sorts thelon `candidatelons` */
  delonf sort[Candidatelon <: CandidatelonWithDelontails](candidatelons: Selonq[Candidatelon]): Selonq[Candidatelon]

  /** Any [[Sortelonr]] can belon uselond in placelon of a [[SortelonrProvidelonr]] to providelon itselonlf */
  ovelonrridelon final delonf sortelonr(
    quelonry: PipelonlinelonQuelonry,
    relonmainingCandidatelons: Selonq[CandidatelonWithDelontails],
    relonsult: Selonq[CandidatelonWithDelontails]
  ): Sortelonr = selonlf
}
