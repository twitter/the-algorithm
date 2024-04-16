package com.tw ter.servo.cac 

 mport com.tw ter.ut l.{Durat on, Future}

/**
 * [[ mcac ]]  s a Cac  w h types that reflect t   mcac d protocol. Keys are str ngs and
 * values are byte arrays.
 */
tra   mcac  extends TtlCac [Str ng, Array[Byte]] {
  def  ncr(key: Str ng, delta: Long = 1): Future[Opt on[Long]]
  def decr(key: Str ng, delta: Long = 1): Future[Opt on[Long]]
}

/**
 * allows one  mcac  to wrap anot r
 */
tra   mcac Wrapper extends TtlCac Wrapper[Str ng, Array[Byte]] w h  mcac  {
  overr de def underly ngCac :  mcac 

  overr de def  ncr(key: Str ng, delta: Long = 1) = underly ngCac . ncr(key, delta)
  overr de def decr(key: Str ng, delta: Long = 1) = underly ngCac .decr(key, delta)
}

/**
 * Sw ch bet en two cac s w h a dec der value
 */
class Dec derable mcac (pr mary:  mcac , secondary:  mcac ,  sAva lable: => Boolean)
    extends  mcac Wrapper {
  overr de def underly ngCac  =  f ( sAva lable) pr mary else secondary
}

/**
 * [[ mcac Cac ]] converts a [[ mcac ]] to a [[Cac [K, V]]] us ng a [[Ser al zer]] for values
 * and a [[KeyTransfor r]] for keys.
 *
 * T  value ser al zer  s b d rect onal. Keys are ser al zed us ng a one-way transformat on
 *  thod, wh ch defaults to _.toStr ng.
 */
class  mcac Cac [K, V](
   mcac :  mcac ,
  ttl: Durat on,
  ser al zer: Ser al zer[V],
  transformKey: KeyTransfor r[K] = new ToStr ngKeyTransfor r[K]: ToStr ngKeyTransfor r[K])
    extends Cac Wrapper[K, V] {
  overr de val underly ngCac  = new KeyValueTransform ngCac (
    new S mpleTtlCac ToCac ( mcac , ttl),
    ser al zer,
    transformKey
  )

  def  ncr(key: K, delta:  nt = 1): Future[Opt on[Long]] = {
     f (delta >= 0)
       mcac . ncr(transformKey(key), delta)
    else
       mcac .decr(transformKey(key), -delta)
  }

  def decr(key: K, delta:  nt = 1): Future[Opt on[Long]] =  ncr(key, -delta)
}
