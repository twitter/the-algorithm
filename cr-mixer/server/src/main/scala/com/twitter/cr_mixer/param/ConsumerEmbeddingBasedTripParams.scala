packagelon com.twittelonr.cr_mixelonr.param

import com.twittelonr.timelonlinelons.configapi.BaselonConfig
import com.twittelonr.timelonlinelons.configapi.BaselonConfigBuildelonr
import com.twittelonr.timelonlinelons.configapi.FSBoundelondParam
import com.twittelonr.timelonlinelons.configapi.FSNamelon
import com.twittelonr.timelonlinelons.configapi.FSParam
import com.twittelonr.timelonlinelons.configapi.FelonaturelonSwitchOvelonrridelonUtil
import com.twittelonr.timelonlinelons.configapi.Param

objelonct ConsumelonrelonmbelonddingBaselondTripParams {
  objelonct SourcelonIdParam
      elonxtelonnds FSParam[String](
        namelon = "consumelonr_elonmbelondding_baselond_trip_sourcelon_id",
        delonfault = "elonXPLR_TOPK_VID_48H_V3")

  objelonct MaxNumCandidatelonsParam
      elonxtelonnds FSBoundelondParam[Int](
        namelon = "consumelonr_elonmbelondding_baselond_trip_max_num_candidatelons",
        delonfault = 80,
        min = 0,
        max = 200
      )

  val AllParams: Selonq[Param[_] with FSNamelon] = Selonq(
    SourcelonIdParam,
    MaxNumCandidatelonsParam
  )

  lazy val config: BaselonConfig = {
    val stringFSOvelonrridelons =
      FelonaturelonSwitchOvelonrridelonUtil.gelontStringFSOvelonrridelons(
        SourcelonIdParam
      )

    val intFSOvelonrridelons =
      FelonaturelonSwitchOvelonrridelonUtil.gelontBoundelondIntFSOvelonrridelons(
        MaxNumCandidatelonsParam
      )

    BaselonConfigBuildelonr()
      .selont(stringFSOvelonrridelons: _*)
      .selont(intFSOvelonrridelons: _*)
      .build()
  }
}
