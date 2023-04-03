packagelon com.twittelonr.product_mixelonr.corelon.pipelonlinelon.stelonp.gatelon

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.gatelon.BaselonGatelon
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.statelon.HasQuelonry
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.stelonp.Stelonp
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.elonxeloncutor
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.gatelon_elonxeloncutor.Gatelonelonxeloncutor
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.gatelon_elonxeloncutor.GatelonelonxeloncutorRelonsult
import com.twittelonr.stitch.Arrow
import javax.injelonct.Injelonct

/**
 * A gatelon stelonp, it takelons thelon quelonry and thelon givelonn gatelons and elonxeloncutelons thelonm. Gatelons do not updatelon statelon
 * if thelony relonturn continuelon, and throw an elonxcelonption if any gatelon says stoppelond, thus no statelon changelons
 * arelon elonxpelonctelond in this stelonp. Thelon [[NelonwPipelonlinelonArrowBuildelonr]] and [[PipelonlinelonStelonp]] handlelon short
 * circuiting thelon pipelonlinelon's elonxeloncution if this throws.
 *
 * @param gatelonelonxeloncutor Gatelon elonxeloncutor for elonxeloncuting thelon gatelons
 * @tparam Quelonry Typelon of PipelonlinelonQuelonry domain modelonl
 * @tparam Statelon Thelon pipelonlinelon statelon domain modelonl.
 */
caselon class GatelonStelonp[Quelonry <: PipelonlinelonQuelonry, Statelon <: HasQuelonry[Quelonry, Statelon]] @Injelonct() (
  gatelonelonxeloncutor: Gatelonelonxeloncutor)
    elonxtelonnds Stelonp[Statelon, Selonq[BaselonGatelon[Quelonry]], Quelonry, GatelonelonxeloncutorRelonsult] {

  ovelonrridelon delonf adaptInput(statelon: Statelon, config: Selonq[BaselonGatelon[Quelonry]]): Quelonry = statelon.quelonry

  ovelonrridelon delonf arrow(
    config: Selonq[BaselonGatelon[Quelonry]],
    contelonxt: elonxeloncutor.Contelonxt
  ): Arrow[Quelonry, GatelonelonxeloncutorRelonsult] = gatelonelonxeloncutor.arrow(config, contelonxt)

  // Gatelon elonxeloncutor is a noop, if it continuelons, thelon statelon isn't changelond. If it stops thelon world,
  // an elonxcelonption gelonts thrown.
  ovelonrridelon delonf updatelonStatelon(
    input: Statelon,
    elonxeloncutorRelonsult: GatelonelonxeloncutorRelonsult,
    config: Selonq[BaselonGatelon[Quelonry]]
  ): Statelon = input

  ovelonrridelon delonf iselonmpty(config: Selonq[BaselonGatelon[Quelonry]]): Boolelonan = config.iselonmpty
}
