package com.tw ter.ann.scald ng.offl ne

 mport com.tw ter.core_workflows.user_model.thr ftscala.CondensedUserState
 mport com.tw ter.cortex.ml.embedd ngs.common.{DataS ceManager, GraphEdge,  lpers, UserK nd}
 mport com.tw ter.ml.featurestore.l b.User d
 mport com.tw ter.ent yembedd ngs.ne ghbors.thr ftscala.{Ent yKey, NearestNe ghbors}
 mport com.tw ter.pluck.s ce.core_workflows.user_model.CondensedUserStateScalaDataset
 mport com.tw ter.scald ng._
 mport com.tw ter.scald ng.typed.TypedP pe
 mport com.tw ter.scald ng_ nternal.dalv2.DAL
 mport com.tw ter.users ce.snapshot.flat.Users ceFlatScalaDataset
 mport com.tw ter.users ce.snapshot.flat.thr ftscala.FlatUser

case class Consu rAssoc(consu r d: User d, assoc: L st[Str ng])

object KnnDebug {

  def getConsu rAssoc at ons(
    graph: TypedP pe[GraphEdge[User d, User d]],
    userna s: TypedP pe[(User d, Str ng)],
    reducers:  nt
  ): TypedP pe[Consu rAssoc] = {
    graph
      .groupBy(_. em d)
      .jo n(userna s).w hReducers(reducers)
      .values
      .map {
        case (edge: GraphEdge[User d, User d], producerScreenNa : Str ng) =>
          Consu rAssoc(consu r d = edge.consu r d, assoc = L st(producerScreenNa ))
      }
      .groupBy(_.consu r d).w hReducers(reducers)
      .reduce[Consu rAssoc] {
        case (uFollow1: Consu rAssoc, uFollow2: Consu rAssoc) =>
          Consu rAssoc(consu r d = uFollow1.consu r d, assoc = uFollow1.assoc ++ uFollow2.assoc)
      }
      .values
  }

  /**
   * Wr e t  ne ghbors and a set of follows to a tsv for eas er analys s dur ng debugg ng
   *   take t  set of users w h bet en 25-50 follows and grab only those users
   *
   * T  returns 4 str ngs of t  form:
   * consu r d, state, followUserNa <f>followUserNa <f>followUserNa , ne ghborNa <n>ne ghborNa <n>ne ghborNa 
   */
  def getDebugTable(
    ne ghborsP pe: TypedP pe[(Ent yKey, NearestNe ghbors)],
    shards:  nt,
    reducers:  nt,
    l m :  nt = 10000,
    userDataset: Opt on[TypedP pe[FlatUser]] = None,
    followDataset: Opt on[TypedP pe[GraphEdge[User d, User d]]] = None,
    consu rStatesDataset: Opt on[TypedP pe[CondensedUserState]] = None,
    m nFollows:  nt = 25,
    maxFollows:  nt = 50
  )(
     mpl c  dateRange: DateRange
  ): TypedP pe[(Str ng, Str ng, Str ng, Str ng)] = {

    val users ceP pe: TypedP pe[FlatUser] = userDataset
      .getOrElse(DAL.readMostRecentSnapshot(Users ceFlatScalaDataset, dateRange).toTypedP pe)

    val followGraph: TypedP pe[GraphEdge[User d, User d]] = followDataset
      .getOrElse(new DataS ceManager().getFollowGraph())

    val consu rStates: TypedP pe[CondensedUserState] = consu rStatesDataset
      .getOrElse(DAL.read(CondensedUserStateScalaDataset).toTypedP pe)

    val userna s: TypedP pe[(User d, Str ng)] = users ceP pe.flatMap { flatUser =>
      (flatUser.screenNa , flatUser. d) match {
        case (So (na : Str ng), So (user d: Long)) => So ((User d(user d), na ))
        case _ => None
      }
    }.fork

    val consu rFollows: TypedP pe[Consu rAssoc] =
      getConsu rAssoc at ons(followGraph, userna s, reducers)
        .f lter { uFollow => (uFollow.assoc.s ze > m nFollows && uFollow.assoc.s ze < maxFollows) }

    val ne ghborGraph: TypedP pe[GraphEdge[User d, User d]] = ne ghborsP pe
      .l m (l m )
      .flatMap {
        case (ent yKey: Ent yKey, ne ghbors: NearestNe ghbors) =>
           lpers.opt onalToLong(ent yKey. d) match {
            case So (ent y d: Long) =>
              ne ghbors.ne ghbors.flatMap { ne ghbor =>
                 lpers
                  .opt onalToLong(ne ghbor.ne ghbor. d)
                  .map { ne ghbor d =>
                    GraphEdge[User d, User d](
                      consu r d = User d(ent y d),
                       em d = User d(ne ghbor d),
                        ght = 1.0F)
                  }
              }
            case None => L st()
          }
      }
    val consu rNe ghbors: TypedP pe[Consu rAssoc] =
      getConsu rAssoc at ons(ne ghborGraph, userna s, reducers)

    consu rFollows
      .groupBy(_.consu r d)
      .jo n(consu rStates.groupBy { consu r => User d(consu r.u d) }).w hReducers(reducers)
      .jo n(consu rNe ghbors.groupBy(_.consu r d)).w hReducers(reducers)
      .values
      .map {
        case ((uFollow: Consu rAssoc, state: CondensedUserState), uNe ghbors: Consu rAssoc) =>
          (
            UserK nd.str ng nject on(uFollow.consu r d),
            state.state.toStr ng,
            uFollow.assoc mkStr ng "<f>",
            uNe ghbors.assoc mkStr ng "<n>")
      }
      .shard(shards)
  }
}
