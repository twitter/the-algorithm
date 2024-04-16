package com.tw ter.ann.dataflow.offl ne

 mport com.spot fy.sc o.Sc oContext
 mport com.spot fy.sc o.Sc o tr cs
 mport com.tw ter.ann.annoy.TypedAnnoy ndex
 mport com.tw ter.ann.brute_force.Ser al zableBruteForce ndex
 mport com.tw ter.ann.common.thr ftscala.Ann ndex tadata
 mport com.tw ter.ann.common.D stance
 mport com.tw ter.ann.common.Cos ne
 mport com.tw ter.ann.common.Ent yEmbedd ng
 mport com.tw ter.ann.common. ndexOutputF le
 mport com.tw ter.ann.common. tr c
 mport com.tw ter.ann.common.ReadWr eFuturePool
 mport com.tw ter.ann.fa ss.Fa ss ndexer
 mport com.tw ter.ann.hnsw.TypedHnsw ndex
 mport com.tw ter.ann.ser al zat on.Pers stedEmbedd ng nject on
 mport com.tw ter.ann.ser al zat on.Thr ft erator O
 mport com.tw ter.ann.ser al zat on.thr ftscala.Pers stedEmbedd ng
 mport com.tw ter.ann.ut l. ndexBu lderUt ls
 mport com.tw ter.beam. o.b gquery.B gQuery O
 mport com.tw ter.beam. o.dal.DalObservedDatasetReg strat on
 mport com.tw ter.beam.job.DateRange
 mport com.tw ter.beam.job.DateRangeOpt ons
 mport com.tw ter.cortex.ml.embedd ngs.common._
 mport com.tw ter.ml.ap .embedd ng.Embedd ng
 mport com.tw ter.ml.ap .embedd ng.Embedd ngMath
 mport com.tw ter.ml.ap .embedd ng.Embedd ngSerDe
 mport com.tw ter.ml.ap .thr ftscala.{Embedd ng => TEmbedd ng}
 mport com.tw ter.ml.featurestore.l b.Ent y d
 mport com.tw ter.ml.featurestore.l b.Semant cCore d
 mport com.tw ter.ml.featurestore.l b.Tfw d
 mport com.tw ter.ml.featurestore.l b.T et d
 mport com.tw ter.ml.featurestore.l b.User d
 mport com.tw ter.scald ng.DateOps
 mport com.tw ter.scald ng.R chDate
 mport com.tw ter.sc o_ nternal.job.Sc oBeamJob
 mport com.tw ter.stateb rd.v2.thr ftscala.{Env ron nt => Stateb rdEnv ron nt}
 mport com.tw ter.ut l.Awa 
 mport com.tw ter.ut l.FuturePool
 mport com.tw ter.wtf.beam.bq_embedd ng_export.BQQueryUt ls
 mport java.t  . nstant
 mport java.ut l.T  Zone
 mport java.ut l.concurrent.Executors
 mport org.apac .beam.sdk. o.F leSystems
 mport org.apac .beam.sdk. o.fs.ResolveOpt ons
 mport org.apac .beam.sdk. o.fs.Res ce d
 mport org.apac .beam.sdk. o.gcp.b gquery.B gQuery O.TypedRead
 mport org.apac .beam.sdk.opt ons.Default
 mport org.apac .beam.sdk.opt ons.Descr pt on
 mport org.apac .beam.sdk.transforms.DoFn
 mport org.apac .beam.sdk.transforms.DoFn._
 mport org.apac .beam.sdk.transforms.PTransform
 mport org.apac .beam.sdk.transforms.ParDo
 mport org.apac .beam.sdk.values.KV
 mport org.apac .beam.sdk.values.PCollect on
 mport org.apac .beam.sdk.values.PDone
 mport org.slf4j.Logger
 mport org.slf4j.LoggerFactory

tra  ANNOpt ons extends DateRangeOpt ons {
  @Descr pt on("Output GCS path for t  generated  ndex")
  def getOutputPath(): Str ng
  def setOutputPath(value: Str ng): Un 

  @Descr pt on(" f set, t   ndex  s grouped")
  @Default.Boolean(false)
  def getGrouped: Boolean
  def setGrouped(value: Boolean): Un 

  @Descr pt on(
    " f set, a seg nt w ll be reg stered for t  prov ded DAL dataset module wh ch w ll tr gger " +
      "DAL reg strat on.")
  @Default.Boolean(false)
  def getEnableDalReg strat on: Boolean
  def setEnableDalReg strat on(value: Boolean): Un 

  @Descr pt on(
    "Output GCS path for t  generated  ndex. T  OutputPath should be of t  format " +
      "'gs://user.{{user_na }}.dp.gcp.twttr.net/subD r/outputD r' and OutputDALPath w ll be " +
      "'subD r/outputD r' for t  to work")
  def getOutputDALPath: Str ng
  def setOutputDALPath(value: Str ng): Un 

  @Descr pt on("Get ANN  ndex dataset na ")
  def getDatasetModuleNa : Str ng
  def setDatasetModuleNa (value: Str ng): Un 

  @Descr pt on("Get ANN  ndex dataset owner role")
  def getDatasetOwnerRole: Str ng
  def setDatasetOwnerRole(value: Str ng): Un 

  @Descr pt on(" f set,  ndex  s wr ten  n <output>/<t  stamp>")
  @Default.Boolean(false)
  def getOutputW hT  stamp: Boolean
  def setOutputW hT  stamp(value: Boolean): Un 

  @Descr pt on("F le wh ch conta ns a SQL query to retr eve embedd ngs from BQ")
  def getDatasetSqlPath: Str ng
  def setDatasetSqlPath(value: Str ng): Un 

  @Descr pt on("D  ns on of embedd ng  n t   nput data. See go/ann")
  def getD  ns on:  nt
  def setD  ns on(value:  nt): Un 

  @Descr pt on("T  type of ent y  D that  s used w h t  embedd ngs. See go/ann")
  def getEnt yK nd: Str ng
  def setEnt yK nd(value: Str ng): Un 

  @Descr pt on("T  k nd of  ndex   want to generate (HNSW/Annoy/Brute Force/fa ss). See go/ann")
  def getAlgo: Str ng
  def setAlgo(value: Str ng): Un 

  @Descr pt on("D stance  tr c ( nnerProduct/Cos ne/L2). See go/ann")
  def get tr c: Str ng
  def set tr c(value: Str ng): Un 

  @Descr pt on("Spec f es how many parallel  nserts happen to t   ndex. See go/ann")
  def getConcurrencyLevel:  nt
  def setConcurrencyLevel(value:  nt): Un 

  @Descr pt on(
    "Used by HNSW algo. Larger value  ncreases bu ld t   but w ll g ve better recall. See go/ann")
  def getEfConstruct on:  nt
  def setEfConstruct on(value:  nt): Un 

  @Descr pt on(
    "Used by HNSW algo. Larger value  ncreases t   ndex s ze but w ll g ve better recall. " +
      "See go/ann")
  def getMaxM:  nt
  def setMaxM(value:  nt): Un 

  @Descr pt on("Used by HNSW algo. Approx mate number of ele nts that w ll be  ndexed. See go/ann")
  def getExpectedEle nts:  nt
  def setExpectedEle nts(value:  nt): Un 

  @Descr pt on(
    "Used by Annoy. num_trees  s prov ded dur ng bu ld t   and affects t  bu ld t   and t  " +
      " ndex s ze. A larger value w ll g ve more accurate results, but larger  ndexes. See go/ann")
  def getAnnoyNumTrees:  nt
  def setAnnoyNumTrees(value:  nt): Un 

  @Descr pt on(
    "FA SS factory str ng determ nes t  ANN algor hm and compress on. " +
      "See https://g hub.com/facebookresearch/fa ss/w k /T - ndex-factory")
  def getFA SSFactoryStr ng: Str ng
  def setFA SSFactoryStr ng(value: Str ng): Un 

  @Descr pt on("Sample rate for tra n ng dur ng creat on of FA SS  ndex. Default  s 0.05f")
  @Default.Float(0.05f)
  def getTra n ngSampleRate: Float
  def setTra n ngSampleRate(value: Float): Un 
}

/**
 * Bu lds ANN  ndex.
 *
 * T   nput embedd ngs are read from B gQuery us ng t   nput SQL query. T  output from t  SQL
 * query needs to have two columns, "ent y D" [Long] and "embedd ng" [L st[Double]]
 *
 * Output d rectory supported  s GCS bucket
 */
object ANN ndexBu lderBeamJob extends Sc oBeamJob[ANNOpt ons] {
  val counterNa Space = "ANN ndexBu lderBeamJob"
  val LOG: Logger = LoggerFactory.getLogger(t .getClass)
   mpl c  val t  Zone: T  Zone = DateOps.UTC

  def conf gureP pel ne(sc: Sc oContext, opts: ANNOpt ons): Un  = {
    val startDate: R chDate = R chDate(opts. nterval.getStart.toDate)
    val endDate: R chDate = R chDate(opts. nterval.getEnd.toDate)
    val  nstant =  nstant.now()
    val out = {
      val base = F leSystems.matchNewRes ce(opts.getOutputPath, /* sD rectory=*/ true)
       f (opts.getOutputW hT  stamp) {
        base.resolve(
           nstant.toEpochM ll .toStr ng,
          ResolveOpt ons.StandardResolveOpt ons.RESOLVE_D RECTORY)
      } else {
        base
      }
    }

    // Def ne template var ables wh ch   would l ke to be replaced  n t  correspond ng sql f le
    val templateVar ables =
      Map(
        "START_DATE" -> startDate.toStr ng(DateOps.DATET ME_HMS_W TH_DASH),
        "END_DATE" -> endDate.toStr ng(DateOps.DATET ME_HMS_W TH_DASH)
      )

    val embedd ngFetchQuery =
      BQQueryUt ls.getBQQueryFromSqlF le(opts.getDatasetSqlPath, templateVar ables)

    val sCollect on =  f (opts.getGrouped) {
      sc.custom nput(
        "Read grouped data from BQ",
        B gQuery O
          .readClass[GroupedEmbedd ngData]()
          .fromQuery(embedd ngFetchQuery).us ngStandardSql()
          .w h thod(TypedRead. thod.D RECT_READ)
      )
    } else {
      sc.custom nput(
        "Read flat data from BQ",
        B gQuery O
          .readClass[FlatEmbedd ngData]().fromQuery(embedd ngFetchQuery).us ngStandardSql()
          .w h thod(TypedRead. thod.D RECT_READ)
      )
    }

    val processedCollect on =
      sCollect on
        .flatMap(transformTableRowToKeyVal)
        .groupBy(_.getKey)
        .map {
          case (groupNa , groupValue) =>
            Map(groupNa  -> groupValue.map(_.getValue))
        }

    val ann ndex tadata =
      Ann ndex tadata(t  stamp = So ( nstant.getEpochSecond), w hGroups = So (opts.getGrouped))

    // Count t  number of groups and output t  ANN  ndex  tadata
    processedCollect on.count.map(count => {
      val annGrouped ndex tadata = ann ndex tadata.copy(
        numGroups = So (count. ntValue())
      )
      val  ndexOutD r = new  ndexOutputF le(out)
       ndexOutD r.wr e ndex tadata(annGrouped ndex tadata)
    })

    // Generate  ndex
    processedCollect on.saveAsCustomOutput(
      "Ser al se to D sk",
      OutputS nk(
        out,
        opts.getAlgo.equals("fa ss"),
        opts.getOutputDALPath,
        opts.getEnableDalReg strat on,
        opts.getDatasetModuleNa ,
        opts.getDatasetOwnerRole,
         nstant,
        opts.getDate(),
        counterNa Space
      )
    )
  }

  def transformTableRowToKeyVal(
    data: BaseEmbedd ngData
  ): Opt on[KV[Str ng, KV[Long, TEmbedd ng]]] = {
    val transformTable = Sc o tr cs.counter(counterNa Space, "transform_table_row_to_kv")
    for {
       d <- data.ent y d
    } y eld {
      transformTable. nc()
      val groupNa : Str ng =  f (data. s nstanceOf[GroupedEmbedd ngData]) {
        (data.as nstanceOf[GroupedEmbedd ngData]).group d.get
      } else {
        ""
      }

      KV.of[Str ng, KV[Long, TEmbedd ng]](
        groupNa ,
        KV.of[Long, TEmbedd ng](
           d,
          Embedd ngSerDe.toThr ft(Embedd ng(data.embedd ng.map(_.toFloat).toArray)))
      )
    }
  }

  case class OutputS nk(
    outD r: Res ce d,
     sFa ss: Boolean,
    outputDALPath: Str ng,
    enableDalReg strat on: Boolean,
    datasetModuleNa : Str ng,
    datasetOwnerRole: Str ng,
     nstant:  nstant,
    date: DateRange,
    counterNa Space: Str ng)
      extends PTransform[PCollect on[Map[Str ng,  erable[KV[Long, TEmbedd ng]]]], PDone] {
    overr de def expand( nput: PCollect on[Map[Str ng,  erable[KV[Long, TEmbedd ng]]]]): PDone = {
      PDone. n {
        val dum Output = {
           f ( sFa ss) {
             nput
              .apply(
                "Bu ld&Wr eFa ssANN ndex",
                ParDo.of(new Bu ldFa ssANN ndex(outD r, counterNa Space))
              )
          } else {
             nput
              .apply(
                "Bu ld&Wr eANN ndex",
                ParDo.of(new Bu ldANN ndex(outD r, counterNa Space))
              )
          }
        }

         f (enableDalReg strat on) {
           nput
            .apply(
              "Reg ster DAL Dataset",
              DalObservedDatasetReg strat on(
                datasetModuleNa ,
                datasetOwnerRole,
                outputDALPath,
                 nstant,
                So (Stateb rdEnv ron nt.Prod),
                So ("ANN  ndex Data F les"))
            )
            .getP pel ne
        } else {
          dum Output.getP pel ne
        }
      }
    }
  }

  class Bu ldANN ndex(outD r: Res ce d, counterNa Space: Str ng)
      extends DoFn[Map[Str ng,  erable[KV[Long, TEmbedd ng]]], Un ] {

    def transformKeyValToEmbedd ngW hEnt y[T <: Ent y d](
      ent yK nd: Ent yK nd[T]
    )(
      keyVal: KV[Long, TEmbedd ng]
    ): Ent yEmbedd ng[T] = {
      val ent y d = ent yK nd match {
        case UserK nd => User d(keyVal.getKey).toThr ft
        case T etK nd => T et d(keyVal.getKey).toThr ft
        case TfwK nd => Tfw d(keyVal.getKey).toThr ft
        case Semant cCoreK nd => Semant cCore d(keyVal.getKey).toThr ft
        case _ => throw new  llegalArgu ntExcept on(s"Unsupported embedd ng k nd: $ent yK nd")
      }
      Ent yEmbedd ng[T](
        Ent y d.fromThr ft(ent y d).as nstanceOf[T],
        Embedd ngSerDe.fromThr ft(keyVal.getValue))
    }

    @ProcessEle nt
    def processEle nt[T <: Ent y d, D <: D stance[D]](
      @Ele nt dataGrouped: Map[Str ng,  erable[KV[Long, TEmbedd ng]]],
      context: ProcessContext
    ): Un  = {
      val opts = context.getP pel neOpt ons.as(classOf[ANNOpt ons])
      val uncastEnt yK nd = Ent yK nd.getEnt yK nd(opts.getEnt yK nd)
      val ent yK nd = uncastEnt yK nd.as nstanceOf[Ent yK nd[T]]
      val transformKVtoEmbedd ngs =
        Sc o tr cs.counter(counterNa Space, "transform_kv_to_embedd ngs")

      val _ = dataGrouped.map {
        case (groupNa , data) =>
          val annEmbedd ngs = data.map { kv =>
            transformKVtoEmbedd ngs. nc()
            transformKeyValToEmbedd ngW hEnt y(ent yK nd)(kv)
          }

          val out = {
             f (opts.getGrouped && groupNa  != "") {
              outD r.resolve(groupNa , ResolveOpt ons.StandardResolveOpt ons.RESOLVE_D RECTORY)
            } else {
              outD r
            }
          }
          LOG. nfo(s"Wr  ng output to ${out}")

          val  tr c =  tr c.fromStr ng(opts.get tr c).as nstanceOf[ tr c[D]]
          val concurrencyLevel = opts.getConcurrencyLevel
          val d  ns on = opts.getD  ns on
          val threadPool = Executors.newF xedThreadPool(concurrencyLevel)

          LOG. nfo(s"Bu ld ng ANN  ndex of type ${opts.getAlgo}")
          val ser al zat on = opts.getAlgo match {
            case "brute_force" =>
              val Pers stedEmbedd ng O =
                new Thr ft erator O[Pers stedEmbedd ng](Pers stedEmbedd ng)
              Ser al zableBruteForce ndex(
                 tr c,
                FuturePool.apply(threadPool),
                new Pers stedEmbedd ng nject on(ent yK nd.byte nject on),
                Pers stedEmbedd ng O
              )
            case "annoy" =>
              TypedAnnoy ndex. ndexBu lder(
                d  ns on,
                opts.getAnnoyNumTrees,
                 tr c,
                ent yK nd.byte nject on,
                FuturePool.apply(threadPool)
              )
            case "hnsw" =>
              val efConstruct on = opts.getEfConstruct on
              val maxM = opts.getMaxM
              val expectedEle nts = opts.getExpectedEle nts
              TypedHnsw ndex.ser al zable ndex(
                d  ns on,
                 tr c,
                efConstruct on,
                maxM,
                expectedEle nts,
                ent yK nd.byte nject on,
                ReadWr eFuturePool(FuturePool.apply(threadPool))
              )
          }

          val future =
             ndexBu lderUt ls.addTo ndex(ser al zat on, annEmbedd ngs.toSeq, concurrencyLevel)
          Awa .result(future.map { _ =>
            ser al zat on.toD rectory(out)
          })
      }
    }
  }

  class Bu ldFa ssANN ndex(outD r: Res ce d, counterNa Space: Str ng)
      extends DoFn[Map[Str ng,  erable[KV[Long, TEmbedd ng]]], Un ] {

    @ProcessEle nt
    def processEle nt[D <: D stance[D]](
      @Ele nt dataGrouped: Map[Str ng,  erable[KV[Long, TEmbedd ng]]],
      context: ProcessContext
    ): Un  = {
      val opts = context.getP pel neOpt ons.as(classOf[ANNOpt ons])
      val transformKVtoEmbedd ngs =
        Sc o tr cs.counter(counterNa Space, "transform_kv_to_embedd ngs")

      val _ = dataGrouped.map {
        case (groupNa , data) =>
          val out = {
             f (opts.getGrouped && groupNa  != "") {
              outD r.resolve(groupNa , ResolveOpt ons.StandardResolveOpt ons.RESOLVE_D RECTORY)
            } else {
              outD r
            }
          }
          LOG. nfo(s"Wr  ng output to ${out}")

          val  tr c =  tr c.fromStr ng(opts.get tr c).as nstanceOf[ tr c[D]]
          val maybeNormal zedP pe = data.map { kv =>
            transformKVtoEmbedd ngs. nc()
            val embedd ng = Embedd ngSerDe.floatEmbedd ngSerDe.fromThr ft(kv.getValue)
            Ent yEmbedd ng[Long](
              kv.getKey,
               f ( tr c == Cos ne) {
                Embedd ngMath.Float.normal ze(embedd ng)
              } else {
                embedd ng
              }
            )
          }

          // Generate  ndex
          Fa ss ndexer.bu ldAndWr eFa ss ndex(
            maybeNormal zedP pe,
            opts.getTra n ngSampleRate,
            opts.getFA SSFactoryStr ng,
             tr c,
            new  ndexOutputF le(out))
      }
    }
  }
}
