packagelon com.twittelonr.cr_mixelonr.param

import com.twittelonr.cr_mixelonr.modelonl.ModelonlConfig.TwoTowelonrFavALL20220808
import com.twittelonr.timelonlinelons.configapi.BaselonConfig
import com.twittelonr.timelonlinelons.configapi.BaselonConfigBuildelonr
import com.twittelonr.timelonlinelons.configapi.FSNamelon
import com.twittelonr.timelonlinelons.configapi.FSParam
import com.twittelonr.timelonlinelons.configapi.FelonaturelonSwitchOvelonrridelonUtil
import com.twittelonr.timelonlinelons.configapi.Param

objelonct ConsumelonrelonmbelonddingBaselondTwoTowelonrParams {
  objelonct ModelonlIdParam
      elonxtelonnds FSParam[String](
        namelon = "consumelonr_elonmbelondding_baselond_two_towelonr_modelonl_id",
        delonfault = TwoTowelonrFavALL20220808,
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
