package com.tw ter.search.common.relevance.ent  es;

 mport java.text.Normal zer;
 mport java.ut l.Map;
 mport java.ut l.Nav gableMap;
 mport java.ut l.Set;
 mport java.ut l.TreeMap;
 mport java.ut l.concurrent.ConcurrentMap;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect.Maps;

 mport org.apac .commons.lang.Str ngUt ls;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common.text.transfor r.HTMLTagRemovalTransfor r;
 mport com.tw ter.common_ nternal.text.extractor.Emoj Extractor;
 mport com.tw ter.search.common. tr cs.SearchRateCounter;
 mport com.tw ter.search.common.part  on ng.snowflakeparser.Snowflake dParser;

publ c f nal class Tw ter ssageUt l {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Tw ter ssageUt l.class);

  pr vate Tw ter ssageUt l() {
  }

  @V s bleForTest ng
  stat c f nal ConcurrentMap<F eld, Counters> COUNTERS_MAP = Maps.newConcurrentMap();
  //   truncate t  locat on str ng because   used to use a  SQL table to store t  geocod ng
  //  nformat on.   n t   SQL table, t  locat on str ng was f x w dth of 30 characters.
  //   have m grated to Manhattan and t  locat on str ng  s no longer l m ed to 30 character.
  // Ho ver,  n order to correctly lookup locat on geocode from Manhattan,   st ll need to
  // truncate t  locat on just l ke   d d before.
  pr vate stat c f nal  nt MAX_LOCAT ON_LEN = 30;

  // Note:   str p tags to  ndex s ce, as typ cally s ce conta ns <a href=...> tags.
  // So t  s   get a s ce w re str pp ng fa ls, as t  URL  n t  tag was
  // excess vely long.    drop t se s ces, as t re  s l tle reason to  ndex t m.
  pr vate stat c f nal  nt MAX_SOURCE_LEN = 64;

  pr vate stat c HTMLTagRemovalTransfor r tagRemovalTransfor r = new HTMLTagRemovalTransfor r();

  pr vate stat c f nal Str ng STAT_PREF X = "tw ter_ ssage_";

  publ c enum F eld {
    FROM_USER_D SPLAY_NAME,
    NORMAL ZED_LOCAT ON,
    OR G_LOCAT ON,
    OR G_SOURCE,
    SHARED_USER_D SPLAY_NAME,
    SOURCE,
    TEXT,
    TO_USER_SCREEN_NAME;

    publ c Str ng getNa ForStats() {
      return na ().toLo rCase();
    }
  }

  @V s bleForTest ng
  stat c class Counters {
    pr vate f nal SearchRateCounter truncatedCounter;
    pr vate f nal SearchRateCounter t etsW hStr ppedSupple ntaryCharsCounter;
    pr vate f nal SearchRateCounter str ppedSupple ntaryCharsCounter;
    pr vate f nal SearchRateCounter nonStr ppedEmoj CharsCounter;
    pr vate f nal SearchRateCounter emoj sAtTruncateBoundaryCounter;

    Counters(F eld f eld) {
      Str ng f eldNa ForStats = f eld.getNa ForStats();
      truncatedCounter = SearchRateCounter.export(
          STAT_PREF X + "truncated_" + f eldNa ForStats);
      t etsW hStr ppedSupple ntaryCharsCounter = SearchRateCounter.export(
          STAT_PREF X + "t ets_w h_str pped_supple ntary_chars_" + f eldNa ForStats);
      str ppedSupple ntaryCharsCounter = SearchRateCounter.export(
          STAT_PREF X + "str pped_supple ntary_chars_" + f eldNa ForStats);
      nonStr ppedEmoj CharsCounter = SearchRateCounter.export(
          STAT_PREF X + "non_str pped_emoj _chars_" + f eldNa ForStats);
      emoj sAtTruncateBoundaryCounter = SearchRateCounter.export(
          STAT_PREF X + "emoj s_at_truncate_boundary_" + f eldNa ForStats);
    }

    SearchRateCounter getTruncatedCounter() {
      return truncatedCounter;
    }

    SearchRateCounter getT etsW hStr ppedSupple ntaryCharsCounter() {
      return t etsW hStr ppedSupple ntaryCharsCounter;
    }

    SearchRateCounter getStr ppedSupple ntaryCharsCounter() {
      return str ppedSupple ntaryCharsCounter;
    }

    SearchRateCounter getNonStr ppedEmoj CharsCounter() {
      return nonStr ppedEmoj CharsCounter;
    }

    SearchRateCounter getEmoj sAtTruncateBoundaryCounter() {
      return emoj sAtTruncateBoundaryCounter;
    }
  }

  stat c {
    for (F eld f eld : F eld.values()) {
      COUNTERS_MAP.put(f eld, new Counters(f eld));
    }
  }

  // Note: t  monora l enforces a l m  of 15 characters for screen na s,
  // but so  users w h up to 20 character na s  re grandfat red- n.  To allow
  // those users to be searchable, support up to 20 chars.
  pr vate stat c f nal  nt MAX_SCREEN_NAME_LEN = 20;

  // Note:   expect t  current l m  to be 10K. Also, all supple ntary un code characters (w h
  // t  except on of emoj s, maybe) w ll be removed and not counted as total length. Added alert
  // for text truncat on rate as  ll. SEARCH-9512
  pr vate stat c f nal  nt MAX_TWEET_TEXT_LEN = 10000;

  @V s bleForTest ng
  stat c f nal SearchRateCounter F LTERED_NO_STATUS_ D =
      SearchRateCounter.export(STAT_PREF X + "f ltered_no_status_ d");
  @V s bleForTest ng
  stat c f nal SearchRateCounter F LTERED_NO_FROM_USER =
      SearchRateCounter.export(STAT_PREF X + "f ltered_no_from_user");
  @V s bleForTest ng
  stat c f nal SearchRateCounter F LTERED_LONG_SCREEN_NAME =
      SearchRateCounter.export(STAT_PREF X + "f ltered_long_screen_na ");
  @V s bleForTest ng
  stat c f nal SearchRateCounter F LTERED_NO_TEXT =
      SearchRateCounter.export(STAT_PREF X + "f ltered_no_text");
  @V s bleForTest ng
  stat c f nal SearchRateCounter F LTERED_NO_DATE =
      SearchRateCounter.export(STAT_PREF X + "f ltered_no_date");
  @V s bleForTest ng
  stat c f nal SearchRateCounter NULLCAST_TWEET =
      SearchRateCounter.export(STAT_PREF X + "f lter_nullcast_t et");
  @V s bleForTest ng
  stat c f nal SearchRateCounter NULLCAST_TWEET_ACCEPTED =
      SearchRateCounter.export(STAT_PREF X + "nullcast_t et_accepted");
  @V s bleForTest ng
  stat c f nal SearchRateCounter  NCONS STENT_TWEET_ D_AND_CREATED_AT =
      SearchRateCounter.export(STAT_PREF X + " ncons stent_t et_ d_and_created_at_ms");

  /** Str ps t  g ven s ce from t   ssage w h t  g ven  D. */
  pr vate stat c Str ng str pS ce(Str ng s ce, Long  ssage d) {
     f (s ce == null) {
      return null;
    }
    // Always str p emoj s from s ces: t y don't really make sense  n t  f eld.
    Str ng str ppedS ce = str pSupple ntaryChars(
        tagRemovalTransfor r.transform(s ce).toStr ng(), F eld.SOURCE, true);
     f (str ppedS ce.length() > MAX_SOURCE_LEN) {
      LOG.warn(" ssage "
          +  ssage d
          + " conta ns str pped s ce that exceeds MAX_SOURCE_LEN. Remov ng: "
          + str ppedS ce);
      COUNTERS_MAP.get(F eld.SOURCE).getTruncatedCounter(). ncre nt();
      return null;
    }
    return str ppedS ce;
  }

  /**
   * Str ps and truncates t  locat on of t   ssage w h t  g ven  D.
   *
   */
  pr vate stat c Str ng str pAndTruncateLocat on(Str ng locat on) {
    // Always str p emoj s from locat ons: t y don't really make sense  n t  f eld.
    Str ng str ppedLocat on = str pSupple ntaryChars(locat on, F eld.NORMAL ZED_LOCAT ON, true);
    return truncateStr ng(str ppedLocat on, MAX_LOCAT ON_LEN, F eld.NORMAL ZED_LOCAT ON, true);
  }

  /**
   * Sets t  or gS ce and str ppedS ce f elds on a Tw ter ssage
   *
   */
  publ c stat c vo d setS ceOn ssage(Tw ter ssage  ssage, Str ng mod f edDev ceS ce) {
    // Always str p emoj s from s ces: t y don't really make sense  n t  f eld.
     ssage.setOr gS ce(str pSupple ntaryChars(mod f edDev ceS ce, F eld.OR G_SOURCE, true));
     ssage.setStr ppedS ce(str pS ce(mod f edDev ceS ce,  ssage.get d()));
  }

  /**
   * Sets t  or gLocat on to t  str pped locat on, and sets
   * t  truncatedNormal zedLocat on to t  truncated and normal zed locat on.
   */
  publ c stat c vo d setAndTruncateLocat onOn ssage(
      Tw ter ssage  ssage,
      Str ng newOr gLocat on) {
    // Always str p emoj s from locat ons: t y don't really make sense  n t  f eld.
     ssage.setOr gLocat on(str pSupple ntaryChars(newOr gLocat on, F eld.OR G_LOCAT ON, true));

    // Locat ons  n t  new locat ons table requ re add  onal normal zat on.   can also change
    // t  length of t  str ng, so   must do t  before truncat on.
     f (newOr gLocat on != null) {
      Str ng normal zed =
          Normal zer.normal ze(newOr gLocat on, Normal zer.Form.NFKC).toLo rCase().tr m();
       ssage.setTruncatedNormal zedLocat on(str pAndTruncateLocat on(normal zed));
    } else {
       ssage.setTruncatedNormal zedLocat on(null);
    }
  }

  /**
   * Val dates t  g ven Tw ter ssage.
   *
   * @param  ssage T   ssage to val date.
   * @param str pEmoj sForF elds T  set of f elds for wh ch emoj s should be str pped.
   * @param acceptNullcast ssage Determ nes  f t   ssage should be accepted,  f  's a nullcast
   *                               ssage.
   * @return {@code true}  f t  g ven  ssage  s val d; {@code false} ot rw se.
   */
  publ c stat c boolean val dateTw ter ssage(
      Tw ter ssage  ssage,
      Set<F eld> str pEmoj sForF elds,
      boolean acceptNullcast ssage) {
     f ( ssage.getNullcast()) {
      NULLCAST_TWEET. ncre nt();
       f (!acceptNullcast ssage) {
        LOG. nfo("Dropp ng nullcasted  ssage " +  ssage.get d());
        return false;
      }
      NULLCAST_TWEET_ACCEPTED. ncre nt();
    }

     f (! ssage.getFromUserScreenNa (). sPresent()
        || Str ngUt ls. sBlank( ssage.getFromUserScreenNa ().get())) {
      LOG.error(" ssage " +  ssage.get d() + " conta ns no from user. Sk pp ng.");
      F LTERED_NO_FROM_USER. ncre nt();
      return false;
    }
    Str ng fromUserScreenNa  =  ssage.getFromUserScreenNa ().get();

     f (fromUserScreenNa .length() > MAX_SCREEN_NAME_LEN) {
      LOG.warn(" ssage " +  ssage.get d() + " has a user screen na  longer than "
               + MAX_SCREEN_NAME_LEN + " characters: " +  ssage.getFromUserScreenNa ()
               + ". Sk pp ng.");
      F LTERED_LONG_SCREEN_NAME. ncre nt();
      return false;
    }

    // Remove supple ntary characters and truncate t se text f elds.
     f ( ssage.getFromUserD splayNa (). sPresent()) {
       ssage.setFromUserD splayNa (str pSupple ntaryChars(
           ssage.getFromUserD splayNa ().get(),
          F eld.FROM_USER_D SPLAY_NAME,
          str pEmoj sForF elds.conta ns(F eld.FROM_USER_D SPLAY_NAME)));
    }
     f ( ssage.getToUserScreenNa (). sPresent()) {
      Str ng str ppedToUserScreenNa  = str pSupple ntaryChars(
           ssage.getToUserLo rcasedScreenNa ().get(),
          F eld.TO_USER_SCREEN_NAME,
          str pEmoj sForF elds.conta ns(F eld.TO_USER_SCREEN_NAME));
       ssage.setToUserScreenNa (
          truncateStr ng(
              str ppedToUserScreenNa ,
              MAX_SCREEN_NAME_LEN,
              F eld.TO_USER_SCREEN_NAME,
              str pEmoj sForF elds.conta ns(F eld.TO_USER_SCREEN_NAME)));
    }

    Str ng str ppedText = str pSupple ntaryChars(
         ssage.getText(),
        F eld.TEXT,
        str pEmoj sForF elds.conta ns(F eld.TEXT));
     ssage.setText(truncateStr ng(
        str ppedText,
        MAX_TWEET_TEXT_LEN,
        F eld.TEXT,
        str pEmoj sForF elds.conta ns(F eld.TEXT)));

     f (Str ngUt ls. sBlank( ssage.getText())) {
      F LTERED_NO_TEXT. ncre nt();
      return false;
    }

     f ( ssage.getDate() == null) {
      LOG.error(" ssage " +  ssage.get d() + " conta ns no date. Sk pp ng.");
      F LTERED_NO_DATE. ncre nt();
      return false;
    }

     f ( ssage. sRet et()) {
      return val dateRet et ssage( ssage.getRet et ssage(), str pEmoj sForF elds);
    }

    // Track  f both t  snowflake  D and created at t  stamp are cons stent.
     f (!Snowflake dParser. sT et DAndCreatedAtCons stent( ssage.get d(),  ssage.getDate())) {
      LOG.error("Found  ncons stent t et  D and created at t  stamp: [ ssage D="
                +  ssage.get d() + "], [ ssageDate=" +  ssage.getDate() + "].");
       NCONS STENT_TWEET_ D_AND_CREATED_AT. ncre nt();
    }

    return true;
  }

  pr vate stat c boolean val dateRet et ssage(
      Tw terRet et ssage  ssage, Set<F eld> str pEmoj sForF elds) {
     f ( ssage.getShared d() == null ||  ssage.getRet et d() == null) {
      LOG.error("Ret et  ssage conta ns a null tw ter  d. Sk pp ng.");
      F LTERED_NO_STATUS_ D. ncre nt();
      return false;
    }

     f ( ssage.getSharedDate() == null) {
      LOG.error("Ret et  ssage " +  ssage.getRet et d() + " conta ns no date. Sk pp ng.");
      return false;
    }

    // Remove supple ntary characters from t se text f elds.
     ssage.setSharedUserD splayNa (str pSupple ntaryChars(
         ssage.getSharedUserD splayNa (),
        F eld.SHARED_USER_D SPLAY_NAME,
        str pEmoj sForF elds.conta ns(F eld.SHARED_USER_D SPLAY_NAME)));

    return true;
  }

  /**
   * Str ps non  ndexable chars from t  text.
   *
   * Returns t  result ng str ng, wh ch may be t  sa  object as t  text argu nt w n
   * no str pp ng or truncat on  s necessary.
   *
   * Non- ndexed characters are "supple ntary un code" that are not emoj s. Note that
   * supple ntary un code are st ll characters that seem worth  ndex ng, as many characters
   *  n CJK languages are supple ntary. Ho ver t  would make t  s ze of    ndex
   * explode (~186k supple ntary characters ex st), so  's not feas ble.
   *
   * @param text T  text to str p
   * @param f eld T  f eld t  text  s from
   * @param str pSupple ntaryEmoj s W t r or not to str p supple ntary emoj s. Note that t 
   * para ter na   sn't 100% accurate. T  para ter  s  ant to repl cate behav or pr or to
   * add ng support for *not* str pp ng supple ntary emoj s. T  pr or behav or would turn an
   * emoj  such as a keycap "1\uFE0F\u20E3" (http://www. emoj .com/v ew/emoj /295/symbols/keycap-1)
   *  nto just '1'. So t  keycap emoj   s not completely str pped, only t  port on after t  '1'.
   *
   */
  @V s bleForTest ng
  publ c stat c Str ng str pSupple ntaryChars(
      Str ng text,
      F eld f eld,
      boolean str pSupple ntaryEmoj s) {
     f (text == null || text. sEmpty()) {
      return text;
    }

    //  n  al ze an empty map so that  f   choose not to str p emoj s,
    // t n no emoj pos  ons w ll be found and   don't need a null
    // c ck before c ck ng  f an emoj   s at a certa n spot.
    Nav gableMap< nteger,  nteger> emoj Pos  ons = new TreeMap<>();

     f (!str pSupple ntaryEmoj s) {
      emoj Pos  ons = Emoj Extractor.getEmoj Pos  ons(text);
    }

    Str ngBu lder str ppedTextBu lder = new Str ngBu lder();
     nt sequenceStart = 0;
     nt   = 0;
    wh le (  < text.length()) {
       f (Character. sSupple ntaryCodePo nt(text.codePo ntAt( ))) {
        // C ck  f t  supple ntary character  s an emoj 
         f (!emoj Pos  ons.conta nsKey( )) {
          //  's not an emoj , or   want to str p emoj s, so str p  

          // text[ ] and text[  + 1] are part of a supple ntary code po nt.
          str ppedTextBu lder.append(text.substr ng(sequenceStart,  ));
          sequenceStart =   + 2;  // sk p 2 chars
            = sequenceStart;
          COUNTERS_MAP.get(f eld).getStr ppedSupple ntaryCharsCounter(). ncre nt();
        } else {
          //  's an emoj , keep  
            += emoj Pos  ons.get( );
          COUNTERS_MAP.get(f eld).getNonStr ppedEmoj CharsCounter(). ncre nt();
        }
      } else {
        ++ ;
      }
    }
     f (sequenceStart < text.length()) {
      str ppedTextBu lder.append(text.substr ng(sequenceStart));
    }

    Str ng str ppedText = str ppedTextBu lder.toStr ng();
     f (str ppedText.length() < text.length()) {
      COUNTERS_MAP.get(f eld).getT etsW hStr ppedSupple ntaryCharsCounter(). ncre nt();
    }
    return str ppedText;
  }

  /**
   * Truncates t  g ven str ng to t  g ven length.
   *
   * Note that   are truncat ng based on t  # of UTF-16 characters a g ven emoj  takes up.
   * So  f a s ngle emoj  takes up 4 UTF-16 characters, that counts as 4 for t  truncat on,
   * not just 1.
   *
   * @param text T  text to truncate
   * @param maxLength T  max mum length of t  str ng after truncat on
   * @param f eld T  f eld from wh ch t  str ng ca s
   * @param spl Emoj sAtMaxLength  f true, don't worry about emoj s and just truncate at maxLength,
   * potent ally spl t ng t m.  f false, truncate before t  emoj   f truncat ng at maxLength
   * would cause t  emoj  to be spl .
   */
  @V s bleForTest ng
  stat c Str ng truncateStr ng(
      Str ng text,
       nt maxLength,
      F eld f eld,
      boolean spl Emoj sAtMaxLength) {
    Precond  ons.c ckArgu nt(maxLength > 0);

     f ((text == null) || (text.length() <= maxLength)) {
      return text;
    }

     nt truncatePo nt = maxLength;
    Nav gableMap< nteger,  nteger> emoj Pos  ons;
    //  f   want to cons der emoj s   should not str p on an emoj  boundary.
     f (!spl Emoj sAtMaxLength) {
      emoj Pos  ons = Emoj Extractor.getEmoj Pos  ons(text);

      // Get t  last emoj  before maxlength.
      Map.Entry< nteger,  nteger> lastEmoj BeforeMaxLengthEntry =
          emoj Pos  ons.lo rEntry(maxLength);

       f (lastEmoj BeforeMaxLengthEntry != null) {
         nt lo rEmoj End = lastEmoj BeforeMaxLengthEntry.getKey()
            + lastEmoj BeforeMaxLengthEntry.getValue();

        //  f t  last emoj  would be truncated, truncate before t  last emoj .
         f (lo rEmoj End > truncatePo nt) {
          truncatePo nt = lastEmoj BeforeMaxLengthEntry.getKey();
          COUNTERS_MAP.get(f eld).getEmoj sAtTruncateBoundaryCounter(). ncre nt();
        }
      }
    }

    COUNTERS_MAP.get(f eld).getTruncatedCounter(). ncre nt();
    return text.substr ng(0, truncatePo nt);
  }
}
