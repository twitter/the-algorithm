package com.tw ter.t  l neranker.uteg_l ked_by_t ets

 mport com.tw ter.recos.recos_common.thr ftscala.Soc alProofType
 mport com.tw ter.recos.user_t et_ent y_graph.thr ftscala.T etEnt yD splayLocat on
 mport com.tw ter.recos.user_t et_ent y_graph.thr ftscala.T etRecom ndat on
 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.snowflake. d.Snowflake d
 mport com.tw ter.t  l neranker.core.Cand dateEnvelope
 mport com.tw ter.t  l neranker.core.DependencyTransfor r
 mport com.tw ter.t  l neranker.model.RecapQuery
 mport com.tw ter.t  l neranker.model.T  Range
 mport com.tw ter.t  l neranker.model.T et dRange
 mport com.tw ter.t  l neranker.model.RecapQuery.DependencyProv der
 mport com.tw ter.t  l nes.cl ents.user_t et_ent y_graph.Recom ndT etEnt yQuery
 mport com.tw ter.t  l nes.cl ents.user_t et_ent y_graph.UserT etEnt yGraphCl ent
 mport com.tw ter.ut l.Future

object UTEGResultsTransform {
  val MaxUserSoc alProofS ze = 10
  val MaxT etSoc alProofS ze = 10
  val M nUserSoc alProofS ze = 1

  def requ redT etAuthors(query: RecapQuery): Opt on[Set[Long]] = {
    query.utegL kedByT etsOpt ons
      .f lter(_. s nNetwork)
      .map(_.  ghtedFollow ngs.keySet)
  }

  def makeUTEGQuery(
    query: RecapQuery,
    soc alProofTypes: Seq[Soc alProofType],
    utegCountProv der: DependencyProv der[ nt]
  ): Recom ndT etEnt yQuery = {
    val utegL kedByT etsOpt = query.utegL kedByT etsOpt ons
    Recom ndT etEnt yQuery(
      user d = query.user d,
      d splayLocat on = T etEnt yD splayLocat on.Ho T  l ne,
      seedUser dsW h  ghts = utegL kedByT etsOpt.map(_.  ghtedFollow ngs).getOrElse(Map.empty),
      maxT etResults = utegCountProv der(query),
      maxT etAge nM ll s = // t  "to"  n t  Range f eld  s not supported by t  new endpo nt
        query.range match {
          case So (T  Range(from, _)) => from.map(_.unt lNow. nM ll s)
          case So (T et dRange(from, _)) => from.map(Snowflake d.t  From d(_).unt lNow. nM ll s)
          case _ => None
        },
      excludedT et ds = query.excludedT et ds,
      maxUserSoc alProofS ze = So (MaxUserSoc alProofS ze),
      maxT etSoc alProofS ze = So (MaxT etSoc alProofS ze),
      m nUserSoc alProofS ze = So (M nUserSoc alProofS ze),
      soc alProofTypes = soc alProofTypes,
      t etAuthors = requ redT etAuthors(query)
    )
  }
}

class UTEGResultsTransform(
  userT etEnt yGraphCl ent: UserT etEnt yGraphCl ent,
  utegCountProv der: DependencyProv der[ nt],
  recom ndat onsF lter: DependencyTransfor r[Seq[T etRecom ndat on], Seq[T etRecom ndat on]],
  soc alProofTypes: Seq[Soc alProofType])
    extends FutureArrow[Cand dateEnvelope, Cand dateEnvelope] {

  overr de def apply(envelope: Cand dateEnvelope): Future[Cand dateEnvelope] = {
    val utegQuery =
      UTEGResultsTransform.makeUTEGQuery(envelope.query, soc alProofTypes, utegCountProv der)
    userT etEnt yGraphCl ent.f ndT etRecom ndat ons(utegQuery).map { recom ndat ons =>
      val f lteredRecom ndat ons = recom ndat onsF lter(envelope.query, recom ndat ons)
      val utegResultsMap = f lteredRecom ndat ons.map { recom ndat on =>
        recom ndat on.t et d -> recom ndat on
      }.toMap
      envelope.copy(utegResults = utegResultsMap)
    }
  }
}
