package com.tw ter.search.common.relevance.ent  es;

 mport java.text.DateFormat;
 mport java.ut l.ArrayL st;
 mport java.ut l.Arrays;
 mport java.ut l.Collect on;
 mport java.ut l.Collect ons;
 mport java.ut l.Date;
 mport java.ut l.HashSet;
 mport java.ut l.L nkedHashMap;
 mport java.ut l.L st;
 mport java.ut l.Locale;
 mport java.ut l.Map;
 mport java.ut l.Opt onal;
 mport java.ut l.Set;
 mport javax.annotat on.Nonnull;
 mport javax.annotat on.Nullable;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect.Compar sonCha n;
 mport com.google.common.collect.L sts;
 mport com.google.common.collect.Maps;
 mport com.google.common.collect.Sets;

 mport org.apac .commons.lang.Str ngUt ls;
 mport org.apac .commons.lang3.bu lder.EqualsBu lder;
 mport org.apac .commons.lang3.bu lder.HashCodeBu lder;
 mport org.apac .commons.lang3.bu lder.ToStr ngBu lder;
 mport org.apac .lucene.analys s.TokenStream;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common.text.language.LocaleUt l;
 mport com.tw ter.common.text.p pel ne.Tw terLanguage dent f er;
 mport com.tw ter.common.text.token.Token zedCharSequence;
 mport com.tw ter.common_ nternal.text.vers on.Pengu nVers on;
 mport com.tw ter.cuad.ner.pla n.thr ftjava.Na dEnt y;
 mport com.tw ter.search.common. ndex ng.thr ftjava.Thr ftExpandedUrl;
 mport com.tw ter.search.common.relevance.features.T etFeatures;
 mport com.tw ter.search.common.relevance.features.T etTextFeatures;
 mport com.tw ter.search.common.relevance.features.T etTextQual y;
 mport com.tw ter.search.common.relevance.features.T etUserFeatures;
 mport com.tw ter.search.common.ut l.text.Normal zer lper;
 mport com.tw ter.serv ce.sp derduck.gen. d aTypes;
 mport com.tw ter.t etyp e.thr ftjava.ComposerS ce;
 mport com.tw ter.ut l.Tw terDateFormat;

/**
 * A representat on of t ets used as an  nter d ate object dur ng  ngest on. As   proceed
 *  n  ngest on,   f ll t  object w h data.   t n convert   to Thr ftVers onedEvents (wh ch
 *  self represents a s ngle t et too,  n d fferent pengu n vers ons potent ally).
 */
publ c class Tw ter ssage {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Tw ter ssage.class);

  publ c stat c class Esc rb rdAnnotat on  mple nts Comparable<Esc rb rdAnnotat on> {
    publ c f nal long group d;
    publ c f nal long doma n d;
    publ c f nal long ent y d;

    publ c Esc rb rdAnnotat on(long group d, long doma n d, long ent y d) {
      t .group d = group d;
      t .doma n d = doma n d;
      t .ent y d = ent y d;
    }

    @Overr de
    publ c boolean equals(Object o2) {
       f (o2  nstanceof Esc rb rdAnnotat on) {
        Esc rb rdAnnotat on a2 = (Esc rb rdAnnotat on) o2;
        return group d == a2.group d && doma n d == a2.doma n d && ent y d == a2.ent y d;
      }
      return false;
    }

    @Overr de
    publ c  nt hashCode() {
      return new HashCodeBu lder()
          .append(group d)
          .append(doma n d)
          .append(ent y d)
          .toHashCode();
    }

    @Overr de
    publ c  nt compareTo(Esc rb rdAnnotat on o) {
      return Compar sonCha n.start()
          .compare(t .group d, o.group d)
          .compare(t .doma n d, o.doma n d)
          .compare(t .ent y d, o.ent y d)
          .result();
    }
  }

  pr vate f nal L st<Esc rb rdAnnotat on> esc rb rdAnnotat ons = L sts.newArrayL st();

  // t et features for mult ple pengu n vers ons
  pr vate stat c class Vers onedT etFeatures {
    // T etFeatures populated by relevance class f ers, structure def ned
    //  n src/ma n/thr ft/class f er.thr ft.
    pr vate T etFeatures t etFeatures = new T etFeatures();
    pr vate Token zedCharSequence token zedCharSequence = null;
    pr vate Set<Str ng> normal zedHashtags = Sets.newHashSet();

    publ c T etFeatures getT etFeatures() {
      return t .t etFeatures;
    }

    publ c vo d setT etFeatures(f nal T etFeatures t etFeatures) {
      t .t etFeatures = t etFeatures;
    }

    publ c T etTextQual y getT etTextQual y() {
      return t .t etFeatures.getT etTextQual y();
    }

    publ c T etTextFeatures getT etTextFeatures() {
      return t .t etFeatures.getT etTextFeatures();
    }

    publ c T etUserFeatures getT etUserFeatures() {
      return t .t etFeatures.getT etUserFeatures();
    }

    publ c Token zedCharSequence getToken zedCharSequence() {
      return t .token zedCharSequence;
    }

    publ c vo d setToken zedCharSequence(Token zedCharSequence sequence) {
      t .token zedCharSequence = sequence;
    }

    publ c Set<Str ng> getNormal zedHashtags() {
      return t .normal zedHashtags;
    }

    publ c vo d addNormal zedHashtags(Str ng normal zedHashtag) {
      t .normal zedHashtags.add(normal zedHashtag);
    }
  }

  publ c stat c f nal  nt  NT_F ELD_NOT_PRESENT = -1;
  publ c stat c f nal long LONG_F ELD_NOT_PRESENT = -1;
  publ c stat c f nal double DOUBLE_F ELD_NOT_PRESENT = -1;
  publ c stat c f nal  nt MAX_USER_REPUTAT ON = 100;

  pr vate f nal long t et d;

  pr vate Str ng text;

  pr vate Date date;
  @Nonnull
  pr vate Opt onal<Tw ter ssageUser> opt onalFromUser = Opt onal.empty();
  @Nonnull
  pr vate Opt onal<Tw ter ssageUser> opt onalToUser = Opt onal.empty();
  pr vate Locale locale = null;
  pr vate Locale l nkLocale = null;

  // Or g nal s ce text.
  pr vate Str ng or gS ce;
  // S ce w h HTML tags removed and truncated.
  pr vate Str ng str ppedS ce;

  // Or g nal locat on text.
  pr vate Str ng or gLocat on;

  // Locat on truncated for  sql f eld-w dth reasons (see Tw ter ssageUt l.java).
  pr vate Str ng truncatedNormal zedLocat on;

  // User's country
  pr vate Str ng fromUserLocCountry;

  pr vate  nteger follo rsCount =  NT_F ELD_NOT_PRESENT;
  pr vate boolean deleted = false;

  // F elds extracted from ent  es ( n t  JSON object)
  pr vate L st<Tw ter ssageUser>  nt ons = new ArrayL st<>();
  pr vate Set<Str ng> hashtags = Sets.newHashSet();
  // Lat/lon and reg on accuracy tuples extracted from t et text, or null.
  pr vate GeoObject geoLocat on = null;
  pr vate boolean uncodeableLocat on = false;
  // T   s set  f t  t et  s geotagged. ( .e. "geo" or "coord nate" sect on  s present
  //  n t  json)
  // T  f eld has only a getter but no setter ---    s f lled  n w n t  json  s parsed.
  pr vate GeoObject geoTaggedLocat on = null;

  pr vate double userReputat on = DOUBLE_F ELD_NOT_PRESENT;
  pr vate boolean geocodeRequ red = false;
  pr vate boolean sens  veContent = false;
  pr vate boolean userProtected;
  pr vate boolean userVer f ed;
  pr vate boolean userBlueVer f ed;
  pr vate Tw terRet et ssage ret et ssage;
  pr vate Tw terQuoted ssage quoted ssage;
  pr vate L st<Str ng> places;
  // maps from or g nal url (t  t.co url) to Thr ftExpandedUrl, wh ch conta ns t 
  // expanded url and t  sp derduck response (cano calLastHopUrl and  d atype)
  pr vate f nal Map<Str ng, Thr ftExpandedUrl> expandedUrls;
  // maps t  photo status  d to t   d a url
  pr vate Map<Long, Str ng> photoUrls;
  pr vate Opt onal<Long>  nReplyToStatus d = Opt onal.empty();
  pr vate Opt onal<Long> d rectedAtUser d = Opt onal.empty();

  pr vate long conversat on d = -1;

  // True  f t et  s nullcasted.
  pr vate boolean nullcast = false;

  // True  f t et  s a self-threaded t et
  pr vate boolean selfThread = false;

  //  f t  t et  s a part of an exclus ve conversat on, t  author who started
  // that conversat on.
  pr vate Opt onal<Long> exclus veConversat onAuthor d = Opt onal.empty();

  // t et features map for mult ple vers ons of pengu n
  pr vate Map<Pengu nVers on, Vers onedT etFeatures> vers onedT etFeaturesMap;

  // Engag nts count: favor es, ret ets and repl es
  pr vate  nt numFavor es = 0;
  pr vate  nt numRet ets = 0;
  pr vate  nt numRepl es = 0;

  // Card  nformat on
  pr vate Str ng cardNa ;
  pr vate Str ng cardDoma n;
  pr vate Str ng cardT le;
  pr vate Str ng cardDescr pt on;
  pr vate Str ng cardLang;
  pr vate Str ng cardUrl;

  pr vate Str ng place d;
  pr vate Str ng placeFullNa ;
  pr vate Str ng placeCountryCode;

  pr vate Set<Na dEnt y> na dEnt  es = Sets.newHashSet();

  // Spaces data
  pr vate Set<Str ng> space ds = Sets.newHashSet();
  pr vate Set<Tw ter ssageUser> spaceAdm ns = Sets.newHashSet();
  pr vate Str ng spaceT le;

  pr vate Opt onal<ComposerS ce> composerS ce = Opt onal.empty();

  pr vate f nal L st<Potent alLocat onObject> potent alLocat ons = L sts.newArrayL st();

  // one or two pengu n vers ons supported by t  system
  pr vate f nal L st<Pengu nVers on> supportedPengu nVers ons;

  publ c Tw ter ssage(Long t et d, L st<Pengu nVers on> supportedPengu nVers ons) {
    t .t et d = t et d;
    t .places = new ArrayL st<>();
    t .expandedUrls = new L nkedHashMap<>();
    // make sure   support at least one, but no more than two vers ons of pengu n
    t .supportedPengu nVers ons = supportedPengu nVers ons;
    t .vers onedT etFeaturesMap = getVers onedT etFeaturesMap();
    Precond  ons.c ckArgu nt(t .supportedPengu nVers ons.s ze() <= 2
        && t .supportedPengu nVers ons.s ze() > 0);
  }

  /**
   * Replace to-user w h  n-reply-to user  f needed.
   */
  publ c vo d replaceToUserW h nReplyToUser fNeeded(
      Str ng  nReplyToScreenNa , long  nReplyToUser d) {
     f (shouldUseReplyUserAsToUser(opt onalToUser,  nReplyToUser d)) {
      Tw ter ssageUser replyUser =
          Tw ter ssageUser.createW hNa sAnd d( nReplyToScreenNa , "",  nReplyToUser d);
      opt onalToUser = Opt onal.of(replyUser);
    }
  }

  // To-user could have been  nferred from t   nt on at t  pos  on 0.
  // But  f t re  s an expl c   n-reply-to user,   m ght need to use   as to-user  nstead.
  pr vate stat c boolean shouldUseReplyUserAsToUser(
      Opt onal<Tw ter ssageUser> currentToUser,
      long  nReplyToUser d) {
     f (!currentToUser. sPresent()) {
      // T re  s no  nt on  n t  t et that qual f es as to-user.
      return true;
    }

    //   already have a  nt on  n t  t et that qual f es as to-user.
    Tw ter ssageUser toUser = currentToUser.get();
     f (!toUser.get d(). sPresent()) {
      // T  to-user from t   nt on  s a stub.
      return true;
    }

    long toUser d = toUser.get d().get();
     f (toUser d !=  nReplyToUser d) {
      // T  to-user from t   nt on  s d fferent that t   n-reply-to user,
      // use  n-reply-to user  nstead.
      return true;
    }

    return false;
  }

  publ c double getUserReputat on() {
    return userReputat on;
  }

  /**
   * Sets t  user reputat on.
   */
  publ c Tw ter ssage setUserReputat on(double newUserReputat on) {
     f (newUserReputat on > MAX_USER_REPUTAT ON) {
      LOG.warn("Out of bounds user reputat on {} for status  d {}", newUserReputat on, t et d);
      t .userReputat on = (float) MAX_USER_REPUTAT ON;
    } else {
      t .userReputat on = newUserReputat on;
    }
    return t ;
  }

  publ c Str ng getText() {
    return text;
  }

  publ c Opt onal<Tw ter ssageUser> getOpt onalToUser() {
    return opt onalToUser;
  }

  publ c vo d setOpt onalToUser(Opt onal<Tw ter ssageUser> opt onalToUser) {
    t .opt onalToUser = opt onalToUser;
  }

  publ c vo d setText(Str ng text) {
    t .text = text;
  }

  publ c Date getDate() {
    return date;
  }

  publ c vo d setDate(Date date) {
    t .date = date;
  }

  publ c vo d setFromUser(@Nonnull Tw ter ssageUser fromUser) {
    Precond  ons.c ckNotNull(fromUser, "Don't set a null fromUser");
    opt onalFromUser = Opt onal.of(fromUser);
  }

  publ c Opt onal<Str ng> getFromUserScreenNa () {
    return opt onalFromUser. sPresent()
        ? opt onalFromUser.get().getScreenNa ()
        : Opt onal.empty();
  }

  /**
   * Sets t  fromUserScreenNa .
   */
  publ c vo d setFromUserScreenNa (@Nonnull Str ng fromUserScreenNa ) {
    Tw ter ssageUser newFromUser = opt onalFromUser. sPresent()
        ? opt onalFromUser.get().copyW hScreenNa (fromUserScreenNa )
        : Tw ter ssageUser.createW hScreenNa (fromUserScreenNa );

    opt onalFromUser = Opt onal.of(newFromUser);
  }

  publ c Opt onal<TokenStream> getToken zedFromUserScreenNa () {
    return opt onalFromUser.flatMap(Tw ter ssageUser::getToken zedScreenNa );
  }

  publ c Opt onal<Str ng> getFromUserD splayNa () {
    return opt onalFromUser.flatMap(Tw ter ssageUser::getD splayNa );
  }

  /**
   * Sets t  fromUserD splayNa .
   */
  publ c vo d setFromUserD splayNa (@Nonnull Str ng fromUserD splayNa ) {
    Tw ter ssageUser newFromUser = opt onalFromUser. sPresent()
        ? opt onalFromUser.get().copyW hD splayNa (fromUserD splayNa )
        : Tw ter ssageUser.createW hD splayNa (fromUserD splayNa );

    opt onalFromUser = Opt onal.of(newFromUser);
  }

  publ c Opt onal<Long> getFromUserTw ter d() {
    return opt onalFromUser.flatMap(Tw ter ssageUser::get d);
  }

  /**
   * Sets t  fromUser d.
   */
  publ c vo d setFromUser d(long fromUser d) {
    Tw ter ssageUser newFromUser = opt onalFromUser. sPresent()
        ? opt onalFromUser.get().copyW h d(fromUser d)
        : Tw ter ssageUser.createW h d(fromUser d);

    opt onalFromUser = Opt onal.of(newFromUser);
  }

  publ c long getConversat on d() {
    return conversat on d;
  }

  publ c vo d setConversat on d(long conversat on d) {
    t .conversat on d = conversat on d;
  }

  publ c boolean  sUserProtected() {
    return t .userProtected;
  }

  publ c vo d setUserProtected(boolean userProtected) {
    t .userProtected = userProtected;
  }

  publ c boolean  sUserVer f ed() {
    return t .userVer f ed;
  }

  publ c vo d setUserVer f ed(boolean userVer f ed) {
    t .userVer f ed = userVer f ed;
  }

  publ c boolean  sUserBlueVer f ed() {
    return t .userBlueVer f ed;
  }

  publ c vo d setUserBlueVer f ed(boolean userBlueVer f ed) {
    t .userBlueVer f ed = userBlueVer f ed;
  }

  publ c vo d set sSens  veContent(boolean  sSens  veContent) {
    t .sens  veContent =  sSens  veContent;
  }

  publ c boolean  sSens  veContent() {
    return t .sens  veContent;
  }

  publ c Opt onal<Tw ter ssageUser> getToUserObject() {
    return opt onalToUser;
  }

  publ c vo d setToUserObject(@Nonnull Tw ter ssageUser user) {
    Precond  ons.c ckNotNull(user, "Don't set a null to-user");
    opt onalToUser = Opt onal.of(user);
  }

  publ c Opt onal<Long> getToUserTw ter d() {
    return opt onalToUser.flatMap(Tw ter ssageUser::get d);
  }

  /**
   * Sets toUser d.
   */
  publ c vo d setToUserTw ter d(long toUser d) {
    Tw ter ssageUser newToUser = opt onalToUser. sPresent()
        ? opt onalToUser.get().copyW h d(toUser d)
        : Tw ter ssageUser.createW h d(toUser d);

    opt onalToUser = Opt onal.of(newToUser);
  }

  publ c Opt onal<Str ng> getToUserLo rcasedScreenNa () {
    return opt onalToUser.flatMap(Tw ter ssageUser::getScreenNa ).map(Str ng::toLo rCase);
  }

  publ c Opt onal<Str ng> getToUserScreenNa () {
    return opt onalToUser.flatMap(Tw ter ssageUser::getScreenNa );
  }

  /**
   * Sets toUserScreenNa .
   */
  publ c vo d setToUserScreenNa (@Nonnull Str ng screenNa ) {
    Precond  ons.c ckNotNull(screenNa , "Don't set a null to-user screenna ");

    Tw ter ssageUser newToUser = opt onalToUser. sPresent()
        ? opt onalToUser.get().copyW hScreenNa (screenNa )
        : Tw ter ssageUser.createW hScreenNa (screenNa );

    opt onalToUser = Opt onal.of(newToUser);
  }

  // to use from T etEventParse lper
  publ c vo d setD rectedAtUser d(Opt onal<Long> d rectedAtUser d) {
    t .d rectedAtUser d = d rectedAtUser d;
  }

  @V s bleForTest ng
  publ c Opt onal<Long> getD rectedAtUser d() {
    return d rectedAtUser d;
  }

  /**
   * Returns t  referenceAuthor d.
   */
  publ c Opt onal<Long> getReferenceAuthor d() {
    // T  semant cs of reference-author- d:
    // -  f t  t et  s a ret et,   should be t  user  d of t  author of t  or g nal t et
    // - else,  f t  t et  s d rected at a user,   should be t   d of t  user  's d rected at.
    // - else,  f t  t et  s a reply  n a root self-thread, d rected-at  s not set, so  's
    //   t   d of t  user who started t  self-thread.
    //
    // For def n  ve  nfo on repl es and d rected-at, take a look at go/repl es. To v ew t se
    // for a certa n t et, use http://go/t.
    //
    // Note that  f d rected-at  s set, reply  s always set.
    //  f reply  s set, d rected-at  s not necessar ly set.
     f ( sRet et() && ret et ssage.hasSharedUserTw ter d()) {
      long ret etedUser d = ret et ssage.getSharedUserTw ter d();
      return Opt onal.of(ret etedUser d);
    } else  f (d rectedAtUser d. sPresent()) {
      // Why not replace d rectedAtUser d w h reply and make t  funct on depend
      // on t  "reply" f eld of T etCoreData?
      //  ll, ver f ed by counters,   seems for ~1% of t ets, wh ch conta n both d rected-at
      // and reply, d rected-at-user  s d fferent than t  reply-to-user  d. T  happens  n t 
      // follow ng case:
      //
      //       author / reply-to / d rected-at
      //  T1   A        -          -
      //  T2   B        A          A
      //  T3   B        B          A
      //
      //  T2  s a reply to T1, T3  s a reply to T2.
      //
      //  's up to us to dec de who t  t et  s "referenc ng", but w h t  current code,
      //   choose that T3  s referenc ng user A.
      return d rectedAtUser d;
    } else {
      // T   s t  case of a root self-thread reply. d rected-at  s not set.
      Opt onal<Long> fromUser d = t .getFromUserTw ter d();
      Opt onal<Long> toUser d = t .getToUserTw ter d();

       f (fromUser d. sPresent() && fromUser d.equals(toUser d)) {
        return fromUser d;
      }
    }
    return Opt onal.empty();
  }

  publ c vo d setNumFavor es( nt numFavor es) {
    t .numFavor es = numFavor es;
  }

  publ c vo d setNumRet ets( nt numRet ets) {
    t .numRet ets = numRet ets;
  }

  publ c vo d setNumRepl es( nt numRepl ess) {
    t .numRepl es = numRepl ess;
  }

  publ c vo d addEsc rb rdAnnotat on(Esc rb rdAnnotat on annotat on) {
    esc rb rdAnnotat ons.add(annotat on);
  }

  publ c L st<Esc rb rdAnnotat on> getEsc rb rdAnnotat ons() {
    return esc rb rdAnnotat ons;
  }

  publ c L st<Potent alLocat onObject> getPotent alLocat ons() {
    return potent alLocat ons;
  }

  publ c vo d setPotent alLocat ons(Collect on<Potent alLocat onObject> potent alLocat ons) {
    t .potent alLocat ons.clear();
    t .potent alLocat ons.addAll(potent alLocat ons);
  }

  @Overr de
  publ c Str ng toStr ng() {
    return ToStr ngBu lder.reflect onToStr ng(t );
  }

  // T et language related getters and setters.

  /**
   * Returns t  locale.
   * <p>
   * Note t  getLocale() w ll never return null, t   s for t  conven ence of text related
   * process ng  n t   ngester.  f   want t  real locale,   need to c ck  sSetLocale()
   * f rst to see  f   really have any  nformat on about t  locale of t  t et.
   */
  publ c Locale getLocale() {
     f (locale == null) {
      return Tw terLanguage dent f er.UNKNOWN;
    } else {
      return locale;
    }
  }

  publ c vo d setLocale(Locale locale) {
    t .locale = locale;
  }

  /**
   * Determ nes  f t  locate  s set.
   */
  publ c boolean  sSetLocale() {
    return locale != null;
  }

  /**
   * Returns t  language of t  locale. E.g. zh
   */
  publ c Str ng getLanguage() {
     f ( sSetLocale()) {
      return getLocale().getLanguage();
    } else {
      return null;
    }
  }

  /**
   * Returns t   ETF BCP 47 Language Tag of t  locale. E.g. zh-CN
   */
  publ c Str ng getBCP47LanguageTag() {
     f ( sSetLocale()) {
      return getLocale().toLanguageTag();
    } else {
      return null;
    }
  }

  publ c vo d setLanguage(Str ng language) {
     f (language != null) {
      locale = LocaleUt l.getLocaleOf(language);
    }
  }

  // T et l nk language related getters and setters.
  publ c Locale getL nkLocale() {
    return l nkLocale;
  }

  publ c vo d setL nkLocale(Locale l nkLocale) {
    t .l nkLocale = l nkLocale;
  }

  /**
   * Returns t  language of t  l nk locale.
   */
  publ c Str ng getL nkLanguage() {
     f (t .l nkLocale == null) {
      return null;
    } else {
      return t .l nkLocale.getLanguage();
    }
  }

  publ c Str ng getOr gS ce() {
    return or gS ce;
  }

  publ c vo d setOr gS ce(Str ng or gS ce) {
    t .or gS ce = or gS ce;
  }

  publ c Str ng getStr ppedS ce() {
    return str ppedS ce;
  }

  publ c vo d setStr ppedS ce(Str ng str ppedS ce) {
    t .str ppedS ce = str ppedS ce;
  }

  publ c Str ng getOr gLocat on() {
    return or gLocat on;
  }

  publ c Str ng getLocat on() {
    return truncatedNormal zedLocat on;
  }

  publ c vo d setOr gLocat on(Str ng or gLocat on) {
    t .or gLocat on = or gLocat on;
  }

  publ c vo d setTruncatedNormal zedLocat on(Str ng truncatedNormal zedLocat on) {
    t .truncatedNormal zedLocat on = truncatedNormal zedLocat on;
  }

  publ c boolean hasFromUserLocCountry() {
    return fromUserLocCountry != null;
  }

  publ c Str ng getFromUserLocCountry() {
    return fromUserLocCountry;
  }

  publ c vo d setFromUserLocCountry(Str ng fromUserLocCountry) {
    t .fromUserLocCountry = fromUserLocCountry;
  }

  publ c Str ng getTruncatedNormal zedLocat on() {
    return truncatedNormal zedLocat on;
  }

  publ c  nteger getFollo rsCount() {
    return follo rsCount;
  }

  publ c vo d setFollo rsCount( nteger follo rsCount) {
    t .follo rsCount = follo rsCount;
  }

  publ c boolean hasFollo rsCount() {
    return follo rsCount !=  NT_F ELD_NOT_PRESENT;
  }

  publ c boolean  sDeleted() {
    return deleted;
  }

  publ c vo d setDeleted(boolean deleted) {
    t .deleted = deleted;
  }

  publ c boolean hasCard() {
    return !Str ngUt ls. sBlank(getCardNa ());
  }

  @Overr de
  publ c  nt hashCode() {
    return ((Long) get d()).hashCode();
  }

  /**
   * Parses t  g ven date us ng t  Tw terDateFormat.
   */
  publ c stat c Date parseDate(Str ng date) {
    DateFormat parser = Tw terDateFormat.apply("EEE MMM d HH:mm:ss Z yyyy");
    try {
      return parser.parse(date);
    } catch (Except on e) {
      return null;
    }
  }

  publ c boolean hasGeoLocat on() {
    return geoLocat on != null;
  }

  publ c vo d setGeoLocat on(GeoObject locat on) {
    t .geoLocat on = locat on;
  }

  publ c GeoObject getGeoLocat on() {
    return geoLocat on;
  }

  publ c Str ng getPlace d() {
    return place d;
  }

  publ c vo d setPlace d(Str ng place d) {
    t .place d = place d;
  }

  publ c Str ng getPlaceFullNa () {
    return placeFullNa ;
  }

  publ c vo d setPlaceFullNa (Str ng placeFullNa ) {
    t .placeFullNa  = placeFullNa ;
  }

  publ c Str ng getPlaceCountryCode() {
    return placeCountryCode;
  }

  publ c vo d setPlaceCountryCode(Str ng placeCountryCode) {
    t .placeCountryCode = placeCountryCode;
  }

  publ c vo d setGeoTaggedLocat on(GeoObject geoTaggedLocat on) {
    t .geoTaggedLocat on = geoTaggedLocat on;
  }

  publ c GeoObject getGeoTaggedLocat on() {
    return geoTaggedLocat on;
  }

  publ c vo d setLatLon(double lat ude, double long ude) {
    geoLocat on = new GeoObject(lat ude, long ude, null);
  }

  publ c Double getLat ude() {
    return hasGeoLocat on() ? geoLocat on.getLat ude() : null;
  }

  publ c Double getLong ude() {
    return hasGeoLocat on() ? geoLocat on.getLong ude() : null;
  }

  publ c boolean  sUncodeableLocat on() {
    return uncodeableLocat on;
  }

  publ c vo d setUncodeableLocat on() {
    uncodeableLocat on = true;
  }

  publ c vo d setGeocodeRequ red() {
    t .geocodeRequ red = true;
  }

  publ c boolean  sGeocodeRequ red() {
    return geocodeRequ red;
  }

  publ c Map<Long, Str ng> getPhotoUrls() {
    return photoUrls;
  }

  /**
   * Assoc ates t  g ven  d aUrl w h t  g ven photoStatus d.
   */
  publ c vo d addPhotoUrl(long photoStatus d, Str ng  d aUrl) {
     f (photoUrls == null) {
      photoUrls = new L nkedHashMap<>();
    }
    photoUrls.put fAbsent(photoStatus d,  d aUrl);
  }

  publ c Map<Str ng, Thr ftExpandedUrl> getExpandedUrlMap() {
    return expandedUrls;
  }

  publ c  nt getExpandedUrlMapS ze() {
    return expandedUrls.s ze();
  }

  /**
   * Assoc ates t  g ven or g nalUrl w h t  g ven expanderUrl.
   */
  publ c vo d addExpandedUrl(Str ng or g nalUrl, Thr ftExpandedUrl expandedUrl) {
    t .expandedUrls.put(or g nalUrl, expandedUrl);
  }

  /**
   * Replaces urls w h resolved ones.
   */
  publ c Str ng getTextReplacedW hResolvedURLs() {
    Str ng retText = text;
    for (Map.Entry<Str ng, Thr ftExpandedUrl> entry : expandedUrls.entrySet()) {
      Thr ftExpandedUrl url nfo = entry.getValue();
      Str ng resolvedUrl;
      Str ng canon calLastHopUrl = url nfo.getCanon calLastHopUrl();
      Str ng expandedUrl = url nfo.getExpandedUrl();
       f (canon calLastHopUrl != null) {
        resolvedUrl = canon calLastHopUrl;
        LOG.debug("{} has canon cal last hop url set", url nfo);
      } else  f (expandedUrl != null) {
        LOG.debug("{} has no canon cal last hop url set, us ng expanded url  nstead", url nfo);
        resolvedUrl = expandedUrl;
      } else {
        LOG.debug("{} has no canon cal last hop url or expanded url set, sk pp ng", url nfo);
        cont nue;
      }
      retText = retText.replace(entry.getKey(), resolvedUrl);
    }
    return retText;
  }

  publ c long get d() {
    return t et d;
  }

  publ c boolean  sRet et() {
    return ret et ssage != null;
  }

  publ c boolean hasQuote() {
    return quoted ssage != null;
  }

  publ c boolean  sReply() {
    return getToUserScreenNa (). sPresent()
        || getToUserTw ter d(). sPresent()
        || get nReplyToStatus d(). sPresent();
  }

  publ c boolean  sReplyToT et() {
    return get nReplyToStatus d(). sPresent();
  }

  publ c Tw terRet et ssage getRet et ssage() {
    return ret et ssage;
  }

  publ c vo d setRet et ssage(Tw terRet et ssage ret et ssage) {
    t .ret et ssage = ret et ssage;
  }

  publ c Tw terQuoted ssage getQuoted ssage() {
    return quoted ssage;
  }

  publ c vo d setQuoted ssage(Tw terQuoted ssage quoted ssage) {
    t .quoted ssage = quoted ssage;
  }

  publ c L st<Str ng> getPlaces() {
    return places;
  }

  publ c vo d addPlace(Str ng place) {
    // Places are used for earlyb rd ser al zat on
    places.add(place);
  }

  publ c Opt onal<Long> get nReplyToStatus d() {
    return  nReplyToStatus d;
  }

  publ c vo d set nReplyToStatus d(long  nReplyToStatus d) {
    Precond  ons.c ckArgu nt( nReplyToStatus d > 0, " n-reply-to status  D should be pos  ve");
    t . nReplyToStatus d = Opt onal.of( nReplyToStatus d);
  }

  publ c boolean getNullcast() {
    return nullcast;
  }

  publ c vo d setNullcast(boolean nullcast) {
    t .nullcast = nullcast;
  }

  publ c L st<Pengu nVers on> getSupportedPengu nVers ons() {
    return supportedPengu nVers ons;
  }

  pr vate Vers onedT etFeatures getVers onedT etFeatures(Pengu nVers on pengu nVers on) {
    Vers onedT etFeatures vers onedT etFeatures = vers onedT etFeaturesMap.get(pengu nVers on);
    return Precond  ons.c ckNotNull(vers onedT etFeatures);
  }

  publ c T etFeatures getT etFeatures(Pengu nVers on pengu nVers on) {
    return getVers onedT etFeatures(pengu nVers on).getT etFeatures();
  }

  @V s bleForTest ng
  // only used  n Tests
  publ c vo d setT etFeatures(Pengu nVers on pengu nVers on, T etFeatures t etFeatures) {
    vers onedT etFeaturesMap.get(pengu nVers on).setT etFeatures(t etFeatures);
  }

  publ c  nt getT etS gnature(Pengu nVers on pengu nVers on) {
    return getVers onedT etFeatures(pengu nVers on).getT etTextFeatures().getS gnature();
  }

  publ c T etTextQual y getT etTextQual y(Pengu nVers on pengu nVers on) {
    return getVers onedT etFeatures(pengu nVers on).getT etTextQual y();
  }

  publ c T etTextFeatures getT etTextFeatures(Pengu nVers on pengu nVers on) {
    return getVers onedT etFeatures(pengu nVers on).getT etTextFeatures();
  }

  publ c T etUserFeatures getT etUserFeatures(Pengu nVers on pengu nVers on) {
    return getVers onedT etFeatures(pengu nVers on).getT etUserFeatures();
  }

  publ c Token zedCharSequence getToken zedCharSequence(Pengu nVers on pengu nVers on) {
    return getVers onedT etFeatures(pengu nVers on).getToken zedCharSequence();
  }

  publ c vo d setToken zedCharSequence(Pengu nVers on pengu nVers on,
                                       Token zedCharSequence sequence) {
    getVers onedT etFeatures(pengu nVers on).setToken zedCharSequence(sequence);
  }

  // True  f t  features conta n mult ple hash tags or mult ple trends.
  // T   s  ntended as an ant -trend-spam  asure.
  publ c stat c boolean hasMult pleHashtagsOrTrends(T etTextFeatures textFeatures) {
    // Allow at most 1 trend and 2 hashtags.
    return textFeatures.getTrend ngTermsS ze() > 1 || textFeatures.getHashtagsS ze() > 2;
  }

  /**
   * Returns t  expanded URLs.
   */
  publ c Collect on<Thr ftExpandedUrl> getExpandedUrls() {
    return expandedUrls.values();
  }

  /**
   * Returns t  canon cal last hop URLs.
   */
  publ c Set<Str ng> getCanon calLastHopUrls() {
    Set<Str ng> result = new HashSet<>(expandedUrls.s ze());
    for (Thr ftExpandedUrl url : expandedUrls.values()) {
      result.add(url.getCanon calLastHopUrl());
    }
    return result;
  }

  publ c Str ng getCardNa () {
    return cardNa ;
  }

  publ c vo d setCardNa (Str ng cardNa ) {
    t .cardNa  = cardNa ;
  }

  publ c Str ng getCardDoma n() {
    return cardDoma n;
  }

  publ c vo d setCardDoma n(Str ng cardDoma n) {
    t .cardDoma n = cardDoma n;
  }

  publ c Str ng getCardT le() {
    return cardT le;
  }

  publ c vo d setCardT le(Str ng cardT le) {
    t .cardT le = cardT le;
  }

  publ c Str ng getCardDescr pt on() {
    return cardDescr pt on;
  }

  publ c vo d setCardDescr pt on(Str ng cardDescr pt on) {
    t .cardDescr pt on = cardDescr pt on;
  }

  publ c Str ng getCardLang() {
    return cardLang;
  }

  publ c vo d setCardLang(Str ng cardLang) {
    t .cardLang = cardLang;
  }

  publ c Str ng getCardUrl() {
    return cardUrl;
  }

  publ c vo d setCardUrl(Str ng cardUrl) {
    t .cardUrl = cardUrl;
  }

  publ c L st<Tw ter ssageUser> get nt ons() {
    return t . nt ons;
  }

  publ c vo d set nt ons(L st<Tw ter ssageUser>  nt ons) {
    t . nt ons =  nt ons;
  }

  publ c L st<Str ng> getLo rcased nt ons() {
    return L sts.transform(get nt ons(), user -> {
      // T  cond  on  s also c cked  n addUserTo nt ons().
      Precond  ons.c ckState(user.getScreenNa (). sPresent(), " nval d  nt on");
      return user.getScreenNa ().get().toLo rCase();
    });
  }

  publ c Set<Str ng> getHashtags() {
    return t .hashtags;
  }

  publ c Set<Str ng> getNormal zedHashtags(Pengu nVers on pengu nVers on) {
    return getVers onedT etFeatures(pengu nVers on).getNormal zedHashtags();
  }

  publ c vo d addNormal zedHashtag(Str ng normal zedHashtag, Pengu nVers on pengu nVers on) {
    getVers onedT etFeatures(pengu nVers on).addNormal zedHashtags(normal zedHashtag);
  }

  publ c Opt onal<ComposerS ce> getComposerS ce() {
    return composerS ce;
  }

  publ c vo d setComposerS ce(ComposerS ce composerS ce) {
    Precond  ons.c ckNotNull(composerS ce, "composerS ce should not be null");
    t .composerS ce = Opt onal.of(composerS ce);
  }

  publ c boolean  sSelfThread() {
    return selfThread;
  }

  publ c vo d setSelfThread(boolean selfThread) {
    t .selfThread = selfThread;
  }

  publ c boolean  sExclus ve() {
    return exclus veConversat onAuthor d. sPresent();
  }

  publ c long getExclus veConversat onAuthor d() {
    return exclus veConversat onAuthor d.get();
  }

  publ c vo d setExclus veConversat onAuthor d(long exclus veConversat onAuthor d) {
    t .exclus veConversat onAuthor d = Opt onal.of(exclus veConversat onAuthor d);
  }

  /**
   * Adds an expanded  d a url based on t  g ven para ters.
   */
  publ c vo d addExpanded d aUrl(Str ng or g nalUrl,
                                  Str ng expandedUrl,
                                  @Nullable  d aTypes  d aType) {
     f (!Str ngUt ls. sBlank(or g nalUrl) && !Str ngUt ls. sBlank(expandedUrl)) {
      Thr ftExpandedUrl thr ftExpandedUrl = new Thr ftExpandedUrl();
       f ( d aType != null) {
        thr ftExpandedUrl.set d aType( d aType);
      }
      thr ftExpandedUrl.setOr g nalUrl(or g nalUrl);
      thr ftExpandedUrl.setExpandedUrl(expandedUrl);  // T  w ll be token zed and  ndexed
      // Note that t   d aURL  s not  ndexed.   could also  ndex  , but    s not  ndexed
      // to reduce RAM usage.
      thr ftExpandedUrl.setCanon calLastHopUrl(expandedUrl); // T  w ll be token zed and  ndexed
      addExpandedUrl(or g nalUrl, thr ftExpandedUrl);
      thr ftExpandedUrl.setConsu r d a(true);
    }
  }

  /**
   * Adds an expanded non- d a url based on t  g ven para ters.
   */
  publ c vo d addExpandedNon d aUrl(Str ng or g nalUrl, Str ng expandedUrl) {
     f (!Str ngUt ls. sBlank(or g nalUrl)) {
      Thr ftExpandedUrl thr ftExpandedUrl = new Thr ftExpandedUrl(or g nalUrl);
       f (!Str ngUt ls. sBlank(expandedUrl)) {
        thr ftExpandedUrl.setExpandedUrl(expandedUrl);
      }
      addExpandedUrl(or g nalUrl, thr ftExpandedUrl);
      thr ftExpandedUrl.setConsu r d a(false);
    }
  }

  /**
   * Only used  n tests.
   *
   * S mulates resolv ng compressed URLs, wh ch  s usually done by ResolveCompressedUrlsStage.
   */
  @V s bleForTest ng
  publ c vo d replaceUrlsW hResolvedUrls(Map<Str ng, Str ng> resolvedUrls) {
    for (Map.Entry<Str ng, Thr ftExpandedUrl> urlEntry : expandedUrls.entrySet()) {
      Str ng tcoUrl = urlEntry.getKey();
       f (resolvedUrls.conta nsKey(tcoUrl)) {
        Thr ftExpandedUrl expandedUrl = urlEntry.getValue();
        expandedUrl.setCanon calLastHopUrl(resolvedUrls.get(tcoUrl));
      }
    }
  }

  /**
   * Adds a  nt on for a user w h t  g ven screen na .
   */
  publ c vo d add nt on(Str ng screenNa ) {
    Tw ter ssageUser user = Tw ter ssageUser.createW hScreenNa (screenNa );
    addUserTo nt ons(user);
  }

  /**
   * Adds t  g ven user to  nt ons.
   */
  publ c vo d addUserTo nt ons(Tw ter ssageUser user) {
    Precond  ons.c ckArgu nt(user.getScreenNa (). sPresent(), "Don't add  nval d  nt ons");
    t . nt ons.add(user);
  }

  /**
   * Adds t  g ven hashtag.
   */
  publ c vo d addHashtag(Str ng hashtag) {
    t .hashtags.add(hashtag);
    for (Pengu nVers on pengu nVers on : supportedPengu nVers ons) {
      addNormal zedHashtag(Normal zer lper.normal ze(hashtag, getLocale(), pengu nVers on),
          pengu nVers on);
    }
  }

  pr vate Map<Pengu nVers on, Vers onedT etFeatures> getVers onedT etFeaturesMap() {
    Map<Pengu nVers on, Vers onedT etFeatures> vers onedMap =
        Maps.newEnumMap(Pengu nVers on.class);
    for (Pengu nVers on pengu nVers on : getSupportedPengu nVers ons()) {
      vers onedMap.put(pengu nVers on, new Vers onedT etFeatures());
    }

    return vers onedMap;
  }

  publ c  nt getNumFavor es() {
    return numFavor es;
  }

  publ c  nt getNumRet ets() {
    return numRet ets;
  }

  publ c  nt getNumRepl es() {
    return numRepl es;
  }

  publ c Set<Na dEnt y> getNa dEnt  es() {
    return na dEnt  es;
  }

  publ c vo d addNa dEnt y(Na dEnt y na dEnt y) {
    na dEnt  es.add(na dEnt y);
  }

  publ c Set<Str ng> getSpace ds() {
    return space ds;
  }

  publ c vo d setSpace ds(Set<Str ng> space ds) {
    t .space ds = Sets.newHashSet(space ds);
  }

  publ c Set<Tw ter ssageUser> getSpaceAdm ns() {
    return spaceAdm ns;
  }

  publ c vo d addSpaceAdm n(Tw ter ssageUser adm n) {
    spaceAdm ns.add(adm n);
  }

  publ c Str ng getSpaceT le() {
    return spaceT le;
  }

  publ c vo d setSpaceT le(Str ng spaceT le) {
    t .spaceT le = spaceT le;
  }

  pr vate stat c boolean equals(L st<Esc rb rdAnnotat on> l1, L st<Esc rb rdAnnotat on> l2) {
    Esc rb rdAnnotat on[] arr1 = l1.toArray(new Esc rb rdAnnotat on[l1.s ze()]);
    Arrays.sort(arr1);
    Esc rb rdAnnotat on[] arr2 = l1.toArray(new Esc rb rdAnnotat on[l2.s ze()]);
    Arrays.sort(arr2);
    return Arrays.equals(arr1, arr2);
  }

  /**
   * Compares t  g ven  ssages us ng reflect on and determ nes  f t y're approx mately equal.
   */
  publ c stat c boolean reflect onApproxEquals(
      Tw ter ssage a,
      Tw ter ssage b,
      L st<Str ng> add  onalExcludeF elds) {
    L st<Str ng> excludeF elds = L sts.newArrayL st(
        "vers onedT etFeaturesMap",
        "geoLocat on",
        "geoTaggedLocat on",
        "esc rb rdAnnotat ons"
    );
    excludeF elds.addAll(add  onalExcludeF elds);

    return EqualsBu lder.reflect onEquals(a, b, excludeF elds)
        && GeoObject.approxEquals(a.getGeoLocat on(), b.getGeoLocat on())
        && GeoObject.approxEquals(a.getGeoTaggedLocat on(), b.getGeoTaggedLocat on())
        && equals(a.getEsc rb rdAnnotat ons(), b.getEsc rb rdAnnotat ons());
  }

  publ c stat c boolean reflect onApproxEquals(Tw ter ssage a, Tw ter ssage b) {
    return reflect onApproxEquals(a, b, Collect ons.emptyL st());
  }
}
