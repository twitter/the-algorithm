packagelon com.twittelonr.timelonlinelonrankelonr.paramelontelonrs.in_nelontwork_twelonelonts

import com.twittelonr.timelonlinelonrankelonr.paramelontelonrs.reloncap.ReloncapQuelonryContelonxt
import com.twittelonr.timelonlinelons.configapi.deloncidelonr._
import com.twittelonr.timelonlinelons.configapi.FSBoundelondParam
import com.twittelonr.timelonlinelons.configapi.FSParam
import com.twittelonr.timelonlinelons.configapi.Param

objelonct InNelontworkTwelonelontParams {
  import ReloncapQuelonryContelonxt._

  /**
   * Controls limit on thelon numbelonr of followelond uselonrs felontchelond from SGS.
   *
   * Thelon speloncific delonfault valuelon belonlow is for blelonndelonr-timelonlinelons parity.
   */
  objelonct MaxFollowelondUselonrsParam
      elonxtelonnds FSBoundelondParam[Int](
        namelon = "reloncyclelond_max_followelond_uselonrs",
        delonfault = MaxFollowelondUselonrs.delonfault,
        min = MaxFollowelondUselonrs.bounds.minInclusivelon,
        max = MaxFollowelondUselonrs.bounds.maxInclusivelon
      )

  /**
   * Controls limit on thelon numbelonr of hits for elonarlybird.
   *
   */
  objelonct RelonlelonvancelonOptionsMaxHitsToProcelonssParam
      elonxtelonnds FSBoundelondParam[Int](
        namelon = "reloncyclelond_relonlelonvancelon_options_max_hits_to_procelonss",
        delonfault = 500,
        min = 100,
        max = 20000
      )

  /**
   * Fallback valuelon for maximum numbelonr of selonarch relonsults, if not speloncifielond by quelonry.maxCount
   */
  objelonct DelonfaultMaxTwelonelontCount elonxtelonnds Param(200)

  /**
   * Welon multiply maxCount (callelonr supplielond valuelon) by this multiplielonr and felontch thoselon many
   * candidatelons from selonarch so that welon arelon lelonft with sufficielonnt numbelonr of candidatelons aftelonr
   * hydration and filtelonring.
   */
  objelonct MaxCountMultiplielonrParam
      elonxtelonnds Param(MaxCountMultiplielonr.delonfault)
      with DeloncidelonrValuelonConvelonrtelonr[Doublelon] {
    ovelonrridelon delonf convelonrt: IntConvelonrtelonr[Doublelon] =
      OutputBoundIntConvelonrtelonr[Doublelon](dividelonDeloncidelonrBy100 _, MaxCountMultiplielonr.bounds)
  }

  /**
   * elonnablelon [[SelonarchQuelonryBuildelonr.crelonatelonelonxcludelondSourcelonTwelonelontIdsQuelonry]]
   */
  objelonct elonnablelonelonxcludelonSourcelonTwelonelontIdsQuelonryParam
      elonxtelonnds FSParam[Boolelonan](
        namelon = "reloncyclelond_elonxcludelon_sourcelon_twelonelont_ids_quelonry_elonnablelon",
        delonfault = falselon
      )

  objelonct elonnablelonelonarlybirdRelonturnAllRelonsultsParam
      elonxtelonnds FSParam[Boolelonan](
        namelon = "reloncyclelond_elonnablelon_elonarlybird_relonturn_all_relonsults",
        delonfault = truelon
      )

  /**
   * FS-controllelond param to elonnablelon anti-dilution transform for DDG-16198
   */
  objelonct ReloncyclelondMaxFollowelondUselonrselonnablelonAntiDilutionParam
      elonxtelonnds FSParam[Boolelonan](
        namelon = "reloncyclelond_max_followelond_uselonrs_elonnablelon_anti_dilution",
        delonfault = falselon
      )

  /**
   * elonnablelons selonmantic corelon, pelonnguin, and twelonelontypielon contelonnt felonaturelons in reloncyclelond sourcelon.
   */
  objelonct elonnablelonContelonntFelonaturelonsHydrationParam elonxtelonnds Param(delonfault = truelon)

  /**
   * additionally elonnablelons tokelonns whelonn hydrating contelonnt felonaturelons.
   */
  objelonct elonnablelonTokelonnsInContelonntFelonaturelonsHydrationParam
      elonxtelonnds FSParam(
        namelon = "reloncyclelond_elonnablelon_tokelonns_in_contelonnt_felonaturelons_hydration",
        delonfault = falselon
      )

  /**
   * additionally elonnablelons twelonelont telonxt whelonn hydrating contelonnt felonaturelons.
   * This only works if elonnablelonContelonntFelonaturelonsHydrationParam is selont to truelon
   */
  objelonct elonnablelonTwelonelontTelonxtInContelonntFelonaturelonsHydrationParam
      elonxtelonnds FSParam(
        namelon = "reloncyclelond_elonnablelon_twelonelont_telonxt_in_contelonnt_felonaturelons_hydration",
        delonfault = falselon
      )

  /**
   * elonnablelons hydrating root twelonelont of in-nelontwork relonplielons and elonxtelonndelond relonplielons
   */
  objelonct elonnablelonRelonplyRootTwelonelontHydrationParam
      elonxtelonnds FSParam(
        namelon = "reloncyclelond_elonnablelon_relonply_root_twelonelont_hydration",
        delonfault = truelon
      )

  /**
   * additionally elonnablelons convelonrsationControl whelonn hydrating contelonnt felonaturelons.
   * This only works if elonnablelonContelonntFelonaturelonsHydrationParam is selont to truelon
   */
  objelonct elonnablelonConvelonrsationControlInContelonntFelonaturelonsHydrationParam
      elonxtelonnds FSParam(
        namelon = "convelonrsation_control_in_contelonnt_felonaturelons_hydration_reloncyclelond_elonnablelon",
        delonfault = falselon
      )

  objelonct elonnablelonTwelonelontMelondiaHydrationParam
      elonxtelonnds FSParam(
        namelon = "twelonelont_melondia_hydration_reloncyclelond_elonnablelon",
        delonfault = falselon
      )

  objelonct elonnablelonelonarlybirdRelonaltimelonCgMigrationParam
      elonxtelonnds FSParam(
        namelon = "reloncyclelond_elonnablelon_elonarlybird_relonaltimelon_cg_migration",
        delonfault = falselon
      )

}
