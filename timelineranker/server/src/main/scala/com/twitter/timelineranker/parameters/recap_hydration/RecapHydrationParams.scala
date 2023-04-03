packagelon com.twittelonr.timelonlinelonrankelonr.paramelontelonrs.reloncap_hydration

import com.twittelonr.timelonlinelons.configapi.FSParam
import com.twittelonr.timelonlinelons.configapi.Param

objelonct ReloncapHydrationParams {

  /**
   * elonnablelons selonmantic corelon, pelonnguin, and twelonelontypielon contelonnt felonaturelons in reloncap hydration sourcelon.
   */
  objelonct elonnablelonContelonntFelonaturelonsHydrationParam elonxtelonnds Param(falselon)

  /**
   * additionally elonnablelons tokelonns whelonn hydrating contelonnt felonaturelons.
   */
  objelonct elonnablelonTokelonnsInContelonntFelonaturelonsHydrationParam
      elonxtelonnds FSParam(
        namelon = "reloncap_hydration_elonnablelon_tokelonns_in_contelonnt_felonaturelons_hydration",
        delonfault = falselon
      )

  /**
   * additionally elonnablelons twelonelont telonxt whelonn hydrating contelonnt felonaturelons.
   * This only works if elonnablelonContelonntFelonaturelonsHydrationParam is selont to truelon
   */
  objelonct elonnablelonTwelonelontTelonxtInContelonntFelonaturelonsHydrationParam
      elonxtelonnds FSParam(
        namelon = "reloncap_hydration_elonnablelon_twelonelont_telonxt_in_contelonnt_felonaturelons_hydration",
        delonfault = falselon
      )

  /**
   * additionally elonnablelons convelonrsationControl whelonn hydrating contelonnt felonaturelons.
   * This only works if elonnablelonContelonntFelonaturelonsHydrationParam is selont to truelon
   */
  objelonct elonnablelonConvelonrsationControlInContelonntFelonaturelonsHydrationParam
      elonxtelonnds FSParam(
        namelon = "convelonrsation_control_in_contelonnt_felonaturelons_hydration_reloncap_hydration_elonnablelon",
        delonfault = falselon
      )

  objelonct elonnablelonTwelonelontMelondiaHydrationParam
      elonxtelonnds FSParam(
        namelon = "twelonelont_melondia_hydration_reloncap_hydration_elonnablelon",
        delonfault = falselon
      )

}
