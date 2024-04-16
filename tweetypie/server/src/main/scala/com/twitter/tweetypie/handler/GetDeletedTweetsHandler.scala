package com.tw ter.t etyp e
package handler

 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.core. nternalServerError
 mport com.tw ter.t etyp e.core.OverCapac y
 mport com.tw ter.t etyp e.storage.Response.T etResponseCode
 mport com.tw ter.t etyp e.storage.T etStorageCl ent.GetT et
 mport com.tw ter.t etyp e.storage.DeleteState
 mport com.tw ter.t etyp e.storage.DeletedT etResponse
 mport com.tw ter.t etyp e.storage.RateL m ed
 mport com.tw ter.t etyp e.storage.T etStorageCl ent
 mport com.tw ter.t etyp e.thr ftscala._

/**
 * Allow access to raw, unhydrated deleted t et f elds from storage backends (currently Manhattan)
 */
object GetDeletedT etsHandler {

  type Type = FutureArrow[GetDeletedT etsRequest, Seq[GetDeletedT etResult]]
  type T etsEx st = Seq[T et d] => St ch[Set[T et d]]

  def processT etResponse(response: Try[GetT et.Response]): St ch[Opt on[T et]] = {
     mport GetT et.Response._

    response match {
      case Return(Found(t et)) => St ch.value(So (t et))
      case Return(Deleted | NotFound | BounceDeleted(_)) => St ch.None
      case Throw(_: RateL m ed) => St ch.except on(OverCapac y("manhattan"))
      case Throw(except on) => St ch.except on(except on)
    }
  }

  def convertDeletedT etResponse(
    r: DeletedT etResponse,
    extant ds: Set[T et d]
  ): GetDeletedT etResult = {
    val  d = r.t et d
     f (extant ds.conta ns( d) || r.deleteState == DeleteState.NotDeleted) {
      GetDeletedT etResult( d, DeletedT etState.NotDeleted)
    } else {
      r.overallResponse match {
        case T etResponseCode.Success =>
          GetDeletedT etResult( d, convertState(r.deleteState), r.t et)
        case T etResponseCode.OverCapac y => throw OverCapac y("manhattan")
        case _ =>
          throw  nternalServerError(
            s"Unhandled response ${r.overallResponse} from getDeletedT ets for t et $ d"
          )
      }
    }
  }

  def convertState(d: DeleteState): DeletedT etState = d match {
    case DeleteState.NotFound => DeletedT etState.NotFound
    case DeleteState.NotDeleted => DeletedT etState.NotDeleted
    case DeleteState.SoftDeleted => DeletedT etState.SoftDeleted
    // Callers of t  endpo nt treat BounceDeleted t ets t  sa  as SoftDeleted
    case DeleteState.BounceDeleted => DeletedT etState.SoftDeleted
    case DeleteState.HardDeleted => DeletedT etState.HardDeleted
  }

  /**
   * Converts [[T etStorageCl ent.GetT et]]  nto a FutureArrow that returns extant t et  ds from
   * t  or g nal l st. T   thod  s used to c ck underly ng storage aga nt cac , preferr ng
   * cac   f a t et ex sts t re.
   */
  def t etsEx st(getT et: T etStorageCl ent.GetT et): T etsEx st =
    (t et ds: Seq[T et d]) =>
      for {
        response <- St ch.traverse(t et ds) { t et d => getT et(t et d).l ftToTry }
        t ets <- St ch.collect(response.map(processT etResponse))
      } y eld t ets.flatten.map(_. d).toSet.f lter(t et ds.conta ns)

  def apply(
    getDeletedT ets: T etStorageCl ent.GetDeletedT ets,
    t etsEx st: T etsEx st,
    stats: StatsRece ver
  ): Type = {

    val notFound = stats.counter("not_found")
    val notDeleted = stats.counter("not_deleted")
    val softDeleted = stats.counter("soft_deleted")
    val hardDeleted = stats.counter("hard_deleted")
    val unknown = stats.counter("unknown")

    def trackState(results: Seq[GetDeletedT etResult]): Un  =
      results.foreach { r =>
        r.state match {
          case DeletedT etState.NotFound => notFound. ncr()
          case DeletedT etState.NotDeleted => notDeleted. ncr()
          case DeletedT etState.SoftDeleted => softDeleted. ncr()
          case DeletedT etState.HardDeleted => hardDeleted. ncr()
          case _ => unknown. ncr()
        }
      }

    FutureArrow { request =>
      St ch.run {
        St ch
          .jo n(
            getDeletedT ets(request.t et ds),
            t etsEx st(request.t et ds)
          )
          .map {
            case (deletedT etResponses, extant ds) =>
              val response ds = deletedT etResponses.map(_.t et d)
              assert(
                response ds == request.t et ds,
                s"getDeletedT ets response does not match order of request: Request  ds " +
                  s"(${request.t et ds.mkStr ng(", ")}) != response  ds (${response ds
                    .mkStr ng(", ")})"
              )
              deletedT etResponses.map { r => convertDeletedT etResponse(r, extant ds) }
          }
      }
    }
  }
}
