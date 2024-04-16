package com.tw ter.tsp.stores

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.store. nterested n nterestsFetchKey
 mport com.tw ter.fr gate.common.store.strato.StratoFetchableStore
 mport com.tw ter. rm .store.common.ObservedReadableStore
 mport com.tw ter. nterests.thr ftscala. nterest d
 mport com.tw ter. nterests.thr ftscala. nterestLabel
 mport com.tw ter. nterests.thr ftscala. nterestRelat onsh p
 mport com.tw ter. nterests.thr ftscala. nterestRelat onsh pV1
 mport com.tw ter. nterests.thr ftscala. nterested n nterestLookupContext
 mport com.tw ter. nterests.thr ftscala. nterested n nterestModel
 mport com.tw ter. nterests.thr ftscala.OptOut nterestLookupContext
 mport com.tw ter. nterests.thr ftscala.User nterest
 mport com.tw ter. nterests.thr ftscala.User nterestData
 mport com.tw ter. nterests.thr ftscala.User nterestsResponse
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.strato.cl ent.Cl ent
 mport com.tw ter.strato.thr ft.ScroogeConv mpl c s._

case class Top cResponse(
  ent y d: Long,
   nterested nData: Seq[ nterested n nterestModel],
  scoreOverr de: Opt on[Double] = None,
  not nterested nT  stamp: Opt on[Long] = None,
  top cFollowT  stamp: Opt on[Long] = None)

case class Top cResponses(responses: Seq[Top cResponse])

object Top cStore {

  pr vate val  nterested n nterestsColumn = " nterests/ nterested n nterests"
  pr vate lazy val Expl c  nterestsContext:  nterested n nterestLookupContext =
     nterested n nterestLookupContext(
      expl c Context = None,
       nferredContext = None,
      d sable mpl c  = So (true)
    )

  pr vate def user nterestsResponseToTop cResponse(
    user nterestsResponse: User nterestsResponse
  ): Top cResponses = {
    val responses = user nterestsResponse. nterests. nterests.toSeq.flatMap { user nterests =>
      user nterests.collect {
        case User nterest(
               nterest d.Semant cCore(semant cCoreEnt y),
              So (User nterestData. nterested n(data))) =>
          val top cFollow ngT  stampOpt = data.collect {
            case  nterested n nterestModel.Expl c Model(
                   nterestRelat onsh p.V1( nterestRelat onsh pV1)) =>
               nterestRelat onsh pV1.t  stampMs
          }.lastOpt on

          Top cResponse(semant cCoreEnt y. d, data, None, None, top cFollow ngT  stampOpt)
      }
    }
    Top cResponses(responses)
  }

  def expl c Follow ngTop cStore(
    stratoCl ent: Cl ent
  )(
     mpl c  statsRece ver: StatsRece ver
  ): ReadableStore[User d, Top cResponses] = {
    val stratoStore =
      StratoFetchableStore
        .w hUn V ew[ nterested n nterestsFetchKey, User nterestsResponse](
          stratoCl ent,
           nterested n nterestsColumn)
        .composeKeyMapp ng[User d](u d =>
           nterested n nterestsFetchKey(
            user d = u d,
            labels = None,
            lookupContext = So (Expl c  nterestsContext)
          ))
        .mapValues(user nterestsResponseToTop cResponse)

    ObservedReadableStore(stratoStore)
  }

  def userOptOutTop cStore(
    stratoCl ent: Cl ent,
    optOutStratoStorePath: Str ng
  )(
     mpl c  statsRece ver: StatsRece ver
  ): ReadableStore[User d, Top cResponses] = {
    val stratoStore =
      StratoFetchableStore
        .w hUn V ew[
          (Long, Opt on[Seq[ nterestLabel]], Opt on[OptOut nterestLookupContext]),
          User nterestsResponse](stratoCl ent, optOutStratoStorePath)
        .composeKeyMapp ng[User d](u d => (u d, None, None))
        .mapValues { user nterestsResponse =>
          val responses = user nterestsResponse. nterests. nterests.toSeq.flatMap { user nterests =>
            user nterests.collect {
              case User nterest(
                     nterest d.Semant cCore(semant cCoreEnt y),
                    So (User nterestData. nterested n(data))) =>
                Top cResponse(semant cCoreEnt y. d, data, None)
            }
          }
          Top cResponses(responses)
        }
    ObservedReadableStore(stratoStore)
  }

  def not nterested nTop csStore(
    stratoCl ent: Cl ent,
    not nterested nStorePath: Str ng
  )(
     mpl c  statsRece ver: StatsRece ver
  ): ReadableStore[User d, Top cResponses] = {
    val stratoStore =
      StratoFetchableStore
        .w hUn V ew[Long, Seq[User nterest]](stratoCl ent, not nterested nStorePath)
        .composeKeyMapp ng[User d]( dent y)
        .mapValues { not nterested n nterests =>
          val responses = not nterested n nterests.collect {
            case User nterest(
                   nterest d.Semant cCore(semant cCoreEnt y),
                  So (User nterestData.Not nterested(not nterested nData))) =>
              val not nterested nT  stampOpt = not nterested nData.collect {
                case  nterestRelat onsh p.V1( nterestRelat onsh pV1:  nterestRelat onsh pV1) =>
                   nterestRelat onsh pV1.t  stampMs
              }.lastOpt on

              Top cResponse(semant cCoreEnt y. d, Seq.empty, None, not nterested nT  stampOpt)
          }
          Top cResponses(responses)
        }
    ObservedReadableStore(stratoStore)
  }

}
