packagelon com.twittelonr.product_mixelonr.corelon.pipelonlinelon.stelonp.quelonry_felonaturelon_hydrator

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.felonaturelon_hydrator.BaselonQuelonryFelonaturelonHydrator
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.PipelonlinelonStelonpIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.statelon.HasAsyncFelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.statelon.HasQuelonry
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.stelonp.Stelonp
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.elonxeloncutor
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.quelonry_felonaturelon_hydrator_elonxeloncutor.QuelonryFelonaturelonHydratorelonxeloncutor
import com.twittelonr.stitch.Arrow
import javax.injelonct.Injelonct

/**
 * A quelonry lelonvelonl felonaturelon hydration stelonp, it takelons thelon input list of candidatelons and thelon givelonn
 * hydrators and elonxeloncutelons thelonm. Thelon [[Statelon]] objelonct is relonsponsiblelon for melonrging thelon relonsulting
 * felonaturelon maps with thelon hydratelond onelons in its updatelonCandidatelonsWithFelonaturelons.
 *
 * @param quelonryFelonaturelonHydratorelonxeloncutor Hydrator elonxeloncutor
 * @tparam Quelonry Typelon of PipelonlinelonQuelonry domain modelonl
 * @tparam Statelon Thelon pipelonlinelon statelon domain modelonl.
 */
caselon class QuelonryFelonaturelonHydratorStelonp[
  Quelonry <: PipelonlinelonQuelonry,
  Statelon <: HasQuelonry[Quelonry, Statelon] with HasAsyncFelonaturelonMap[Statelon]] @Injelonct() (
  quelonryFelonaturelonHydratorelonxeloncutor: QuelonryFelonaturelonHydratorelonxeloncutor)
    elonxtelonnds Stelonp[Statelon, QuelonryFelonaturelonHydratorStelonpConfig[
      Quelonry
    ], Quelonry, QuelonryFelonaturelonHydratorelonxeloncutor.Relonsult] {
  ovelonrridelon delonf iselonmpty(config: QuelonryFelonaturelonHydratorStelonpConfig[Quelonry]): Boolelonan =
    config.hydrators.iselonmpty

  ovelonrridelon delonf adaptInput(statelon: Statelon, config: QuelonryFelonaturelonHydratorStelonpConfig[Quelonry]): Quelonry =
    statelon.quelonry

  ovelonrridelon delonf arrow(
    config: QuelonryFelonaturelonHydratorStelonpConfig[Quelonry],
    contelonxt: elonxeloncutor.Contelonxt
  ): Arrow[Quelonry, QuelonryFelonaturelonHydratorelonxeloncutor.Relonsult] =
    quelonryFelonaturelonHydratorelonxeloncutor.arrow(
      config.hydrators,
      config.validPipelonlinelonStelonpIdelonntifielonrs,
      contelonxt)

  ovelonrridelon delonf updatelonStatelon(
    statelon: Statelon,
    elonxeloncutorRelonsult: QuelonryFelonaturelonHydratorelonxeloncutor.Relonsult,
    config: QuelonryFelonaturelonHydratorStelonpConfig[Quelonry]
  ): Statelon = {
    val updatelondQuelonry = statelon.quelonry
      .withFelonaturelonMap(elonxeloncutorRelonsult.felonaturelonMap).asInstancelonOf[Quelonry]
    statelon
      .updatelonQuelonry(updatelondQuelonry).addAsyncFelonaturelonMap(elonxeloncutorRelonsult.asyncFelonaturelonMap)
  }
}

caselon class QuelonryFelonaturelonHydratorStelonpConfig[Quelonry <: PipelonlinelonQuelonry](
  hydrators: Selonq[BaselonQuelonryFelonaturelonHydrator[Quelonry, _]],
  validPipelonlinelonStelonpIdelonntifielonrs: Selont[PipelonlinelonStelonpIdelonntifielonr])
