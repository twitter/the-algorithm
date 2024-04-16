package com.tw ter.t  l nes.data_process ng.ad_hoc.earlyb rd_rank ng.model_evaluat on

 mport com.tw ter.algeb rd.Aggregator
 mport com.tw ter.algeb rd.AveragedValue
 mport com.tw ter.ml.ap .pred ct on_eng ne.Pred ct onEng nePlug n
 mport com.tw ter.ml.ap .ut l.FDsl
 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.ap . RecordOneToManyAdapter
 mport com.tw ter.scald ng.Args
 mport com.tw ter.scald ng.DateRange
 mport com.tw ter.scald ng.Execut on
 mport com.tw ter.scald ng.TypedJson
 mport com.tw ter.scald ng.TypedP pe
 mport com.tw ter.scald ng_ nternal.dalv2.DAL
 mport com.tw ter.scald ng_ nternal.job.Tw terExecut onApp
 mport com.tw ter.t  l nes.data_process ng.ad_hoc.earlyb rd_rank ng.common.Earlyb rdTra n ngRecapConf gurat on
 mport com.tw ter.t  l nes.data_process ng.ut l.Request mpl c s.R chRequest
 mport com.tw ter.t  l nes.data_process ng.ut l.example.RecapT etExample
 mport com.tw ter.t  l nes.data_process ng.ut l.execut on.UTCDateRangeFromArgs
 mport com.tw ter.t  l nes.pred ct on.adapters.recap.RecapSuggest onRecordAdapter
 mport com.tw ter.t  l nes.pred ct on.features.recap.RecapFeatures
 mport com.tw ter.t  l nes.suggests.common.record.thr ftscala.Suggest onRecord
 mport com.tw ter.t  l neserv ce.suggests.logg ng.recap.thr ftscala.H ghl ghtT et
 mport com.tw ter.t  l neserv ce.suggests.logg ng.thr ftscala.SuggestsRequestLog
 mport scala.collect on.JavaConverters._
 mport scala.language.reflect veCalls
 mport scala.ut l.Random
 mport twadoop_conf g.conf gurat on.log_categor es.group.t  l nes.T  l neserv ce nject onRequestLogScalaDataset

/**
 * Evaluates an Earlyb rd model us ng 1%  nject on request logs.
 *
 * Argu nts:
 * --model_base_path  path to Earlyb rd model snapshots
 * --models           l st of model na s to evaluate
 * --output           path to output stats
 * --parallel sm      (default: 3) number of tasks to run  n parallel
 * --topks            (opt onal) l st of values of `k` ( ntegers) for top-K  tr cs
 * --topn_fract ons   (opt onal) l st of values of `n` (doubles) for top-N-fract on  tr cs
 * --seed             (opt onal) seed for random number generator
 */
object Earlyb rdModelEvaluat onJob extends Tw terExecut onApp w h UTCDateRangeFromArgs {

   mport FDsl._
   mport Pred ct onEng nePlug n._

  pr vate[t ] val averager: Aggregator[Double, AveragedValue, Double] =
    AveragedValue.aggregator
  pr vate[t ] val recapAdapter:  RecordOneToManyAdapter[Suggest onRecord] =
    new RecapSuggest onRecordAdapter(c ckD llT   = false)

  overr de def job: Execut on[Un ] = {
    for {
      args <- Execut on.getArgs
      dateRange <- dateRangeEx
       tr cs = get tr cs(args)
      random = bu ldRandom(args)
      modelBasePath = args("model_base_path")
      models = args.l st("models")
      parallel sm = args. nt("parallel sm", 3)
      logs = logsHav ngCand dates(dateRange)
      modelScoredCand dates = models.map { model =>
        (model, scoreCand datesUs ngModel(logs, s"$modelBasePath/$model"))
      }
      funct onScoredCand dates = L st(
        ("random", scoreCand datesUs ngFunct on(logs, _ => So (random.nextDouble()))),
        ("or g nal_earlyb rd", scoreCand datesUs ngFunct on(logs, extractOr g nalEarlyb rdScore)),
        ("blender", scoreCand datesUs ngFunct on(logs, extractBlenderScore))
      )
      allCand dates = modelScoredCand dates ++ funct onScoredCand dates
      statsExecut ons = allCand dates.map {
        case (na , p pe) =>
          for {
            saved <- p pe.forceToD skExecut on
            stats <- compute tr cs(saved,  tr cs, parallel sm)
          } y eld (na , stats)
      }
      stats <- Execut on.w hParallel sm(statsExecut ons, parallel sm)
      _ <- TypedP pe.from(stats).wr eExecut on(TypedJson(args("output")))
    } y eld ()
  }

  pr vate[t ] def compute tr cs(
    requests: TypedP pe[Seq[Cand dateRecord]],
     tr csToCompute: Seq[Earlyb rdEvaluat on tr c],
    parallel sm:  nt
  ): Execut on[Map[Str ng, Double]] = {
    val  tr cExecut ons =  tr csToCompute.map {  tr c =>
      val  tr cEx = requests.flatMap( tr c(_)).aggregate(averager).toOpt onExecut on
       tr cEx.map { value => value.map(( tr c.na , _)) }
    }
    Execut on.w hParallel sm( tr cExecut ons, parallel sm).map(_.flatten.toMap)
  }

  pr vate[t ] def get tr cs(args: Args): Seq[Earlyb rdEvaluat on tr c] = {
    val topKs = args.l st("topks").map(_.to nt)
    val topNFract ons = args.l st("topn_fract ons").map(_.toDouble)
    val topK tr cs = topKs.flatMap { topK =>
      Seq(
        TopKRecall(topK, f lterFe rThanK = false),
        TopKRecall(topK, f lterFe rThanK = true),
        ShownT etRecall(topK),
        AverageFullScoreForTopL ght(topK),
        SumScoreRecallForTopL ght(topK),
        HasFe rThanKCand dates(topK),
        ShownT etRecallW hFullScores(topK),
        Probab l yOfCorrectOrder ng
      )
    }
    val topNPercent tr cs = topNFract ons.flatMap { topNPercent =>
      Seq(
        TopNPercentRecall(topNPercent),
        ShownT etPercentRecall(topNPercent)
      )
    }
    topK tr cs ++ topNPercent tr cs ++ Seq(NumberOfCand dates)
  }

  pr vate[t ] def bu ldRandom(args: Args): Random = {
    val seedOpt = args.opt onal("seed").map(_.toLong)
    seedOpt.map(new Random(_)).getOrElse(new Random())
  }

  pr vate[t ] def logsHav ngCand dates(dateRange: DateRange): TypedP pe[SuggestsRequestLog] =
    DAL
      .read(T  l neserv ce nject onRequestLogScalaDataset, dateRange)
      .toTypedP pe
      .f lter(_.recapCand dates.ex sts(_.nonEmpty))

  /**
   * Uses a model def ned at `earlyb rdModelPath` to score cand dates and
   * returns a Seq[Cand dateRecord] for each request.
   */
  pr vate[t ] def scoreCand datesUs ngModel(
    logs: TypedP pe[SuggestsRequestLog],
    earlyb rdModelPath: Str ng
  ): TypedP pe[Seq[Cand dateRecord]] = {
    logs
      .us ngScorer(earlyb rdModelPath)
      .map {
        case (scorer: Pred ct onEng neScorer, log: SuggestsRequestLog) =>
          val suggest onRecords =
            RecapT etExample
              .extractCand dateT etExamples(log)
              .map(_.asSuggest onRecord)
          val servedT et ds = log.servedH ghl ghtT ets.flatMap(_.t et d).toSet
          val rena r = (new Earlyb rdTra n ngRecapConf gurat on).Earlyb rdFeatureRena r
          suggest onRecords.flatMap { suggest onRecord =>
            val dataRecordOpt = recapAdapter.adaptToDataRecords(suggest onRecord).asScala. adOpt on
            dataRecordOpt.foreach(rena r.transform)
            for {
              t et d <- suggest onRecord. em d
              fullScore <- suggest onRecord.recapFeatures.flatMap(_.comb nedModelScore)
              earlyb rdScore <- dataRecordOpt.flatMap(calculateL ghtScore(_, scorer))
            } y eld Cand dateRecord(
              t et d = t et d,
              fullScore = fullScore,
              earlyScore = earlyb rdScore,
              served = servedT et ds.conta ns(t et d)
            )
          }
      }
  }

  /**
   * Uses a s mple funct on to score cand dates and returns a Seq[Cand dateRecord] for each
   * request.
   */
  pr vate[t ] def scoreCand datesUs ngFunct on(
    logs: TypedP pe[SuggestsRequestLog],
    earlyScoreExtractor: H ghl ghtT et => Opt on[Double]
  ): TypedP pe[Seq[Cand dateRecord]] = {
    logs
      .map { log =>
        val t etCand dates = log.recapT etCand dates.getOrElse(N l)
        val servedT et ds = log.servedH ghl ghtT ets.flatMap(_.t et d).toSet
        for {
          cand date <- t etCand dates
          t et d <- cand date.t et d
          fullScore <- cand date.recapFeatures.flatMap(_.comb nedModelScore)
          earlyScore <- earlyScoreExtractor(cand date)
        } y eld Cand dateRecord(
          t et d = t et d,
          fullScore = fullScore,
          earlyScore = earlyScore,
          served = servedT et ds.conta ns(t et d)
        )
      }
  }

  pr vate[t ] def extractOr g nalEarlyb rdScore(cand date: H ghl ghtT et): Opt on[Double] =
    for {
      recapFeatures <- cand date.recapFeatures
      t etFeatures <- recapFeatures.t etFeatures
    } y eld t etFeatures.earlyb rdScore

  pr vate[t ] def extractBlenderScore(cand date: H ghl ghtT et): Opt on[Double] =
    for {
      recapFeatures <- cand date.recapFeatures
      t etFeatures <- recapFeatures.t etFeatures
    } y eld t etFeatures.blenderScore

  pr vate[t ] def calculateL ghtScore(
    dataRecord: DataRecord,
    scorer: Pred ct onEng neScorer
  ): Opt on[Double] = {
    val scoredRecord = scorer(dataRecord)
     f (scoredRecord.hasFeature(RecapFeatures.PRED CTED_ S_UN F ED_ENGAGEMENT)) {
      So (scoredRecord.getFeatureValue(RecapFeatures.PRED CTED_ S_UN F ED_ENGAGEMENT).toDouble)
    } else {
      None
    }
  }
}
