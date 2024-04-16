package com.tw ter.t etyp e
package conf g

 mport com.tw ter.abdec der.ABDec derFactory
 mport com.tw ter.conf g.yaml.YamlConf g
 mport com.tw ter.dec der.Dec der
 mport com.tw ter.featuresw c s.v2.FeatureSw c s
 mport com.tw ter.f nagle. mcac d
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.servo.cac ._
 mport com.tw ter.servo.cac .{KeyValueResult => _}
 mport com.tw ter.servo.repos ory._
 mport com.tw ter.st ch.NotFound
 mport com.tw ter.st ch.St ch
 mport com.tw ter.st ch.repo.Repo
 mport com.tw ter.st ch.t  l neserv ce.T  l neServ ce
 mport com.tw ter.strato.cl ent.{Cl ent => StratoCl ent}
 mport com.tw ter.str ngcenter.cl ent.ExternalStr ngReg stry
 mport com.tw ter.str ngcenter.cl ent.Mult ProjectStr ngCenter
 mport com.tw ter.translat on.Languages
 mport com.tw ter.translat on.YamlConf gLanguages
 mport com.tw ter.t etyp e.cach ng.Cac Operat ons
 mport com.tw ter.t etyp e.cach ng.Exp ry
 mport com.tw ter.t etyp e.cach ng.ServoCac dValueSer al zer
 mport com.tw ter.t etyp e.cach ng.St chCach ng
 mport com.tw ter.t etyp e.cach ng.ValueSer al zer
 mport com.tw ter.t etyp e.cl ent_ d.Cl ent d lper
 mport com.tw ter.t etyp e.core.F lteredState
 mport com.tw ter.t etyp e.core.T etResult
 mport com.tw ter.t etyp e.hydrator.TextRepa rer
 mport com.tw ter.t etyp e.hydrator.T etHydrat on
 mport com.tw ter.t etyp e.hydrator.T etQueryOpt onsExpander
 mport com.tw ter.t etyp e.repos ory.T etRepos ory
 mport com.tw ter.t etyp e.repos ory.UserRepos ory
 mport com.tw ter.t etyp e.repos ory._
 mport com.tw ter.t etyp e.serverut l.Bor ngStackTrace
 mport com.tw ter.t etyp e.serverut l.Except onCounter
 mport com.tw ter.t etyp e.thr ftscala.Dev ceS ce
 mport com.tw ter.t etyp e.thr ftscala.Place
 mport com.tw ter.t etyp e.thr ftscala.ent  es.Ent yExtractor
 mport com.tw ter.t etyp e.ut l.St chUt ls
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.FuturePool
 mport com.tw ter.ut l.T  r
 mport com.tw ter.v s b l y.V s b l yL brary
 mport com.tw ter.v s b l y.common.KeywordMatc r
 mport com.tw ter.v s b l y.common.Local zat onS ce
 mport com.tw ter.v s b l y.common.T et d a tadataS ce
 mport com.tw ter.v s b l y.common.T etPerspect veS ce
 mport com.tw ter.v s b l y.common.UserRelat onsh pS ce
 mport com.tw ter.v s b l y.common.UserS ce
 mport com.tw ter.v s b l y.common.tflock.User s nv edToConversat onRepos ory
 mport com.tw ter.v s b l y.conf gap .conf gs.V s b l yDec derGates
 mport com.tw ter.v s b l y.generators.CountryNa Generator
 mport com.tw ter.v s b l y.generators.Local zed nterst  alGenerator
 mport com.tw ter.v s b l y.generators.TombstoneGenerator
 mport com.tw ter.v s b l y. nterfaces.t ets.DeletedT etV s b l yL brary
 mport com.tw ter.v s b l y. nterfaces.t ets.QuotedT etV s b l yL brary
 mport com.tw ter.v s b l y. nterfaces.t ets.T etV s b l yL brary
 mport com.tw ter.v s b l y. nterfaces.t ets.UserUnava lableStateV s b l yL brary
 mport com.tw ter.v s b l y.ut l.Dec derUt l
 mport com.tw ter.v s b l y.ut l.FeatureSw chUt l
 mport java.ut l.concurrent.Executors

/**
 * Log calRepos or es  s a layer above ExternalRepos or es.  T se repos may have add  onal
 * log c layered  n, such as  mcac -cach ng, hot-key cach ng, etc.  T re may
 * also be mult ple log cal repos or es mapped to an s ngle external repos ory.
 *
 * T se repos or es are used  n t et hydrat on and t et creat on.
 */
tra  Log calRepos or es {

  def card2Repo: Card2Repos ory.Type
  def cardRepo: CardRepos ory.Type
  def cardUsersRepo: CardUsersRepos ory.Type
  def conversat on dRepo: Conversat on dRepos ory.Type
  def conversat onControlRepo: Conversat onControlRepos ory.Type
  def conversat onMutedRepo: Conversat onMutedRepos ory.Type
  def conta nerAsGetT etResultRepo: Creat vesConta nerMater al zat onRepos ory.GetT etType
  def conta nerAsGetT etF eldsResultRepo: Creat vesConta nerMater al zat onRepos ory.GetT etF eldsType
  def dev ceS ceRepo: Dev ceS ceRepos ory.Type
  def esc rb rdAnnotat onRepo: Esc rb rdAnnotat onRepos ory.Type
  def geoScrubT  stampRepo: GeoScrubT  stampRepos ory.Type
  def languageRepo: LanguageRepos ory.Type
  def  d a tadataRepo:  d a tadataRepos ory.Type
  def pasted d aRepo: Pasted d aRepos ory.Type
  def perspect veRepo: Perspect veRepos ory.Type
  def placeRepo: PlaceRepos ory.Type
  def prof leGeoRepo: Prof leGeoRepos ory.Type
  def quoterHasAlreadyQuotedRepo: QuoterHasAlreadyQuotedRepos ory.Type
  def lastQuoteOfQuoterRepo: LastQuoteOfQuoterRepos ory.Type
  def relat onsh pRepo: Relat onsh pRepos ory.Type
  def stratoSafetyLabelsRepo: StratoSafetyLabelsRepos ory.Type
  def stratoCommun y mbersh pRepo: StratoCommun y mbersh pRepos ory.Type
  def stratoCommun yAccessRepo: StratoCommun yAccessRepos ory.Type
  def stratoSuperFollowEl g bleRepo: StratoSuperFollowEl g bleRepos ory.Type
  def stratoSuperFollowRelat onsRepo: StratoSuperFollowRelat onsRepos ory.Type
  def stratoPromotedT etRepo: StratoPromotedT etRepos ory.Type
  def stratoSubscr pt onVer f cat onRepo: StratoSubscr pt onVer f cat onRepos ory.Type
  def takedownRepo: UserTakedownRepos ory.Type
  def t etSpamC ckRepo: T etSpamC ckRepos ory.Type
  def ret etSpamC ckRepo: Ret etSpamC ckRepos ory.Type
  def t etCountsRepo: T etCountsRepos ory.Type
  def t etV s b l yRepo: T etV s b l yRepos ory.Type
  def quotedT etV s b l yRepo: QuotedT etV s b l yRepos ory.Type
  def deletedT etV s b l yRepo: DeletedT etV s b l yRepos ory.Type
  def un nt onedEnt  esRepo: Un nt onedEnt  esRepos ory.Type
  def urlRepo: UrlRepos ory.Type
  def userRepo: UserRepos ory.Type
  def opt onalUserRepo: UserRepos ory.Opt onal
  def user dent yRepo: User dent yRepos ory.Type
  def user s nv edToConversat onRepo: User s nv edToConversat onRepos ory.Type
  def userProtect onRepo: UserProtect onRepos ory.Type
  def userV ewRepo: UserV ewRepos ory.Type
  def userV s b l yRepo: UserV s b l yRepos ory.Type

  def t etResultRepo: T etResultRepos ory.Type
  def t etRepo: T etRepos ory.Type
  def opt onalT etRepo: T etRepos ory.Opt onal

  /**
   * Not actually repos or es, but  nt mately  ntertw ned.
   */
  def t etHydrators: T etHydrators
}

object Log calRepos or es {

  /**
   * M ddleware  s a funct on that takes a st ch repo and returns a new st ch repo.
   */
  type M ddleware[K, V] = (K => St ch[V]) => K => St ch[V]

  // M ddleware2  s a funct on that takes a two-arg st ch repo and returns a new two-arg st ch repo.
  type M ddleware2[K, C, V] = ((K, C) => St ch[V]) => ((K, C) => St ch[V])
  val except onLog: Logger = Logger(getClass)

  // Converts a M ddleware2 to a M ddleware for use w h w hM ddleware.
  def tupledM ddleware[K, C, V](m ddleware2: M ddleware2[K, C, V]): M ddleware[(K, C), V] =
    repo => m ddleware2(Funct on.untupled(repo)).tupled

  object ObserveSt ch {
    def apply[K, V](
      repo: K => St ch[V],
      repoNa : Str ng,
      stats: StatsRece ver
    ): K => St ch[V] = {
      val successCounter = stats.counter("success")
      val notFoundCounter = stats.counter("not_found")
      val latencyStat = stats.stat("latency_ms")

      val except onCounter =
        Except onCounter(
          stats,
          // don't count F lteredState except ons
          F lteredState. gnor ngCategor zer(Except onCounter.defaultCategor zer)
        )

      (key: K) =>
        St chUt ls.trackLatency(latencyStat, repo(key)).respond {
          case Return(_) => successCounter. ncr()
          case Throw(NotFound) => notFoundCounter. ncr()
          case Throw(t) =>
            val  ssage = s"$repoNa : $key"
             f (Bor ngStackTrace. sBor ng(t)) {
              except onLog.debug( ssage, t)
            } else {
              except onLog.warn( ssage, t)
            }

            except onCounter(t)
        }
    }
  }

  /**
   * Add m ddleware to conf gure a repos ory. T  stats rece ver  s
   * scoped for t  currently-conf gured repos ory. T  `toRepo` f eld
   *  s t  repos ory w h so  set of m ddleware appl ed. Each  thod
   * adds a new m ddleware to t  current repo, and returns   as a
   * `RepoConf g`, allow ng  thod cha n ng.
   *
   * S nce each  thod call appl es a new m ddleware, t  f nal m ddleware  s
   * t  outermost m ddleware, and thus t  one that sees t  argu nts
   * f rst.
   */
  class RepoConf g[K, V](
    val toRepo: K => St ch[V],
    stats: StatsRece ver,
    na : Str ng,
     mcac dCl entW h nProcessCach ng:  mcac d.Cl ent) {
    def w hM ddleware(m ddleware: M ddleware[K, V]): RepoConf g[K, V] =
      new RepoConf g[K, V](m ddleware(toRepo), stats, na ,  mcac dCl entW h nProcessCach ng)

    /**
     * Wraps a repo w h success/fa lure/latency stats track ng and logs
     * except ons. T  w ll be appl ed to every repos ory.
     *
     * @param repoNa  Used w n logg ng except ons thrown by t  underly ng repo.
     */
    def observe(repoNa : Str ng = s"${na }_repo"): RepoConf g[K, V] = {
      w hM ddleware { repo => ObserveSt ch[K, V](repo, repoNa , stats) }
    }

    /**
     * Use t  suppl ed cac  to wrap t  repos ory w h a read-through
     * cach ng layer.
     */
    def cach ng(
      cac : Lock ngCac [K, Cac d[V]],
      part alHandler: Cac dResult.Part alHandler[K, V],
      maxCac RequestS ze:  nt =  nt.MaxValue
    ): RepoConf g[K, V] = {
      val st chLock ngCac  = St chLock ngCac (
        underly ng = cac ,
        p cker = new PreferNe stCac d[V],
        maxRequestS ze = maxCac RequestS ze
      )

      val handler: Cac dResult.Handler[K, V] =
        Cac dResult.Handler(
          Cac dResult.Part alHandler.orElse(
            part alHandler,
            Cac dResult.fa luresAreDoNotCac 
          )
        )

      w hM ddleware { repo =>
        Cac St ch[K, K, V](
          repo = repo,
          cac  = st chLock ngCac ,
           dent y,
          handler = handler,
          cac able = Cac St ch.cac FoundAndNotFound
        )
      }
    }

    def newCach ng(
      keySer al zer: K => Str ng,
      valueSer al zer: ValueSer al zer[Try[V]]
    ): RepoConf g[K, V] =
      w hM ddleware { repo =>
        val logger = Logger(s"com.tw ter.t etyp e.conf g.Log calRepos or es.$na ")

        val cac Operat ons: Cac Operat ons[K, Try[V]] =
          new Cac Operat ons(
            keySer al zer = keySer al zer,
            valueSer al zer = valueSer al zer,
             mcac dCl ent =  mcac dCl entW h nProcessCach ng,
            statsRece ver = stats.scope("cach ng"),
            logger = logger
          )

        val tryRepo: K => St ch[Try[V]] = repo.andT n(_.l ftToTry)
        val cach ngTryRepo: K => St ch[Try[V]] = new St chCach ng(cac Operat ons, tryRepo)
        cach ngTryRepo.andT n(_.lo rFromTry)
      }

    def toRepo2[K1, C]( mpl c  tupleToK: ((K1, C)) <:< K): (K1, C) => St ch[V] =
      (k1, c) => toRepo(tupleToK((k1, c)))
  }

  def softTtlPart alHandler[K, V](
    softTtl: Opt on[V] => Durat on,
    softTtlPerturbat onFactor: Float = 0.05f
  ): Cac dResult.Part alHandler[K, V] =
    Cac dResult
      .softTtlExp rat on[K, V](softTtl, Cac dResult.randomExp ry(softTtlPerturbat onFactor))

  def apply(
    sett ngs: T etServ ceSett ngs,
    stats: StatsRece ver,
    t  r: T  r,
    dec derGates: T etyp eDec derGates,
    external: ExternalRepos or es,
    cac s: Cac s,
    stratoCl ent: StratoCl ent,
    has d a: T et => Boolean,
    cl ent d lper: Cl ent d lper,
    featureSw c sW houtExper  nts: FeatureSw c s,
  ): Log calRepos or es = {
    val repoStats = stats.scope("repos or es")

    def repoConf g[K, V](na : Str ng, repo: K => St ch[V]): RepoConf g[K, V] =
      new RepoConf g[K, V](
        na  = na ,
        toRepo = repo,
        stats = repoStats.scope(na ),
         mcac dCl entW h nProcessCach ng = cac s. mcac dCl entW h nProcessCach ng)

    def repo2Conf g[K, C, V](na : Str ng, repo: (K, C) => St ch[V]): RepoConf g[(K, C), V] =
      repoConf g[(K, C), V](na , repo.tupled)

    new Log calRepos or es {
      // t  f nal t etResultRepo has a c rcular dependency, w re   depends on hydrators
      // that  n turn depend on t  t etResultRepo, so   create a `t etResultRepo` funct on
      // that prox es to `var f nalT etResultRepo`, wh ch gets set at t  end of t  block.
      var f nalT etResultRepo: T etResultRepos ory.Type = null
      val t etResultRepo: T etResultRepos ory.Type =
        (t et d, opts) => f nalT etResultRepo(t et d, opts)
      val t etRepo: T etRepos ory.Type = T etRepos ory.fromT etResult(t etResultRepo)

      val opt onalT etRepo: T etRepos ory.Opt onal = T etRepos ory.opt onal(t etRepo)

      val userRepo: UserRepos ory.Type =
        repo2Conf g(repo = external.userRepo, na  = "user")
          .observe()
          .toRepo2

      val opt onalUserRepo: UserRepos ory.Opt onal = UserRepos ory.opt onal(userRepo)

      pr vate[t ] val t etV s b l yStatsRece ver: StatsRece ver =
        repoStats.scope("t et_v s b l y_l brary")
      pr vate[t ] val userUnava lableV s b l yStatsRece ver: StatsRece ver =
        repoStats.scope("user_unava lable_v s b l y_l brary")
      pr vate[t ] val quotedT etV s b l yStatsRece ver: StatsRece ver =
        repoStats.scope("quoted_t et_v s b l y_l brary")
      pr vate[t ] val deletedT etV s b l yStatsRece ver: StatsRece ver =
        repoStats.scope("deleted_t et_v s b l y_l brary")
      // T etV s b l yL brary st ll uses t  old c.t.logg ng.Logger
      pr vate[t ] val t etV s b l yLogger =
        com.tw ter.logg ng.Logger("com.tw ter.t etyp e.T etV s b l y")
      pr vate[t ] val v s b l yDec der: Dec der = Dec derUt l.mkDec der(
        dec derOverlayPath = sett ngs.vfDec derOverlayF lena ,
        useLocalDec derOverr des = true)
      pr vate[t ] val v s b l yDec derGates = V s b l yDec derGates(v s b l yDec der)

      pr vate[t ] def v s b l yL brary(statsRece ver: StatsRece ver) = V s b l yL brary
        .Bu lder(
          log = t etV s b l yLogger,
          statsRece ver = statsRece ver,
           mo zeSafetyLevelParams = v s b l yDec derGates.enable mo zeSafetyLevelParams
        )
        .w hDec der(v s b l yDec der)
        .w hDefaultABDec der( sLocal = false)
        .w hCaptureDebugStats(Gate.True)
        .w hEnableComposableAct ons(Gate.True)
        .w hEnableFa lClosed(Gate.True)
        .w hEnableShortC rcu  ng(v s b l yDec derGates.enableShortC rcu  ngTVL)
        .w hSpec alLogg ng(v s b l yDec derGates.enableSpec alLogg ng)
        .bu ld()

      def countryNa Generator(statsRece ver: StatsRece ver) = {
        // T etV s b l yL brary, DeletedT etV s b l yL brary, and
        // UserUnava lableV s b l yL brary do not evaluate any Rules
        // that requ re t  d splay of country na s  n copy
        CountryNa Generator.prov desW hCustomMap(Map.empty, statsRece ver)
      }

      def tombstoneGenerator(
        countryNa Generator: CountryNa Generator,
        statsRece ver: StatsRece ver
      ) =
        TombstoneGenerator(
          v s b l yL brary(statsRece ver).v sParams,
          countryNa Generator,
          statsRece ver)

      pr vate[t ] val userUnava lableV s b l yL brary =
        UserUnava lableStateV s b l yL brary(
          v s b l yL brary(userUnava lableV s b l yStatsRece ver),
          v s b l yDec der,
          tombstoneGenerator(
            countryNa Generator(userUnava lableV s b l yStatsRece ver),
            userUnava lableV s b l yStatsRece ver
          ),
          Local zed nterst  alGenerator(v s b l yDec der, userUnava lableV s b l yStatsRece ver)
        )

      val user dent yRepo: User dent yRepos ory.Type =
        repoConf g(repo = User dent yRepos ory(userRepo), na  = "user_ dent y")
          .observe()
          .toRepo

      val userProtect onRepo: UserProtect onRepos ory.Type =
        repoConf g(repo = UserProtect onRepos ory(userRepo), na  = "user_protect on")
          .observe()
          .toRepo

      val userV ewRepo: UserV ewRepos ory.Type =
        repoConf g(repo = UserV ewRepos ory(userRepo), na  = "user_v ew")
          .observe()
          .toRepo

      val userV s b l yRepo: UserV s b l yRepos ory.Type =
        repoConf g(
          repo = UserV s b l yRepos ory(userRepo, userUnava lableV s b l yL brary),
          na  = "user_v s b l y"
        ).observe().toRepo

      val urlRepo: UrlRepos ory.Type =
        repoConf g(repo = external.urlRepo, na  = "url")
          .observe()
          .toRepo

      val prof leGeoRepo: Prof leGeoRepos ory.Type =
        repoConf g(repo = external.prof leGeoRepo, na  = "prof le_geo")
          .observe()
          .toRepo

      val quoterHasAlreadyQuotedRepo: QuoterHasAlreadyQuotedRepos ory.Type =
        repo2Conf g(repo = external.quoterHasAlreadyQuotedRepo, na  = "quoter_has_already_quoted")
          .observe()
          .toRepo2

      val lastQuoteOfQuoterRepo: LastQuoteOfQuoterRepos ory.Type =
        repo2Conf g(repo = external.lastQuoteOfQuoterRepo, na  = "last_quote_of_quoter")
          .observe()
          .toRepo2

      val  d a tadataRepo:  d a tadataRepos ory.Type =
        repoConf g(repo = external. d a tadataRepo, na  = " d a_ tadata")
          .observe()
          .toRepo

      val perspect veRepo: Perspect veRepos ory.Type =
        repoConf g(repo = external.perspect veRepo, na  = "perspect ve")
          .observe()
          .toRepo

      val conversat onMutedRepo: Conversat onMutedRepos ory.Type =
        T  l neServ ce.GetPerspect ves.getConversat onMuted(perspect veRepo)

      // Because observe  s appl ed before cach ng, only cac  m sses
      // ( .e. calls to t  underly ng repo) are observed.
      // Note that `newCach ng` has stats around cac  h /m ss but `cach ng` does not.
      val dev ceS ceRepo: Dev ceS ceRepos ory.Type =
        repoConf g(repo = external.dev ceS ceRepo, na  = "dev ce_s ce")
          .observe()
          .newCach ng(
            keySer al zer = app dStr => Dev ceS ceKey(app dStr).toStr ng,
            valueSer al zer = ServoCac dValueSer al zer(
              codec = Dev ceS ce,
              exp ry = Exp ry.byAge(sett ngs.dev ceS ce mcac Ttl),
              softTtl = sett ngs.dev ceS ce mcac SoftTtl
            )
          )
          .cach ng(
            cac  = cac s.dev ceS ce nProcessCac ,
            part alHandler = softTtlPart alHandler(_ => sett ngs.dev ceS ce nProcessSoftTtl)
          )
          .toRepo

      // Because observe  s appl ed before cach ng, only cac  m sses
      // ( .e. calls to t  underly ng repo) are observed
      // Note that `newCach ng` has stats around cac  h /m ss but `cach ng` does not.
      val placeRepo: PlaceRepos ory.Type =
        repoConf g(repo = external.placeRepo, na  = "place")
          .observe()
          .newCach ng(
            keySer al zer = placeKey => placeKey.toStr ng,
            valueSer al zer = ServoCac dValueSer al zer(
              codec = Place,
              exp ry = Exp ry.byAge(sett ngs.place mcac Ttl),
              softTtl = sett ngs.place mcac SoftTtl
            )
          )
          .toRepo

      val cardRepo: CardRepos ory.Type =
        repoConf g(repo = external.cardRepo, na  = "cards")
          .observe()
          .toRepo

      val card2Repo: Card2Repos ory.Type =
        repo2Conf g(repo = external.card2Repo, na  = "card2")
          .observe()
          .toRepo2

      val cardUsersRepo: CardUsersRepos ory.Type =
        repo2Conf g(repo = external.cardUsersRepo, na  = "card_users")
          .observe()
          .toRepo2

      val relat onsh pRepo: Relat onsh pRepos ory.Type =
        repoConf g(repo = external.relat onsh pRepo, na  = "relat onsh p")
          .observe()
          .toRepo

      val conversat on dRepo: Conversat on dRepos ory.Type =
        repoConf g(repo = external.conversat on dRepo, na  = "conversat on_ d")
          .observe()
          .toRepo

      val conversat onControlRepo: Conversat onControlRepos ory.Type =
        repo2Conf g(
          repo = Conversat onControlRepos ory(t etRepo, stats.scope("conversat on_control")),
          na  = "conversat on_control"
        ).observe().toRepo2

      val conta nerAsGetT etResultRepo: Creat vesConta nerMater al zat onRepos ory.GetT etType =
        repo2Conf g(
          repo = external.conta nerAsT etRepo,
          na  = "conta ner_as_t et"
        ).observe().toRepo2

      val conta nerAsGetT etF eldsResultRepo: Creat vesConta nerMater al zat onRepos ory.GetT etF eldsType =
        repo2Conf g(
          repo = external.conta nerAsT etF eldsRepo,
          na  = "conta ner_as_t et_f elds"
        ).observe().toRepo2

      val languageRepo: LanguageRepos ory.Type = {
        val pool = FuturePool(Executors.newF xedThreadPool(sett ngs.numPengu nThreads))
        repoConf g(repo = Pengu nLanguageRepos ory(pool), na  = "language")
          .observe()
          .toRepo
      }

      // Because observe  s appl ed before cach ng, only cac  m sses
      // ( .e. calls to t  underly ng repo) are observed
      // Note that `newCach ng` has stats around cac  h /m ss but `cach ng` does not.
      val t etCountsRepo: T etCountsRepos ory.Type =
        repoConf g(repo = external.t etCountsRepo, na  = "counts")
          .observe()
          .cach ng(
            cac  = cac s.t etCountsCac ,
            part alHandler = softTtlPart alHandler {
              case So (0) => sett ngs.t etCounts mcac ZeroSoftTtl
              case _ => sett ngs.t etCounts mcac NonZeroSoftTtl
            },
            maxCac RequestS ze = sett ngs.t etCountsCac ChunkS ze
          )
          .toRepo

      val pasted d aRepo: Pasted d aRepos ory.Type =
        repo2Conf g(repo = Pasted d aRepos ory(t etRepo), na  = "pasted_ d a")
          .observe()
          .toRepo2

      val esc rb rdAnnotat onRepo: Esc rb rdAnnotat onRepos ory.Type =
        repoConf g(repo = external.esc rb rdAnnotat onRepo, na  = "esc rb rd_annotat ons")
          .observe()
          .toRepo

      val stratoSafetyLabelsRepo: StratoSafetyLabelsRepos ory.Type =
        repo2Conf g(repo = external.stratoSafetyLabelsRepo, na  = "strato_safety_labels")
          .observe()
          .toRepo2

      val stratoCommun y mbersh pRepo: StratoCommun y mbersh pRepos ory.Type =
        repoConf g(
          repo = external.stratoCommun y mbersh pRepo,
          na  = "strato_commun y_ mbersh ps")
          .observe()
          .toRepo

      val stratoCommun yAccessRepo: StratoCommun yAccessRepos ory.Type =
        repoConf g(repo = external.stratoCommun yAccessRepo, na  = "strato_commun y_access")
          .observe()
          .toRepo

      val stratoSuperFollowEl g bleRepo: StratoSuperFollowEl g bleRepos ory.Type =
        repoConf g(
          repo = external.stratoSuperFollowEl g bleRepo,
          na  = "strato_super_follow_el g ble")
          .observe()
          .toRepo

      val stratoSuperFollowRelat onsRepo: StratoSuperFollowRelat onsRepos ory.Type =
        repo2Conf g(
          repo = external.stratoSuperFollowRelat onsRepo,
          na  = "strato_super_follow_relat ons")
          .observe()
          .toRepo2

      val stratoPromotedT etRepo: StratoPromotedT etRepos ory.Type =
        repoConf g(repo = external.stratoPromotedT etRepo, na  = "strato_promoted_t et")
          .observe()
          .toRepo

      val stratoSubscr pt onVer f cat onRepo: StratoSubscr pt onVer f cat onRepos ory.Type =
        repo2Conf g(
          repo = external.stratoSubscr pt onVer f cat onRepo,
          na  = "strato_subscr pt on_ver f cat on")
          .observe()
          .toRepo2

      val un nt onedEnt  esRepo: Un nt onedEnt  esRepos ory.Type =
        repo2Conf g(repo = external.un nt onedEnt  esRepo, na  = "un nt oned_ent  es")
          .observe()
          .toRepo2

      pr vate[t ] val userS ce =
        UserS ce.fromRepo(
          Repo { (k, _) =>
            val opts = UserQueryOpt ons(k.f elds, UserV s b l y.All)
            userRepo(UserKey(k. d), opts)
          }
        )

      pr vate[t ] val userRelat onsh pS ce =
        UserRelat onsh pS ce.fromRepo(
          Repo[UserRelat onsh pS ce.Key, Un , Boolean] { (key, _) =>
            relat onsh pRepo(
              Relat onsh pKey(key.subject d, key.object d, key.relat onsh p)
            )
          }
        )

      pr vate[t ] val t etPerspect veS ce =
        T etPerspect veS ce.fromGetPerspect ves(perspect veRepo)
      pr vate[t ] val t et d a tadataS ce =
        T et d a tadataS ce.fromFunct on( d a tadataRepo)

      val user s nv edToConversat onRepo: User s nv edToConversat onRepos ory.Type =
        repo2Conf g(
          repo = external.user s nv edToConversat onRepo,
          na  = "user_ s_ nv ed_to_conversat on")
          .observe()
          .toRepo2

      pr vate[t ] val str ngCenterCl ent: Mult ProjectStr ngCenter = {
        val str ngCenterProjects = sett ngs.flags.str ngCenterProjects().toL st

        val languages: Languages = new YamlConf gLanguages(
          new YamlConf g(sett ngs.flags.languagesConf g()))

        val logg ngAbDec der = ABDec derFactory("/usr/local/conf g/abdec der/abdec der.yml")
          .w hEnv ron nt("product on")
          .bu ldW hLogg ng()

        Mult ProjectStr ngCenter(
          projects = str ngCenterProjects,
          defaultBundlePath = Mult ProjectStr ngCenter.StandardDefaultBundlePath,
          refresh ngBundlePath = Mult ProjectStr ngCenter.StandardRefresh ngBundlePath,
          refresh ng nterval = Mult ProjectStr ngCenter.StandardRefresh ng nterval,
          requ reDefaultBundleEx sts = true,
          languages = languages,
          statsRece ver = t etV s b l yStatsRece ver,
          logg ngABDec der = logg ngAbDec der
        )
      }
      pr vate[t ] val str ngReg stry: ExternalStr ngReg stry = new ExternalStr ngReg stry()
      pr vate[t ] val local zat onS ce: Local zat onS ce =
        Local zat onS ce.fromMult ProjectStr ngCenterCl ent(str ngCenterCl ent, str ngReg stry)

      val t etV s b l yRepo: T etV s b l yRepos ory.Type = {
        val t etV s b l yL brary: T etV s b l yL brary.Type =
          T etV s b l yL brary(
            v s b l yL brary(t etV s b l yStatsRece ver),
            userS ce = userS ce,
            userRelat onsh pS ce = userRelat onsh pS ce,
            keywordMatc r = KeywordMatc r.defaultMatc r(stats),
            stratoCl ent = stratoCl ent,
            local zat onS ce = local zat onS ce,
            dec der = v s b l yDec der,
             nv edToConversat onRepo = user s nv edToConversat onRepo,
            t etPerspect veS ce = t etPerspect veS ce,
            t et d a tadataS ce = t et d a tadataS ce,
            tombstoneGenerator = tombstoneGenerator(
              countryNa Generator(t etV s b l yStatsRece ver),
              t etV s b l yStatsRece ver
            ),
             nterst  alGenerator =
              Local zed nterst  alGenerator(v s b l yDec der, t etV s b l yStatsRece ver),
            l m edAct onsFeatureSw c s =
              FeatureSw chUt l.mkL m edAct onsFeatureSw c s(t etV s b l yStatsRece ver),
            enablePar yTest = dec derGates.t etV s b l yL braryEnablePar yTest
          )

        val underly ng =
          T etV s b l yRepos ory(
            t etV s b l yL brary,
            v s b l yDec derGates,
            t etV s b l yLogger,
            repoStats.scope("t et_v s b l y_repo")
          )

        repoConf g(repo = underly ng, na  = "t et_v s b l y")
          .observe()
          .toRepo
      }

      val quotedT etV s b l yRepo: QuotedT etV s b l yRepos ory.Type = {
        val quotedT etV s b l yL brary: QuotedT etV s b l yL brary.Type =
          QuotedT etV s b l yL brary(
            v s b l yL brary(quotedT etV s b l yStatsRece ver),
            userS ce = userS ce,
            userRelat onsh pS ce = userRelat onsh pS ce,
            v s b l yDec der,
            userStateV s b l yL brary = userUnava lableV s b l yL brary,
            enableVfFeatureHydrat on = dec derGates.enableVfFeatureHydrat on nQuotedT etVLSh m
          )

        val underly ng =
          QuotedT etV s b l yRepos ory(quotedT etV s b l yL brary, v s b l yDec derGates)

        repoConf g(repo = underly ng, na  = "quoted_t et_v s b l y")
          .observe()
          .toRepo
      }

      val deletedT etV s b l yRepo: DeletedT etV s b l yRepos ory.Type = {
        val deletedT etV s b l yL brary: DeletedT etV s b l yL brary.Type =
          DeletedT etV s b l yL brary(
            v s b l yL brary(deletedT etV s b l yStatsRece ver),
            v s b l yDec der,
            tombstoneGenerator(
              countryNa Generator(deletedT etV s b l yStatsRece ver),
              deletedT etV s b l yStatsRece ver
            )
          )

        val underly ng = DeletedT etV s b l yRepos ory.apply(
          deletedT etV s b l yL brary
        )

        repoConf g(repo = underly ng, na  = "deleted_t et_v s b l y")
          .observe()
          .toRepo
      }

      val takedownRepo: UserTakedownRepos ory.Type =
        repoConf g(repo = UserTakedownRepos ory(userRepo), na  = "takedowns")
          .observe()
          .toRepo

      val t etSpamC ckRepo: T etSpamC ckRepos ory.Type =
        repo2Conf g(repo = external.t etSpamC ckRepo, na  = "t et_spam_c ck")
          .observe()
          .toRepo2

      val ret etSpamC ckRepo: Ret etSpamC ckRepos ory.Type =
        repoConf g(repo = external.ret etSpamC ckRepo, na  = "ret et_spam_c ck")
          .observe()
          .toRepo

      // Because observe  s appl ed before cach ng, only cac  m sses
      // ( .e. calls to t  underly ng repo) are observed
      // Note that `newCach ng` has stats around cac  h /m ss but `cach ng` does not.
      val geoScrubT  stampRepo: GeoScrubT  stampRepos ory.Type =
        repoConf g(repo = external.geoScrubT  stampRepo, na  = "geo_scrub")
          .observe()
          .cach ng(
            cac  = cac s.geoScrubCac ,
            part alHandler = (_ => None)
          )
          .toRepo

      val t etHydrators: T etHydrators =
        T etHydrators(
          stats = stats,
          dec derGates = dec derGates,
          repos = t ,
          t etDataCac  = cac s.t etDataCac ,
          has d a = has d a,
          featureSw c sW houtExper  nts = featureSw c sW houtExper  nts,
          cl ent d lper = cl ent d lper,
        )

      val queryOpt onsExpander: T etQueryOpt onsExpander.Type =
        T etQueryOpt onsExpander.threadLocal mo ze(
          T etQueryOpt onsExpander.expandDependenc es
        )

      // mutat ons to t ets that   only need to apply w n read ng from t  external
      // repos ory, and not w n read ng from cac 
      val t etMutat on: Mutat on[T et] =
        Mutat on
          .all(
            Seq(
              Ent yExtractor.mutat onAll,
              TextRepa rer.BlankL neCollapser,
              TextRepa rer.CoreTextBugPatc r
            )
          ).only f(_.coreData. sDef ned)

      val cach ngT etRepo: T etResultRepos ory.Type =
        repo2Conf g(repo = external.t etResultRepo, na  = "saved_t et")
          .observe()
          .w hM ddleware { repo =>
            // appl es t etMutat on to t  results of T etResultRepos ory
            val mutateResult = T etResult.mutate(t etMutat on)
            repo.andT n(st chResult => st chResult.map(mutateResult))
          }
          .w hM ddleware(
            tupledM ddleware(
              Cach ngT etRepos ory(
                cac s.t etResultCac ,
                sett ngs.t etTombstoneTtl,
                stats.scope("saved_t et", "cac "),
                cl ent d lper,
                dec derGates.logCac Except ons,
              )
            )
          )
          .toRepo2

      f nalT etResultRepo = repo2Conf g(repo = cach ngT etRepo, na  = "t et")
        .w hM ddleware(
          tupledM ddleware(
            T etHydrat on.hydrateRepo(
              t etHydrators.hydrator,
              t etHydrators.cac ChangesEffect,
              queryOpt onsExpander
            )
          )
        )
        .observe()
        .w hM ddleware(tupledM ddleware(T etResultRepos ory.shortC rcu  nval d ds))
        .toRepo2
    }
  }
}
