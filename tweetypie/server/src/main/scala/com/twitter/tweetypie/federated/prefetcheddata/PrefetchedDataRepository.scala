package com.tw ter.t etyp e
package federated
package prefetc ddata

 mport com.tw ter.consu r_pr vacy. nt on_controls.thr ftscala.Un nt on nfo
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.g zmoduck.thr ftscala.LookupContext
 mport com.tw ter.g zmoduck.thr ftscala.QueryF elds
 mport com.tw ter.g zmoduck.thr ftscala.UserResult
 mport com.tw ter.spam.rtf.thr ftscala.SafetyLevel
 mport com.tw ter.st ch.compat.LegacySeqGroup
 mport com.tw ter.st ch.SeqGroup
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.graphql.thr ftscala.Cac M ssStrategy
 mport com.tw ter.strato.graphql.thr ftscala.Prefetc dData
 mport com.tw ter.strato.graphql.thr ftscala.T etResult
 mport com.tw ter.t etyp e.backends.G zmoduck
 mport com.tw ter.t etyp e.thr ftscala.T et
 mport com.tw ter.ut l.Throwables
 mport com.tw ter.v bes.thr ftscala.V beV2
 mport com.tw ter. averb rd.common.GetRequestContext
 mport com.tw ter. averb rd.common.PerTOOAppCallerStats
 mport com.tw ter. averb rd.common.RequestContext
 mport com.tw ter. averb rd.converters.t et. averb rdEnt ySetMutat ons
 mport com.tw ter. averb rd.converters.t et. averb rdT etMutat ons
 mport com.tw ter. averb rd.hydrators._
 mport com.tw ter. averb rd.mappers.Ap T etPrefetc dMapper
 mport com.tw ter. averb rd.repos or es.UserRepos ory
 mport com.tw ter. averb rd.converters.common.Ent yRender ngOpt ons

pr vate[federated] f nal case class Prefetc dDataRequest(
  t et: T et,
  s ceT et: Opt on[T et],
  quotedT et: Opt on[T et],
  un nt on nfo: Opt on[Un nt on nfo] = None,
  v be: Opt on[V beV2] = None,
  safetyLevel: SafetyLevel,
  requestContext: RequestContext)

pr vate[federated] f nal case class Prefetc dDataResponse(value: Prefetc dData)

pr vate[federated] object Prefetc dDataResponse {
  // For NotFound, t re  s no subsequent result or quoted_t et_results f eld, so both
  // sett ngs are false  re. T se dec ders w ll be removed post m grat on.
  pr vate[t ] val prefetc dMapper = new Ap T etPrefetc dMapper(
    sk pT etResultPrefetch em = () => false
  )
  def notFound(t et d: Long): Prefetc dDataResponse =
    Prefetc dDataResponse(
      value = prefetc dMapper.getPrefetc dData(
        t et d = t et d,
        ap T et = None,
        t etResult = None
      )
    )
}

pr vate[federated] object Prefetc dDataRepos ory {
  def apply(
    thr ftT etToAp T et: Thr ftT etToAp T et,
    prefetc dMapper: Ap T etPrefetc dMapper,
    statsRece ver: StatsRece ver,
  ): Prefetc dDataRequest => St ch[Prefetc dDataResponse] =
    (request: Prefetc dDataRequest) => {
      val thr ftT etToAp T etRequest = Thr ftT etToAp T etRequest(
        t et = request.t et,
        s ceT et = request.s ceT et,
        quotedT et = request.quotedT et,
        // For T et wr es, f lteredReason w ll always be None.
        f lteredReason = None,
        safetyLevel = request.safetyLevel,
        requestContext = request.requestContext,
        ent yRender ngOpt ons = Ent yRender ngOpt ons()
      )

      val successCounter = statsRece ver.counter("success")
      val fa luresCounter = statsRece ver.counter("fa lures")
      val fa luresScope = statsRece ver.scope("fa lures")

      thr ftT etToAp T et
        .arrow(thr ftT etToAp T etRequest)
        .onSuccess(_ => successCounter. ncr())
        .onFa lure { t =>
          fa luresCounter. ncr()
          fa luresScope.counter(Throwables.mkStr ng(t): _*). ncr()
        }
        .map((resp: Thr ftT etToAp T etResponse) => {
          val prefetc dData: Prefetc dData = prefetc dMapper.getPrefetc dData(
            t et d = request.t et. d,
            ap T et = So (resp.ap T et),
            // s nce Ap T et was hydrate,   can fabr cate a T etResult.T et
            t etResult = So (T etResult.T et(request.t et. d)),
            un nt on nfo = request.un nt on nfo,
            ed Control = request.t et.ed Control,
            prev ousCounts = request.t et.prev ousCounts,
            v be = request.v be,
            ed Perspect ve = request.t et.ed Perspect ve,
            noteT et = request.t et.noteT et
          )

          // Not fy GraphQL AP  to not attempt hydrat on for m ss ng
          // Ap T et/T etResult f elds. T   s only needed on t 
          // T et wr e path s nce t  newly created T et may not
          // be fully pers sted yet  n tb rd Manhattan.
          val shortC rcu edPrefetc dData = prefetc dData.copy(
            onCac M ss = Cac M ssStrategy.ShortC rcu Ex st ng
          )

          Prefetc dDataResponse(shortC rcu edPrefetc dData)
        })
    }
}

pr vate[federated] object Prefetc dDataRepos oryBu lder {
  def apply(
    getUserResultsBy d: G zmoduck.GetBy d,
    statsRece ver: StatsRece ver
  ): Prefetc dDataRequest => St ch[Prefetc dDataResponse] = {
    val repoStats = statsRece ver.scope("repos or es")

    case class GetUserResultBy d(
      queryF elds: Set[QueryF elds],
      lookupContext: LookupContext,
    ) extends SeqGroup[User d, UserResult] {
      overr de def run(keys: Seq[User d]): Future[Seq[Try[UserResult]]] =
        LegacySeqGroup.l ftToSeqTry(getUserResultsBy d((lookupContext, keys, queryF elds)))

      overr de def maxS ze:  nt = 100
    }

    val st chGetUserResultBy d: UserRepos ory.GetUserResultBy d =
      (user d: User d, queryF elds: Set[QueryF elds], lookupContext: LookupContext) =>
        St ch.call(user d, GetUserResultBy d(queryF elds, lookupContext))

    val userRepos ory = new UserRepos ory(st chGetUserResultBy d, repoStats)

    // Note, t   s  averb rd.common.GetRequestContext
    val getRequestContext = new GetRequestContext()

    // Tw ggyUserHydrator  s needed to hydrate Tw ggyUsers for CWC and m sc. log c
    val tw ggyUserHydrator = new Tw ggyUserHydrator(userRepos ory, getRequestContext)

    val  averb rdMutat ons = new  averb rdT etMutat ons(
      new  averb rdEnt ySetMutat ons(
        new PerTOOAppCallerStats(statsRece ver, getRequestContext)
      )
    )

    val prefetc dMapper = new Ap T etPrefetc dMapper(
      // do not sk p t   n mutat on path as   depends on  
      sk pT etResultPrefetch em = () => false
    )

    val thr ftT etToAp T et: Thr ftT etToAp T et =
      new FoundThr ftT etToAp T et(
        statsRece ver,
        tw ggyUserHydrator,
         averb rdMutat ons
      )
    Prefetc dDataRepos ory(
      thr ftT etToAp T et,
      prefetc dMapper,
      repoStats.scope("prefetc d_data_repo")
    )
  }
}
