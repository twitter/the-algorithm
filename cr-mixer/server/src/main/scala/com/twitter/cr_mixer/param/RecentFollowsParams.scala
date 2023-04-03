packagelon com.twittelonr.cr_mixelonr.param

import com.twittelonr.timelonlinelons.configapi.BaselonConfig
import com.twittelonr.timelonlinelons.configapi.BaselonConfigBuildelonr
import com.twittelonr.timelonlinelons.configapi.FSNamelon
import com.twittelonr.timelonlinelons.configapi.FSParam
import com.twittelonr.timelonlinelons.configapi.FelonaturelonSwitchOvelonrridelonUtil
import com.twittelonr.timelonlinelons.configapi.Param

objelonct ReloncelonntFollowsParams {
  objelonct elonnablelonSourcelonParam
      elonxtelonnds FSParam[Boolelonan](
        namelon = "twistly_reloncelonntfollows_elonnablelon_sourcelon",
        delonfault = truelon
      )

  val AllParams: Selonq[Param[_] with FSNamelon] = Selonq(elonnablelonSourcelonParam)
  lazy val config: BaselonConfig = {
    val boolelonanOvelonrridelons = FelonaturelonSwitchOvelonrridelonUtil.gelontBoolelonanFSOvelonrridelons(
      elonnablelonSourcelonParam
    )

    BaselonConfigBuildelonr()
      .selont(boolelonanOvelonrridelons: _*)
      .build()
  }
}
