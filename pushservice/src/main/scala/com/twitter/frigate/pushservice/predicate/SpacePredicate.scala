package com.tw ter.fr gate.pushserv ce.pred cate

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base.SpaceCand date
 mport com.tw ter.fr gate.common.base.SpaceCand dateDeta ls
 mport com.tw ter.fr gate.common.base.T etAuthorDeta ls
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.RawCand date
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
 mport com.tw ter. rm .pred cate.Na dPred cate
 mport com.tw ter. rm .pred cate.Pred cate
 mport com.tw ter. rm .pred cate.soc algraph.Edge
 mport com.tw ter. rm .pred cate.soc algraph.Relat onEdge
 mport com.tw ter. rm .pred cate.soc algraph.Soc alGraphPred cate
 mport com.tw ter.soc algraph.thr ftscala.Relat onsh pType
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.strato.response.Err
 mport com.tw ter.ubs.thr ftscala.Aud oSpace
 mport com.tw ter.ubs.thr ftscala.BroadcastState
 mport com.tw ter.ubs.thr ftscala.Part c pantUser
 mport com.tw ter.ubs.thr ftscala.Part c pants
 mport com.tw ter.ut l.Future

object SpacePred cate {

  /** F lters t  request  f t  target  s present  n t  space as a l stener, speakeTestConf gr, or adm n */
  def target nSpace(
    aud oSpacePart c pantsStore: ReadableStore[Str ng, Part c pants]
  )(
     mpl c  statsRece ver: StatsRece ver
  ): Na dPred cate[SpaceCand dateDeta ls w h RawCand date] = {
    val na  = "target_ n_space"
    Pred cate
      .fromAsync[SpaceCand dateDeta ls w h RawCand date] { spaceCand date =>
        aud oSpacePart c pantsStore.get(spaceCand date.space d).map {
          case So (part c pants) =>
            val allPart c pants: Seq[Part c pantUser] =
              (part c pants.adm ns ++ part c pants.speakers ++ part c pants.l steners).flatten.toSeq
            val  s nSpace = allPart c pants.ex sts { part c pant =>
              part c pant.tw terUser d.conta ns(spaceCand date.target.target d)
            }
            ! s nSpace
          case None => false
        }
      }.w hStats(statsRece ver.scope(na ))
      .w hNa (na )
  }

  /**
   *
   * @param aud oSpaceStore: space  tadata store
   * @param statsRece ver: record stats
   * @return: true  f t  space not started ELSE false to f lter out not f cat on
   */
  def sc duledSpaceStarted(
    aud oSpaceStore: ReadableStore[Str ng, Aud oSpace]
  )(
     mpl c  statsRece ver: StatsRece ver
  ): Na dPred cate[SpaceCand date w h RawCand date] = {
    val na  = "sc duled_space_started"
    Pred cate
      .fromAsync[SpaceCand date w h RawCand date] { spaceCand date =>
        aud oSpaceStore
          .get(spaceCand date.space d)
          .map(_.ex sts(_.state.conta ns(BroadcastState.NotStarted)))
          .rescue {
            case Err(Err.Author zat on, _, _) =>
              Future.False
          }
      }
      .w hStats(statsRece ver.scope(na ))
      .w hNa (na )
  }

  pr vate def relat onsh pMapEdgeFromSpaceCand date(
    cand date: RawCand date w h SpaceCand date
  ): Opt on[(Long, Seq[Long])] = {
    cand date.host d.map { spaceHost d =>
      (cand date.target.target d, Seq(spaceHost d))
    }
  }

  /**
   * C ck only host block for sc duled space rem nders
   * @return: True  f no block ng relat on bet en host and target user, else False
   */
  def spaceHostTargetUserBlock ng(
    edgeStore: ReadableStore[Relat onEdge, Boolean]
  )(
     mpl c  statsRece ver: StatsRece ver
  ): Na dPred cate[SpaceCand date w h RawCand date] = {
    val na  = "space_host_target_user_block ng"
    Pred catesForCand date
      .block ng(edgeStore)
      .opt onalOn(relat onsh pMapEdgeFromSpaceCand date, false)
      .w hStats(statsRece ver.scope(na ))
      .w hNa (na )
  }

  pr vate def edgeFromCand date(
    cand date: PushCand date w h T etAuthorDeta ls
  ): Future[Opt on[Edge]] = {
    cand date.t etAuthor.map(_.map { author => Edge(cand date.target.target d, author. d) })
  }

  def recom ndedT etAuthorAcceptableToTargetUser(
    edgeStore: ReadableStore[Relat onEdge, Boolean]
  )(
     mpl c  statsRece ver: StatsRece ver
  ): Na dPred cate[PushCand date w h T etAuthorDeta ls] = {
    val na  = "recom nded_t et_author_acceptable_to_target_user"
    Soc alGraphPred cate
      .anyRelat onEx sts(
        edgeStore,
        Set(
          Relat onsh pType.Block ng,
          Relat onsh pType.BlockedBy,
          Relat onsh pType.H deRecom ndat ons,
          Relat onsh pType.Mut ng
        )
      )
      .fl p
      .flatOpt onContraMap(
        edgeFromCand date,
        m ss ngResult = false
      )
      .w hStats(statsRece ver.scope(s"pred cate_$na "))
      .w hNa (na )
  }

  def narrowCastSpace(
     mpl c  statsRece ver: StatsRece ver
  ): Na dPred cate[SpaceCand dateDeta ls w h RawCand date] = {
    val na  = "narrow_cast_space"
    val narrowCastSpaceScope = statsRece ver.scope(na )
    val employeeSpaceCounter = narrowCastSpaceScope.counter("employees")
    val superFollo rSpaceCounter = narrowCastSpaceScope.counter("super_follo rs")

    Pred cate
      .fromAsync[SpaceCand dateDeta ls w h RawCand date] { cand date =>
        cand date.aud oSpaceFut.map {
          case So (aud oSpace)  f aud oSpace.narrowCastSpaceType.conta ns(1L) =>
            employeeSpaceCounter. ncr()
            cand date.target.params(PushFeatureSw chParams.EnableEmployeeOnlySpaceNot f cat ons)
          case So (aud oSpace)  f aud oSpace.narrowCastSpaceType.conta ns(2L) =>
            superFollo rSpaceCounter. ncr()
            false
          case _ => true
        }
      }.w hStats(narrowCastSpaceScope)
      .w hNa (na )
  }
}
