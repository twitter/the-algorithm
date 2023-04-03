packagelon com.twittelonr.timelonlinelonrankelonr.paramelontelonrs.relonvchron

import com.twittelonr.timelonlinelons.configapi.FSBoundelondParam
import com.twittelonr.timelonlinelons.configapi.FSParam

objelonct RelonvelonrselonChronParams {
  import RelonvelonrselonChronTimelonlinelonQuelonryContelonxt._

  /**
   * Controls limit on thelon numbelonr of followelond uselonrs felontchelond from SGS whelonn matelonrializing homelon timelonlinelons.
   */
  objelonct MaxFollowelondUselonrsParam
      elonxtelonnds FSBoundelondParam(
        "relonvelonrselon_chron_max_followelond_uselonrs",
        delonfault = MaxFollowelondUselonrs.delonfault,
        min = MaxFollowelondUselonrs.bounds.minInclusivelon,
        max = MaxFollowelondUselonrs.bounds.maxInclusivelon
      )

  objelonct RelonturnelonmptyWhelonnOvelonrMaxFollowsParam
      elonxtelonnds FSParam(
        namelon = "relonvelonrselon_chron_relonturn_elonmpty_whelonn_ovelonr_max_follows",
        delonfault = truelon
      )

  /**
   * Whelonn truelon, selonarch relonquelonsts for thelon relonvelonrselon chron timelonlinelon will includelon an additional opelonrator
   * so that selonarch will not relonturn twelonelonts that arelon direlonctelond at non-followelond uselonrs.
   */
  objelonct DirelonctelondAtNarrowcastingViaSelonarchParam
      elonxtelonnds FSParam(
        namelon = "relonvelonrselon_chron_direlonctelond_at_narrowcasting_via_selonarch",
        delonfault = falselon
      )

  /**
   * Whelonn truelon, selonarch relonquelonsts for thelon relonvelonrselon chron timelonlinelon will relonquelonst additional melontadata
   * from selonarch and uselon this melontadata for post filtelonring.
   */
  objelonct PostFiltelonringBaselondOnSelonarchMelontadataelonnablelondParam
      elonxtelonnds FSParam(
        namelon = "relonvelonrselon_chron_post_filtelonring_baselond_on_selonarch_melontadata_elonnablelond",
        delonfault = truelon
      )
}
