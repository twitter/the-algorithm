package com.tw ter.cr_m xer.module.core

 mport com.google. nject.Prov des
 mport com.tw ter.cr_m xer.model.ModuleNa s
 mport com.tw ter.cr_m xer.scr be.Scr beCategor es
 mport com.tw ter.cr_m xer.scr be.Scr beCategory
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.logg ng.BareFormatter
 mport com.tw ter.logg ng.Level
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.logg ng.NullHandler
 mport com.tw ter.logg ng.Queue ngHandler
 mport com.tw ter.logg ng.Scr beHandler
 mport com.tw ter.logg ng.{LoggerFactory => Tw terLoggerFactory}
 mport javax. nject.Na d
 mport javax. nject.S ngleton

object LoggerFactoryModule extends Tw terModule {

  pr vate val DefaultQueueS ze = 10000

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.AbDec derLogger)
  def prov deAbDec derLogger(
    serv ce dent f er: Serv ce dent f er,
    statsRece ver: StatsRece ver
  ): Logger = {
    bu ldLoggerFactory(
      Scr beCategor es.AbDec der,
      serv ce dent f er.env ron nt,
      statsRece ver.scope("Scr beLogger"))
      .apply()
  }

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.TopLevelAp Ddg tr csLogger)
  def prov deTopLevelAp Ddg tr csLogger(
    serv ce dent f er: Serv ce dent f er,
    statsRece ver: StatsRece ver
  ): Logger = {
    bu ldLoggerFactory(
      Scr beCategor es.TopLevelAp Ddg tr cs,
      serv ce dent f er.env ron nt,
      statsRece ver.scope("Scr beLogger"))
      .apply()
  }

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.T etRecsLogger)
  def prov deT etRecsLogger(
    serv ce dent f er: Serv ce dent f er,
    statsRece ver: StatsRece ver
  ): Logger = {
    bu ldLoggerFactory(
      Scr beCategor es.T etsRecs,
      serv ce dent f er.env ron nt,
      statsRece ver.scope("Scr beLogger"))
      .apply()
  }

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.BlueVer f edT etRecsLogger)
  def prov deV TT etRecsLogger(
    serv ce dent f er: Serv ce dent f er,
    statsRece ver: StatsRece ver
  ): Logger = {
    bu ldLoggerFactory(
      Scr beCategor es.V TT etsRecs,
      serv ce dent f er.env ron nt,
      statsRece ver.scope("Scr beLogger"))
      .apply()
  }

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.RelatedT etsLogger)
  def prov deRelatedT etsLogger(
    serv ce dent f er: Serv ce dent f er,
    statsRece ver: StatsRece ver
  ): Logger = {
    bu ldLoggerFactory(
      Scr beCategor es.RelatedT ets,
      serv ce dent f er.env ron nt,
      statsRece ver.scope("Scr beLogger"))
      .apply()
  }

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.UtegT etsLogger)
  def prov deUtegT etsLogger(
    serv ce dent f er: Serv ce dent f er,
    statsRece ver: StatsRece ver
  ): Logger = {
    bu ldLoggerFactory(
      Scr beCategor es.UtegT ets,
      serv ce dent f er.env ron nt,
      statsRece ver.scope("Scr beLogger"))
      .apply()
  }

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.AdsRecom ndat onsLogger)
  def prov deAdsRecom ndat onsLogger(
    serv ce dent f er: Serv ce dent f er,
    statsRece ver: StatsRece ver
  ): Logger = {
    bu ldLoggerFactory(
      Scr beCategor es.AdsRecom ndat ons,
      serv ce dent f er.env ron nt,
      statsRece ver.scope("Scr beLogger"))
      .apply()
  }

  pr vate def bu ldLoggerFactory(
    category: Scr beCategory,
    env ron nt: Str ng,
    statsRece ver: StatsRece ver
  ): Tw terLoggerFactory = {
    env ron nt match {
      case "prod" =>
        Tw terLoggerFactory(
          node = category.getProdLoggerFactoryNode,
          level = So (Level. NFO),
          useParents = false,
          handlers = L st(
            Queue ngHandler(
              maxQueueS ze = DefaultQueueS ze,
              handler = Scr beHandler(
                category = category.scr beCategory,
                formatter = BareFormatter,
                statsRece ver = statsRece ver.scope(category.getProdLoggerFactoryNode)
              )
            )
          )
        )
      case _ =>
        Tw terLoggerFactory(
          node = category.getStag ngLoggerFactoryNode,
          level = So (Level.DEBUG),
          useParents = false,
          handlers = L st(
            { () => NullHandler }
          )
        )
    }
  }
}
