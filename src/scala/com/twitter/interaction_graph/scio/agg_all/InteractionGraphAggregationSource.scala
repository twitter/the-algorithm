package com.tw ter. nteract on_graph.sc o.agg_all

 mport com.spot fy.sc o.Sc oContext
 mport com.spot fy.sc o.values.SCollect on
 mport com.tw ter.beam. o.dal.DAL
 mport com.tw ter.beam. o.dal.DAL.ReadOpt ons
 mport com.tw ter.beam.job.Serv ce dent f erOpt ons
 mport com.tw ter.dal.cl ent.dataset.SnapshotDALDatasetBase
 mport com.tw ter.dal.cl ent.dataset.T  Part  onedDALDataset
 mport com.tw ter. nteract on_graph.sc o.agg_address_book. nteract onGraphAggAddressBookEdgeSnapshotScalaDataset
 mport com.tw ter. nteract on_graph.sc o.agg_address_book. nteract onGraphAggAddressBookVertexSnapshotScalaDataset
 mport com.tw ter. nteract on_graph.sc o.agg_cl ent_event_logs. nteract onGraphAggCl entEventLogsEdgeDa lyScalaDataset
 mport com.tw ter. nteract on_graph.sc o.agg_cl ent_event_logs. nteract onGraphAggCl entEventLogsVertexDa lyScalaDataset
 mport com.tw ter. nteract on_graph.sc o.agg_d rect_ nteract ons. nteract onGraphAggD rect nteract onsEdgeDa lyScalaDataset
 mport com.tw ter. nteract on_graph.sc o.agg_d rect_ nteract ons. nteract onGraphAggD rect nteract onsVertexDa lyScalaDataset
 mport com.tw ter. nteract on_graph.sc o.agg_flock. nteract onGraphAggFlockEdgeSnapshotScalaDataset
 mport com.tw ter. nteract on_graph.sc o.agg_flock. nteract onGraphAggFlockVertexSnapshotScalaDataset
 mport com.tw ter. nteract on_graph.thr ftscala.Edge
 mport com.tw ter. nteract on_graph.thr ftscala.Vertex
 mport com.tw ter.stateb rd.v2.thr ftscala.Env ron nt
 mport com.tw ter.users ce.snapshot.flat.Users ceFlatScalaDataset
 mport com.tw ter.users ce.snapshot.flat.thr ftscala.FlatUser
 mport com.tw ter.ut l.Durat on
 mport org.joda.t  . nterval

case class  nteract onGraphAggregat onS ce(
  p pel neOpt ons:  nteract onGraphAggregat onOpt on
)(
   mpl c  sc: Sc oContext) {
  val dalEnv ron nt: Str ng = p pel neOpt ons
    .as(classOf[Serv ce dent f erOpt ons])
    .getEnv ron nt()

  def readDALDataset[T: Man fest](
    dataset: T  Part  onedDALDataset[T],
     nterval:  nterval,
    dalEnv ron nt: Str ng,
    project ons: Opt on[Seq[Str ng]] = None
  )(
     mpl c  sc: Sc oContext,
  ): SCollect on[T] = {
    sc.custom nput(
      s"Read ng ${dataset.role.na }.${dataset.log calNa }",
      DAL.read[T](
        dataset = dataset,
         nterval =  nterval,
        env ron ntOverr de = Env ron nt.valueOf(dalEnv ron nt),
        readOpt ons = ReadOpt ons(project ons)
      )
    )
  }

  def readMostRecentSnapshotDALDataset[T: Man fest](
    dataset: SnapshotDALDatasetBase[T],
    date nterval:  nterval,
    dalEnv ron nt: Str ng,
    project ons: Opt on[Seq[Str ng]] = None
  )(
     mpl c  sc: Sc oContext,
  ): SCollect on[T] = {
    sc.custom nput(
      s"Read ng most recent snapshot ${dataset.role.na }.${dataset.log calNa }",
      DAL.readMostRecentSnapshot[T](
        dataset,
        date nterval,
        Env ron nt.valueOf(dalEnv ron nt),
        readOpt ons = ReadOpt ons(project ons)
      )
    )
  }

  def readMostRecentSnapshotNoOlderThanDALDataset[T: Man fest](
    dataset: SnapshotDALDatasetBase[T],
    noOlderThan: Durat on,
    dalEnv ron nt: Str ng,
    project ons: Opt on[Seq[Str ng]] = None
  )(
     mpl c  sc: Sc oContext,
  ): SCollect on[T] = {
    sc.custom nput(
      s"Read ng most recent snapshot ${dataset.role.na }.${dataset.log calNa }",
      DAL.readMostRecentSnapshotNoOlderThan[T](
        dataset,
        noOlderThan,
        env ron ntOverr de = Env ron nt.valueOf(dalEnv ron nt),
        readOpt ons = ReadOpt ons(project ons)
      )
    )
  }

  def readAddressBookFeatures(): (SCollect on[Edge], SCollect on[Vertex]) = {
    val edges = readMostRecentSnapshotNoOlderThanDALDataset[Edge](
      dataset =  nteract onGraphAggAddressBookEdgeSnapshotScalaDataset,
      noOlderThan = Durat on.fromDays(5),
      dalEnv ron nt = dalEnv ron nt,
    )

    val vertex = readMostRecentSnapshotNoOlderThanDALDataset[Vertex](
      dataset =  nteract onGraphAggAddressBookVertexSnapshotScalaDataset,
      noOlderThan = Durat on.fromDays(5),
      dalEnv ron nt = dalEnv ron nt,
    )

    (edges, vertex)
  }

  def readCl entEventLogsFeatures(
    date nterval:  nterval
  ): (SCollect on[Edge], SCollect on[Vertex]) = {
    val edges = readDALDataset[Edge](
      dataset =  nteract onGraphAggCl entEventLogsEdgeDa lyScalaDataset,
      dalEnv ron nt = dalEnv ron nt,
       nterval = date nterval
    )

    val vertex = readDALDataset[Vertex](
      dataset =  nteract onGraphAggCl entEventLogsVertexDa lyScalaDataset,
      dalEnv ron nt = dalEnv ron nt,
       nterval = date nterval
    )

    (edges, vertex)
  }

  def readD rect nteract onsFeatures(
    date nterval:  nterval
  ): (SCollect on[Edge], SCollect on[Vertex]) = {
    val edges = readDALDataset[Edge](
      dataset =  nteract onGraphAggD rect nteract onsEdgeDa lyScalaDataset,
      dalEnv ron nt = dalEnv ron nt,
       nterval = date nterval
    )

    val vertex = readDALDataset[Vertex](
      dataset =  nteract onGraphAggD rect nteract onsVertexDa lyScalaDataset,
      dalEnv ron nt = dalEnv ron nt,
       nterval = date nterval
    )

    (edges, vertex)
  }

  def readFlockFeatures(): (SCollect on[Edge], SCollect on[Vertex]) = {
    val edges = readMostRecentSnapshotNoOlderThanDALDataset[Edge](
      dataset =  nteract onGraphAggFlockEdgeSnapshotScalaDataset,
      noOlderThan = Durat on.fromDays(5),
      dalEnv ron nt = dalEnv ron nt,
    )

    val vertex = readMostRecentSnapshotNoOlderThanDALDataset[Vertex](
      dataset =  nteract onGraphAggFlockVertexSnapshotScalaDataset,
      noOlderThan = Durat on.fromDays(5),
      dalEnv ron nt = dalEnv ron nt,
    )

    (edges, vertex)
  }

  def readAggregatedFeatures(date nterval:  nterval): (SCollect on[Edge], SCollect on[Vertex]) = {
    val edges = readMostRecentSnapshotDALDataset[Edge](
      dataset =  nteract onGraph toryAggregatedEdgeSnapshotScalaDataset,
      dalEnv ron nt = dalEnv ron nt,
      date nterval = date nterval
    )

    val vertex = readMostRecentSnapshotDALDataset[Vertex](
      dataset =  nteract onGraph toryAggregatedVertexSnapshotScalaDataset,
      dalEnv ron nt = dalEnv ron nt,
      date nterval = date nterval
    )

    (edges, vertex)
  }

  def readFlatUsers(): SCollect on[FlatUser] =
    readMostRecentSnapshotNoOlderThanDALDataset[FlatUser](
      dataset = Users ceFlatScalaDataset,
      noOlderThan = Durat on.fromDays(5),
      dalEnv ron nt = dalEnv ron nt,
      project ons = So (Seq(" d", "val d_user"))
    )
}
