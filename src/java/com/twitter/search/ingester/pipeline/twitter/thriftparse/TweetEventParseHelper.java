package com.tw ter.search. ngester.p pel ne.tw ter.thr ftparse;

 mport java.ut l.Date;
 mport java.ut l.L st;
 mport java.ut l.Opt onal;
 mport javax.annotat on.Nonnull;
 mport javax.annotat on.Nullable;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect.L sts;

 mport org.apac .commons.lang.Str ngEscapeUt ls;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common_ nternal.text.vers on.Pengu nVers on;
 mport com.tw ter.dataproducts.enr ch nts.thr ftjava.GeoEnt y;
 mport com.tw ter.dataproducts.enr ch nts.thr ftjava.Potent alLocat on;
 mport com.tw ter.dataproducts.enr ch nts.thr ftjava.Prof leGeoEnr ch nt;
 mport com.tw ter.esc rb rd.thr ftjava.T etEnt yAnnotat on;
 mport com.tw ter.expandodo.thr ftjava.Card2;
 mport com.tw ter.g zmoduck.thr ftjava.User;
 mport com.tw ter. d aserv ces.commons.t et d a.thr ft_java. d a nfo;
 mport com.tw ter.search.common.debug.thr ftjava.DebugEvents;
 mport com.tw ter.search.common. tr cs.Percent le;
 mport com.tw ter.search.common. tr cs.Percent leUt l;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common.part  on ng.snowflakeparser.Snowflake dParser;
 mport com.tw ter.search.common.relevance.ent  es.GeoObject;
 mport com.tw ter.search.common.relevance.ent  es.Potent alLocat onObject;
 mport com.tw ter.search.common.relevance.ent  es.Tw ter ssage;
 mport com.tw ter.search.common.relevance.ent  es.Tw ter ssage.Esc rb rdAnnotat on;
 mport com.tw ter.search.common.relevance.ent  es.Tw ter ssageUser;
 mport com.tw ter.search.common.relevance.ent  es.Tw ter ssageUt l;
 mport com.tw ter.search.common.relevance.ent  es.Tw terQuoted ssage;
 mport com.tw ter.search.common.relevance.ent  es.Tw terRet et ssage;
 mport com.tw ter.search. ngester.model. ngesterTw ter ssage;
 mport com.tw ter.search. ngester.p pel ne.ut l.CardF eldUt l;
 mport com.tw ter.serv ce.sp derduck.gen. d aTypes;
 mport com.tw ter.t etyp e.thr ftjava.Dev ceS ce;
 mport com.tw ter.t etyp e.thr ftjava.D rectedAtUser;
 mport com.tw ter.t etyp e.thr ftjava.Esc rb rdEnt yAnnotat ons;
 mport com.tw ter.t etyp e.thr ftjava.Exclus veT etControl;
 mport com.tw ter.t etyp e.thr ftjava.GeoCoord nates;
 mport com.tw ter.t etyp e.thr ftjava.HashtagEnt y;
 mport com.tw ter.t etyp e.thr ftjava. d aEnt y;
 mport com.tw ter.t etyp e.thr ftjava. nt onEnt y;
 mport com.tw ter.t etyp e.thr ftjava.Place;
 mport com.tw ter.t etyp e.thr ftjava.QuotedT et;
 mport com.tw ter.t etyp e.thr ftjava.Reply;
 mport com.tw ter.t etyp e.thr ftjava.T et;
 mport com.tw ter.t etyp e.thr ftjava.T etCoreData;
 mport com.tw ter.t etyp e.thr ftjava.T etCreateEvent;
 mport com.tw ter.t etyp e.thr ftjava.T etDeleteEvent;
 mport com.tw ter.t etyp e.thr ftjava.UrlEnt y;
 mport com.tw ter.t etyp e.t ettext.Part alHtmlEncod ng;

/**
 * T   s an ut l y class for convert ng Thr ft T etEvent  ssages sent by T etyP e
 *  nto  ngester  nternal representat on,  ngesterTw ter ssage.
 */
publ c f nal class T etEventParse lper {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(T etEventParse lper.class);

  @V s bleForTest ng
  stat c f nal SearchCounter NUM_TWEETS_W TH_NULL_TEXT =
      SearchCounter.export("t ets_w h_null_text_from_thr ft_cnt");

  @V s bleForTest ng
  stat c f nal SearchCounter TWEET_S ZE = SearchCounter.export("t et_s ze_from_thr ft");

  @V s bleForTest ng
  stat c f nal Percent le<Long> TWEET_S ZE_PERCENT LES =
      Percent leUt l.createPercent le("t et_s ze_from_thr ft");

  @V s bleForTest ng
  stat c f nal SearchCounter NUM_TWEETS_W TH_CONVERSAT ON_ D =
      SearchCounter.export("t ets_w h_conversat on_ d_from_thr ft_cnt");

  @V s bleForTest ng
  stat c f nal SearchCounter NUM_TWEETS_W TH_QUOTE =
      SearchCounter.export("t ets_w h_quote_from_thr ft_cnt");

  @V s bleForTest ng
  stat c f nal SearchCounter NUM_TWEETS_W TH_ANNOTAT ONS =
      SearchCounter.export("t ets_w h_annotat on_from_thr ft_cnt");

  @V s bleForTest ng
  stat c f nal SearchCounter NUM_ANNOTAT ONS_ADDED =
      SearchCounter.export("num_annotat ons_from_thr ft_cnt");

  @V s bleForTest ng
  stat c f nal SearchCounter NUM_TWEETS_W TH_COORD NATE_F ELD =
      SearchCounter.export("t ets_w h_coord nate_f eld_from_thr ft_cnt");

  @V s bleForTest ng
  stat c f nal SearchCounter NUM_PLACE_ADDED =
      SearchCounter.export("num_places_from_thr ft_cnt");

  @V s bleForTest ng
  stat c f nal SearchCounter NUM_TWEETS_W TH_PLACE_F ELD =
      SearchCounter.export("t ets_w h_place_f eld_from_thr ft_cnt");

  @V s bleForTest ng
  stat c f nal SearchCounter NUM_TWEETS_W TH_PLACE_COUNTRY_CODE =
      SearchCounter.export("t ets_w h_place_country_code_from_thr ft_cnt");

  @V s bleForTest ng
  stat c f nal SearchCounter NUM_TWEETS_USE_PLACE_F ELD =
      SearchCounter.export("t ets_use_place_f eld_from_thr ft_cnt");

  @V s bleForTest ng
  stat c f nal SearchCounter NUM_TWEETS_CANNOT_PARSE_PLACE_F ELD =
      SearchCounter.export("t ets_cannot_parse_place_f eld_from_thr ft_cnt");

  @V s bleForTest ng
  stat c f nal SearchCounter NUM_TWEETS_W TH_PROF LE_GEO_ENR CHMENT =
      SearchCounter.export("t ets_w h_prof le_geo_enr ch nt_from_thr ft_cnt");

  @V s bleForTest ng
  stat c f nal SearchCounter NUM_TWEETS_W TH_MENT ONS =
      SearchCounter.export("t ets_w h_ nt ons_from_thr ft_cnt");

  @V s bleForTest ng
  stat c f nal SearchCounter NUM_MENT ONS_ADDED =
      SearchCounter.export("num_ nt ons_from_thr ft_cnt");

  @V s bleForTest ng
  stat c f nal SearchCounter NUM_TWEETS_W TH_HASHTAGS =
      SearchCounter.export("t ets_w h_hashtags_from_thr ft_cnt");

  @V s bleForTest ng
  stat c f nal SearchCounter NUM_HASHTAGS_ADDED =
      SearchCounter.export("num_hashtags_from_thr ft_cnt");

  @V s bleForTest ng
  stat c f nal SearchCounter NUM_TWEETS_W TH_MED A_URL =
      SearchCounter.export("t ets_w h_ d a_url_from_thr ft_cnt");

  @V s bleForTest ng
  stat c f nal SearchCounter NUM_MED A_URLS_ADDED =
      SearchCounter.export("num_ d a_urls_from_thr ft_cnt");

  @V s bleForTest ng
  stat c f nal SearchCounter NUM_TWEETS_W TH_PHOTO_MED A_URL =
      SearchCounter.export("t ets_w h_photo_ d a_url_from_thr ft_cnt");

  @V s bleForTest ng
  stat c f nal SearchCounter NUM_TWEETS_W TH_V DEO_MED A_URL =
      SearchCounter.export("t ets_w h_v deo_ d a_url_from_thr ft_cnt");

  @V s bleForTest ng
  stat c f nal SearchCounter NUM_TWEETS_W TH_NON_MED A_URL =
      SearchCounter.export("t ets_w h_non_ d a_url_from_thr ft_cnt");

  @V s bleForTest ng
  stat c f nal SearchCounter NUM_NON_MED A_URLS_ADDED =
      SearchCounter.export("num_non_ d a_urls_from_thr ft_cnt");

  @V s bleForTest ng
  stat c f nal SearchCounter NUM_TWEETS_M SS NG_QUOTE_URLS =
      SearchCounter.export("num_t ets_m ss ng_quote_urls_cnt");

  // Ut l y class, d sallow  nstant at on.
  pr vate T etEventParse lper() {
  }

  /** Bu lds an  ngesterTw ter ssage  nstance from a T etCreateEvent. */
  @Nonnull
  publ c stat c  ngesterTw ter ssage getTw ter ssageFromCreat onEvent(
      @Nonnull T etCreateEvent createEvent,
      @Nonnull L st<Pengu nVers on> supportedPengu nVers ons,
      @Nullable DebugEvents debugEvents) throws Thr ftT etPars ngExcept on {

    T et t et = createEvent.getT et();
     f (t et == null) {
      throw new Thr ftT etPars ngExcept on("No t et f eld  n T etCreateEvent");
    }

    T etCoreData coreData = t et.getCore_data();
     f (coreData == null) {
      throw new Thr ftT etPars ngExcept on("No core_data f eld  n T et  n T etCreateEvent");
    }

    User user = createEvent.getUser();
     f (user == null) {
      throw new Thr ftT etPars ngExcept on("No user f eld  n T etCreateEvent");
    }
     f (!user. sSetProf le()) {
      throw new Thr ftT etPars ngExcept on("No prof le f eld  n User  n T etCreateEvent");
    }
     f (!user. sSetSafety()) {
      throw new Thr ftT etPars ngExcept on("No safety f eld  n User  n T etCreateEvent");
    }

    long tw ter d = t et.get d();
     ngesterTw ter ssage  ssage = new  ngesterTw ter ssage(
        tw ter d,
        supportedPengu nVers ons,
        debugEvents);

    // Set t  creat on t   based on t  t et  D, because   has m ll second granular y,
    // and coreData.created_at_secs has only second granular y.
     ssage.setDate(new Date(Snowflake dParser.getT  stampFromT et d(tw ter d)));

    boolean  sNsfw = coreData. sNsfw_adm n() || coreData. sNsfw_user();
    boolean has d aOrUrlsOrCards =
        t et.get d aS ze() > 0
            || t et.getUrlsS ze() > 0
            || t et.getCardsS ze() > 0
            || t et. sSetCard2();

     ssage.set sSens  veContent( sNsfw && has d aOrUrlsOrCards);

     ssage.setFromUser(getFromUser(user));
     f (user. sSetCounts()) {
       ssage.setFollo rsCount(( nt) user.getCounts().getFollo rs());
    }
     ssage.setUserProtected(user.getSafety(). s s_protected());
     ssage.setUserVer f ed(user.getSafety(). sVer f ed());
     ssage.setUserBlueVer f ed(user.getSafety(). s s_blue_ver f ed());

     f (t et. sSetLanguage()) {
       ssage.setLanguage(t et.getLanguage().getLanguage()); // language  D l ke "en"
    }

     f (t et. sSetSelf_thread_ tadata()) {
       ssage.setSelfThread(true);
    }

    Exclus veT etControl exclus veT etControl = t et.getExclus ve_t et_control();
     f (exclus veT etControl != null) {
       f (exclus veT etControl. sSetConversat on_author_ d()) {
         ssage.setExclus veConversat onAuthor d(
            exclus veT etControl.getConversat on_author_ d());
      }
    }

    setD rectedAtUser( ssage, coreData);
    add nt onsTo ssage( ssage, t et);
    addHashtagsTo ssage( ssage, t et);
    add d aEnt  esTo ssage( ssage, t et.get d(), t et.get d a());
    addUrlsTo ssage( ssage, t et.getUrls());
    addEsc rb rdAnnotat onsTo ssage( ssage, t et);
     ssage.setNullcast(coreData. sNullcast());

     f (coreData. sSetConversat on_ d()) {
       ssage.setConversat on d(coreData.getConversat on_ d());
      NUM_TWEETS_W TH_CONVERSAT ON_ D. ncre nt();
    }

    // quotes
     f (t et. sSetQuoted_t et()) {
      QuotedT et quotedT et = t et.getQuoted_t et();
       f (quotedT et.getT et_ d() > 0 &&  quotedT et.getUser_ d() > 0) {
         f (quotedT et. sSetPermal nk()) {
          Str ng quotedURL = quotedT et.getPermal nk().getLong_url();
          UrlEnt y quotedURLEnt y = new UrlEnt y();
          quotedURLEnt y.setExpanded(quotedURL);
          quotedURLEnt y.setUrl(quotedT et.getPermal nk().getShort_url());
          quotedURLEnt y.setD splay(quotedT et.getPermal nk().getD splay_text());
          addUrlsTo ssage( ssage, L sts.newArrayL st(quotedURLEnt y));
        } else {
          LOG.warn("T et {} has quoted t et, but  s m ss ng quoted t et URL: {}",
                   t et.get d(), quotedT et);
          NUM_TWEETS_M SS NG_QUOTE_URLS. ncre nt();
        }
        Tw terQuoted ssage quoted ssage =
            new Tw terQuoted ssage(
                quotedT et.getT et_ d(),
                quotedT et.getUser_ d());
         ssage.setQuoted ssage(quoted ssage);
        NUM_TWEETS_W TH_QUOTE. ncre nt();
      }
    }

    // card f elds
     f (createEvent.getT et(). sSetCard2()) {
      Card2 card = createEvent.getT et().getCard2();
       ssage.setCardNa (card.getNa ());
       ssage.setCardT le(
          CardF eldUt l.extractB nd ngValue(CardF eldUt l.T TLE_B ND NG_KEY, card));
       ssage.setCardDescr pt on(
          CardF eldUt l.extractB nd ngValue(CardF eldUt l.DESCR PT ON_B ND NG_KEY, card));
      CardF eldUt l.der veCardLang( ssage);
       ssage.setCardUrl(card.getUrl());
    }

    // So  f elds should be set based on t  "or g nal" t et. So  f t  t et  s a ret et,
    //   want to extract those f elds from t  ret eted t et.
    T et ret etOrT et = t et;
    T etCoreData ret etOrT etCoreData = coreData;
    User ret etOrT etUser = user;

    // ret ets
    boolean  sRet et = coreData. sSetShare();
     f ( sRet et) {
      ret etOrT et = createEvent.getS ce_t et();
      ret etOrT etCoreData = ret etOrT et.getCore_data();
      ret etOrT etUser = createEvent.getS ce_user();

      Tw terRet et ssage ret et ssage = new Tw terRet et ssage();
      ret et ssage.setRet et d(tw ter d);

       f (ret etOrT etUser != null) {
         f (ret etOrT etUser. sSetProf le()) {
          ret et ssage.setSharedUserD splayNa (ret etOrT etUser.getProf le().getNa ());
        }
        ret et ssage.setSharedUserTw ter d(ret etOrT etUser.get d());
      }

      ret et ssage.setSharedDate(new Date(ret etOrT etCoreData.getCreated_at_secs() * 1000));
      ret et ssage.setShared d(ret etOrT et.get d());

      add d aEnt  esTo ssage( ssage, ret etOrT et.get d(), ret etOrT et.get d a());
      addUrlsTo ssage( ssage, ret etOrT et.getUrls());

      //  f a t et's text  s longer than 140 characters, t  text for any ret et of that t et
      // w ll be truncated. And  f t  or g nal t et has hashtags or  nt ons after character 140,
      // t  T etyp e event for t  ret et w ll not  nclude those hashtags/ nt ons, wh ch w ll
      // make t  ret et unsearchable by those hashtags/ nt ons. So  n order to avo d t 
      // problem,   add to t  ret et all hashtags/ nt ons set on t  or g nal t et.
      add nt onsTo ssage( ssage, ret etOrT et);
      addHashtagsTo ssage( ssage, ret etOrT et);

       ssage.setRet et ssage(ret et ssage);
    }

    // So  f elds should be set based on t  "or g nal" t et.
    // Only set geo f elds  f t   s not a ret et
     f (! sRet et) {
      setGeoF elds( ssage, ret etOrT etCoreData, ret etOrT etUser);
      setPlacesF elds( ssage, ret etOrT et);
    }
    setText( ssage, ret etOrT etCoreData);
    set nReplyTo( ssage, ret etOrT etCoreData,  sRet et);
    setDev ceS ceF eld( ssage, ret etOrT et);

    // Prof le geo enr ch nt f elds should be set based on t  t et, even  f  's a ret et.
    setProf leGeoEnr ch ntF elds( ssage, t et);

    // T  composer used to create t  t et: standard t et creator or t  ca ra flow.
    setComposerS ce( ssage, t et);

    return  ssage;
  }

  pr vate stat c vo d setGeoF elds(
      Tw ter ssage  ssage, T etCoreData coreData, User user) {

     f (coreData. sSetCoord nates()) {
      NUM_TWEETS_W TH_COORD NATE_F ELD. ncre nt();
      GeoCoord nates coords = coreData.getCoord nates();
       ssage.setGeoTaggedLocat on(
          GeoObject.createFor ngester(coords.getLat ude(), coords.getLong ude()));

      Str ng locat on =
          Str ng.format("GeoAP :%.4f,%.4f", coords.getLat ude(), coords.getLong ude());
      Tw ter ssageUt l.setAndTruncateLocat onOn ssage( ssage, locat on);
    }

    //  f t  locat on was not set from t  coord nates.
     f (( ssage.getOr gLocat on() == null) && (user != null) && user. sSetProf le()) {
      Tw ter ssageUt l.setAndTruncateLocat onOn ssage( ssage, user.getProf le().getLocat on());
    }
  }

  pr vate stat c vo d setPlacesF elds(Tw ter ssage  ssage, T et t et) {
     f (!t et. sSetPlace()) {
      return;
    }

    Place place = t et.getPlace();

     f (place. sSetConta ners() && place.getConta nersS ze() > 0) {
      NUM_TWEETS_W TH_PLACE_F ELD. ncre nt();
      NUM_PLACE_ADDED.add(place.getConta nersS ze());

      for (Str ng place d : place.getConta ners()) {
         ssage.addPlace(place d);
      }
    }

    Precond  ons.c ckArgu nt(place. sSet d(), "T et.Place w hout  d.");
     ssage.setPlace d(place.get d());
    Precond  ons.c ckArgu nt(place. sSetFull_na (), "T et.Place w hout full_na .");
     ssage.setPlaceFullNa (place.getFull_na ());
     f (place. sSetCountry_code()) {
       ssage.setPlaceCountryCode(place.getCountry_code());
      NUM_TWEETS_W TH_PLACE_COUNTRY_CODE. ncre nt();
    }

     f ( ssage.getGeoTaggedLocat on() == null) {
      Opt onal<GeoObject> locat on = GeoObject.fromPlace(place);

       f (locat on. sPresent()) {
        NUM_TWEETS_USE_PLACE_F ELD. ncre nt();
         ssage.setGeoTaggedLocat on(locat on.get());
      } else {
        NUM_TWEETS_CANNOT_PARSE_PLACE_F ELD. ncre nt();
      }
    }
  }

  pr vate stat c vo d setText(Tw ter ssage  ssage, T etCoreData coreData) {
    /**
     * T etyP e doesn't do a full HTML escap ng of t  text, only a part al escap ng
     * so   use t  r code to unescape   f rst, t n   do
     * a second unescap ng because w n t  t et text  self has HTML escape
     * sequences,   want to  ndex t  unescaped vers on, not t  escape sequence  self.
     * --
     * Yes,   *double* unescape html. About 1-2 t ets per second are double escaped,
     * and   probably want to  ndex t  real text and not th ngs l ke '&#9733;'.
     * Unescap ng already unescaped text seems safe  n pract ce.
     * --
     *
     * T  may seem wrong, because one th nks   should  ndex whatever t  user posts,
     * but g ven punctuat on str pp ng t  creates odd behav or:
     *
     *  f so one t ets &amp; t y won't be able to f nd   by search ng for '&amp;' because
     * t  t et w ll be  ndexed as 'amp'
     *
     *   would also prevent so  t ets from surfac ng for certa n searc s, for example:
     *
     * User T ets: John Mayer &amp; Dave Chappelle
     *   Unescape To: John Mayer & Dave Chappelle
     *   Str p/Normal ze To: john mayer dave chappelle
     *
     * A user search ng for 'John Mayer Dave Chappelle' would get t  above t et.
     *
     *  f   d dn't double unescape
     *
     * User T ets: John Mayer &amp; Dave Chappelle
     *   Str p/Normal ze To: john mayer amp dave chappelle
     *
     * A user search ng for 'John Mayer Dave Chappelle' would m ss t  above t et.
     *
     * Second example
     *
     * User T ets: L'Human &eacute;
     *   Unescape To: L'Human é
     *   Str p/Normal ze To: l human e
     *
     *  f   d dn't double escape
     *
     * User T ets: L'Human &eacute;
     *   Str p/Normal ze To: l human  eacute
     *
     */

    Str ng text = coreData. sSetText()
        ? Str ngEscapeUt ls.unescapeHtml(Part alHtmlEncod ng.decode(coreData.getText()))
        : coreData.getText();
     ssage.setText(text);
     f (text != null) {
      long t etLength = text.length();
      TWEET_S ZE.add(t etLength);
      TWEET_S ZE_PERCENT LES.record(t etLength);
    } else {
      NUM_TWEETS_W TH_NULL_TEXT. ncre nt();
    }
  }

  pr vate stat c vo d set nReplyTo(
      Tw ter ssage  ssage, T etCoreData coreData, boolean  sRet et) {
    Reply reply = coreData.getReply();
     f (! sRet et && reply != null) {
      Str ng  nReplyToScreenNa  = reply.get n_reply_to_screen_na ();
      long  nReplyToUser d = reply.get n_reply_to_user_ d();
       ssage.replaceToUserW h nReplyToUser fNeeded( nReplyToScreenNa ,  nReplyToUser d);
    }

     f ((reply != null) && reply. sSet n_reply_to_status_ d()) {
       ssage.set nReplyToStatus d(reply.get n_reply_to_status_ d());
    }
  }

  pr vate stat c vo d setProf leGeoEnr ch ntF elds(Tw ter ssage  ssage, T et t et) {
     f (!t et. sSetProf le_geo_enr ch nt()) {
      return;
    }

    Prof leGeoEnr ch nt prof leGeoEnr ch nt = t et.getProf le_geo_enr ch nt();
    L st<Potent alLocat on> thr ftPotent alLocat ons =
        prof leGeoEnr ch nt.getPotent al_locat ons();
     f (!thr ftPotent alLocat ons. sEmpty()) {
      NUM_TWEETS_W TH_PROF LE_GEO_ENR CHMENT. ncre nt();
      L st<Potent alLocat onObject> potent alLocat ons = L sts.newArrayL st();
      for (Potent alLocat on potent alLocat on : thr ftPotent alLocat ons) {
        GeoEnt y geoEnt y = potent alLocat on.getGeo_ent y();
        potent alLocat ons.add(new Potent alLocat onObject(geoEnt y.getCountry_code(),
                                                           geoEnt y.getReg on(),
                                                           geoEnt y.getLocal y()));
      }

       ssage.setPotent alLocat ons(potent alLocat ons);
    }
  }

  pr vate stat c vo d setDev ceS ceF eld(Tw ter ssage  ssage, T et t et) {
    Dev ceS ce dev ceS ce = t et.getDev ce_s ce();
    Tw ter ssageUt l.setS ceOn ssage( ssage, mod fyDev ceS ceW hNofollow(dev ceS ce));
  }

  /** Bu lds an  ngesterTw ter ssage  nstance from a T etDeleteEvent. */
  @Nonnull
  publ c stat c  ngesterTw ter ssage getTw ter ssageFromDelet onEvent(
      @Nonnull T etDeleteEvent deleteEvent,
      @Nonnull L st<Pengu nVers on> supportedPengu nVers ons,
      @Nullable DebugEvents debugEvents) throws Thr ftT etPars ngExcept on {

    T et t et = deleteEvent.getT et();
     f (t et == null) {
      throw new Thr ftT etPars ngExcept on("No t et f eld  n T etDeleteEvent");
    }
    long t et d = t et.get d();

    T etCoreData coreData = t et.getCore_data();
     f (coreData == null) {
      throw new Thr ftT etPars ngExcept on("No T etCoreData  n T etDeleteEvent");
    }
    long user d = coreData.getUser_ d();

     ngesterTw ter ssage  ssage = new  ngesterTw ter ssage(
        t et d,
        supportedPengu nVers ons,
        debugEvents);
     ssage.setDeleted(true);
     ssage.setText("delete");
     ssage.setFromUser(Tw ter ssageUser.createW hNa sAnd d("delete", "delete", user d));

    return  ssage;
  }

  pr vate stat c Tw ter ssageUser getFromUser(User user) {
    Str ng screenNa  = user.getProf le().getScreen_na ();
    long  d = user.get d();
    Str ng d splayNa  = user.getProf le().getNa ();
    return Tw ter ssageUser.createW hNa sAnd d(screenNa , d splayNa ,  d);
  }

  pr vate stat c vo d add nt onsTo ssage( ngesterTw ter ssage  ssage, T et t et) {
    L st< nt onEnt y>  nt ons = t et.get nt ons();
     f ( nt ons != null) {
      NUM_TWEETS_W TH_MENT ONS. ncre nt();
      NUM_MENT ONS_ADDED.add( nt ons.s ze());
      for ( nt onEnt y  nt on :  nt ons) {
        add nt on( ssage,  nt on);
      }
    }
  }

  pr vate stat c vo d add nt on( ngesterTw ter ssage  ssage,  nt onEnt y  nt on) {
    // Default values. T y are   rd, but are cons stent w h JSON pars ng behav or.
    Opt onal<Long>  d = Opt onal.of(-1L);
    Opt onal<Str ng> screenNa  = Opt onal.of("");
    Opt onal<Str ng> d splayNa  = Opt onal.of("");

     f ( nt on. sSetUser_ d()) {
       d = Opt onal.of( nt on.getUser_ d());
    }

     f ( nt on. sSetScreen_na ()) {
      screenNa  = Opt onal.of( nt on.getScreen_na ());
    }

     f ( nt on. sSetNa ()) {
      d splayNa  = Opt onal.of( nt on.getNa ());
    }

    Tw ter ssageUser  nt onedUser = Tw ter ssageUser
        .createW hOpt onalNa sAnd d(screenNa , d splayNa ,  d);

     f ( sToUser( nt on,  ssage.getToUserObject())) {
       ssage.setToUserObject( nt onedUser);
    }
     ssage.addUserTo nt ons( nt onedUser);
  }

  pr vate stat c boolean  sToUser(
       nt onEnt y  nt on, Opt onal<Tw ter ssageUser> opt onalToUser) {
     f ( nt on.getFrom_ ndex() == 0) {
      return true;
    }
     f (opt onalToUser. sPresent()) {
      Tw ter ssageUser toUser = opt onalToUser.get();
       f (toUser.get d(). sPresent()) {
        long toUser d = toUser.get d().get();
        return  nt on.getUser_ d() == toUser d;
      }
    }
    return false;
  }

  pr vate stat c vo d addHashtagsTo ssage( ngesterTw ter ssage  ssage, T et t et) {
    L st<HashtagEnt y> hashtags = t et.getHashtags();
     f (hashtags != null) {
      NUM_TWEETS_W TH_HASHTAGS. ncre nt();
      NUM_HASHTAGS_ADDED.add(hashtags.s ze());
      for (HashtagEnt y hashtag : hashtags) {
        addHashtag( ssage, hashtag);
      }
    }
  }

  pr vate stat c vo d addHashtag( ngesterTw ter ssage  ssage, HashtagEnt y hashtag) {
    Str ng hashtagStr ng = hashtag.getText();
     ssage.addHashtag(hashtagStr ng);
  }

  /** Add t  g ven  d a ent  es to t  g ven  ssage. */
  publ c stat c vo d add d aEnt  esTo ssage(
       ngesterTw ter ssage  ssage,
      long photoStatus d,
      @Nullable L st< d aEnt y>  d as) {

     f ( d as != null) {
      NUM_TWEETS_W TH_MED A_URL. ncre nt();
      NUM_MED A_URLS_ADDED.add( d as.s ze());

      boolean hasPhoto d aUrl = false;
      boolean hasV deo d aUrl = false;
      for ( d aEnt y  d a :  d as) {
         d aTypes  d aType = null;
         f ( d a. sSet d a_ nfo()) {
           d a nfo  d a nfo =  d a.get d a_ nfo();
           f ( d a nfo != null) {
             f ( d a nfo. sSet( d a nfo._F elds. MAGE_ NFO)) {
               d aType =  d aTypes.NAT VE_ MAGE;
              Str ng  d aUrl =  d a.get d a_url_https();
               f ( d aUrl != null) {
                hasPhoto d aUrl = true;
                 ssage.addPhotoUrl(photoStatus d,  d aUrl);
                // Add t  l nk to t  expanded URLs too, so that t  HAS_NAT VE_ MAGE_FLAG  s set
                // correctly too. See EncodedFeatureBu lder.updateL nkEncodedFeatures().
              }
            } else  f ( d a nfo. sSet( d a nfo._F elds.V DEO_ NFO)) {
               d aType =  d aTypes.V DEO;
              hasV deo d aUrl = true;
            }
          }
        }
        Str ng or g nalUrl =  d a.getUrl();
        Str ng expandedUrl =  d a.getExpanded_url();
         ssage.addExpanded d aUrl(or g nalUrl, expandedUrl,  d aType);
      }

       f (hasPhoto d aUrl) {
        NUM_TWEETS_W TH_PHOTO_MED A_URL. ncre nt();
      }
       f (hasV deo d aUrl) {
        NUM_TWEETS_W TH_V DEO_MED A_URL. ncre nt();
      }
    }
  }

  /** Adds t  g ven urls to t  g ven  ssage. */
  publ c stat c vo d addUrlsTo ssage(
       ngesterTw ter ssage  ssage,
      @Nullable L st<UrlEnt y> urls) {

     f (urls != null) {
      NUM_TWEETS_W TH_NON_MED A_URL. ncre nt();
      NUM_NON_MED A_URLS_ADDED.add(urls.s ze());
      for (UrlEnt y url : urls) {
        Str ng or g nalUrl = url.getUrl();
        Str ng expandedUrl = url.getExpanded();
         ssage.addExpandedNon d aUrl(or g nalUrl, expandedUrl);
      }
    }
  }

  pr vate stat c vo d addEsc rb rdAnnotat onsTo ssage(
       ngesterTw ter ssage  ssage, T et t et) {
     f (t et. sSetEsc rb rd_ent y_annotat ons()) {
      Esc rb rdEnt yAnnotat ons ent yAnnotat ons = t et.getEsc rb rd_ent y_annotat ons();
       f (ent yAnnotat ons. sSetEnt y_annotat ons()) {
        NUM_TWEETS_W TH_ANNOTAT ONS. ncre nt();
        NUM_ANNOTAT ONS_ADDED.add(ent yAnnotat ons.getEnt y_annotat onsS ze());
        for (T etEnt yAnnotat on ent yAnnotat on : ent yAnnotat ons.getEnt y_annotat ons()) {
          Esc rb rdAnnotat on esc rb rdAnnotat on =
              new Esc rb rdAnnotat on(ent yAnnotat on.getGroup d(),
                  ent yAnnotat on.getDoma n d(),
                  ent yAnnotat on.getEnt y d());
           ssage.addEsc rb rdAnnotat on(esc rb rdAnnotat on);
        }
      }
    }
  }

  pr vate stat c vo d setComposerS ce( ngesterTw ter ssage  ssage, T et t et) {
     f (t et. sSetComposer_s ce()) {
       ssage.setComposerS ce(t et.getComposer_s ce());
    }
  }

  pr vate stat c Str ng mod fyDev ceS ceW hNofollow(@Nullable Dev ceS ce dev ceS ce) {
     f (dev ceS ce != null) {
      Str ng s ce = dev ceS ce.getD splay();
       nt   = s ce. ndexOf("\">");
       f (  == -1) {
        return s ce;
      } else {
        return s ce.substr ng(0,  ) + "\" rel=\"nofollow\">" + s ce.substr ng(  + 2);
      }
    } else {
      return "<a href=\"http://tw ter.com\" rel=\"nofollow\">Tw ter</a>";
    }
  }

  pr vate stat c vo d setD rectedAtUser(
       ngesterTw ter ssage  ssage,
      T etCoreData t etCoreData) {
     f (!t etCoreData. sSetD rected_at_user()) {
      return;
    }

    D rectedAtUser d rectedAtUser = t etCoreData.getD rected_at_user();

     f (!d rectedAtUser. sSetUser_ d()) {
      return;
    }

     ssage.setD rectedAtUser d(Opt onal.of(d rectedAtUser.getUser_ d()));
  }
}
