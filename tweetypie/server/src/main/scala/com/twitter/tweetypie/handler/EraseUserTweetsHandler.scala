package com.tw ter.t etyp e
package handler

 mport com.tw ter.f nagle.stats.Stat
 mport com.tw ter.flockdb.cl ent._
 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.t etyp e.thr ftscala._

tra  EraseUserT etsHandler {

  val eraseUserT etsRequest: FutureArrow[EraseUserT etsRequest, Un ]

  val asyncEraseUserT etsRequest: FutureArrow[AsyncEraseUserT etsRequest, Un ]
}

/**
 * T  l brary allows   to erase all of a users's t ets.  's used to clean up
 * t ets after a user deletes t  r account.
 */
object EraseUserT etsHandler {

  /**
   * Bu ld a FutureEffect wh ch, w n called, deletes one page worth of t ets at t 
   * spec f ed flock cursor. W n t  page of t ets has been deleted anot r asyncEraseUserT ets
   * request  s made w h t  updated cursor locat on so that t  next page of t ets can be processed.
   */
  def apply(
    selectPage: FutureArrow[Select[StatusGraph], PageResult[Long]],
    deleteT et: FutureEffect[(T et d, User d)],
    asyncEraseUserT ets: FutureArrow[AsyncEraseUserT etsRequest, Un ],
    stats: StatsRece ver,
    sleep: () => Future[Un ] = () => Future.Un 
  ): EraseUserT etsHandler =
    new EraseUserT etsHandler {
      val latencyStat: Stat = stats.stat("latency_ms")
      val deletedT etsStat: Stat = stats.stat("t ets_deleted_for_erased_user")

      val selectUserT ets: AsyncEraseUserT etsRequest => Select[StatusGraph] =
        (request: AsyncEraseUserT etsRequest) =>
          UserT  l neGraph
            .from(request.user d)
            .w hCursor(Cursor(request.flockCursor))

      // For a prov ded l st of t et ds, delete each one sequent ally, sleep ng bet en each call
      // T   s a rate l m  ng  chan sm to slow down delet ons.
      def deletePage(page: PageResult[Long], expectedUser d: User d): Future[Un ] =
        page.entr es.foldLeft(Future.Un ) { (prev ousFuture, next d) =>
          for {
            _ <- prev ousFuture
            _ <- sleep()
            _ <- deleteT et((next d, expectedUser d))
          } y eld ()
        }

      /**
       *  f   aren't on t  last page, make anot r EraseUserT ets request to delete
       * t  next page of t ets
       */
      val nextRequestOrEnd: (AsyncEraseUserT etsRequest, PageResult[Long]) => Future[Un ] =
        (request: AsyncEraseUserT etsRequest, page: PageResult[Long]) =>
           f (page.nextCursor. sEnd) {
            latencyStat.add(T  .fromM ll seconds(request.startT  stamp).unt lNow. nM ll s)
            deletedT etsStat.add(request.t etCount + page.entr es.s ze)
            Future.Un 
          } else {
            asyncEraseUserT ets(
              request.copy(
                flockCursor = page.nextCursor.value,
                t etCount = request.t etCount + page.entr es.s ze
              )
            )
          }

      overr de val eraseUserT etsRequest: FutureArrow[EraseUserT etsRequest, Un ] =
        FutureArrow { request =>
          asyncEraseUserT ets(
            AsyncEraseUserT etsRequest(
              user d = request.user d,
              flockCursor = Cursor.start.value,
              startT  stamp = T  .now. nM ll s,
              t etCount = 0L
            )
          )
        }

      overr de val asyncEraseUserT etsRequest: FutureArrow[AsyncEraseUserT etsRequest, Un ] =
        FutureArrow { request =>
          for {
            _ <- sleep()

            // get one page of t ets
            page <- selectPage(selectUserT ets(request))

            // delete t ets
            _ <- deletePage(page, request.user d)

            // make call to delete t  next page of t ets
            _ <- nextRequestOrEnd(request, page)
          } y eld ()
        }
    }
}
