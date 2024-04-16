package com.tw ter.t etyp e.cach ng

 mport scala.collect on.mutable
 mport com.tw ter.ut l.Future
 mport com.tw ter.st ch.St ch
 mport com.tw ter.st ch.Runner
 mport com.tw ter.st ch.FutureRunner
 mport com.tw ter.st ch.Group

/**
 * Workaround for a  nfel c y  n t   mple ntat on of [[St ch.async]].
 *
 * T  has t  sa  semant cs to [[St ch.async]], w h t  except on
 * that  nterrupts to t  ma n computat on w ll not  nterrupt t 
 * async call.
 *
 * T  problem that t   mple ntat on solves  s that   do not want
 * async calls grouped toget r w h synchronous calls. See t 
 * ma l ng l st thread [1] for d scuss on. T  may eventually be
 * f xed  n St ch.
 */
pr vate[cach ng] object St chAsync {
  // Conta ns a deferred St ch that   want to run asynchronously
  pr vate[t ] class AsyncCall(deferred: => St ch[_]) {
    def call(): St ch[_] = deferred
  }

  pr vate object AsyncGroup extends Group[AsyncCall, Un ] {
    overr de def runner(): Runner[AsyncCall, Un ] =
      new FutureRunner[AsyncCall, Un ] {
        // All of t  deferred calls of any type. W n t y are
        // executed  n `run`, t  normal St ch batch ng and dedup ng
        // w ll occur.
        pr vate[t ] val calls = new mutable.ArrayBuffer[AsyncCall]

        def add(call: AsyncCall): St ch[Un ] = {
          // Just re mber t  deferred call.
          calls.append(call)

          // S nce   don't wa  for t  complet on of t  effect,
          // just return a constant value.
          St ch.Un 
        }

        def run(): Future[_] = {
          // T  future returned from t   nnter  nvocat on of
          // St ch.run  s not l nked to t  returned future, so t se
          // effects are not l nked to t  outer Run  n wh ch t 
          //  thod was  nvoked.
          St ch.run {
            St ch.traverse(calls) { asyncCall: AsyncCall =>
              asyncCall
                .call()
                .l ftToTry // So that an except on w ll not  nterrupt t  ot r calls
            }
          }
          Future.Un 
        }
      }
  }

  def apply(call: => St ch[_]): St ch[Un ] =
    // Group toget r all of t  async calls
    St ch.call(new AsyncCall(call), AsyncGroup)
}
