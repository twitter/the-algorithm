package com.tw ter.cr_m xer.module.core

 mport com.tw ter.f nagle.stats.LoadedStatsRece ver
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.servo.ut l. mo z ngStatsRece ver

object  mo z ngStatsRece verModule extends Tw terModule {
  overr de def conf gure(): Un  = {
    b nd[StatsRece ver].to nstance(new  mo z ngStatsRece ver(LoadedStatsRece ver))
  }
}
