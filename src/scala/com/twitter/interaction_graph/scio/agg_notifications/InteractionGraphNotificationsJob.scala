package com.tw ter. nteract on_graph.sc o.agg_not f cat ons

 mport com.spot fy.sc o.Sc oContext
 mport com.spot fy.sc o.values.SCollect on
 mport com.tw ter.beam. o.dal.DAL
 mport com.tw ter.beam. o.fs.mult format.D skFormat
 mport com.tw ter.beam. o.fs.mult format.PathLa t
 mport com.tw ter.beam. o.fs.mult format.ReadOpt ons
 mport com.tw ter.beam. o.fs.mult format.Wr eOpt ons
 mport com.tw ter.cl ent_event_f lter ng.Fr gateF lteredCl entEventsDataflowScalaDataset
 mport com.tw ter.cl entapp.thr ftscala.LogEvent
 mport com.tw ter. nteract on_graph.sc o.common.FeatureGeneratorUt l
 mport com.tw ter. nteract on_graph.thr ftscala._
 mport com.tw ter.sc o_ nternal.job.Sc oBeamJob
 mport com.tw ter.stateb rd.v2.thr ftscala.Env ron nt
 mport com.tw ter.t ets ce.publ c_t ets.Publ cT etsScalaDataset

object  nteract onGraphNot f cat onsJob extends Sc oBeamJob[ nteract onGraphNot f cat onsOpt on] {
  overr de protected def conf gureP pel ne(
    sc: Sc oContext,
    opts:  nteract onGraphNot f cat onsOpt on
  ): Un  = {

    val pushCl entEvents: SCollect on[LogEvent] = sc
      .custom nput(
        na  = "Read Push Cl ent Events",
        DAL
          .read(
            Fr gateF lteredCl entEventsDataflowScalaDataset,
            opts. nterval,
            DAL.Env ron nt.Prod,
          )
      )
    val pushNtabEvents =
      pushCl entEvents.flatMap( nteract onGraphNot f cat onUt l.getPushNtabEvents)

    // look back t ets for 2 days because MR gets t ets from 2 days ago.
    // Allow a grace per od of 24 h s to reduce oncall workload
    val graceH s = 24
    val  nterval2DaysBefore =
      opts. nterval.w hStart(opts. nterval.getStart.m nusDays(2).plusH s(graceH s))
    val t etAuthors: SCollect on[(Long, Long)] = sc
      .custom nput(
        na  = "Read T ets",
        DAL
          .read(
            dataset = Publ cT etsScalaDataset,
             nterval =  nterval2DaysBefore,
            env ron ntOverr de = DAL.Env ron nt.Prod,
            readOpt ons = ReadOpt ons(project ons = So (Seq("t et d", "user d")))
          )
      ).map { t => (t.t et d, t.user d) }

    val pushNtabEdgeCounts = pushNtabEvents
      .jo n(t etAuthors)
      .map {
        case (_, ((src d, feature), dest d)) => ((src d, dest d, feature), 1L)
      }
      .w hNa ("summ ng edge feature counts")
      .sumByKey

    val aggPushEdges = pushNtabEdgeCounts
      .map {
        case ((src d, dest d, featureNa ), count) =>
          (src d, dest d) -> Seq(
            EdgeFeature(featureNa , FeatureGeneratorUt l. n  al zeTSS(count)))
      }
      .sumByKey
      .map {
        case ((src d, dest d), edgeFeatures) =>
          Edge(src d, dest d, None, edgeFeatures.sortBy(_.na .value))
      }

    aggPushEdges.saveAsCustomOutput(
      "Wr e Edge Records",
      DAL.wr e[Edge](
         nteract onGraphAggNot f cat onsEdgeDa lyScalaDataset,
        PathLa t.Da lyPath(opts.getOutputPath + "/aggregated_not f cat ons_edge_da ly"),
        opts. nterval,
        D skFormat.Parquet,
        Env ron nt.valueOf(opts.getDALWr eEnv ron nt),
        wr eOpt on = Wr eOpt ons(numOfShards = So (opts.getNumberOfShards))
      )
    )
  }
}
