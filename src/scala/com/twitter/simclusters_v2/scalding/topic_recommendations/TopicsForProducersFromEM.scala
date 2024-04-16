package com.tw ter.s mclusters_v2.scald ng.top c_recom ndat ons
 mport com.tw ter.b ject on.Bufferable
 mport com.tw ter.b ject on. nject on
 mport com.tw ter.recos.ent  es.thr ftscala._
 mport com.tw ter.scald ng._
 mport com.tw ter.scald ng_ nternal.dalv2.DALWr e._
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal
 mport com.tw ter.s mclusters_v2.common.Country
 mport com.tw ter.s mclusters_v2.common.Language
 mport com.tw ter.s mclusters_v2.common.Semant cCoreEnt y d
 mport com.tw ter.s mclusters_v2.common.Top c d
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.s mclusters_v2.hdfs_s ces.DataS ces
 mport com.tw ter.s mclusters_v2.hdfs_s ces.TopLocaleTop csForProducerFromEmScalaDataset
 mport com.tw ter.s mclusters_v2.scald ng.common.matr x.SparseMatr x
 mport com.tw ter.s mclusters_v2.scald ng.embedd ng.common.Embedd ngUt l.Producer d
 mport com.tw ter.s mclusters_v2.scald ng.embedd ng.common.ExternalDataS ces
 mport com.tw ter.s mclusters_v2.thr ftscala.UserAndNe ghbors
 mport com.tw ter.wtf.scald ng.jobs.common.AdhocExecut onApp
 mport com.tw ter.wtf.scald ng.jobs.common.Sc duledExecut onApp
 mport com.tw ter.wtf.scald ng.jobs.common.EMRunner
 mport java.ut l.T  Zone

/**
 *  n t  f le,   compute t  top top cs for a producer to be shown on t  Top cs To Follow Module on Prof le Pages
 *
 * T  top top cs for a producer are computed us ng t  Expectat on-Max m zat on (EM) approach
 *
 *   works as follows:
 *
 *  1. Obta n t  background model d str but on of number of follo rs for a top c
 *
 *  2. Obta n t  doma n model d str but on of t  number of producer's follo rs who follow a top c
 *
 *  4.  erat vely, use t  Expectat on-Max m zat on approach to get t  best est mate of t  doma n model's top c d str but on for a producer
 *
 *  5. for each producer,   only keep  s top K top cs w h h g st   ghts  n t  doma n model's top c d str but on after t  EM step
 *
 *  6. Please note that   also store t  locale  nfo for each producer along w h t  top cs
 */
/**
scald ng remote run --user cassowary --reducers 2000 \
 --target src/scala/com/tw ter/s mclusters_v2/scald ng/top c_recom ndat ons:top_top cs_for_producers_from_em-adhoc \
 --ma n-class com.tw ter.s mclusters_v2.scald ng.top c_recom ndat ons.Top csForProducersFromEMAdhocApp \
 --subm ter  hadoopnest1.atla.tw ter.com  \
 --  --date 2020-07-05 --m nAct veFollo rs 10000 --m nTop cFollowsThreshold 100 --maxTop csPerProducerPerLocale 50 \
 --output_d r_top cs_per_producer /user/cassowary/adhoc/y _ldap/ttf_prof le_pages_producers_to_top cs
 */
object Top csForProducersFromEMAdhocApp extends AdhocExecut onApp {

  overr de def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): Execut on[Un ] = {
     mport Top csForProducersFromEM._
    val outputD rTop csPerProducer = args("output_d r_top cs_per_producer")
    val m nAct veFollo rsForProducer = args. nt("m nAct veFollo rs", 100)
    val m nTop cFollowsThreshold = args. nt("m nNumTop cFollows", 100)
    val maxTop csPerProducerPerLocale = args. nt("maxTop csPerProducer", 100)
    val lambda = args.double("lambda", 0.95)

    val numEMSteps = args. nt("numEM", 100)

    val top csFollo dByProducersFollo rs: TypedP pe[
      (Producer d, (Top c d, Opt on[Language], Opt on[Country]), Double)
    ] = getTopLocaleTop csForProducersFromEM(
      DataS ces
        .userUserNormal zedGraphS ce(dateRange.prepend(Days(7))),
      ExternalDataS ces.top cFollowGraphS ce,
      ExternalDataS ces.userS ce,
      ExternalDataS ces. nferredUserConsu dLanguageS ce,
      m nAct veFollo rsForProducer,
      m nTop cFollowsThreshold,
      lambda,
      numEMSteps
    )

    val topTop csPerLocaleProducerTsvExec = sortAndGetTopLocaleTop csPerProducer(
      top csFollo dByProducersFollo rs,
      maxTop csPerProducerPerLocale
    ).wr eExecut on(
      TypedTsv(outputD rTop csPerProducer)
    )

    topTop csPerLocaleProducerTsvExec
  }
}

/**
capesospy-v2 update --bu ld_locally \
 --start_cron top_top cs_for_producers_from_em \
 src/scala/com/tw ter/s mclusters_v2/capesos_conf g/atla_proc3.yaml
 */
object Top csForProducersFromEMBatchApp extends Sc duledExecut onApp {
  overr de val f rstT  : R chDate = R chDate("2020-07-26")

  overr de val batch ncre nt: Durat on = Days(7)

  pr vate val topTop csPerProducerFromEMPath: Str ng =
    "/user/cassowary/manhattan_sequence_f les/top_top cs_for_producers_from_em"

  overr de def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): Execut on[Un ] = {
     mport Top csForProducersFromEM._

    // threshold of t  m n mum number of act ve follo rs needed for a user to be cons dered as a producer
    val m nAct veFollo rsForProducer = args. nt("m nAct veFollo rs", 100)

    // threshold of t  top c locale follows score needed for a top c to be cons dered as val d
    val m nTop cFollowsThreshold = args. nt("m nNumTop cFollows", 100)

    val maxTop csPerProducer = args. nt("maxTop csPerProducer", 100)

    // lambda para ter for t  EM algor hm
    val lambda = args.double("lambda", 0.95)

    // number of EM  erat ons
    val numEMSteps = args. nt("numEM", 100)

    // (producer, locale) -> L st<(top cs, scores)> from Expectat on Max m zat on approach
    val top csFollo dByProducersFollo rs = getTopLocaleTop csForProducersFromEM(
      DataS ces
        .userUserNormal zedGraphS ce(dateRange.prepend(Days(7))),
      ExternalDataS ces.top cFollowGraphS ce,
      ExternalDataS ces.userS ce,
      ExternalDataS ces. nferredUserConsu dLanguageS ce,
      m nAct veFollo rsForProducer,
      m nTop cFollowsThreshold,
      lambda,
      numEMSteps
    )

    val topLocaleTop csForProducersFromEMKeyValExec =
      sortAndGetTopLocaleTop csPerProducer(
        top csFollo dByProducersFollo rs,
        maxTop csPerProducer
      ).map {
          case ((producer d, languageOpt, countryOpt), top csW hScores) =>
            KeyVal(
              User dW hLocale(
                user d = producer d,
                locale = Locale(language = languageOpt, country = countryOpt)),
              Semant cCoreEnt yScoreL st(top csW hScores.map {
                case (top c d, top cScore) =>
                  Semant cEnt yScore(Semant cCoreEnt y(ent y d = top c d), score = top cScore)
              })
            )
        }.wr eDALVers onedKeyValExecut on(
          TopLocaleTop csForProducerFromEmScalaDataset,
          D.Suff x(topTop csPerProducerFromEMPath),
          vers on = Expl c EndT  (dateRange.end)
        )
    topLocaleTop csForProducersFromEMKeyValExec
  }
}

object Top csForProducersFromEM {

  pr vate val M nProducerTop cScoreThreshold = 0.0

   mpl c  val sparseMatr x nj:  nject on[
    (Semant cCoreEnt y d, Opt on[Language], Opt on[Country]),
    Array[Byte]
  ] =
    Bufferable. nject onOf[(Semant cCoreEnt y d, Opt on[Language], Opt on[Country])]

  // T  funct on takes t  producer to top cs map and generates t  sorted and
  // truncated top locale top cs ranked l st for each producer
  def sortAndGetTopLocaleTop csPerProducer(
    producerToTop cs: TypedP pe[(Producer d, (Top c d, Opt on[Language], Opt on[Country]), Double)],
    maxTop csPerProducerPerLocale:  nt
  )(
     mpl c  un que D: Un que D
  ): TypedP pe[((Producer d, Opt on[Language], Opt on[Country]), L st[(Top c d, Double)])] = {
    val numProducersW hLocales = Stat("num_producers_w h_locales")
    producerToTop cs
      .map {
        case (producer d, (top c d, languageOpt, countryOpt), score) =>
          ((producer d, languageOpt, countryOpt), Seq((top c d, score)))
      }.sumByKey.mapValues { top csL st: Seq[(Top c d, Double)] =>
        numProducersW hLocales. nc()
        top csL st
          .f lter(_._2 >= M nProducerTop cScoreThreshold).sortBy(-_._2).take(
            maxTop csPerProducerPerLocale).toL st
      }.toTypedP pe
  }

  def getTopLocaleTop csForProducersFromEM(
    userUserGraph: TypedP pe[UserAndNe ghbors],
    follo dTop csToUsers: TypedP pe[(Top c d, User d)],
    userS ce: TypedP pe[(User d, (Country, Language))],
    userLanguages: TypedP pe[(User d, Seq[(Language, Double)])],
    m nAct veFollo rsForProducer:  nt,
    m nTop cFollowsThreshold:  nt,
    lambda: Double,
    numEMSteps:  nt
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): TypedP pe[(Producer d, (Top c d, Opt on[Language], Opt on[Country]), Double)] = {

    // Obta n Producer To Users Matr x
    val producersToUsersMatr x: SparseMatr x[Producer d, User d, Double] =
      Top csForProducersUt ls.getProducersToFollo dByUsersSparseMatr x(
        userUserGraph,
        m nAct veFollo rsForProducer)

    // Obta n Users to Top csW hLocales Matr x
    val top cToUsersMatr x: SparseMatr x[
      (Top c d, Opt on[Language], Opt on[Country]),
      User d,
      Double
    ] = Top csForProducersUt ls.getFollo dTop csToUserSparseMatr x(
      follo dTop csToUsers,
      userS ce,
      userLanguages,
      m nTop cFollowsThreshold)

    // Doma n  nput probab l y d str but on  s t  Map(top cs->follo rs) per producer locale
    val doma n nputModel = producersToUsersMatr x
      .mult plySparseMatr x(top cToUsersMatr x.transpose).toTypedP pe.map {
        case (producer d, (top c d, languageOpt, countryOpt), dotProduct) =>
          ((producer d, languageOpt, countryOpt), Map(top c d -> dotProduct))
      }.sumByKey.toTypedP pe.map {
        case ((producer d, languageOpt, countryOpt), top csDoma n nputMap) =>
          ((languageOpt, countryOpt), (producer d, top csDoma n nputMap))
      }

    // BackgroundModel  s t  Map(top cs -> Expected value of t  number of users who follow t  top c)
    val backgroundModel = top cToUsersMatr x.rowL1Norms.map {
      case ((top c d, languageOpt, countryOpt), numFollo rsOfTop c) =>
        ((languageOpt, countryOpt), Map(top c d -> numFollo rsOfTop c))
    }.sumByKey

    val resultsFromEMForEachLocale = doma n nputModel.hashJo n(backgroundModel).flatMap {
      case (
            (languageOpt, countryOpt),
            ((producer d, doma n nputTop cFollo rsMap), backgroundModelTop cFollo rsMap)) =>
        val emScoredTop csForEachProducerPerLocale = EMRunner.est mateDoma nModel(
          doma n nputTop cFollo rsMap,
          backgroundModelTop cFollo rsMap,
          lambda,
          numEMSteps)

        emScoredTop csForEachProducerPerLocale.map {
          case (top c d, top cScore) =>
            (producer d, (top c d, languageOpt, countryOpt), top cScore)
        }
    }
    resultsFromEMForEachLocale
  }
}
