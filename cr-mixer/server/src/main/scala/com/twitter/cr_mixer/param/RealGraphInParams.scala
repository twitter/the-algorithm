packagelon com.twittelonr.cr_mixelonr.param

import com.twittelonr.timelonlinelons.configapi._

objelonct RelonalGraphInParams {
  objelonct elonnablelonSourcelonGraphParam
      elonxtelonnds FSParam[Boolelonan](
        namelon = "graph_relonalgraphin_elonnablelon_sourcelon",
        delonfault = falselon
      )

  val AllParams: Selonq[Param[_] with FSNamelon] = Selonq(
    elonnablelonSourcelonGraphParam,
  )

  lazy val config: BaselonConfig = {
    val boolelonanOvelonrridelons = FelonaturelonSwitchOvelonrridelonUtil.gelontBoolelonanFSOvelonrridelons(
      elonnablelonSourcelonGraphParam
    )

    BaselonConfigBuildelonr()
      .selont(boolelonanOvelonrridelons: _*)
      .build()
  }
}
