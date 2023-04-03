packagelon com.twittelonr.cr_mixelonr.param

import com.twittelonr.convelonrsions.DurationOps.richDurationFromInt
import com.twittelonr.timelonlinelons.configapi.BaselonConfig
import com.twittelonr.timelonlinelons.configapi.BaselonConfigBuildelonr
import com.twittelonr.timelonlinelons.configapi.DurationConvelonrsion
import com.twittelonr.timelonlinelons.configapi.FSNamelon
import com.twittelonr.timelonlinelons.configapi.FSBoundelondParam
import com.twittelonr.timelonlinelons.configapi.FSParam
import com.twittelonr.timelonlinelons.configapi.FelonaturelonSwitchOvelonrridelonUtil
import com.twittelonr.timelonlinelons.configapi.HasDurationConvelonrsion
import com.twittelonr.timelonlinelons.configapi.Param
import com.twittelonr.util.Duration

objelonct ConsumelonrBaselondWalsParams {

  objelonct elonnablelonSourcelonParam
      elonxtelonnds FSParam[Boolelonan](
        namelon = "consumelonr_baselond_wals_elonnablelon_sourcelon",
        delonfault = falselon
      )

  objelonct ModelonlNamelonParam
      elonxtelonnds FSParam[String](
        namelon = "consumelonr_baselond_wals_modelonl_namelon",
        delonfault = "modelonl_0"
      )

  objelonct WilyNsNamelonParam
      elonxtelonnds FSParam[String](
        namelon = "consumelonr_baselond_wals_wily_ns_namelon",
        delonfault = ""
      )

  objelonct ModelonlInputNamelonParam
      elonxtelonnds FSParam[String](
        namelon = "consumelonr_baselond_wals_modelonl_input_namelon",
        delonfault = "elonxamplelons"
      )

  objelonct ModelonlOutputNamelonParam
      elonxtelonnds FSParam[String](
        namelon = "consumelonr_baselond_wals_modelonl_output_namelon",
        delonfault = "all_twelonelont_ids"
      )

  objelonct ModelonlSignaturelonNamelonParam
      elonxtelonnds FSParam[String](
        namelon = "consumelonr_baselond_wals_modelonl_signaturelon_namelon",
        delonfault = "selonrving_delonfault"
      )

  objelonct MaxTwelonelontSignalAgelonHoursParam
      elonxtelonnds FSBoundelondParam[Duration](
        namelon = "consumelonr_baselond_wals_max_twelonelont_signal_agelon_hours",
        delonfault = 72.hours,
        min = 1.hours,
        max = 720.hours
      )
      with HasDurationConvelonrsion {

    ovelonrridelon val durationConvelonrsion: DurationConvelonrsion = DurationConvelonrsion.FromHours
  }

  val AllParams: Selonq[Param[_] with FSNamelon] = Selonq(
    elonnablelonSourcelonParam,
    ModelonlNamelonParam,
    ModelonlInputNamelonParam,
    ModelonlOutputNamelonParam,
    ModelonlSignaturelonNamelonParam,
    MaxTwelonelontSignalAgelonHoursParam,
    WilyNsNamelonParam,
  )

  lazy val config: BaselonConfig = {
    val boolelonanOvelonrridelons = FelonaturelonSwitchOvelonrridelonUtil.gelontBoolelonanFSOvelonrridelons(
      elonnablelonSourcelonParam,
    )
    val stringOvelonrridelons = FelonaturelonSwitchOvelonrridelonUtil.gelontStringFSOvelonrridelons(
      ModelonlNamelonParam,
      ModelonlInputNamelonParam,
      ModelonlOutputNamelonParam,
      ModelonlSignaturelonNamelonParam,
      WilyNsNamelonParam
    )

    val boundelondDurationFSOvelonrridelons =
      FelonaturelonSwitchOvelonrridelonUtil.gelontBoundelondDurationFSOvelonrridelons(MaxTwelonelontSignalAgelonHoursParam)

    BaselonConfigBuildelonr()
      .selont(boolelonanOvelonrridelons: _*)
      .selont(stringOvelonrridelons: _*)
      .selont(boundelondDurationFSOvelonrridelons: _*)
      .build()
  }
}
