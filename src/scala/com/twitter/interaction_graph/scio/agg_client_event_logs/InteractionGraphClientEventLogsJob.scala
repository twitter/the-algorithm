package com.tw ter. nteract on_graph.sc o.agg_cl ent_event_logs

 mport com.spot fy.sc o.Sc oContext
 mport com.tw ter.beam. o.dal.DAL
 mport com.tw ter.beam. o.dal.DAL.D skFormat
 mport com.tw ter.beam. o.dal.DAL.Wr eOpt ons
 mport com.tw ter.beam. o.fs.mult format.PathLa t
 mport com.tw ter.beam.job.Serv ce dent f erOpt ons
 mport com.tw ter. nteract on_graph.sc o.common.UserUt l
 mport com.tw ter. nteract on_graph.thr ftscala.Edge
 mport com.tw ter. nteract on_graph.thr ftscala.Vertex
 mport com.tw ter.sc o_ nternal.job.Sc oBeamJob
 mport com.tw ter.stateb rd.v2.thr ftscala.Env ron nt
 mport org.joda.t  . nterval

object  nteract onGraphCl entEventLogsJob
    extends Sc oBeamJob[ nteract onGraphCl entEventLogsOpt on] {
  overr de protected def conf gureP pel ne(
    sc oContext: Sc oContext,
    p pel neOpt ons:  nteract onGraphCl entEventLogsOpt on
  ): Un  = {

    @trans ent
     mpl c  lazy val sc: Sc oContext = sc oContext
     mpl c  lazy val jobCounters:  nteract onGraphCl entEventLogsCountersTra  =
       nteract onGraphCl entEventLogsCounters

    lazy val date nterval:  nterval = p pel neOpt ons. nterval

    val s ces =  nteract onGraphCl entEventLogsS ce(p pel neOpt ons)

    val user nteract ons = s ces.readUser nteract ons(date nterval)
    val rawUsers = s ces.readComb nedUsers()
    val safeUsers = UserUt l.getVal dUsers(rawUsers)

    val (vertex, edges) =  nteract onGraphCl entEventLogsUt l.process(user nteract ons, safeUsers)

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
      DAL.wr e[Vertex](
         nteract onGraphAggCl entEventLogsVertexDa lyScalaDataset,
        PathLa t.Da lyPath(
          p pel neOpt ons.getOutputPath + "/aggregated_cl ent_event_logs_vertex_da ly"),
        date nterval,
        D skFormat.Parquet,
        Env ron nt.valueOf(dalWr eEnv ron nt),
        wr eOpt on =
          Wr eOpt ons(numOfShards = So ((p pel neOpt ons.getNumberOfShards / 32.0).ce l.to nt))
      )
    )

    edges.saveAsCustomOutput(
      "Wr e Edge Records",
      DAL.wr e[Edge](
         nteract onGraphAggCl entEventLogsEdgeDa lyScalaDataset,
        PathLa t.Da lyPath(
          p pel neOpt ons.getOutputPath + "/aggregated_cl ent_event_logs_edge_da ly"),
        date nterval,
        D skFormat.Parquet,
        Env ron nt.valueOf(dalWr eEnv ron nt),
        wr eOpt on = Wr eOpt ons(numOfShards = So (p pel neOpt ons.getNumberOfShards))
      )
    )
  }
}
