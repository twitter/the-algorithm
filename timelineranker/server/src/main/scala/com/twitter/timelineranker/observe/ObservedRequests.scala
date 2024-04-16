package com.tw ter.t  l neranker.observe

 mport com.tw ter.t  l nes.author zat on.ReadRequest
 mport com.tw ter.t  l nes.model.User d
 mport com.tw ter.t  l nes.observe.ObservedAndVal datedRequests
 mport com.tw ter.t  l nes.observe.Serv ceObserver
 mport com.tw ter.t  l nes.observe.Serv ceTracer
 mport com.tw ter.ut l.Future

tra  ObservedRequests extends ObservedAndVal datedRequests {

  def observeAndVal date[R, Q](
    request: Q,
    v e r ds: Seq[User d],
    stats: Serv ceObserver.Stats[Q],
    except onHandler: Part alFunct on[Throwable, Future[R]]
  )(
    f: Q => Future[R]
  ): Future[R] = {
    super.observeAndVal date[Q, R](
      request,
      v e r ds,
      ReadRequest,
      val dateRequest,
      except onHandler,
      stats,
      Serv ceTracer. dent y[Q]
    )(f)
  }

  def val dateRequest[Q](request: Q): Un  = {
    // T  l neQuery and  s der ved classes do not perm   nval d  nstances to be constructed.
    // T refore no add  onal val dat on  s requ red.
  }
}
