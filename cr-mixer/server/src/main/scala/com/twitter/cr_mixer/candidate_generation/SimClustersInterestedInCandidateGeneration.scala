package com.tw ter.cr_m xer.cand date_generat on

 mport com.tw ter.cr_m xer.model.Cand dateGenerat on nfo
 mport com.tw ter.cr_m xer.model.T etW hCand dateGenerat on nfo
 mport com.tw ter.cr_m xer.model.T etW hScore
 mport com.tw ter.cr_m xer.param.GlobalParams
 mport com.tw ter.cr_m xer.param. nterested nParams
 mport com.tw ter.cr_m xer.param.S mClustersANNParams
 mport com.tw ter.cr_m xer.s m lar y_eng ne.Eng neQuery
 mport com.tw ter.cr_m xer.s m lar y_eng ne.S mClustersANNS m lar yEng ne
 mport com.tw ter.cr_m xer.s m lar y_eng ne.StandardS m lar yEng ne
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base.Cand dateS ce
 mport com.tw ter.fr gate.common.ut l.StatsUt l
 mport com.tw ter.s mclusters_v2.common.ModelVers ons
 mport com.tw ter.s mclusters_v2.thr ftscala. nternal d
 mport com.tw ter.t  l nes.conf gap 
 mport com.tw ter.ut l.Future
 mport javax. nject. nject
 mport javax. nject.S ngleton
 mport javax. nject.Na d
 mport com.tw ter.cr_m xer.model.ModuleNa s

/**
 * T  store looks for s m lar t ets for a g ven User d that generates User nterested n
 * from S mClustersANN.   w ll be a standalone Cand dateGenerat on class mov ng forward.
 *
 * After t  abstract on  mprove nt (apply S m lar yEng ne tra )
 * t se CG w ll be subjected to change.
 */
@S ngleton
case class S mClusters nterested nCand dateGenerat on @ nject() (
  @Na d(ModuleNa s.S mClustersANNS m lar yEng ne)
  s mClustersANNS m lar yEng ne: StandardS m lar yEng ne[
    S mClustersANNS m lar yEng ne.Query,
    T etW hScore
  ],
  statsRece ver: StatsRece ver)
    extends Cand dateS ce[
      S mClusters nterested nCand dateGenerat on.Query,
      Seq[T etW hCand dateGenerat on nfo]
    ] {

  overr de def na : Str ng = t .getClass.getS mpleNa 
  pr vate val stats = statsRece ver.scope(na )
  pr vate val fetchCand datesStat = stats.scope("fetchCand dates")

  overr de def get(
    query: S mClusters nterested nCand dateGenerat on.Query
  ): Future[Opt on[Seq[Seq[T etW hCand dateGenerat on nfo]]]] = {

    query. nternal d match {
      case _:  nternal d.User d =>
        StatsUt l.trackOpt on emsStats(fetchCand datesStat) {
          // User nterested n Quer es
          val user nterested nCand dateResultFut =
             f (query.enableUser nterested n && query.enableProdS mClustersANNS m lar yEng ne)
              get nterested nCand dateResult(
                s mClustersANNS m lar yEng ne,
                query. nterested nS mClustersANNQuery,
                query.s mClusters nterested nM nScore)
            else
              Future.None

          val user nterested nExper  ntalSANNCand dateResultFut =
             f (query.enableUser nterested n && query.enableExper  ntalS mClustersANNS m lar yEng ne)
              get nterested nCand dateResult(
                s mClustersANNS m lar yEng ne,
                query. nterested nExper  ntalS mClustersANNQuery,
                query.s mClusters nterested nM nScore)
            else
              Future.None

          val user nterested nSANN1Cand dateResultFut =
             f (query.enableUser nterested n && query.enableS mClustersANN1S m lar yEng ne)
              get nterested nCand dateResult(
                s mClustersANNS m lar yEng ne,
                query. nterested nS mClustersANN1Query,
                query.s mClusters nterested nM nScore)
            else
              Future.None

          val user nterested nSANN2Cand dateResultFut =
             f (query.enableUser nterested n && query.enableS mClustersANN2S m lar yEng ne)
              get nterested nCand dateResult(
                s mClustersANNS m lar yEng ne,
                query. nterested nS mClustersANN2Query,
                query.s mClusters nterested nM nScore)
            else
              Future.None

          val user nterested nSANN3Cand dateResultFut =
             f (query.enableUser nterested n && query.enableS mClustersANN3S m lar yEng ne)
              get nterested nCand dateResult(
                s mClustersANNS m lar yEng ne,
                query. nterested nS mClustersANN3Query,
                query.s mClusters nterested nM nScore)
            else
              Future.None

          val user nterested nSANN5Cand dateResultFut =
             f (query.enableUser nterested n && query.enableS mClustersANN5S m lar yEng ne)
              get nterested nCand dateResult(
                s mClustersANNS m lar yEng ne,
                query. nterested nS mClustersANN5Query,
                query.s mClusters nterested nM nScore)
            else
              Future.None

          val user nterested nSANN4Cand dateResultFut =
             f (query.enableUser nterested n && query.enableS mClustersANN4S m lar yEng ne)
              get nterested nCand dateResult(
                s mClustersANNS m lar yEng ne,
                query. nterested nS mClustersANN4Query,
                query.s mClusters nterested nM nScore)
            else
              Future.None
          // UserNext nterested n Quer es
          val userNext nterested nCand dateResultFut =
             f (query.enableUserNext nterested n && query.enableProdS mClustersANNS m lar yEng ne)
              get nterested nCand dateResult(
                s mClustersANNS m lar yEng ne,
                query.next nterested nS mClustersANNQuery,
                query.s mClusters nterested nM nScore)
            else
              Future.None

          val userNext nterested nExper  ntalSANNCand dateResultFut =
             f (query.enableUserNext nterested n && query.enableExper  ntalS mClustersANNS m lar yEng ne)
              get nterested nCand dateResult(
                s mClustersANNS m lar yEng ne,
                query.next nterested nExper  ntalS mClustersANNQuery,
                query.s mClusters nterested nM nScore)
            else
              Future.None

          val userNext nterested nSANN1Cand dateResultFut =
             f (query.enableUserNext nterested n && query.enableS mClustersANN1S m lar yEng ne)
              get nterested nCand dateResult(
                s mClustersANNS m lar yEng ne,
                query.next nterested nS mClustersANN1Query,
                query.s mClusters nterested nM nScore)
            else
              Future.None

          val userNext nterested nSANN2Cand dateResultFut =
             f (query.enableUserNext nterested n && query.enableS mClustersANN2S m lar yEng ne)
              get nterested nCand dateResult(
                s mClustersANNS m lar yEng ne,
                query.next nterested nS mClustersANN2Query,
                query.s mClusters nterested nM nScore)
            else
              Future.None

          val userNext nterested nSANN3Cand dateResultFut =
             f (query.enableUserNext nterested n && query.enableS mClustersANN3S m lar yEng ne)
              get nterested nCand dateResult(
                s mClustersANNS m lar yEng ne,
                query.next nterested nS mClustersANN3Query,
                query.s mClusters nterested nM nScore)
            else
              Future.None

          val userNext nterested nSANN5Cand dateResultFut =
             f (query.enableUserNext nterested n && query.enableS mClustersANN5S m lar yEng ne)
              get nterested nCand dateResult(
                s mClustersANNS m lar yEng ne,
                query.next nterested nS mClustersANN5Query,
                query.s mClusters nterested nM nScore)
            else
              Future.None

          val userNext nterested nSANN4Cand dateResultFut =
             f (query.enableUserNext nterested n && query.enableS mClustersANN4S m lar yEng ne)
              get nterested nCand dateResult(
                s mClustersANNS m lar yEng ne,
                query.next nterested nS mClustersANN4Query,
                query.s mClusters nterested nM nScore)
            else
              Future.None

          // AddressBook nterested n Quer es
          val userAddressBook nterested nCand dateResultFut =
             f (query.enableAddressBookNext nterested n && query.enableProdS mClustersANNS m lar yEng ne)
              get nterested nCand dateResult(
                s mClustersANNS m lar yEng ne,
                query.addressbook nterested nS mClustersANNQuery,
                query.s mClusters nterested nM nScore)
            else
              Future.None

          val userAddressBookExper  ntalSANNCand dateResultFut =
             f (query.enableAddressBookNext nterested n && query.enableExper  ntalS mClustersANNS m lar yEng ne)
              get nterested nCand dateResult(
                s mClustersANNS m lar yEng ne,
                query.addressbook nterested nExper  ntalS mClustersANNQuery,
                query.s mClusters nterested nM nScore)
            else
              Future.None

          val userAddressBookSANN1Cand dateResultFut =
             f (query.enableAddressBookNext nterested n && query.enableS mClustersANN1S m lar yEng ne)
              get nterested nCand dateResult(
                s mClustersANNS m lar yEng ne,
                query.addressbook nterested nS mClustersANN1Query,
                query.s mClusters nterested nM nScore)
            else
              Future.None

          val userAddressBookSANN2Cand dateResultFut =
             f (query.enableAddressBookNext nterested n && query.enableS mClustersANN2S m lar yEng ne)
              get nterested nCand dateResult(
                s mClustersANNS m lar yEng ne,
                query.addressbook nterested nS mClustersANN2Query,
                query.s mClusters nterested nM nScore)
            else
              Future.None

          val userAddressBookSANN3Cand dateResultFut =
             f (query.enableAddressBookNext nterested n && query.enableS mClustersANN3S m lar yEng ne)
              get nterested nCand dateResult(
                s mClustersANNS m lar yEng ne,
                query.addressbook nterested nS mClustersANN3Query,
                query.s mClusters nterested nM nScore)
            else
              Future.None

          val userAddressBookSANN5Cand dateResultFut =
             f (query.enableAddressBookNext nterested n && query.enableS mClustersANN5S m lar yEng ne)
              get nterested nCand dateResult(
                s mClustersANNS m lar yEng ne,
                query.addressbook nterested nS mClustersANN5Query,
                query.s mClusters nterested nM nScore)
            else
              Future.None

          val userAddressBookSANN4Cand dateResultFut =
             f (query.enableAddressBookNext nterested n && query.enableS mClustersANN4S m lar yEng ne)
              get nterested nCand dateResult(
                s mClustersANNS m lar yEng ne,
                query.addressbook nterested nS mClustersANN4Query,
                query.s mClusters nterested nM nScore)
            else
              Future.None

          Future
            .collect(
              Seq(
                user nterested nCand dateResultFut,
                userNext nterested nCand dateResultFut,
                userAddressBook nterested nCand dateResultFut,
                user nterested nExper  ntalSANNCand dateResultFut,
                userNext nterested nExper  ntalSANNCand dateResultFut,
                userAddressBookExper  ntalSANNCand dateResultFut,
                user nterested nSANN1Cand dateResultFut,
                userNext nterested nSANN1Cand dateResultFut,
                userAddressBookSANN1Cand dateResultFut,
                user nterested nSANN2Cand dateResultFut,
                userNext nterested nSANN2Cand dateResultFut,
                userAddressBookSANN2Cand dateResultFut,
                user nterested nSANN3Cand dateResultFut,
                userNext nterested nSANN3Cand dateResultFut,
                userAddressBookSANN3Cand dateResultFut,
                user nterested nSANN5Cand dateResultFut,
                userNext nterested nSANN5Cand dateResultFut,
                userAddressBookSANN5Cand dateResultFut,
                user nterested nSANN4Cand dateResultFut,
                userNext nterested nSANN4Cand dateResultFut,
                userAddressBookSANN4Cand dateResultFut
              )
            ).map { cand dateResults =>
              So (
                cand dateResults.map(cand dateResult => cand dateResult.getOrElse(Seq.empty))
              )
            }
        }
      case _ =>
        stats.counter("s ce d_ s_not_user d_cnt"). ncr()
        Future.None
    }
  }

  pr vate def s mClustersCand dateM nScoreF lter(
    s mClustersAnnCand dates: Seq[T etW hScore],
    s mClusters nterested nM nScore: Double,
    s mClustersANNConf g d: Str ng
  ): Seq[T etW hScore] = {
    val f lteredCand dates = s mClustersAnnCand dates
      .f lter { cand date =>
        cand date.score > s mClusters nterested nM nScore
      }

    stats.stat(s mClustersANNConf g d, "s mClustersAnnCand dates_s ze").add(f lteredCand dates.s ze)
    stats.counter(s mClustersANNConf g d, "s mClustersAnnRequests"). ncr()
     f (f lteredCand dates. sEmpty)
      stats.counter(s mClustersANNConf g d, "emptyF lteredS mClustersAnnCand dates"). ncr()

    f lteredCand dates.map { cand date =>
      T etW hScore(cand date.t et d, cand date.score)
    }
  }

  pr vate def get nterested nCand dateResult(
    s mClustersANNS m lar yEng ne: StandardS m lar yEng ne[
      S mClustersANNS m lar yEng ne.Query,
      T etW hScore
    ],
    s mClustersANNQuery: Eng neQuery[S mClustersANNS m lar yEng ne.Query],
    s mClusters nterested nM nScore: Double,
  ): Future[Opt on[Seq[T etW hCand dateGenerat on nfo]]] = {
    val  nterested nCand datesFut =
      s mClustersANNS m lar yEng ne.getCand dates(s mClustersANNQuery)

    val  nterested nCand dateResultFut =  nterested nCand datesFut.map {  nterested nCand dates =>
      stats.stat("cand dateS ze").add( nterested nCand dates.s ze)

      val embedd ngCand datesStat = stats.scope(
        s mClustersANNQuery.storeQuery.s mClustersANNQuery.s ceEmbedd ng d.embedd ngType.na )

      embedd ngCand datesStat.stat("cand dateS ze").add( nterested nCand dates.s ze)
       f ( nterested nCand dates. sEmpty) {
        embedd ngCand datesStat.counter("empty_results"). ncr()
      }
      embedd ngCand datesStat.counter("requests"). ncr()

      val f lteredT ets = s mClustersCand dateM nScoreF lter(
         nterested nCand dates.toSeq.flatten,
        s mClusters nterested nM nScore,
        s mClustersANNQuery.storeQuery.s mClustersANNConf g d)

      val  nterested nT etsW hCG nfo = f lteredT ets.map { t etW hScore =>
        T etW hCand dateGenerat on nfo(
          t etW hScore.t et d,
          Cand dateGenerat on nfo(
            None,
            S mClustersANNS m lar yEng ne
              .toS m lar yEng ne nfo(s mClustersANNQuery, t etW hScore.score),
            Seq.empty // SANN  s an atom c SE, and  nce   has no contr but ng SEs
          )
        )
      }

      val  nterested nResults =  f ( nterested nT etsW hCG nfo.nonEmpty) {
        So ( nterested nT etsW hCG nfo)
      } else None
       nterested nResults
    }
     nterested nCand dateResultFut
  }
}

object S mClusters nterested nCand dateGenerat on {

  case class Query(
     nternal d:  nternal d,
    enableUser nterested n: Boolean,
    enableUserNext nterested n: Boolean,
    enableAddressBookNext nterested n: Boolean,
    enableProdS mClustersANNS m lar yEng ne: Boolean,
    enableExper  ntalS mClustersANNS m lar yEng ne: Boolean,
    enableS mClustersANN1S m lar yEng ne: Boolean,
    enableS mClustersANN2S m lar yEng ne: Boolean,
    enableS mClustersANN3S m lar yEng ne: Boolean,
    enableS mClustersANN5S m lar yEng ne: Boolean,
    enableS mClustersANN4S m lar yEng ne: Boolean,
    s mClusters nterested nM nScore: Double,
    s mClustersNext nterested nM nScore: Double,
    s mClustersAddressBook nterested nM nScore: Double,
     nterested nS mClustersANNQuery: Eng neQuery[S mClustersANNS m lar yEng ne.Query],
    next nterested nS mClustersANNQuery: Eng neQuery[S mClustersANNS m lar yEng ne.Query],
    addressbook nterested nS mClustersANNQuery: Eng neQuery[S mClustersANNS m lar yEng ne.Query],
     nterested nExper  ntalS mClustersANNQuery: Eng neQuery[S mClustersANNS m lar yEng ne.Query],
    next nterested nExper  ntalS mClustersANNQuery: Eng neQuery[
      S mClustersANNS m lar yEng ne.Query
    ],
    addressbook nterested nExper  ntalS mClustersANNQuery: Eng neQuery[
      S mClustersANNS m lar yEng ne.Query
    ],
     nterested nS mClustersANN1Query: Eng neQuery[S mClustersANNS m lar yEng ne.Query],
    next nterested nS mClustersANN1Query: Eng neQuery[S mClustersANNS m lar yEng ne.Query],
    addressbook nterested nS mClustersANN1Query: Eng neQuery[S mClustersANNS m lar yEng ne.Query],
     nterested nS mClustersANN2Query: Eng neQuery[S mClustersANNS m lar yEng ne.Query],
    next nterested nS mClustersANN2Query: Eng neQuery[S mClustersANNS m lar yEng ne.Query],
    addressbook nterested nS mClustersANN2Query: Eng neQuery[S mClustersANNS m lar yEng ne.Query],
     nterested nS mClustersANN3Query: Eng neQuery[S mClustersANNS m lar yEng ne.Query],
    next nterested nS mClustersANN3Query: Eng neQuery[S mClustersANNS m lar yEng ne.Query],
    addressbook nterested nS mClustersANN3Query: Eng neQuery[S mClustersANNS m lar yEng ne.Query],
     nterested nS mClustersANN5Query: Eng neQuery[S mClustersANNS m lar yEng ne.Query],
    next nterested nS mClustersANN5Query: Eng neQuery[S mClustersANNS m lar yEng ne.Query],
    addressbook nterested nS mClustersANN5Query: Eng neQuery[S mClustersANNS m lar yEng ne.Query],
     nterested nS mClustersANN4Query: Eng neQuery[S mClustersANNS m lar yEng ne.Query],
    next nterested nS mClustersANN4Query: Eng neQuery[S mClustersANNS m lar yEng ne.Query],
    addressbook nterested nS mClustersANN4Query: Eng neQuery[S mClustersANNS m lar yEng ne.Query],
  )

  def fromParams(
     nternal d:  nternal d,
    params: conf gap .Params,
  ): Query = {
    // S mClusters common conf gs
    val s mClustersModelVers on =
      ModelVers ons.Enum.enumToS mClustersModelVers onMap(params(GlobalParams.ModelVers onParam))
    val s mClustersANNConf g d = params(S mClustersANNParams.S mClustersANNConf g d)
    val exper  ntalS mClustersANNConf g d = params(
      S mClustersANNParams.Exper  ntalS mClustersANNConf g d)
    val s mClustersANN1Conf g d = params(S mClustersANNParams.S mClustersANN1Conf g d)
    val s mClustersANN2Conf g d = params(S mClustersANNParams.S mClustersANN2Conf g d)
    val s mClustersANN3Conf g d = params(S mClustersANNParams.S mClustersANN3Conf g d)
    val s mClustersANN5Conf g d = params(S mClustersANNParams.S mClustersANN5Conf g d)
    val s mClustersANN4Conf g d = params(S mClustersANNParams.S mClustersANN4Conf g d)

    val s mClusters nterested nM nScore = params( nterested nParams.M nScoreParam)
    val s mClustersNext nterested nM nScore = params(
       nterested nParams.M nScoreSequent alModelParam)
    val s mClustersAddressBook nterested nM nScore = params(
       nterested nParams.M nScoreAddressBookParam)

    //  nterested n embedd ngs para ters
    val  nterested nEmbedd ng = params( nterested nParams. nterested nEmbedd ng dParam)
    val next nterested nEmbedd ng = params( nterested nParams.Next nterested nEmbedd ng dParam)
    val addressbook nterested nEmbedd ng = params(
       nterested nParams.AddressBook nterested nEmbedd ng dParam)

    // Prod S mClustersANN Query
    val  nterested nS mClustersANNQuery =
      S mClustersANNS m lar yEng ne.fromParams(
         nternal d,
         nterested nEmbedd ng.embedd ngType,
        s mClustersModelVers on,
        s mClustersANNConf g d,
        params)

    val next nterested nS mClustersANNQuery =
      S mClustersANNS m lar yEng ne.fromParams(
         nternal d,
        next nterested nEmbedd ng.embedd ngType,
        s mClustersModelVers on,
        s mClustersANNConf g d,
        params)

    val addressbook nterested nS mClustersANNQuery =
      S mClustersANNS m lar yEng ne.fromParams(
         nternal d,
        addressbook nterested nEmbedd ng.embedd ngType,
        s mClustersModelVers on,
        s mClustersANNConf g d,
        params)

    // Exper  ntal SANN cluster Query
    val  nterested nExper  ntalS mClustersANNQuery =
      S mClustersANNS m lar yEng ne.fromParams(
         nternal d,
         nterested nEmbedd ng.embedd ngType,
        s mClustersModelVers on,
        exper  ntalS mClustersANNConf g d,
        params)

    val next nterested nExper  ntalS mClustersANNQuery =
      S mClustersANNS m lar yEng ne.fromParams(
         nternal d,
        next nterested nEmbedd ng.embedd ngType,
        s mClustersModelVers on,
        exper  ntalS mClustersANNConf g d,
        params)

    val addressbook nterested nExper  ntalS mClustersANNQuery =
      S mClustersANNS m lar yEng ne.fromParams(
         nternal d,
        addressbook nterested nEmbedd ng.embedd ngType,
        s mClustersModelVers on,
        exper  ntalS mClustersANNConf g d,
        params)

    // S mClusters ANN cluster 1 Query
    val  nterested nS mClustersANN1Query =
      S mClustersANNS m lar yEng ne.fromParams(
         nternal d,
         nterested nEmbedd ng.embedd ngType,
        s mClustersModelVers on,
        s mClustersANN1Conf g d,
        params)

    val next nterested nS mClustersANN1Query =
      S mClustersANNS m lar yEng ne.fromParams(
         nternal d,
        next nterested nEmbedd ng.embedd ngType,
        s mClustersModelVers on,
        s mClustersANN1Conf g d,
        params)

    val addressbook nterested nS mClustersANN1Query =
      S mClustersANNS m lar yEng ne.fromParams(
         nternal d,
        addressbook nterested nEmbedd ng.embedd ngType,
        s mClustersModelVers on,
        s mClustersANN1Conf g d,
        params)

    // S mClusters ANN cluster 2 Query
    val  nterested nS mClustersANN2Query =
      S mClustersANNS m lar yEng ne.fromParams(
         nternal d,
         nterested nEmbedd ng.embedd ngType,
        s mClustersModelVers on,
        s mClustersANN2Conf g d,
        params)

    val next nterested nS mClustersANN2Query =
      S mClustersANNS m lar yEng ne.fromParams(
         nternal d,
        next nterested nEmbedd ng.embedd ngType,
        s mClustersModelVers on,
        s mClustersANN2Conf g d,
        params)

    val addressbook nterested nS mClustersANN2Query =
      S mClustersANNS m lar yEng ne.fromParams(
         nternal d,
        addressbook nterested nEmbedd ng.embedd ngType,
        s mClustersModelVers on,
        s mClustersANN2Conf g d,
        params)

    // S mClusters ANN cluster 3 Query
    val  nterested nS mClustersANN3Query =
      S mClustersANNS m lar yEng ne.fromParams(
         nternal d,
         nterested nEmbedd ng.embedd ngType,
        s mClustersModelVers on,
        s mClustersANN3Conf g d,
        params)

    val next nterested nS mClustersANN3Query =
      S mClustersANNS m lar yEng ne.fromParams(
         nternal d,
        next nterested nEmbedd ng.embedd ngType,
        s mClustersModelVers on,
        s mClustersANN3Conf g d,
        params)

    val addressbook nterested nS mClustersANN3Query =
      S mClustersANNS m lar yEng ne.fromParams(
         nternal d,
        addressbook nterested nEmbedd ng.embedd ngType,
        s mClustersModelVers on,
        s mClustersANN3Conf g d,
        params)

    // S mClusters ANN cluster 5 Query
    val  nterested nS mClustersANN5Query =
      S mClustersANNS m lar yEng ne.fromParams(
         nternal d,
         nterested nEmbedd ng.embedd ngType,
        s mClustersModelVers on,
        s mClustersANN5Conf g d,
        params)
    // S mClusters ANN cluster 4 Query
    val  nterested nS mClustersANN4Query =
      S mClustersANNS m lar yEng ne.fromParams(
         nternal d,
         nterested nEmbedd ng.embedd ngType,
        s mClustersModelVers on,
        s mClustersANN4Conf g d,
        params)

    val next nterested nS mClustersANN5Query =
      S mClustersANNS m lar yEng ne.fromParams(
         nternal d,
        next nterested nEmbedd ng.embedd ngType,
        s mClustersModelVers on,
        s mClustersANN5Conf g d,
        params)

    val next nterested nS mClustersANN4Query =
      S mClustersANNS m lar yEng ne.fromParams(
         nternal d,
        next nterested nEmbedd ng.embedd ngType,
        s mClustersModelVers on,
        s mClustersANN4Conf g d,
        params)

    val addressbook nterested nS mClustersANN5Query =
      S mClustersANNS m lar yEng ne.fromParams(
         nternal d,
        addressbook nterested nEmbedd ng.embedd ngType,
        s mClustersModelVers on,
        s mClustersANN5Conf g d,
        params)

    val addressbook nterested nS mClustersANN4Query =
      S mClustersANNS m lar yEng ne.fromParams(
         nternal d,
        addressbook nterested nEmbedd ng.embedd ngType,
        s mClustersModelVers on,
        s mClustersANN4Conf g d,
        params)

    Query(
       nternal d =  nternal d,
      enableUser nterested n = params( nterested nParams.EnableS ceParam),
      enableUserNext nterested n = params( nterested nParams.EnableS ceSequent alModelParam),
      enableAddressBookNext nterested n = params( nterested nParams.EnableS ceAddressBookParam),
      enableProdS mClustersANNS m lar yEng ne =
        params( nterested nParams.EnableProdS mClustersANNParam),
      enableExper  ntalS mClustersANNS m lar yEng ne =
        params( nterested nParams.EnableExper  ntalS mClustersANNParam),
      enableS mClustersANN1S m lar yEng ne = params( nterested nParams.EnableS mClustersANN1Param),
      enableS mClustersANN2S m lar yEng ne = params( nterested nParams.EnableS mClustersANN2Param),
      enableS mClustersANN3S m lar yEng ne = params( nterested nParams.EnableS mClustersANN3Param),
      enableS mClustersANN5S m lar yEng ne = params( nterested nParams.EnableS mClustersANN5Param),
      enableS mClustersANN4S m lar yEng ne = params( nterested nParams.EnableS mClustersANN4Param),
      s mClusters nterested nM nScore = s mClusters nterested nM nScore,
      s mClustersNext nterested nM nScore = s mClustersNext nterested nM nScore,
      s mClustersAddressBook nterested nM nScore = s mClustersAddressBook nterested nM nScore,
       nterested nS mClustersANNQuery =  nterested nS mClustersANNQuery,
      next nterested nS mClustersANNQuery = next nterested nS mClustersANNQuery,
      addressbook nterested nS mClustersANNQuery = addressbook nterested nS mClustersANNQuery,
       nterested nExper  ntalS mClustersANNQuery =  nterested nExper  ntalS mClustersANNQuery,
      next nterested nExper  ntalS mClustersANNQuery =
        next nterested nExper  ntalS mClustersANNQuery,
      addressbook nterested nExper  ntalS mClustersANNQuery =
        addressbook nterested nExper  ntalS mClustersANNQuery,
       nterested nS mClustersANN1Query =  nterested nS mClustersANN1Query,
      next nterested nS mClustersANN1Query = next nterested nS mClustersANN1Query,
      addressbook nterested nS mClustersANN1Query = addressbook nterested nS mClustersANN1Query,
       nterested nS mClustersANN2Query =  nterested nS mClustersANN2Query,
      next nterested nS mClustersANN2Query = next nterested nS mClustersANN2Query,
      addressbook nterested nS mClustersANN2Query = addressbook nterested nS mClustersANN2Query,
       nterested nS mClustersANN3Query =  nterested nS mClustersANN3Query,
      next nterested nS mClustersANN3Query = next nterested nS mClustersANN3Query,
      addressbook nterested nS mClustersANN3Query = addressbook nterested nS mClustersANN3Query,
       nterested nS mClustersANN5Query =  nterested nS mClustersANN5Query,
      next nterested nS mClustersANN5Query = next nterested nS mClustersANN5Query,
      addressbook nterested nS mClustersANN5Query = addressbook nterested nS mClustersANN5Query,
       nterested nS mClustersANN4Query =  nterested nS mClustersANN4Query,
      next nterested nS mClustersANN4Query = next nterested nS mClustersANN4Query,
      addressbook nterested nS mClustersANN4Query = addressbook nterested nS mClustersANN4Query,
    )
  }
}
