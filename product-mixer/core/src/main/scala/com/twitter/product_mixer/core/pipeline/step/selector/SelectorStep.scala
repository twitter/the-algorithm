packagelon com.twittelonr.product_mixelonr.corelon.pipelonlinelon.stelonp.selonlelonctor

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.Selonlelonctor
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.statelon.HasCandidatelonsWithDelontails
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.statelon.HasQuelonry
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.stelonp.Stelonp
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.elonxeloncutor
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.selonlelonctor_elonxeloncutor.Selonlelonctorelonxeloncutor
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.selonlelonctor_elonxeloncutor.SelonlelonctorelonxeloncutorRelonsult
import com.twittelonr.stitch.Arrow
import javax.injelonct.Injelonct

/**
 * A selonlelonction stelonp, it takelons thelon input list of candidatelons with delontails and thelon givelonn
 * selonlelonctors and elonxeloncutelons thelonm to deloncidelon which candidatelons should belon selonlelonctelond.
 *
 * @param selonlelonctorelonxeloncutor Selonlelonctor elonxeloncutor
 * @tparam Quelonry Typelon of PipelonlinelonQuelonry domain modelonl
 * @tparam Statelon Thelon pipelonlinelon statelon domain modelonl.
 */
caselon class SelonlelonctorStelonp[
  Quelonry <: PipelonlinelonQuelonry,
  Statelon <: HasQuelonry[Quelonry, Statelon] with HasCandidatelonsWithDelontails[Statelon]] @Injelonct() (
  selonlelonctorelonxeloncutor: Selonlelonctorelonxeloncutor)
    elonxtelonnds Stelonp[Statelon, Selonq[
      Selonlelonctor[Quelonry]
    ], Selonlelonctorelonxeloncutor.Inputs[
      Quelonry
    ], SelonlelonctorelonxeloncutorRelonsult] {

  ovelonrridelon delonf adaptInput(
    statelon: Statelon,
    config: Selonq[Selonlelonctor[Quelonry]]
  ): Selonlelonctorelonxeloncutor.Inputs[Quelonry] =
    Selonlelonctorelonxeloncutor.Inputs(statelon.quelonry, statelon.candidatelonsWithDelontails)

  ovelonrridelon delonf arrow(
    config: Selonq[Selonlelonctor[Quelonry]],
    contelonxt: elonxeloncutor.Contelonxt
  ): Arrow[Selonlelonctorelonxeloncutor.Inputs[Quelonry], SelonlelonctorelonxeloncutorRelonsult] =
    selonlelonctorelonxeloncutor.arrow(config, contelonxt)

  ovelonrridelon delonf updatelonStatelon(
    input: Statelon,
    elonxeloncutorRelonsult: SelonlelonctorelonxeloncutorRelonsult,
    config: Selonq[Selonlelonctor[Quelonry]]
  ): Statelon = input.updatelonCandidatelonsWithDelontails(elonxeloncutorRelonsult.selonlelonctelondCandidatelons)

  // Selonlelonction is a bit diffelonrelonnt to othelonr stelonps (i.elon, othelonr stelonps, elonmpty melonans don't changelon anything)
  // whelonrelon an elonmpty selonlelonction list drops all candidatelons.
  ovelonrridelon delonf iselonmpty(config: Selonq[Selonlelonctor[Quelonry]]): Boolelonan = falselon
}
