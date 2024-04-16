package com.tw ter.fr gate.pushserv ce.ut l

 mport com.tw ter.fr gate.common.store.dev ce nfo.Dev ce nfo
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.Target
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams.EnableRuxLand ngPage
 mport com.tw ter.fr gate.pushserv ce.params.PushParams.EnableRuxLand ngPageAndro dParam
 mport com.tw ter.fr gate.pushserv ce.params.PushParams.EnableRuxLand ngPage OSParam
 mport com.tw ter.fr gate.pushserv ce.params.PushParams.RuxLand ngPageExper  ntKeyAndro dParam
 mport com.tw ter.fr gate.pushserv ce.params.PushParams.RuxLand ngPageExper  ntKey OSParam
 mport com.tw ter.fr gate.pushserv ce.params.PushParams.ShowRuxLand ngPageAsModalOn OS
 mport com.tw ter.rux.common.context.thr ftscala.Mag cRecsNTabT et
 mport com.tw ter.rux.common.context.thr ftscala.Mag cRecsPushT et
 mport com.tw ter.rux.common.context.thr ftscala.RuxContext
 mport com.tw ter.rux.common.context.thr ftscala.S ce
 mport com.tw ter.rux.common.encode.RuxContextEncoder

/**
 * T  class prov des ut l y funct ons for ema l land ng page for push
 */
object Ema lLand ngPageExper  ntUt l {
  val ruxCxtEncoder = new RuxContextEncoder()

  def get b s2ModelValue(
    dev ce nfoOpt: Opt on[Dev ce nfo],
    target: Target,
    t et d: Long
  ): Map[Str ng, Str ng] = {
    val enable = enablePushEma lLand ng(dev ce nfoOpt, target)
     f (enable) {
      val ruxCxt =  f (dev ce nfoOpt.ex sts(_. sRuxLand ngPageEl g ble)) {
        val encodedCxt = getRuxContext(t et d, target, dev ce nfoOpt)
        Map("rux_cxt" -> encodedCxt)
      } else Map.empty[Str ng, Str ng]
      val enableModal =  f (showModalFor OS(dev ce nfoOpt, target)) {
        Map("enable_modal" -> "true")
      } else Map.empty[Str ng, Str ng]

      Map("land_on_ema l_land ng_page" -> "true") ++ ruxCxt ++ enableModal
    } else Map.empty[Str ng, Str ng]
  }

  def createNTabRuxLand ngUR (screenNa : Str ng, t et d: Long): Str ng = {
    val encodedCxt =
      ruxCxtEncoder.encode(RuxContext(So (S ce.Mag cRecsNTabT et(Mag cRecsNTabT et(t et d)))))
    s"$screenNa /status/${t et d.toStr ng}?cxt=$encodedCxt"
  }

  pr vate def getRuxContext(
    t et d: Long,
    target: Target,
    dev ce nfoOpt: Opt on[Dev ce nfo]
  ): Str ng = {
    val  sDev ce OS = PushDev ceUt l. sPr maryDev ce OS(dev ce nfoOpt)
    val  sDev ceAndro d = PushDev ceUt l. sPr maryDev ceAndro d(dev ce nfoOpt)
    val keyOpt =  f ( sDev ce OS) {
      target.params(RuxLand ngPageExper  ntKey OSParam)
    } else  f ( sDev ceAndro d) {
      target.params(RuxLand ngPageExper  ntKeyAndro dParam)
    } else None
    val context = RuxContext(So (S ce.Mag cRecsT et(Mag cRecsPushT et(t et d))), None, keyOpt)
    ruxCxtEncoder.encode(context)
  }

  pr vate def enablePushEma lLand ng(
    dev ce nfoOpt: Opt on[Dev ce nfo],
    target: Target
  ): Boolean =
    dev ce nfoOpt.ex sts(dev ce nfo =>
       f (dev ce nfo. sEma lLand ngPageEl g ble) {
        val  sRuxLand ngPageEnabled = target.params(EnableRuxLand ngPage)
         sRuxLand ngPageEnabled &&  sRuxLand ngEnabledBasedOnDev ce nfo(dev ce nfoOpt, target)
      } else false)

  pr vate def showModalFor OS(dev ce nfoOpt: Opt on[Dev ce nfo], target: Target): Boolean = {
    dev ce nfoOpt.ex sts { dev ce nfo =>
      dev ce nfo. sRuxLand ngPageAsModalEl g ble && target.params(ShowRuxLand ngPageAsModalOn OS)
    }
  }

  pr vate def  sRuxLand ngEnabledBasedOnDev ce nfo(
    dev ce nfoOpt: Opt on[Dev ce nfo],
    target: Target
  ): Boolean = {
    val  sDev ce OS = PushDev ceUt l. sPr maryDev ce OS(dev ce nfoOpt)
    val  sDev ceAndro d = PushDev ceUt l. sPr maryDev ceAndro d(dev ce nfoOpt)
     f ( sDev ce OS) {
      target.params(EnableRuxLand ngPage OSParam)
    } else  f ( sDev ceAndro d) {
      target.params(EnableRuxLand ngPageAndro dParam)
    } else true
  }
}
