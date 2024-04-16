package com.tw ter. nteract on_graph.sc o.agg_flock

 mport com.spot fy.sc o.Sc oContext
 mport com.spot fy.sc o.values.SCollect on
 mport com.tw ter.beam. o.dal.DAL
 mport com.tw ter.beam. o.dal.DAL.D skFormat
 mport com.tw ter.beam. o.dal.DAL.PathLa t
 mport com.tw ter.beam. o.dal.DAL.Wr eOpt ons
 mport com.tw ter.beam.job.Serv ce dent f erOpt ons
 mport com.tw ter. nteract on_graph.sc o.agg_flock. nteract onGraphAggFlockUt l._
 mport com.tw ter. nteract on_graph.sc o.common.DateUt l
 mport com.tw ter. nteract on_graph.sc o.common.FeatureGeneratorUt l
 mport com.tw ter. nteract on_graph.thr ftscala.Edge
 mport com.tw ter. nteract on_graph.thr ftscala.FeatureNa 
 mport com.tw ter. nteract on_graph.thr ftscala.Vertex
 mport com.tw ter.sc o_ nternal.job.Sc oBeamJob
 mport com.tw ter.stateb rd.v2.thr ftscala.Env ron nt
 mport com.tw ter.ut l.Durat on
 mport java.t  . nstant
 mport org.joda.t  . nterval

object  nteract onGraphAggFlockJob extends Sc oBeamJob[ nteract onGraphAggFlockOpt on] {
  overr de protected def conf gureP pel ne(
    sc oContext: Sc oContext,
    p pel neOpt ons:  nteract onGraphAggFlockOpt on
  ): Un  = {
    @trans ent
     mpl c  lazy val sc: Sc oContext = sc oContext
     mpl c  lazy val date nterval:  nterval = p pel neOpt ons. nterval

    val s ce =  nteract onGraphAggFlockS ce(p pel neOpt ons)

    val emb ggen nterval = DateUt l.emb ggen(date nterval, Durat on.fromDays(7))

    val flockFollowsSnapshot = s ce.readFlockFollowsSnapshot(emb ggen nterval)

    // t  flock snapshot  're read ng from has already been f ltered for safe/val d users  nce no f lter ng for safeUsers
    val flockFollowsFeature =
      getFlockFeatures(flockFollowsSnapshot, FeatureNa .NumFollows, date nterval)

    val flockMutualFollowsFeature = getMutualFollowFeature(flockFollowsFeature)

    val allSCollect ons = Seq(flockFollowsFeature, flockMutualFollowsFeature)

    val allFeatures = SCollect on.un onAll(allSCollect ons)

    val (vertex, edges) = FeatureGeneratorUt l.getFeatures(allFeatures)

    val dalEnv ron nt: Str ng = p pel neOpt ons
      .as(classOf[Serv ce dent f erOpt ons])
      .getEnv ron nt()
    val dalWr eEnv ron nt =  f (p pel neOpt ons.getDALWr eEnv ron nt != null) {
      p pel neOpt ons.getDALWr eEnv ron nt
    } else {
      dalEnv ron nt
    }

    vertex.saveAsCustomOutput(
      "Wr e Vertex Records",
      DAL.wr eSnapshot[Vertex](
         nteract onGraphAggFlockVertexSnapshotScalaDataset,
        PathLa t.Da lyPath(p pel neOpt ons.getOutputPath + "/aggregated_flock_vertex_da ly"),
         nstant.ofEpochM ll (date nterval.getEndM ll s),
        D skFormat.Parquet,
        Env ron nt.valueOf(dalWr eEnv ron nt),
        wr eOpt on =
          Wr eOpt ons(numOfShards = So ((p pel neOpt ons.getNumberOfShards / 64.0).ce l.to nt))
      )
    )

    edges.saveAsCustomOutput(
      "Wr e Edge Records",
      DAL.wr eSnapshot[Edge](
         nteract onGraphAggFlockEdgeSnapshotScalaDataset,
        PathLa t.Da lyPath(p pel neOpt ons.getOutputPath + "/aggregated_flock_edge_da ly"),
         nstant.ofEpochM ll (date nterval.getEndM ll s),
        D skFormat.Parquet,
        Env ron nt.valueOf(dalWr eEnv ron nt),
        wr eOpt on = Wr eOpt ons(numOfShards = So (p pel neOpt ons.getNumberOfShards))
      )
    )

  }
}
