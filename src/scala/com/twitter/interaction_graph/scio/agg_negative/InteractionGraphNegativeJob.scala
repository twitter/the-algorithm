package com.tw ter. nteract on_graph.sc o.agg_negat ve

 mport com.google.ap .serv ces.b gquery.model.T  Part  on ng
 mport com.spot fy.sc o.Sc oContext
 mport com.spot fy.sc o.values.SCollect on
 mport com.tw ter.algeb rd.mutable.Pr or yQueueMono d
 mport com.tw ter.beam. o.dal.DAL
 mport com.tw ter.beam. o.fs.mult format.PathLa t
 mport com.tw ter.beam. o.fs.mult format.Wr eOpt ons
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.dal.cl ent.dataset.SnapshotDALDataset
 mport com.tw ter. nteract on_graph.sc o.common.Convers onUt l.hasNegat veFeatures
 mport com.tw ter. nteract on_graph.sc o.common.Convers onUt l.toRealGraphEdgeFeatures
 mport com.tw ter. nteract on_graph.sc o.common.FeatureGeneratorUt l.getEdgeFeature
 mport com.tw ter. nteract on_graph.sc o.common.GraphUt l
 mport com.tw ter. nteract on_graph.sc o.common. nteract onGraphRaw nput
 mport com.tw ter. nteract on_graph.thr ftscala.Edge
 mport com.tw ter. nteract on_graph.thr ftscala.FeatureNa 
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal
 mport com.tw ter.sc o_ nternal.job.Sc oBeamJob
 mport com.tw ter.scrooge.Thr ftStruct
 mport com.tw ter.soc algraph.hadoop.Soc algraphUnfollowsScalaDataset
 mport com.tw ter.tcdc.bqblaster.beam.syntax._
 mport com.tw ter.tcdc.bqblaster.core.avro.TypedProject on
 mport com.tw ter.tcdc.bqblaster.core.transform.RootTransform
 mport com.tw ter.t  l nes.real_graph.thr ftscala.RealGraphFeaturesTest
 mport com.tw ter.t  l nes.real_graph.v1.thr ftscala.{RealGraphFeatures => RealGraphFeaturesV1}
 mport com.tw ter.user_sess on_store.thr ftscala.UserSess on
 mport flockdb_tools.datasets.flock.FlockBlocksEdgesScalaDataset
 mport flockdb_tools.datasets.flock.FlockMutesEdgesScalaDataset
 mport flockdb_tools.datasets.flock.FlockReportAsAbuseEdgesScalaDataset
 mport flockdb_tools.datasets.flock.FlockReportAsSpamEdgesScalaDataset
 mport java.t  . nstant
 mport org.apac .beam.sdk. o.gcp.b gquery.B gQuery O

object  nteract onGraphNegat veJob extends Sc oBeamJob[ nteract onGraphNegat veOpt on] {
  val maxDest nat on ds = 500 // p99  s about 500
  def getFeatureCounts(e: Edge):  nt = e.features.s ze
  val negat veEdgeOrder ng = Order ng.by[Edge,  nt](getFeatureCounts)
  val negat veEdgeReverseOrder ng = negat veEdgeOrder ng.reverse
   mpl c  val pqMono d: Pr or yQueueMono d[Edge] =
    new Pr or yQueueMono d[Edge](maxDest nat on ds)(negat veEdgeOrder ng)

  overr de protected def conf gureP pel ne(
    sc: Sc oContext,
    opts:  nteract onGraphNegat veOpt on
  ): Un  = {

    val endTs = opts. nterval.getEndM ll s

    // read  nput datasets
    val blocks: SCollect on[ nteract onGraphRaw nput] =
      GraphUt l.getFlockFeatures(
        readSnapshot(FlockBlocksEdgesScalaDataset, sc),
        FeatureNa .NumBlocks,
        endTs)

    val mutes: SCollect on[ nteract onGraphRaw nput] =
      GraphUt l.getFlockFeatures(
        readSnapshot(FlockMutesEdgesScalaDataset, sc),
        FeatureNa .NumMutes,
        endTs)

    val abuseReports: SCollect on[ nteract onGraphRaw nput] =
      GraphUt l.getFlockFeatures(
        readSnapshot(FlockReportAsAbuseEdgesScalaDataset, sc),
        FeatureNa .NumReportAsAbuses,
        endTs)

    val spamReports: SCollect on[ nteract onGraphRaw nput] =
      GraphUt l.getFlockFeatures(
        readSnapshot(FlockReportAsSpamEdgesScalaDataset, sc),
        FeatureNa .NumReportAsSpams,
        endTs)

    //   only keep unfollows  n t  past 90 days due to t  huge s ze of t  dataset,
    // and to prevent permanent "shadow-bann ng"  n t  event of acc dental unfollows.
    //   treat unfollows as less cr  cal than above 4 negat ve s gnals, s nce   deals more w h
    //  nterest than  alth typ cally, wh ch m ght change over t  .
    val unfollows: SCollect on[ nteract onGraphRaw nput] =
      GraphUt l
        .getSoc alGraphFeatures(
          readSnapshot(Soc algraphUnfollowsScalaDataset, sc),
          FeatureNa .NumUnfollows,
          endTs)
        .f lter(_.age < 90)

    // group all features by (src, dest)
    val allEdgeFeatures: SCollect on[Edge] =
      getEdgeFeature(SCollect on.un onAll(Seq(blocks, mutes, abuseReports, spamReports, unfollows)))

    val negat veFeatures: SCollect on[KeyVal[Long, UserSess on]] =
      allEdgeFeatures
        .keyBy(_.s ce d)
        .topByKey(maxDest nat on ds)(Order ng.by(_.features.s ze))
        .map {
          case (src d, pqEdges) =>
            val topKNeg =
              pqEdges.toSeq.flatMap(toRealGraphEdgeFeatures(hasNegat veFeatures))
            KeyVal(
              src d,
              UserSess on(
                user d = So (src d),
                realGraphFeaturesTest =
                  So (RealGraphFeaturesTest.V1(RealGraphFeaturesV1(topKNeg)))))
        }

    // save to GCS (v a DAL)
    negat veFeatures.saveAsCustomOutput(
      "Wr e Negat ve Edge Label",
      DAL.wr eVers onedKeyVal(
        dataset = RealGraphNegat veFeaturesScalaDataset,
        pathLa t = PathLa t.Vers onedPath(opts.getOutputPath),
         nstant =  nstant.ofEpochM ll (opts. nterval.getEndM ll s),
        wr eOpt on = Wr eOpt ons(numOfShards = So (3000))
      )
    )

    // save to BQ
    val  ngest onDate = opts.getDate().value.getStart.toDate
    val bqDataset = opts.getBqDataset
    val bqF eldsTransform = RootTransform
      .Bu lder()
      .w hPrependedF elds("dateH " -> TypedProject on.fromConstant( ngest onDate))
    val t  Part  on ng = new T  Part  on ng()
      .setType("DAY").setF eld("dateH ").setExp rat onMs(21.days. nM ll seconds)
    val bqWr er = B gQuery O
      .wr e[Edge]
      .to(s"${bqDataset}. nteract on_graph_agg_negat ve_edge_snapshot")
      .w hExtendedError nfo()
      .w hT  Part  on ng(t  Part  on ng)
      .w hLoadJobProject d("twttr-recos-ml-prod")
      .w hThr ftSupport(bqF eldsTransform.bu ld(), AvroConverter.Legacy)
      .w hCreateD spos  on(B gQuery O.Wr e.CreateD spos  on.CREATE_ F_NEEDED)
      .w hWr eD spos  on(
        B gQuery O.Wr e.Wr eD spos  on.WR TE_TRUNCATE
      ) //   only want t  latest snapshot

    allEdgeFeatures
      .saveAsCustomOutput(
        s"Save Recom ndat ons to BQ  nteract on_graph_agg_negat ve_edge_snapshot",
        bqWr er
      )
  }

  def readSnapshot[T <: Thr ftStruct](
    dataset: SnapshotDALDataset[T],
    sc: Sc oContext
  ): SCollect on[T] = {
    sc.custom nput(
      s"Read ng most recent snaphost ${dataset.role.na }.${dataset.log calNa }",
      DAL.readMostRecentSnapshotNoOlderThan[T](dataset, 7.days)
    )
  }
}
