package com.tw ter.s mclusters_v2.scald ng.top c_recom ndat ons.model_based_top c_recom ndat ons

 mport com.tw ter.algeb rd.Mono d
 mport com.tw ter.b ject on. nject on
 mport com.tw ter.dal.cl ent.dataset.SnapshotDALDatasetBase
 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.ap ._
 mport com.tw ter.scald ng.TypedP pe
 mport com.tw ter.scald ng._
 mport com.tw ter.scald ng_ nternal.dalv2.DALWr e.D
 mport com.tw ter.scald ng_ nternal.dalv2.dataset.DALWr e._
 mport com.tw ter.s mclusters_v2.common.Country
 mport com.tw ter.s mclusters_v2.common.Language
 mport com.tw ter.s mclusters_v2.common.Top c d
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.wtf.scald ng.jobs.common.AdhocExecut onApp
 mport com.tw ter.wtf.scald ng.jobs.common.Sc duledExecut onApp
 mport java.ut l.T  Zone
 mport scala.ut l.Random
 mport com.tw ter.ml.ap .ut l.FDsl._
 mport com.tw ter.scald ng.s ce.Da lySuff xCsv
 mport com.tw ter.scald ng.s ce.Da lySuff xTypedTsv
 mport com.tw ter.s mclusters_v2.hdfs_s ces.FavTfgTop cEmbedd ngsScalaDataset
 mport com.tw ter.s mclusters_v2.scald ng.embedd ng.common.ExternalDataS ces
 mport com.tw ter.s mclusters_v2.thr ftscala.Embedd ngType

/**
 T  job  s to obta n t  tra n ng and test data for t  model-based approach to top c recom ndat ons:
 Approach:
 1. Read FavTfgTop cEmbedd ngsScalaDataset - to get top c s mclusters embedd ngs for t  follo d and not  nterested  n top cs
 2. Read S mclustersV2 nterested n20M145KUpdatedScalaDataset - to get user's  nterested n S mclusters embedd ngs
 3. Read Users ceScalaDataset - to get user's countryCode and language
 Use t  datasets above to get t  features for t  model and generate DataRecords.
 */

/*
To run:
scald ng remote run --target src/scala/com/tw ter/s mclusters_v2/scald ng/top c_recom ndat ons/model_based_top c_recom ndat ons:tra n ng_data_for_top c_recom ndat ons-adhoc \
--user cassowary \
--subm ter atla-aor-08-sr1 \
--ma n-class com.tw ter.s mclusters_v2.scald ng.top c_recom ndat ons.model_based_top c_recom ndat ons.UserTop cFeatureHydrat onAdhocApp \
--subm ter- mory 128192. gabyte --hadoop-propert es "mapreduce.map. mory.mb=8192 mapreduce.map.java.opts='-Xmx7618M' mapreduce.reduce. mory.mb=8192 mapreduce.reduce.java.opts='-Xmx7618M'" \
-- \
--date 2020-10-14 \
--outputD r "/user/cassowary/adhoc/y _ldap/user_top c_features_popular_clusters_f ltered_oct_16"
 */

object UserTop cFeatureHydrat onAdhocApp extends AdhocExecut onApp {

   mport UserTop cModell ngJobUt ls._

  overr de def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): Execut on[Un ] = {

    val outputD r = args("outputD r")
    val numDataRecordsTra n ng = Stat("num_data_records_tra n ng")
    val numDataRecordsTest ng = Stat("num_data_records_test ng")
    val test ngRat o = args.double("test ngRat o", 0.2)

    val (tra n ngDataSamples, testDataSamples, sortedVocab) = UserTop cModell ngJobUt ls.run(
      ExternalDataS ces.top cFollowGraphS ce,
      ExternalDataS ces.not nterestedTop csS ce,
      ExternalDataS ces.userS ce,
      DataS ces.getUser nterested nData,
      DataS ces.getPerLanguageTop cEmbedd ngs,
      test ngRat o
    )

    val userTop cAdapter = new UserTop cDataRecordAdapter()
    Execut on
      .z p(
        convertTypedP peToDataSetP pe(
          tra n ngDataSamples.map { tra n =>
            numDataRecordsTra n ng. nc()
            tra n
          },
          userTop cAdapter)
          .wr eExecut on(
            Da lySuff xFeatureS nk(outputD r + "/tra n ng")
          ),
        convertTypedP peToDataSetP pe(
          testDataSamples.map { test =>
            numDataRecordsTest ng. nc()
            test
          },
          userTop cAdapter)
          .wr eExecut on(
            Da lySuff xFeatureS nk(outputD r + "/test ng")
          ),
        sortedVocab
          .map { top csW hSorted ndexes =>
            top csW hSorted ndexes.map(_._1)
          }.flatten.wr eExecut on(Da lySuff xTypedTsv(outputD r + "/vocab"))
      ).un 
  }
}

/**
capesospy-v2 update --bu ld_locally \
 --start_cron tra n ng_data_for_top c_recom ndat ons \
 src/scala/com/tw ter/s mclusters_v2/capesos_conf g/atla_proc3.yaml
 */

object UserTop cFeatureHydrat onSc duledApp extends Sc duledExecut onApp {

   mport UserTop cModell ngJobUt ls._

  pr vate val outputPath: Str ng =
    "/user/cassowary/processed/user_top c_modell ng"

  overr de def batch ncre nt: Durat on = Days(1)

  overr de def f rstT  : R chDate = R chDate("2020-10-13")

  overr de def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): Execut on[Un ] = {
    val test ngRat o = args.double("test ngRat o", 0.2)

    val (tra n ngDataSamples, testDataSamples, sortedVocab) = UserTop cModell ngJobUt ls.run(
      ExternalDataS ces.top cFollowGraphS ce,
      ExternalDataS ces.not nterestedTop csS ce,
      ExternalDataS ces.userS ce,
      DataS ces.getUser nterested nData,
      DataS ces.getPerLanguageTop cEmbedd ngs,
      test ngRat o
    )

    val userTop cAdapter = new UserTop cDataRecordAdapter()
    Execut on
      .z p(
        getTra nTestExec(
          tra n ngDataSamples,
          testDataSamples,
          Top cRecom ndat onsTra nDatarecordsJavaDataset,
          Top cRecom ndat onsTestDatarecordsJavaDataset,
          outputPath,
          userTop cAdapter
        ),
        sortedVocab
          .map { top csW hSorted ndexes =>
            top csW hSorted ndexes.map(_._1)
          }.flatten.wr eExecut on(Da lySuff xTypedTsv(outputPath + "/vocab"))
      ).un 

  }
}

object UserTop cModell ngJobUt ls {

  /**
   * T  ma n funct on that produces tra n ng and t  test data
   *
   * @param top cFollowGraphS ce user w h follo d top cs from TFG
   * @param not nterestedTop csS ce  user w h not  nterested  n top cs
   * @param userS ce user w h country and language
   * @param user nterested nData user w h  nterested n s mcluster embedd ngs
   * @param top cPerLanguageEmbedd ngs top cs w h s mcluster embedd ngs
   *
   * @return Tuple (tra n ngDataSamples, test ngDataSamples, sortedTop csVocab)
   */
  def run(
    top cFollowGraphS ce: TypedP pe[(Top c d, User d)],
    not nterestedTop csS ce: TypedP pe[(Top c d, User d)],
    userS ce: TypedP pe[(User d, (Country, Language))],
    user nterested nData: TypedP pe[(User d, Map[ nt, Double])],
    top cPerLanguageEmbedd ngs: TypedP pe[((Top c d, Language), Map[ nt, Double])],
    test ngRat o: Double
  )(
     mpl c  un que D: Un que D,
    dateRange: DateRange,
    t  Zone: T  Zone
  ): (
    TypedP pe[UserTop cTra n ngSample],
    TypedP pe[UserTop cTra n ngSample],
    TypedP pe[Seq[(Top c d,  nt)]]
  ) = {
    val allFollowableTop cs: TypedP pe[Top c d] =
      top cFollowGraphS ce.map(_._1).d st nct

    val allFollowableTop csW hMapped ds: TypedP pe[(Top c d,  nt)] =
      allFollowableTop cs.groupAll.mapGroup {
        case (_, top c er) =>
          top c er.z pW h ndex.map {
            case (top c d, mapped d) =>
              (top c d, mapped d)
          }
      }.values

    val sortedVocab: TypedP pe[Seq[(Top c d,  nt)]] =
      allFollowableTop csW hMapped ds.map(Seq(_)).map(_.sortBy(_._2))

    val dataTra n ngSamples: TypedP pe[UserTop cTra n ngSample] = getDataSamplesFromTra n ngData(
      top cFollowGraphS ce,
      not nterestedTop csS ce,
      userS ce,
      user nterested nData,
      top cPerLanguageEmbedd ngs,
      allFollowableTop csW hMapped ds
    )
    val (tra nSpl , testSpl ) = spl ByUser(dataTra n ngSamples, test ngRat o)

    (tra nSpl , testSpl , sortedVocab)
  }

  /**
   * Spl  t  data samples based on user_ d  nto tra n and test data. T  ensures that t  sa 
   * user's data records are not part of both tra n and test data.
   */
  def spl ByUser(
    dataTra n ngSamples: TypedP pe[UserTop cTra n ngSample],
    test ngRat o: Double
  ): (TypedP pe[UserTop cTra n ngSample], TypedP pe[UserTop cTra n ngSample]) = {
    val (tra nSpl , testSpl ) = dataTra n ngSamples
      .map { currSmple => (currSmple.user d, currSmple) }.groupBy(_._1).part  on(_ =>
        Random.nextDouble() > test ngRat o)
    val tra n ngData = tra nSpl .values.map(_._2)
    val test ngData = testSpl .values.map(_._2)
    (tra n ngData, test ngData)
  }

  /**
   * To get t  target top c for each tra n ng data sample for a user from t  Top cFollowGraph
   *
   * @param top cFollowS ce
   * @return (User d, Set(allFollo dTop csExceptTargetTop c), targetTop c)
   */
  def getTargetTop csFromTFG(
    top cFollowS ce: TypedP pe[(Top c d, User d)]
  )(
     mpl c  un que D: Un que D
  ): TypedP pe[(User d, Set[Top c d], Top c d)] = {
    val numTra n ngSamples = Stat("num_pos  ve_tra n ng_samples")

    val userFollo dTop cs = top cFollowS ce.swap
      .map {
        case (user d, top c d) => (user d, Set(top c d))
      }.sumByKey.toTypedP pe

    userFollo dTop cs.flatMap {
      case (user D, follo dTop csSet) =>
        follo dTop csSet.map { currFollo dTop c =>
          numTra n ngSamples. nc()
          val rema n ngTop cs = follo dTop csSet - currFollo dTop c
          (user D, rema n ngTop cs, currFollo dTop c)
        }
    }
  }

  /**
   *  lper funct on that does t   nter d ate jo n operat on bet en a user's follo d,
   * not- nterested,  nterested n, country and language typedp pe s ces, read from d fferent s ces.
   */

  def getFeatures nter d ateJo n(
    top cFollowGraphS ce: TypedP pe[(Top c d, User d)],
    not nterestedTop csS ce: TypedP pe[(Top c d, User d)],
    allFollowableTop csW hMapped ds: TypedP pe[(Top c d,  nt)],
    userCountryAndLanguage: TypedP pe[(User d, (Country, Language))],
    user nterested nData: TypedP pe[(User d, Map[ nt, Double])]
  )(
     mpl c  un que D: Un que D
  ): TypedP pe[
    (
      User d,
      Set[Top c d],
      Set[Top c d],
      Top c d,
       nt,
      Country,
      Language,
      Map[ nt, Double]
    )
  ] = {
     mpl c  val l2b: Long => Array[Byte] =  nject on.long2B gEnd an

    val userW hFollo dTargetTop cs: TypedP pe[
      (User d, Set[Top c d], Top c d)
    ] = getTargetTop csFromTFG(top cFollowGraphS ce)

    val userW hNot nterestedTop cs: TypedP pe[(User d, Set[Top c d])] =
      not nterestedTop csS ce.swap.mapValues(Set(_)).sumByKey.toTypedP pe

    userW hFollo dTargetTop cs
      .groupBy(_._1).leftJo n(userW hNot nterestedTop cs).values.map {
        case ((user d, follo dTop cs, targetFollo dTop c), not nterestedOpt) =>
          (
            user d,
            follo dTop cs,
            targetFollo dTop c,
            not nterestedOpt.getOrElse(Set.empty[Top c d]))
      }
      .map {
        case (user d, follo dTop cs, targetFollo dTop c, not nterestedTop cs) =>
          (targetFollo dTop c, (user d, follo dTop cs, not nterestedTop cs))
      }.jo n(allFollowableTop csW hMapped ds).map {
        case (targetTop c, ((user d, follo dTop cs, not nterestedTop cs), targetTop c dx)) =>
          (user d, follo dTop cs, not nterestedTop cs, targetTop c, targetTop c dx)
      }
      .groupBy(_._1).sketch(4000)
      .jo n(userCountryAndLanguage
        .groupBy(_._1)).sketch(4000).leftJo n(user nterested nData)
      .values.map {
        case (
              (
                (user d, follo dTop cs, not nterestedTop cs, targetTop c, targetTop c dx),
                (_, (userCountry, userLanguage))
              ),
              user ntOpt) =>
          (
            user d,
            follo dTop cs,
            not nterestedTop cs,
            targetTop c,
            targetTop c dx,
            userCountry,
            userLanguage,
            user ntOpt.getOrElse(Map.empty))
      }
  }

  /**
   *  lper funct on that aggregates user's follo d top cs, not- nterested top cs,
   * country, language w h jo n operat ons and generates t  UserTop cTra n ngSample
   * for each DataRecord
   */
  def getDataSamplesFromTra n ngData(
    top cFollowGraphS ce: TypedP pe[(Top c d, User d)],
    not nterestedTop csS ce: TypedP pe[(Top c d, User d)],
    userCountryAndLanguage: TypedP pe[(User d, (Country, Language))],
    user nterested nData: TypedP pe[(User d, Map[ nt, Double])],
    top cPerLanguageEmbedd ngs: TypedP pe[((Top c d, Language), Map[ nt, Double])],
    allFollowableTop csW hMapped ds: TypedP pe[(Top c d,  nt)]
  )(
     mpl c  un que D: Un que D
  ): TypedP pe[UserTop cTra n ngSample] = {

     mpl c  val l2b: Long => Array[Byte] =  nject on.long2B gEnd an

    val allTop cEmbedd ngsMap: ValueP pe[Map[(Top c d, Language), Map[ nt, Double]]] =
      top cPerLanguageEmbedd ngs.map {
        case (top cW hLang, embedd ng) =>
          Map(top cW hLang -> embedd ng)
      }.sum

    val userW hFollo dAndNot nterestedTop cs = getFeatures nter d ateJo n(
      top cFollowGraphS ce,
      not nterestedTop csS ce,
      allFollowableTop csW hMapped ds,
      userCountryAndLanguage,
      user nterested nData)

    userW hFollo dAndNot nterestedTop cs.flatMapW hValue(allTop cEmbedd ngsMap) {
      case (
            (
              user d,
              follo dTop cs,
              not nterestedTop cs,
              targetTop c,
              targetTop c dx,
              userCountry,
              userLanguage,
              user nt),
            So (allTop cEmbedd ngs)) =>
        val averageFollo dTop csS mClusters = Mono d
          .sum(follo dTop cs.toSeq.map { top c d =>
            allTop cEmbedd ngs.getOrElse((top c d, userLanguage), Map.empty)
          }).mapValues(v =>
            v / follo dTop cs.s ze) // average s mcluster embedd ng of t  follo d top cs

        val averageNot nterestedTop csS mClusters = Mono d
          .sum(not nterestedTop cs.toSeq.map { top c d =>
            allTop cEmbedd ngs.getOrElse((top c d, userLanguage), Map.empty)
          }).mapValues(v =>
            v / not nterestedTop cs.s ze) // average s mcluster embedd ng of t  not nterested top cs

        So (
          UserTop cTra n ngSample(
            user d,
            follo dTop cs,
            not nterestedTop cs,
            userCountry,
            userLanguage,
            targetTop c dx,
            user nt,
            averageFollo dTop csS mClusters,
            averageNot nterestedTop csS mClusters
          )
        )

      case _ =>
        None
    }
  }

  /**
   * Wr e tra n and test data
   */
  def getTra nTestExec(
    tra n ngData: TypedP pe[UserTop cTra n ngSample],
    test ngData: TypedP pe[UserTop cTra n ngSample],
    tra nDataset: SnapshotDALDatasetBase[DataRecord],
    testDataset: SnapshotDALDatasetBase[DataRecord],
    outputPath: Str ng,
    adapter:  RecordOneToOneAdapter[UserTop cTra n ngSample]
  )(
     mpl c  dateRange: DateRange
  ): Execut on[Un ] = {
    val tra nExec =
      convertTypedP peToDataSetP pe(tra n ngData, adapter)
        .wr eDALSnapshotExecut on(
          tra nDataset,
          D.Da ly,
          D.Suff x(s"$outputPath/tra n ng"),
          D.EBLzo(),
          dateRange.end)
    val testExec =
      convertTypedP peToDataSetP pe(test ngData, adapter)
        .wr eDALSnapshotExecut on(
          testDataset,
          D.Da ly,
          D.Suff x(s"$outputPath/test ng"),
          D.EBLzo(),
          dateRange.end)
    Execut on.z p(tra nExec, testExec).un 
  }

  /**
   * To get t  datasetP pe conta n ng datarecords hydrated by datarecordAdapter
   * @param userTra n ngSamples
   * @param adapter
   * @return DataSetP pe
   */
  def convertTypedP peToDataSetP pe(
    userTra n ngSamples: TypedP pe[UserTop cTra n ngSample],
    adapter:  RecordOneToOneAdapter[UserTop cTra n ngSample]
  ): DataSetP pe = {
    userTra n ngSamples.toDataSetP pe(adapter)
  }
}
