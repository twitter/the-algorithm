package com.tw ter.t etyp e
package conf g

 mport com.tw ter.coreserv ces.fa led_task.wr er.Fa ledTaskWr er
 mport com.tw ter.featuresw c s.v2.FeatureSw c s
 mport com.tw ter.flockdb.cl ent._
 mport com.tw ter.servo.forked
 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.servo.ut l.Scr be
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.cl ent_ d.Cl ent d lper
 mport com.tw ter.t etyp e.handler._
 mport com.tw ter.t etyp e.repos ory._
 mport com.tw ter.t etyp e.serv ce.Repl cat ngT etServ ce
 mport com.tw ter.t etyp e.serv ce._
 mport com.tw ter.t etyp e.storage.T etStorageCl ent
 mport com.tw ter.t etyp e.storage.T etStorageCl ent.GetT et
 mport com.tw ter.t etyp e.store._
 mport com.tw ter.t etyp e.thr ftscala._
 mport com.tw ter.ut l.Act v y
 mport com.tw ter.ut l.T  r

/**
 * Bu lds a fully conf gured Thr ftT etServ ce  nstance.
 *
 * T  core of t  t et serv ce  s a D spatch ngT etServ ce, wh ch  s respons ble
 * for d spatch ng requests to underly ng handlers and stores.
 * T  D spatch ngT etServ ce  nstance  s wrapped  n:
 * - ObservedT etServ ce        (adds stats count ng)
 * - Cl entHandl ngT etServ ce  (aut nt cat on, except on handl ng, etc)
 * - Repl cat ngT etServ ce     (repl cates so  reads)
 *
 * T etServ ceBu lder returns an Act v y[Thr ftT etServ ce] wh ch updates
 * on conf g changes. See Dynam cConf g.scala for more deta ls.
 */
object T etServ ceBu lder {
  def apply(
    sett ngs: T etServ ceSett ngs,
    statsRece ver: StatsRece ver,
    t  r: T  r,
    dec derGates: T etyp eDec derGates,
    featureSw c sW hExper  nts: FeatureSw c s,
    featureSw c sW houtExper  nts: FeatureSw c s,
    backendCl ents: BackendCl ents,
    cl ent d lper: Cl ent d lper,
  ): Act v y[Thr ftT etServ ce] = {
    // a forward reference, w ll be set to t  D spatch ngT etServ ce once created
    val syncT etServ ce = new MutableT etServ ceProxy(null)

    val t etServ ceScope = statsRece ver.scope("t et_serv ce")

    val d spatch ngT etServ ce =
      D spatch ngT etServ ceBu lder(
        sett ngs,
        statsRece ver,
        t etServ ceScope,
        syncT etServ ce,
        t  r,
        dec derGates,
        featureSw c sW hExper  nts,
        featureSw c sW houtExper  nts,
        backendCl ents,
        cl ent d lper,
      )

    val fa lureLogg ngT etServ ce =
      // Add t  fa lure wr  ng  ns de of t  author zat on f lter so
      // that   don't wr e out t  fa lures w n author zat on fa ls.
      new Fa lureLogg ngT etServ ce(
        fa ledTaskWr er = Fa ledTaskWr er("t etyp e_serv ce_fa lures",  dent y),
        underly ng = d spatch ngT etServ ce
      )

    val observedT etServ ce =
      new ObservedT etServ ce(fa lureLogg ngT etServ ce, t etServ ceScope, cl ent d lper)

    // Every t   conf g  s updated, create a new t et serv ce. Only
    // Cl entHandl ngT etServ ce and Repl cat ngT etServ ce need to
    // be recreated, as t  underly ng T etServ ces above don't depend
    // on t  conf g.
    Dynam cConf g(
      statsRece ver.scope("dynam c_conf g"),
      backendCl ents.conf gBus,
      sett ngs
    ).map { dynam cConf g =>
      val cl entHandl ngT etServ ce =
        new Cl entHandl ngT etServ ce(
          observedT etServ ce,
          t etServ ceScope,
          dynam cConf g.loadS dEl g ble,
          dec derGates.s dReadTraff cVoluntar ly,
          Cl entHandl ngT etServ ceAuthor zer(
            sett ngs = sett ngs,
            dynam cConf g = dynam cConf g,
            statsRece ver = statsRece ver
          ),
          GetT etsAuthor zer(
            conf g = dynam cConf g,
            maxRequestS ze = sett ngs.maxGetT etsRequestS ze,
             nstanceCount = sett ngs. nstanceCount,
            enforceRateL m edCl ents = dec derGates.enforceRateL m edCl ents,
            maxRequestW dthEnabled = dec derGates.maxRequestW dthEnabled,
            statsRece ver = t etServ ceScope.scope("get_t ets"),
          ),
          GetT etF eldsAuthor zer(
            conf g = dynam cConf g,
            maxRequestS ze = sett ngs.maxGetT etsRequestS ze,
             nstanceCount = sett ngs. nstanceCount,
            enforceRateL m edCl ents = dec derGates.enforceRateL m edCl ents,
            maxRequestW dthEnabled = dec derGates.maxRequestW dthEnabled,
            statsRece ver = t etServ ceScope.scope("get_t et_f elds"),
          ),
          RequestS zeAuthor zer(sett ngs.maxRequestS ze, dec derGates.maxRequestW dthEnabled),
          cl ent d lper,
        )

      syncT etServ ce.underly ng = cl entHandl ngT etServ ce

      val repl cat ngServ ce =
         f (!sett ngs.enableRepl cat on)
          cl entHandl ngT etServ ce
        else {
          new Repl cat ngT etServ ce(
            underly ng = cl entHandl ngT etServ ce,
            repl cat onTargets = backendCl ents.lowQoSRepl cat onCl ents,
            executor = new forked.QueueExecutor(
              100,
              statsRece ver.scope("repl cat ng_t et_serv ce")
            ),
          )
        }

      repl cat ngServ ce
    }
  }
}

object D spatch ngT etServ ceBu lder {
  val has d a: T et => Boolean =  d a ndex lper(Res ces.loadPartner d aRegexes())

  def apply(
    sett ngs: T etServ ceSett ngs,
    statsRece ver: StatsRece ver,
    t etServ ceScope: StatsRece ver,
    syncT etServ ce: Thr ftT etServ ce,
    t  r: T  r,
    dec derGates: T etyp eDec derGates,
    featureSw c sW hExper  nts: FeatureSw c s,
    featureSw c sW houtExper  nts: FeatureSw c s,
    backendCl ents: BackendCl ents,
    cl ent d lper: Cl ent d lper,
  ): Thr ftT etServ ce = {
    val (sync nvocat onBu lder, async nvocat onBu lder) = {
      val b =
        new Serv ce nvocat onBu lder(syncT etServ ce, sett ngs.s mulateDeferredrpcCallbacks)
      (b.w hCl ent d(sett ngs.thr ftCl ent d), b.w hCl ent d(sett ngs.deferredrpcCl ent d))
    }

    val t etKeyFactory = T etKeyFactory(sett ngs.t etKeyCac Vers on)

    val cac s =
       f (!sett ngs.w hCac )
        Cac s.NoCac 
      else
        Cac s(
          sett ngs = sett ngs,
          stats = statsRece ver,
          t  r = t  r,
          cl ents = backendCl ents,
          t etKeyFactory = t etKeyFactory,
          dec derGates = dec derGates,
          cl ent d lper = cl ent d lper,
        )

    val log calRepos =
      Log calRepos or es(
        sett ngs = sett ngs,
        stats = statsRece ver,
        t  r = t  r,
        dec derGates = dec derGates,
        external = new ExternalServ ceRepos or es(
          cl ents = backendCl ents,
          statsRece ver = statsRece ver,
          sett ngs = sett ngs,
          cl ent d lper = cl ent d lper,
        ),
        cac s = cac s,
        stratoCl ent = backendCl ents.stratoserverCl ent,
        has d a = has d a,
        cl ent d lper = cl ent d lper,
        featureSw c sW houtExper  nts = featureSw c sW houtExper  nts,
      )

    val t etCreat onLock =
      new Cac BasedT etCreat onLock(
        cac  = cac s.t etCreateLockerCac ,
        maxTr es = 3,
        stats = statsRece ver.scope("t et_save").scope("locker"),
        logUn queness d =
           f (sett ngs.scr beUn queness ds) Cac BasedT etCreat onLock.Scr beUn queness d
          else Cac BasedT etCreat onLock.LogUn queness d
      )

    val t etStores =
      T etStores(
        sett ngs = sett ngs,
        statsRece ver = statsRece ver,
        t  r = t  r,
        dec derGates = dec derGates,
        t etKeyFactory = t etKeyFactory,
        cl ents = backendCl ents,
        cac s = cac s,
        asyncBu lder = async nvocat onBu lder,
        has d a = has d a,
        cl ent d lper = cl ent d lper,
      )

    val t etDeletePathHandler =
      new DefaultT etDeletePathHandler(
        t etServ ceScope,
        log calRepos.t etResultRepo,
        log calRepos.opt onalUserRepo,
        log calRepos.stratoSafetyLabelsRepo,
        log calRepos.lastQuoteOfQuoterRepo,
        t etStores,
        getPerspect ves = backendCl ents.t  l neServ ce.getPerspect ves,
      )

    val t etBu lders =
      T etBu lders(
        sett ngs = sett ngs,
        statsRece ver = statsRece ver,
        dec derGates = dec derGates,
        featureSw c sW hExper  nts = featureSw c sW hExper  nts,
        cl ents = backendCl ents,
        cac s = cac s,
        repos = log calRepos,
        t etStore = t etStores,
        has d a = has d a,
        unret etEd s = t etDeletePathHandler.unret etEd s,
      )

    val hydrateT etFor nsert =
      Wr ePathHydrat on.hydrateT et(
        log calRepos.t etHydrators.hydrator,
        statsRece ver.scope(" nsert_t et")
      )

    val defaultT etQueryOpt ons = T etQuery.Opt ons( nclude = GetT etsHandler.Base nclude)

    val parentUser dRepo: ParentUser dRepos ory.Type =
      ParentUser dRepos ory(
        t etRepo = log calRepos.t etRepo
      )

    val undeleteT etHandler =
      UndeleteT etHandlerBu lder(
        backendCl ents.t etStorageCl ent,
        log calRepos,
        t etStores,
        parentUser dRepo,
        statsRece ver
      )

    val eraseUserT etsHandler =
      EraseUserT etsHandlerBu lder(
        backendCl ents,
        async nvocat onBu lder,
        dec derGates,
        sett ngs,
        t  r,
        t etDeletePathHandler,
        t etServ ceScope
      )

    val setRet etV s b l yHandler =
      SetRet etV s b l yHandler(
        t etGetter =
          T etRepos ory.t etGetter(log calRepos.opt onalT etRepo, defaultT etQueryOpt ons),
        t etStores.setRet etV s b l y
      )

    val takedownHandler =
      TakedownHandlerBu lder(
        log calRepos = log calRepos,
        t etStores = t etStores
      )

    val updatePoss blySens  veT etHandler =
      UpdatePoss blySens  veT etHandler(
        HandlerError.getRequ red(
          T etRepos ory.t etGetter(log calRepos.opt onalT etRepo, defaultT etQueryOpt ons),
          HandlerError.t etNotFoundExcept on
        ),
        HandlerError.getRequ red(
          FutureArrow(
            UserRepos ory
              .userGetter(
                log calRepos.opt onalUserRepo,
                UserQueryOpt ons(Set(UserF eld.Safety), UserV s b l y.All)
              )
              .compose(UserKey.by d)
          ),
          HandlerError.userNotFoundExcept on
        ),
        t etStores.updatePoss blySens  veT et
      )

    val userTakedownHandler =
      UserTakedownHandlerBu lder(
        log calRepos = log calRepos,
        t etStores = t etStores,
        stats = t etServ ceScope
      )

    val getDeletedT etsHandler =
      GetDeletedT etsHandler(
        getDeletedT ets = backendCl ents.t etStorageCl ent.getDeletedT ets,
        t etsEx st =
          GetDeletedT etsHandler.t etsEx st(backendCl ents.t etStorageCl ent.getT et),
        stats = t etServ ceScope.scope("get_deleted_t ets_handler")
      )

    val hydrateQuotedT et =
      Wr ePathHydrat on.hydrateQuotedT et(
        log calRepos.opt onalT etRepo,
        log calRepos.opt onalUserRepo,
        log calRepos.quoterHasAlreadyQuotedRepo
      )

    val deleteLocat onDataHandler =
      DeleteLocat onDataHandler(
        backendCl ents.geoScrubEventStore.getGeoScrubT  stamp,
        Scr be(DeleteLocat onData, "t etyp e_delete_locat on_data"),
        backendCl ents.deleteLocat onDataPubl s r
      )

    val getStoredT etsHandler = GetStoredT etsHandler(log calRepos.t etResultRepo)

    val getStoredT etsByUserHandler = GetStoredT etsByUserHandler(
      getStoredT etsHandler = getStoredT etsHandler,
      getStoredT et = backendCl ents.t etStorageCl ent.getStoredT et,
      selectPage = FutureArrow { select =>
        backendCl ents.tflockReadCl ent
          .selectPage(select, So (sett ngs.getStoredT etsByUserPageS ze))
      },
      maxPages = sett ngs.getStoredT etsByUserMaxPages
    )

    val getT etsHandler =
      GetT etsHandler(
        log calRepos.t etResultRepo,
        log calRepos.conta nerAsGetT etResultRepo,
        log calRepos.deletedT etV s b l yRepo,
        statsRece ver.scope("read_path"),
        dec derGates.shouldMater al zeConta ners
      )

    val getT etF eldsHandler =
      GetT etF eldsHandler(
        log calRepos.t etResultRepo,
        log calRepos.deletedT etV s b l yRepo,
        log calRepos.conta nerAsGetT etF eldsResultRepo,
        statsRece ver.scope("read_path"),
        dec derGates.shouldMater al zeConta ners
      )

    val unret etHandler =
      Unret etHandler(
        t etDeletePathHandler.deleteT ets,
        backendCl ents.t  l neServ ce.getPerspect ves,
        t etDeletePathHandler.unret etEd s,
        log calRepos.t etRepo,
      )

    val hydrate nsertEvent =
      Wr ePathHydrat on.hydrate nsertT etEvent(
        hydrateT et = hydrateT etFor nsert,
        hydrateQuotedT et = hydrateQuotedT et
      )

    val scrubGeoUpdateUserT  stampBu lder =
      ScrubGeoEventBu lder.UpdateUserT  stamp(
        stats = t etServ ceScope.scope("scrub_geo_update_user_t  stamp"),
        userRepo = log calRepos.opt onalUserRepo
      )

    val scrubGeoScrubT etsBu lder =
      ScrubGeoEventBu lder.ScrubT ets(
        stats = t etServ ceScope.scope("scrub_geo"),
        userRepo = log calRepos.opt onalUserRepo
      )

    val handlerF lter =
      PostT et
        .Dupl cateHandler(
          t etCreat onLock = t etCreat onLock,
          getT ets = getT etsHandler,
          stats = statsRece ver.scope("dupl cate")
        )
        .andT n(PostT et.RescueT etCreateFa lure)
        .andT n(PostT et.LogFa lures)

    val postT etHandler =
      handlerF lter[PostT etRequest](
        PostT et.Handler(
          t etBu lder = t etBu lders.t etBu lder,
          hydrate nsertEvent = hydrate nsertEvent,
          t etStore = t etStores,
        )
      )

    val postRet etHandler =
      handlerF lter[Ret etRequest](
        PostT et.Handler(
          t etBu lder = t etBu lders.ret etBu lder,
          hydrate nsertEvent = hydrate nsertEvent,
          t etStore = t etStores,
        )
      )

    val quotedT etDeleteBu lder: QuotedT etDeleteEventBu lder.Type =
      QuotedT etDeleteEventBu lder(log calRepos.opt onalT etRepo)

    val quotedT etTakedownBu lder: QuotedT etTakedownEventBu lder.Type =
      QuotedT etTakedownEventBu lder(log calRepos.opt onalT etRepo)

    val setAdd  onalF eldsBu lder: SetAdd  onalF eldsBu lder.Type =
      SetAdd  onalF eldsBu lder(
        t etRepo = log calRepos.t etRepo
      )

    val asyncSetAdd  onalF eldsBu lder: AsyncSetAdd  onalF eldsBu lder.Type =
      AsyncSetAdd  onalF eldsBu lder(
        userRepo = log calRepos.userRepo
      )

    val deleteAdd  onalF eldsBu lder: DeleteAdd  onalF eldsBu lder.Type =
      DeleteAdd  onalF eldsBu lder(
        t etRepo = log calRepos.t etRepo
      )

    val asyncDeleteAdd  onalF eldsBu lder: AsyncDeleteAdd  onalF eldsBu lder.Type =
      AsyncDeleteAdd  onalF eldsBu lder(
        userRepo = log calRepos.userRepo
      )

    new D spatch ngT etServ ce(
      asyncDeleteAdd  onalF eldsBu lder = asyncDeleteAdd  onalF eldsBu lder,
      asyncSetAdd  onalF eldsBu lder = asyncSetAdd  onalF eldsBu lder,
      deleteAdd  onalF eldsBu lder = deleteAdd  onalF eldsBu lder,
      deleteLocat onDataHandler = deleteLocat onDataHandler,
      deletePathHandler = t etDeletePathHandler,
      eraseUserT etsHandler = eraseUserT etsHandler,
      getDeletedT etsHandler = getDeletedT etsHandler,
      getStoredT etsHandler = getStoredT etsHandler,
      getStoredT etsByUserHandler = getStoredT etsByUserHandler,
      getT etsHandler = getT etsHandler,
      getT etF eldsHandler = getT etF eldsHandler,
      getT etCountsHandler = GetT etCountsHandler(log calRepos.t etCountsRepo),
      postT etHandler = postT etHandler,
      postRet etHandler = postRet etHandler,
      quotedT etDeleteBu lder = quotedT etDeleteBu lder,
      quotedT etTakedownBu lder = quotedT etTakedownBu lder,
      scrubGeoUpdateUserT  stampBu lder = scrubGeoUpdateUserT  stampBu lder,
      scrubGeoScrubT etsBu lder = scrubGeoScrubT etsBu lder,
      setAdd  onalF eldsBu lder = setAdd  onalF eldsBu lder,
      setRet etV s b l yHandler = setRet etV s b l yHandler,
      statsRece ver = statsRece ver,
      takedownHandler = takedownHandler,
      t etStore = t etStores,
      undeleteT etHandler = undeleteT etHandler,
      unret etHandler = unret etHandler,
      updatePoss blySens  veT etHandler = updatePoss blySens  veT etHandler,
      userTakedownHandler = userTakedownHandler,
      cl ent d lper = cl ent d lper,
    )
  }
}

object TakedownHandlerBu lder {
  type Type = FutureArrow[TakedownRequest, Un ]

  def apply(log calRepos: Log calRepos or es, t etStores: TotalT etStore) =
    TakedownHandler(
      getT et = HandlerError.getRequ red(
        t etGetter(log calRepos),
        HandlerError.t etNotFoundExcept on
      ),
      getUser = HandlerError.getRequ red(
        userGetter(log calRepos),
        HandlerError.userNotFoundExcept on
      ),
      wr eTakedown = t etStores.takedown
    )

  def t etGetter(log calRepos: Log calRepos or es): FutureArrow[T et d, Opt on[T et]] =
    FutureArrow(
      T etRepos ory.t etGetter(
        log calRepos.opt onalT etRepo,
        T etQuery.Opt ons(
           nclude = GetT etsHandler.Base nclude.also(
            t etF elds = Set(
              T et.T etyp eOnlyTakedownCountryCodesF eld. d,
              T et.T etyp eOnlyTakedownReasonsF eld. d
            )
          )
        )
      )
    )

  def userGetter(log calRepos: Log calRepos or es): FutureArrow[User d, Opt on[User]] =
    FutureArrow(
      UserRepos ory
        .userGetter(
          log calRepos.opt onalUserRepo,
          UserQueryOpt ons(
            Set(UserF eld.Roles, UserF eld.Safety, UserF eld.Takedowns),
            UserV s b l y.All
          )
        )
        .compose(UserKey.by d)
    )
}

object UserTakedownHandlerBu lder {
  def apply(
    log calRepos: Log calRepos or es,
    t etStores: TotalT etStore,
    stats: StatsRece ver
  ): UserTakedownHandler.Type =
    UserTakedownHandler(
      getT et = TakedownHandlerBu lder.t etGetter(log calRepos),
      t etTakedown = t etStores.takedown,
    )
}

object EraseUserT etsHandlerBu lder {
  def apply(
    backendCl ents: BackendCl ents,
    async nvocat onBu lder: Serv ce nvocat onBu lder,
    dec derGates: T etyp eDec derGates,
    sett ngs: T etServ ceSett ngs,
    t  r: T  r,
    t etDeletePathHandler: DefaultT etDeletePathHandler,
    t etServ ceScope: StatsRece ver
  ): EraseUserT etsHandler =
    EraseUserT etsHandler(
      selectPage(backendCl ents, sett ngs),
      deleteT et(t etDeletePathHandler),
      eraseUserT ets(backendCl ents, async nvocat onBu lder),
      t etServ ceScope.scope("erase_user_t ets"),
      sleep(dec derGates, sett ngs, t  r)
    )

  def selectPage(
    backendCl ents: BackendCl ents,
    sett ngs: T etServ ceSett ngs
  ): FutureArrow[Select[StatusGraph], PageResult[Long]] =
    FutureArrow(
      backendCl ents.tflockWr eCl ent.selectPage(_, So (sett ngs.eraseUserT etsPageS ze))
    )

  def deleteT et(
    t etDeletePathHandler: DefaultT etDeletePathHandler
  ): FutureEffect[(T et d, User d)] =
    FutureEffect[(T et d, User d)] {
      case (t et d, expectedUser d) =>
        t etDeletePathHandler
          . nternalDeleteT ets(
            request = DeleteT etsRequest(
              Seq(t et d),
               sUserErasure = true,
              expectedUser d = So (expectedUser d)
            ),
            byUser d = None,
            aut nt catedUser d = None,
            val date = t etDeletePathHandler.val dateT etsForUserErasureDaemon
          )
          .un 
    }

  def eraseUserT ets(
    backendCl ents: BackendCl ents,
    async nvocat onBu lder: Serv ce nvocat onBu lder
  ): FutureArrow[AsyncEraseUserT etsRequest, Un ] =
    async nvocat onBu lder
      .asyncV a(backendCl ents.asyncT etDelet onServ ce)
      . thod(_.asyncEraseUserT ets)

  def sleep(
    dec derGates: T etyp eDec derGates,
    sett ngs: T etServ ceSett ngs,
    t  r: T  r
  ): () => Future[Un ] =
    () =>
       f (dec derGates.delayEraseUserT ets()) {
        Future.sleep(sett ngs.eraseUserT etsDelay)(t  r)
      } else {
        Future.Un 
      }
}

object UndeleteT etHandlerBu lder {
  def apply(
    t etStorage: T etStorageCl ent,
    log calRepos: Log calRepos or es,
    t etStores: TotalT etStore,
    parentUser dRepo: ParentUser dRepos ory.Type,
    statsRece ver: StatsRece ver
  ): UndeleteT etHandler.Type =
    UndeleteT etHandler(
      undelete = t etStorage.undelete,
      t etEx sts = t etEx sts(t etStorage),
      getUser = FutureArrow(
        UserRepos ory
          .userGetter(
            log calRepos.opt onalUserRepo,
            UserQueryOpt ons(
              // ExtendedProf le  s needed to v ew a user's b rthday to
              // guarantee   are not undelet ng t ets from w n a user was < 13
              T etBu lder.userF elds ++ Set(UserF eld.ExtendedProf le),
              UserV s b l y.All,
              f lteredAsFa lure = false
            )
          )
          .compose(UserKey.by d)
      ),
      getDeletedT ets = t etStorage.getDeletedT ets,
      parentUser dRepo = parentUser dRepo,
      save = save(
        log calRepos,
        t etStores,
        statsRece ver
      )
    )

  pr vate def t etEx sts(t etStorage: T etStorageCl ent): FutureArrow[T et d, Boolean] =
    FutureArrow {  d =>
      St ch
        .run(t etStorage.getT et( d))
        .map {
          case _: GetT et.Response.Found => true
          case _ => false
        }
    }

  //  1. hydrates t  undeleted t et
  //  2. hands a UndeleteT etEvent to relevant stores.
  //  3. return t  hydrated t et
  def save(
    log calRepos: Log calRepos or es,
    t etStores: TotalT etStore,
    statsRece ver: StatsRece ver
  ): FutureArrow[UndeleteT et.Event, T et] = {

    val hydrateT et =
      Wr ePathHydrat on.hydrateT et(
        log calRepos.t etHydrators.hydrator,
        statsRece ver.scope("undelete_t et")
      )

    val hydrateQuotedT et =
      Wr ePathHydrat on.hydrateQuotedT et(
        log calRepos.opt onalT etRepo,
        log calRepos.opt onalUserRepo,
        log calRepos.quoterHasAlreadyQuotedRepo
      )

    val hydrateUndeleteEvent =
      Wr ePathHydrat on.hydrateUndeleteT etEvent(
        hydrateT et = hydrateT et,
        hydrateQuotedT et = hydrateQuotedT et
      )

    FutureArrow[UndeleteT et.Event, T et] { event =>
      for {
        hydratedEvent <- hydrateUndeleteEvent(event)
        _ <- t etStores.undeleteT et(hydratedEvent)
      } y eld hydratedEvent.t et
    }
  }
}
