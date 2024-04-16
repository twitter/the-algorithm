package com.tw ter.un f ed_user_act ons.enr c r.hydrator
 mport com.tw ter.dynmap.DynMap
 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.graphql.thr ftscala.Auth aders
 mport com.tw ter.graphql.thr ftscala.Aut nt cat on
 mport com.tw ter.graphql.thr ftscala.Docu nt
 mport com.tw ter.graphql.thr ftscala.GraphQlRequest
 mport com.tw ter.graphql.thr ftscala.GraphqlExecut onServ ce
 mport com.tw ter.graphql.thr ftscala.Var ables
 mport com.tw ter.un f ed_user_act ons.enr c r. mple ntat onExcept on
 mport com.tw ter.un f ed_user_act ons.enr c r.graphql.GraphqlRspParser
 mport com.tw ter.un f ed_user_act ons.enr c r.hcac .LocalCac 
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch ntEnvelop
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch nt dType
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch nt nstruct on
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch ntKey
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Author nfo
 mport com.tw ter.un f ed_user_act ons.thr ftscala. em
 mport com.tw ter.ut l.Future

class DefaultHydrator(
  cac : LocalCac [Enr ch ntKey, DynMap],
  graphqlCl ent: GraphqlExecut onServ ce.F nagledCl ent,
  scopedStatsRece ver: StatsRece ver = NullStatsRece ver)
    extends AbstractHydrator(scopedStatsRece ver) {

  pr vate def constructGraphqlReq(
    enr ch ntKey: Enr ch ntKey
  ): GraphQlRequest =
    enr ch ntKey.keyType match {
      case Enr ch nt dType.T et d =>
        GraphQlRequest(
          // see go/graph ql/M5sHxua-RD RtTn48CAhng
          docu nt = Docu nt.Docu nt d("M5sHxua-RD RtTn48CAhng"),
          operat onNa  = So ("T etHydrat on"),
          var ables = So (
            Var ables.JsonEncodedVar ables(s"""{"rest_ d": "${enr ch ntKey. d}"}""")
          ),
          aut nt cat on = Aut nt cat on.Auth aders(
            Auth aders()
          )
        )
      case _ =>
        throw new  mple ntat onExcept on(
          s"M ss ng  mple ntat on for hydrat on of type ${enr ch ntKey.keyType}")
    }

  pr vate def hydrateAuthor nfo( em:  em.T et nfo, author d: Opt on[Long]):  em.T et nfo = {
     em.t et nfo.act onT etAuthor nfo match {
      case So (_) =>  em
      case _ =>
         em.copy(t et nfo =  em.t et nfo.copy(
          act onT etAuthor nfo = So (Author nfo(author d = author d))
        ))
    }
  }

  overr de protected def safelyHydrate(
     nstruct on: Enr ch nt nstruct on,
    key: Enr ch ntKey,
    envelop: Enr ch ntEnvelop
  ): Future[Enr ch ntEnvelop] = {
     nstruct on match {
      case Enr ch nt nstruct on.T etEnr ch nt =>
        val dynMapFuture = cac .getOrElseUpdate(key) {
          graphqlCl ent
            .graphql(constructGraphqlReq(enr ch ntKey = key))
            .map { body =>
              body.response.flatMap { r =>
                GraphqlRspParser.toDynMapOpt(r)
              }.get
            }
        }

        dynMapFuture.map(map => {
          val author dOpt =
            map.getLongOpt("data.t et_result_by_rest_ d.result.core.user.legacy. d_str")

          val hydratedEnvelop = envelop.uua. em match {
            case  em:  em.T et nfo =>
              envelop.copy(uua = envelop.uua.copy( em = hydrateAuthor nfo( em, author dOpt)))
            case _ => envelop
          }
          hydratedEnvelop
        })
      case _ => Future.value(envelop)
    }
  }
}
