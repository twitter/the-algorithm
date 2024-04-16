package com.tw ter. nteract on_graph.sc o.agg_all

 mport com.google.cloud.b gquery.B gQueryOpt ons
 mport com.google.cloud.b gquery.QueryJobConf gurat on
 mport com.spot fy.sc o.Sc oContext
 mport com.spot fy.sc o.Sc o tr cs
 mport com.spot fy.sc o.values.SCollect on
 mport com.tw ter.beam. o.dal.DAL
 mport com.tw ter.beam. o.dal.DAL.D skFormat
 mport com.tw ter.beam. o.dal.DAL.PathLa t
 mport com.tw ter.beam. o.dal.DAL.Wr eOpt ons
 mport com.tw ter.beam. o.except on.DataNotFoundExcept on
 mport com.tw ter.beam.job.Serv ce dent f erOpt ons
 mport com.tw ter. nteract on_graph.sc o.agg_all. nteract onGraphAggregat onTransform._
 mport com.tw ter. nteract on_graph.sc o.common.DateUt l
 mport com.tw ter. nteract on_graph.sc o.common.FeatureGeneratorUt l
 mport com.tw ter. nteract on_graph.sc o.common.UserUt l
 mport com.tw ter. nteract on_graph.thr ftscala.Edge
 mport com.tw ter. nteract on_graph.thr ftscala.Vertex
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal
 mport com.tw ter.sc o_ nternal.job.Sc oBeamJob
 mport com.tw ter.stateb rd.v2.thr ftscala.Env ron nt
 mport com.tw ter.user_sess on_store.thr ftscala.UserSess on
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.wtf.cand date.thr ftscala.ScoredEdge
 mport java.t  . nstant
 mport org.apac .avro.gener c.Gener cRecord
 mport org.apac .beam.sdk. o.gcp.b gquery.B gQuery O
 mport org.apac .beam.sdk. o.gcp.b gquery.B gQuery O.TypedRead
 mport org.apac .beam.sdk. o.gcp.b gquery.Sc maAndRecord
 mport org.apac .beam.sdk.transforms.Ser al zableFunct on
 mport org.joda.t  . nterval
 mport scala.collect on.JavaConverters._

object  nteract onGraphAggregat onJob extends Sc oBeamJob[ nteract onGraphAggregat onOpt on] {

  // to parse latest date from t  BQ table  're read ng from
  val parseDateRow = new Ser al zableFunct on[Sc maAndRecord, Str ng] {
    overr de def apply( nput: Sc maAndRecord): Str ng = {
      val gener cRecord: Gener cRecord =  nput.getRecord()
      gener cRecord.get("ds").toStr ng
    }
  }

  // note that  're us ng t  prob_expl c  for real_graph_features (for Ho )
  val parseRow = new Ser al zableFunct on[Sc maAndRecord, ScoredEdge] {
    overr de def apply(record: Sc maAndRecord): ScoredEdge = {
      val gener cRecord: Gener cRecord = record.getRecord()
      ScoredEdge(
        gener cRecord.get("s ce_ d").as nstanceOf[Long],
        gener cRecord.get("dest nat on_ d").as nstanceOf[Long],
        gener cRecord.get("prob_expl c ").as nstanceOf[Double],
        gener cRecord.get("follo d").as nstanceOf[Boolean],
      )
    }
  }

  overr de def runP pel ne(
    sc: Sc oContext,
    opts:  nteract onGraphAggregat onOpt on
  ): Un  = {

    val dateStr: Str ng = opts.getDate().value.getStart.toStr ng("yyyyMMdd")
    logger. nfo(s"dateStr $dateStr")
    val project: Str ng = "twttr-recos-ml-prod"
    val datasetNa : Str ng = "realgraph"
    val bqTableNa : Str ng = "scores"
    val fullBqTableNa : Str ng = s"$project:$datasetNa .$bqTableNa "

     f (opts.getDALWr eEnv ron nt.toLo rCase == "prod") {
      val bqCl ent =
        B gQueryOpt ons.newBu lder.setProject d(project).bu ld.getServ ce
      val query =
        s"""
           |SELECT total_rows
           |FROM `$project.$datasetNa . NFORMAT ON_SCHEMA.PART T ONS`
           |WHERE part  on_ d ="$dateStr" AND
           |table_na ="$bqTableNa " AND total_rows > 0
           |""".str pMarg n
      val queryConf g = QueryJobConf gurat on.of(query)
      val results = bqCl ent.query(queryConf g).getValues.asScala.toSeq
       f (results. sEmpty || results. ad.get(0).getLongValue == 0) {
        throw new DataNotFoundExcept on(s"$dateStr not present  n $fullBqTableNa .")
      }
    }
    sc.run()
  }

  overr de protected def conf gureP pel ne(
    sc oContext: Sc oContext,
    p pel neOpt ons:  nteract onGraphAggregat onOpt on
  ): Un  = {
    @trans ent
     mpl c  lazy val sc: Sc oContext = sc oContext
     mpl c  lazy val date nterval:  nterval = p pel neOpt ons. nterval
    val yesterday = DateUt l.subtract(date nterval, Durat on.fromDays(1))

    val dalEnv ron nt: Str ng = p pel neOpt ons
      .as(classOf[Serv ce dent f erOpt ons])
      .getEnv ron nt()
    val dalWr eEnv ron nt =  f (p pel neOpt ons.getDALWr eEnv ron nt != null) {
      p pel neOpt ons.getDALWr eEnv ron nt
    } else {
      dalEnv ron nt
    }
    val dateStr: Str ng = p pel neOpt ons.getDate().value.getStart.toStr ng("yyyy-MM-dd")
    logger. nfo(s"dateStr $dateStr")
    val project: Str ng = "twttr-recos-ml-prod"
    val datasetNa : Str ng = "realgraph"
    val bqTableNa : Str ng = "scores"
    val fullBqTableNa : Str ng = s"$project:$datasetNa .$bqTableNa "

    val scoreExport: SCollect on[ScoredEdge] =
      sc.custom nput(
        s"Read from BQ table $fullBqTableNa ",
        B gQuery O
          .read(parseRow)
          .fromQuery(s"""SELECT s ce_ d, dest nat on_ d, prob_expl c , follo d
               |FROM `$project.$datasetNa .$bqTableNa `
               |WHERE ds = '$dateStr'""".str pMarg n)
          .us ngStandardSql()
          .w h thod(TypedRead. thod.DEFAULT)
      )

    val s ce =  nteract onGraphAggregat onS ce(p pel neOpt ons)

    val (addressEdgeFeatures, addressVertexFeatures) = s ce.readAddressBookFeatures()

    val (cl entEventLogsEdgeFeatures, cl entEventLogsVertexFeatures) =
      s ce.readCl entEventLogsFeatures(date nterval)

    val (flockEdgeFeatures, flockVertexFeatures) = s ce.readFlockFeatures()

    val (d rect nteract onsEdgeFeatures, d rect nteract onsVertexFeatures) =
      s ce.readD rect nteract onsFeatures(date nterval)

    val  nval dUsers = UserUt l.get nval dUsers(s ce.readFlatUsers())

    val (prevAggEdge, prevAggVertex) = s ce.readAggregatedFeatures(yesterday)

    val prevAggregatedVertex: SCollect on[Vertex] =
      UserUt l
        .f lterUsersBy dMapp ng[Vertex](
          prevAggVertex,
           nval dUsers,
          v => v.user d
        )

    /** Remove status-based features (flock/ab) from current graph, because   only need t  latest
     *  T   s to allow us to f lter and roll-up a smaller dataset, to wh ch   w ll st ll add
     *  back t  status-based features for t  complete scoredAggregates (that ot r teams w ll read).
     */
    val prevAggEdgeF ltered = prevAggEdge
      .f lter { e =>
        e.s ce d != e.dest nat on d
      }
      .w hNa ("f lter ng status-based edges")
      .flatMap(FeatureGeneratorUt l.removeStatusFeatures)
    val prevAggEdgeVal d: SCollect on[Edge] =
      UserUt l
        .f lterUsersByMult ple dMapp ngs[Edge](
          prevAggEdgeF ltered,
           nval dUsers,
          Seq(e => e.s ce d, e => e.dest nat on d)
        )

    val aggregatedAct v yVertexDa ly = UserUt l
      .f lterUsersBy dMapp ng[Vertex](
        FeatureGeneratorUt l
          .comb neVertexFeatures(
            cl entEventLogsVertexFeatures ++
              d rect nteract onsVertexFeatures ++
              addressVertexFeatures ++
              flockVertexFeatures
          ),
         nval dUsers,
        v => v.user d
      )

    //   spl  up t  roll-up of decayed counts bet en status vs act v y/count-based features
    val aggregatedAct v yEdgeDa ly = FeatureGeneratorUt l
      .comb neEdgeFeatures(cl entEventLogsEdgeFeatures ++ d rect nteract onsEdgeFeatures)

    // Vertex level, Add t  decay sum for  tory and da ly
    val aggregatedAct v yVertex = FeatureGeneratorUt l
      .comb neVertexFeaturesW hDecay(
        prevAggregatedVertex,
        aggregatedAct v yVertexDa ly,
         nteract onGraphScor ngConf g.ONE_M NUS_ALPHA,
         nteract onGraphScor ngConf g.ALPHA
      )

    // Edge level, Add t  decay sum for  tory and da ly
    val aggregatedAct v yEdge = FeatureGeneratorUt l
      .comb neEdgeFeaturesW hDecay(
        prevAggEdgeVal d,
        aggregatedAct v yEdgeDa ly,
         nteract onGraphScor ngConf g.ONE_M NUS_ALPHA,
         nteract onGraphScor ngConf g.ALPHA
      )
      .f lter(FeatureGeneratorUt l.edgeW hFeatureOt rThanD llT  )
      .w hNa ("remov ng edges that only have d ll t   features")

    val edgeKeyedScores = scoreExport.keyBy { e => (e.s ce d, e.dest nat on d) }

    val scoredAggregatedAct v yEdge = aggregatedAct v yEdge
      .keyBy { e => (e.s ce d, e.dest nat on d) }
      .w hNa ("jo n w h scores")
      .leftOuterJo n(edgeKeyedScores)
      .map {
        case (_, (e, scoredEdgeOpt)) =>
          val scoreOpt = scoredEdgeOpt.map(_.score)
          e.copy(  ght =  f (scoreOpt.nonEmpty) {
            Sc o tr cs.counter("after jo n ng edge w h scores", "has score"). nc()
            scoreOpt
          } else {
            Sc o tr cs.counter("after jo n ng edge w h scores", "no score"). nc()
            None
          })
      }

    val comb nedFeatures = FeatureGeneratorUt l
      .comb neEdgeFeatures(aggregatedAct v yEdge ++ addressEdgeFeatures ++ flockEdgeFeatures)
      .keyBy { e => (e.s ce d, e.dest nat on d) }

    val aggregatedAct v yScoredEdge =
      edgeKeyedScores
        .w hNa ("jo n w h comb ned edge features")
        .leftOuterJo n(comb nedFeatures)
        .map {
          case (_, (scoredEdge, comb nedFeaturesOpt)) =>
             f (comb nedFeaturesOpt.ex sts(_.features.nonEmpty)) {
              Sc o tr cs.counter("after jo n ng scored edge w h features", "has features"). nc()
              Edge(
                s ce d = scoredEdge.s ce d,
                dest nat on d = scoredEdge.dest nat on d,
                  ght = So (scoredEdge.score),
                features = comb nedFeaturesOpt.map(_.features).getOrElse(N l)
              )
            } else {
              Sc o tr cs.counter("after jo n ng scored edge w h features", "no features"). nc()
              Edge(
                s ce d = scoredEdge.s ce d,
                dest nat on d = scoredEdge.dest nat on d,
                  ght = So (scoredEdge.score),
                features = N l
              )
            }
        }

    val realGraphFeatures =
      getTopKT  l neFeatures(aggregatedAct v yScoredEdge, p pel neOpt ons.getMaxDest nat on ds)

    aggregatedAct v yVertex.saveAsCustomOutput(
      "Wr e  tory Aggregated Vertex Records",
      DAL.wr eSnapshot[Vertex](
        dataset =  nteract onGraph toryAggregatedVertexSnapshotScalaDataset,
        pathLa t = PathLa t.Da lyPath(p pel neOpt ons.getOutputPath + "/aggregated_vertex"),
        endDate =  nstant.ofEpochM ll (date nterval.getEndM ll s),
        d skFormat = D skFormat.Parquet,
        env ron ntOverr de = Env ron nt.valueOf(dalWr eEnv ron nt),
        wr eOpt on = Wr eOpt ons(numOfShards = So (p pel neOpt ons.getNumberOfShards / 10))
      )
    )

    scoredAggregatedAct v yEdge.saveAsCustomOutput(
      "Wr e  tory Aggregated Edge Records",
      DAL.wr eSnapshot[Edge](
        dataset =  nteract onGraph toryAggregatedEdgeSnapshotScalaDataset,
        pathLa t = PathLa t.Da lyPath(p pel neOpt ons.getOutputPath + "/aggregated_raw_edge"),
        endDate =  nstant.ofEpochM ll (date nterval.getEndM ll s),
        d skFormat = D skFormat.Parquet,
        env ron ntOverr de = Env ron nt.valueOf(dalWr eEnv ron nt),
        wr eOpt on = Wr eOpt ons(numOfShards = So (p pel neOpt ons.getNumberOfShards))
      )
    )

    aggregatedAct v yVertexDa ly.saveAsCustomOutput(
      "Wr e Da ly Aggregated Vertex Records",
      DAL.wr e[Vertex](
        dataset =  nteract onGraphAggregatedVertexDa lyScalaDataset,
        pathLa t =
          PathLa t.Da lyPath(p pel neOpt ons.getOutputPath + "/aggregated_vertex_da ly"),
         nterval = date nterval,
        d skFormat = D skFormat.Parquet,
        env ron ntOverr de = Env ron nt.valueOf(dalWr eEnv ron nt),
        wr eOpt on = Wr eOpt ons(numOfShards = So (p pel neOpt ons.getNumberOfShards / 10))
      )
    )

    aggregatedAct v yEdgeDa ly.saveAsCustomOutput(
      "Wr e Da ly Aggregated Edge Records",
      DAL.wr e[Edge](
        dataset =  nteract onGraphAggregatedEdgeDa lyScalaDataset,
        pathLa t = PathLa t.Da lyPath(p pel neOpt ons.getOutputPath + "/aggregated_edge_da ly"),
         nterval = date nterval,
        d skFormat = D skFormat.Parquet,
        env ron ntOverr de = Env ron nt.valueOf(dalWr eEnv ron nt),
        wr eOpt on = Wr eOpt ons(numOfShards = So (p pel neOpt ons.getNumberOfShards))
      )
    )

    realGraphFeatures.saveAsCustomOutput(
      "Wr e T  l ne Real Graph Features",
      DAL.wr eVers onedKeyVal[KeyVal[Long, UserSess on]](
        dataset = RealGraphFeaturesScalaDataset,
        pathLa t =
          PathLa t.Vers onedPath(p pel neOpt ons.getOutputPath + "/real_graph_features"),
        env ron ntOverr de = Env ron nt.valueOf(dalWr eEnv ron nt),
        wr eOpt on = Wr eOpt ons(numOfShards = So (p pel neOpt ons.getNumberOfShards))
      )
    )
  }
}
