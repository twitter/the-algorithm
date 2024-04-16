/** Copyr ght 2010 Tw ter,  nc. */
package com.tw ter.t etyp e
package store

 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.add  onalf elds.Add  onalF elds
 mport com.tw ter.t etyp e.storage.F eld
 mport com.tw ter.t etyp e.storage.Response.T etResponse
 mport com.tw ter.t etyp e.storage.Response.T etResponseCode
 mport com.tw ter.t etyp e.storage.T etStorageCl ent
 mport com.tw ter.t etyp e.storage.T etStorageCl ent.GetT et
 mport com.tw ter.t etyp e.storage.T etStorageExcept on
 mport com.tw ter.t etyp e.thr ftscala._
 mport com.tw ter.ut l.Future

case class UpdateT etNotFoundExcept on(t et d: T et d) extends Except on

tra  ManhattanT etStore
    extends T etStoreBase[ManhattanT etStore]
    w h  nsertT et.Store
    w h AsyncDeleteT et.Store
    w h ScrubGeo.Store
    w h SetAdd  onalF elds.Store
    w h DeleteAdd  onalF elds.Store
    w h AsyncDeleteAdd  onalF elds.Store
    w h Takedown.Store
    w h UpdatePoss blySens  veT et.Store
    w h AsyncUpdatePoss blySens  veT et.Store {
  def wrap(w: T etStore.Wrap): ManhattanT etStore =
    new T etStoreWrapper(w, t )
      w h ManhattanT etStore
      w h  nsertT et.StoreWrapper
      w h AsyncDeleteT et.StoreWrapper
      w h ScrubGeo.StoreWrapper
      w h SetAdd  onalF elds.StoreWrapper
      w h DeleteAdd  onalF elds.StoreWrapper
      w h AsyncDeleteAdd  onalF elds.StoreWrapper
      w h Takedown.StoreWrapper
      w h UpdatePoss blySens  veT et.StoreWrapper
      w h AsyncUpdatePoss blySens  veT et.StoreWrapper
}

/**
 * A T etStore  mple ntat on that wr es to Manhattan.
 */
object ManhattanT etStore {
  val Act on: AsyncWr eAct on.Tb rdUpdate.type = AsyncWr eAct on.Tb rdUpdate

  pr vate val log = Logger(getClass)
  pr vate val successResponses = Set(T etResponseCode.Success, T etResponseCode.Deleted)

  case class Annotat onFa lure( ssage: Str ng) extends Except on( ssage)

  def apply(t etStorageCl ent: T etStorageCl ent): ManhattanT etStore = {

    def handleStorageResponses(
      responsesSt ch: St ch[Seq[T etResponse]],
      act on: Str ng
    ): Future[Un ] =
      St ch
        .run(responsesSt ch)
        .onFa lure {
          case ex: T etStorageExcept on => log.warn("fa led on: " + act on, ex)
          case _ =>
        }
        .flatMap { responses =>
          Future.w n(responses.ex sts(resp => !successResponses(resp.overallResponse))) {
            Future.except on(Annotat onFa lure(s"$act on gets fa lure response $responses"))
          }
        }

    def updateT et d a ds(mutat on: Mutat on[ d aEnt y]): T et => T et =
      t et => t et.copy( d a = t et. d a.map(ent  es => ent  es.map(mutat on.endo)))

    /**
     * Does a get and set, and only sets f elds that are allo d to be
     * changed. T  also prevents  ncom ng t ets conta n ng  ncomplete
     * f elds from be ng saved to Manhattan.
     */
    def updateOneT etBy dAct on(t et d: T et d, copyF elds: T et => T et): Future[Un ] = {
      St ch.run {
        t etStorageCl ent.getT et(t et d).flatMap {
          case GetT et.Response.Found(t et) =>
            val updatedT et = copyF elds(t et)

             f (updatedT et != t et) {
              t etStorageCl ent.addT et(updatedT et)
            } else {
              St ch.Un 
            }
          case _ => St ch.except on(UpdateT etNotFoundExcept on(t et d))
        }
      }
    }

    // T  should NOT be used  n parallel w h ot r wr e operat ons.
    // A race cond  on can occur after changes to t  storage l brary to
    // return all add  onal f elds. T  result ng behav or can cause
    // f elds that  re mod f ed by ot r wr es to revert to t  r old value.
    def updateOneT etAct on(update: T et, copyF elds: T et => T et => T et): Future[Un ] =
      updateOneT etBy dAct on(update. d, copyF elds(update))

    def t etStoreUpdateT et(t et: T et): Future[Un ] = {
      val setF elds = Add  onalF elds.nonEmptyAdd  onalF eld ds(t et).map(F eld.add  onalF eld)
      handleStorageResponses(
        t etStorageCl ent.updateT et(t et, setF elds).map(Seq(_)),
        s"updateT et($t et, $setF elds)"
      )
    }

    // T   s an ed  so update t   n  al T et's control
    def update n  alT et(event:  nsertT et.Event): Future[Un ] = {
      event. n  alT etUpdateRequest match {
        case So (request) =>
          updateOneT etBy dAct on(
            request. n  alT et d,
            t et =>  n  alT etUpdate.updateT et(t et, request)
          )
        case None => Future.Un 
      }
    }

    new ManhattanT etStore {
      overr de val  nsertT et: FutureEffect[ nsertT et.Event] =
        FutureEffect[ nsertT et.Event] { event =>
          St ch
            .run(
              t etStorageCl ent.addT et(event. nternalT et.t et)
            ).flatMap(_ => update n  alT et(event))
        }

      overr de val asyncDeleteT et: FutureEffect[AsyncDeleteT et.Event] =
        FutureEffect[AsyncDeleteT et.Event] { event =>
           f (event. sBounceDelete) {
            St ch.run(t etStorageCl ent.bounceDelete(event.t et. d))
          } else {
            St ch.run(t etStorageCl ent.softDelete(event.t et. d))
          }
        }

      overr de val retryAsyncDeleteT et: FutureEffect[
        T etStoreRetryEvent[AsyncDeleteT et.Event]
      ] =
        T etStore.retry(Act on, asyncDeleteT et)

      overr de val scrubGeo: FutureEffect[ScrubGeo.Event] =
        FutureEffect[ScrubGeo.Event] { event =>
          St ch.run(t etStorageCl ent.scrub(event.t et ds, Seq(F eld.Geo)))
        }

      overr de val setAdd  onalF elds: FutureEffect[SetAdd  onalF elds.Event] =
        FutureEffect[SetAdd  onalF elds.Event] { event =>
          t etStoreUpdateT et(event.add  onalF elds)
        }

      overr de val deleteAdd  onalF elds: FutureEffect[DeleteAdd  onalF elds.Event] =
        FutureEffect[DeleteAdd  onalF elds.Event] { event =>
          handleStorageResponses(
            t etStorageCl ent.deleteAdd  onalF elds(
              Seq(event.t et d),
              event.f eld ds.map(F eld.add  onalF eld)
            ),
            s"deleteAdd  onalF elds(${event.t et d}, ${event.f eld ds}})"
          )
        }

      overr de val asyncDeleteAdd  onalF elds: FutureEffect[AsyncDeleteAdd  onalF elds.Event] =
        FutureEffect[AsyncDeleteAdd  onalF elds.Event] { event =>
          handleStorageResponses(
            t etStorageCl ent.deleteAdd  onalF elds(
              Seq(event.t et d),
              event.f eld ds.map(F eld.add  onalF eld)
            ),
            s"deleteAdd  onalF elds(Seq(${event.t et d}), ${event.f eld ds}})"
          )
        }

      overr de val retryAsyncDeleteAdd  onalF elds: FutureEffect[
        T etStoreRetryEvent[AsyncDeleteAdd  onalF elds.Event]
      ] =
        T etStore.retry(Act on, asyncDeleteAdd  onalF elds)

      overr de val takedown: FutureEffect[Takedown.Event] =
        FutureEffect[Takedown.Event] { event =>
          val (f eldsToUpdate, f eldsToDelete) =
            Seq(
              F eld.T etyp eOnlyTakedownCountryCodes,
              F eld.T etyp eOnlyTakedownReasons
            ).f lter(_ => event.updateCodesAndReasons)
              .part  on(f => event.t et.getF eldBlob(f. d). sDef ned)

          val allF eldsToUpdate = Seq(F eld.HasTakedown) ++ f eldsToUpdate

          Future
            .jo n(
              handleStorageResponses(
                t etStorageCl ent
                  .updateT et(event.t et, allF eldsToUpdate)
                  .map(Seq(_)),
                s"updateT et(${event.t et}, $allF eldsToUpdate)"
              ),
              Future.w n(f eldsToDelete.nonEmpty) {
                handleStorageResponses(
                  t etStorageCl ent
                    .deleteAdd  onalF elds(Seq(event.t et. d), f eldsToDelete),
                  s"deleteAdd  onalF elds(Seq(${event.t et. d}), $f eldsToDelete)"
                )
              }
            ).un 
        }

      overr de val updatePoss blySens  veT et: FutureEffect[UpdatePoss blySens  veT et.Event] =
        FutureEffect[UpdatePoss blySens  veT et.Event] { event =>
          updateOneT etAct on(event.t et, T etUpdate.copyNsfwF eldsForUpdate)
        }

      overr de val asyncUpdatePoss blySens  veT et: FutureEffect[
        AsyncUpdatePoss blySens  veT et.Event
      ] =
        FutureEffect[AsyncUpdatePoss blySens  veT et.Event] { event =>
          updateOneT etAct on(event.t et, T etUpdate.copyNsfwF eldsForUpdate)
        }

      overr de val retryAsyncUpdatePoss blySens  veT et: FutureEffect[
        T etStoreRetryEvent[AsyncUpdatePoss blySens  veT et.Event]
      ] =
        T etStore.retry(Act on, asyncUpdatePoss blySens  veT et)

    }
  }
}
