package com.tw ter.t etyp e
package hydrator

 mport com.tw ter.t etyp e.thr ftscala._

object ScrubUncac able {

  // A mutat on to use for scrubb ng t ets for cac 
  val t etMutat on: Mutat on[T et] =
    Mutat on { t et =>
       f (t et.place != None ||
        t et.counts != None ||
        t et.dev ceS ce != None ||
        t et.perspect ve != None ||
        t et.cards != None ||
        t et.card2 != None ||
        t et.spamLabels != None ||
        t et.conversat onMuted != None)
        So (
          t et.copy(
            place = None,
            counts = None,
            dev ceS ce = None,
            perspect ve = None,
            cards = None,
            card2 = None,
            spamLabels = None,
            conversat onMuted = None
          )
        )
      else
        None
    }

  // throws an Assert onError  f a t et w n a t et  s scrubbed
  def assertNotScrubbed( ssage: Str ng): Mutat on[T et] =
    t etMutat on.w hEffect(Effect(update => assert(update. sEmpty,  ssage)))
}
