package com.tw ter.t  l neranker.cl ents

 mport com.tw ter.f nagle. mcac d.{Cl ent => F nagle mcac Cl ent}
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.servo.cac .F nagle mcac 
 mport com.tw ter.servo.cac . mcac Cac 
 mport com.tw ter.servo.cac .Observable mcac 
 mport com.tw ter.servo.cac .Ser al zer
 mport com.tw ter.servo.cac .StatsRece verCac Observer
 mport com.tw ter.t  l nes.ut l.stats.RequestScope
 mport com.tw ter.t  l nes.ut l.stats.ScopedFactory
 mport com.tw ter.ut l.Durat on

/**
 * Factory to create a servo  mcac -backed Cac  object. Cl ents are requ red to prov de a
 * ser al zer/deser al zer for keys and values.
 */
class  mcac Factory( mcac Cl ent: F nagle mcac Cl ent, statsRece ver: StatsRece ver) {
  pr vate[t ] val logger = Logger.get(getClass.getS mpleNa )

  def apply[K, V](
    keySer al zer: K => Str ng,
    valueSer al zer: Ser al zer[V],
    ttl: Durat on
  ):  mcac Cac [K, V] = {
    new  mcac Cac [K, V](
       mcac  = new Observable mcac (
        new F nagle mcac ( mcac Cl ent),
        new StatsRece verCac Observer(statsRece ver, 1000, logger)
      ),
      ttl = ttl,
      ser al zer = valueSer al zer,
      transformKey = keySer al zer
    )
  }
}

class Scoped mcac Factory( mcac Cl ent: F nagle mcac Cl ent, statsRece ver: StatsRece ver)
    extends ScopedFactory[ mcac Factory] {

  overr de def scope(scope: RequestScope):  mcac Factory = {
    new  mcac Factory(
       mcac Cl ent,
      statsRece ver.scope(" mcac ", scope.scope)
    )
  }
}
