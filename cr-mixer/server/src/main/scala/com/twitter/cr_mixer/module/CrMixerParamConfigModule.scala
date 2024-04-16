package com.tw ter.cr_m xer.module

 mport com.google. nject.Prov des
 mport com.tw ter.t  l nes.conf gap .Conf g
 mport com.tw ter.cr_m xer.param.CrM xerParamConf g
 mport com.tw ter. nject.Tw terModule
 mport javax. nject.S ngleton

object CrM xerParamConf gModule extends Tw terModule {

  @Prov des
  @S ngleton
  def prov deConf g(): Conf g = {
    CrM xerParamConf g.conf g
  }
}
