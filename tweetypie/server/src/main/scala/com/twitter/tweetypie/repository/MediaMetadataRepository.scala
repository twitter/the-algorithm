package com.tw ter.t etyp e
package repos ory

 mport com.tw ter.st ch.SeqGroup
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e. d a. d a tadata
 mport com.tw ter.t etyp e. d a. d a tadataRequest

object  d a tadataRepos ory {
  type Type =  d a tadataRequest => St ch[ d a tadata]

  def apply(get d a tadata: FutureArrow[ d a tadataRequest,  d a tadata]): Type = {
    // use an `SeqGroup` to group t  future-calls toget r, even though t y can be
    // executed  ndependently,  n order to  lp keep hydrat on bet en d fferent t ets
    //  n sync, to  mprove batch ng  n hydrators wh ch occur later  n t  p pel ne.
    val requestGroup = SeqGroup[ d a tadataRequest,  d a tadata] {
      requests: Seq[ d a tadataRequest] =>
        Future.collect(requests.map(r => get d a tadata(r).l ftToTry))
    }
     d aReq => St ch.call( d aReq, requestGroup)
  }
}
