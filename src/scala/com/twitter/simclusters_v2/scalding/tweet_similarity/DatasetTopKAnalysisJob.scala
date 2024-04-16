package com.tw ter.s mclusters_v2.scald ng.t et_s m lar y

 mport com.tw ter.ml.ap .Da lySuff xFeatureS ce
 mport com.tw ter.ml.ap .DataSetP pe
 mport com.tw ter.ml.ap .R chDataRecord
 mport com.tw ter.scald ng.typed.TypedP pe
 mport com.tw ter.scald ng.Execut on
 mport com.tw ter.scald ng._
 mport com.tw ter.scald ng_ nternal.job.Tw terExecut onApp
 mport com.tw ter.s mclusters_v2.common.T et d
 mport com.tw ter.s mclusters_v2.scald ng.common.Ut l
 mport com.tw ter.s mclusters_v2.t et_s m lar y.T etS m lar yFeatures
 mport java.ut l.T  Zone

object DatasetTopKAnalys sJob {

  case class T etPa rW hStats(
    queryT et: T et d,
    cand dateT et: T et d,
    cooccurrenceCount: Double,
    coengage ntCount: Double,
    coengage ntRate: Double)

  def getCoocurrenceT etPa rs(dataset: DataSetP pe): TypedP pe[T etPa rW hStats] = {
    val featureContext = dataset.featureContext

    dataset.records
      .map { record =>
        val r chDataRecord = new R chDataRecord(record, featureContext)
        val coengaged =
           f (r chDataRecord
              .getFeatureValue(T etS m lar yFeatures.Label)
              .booleanValue) 1
          else 0
        (
          (
            r chDataRecord.getFeatureValue(T etS m lar yFeatures.QueryT et d).toLong,
            r chDataRecord.getFeatureValue(T etS m lar yFeatures.Cand dateT et d).toLong),
          (1, coengaged)
        )
      }.sumByKey
      .map {
        case ((queryT et, cand dateT et), (coocurrenceCount, coengage ntCount)) =>
          T etPa rW hStats(
            queryT et,
            cand dateT et,
            coocurrenceCount.toDouble,
            coengage ntCount.toDouble,
            coengage ntCount.toDouble / coocurrenceCount.toDouble
          )
      }
  }

  def getQueryT etToCounts(dataset: DataSetP pe): TypedP pe[(Long, ( nt,  nt))] = {
    val featureContext = dataset.featureContext
    dataset.records.map { record =>
      val r chDataRecord = new R chDataRecord(record, featureContext)
      val coengaged =
         f (r chDataRecord
            .getFeatureValue(T etS m lar yFeatures.Label)
            .booleanValue) 1
        else 0
      (
        r chDataRecord.getFeatureValue(T etS m lar yFeatures.QueryT et d).toLong,
        (1, coengaged)
      )
    }.sumByKey
  }

  def pr ntGlobalTopKT etPa rsBy(
    t etPa rs: TypedP pe[T etPa rW hStats],
    k:  nt,
    orderByFnt: T etPa rW hStats => Double
  ): Execut on[Un ] = {
    val topKT etPa rs =
      t etPa rs.groupAll
        .sortedReverseTake(k)(Order ng.by(orderByFnt))
        .values
    topKT etPa rs.to erableExecut on.map { s =>
      pr ntln(s.map(Ut l.prettyJsonMapper.wr eValueAsStr ng).mkStr ng("\n"))
    }
  }

  def pr ntT etTopKT etsBy(
    groupedBy: Grouped[T et d, T etPa rW hStats],
    k:  nt,
    orderByFnt: T etPa rW hStats => Double,
    descend ng: Boolean = true
  ): Execut on[Un ] = {
     f (descend ng) {
      pr ntln("T etTopKT ets (descend ng order)")
      groupedBy
        .sortedReverseTake(k)(Order ng.by(orderByFnt))
        .to erableExecut on
        .map { record => pr ntln(record.toStr ng()) }
    } else {
      pr ntln("T etTopKT ets (ascend ng order)")
      groupedBy
        .sortedTake(k)(Order ng.by(orderByFnt))
        .to erableExecut on
        .map { record => pr ntln(record.toStr ng()) }
    }
  }

  def pr ntT etPa rStatsExec(
    t etPa rs: TypedP pe[T etPa rW hStats],
    k:  nt
  ): Execut on[Un ] = {
    Execut on
      .sequence(
        Seq(
          Ut l.pr ntSummaryOfNu r cColumn(
            t etPa rs.map(_.cooccurrenceCount),
            So ("T et-pa r Coocurrence Count")),
          pr ntGlobalTopKT etPa rsBy(
            t etPa rs,
            k,
            { t etPa rs => t etPa rs.cooccurrenceCount }),
          Ut l.pr ntSummaryOfNu r cColumn(
            t etPa rs.map(_.coengage ntCount),
            So ("T et-pa r Coengage nt Count")),
          pr ntGlobalTopKT etPa rsBy(
            t etPa rs,
            k,
            { t etPa rs => t etPa rs.coengage ntCount }),
          Ut l.pr ntSummaryOfNu r cColumn(
            t etPa rs.map(_.coengage ntRate),
            So ("T et-pa r Coengage nt Rate")),
          pr ntGlobalTopKT etPa rsBy(t etPa rs, k, { t etPa rs => t etPa rs.coengage ntRate })
        )
      ).un 
  }

  def pr ntPerQueryStatsExec(dataset: DataSetP pe, k:  nt): Execut on[Un ] = {
    val queryToCounts = getQueryT etToCounts(dataset)

    val topKQueryT etsByOccurrence =
      queryToCounts.groupAll
        .sortedReverseTake(k)(Order ng.by { case (_, (cooccurrenceCount, _)) => cooccurrenceCount })
        .values

    val topKQueryT etsByEngage nt =
      queryToCounts.groupAll
        .sortedReverseTake(k)(Order ng.by { case (_, (_, coengage ntCount)) => coengage ntCount })
        .values

    Execut on
      .sequence(
        Seq(
          Ut l.pr ntSummaryOfNu r cColumn(
            queryToCounts.map(_._2._1),
            So ("Per-query Total Cooccurrence Count")),
          topKQueryT etsByOccurrence.to erableExecut on.map { s =>
            pr ntln(s.map(Ut l.prettyJsonMapper.wr eValueAsStr ng).mkStr ng("\n"))
          },
          Ut l.pr ntSummaryOfNu r cColumn(
            queryToCounts.map(_._2._2),
            So ("Per-query Total Coengage nt Count")),
          topKQueryT etsByEngage nt.to erableExecut on.map { s =>
            pr ntln(s.map(Ut l.prettyJsonMapper.wr eValueAsStr ng).mkStr ng("\n"))
          }
        )
      ).un 
  }

  def runT etTopKT etsOutputExecs(
    t etPa rs: TypedP pe[T etPa rW hStats],
    k:  nt,
    outputPath: Str ng
  ): Execut on[Un ] = {
    t etPa rs
      .groupBy(_.queryT et)
      .sortedReverseTake(k)(Order ng.by(_.coengage ntRate))
      .wr eExecut on(TypedTsv(outputPath + "/topK_by_coengage nt_rate"))
  }
}

/** To run:
  scald ng remote run --target src/scala/com/tw ter/s mclusters_v2/scald ng/t et_s m lar y:dataset_topk_analys s-adhoc \
  --user cassowary \
  --subm ter hadoopnest2.atla.tw ter.com \
  --ma n-class com.tw ter.s mclusters_v2.scald ng.t et_s m lar y.DatasetTopKAnalys sAdhocApp -- \
  --date 2020-02-19 \
  --dataset_path /user/cassowary/adhoc/tra n ng_data/2020-02-19_class_balanced/tra n \
  --output_path /user/cassowary/adhoc/tra n ng_data/2020-02-19_class_balanced/tra n/analys s
 * */
object DatasetTopKAnalys sAdhocApp extends Tw terExecut onApp {
   mpl c  val t  Zone: T  Zone = DateOps.UTC
   mpl c  val dateParser: DateParser = DateParser.default

  def job: Execut on[Un ] = Execut on.w h d {  mpl c  un que d =>
    Execut on.w hArgs { args: Args =>
       mpl c  val dateRange: DateRange = DateRange.parse(args.l st("date"))
      val dataset: DataSetP pe = Da lySuff xFeatureS ce(args("dataset_path")).read
      val outputPath: Str ng = args("output_path")
      val topK:  nt = args. nt("top_K", default = 10)

      val t etPa rs = DatasetTopKAnalys sJob.getCoocurrenceT etPa rs(dataset)

      Execut on
        .z p(
          DatasetTopKAnalys sJob.pr ntT etPa rStatsExec(t etPa rs, topK),
          DatasetTopKAnalys sJob.runT etTopKT etsOutputExecs(t etPa rs, topK, outputPath),
          DatasetTopKAnalys sJob.pr ntPerQueryStatsExec(dataset, topK)
        ).un 
    }
  }
}

/** To run:
  scald ng remote run --target src/scala/com/tw ter/s mclusters_v2/scald ng/t et_s m lar y:dataset_topk_analys s-dump \
  --user cassowary \
  --subm ter hadoopnest2.atla.tw ter.com \
  --ma n-class com.tw ter.s mclusters_v2.scald ng.t et_s m lar y.DatasetTopKAnalys sDumpApp -- \
  --date 2020-02-01 \
  --dataset_path /user/cassowary/adhoc/tra n ng_data/2020-02-01/tra n \
  --t ets 1223105606757695490 \
  --top_K 100
 * */
object DatasetTopKAnalys sDumpApp extends Tw terExecut onApp {
   mpl c  val t  Zone: T  Zone = DateOps.UTC
   mpl c  val dateParser: DateParser = DateParser.default

  def job: Execut on[Un ] = Execut on.w h d {  mpl c  un que d =>
    Execut on.w hArgs { args: Args =>
       mpl c  val dateRange: DateRange = DateRange.parse(args.l st("date"))
      val dataset: DataSetP pe = Da lySuff xFeatureS ce(args("dataset_path")).read
      val t ets = args.l st("t ets").map(_.toLong).toSet
      val topK:  nt = args. nt("top_K", default = 100)

      val t etPa rs = DatasetTopKAnalys sJob.getCoocurrenceT etPa rs(dataset)

       f (t ets. sEmpty) {
        Execut on.from(pr ntln("Empty query t ets"))
      } else {
        val f lteredGroupby = t etPa rs
          .f lter { record => t ets.conta ns(record.queryT et) }
          .groupBy(_.queryT et)

        Execut on
          .z p(
            //Top K
            DatasetTopKAnalys sJob
              .pr ntT etTopKT etsBy(f lteredGroupby, topK, pa r => pa r.coengage ntCount),
            //Bottom K
            DatasetTopKAnalys sJob.pr ntT etTopKT etsBy(
              f lteredGroupby,
              topK,
              pa r => pa r.coengage ntCount,
              descend ng = false)
          ).un 
      }
    }
  }
}
