package com.tw ter.t etyp e
package handler

 mport com.tw ter.eventbus.cl ent.EventBusPubl s r
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.backends.GeoScrubEventStore.GetGeoScrubT  stamp
 mport com.tw ter.t etyp e.thr ftscala.DeleteLocat onData
 mport com.tw ter.t etyp e.thr ftscala.DeleteLocat onDataRequest

/**
 *  n  ates t  process of remov ng t  geo  nformat on from a user's
 * t ets.
 */
object DeleteLocat onDataHandler {
  type Type = DeleteLocat onDataRequest => Future[Un ]

  def apply(
    getLastScrubT  : GetGeoScrubT  stamp,
    scr be: DeleteLocat onData => Future[Un ],
    eventbus: EventBusPubl s r[DeleteLocat onData]
  ): Type =
    request => {
      // Attempt to bound t  t   range of t  t ets that need to be
      // scrubbed by f nd ng t  most recent scrub t   on record. T 
      //  s an opt m zat on that prevents scrubb ng already-scrubbed
      // t ets, so    s OK  f t  value that   f nd  s occas onally
      // stale or  f t  lookup fa ls. Pr mar ly, t   s  ntended to
      // protect aga nst  ntent onal abuse by enqueue ng mult ple
      // delete_locat on_data events that have to traverse a very long
      // t  l ne.
      St ch
        .run(getLastScrubT  (request.user d))
        //  f t re  s no t  stamp or t  lookup fa led, cont nue w h
        // an unchanged request.
        .handle { case _ => None }
        .flatMap { lastScrubT   =>
          // Due to clock skew,  's poss ble for t  last scrub
          // t  stamp to be larger than t  t  stamp from t  request,
          // but    gnore that so that   keep a fa hful record of
          // user requests. T  execut on of such events w ll end up a
          // no-op.
          val event =
            DeleteLocat onData(
              user d = request.user d,
              t  stampMs = T  .now. nM ll seconds,
              lastT  stampMs = lastScrubT  .map(_. nM ll seconds)
            )

          Future.jo n(
            Seq(
              // Scr be t  event so that   can reprocess events  f
              // t re  s a bug or operat onal  ssue that causes so 
              // events to be lost.
              scr be(event),
              // T  actual delet on process  s handled by t  T etyP e
              // geoscrub daemon.
              eventbus.publ sh(event)
            )
          )
        }
    }
}
