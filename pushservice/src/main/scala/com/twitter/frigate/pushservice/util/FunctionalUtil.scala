package com.tw ter.fr gate.pushserv ce.ut l

 mport com.tw ter.f nagle.stats.Counter

object Funct onalUt l {
  def  ncr[T](counter: Counter): T => T = { x =>
    {
      counter. ncr()
      x
    }
  }
}
