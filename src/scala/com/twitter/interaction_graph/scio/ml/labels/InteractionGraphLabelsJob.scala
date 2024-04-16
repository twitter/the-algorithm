package com.tw ter. nteract on_graph.sc o.ml.labels

 mport com.google.ap .serv ces.b gquery.model.T  Part  on ng
 mport com.spot fy.sc o.Sc oContext
 mport com.spot fy.sc o.values.SCollect on
 mport com.tw ter.beam. o.dal.DAL
 mport com.tw ter.beam. o.fs.mult format.D skFormat
 mport com.tw ter.beam. o.fs.mult format.PathLa t
 mport com.tw ter.beam. o.fs.mult format.Wr eOpt ons
 mport com.tw ter.beam.job.Serv ce dent f erOpt ons
 mport com.tw ter.cde.sc o.dal_read.S ceUt l
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.dal.cl ent.dataset.T  Part  onedDALDataset
 mport com.tw ter. nteract on_graph.sc o.agg_cl ent_event_logs. nteract onGraphAggCl entEventLogsEdgeDa lyScalaDataset
 mport com.tw ter. nteract on_graph.sc o.agg_d rect_ nteract ons. nteract onGraphAggD rect nteract onsEdgeDa lyScalaDataset
 mport com.tw ter. nteract on_graph.sc o.agg_not f cat ons. nteract onGraphAggNot f cat onsEdgeDa lyScalaDataset
 mport com.tw ter. nteract on_graph.thr ftscala.Edge
 mport com.tw ter. nteract on_graph.thr ftscala.EdgeLabel
 mport com.tw ter.sc o_ nternal.job.Sc oBeamJob
 mport com.tw ter.soc algraph.event.thr ftscala.FollowEvent
 mport com.tw ter.soc algraph.hadoop.Soc algraphFollowEventsScalaDataset
 mport com.tw ter.stateb rd.v2.thr ftscala.Env ron nt
 mport com.tw ter.tcdc.bqblaster.beam.syntax._
 mport com.tw ter.tcdc.bqblaster.core.avro.TypedProject on
 mport com.tw ter.tcdc.bqblaster.core.transform.RootTransform
 mport org.apac .beam.sdk. o.gcp.b gquery.B gQuery O
 mport org.joda.t  . nterval

object  nteract onGraphLabelsJob extends Sc oBeamJob[ nteract onGraphLabelsOpt on] {

  overr de protected def conf gureP pel ne(
    sc oContext: Sc oContext,
    p pel neOpt ons:  nteract onGraphLabelsOpt on
  ): Un  = {
    @trans ent
     mpl c  lazy val sc: Sc oContext = sc oContext
     mpl c  lazy val date nterval:  nterval = p pel neOpt ons. nterval

    val bqTableNa : Str ng = p pel neOpt ons.getBqTableNa 
    val dalEnv ron nt: Str ng = p pel neOpt ons
      .as(classOf[Serv ce dent f erOpt ons])
      .getEnv ron nt()
    val dalWr eEnv ron nt =  f (p pel neOpt ons.getDALWr eEnv ron nt != null) {
      p pel neOpt ons.getDALWr eEnv ron nt
    } else {
      dalEnv ron nt
    }

    def readPart  on[T: Man fest](dataset: T  Part  onedDALDataset[T]): SCollect on[T] = {
      S ceUt l.readDALDataset[T](
        dataset = dataset,
         nterval = date nterval,
        dalEnv ron nt = dalEnv ron nt
      )
    }

    val follows = readPart  on[FollowEvent](Soc algraphFollowEventsScalaDataset)
      .flatMap(LabelUt l.fromFollowEvent)

    val d rect nteract ons =
      readPart  on[Edge]( nteract onGraphAggD rect nteract onsEdgeDa lyScalaDataset)
        .flatMap(LabelUt l.from nteract onGraphEdge)

    val cl entEvents =
      readPart  on[Edge]( nteract onGraphAggCl entEventLogsEdgeDa lyScalaDataset)
        .flatMap(LabelUt l.from nteract onGraphEdge)

    val pushEvents =
      readPart  on[Edge]( nteract onGraphAggNot f cat onsEdgeDa lyScalaDataset)
        .flatMap(LabelUt l.from nteract onGraphEdge)


    val labels = groupLabels(
      follows ++
        d rect nteract ons ++
        cl entEvents ++
        pushEvents)

    labels.saveAsCustomOutput(
      "Wr e Edge Labels",
      DAL.wr e[EdgeLabel](
         nteract onGraphLabelsDa lyScalaDataset,
        PathLa t.Da lyPath(p pel neOpt ons.getOutputPath),
        date nterval,
        D skFormat.Parquet,
        Env ron nt.valueOf(dalWr eEnv ron nt),
        wr eOpt on = Wr eOpt ons(numOfShards = So (p pel neOpt ons.getNumberOfShards))
      )
    )

    // save to BQ
     f (p pel neOpt ons.getBqTableNa  != null) {
      val  ngest onT   = p pel neOpt ons.getDate().value.getStart.toDate
      val bqF eldsTransform = RootTransform
        .Bu lder()
        .w hPrependedF elds("dateH " -> TypedProject on.fromConstant( ngest onT  ))
      val t  Part  on ng = new T  Part  on ng()
        .setType("DAY").setF eld("dateH ").setExp rat onMs(90.days. nM ll seconds)
      val bqWr er = B gQuery O
        .wr e[EdgeLabel]
        .to(bqTableNa )
        .w hExtendedError nfo()
        .w hT  Part  on ng(t  Part  on ng)
        .w hLoadJobProject d("twttr-recos-ml-prod")
        .w hThr ftSupport(bqF eldsTransform.bu ld(), AvroConverter.Legacy)
        .w hCreateD spos  on(B gQuery O.Wr e.CreateD spos  on.CREATE_ F_NEEDED)
        .w hWr eD spos  on(B gQuery O.Wr e.Wr eD spos  on.WR TE_APPEND)
      labels
        .saveAsCustomOutput(
          s"Save Recom ndat ons to BQ $bqTableNa ",
          bqWr er
        )
    }

  }

  def groupLabels(labels: SCollect on[EdgeLabel]): SCollect on[EdgeLabel] = {
    labels
      .map { e: EdgeLabel => ((e.s ce d, e.dest nat on d), e.labels.toSet) }
      .sumByKey
      .map { case ((src d, dest d), labels) => EdgeLabel(src d, dest d, labels) }
  }
}
