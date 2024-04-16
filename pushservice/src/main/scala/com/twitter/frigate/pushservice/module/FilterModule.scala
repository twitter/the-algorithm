package com.tw ter.fr gate.pushserv ce.module

 mport com.google. nject.Prov des
 mport javax. nject.S ngleton
 mport com.tw ter.d scovery.common.nackwarmupf lter.NackWarmupF lter
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter. nject.annotat ons.Flag
 mport com.tw ter.ut l.Durat on

object F lterModule extends Tw terModule {
  @S ngleton
  @Prov des
  def prov desNackWarmupF lter(
    @Flag(FlagNa .nackWarmupDurat on) warmupDurat on: Durat on
  ): NackWarmupF lter = new NackWarmupF lter(warmupDurat on)
}
