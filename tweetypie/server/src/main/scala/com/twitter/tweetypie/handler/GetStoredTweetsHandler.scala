package com.tw ter.t etyp e
package handler

 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.core.StoredT etResult._
 mport com.tw ter.t etyp e.core.StoredT etResult
 mport com.tw ter.t etyp e.core.T etResult
 mport com.tw ter.t etyp e.F eld d
 mport com.tw ter.t etyp e.FutureArrow
 mport com.tw ter.t etyp e.repos ory.Cac Control
 mport com.tw ter.t etyp e.repos ory.T etQuery
 mport com.tw ter.t etyp e.repos ory.T etResultRepos ory
 mport com.tw ter.t etyp e.thr ftscala.{BounceDeleted => BounceDeletedState}
 mport com.tw ter.t etyp e.thr ftscala.{ForceAdded => ForceAddedState}
 mport com.tw ter.t etyp e.thr ftscala.GetStoredT etsRequest
 mport com.tw ter.t etyp e.thr ftscala.GetStoredT etsOpt ons
 mport com.tw ter.t etyp e.thr ftscala.GetStoredT etsResult
 mport com.tw ter.t etyp e.thr ftscala.{HardDeleted => HardDeletedState}
 mport com.tw ter.t etyp e.thr ftscala.{NotFound => NotFoundState}
 mport com.tw ter.t etyp e.thr ftscala.{SoftDeleted => SoftDeletedState}
 mport com.tw ter.t etyp e.thr ftscala.StatusCounts
 mport com.tw ter.t etyp e.thr ftscala.StoredT etError
 mport com.tw ter.t etyp e.thr ftscala.StoredT et nfo
 mport com.tw ter.t etyp e.thr ftscala.StoredT etState
 mport com.tw ter.t etyp e.thr ftscala.{Undeleted => UndeletedState}

object GetStoredT etsHandler {
  type Type = FutureArrow[GetStoredT etsRequest, Seq[GetStoredT etsResult]]

  def apply(t etRepo: T etResultRepos ory.Type): Type = {
    FutureArrow[GetStoredT etsRequest, Seq[GetStoredT etsResult]] { request =>
      val requestOpt ons: GetStoredT etsOpt ons =
        request.opt ons.getOrElse(GetStoredT etsOpt ons())
      val queryOpt ons = toT etQueryOpt ons(requestOpt ons)

      val result = St ch
        .traverse(request.t et ds) { t et d =>
          t etRepo(t et d, queryOpt ons)
            .map(toStoredT et nfo)
            .map(GetStoredT etsResult(_))
            .handle {
              case _ =>
                GetStoredT etsResult(
                  StoredT et nfo(
                    t et d = t et d,
                    errors = Seq(StoredT etError.Fa ledFetch)
                  )
                )
            }
        }

      St ch.run(result)
    }
  }

  pr vate def toT etQueryOpt ons(opt ons: GetStoredT etsOpt ons): T etQuery.Opt ons = {
    val countsF elds: Set[F eld d] = Set(
      StatusCounts.Favor eCountF eld. d,
      StatusCounts.ReplyCountF eld. d,
      StatusCounts.Ret etCountF eld. d,
      StatusCounts.QuoteCountF eld. d
    )

    T etQuery.Opt ons(
       nclude = GetT etsHandler.Base nclude.also(
        t etF elds = Set(T et.CountsF eld. d) ++ opt ons.add  onalF eld ds,
        countsF elds = countsF elds
      ),
      cac Control = Cac Control.NoCac ,
      enforceV s b l yF lter ng = !opt ons.bypassV s b l yF lter ng,
      forUser d = opt ons.forUser d,
      requ reS ceT et = false,
      fetchStoredT ets = true
    )
  }

  pr vate def toStoredT et nfo(t etResult: T etResult): StoredT et nfo = {
    def translateErrors(errors: Seq[StoredT etResult.Error]): Seq[StoredT etError] = {
      errors.map {
        case StoredT etResult.Error.Corrupt => StoredT etError.Corrupt
        case StoredT etResult.Error.F eldsM ss ngOr nval d =>
          StoredT etError.F eldsM ss ngOr nval d
        case StoredT etResult.Error.ScrubbedF eldsPresent => StoredT etError.ScrubbedF eldsPresent
        case StoredT etResult.Error.ShouldBeHardDeleted => StoredT etError.ShouldBeHardDeleted
      }
    }

    val t etData = t etResult.value

    t etData.storedT etResult match {
      case So (storedT etResult) => {
        val (t et, storedT etState, errors) = storedT etResult match {
          case Present(errors, _) => (So (t etData.t et), None, translateErrors(errors))
          case HardDeleted(softDeletedAtMsec, hardDeletedAtMsec) =>
            (
              So (t etData.t et),
              So (
                StoredT etState.HardDeleted(
                  HardDeletedState(softDeletedAtMsec, hardDeletedAtMsec))),
              Seq()
            )
          case SoftDeleted(softDeletedAtMsec, errors, _) =>
            (
              So (t etData.t et),
              So (StoredT etState.SoftDeleted(SoftDeletedState(softDeletedAtMsec))),
              translateErrors(errors)
            )
          case BounceDeleted(deletedAtMsec, errors, _) =>
            (
              So (t etData.t et),
              So (StoredT etState.BounceDeleted(BounceDeletedState(deletedAtMsec))),
              translateErrors(errors)
            )
          case Undeleted(undeletedAtMsec, errors, _) =>
            (
              So (t etData.t et),
              So (StoredT etState.Undeleted(UndeletedState(undeletedAtMsec))),
              translateErrors(errors)
            )
          case ForceAdded(addedAtMsec, errors, _) =>
            (
              So (t etData.t et),
              So (StoredT etState.ForceAdded(ForceAddedState(addedAtMsec))),
              translateErrors(errors)
            )
          case Fa led(errors) => (None, None, translateErrors(errors))
          case NotFound => (None, So (StoredT etState.NotFound(NotFoundState())), Seq())
        }

        StoredT et nfo(
          t et d = t etData.t et. d,
          t et = t et.map(san  zeNull d aF elds),
          storedT etState = storedT etState,
          errors = errors
        )
      }

      case None =>
        StoredT et nfo(
          t et d = t etData.t et. d,
          t et = So (san  zeNull d aF elds(t etData.t et))
        )
    }
  }

  pr vate def san  zeNull d aF elds(t et: T et): T et = {
    // So   d a f elds are  n  al zed as `null` at t  storage layer.
    //  f t  T et  s  ant to be hard deleted, or  s not hydrated for
    // so  ot r reason but t   d a ent  es st ll ex st,   san  ze
    // t se f elds to allow ser al zat on.
    t et.copy( d a = t et. d a.map(_.map {  d aEnt y =>
       d aEnt y.copy(
        url = Opt on( d aEnt y.url).getOrElse(""),
         d aUrl = Opt on( d aEnt y. d aUrl).getOrElse(""),
         d aUrlHttps = Opt on( d aEnt y. d aUrlHttps).getOrElse(""),
        d splayUrl = Opt on( d aEnt y.d splayUrl).getOrElse(""),
        expandedUrl = Opt on( d aEnt y.expandedUrl).getOrElse(""),
      )
    }))
  }
}
