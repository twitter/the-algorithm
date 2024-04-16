package com.tw ter.search.earlyb rd.docu nt;

 mport java. o. OExcept on;
 mport java.ut l.concurrent.T  Un ;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect.L sts;

 mport org.apac .lucene.docu nt.Docu nt;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.dec der.Dec der;
 mport com.tw ter.search.common.dec der.Dec derUt l;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common.part  on ng.snowflakeparser.Snowflake dParser;
 mport com.tw ter.search.common.sc ma.Sc maDocu ntFactory;
 mport com.tw ter.search.common.sc ma.base.F eldNa To dMapp ng;
 mport com.tw ter.search.common.sc ma.base. mmutableSc ma nterface;
 mport com.tw ter.search.common.sc ma.base.Sc ma;
 mport com.tw ter.search.common.sc ma.base.Thr ftDocu ntUt l;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdCluster;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdThr ftDocu ntUt l;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftDocu nt;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ft ndex ngEvent;
 mport com.tw ter.search.common.ut l.text.f lter.Normal zedTokenF lter;
 mport com.tw ter.search.common.ut l.text.spl ter.Hashtag nt onPunctuat onSpl ter;
 mport com.tw ter.search.earlyb rd.except on.Cr  calExcept onHandler;
 mport com.tw ter.search.earlyb rd.part  on.Search ndex ng tr cSet;

publ c class Thr ft ndex ngEventDocu ntFactory extends Docu ntFactory<Thr ft ndex ngEvent> {
  pr vate stat c f nal Logger LOG =
      LoggerFactory.getLogger(Thr ft ndex ngEventDocu ntFactory.class);

  pr vate stat c f nal F eldNa To dMapp ng  D_MAPP NG = new Earlyb rdF eldConstants();
  pr vate stat c f nal long T MESTAMP_ALLOWED_FUTURE_DELTA_MS = T  Un .SECONDS.toM ll s(60);
  pr vate stat c f nal Str ng F LTER_TWEETS_W TH_FUTURE_TWEET_ D_AND_CREATED_AT_DEC DER_KEY =
      "f lter_t ets_w h_future_t et_ d_and_created_at";

  pr vate stat c f nal SearchCounter NUM_TWEETS_W TH_FUTURE_TWEET_ D_AND_CREATED_AT_MS =
      SearchCounter.export("num_t ets_w h_future_t et_ d_and_created_at_ms");
  pr vate stat c f nal SearchCounter NUM_TWEETS_W TH_ NCONS STENT_TWEET_ D_AND_CREATED_AT_MS_FOUND =
      SearchCounter.export("num_t ets_w h_ ncons stent_t et_ d_and_created_at_ms_found");
  pr vate stat c f nal SearchCounter
    NUM_TWEETS_W TH_ NCONS STENT_TWEET_ D_AND_CREATED_AT_MS_ADJUSTED =
      SearchCounter.export("num_t ets_w h_ ncons stent_t et_ d_and_created_at_ms_adjusted");
  pr vate stat c f nal SearchCounter NUM_TWEETS_W TH_ NCONS STENT_TWEET_ D_AND_CREATED_AT_MS_DROPPED
    = SearchCounter.export("num_t ets_w h_ ncons stent_t et_ d_and_created_at_ms_dropped");

  @V s bleForTest ng
  stat c f nal Str ng ENABLE_ADJUST_CREATED_AT_T ME_ F_M SMATCH_W TH_SNOWFLAKE =
      "enable_adjust_created_at_t  _ f_m smatch_w h_snowflake";

  @V s bleForTest ng
  stat c f nal Str ng ENABLE_DROP_CREATED_AT_T ME_ F_M SMATCH_W TH_SNOWFLAKE =
      "enable_drop_created_at_t  _ f_m smatch_w h_snowflake";

  pr vate f nal Sc maDocu ntFactory sc maDocu ntFactory;
  pr vate f nal Earlyb rdCluster cluster;
  pr vate f nal Search ndex ng tr cSet search ndex ng tr cSet;
  pr vate f nal Dec der dec der;
  pr vate f nal Sc ma sc ma;
  pr vate f nal Clock clock;

  publ c Thr ft ndex ngEventDocu ntFactory(
      Sc ma sc ma,
      Earlyb rdCluster cluster,
      Dec der dec der,
      Search ndex ng tr cSet search ndex ng tr cSet,
      Cr  calExcept onHandler cr  calExcept onHandler) {
    t (
        sc ma,
        getSc maDocu ntFactory(sc ma, cluster, dec der),
        cluster,
        search ndex ng tr cSet,
        dec der,
        Clock.SYSTEM_CLOCK,
        cr  calExcept onHandler
    );
  }

  /**
   * Returns a docu nt factory that knows how to convert Thr ftDocu nts to Docu nts based on t 
   * prov ded sc ma.
   */
  publ c stat c Sc maDocu ntFactory getSc maDocu ntFactory(
      Sc ma sc ma,
      Earlyb rdCluster cluster,
      Dec der dec der) {
    return new Sc maDocu ntFactory(sc ma,
        L sts.newArrayL st(
            new Truncat onTokenStreamWr er(cluster, dec der),
            (f eld nfo, stream) -> {
              // Str p # @ $ symbols, and break up underscore connected tokens.
               f (f eld nfo.getF eldType().useT etSpec f cNormal zat on()) {
                return new Hashtag nt onPunctuat onSpl ter(new Normal zedTokenF lter(stream));
              }

              return stream;
            }));
  }

  @V s bleForTest ng
  protected Thr ft ndex ngEventDocu ntFactory(
      Sc ma sc ma,
      Sc maDocu ntFactory sc maDocu ntFactory,
      Earlyb rdCluster cluster,
      Search ndex ng tr cSet search ndex ng tr cSet,
      Dec der dec der,
      Clock clock,
      Cr  calExcept onHandler cr  calExcept onHandler) {
    super(cr  calExcept onHandler);
    t .sc ma = sc ma;
    t .sc maDocu ntFactory = sc maDocu ntFactory;
    t .cluster = cluster;
    t .search ndex ng tr cSet = search ndex ng tr cSet;
    t .dec der = dec der;
    t .clock = clock;
  }

  @Overr de
  publ c long getStatus d(Thr ft ndex ngEvent event) {
    Precond  ons.c ckNotNull(event);
     f (event. sSetDocu nt() && event.getDocu nt() != null) {
      Thr ftDocu nt thr ftDocu nt = event.getDocu nt();
      try {
        //  deally,   should not call getSc maSnapshot()  re.  But, as t   s called only to
        // retr eve status  d and t   D f eld  s stat c, t   s f ne for t  purpose.
        thr ftDocu nt = Thr ftDocu ntPreprocessor.preprocess(
            thr ftDocu nt, cluster, sc ma.getSc maSnapshot());
      } catch ( OExcept on e) {
        throw new  llegalStateExcept on("Unable to obta n t et  D from Thr ftDocu nt", e);
      }
      return Thr ftDocu ntUt l.getLongValue(
          thr ftDocu nt, Earlyb rdF eldConstant. D_F ELD.getF eldNa (),  D_MAPP NG);
    } else {
      throw new  llegalArgu ntExcept on("Thr ftDocu nt  s null  ns de Thr ft ndex ngEvent.");
    }
  }

  @Overr de
  protected Docu nt  nnerNewDocu nt(Thr ft ndex ngEvent event) throws  OExcept on {
    Precond  ons.c ckNotNull(event);
    Precond  ons.c ckNotNull(event.getDocu nt());

     mmutableSc ma nterface sc maSnapshot = sc ma.getSc maSnapshot();

    //  f t  t et  d and create_at are  n t  future, do not  ndex  .
     f (areT et DAndCreateAt nT Future(event)
        && Dec derUt l. sAva lableForRandomRec p ent(dec der,
        F LTER_TWEETS_W TH_FUTURE_TWEET_ D_AND_CREATED_AT_DEC DER_KEY)) {
      NUM_TWEETS_W TH_FUTURE_TWEET_ D_AND_CREATED_AT_MS. ncre nt();
      return null;
    }

     f ( sNullcastB AndF lterCons stent(sc maSnapshot, event)) {
      Thr ftDocu nt thr ftDocu nt =
          adjustOrDrop fT et DAndCreatedAtAre ncons stent(
              Thr ftDocu ntPreprocessor.preprocess(event.getDocu nt(), cluster, sc maSnapshot));

       f (thr ftDocu nt != null) {
        return sc maDocu ntFactory.newDocu nt(thr ftDocu nt);
      } else {
        return null;
      }
    } else {
      return null;
    }
  }

  pr vate Thr ftDocu nt adjustOrDrop fT et DAndCreatedAtAre ncons stent(Thr ftDocu nt docu nt) {
    f nal long t et D = Earlyb rdThr ftDocu ntUt l.get D(docu nt);
    // Thr ft docu nt  s stor ng created at  n seconds.
    f nal long createdAtMs = Earlyb rdThr ftDocu ntUt l.getCreatedAtMs(docu nt);

     f (!Snowflake dParser. sT et DAndCreatedAtCons stent(t et D, createdAtMs)) {
      //  ncre nt found counter.
      NUM_TWEETS_W TH_ NCONS STENT_TWEET_ D_AND_CREATED_AT_MS_FOUND. ncre nt();
      LOG.error(
          "Found  ncons stent t et  D and created at t  stamp: [t et D={}], [createdAtMs={}]",
          t et D, createdAtMs);

       f (Dec derUt l. sAva lableForRandomRec p ent(
          dec der, ENABLE_ADJUST_CREATED_AT_T ME_ F_M SMATCH_W TH_SNOWFLAKE)) {
        // Update created at (and csf) w h t  t   stamp  n snow flake  D.
        f nal long createdAtMs n D = Snowflake dParser.getT  stampFromT et d(t et D);
        Earlyb rdThr ftDocu ntUt l.replaceCreatedAtAndCreatedAtCSF(
            docu nt, ( nt) (createdAtMs n D / 1000));

        //  ncre nt adjusted counter.
        NUM_TWEETS_W TH_ NCONS STENT_TWEET_ D_AND_CREATED_AT_MS_ADJUSTED. ncre nt();
        LOG.error(
            "Updated created at to match t et  D: createdAtMs={}, t et D={}, createdAtMs n D={}",
            createdAtMs, t et D, createdAtMs n D);
      } else  f (Dec derUt l. sAva lableForRandomRec p ent(
          dec der, ENABLE_DROP_CREATED_AT_T ME_ F_M SMATCH_W TH_SNOWFLAKE)) {
        // Drop and  ncre nt counter!
        NUM_TWEETS_W TH_ NCONS STENT_TWEET_ D_AND_CREATED_AT_MS_DROPPED. ncre nt();
        LOG.error(
            "Dropped t et w h  ncons stent  D and t  stamp: createdAtMs={}, t et D={}",
            createdAtMs, t et D);
        return null;
      }
    }

    return docu nt;
  }

  pr vate boolean  sNullcastB AndF lterCons stent(
       mmutableSc ma nterface sc maSnapshot,
      Thr ft ndex ngEvent event) {
    return Thr ftDocu ntPreprocessor. sNullcastB AndF lterCons stent(
        event.getDocu nt(), sc maSnapshot);
  }

  /**
   * C ck  f t  t et  D and create_at are  n t  future and beyond t  allo d
   * T MESTAMP_ALLOWED_FUTURE_DELTA_MS range from current t   stamp.
   */
  pr vate boolean areT et DAndCreateAt nT Future(Thr ft ndex ngEvent event) {
    Thr ftDocu nt docu nt = event.getDocu nt();

    f nal long t et D = Earlyb rdThr ftDocu ntUt l.get D(docu nt);
     f (t et D < Snowflake dParser.SNOWFLAKE_ D_LOWER_BOUND) {
      return false;
    }

    f nal long t et DT  stampMs = Snowflake dParser.getT  stampFromT et d(t et D);
    f nal long allo dFutureT  stampMs = clock.nowM ll s() + T MESTAMP_ALLOWED_FUTURE_DELTA_MS;

    f nal long createdAtMs = Earlyb rdThr ftDocu ntUt l.getCreatedAtMs(docu nt);
     f (t et DT  stampMs > allo dFutureT  stampMs && createdAtMs > allo dFutureT  stampMs) {
      LOG.error(
          "Found future t et  D and created at t  stamp: "
              + "[t et D={}], [createdAtMs={}], [compareDeltaMs={}]",
          t et D, createdAtMs, T MESTAMP_ALLOWED_FUTURE_DELTA_MS);
      return true;
    }

    return false;
  }
}
