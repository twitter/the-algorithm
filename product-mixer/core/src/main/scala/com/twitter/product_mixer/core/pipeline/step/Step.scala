packagelon com.twittelonr.product_mixelonr.corelon.pipelonlinelon.stelonp

import com.twittelonr.product_mixelonr.corelon.selonrvicelon.elonxeloncutor
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.elonxeloncutorRelonsult
import com.twittelonr.stitch.Arrow

/**
 * A Stelonp within a Pipelonlinelon, a Stelonp is a unitary phaselon within an elonntirelon chain that makelons up a pipelonlinelon.
 * @tparam Statelon Thelon relonquelonst domain modelonl.
 * @tparam elonxeloncutorConfig Thelon configs that should belon passelond into thelon elonxeloncutor at build timelon.
 * @tparam elonxeloncutorInput Thelon input typelon that an elonxeloncutor takelons at relonquelonst timelon.
 * @tparam elonxRelonsult Thelon relonsult that a stelonp's elonxeloncutor will relonturn.
 * @tparam OutputStatelon Thelon final/updatelond statelon a stelonp would output, this is typically taking thelon elonxRelonsult
 *                     and mutating/transforming thelon Statelon.
 */
trait Stelonp[Statelon, -Config, elonxeloncutorInput, elonxRelonsult <: elonxeloncutorRelonsult] {

  /**
   * Adapt thelon statelon into thelon elonxpelonctelond input for thelon Stelonp's Arrow.
   *
   * @param statelon Statelon objelonct passelond into thelon Stelonp.
   * @param config Thelon config objelonct uselond to build thelon elonxeloncutor arrow or input.
   * @relonturn elonxeloncutorInput that is uselond in thelon arrow of thelon undelonrlying elonxeloncutor.
   */
  delonf adaptInput(statelon: Statelon, config: Config): elonxeloncutorInput

  /**
   * Thelon actual arrow to belon elonxeloncutelond for thelon stelonp, taking in thelon adaptelond input from [[adaptInput]]
   * and relonturning thelon elonxpelonctelond relonsult.
   * @param config Runtimelon configurations to configurelon thelon arrow with.
   * @param contelonxt Contelonxt of elonxeloncutor.
   */
  delonf arrow(
    config: Config,
    contelonxt: elonxeloncutor.Contelonxt
  ): Arrow[elonxeloncutorInput, elonxRelonsult]

  /**
   * Whelonthelonr thelon stelonp is considelonrelond a noop/elonmpty baselond off of input beloning passelond in. elonmpty
   * stelonps arelon skippelond whelonn beloning elonxeloncutelond.
   */
  delonf iselonmpty(config: Config): Boolelonan

  /**
   * Updatelon thelon passelond in statelon baselond off of thelon relonsult from [[arrow]]
   * @param statelon Statelon objelonct passelond into thelon Stelonp.
   * @param elonxeloncutorRelonsult elonxeloncutor relonsult relonturnelond from [[arrow]]
   * @param config Thelon config objelonct uselond to build thelon elonxeloncutor arrow or input.
   * @relonturn Updatelond statelon objelonct passelond.
   */
  delonf updatelonStatelon(statelon: Statelon, elonxeloncutorRelonsult: elonxRelonsult, config: Config): Statelon
}
