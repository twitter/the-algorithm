package com.tw ter.search.earlyb rd;

 mport java. o. OExcept on;
 mport java.ut l.ArrayL st;
 mport java.ut l.HashMap;
 mport java.ut l. erator;
 mport java.ut l.L st;
 mport java.ut l.Map;
 mport java.ut l.Set;
 mport java.ut l.stream.Collectors;
 mport javax.annotat on.Nonnull;
 mport javax.annotat on.Nullable;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Jo ner;
 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect. mmutableMap;
 mport com.google.common.collect. mmutableSet;
 mport com.google.common.collect.L sts;

 mport org.apac .commons.lang.Str ngUt ls;
 mport org.apac .lucene. ndex.Term;
 mport org.apac .lucene.queryparser.class c.ParseExcept on;
 mport org.apac .lucene.queryparser.class c.QueryParser;
 mport org.apac .lucene.search.BooleanClause.Occur;
 mport org.apac .lucene.search.BooleanQuery;
 mport org.apac .lucene.search.Query;
 mport org.apac .thr ft.TExcept on;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.dec der.Dec der;
 mport com.tw ter.search.common.database.DatabaseConf g;
 mport com.tw ter.search.common.dec der.Dec derUt l;
 mport com.tw ter.search.common.features.thr ft.Thr ftSearchFeatureSc ma;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common. tr cs.SearchRateCounter;
 mport com.tw ter.search.common. tr cs.SearchT  r;
 mport com.tw ter.search.common.part  on ng.base.Seg nt;
 mport com.tw ter.search.common.query.MappableF eld;
 mport com.tw ter.search.common.query.QueryH Attr bute lper;
 mport com.tw ter.search.common.query.thr ftjava.CollectorParams;
 mport com.tw ter.search.common.query.thr ftjava.CollectorTerm nat onParams;
 mport com.tw ter.search.common.query.thr ftjava.EarlyTerm nat on nfo;
 mport com.tw ter.search.common.rank ng.thr ftjava.Thr ftRank ngParams;
 mport com.tw ter.search.common.rank ng.thr ftjava.Thr ftScor ngFunct onType;
 mport com.tw ter.search.common.results.thr ftjava.F eldH L st;
 mport com.tw ter.search.common.sc ma.Sc maUt l;
 mport com.tw ter.search.common.sc ma.SearchWh espaceAnalyzer;
 mport com.tw ter.search.common.sc ma.base.F eld  ghtDefault;
 mport com.tw ter.search.common.sc ma.base. mmutableSc ma nterface;
 mport com.tw ter.search.common.sc ma.base.Sc ma;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdCluster;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant;
 mport com.tw ter.search.common.search.Term nat onTracker;
 mport com.tw ter.search.common.search.Tw terEarlyTerm nat onCollector;
 mport com.tw ter.search.common.search.term nat on.QueryT  outFactory;
 mport com.tw ter.search.common.ut l.earlyb rd.Earlyb rdResponseUt l;
 mport com.tw ter.search.common.ut l.ml.tensorflow_eng ne.TensorflowModelsManager;
 mport com.tw ter.search.common.ut l.thr ft.Thr ftUt ls;
 mport com.tw ter.search.core.earlyb rd.facets.FacetCountState;
 mport com.tw ter.search.earlyb rd.common.Cl ent dUt l;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdConf g;
 mport com.tw ter.search.earlyb rd.except on.Cl entExcept on;
 mport com.tw ter.search.earlyb rd.except on.Trans entExcept on;
 mport com.tw ter.search.earlyb rd. ndex.facets.FacetSk pL st;
 mport com.tw ter.search.earlyb rd.ml.Scor ngModelsManager;
 mport com.tw ter.search.earlyb rd.part  on.Aud oSpaceTable;
 mport com.tw ter.search.earlyb rd.part  on.Mult Seg ntTermD ct onaryManager;
 mport com.tw ter.search.earlyb rd.part  on.Part  onConf g;
 mport com.tw ter.search.earlyb rd.part  on.Seg nt nfo;
 mport com.tw ter.search.earlyb rd.part  on.Seg ntManager;
 mport com.tw ter.search.earlyb rd.querycac .QueryCac Convers onRules;
 mport com.tw ter.search.earlyb rd.querycac .QueryCac Manager;
 mport com.tw ter.search.earlyb rd.queryparser.DetectF eldAnnotat onV s or;
 mport com.tw ter.search.earlyb rd.queryparser.Earlyb rdLuceneQueryV s or;
 mport com.tw ter.search.earlyb rd.queryparser.H ghFrequencyTermPa rRewr eV s or;
 mport com.tw ter.search.earlyb rd.queryparser.LuceneRelevanceQueryV s or;
 mport com.tw ter.search.earlyb rd.queryparser.ProtectedOperatorQueryRewr er;
 mport com.tw ter.search.earlyb rd.search.AbstractResultsCollector;
 mport com.tw ter.search.earlyb rd.search.Ant Gam ngF lter;
 mport com.tw ter.search.earlyb rd.search.quer es.BadUserRepF lter;
 mport com.tw ter.search.earlyb rd.search.Earlyb rdLuceneSearc r;
 mport com.tw ter.search.earlyb rd.search.Earlyb rdMult Seg ntSearc r;
 mport com.tw ter.search.earlyb rd.search.quer es.MatchAllDocsQuery;
 mport com.tw ter.search.earlyb rd.search.quer es.Requ redStatus DsF lter;
 mport com.tw ter.search.earlyb rd.search.SearchRequest nfo;
 mport com.tw ter.search.earlyb rd.search.SearchResultsCollector;
 mport com.tw ter.search.earlyb rd.search.SearchResults nfo;
 mport com.tw ter.search.earlyb rd.search.S mpleSearchResults;
 mport com.tw ter.search.earlyb rd.search.Soc alF lter;
 mport com.tw ter.search.earlyb rd.search.Soc alSearchResultsCollector;
 mport com.tw ter.search.earlyb rd.search.quer es.UserFlagsExcludeF lter;
 mport com.tw ter.search.earlyb rd.search.quer es.User dMult Seg ntQuery;
 mport com.tw ter.search.earlyb rd.search.facets.Ent yAnnotat onCollector;
 mport com.tw ter.search.earlyb rd.search.facets.ExpandedUrlCollector;
 mport com.tw ter.search.earlyb rd.search.facets.Expla nFacetResultsCollector;
 mport com.tw ter.search.earlyb rd.search.facets.FacetRank ngModule;
 mport com.tw ter.search.earlyb rd.search.facets.FacetResultsCollector;
 mport com.tw ter.search.earlyb rd.search.facets.FacetSearchRequest nfo;
 mport com.tw ter.search.earlyb rd.search.facets.Na dEnt yCollector;
 mport com.tw ter.search.earlyb rd.search.facets.SpaceFacetCollector;
 mport com.tw ter.search.earlyb rd.search.facets.TermStat st csCollector;
 mport com.tw ter.search.earlyb rd.search.facets.TermStat st csRequest nfo;
 mport com.tw ter.search.earlyb rd.search.relevance.RelevanceSearchRequest nfo;
 mport com.tw ter.search.earlyb rd.search.relevance.RelevanceSearchResults;
 mport com.tw ter.search.earlyb rd.search.relevance.collectors.AbstractRelevanceCollector;
 mport com.tw ter.search.earlyb rd.search.relevance.collectors.BatchRelevanceTopCollector;
 mport com.tw ter.search.earlyb rd.search.relevance.collectors.RelevanceAllCollector;
 mport com.tw ter.search.earlyb rd.search.relevance.collectors.RelevanceTopCollector;
 mport com.tw ter.search.earlyb rd.search.relevance.scor ng.RelevanceQuery;
 mport com.tw ter.search.earlyb rd.search.relevance.scor ng.Scor ngFunct on;
 mport com.tw ter.search.earlyb rd.search.relevance.scor ng.Scor ngFunct onProv der;
 mport com.tw ter.search.earlyb rd.search.relevance.scor ng.TensorflowBasedScor ngFunct on;
 mport com.tw ter.search.earlyb rd.stats.Earlyb rdRPCStats;
 mport com.tw ter.search.earlyb rd.stats.Earlyb rdSearc rStats;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdDebug nfo;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponseCode;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftFacetCount;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftFacetCount tadata;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftFacetF eldRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftFacetF eldResults;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftFacetRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftFacetResults;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchQuery;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchRank ngMode;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchRelevanceOpt ons;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResultExtra tadata;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult tadataOpt ons;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResults;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftTermRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftTermStat st csRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftTermStat st csResults;
 mport com.tw ter.search.earlyb rd.ut l.Earlyb rdSearchResultUt l;
 mport com.tw ter.search.queryparser.parser.Ser al zedQueryParser;
 mport com.tw ter.search.queryparser.query.Conjunct on;
 mport com.tw ter.search.queryparser.query.D sjunct on;
 mport com.tw ter.search.queryparser.query.QueryNodeUt ls;
 mport com.tw ter.search.queryparser.query.QueryParserExcept on;
 mport com.tw ter.search.queryparser.query.annotat on.Annotat on;
 mport com.tw ter.search.queryparser.query.search.SearchOperator;
 mport com.tw ter.search.queryparser.query.search.SearchOperatorConstants;
 mport com.tw ter.search.queryparser.ut l. dT  Ranges;
 mport com.tw ter.search.queryparser.v s ors.Convers onV s or;
 mport com.tw ter.search.queryparser.v s ors.DetectPos  veOperatorV s or;
 mport com.tw ter.search.queryparser.v s ors.Na dD sjunct onV s or;
 mport com.tw ter.search.queryparser.v s ors.Prox m yGroupRewr eV s or;
 mport com.tw ter.search.queryparser.v s ors.Str pAnnotat onsV s or;

 mport stat c com.tw ter.search.queryparser.query.search.SearchOperator.Type.UNT L_T ME;

/**
 * T  class prov des t  bas c search()  thod:
 * - converts t  thr ft request object  nto what lucene expects.
 * - gets t  seg nt.
 * - handles all errors, and prepares t  response  n case of error.
 *
 *   have one  nstance of t  class per search rece ved.
 */
publ c class Earlyb rdSearc r {
  publ c enum QueryMode {
    // Please th nk before add ng more query modes: can t  be  mple nted  n a general way?
    RECENCY(new Earlyb rdRPCStats("search_recency")),
    FACETS(new Earlyb rdRPCStats("search_facets")),
    TERM_STATS(new Earlyb rdRPCStats("search_termstats")),
    RELEVANCE(new Earlyb rdRPCStats("search_relevance")),
    TOP_TWEETS(new Earlyb rdRPCStats("search_topt ets"));

    pr vate f nal Earlyb rdRPCStats requestStats;

    QueryMode(Earlyb rdRPCStats requestStats) {
      t .requestStats = requestStats;
    }

    publ c Earlyb rdRPCStats getRequestStats() {
      return requestStats;
    }
  }

  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Earlyb rdSearc r.class);
  pr vate stat c f nal Str ng MATCH_ALL_SER AL ZED_QUERY = "(* )";
  /**
   * gener c f eld annotat ons can be mapped to a concrete f eld  n t   ndex us ng t  mapp ng
   * v a {@l nk com.tw ter.search.queryparser.query.annotat on.Annotat on.Type#MAPPABLE_F ELD}
   */
  pr vate stat c f nal Map<MappableF eld, Str ng> MAPPABLE_F ELD_MAP =
       mmutableMap.of(
          MappableF eld.URL,
          Earlyb rdF eldConstant.RESOLVED_L NKS_TEXT_F ELD.getF eldNa ());

  pr vate stat c f nal Str ng ALLOW_QUERY_SPEC F C_S GNAL_DEC DER_KEY
      = "allow_query_spec f c_score_adjust nts";

  @V s bleForTest ng
  publ c stat c f nal Str ng ALLOW_AUTHOR_SPEC F C_S GNAL_DEC DER_KEY
      = "allow_author_spec f c_score_adjust nts";

  pr vate stat c f nal Str ng USE_MULT _TERM_D SJUNCT ON_FOR_L KED_BY_USER_ DS_DEC DER_KEY
      = "use_mult _term_d sjunct on_for_l ked_by_user_ ds";

  pr vate stat c f nal Str ng ALLOW_CAMELCASE_USERNAME_F ELD_WE GHT_OVERR DE_DEC DER_KEY_PREF X
      = "allow_ca lcase_userna _f eld_  ght_overr de_ n_";

  pr vate stat c f nal Str ng ALLOW_TOKEN ZED_D SPLAY_NAME_F ELD_WE GHT_OVERR DE_DEC DER_KEY_PREF X
      = "allow_token zed_d splay_na _f eld_  ght_overr de_ n_";

  pr vate stat c f nal boolean ALLOW_QUERY_SPEC F C_S GNAL_CONF G
      = Earlyb rdConf g.getBool("allow_query_spec f c_score_adjust nts", false);

  pr vate stat c f nal boolean ALLOW_AUTHOR_SPEC F C_S GNAL_CONF G
      = Earlyb rdConf g.getBool("allow_author_spec f c_score_adjust nts", false);

  publ c stat c f nal  nt DEFAULT_NUM_FACET_RESULTS = 100;

  pr vate f nal  mmutableSc ma nterface sc maSnapshot;
  pr vate f nal Earlyb rdCluster cluster;

  pr vate f nal Clock clock;
  pr vate f nal Dec der dec der;

  // T  actual request thr ft.
  pr vate f nal Earlyb rdRequest request;

  // searchQuery from  ns de t  request.
  pr vate f nal Thr ftSearchQuery searchQuery;

  // CollectorParams from  ns de t  searchQuery;
  pr vate f nal CollectorParams collectorParams;

  // Parsed query (parsed from ser al zed query str ng  n request).
  pr vate com.tw ter.search.queryparser.query.Query parsedQuery;
  pr vate boolean parsedQueryAllowNullcast;
  pr vate  dT  Ranges  dT  Ranges;

  // Lucene vers on of t  above.  T   s what   w ll actually be execut ng.
  pr vate org.apac .lucene.search.Query luceneQuery;

  // Used for quer es w re   want to collect per-f eld h  attr but on
  @Nullable
  pr vate QueryH Attr bute lper h Attr bute lper;

  // Debugg ng  nfo can be appended to t  buffer.
  pr vate f nal Str ngBu lder  ssageBuffer = new Str ngBu lder(1024);
  pr vate f nal Earlyb rdDebug nfo debug nfo = new Earlyb rdDebug nfo();

  // T  seg nt   are search ng, or null for t  mult -searc r.
  pr vate Seg nt seg nt = null;

  // True  ff   are search ng all seg nts (mult -searc r).
  pr vate f nal boolean searchAllSeg nts;

  // Track ng term nat on cr er a for t  query
  pr vate f nal Term nat onTracker term nat onTracker;

  pr vate Earlyb rdLuceneSearc r searc r = null;

  pr vate f nal Seg ntManager seg ntManager;
  pr vate f nal QueryCac Manager queryCac Manager;
  pr vate f nal Scor ngModelsManager scor ngModelsManager;
  pr vate f nal TensorflowModelsManager tensorflowModelsManager;

  pr vate Ant Gam ngF lter ant Gam ngF lter = null;

  pr vate f nal boolean searchH ghFrequencyTermPa rs =
      Earlyb rdConf g.getBool("search_h gh_frequency_term_pa rs", false);

  // How long to allow post-term nat on w n enforc ng query t  out
  pr vate f nal  nt enforceQueryT  outBufferM ll s =
      Earlyb rdConf g.get nt("enforce_query_t  out_buffer_m ll s", 50);

  pr vate Earlyb rdRPCStats requestStats;

  pr vate QueryT  outFactory queryT  outFactory;

  // Exported stats
  pr vate f nal Earlyb rdSearc rStats searc rStats;

  @V s bleForTest ng
  publ c stat c f nal SearchCounter F ELD_WE GHT_OVERR DE_MAP_NON_NULL_COUNT =
      SearchCounter.export("f eld_  ght_overr de_map_non_null_count");
  @V s bleForTest ng
  publ c stat c f nal SearchCounter DROPPED_CAMELCASE_USERNAME_F ELD_WE GHT_OVERR DE =
      SearchCounter.export("dropped_ca lcase_userna _f eld_  ght_overr de");
  @V s bleForTest ng
  publ c stat c f nal SearchCounter DROPPED_TOKEN ZED_D SPLAY_NAME_F ELD_WE GHT_OVERR DE =
      SearchCounter.export("dropped_token zed_d splay_na _f eld_  ght_overr de");

  pr vate stat c f nal SearchCounter RESPONSE_HAS_NO_THR FT_SEARCH_RESULTS =
      SearchCounter.export("t ets_earlyb rd_searc r_response_has_no_thr ft_search_results");
  pr vate stat c f nal SearchCounter CL ENT_HAS_FEATURE_SCHEMA_COUNTER =
      SearchCounter.export("t ets_earlyb rd_searc r_cl ent_has_feature_sc ma");
  pr vate stat c f nal SearchCounter CL ENT_DOESNT_HAVE_FEATURE_SCHEMA_COUNTER =
      SearchCounter.export("t et_earlyb rd_searc r_cl ent_doesnt_have_feature_sc ma");
  pr vate stat c f nal SearchCounter COLLECTOR_PARAMS_MAX_H TS_TO_PROCESS_NOT_SET_COUNTER =
      SearchCounter.export("collector_params_max_h s_to_process_not_set");
  pr vate stat c f nal SearchCounter POS T VE_PROTECTED_OPERATOR_DETECTED_COUNTER =
      SearchCounter.export("pos  ve_protected_operator_detected_counter");

  // Query mode   are execut ng.
  pr vate f nal QueryMode queryMode;

  // facetRequest from  ns de t  request (or null).
  pr vate f nal Thr ftFacetRequest facetRequest;

  // termStat st csRequest from  ns de t  request (or null).
  pr vate f nal Thr ftTermStat st csRequest termStat st csRequest;

  // Results f elds f lled  n dur ng search nternal().
  pr vate Thr ftSearchResults searchResults = null;
  pr vate Thr ftFacetResults facetResults = null;
  pr vate Thr ftTermStat st csResults termStat st csResults = null;
  pr vate EarlyTerm nat on nfo earlyTerm nat on nfo = null;

  // Part  on conf g used to f ll  n debugg ng  nfo.
  //  f null, no debug  nfo  s wr ten  nto results.
  @Nullable
  pr vate f nal Part  onConf g part  onConf g;

  pr vate f nal Mult Seg ntTermD ct onaryManager mult Seg ntTermD ct onaryManager;

  pr vate f nal Qual yFactor qual yFactor;

  pr vate Set<Str ng> quer edF elds;
  pr vate f nal Aud oSpaceTable aud oSpaceTable;

  publ c Earlyb rdSearc r(
      Earlyb rdRequest request,
      Seg ntManager seg ntManager,
      Aud oSpaceTable aud oSpaceTable,
      QueryCac Manager queryCac Manager,
       mmutableSc ma nterface sc ma,
      Earlyb rdCluster cluster,
      @Nullable Part  onConf g part  onConf g,
      Dec der dec der,
      Earlyb rdSearc rStats searc rStats,
      Scor ngModelsManager scor ngModelsManager,
      TensorflowModelsManager tensorflowModelsManager,
      Clock clock,
      Mult Seg ntTermD ct onaryManager mult Seg ntTermD ct onaryManager,
      QueryT  outFactory queryT  outFactory,
      Qual yFactor qual yFactor) {
    t .queryMode = getQueryMode(request);
    t .sc maSnapshot = sc ma.getSc maSnapshot();
    // set t  request stats as early as poss ble, so that   can track errors that happen
    // early on  n query process ng.
    t .requestStats = queryMode.getRequestStats();
    t .facetRequest = request. sSetFacetRequest() ? request.getFacetRequest() : null;
    t .termStat st csRequest = request. sSetTermStat st csRequest()
        ? request.getTermStat st csRequest() : null;
    t .part  onConf g = part  onConf g;
    t .searc rStats = searc rStats;
    t .mult Seg ntTermD ct onaryManager = mult Seg ntTermD ct onaryManager;
    t .clock = clock;
    t .dec der = dec der;
    t .request = request;
    t .seg ntManager = seg ntManager;
    t .queryCac Manager = queryCac Manager;
    t .cluster = cluster;
    t .scor ngModelsManager = scor ngModelsManager;
    t .tensorflowModelsManager = tensorflowModelsManager;
    t .aud oSpaceTable = aud oSpaceTable;
    // Note:  're deferr ng t  val dat on/nullc cks unt l val dateRequest()
    // for more conta ned except on handl ng
    t .searchQuery = request.getSearchQuery();
    t .collectorParams = t .searchQuery == null ? null : t .searchQuery.getCollectorParams();
    // Search all seg nts  f searchSeg nt d  s unset.
    t .searchAllSeg nts = !request. sSetSearchSeg nt d();
     f (t .collectorParams == null
        || !t .collectorParams. sSetTerm nat onParams()) {
      t .term nat onTracker = new Term nat onTracker(clock);
    } else  f (request. sSetCl entRequestT  Ms()) {
      t .term nat onTracker = new Term nat onTracker(collectorParams.getTerm nat onParams(),
          request.getCl entRequestT  Ms(), clock,
          getPostTerm nat onOver adM ll s(collectorParams.getTerm nat onParams()));
    } else {
      t .term nat onTracker = new Term nat onTracker(
          collectorParams.getTerm nat onParams(), clock,
          getPostTerm nat onOver adM ll s(collectorParams.getTerm nat onParams()));
    }
    t .queryT  outFactory = queryT  outFactory;
    t .qual yFactor = qual yFactor;
  }

  pr vate  nt getPostTerm nat onOver adM ll s(CollectorTerm nat onParams term nat onParams) {
    //  f enforc ng t  outs, set t  post-term nat on buffer to t  smaller of t  t  out or t 
    // conf gured buffer. T  ensures that t  out >= buffer, and a request w h a smaller t  out
    // should just t   out  m d ately (because t  out == buffer).
    return (term nat onParams. sEnforceQueryT  out() && term nat onParams.getT  outMs() > 0)
        ? Math.m n(enforceQueryT  outBufferM ll s, term nat onParams.getT  outMs()) : 0;
  }

  // Appends a debug str ng to t  buffer.
  pr vate vo d append ssage(Str ng  ssage) {
     ssageBuffer.append( ssage).append("\n");
  }

  /**
   * Processes an Earlyb rd search request.
   * @return t  earlyb rd response for t  search request.
   */
  publ c Earlyb rdResponse search() {
    try {
      debug nfo.setHost(DatabaseConf g.getLocalHostna ());

      // Throws trans ent except on for  nval d requests.
      val dateRequest();

      // Throws cl ent except on for bad quer es,
      parseEarlyb rdRequest();

      // Mod fy t  Lucene query  f necessary.
      luceneQuery = postLuceneQueryProcess(luceneQuery);

      // M ght return PART T ON_NOT_FOUND or PART T ON_D SABLED.
      Earlyb rdResponseCode code =  n Searc r();
       f (code != Earlyb rdResponseCode.SUCCESS) {
        return respondError(code);
      }

      return search nternal();

    } catch (Trans entExcept on e) {
      LOG.error(Str ng.format("Trans ent except on  n search() for Earlyb rdRequest:\n%s", request),
                e);
      append ssage(e.get ssage());
      return respondError(Earlyb rdResponseCode.TRANS ENT_ERROR);
    } catch (Cl entExcept on e) {
      LOG.warn(Str ng.format("Cl ent except on  n search() %s for Earlyb rdRequest:\n %s",
          e, request));
      append ssage(e.get ssage());
      return respondError(Earlyb rdResponseCode.CL ENT_ERROR);
    } catch (Except on e) {
      LOG.warn(Str ng.format("Uncaught except on  n search() for Earlyb rdRequest:\n%s", request),
               e);
      append ssage(e.get ssage());
      return respondError(Earlyb rdResponseCode.TRANS ENT_ERROR);
    } catch (Assert onError e) {
      LOG.warn(Str ng.format("Assert on error  n search() for Earlyb rdRequest:\n%s", request), e);
      append ssage(e.get ssage());
      return respondError(Earlyb rdResponseCode.TRANS ENT_ERROR);
    } catch (Error e) {
      // SEARCH-33166:  f   got  re,    ans what was thrown was not an Except on, or anyth ng
      //   know how to handle. Log t  Error for d agnost c purposes and propagate  .
      LOG.error("Re-throw ng uncaught error", e);
      throw e;
    }
  }

  publ c Earlyb rdRPCStats getRequestStats() {
    return requestStats;
  }

  /**
   * Wraps t  g ven query w h t  prov ded f lter quer es.
   *
   * @param query t  query to wrap w h f lters.
   * @param f lters t  f lters to wrap t  query w h.
   * @return a BooleanQuery wrapped w h f lters
   */
  publ c stat c Query wrapF lters(Query query, Query... f lters) {
    boolean f ltersEmpty = f lters == null || f lters.length == 0;

     f (!f ltersEmpty) {
      f ltersEmpty = true;
      for (Query f : f lters) {
         f (f != null) {
          f ltersEmpty = false;
          break;
        }
      }
    }

     f (f ltersEmpty) {
       f (query == null) {
        return new MatchAllDocsQuery();
      } else {
        return query;
      }
    }

    BooleanQuery.Bu lder bqBu lder = new BooleanQuery.Bu lder();
     f (query != null) {
      bqBu lder.add(query, Occur.MUST);
    }
    for (Query f : f lters) {
       f (f != null) {
        bqBu lder.add(f, Occur.F LTER);
      }
    }
    return bqBu lder.bu ld();
  }

  // Exam ne all f elds  n t  request for san y.
  pr vate vo d val dateRequest() throws Trans entExcept on, Cl entExcept on {
    // F rst try thr ft's  nternal val date.  Should always succeed.
    try {
      request.val date();
    } catch (TExcept on e) {
      throw new Trans entExcept on(e.get ssage(), e);
    }

     f (searchQuery == null) {
      throw new Trans entExcept on("No Thr ftSearchQuery spec f ed");
    }

     f (collectorParams == null) {
      throw new Trans entExcept on("No CollectorParams spec f ed");
    }

    val dateTermStatsRequest();

     f (!searchAllSeg nts) {
       f (request.getSearchSeg nt d() <= 0) {
        Str ng msg = "Bad t   sl ce  D: " + request.getSearchSeg nt d();
        throw new Trans entExcept on(msg);
      }

      //  n  al ze t  seg nt.
      Seg nt nfo seg nt nfo = t .seg ntManager.getSeg nt nfo(request.getSearchSeg nt d());
      seg nt = seg nt nfo != null ? seg nt nfo.getSeg nt() : null;
    }

     f (collectorParams.getNumResultsToReturn() < 0) {
      Str ng msg = " nval d numResults: " + collectorParams.getNumResultsToReturn();
      throw new Trans entExcept on(msg);
    }

     f (searchQuery.getNa dD sjunct onMapS ze() > 0 && searchQuery. sSetLuceneQuery()) {
      throw new Cl entExcept on("na dMult TermD sjunct onMap does not support w h luceneQuery");
    }
  }

  pr vate vo d val dateTermStatsRequest() throws Cl entExcept on {
    // Val date t  f eld na s and values for all Thr ftTermRequests.
     f (request. sSetTermStat st csRequest()
        && request.getTermStat st csRequest(). sSetTermRequests()) {
      for (Thr ftTermRequest termRequest : request.getTermStat st csRequest().getTermRequests()) {
        //  f termRequest.f eldNa   s not set,   defaults to 'text', wh ch  s a str ng f eld,
        // so   don't need to c ck t  term.
         f (termRequest. sSetF eldNa ()) {
          Str ng f eldNa  = termRequest.getF eldNa ();
          Sc ma.F eld nfo facetF eld nfo = sc maSnapshot.getFacetF eldByFacetNa (f eldNa );
           f (facetF eld nfo != null) {
            // Facet f elds are str ng f elds, so   don't need to c ck t  term.
            cont nue;
          }

          Sc ma.F eld nfo f eld nfo = sc maSnapshot.getF eld nfo(f eldNa );
           f (f eld nfo == null) {
            throw new Cl entExcept on("F eld " + f eldNa  + "  s not present  n t  sc ma.");
          }

          try {
            Sc maUt l.toBytesRef(f eld nfo, termRequest.getTerm());
          } catch (UnsupportedOperat onExcept on e) {
            throw new Cl entExcept on("Term " + termRequest.getTerm() + "  s not compat ble w h "
                                      + "t  type of f eld " + f eldNa );
          }
        }
      }
    }
  }

  pr vate vo d setQuer es nDebug nfo(
      com.tw ter.search.queryparser.query.Query parsedQ,
      org.apac .lucene.search.Query luceneQ) {
    debug nfo.setParsedQuery(parsedQ == null ? null : parsedQ.ser al ze());
    debug nfo.setLuceneQuery(luceneQ == null ? null : luceneQ.toStr ng());
  }

  /**
   * Takes t  Earlyb rdRequest that ca   nto t  serv ce and after var ous pars ng and process ng
   * steps ult mately produces a Lucene query.
   */
  pr vate vo d parseEarlyb rdRequest() throws Cl entExcept on {
    Ser al zedQueryParser parser = new Ser al zedQueryParser(Earlyb rdConf g.getPengu nVers on());

    try {
      //  f t  deprecated  erat veQuer es f eld  s set, return an error to t  cl ent
      //  nd cat ng that support for   has been removed.
       f (searchQuery. sSetDeprecated_ erat veQuer es()) {
        throw new Cl entExcept on(" nval d request:  erat veQuer es feature has been removed");
      }

      //   parse t  actual query from t  user,  f any
      luceneQuery = null;
      parsedQuery = null;  // t  w ll be set by parseQuery lper()

       f (searchQuery.getL kedByUser DF lter64S ze() > 0
          && searchQuery. sSetLuceneQuery()) {
        throw new Cl entExcept on("l kedByUser DF lter64 does not support w h luceneQuery");
      }

       f (!Str ngUt ls. sBlank(request.getSearchQuery().getSer al zedQuery())) {
        searc rStats.thr ftQueryW hSer al zedQuery. ncre nt();
        luceneQuery = parseSer al zedQuery(searchQuery.getSer al zedQuery(), parser, true);
      } else  f (!Str ngUt ls. sBlank(request.getSearchQuery().getLuceneQuery())) {
        searc rStats.thr ftQueryW hLuceneQuery. ncre nt();
        luceneQuery = parseLuceneQuery(searchQuery.getLuceneQuery());
        LOG. nfo("lucene query: {}", searchQuery.getLuceneQuery());
         f (luceneQuery != null) {
          LOG. nfo("Us ng lucene query d rectly from t  request: " + luceneQuery.toStr ng());
        }
      } else {
        searc rStats.thr ftQueryW houtTextQuery. ncre nt();
        luceneQuery = parseSer al zedQuery(
            MATCH_ALL_SER AL ZED_QUERY,
            parser,
            queryMode != QueryMode.TERM_STATS);
      }
    } catch (QueryParserExcept on | BooleanQuery.TooManyClauses e) {
      LOG. nfo("Except on pars ng query dur ng search", e);
      append ssage(e.get ssage());
      throw new Cl entExcept on(e);
    }
  }

  /**
   * Parses a ser al zed query and creates a Lucene query out of  .
   *
   * To see how ser al zed quer es look l ke, go to go/searchsyntax.
   */
  pr vate Query parseSer al zedQuery(
      Str ng ser al zedQuery,
      Ser al zedQueryParser parser,
      boolean shouldAdjustQueryBasedOnRequestPara ters) throws QueryParserExcept on {
    // Parse t  ser al zed query.
    parsedQuery = parser.parse(ser al zedQuery);
     f (parsedQuery == null) {
      return null;
    }

    // rewr e query  f pos  ve 'protected' operator  s detected
     f (parsedQuery.accept(new DetectPos  veOperatorV s or(SearchOperatorConstants.PROTECTED))) {
      POS T VE_PROTECTED_OPERATOR_DETECTED_COUNTER. ncre nt();
      ProtectedOperatorQueryRewr er rewr er = new ProtectedOperatorQueryRewr er();
      parsedQuery = rewr er.rewr e(
          parsedQuery,
          request.follo dUser ds,
          seg ntManager.getUserTable());
    }

    Thr ftSearchRelevanceOpt ons opt ons = searchQuery.getRelevanceOpt ons();
     f (shouldAdjustQueryBasedOnRequestPara ters) {
      //  f l kedByUser DF lter64  s set, comb ne   w h query
      // Note:   deal w h l kedByUser DF lter64  re  nstead of  n postLuceneQueryProcess as  
      // want annotate query w h ranks.
       f (searchQuery. sSetL kedByUser DF lter64()
          && searchQuery.getL kedByUser DF lter64S ze() > 0) {
        parsedQuery = comb neW hL kedByUser dF lter64(
            parsedQuery, searchQuery.getL kedByUser DF lter64());
      }

      //  f na dL stMap f eld  s set, replace t  na d l sts  n t  ser al zed query.
       f (searchQuery.getNa dD sjunct onMapS ze() > 0) {
        parsedQuery = parsedQuery.accept(
            new Na dD sjunct onV s or(searchQuery.getNa dD sjunct onMap()));
      }

       f (searchQuery. sSetRelevanceOpt ons()
          && searchQuery.getRelevanceOpt ons(). sCollectF eldH Attr but ons()) {
        // NOTE: Before   do any mod f cat ons to t  ser al zed query tree, annotate t  query
        // nodes w h t  r node rank  n t  or g nal query.
        t .h Attr bute lper =
            QueryH Attr bute lper.from(parsedQuery, sc maSnapshot);
        parsedQuery = h Attr bute lper.getAnnotatedQuery();
      }

      // Currently ant soc al/nullcast t ets are dropped w n   bu ld  ndex, but so  t ets may
      // beco  ant soc al w h realt   updates. For cons stency,   should always f lter out
      // ant soc al/nullcast t ets  f t  user  s not expl c ly  nclud ng  .
      f nal boolean allowAnt soc al =
          parsedQuery.accept(new DetectPos  veOperatorV s or(SearchOperatorConstants.ANT SOC AL));
       f (!allowAnt soc al) {
        parsedQuery = QueryNodeUt ls.appendAsConjunct on(
            parsedQuery,
            QueryCac Convers onRules.CACHED_EXCLUDE_ANT SOC AL);
      }
      parsedQueryAllowNullcast =
          parsedQuery.accept(new DetectPos  veOperatorV s or(SearchOperatorConstants.NULLCAST));
       f (!parsedQueryAllowNullcast) {
        parsedQuery = QueryNodeUt ls.appendAsConjunct on(
            parsedQuery, new SearchOperator("f lter", SearchOperatorConstants.NULLCAST).negate());
      }

      // Str p all annotat ons from t  f lters that w ll be converted to query cac  f lters.
      // See SEARCH-15552.
      parsedQuery = parsedQuery.accept(
          new Str pAnnotat onsV s or(QueryCac Convers onRules.STR P_ANNOTAT ONS_QUER ES));

      // Convert certa n f lters  nto cac d f lters, also consol date t m.
      parsedQuery = parsedQuery.accept(
          new Convers onV s or(QueryCac Convers onRules.DEFAULT_RULES));

      // add prox m y  f needed
       f (opt ons != null
          && opt ons. sProx m yScor ng()
          && searchQuery.getRank ngMode() != Thr ftSearchRank ngMode.RECENCY) {
        parsedQuery = parsedQuery.accept(new Prox m yGroupRewr eV s or()).s mpl fy();
      }
    }

     f (request. sSk pVeryRecentT ets()) {
      parsedQuery = restr ctQueryToFully ndexedT ets(parsedQuery);
    }

    parsedQuery = parsedQuery.s mpl fy();
    debug nfo.setParsedQuery(parsedQuery.ser al ze());

    // Extract top-level s nce- d for pag nat on opt m zat ons.
     dT  Ranges =  dT  Ranges.fromQuery(parsedQuery);

    // Does any f nal process ng spec f c to Earlyb rdSearch class.
    parsedQuery = preLuceneQueryProcess(parsedQuery);

    // Convert to a lucene query.
    Earlyb rdLuceneQueryV s or luceneV s or = getLuceneV s or(
        opt ons == null ? null : opt ons.getF eld  ghtMapOverr de());

     f (opt ons != null) {
      luceneV s or
          .setProx m yPhrase  ght((float) opt ons.getProx m yPhrase  ght())
          .setProx m yPhraseSlop(opt ons.getProx m yPhraseSlop());
    }

    // Propagate h  attr bute  lper to t  lucene v s or  f   has been setup.
    luceneV s or.setF eldH Attr bute lper(t .h Attr bute lper);

    org.apac .lucene.search.Query query = parsedQuery.accept(luceneV s or);
     f (query != null) {
      debug nfo.setLuceneQuery(query.toStr ng());
    }

    quer edF elds = luceneV s or.getQuer edF elds();

    return query;
  }

  pr vate Query parseLuceneQuery(Str ng query) {
    QueryParser parser = new QueryParser(
        Earlyb rdF eldConstant.TEXT_F ELD.getF eldNa (),
        new SearchWh espaceAnalyzer());
    parser.setSpl OnWh espace(true);
    try {
      return parser.parse(query);
    } catch (ParseExcept on e) {
      LOG.error("Cannot parse raw lucene query: " + query, e);
    } catch (NullPo nterExcept on e) {
      LOG.error("NullPo nterExcept on wh le pars ng raw lucene query: " + query
          + ", probably y  grammar  s wrong.\n", e);
    }
    return null;
  }

  pr vate com.tw ter.search.queryparser.query.Query comb neW hL kedByUser dF lter64(
      com.tw ter.search.queryparser.query.Query query,
      L st<Long>  ds) throws QueryParserExcept on {
    return QueryNodeUt ls.appendAsConjunct on(query, getL kedByUser dQuery( ds));
  }

  /**
   *  n Searc r  n  al zes t  seg ntSearc r, and returns SUCCESS  f OK
   * or so  ot r response code   not OK.
   */
  pr vate Earlyb rdResponseCode  n Searc r() throws  OExcept on {
    searc r = null;
     f (searchAllSeg nts) {
      return  n Mult Seg ntSearc r();
    } else {
      return  n S ngleSeg ntSearc r();
    }
  }

  pr vate Earlyb rdResponseCode  n S ngleSeg ntSearc r() throws  OExcept on {
     f (seg nt == null) {
      Str ng  ssage = "Seg nt not found for t   sl ce: " + request.getSearchSeg nt d();
      LOG.warn( ssage);
      append ssage( ssage);
      return Earlyb rdResponseCode.PART T ON_NOT_FOUND;
    }

    Earlyb rdResponseCode code = t .seg ntManager.c ckSeg nt(seg nt);
     f (code != Earlyb rdResponseCode.SUCCESS) {
      Str ng  ssage = "Seg nt " + seg nt + " e  r d sabled or dropped";
      LOG.warn( ssage);
      append ssage( ssage);
      return code;
    }

    searc r = seg ntManager.getSearc r(seg nt, sc maSnapshot);
     f (searc r == null) {
      Str ng  ssage = "Could not construct searc r for seg nt " + seg nt;
      LOG.error( ssage);
      append ssage( ssage);
      return Earlyb rdResponseCode.PERS STENT_ERROR;
    } else {
      append ssage("Search ng seg nt: " + seg nt);
      return Earlyb rdResponseCode.SUCCESS;
    }
  }

  pr vate Earlyb rdResponseCode  n Mult Seg ntSearc r() throws  OExcept on {
    Earlyb rdMult Seg ntSearc r mult Searc r =
        seg ntManager.getMult Searc r(sc maSnapshot);
    searc r = mult Searc r;
    Precond  ons.c ckNotNull(searc r);

    // Set a top level s nce  d to sk p ent re seg nts w n poss ble.
    mult Searc r.set dT  Ranges( dT  Ranges);
    return Earlyb rdResponseCode.SUCCESS;
  }

  pr vate com.tw ter.search.queryparser.query.Query
  restr ctQueryToFully ndexedT ets(com.tw ter.search.queryparser.query.Query query) {
    long unt lT  Seconds =
        RecentT etRestr ct on.recentT etsUnt lT  (dec der, ( nt) (clock.nowM ll s() / 1000));
     f (unt lT  Seconds == 0) {
      return query;
    }

    SearchOperator t  L m  = new SearchOperator(UNT L_T ME, unt lT  Seconds);
    return new Conjunct on(query, t  L m );
  }

  pr vate Earlyb rdResponse newResponse(Earlyb rdResponseCode code, boolean setDebug nfo) {
    Earlyb rdResponse response = new Earlyb rdResponse();
    response.setResponseCode(code);
     f (setDebug nfo) {
      response.setDebug nfo(debug nfo);
       f ( ssageBuffer.length() > 0) {
        response.setDebugStr ng(DatabaseConf g.getLocalHostna ()
                                + ":\n" +  ssageBuffer.toStr ng());
      }
    }
    return response;
  }

  pr vate Earlyb rdResponse respondError(Earlyb rdResponseCode code) {
    append ssage("Respond ng w h error code " + code);
    // Always respond w h an error  ssage, even w n request.debug  s false
    return newResponse(code, true);
  }

  @V s bleForTest ng
  publ c Term nat onTracker getTerm nat onTracker() {
    return term nat onTracker;
  }

  publ c vo d maybeSetCollectorDebug nfo(Tw terEarlyTerm nat onCollector collector) {
     f (request. sSetDebugOpt ons() && request.getDebugOpt ons(). s ncludeCollectorDebug nfo()) {
      debug nfo.setCollectorDebug nfo(collector.getDebug nfo());
    }
  }

  publ c vo d setTermStat st csDebug nfo(L st<Str ng> termStat st csDebug nfo) {
    debug nfo.setTermStat st csDebug nfo(termStat st csDebug nfo);
  }

  pr vate Earlyb rdResponse search nternal() throws Trans entExcept on, Cl entExcept on {
    searchResults = new Thr ftSearchResults();

    SearchResults nfo searchResults nfo;
    try {
      sw ch (queryMode) {
        case RECENCY:
          searchResults nfo = processRealt  Query();
          break;
        case RELEVANCE:
          // Relevance search and Model-based search d ffer only on t  scor ng funct on used.
          SearchT  r t  r = searc rStats.createT  r();
          t  r.start();
          searchResults nfo = processRelevanceQuery();
          t  r.stop();
          searc rStats.recordRelevanceStats(t  r, request);
          break;
        case FACETS:
          searchResults nfo = processFacetsQuery();
          break;
        case TERM_STATS:
          searchResults nfo = processTermStatsQuery();
          break;
        case TOP_TWEETS:
          searchResults nfo = processTopT etsQuery();
          break;
        default:
          throw new Trans entExcept on("Unknown query mode " + queryMode);
      }

      return respondSuccess(searchResults, facetResults, termStat st csResults,
          earlyTerm nat on nfo, searchResults nfo);
    } catch ( OExcept on e) {
      throw new Trans entExcept on(e.get ssage(), e);
    }
  }

  /**
   *  lper  thod to process facets query.
   */
  pr vate SearchResults nfo processFacetsQuery() throws Cl entExcept on,  OExcept on {
    // f gure out wh ch f elds   need to count
    FacetCountState facetCountState = newFacetCountState();

    // Add  onally wrap   query  nto a sk p l st boolean query for faster count ng.
     f (!facetRequest. sUs ngQueryCac ()) {
      // Only  f all f elds to be counted use sk p l sts, t n   can add a requ red clause
      // that f lters out all results that do not conta n those f elds
      boolean cannotAddRequ redClause = facetCountState.hasF eldToCountW houtSk pL st();
      f nal Query facetSk pL stF lter =
          cannotAddRequ redClause ? null : FacetSk pL st.getSk pL stQuery(facetCountState);
      f nal Query ant soc alF lter = UserFlagsExcludeF lter.getUserFlagsExcludeF lter(
          seg ntManager.getUserTable(), true, true, false);
      luceneQuery = wrapF lters(luceneQuery,
          facetSk pL stF lter,
          ant soc alF lter);
    }

    facetResults = new Thr ftFacetResults(new HashMap<>());

    FacetSearchRequest nfo searchRequest nfo =
        new FacetSearchRequest nfo(searchQuery, facetRequest.getFacetRank ngOpt ons(),
            luceneQuery, facetCountState, term nat onTracker);
    searchRequest nfo.set dT  Ranges( dT  Ranges);
     f (searchQuery.getMaxH sPerUser() > 0) {
      ant Gam ngF lter = new Ant Gam ngF lter(
          searchQuery.getMaxH sPerUser(),
          searchQuery.getMaxT epcredForAnt Gam ng(),
          luceneQuery);
    }

    AbstractResultsCollector<
        FacetSearchRequest nfo, Earlyb rdLuceneSearc r.FacetSearchResults> collector;
     f (request.getDebugMode() > 2) {
      collector = new Expla nFacetResultsCollector(sc maSnapshot,
          searchRequest nfo, ant Gam ngF lter, searc rStats, clock, request.debugMode);
    } else {
      collector = new FacetResultsCollector(sc maSnapshot,
          searchRequest nfo, ant Gam ngF lter, searc rStats, clock, request.debugMode);
    }

    setQuer es nDebug nfo(parsedQuery, searchRequest nfo.getLuceneQuery());
    searc r.search(searchRequest nfo.getLuceneQuery(), collector);
    Earlyb rdLuceneSearc r.FacetSearchResults h s = collector.getResults();

    Earlyb rdSearchResultUt l.setResultStat st cs(searchResults, h s);
    earlyTerm nat on nfo = Earlyb rdSearchResultUt l.prepareEarlyTerm nat on nfo(h s);
    Set<Long> user DWh el st =
        ant Gam ngF lter != null ? ant Gam ngF lter.getUser DWh el st() : null;
    prepareFacetResults(facetResults, h s, facetCountState, user DWh el st,
        request.getDebugMode());
    facetResults.setUser DWh el st(user DWh el st);

    maybeSetCollectorDebug nfo(collector);

     f (collector  nstanceof Expla nFacetResultsCollector) {
      ((Expla nFacetResultsCollector) collector).setExplanat ons(facetResults);
    }

    return h s;
  }

  /**
   *  lper  thod to process term-stats query.
   */
  pr vate SearchResults nfo processTermStatsQuery() throws  OExcept on {
    // f rst extract t  terms that   need to count
    TermStat st csRequest nfo searchRequest nfo =
        new TermStat st csRequest nfo(searchQuery, luceneQuery, termStat st csRequest,
            term nat onTracker);
    searchRequest nfo.set dT  Ranges( dT  Ranges);
    setQuer es nDebug nfo(parsedQuery, searchRequest nfo.getLuceneQuery());
    TermStat st csCollector.TermStat st csSearchResults h s =
        searc r.collectTermStat st cs(searchRequest nfo, t , request.getDebugMode());
    Earlyb rdSearchResultUt l.setResultStat st cs(searchResults, h s);
    earlyTerm nat on nfo = Earlyb rdSearchResultUt l.prepareEarlyTerm nat on nfo(h s);
     f (h s.results != null) {
      termStat st csResults = new Thr ftTermStat st csResults();
      prepareTermStat st csResults(termStat st csResults, h s, request.getDebugMode());
    }

    return h s;
  }

  /**
   *  lper  thod to process realt   query.
   */
  pr vate SearchResults nfo processRealt  Query() throws  OExcept on, Cl entExcept on {
    // D sable maxH sToProcess.
     f (!collectorParams. sSetTerm nat onParams()) {
      collectorParams.setTerm nat onParams(new CollectorTerm nat onParams());
      collectorParams.getTerm nat onParams().setMaxH sToProcess(-1);
      COLLECTOR_PARAMS_MAX_H TS_TO_PROCESS_NOT_SET_COUNTER. ncre nt();
    }

    SearchRequest nfo searchRequest nfo = new SearchRequest nfo(
      searchQuery, luceneQuery, term nat onTracker);
    searchRequest nfo.set dT  Ranges( dT  Ranges);
    searchRequest nfo.setH Attr bute lper(h Attr bute lper);
    searchRequest nfo.setT  stamp(getQueryT  stamp(searchQuery));

    AbstractResultsCollector<SearchRequest nfo, S mpleSearchResults> collector;
     f (searchQuery. sSetSoc alF lterType()) {
       f (!searchRequest nfo.getSearchQuery(). sSetD rectFollowF lter()
          || !searchRequest nfo.getSearchQuery(). sSetTrustedF lter()) {
        searc rStats.unsetF ltersForSoc alF lterTypeQuery. ncre nt();
        throw new Cl entExcept on(
            "Soc alF lterType spec f ed w hout a TrustedF lter or D rectFollowF lter");
      }
      Soc alF lter soc alF lter = new Soc alF lter(
          searchQuery.getSoc alF lterType(),
          searchRequest nfo.getSearchQuery().getSearc r d(),
          searchRequest nfo.getSearchQuery().getTrustedF lter(),
          searchRequest nfo.getSearchQuery().getD rectFollowF lter());
      collector = new Soc alSearchResultsCollector(
          sc maSnapshot,
          searchRequest nfo,
          soc alF lter,
          searc rStats,
          cluster,
          seg ntManager.getUserTable(),
          request.getDebugMode());
    } else {
      collector = new SearchResultsCollector(
          sc maSnapshot,
          searchRequest nfo,
          clock,
          searc rStats,
          cluster,
          seg ntManager.getUserTable(),
          request.getDebugMode());
    }

    setQuer es nDebug nfo(parsedQuery, luceneQuery);
    searc r.search(luceneQuery, collector);

    S mpleSearchResults h s = collector.getResults();

    Earlyb rdSearchResultUt l.setResultStat st cs(searchResults, h s);
    earlyTerm nat on nfo = Earlyb rdSearchResultUt l.prepareEarlyTerm nat on nfo(h s);
    Earlyb rdSearchResultUt l.prepareResultsArray(
        searchResults.getResults(), h s, request.debugMode > 0 ? part  onConf g : null);
    searchResults.setH Counts(collector.getH CountMap());

    maybeSetCollectorDebug nfo(collector);

    addResultPayloads();

    return h s;
  }

  /**
   *  lper  thod to process relevance query.
   */
  pr vate SearchResults nfo processRelevanceQuery() throws  OExcept on, Cl entExcept on {
     f (!searchQuery. sSetRelevanceOpt ons()) {
      LOG.warn("Relevance query w h no relevance opt ons!");
      searchQuery.setRelevanceOpt ons(new Thr ftSearchRelevanceOpt ons());
    }

    // Note: today t  assumpt on  s that  f   spec fy hasSpec f edT ets,
    //   really do want all t ets scored and returned.
    f nal boolean hasSpec f edT ets = searchQuery.getSearchStatus dsS ze() > 0;
     f (hasSpec f edT ets) {
      collectorParams.setNumResultsToReturn(searchQuery.getSearchStatus dsS ze());
    }
    //  f   have expl c  user  ds,   w ll want to look at all results from those users, and w ll
    // not need to use t  Ant Gam ngF lter.
    f nal boolean hasSpec f edFromUser ds = searchQuery.getFromUser DF lter64S ze() > 0;

    createRelevanceAnt Gam ngF lter(hasSpec f edT ets, hasSpec f edFromUser ds);

     f (searchQuery.getRelevanceOpt ons(). sSetRank ngParams()) {
      Thr ftRank ngParams rank ngParams = searchQuery.getRelevanceOpt ons().getRank ngParams();

      // T  score adjust nt s gnals that are passed  n t  request are d sabled for t  arch ve
      // cluster or w n t  features are dec dered off.  f t  request prov des those f elds,
      //   unset t m s nce c ck ng t  hashmap w n scor ng can cause a sl ght bump  n
      // latency.
      //
      // Ver fy that t  s gnal query spec f c scores for t ets s gnal  s enabled
       f (rank ngParams. sSetQuerySpec f cScoreAdjust nts()) {
         f (ALLOW_QUERY_SPEC F C_S GNAL_CONF G
            && Dec derUt l. sAva lableForRandomRec p ent(
            dec der, ALLOW_QUERY_SPEC F C_S GNAL_DEC DER_KEY)) {
          searc rStats.querySpec f cS gnalQuer esUsed. ncre nt();
          searc rStats.querySpec f cS gnalMapTotalS ze.add(
              rank ngParams.getQuerySpec f cScoreAdjust ntsS ze());
        } else {
          searchQuery.getRelevanceOpt ons().getRank ngParams().unsetQuerySpec f cScoreAdjust nts();
          searc rStats.querySpec f cS gnalQuer esErased. ncre nt();
        }
      }

      // Ver fy that t  s gnal author spec f c scores s gnal  s enabled
       f (rank ngParams. sSetAuthorSpec f cScoreAdjust nts()) {
         f (ALLOW_AUTHOR_SPEC F C_S GNAL_CONF G
            && Dec derUt l. sAva lableForRandomRec p ent(
            dec der, ALLOW_AUTHOR_SPEC F C_S GNAL_DEC DER_KEY)) {
          searc rStats.authorSpec f cS gnalQuer esUsed. ncre nt();
          searc rStats.authorSpec f cS gnalMapTotalS ze.add(
              rank ngParams.getAuthorSpec f cScoreAdjust ntsS ze());
        } else {
          searchQuery.getRelevanceOpt ons().getRank ngParams()
              .unsetAuthorSpec f cScoreAdjust nts();
          searc rStats.authorSpec f cS gnalQuer esErased. ncre nt();
        }
      }
    }

    Scor ngFunct on scor ngFunct on =
        new Scor ngFunct onProv der.DefaultScor ngFunct onProv der(
            request, sc maSnapshot, searchQuery, ant Gam ngF lter,
            seg ntManager.getUserTable(), h Attr bute lper,
            parsedQuery, scor ngModelsManager, tensorflowModelsManager)
            .getScor ngFunct on();
    scor ngFunct on.setDebugMode(request.getDebugMode());

    RelevanceQuery relevanceQuery = new RelevanceQuery(luceneQuery, scor ngFunct on);
    RelevanceSearchRequest nfo searchRequest nfo =
        new RelevanceSearchRequest nfo(
            searchQuery, relevanceQuery, term nat onTracker, qual yFactor);
    searchRequest nfo.set dT  Ranges( dT  Ranges);
    searchRequest nfo.setH Attr bute lper(h Attr bute lper);
    searchRequest nfo.setT  stamp(getQueryT  stamp(searchQuery));

     f (shouldUseTensorFlowCollector()
        && searchQuery.getRelevanceOpt ons(). sUseRelevanceAllCollector()) {
      throw new Cl entExcept on("Tensorflow scor ng does not work w h t  RelevanceAllCollector");
    }

    f nal AbstractRelevanceCollector collector;
    // F rst c ck  f t  Tensorflow results collector should be used, because t 
    // TensorflowBasedScor ngFunct on only works w h t  BatchRelevanceTopCollector
     f (shouldUseTensorFlowCollector()) {
      // Collect top numResults.
      collector = new BatchRelevanceTopCollector(
          sc maSnapshot,
          searchRequest nfo,
          scor ngFunct on,
          searc rStats,
          cluster,
          seg ntManager.getUserTable(),
          clock,
          request.getDebugMode());
    } else  f (hasSpec f edT ets
        || searchQuery.getRelevanceOpt ons(). sUseRelevanceAllCollector()) {
      // Collect all.
      collector = new RelevanceAllCollector(
          sc maSnapshot,
          searchRequest nfo,
          scor ngFunct on,
          searc rStats,
          cluster,
          seg ntManager.getUserTable(),
          clock,
          request.getDebugMode());
    } else {
      // Collect top numResults.
      collector = new RelevanceTopCollector(
          sc maSnapshot,
          searchRequest nfo,
          scor ngFunct on,
          searc rStats,
          cluster,
          seg ntManager.getUserTable(),
          clock,
          request.getDebugMode());
    }

    // Make sure that t  Tensorflow scor ng funct on and t  Tensorflow results collector are
    // always used toget r.  f t  fa ls   w ll result  n a TRANS ENT_ERROR response.
    Precond  ons.c ckState((collector  nstanceof BatchRelevanceTopCollector)
        == (scor ngFunct on  nstanceof TensorflowBasedScor ngFunct on));

    setQuer es nDebug nfo(parsedQuery, searchRequest nfo.getLuceneQuery());
    searc r.search(searchRequest nfo.getLuceneQuery(), collector);

    RelevanceSearchResults h s = collector.getResults();
    Earlyb rdSearchResultUt l.setResultStat st cs(searchResults, h s);
    searchResults.setScor ngT  Nanos(h s.getScor ngT  Nanos());

    earlyTerm nat on nfo = Earlyb rdSearchResultUt l.prepareEarlyTerm nat on nfo(h s);
    Earlyb rdSearchResultUt l.setLanguage togram(searchResults, collector.getLanguage togram());
    Earlyb rdSearchResultUt l.prepareRelevanceResultsArray(
        searchResults.getResults(),
        h s,
        ant Gam ngF lter != null ? ant Gam ngF lter.getUser DWh el st() : null,
        request.getDebugMode() > 0 ? part  onConf g : null);

    searchResults.setH Counts(collector.getH CountMap());
    searchResults.setRelevanceStats(h s.getRelevanceStats());

    maybeSetCollectorDebug nfo(collector);

     f (explanat onsEnabled(request.getDebugMode())) {
      searc r.expla nSearchResults(searchRequest nfo, h s, searchResults);
    }

    addResultPayloads();

    return h s;
  }

  publ c stat c boolean explanat onsEnabled( nt debugLevel) {
    return debugLevel > 1;
  }

  pr vate boolean shouldUseTensorFlowCollector() {
    return tensorflowModelsManager. sEnabled()
        && searchQuery.getRelevanceOpt ons(). sSetRank ngParams()
        && searchQuery.getRelevanceOpt ons().getRank ngParams(). sSetType()
        && searchQuery.getRelevanceOpt ons().getRank ngParams().getType()
        == Thr ftScor ngFunct onType.TENSORFLOW_BASED;
  }
  /**
   * Opt onally,  f requested and needed, w ll create a new Ant Gam ngF lter. Ot rw ze, no
   * Ant Gam ngF lter w ll be used for t  query.
   * @param hasSpec f edT ets w t r t  request has searchStatus ds spec f ed.
   * @param hasSpec f edFromUser ds w t r t  request has fromUser DF lter64 spec f ed.
   */
  pr vate vo d createRelevanceAnt Gam ngF lter(
      boolean hasSpec f edT ets, boolean hasSpec f edFromUser ds) {

    // Ant -gam ng f lter (turned off for spec f ed t ets mode, or w n   expl c ly ask ng
    // for spec f c users' t ets).
     f (searchQuery.getMaxH sPerUser() > 0 && !hasSpec f edT ets && !hasSpec f edFromUser ds) {
      searc rStats.relevanceAnt Gam ngF lterUsed. ncre nt();
      ant Gam ngF lter = new Ant Gam ngF lter(
          searchQuery.getMaxH sPerUser(),
          searchQuery.getMaxT epcredForAnt Gam ng(),
          luceneQuery);
    } else  f (searchQuery.getMaxH sPerUser() <= 0) {
      searc rStats.relevanceAnt Gam ngF lterNotRequested. ncre nt();
    } else  f (hasSpec f edT ets && hasSpec f edFromUser ds) {
      searc rStats.relevanceAnt Gam ngF lterSpec f edT etsAndFromUser ds. ncre nt();
    } else  f (hasSpec f edT ets) {
      searc rStats.relevanceAnt Gam ngF lterSpec f edT ets. ncre nt();
    } else  f (hasSpec f edFromUser ds) {
      searc rStats.relevanceAnt Gam ngF lterSpec f edFromUser ds. ncre nt();
    }
  }

  /**
   * C ck to make sure that t re are no nullcast docu nts  n results.   f t re ex sts nullcasts
   *  n results,   should log error and  ncre nt counters correspond ngly.
   */
  @V s bleForTest ng
  publ c vo d logAnd ncre ntStats fNullcast nResults(Thr ftSearchResults thr ftSearchResults) {
     f (!thr ftSearchResults. sSetResults()) {
      return;
    }

    Set<Long> unexpectedNullcastStatus ds =
        Earlyb rdResponseUt l.f ndUnexpectedNullcastStatus ds(thr ftSearchResults, request);

     f (!unexpectedNullcastStatus ds. sEmpty()) {
      searc rStats.nullcastUnexpectedQuer es. ncre nt();
      searc rStats.nullcastUnexpectedResults.add(unexpectedNullcastStatus ds.s ze());

      Str ng base64Request;
      try {
        base64Request = Thr ftUt ls.toBase64EncodedStr ng(request);
      } catch (TExcept on e) {
        base64Request = "Fa led to parse base 64 request";
      }
      LOG.error(
          "Found unexpected nullcast t ets: {} | parsedQuery: {} | request: {} | response: {} | "
              + "request base 64: {}",
          Jo ner.on(",").jo n(unexpectedNullcastStatus ds),
          parsedQuery.ser al ze(),
          request,
          thr ftSearchResults,
          base64Request);
    }
  }

  pr vate vo d addResultPayloads() throws  OExcept on {
     f (searchQuery.getResult tadataOpt ons() != null) {
       f (searchQuery.getResult tadataOpt ons(). sGetT etUrls()) {
        searc r.f llFacetResults(new ExpandedUrlCollector(), searchResults);
      }

       f (searchQuery.getResult tadataOpt ons(). sGetNa dEnt  es()) {
        searc r.f llFacetResults(new Na dEnt yCollector(), searchResults);
      }

       f (searchQuery.getResult tadataOpt ons(). sGetEnt yAnnotat ons()) {
        searc r.f llFacetResults(new Ent yAnnotat onCollector(), searchResults);
      }

       f (searchQuery.getResult tadataOpt ons(). sGetSpaces()) {
        searc r.f llFacetResults(new SpaceFacetCollector(aud oSpaceTable), searchResults);
      }
    }
  }

  /**
   *  lper  thod to process top t ets query.
   */
  pr vate SearchResults nfo processTopT etsQuery() throws  OExcept on, Cl entExcept on {
    // set dum  relevance opt ons  f  's not ava lable, but t  shouldn't happen  n prod
     f (!searchQuery. sSetRelevanceOpt ons()) {
      searchQuery.setRelevanceOpt ons(new Thr ftSearchRelevanceOpt ons());
    }
     f (!searchQuery.getRelevanceOpt ons(). sSetRank ngParams()) {
      searchQuery.getRelevanceOpt ons().setRank ngParams(
          // t   s  mportant, or  's gonna p ck DefaultScor ngFunct on wh ch pretty much
          // does noth ng.
          new Thr ftRank ngParams().setType(Thr ftScor ngFunct onType.TOPTWEETS));
    }
    Scor ngFunct on scor ngFunct on = new Scor ngFunct onProv der.DefaultScor ngFunct onProv der(
        request, sc maSnapshot, searchQuery, null,
        seg ntManager.getUserTable(), h Attr bute lper, parsedQuery,
        scor ngModelsManager, tensorflowModelsManager)
        .getScor ngFunct on();
    scor ngFunct on.setDebugMode(request.getDebugMode());

    RelevanceQuery relevanceQuery = new RelevanceQuery(luceneQuery, scor ngFunct on);
    RelevanceSearchRequest nfo searchRequest nfo =
        new RelevanceSearchRequest nfo(
            searchQuery, relevanceQuery, term nat onTracker, qual yFactor);
    searchRequest nfo.set dT  Ranges( dT  Ranges);
    searchRequest nfo.setT  stamp(getQueryT  stamp(searchQuery));

    f nal AbstractRelevanceCollector collector =
        new RelevanceTopCollector(
            sc maSnapshot,
            searchRequest nfo,
            scor ngFunct on,
            searc rStats,
            cluster,
            seg ntManager.getUserTable(),
            clock,
            request.getDebugMode());

    setQuer es nDebug nfo(parsedQuery, searchRequest nfo.getLuceneQuery());
    searc r.search(searchRequest nfo.getLuceneQuery(), collector);

    RelevanceSearchResults h s = collector.getResults();
    Earlyb rdSearchResultUt l.setResultStat st cs(searchResults, h s);
    searchResults.setScor ngT  Nanos(h s.getScor ngT  Nanos());
    earlyTerm nat on nfo = Earlyb rdSearchResultUt l.prepareEarlyTerm nat on nfo(h s);
    Earlyb rdSearchResultUt l.setLanguage togram(
        searchResults,
        collector.getLanguage togram());
    Earlyb rdSearchResultUt l.prepareRelevanceResultsArray(
        searchResults.getResults(),
        h s,
        null,
        request.getDebugMode() > 0 ? part  onConf g : null);

    searchResults.setH Counts(collector.getH CountMap());
    searchResults.setRelevanceStats(h s.getRelevanceStats());

    maybeSetCollectorDebug nfo(collector);

     f (explanat onsEnabled(request.getDebugMode())
        && searchQuery. sSetRelevanceOpt ons()
        && searchQuery.getRelevanceOpt ons(). sSetRank ngParams()) {
      searc r.expla nSearchResults(searchRequest nfo, h s, searchResults);
    }

    addResultPayloads();

    return h s;
  }

  pr vate FacetCountState newFacetCountState() throws Cl entExcept on {
     nt m nNumFacetResults = DEFAULT_NUM_FACET_RESULTS;
     f (facetRequest. sSetFacetRank ngOpt ons()
        && facetRequest.getFacetRank ngOpt ons(). sSetNumCand datesFromEarlyb rd()) {
      m nNumFacetResults = facetRequest.getFacetRank ngOpt ons().getNumCand datesFromEarlyb rd();
    }

    // f gure out wh ch f elds   need to count
    FacetCountState facetCountState = new FacetCountState(sc maSnapshot, m nNumFacetResults);

    // all categor es  f none!
     f (facetRequest.getFacetF elds() == null || facetRequest.getFacetF elds(). sEmpty()) {
      for (Sc ma.F eld nfo facetF eld : sc maSnapshot.getFacetF elds()) {
        facetCountState.addFacet(
            facetF eld.getF eldType().getFacetNa (), DEFAULT_NUM_FACET_RESULTS);
      }
    } else {
       erator<Thr ftFacetF eldRequest>   = facetRequest.getFacetF elds erator();
      wh le ( .hasNext()) {
        Thr ftFacetF eldRequest facetF eldRequest =  .next();
        Sc ma.F eld nfo facet = sc maSnapshot.getFacetF eldByFacetNa (
            facetF eldRequest.getF eldNa ());
         f (facet != null) {
          facetCountState.addFacet(
              facet.getF eldType().getFacetNa (), facetF eldRequest.getNumResults());
        } else {
          throw new Cl entExcept on("Unknown facet f eld: " + facetF eldRequest.getF eldNa ());
        }
      }
    }
    return facetCountState;
  }

  pr vate com.tw ter.search.queryparser.query.Query preLuceneQueryProcess(
      com.tw ter.search.queryparser.query.Query tw terQuery) throws QueryParserExcept on {

    com.tw ter.search.queryparser.query.Query query = tw terQuery;
     f (searchH ghFrequencyTermPa rs && ! ncludesCardF eld(searchQuery, query)) {
      // Process h gh frequency term pa rs. Works best w n query  s as flat as poss ble.
      query = H ghFrequencyTermPa rRewr eV s or.safeRewr e(
          query,
          Dec derUt l. sAva lableForRandomRec p ent(
              dec der, "enable_hf_term_pa r_negat ve_d sjunct on_rewr e"));
    }
    return query.s mpl fy();
  }

  pr vate Query postLuceneQueryProcess(f nal Query query) throws Cl entExcept on {
     f (Str ngUt ls. sBlank(request.getSearchQuery().getSer al zedQuery())
        && Str ngUt ls. sBlank(request.getSearchQuery().getLuceneQuery())) {
      searc rStats.numRequestsW hBlankQuery.get(queryMode). ncre nt();
       f (searchQuery.getSearchStatus dsS ze() == 0
          && searchQuery.getFromUser DF lter64S ze() == 0
          && searchQuery.getL kedByUser DF lter64S ze() == 0) {
        // No query or  ds to search.  T   s only allo d  n so  modes.
         f (queryMode == QueryMode.RECENCY
            || queryMode == QueryMode.RELEVANCE
            || queryMode == QueryMode.TOP_TWEETS) {
          throw new Cl entExcept on(
              "No query or status  ds for " + queryMode.toStr ng().toLo rCase() + " query");
        }
      }
    }

    // Wrap t  query as needed w h add  onal query f lters.
    L st<Query> f lters = L sts.newArrayL st();

    // M n t ep cred f lter.
     f (searchQuery. sSetM nT epCredF lter()) {
      searc rStats.addedF lterBadUserRep. ncre nt();
      f lters.add(BadUserRepF lter.getBadUserRepF lter(searchQuery.getM nT epCredF lter()));
    }

     f (searchQuery.getFromUser DF lter64S ze() > 0) {
      t .quer edF elds.add(Earlyb rdF eldConstant.FROM_USER_ D_F ELD.getF eldNa ());
      t .searc rStats.addedF lterFromUser ds. ncre nt();
      try {
        f lters.add(User dMult Seg ntQuery.create dD sjunct onQuery(
            "from_user_ d_f lter",
            searchQuery.getFromUser DF lter64(),
            Earlyb rdF eldConstant.FROM_USER_ D_F ELD.getF eldNa (),
            sc maSnapshot,
            mult Seg ntTermD ct onaryManager,
            dec der,
            cluster,
            L sts.newArrayL st(),
            null,
            queryT  outFactory.createQueryT  out(request, term nat onTracker, clock)));
      } catch (QueryParserExcept on e) {
        throw new Cl entExcept on(e);
      }
    }

    // Wrap t  lucene query w h t se f lters.
    Query wrappedQuery = wrapF lters(query, f lters.toArray(new Query[f lters.s ze()]));

    //  f searchStatus ds  s set, add  onally mod fy t  query to search exactly t se
    //  ds, us ng t  luceneQuery only for scor ng.
     f (searchQuery.getSearchStatus dsS ze() > 0) {
      t .searc rStats.addedF lterT et ds. ncre nt();

      f nal Query queryForScor ng = wrappedQuery;
      f nal Query queryForRetr eval =
          Requ redStatus DsF lter.getRequ redStatus DsQuery(searchQuery.getSearchStatus ds());

      return new BooleanQuery.Bu lder()
          .add(queryForRetr eval, Occur.MUST)
          .add(queryForScor ng, Occur.SHOULD)
          .bu ld();
    }

    return wrappedQuery;
  }

  pr vate com.tw ter.search.queryparser.query.Query getL kedByUser dQuery(
      L st<Long>  ds) throws QueryParserExcept on {
     f (Dec derUt l. sAva lableForRandomRec p ent(
        dec der, USE_MULT _TERM_D SJUNCT ON_FOR_L KED_BY_USER_ DS_DEC DER_KEY)) {
      // rewr e L kedByUser dF lter64 to a mult _term_d sjunt on query
      return createMult TermD sjunct onQueryForL kedByUser ds( ds);
    } else {
      // rewr e L kedByUser dF lter64 to a d sjunct on of mult ple l ked_by_user_ ds query
      return createD sjunct onQueryForL kedByUser ds( ds);
    }
  }

  /**
   * Returns t  Lucene query v s or that should be appl ed to t  or g nal request.
   *
   * @param f eld  ghtMapOverr de T  per-f eld   ght overr des.
   */
  @V s bleForTest ng
  publ c Earlyb rdLuceneQueryV s or getLuceneV s or(
      Map<Str ng, Double> f eld  ghtMapOverr de) {
    Str ng clusterNa  = cluster.getNa ForStats();
    //  ff  n relevance mode _and_  ntepreteS nce d  s false,   turn off s nce_ d
    // operator by us ng LuceneRelevanceQueryV s or.

     f (searchQuery.getRank ngMode() == Thr ftSearchRank ngMode.RELEVANCE
        && searchQuery.getRelevanceOpt ons() != null
        && !searchQuery.getRelevanceOpt ons(). s nterpretS nce d()) {
      // hack!  reset top level s nce  d, wh ch  s t  sa  th ng LuceneRelevanceV s or
      //  s do ng.
       dT  Ranges = null;
      return new LuceneRelevanceQueryV s or(
          sc maSnapshot,
          queryCac Manager,
          seg ntManager.getUserTable(),
          seg ntManager.getUserScrubGeoMap(),
          term nat onTracker,
          F eld  ghtDefault.overr deF eld  ghtMap(
              sc maSnapshot.getF eld  ghtMap(),
              dropBadF eld  ghtOverr des(f eld  ghtMapOverr de, dec der, clusterNa )),
          MAPPABLE_F ELD_MAP,
          mult Seg ntTermD ct onaryManager,
          dec der,
          cluster,
          queryT  outFactory.createQueryT  out(
              request, term nat onTracker, clock));
    } else {
      return new Earlyb rdLuceneQueryV s or(
          sc maSnapshot,
          queryCac Manager,
          seg ntManager.getUserTable(),
          seg ntManager.getUserScrubGeoMap(),
          term nat onTracker,
          F eld  ghtDefault.overr deF eld  ghtMap(
              sc maSnapshot.getF eld  ghtMap(),
              dropBadF eld  ghtOverr des(f eld  ghtMapOverr de, dec der, clusterNa )),
          MAPPABLE_F ELD_MAP,
          mult Seg ntTermD ct onaryManager,
          dec der,
          cluster,
          queryT  outFactory.createQueryT  out(
              request, term nat onTracker, clock));
    }
  }

  pr vate vo d prepareFacetResults(Thr ftFacetResults thr ftFacetResults,
                                     Earlyb rdLuceneSearc r.FacetSearchResults h s,
                                     FacetCountState<Thr ftFacetF eldResults> facetCountState,
                                     Set<Long> user DWh el st,
                                     byte debugMode) throws  OExcept on {
    for (FacetRank ngModule rank ngModule : FacetRank ngModule.REG STERED_RANK NG_MODULES) {
      rank ngModule.prepareResults(h s, facetCountState);
    }

    Map<Term, Thr ftFacetCount> allFacetResults = new HashMap<>();

     erator<FacetCountState.FacetF eldResults<Thr ftFacetF eldResults>> f eldResults erator =
        facetCountState.getFacetF eldResults erator();
    wh le (f eldResults erator.hasNext()) {

      FacetCountState.FacetF eldResults<Thr ftFacetF eldResults> facetF eldResults =
          f eldResults erator.next();

       f (facetF eldResults.results == null) {
        // return empty resultset for t  facet
        L st<Thr ftFacetCount> emptyL st = new ArrayL st<>();
        facetF eldResults.results = new Thr ftFacetF eldResults(emptyL st, 0);
      }
      thr ftFacetResults.putToFacetF elds(facetF eldResults.facetNa ,
          facetF eldResults.results);

      Sc ma.F eld nfo f eld = sc maSnapshot.getFacetF eldByFacetNa (
          facetF eldResults.facetNa );

      for (Thr ftFacetCount result : facetF eldResults.results.topFacets) {
         f (result.facetLabel != null) {
          allFacetResults.put(new Term(f eld.getNa (), result.facetLabel), result);
        } else {
          LOG.warn("Null facetLabel, f eld: {}, result: {}", f eld.getNa (), result);
        }
      }
    }

    searc r.f llFacetResult tadata(allFacetResults, sc maSnapshot, debugMode);

     f (user DWh el st != null) {
      for (Thr ftFacetCount facetCount : allFacetResults.values()) {
        Thr ftFacetCount tadata  tadata = facetCount.get tadata();
         f ( tadata != null) {
           tadata.setDontF lterUser(user DWh el st.conta ns( tadata.getTw terUser d()));
        }
      }
    }
  }

  pr vate vo d prepareTermStat st csResults(
      Thr ftTermStat st csResults termStat st cs,
      TermStat st csCollector.TermStat st csSearchResults h s,
      byte debugMode) throws  OExcept on {

    termStat st cs.setB n ds(h s.b n ds);
    termStat st cs.set togramSett ngs(termStat st csRequest.get togramSett ngs());
    termStat st cs.setTermResults(h s.results);
    setTermStat st csDebug nfo(h s.getTermStat st csDebug nfo());

     f (h s.lastCompleteB n d != -1) {
      termStat st cs.setM nCompleteB n d(h s.lastCompleteB n d);
    } else {
      SearchRateCounter.export(Str ng.format(
          "term_stats_%s_unset_m n_complete_b n_ d", request.getCl ent d())). ncre nt();
    }

     f ( dT  Ranges != null
        &&  dT  Ranges.getUnt lT  Exclus ve(). sPresent()
        && h s.getM nSearc dT  () >  dT  Ranges.getUnt lT  Exclus ve().get()) {
      SearchRateCounter.export(Str ng.format(
          "term_stats_%s_m n_searc d_t  _after_unt l_t  ", request.getCl ent d())). ncre nt();
    }

    searc r.f llTermStats tadata(termStat st cs, sc maSnapshot, debugMode);
  }

  pr vate Earlyb rdResponse respondSuccess(
      Thr ftSearchResults thr ftSearchResults,
      Thr ftFacetResults thr ftFacetResults,
      Thr ftTermStat st csResults termStat st cResults,
      @Nonnull EarlyTerm nat on nfo earlyTerm nat onState,
      @Nonnull SearchResults nfo searchResults nfo) {

    Precond  ons.c ckNotNull(earlyTerm nat onState);
    Precond  ons.c ckNotNull(searchResults nfo);

    exportEarlyTerm nat onStats(earlyTerm nat onState);

    Earlyb rdResponse response =
        newResponse(Earlyb rdResponseCode.SUCCESS, request.getDebugMode() > 0);
    response.setEarlyTerm nat on nfo(earlyTerm nat onState);
    response.setNumSearc dSeg nts(searchResults nfo.getNumSearc dSeg nts());

     f (thr ftSearchResults != null) {
      // Nullcast c ck  s only used w n parsed query  s ava lable:  f t re  s no parsed query,
      //   would not add poss ble exclude nullcast f lter.
       f (parsedQuery != null && !parsedQueryAllowNullcast) {
        logAnd ncre ntStats fNullcast nResults(thr ftSearchResults);
      }
      response.setSearchResults(thr ftSearchResults);
    } else {
      RESPONSE_HAS_NO_THR FT_SEARCH_RESULTS. ncre nt();
    }
     f (thr ftFacetResults != null) {
      response.setFacetResults(thr ftFacetResults);
    }
     f (termStat st cResults != null) {
      response.setTermStat st csResults(termStat st cResults);
    }

    appendFeatureSc ma fNeeded(response);

    appendL kedByUser ds fNeeded(response);

    return response;
  }

  pr vate vo d exportEarlyTerm nat onStats(@Nonnull EarlyTerm nat on nfo earlyTerm nat onState) {
     f (earlyTerm nat onState. sSetEarlyTerm nat onReason()) {
      SearchRateCounter.export(Str ng.format("early_term nat on_%s_%s",
          Cl ent dUt l.formatCl ent d(request.getCl ent d()),
          earlyTerm nat onState.getEarlyTerm nat onReason())). ncre nt();
      SearchRateCounter.export(Str ng.format("early_term nat on_%s_%s",
          Cl ent dUt l.formatCl ent dAndRequestType(
              request.getCl ent d(), queryMode.na ().toLo rCase()),
          earlyTerm nat onState.getEarlyTerm nat onReason())). ncre nt();
    }
  }

  /**
   * Bu lds a rank -> user d map for l ked_by_user_ d quer es that request h  attr but on, and
   * appends t  result ng map to t  response.
   */
  pr vate vo d appendL kedByUser ds fNeeded(Earlyb rdResponse response) {
    // C ck  f user asked for l kedByUser ds l st  n response
    Thr ftSearchRelevanceOpt ons resultRelevanceOpt ons =
        request.getSearchQuery().getRelevanceOpt ons();
     f ((resultRelevanceOpt ons == null)
        || !resultRelevanceOpt ons. sCollectF eldH Attr but ons()) {
      return;
    }

    // Make sure   have results  n response and h  attr but on  lper  s set up correctly
     f (!response. sSetSearchResults() || h Attr bute lper == null) {
      return;
    }

    // Get rank to node map
    Map<com.tw ter.search.queryparser.query.Query,  nteger> nodeToRankMap =
        Precond  ons.c ckNotNull(h Attr bute lper.getNodeToRankMap());

    Map<com.tw ter.search.queryparser.query.Query, L st< nteger>> expandedNodeToRankMap =
        Precond  ons.c ckNotNull(h Attr bute lper.getExpandedNodeToRankMap());

    // Bu ld a rank to  d map
     mmutableMap.Bu lder< nteger, Long> bu lder =  mmutableMap.bu lder();
    for (com.tw ter.search.queryparser.query.Query query : nodeToRankMap.keySet()) {
       f (query  nstanceof SearchOperator) {
        SearchOperator op = (SearchOperator) query;
         f (expandedNodeToRankMap.conta nsKey(query)) {
          // for mult _term_d sjunct on case
          L st< nteger> ranks = expandedNodeToRankMap.get(op);
          Precond  ons.c ckArgu nt(op.getNumOperands() == ranks.s ze() + 1);
          for ( nt   = 0;   < ranks.s ze(); ++ ) {
            bu lder.put(ranks.get( ), Long.valueOf(op.getOperands().get(  + 1)));
          }
        } else  f (op.getOperatorType() == SearchOperator.Type.L KED_BY_USER_ D) {
          // for l ked_by_user_ d case
          Precond  ons.c ckArgu nt(op.getAnnotat onOf(Annotat on.Type.NODE_RANK). sPresent());
          bu lder.put(
              ( nteger) op.getAnnotat onOf(Annotat on.Type.NODE_RANK).get().getValue(),
              Long.valueOf(op.getOperands().get(0)));
        }
      }
    }
    Map< nteger, Long> rankTo dMap = bu lder.bu ld();

    // Append l ked_by_user_ d f led  nto result
    for (Thr ftSearchResult result : response.getSearchResults().getResults()) {
       f (result. sSet tadata()
          && result.get tadata(). sSetF eldH Attr but on()
          && result.get tadata().getF eldH Attr but on(). sSetH Map()) {

        L st<Long> l kedByUser dL st = L sts.newArrayL st();

        Map< nteger, F eldH L st> h Map =
            result.get tadata().getF eldH Attr but on().getH Map();
        //  erate h  attr but ons
        for ( nt rank : h Map.keySet()) {
           f (rankTo dMap.conta nsKey(rank)) {
            l kedByUser dL st.add(rankTo dMap.get(rank));
          }
        }
         f (!result.get tadata(). sSetExtra tadata()) {
          result.get tadata().setExtra tadata(new Thr ftSearchResultExtra tadata());
        }
        result.get tadata().getExtra tadata().setL kedByUser ds(l kedByUser dL st);
      }
    }
  }

  pr vate vo d appendFeatureSc ma fNeeded(Earlyb rdResponse response) {
    // Do not append t  sc ma  f t  cl ent d dn't request  .
    Thr ftSearchResult tadataOpt ons result tadataOpt ons =
        request.getSearchQuery().getResult tadataOpt ons();
     f ((result tadataOpt ons == null) || !result tadataOpt ons. sReturnSearchResultFeatures()) {
      return;
    }

     f (!response. sSetSearchResults()) {
      return;
    }

    Thr ftSearchFeatureSc ma featureSc ma = sc maSnapshot.getSearchFeatureSc ma();
    Precond  ons.c ckState(
        featureSc ma. sSetSc maSpec f er(),
        "T  feature sc ma doesn't have a sc ma spec f er set: {}", featureSc ma);

    //  f t  cl ent has t  sc ma,   only need to return t  sc ma vers on.
    //  f t  cl ent doesn't have t  sc ma,   need to return t  sc ma entr es too.
     f (result tadataOpt ons. sSetFeatureSc masAva lable nCl ent()
        && result tadataOpt ons.getFeatureSc masAva lable nCl ent().conta ns(
        featureSc ma.getSc maSpec f er())) {
      CL ENT_HAS_FEATURE_SCHEMA_COUNTER. ncre nt();
      Thr ftSearchFeatureSc ma responseFeatureSc ma = new Thr ftSearchFeatureSc ma();
      responseFeatureSc ma.setSc maSpec f er(featureSc ma.getSc maSpec f er());
      response.getSearchResults().setFeatureSc ma(responseFeatureSc ma);
    } else {
      CL ENT_DOESNT_HAVE_FEATURE_SCHEMA_COUNTER. ncre nt();
      Precond  ons.c ckState(featureSc ma. sSetEntr es(),
          "Entr es are not set  n t  feature sc ma: " + featureSc ma);
      response.getSearchResults().setFeatureSc ma(featureSc ma);
    }
  }

  pr vate stat c long getQueryT  stamp(Thr ftSearchQuery query) {
    return query != null && query. sSetT  stampMsecs() ? query.getT  stampMsecs() : 0;
  }

  pr vate stat c boolean  ncludesCardF eld(Thr ftSearchQuery searchQuery,
                                           com.tw ter.search.queryparser.query.Query query)
      throws QueryParserExcept on {

     f (searchQuery. sSetRelevanceOpt ons()) {
      Thr ftSearchRelevanceOpt ons opt ons = searchQuery.getRelevanceOpt ons();
       f (opt ons. sSetF eld  ghtMapOverr de()
          && (opt ons.getF eld  ghtMapOverr de().conta nsKey(
              Earlyb rdF eldConstant.CARD_T TLE_F ELD.getF eldNa ())
          || opt ons.getF eld  ghtMapOverr de()
          .conta nsKey(Earlyb rdF eldConstant.CARD_DESCR PT ON_F ELD.getF eldNa ()))) {

        return true;
      }
    }

    return query.accept(new DetectF eldAnnotat onV s or( mmutableSet.of(
        Earlyb rdF eldConstant.CARD_T TLE_F ELD.getF eldNa (),
        Earlyb rdF eldConstant.CARD_DESCR PT ON_F ELD.getF eldNa ())));
  }

  pr vate stat c QueryMode getQueryMode(Earlyb rdRequest request) {
     f (request. sSetFacetRequest()) {
      return QueryMode.FACETS;
    } else  f (request. sSetTermStat st csRequest()) {
      return QueryMode.TERM_STATS;
    }

    // Recency mode unt l   determ ne ot rw se.
    QueryMode queryMode = QueryMode.RECENCY;
    Thr ftSearchQuery searchQuery = request.getSearchQuery();
     f (searchQuery != null) {
      sw ch (searchQuery.getRank ngMode()) {
        case RECENCY:
          queryMode = QueryMode.RECENCY;
          break;
        case RELEVANCE:
          queryMode = QueryMode.RELEVANCE;
          break;
        case TOPTWEETS:
          queryMode = QueryMode.TOP_TWEETS;
          break;
        default:
          break;
      }
    }

     f (searchQuery == null
        || !searchQuery. sSetSer al zedQuery()
        || searchQuery.getSer al zedQuery(). sEmpty()) {
      LOG.debug("Search query was empty, query mode was " + queryMode);
    }

    return queryMode;
  }

  pr vate stat c <V>  mmutableMap<Str ng, V> dropBadF eld  ghtOverr des(
      Map<Str ng, V> map, Dec der dec der, Str ng clusterNa ) {

     f (map == null) {
      return null;
    }

    F ELD_WE GHT_OVERR DE_MAP_NON_NULL_COUNT. ncre nt();
     mmutableMap.Bu lder<Str ng, V> bu lder =  mmutableMap.bu lder();

    for (Map.Entry<Str ng, V> entry : map.entrySet()) {
       f (Earlyb rdF eldConstant.CAMELCASE_USER_HANDLE_F ELD.getF eldNa ().equals(entry.getKey())
          && ! sAllo dCa lcaseUserna F eld  ghtOverr de(dec der, clusterNa )) {
        DROPPED_CAMELCASE_USERNAME_F ELD_WE GHT_OVERR DE. ncre nt();
      } else  f (Earlyb rdF eldConstant.TOKEN ZED_USER_NAME_F ELD.getF eldNa ().equals(
                     entry.getKey())
          && ! sAllo dToken zedScreenNa F eld  ghtOverr de(dec der, clusterNa )) {
        DROPPED_TOKEN ZED_D SPLAY_NAME_F ELD_WE GHT_OVERR DE. ncre nt();
      } else {
        bu lder.put(entry.getKey(), entry.getValue());
      }
    }

    return bu lder.bu ld();
  }

  pr vate stat c boolean  sAllo dCa lcaseUserna F eld  ghtOverr de(
      Dec der dec der, Str ng clusterNa ) {
    return Dec derUt l. sAva lableForRandomRec p ent(dec der,
        ALLOW_CAMELCASE_USERNAME_F ELD_WE GHT_OVERR DE_DEC DER_KEY_PREF X + clusterNa );
  }

  pr vate stat c boolean  sAllo dToken zedScreenNa F eld  ghtOverr de(
      Dec der dec der, Str ng clusterNa ) {
    return Dec derUt l. sAva lableForRandomRec p ent(dec der,
        ALLOW_TOKEN ZED_D SPLAY_NAME_F ELD_WE GHT_OVERR DE_DEC DER_KEY_PREF X + clusterNa );
  }

  pr vate stat c com.tw ter.search.queryparser.query.Query
  createMult TermD sjunct onQueryForL kedByUser ds(L st<Long>  ds) throws QueryParserExcept on {
    L st<Str ng> operands = new ArrayL st<>( ds.s ze() + 1);
    operands.add(Earlyb rdF eldConstant.L KED_BY_USER_ D_F ELD.getF eldNa ());
    for (long  d :  ds) {
      operands.add(Str ng.valueOf( d));
    }
    return new SearchOperator(SearchOperator.Type.MULT _TERM_D SJUNCT ON, operands)
        .s mpl fy();
  }

  pr vate stat c com.tw ter.search.queryparser.query.Query createD sjunct onQueryForL kedByUser ds(
      L st<Long>  ds) throws QueryParserExcept on {
    return new D sjunct on(
         ds.stream()
            .map( d -> new SearchOperator(SearchOperator.Type.L KED_BY_USER_ D,  d))
            .collect(Collectors.toL st()))
        .s mpl fy();
  }

  publ c com.tw ter.search.queryparser.query.Query getParsedQuery() {
    return parsedQuery;
  }

  /**
   * Get t   ndex f elds that  re quer ed after t  searc r completed  s job.
   * @return
   */
  publ c Set<Str ng> getQuer edF elds() {
    return quer edF elds;
  }

  publ c Query getLuceneQuery() {
    return luceneQuery;
  }
}
