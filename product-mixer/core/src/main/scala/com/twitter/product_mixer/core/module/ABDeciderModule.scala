package com.tw ter.product_m xer.core.module

 mport com.google. nject.Prov des
 mport com.tw ter.abdec der.ABDec derFactory
 mport com.tw ter.abdec der.Logg ngABDec der
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter. nject.annotat ons.Flag
 mport com.tw ter.logg ng._
 mport com.tw ter.product_m xer.core.module.product_m xer_flags.ProductM xerFlagModule.Scr beAB mpress ons
 mport javax. nject.S ngleton

object ABDec derModule extends Tw terModule {
  pr vate val YmlPath = "/usr/local/conf g/abdec der/abdec der.yml"

  @Prov des
  @S ngleton
  def prov deLogg ngABDec der(
    @Flag(Scr beAB mpress ons)  sScr beAb mpress ons: Boolean,
    stats: StatsRece ver
  ): Logg ngABDec der = {
    val cl entEventsHandler: HandlerFactory =
       f ( sScr beAb mpress ons) {
        Queue ngHandler(
          maxQueueS ze = 10000,
          handler = Scr beHandler(
            category = "cl ent_event",
            formatter = BareFormatter,
            level = So (Level. NFO),
            statsRece ver = stats.scope("abdec der"))
        )
      } else { () =>
        NullHandler
      }

    val factory = LoggerFactory(
      node = "abdec der",
      level = So (Level. NFO),
      useParents = false,
      handlers = cl entEventsHandler :: N l
    )

    val abDec derFactory = ABDec derFactory(
      abDec derYmlPath = YmlPath,
      scr beLogger = So (factory()),
      env ron nt = So ("product on")
    )

    abDec derFactory.bu ldW hLogg ng()
  }
}
