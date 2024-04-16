package com.tw ter.t  l neranker.observe

 mport com.tw ter.servo.ut l.Gate
 mport com.tw ter.t  l neranker.model.T  l neQuery
 mport com.tw ter.t  l nes.features.Features
 mport com.tw ter.t  l nes.features.UserL st
 mport com.tw ter.t  l nes.observe.DebugObserver
 mport com.tw ter.t  l neranker.{thr ftscala => thr ft}

/**
 * Bu lds t  DebugObserver that  s attac d to thr ft requests.
 * T  class ex sts to central ze t  gates that determ ne w t r or not
 * to enable debug transcr pts for a part cular request.
 */
class DebugObserverBu lder(wh el st: UserL st) {

  lazy val observer: DebugObserver = bu ld()

  pr vate[t ] def bu ld(): DebugObserver = {
    new DebugObserver(queryGate)
  }

  pr vate[observe] def queryGate: Gate[Any] = {
    val shouldEnableDebug = wh el st.user dGate(Features.DebugTranscr pt)

    Gate { a: Any =>
      a match {
        case q: thr ft.EngagedT etsQuery => shouldEnableDebug(q.user d)
        case q: thr ft.RecapHydrat onQuery => shouldEnableDebug(q.user d)
        case q: thr ft.RecapQuery => shouldEnableDebug(q.user d)
        case q: T  l neQuery => shouldEnableDebug(q.user d)
        case _ => false
      }
    }
  }
}
