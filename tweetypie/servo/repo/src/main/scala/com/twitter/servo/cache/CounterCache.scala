package com.tw ter.servo.cac 

 mport com.tw ter.ut l.{Durat on, Future}

tra  CounterCac [K] extends Cac [K, Long] {
  def  ncr(key: K, delta:  nt = 1): Future[Opt on[Long]]
  def decr(key: K, delta:  nt = 1): Future[Opt on[Long]]
}

class  mcac CounterCac [K](
   mcac :  mcac ,
  ttl: Durat on,
  transformKey: KeyTransfor r[K] = ((k: K) => k.toStr ng): (K => java.lang.Str ng))
    extends  mcac Cac [K, Long]( mcac , ttl, CounterSer al zer, transformKey)
    w h CounterCac [K]

class NullCounterCac [K] extends NullCac [K, Long] w h CounterCac [K] {
  overr de def  ncr(key: K, delta:  nt = 1): Future[Opt on[Long]] = Future.value(So (0L))
  overr de def decr(key: K, delta:  nt = 1): Future[Opt on[Long]] = Future.value(So (0L))
}
