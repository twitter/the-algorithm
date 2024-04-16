package com.tw ter.s mclusters_v2.scald ng.top c_recom ndat ons

 mport com.tw ter.b ject on.Bufferable
 mport com.tw ter.b ject on. nject on
 mport com.tw ter.recos.ent  es.thr ftscala._
 mport com.tw ter.scald ng._
 mport com.tw ter.scald ng_ nternal.dalv2.DALWr e._
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal
 mport com.tw ter.s mclusters_v2.common.Country
 mport com.tw ter.s mclusters_v2.common.Language
 mport com.tw ter.s mclusters_v2.common.Top c d
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.s mclusters_v2.hdfs_s ces.DataS ces
 mport com.tw ter.s mclusters_v2.hdfs_s ces.TopProducersForLocaleTop csFromTop cFollowGraphScalaDataset
 mport com.tw ter.s mclusters_v2.scald ng.common.matr x.SparseMatr x
 mport com.tw ter.s mclusters_v2.scald ng.embedd ng.common.Embedd ngUt l.Producer d
 mport com.tw ter.s mclusters_v2.scald ng.embedd ng.common.ExternalDataS ces
 mport com.tw ter.s mclusters_v2.thr ftscala.UserAndNe ghbors
 mport com.tw ter.wtf.scald ng.jobs.common.AdhocExecut onApp
 mport com.tw ter.wtf.scald ng.jobs.common.Sc duledExecut onApp
 mport java.ut l.T  Zone

/**
 *  n t  f le,   compute t  top producers for a top c from t  Top c Follow Graph
 *
 *   works as follows:
 *
 *  1. Producer embedd ng: L st of users who follow t  producer's prof le and follow atleast one top c
 *
 *  2. Top c embedd ng: L st of users who follow t  top c
 *
 *  3. Score(producer, top c) = cos ne s m lar y of t  producer and top c embedd ng as def ned above
 *
 *  4. Please note that   compute t  top producers for each top c locale.
 */

/**
scald ng remote run --user cassowary \
 --target src/scala/com/tw ter/s mclusters_v2/scald ng/top c_recom ndat ons:top_producers_for_top cs_from_top c_follow_graph-adhoc \
 --ma n-class com.tw ter.s mclusters_v2.scald ng.top c_recom ndat ons.ProducersForTop csFromTop cFollowGraphAdhocApp \
 --subm ter  hadoopnest1.atla.tw ter.com  \
 --  --date 2021-01-06 --m nAct veFollo rs 400 --maxProducersPerTop c 50 \
 --output_d r_producers_per_top c /user/cassowary/adhoc/ldap/ttf_prof le_pages_top cs_to_producers
 */

object ProducersForTop csFromTop cFollowGraphAdhocApp extends AdhocExecut onApp {

  overr de def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): Execut on[Un ] = {
     mport ProducersForTop csFromTop cFollowGraph._
    val outputD rProducersPerTop c = args("output_d r_producers_per_top c")
    val m nAct veFollo rsForProducer = args. nt("m nAct veFollo rs", 400)
    val maxProducersPerTop cPerLocale = args. nt("maxProducersPerTop c", 50)
    val m nTop cFollows = args. nt("m nTop cFollows", 100)

    val top csFollo dByProducersFollo rs = getTop csFromProducersFollo rs(
      DataS ces
        .userUserNormal zedGraphS ce(dateRange.prepend(Days(7))),
      ExternalDataS ces.top cFollowGraphS ce,
      ExternalDataS ces.userS ce,
      ExternalDataS ces. nferredUserConsu dLanguageS ce,
      m nAct veFollo rsForProducer,
      m nTop cFollows
    )

    sortAndGetTopProducersPerLocaleTop c(
      top csFollo dByProducersFollo rs,
      maxProducersPerTop cPerLocale).wr eExecut on(TypedTsv(outputD rProducersPerTop c))

  }
}

/**
capesospy-v2 update --bu ld_locally \
 --start_cron top_producers_for_top cs_from_top c_follow_graph \
 src/scala/com/tw ter/s mclusters_v2/capesos_conf g/atla_proc3.yaml
 */

object ProducersForTop csFromTop cFollowGraphBatchApp extends Sc duledExecut onApp {
  overr de val f rstT  : R chDate = R chDate("2020-10-01")

  overr de val batch ncre nt: Durat on = Days(1)

  pr vate val topProducersForLocaleTop csPath: Str ng =
    "/user/cassowary/manhattan_sequence_f les/top_producers_for_top cs_from_top c_follow_graph"

  overr de def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): Execut on[Un ] = {
     mport ProducersForTop csFromTop cFollowGraph._
    val m nAct veFollo rsForProducer = args. nt("m nAct veFollo rs", 400)
    val maxProducersPerTop cPerLocale = args. nt("maxProducersPerTop c", 50)
    val m nTop cFollows = args. nt("m nTop cFollows", 100)

    val top csFollo dByProducersFollo rs = getTop csFromProducersFollo rs(
      DataS ces
        .userUserNormal zedGraphS ce(dateRange.prepend(Days(7))),
      ExternalDataS ces.top cFollowGraphS ce,
      ExternalDataS ces.userS ce,
      ExternalDataS ces. nferredUserConsu dLanguageS ce,
      m nAct veFollo rsForProducer,
      m nTop cFollows
    )

    sortAndGetTopProducersPerLocaleTop c(
      top csFollo dByProducersFollo rs,
      maxProducersPerTop cPerLocale)
      .map {
        case ((top c d, languageOpt, countryOpt), producersW hScores) =>
          KeyVal(
            Semant cCoreEnt yW hLocale(
              ent y d = top c d,
              context = Locale(language = languageOpt, country = countryOpt)),
            UserScoreL st(producersW hScores.map {
              case (producer d, producerScore) =>
                UserW hScore(user d = producer d, score = producerScore)
            })
          )
      }.wr eDALVers onedKeyValExecut on(
        TopProducersForLocaleTop csFromTop cFollowGraphScalaDataset,
        D.Suff x(topProducersForLocaleTop csPath),
        vers on = Expl c EndT  (dateRange.end)
      )
  }
}

object ProducersForTop csFromTop cFollowGraph {

   mpl c  val sparseMatr x nj:  nject on[
    (Producer d, Opt on[Language], Opt on[Country]),
    Array[Byte]
  ] =
    Bufferable. nject onOf[(Producer d, Opt on[Language], Opt on[Country])]

  // T  funct on takes t  producer to top cs map and generates t  sorted and
  // truncated top producers ranked l st for each locale top c
  def sortAndGetTopProducersPerLocaleTop c(
    producerToTop cs: TypedP pe[(Producer d, (Top c d, Opt on[Language], Opt on[Country]), Double)],
    maxProducersPerLocaleTop c:  nt
  )(
     mpl c  un que D: Un que D
  ): TypedP pe[((Top c d, Opt on[Language], Opt on[Country]), L st[(Producer d, Double)])] = {
    val numTop csW hLocales = Stat("num_top cs_w h_locales")
    producerToTop cs
      .map {
        case (producer d, (top c d, languageOpt, countryOpt), score) =>
          ((top c d, languageOpt, countryOpt), Seq((producer d, score)))
      }
      .sumByKey.mapValues { producersL st =>
        numTop csW hLocales. nc()
        producersL st.sortBy(-_._2).take(maxProducersPerLocaleTop c).toL st
      }.toTypedP pe
  }

  def getTop csFromProducersFollo rs(
    userUserGraph: TypedP pe[UserAndNe ghbors],
    follo dTop csToUsers: TypedP pe[(Top c d, User d)],
    userS ce: TypedP pe[(User d, (Country, Language))],
    userLanguages: TypedP pe[(User d, Seq[(Language, Double)])],
    m nAct veFollo rsForProducer:  nt,
    m nTop cFollows:  nt
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): TypedP pe[(Producer d, (Top c d, Opt on[Language], Opt on[Country]), Double)] = {

    val usersFollow ngTop cs: TypedP pe[User d] = follo dTop csToUsers.map(_._2).d st nct
    val producerToUsersSparseMatr x: SparseMatr x[Producer d, User d, Double] =
      Top csForProducersUt ls
        .getProducersToFollo dByUsersSparseMatr x(
          userUserGraph,
          m nAct veFollo rsForProducer).f lterCols(usersFollow ngTop cs).rowL2Normal ze

    val userToTop csSparseSk nnyMatr x: SparseMatr x[
      User d,
      (Top c d, Opt on[Language], Opt on[Country]),
      Double
    ] =
      Top csForProducersUt ls
        .getFollo dTop csToUserSparseMatr x(
          follo dTop csToUsers,
          userS ce,
          userLanguages,
          m nTop cFollows).rowL2Normal ze.transpose

    // Obta n t  Producer to Locale Top cs Matr x
    val producersToLocaleTop csMatr x: SparseMatr x[
      Producer d,
      (Top c d, Opt on[Language], Opt on[Country]),
      Double
    ] =
      producerToUsersSparseMatr x.mult plySparseMatr x(userToTop csSparseSk nnyMatr x)

    producersToLocaleTop csMatr x.toTypedP pe
  }
}
