package com.tw ter.representat on_manager.modules

 mport com.google. nject.Prov des
 mport com.tw ter.esc rb rd.ut l.uttcl ent.Cac Conf gV2
 mport com.tw ter.esc rb rd.ut l.uttcl ent.Cac dUttCl entV2
 mport com.tw ter.esc rb rd.ut l.uttcl ent.UttCl entCac Conf gsV2
 mport com.tw ter.esc rb rd.utt.strato.thr ftscala.Env ron nt
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.strato.cl ent.{Cl ent => StratoCl ent}
 mport javax. nject.S ngleton

object UttCl entModule extends Tw terModule {

  @S ngleton
  @Prov des
  def prov desUttCl ent(
    stratoCl ent: StratoCl ent,
    statsRece ver: StatsRece ver
  ): Cac dUttCl entV2 = {
    // Save 2 ^ 18 UTTs. Prom s ng 100% cac  rate
    val defaultCac Conf gV2: Cac Conf gV2 = Cac Conf gV2(262143)

    val uttCl entCac Conf gsV2: UttCl entCac Conf gsV2 = UttCl entCac Conf gsV2(
      getTaxono Conf g = defaultCac Conf gV2,
      getUttTaxono Conf g = defaultCac Conf gV2,
      getLeaf ds = defaultCac Conf gV2,
      getLeafUttEnt  es = defaultCac Conf gV2
    )

    // Cac dUttCl ent to use StratoCl ent
    new Cac dUttCl entV2(
      stratoCl ent = stratoCl ent,
      env = Env ron nt.Prod,
      cac Conf gs = uttCl entCac Conf gsV2,
      statsRece ver = statsRece ver.scope("cac d_utt_cl ent")
    )
  }
}
