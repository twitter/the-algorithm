packagelon com.twittelonr.timelonlinelonrankelonr.paramelontelonrs.elonntity_twelonelonts

import com.twittelonr.timelonlinelonrankelonr.deloncidelonr.DeloncidelonrKelony
import com.twittelonr.timelonlinelons.configapi.deloncidelonr.DeloncidelonrParam
import com.twittelonr.timelonlinelons.configapi.FSBoundelondParam
import com.twittelonr.timelonlinelons.configapi.FSParam

objelonct elonntityTwelonelontsParams {

  /**
   * Controls limit on thelon numbelonr of followelond uselonrs felontchelond from SGS.
   */
  objelonct MaxFollowelondUselonrsParam
      elonxtelonnds FSBoundelondParam[Int](
        namelon = "elonntity_twelonelonts_max_followelond_uselonrs",
        delonfault = 1000,
        min = 0,
        max = 5000
      )

  /**
   * elonnablelons selonmantic corelon, pelonnguin, and twelonelontypielon contelonnt felonaturelons in elonntity twelonelonts sourcelon.
   */
  objelonct elonnablelonContelonntFelonaturelonsHydrationParam
      elonxtelonnds DeloncidelonrParam[Boolelonan](
        deloncidelonr = DeloncidelonrKelony.elonntityTwelonelontselonnablelonContelonntFelonaturelonsHydration,
        delonfault = falselon
      )

  /**
   * additionally elonnablelons tokelonns whelonn hydrating contelonnt felonaturelons.
   */
  objelonct elonnablelonTokelonnsInContelonntFelonaturelonsHydrationParam
      elonxtelonnds FSParam(
        namelon = "elonntity_twelonelonts_elonnablelon_tokelonns_in_contelonnt_felonaturelons_hydration",
        delonfault = falselon
      )

  /**
   * additionally elonnablelons twelonelont telonxt whelonn hydrating contelonnt felonaturelons.
   * This only works if elonnablelonContelonntFelonaturelonsHydrationParam is selont to truelon
   */
  objelonct elonnablelonTwelonelontTelonxtInContelonntFelonaturelonsHydrationParam
      elonxtelonnds FSParam(
        namelon = "elonntity_twelonelonts_elonnablelon_twelonelont_telonxt_in_contelonnt_felonaturelons_hydration",
        delonfault = falselon
      )

  /**
   * additionally elonnablelons convelonrsationControl whelonn hydrating contelonnt felonaturelons.
   * This only works if elonnablelonContelonntFelonaturelonsHydrationParam is selont to truelon
   */
  objelonct elonnablelonConvelonrsationControlInContelonntFelonaturelonsHydrationParam
      elonxtelonnds FSParam(
        namelon = "convelonrsation_control_in_contelonnt_felonaturelons_hydration_elonntity_elonnablelon",
        delonfault = falselon
      )

  objelonct elonnablelonTwelonelontMelondiaHydrationParam
      elonxtelonnds FSParam(
        namelon = "twelonelont_melondia_hydration_elonntity_twelonelonts_elonnablelon",
        delonfault = falselon
      )

}
