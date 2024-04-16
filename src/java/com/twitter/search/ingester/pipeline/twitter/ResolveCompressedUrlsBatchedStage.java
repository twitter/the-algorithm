package com.tw ter.search. ngester.p pel ne.tw ter;

 mport java.net.UR ;
 mport java.net.UR SyntaxExcept on;
 mport java.ut l.Collect on;
 mport java.ut l.Collect ons;
 mport java.ut l.HashSet;
 mport java.ut l.Locale;
 mport java.ut l.Map;
 mport java.ut l.Set;
 mport javax.nam ng.Nam ngExcept on;

 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect.Maps;
 mport com.google.common.collect.Sets;

 mport org.apac .commons.lang.Str ngUt ls;
 mport org.apac .commons.p pel ne.StageExcept on;
 mport org.apac .commons.p pel ne.val dat on.Consu dTypes;
 mport org.apac .commons.p pel ne.val dat on.ProducesConsu d;

 mport com.tw ter.common.text.language.LocaleUt l;
 mport com.tw ter.search.common.dec der.SearchDec der;
 mport com.tw ter.search.common. ndex ng.thr ftjava.Thr ftExpandedUrl;
 mport com.tw ter.search.common. tr cs.Percent le;
 mport com.tw ter.search.common. tr cs.Percent leUt l;
 mport com.tw ter.search.common. tr cs.RelevanceStats;
 mport com.tw ter.search.common. tr cs.SearchRateCounter;
 mport com.tw ter.search. ngester.model. ngesterTw ter ssage;
 mport com.tw ter.search. ngester.p pel ne.ut l.Batc dEle nt;
 mport com.tw ter.search. ngester.p pel ne.ut l.P pel neStageExcept on;
 mport com.tw ter.search. ngester.p pel ne.w re.W reModule;
 mport com.tw ter.serv ce.sp derduck.gen. d aTypes;
 mport com.tw ter.ut l.Durat on;
 mport com.tw ter.ut l.Funct on;
 mport com.tw ter.ut l.Future;

@Consu dTypes( ngesterTw ter ssage.class)
@ProducesConsu d
publ c class ResolveCompressedUrlsBatc dStage extends Tw terBatc dBaseStage
    < ngesterTw ter ssage,  ngesterTw ter ssage> {

  pr vate stat c f nal  nt P NK_REQUEST_T MEOUT_M LL S = 500;
  pr vate stat c f nal  nt P NK_REQUEST_RETR ES = 2;
  pr vate stat c f nal Str ng P NK_REQUESTS_BATCH_S ZE_DEC DER_KEY = "p nk_requests_batch_s ze";
  pr vate AsyncP nkUrlsResolver urlResolver;
  pr vate  nt resolveUrlPercentage = 100;
  pr vate Str ng p nkCl ent d;
  pr vate SearchDec der searchDec der;

  // T  number of URLs that   attempted to resolve.
  pr vate SearchRateCounter l nksAttempted;
  // T  number of URLs that  re successfully resolved.
  pr vate SearchRateCounter l nksSucceeded;
  // T  number of URLs  gnored because t y are too long.
  pr vate SearchRateCounter l nksTooLong;
  // T  number of URLs truncated because t y are too long.
  pr vate SearchRateCounter l nksTruncated;

  // T  number of resolved URLs w hout a  d a type.
  pr vate SearchRateCounter urlsW hout d aType;
  // T  number of resolved URLs w h a spec f c  d a type.
  pr vate f nal Map< d aTypes, SearchRateCounter> urlsW h d aTypeMap =
      Maps.newEnumMap( d aTypes.class);

  // T  number of t ets for wh ch all URLs  re resolved.
  pr vate SearchRateCounter t etsW hResolvedURLs;
  // T  number of t ets for wh ch so  URLs  re not resolved.
  pr vate SearchRateCounter t etsW hUnresolvedURLs;

  // How long   takes to fully resolve all URLs  n a t et.
  pr vate Percent le<Long> m ll sToResolveAllT etURLs;

  // max age that a t et can be before passed down t  p pel ne
  pr vate long t etMaxAgeToResolve;

  // number of t  s an ele nt  s w h n quota.
  pr vate SearchRateCounter numberOfEle ntsW h nQuota;

  // number of t  s ele nt  s not w h n quota.  f ele nt not w h n quota,   dont batch.
  pr vate SearchRateCounter numberOfEle ntsNotW h nQuota;

  // number of t  s ele nt has urls.
  pr vate SearchRateCounter numberOfEle ntsW hUrls;

  // number of t  s ele nt does not have urls.  f ele nt does not have URL,   dont batch.
  pr vate SearchRateCounter numberOfEle ntsW houtUrls;

  // number of calls to needsToBeBatc d  thod.
  pr vate SearchRateCounter numberOfCallsToNeedsToBeBatc d;


  publ c vo d setT etMaxAgeToResolve(long t etMaxAgeToResolve) {
    t .t etMaxAgeToResolve = t etMaxAgeToResolve;
  }

  @Overr de
  protected Class< ngesterTw ter ssage> getQueueObjectType() {
    return  ngesterTw ter ssage.class;
  }

  @Overr de
  protected boolean needsToBeBatc d( ngesterTw ter ssage ele nt) {
    numberOfCallsToNeedsToBeBatc d. ncre nt();
    boolean  sW h nQuota = (ele nt.get d() % 100) < resolveUrlPercentage;

     f ( sW h nQuota) {
      t .numberOfEle ntsW h nQuota. ncre nt();
    } else {
      t .numberOfEle ntsNotW h nQuota. ncre nt();
    }

    boolean hasUrls = !ele nt.getExpandedUrlMap(). sEmpty();

     f (hasUrls) {
      t .numberOfEle ntsW hUrls. ncre nt();
    } else {
      t .numberOfEle ntsW houtUrls. ncre nt();
    }

    return hasUrls &&  sW h nQuota;
  }

  //  dent y transformat on. T and U types are t  sa 
  @Overr de
  protected  ngesterTw ter ssage transform( ngesterTw ter ssage ele nt) {
    return ele nt;
  }

  @Overr de
  publ c vo d  n Stats() {
    super. n Stats();
    common nnerSetupStats();
  }

  @Overr de
  protected vo d  nnerSetupStats() {
    super. nnerSetupStats();
    common nnerSetupStats();
  }

  pr vate vo d common nnerSetupStats() {
    l nksAttempted = RelevanceStats.exportRate(getStageNa Pref x() + "_num_l nks_attempted");
    l nksSucceeded = RelevanceStats.exportRate(getStageNa Pref x() + "_num_l nks_succeeded");
    l nksTooLong = RelevanceStats.exportRate(getStageNa Pref x() + "_num_l nks_toolong");
    l nksTruncated = RelevanceStats.exportRate(getStageNa Pref x() + "_num_l nks_truncated");

    urlsW hout d aType = RelevanceStats.exportRate(
        getStageNa Pref x() + "_urls_w hout_ d a_type");

    for ( d aTypes  d aType :  d aTypes.values()) {
      urlsW h d aTypeMap.put(
           d aType,
          RelevanceStats.exportRate(
              getStageNa Pref x() + "_urls_w h_ d a_type_" +  d aType.na ().toLo rCase()));
    }

    t etsW hResolvedURLs = RelevanceStats.exportRate(
        getStageNa Pref x() + "_num_t ets_w h_resolved_urls");
    t etsW hUnresolvedURLs = RelevanceStats.exportRate(
        getStageNa Pref x() + "_num_t ets_w h_unresolved_urls");

    m ll sToResolveAllT etURLs = Percent leUt l.createPercent le(
        getStageNa Pref x() + "_m ll s_to_resolve_all_t et_urls");

    numberOfCallsToNeedsToBeBatc d = SearchRateCounter.export(getStageNa Pref x()
        + "_calls_to_needsToBeBatc d");

    numberOfEle ntsW h nQuota = SearchRateCounter.export(getStageNa Pref x()
        + "_ s_w h n_quota");

    numberOfEle ntsNotW h nQuota = SearchRateCounter.export(getStageNa Pref x()
        + "_ s_not_w h n_quota");

    numberOfEle ntsW hUrls = SearchRateCounter.export(getStageNa Pref x()
        + "_has_urls");

    numberOfEle ntsW houtUrls = SearchRateCounter.export(getStageNa Pref x()
        + "_does_not_have_urls");
  }

  @Overr de
  protected vo d do nnerPreprocess() throws StageExcept on, Nam ngExcept on {
    searchDec der = new SearchDec der(dec der);
    //   need to call t  after ass gn ng searchDec der because   updateBatchS ze funct on
    // depends on t  searchDec der.
    super.do nnerPreprocess();
    common nnerSetup();
  }

  @Overr de
  protected vo d  nnerSetup() throws P pel neStageExcept on, Nam ngExcept on {
    searchDec der = new SearchDec der(dec der);
    //   need to call t  after ass gn ng searchDec der because   updateBatchS ze funct on
    // depends on t  searchDec der.
    super. nnerSetup();
    common nnerSetup();
  }

  pr vate vo d common nnerSetup() throws Nam ngExcept on {
    Precond  ons.c ckNotNull(p nkCl ent d);
    urlResolver = new AsyncP nkUrlsResolver(
        W reModule
            .getW reModule()
            .getStorer(Durat on.fromM ll seconds(P NK_REQUEST_T MEOUT_M LL S),
                P NK_REQUEST_RETR ES),
        p nkCl ent d);
  }

  @Overr de
  protected Future<Collect on< ngesterTw ter ssage>>  nnerProcessBatch(Collect on<Batc dEle nt
      < ngesterTw ter ssage,  ngesterTw ter ssage>> batch) {
    // Batch urls
    Map<Str ng, Set< ngesterTw ter ssage>> urlToT etsMap = createUrlToT etMap(batch);

    Set<Str ng> urlsToResolve = urlToT etsMap.keySet();

    updateBatchS ze();

    l nksAttempted. ncre nt(batch.s ze());
    // Do t  lookup
    return urlResolver.resolveUrls(urlsToResolve).map(processResolvedUrlsFunct on(batch));
  }

  @Overr de
  protected vo d updateBatchS ze() {
    // update batch based on dec der
     nt dec dedBatchS ze = searchDec der.featureEx sts(P NK_REQUESTS_BATCH_S ZE_DEC DER_KEY)
        ? searchDec der.getAva lab l y(P NK_REQUESTS_BATCH_S ZE_DEC DER_KEY)
        : batchS ze;

    setBatc dStageBatchS ze(dec dedBatchS ze);
  }

  // f not all urls for a  ssage w re resolved re-enqueue unt l maxAge  s reac d
  pr vate Funct on<Map<Str ng, ResolveCompressedUrlsUt ls.Url nfo>,
      Collect on< ngesterTw ter ssage>>
  processResolvedUrlsFunct on(Collect on<Batc dEle nt< ngesterTw ter ssage,
       ngesterTw ter ssage>> batch) {
    return Funct on.func(resolvedUrls -> {
      l nksSucceeded. ncre nt(resolvedUrls.s ze());

      for (ResolveCompressedUrlsUt ls.Url nfo url nfo : resolvedUrls.values()) {
         f (url nfo. d aType != null) {
          urlsW h d aTypeMap.get(url nfo. d aType). ncre nt();
        } else {
          urlsW hout d aType. ncre nt();
        }
      }

      Set< ngesterTw ter ssage> successfulT ets = Sets.newHashSet();

      for (Batc dEle nt< ngesterTw ter ssage,  ngesterTw ter ssage> batc dEle nt : batch) {
         ngesterTw ter ssage  ssage = batc dEle nt.get em();
        Set<Str ng> t etUrls =  ssage.getExpandedUrlMap().keySet();

         nt resolvedUrlCounter = 0;

        for (Str ng url : t etUrls) {
          ResolveCompressedUrlsUt ls.Url nfo url nfo = resolvedUrls.get(url);

          //  f t  url d dn't resolve move on to t  next one, t  m ght tr gger a re-enqueue
          //  f t  t et  s st ll k nd of new. But   want to process t  rest for w n that
          //  s not t  case and   are go ng to end up pass ng   to t  next stage
           f (url nfo == null) {
            cont nue;
          }

          Str ng resolvedUrl = url nfo.resolvedUrl;
          Locale locale = url nfo.language == null ? null
              : LocaleUt l.getLocaleOf(url nfo.language);

           f (Str ngUt ls. sNotBlank(resolvedUrl)) {
            Thr ftExpandedUrl expandedUrl =  ssage.getExpandedUrlMap().get(url);
            resolvedUrlCounter += 1;
            enr chT etW hUrl nfo( ssage, expandedUrl, url nfo, locale);
          }
        }
        long t et ssageAge = clock.nowM ll s() -  ssage.getDate().getT  ();

         f (resolvedUrlCounter == t etUrls.s ze()) {
          m ll sToResolveAllT etURLs.record(t et ssageAge);
          t etsW hResolvedURLs. ncre nt();
          successfulT ets.add( ssage);
        } else  f (t et ssageAge > t etMaxAgeToResolve) {
          t etsW hUnresolvedURLs. ncre nt();
          successfulT ets.add( ssage);
        } else {
          //re-enqueue  f all urls  ren't resolved and t  t et  s  nger than maxAge
          reEnqueueAndRetry(batc dEle nt);
        }
      }
      return successfulT ets;
    });
  }

  pr vate Map<Str ng, Set< ngesterTw ter ssage>> createUrlToT etMap(
      Collect on<Batc dEle nt< ngesterTw ter ssage,  ngesterTw ter ssage>> batch) {
    Map<Str ng, Set< ngesterTw ter ssage>> urlToT etsMap = Maps.newHashMap();
    for (Batc dEle nt< ngesterTw ter ssage,  ngesterTw ter ssage> batc dEle nt : batch) {
       ngesterTw ter ssage  ssage = batc dEle nt.get em();
      for (Str ng or g nalUrl :  ssage.getExpandedUrlMap().keySet()) {
        Set< ngesterTw ter ssage>  ssages = urlToT etsMap.get(or g nalUrl);
         f ( ssages == null) {
           ssages = new HashSet<>();
          urlToT etsMap.put(or g nalUrl,  ssages);
        }
         ssages.add( ssage);
      }
    }
    return Collect ons.unmod f ableMap(urlToT etsMap);
  }

  // enr ch t  tw ter ssage w h t  resolvedCounter Urls.
  pr vate vo d enr chT etW hUrl nfo( ngesterTw ter ssage  ssage,
                                      Thr ftExpandedUrl expandedUrl,
                                      ResolveCompressedUrlsUt ls.Url nfo url nfo,
                                      Locale locale) {
    Str ng truncatedUrl = maybeTruncate(url nfo.resolvedUrl);
     f (truncatedUrl == null) {
      return;
    }

    expandedUrl.setCanon calLastHopUrl(truncatedUrl);
     f (url nfo. d aType != null) {
      // Overwr e url  d a type w h  d a type from resolved url only  f t   d a type from
      // resolved url  s not Unknown
       f (!expandedUrl. sSet d aType() || url nfo. d aType !=  d aTypes.UNKNOWN) {
        expandedUrl.set d aType(url nfo. d aType);
      }
    }
     f (url nfo.l nkCategory != null) {
      expandedUrl.setL nkCategory(url nfo.l nkCategory);
    }
    // Note that  f t re are mult ple l nks  n one t et  ssage, t  language of t 
    // l nk that got exam ned later  n t  for loop w ll overwr e t  values that  re
    // wr ten before. T   s not an opt mal des gn but cons der ng most t ets have
    // only one l nk, or sa -language l nks, t  shouldn't be a b g  ssue.
     f (locale != null) {
       ssage.setL nkLocale(locale);
    }

     f (url nfo.descr pt on != null) {
      expandedUrl.setDescr pt on(url nfo.descr pt on);
    }

     f (url nfo.t le != null) {
      expandedUrl.setT le(url nfo.t le);
    }
  }

  // test  thods
  publ c vo d setResolveUrlPercentage( nt percentage) {
    t .resolveUrlPercentage = percentage;
  }

  publ c vo d setP nkCl ent d(Str ng p nkCl ent d) {
    t .p nkCl ent d = p nkCl ent d;
  }

  publ c stat c f nal  nt MAX_URL_LENGTH = 1000;

  pr vate Str ng maybeTruncate(Str ng fullUrl) {
     f (fullUrl.length() <= MAX_URL_LENGTH) {
      return fullUrl;
    }

    try {
      UR  parsed = new UR (fullUrl);

      // Create a URL w h an empty query and frag nt.
      Str ng s mpl f ed = new UR (parsed.getSc  (),
          parsed.getAuthor y(),
          parsed.getPath(),
          null,
          null).toStr ng();
       f (s mpl f ed.length() < MAX_URL_LENGTH) {
        l nksTruncated. ncre nt();
        return s mpl f ed;
      }
    } catch (UR SyntaxExcept on e) {
    }

    l nksTooLong. ncre nt();
    return null;
  }
}
