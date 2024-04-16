package com.tw ter.t etyp e.federated.columns

 mport com.tw ter.spam.rtf.thr ftscala.SafetyLevel
 mport com.tw ter.st ch.MapGroup
 mport com.tw ter.t etyp e.User d
 mport com.tw ter.t etyp e.federated.columns.FederatedF eldGroupBu lder.allCountF elds
 mport com.tw ter.t etyp e.federated.columns.FederatedF eldGroupBu lder.countT etF elds
 mport com.tw ter.t etyp e.thr ftscala.GetT etF eldsOpt ons
 mport com.tw ter.t etyp e.thr ftscala.GetT etF eldsRequest
 mport com.tw ter.t etyp e.thr ftscala.GetT etF eldsResult
 mport com.tw ter.t etyp e.thr ftscala.StatusCounts
 mport com.tw ter.t etyp e.thr ftscala.T et
 mport com.tw ter.t etyp e.thr ftscala.T et nclude
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.Throw
 mport com.tw ter.ut l.Try

case class GroupOpt ons(tw terUser d: Opt on[User d])

object FederatedF eldGroupBu lder {
  type Type = GroupOpt ons => MapGroup[FederatedF eldReq, GetT etF eldsResult]

  def apply(
    getT etF eldsHandler: GetT etF eldsRequest => Future[Seq[GetT etF eldsResult]]
  ): Type = {
    FederatedF eldGroup(getT etF eldsHandler, _)
  }

  // T  set of non-deprecated count f eld  ncludes
  val allCountF elds: Set[T et nclude] = Set(
    T et nclude.CountsF eld d(StatusCounts.Ret etCountF eld. d),
    T et nclude.CountsF eld d(StatusCounts.QuoteCountF eld. d),
    T et nclude.CountsF eld d(StatusCounts.Favor eCountF eld. d),
    T et nclude.CountsF eld d(StatusCounts.ReplyCountF eld. d),
    T et nclude.CountsF eld d(StatusCounts.BookmarkCountF eld. d),
  )

  // T et f eld  ncludes wh ch conta n counts. T se are t  only f elds w re count f eld  ncludes are relevant.
  val countT etF elds: Set[T et nclude] = Set(
    T et nclude.T etF eld d(T et.CountsF eld. d),
    T et nclude.T etF eld d(T et.Prev ousCountsF eld. d))
}

case class FederatedF eldGroup(
  getT etF eldsHandler: GetT etF eldsRequest => Future[Seq[GetT etF eldsResult]],
  opt ons: GroupOpt ons)
    extends MapGroup[FederatedF eldReq, GetT etF eldsResult] {
  overr de protected def run(
    reqs: Seq[FederatedF eldReq]
  ): Future[FederatedF eldReq => Try[GetT etF eldsResult]] = {

    // request ng t  f eld  ds of t  requested add  onal f eld  ds  n t  group
    val f eld ncludes: Set[T et nclude] = reqs.map { req: FederatedF eldReq =>
      T et nclude.T etF eld d(req.f eld d)
    }.toSet

    val all ncludes: Set[T et nclude] =  f (f eld ncludes. ntersect(countT etF elds).nonEmpty) {
      //  f counts are be ng requested    nclude all count f elds by default
      // because t re  s no way to spec fy t m  nd v dually w h federated f elds,
      f eld ncludes ++ allCountF elds
    } else {
      f eld ncludes
    }

    val gtfOpt ons = GetT etF eldsOpt ons(
      t et ncludes = all ncludes,
      forUser d = opt ons.tw terUser d,
      // v s b l y f lter ng happens at t  ap  layer / t et top level
      // and t refore  s not requ red at  nd v dual f eld level
      safetyLevel = So (SafetyLevel.F lterNone)
    )
    getT etF eldsHandler(
      GetT etF eldsRequest(
        t et ds = reqs.map(_.t et d).d st nct,
        opt ons = gtfOpt ons
      )
    ).map {
      response =>
        { req =>
          response.f nd(_.t et d == req.t et d) match {
            case So (result) => Try(result)
            case None =>
              Throw(new NoSuchEle ntExcept on(s"response not found for t et: ${req.t et d}"))
          }
        }
    }
  }
}
