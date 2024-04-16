package com.tw ter.users gnalserv ce.module
 mport com.google. nject.Prov des
 mport com.tw ter.f nagle.ut l.DefaultT  r
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.ut l.T  r
 mport javax. nject.S ngleton

object T  rModule extends Tw terModule {
  @S ngleton
  @Prov des
  def prov desT  r: T  r = DefaultT  r
}
