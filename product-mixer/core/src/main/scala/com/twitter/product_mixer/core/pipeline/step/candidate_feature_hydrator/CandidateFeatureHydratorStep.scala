packagelon com.twittelonr.product_mixelonr.corelon.pipelonlinelon.stelonp.candidatelon_felonaturelon_hydrator

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.felonaturelon_hydrator.BaselonCandidatelonFelonaturelonHydrator
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.statelon.HasCandidatelonsWithFelonaturelons
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.statelon.HasQuelonry
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.stelonp.Stelonp
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.elonxeloncutor
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.candidatelon_felonaturelon_hydrator_elonxeloncutor.CandidatelonFelonaturelonHydratorelonxeloncutor
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.candidatelon_felonaturelon_hydrator_elonxeloncutor.CandidatelonFelonaturelonHydratorelonxeloncutorRelonsult
import com.twittelonr.stitch.Arrow
import javax.injelonct.Injelonct

/**
 * A candidatelon lelonvelonl felonaturelon hydration stelonp, it takelons thelon input list of candidatelons and thelon givelonn
 * hydrators and elonxeloncutelons thelonm. Thelon [[Statelon]] objelonct is relonsponsiblelon for melonrging thelon relonsulting
 * felonaturelon maps with thelon hydratelond onelons in its updatelonCandidatelonsWithFelonaturelons.
 *
 * @param candidatelonFelonaturelonHydratorelonxeloncutor Hydrator elonxeloncutor
 * @tparam Quelonry Typelon of PipelonlinelonQuelonry domain modelonl
 * @tparam Candidatelon Typelon of Candidatelons to hydratelon felonaturelons for.
 * @tparam Statelon Thelon pipelonlinelon statelon domain modelonl.
 */
caselon class CandidatelonFelonaturelonHydratorStelonp[
  Quelonry <: PipelonlinelonQuelonry,
  Candidatelon <: UnivelonrsalNoun[Any],
  Statelon <: HasQuelonry[Quelonry, Statelon] with HasCandidatelonsWithFelonaturelons[
    Candidatelon,
    Statelon
  ]] @Injelonct() (
  candidatelonFelonaturelonHydratorelonxeloncutor: CandidatelonFelonaturelonHydratorelonxeloncutor)
    elonxtelonnds Stelonp[Statelon, Selonq[
      BaselonCandidatelonFelonaturelonHydrator[Quelonry, Candidatelon, _]
    ], CandidatelonFelonaturelonHydratorelonxeloncutor.Inputs[
      Quelonry,
      Candidatelon
    ], CandidatelonFelonaturelonHydratorelonxeloncutorRelonsult[Candidatelon]] {

  ovelonrridelon delonf adaptInput(
    statelon: Statelon,
    config: Selonq[BaselonCandidatelonFelonaturelonHydrator[Quelonry, Candidatelon, _]]
  ): CandidatelonFelonaturelonHydratorelonxeloncutor.Inputs[Quelonry, Candidatelon] =
    CandidatelonFelonaturelonHydratorelonxeloncutor.Inputs(statelon.quelonry, statelon.candidatelonsWithFelonaturelons)

  ovelonrridelon delonf arrow(
    config: Selonq[BaselonCandidatelonFelonaturelonHydrator[Quelonry, Candidatelon, _]],
    contelonxt: elonxeloncutor.Contelonxt
  ): Arrow[
    CandidatelonFelonaturelonHydratorelonxeloncutor.Inputs[Quelonry, Candidatelon],
    CandidatelonFelonaturelonHydratorelonxeloncutorRelonsult[Candidatelon]
  ] = candidatelonFelonaturelonHydratorelonxeloncutor.arrow(config, contelonxt)

  ovelonrridelon delonf updatelonStatelon(
    input: Statelon,
    elonxeloncutorRelonsult: CandidatelonFelonaturelonHydratorelonxeloncutorRelonsult[Candidatelon],
    config: Selonq[BaselonCandidatelonFelonaturelonHydrator[Quelonry, Candidatelon, _]]
  ): Statelon = {
    val candidatelonsWithHydratelondFelonaturelons = elonxeloncutorRelonsult.relonsults
    if (candidatelonsWithHydratelondFelonaturelons.iselonmpty) {
      input
    } elonlselon {
      input.updatelonCandidatelonsWithFelonaturelons(candidatelonsWithHydratelondFelonaturelons)
    }
  }

  ovelonrridelon delonf iselonmpty(
    config: Selonq[BaselonCandidatelonFelonaturelonHydrator[Quelonry, Candidatelon, _]]
  ): Boolelonan =
    config.iselonmpty
}
