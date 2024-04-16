package com.tw ter.s mclusters_v2.scald ng.evaluat on

 mport com.tw ter.ml.ap .constant.SharedFeatures.AUTHOR_ D
 mport com.tw ter.ml.ap .constant.SharedFeatures.T MESTAMP
 mport com.tw ter.ml.ap .constant.SharedFeatures.TWEET_ D
 mport com.tw ter.ml.ap .constant.SharedFeatures.USER_ D
 mport com.tw ter.ml.ap .Da lySuff xFeatureS ce
 mport com.tw ter.ml.ap .DataSetP pe
 mport com.tw ter.ml.ap .R chDataRecord
 mport com.tw ter.scald ng._
 mport com.tw ter.scald ng_ nternal.dalv2.DALWr e._
 mport com.tw ter.scald ng_ nternal.job.Tw terExecut onApp
 mport com.tw ter.scald ng_ nternal.job.analyt cs_batch.Analyt csBatchExecut on
 mport com.tw ter.scald ng_ nternal.job.analyt cs_batch.Analyt csBatchExecut onArgs
 mport com.tw ter.scald ng_ nternal.job.analyt cs_batch.BatchDescr pt on
 mport com.tw ter.scald ng_ nternal.job.analyt cs_batch.BatchF rstT  
 mport com.tw ter.scald ng_ nternal.job.analyt cs_batch.Batch ncre nt
 mport com.tw ter.scald ng_ nternal.job.analyt cs_batch.Tw terSc duledExecut onApp
 mport com.tw ter.s mclusters_v2.hdfs_s ces.T  l neDataExtractorF xedPathS ce
 mport com.tw ter.s mclusters_v2.hdfs_s ces._
 mport com.tw ter.s mclusters_v2.thr ftscala.D splayLocat on
 mport com.tw ter.s mclusters_v2.thr ftscala.ReferenceT et
 mport com.tw ter.s mclusters_v2.thr ftscala.ReferenceT ets
 mport com.tw ter.s mclusters_v2.thr ftscala.T etLabels
 mport com.tw ter.t  l nes.pred ct on.features.common.T  l nesSharedFeatures. S_L NGER_ MPRESS ON
 mport com.tw ter.t  l nes.pred ct on.features.common.T  l nesSharedFeatures.SOURCE_AUTHOR_ D
 mport com.tw ter.t  l nes.pred ct on.features.common.T  l nesSharedFeatures.SOURCE_TWEET_ D
 mport com.tw ter.t  l nes.pred ct on.features. l. TLFeatures
 mport com.tw ter.t  l nes.pred ct on.features.recap.RecapFeatures
 mport java.ut l.T  Zone

/**
 * A sc duled vers on of t  job to parse T  l nes data for  mpressed and engaged t ets.
 capesospy-v2 update|create --start_cron t et_evaluat on_t  l nes_reference_batch src/scala/com/tw ter/s mclusters_v2/capesos_conf g/atla_proc.yaml
 */
object Sc duledT  l nesDataExtract onBatch extends Tw terSc duledExecut onApp {

  val outputPath = "/user/cassowary/processed/t et_evaluat on_reference_set/t  l nes"

  pr vate val f rstT  : Str ng = "2019-03-31"
  pr vate  mpl c  val tz: T  Zone = DateOps.UTC
  pr vate  mpl c  val parser: DateParser = DateParser.default
  pr vate val batch ncre nt: Durat on = Days(1)

  pr vate val execArgs = Analyt csBatchExecut onArgs(
    batchDesc = BatchDescr pt on(t .getClass.getNa .replace("$", "")),
    f rstT   = BatchF rstT  (R chDate(f rstT  )),
    lastT   = None,
    batch ncre nt = Batch ncre nt(batch ncre nt)
  )

  overr de def sc duledJob: Execut on[Un ] = Analyt csBatchExecut on(execArgs) {
     mpl c  dateRange =>
      Execut on.w h d {  mpl c  un que d =>
        Execut on.w hArgs { args =>
          val defaultSampleRate = 1.0
          val recaps =
            T  l nesEngage ntDataExtractor.readT  l nesRecapT ets(
              recapT ets =
                Da lySuff xFeatureS ce(T  l nesEngage ntDataExtractor.RecapT etHdfsPath).read,
              sampleRate = defaultSampleRate
            )(dateRange)
          val recT ets =
            T  l nesEngage ntDataExtractor.readT  l nesRecT ets(
              recT ets =
                Da lySuff xFeatureS ce(T  l nesEngage ntDataExtractor.RecT etHdfsPath).read,
              sampleRate = defaultSampleRate
            )(dateRange)

          (recaps ++ recT ets).wr eDALSnapshotExecut on(
            T etEvaluat onT  l nesReferenceSetScalaDataset,
            D.Da ly,
            D.Suff x(outputPath),
            D.EBLzo(),
            dateRange.end
          )
        }
      }
  }
}

/**
 * Ad-hoc vers on of t  job to process a subset of t  T  l ne data, e  r to catch up w h data
 * on a part cular day, or to generate human readable data for debugg ng.
 ./bazel bundle src/scala/com/tw ter/s mclusters_v2/scald ng/evaluat on:t et_evaluat on_t  l nes_reference_adhoc

 oscar hdfs --screen --user cassowary --bundle t et_evaluat on_t  l nes_reference_adhoc \
 --tool com.tw ter.s mclusters_v2.scald ng.evaluat on.AdhocT  l nesDataExtract on \
 -- --date 2018-11-15 --output_d r /user/cassowary/y _ldap/test_htl_data/recap --sample_rate 0.01 \
 --recap --rect et --output_tsv
 */
object AdhocT  l nesDataExtract on extends Tw terExecut onApp {

  @Overr de
  def job: Execut on[Un ] = {
    Execut on.w hArgs { args =>
       mpl c  val dateRange: DateRange =
        DateRange.parse(args.l st("date"))(DateOps.UTC, DateParser.default)

      val outputD r = args("output_d r")
      val readRecT et = args.boolean("rect et")
      val readRecap = args.boolean("recap")
      val sampleRate = args.double("sample_rate")
      val useTsv = args.boolean("output_tsv")

       f (!readRecT et && !readRecap) {
        throw new  llegalArgu ntExcept on("Must read at least so  data!")
      }
      val recT ets =  f (readRecT et) {
        pr ntln("RecT ets are  ncluded  n t  dataset")
        T  l nesEngage ntDataExtractor.readT  l nesRecT ets(
          recT ets =
            Da lySuff xFeatureS ce(T  l nesEngage ntDataExtractor.RecT etHdfsPath).read,
          sampleRate = sampleRate)(dateRange)
      } else {
        TypedP pe.empty
      }

      val recaps =  f (readRecap) {
        pr ntln("Recaps are  ncluded  n t  dataset")
        T  l nesEngage ntDataExtractor.readT  l nesRecapT ets(
          recapT ets =
            Da lySuff xFeatureS ce(T  l nesEngage ntDataExtractor.RecapT etHdfsPath).read,
          sampleRate = sampleRate
        )(dateRange)
      } else {
        TypedP pe.empty
      }

      val referenceT ets = recaps ++ recT ets

       f (useTsv) {
        // Wr e  n pla n text  n tsv format for human readab l y
        referenceT ets
          .map(t => (t.targetUser d, t. mpressedT ets))
          .wr eExecut on(TypedTsv[(Long, Seq[ReferenceT et])](outputD r))
      } else {
        // Wr e  n compact thr ft lzo format
        referenceT ets
          .wr eExecut on(T  l neDataExtractorF xedPathS ce(outputD r))
      }
    }
  }
}

/**
 * Base class to prov de funct ons to parse t et engage nt data from Ho  T  l ne's data.
 *   are ma nly  nterested  n 2 t et data sets from Ho  T  l ne:
 * 1. Recap t et: T ets + RTs from user's follow graph.   are  nterested  n out of network RTs.
 * 2. RecT et: Out of network t ets not from user's follow graph.
 */
object T  l nesEngage ntDataExtractor {

  val RecapT etHdfsPath = "/atla/proc2/user/t  l nes/processed/suggests/recap/data_records"
  val RecT etHdfsPath = "/atla/proc2/user/t  l nes/processed/ nject ons/rect et/data_records"

  // T  l nes na  t  sa  feature d fferently depend ng on t  surface area (ex. recap vs rect et).
  // For each data s ce   extract t  features w h d fferent feature na s. Deta l:
  def toRecapT etLabels(record: R chDataRecord): T etLabels = {
    val  sCl cked = record.getFeatureValue(RecapFeatures. S_CL CKED)
    val  sFav = record.getFeatureValue(RecapFeatures. S_FAVOR TED)
    val  sRT = record.getFeatureValue(RecapFeatures. S_RETWEETED)
    val  sQuoted = record.getFeatureValue(RecapFeatures. S_QUOTED)
    val  sRepl ed = record.getFeatureValue(RecapFeatures. S_REPL ED)
    T etLabels( sCl cked,  sFav,  sRT,  sQuoted,  sRepl ed)
  }

  def toRecT etLabels(record: R chDataRecord): T etLabels = {
    // Refer to  TLFeatures for more labels
    val  sCl cked = record.getFeatureValue( TLFeatures. S_CL CKED)
    val  sFav = record.getFeatureValue( TLFeatures. S_FAVOR TED)
    val  sRT = record.getFeatureValue( TLFeatures. S_RETWEETED)
    val  sQuoted = record.getFeatureValue( TLFeatures. S_QUOTED)
    val  sRepl ed = record.getFeatureValue( TLFeatures. S_REPL ED)
    T etLabels( sCl cked,  sFav,  sRT,  sQuoted,  sRepl ed)
  }

  /**
   * Return Recap t ets, wh ch are  n-network t ets.  re   only f lter for Ret ets of t ets
   * that are outs de t  user's follow graph.
   */
  def readT  l nesRecapT ets(
    recapT ets: DataSetP pe,
    sampleRate: Double
  )(
     mpl c  dateRange: DateRange
  ): TypedP pe[ReferenceT ets] = {
    // recapT ets are  n network t ets.   want to d scover RTs of OON t ets.
    // For Ret ets,   c ck  S_RETWEET and use SOURCE_TWEET_ D, and t n c ck
    // PROBABLY_FROM_FOLLOWED_AUTHOR, wh ch f lters  n network t et from user's top 1000 follow graph.

    recapT ets.r chRecords
      .sample(sampleRate)
      .f lter { record =>
        val  s nDateRange = dateRange.conta ns(R chDate(record.getFeatureValue(T MESTAMP).toLong))
        val  sL ngered mpress on = record.getFeatureValue( S_L NGER_ MPRESS ON)
        val  s nNetwork =
          record.getFeatureValue(RecapFeatures.PROBABLY_FROM_FOLLOWED_AUTHOR) // approx mate
        val  sRet et = record.getFeatureValue(RecapFeatures. S_RETWEET)
         sRet et && (! s nNetwork) &&  s nDateRange &&  sL ngered mpress on
      }
      .flatMap { record =>
        for {
          user d <- Opt on(record.getFeatureValue(USER_ D)).map(_.toLong)
          s ceT et d <- Opt on(record.getFeatureValue(SOURCE_TWEET_ D)).map(
            _.toLong
          ) // s ce t et d  s t  RT  d
          s ceAuthor d <- Opt on(record.getFeatureValue(SOURCE_AUTHOR_ D)).map(_.toLong)
          t  stamp <- Opt on(record.getFeatureValue(T MESTAMP)).map(_.toLong)
          labels = toRecapT etLabels(record)
        } y eld {
          (
            user d,
            Seq(
              ReferenceT et(
                s ceT et d,
                s ceAuthor d,
                t  stamp,
                D splayLocat on.T  l nesRecap,
                labels))
          )
        }
      }
      .sumByKey
      .map { case (u d, t etSeq) => ReferenceT ets(u d, t etSeq) }
  }

  /**
   * Return RecT ets, wh ch are out of network t ets served  n t  T  l ne.
   */
  def readT  l nesRecT ets(
    recT ets: DataSetP pe,
    sampleRate: Double
  )(
     mpl c  dateRange: DateRange
  ): TypedP pe[ReferenceT ets] = {
    // recT ets conta n str ctly out of network  nject on t ets

    recT ets.r chRecords
      .sample(sampleRate)
      .f lter { record =>
        val  s nDateRange = dateRange.conta ns(R chDate(record.getFeatureValue(T MESTAMP).toLong))
        val  sL ngered mpress on = record.getFeatureValue( S_L NGER_ MPRESS ON)

         s nDateRange &&  sL ngered mpress on
      }
      .flatMap { record =>
        for {
          user d <- Opt on(record.getFeatureValue(USER_ D)).map(_.toLong)
          t et d <- Opt on(record.getFeatureValue(TWEET_ D)).map(_.toLong)
          author d <- Opt on(record.getFeatureValue(AUTHOR_ D)).map(_.toLong)
          t  stamp <- Opt on(record.getFeatureValue(T MESTAMP)).map(_.toLong)
          labels = toRecT etLabels(record)
        } y eld {
          (
            user d,
            Seq(
              ReferenceT et(
                t et d,
                author d,
                t  stamp,
                D splayLocat on.T  l nesRect et,
                labels))
          )
        }
      }
      .sumByKey
      .map { case (u d, t etSeq) => ReferenceT ets(u d, t etSeq) }
  }
}
