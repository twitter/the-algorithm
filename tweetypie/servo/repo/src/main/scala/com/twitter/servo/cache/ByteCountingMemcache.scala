package com.tw ter.servo.cac 

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ut l.{Durat on, Future}

case class ByteCount ng mcac Factory(
   mcac Factory:  mcac Factory,
  statsRece ver: StatsRece ver,
  del m er: Str ng = constants.Colon,
  c cksumS ze:  nt = 8) //  mcac d c cksums are u64s
    extends  mcac Factory {

  def apply() =
    new ByteCount ng mcac ( mcac Factory(), statsRece ver, del m er, c cksumS ze)
}

/**
 * A decorator around a  mcac  that counts t  rough number
 * of bytes transferred, bucketed & rolled up by  n/out,  thod na ,
 * and key pref x
 */
class ByteCount ng mcac (
  underly ng:  mcac ,
  statsRece ver: StatsRece ver,
  del m er: Str ng,
  c cksumS ze:  nt)
    extends  mcac  {
  val scopedRece ver = statsRece ver.scope(" mcac ").scope("bytes")

  val outStat = scopedRece ver.stat("out")
  val outRece ver = scopedRece ver.scope("out")

  val  nStat = scopedRece ver.stat(" n")
  val  nRece ver = scopedRece ver.scope(" n")

  val getOutStat = outRece ver.stat("get")
  val getOutRece ver = outRece ver.scope("get")

  val get nStat =  nRece ver.stat("get")
  val get nRece ver =  nRece ver.scope("get")
  val get nH sStat = get nRece ver.stat("h s")
  val get nH sRece ver = get nRece ver.scope("h s")
  val get nM ssesStat = get nRece ver.stat("m sses")
  val get nM ssesRece ver = get nRece ver.scope("m sses")

  val gwcOutStat = outRece ver.stat("get_w h_c cksum")
  val gwcOutRece ver = outRece ver.scope("get_w h_c cksum")

  val gwc nStat =  nRece ver.stat("get_w h_c cksum")
  val gwc nRece ver =  nRece ver.scope("get_w h_c cksum")
  val gwc nH sStat = gwcOutRece ver.stat("h s")
  val gwc nH sRece ver = gwcOutRece ver.scope("h s")
  val gwc nM ssesStat = gwcOutRece ver.stat("m sses")
  val gwc nM ssesRece ver = gwcOutRece ver.scope("m sses")

  val addStat = outRece ver.stat("add")
  val addRece ver = outRece ver.scope("add")

  val setStat = outRece ver.stat("set")
  val setRece ver = outRece ver.scope("set")

  val replaceStat = outRece ver.stat("replace")
  val replaceRece ver = outRece ver.scope("replace")

  val casStat = outRece ver.stat("c ck_and_set")
  val casRece ver = outRece ver.scope("c ck_and_set")

  def release() = underly ng.release()

  // get na space from key
  protected[t ] def ns(key: Str ng) = {
    val  dx = math.m n(key.s ze - 1, math.max(key.last ndexOf(del m er), 0))
    key.substr ng(0,  dx).replaceAll(del m er, "_")
  }

  overr de def get(keys: Seq[Str ng]): Future[KeyValueResult[Str ng, Array[Byte]]] = {
    keys foreach { key =>
      val s ze = key.s ze
      outStat.add(s ze)
      getOutStat.add(s ze)
      getOutRece ver.stat(ns(key)).add(s ze)
    }
    underly ng.get(keys) onSuccess { lr =>
      lr.found foreach {
        case (key, bytes) =>
          val s ze = key.s ze + bytes.length
           nStat.add(s ze)
          get nStat.add(s ze)
          get nH sStat.add(s ze)
          get nH sRece ver.stat(ns(key)).add(s ze)
      }
      lr.notFound foreach { key =>
        val s ze = key.s ze
         nStat.add(s ze)
        get nStat.add(s ze)
        get nM ssesStat.add(s ze)
        get nM ssesRece ver.stat(ns(key)).add(s ze)
      }
    }
  }

  overr de def getW hC cksum(
    keys: Seq[Str ng]
  ): Future[CsKeyValueResult[Str ng, Array[Byte]]] = {
    keys foreach { key =>
      val s ze = key.s ze
      outStat.add(s ze)
      gwcOutStat.add(s ze)
      gwcOutRece ver.stat(ns(key)).add(s ze)
    }
    underly ng.getW hC cksum(keys) onSuccess { lr =>
      lr.found foreach {
        case (key, (bytes, _)) =>
          val s ze = key.s ze + (bytes map { _.length } getOrElse (0)) + c cksumS ze
           nStat.add(s ze)
          gwc nStat.add(s ze)
          gwc nH sStat.add(s ze)
          gwc nH sRece ver.stat(ns(key)).add(s ze)
      }
      lr.notFound foreach { key =>
        val s ze = key.s ze
         nStat.add(s ze)
        gwc nStat.add(s ze)
        gwc nM ssesStat.add(s ze)
        gwc nM ssesRece ver.stat(ns(key)).add(s ze)
      }
    }
  }

  overr de def add(key: Str ng, value: Array[Byte], ttl: Durat on): Future[Boolean] = {
    val s ze = key.s ze + value.s ze
    outStat.add(s ze)
    addStat.add(s ze)
    addRece ver.stat(ns(key)).add(s ze)
    underly ng.add(key, value, ttl)
  }

  overr de def c ckAndSet(
    key: Str ng,
    value: Array[Byte],
    c cksum: C cksum,
    ttl: Durat on
  ): Future[Boolean] = {
    val s ze = key.s ze + value.s ze + c cksumS ze
    outStat.add(s ze)
    casStat.add(s ze)
    casRece ver.stat(ns(key)).add(s ze)
    underly ng.c ckAndSet(key, value, c cksum, ttl)
  }

  overr de def set(key: Str ng, value: Array[Byte], ttl: Durat on): Future[Un ] = {
    val s ze = key.s ze + value.s ze
    outStat.add(s ze)
    setStat.add(s ze)
    setRece ver.stat(ns(key)).add(s ze)
    underly ng.set(key, value, ttl)
  }

  overr de def replace(key: Str ng, value: Array[Byte], ttl: Durat on): Future[Boolean] = {
    val s ze = key.s ze + value.s ze
    outStat.add(s ze)
    replaceStat.add(s ze)
    replaceRece ver.stat(ns(key)).add(s ze)
    underly ng.replace(key, value, ttl)
  }

  overr de def delete(key: Str ng): Future[Boolean] = {
    outStat.add(key.s ze)
    underly ng.delete(key)
  }

  overr de def  ncr(key: Str ng, delta: Long = 1): Future[Opt on[Long]] = {
    val s ze = key.s ze + 8
    outStat.add(s ze)
    underly ng. ncr(key, delta)
  }

  overr de def decr(key: Str ng, delta: Long = 1): Future[Opt on[Long]] = {
    val s ze = key.s ze + 8
    outStat.add(s ze)
    underly ng.decr(key, delta)
  }
}
