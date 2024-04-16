package com.tw ter.search.earlyb rd.queryparser;

 mport java.ut l.Arrays;
 mport java.ut l.Collect on;
 mport java.ut l.Collect ons;
 mport java.ut l.HashSet;
 mport java.ut l.L st;
 mport java.ut l.Map;
 mport java.ut l.Set;
 mport java.ut l.TreeSet;
 mport javax.annotat on.Nullable;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Funct ons;
 mport com.google.common.base.Opt onal;
 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect. mmutableL st;
 mport com.google.common.collect. mmutableMap;
 mport com.google.common.collect.L sts;
 mport com.google.common.collect.Sets;

 mport org.apac .lucene.search.BooleanClause;
 mport org.apac .lucene.search.BooleanClause.Occur;
 mport org.apac .lucene.search.BooleanQuery;
 mport org.apac .lucene.search.BoostQuery;
 mport org.apac .lucene.search.MatchNoDocsQuery;
 mport org.apac .lucene.search.PhraseQuery;
 mport org.apac .lucene.search.Query;
 mport org.apac .lucene.search.TermQuery;
 mport org.locat ontech.spat al4j.shape.Po nt;
 mport org.locat ontech.spat al4j.shape.Rectangle;
 mport org.locat ontech.spat al4j.shape. mpl.Po nt mpl;
 mport org.locat ontech.spat al4j.shape. mpl.Rectangle mpl;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.dec der.Dec der;
 mport com.tw ter.search.common.constants.QueryCac Constants;
 mport com.tw ter.search.common.dec der.Dec derUt l;
 mport com.tw ter.search.common.encod ng.features.ByteNormal zer;
 mport com.tw ter.search.common. ndex ng.thr ftjava.Thr ftGeoLocat onS ce;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common.query.BoostUt ls;
 mport com.tw ter.search.common.query.F eld  ghtUt l;
 mport com.tw ter.search.common.query.F lteredQuery;
 mport com.tw ter.search.common.query.H Attr bute lper;
 mport com.tw ter.search.common.query.MappableF eld;
 mport com.tw ter.search.common.sc ma. mmutableSc ma;
 mport com.tw ter.search.common.sc ma.Sc maUt l;
 mport com.tw ter.search.common.sc ma.base.F eld  ghtDefault;
 mport com.tw ter.search.common.sc ma.base. mmutableSc ma nterface;
 mport com.tw ter.search.common.sc ma.base.Sc ma;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdCluster;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdThr ftDocu ntBu lder;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdThr ftDocu ntUt l;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftCSFType;
 mport com.tw ter.search.common.search.Term nat onTracker;
 mport com.tw ter.search.common.search.term nat on.QueryT  out;
 mport com.tw ter.search.common.ut l.analys s. ntTermAttr bute mpl;
 mport com.tw ter.search.common.ut l.analys s.LongTermAttr bute mpl;
 mport com.tw ter.search.common.ut l.spat al.GeohashChunk mpl;
 mport com.tw ter.search.common.ut l.text.H ghFrequencyTermPa rs;
 mport com.tw ter.search.common.ut l.text.Normal zer lper;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdConf g;
 mport com.tw ter.search.earlyb rd.common.userupdates.UserScrubGeoMap;
 mport com.tw ter.search.earlyb rd.common.userupdates.UserTable;
 mport com.tw ter.search.earlyb rd.part  on.Mult Seg ntTermD ct onaryManager;
 mport com.tw ter.search.earlyb rd.querycac .Cac dF lterQuery;
 mport com.tw ter.search.earlyb rd.querycac .QueryCac Manager;
 mport com.tw ter.search.earlyb rd.search.quer es.CSFD sjunct onF lter;
 mport com.tw ter.search.earlyb rd.search.quer es.DocValRangeF lter;
 mport com.tw ter.search.earlyb rd.search.quer es.FeatureValue nAcceptL stOrUnsetF lter;
 mport com.tw ter.search.earlyb rd.search.GeoQuadTreeQueryBu lder;
 mport com.tw ter.search.earlyb rd.search.quer es.MatchAllDocsQuery;
 mport com.tw ter.search.earlyb rd.search.quer es.Requ redStatus DsF lter;
 mport com.tw ter.search.earlyb rd.search.quer es.S nceMax DF lter;
 mport com.tw ter.search.earlyb rd.search.quer es.S nceUnt lF lter;
 mport com.tw ter.search.earlyb rd.search.quer es.TermQueryW hSafeToStr ng;
 mport com.tw ter.search.earlyb rd.search.quer es.UserFlagsExcludeF lter;
 mport com.tw ter.search.earlyb rd.search.quer es.UserScrubGeoF lter;
 mport com.tw ter.search.earlyb rd.search.quer es.User dMult Seg ntQuery;
 mport com.tw ter.search.earlyb rd.search.relevance.M nFeatureValueF lter;
 mport com.tw ter.search.earlyb rd.search.relevance.ScoreF lterQuery;
 mport com.tw ter.search.earlyb rd.search.relevance.scor ng.Scor ngFunct onProv der;
 mport com.tw ter.search.queryparser.query.Conjunct on;
 mport com.tw ter.search.queryparser.query.D sjunct on;
 mport com.tw ter.search.queryparser.query.Phrase;
 mport com.tw ter.search.queryparser.query.QueryNodeUt ls;
 mport com.tw ter.search.queryparser.query.QueryParserExcept on;
 mport com.tw ter.search.queryparser.query.Spec alTerm;
 mport com.tw ter.search.queryparser.query.Term;
 mport com.tw ter.search.queryparser.query.annotat on.Annotat on;
 mport com.tw ter.search.queryparser.query.annotat on.FloatAnnotat on;
 mport com.tw ter.search.queryparser.query.search.L nk;
 mport com.tw ter.search.queryparser.query.search.SearchOperator;
 mport com.tw ter.search.queryparser.query.search.SearchOperatorConstants;
 mport com.tw ter.search.queryparser.query.search.SearchQueryV s or;
 mport com.tw ter.search.queryparser.ut l.GeoCode;
 mport com.tw ter.serv ce.sp derduck.gen.L nkCategory;
 mport com.tw ter.t etyp e.thr ftjava.ComposerS ce;

/**
 * V s or for {@l nk com.tw ter.search.queryparser.query.Query}, wh ch produces a Lucene
 * Query ({@l nk Query}).
 */
publ c class Earlyb rdLuceneQueryV s or extends SearchQueryV s or<Query> {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Earlyb rdLuceneQueryV s or.class);

  @V s bleForTest ng
  stat c f nal Str ng UNSUPPORTED_OPERATOR_PREF X = "unsupported_query_operator_";

  pr vate stat c f nal Str ng SM LEY_FORMAT_STR NG = "__has_%s_sm ley";
  pr vate stat c f nal Str ng PHRASE_W LDCARD = "*";
  pr vate stat c f nal float DEFAULT_F ELD_WE GHT = 1.0f;

  pr vate stat c f nal SearchCounter S NCE_T ME_ NVAL D_ NT_COUNTER =
      SearchCounter.export("Earlyb rdLuceneQueryV s or_s nce_t  _ nval d_ nt");
  pr vate stat c f nal SearchCounter UNT L_T ME_ NVAL D_ NT_COUNTER =
      SearchCounter.export("Earlyb rdLuceneQueryV s or_unt l_t  _ nval d_ nt");

  pr vate stat c f nal SearchCounter NUM_QUER ES_BELOW_M N_ENGAGEMENT_THRESHOLD =
      SearchCounter.export(
          "Earlyb rdLuceneQueryV s or_num_quer es_below_m n_engage nt_threshold");
  pr vate stat c f nal SearchCounter NUM_QUER ES_ABOVE_M N_ENGAGEMENT_THRESHOLD =
      SearchCounter.export(
          "Earlyb rdLuceneQueryV s or_num_quer es_above_m n_engage nt_threshold");

  pr vate stat c f nal SearchOperator OPERATOR_CACHED_EXCLUDE_ANT SOC AL_AND_NAT VERETWEETS =
      new SearchOperator(SearchOperator.Type.CACHED_F LTER,
          QueryCac Constants.EXCLUDE_ANT SOC AL_AND_NAT VERETWEETS);

  pr vate stat c f nal Map<Str ng, L st<SearchOperator>> OPERATORS_BY_SAFE_EXCLUDE_OPERAND =
       mmutableMap.of(
          SearchOperatorConstants.TWEET_SPAM,  mmutableL st.of(
              new SearchOperator(SearchOperator.Type.DOCVAL_RANGE_F LTER,
                  "extended_encoded_t et_features.label_spam_flag", "0", "1"),
              new SearchOperator(SearchOperator.Type.DOCVAL_RANGE_F LTER,
                  "extended_encoded_t et_features.label_spam_h _rcl_flag", "0", "1"),
              new SearchOperator(SearchOperator.Type.DOCVAL_RANGE_F LTER,
                  "extended_encoded_t et_features.label_dup_content_flag", "0", "1")),

          SearchOperatorConstants.TWEET_ABUS VE,  mmutableL st.of(
              new SearchOperator(SearchOperator.Type.DOCVAL_RANGE_F LTER,
                  "extended_encoded_t et_features.label_abus ve_flag", "0", "1")),

          SearchOperatorConstants.TWEET_UNSAFE,  mmutableL st.of(
              new SearchOperator(SearchOperator.Type.DOCVAL_RANGE_F LTER,
                  "extended_encoded_t et_features.label_nsfw_h _prc_flag", "0", "1"))
      );

  pr vate stat c f nal  mmutableMap<Str ng, F eld  ghtDefault> DEFAULT_F ELDS =
       mmutableMap.of(Earlyb rdF eldConstant.TEXT_F ELD.getF eldNa (),
                      new F eld  ghtDefault(true, DEFAULT_F ELD_WE GHT));

  // All Earlyb rd f elds that should have geo scrubbed t ets f ltered out w n searc d.
  // See go/realt  -geo-f lter ng
  @V s bleForTest ng
  publ c stat c f nal L st<Str ng> GEO_F ELDS_TO_BE_SCRUBBED = Arrays.asL st(
      Earlyb rdF eldConstant.GEO_HASH_F ELD.getF eldNa (),
      Earlyb rdF eldConstant.PLACE_F ELD.getF eldNa (),
      Earlyb rdF eldConstant.PLACE_ D_F ELD.getF eldNa (),
      Earlyb rdF eldConstant.PLACE_FULL_NAME_F ELD.getF eldNa (),
      Earlyb rdF eldConstant.PLACE_COUNTRY_CODE_F ELD.getF eldNa ());

  // Geo scrubb ng doesn't remove user prof le locat on, so w n us ng t  geo locat on type f lters
  //   only need to f lter out geo scrubbed t ets for t  geo locat on types ot r than
  // Thr ftGeoLocat onS ce.USER_PROF LE.
  // Separately,   also need to f lter out geo scrubbed t ets for t  place_ d f lter.
  pr vate stat c f nal L st<Str ng> GEO_F LTERS_TO_BE_SCRUBBED = Arrays.asL st(
      Earlyb rdF eldConstants.formatGeoType(Thr ftGeoLocat onS ce.GEOTAG),
      Earlyb rdF eldConstants.formatGeoType(Thr ftGeoLocat onS ce.TWEET_TEXT),
      Earlyb rdThr ftDocu ntUt l.formatF lter(
          Earlyb rdF eldConstant.PLACE_ D_F ELD.getF eldNa ()));

  // quer es whose parents are negated.
  // used to dec de  f a negated query  s w h n a negated parent or not.
  pr vate f nal Set<com.tw ter.search.queryparser.query.Query> parentNegatedQuer es =
      Sets.new dent yHashSet();

  pr vate f nal  mmutableSc ma nterface sc maSnapshot;
  pr vate f nal  mmutableMap<Str ng, F eld  ghtDefault> defaultF eld  ghtMap;
  pr vate f nal QueryCac Manager queryCac Manager;
  pr vate f nal UserTable userTable;
  pr vate f nal UserScrubGeoMap userScrubGeoMap;

  @Nullable
  pr vate f nal Term nat onTracker term nat onTracker;
  pr vate f nal Map<MappableF eld, Str ng> mappableF eldMap;
  pr vate f nal Mult Seg ntTermD ct onaryManager mult Seg ntTermD ct onaryManager;
  pr vate f nal Dec der dec der;
  pr vate f nal Earlyb rdCluster earlyb rdCluster;

  pr vate float prox m yPhrase  ght = 1.0f;
  pr vate  nt prox m yPhraseSlop = 255;
  pr vate  mmutableMap<Str ng, Float> enabledF eld  ghtMap;
  pr vate Set<Str ng> quer edF elds;

  //  f   need to accumulate and collect per-f eld and per query node h  attr but on  nformat on,
  // t  w ll have a mapp ng bet en t  query nodes and t  r un que ranks, as  ll as t 
  // attr bute collector.
  @Nullable
  pr vate H Attr bute lper h Attr bute lper;

  @Nullable
  pr vate QueryT  out queryT  out;

  publ c Earlyb rdLuceneQueryV s or(
       mmutableSc ma nterface sc maSnapshot,
      QueryCac Manager queryCac Manager,
      UserTable userTable,
      UserScrubGeoMap userScrubGeoMap,
      Earlyb rdCluster earlyb rdCluster,
      Dec der dec der) {
    t (sc maSnapshot, queryCac Manager, userTable, userScrubGeoMap, null, DEFAULT_F ELDS,
         Collect ons.emptyMap(), null, dec der, earlyb rdCluster, null);
  }

  publ c Earlyb rdLuceneQueryV s or(
       mmutableSc ma nterface sc maSnapshot,
      QueryCac Manager queryCac Manager,
      UserTable userTable,
      UserScrubGeoMap userScrubGeoMap,
      @Nullable Term nat onTracker term nat onTracker,
      Map<Str ng, F eld  ghtDefault> f eld  ghtMap,
      Map<MappableF eld, Str ng> mappableF eldMap,
      Mult Seg ntTermD ct onaryManager mult Seg ntTermD ct onaryManager,
      Dec der dec der,
      Earlyb rdCluster earlyb rdCluster,
      QueryT  out queryT  out) {
    t .sc maSnapshot = sc maSnapshot;
    t .defaultF eld  ghtMap =  mmutableMap.copyOf(f eld  ghtMap);
    t .enabledF eld  ghtMap = F eld  ghtDefault.getOnlyEnabled(defaultF eld  ghtMap);
    t .queryCac Manager = queryCac Manager;
    t .userTable = userTable;
    t .userScrubGeoMap = userScrubGeoMap;
    t .mappableF eldMap = Precond  ons.c ckNotNull(mappableF eldMap);
    t .term nat onTracker = term nat onTracker;
    t .mult Seg ntTermD ct onaryManager = mult Seg ntTermD ct onaryManager;
    t .dec der = dec der;
    t .earlyb rdCluster = earlyb rdCluster;
    t .queryT  out = queryT  out;
    t .quer edF elds = new TreeSet<>();
  }

  publ c  mmutableMap<Str ng, Float> getEnabledF eld  ghtMap() {
    return enabledF eld  ghtMap;
  }

  publ c  mmutableMap<Str ng, F eld  ghtDefault> getDefaultF eld  ghtMap() {
    return defaultF eld  ghtMap;
  }

  publ c Earlyb rdLuceneQueryV s or setProx m yPhrase  ght(float   ght) {
    t .prox m yPhrase  ght =   ght;
    return t ;
  }

  publ c Earlyb rdLuceneQueryV s or setProx m yPhraseSlop( nt slop) {
    t .prox m yPhraseSlop = slop;
    return t ;
  }

  publ c vo d setF eldH Attr bute lper(H Attr bute lper newH Attr bute lper) {
    t .h Attr bute lper = newH Attr bute lper;
  }

  @Overr de
  publ c f nal Query v s (D sjunct on d sjunct on) throws QueryParserExcept on {
    BooleanQuery.Bu lder bqBu lder = new BooleanQuery.Bu lder();
    L st<com.tw ter.search.queryparser.query.Query> ch ldren = d sjunct on.getCh ldren();
    // Do a f nal round of c ck,  f all nodes under a d sjunct on are MUST,
    // treat t m all as DEFAULT (SHOULD  n Lucene).
    boolean allMust = true;
    for (com.tw ter.search.queryparser.query.Query ch ld : ch ldren) {
       f (!ch ld.mustOccur()) {
        allMust = false;
        break;
      }
    }
     f (allMust) {
      ch ldren = L sts.transform(ch ldren, QueryNodeUt ls.MAKE_QUERY_DEFAULT);
    }
    // Actually convert ng all ch ldren now.
    for (com.tw ter.search.queryparser.query.Query ch ld : ch ldren) {
      f nal Query q = ch ld.accept(t );
       f (q != null) {
        //  f a node  s marked w h MUSTHAVE annotat on,   set   to must even  f  's a
        // d sjunct on.
         f (ch ld.mustOccur()) {
          bqBu lder.add(q, Occur.MUST);
        } else {
          bqBu lder.add(q, Occur.SHOULD);
        }
      }
    }

    Query bq = bqBu lder.bu ld();
    float boost = (float) getBoostFromAnnotat ons(d sjunct on.getAnnotat ons());
     f (boost >= 0) {
      bq = BoostUt ls.maybeWrap nBoostQuery(bq, boost);
    }
    return bq;
  }

  @Overr de
  publ c Query v s (Conjunct on conjunct on) throws QueryParserExcept on {
    BooleanQuery.Bu lder bqBu lder = new BooleanQuery.Bu lder();
    L st<com.tw ter.search.queryparser.query.Query> ch ldren = conjunct on.getCh ldren();
    boolean hasPos  veTerms = false;
    for (com.tw ter.search.queryparser.query.Query ch ld : ch ldren) {
      boolean ch ldMustNotOccur = ch ld.mustNotOccur();
      boolean ch ldAdded = addQuery(bqBu lder, ch ld);
       f (ch ldAdded && !ch ldMustNotOccur) {
        hasPos  veTerms = true;
      }
    }
     f (!ch ldren. sEmpty() && !hasPos  veTerms) {
      bqBu lder.add(new MatchAllDocsQuery(), Occur.MUST);
    }

    Query bq = bqBu lder.bu ld();
    float boost = (float) getBoostFromAnnotat ons(conjunct on.getAnnotat ons());
     f (boost >= 0) {
      bq = BoostUt ls.maybeWrap nBoostQuery(bq, boost);
    }
    return bq;
  }

  @Overr de
  publ c Query v s (Phrase phrase) throws QueryParserExcept on {
    return v s (phrase, false);
  }

  @Overr de
  publ c Query v s (Term term) throws QueryParserExcept on {
    return f nal zeQuery(createTermQueryD sjunct on(term), term);
  }

  @Overr de
  publ c Query v s (Spec alTerm spec al) throws QueryParserExcept on {
    Str ng f eld;

    sw ch (spec al.getType()) {
      case HASHTAG:
        f eld = Earlyb rdF eldConstant.HASHTAGS_F ELD.getF eldNa ();
        break;
      case STOCK:
        f eld = Earlyb rdF eldConstant.STOCKS_F ELD.getF eldNa ();
        break;
      case MENT ON:
        f eld = Earlyb rdF eldConstant.MENT ONS_F ELD.getF eldNa ();
        break;
      default:
        f eld = Earlyb rdF eldConstant.TEXT_F ELD.getF eldNa ();
    }

    Str ng termText = spec al.getSpec alChar() + spec al.getValue();
    Query q = createS mpleTermQuery(spec al, f eld, termText);

    float boost = (float) getBoostFromAnnotat ons(spec al.getAnnotat ons());
     f (boost >= 0) {
      q = BoostUt ls.maybeWrap nBoostQuery(q, boost);
    }

    return negateQuery fNodeNegated(spec al, q);
  }

  @Overr de
  publ c Query v s (L nk l nk) throws QueryParserExcept on {
    Query q = createS mpleTermQuery(
        l nk, Earlyb rdF eldConstant.L NKS_F ELD.getF eldNa (), l nk.getOperand());

    float boost = (float) getBoostFromAnnotat ons(l nk.getAnnotat ons());
     f (boost >= 0) {
      q = BoostUt ls.maybeWrap nBoostQuery(q, boost);
    }

    return negateQuery fNodeNegated(l nk, q);
  }

  @Overr de
  publ c Query v s (f nal SearchOperator op) throws QueryParserExcept on {
    f nal Query query;
    SearchOperator.Type type = op.getOperatorType();

    sw ch (type) {
      case TO:
        query = v s ToOperator(op);
        break;

      case FROM:
        query = v s FromOperator(op);
        break;

      case F LTER:
        query = v s F lterOperator(op);
        break;

      case  NCLUDE:
        query = v s  ncludeOperator(op);
        break;

      case EXCLUDE:
        query = v s ExcludeOperator(op);
        break;

      case LANG:
        query = v s LangOperator(op);
        break;

      case SOURCE:
        query = v s S ceOperator(op);
        break;

      case SM LEY:
        query = v s Sm leyOperator(op);
        break;

      case DOCVAL_RANGE_F LTER:
        query = v s DocValRangeF lterOperator(op);
        break;

      case CACHED_F LTER:
        query = v s Cac dF lterOperator(op);
        break;

      case SCORE_F LTER:
        query = v s ScoredF lterOperator(op);
        break;

      case S NCE_T ME:
        query = v s S nceT  Operator(op);
        break;

      case UNT L_T ME:
        query = v s Unt lT  Operator(op);
        break;

      case S NCE_ D:
        query = v s S nce DOperator(op);
        break;

      case MAX_ D:
        query = v s Max DOperator(op);
        break;

      case GEOLOCAT ON_TYPE:
        query = v s GeoLocat onTypeOperator(op);
        break;

      case GEOCODE:
        query = v s GeocodeOperator(op);
        break;

      case GEO_BOUND NG_BOX:
        query = v s GeoBound ngBoxOperator(op);
        break;

      case PLACE:
        query = v s PlaceOperator(op);
        break;

      case L NK:
        // T  should never be called - t  L nk v s or (v s or(L nk l nk)) should be.
        query = v s L nkOperator(op);
        break;

      case ENT TY_ D:
        query = v s Ent y dOperator(op);
        break;

      case FROM_USER_ D:
        query = v s FromUser DOperator(op);
        break;

      case  N_REPLY_TO_TWEET_ D:
        query = v s  nReplyToT et dOperator(op);
        break;

      case  N_REPLY_TO_USER_ D:
        query = v s  nReplyToUser dOperator(op);
        break;

      case L KED_BY_USER_ D:
        query = v s L kedByUser dOperator(op);
        break;

      case RETWEETED_BY_USER_ D:
        query = v s Ret etedByUser dOperator(op);
        break;

      case REPL ED_TO_BY_USER_ D:
        query = v s Repl edToByUser dOperator(op);
        break;

      case QUOTED_USER_ D:
        query = v s QuotedUser dOperator(op);
        break;

      case QUOTED_TWEET_ D:
        query = v s QuotedT et dOperator(op);
        break;

      case D RECTED_AT_USER_ D:
        query = v s D rectedAtUser dOperator(op);
        break;

      case CONVERSAT ON_ D:
        query = v s Conversat on dOperator(op);
        break;

      case COMPOSER_SOURCE:
        query = v s ComposerS ceOperator(op);
        break;

      case RETWEETS_OF_TWEET_ D:
        query = v s Ret etsOfT et dOperator(op);
        break;

      case RETWEETS_OF_USER_ D:
        query = v s Ret etsOfUser dOperator(op);
        break;

      case L NK_CATEGORY:
        query = v s L nkCategoryOperator(op);
        break;

      case CARD_NAME:
        query = v s CardNa Operator(op);
        break;

      case CARD_DOMA N:
        query = v s CardDoma nOperator(op);
        break;

      case CARD_LANG:
        query = v s CardLangOperator(op);
        break;

      case HF_TERM_PA R:
        query = v s HFTermPa rOperator(op);
        break;

      case HF_PHRASE_PA R:
        query = v s HFTermPhrasePa rOperator(op);
        break;

      case PROX M TY_GROUP:
        Phrase phrase = new Phrase(
            L sts.transform(op.getOperands(),
                            s -> Normal zer lper.normal zeW hUnknownLocale(
                                s, Earlyb rdConf g.getPengu nVers on())));

        query = v s (phrase, true);
        break;

      case MULT _TERM_D SJUNCT ON:
        query = v s Mult TermD sjunct on(op);
        break;

      case CSF_D SJUNCT ON_F LTER:
        query = v s CSFD sjunct onF lter(op);
        break;

      case SAFETY_EXCLUDE:
        query = v s SafetyExclude(op);
        break;

      case SPACE_ D:
        query = v s Space d(op);
        break;

      case NAMED_ENT TY:
        query = v s Na dEnt y(op);
        break;

      case NAMED_ENT TY_W TH_TYPE:
        query = v s Na dEnt yW hType(op);
        break;

      case M N_FAVES:
      case M N_QUAL TY_SCORE:
      case M N_REPL ES:
      case M N_RETWEETS:
      case M N_REPUTAT ON:
        query = v s M nFeatureValueOperator(type, op);
        break;

      case FEATURE_VALUE_ N_ACCEPT_L ST_OR_UNSET:
        query = v s FeatureValue nAcceptL stOrUnsetF lterOperator(op);
        break;

      case NEAR:
      case RELATED_TO_TWEET_ D:
      case S NCE:
      case S TE:
      case UNT L:
      case W TH N:
      case W TH N_T ME:
        query = createUnsupportedOperatorQuery(op);
        break;

      case NAMED_CSF_D SJUNCT ON_F LTER:
      case NAMED_MULT _TERM_D SJUNCT ON:
        query = logAndThrowQueryParserExcept on(
            "Na d d sjunct on operator could not be converted to a d sjunct on operator.");
        break;

      default:
        query = logAndThrowQueryParserExcept on("Unknown operator " + op.toStr ng());
    }

    return negateQuery fNodeNegated(op, query);
  }

  protected Query v s ToOperator(SearchOperator op) throws QueryParserExcept on {
    return createNormal zedTermQuery(
        op, Earlyb rdF eldConstant.TO_USER_F ELD.getF eldNa (), op.getOperand());
  }

  protected Query v s FromOperator(SearchOperator op) throws QueryParserExcept on {
    return createNormal zedTermQuery(
        op, Earlyb rdF eldConstant.FROM_USER_F ELD.getF eldNa (), op.getOperand());
  }

  protected Query v s F lterOperator(SearchOperator op) throws QueryParserExcept on {
    return v s F lterOperator(op, false);
  }

  protected Query v s  ncludeOperator(SearchOperator op) throws QueryParserExcept on {
    //  nclude  s a b  funny.   f   have [ nclude ret ets]   are say ng
    // do  nclude ret ets, wh ch  s t  default.  Also conjunct ons re-negate
    // whatever node   em  from t  v s or.
     f (! sParentNegated(op) && !node sNegated(op)) {
      // pos  ve  nclude - no-op.
      return null;
    }
    return v s F lterOperator(op, false);
  }

  protected Query v s ExcludeOperator(SearchOperator op) throws QueryParserExcept on {
    // Exclude  s a b  funny.   f   have -[exclude ret ets]   are say ng
    // dont exclude ret ets, wh ch  s t  default.
     f ( sParentNegated(op) || node sNegated(op)) {
      // Negat ve exclude.  Do noth ng - parent w ll not add t  to t  l st of ch ldren.
      return null;
    } else {
      // Pos  ve exclude.
      return v s F lterOperator(op, true);
    }
  }

  protected Query v s F lterOperator(SearchOperator op, boolean negate)
      throws QueryParserExcept on {
    Query q;
    boolean negateQuery = negate;

     f (op.getOperand().equals(SearchOperatorConstants.ANT SOC AL)) {
      // S nce t  object   use to  mple nt t se f lters  s actually an
      // EXCLUDE f lter,   need to negate   to get   to work as a regular f lter.
      q = UserFlagsExcludeF lter.getUserFlagsExcludeF lter(userTable, true, false, false);
      negateQuery = !negateQuery;
    } else  f (op.getOperand().equals(SearchOperatorConstants.OFFENS VE_USER)) {
      q = UserFlagsExcludeF lter.getUserFlagsExcludeF lter(userTable, false, true, false);
      negateQuery = !negateQuery;
    } else  f (op.getOperand().equals(SearchOperatorConstants.ANT SOC AL_OFFENS VE_USER)) {
      q = UserFlagsExcludeF lter.getUserFlagsExcludeF lter(userTable, true, true, false);
      negateQuery = !negateQuery;
    } else  f (op.getOperand().equals(SearchOperatorConstants.PROTECTED)) {
      q = UserFlagsExcludeF lter.getUserFlagsExcludeF lter(userTable, false, false, true);
      negateQuery = !negateQuery;
    } else  f (op.getOperand().equals(SearchOperatorConstants.HAS_ENGAGEMENT)) {
      return bu ldHasEngage ntsQuery();
    } else  f (op.getOperand().equals(SearchOperatorConstants.SAFE_SEARCH_F LTER)) {
      BooleanQuery.Bu lder bqBu lder = new BooleanQuery.Bu lder();
      bqBu lder.add(
          createNoScoreTermQuery(
              op,
              Earlyb rdF eldConstant. NTERNAL_F ELD.getF eldNa (),
              Earlyb rdF eldConstant. S_OFFENS VE),
          Occur.SHOULD);

      // T  follow ng  nternal f eld __f lter_sens  ve_content
      //  s not currently bu lt by earlyb rd.
      // T   ans t  safe search f lter soley operates on t   s_offens ve b 
      bqBu lder.add(
          createNoScoreTermQuery(
              op,
              Earlyb rdF eldConstant. NTERNAL_F ELD.getF eldNa (),
              Earlyb rdThr ftDocu ntUt l.formatF lter(SearchOperatorConstants.SENS T VE_CONTENT)),
          Occur.SHOULD);
      q = bqBu lder.bu ld();
      negateQuery = !negateQuery;
    } else  f (op.getOperand().equals(SearchOperatorConstants.RETWEETS)) {
      // Spec al case for f lter:ret ets -   use t  text f eld search "-rt"
      // mostly for legacy reasons.
      q = createS mpleTermQuery(
          op,
          Earlyb rdF eldConstant.TEXT_F ELD.getF eldNa (),
          Earlyb rdThr ftDocu ntBu lder.RETWEET_TERM);
    } else  f (sc maSnapshot.getFacetF eldByFacetNa (op.getOperand()) != null) {
      Sc ma.F eld nfo facetF eld = sc maSnapshot.getFacetF eldByFacetNa (op.getOperand());
       f (facetF eld.getF eldType(). sStoreFacetSk pl st()) {
        q = createS mpleTermQuery(
            op,
            Earlyb rdF eldConstant. NTERNAL_F ELD.getF eldNa (),
            Earlyb rdF eldConstant.getFacetSk pF eldNa (facetF eld.getNa ()));
      } else {
        // return empty BQ that doesn't match anyth ng
        q = new BooleanQuery.Bu lder().bu ld();
      }
    } else  f (op.getOperand().equals(SearchOperatorConstants.V NE_L NK)) {
      // Temporary spec al case for f lter:v ne_l nk. T  f lter  s called "v ne_l nk", but  
      // should use t   nternal f eld "__f lter_v ne".   need t  spec al case because ot rw se
      //   would look for t  non-ex st ng "__f lter_v ne_l nk" f eld. See SEARCH-9390
      q = createNoScoreTermQuery(
          op,
          Earlyb rdF eldConstant. NTERNAL_F ELD.getF eldNa (),
          Earlyb rdThr ftDocu ntUt l.formatF lter("v ne"));
    } else {
      // T  default van lla f lters just uses t  f lter format str ng and t 
      // operand text.
      q = createNoScoreTermQuery(
          op,
          Earlyb rdF eldConstant. NTERNAL_F ELD.getF eldNa (),
          Earlyb rdThr ftDocu ntUt l.formatF lter(op.getOperand()));
    }
    // Double c ck: no f lters should have any score contr but on.
    q = new BoostQuery(q, 0.0f);
    return negateQuery ? negateQuery(q) : q;
  }

  pr vate Query bu ldHasEngage ntsQuery() {
     f (earlyb rdCluster == Earlyb rdCluster.PROTECTED) {
      // Engage nts and engage nt counts are not  ndexed on Earlyb rds, so t re  s no need to
      // traverse t  ent re seg nt w h t  M nFeatureValueF lter. See SEARCH-28120
      return new MatchNoDocsQuery();
    }

    Query favF lter = M nFeatureValueF lter.getM nFeatureValueF lter(
        Earlyb rdF eldConstant.FAVOR TE_COUNT.getF eldNa (), 1);
    Query ret etF lter = M nFeatureValueF lter.getM nFeatureValueF lter(
        Earlyb rdF eldConstant.RETWEET_COUNT.getF eldNa (), 1);
    Query replyF lter = M nFeatureValueF lter.getM nFeatureValueF lter(
        Earlyb rdF eldConstant.REPLY_COUNT.getF eldNa (), 1);
    return new BooleanQuery.Bu lder()
        .add(favF lter, Occur.SHOULD)
        .add(ret etF lter, Occur.SHOULD)
        .add(replyF lter, Occur.SHOULD)
        .bu ld();
  }

  protected Query v s LangOperator(SearchOperator op) throws QueryParserExcept on {
    return createNoScoreTermQuery(
        op, Earlyb rdF eldConstant. SO_LANGUAGE_F ELD.getF eldNa (), op.getOperand());
  }

  protected Query v s S ceOperator(SearchOperator op) throws QueryParserExcept on {
    return createNoScoreTermQuery(
        op, Earlyb rdF eldConstant.NORMAL ZED_SOURCE_F ELD.getF eldNa (), op.getOperand());
  }

  protected Query v s Sm leyOperator(SearchOperator op) throws QueryParserExcept on {
    return createS mpleTermQuery(
        op,
        Earlyb rdF eldConstant. NTERNAL_F ELD.getF eldNa (),
        Str ng.format(SM LEY_FORMAT_STR NG, op.getOperand()));
  }

  protected Query v s DocValRangeF lterOperator(SearchOperator op) throws QueryParserExcept on {
    Str ng csfF eldNa  = op.getOperands().get(0).toLo rCase();

    Thr ftCSFType csfF eldType = sc maSnapshot.getCSFF eldType(csfF eldNa );
     f (csfF eldType == null) {
      throw new QueryParserExcept on(" nval d csf f eld na  " + op.getOperands().get(0)
          + " used  n " + op.ser al ze());
    }

    try {
       f (csfF eldType == Thr ftCSFType.DOUBLE
          || csfF eldType == Thr ftCSFType.FLOAT) {
        return DocValRangeF lter.getDocValRangeQuery(csfF eldNa , csfF eldType,
            Double.parseDouble(op.getOperands().get(1)),
            Double.parseDouble(op.getOperands().get(2)));
      } else  f (csfF eldType == Thr ftCSFType.LONG
          || csfF eldType == Thr ftCSFType. NT
          || csfF eldType == Thr ftCSFType.BYTE) {
        Query query = DocValRangeF lter.getDocValRangeQuery(csfF eldNa , csfF eldType,
            Long.parseLong(op.getOperands().get(1)),
            Long.parseLong(op.getOperands().get(2)));
         f (csfF eldNa .equals(Earlyb rdF eldConstant.LAT_LON_CSF_F ELD.getF eldNa ())) {
          return wrapQuery nUserScrubGeoF lter(query);
        }
        return query;
      } else {
        throw new QueryParserExcept on(" nval d Thr ftCSFType. drop t  op: " + op.ser al ze());
      }
    } catch (NumberFormatExcept on e) {
      throw new QueryParserExcept on(" nval d range nu r c type used  n " + op.ser al ze());
    }
  }

  protected f nal Query v s Cac dF lterOperator(SearchOperator op) throws QueryParserExcept on {
    try {
      return Cac dF lterQuery.getCac dF lterQuery(op.getOperand(), queryCac Manager);
    } catch (Cac dF lterQuery.NoSuchF lterExcept on e) {
      throw new QueryParserExcept on(e.get ssage(), e);
    }
  }

  protected f nal Query v s ScoredF lterOperator(SearchOperator op) throws QueryParserExcept on {
    f nal L st<Str ng> operands = op.getOperands();
    f nal Str ng scoreFunct on = operands.get(0);
    Scor ngFunct onProv der.Na dScor ngFunct onProv der scor ngFunct onProv der =
      Scor ngFunct onProv der.getScor ngFunct onProv derByNa (scoreFunct on, sc maSnapshot);
     f (scor ngFunct onProv der == null) {
      throw new QueryParserExcept on("Unknown scor ng funct on na  [" + scoreFunct on
          + " ] used as score_f lter's operand");
    }

    return ScoreF lterQuery.getScoreF lterQuery(
        sc maSnapshot,
        scor ngFunct onProv der,
        Float.parseFloat(operands.get(1)),
        Float.parseFloat(operands.get(2)));
  }

  protected Query v s S nceT  Operator(SearchOperator op) {
    try {
      return S nceUnt lF lter.getS nceQuery( nteger.parse nt(op.getOperand()));
    } catch (NumberFormatExcept on e) {
      LOG.warn("s nce t    s not a val d  nteger, t  date  sn't reasonable. drop t  op: "
          + op.ser al ze());
      S NCE_T ME_ NVAL D_ NT_COUNTER. ncre nt();
      return null;
    }
  }

  protected Query v s Unt lT  Operator(SearchOperator op) {
    try {
      return S nceUnt lF lter.getUnt lQuery( nteger.parse nt(op.getOperand()));
    } catch (NumberFormatExcept on e) {
      LOG.warn("unt l t    s not a val d  nteger, t  date  sn't reasonable. drop t  op: "
          + op.ser al ze());
      UNT L_T ME_ NVAL D_ NT_COUNTER. ncre nt();
      return null;
    }
  }

  protected Query v s S nce DOperator(SearchOperator op) {
    long  d = Long.parseLong(op.getOperand());
    return S nceMax DF lter.getS nce DQuery( d);
  }

  protected Query v s Max DOperator(SearchOperator op) {
    long  d = Long.parseLong(op.getOperand());
    return S nceMax DF lter.getMax DQuery( d);
  }

  protected Query v s GeoLocat onTypeOperator(SearchOperator op) throws QueryParserExcept on {
    Str ng operand = op.getOperand();
    Thr ftGeoLocat onS ce s ce = Thr ftGeoLocat onS ce.valueOf(operand.toUpperCase());
    //  f necessary, t  query w ll be wrapped by t  UserScrubGeoF lter w h n
    // t  createS mpleTermQuery()  lper  thod
    return createNoScoreTermQuery(
        op,
        Earlyb rdF eldConstant. NTERNAL_F ELD.getF eldNa (),
        Earlyb rdF eldConstants.formatGeoType(s ce));
  }

  protected Query v s GeocodeOperator(SearchOperator op) throws QueryParserExcept on {
    return v s GeocodeOrGeocodePr vateOperator(op);
  }

  protected Query v s GeoBound ngBoxOperator(SearchOperator op) throws QueryParserExcept on {
    Rectangle rectangle = bound ngBoxFromSearchOperator(op);
    return wrapQuery nUserScrubGeoF lter(
        GeoQuadTreeQueryBu lder.bu ldGeoQuadTreeQuery(rectangle, term nat onTracker));
  }

  protected Query v s PlaceOperator(SearchOperator op) throws QueryParserExcept on {
    // T  query w ll be wrapped by t  UserScrubGeoF lter w h n t  createS mpleTermQuery()
    //  lper  thod
    return createS mpleTermQuery(
        op, Earlyb rdF eldConstant.PLACE_F ELD.getF eldNa (), op.getOperand());
  }

  protected Query v s L nkOperator(SearchOperator op) throws QueryParserExcept on {
    // T  should never be called - t  L nk v s or (v s or(L nk l nk)) should be.
     f (op  nstanceof L nk) {
      LOG.warn("Unexpected L nk operator " + op.ser al ze());
      return v s ((L nk) op);
    } else {
      throw new QueryParserExcept on("Operator type set to " + op.getOperatorNa ()
          + " but    s not an  nstance of L nk [" + op.toStr ng() + "]");
    }
  }

  protected Query v s Ent y dOperator(SearchOperator op) throws QueryParserExcept on {
    return createS mpleTermQuery(
        op, Earlyb rdF eldConstant.ENT TY_ D_F ELD.getF eldNa (), op.getOperand());
  }

  protected Query v s FromUser DOperator(SearchOperator op) {
    return bu ldLongTermAttr buteQuery(
        op, Earlyb rdF eldConstant.FROM_USER_ D_F ELD.getF eldNa ());
  }

  protected Query v s  nReplyToT et dOperator(SearchOperator op) {
    return bu ldLongTermAttr buteQuery(
        op, Earlyb rdF eldConstant. N_REPLY_TO_TWEET_ D_F ELD.getF eldNa ());
  }

  protected Query v s  nReplyToUser dOperator(SearchOperator op) {
    return bu ldLongTermAttr buteQuery(
        op, Earlyb rdF eldConstant. N_REPLY_TO_USER_ D_F ELD.getF eldNa ());
  }

  protected Query v s L kedByUser dOperator(SearchOperator op) throws QueryParserExcept on {
    return bu ldLongTermAttr buteQuery(op,
        Earlyb rdF eldConstant.L KED_BY_USER_ D_F ELD.getF eldNa ());
  }

  protected Query v s Ret etedByUser dOperator(SearchOperator op) throws QueryParserExcept on {
    return bu ldLongTermAttr buteQuery(op,
        Earlyb rdF eldConstant.RETWEETED_BY_USER_ D.getF eldNa ());
  }

  protected Query v s Repl edToByUser dOperator(SearchOperator op) throws QueryParserExcept on {
    return bu ldLongTermAttr buteQuery(op,
        Earlyb rdF eldConstant.REPL ED_TO_BY_USER_ D.getF eldNa ());
  }

  protected Query v s QuotedUser dOperator(SearchOperator op) throws QueryParserExcept on {
    return bu ldLongTermAttr buteQuery(op,
        Earlyb rdF eldConstant.QUOTED_USER_ D_F ELD.getF eldNa ());
  }

  protected Query v s QuotedT et dOperator(SearchOperator op) throws QueryParserExcept on {
    return bu ldLongTermAttr buteQuery(op,
        Earlyb rdF eldConstant.QUOTED_TWEET_ D_F ELD.getF eldNa ());
  }

  protected Query v s D rectedAtUser dOperator(SearchOperator op) throws QueryParserExcept on {
    return bu ldLongTermAttr buteQuery(op,
        Earlyb rdF eldConstant.D RECTED_AT_USER_ D_F ELD.getF eldNa ());
  }

  protected Query v s Conversat on dOperator(SearchOperator op) throws QueryParserExcept on {
    return bu ldLongTermAttr buteQuery(
        op, Earlyb rdF eldConstant.CONVERSAT ON_ D_F ELD.getF eldNa ());
  }

  protected Query v s ComposerS ceOperator(SearchOperator op) throws QueryParserExcept on {
    Precond  ons.c ckNotNull(op.getOperand(), "composer_s ce requ res operand");
    try {
      ComposerS ce composerS ce = ComposerS ce.valueOf(op.getOperand().toUpperCase());
      return bu ldNoScore ntTermQuery(
          op, Earlyb rdF eldConstant.COMPOSER_SOURCE, composerS ce.getValue());
    } catch ( llegalArgu ntExcept on e) {
      throw new QueryParserExcept on(" nval d operand for composer_s ce: " + op.getOperand(), e);
    }
  }

  protected Query v s Ret etsOfT et dOperator(SearchOperator op) {
    return bu ldLongTermAttr buteQuery(
        op, Earlyb rdF eldConstant.RETWEET_SOURCE_TWEET_ D_F ELD.getF eldNa ());
  }

  protected Query v s Ret etsOfUser dOperator(SearchOperator op) {
    return bu ldLongTermAttr buteQuery(
        op, Earlyb rdF eldConstant.RETWEET_SOURCE_USER_ D_F ELD.getF eldNa ());
  }

  protected Query v s L nkCategoryOperator(SearchOperator op) {
     nt l nkCategory;
    try {
      l nkCategory = L nkCategory.valueOf(op.getOperand()).getValue();
    } catch ( llegalArgu ntExcept on e) {
      l nkCategory =  nteger.parse nt(op.getOperand());
    }

    Str ng f eldNa  = Earlyb rdF eldConstant.L NK_CATEGORY_F ELD.getF eldNa ();
    org.apac .lucene. ndex.Term term = new org.apac .lucene. ndex.Term(
        f eldNa ,  ntTermAttr bute mpl.copy ntoNewBytesRef(l nkCategory));
    return wrapQuery(
        new TermQueryW hSafeToStr ng(term,  nteger.toStr ng(l nkCategory)), op, f eldNa );
  }

  protected Query v s CardNa Operator(SearchOperator op) throws QueryParserExcept on {
    return createNoScoreTermQuery(
        op, Earlyb rdF eldConstant.CARD_NAME_F ELD.getF eldNa (), op.getOperand());
  }

  protected Query v s CardDoma nOperator(SearchOperator op) throws QueryParserExcept on {
    return createNoScoreTermQuery(
        op, Earlyb rdF eldConstant.CARD_DOMA N_F ELD.getF eldNa (), op.getOperand());
  }

  protected Query v s CardLangOperator(SearchOperator op) throws QueryParserExcept on {
    return createNoScoreTermQuery(
        op, Earlyb rdF eldConstant.CARD_LANG.getF eldNa (), op.getOperand());
  }

  protected Query v s HFTermPa rOperator(SearchOperator op) throws QueryParserExcept on {
    f nal L st<Str ng> operands = op.getOperands();
    Str ng termPa r = H ghFrequencyTermPa rs.createPa r(op.getOperands().get(0),
        op.getOperands().get(1));
    Query q = createS mpleTermQuery(op,  mmutableSc ma.HF_TERM_PA RS_F ELD, termPa r);
    float boost = Float.parseFloat(operands.get(2));
     f (boost >= 0) {
      q = BoostUt ls.maybeWrap nBoostQuery(q, boost);
    }
    return q;
  }

  protected Query v s HFTermPhrasePa rOperator(SearchOperator op) throws QueryParserExcept on {
    f nal L st<Str ng> operands = op.getOperands();
    Str ng termPa r = H ghFrequencyTermPa rs.createPhrasePa r(op.getOperands().get(0),
                                                              op.getOperands().get(1));
    Query q = createS mpleTermQuery(op,  mmutableSc ma.HF_PHRASE_PA RS_F ELD, termPa r);
    float boost = Float.parseFloat(operands.get(2));
     f (boost >= 0) {
      q = BoostUt ls.maybeWrap nBoostQuery(q, boost);
    }
    return q;
  }

  pr vate Query logAndThrowQueryParserExcept on(Str ng  ssage) throws QueryParserExcept on {
    LOG.error( ssage);
    throw new QueryParserExcept on( ssage);
  }

  pr vate Query logM ss ngEntr esAndThrowQueryParserExcept on(Str ng f eld, SearchOperator op)
      throws QueryParserExcept on {
    return logAndThrowQueryParserExcept on(
        Str ng.format("M ss ng requ red %s entr es for %s", f eld, op.ser al ze()));
  }

  // prev ous  mple ntat on of t  operator allo d  nsert on of
  // operands from t  thr ft search query.  T  was reverted to ensure s mpl c y
  // of t  ap , and to keep t  ser al zed query self conta ned.
  protected f nal Query v s Mult TermD sjunct on(SearchOperator op) throws QueryParserExcept on {
    f nal L st<Str ng> operands = op.getOperands();
    f nal Str ng f eld = operands.get(0);

     f ( sUser dF eld(f eld)) {
      L st<Long>  ds = L sts.newArrayL st();
      parseLongArgs(operands.subL st(1, operands.s ze()),  ds, op);
       f ( ds.s ze() > 0) {
        // Try to get ranks for  ds  f ex st from h Attr bute lper.
        // Ot rw se just pass  n a empty l st.
        L st< nteger> ranks;
         f (h Attr bute lper != null
            && h Attr bute lper.getExpandedNodeToRankMap().conta nsKey(op)) {
          ranks = h Attr bute lper.getExpandedNodeToRankMap().get(op);
        } else {
          ranks = L sts.newArrayL st();
        }
        return User dMult Seg ntQuery.create dD sjunct onQuery(
            "mult _term_d sjunct on_" + f eld,
             ds,
            f eld,
            sc maSnapshot,
            mult Seg ntTermD ct onaryManager,
            dec der,
            earlyb rdCluster,
            ranks,
            h Attr bute lper,
            queryT  out);
      } else {
        return logM ss ngEntr esAndThrowQueryParserExcept on(f eld, op);
      }
    } else  f (Earlyb rdF eldConstant. D_F ELD.getF eldNa ().equals(f eld)) {
      L st<Long>  ds = L sts.newArrayL st();
      parseLongArgs(operands.subL st(1, operands.s ze()),  ds, op);
       f ( ds.s ze() > 0) {
        return Requ redStatus DsF lter.getRequ redStatus DsQuery( ds);
      } else {
        return logM ss ngEntr esAndThrowQueryParserExcept on(f eld, op);
      }
    } else  f ( sT et dF eld(f eld)) {
      L st<Long>  ds = L sts.newArrayL st();
      parseLongArgs(operands.subL st(1, operands.s ze()),  ds, op);
       f ( ds.s ze() > 0) {
        BooleanQuery.Bu lder bqBu lder = new BooleanQuery.Bu lder();
         nt numClauses = 0;
        for (long  d :  ds) {
           f (numClauses >= BooleanQuery.getMaxClauseCount()) {
            BooleanQuery saved = bqBu lder.bu ld();
            bqBu lder = new BooleanQuery.Bu lder();
            bqBu lder.add(saved, BooleanClause.Occur.SHOULD);
            numClauses = 1;
          }
          bqBu lder.add(bu ldLongTermAttr buteQuery(op, f eld,  d), Occur.SHOULD);
          ++numClauses;
        }
        return bqBu lder.bu ld();
      } else {
        return logM ss ngEntr esAndThrowQueryParserExcept on(f eld, op);
      }
    } else {
      return createUnsupportedOperatorQuery(op);
    }
  }

  protected f nal Query v s CSFD sjunct onF lter(SearchOperator op)
      throws QueryParserExcept on {
    L st<Str ng> operands = op.getOperands();
    Str ng f eld = operands.get(0);

    Thr ftCSFType csfType = sc maSnapshot.getCSFF eldType(f eld);
     f (csfType == null) {
      throw new QueryParserExcept on("F eld must be a CSF");
    }

     f (csfType != Thr ftCSFType.LONG) {
      throw new QueryParserExcept on("csf_d sjunct on_f lter only works w h long f elds");
    }

    Set<Long> values = new HashSet<>();
    parseLongArgs(operands.subL st(1, operands.s ze()), values, op);

    Query query = CSFD sjunct onF lter.getCSFD sjunct onF lter(f eld, values);
     f (f eld.equals(Earlyb rdF eldConstant.LAT_LON_CSF_F ELD.getF eldNa ())) {
      return wrapQuery nUserScrubGeoF lter(query);
    }
    return query;
  }

  protected Query v s SafetyExclude(SearchOperator op) throws QueryParserExcept on {
    //   do not allow negat ng safety_exclude operator. Note t  operator  s  nternal so  f  
    // get  re,    ans t re's a bug  n t  query construct on s de.
     f ( sParentNegated(op) || node sNegated(op)) {
      throw new QueryParserExcept on("Negat ng safety_exclude operator  s not allo d: " + op);
    }

    // Convert t  safety f lter to ot r operators depend ng on cluster sett ng
    // T  safety f lter  s  nterpreted d fferently on arch ve because t  underly ng safety labels
    //  n extended encoded f eld are not ava lable on arch ve.
     f (Earlyb rdCluster. sArch ve(earlyb rdCluster)) {
      return v s (OPERATOR_CACHED_EXCLUDE_ANT SOC AL_AND_NAT VERETWEETS);
    } else {
      L st<com.tw ter.search.queryparser.query.Query> ch ldren = L sts.newArrayL st();
      for (Str ng f lterNa  : op.getOperands()) {
        ch ldren.addAll(
            OPERATORS_BY_SAFE_EXCLUDE_OPERAND.getOrDefault(f lterNa ,  mmutableL st.of()));
      }
      return v s (new Conjunct on(ch ldren));
    }
  }

  protected Query v s Na dEnt y(SearchOperator op) throws QueryParserExcept on {
    L st<Str ng> operands = op.getOperands();
    Precond  ons.c ckState(operands.s ze() == 1,
        "na d_ent y: wrong number of operands");

    return createD sjunct on(
        operands.get(0).toLo rCase(),
        op,
        Earlyb rdF eldConstant.NAMED_ENT TY_FROM_TEXT_F ELD,
        Earlyb rdF eldConstant.NAMED_ENT TY_FROM_URL_F ELD);
  }

  protected Query v s Space d(SearchOperator op) throws QueryParserExcept on {
    L st<Str ng> operands = op.getOperands();
    Precond  ons.c ckState(operands.s ze() == 1,
        "space_ d: wrong number of operands");

    return createS mpleTermQuery(
        op,
        Earlyb rdF eldConstant.SPACE_ D_F ELD.getF eldNa (),
        op.getOperand()
    );
  }

  protected Query v s Na dEnt yW hType(SearchOperator op) throws QueryParserExcept on {
    L st<Str ng> operands = op.getOperands();
    Precond  ons.c ckState(operands.s ze() == 2,
        "na d_ent y_w h_type: wrong number of operands");

    Str ng na  = operands.get(0);
    Str ng type = operands.get(1);
    return createD sjunct on(
        Str ng.format("%s:%s", na , type).toLo rCase(),
        op,
        Earlyb rdF eldConstant.NAMED_ENT TY_W TH_TYPE_FROM_TEXT_F ELD,
        Earlyb rdF eldConstant.NAMED_ENT TY_W TH_TYPE_FROM_URL_F ELD);
  }

  // Create a d sjunct on query for a g ven value  n one of t  g ven f elds
  pr vate Query createD sjunct on(
      Str ng value, SearchOperator operator, Earlyb rdF eldConstant... f elds)
      throws QueryParserExcept on {
    BooleanQuery.Bu lder booleanQueryBu lder = new BooleanQuery.Bu lder();
    for (Earlyb rdF eldConstant f eld : f elds) {
      booleanQueryBu lder.add(
          createS mpleTermQuery(operator, f eld.getF eldNa (), value), Occur.SHOULD);
    }
    return booleanQueryBu lder.bu ld();
  }

  protected Query v s M nFeatureValueOperator(SearchOperator.Type type, SearchOperator op) {
    f nal L st<Str ng> operands = op.getOperands();

    Str ng featureNa ;
    sw ch (type) {
      case M N_FAVES:
        featureNa  = Earlyb rdF eldConstant.FAVOR TE_COUNT.getF eldNa ();
        break;
      case M N_QUAL TY_SCORE:
        featureNa  = Earlyb rdF eldConstant.PARUS_SCORE.getF eldNa ();
        break;
      case M N_REPL ES:
        featureNa  = Earlyb rdF eldConstant.REPLY_COUNT.getF eldNa ();
        break;
      case M N_REPUTAT ON:
        featureNa  = Earlyb rdF eldConstant.USER_REPUTAT ON.getF eldNa ();
        break;
      case M N_RETWEETS:
        featureNa  = Earlyb rdF eldConstant.RETWEET_COUNT.getF eldNa ();
        break;
      default:
        throw new  llegalArgu ntExcept on("Unknown m n feature type " + type);
    }

    double operand = Double.parseDouble(operands.get(0));

    // SEARCH-16751: Because   use QueryCac Constants.HAS_ENGAGEMENT as a dr v ng query below,  
    // won't return t ets w h 0 engage nts w n   handle a query w h a [m n_X 0] f lter (e.g.
    // (* cat [m n_faves 0] ). Thus   need to return a MatchAllDocsQuery  n that case.
     f (operand == 0) {
      return new MatchAllDocsQuery();
    }

    // Only perform t  rewr e  f t  operator  s a m n engage nt operator.
     f ( sOperatorTypeEngage ntF lter(type)) {
      return bu ldQueryForEngage ntOperator(op, operands, featureNa );
    }

     f (type == SearchOperator.Type.M N_REPUTAT ON) {
      return bu ldQueryForM nReputat onOperator(operands, featureNa );
    }

    return M nFeatureValueF lter.getM nFeatureValueF lter(
        featureNa , Double.parseDouble(operands.get(0)));
  }

  protected Query v s FeatureValue nAcceptL stOrUnsetF lterOperator(SearchOperator op)
      throws QueryParserExcept on {
    f nal L st<Str ng> operands = op.getOperands();
    f nal Str ng f eld = operands.get(0);

     f ( s dCSFF eld(f eld)) {
      Set<Long>  ds = Sets.newHashSet();
      parseLongArgs(operands.subL st(1, operands.s ze()),  ds, op);
      return FeatureValue nAcceptL stOrUnsetF lter.getFeatureValue nAcceptL stOrUnsetF lter(
          f eld,  ds);
    } else {
      return logAndThrowQueryParserExcept on(
          " nval d CSF f eld passed to operator " + op.toStr ng());
    }
  }

  /**
   * Creates a Lucene query for an operator that's not supported by t  search serv ce.
   *
   * NOTE: Developer,  f   are wr  ng a class to extends t  class, make sure t 
   * behav   of t  funct on makes sense for y  search serv ce.
   *
   * @param op T  operator that's not supported by t  search serv ce.
   * @return T  Lucene query for t  operator
   */
  protected Query createUnsupportedOperatorQuery(SearchOperator op) throws QueryParserExcept on {
    SearchCounter
        .export(UNSUPPORTED_OPERATOR_PREF X + op.getOperatorType().getOperatorNa ())
        . ncre nt();
    return v s (op.toPhrase());
  }

  pr vate Query bu ldNoScore ntTermQuery(
      SearchOperator op,
      Earlyb rdF eldConstant f eld,
       nt termValue) {
    org.apac .lucene. ndex.Term term = new org.apac .lucene. ndex.Term(
        f eld.getF eldNa (),  ntTermAttr bute mpl.copy ntoNewBytesRef(termValue));
    return wrapQuery(
        new TermQueryW hSafeToStr ng(term,  nteger.toStr ng(termValue)), op, f eld.getF eldNa ());
  }

  pr vate Query bu ldQueryForM nReputat onOperator(L st<Str ng> operands, Str ng featureNa ) {
     nt operand = ( nt) Double.parseDouble(operands.get(0));
    // Dr v ng by M nFeatureValueF lter's Doc dSet erator  s very slow, because   have to
    // perform an expens ve c ck for all doc  Ds  n t  seg nt, so   use a cac d result to
    // dr ve t  query, and use M nFeatureValueF lter as a secondary f lter.
    Str ng queryCac F lterNa ;
     f (operand >= 50) {
      queryCac F lterNa  = QueryCac Constants.M N_REPUTAT ON_50;
    } else  f (operand >= 36) {
      queryCac F lterNa  = QueryCac Constants.M N_REPUTAT ON_36;
    } else  f (operand >= 30) {
      queryCac F lterNa  = QueryCac Constants.M N_REPUTAT ON_30;
    } else {
      return M nFeatureValueF lter.getM nFeatureValueF lter(featureNa , operand);
    }

    try {
      Query dr v ngQuery = Cac dF lterQuery.getCac dF lterQuery(
          queryCac F lterNa , queryCac Manager);
      return new F lteredQuery(
          dr v ngQuery, M nFeatureValueF lter.getDoc dF lterFactory(featureNa , operand));
    } catch (Except on e) {
      //  f t  f lter  s not found, that's OK,   m ght be   f rst t   runn ng t  query cac ,
      // or t re may be no t ets w h that h gh reputat on.
      return M nFeatureValueF lter.getM nFeatureValueF lter(featureNa , operand);
    }
  }

  pr vate Query bu ldQueryForEngage ntOperator(
      SearchOperator op, L st<Str ng> operands, Str ng featureNa ) {
    // Engage nts and engage nt counts are not  ndexed on Protected Earlyb rds, so t re  s no
    // need to traverse t  ent re seg nt w h t  M nFeatureValueF lter. SEARCH-28120
     f (earlyb rdCluster == Earlyb rdCluster.PROTECTED) {
      return new MatchNoDocsQuery();
    }

    Earlyb rdF eldConstant f eld =
        Earlyb rdF eldConstants.CSF_NAME_TO_M N_ENGAGEMENT_F ELD_MAP.get(featureNa );
     f (f eld == null) {
      throw new  llegalArgu ntExcept on(Str ng.format("Expected t  feature to be "
          + "FAVOR TE_COUNT, REPLY_COUNT, or RETWEET_COUNT. Got %s.", featureNa ));
    }
     nt operand = ( nt) Double.parseDouble(operands.get(0));
    ByteNormal zer normal zer = M nFeatureValueF lter.getM nFeatureValueNormal zer(featureNa );
     nt m nValue = normal zer.uns gnedByteTo nt(normal zer.normal ze(operand));

    //   default to t  old behav or of f lter ng posts  nstead of consult ng t  m n engage nt
    // f eld  f t  operand  s less than so  threshold value because   seems, emp r cally, that
    // t  old  thod results  n lo r query latenc es for lo r values of t  f lter operand.
    // T  threshold can be controlled by t  "use_m n_engage nt_f eld_threshold" dec der. T 
    // current default value  s 90. SEARCH-16102
     nt useM nEngage ntF eldThreshold = dec der.getAva lab l y(
        "use_m n_engage nt_f eld_threshold").getOrElse(() -> 0);
     f (operand >= useM nEngage ntF eldThreshold) {
      NUM_QUER ES_ABOVE_M N_ENGAGEMENT_THRESHOLD. ncre nt();
    } else {
      NUM_QUER ES_BELOW_M N_ENGAGEMENT_THRESHOLD. ncre nt();
    }
     f (sc maHasF eld(f eld) && operand >= useM nEngage ntF eldThreshold) {
      return bu ldNoScore ntTermQuery(op, f eld, m nValue);
    }
    // Dr v ng by M nFeatureValueF lter's Doc dSet erator  s very slow, because   have to
    // perform an expens ve c ck for all doc  Ds  n t  seg nt, so   use a cac d result to
    // dr ve t  query, and use M nFeatureValueF lter as a secondary f lter.
    try {
      Query dr v ngQuery = m nEngag ntsDr v ngQuery(op, operand);
      return new F lteredQuery(
          dr v ngQuery, M nFeatureValueF lter.getDoc dF lterFactory(featureNa , operand));
    } catch (Except on e) {
      //  f t  f lter  s not found, that's OK,   m ght be   f rst t   runn ng t  query cac ,
      // or t re may be no T ets w h that many engage nts (  would only expect t   n tests).
      return M nFeatureValueF lter.getM nFeatureValueF lter(featureNa , operand);
    }
  }

  pr vate Query m nEngag ntsDr v ngQuery(SearchOperator operator,  nt m nValue)
          throws Cac dF lterQuery.NoSuchF lterExcept on, QueryParserExcept on {
    //  f t  m n engage nts value  s large, t n many of t  h s that have engage nt w ll st ll
    // not match t  query, lead ng to extre ly slow quer es. T refore,  f t re  s more than 100
    // engage nts,   dr ve by a more restr cted f lter. See SEARCH-33740
    Str ng f lter;
     f (m nValue < 100) {
      f lter = QueryCac Constants.HAS_ENGAGEMENT;
    } else  f (operator.getOperatorType() == SearchOperator.Type.M N_FAVES) {
      f lter = QueryCac Constants.M N_FAVES_100;
    } else  f (operator.getOperatorType() == SearchOperator.Type.M N_REPL ES) {
      f lter = QueryCac Constants.M N_REPL ES_100;
    } else  f (operator.getOperatorType() == SearchOperator.Type.M N_RETWEETS) {
      f lter = QueryCac Constants.M N_RETWEETS_100;
    } else {
      throw new QueryParserExcept on("M ss ng engage nt f lter.");
    }
    return Cac dF lterQuery.getCac dF lterQuery(f lter, queryCac Manager);
  }

  pr vate boolean  sOperatorTypeEngage ntF lter(SearchOperator.Type type) {
    return type == SearchOperator.Type.M N_FAVES
        || type == SearchOperator.Type.M N_RETWEETS
        || type == SearchOperator.Type.M N_REPL ES;
  }

  pr vate boolean sc maHasF eld(Earlyb rdF eldConstant f eld) {
    return sc maSnapshot.hasF eld(f eld.getF eld d());
  }

  //  lper funct ons
  pr vate Query createS mpleTermQuery(
      com.tw ter.search.queryparser.query.Query node, Str ng f eld, Str ng text)
      throws QueryParserExcept on {
    Query baseQuery = new TermQuery(createTerm(f eld, text));
     f ( sGeoF eldThatShouldBeScrubbed(f eld, text)) {
      baseQuery = wrapQuery nUserScrubGeoF lter(baseQuery);
    }
    return wrapQuery(baseQuery, node, f eld);
  }

  pr vate boolean  sGeoF eldThatShouldBeScrubbed(Str ng f eld, Str ng text) {
     f (f eld.equals(Earlyb rdF eldConstant. NTERNAL_F ELD.getF eldNa ())) {
      // t   nternal f eld  s used for t  place  d f lter and t  geo locat on type f lters, so 
      // of wh ch should be scrubbed
      return GEO_F LTERS_TO_BE_SCRUBBED.conta ns(text);
    }
    return GEO_F ELDS_TO_BE_SCRUBBED.conta ns(f eld);
  }

  // L ke above, but sets boost to 0 to d sable scor ng component.  T  should be used
  // for f lters that do not  mpact scor ng (such as f lter: mages).
  pr vate Query createNoScoreTermQuery(com.tw ter.search.queryparser.query.Query node,
                                             Str ng f eld, Str ng text)
      throws QueryParserExcept on {
    Query query = createS mpleTermQuery(node, f eld, text);
    return new BoostQuery(query, 0.0f);  // No score contr but on.
  }

  pr vate Query createNormal zedTermQuery(com.tw ter.search.queryparser.query.Query node,
                                                Str ng f eld, Str ng text)
      throws QueryParserExcept on {
    return createS mpleTermQuery(
        node,
        f eld,
        Normal zer lper.normal zeW hUnknownLocale(text, Earlyb rdConf g.getPengu nVers on()));
  }

  /**
   * Get t  boost from t  annotat on l st of a query node.
   * R ght now t   s very s mple,   s mple extract t  value of so  annotat ons and  gnore all
   * ot rs, also,  f t re are mult ple annotat ons that have values,   only use t  f rst one  
   * see  n t  l st (although t  rewr ten query EB rece ves should have t ).
   * NOTE:   use s mple   ght select on log c  re based on t  assumpt on that t  annotator
   * and rewr er w ll not produce amb guous   ght  nformat on. T re should always be only one
   *   ght-bear ng annotat on for a spec f c node.
   *
   * @param annotat ons T  l st of annotat ons of t  query node.
   * @return T  boost for t  query node, 0  f t re  s no boost,  n wh ch case   shouldn't
   *         apply   at all.
   */
  pr vate stat c double getBoostFromAnnotat ons(L st<Annotat on> annotat ons) {
     f (annotat ons != null) {
      for (Annotat on anno : annotat ons) {
        sw ch (anno.getType()) {
          case VAR ANT:
          case SPELL NG:
          case WE GHT:
          case OPT ONAL:
            return ((FloatAnnotat on) anno).getValue();
          default:
        }
      }
    }
    return -1;
  }

  pr vate stat c double getPhraseProx m yFromAnnotat ons(L st<Annotat on> annotat ons) {
     f (annotat ons != null) {
      for (Annotat on anno : annotat ons) {
         f (anno.getType() == Annotat on.Type.PROX M TY) {
          return ((FloatAnnotat on) anno).getValue();
        }
      }
    }
    return -1;
  }

  pr vate stat c boolean  sOpt onal(com.tw ter.search.queryparser.query.Query node) {
    return node.hasAnnotat onType(Annotat on.Type.OPT ONAL);
  }

  pr vate stat c boolean  sProx m yGroup(com.tw ter.search.queryparser.query.Query node) {
     f (node. sTypeOf(com.tw ter.search.queryparser.query.Query.QueryType.OPERATOR)) {
      SearchOperator op = (SearchOperator) node;
       f (op.getOperatorType() == SearchOperator.Type.PROX M TY_GROUP) {
        return true;
      }
    }
    return false;
  }

  pr vate f nal Query s mpl fyBooleanQuery(BooleanQuery q) {
     f (q.clauses() == null || q.clauses().s ze() != 1) {
      return q;
    }

    return q.clauses().get(0).getQuery();
  }

  pr vate Query v s (f nal Phrase phrase, boolean sloppy) throws QueryParserExcept on {
    Opt onal<Annotat on> f eldOpt = phrase.getAnnotat onOf(Annotat on.Type.F ELD);
     f (f eldOpt. sPresent()) {
      Str ng f eld = f eldOpt.get().valueToStr ng();
      Sc ma.F eld nfo f eld nfo = sc maSnapshot.getF eld nfo(f eld);
       f (f eld nfo != null && !f eld nfo.getF eldType().hasPos  ons()) {
        throw new QueryParserExcept on(Str ng.format("F eld %s does not support phrase quer es "
            + "because   does not have pos  on  nformat on.", f eld));
      }
    }
    BooleanQuery.Bu lder queryBu lder = new BooleanQuery.Bu lder();
    Map<Str ng, Float> actualF eld  ghts = getF eld  ghtMapForNode(phrase);
    for (Map.Entry<Str ng, Float> entry : actualF eld  ghts.entrySet()) {
      PhraseQuery.Bu lder phraseQueryBu lder = new PhraseQuery.Bu lder();
       nt curPos = 0;
      for (Str ng term : phrase.getTerms()) {
         f (!term.equals(PHRASE_W LDCARD)) {
          phraseQueryBu lder.add(createTerm(entry.getKey(), term), curPos);
          curPos++;
        } else  f (curPos != 0) { //"*" at t  begg n ng of a phrase has no effect/ an ng
          curPos++;
        }
      }

      // No actual terms added to query
       f (curPos == 0) {
        break;
      }
       nt annotatedSlopp ness = ( nt) getPhraseProx m yFromAnnotat ons(phrase.getAnnotat ons());
       f (annotatedSlopp ness > 0) {
        phraseQueryBu lder.setSlop(annotatedSlopp ness);
      } else  f (sloppy) {
        phraseQueryBu lder.setSlop(prox m yPhraseSlop);
      }
      float f eld  ght = entry.getValue();
      float boost = (float) getBoostFromAnnotat ons(phrase.getAnnotat ons());
      Query query = phraseQueryBu lder.bu ld();
       f (boost >= 0) {
        query = BoostUt ls.maybeWrap nBoostQuery(query, boost * f eld  ght);
      } else  f (f eld  ght != DEFAULT_F ELD_WE GHT) {
        query = BoostUt ls.maybeWrap nBoostQuery(query, f eld  ght);
      } else {
        query = BoostUt ls.maybeWrap nBoostQuery(query, prox m yPhrase  ght);
      }
      Occur occur = actualF eld  ghts.s ze() > 1 ? Occur.SHOULD : Occur.MUST;
      queryBu lder.add(wrapQuery(query, phrase, entry.getKey()), occur);
    }
    Query q = s mpl fyBooleanQuery(queryBu lder.bu ld());
    return negateQuery fNodeNegated(phrase, q);
  }

  pr vate Query wrapQuery(
      org.apac .lucene.search.Query query,
      com.tw ter.search.queryparser.query.Query node,
      Str ng f eldNa ) {
    return Earlyb rdQuery lper.maybeWrapW hT  out(
        Earlyb rdQuery lper.maybeWrapW hH Attr but onCollector(
            query, node, sc maSnapshot.getF eld nfo(f eldNa ), h Attr bute lper),
        node, queryT  out);
  }

  pr vate f nal boolean node sNegated(com.tw ter.search.queryparser.query.Query node) {
     f ( sParentNegated(node)) {
      return !node.mustNotOccur();
    } else {
      return node.mustNotOccur();
    }
  }

  pr vate f nal Query negateQuery(Query q) {
    return new BooleanQuery.Bu lder()
        .add(q, Occur.MUST_NOT)
        .add(new MatchAllDocsQuery(), Occur.MUST)
        .bu ld();
  }

  // S mple  lper to exam ne node, and negate t  lucene query  f necessary.
  pr vate f nal Query negateQuery fNodeNegated(com.tw ter.search.queryparser.query.Query node,
                                                 Query query) {
     f (query == null) {
      return null;
    }
    return node sNegated(node) ? negateQuery(query) : query;
  }

  pr vate boolean  sParentNegated(com.tw ter.search.queryparser.query.Query query) {
    return parentNegatedQuer es.conta ns(query);
  }

  pr vate org.apac .lucene. ndex.Term createTerm(Str ng f eld, Str ng text)
      throws QueryParserExcept on {
    Sc ma.F eld nfo f eld nfo = sc maSnapshot.getF eld nfo(f eld);
     f (f eld nfo == null) {
      throw new QueryParserExcept on("Unknown f eld: " + f eld);
    }

    quer edF elds.add(f eld);

    try {
      return new org.apac .lucene. ndex.Term(f eld, Sc maUt l.toBytesRef(f eld nfo, text));
    } catch (UnsupportedOperat onExcept on e) {
      throw new QueryParserExcept on(e.get ssage(), e.getCause());
    }
  }

  /**
   * Get f eld   ght map for a node, comb ng default values and  s annotat ons.
   */
  pr vate Map<Str ng, Float> getF eld  ghtMapForNode(
      com.tw ter.search.queryparser.query.Query query) throws QueryParserExcept on {
    return F eld  ghtUt l.comb neDefaultW hAnnotat on(
        query,
        defaultF eld  ghtMap,
        enabledF eld  ghtMap,
        Funct ons.<Str ng> dent y(),
        mappableF eldMap,
        Funct ons.<Str ng> dent y());
  }

  pr vate boolean addQuery(
      BooleanQuery.Bu lder bqBu lder,
      com.tw ter.search.queryparser.query.Query ch ld) throws QueryParserExcept on {
    Occur occur = Occur.MUST;
     f (ch ld.mustNotOccur()) {
      // To bu ld a conjunct on,   w ll not rely on t  negat on  n t  ch ld v s or.
      //  nstead   w ll add t  term as MUST_NOT occur.
      // Store t   n parentNegatedQuer es so t  ch ld v s or can do t  r ght th ng.
      occur = Occur.MUST_NOT;
      parentNegatedQuer es.add(ch ld);
    } else  f ( sOpt onal(ch ld) ||  sProx m yGroup(ch ld)) {
      occur = Occur.SHOULD;
    }

    Query q = ch ld.accept(t );
     f (q != null) {
      bqBu lder.add(q, occur);
      return true;
    }
    return false;
  }

  /**
   * Constructs a BooleanQuery from a queryparser Query node.
   * Adds f elds as conf gured  n t  f eld  ghtMap and spec f ed by termQueryD sjunct onType
   *  - TermQueryD sjunct onType.ONLY_OPT ONAL ZED adds opt onal f elds
   *  (only resolved_l nks_text for now),
   *  - TermQueryD sjunct onType.DROP_OPT ONAL ZED adds all ot r val d f elds expect
   *  resolved_l nks_text (for now),
   *  - TermQueryD sjunct onType.NORMAL adds all val d f elds
   * @param query an  nstance of com.tw ter.search.queryparser.query.Query or
   * com.tw ter.search.queryparser.query.Term
   * @return a BooleanQuery cons sts of f elds from query
   */
  pr vate BooleanQuery createTermQueryD sjunct on(
      com.tw ter.search.queryparser.query.Query query) throws QueryParserExcept on {
    Str ng normTerm = query. sTypeOf(com.tw ter.search.queryparser.query.Query.QueryType.TERM)
        ? ((Term) query).getValue() : query.toStr ng(false);
    BooleanQuery.Bu lder booleanQueryBu lder = new BooleanQuery.Bu lder();
    Map<Str ng, Float> actualF eld  ghtMap = getF eld  ghtMapForNode(query);
    Set<Str ng> f eldsToUse = Sets.newL nkedHashSet(actualF eld  ghtMap.keySet());
    Occur occur = f eldsToUse.s ze() > 1 ? Occur.SHOULD : Occur.MUST;
    for (Str ng f eld : f eldsToUse) {
      addTermQueryW hF eld(booleanQueryBu lder, query, normTerm, f eld, occur,
          actualF eld  ghtMap.get(f eld));
    }
    return booleanQueryBu lder.bu ld();
  }

  pr vate vo d addTermQueryW hF eld(
      BooleanQuery.Bu lder bqBu lder,
      com.tw ter.search.queryparser.query.Query term,
      Str ng normTerm,
      Str ng f eldNa ,
      Occur occur,
      float f eld  ght) throws QueryParserExcept on {
    float boost = (float) getBoostFromAnnotat ons(term.getAnnotat ons());
    Query query = createS mpleTermQuery(term, f eldNa , normTerm);
     f (boost >= 0) {
      query = BoostUt ls.maybeWrap nBoostQuery(query, boost * f eld  ght);
    } else {
      query = BoostUt ls.maybeWrap nBoostQuery(query, f eld  ght);
    }
    bqBu lder.add(query, occur);
  }

  pr vate Query f nal zeQuery(BooleanQuery bq, Term term) {
    Query q = s mpl fyBooleanQuery(bq);
    return negateQuery fNodeNegated(term, q);
  }

  pr vate Rectangle bound ngBoxFromSearchOperator(SearchOperator op) throws QueryParserExcept on {
    Precond  ons.c ckArgu nt(op.getOperatorType() == SearchOperator.Type.GEO_BOUND NG_BOX);
    Precond  ons.c ckNotNull(op.getOperands());
    Precond  ons.c ckState(op.getOperands().s ze() == 4);

    L st<Str ng> operands = op.getOperands();
    try {
      // Unfortunately,   store coord nates as floats  n    ndex, wh ch causes a lot of prec s on
      // loss. On t  query s de,   have to cast  nto floats to match.
      float m nLat = (float) Double.parseDouble(operands.get(0));
      float m nLon = (float) Double.parseDouble(operands.get(1));
      float maxLat = (float) Double.parseDouble(operands.get(2));
      float maxLon = (float) Double.parseDouble(operands.get(3));

      Po nt lo rLeft = new Po nt mpl(m nLon, m nLat, GeohashChunk mpl.getSpat alContext());
      Po nt upperR ght = new Po nt mpl(maxLon, maxLat, GeohashChunk mpl.getSpat alContext());
      return new Rectangle mpl(lo rLeft, upperR ght, GeohashChunk mpl.getSpat alContext());
    } catch (NumberFormatExcept on e) {
      // cons der operator  nval d  f any of t  coord nate cannot be parsed.
      throw new QueryParserExcept on("Malfor d bound ng box operator." + op.ser al ze());
    }
  }

  pr vate Query v s GeocodeOrGeocodePr vateOperator(SearchOperator op)
      throws QueryParserExcept on {

    GeoCode geoCode = GeoCode.fromOperator(op);
     f (geoCode == null) {
      throw new QueryParserExcept on(" nval d GeoCode operator:" + op.ser al ze());
    }

    return wrapQuery nUserScrubGeoF lter(
        GeoQuadTreeQueryBu lder.bu ldGeoQuadTreeQuery(geoCode, term nat onTracker));
  }

  pr vate Query wrapQuery nUserScrubGeoF lter(Query baseQuery) {
     f (Dec derUt l. sAva lableForRandomRec p ent(
        dec der, "f lter_out_geo_scrubbed_t ets_" + earlyb rdCluster.getNa ForStats())) {
      return new F lteredQuery(
          baseQuery,
          UserScrubGeoF lter.getDoc dF lterFactory(userScrubGeoMap));
    } else {
      return baseQuery;
    }
  }

  pr vate Query bu ldLongTermAttr buteQuery(SearchOperator op, Str ng f eldNa ) {
    return bu ldLongTermAttr buteQuery(op, f eldNa , Long.parseLong(op.getOperand()));
  }

  pr vate Query bu ldLongTermAttr buteQuery(SearchOperator op, Str ng f eldNa , long argValue) {
    org.apac .lucene. ndex.Term term = new org.apac .lucene. ndex.Term(
        f eldNa , LongTermAttr bute mpl.copy ntoNewBytesRef(argValue));
    return wrapQuery(new TermQueryW hSafeToStr ng(term, Long.toStr ng(argValue)), op, f eldNa );
  }

  pr vate stat c vo d parseLongArgs(L st<Str ng> operands,
                                    Collect on<Long> argu nts,
                                    SearchOperator op) throws QueryParserExcept on {
    for (Str ng operand : operands) {
      try {
        argu nts.add(Long.parseLong(operand));
      } catch (NumberFormatExcept on e) {
        throw new QueryParserExcept on(" nval d long operand  n " + op.ser al ze(), e);
      }
    }
  }

  pr vate stat c boolean  sUser dF eld(Str ng f eld) {
    return Earlyb rdF eldConstant.FROM_USER_ D_F ELD.getF eldNa ().equals(f eld)
        || Earlyb rdF eldConstant. N_REPLY_TO_USER_ D_F ELD.getF eldNa ().equals(f eld)
        || Earlyb rdF eldConstant.RETWEET_SOURCE_USER_ D_F ELD.getF eldNa ().equals(f eld)
        || Earlyb rdF eldConstant.L KED_BY_USER_ D_F ELD.getF eldNa ().equals(f eld)
        || Earlyb rdF eldConstant.RETWEETED_BY_USER_ D.getF eldNa ().equals(f eld)
        || Earlyb rdF eldConstant.REPL ED_TO_BY_USER_ D.getF eldNa ().equals(f eld)
        || Earlyb rdF eldConstant.QUOTED_USER_ D_F ELD.getF eldNa ().equals(f eld)
        || Earlyb rdF eldConstant.D RECTED_AT_USER_ D_F ELD.getF eldNa ().equals(f eld);
  }

  pr vate stat c boolean  sT et dF eld(Str ng f eld) {
    return Earlyb rdF eldConstant. N_REPLY_TO_TWEET_ D_F ELD.getF eldNa ().equals(f eld)
        || Earlyb rdF eldConstant.RETWEET_SOURCE_TWEET_ D_F ELD.getF eldNa ().equals(f eld)
        || Earlyb rdF eldConstant.QUOTED_TWEET_ D_F ELD.getF eldNa ().equals(f eld)
        || Earlyb rdF eldConstant.CONVERSAT ON_ D_F ELD.getF eldNa ().equals(f eld);
  }

  pr vate stat c boolean  s dCSFF eld(Str ng f eld) {
    return Earlyb rdF eldConstant.D RECTED_AT_USER_ D_CSF.getF eldNa ().equals(f eld);
  }

  publ c Set<Str ng> getQuer edF elds() {
    return quer edF elds;
  }
}
