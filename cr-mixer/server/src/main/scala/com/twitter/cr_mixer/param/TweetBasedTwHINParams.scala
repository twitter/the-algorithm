packagelon com.twittelonr.cr_mixelonr.param

import com.twittelonr.cr_mixelonr.modelonl.ModelonlConfig
import com.twittelonr.timelonlinelons.configapi.BaselonConfig
import com.twittelonr.timelonlinelons.configapi.BaselonConfigBuildelonr
import com.twittelonr.timelonlinelons.configapi.FSNamelon
import com.twittelonr.timelonlinelons.configapi.FSParam
import com.twittelonr.timelonlinelons.configapi.FelonaturelonSwitchOvelonrridelonUtil
import com.twittelonr.timelonlinelons.configapi.Param

objelonct TwelonelontBaselondTwHINParams {
  objelonct ModelonlIdParam
      elonxtelonnds FSParam[String](
        namelon = "twelonelont_baselond_twhin_modelonl_id",
        delonfault = ModelonlConfig.TwelonelontBaselondTwHINRelongularUpdatelonAll20221024,
      )

  val AllParams: Selonq[Param[_] with FSNamelon] = Selonq(ModelonlIdParam)

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
