package com.tw ter.recos njector.conf g

 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f nagle.thr ft.Cl ent d
 mport com.tw ter.fr gate.common.store.T etCreat onT  MHStore
 mport com.tw ter.fr gate.common.ut l.Url nfo
 mport com.tw ter.g zmoduck.thr ftscala.User
 mport com.tw ter.recos njector.dec der.Recos njectorDec der
 mport com.tw ter.soc algraph.thr ftscala.{ dsRequest,  dsResult}
 mport com.tw ter.st ch.t etyp e.T etyP e.T etyP eResult
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.ut l.Future

tra  Conf g { self =>
   mpl c  def statsRece ver: StatsRece ver

  // ReadableStores
  def t etyP eStore: ReadableStore[Long, T etyP eResult]

  def userStore: ReadableStore[Long, User]

  def soc alGraph dStore: ReadableStore[ dsRequest,  dsResult]

  def url nfoStore: ReadableStore[Str ng, Url nfo]

  // Manhattan stores
  def t etCreat onStore: T etCreat onT  MHStore

  // Dec der
  def recos njectorDec der: Recos njectorDec der

  // Constants
  def recos njectorThr ftCl ent d: Cl ent d

  def serv ce dent f er: Serv ce dent f er

  def outputKafkaTop cPref x: Str ng

  def  n (): Future[Un ] = Future.Done
}
