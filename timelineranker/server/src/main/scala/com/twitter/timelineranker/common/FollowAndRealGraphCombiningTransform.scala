package com.tw ter.t  l neranker.common

 mport com.tw ter.f nagle.stats.Stat
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.t  l neranker.core.Cand dateEnvelope
 mport com.tw ter.t  l neranker.model.RecapQuery.DependencyProv der
 mport com.tw ter.t  l neranker.para ters.recap.RecapQueryContext
 mport com.tw ter.t  l neranker.para ters. n_network_t ets. nNetworkT etParams.RecycledMaxFollo dUsersEnableAnt D lut onParam
 mport com.tw ter.t  l neranker.v s b l y.FollowGraphDataProv der
 mport com.tw ter.t  l nes.earlyb rd.common.opt ons.AuthorScoreAdjust nts
 mport com.tw ter.ut l.Future

/**
 * Transform wh ch cond  onally aug nts follow graph data us ng t  real graph,
 * der ved from t  earlyb rdOpt ons passed  n t  query
 *
 * @param followGraphDataProv der data prov der to be used for fetch ng updated mutual follow  nfo
 * @param maxFollo dUsersProv der max number of users to return
 * @param enableRealGraphUsersProv der should   aug nt us ng real graph data?
 * @param maxRealGraphAndFollo dUsersProv der max comb ned users to return, overr des maxFollo dUsersProv der above
 * @param statsRece ver scoped stats rece ved
 */
class FollowAndRealGraphComb n ngTransform(
  followGraphDataProv der: FollowGraphDataProv der,
  maxFollo dUsersProv der: DependencyProv der[ nt],
  enableRealGraphUsersProv der: DependencyProv der[Boolean],
  maxRealGraphAndFollo dUsersProv der: DependencyProv der[ nt],
   mputeRealGraphAuthor  ghtsProv der: DependencyProv der[Boolean],
   mputeRealGraphAuthor  ghtsPercent leProv der: DependencyProv der[ nt],
  statsRece ver: StatsRece ver)
    extends FutureArrow[Cand dateEnvelope, Cand dateEnvelope] {

  // Number of authors  n t  seedset after m x ng follo d users and real graph users
  // Only have t  stat  f user follows >= maxFollo dUsers and enableRealGraphUsers  s true and onlyRealGraphUsers  s false
  val numFollowAndRealGraphUsersStat: Stat = statsRece ver.stat("numFollowAndRealGraphUsers")
  val numFollowAndRealGraphUsersFromFollowGraphStat =
    statsRece ver.scope("numFollowAndRealGraphUsers").stat("FollowGraphUsers")
  val numFollowAndRealGraphUsersFromRealGraphStat =
    statsRece ver.scope("numFollowAndRealGraphUsers").stat("RealGraphUsers")
  val numFollowAndRealGraphUsersFromRealGraphCounter =
    statsRece ver.scope("numFollowAndRealGraphUsers").counter()

  // Number of authors  n t  seedset w h only follo d users
  // Only have t  stat  f user follows >= maxFollo dUsers and enableRealGraphUsers  s false
  val numFollo dUsersStat: Stat = statsRece ver.stat("numFollo dUsers")

  // Number of authors  n t  seedset w h only follo d users
  // Only have t  stat  f user follows < maxFollo dUsers
  val numFollo dUsersLessThanMaxStat: Stat = statsRece ver.stat("numFollo dUsersLessThanMax")
  val numFollo dUsersLessThanMaxCounter =
    statsRece ver.scope("numFollo dUsersLessThanMax").counter()
  val numFollo dUsersMoreThanMaxStat: Stat = statsRece ver.stat("numFollo dUsersMoreThanMax")
  val numFollo dUsersMoreThanMaxCounter =
    statsRece ver.scope("numFollo dUsersMoreThanMax").counter()

  val realGraphAuthor  ghtsSumProdStat: Stat = statsRece ver.stat("realGraphAuthor  ghtsSumProd")
  val realGraphAuthor  ghtsSumM nExpStat: Stat =
    statsRece ver.stat("realGraphAuthor  ghtsSumM nExp")
  val realGraphAuthor  ghtsSumP50ExpStat: Stat =
    statsRece ver.stat("realGraphAuthor  ghtsSumP50Exp")
  val realGraphAuthor  ghtsSumP95ExpStat: Stat =
    statsRece ver.stat("realGraphAuthor  ghtsSumP95Exp")
  val numAuthorsW houtRealgraphScoreStat: Stat =
    statsRece ver.stat("numAuthorsW houtRealgraphScore")

  overr de def apply(envelope: Cand dateEnvelope): Future[Cand dateEnvelope] = {
    val realGraphData = envelope.query.earlyb rdOpt ons
      .map(_.authorScoreAdjust nts.authorScoreMap)
      .getOrElse(Map.empty)

    Future
      .jo n(
        envelope.followGraphData.follo dUser dsFuture,
        envelope.followGraphData.mutedUser dsFuture
      ).map {
        case (follo dUser ds, mutedUser ds) =>
          // Ant -d lut on param for DDG-16198
          val recycledMaxFollo dUsersEnableAnt D lut onParamProv der =
            DependencyProv der.from(RecycledMaxFollo dUsersEnableAnt D lut onParam)

          val maxFollo dUsers = {
             f (follo dUser ds.s ze > RecapQueryContext.MaxFollo dUsers.default && recycledMaxFollo dUsersEnableAnt D lut onParamProv der(
                envelope.query)) {
              // tr gger exper  nt
              maxFollo dUsersProv der(envelope.query)
            } else {
              maxFollo dUsersProv der(envelope.query)
            }
          }

          val f lteredRealGraphUser ds = realGraphData.keySet
            .f lterNot(mutedUser ds)
            .take(maxFollo dUsers)
            .toSeq

          val f lteredFollo dUser ds = follo dUser ds.f lterNot(mutedUser ds)

           f (follo dUser ds.s ze < maxFollo dUsers) {
            numFollo dUsersLessThanMaxStat.add(f lteredFollo dUser ds.s ze)
            // stats
            numFollo dUsersLessThanMaxCounter. ncr()
            (f lteredFollo dUser ds, false)
          } else {
            numFollo dUsersMoreThanMaxStat.add(f lteredFollo dUser ds.s ze)
            numFollo dUsersMoreThanMaxCounter. ncr()
             f (enableRealGraphUsersProv der(envelope.query)) {
              val maxRealGraphAndFollo dUsersNum =
                maxRealGraphAndFollo dUsersProv der(envelope.query)
              requ re(
                maxRealGraphAndFollo dUsersNum >= maxFollo dUsers,
                "maxRealGraphAndFollo dUsers must be greater than or equal to maxFollo dUsers."
              )
              val recentFollo dUsersNum = RecapQueryContext.MaxFollo dUsers.bounds
                .apply(maxRealGraphAndFollo dUsersNum - f lteredRealGraphUser ds.s ze)

              val recentFollo dUsers =
                f lteredFollo dUser ds
                  .f lterNot(f lteredRealGraphUser ds.conta ns)
                  .take(recentFollo dUsersNum)

              val f lteredFollowAndRealGraphUser ds =
                recentFollo dUsers ++ f lteredRealGraphUser ds

              // Track t  s ze of recentFollo dUsers from SGS
              numFollowAndRealGraphUsersFromFollowGraphStat.add(recentFollo dUsers.s ze)
              // Track t  s ze of f lteredRealGraphUser ds from real graph dataset.
              numFollowAndRealGraphUsersFromRealGraphStat.add(f lteredRealGraphUser ds.s ze)

              numFollowAndRealGraphUsersFromRealGraphCounter. ncr()

              numFollowAndRealGraphUsersStat.add(f lteredFollowAndRealGraphUser ds.s ze)

              (f lteredFollowAndRealGraphUser ds, true)
            } else {
              numFollo dUsersStat.add(follo dUser ds.s ze)
              (f lteredFollo dUser ds, false)
            }
          }
      }.map {
        case (updatedFollowSeq, shouldUpdateMutualFollows) =>
          val updatedMutualFollow ng =  f (shouldUpdateMutualFollows) {
            followGraphDataProv der.getMutuallyFollow ngUser ds(
              envelope.query.user d,
              updatedFollowSeq)
          } else {
            envelope.followGraphData.mutuallyFollow ngUser dsFuture
          }

          val followGraphData = envelope.followGraphData.copy(
            follo dUser dsFuture = Future.value(updatedFollowSeq),
            mutuallyFollow ngUser dsFuture = updatedMutualFollow ng
          )

          val author dsW hRealgraphScore = realGraphData.keySet
          val author dsW houtRealgraphScores =
            updatedFollowSeq.f lterNot(author dsW hRealgraphScore.conta ns)

          //stat for logg ng t  percentage of users' follow ngs that do not have a realgraph score
           f (updatedFollowSeq.nonEmpty)
            numAuthorsW houtRealgraphScoreStat.add(
              author dsW houtRealgraphScores.s ze / updatedFollowSeq.s ze * 100)

           f ( mputeRealGraphAuthor  ghtsProv der(envelope.query) && realGraphData.nonEmpty) {
            val  mputedScorePercent le =
               mputeRealGraphAuthor  ghtsPercent leProv der(envelope.query) / 100.0
            val ex st ngAuthor dScores = realGraphData.values.toL st.sorted
            val  mputedScore ndex = Math.m n(
              ex st ngAuthor dScores.length - 1,
              (ex st ngAuthor dScores.length *  mputedScorePercent le).to nt)
            val  mputedScore = ex st ngAuthor dScores( mputedScore ndex)

            val updatedAuthorScoreMap = realGraphData ++ author dsW houtRealgraphScores
              .map(_ ->  mputedScore).toMap
             mputedScorePercent le match {
              case 0.0 =>
                realGraphAuthor  ghtsSumM nExpStat.add(updatedAuthorScoreMap.values.sum.toFloat)
              case 0.5 =>
                realGraphAuthor  ghtsSumP50ExpStat.add(updatedAuthorScoreMap.values.sum.toFloat)
              case 0.95 =>
                realGraphAuthor  ghtsSumP95ExpStat.add(updatedAuthorScoreMap.values.sum.toFloat)
              case _ =>
            }
            val earlyb rdOpt onsW hUpdatedAuthorScoreMap = envelope.query.earlyb rdOpt ons
              .map(_.copy(authorScoreAdjust nts = AuthorScoreAdjust nts(updatedAuthorScoreMap)))
            val updatedQuery =
              envelope.query.copy(earlyb rdOpt ons = earlyb rdOpt onsW hUpdatedAuthorScoreMap)
            envelope.copy(query = updatedQuery, followGraphData = followGraphData)
          } else {
            envelope.query.earlyb rdOpt ons
              .map(_.authorScoreAdjust nts.authorScoreMap.values.sum.toFloat).foreach {
                realGraphAuthor  ghtsSumProdStat.add(_)
              }
            envelope.copy(followGraphData = followGraphData)
          }
      }
  }
}
