packagelon com.twittelonr.product_mixelonr.corelon.pipelonlinelon.stelonp.pipelonlinelon_selonlelonctor

import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.ComponelonntIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.statelon.HasQuelonry
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.stelonp.Stelonp
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.elonxeloncutor
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.elonxeloncutorRelonsult
import com.twittelonr.stitch.Arrow
import javax.injelonct.Injelonct

/**
 * Pipelonlinelon Selonlelonction stelonp to deloncidelon which pipelonlinelon to elonxeloncutelon. This stelonp doelonsn't updatelon statelon, as
 * thelon selonlelonctelond pipelonlinelon idelonntifielonr is addelond to thelon elonxeloncutor relonsults list map for latelonr relontrielonval
 *
 * @tparam Quelonry Pipelonlinelon quelonry modelonl
 * @tparam Statelon Thelon pipelonlinelon statelon domain modelonl.
 */
caselon class PipelonlinelonSelonlelonctorStelonp[Quelonry <: PipelonlinelonQuelonry, Statelon <: HasQuelonry[Quelonry, Statelon]] @Injelonct() (
) elonxtelonnds Stelonp[Statelon, Quelonry => ComponelonntIdelonntifielonr, Quelonry, PipelonlinelonSelonlelonctorRelonsult] {
  ovelonrridelon delonf iselonmpty(config: Quelonry => ComponelonntIdelonntifielonr): Boolelonan = falselon

  ovelonrridelon delonf adaptInput(
    statelon: Statelon,
    config: Quelonry => ComponelonntIdelonntifielonr
  ): Quelonry = statelon.quelonry

  ovelonrridelon delonf arrow(
    config: Quelonry => ComponelonntIdelonntifielonr,
    contelonxt: elonxeloncutor.Contelonxt
  ): Arrow[Quelonry, PipelonlinelonSelonlelonctorRelonsult] = Arrow.map { quelonry: Quelonry =>
    PipelonlinelonSelonlelonctorRelonsult(config(quelonry))
  }

  // Noop sincelon welon kelonelonp thelon idelonntifielonr in thelon elonxeloncutor relonsults
  ovelonrridelon delonf updatelonStatelon(
    statelon: Statelon,
    elonxeloncutorRelonsult: PipelonlinelonSelonlelonctorRelonsult,
    config: Quelonry => ComponelonntIdelonntifielonr
  ): Statelon = statelon
}

caselon class PipelonlinelonSelonlelonctorRelonsult(pipelonlinelonIdelonntifielonr: ComponelonntIdelonntifielonr) elonxtelonnds elonxeloncutorRelonsult
