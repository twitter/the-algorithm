package com.tw ter. nteract on_graph.sc o.agg_d rect_ nteract ons

 mport com.spot fy.sc o.Sc oContext
 mport com.tw ter.beam. o.dal.DAL
 mport com.tw ter.beam. o.dal.DAL.D skFormat
 mport com.tw ter.beam. o.fs.mult format.PathLa t
 mport com.tw ter.beam. o.fs.mult format.Wr eOpt ons
 mport com.tw ter.beam.job.Serv ce dent f erOpt ons
 mport com.tw ter. nteract on_graph.sc o.common.UserUt l
 mport com.tw ter. nteract on_graph.thr ftscala.Edge
 mport com.tw ter. nteract on_graph.thr ftscala.Vertex
 mport com.tw ter.sc o_ nternal.job.Sc oBeamJob
 mport com.tw ter.stateb rd.v2.thr ftscala.Env ron nt
 mport org.joda.t  . nterval

object  nteract onGraphAggD rect nteract onsJob
    extends Sc oBeamJob[ nteract onGraphAggD rect nteract onsOpt on] {
  overr de protected def conf gureP pel ne(
    sc oContext: Sc oContext,
    p pel neOpt ons:  nteract onGraphAggD rect nteract onsOpt on
  ): Un  = {
    @trans ent
     mpl c  lazy val sc: Sc oContext = sc oContext
     mpl c  lazy val date nterval:  nterval = p pel neOpt ons. nterval

    val dalEnv ron nt: Str ng = p pel neOpt ons
      .as(classOf[Serv ce dent f erOpt ons])
      .getEnv ron nt()
    val dalWr eEnv ron nt =  f (p pel neOpt ons.getDALWr eEnv ron nt != null) {
      p pel neOpt ons.getDALWr eEnv ron nt
    } else {
      dalEnv ron nt
    }

    val s ce =  nteract onGraphAggD rect nteract onsS ce(p pel neOpt ons)

    val rawUsers = s ce.readComb nedUsers()
    val safeUsers = UserUt l.getVal dUsers(rawUsers)

    val rawFavor es = s ce.readFavor es(date nterval)
    val rawPhotoTags = s ce.readPhotoTags(date nterval)
    val t etS ce = s ce.readT etS ce(date nterval)

    val (vertex, edges) =  nteract onGraphAggD rect nteract onsUt l.process(
      rawFavor es,
      t etS ce,
      rawPhotoTags,
      safeUsers
    )

    vertex.saveAsCustomOutput(
      "Wr e Vertex Records",
      DAL.wr e[Vertex](
         nteract onGraphAggD rect nteract onsVertexDa lyScalaDataset,
        PathLa t.Da lyPath(
          p pel neOpt ons.getOutputPath + "/aggregated_d rect_ nteract ons_vertex_da ly"),
        date nterval,
        D skFormat.Parquet,
        Env ron nt.valueOf(dalWr eEnv ron nt),
        wr eOpt on =
          Wr eOpt ons(numOfShards = So ((p pel neOpt ons.getNumberOfShards / 8.0).ce l.to nt))
      )
    )

    edges.saveAsCustomOutput(
      "Wr e Edge Records",
      DAL.wr e[Edge](
         nteract onGraphAggD rect nteract onsEdgeDa lyScalaDataset,
        PathLa t.Da lyPath(
          p pel neOpt ons.getOutputPath + "/aggregated_d rect_ nteract ons_edge_da ly"),
        date nterval,
        D skFormat.Parquet,
        Env ron nt.valueOf(dalWr eEnv ron nt),
        wr eOpt on = Wr eOpt ons(numOfShards = So (p pel neOpt ons.getNumberOfShards))
      )
    )

  }
}
