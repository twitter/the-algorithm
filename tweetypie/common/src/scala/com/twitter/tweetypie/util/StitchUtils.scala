package com.tw ter.t etyp e.ut l

 mport com.tw ter.f nagle.stats.Stat
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.servo
 mport com.tw ter.ut l.Return
 mport com.tw ter.ut l.Throw
 mport com.tw ter.st ch.St ch

object St chUt ls {
  def trackLatency[T](latencyStat: Stat, s: => St ch[T]): St ch[T] = {
    St ch
      .t  (s)
      .map {
        case (res, durat on) =>
          latencyStat.add(durat on. nM ll s)
          res
      }
      .lo rFromTry
  }

  def observe[T](statsRece ver: StatsRece ver, ap Na : Str ng): St ch[T] => St ch[T] = {
    val stats = statsRece ver.scope(ap Na )

    val requests = stats.counter("requests")
    val success = stats.counter("success")
    val latencyStat = stats.stat("latency_ms")

    val except onCounter =
      new servo.ut l.Except onCounter(stats, "fa lures")

    st ch =>
      trackLatency(latencyStat, st ch)
        .respond {
          case Return(_) =>
            requests. ncr()
            success. ncr()

          case Throw(e) =>
            except onCounter(e)
            requests. ncr()
        }
  }

  def translateExcept ons[T](
    st ch: St ch[T],
    translateExcept on: Part alFunct on[Throwable, Throwable]
  ): St ch[T] =
    st ch.rescue {
      case t  f translateExcept on. sDef nedAt(t) =>
        St ch.except on(translateExcept on(t))
      case t => St ch.except on(t)
    }
}
