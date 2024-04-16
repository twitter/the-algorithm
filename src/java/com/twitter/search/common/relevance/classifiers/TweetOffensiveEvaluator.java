package com.tw ter.search.common.relevance.class f ers;

 mport java. o.F le;
 mport java. o. OExcept on;
 mport java. o. nputStream;
 mport java.ut l.ArrayL st;
 mport java.ut l.L st;
 mport java.ut l.concurrent.Executors;
 mport java.ut l.concurrent.Sc duledExecutorServ ce;
 mport java.ut l.concurrent.atom c.Atom cReference;

 mport com.google.common.base.Jo ner;
 mport com.google.common.base.Precond  ons;
 mport com.google.common. o.ByteS ce;
 mport com.google.common.ut l.concurrent.ThreadFactoryBu lder;

 mport org.apac .commons. o. OUt ls;
 mport org.apac .commons.lang.Str ngUt ls;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common.text.language.LocaleUt l;
 mport com.tw ter.common.text.token.Token zedCharSequence;
 mport com.tw ter.common.text.token.attr bute.TokenType;
 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.common_ nternal.text.p pel ne.Tw terNgramGenerator;
 mport com.tw ter.common_ nternal.text.top c.Blackl stedTop cs;
 mport com.tw ter.common_ nternal.text.top c.Blackl stedTop cs.F lterMode;
 mport com.tw ter.common_ nternal.text.vers on.Pengu nVers on;
 mport com.tw ter.search.common. tr cs.RelevanceStats;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common. tr cs.SearchRateCounter;
 mport com.tw ter.search.common.relevance.ent  es.Tw ter ssage;
 mport com.tw ter.search.common.relevance.features.T etTextFeatures;
 mport com.tw ter.search.common.relevance.features.T etTextQual y;
 mport com.tw ter.search.common.ut l. o.per od c.Per od cF leLoader;
 mport com.tw ter.search.common.ut l.text.Normal zer lper;
 mport com.tw ter.search.common.ut l.text.Token zer lper;

/**
 * Determ nes  f t et text or userna  conta ns potent ally offens ve language.
 */
publ c class T etOffens veEvaluator extends T etEvaluator {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(T etOffens veEvaluator.class);

  pr vate stat c f nal  nt MAX_OFFENS VE_TERMS = 2;

  pr vate f nal F le f lterD rectory;
  pr vate stat c f nal F le DEFAULT_F LTER_D R = new F le("");
  pr vate stat c f nal Str ng ADULT_TOKEN_F LE_NAME = "adult_tokens.txt";
  pr vate stat c f nal Str ng OFFENS VE_TOP C_F LE_NAME = "offens ve_top cs.txt";
  pr vate stat c f nal Str ng OFFENS VE_SUBSTR NG_F LE_NAME = "offens ve_substr ngs.txt";

  pr vate stat c f nal ThreadLocal<Tw terNgramGenerator> NGRAM_GENERATOR_HOLDER =
      new ThreadLocal<Tw terNgramGenerator>() {
        @Overr de
        protected Tw terNgramGenerator  n  alValue() {
          //  'll generate ngrams from Token zedCharSequence, wh ch conta ns token zat on results,
          // so   doesn't matter wh ch Pengu n vers on to use  re.
          return new Tw terNgramGenerator.Bu lder(Pengu nVers on.PENGU N_6)
              .setS ze(1, MAX_OFFENS VE_TERMS)
              .bu ld();
        }
      };

  pr vate f nal Atom cReference<Blackl stedTop cs> offens veTop cs =
    new Atom cReference<>();
  pr vate f nal Atom cReference<Blackl stedTop cs> offens veUsersTop cs =
    new Atom cReference<>();

  pr vate f nal Atom cReference<ByteS ce> adultTokenF leContents = new Atom cReference<>();
  pr vate f nal Atom cReference<ByteS ce> offens veTokenF leContents = new Atom cReference<>();
  pr vate f nal Atom cReference<ByteS ce> offens veSubstr ngF leContents = new
    Atom cReference<>();

  pr vate f nal SearchCounter sens  veTextCounter =
      RelevanceStats.exportLong("num_sens  ve_text");

  publ c T etOffens veEvaluator() {
    t (DEFAULT_F LTER_D R);
  }

  publ c T etOffens veEvaluator(
    F le f lterD rectory
  ) {
    t .f lterD rectory = f lterD rectory;
    adultTokenF leContents.set(Blackl stedTop cs.getRes ce(
      Blackl stedTop cs.DATA_PREF X + ADULT_TOKEN_F LE_NAME));
    offens veTokenF leContents.set(Blackl stedTop cs.getRes ce(
      Blackl stedTop cs.DATA_PREF X + OFFENS VE_TOP C_F LE_NAME));
    offens veSubstr ngF leContents.set(Blackl stedTop cs.getRes ce(
      Blackl stedTop cs.DATA_PREF X + OFFENS VE_SUBSTR NG_F LE_NAME));

    try {
      rebu ldBlackl stedTop cs();
    } catch ( OExcept on e) {
      throw new Runt  Except on(e);
    }

    Sc duledExecutorServ ce executor = Executors.newS ngleThreadSc duledExecutor(
      new ThreadFactoryBu lder()
        .setNa Format("offens ve-evaluator-blackl st-reloader")
        .setDaemon(true)
        .bu ld());
     n Per od cF leLoader(adultTokenF leContents, ADULT_TOKEN_F LE_NAME, executor);
     n Per od cF leLoader(offens veTokenF leContents, OFFENS VE_TOP C_F LE_NAME, executor);
     n Per od cF leLoader(offens veSubstr ngF leContents, OFFENS VE_SUBSTR NG_F LE_NAME, executor);
  }

  pr vate vo d  n Per od cF leLoader(
    Atom cReference<ByteS ce> byteS ce,
    Str ng f leNa ,
    Sc duledExecutorServ ce executor) {
    F le f le = new F le(f lterD rectory, f leNa );
    try {
      Per od cF leLoader loader = new Per od cF leLoader(
        "offens ve-evaluator-" + f leNa ,
        f le.getPath(),
        executor,
        Clock.SYSTEM_CLOCK) {
        @Overr de
        protected vo d accept( nputStream stream) throws  OExcept on {
          byteS ce.set(ByteS ce.wrap( OUt ls.toByteArray(stream)));
          rebu ldBlackl stedTop cs();
        }
      };
      loader. n ();
    } catch (Except on e) {
      // Not t  end of t  world  f   couldn't load t  f le,   already loaded t  res ce.
      LOG.error("Could not load offens ve top c f lter " + f leNa  + " from Conf gBus", e);
    }
  }

  pr vate vo d rebu ldBlackl stedTop cs() throws  OExcept on {
    offens veTop cs.set(new Blackl stedTop cs.Bu lder(false)
      .loadF lterFromS ce(adultTokenF leContents.get(), F lterMode.EXACT)
      .loadF lterFromS ce(offens veSubstr ngF leContents.get(), F lterMode.SUBSTR NG)
      .bu ld());

    offens veUsersTop cs.set(new Blackl stedTop cs.Bu lder(false)
      .loadF lterFromS ce(offens veTokenF leContents.get(), F lterMode.EXACT)
      .loadF lterFromS ce(offens veSubstr ngF leContents.get(), F lterMode.SUBSTR NG)
      .bu ld());
  }

  @Overr de
  publ c vo d evaluate(f nal Tw ter ssage t et) {
    Blackl stedTop cs offens veF lter = t .offens veTop cs.get();
    Blackl stedTop cs offens veUsersF lter = t .offens veUsersTop cs.get();

     f (offens veF lter == null || offens veUsersF lter == null) {
      return;
    }

     f (t et. sSens  veContent()) {
      sens  veTextCounter. ncre nt();
    }

    // C ck for user na .
    Precond  ons.c ckState(t et.getFromUserScreenNa (). sPresent(),
        "M ss ng from-user screen na ");

    for (Pengu nVers on pengu nVers on : t et.getSupportedPengu nVers ons()) {
      T etTextQual y textQual y = t et.getT etTextQual y(pengu nVers on);

       f (t et. sSens  veContent()) {
        textQual y.addBoolQual y(T etTextQual y.BooleanQual yType.SENS T VE);
      }

      // C ck  f userna  has an offens ve term
       f ( sUserNa Offens ve(
          t et.getFromUserScreenNa ().get(), offens veUsersF lter, pengu nVers on)) {
        SearchRateCounter offens veUserCounter = RelevanceStats.exportRate(
            "num_offens ve_user_" + pengu nVers on.na ().toLo rCase());
        offens veUserCounter. ncre nt();
        textQual y.addBoolQual y(T etTextQual y.BooleanQual yType.OFFENS VE_USER);
      }

      // C ck  f t et has an offens ve term
       f ( sT etOffens ve(t et, offens veF lter, pengu nVers on)) {
        SearchRateCounter offens veTextCounter = RelevanceStats.exportRate(
            "num_offens ve_text_" + pengu nVers on.na ().toLo rCase());
        offens veTextCounter. ncre nt();
        textQual y.addBoolQual y(T etTextQual y.BooleanQual yType.OFFENS VE);
      }
    }
  }

  pr vate boolean  sUserNa Offens ve(Str ng userNa ,
                                      Blackl stedTop cs offens veUsersF lter,
                                      Pengu nVers on pengu nVers on) {
    Str ng normal zedUserNa  = Normal zer lper.normal zeKeepCase(
        userNa , LocaleUt l.UNKNOWN, pengu nVers on);
    L st<Str ng> termsToC ck = new ArrayL st(Token zer lper.getSubtokens(normal zedUserNa ));
    termsToC ck.add(normal zedUserNa .toLo rCase());

    for (Str ng userNa Token : termsToC ck) {
       f (!Str ngUt ls. sBlank(userNa Token) && offens veUsersF lter.f lter(userNa Token)) {
        return true;
      }
    }
    return false;
  }

  pr vate boolean  sT etOffens ve(f nal Tw ter ssage t et,
                                   Blackl stedTop cs offens veF lter,
                                   Pengu nVers on pengu nVers on) {
    T etTextFeatures textFeatures = t et.getT etTextFeatures(pengu nVers on);

    boolean t etHasOffens veTerm = false;

    // C ck for t et text.
    L st<Token zedCharSequence> ngrams =
        NGRAM_GENERATOR_HOLDER.get().generateNgramsAsToken zedCharSequence(
            textFeatures.getTokenSequence(), t et.getLocale());
    for (Token zedCharSequence ngram : ngrams) {
      // sk p URL ngram
       f (!ngram.getTokensOf(TokenType.URL). sEmpty()) {
        cont nue;
      }
      Str ng ngramStr = ngram.toStr ng();
       f (!Str ngUt ls. sBlank(ngramStr) && offens veF lter.f lter(ngramStr)) {
        t etHasOffens veTerm = true;
        break;
      }
    }

    // Due to so  strangeness  n Pengu n,   don't get ngrams for tokens around "\n-" or "-\n"
    //  n t  or g nal str ng, t  made us m ss so  offens ve words t  way.  re   do anot r
    // pass of c ck us ng just t  tokens generated by t  token zer. (See SEARCHQUAL-8907)
     f (!t etHasOffens veTerm) {
      for (Str ng ngramStr : textFeatures.getTokens()) {
        // sk p URLs
         f (ngramStr.startsW h("http://") || ngramStr.startsW h("https://")) {
          cont nue;
        }
         f (!Str ngUt ls. sBlank(ngramStr) && offens veF lter.f lter(ngramStr)) {
          t etHasOffens veTerm = true;
          break;
        }
      }
    }

     f (!t etHasOffens veTerm) {
      // c ck for resolved URLs
      Str ng resolvedUrlsText =
          Jo ner.on(" ").sk pNulls().jo n(textFeatures.getResolvedUrlTokens());
      L st<Str ng> ngramStrs = NGRAM_GENERATOR_HOLDER.get().generateNgramsAsStr ng(
          resolvedUrlsText, LocaleUt l.UNKNOWN);
      for (Str ng ngram : ngramStrs) {
         f (!Str ngUt ls. sBlank(ngram) && offens veF lter.f lter(ngram)) {
          t etHasOffens veTerm = true;
          break;
        }
      }
    }

    return t etHasOffens veTerm;
  }
}
