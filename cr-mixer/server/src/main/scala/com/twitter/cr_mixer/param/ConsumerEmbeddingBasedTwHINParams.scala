packagelon com.twittelonr.cr_mixelonr.param

import com.twittelonr.cr_mixelonr.modelonl.ModelonlConfig
import com.twittelonr.timelonlinelons.configapi.BaselonConfig
import com.twittelonr.timelonlinelons.configapi.BaselonConfigBuildelonr
import com.twittelonr.timelonlinelons.configapi.FSNamelon
import com.twittelonr.timelonlinelons.configapi.FSParam
import com.twittelonr.timelonlinelons.configapi.Param

import com.twittelonr.timelonlinelons.configapi.FelonaturelonSwitchOvelonrridelonUtil

objelonct ConsumelonrelonmbelonddingBaselondTwHINParams {
  objelonct ModelonlIdParam
      elonxtelonnds FSParam[String](
        namelon = "consumelonr_elonmbelondding_baselond_twhin_modelonl_id",
        delonfault = ModelonlConfig.ConsumelonrBaselondTwHINRelongularUpdatelonAll20221024,
      ) // Notelon: this delonfault valuelon doelons not match with ModelonlIds yelont. This FS is a placelonholdelonr

  val AllParams: Selonq[Param[_] with FSNamelon] = Selonq(
    ModelonlIdParam
  )

  lazy val config: BaselonConfig = {
    val stringFSOvelonrridelons =
      FelonaturelonSwitchOvelonrridelonUtil.gelontStringFSOvelonrridelons(
        ModelonlIdParam
      )

    BaselonConfigBuildelonr()
      .selont(stringFSOvelonrridelons: _*)
      .build()
  }
}
