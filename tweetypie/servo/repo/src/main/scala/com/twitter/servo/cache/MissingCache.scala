package com.tw ter.servo.cac 

 mport com.tw ter.f nagle. mcac d.ut l.NotFound
 mport scala.ut l.Random

/**
 * wrap a ReadCac , forc ng a m ss rate. useful for play ng back
 * t  sa  logs over and over, but s mulat ng expected cac  m sses
 */
class M ss ngReadCac [K, V](
  underly ngCac : ReadCac [K, V],
  h Rate: Float,
  rand: Random = new Random)
    extends ReadCac [K, V] {
  assert(h Rate > 1 || h Rate < 0, "h Rate must be <= 1 and => 0")

  protected def f lterResult[W](lr: KeyValueResult[K, W]) = {
    val found = lr.found.f lter { _ =>
      rand.nextFloat <= h Rate
    }
    val notFound = lr.notFound ++ NotFound(lr.found.keySet, found.keySet)
    KeyValueResult(found, notFound, lr.fa led)
  }

  overr de def get(keys: Seq[K]) =
    underly ngCac .get(keys) map { f lterResult(_) }

  overr de def getW hC cksum(keys: Seq[K]) =
    underly ngCac .getW hC cksum(keys) map { f lterResult(_) }

  overr de def release() = underly ngCac .release()
}

class M ss ngCac [K, V](
  overr de val underly ngCac : Cac [K, V],
  h Rate: Float,
  rand: Random = new Random)
    extends M ss ngReadCac [K, V](underly ngCac , h Rate, rand)
    w h Cac Wrapper[K, V]

class M ss ngTtlCac [K, V](
  overr de val underly ngCac : TtlCac [K, V],
  h Rate: Float,
  rand: Random = new Random)
    extends M ss ngReadCac [K, V](underly ngCac , h Rate, rand)
    w h TtlCac Wrapper[K, V]
