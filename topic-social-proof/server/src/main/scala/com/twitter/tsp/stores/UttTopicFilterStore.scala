package com.tw ter.tsp.stores

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.Fa lureFlags.flagsOf
 mport com.tw ter.f nagle.mux.Cl entD scardedRequestExcept on
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.store. nterests
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.top cl st ng.Product d
 mport com.tw ter.top cl st ng.Top cL st ng
 mport com.tw ter.top cl st ng.Top cL st ngV e rContext
 mport com.tw ter.top cl st ng.{Semant cCoreEnt y d => ScEnt y d}
 mport com.tw ter.tsp.thr ftscala.Top cFollowType
 mport com.tw ter.tsp.thr ftscala.Top cL st ngSett ng
 mport com.tw ter.tsp.thr ftscala.Top cSoc alProofF lter ngBypassMode
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.T  outExcept on
 mport com.tw ter.ut l.T  r

class UttTop cF lterStore(
  top cL st ng: Top cL st ng,
  userOptOutTop csStore: ReadableStore[ nterests.User d, Top cResponses],
  expl c Follow ngTop csStore: ReadableStore[ nterests.User d, Top cResponses],
  not nterestedTop csStore: ReadableStore[ nterests.User d, Top cResponses],
  local zedUttRecom ndableTop csStore: ReadableStore[Local zedUttTop cNa Request, Set[Long]],
  t  r: T  r,
  stats: StatsRece ver) {
   mport UttTop cF lterStore._

  // Set of blackl sted Semant cCore  Ds that are paused.
  pr vate[t ] def getPausedTop cs(top cCtx: Top cL st ngV e rContext): Set[ScEnt y d] = {
    top cL st ng.getPausedTop cs(top cCtx)
  }

  pr vate[t ] def getOptOutTop cs(user d: Long): Future[Set[ScEnt y d]] = {
    stats.counter("getOptOutTop csCount"). ncr()
    userOptOutTop csStore
      .get(user d).map { responseOpt =>
        responseOpt
          .map { responses => responses.responses.map(_.ent y d) }.getOrElse(Seq.empty).toSet
      }.ra seW h n(DefaultOptOutT  out)(t  r).rescue {
        case err: T  outExcept on =>
          stats.counter("getOptOutTop csT  out"). ncr()
          Future.except on(err)
        case err: Cl entD scardedRequestExcept on
             f flagsOf(err).conta ns(" nterrupted") && flagsOf(err)
              .conta ns(" gnorable") =>
          stats.counter("getOptOutTop csD scardedBackupRequest"). ncr()
          Future.except on(err)
        case err =>
          stats.counter("getOptOutTop csFa lure"). ncr()
          Future.except on(err)
      }
  }

  pr vate[t ] def getNot nterested n(user d: Long): Future[Set[ScEnt y d]] = {
    stats.counter("getNot nterested nCount"). ncr()
    not nterestedTop csStore
      .get(user d).map { responseOpt =>
        responseOpt
          .map { responses => responses.responses.map(_.ent y d) }.getOrElse(Seq.empty).toSet
      }.ra seW h n(DefaultNot nterested nT  out)(t  r).rescue {
        case err: T  outExcept on =>
          stats.counter("getNot nterested nT  out"). ncr()
          Future.except on(err)
        case err: Cl entD scardedRequestExcept on
             f flagsOf(err).conta ns(" nterrupted") && flagsOf(err)
              .conta ns(" gnorable") =>
          stats.counter("getNot nterested nD scardedBackupRequest"). ncr()
          Future.except on(err)
        case err =>
          stats.counter("getNot nterested nFa lure"). ncr()
          Future.except on(err)
      }
  }

  pr vate[t ] def getFollo dTop cs(user d: Long): Future[Set[Top cResponse]] = {
    stats.counter("getFollo dTop csCount"). ncr()

    expl c Follow ngTop csStore
      .get(user d).map { responseOpt =>
        responseOpt.map(_.responses.toSet).getOrElse(Set.empty)
      }.ra seW h n(Default nterested nT  out)(t  r).rescue {
        case _: T  outExcept on =>
          stats.counter("getFollo dTop csT  out"). ncr()
          Future(Set.empty)
        case _ =>
          stats.counter("getFollo dTop csFa lure"). ncr()
          Future(Set.empty)
      }
  }

  pr vate[t ] def getFollo dTop c ds(user d: Long): Future[Set[ScEnt y d]] = {
    getFollo dTop cs(user d: Long).map(_.map(_.ent y d))
  }

  pr vate[t ] def getWh el stTop c ds(
    normal zedContext: Top cL st ngV e rContext,
    enable nternat onalTop cs: Boolean
  ): Future[Set[ScEnt y d]] = {
    stats.counter("getWh el stTop c dsCount"). ncr()

    val uttRequest = Local zedUttTop cNa Request(
      product d = Product d.Followable,
      v e rContext = normal zedContext,
      enable nternat onalTop cs = enable nternat onalTop cs
    )
    local zedUttRecom ndableTop csStore
      .get(uttRequest).map { response =>
        response.getOrElse(Set.empty)
      }.rescue {
        case _ =>
          stats.counter("getWh el stTop c dsFa lure"). ncr()
          Future(Set.empty)
      }
  }

  pr vate[t ] def getDenyL stTop c dsForUser(
    user d: User d,
    top cL st ngSett ng: Top cL st ngSett ng,
    context: Top cL st ngV e rContext,
    bypassModes: Opt on[Set[Top cSoc alProofF lter ngBypassMode]]
  ): Future[Set[ScEnt y d]] = {

    val denyL stTop c dsFuture = top cL st ngSett ng match {
      case Top cL st ngSett ng. mpl c Follow =>
        getFollo dTop c ds(user d)
      case _ =>
        Future(Set.empty[ScEnt y d])
    }

    //   don't f lter opt-out top cs for  mpl c  follow top c l st ng sett ng
    val optOutTop c dsFuture = top cL st ngSett ng match {
      case Top cL st ngSett ng. mpl c Follow => Future(Set.empty[ScEnt y d])
      case _ => getOptOutTop cs(user d)
    }

    val not nterestedTop c dsFuture =
       f (bypassModes.ex sts(_.conta ns(Top cSoc alProofF lter ngBypassMode.Not nterested))) {
        Future(Set.empty[ScEnt y d])
      } else {
        getNot nterested n(user d)
      }
    val pausedTop c dsFuture = Future.value(getPausedTop cs(context))

    Future
      .collect(
        L st(
          denyL stTop c dsFuture,
          optOutTop c dsFuture,
          not nterestedTop c dsFuture,
          pausedTop c dsFuture)).map { l st => l st.reduce(_ ++ _) }
  }

  pr vate[t ] def getD ff(
    aFut: Future[Set[ScEnt y d]],
    bFut: Future[Set[ScEnt y d]]
  ): Future[Set[ScEnt y d]] = {
    Future.jo n(aFut, bFut).map {
      case (a, b) => a.d ff(b)
    }
  }

  /**
   * calculates t  d ff of all t  wh el sted  Ds w h blackl sted  Ds and returns t  set of  Ds
   * that   w ll be recom nd ng from or follo d top cs by t  user by cl ent sett ng.
   */
  def getAllowL stTop csForUser(
    user d: User d,
    top cL st ngSett ng: Top cL st ngSett ng,
    context: Top cL st ngV e rContext,
    bypassModes: Opt on[Set[Top cSoc alProofF lter ngBypassMode]]
  ): Future[Map[ScEnt y d, Opt on[Top cFollowType]]] = {

    /**
     * T le: an  llustrat ve table to expla n how allow l st  s composed
     * AllowL st = Wh eL st - DenyL st - OptOutTop cs - PausedTop cs - Not nterested nTop cs
     *
     * Top cL st ngSett ng: Follow ng                  mpl c Follow                       All                       Followable
     * Wh el st:          Follo dTop cs(user)      AllWh el stedTop cs                 N l                       AllWh el stedTop cs
     * DenyL st:           N l                       Follo dTop cs(user)                 N l                       N l
     *
     * ps. for Top cL st ngSett ng.All, t  returned allow l st  s N l. Why?
     *  's because that allowL st  s not requ red g ven t  Top cL st ngSett ng == 'All'.
     * See Top cSoc alProofHandler.f lterByAllo dL st() for more deta ls.
     */

    top cL st ngSett ng match {
      // "All"  ans all t  UTT ent y  s qual f ed. So don't need to fetch t  Wh el st anymore.
      case Top cL st ngSett ng.All => Future.value(Map.empty)
      case Top cL st ngSett ng.Follow ng =>
        getFollow ngTop csForUserW hT  stamp(user d, context, bypassModes).map {
          _.mapValues(_ => So (Top cFollowType.Follow ng))
        }
      case Top cL st ngSett ng. mpl c Follow =>
        getD ff(
          getWh el stTop c ds(context, enable nternat onalTop cs = true),
          getDenyL stTop c dsForUser(user d, top cL st ngSett ng, context, bypassModes)).map {
          _.map { scEnt y d =>
            scEnt y d -> So (Top cFollowType. mpl c Follow)
          }.toMap
        }
      case _ =>
        val follo dTop c dsFut = getFollo dTop c ds(user d)
        val allowL stTop c dsFut = getD ff(
          getWh el stTop c ds(context, enable nternat onalTop cs = true),
          getDenyL stTop c dsForUser(user d, top cL st ngSett ng, context, bypassModes))
        Future.jo n(allowL stTop c dsFut, follo dTop c dsFut).map {
          case (allowL stTop c d, follo dTop c ds) =>
            allowL stTop c d.map { scEnt y d =>
               f (follo dTop c ds.conta ns(scEnt y d))
                scEnt y d -> So (Top cFollowType.Follow ng)
              else scEnt y d -> So (Top cFollowType. mpl c Follow)
            }.toMap
        }
    }
  }

  pr vate[t ] def getFollow ngTop csForUserW hT  stamp(
    user d: User d,
    context: Top cL st ngV e rContext,
    bypassModes: Opt on[Set[Top cSoc alProofF lter ngBypassMode]]
  ): Future[Map[ScEnt y d, Opt on[Long]]] = {

    val follo dTop c dToT  stampFut = getFollo dTop cs(user d).map(_.map { follo dTop c =>
      follo dTop c.ent y d -> follo dTop c.top cFollowT  stamp
    }.toMap)

    follo dTop c dToT  stampFut.flatMap { follo dTop c dToT  stamp =>
      getD ff(
        Future(follo dTop c dToT  stamp.keySet),
        getDenyL stTop c dsForUser(user d, Top cL st ngSett ng.Follow ng, context, bypassModes)
      ).map {
        _.map { scEnt y d =>
          scEnt y d -> follo dTop c dToT  stamp.get(scEnt y d).flatten
        }.toMap
      }
    }
  }
}

object UttTop cF lterStore {
  val DefaultNot nterested nT  out: Durat on = 60.m ll seconds
  val DefaultOptOutT  out: Durat on = 60.m ll seconds
  val Default nterested nT  out: Durat on = 60.m ll seconds
}
