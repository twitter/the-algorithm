package com.tw ter.ann.serv ce.query_server.common.throttl ng

/**
 * A s mple r ng buffer that keeps track of long values over `w ndow`.
 */
pr vate[throttl ng] class W ndo dStats(w ndow:  nt) {
  pr vate[t ] val buffer = new Array[Long](w ndow)
  pr vate[t ] var  ndex = 0
  pr vate[t ] var sumValue = 0L
  pr vate[t ] var count = 0

  def add(v: Long): Un  = {
    count = math.m n(count + 1, w ndow)
    val old = buffer( ndex)
    buffer( ndex) = v
     ndex = ( ndex + 1) % w ndow
    sumValue += v - old
  }

  def avg: Double = { sumValue.toDouble / count }
  def sum: Long = { sumValue }
}
