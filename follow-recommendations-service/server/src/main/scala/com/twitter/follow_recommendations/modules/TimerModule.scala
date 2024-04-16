package com.tw ter.follow_recom ndat ons.modules

 mport com.google. nject.Prov des
 mport com.google. nject.S ngleton
 mport com.tw ter.f nagle. mcac d.ZookeeperStateMon or.DefaultT  r
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.ut l.T  r

object T  rModule extends Tw terModule {
  @Prov des
  @S ngleton
  def prov desT  r: T  r = DefaultT  r
}
