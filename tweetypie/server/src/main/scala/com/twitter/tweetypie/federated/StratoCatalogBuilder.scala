package com.tw ter.t etyp e.federated

 mport com.tw ter.ads. nternal.pcl.serv ce.CallbackPromotedContentLogger
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.scrooge.Thr ftStructF eld nfo
 mport com.tw ter.servo.ut l.Gate
 mport com.tw ter.strato.catalog.Catalog
 mport com.tw ter.strato.cl ent.Cl ent
 mport com.tw ter.strato.fed.StratoFed
 mport com.tw ter.strato.thr ft.ScroogeConv
 mport com.tw ter.t etyp e.Thr ftT etServ ce
 mport com.tw ter.t etyp e.T et
 mport com.tw ter.t etyp e.backends.G zmoduck
 mport com.tw ter.t etyp e.federated.columns._
 mport com.tw ter.t etyp e.federated.context.GetRequestContext
 mport com.tw ter.t etyp e.federated.prefetc ddata.Prefetc dDataRepos oryBu lder
 mport com.tw ter.t etyp e.federated.promotedcontent.T etPromotedContentLogger
 mport com.tw ter.t etyp e.repos ory.Un nt on nfoRepos ory
 mport com.tw ter.t etyp e.repos ory.V beRepos ory
 mport com.tw ter.ut l.Act v y
 mport com.tw ter.ut l.logg ng.Logger

object StratoCatalogBu lder {

  def catalog(
    thr ftT etServ ce: Thr ftT etServ ce,
    stratoCl ent: Cl ent,
    getUserResultsBy d: G zmoduck.GetBy d,
    callbackPromotedContentLogger: CallbackPromotedContentLogger,
    statsRece ver: StatsRece ver,
    enableCommun yT etCreatesDec der: Gate[Un ],
  ): Act v y[Catalog[StratoFed.Column]] = {
    val log = Logger(getClass)

    val getRequestContext = new GetRequestContext()
    val prefetc dDataRepos ory =
      Prefetc dDataRepos oryBu lder(getUserResultsBy d, statsRece ver)
    val un nt on nfoRepos ory = Un nt on nfoRepos ory(stratoCl ent)
    val v beRepos ory = V beRepos ory(stratoCl ent)

    val t etPromotedContentLogger =
      T etPromotedContentLogger(callbackPromotedContentLogger)

    // A st ch group bu lder to be used for Federated F eld Column requests. T  handler must be t  sa  across
    // all Federated F eld Columns to ensure requests are batc d across columns for d fferent f elds
    val federatedF eldGroupBu lder: FederatedF eldGroupBu lder.Type = FederatedF eldGroupBu lder(
      thr ftT etServ ce.getT etF elds)

    val columns: Seq[StratoFed.Column] = Seq(
      new Unret etColumn(
        thr ftT etServ ce.unret et,
        getRequestContext,
      ),
      new CreateRet etColumn(
        thr ftT etServ ce.postRet et,
        getRequestContext,
        prefetc dDataRepos ory,
        t etPromotedContentLogger,
        statsRece ver
      ),
      new CreateT etColumn(
        thr ftT etServ ce.postT et,
        getRequestContext,
        prefetc dDataRepos ory,
        un nt on nfoRepos ory,
        v beRepos ory,
        t etPromotedContentLogger,
        statsRece ver,
        enableCommun yT etCreatesDec der,
      ),
      new DeleteT etColumn(
        thr ftT etServ ce.deleteT ets,
        getRequestContext,
      ),
      new GetT etF eldsColumn(thr ftT etServ ce.getT etF elds, statsRece ver),
      new GetStoredT etsColumn(thr ftT etServ ce.getStoredT ets),
      new GetStoredT etsByUserColumn(thr ftT etServ ce.getStoredT etsByUser)
    )

    // Gat r t et f eld  ds that are el g ble to be federated f eld columns
    val federatedF eld nfos =
      T et.f eld nfos
        .f lter(( nfo: Thr ftStructF eld nfo) =>
          FederatedF eldColumn. sFederatedF eld( nfo.tf eld. d))

    //  nstant ate t  federated f eld columns
    val federatedF eldColumns: Seq[FederatedF eldColumn] =
      federatedF eld nfos.map { f eld nfo: Thr ftStructF eld nfo =>
        val path = FederatedF eldColumn.makeColumnPath(f eld nfo.tf eld)
        val stratoType = ScroogeConv.typeOfF eld nfo(f eld nfo)
        log. nfo(f"creat ng federated column: $path")
        new FederatedF eldColumn(
          federatedF eldGroupBu lder,
          thr ftT etServ ce.setAdd  onalF elds,
          stratoType,
          f eld nfo.tf eld,
        )
      }

    //  nstant ate t  federated V1 f eld columns
    val federatedV1F eldColumns: Seq[FederatedF eldColumn] =
      federatedF eld nfos
        .f lter(f => FederatedF eldColumn. sM grat onFederatedF eld(f.tf eld))
        .map { f eld nfo: Thr ftStructF eld nfo =>
          val v1Path = FederatedF eldColumn.makeV1ColumnPath(f eld nfo.tf eld)
          val stratoType = ScroogeConv.typeOfF eld nfo(f eld nfo)
          log. nfo(f"creat ng V1 federated column: $v1Path")
          new FederatedF eldColumn(
            federatedF eldGroupBu lder,
            thr ftT etServ ce.setAdd  onalF elds,
            stratoType,
            f eld nfo.tf eld,
            So (v1Path)
          )
        }

    // Comb ne t  dynam c and hard coded federated columns
    val allColumns: Seq[StratoFed.Column] =
      columns ++ federatedF eldColumns ++ federatedV1F eldColumns

    Act v y.value(
      Catalog(
        allColumns.map { column =>
          column.path -> column
        }: _*
      ))
  }
}
