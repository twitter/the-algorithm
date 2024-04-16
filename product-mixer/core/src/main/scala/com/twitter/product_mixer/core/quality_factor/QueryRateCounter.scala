package com.tw ter.product_m xer.core.qual y_factor

 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Stopwatch
 mport com.tw ter.ut l.TokenBucket

/**
 * Query rate counter based on a leaky bucket. For more, see [[com.tw ter.ut l.TokenBucket]].
 */
case class QueryRateCounter pr vate[qual y_factor] (
  queryRateW ndow: Durat on) {

  pr vate val queryRateW ndow nSeconds = queryRateW ndow. nSeconds

  pr vate val leakyBucket: TokenBucket =
    TokenBucket.newLeakyBucket(ttl = queryRateW ndow, reserve = 0, nowMs = Stopwatch.t  M ll s)

  def  ncre nt(count:  nt): Un  = leakyBucket.put(count)

  def getRate(): Double = leakyBucket.count / queryRateW ndow nSeconds
}
