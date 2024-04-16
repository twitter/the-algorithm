package com.tw ter.search. ngester.p pel ne.ut l;

 mport java.ut l.Collect on;
 mport java.ut l.Collect ons;
 mport java.ut l.HashSet;
 mport java.ut l.L st;
 mport java.ut l.Map;
 mport java.ut l.Opt onal;
 mport java.ut l.Set;
 mport javax.annotat on.Nullable;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect. mmutableL st;
 mport com.google.common.collect.L sts;
 mport com.google.common.collect.Maps;

 mport org.apac .thr ft.TBase;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common_ nternal.analyt cs.test_user_f lter.TestUserF lter;
 mport com.tw ter.common_ nternal.text.vers on.Pengu nVers on;
 mport com.tw ter. tastore.cl ent_v2. tastoreCl ent;
 mport com.tw ter. tastore.data. tastoreColumn;
 mport com.tw ter. tastore.data. tastoreExcept on;
 mport com.tw ter. tastore.data. tastoreRow;
 mport com.tw ter. tastore.data. tastoreValue;
 mport com.tw ter.search.common. tr cs.RelevanceStats;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common. tr cs.SearchRateCounter;
 mport com.tw ter.search.common. tr cs.SearchRequestStats;
 mport com.tw ter.search.common.relevance.ent  es.Tw ter ssage;
 mport com.tw ter.search.common.relevance.features.RelevanceS gnalConstants;
 mport com.tw ter.search. ngester.model. ngesterTw ter ssage;
 mport com.tw ter.serv ce. tastore.gen.ResponseCode;
 mport com.tw ter.serv ce. tastore.gen.T epCred;
 mport com.tw ter.ut l.Funct on;
 mport com.tw ter.ut l.Future;

publ c class UserPropert esManager {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(UserPropert esManager.class);

  @V s bleForTest ng
  protected stat c f nal L st< tastoreColumn<? extends TBase<?, ?>>> COLUMNS =
       mmutableL st.of( tastoreColumn.TWEEPCRED);           // conta ns t epcred value

  // sa  spam threshold that  s use  n t eyp e to spread user level spam to t ets, all t ets
  // from user w h spam score above such are marked so and removed from search results
  @V s bleForTest ng
  publ c stat c f nal double SPAM_SCORE_THRESHOLD = 4.5;

  @V s bleForTest ng
  stat c f nal SearchRequestStats MANHATTAN_METASTORE_STATS =
      SearchRequestStats.export("manhattan_ tastore_get", true);

  pr vate stat c f nal  tastoreGetColumnStats GET_TWEEP_CRED
      = new  tastoreGetColumnStats("t ep_cred");

  @V s bleForTest ng
  stat c f nal SearchRateCounter M SS NG_REPUTAT ON_COUNTER = RelevanceStats.exportRate(
      "num_m ss ng_reputat on");
  @V s bleForTest ng
  stat c f nal SearchRateCounter  NVAL D_REPUTAT ON_COUNTER = RelevanceStats.exportRate(
      "num_ nval d_reputat on");
  @V s bleForTest ng
  stat c f nal SearchRateCounter ACCEPTED_REPUTAT ON_COUNTER = RelevanceStats.exportRate(
      "num_accepted_reputat on");
  @V s bleForTest ng
  stat c f nal SearchRateCounter SK PPED_REPUTAT ON_CHECK_COUNTER = RelevanceStats.exportRate(
      "num_sk pped_reputat on_c ck_for_test_user");
  @V s bleForTest ng
  stat c f nal SearchCounter DEFAULT_REPUTAT ON_COUNTER = SearchCounter.export(
      " ssages_default_reputat on_count");
  @V s bleForTest ng
  stat c f nal SearchCounter MESSAGE_FROM_TEST_USER =
      SearchCounter.export(" ssages_from_test_user");

  // User level b s that are spread onto t ets
  pr vate stat c f nal SearchRateCounter  S_USER_NSFW_COUNTER = RelevanceStats.exportRate(
      "num_ s_nsfw");
  pr vate stat c f nal SearchRateCounter  S_USER_SPAM_COUNTER = RelevanceStats.exportRate(
      "num_ s_spam");

  // count how many t ets has "poss bly_sens  ve" set to true  n t  or g nal json  ssage
  pr vate stat c f nal SearchRateCounter  S_SENS T VE_FROM_JSON_COUNTER = RelevanceStats.exportRate(
      "num_ s_sens  ve_ n_json");

  pr vate stat c f nal SearchCounter SENS T VE_B TS_COUNTER =
      SearchCounter.export(" ssages_sens  ve_b s_set_count");

  pr vate f nal  tastoreCl ent  tastoreCl ent;
  pr vate f nal UserPropert esManager. tastoreGetColumnStats t epCredStats;

  /**
   * Stats for keep ng track of mult Get requests to  tastore for a spec f c data column.
   */
  @V s bleForTest ng stat c class  tastoreGetColumnStats {
    /**
     * No data was returned from  tastore for a spec f c user.
     */
    pr vate f nal SearchCounter notReturned;
    /**
     *  tastore returned a successful OK response.
     */
    pr vate f nal SearchCounter  tastoreSuccess;
    /**
     *  tastore returned a NOT_FOUND response for a user.
     */
    pr vate f nal SearchCounter  tastoreNotFound;
    /**
     *  tastore returned a BAD_ NPUT response for a user.
     */
    pr vate f nal SearchCounter  tastoreBad nput;
    /**
     *  tastore returned a TRANS ENT_ERROR response for a user.
     */
    pr vate f nal SearchCounter  tastoreTrans entError;
    /**
     *  tastore returned a PERMANENT_ERROR response for a user.
     */
    pr vate f nal SearchCounter  tastorePermanentError;
    /**
     *  tastore returned an unknown response code for a user.
     */
    pr vate f nal SearchCounter  tastoreUnknownResponseCode;
    /**
     * Total number of users that   asked data for  n  tastore.
     */
    pr vate f nal SearchCounter totalRequests;

    @V s bleForTest ng  tastoreGetColumnStats(Str ng columnNa ) {
      Str ng pref x = "manhattan_ tastore_get_" + columnNa ;
      notReturned = SearchCounter.export(pref x + "_response_not_returned");
       tastoreSuccess = SearchCounter.export(pref x + "_response_success");
       tastoreNotFound = SearchCounter.export(pref x + "_response_not_found");
       tastoreBad nput = SearchCounter.export(pref x + "_response_bad_ nput");
       tastoreTrans entError = SearchCounter.export(pref x + "_response_trans ent_error");
       tastorePermanentError = SearchCounter.export(pref x + "_response_permanent_error");
       tastoreUnknownResponseCode =
          SearchCounter.export(pref x + "_response_unknown_response_code");
      // Have a d st ngu shable pref x for t  total requests stat so that   can use   to get
      // a v z rate aga nst w ld-carded "pref x_response_*" stats.
      totalRequests = SearchCounter.export(pref x + "_requests");
    }

    /**
     * Tracks  tastore get column stats for an  nd v dual user's response.
     * @param responseCode t  response code rece ved from  tastore. Expected to be null  f no
     *        response ca  back at all.
     */
    pr vate vo d track tastoreResponseCode(@Nullable ResponseCode responseCode) {
      totalRequests. ncre nt();

       f (responseCode == null) {
        notReturned. ncre nt();
      } else  f (responseCode == ResponseCode.OK) {
         tastoreSuccess. ncre nt();
      } else  f (responseCode == ResponseCode.NOT_FOUND) {
         tastoreNotFound. ncre nt();
      } else  f (responseCode == ResponseCode.BAD_ NPUT) {
         tastoreBad nput. ncre nt();
      } else  f (responseCode == ResponseCode.TRANS ENT_ERROR) {
         tastoreTrans entError. ncre nt();
      } else  f (responseCode == ResponseCode.PERMANENT_ERROR) {
         tastorePermanentError. ncre nt();
      } else {
         tastoreUnknownResponseCode. ncre nt();
      }
    }

    @V s bleForTest ng long getTotalRequests() {
      return totalRequests.get();
    }

    @V s bleForTest ng long getNotReturnedCount() {
      return notReturned.get();
    }

    @V s bleForTest ng long get tastoreSuccessCount() {
      return  tastoreSuccess.get();
    }

    @V s bleForTest ng long get tastoreNotFoundCount() {
      return  tastoreNotFound.get();
    }

    @V s bleForTest ng long get tastoreBad nputCount() {
      return  tastoreBad nput.get();
    }

    @V s bleForTest ng long get tastoreTrans entErrorCount() {
      return  tastoreTrans entError.get();
    }

    @V s bleForTest ng long get tastorePermanentErrorCount() {
      return  tastorePermanentError.get();
    }

    @V s bleForTest ng long get tastoreUnknownResponseCodeCount() {
      return  tastoreUnknownResponseCode.get();
    }
  }

  /** Class that holds all user propert es from Manhattan. */
  @V s bleForTest ng
  protected stat c class ManhattanUserPropert es {
    pr vate double spamScore = 0;
    pr vate float t epcred = RelevanceS gnalConstants.UNSET_REPUTAT ON_SENT NEL;   // default

    publ c ManhattanUserPropert es setSpamScore(double newSpamScore) {
      t .spamScore = newSpamScore;
      return t ;
    }

    publ c float getT epcred() {
      return t epcred;
    }

    publ c ManhattanUserPropert es setT epcred(float newT epcred) {
      t .t epcred = newT epcred;
      return t ;
    }
  }

  publ c UserPropert esManager( tastoreCl ent  tastoreCl ent) {
    t ( tastoreCl ent, GET_TWEEP_CRED);
  }

  @V s bleForTest ng
  UserPropert esManager(
       tastoreCl ent  tastoreCl ent,
       tastoreGetColumnStats t epCredStats) {
    t . tastoreCl ent =  tastoreCl ent;
    t .t epCredStats = t epCredStats;
  }

  /**
   * Gets user propert es  nclud ng TWEEPCRED, SpamScore values/flags from  tastore for t 
   * g ven user ds.
   *
   * @param user ds t  l st of users for wh ch to get t  propert es.
   * @return mapp ng from user d to UserPropert es.  f a user's t pcred score  s not present  n t 
   *  tastore, of  f t re was a problem retr ev ng  , that user's score w ll not be set  n t 
   * returned map.
   */
  @V s bleForTest ng
  Future<Map<Long, ManhattanUserPropert es>> getManhattanUserPropert es(f nal L st<Long> user ds) {
    Precond  ons.c ckArgu nt(user ds != null);
     f ( tastoreCl ent == null || user ds. sEmpty()) {
      return Future.value(Collect ons.emptyMap());
    }

    f nal long start = System.currentT  M ll s();

    return  tastoreCl ent.mult Get(user ds, COLUMNS)
        .map(new Funct on<Map<Long,  tastoreRow>, Map<Long, ManhattanUserPropert es>>() {
          @Overr de
          publ c Map<Long, ManhattanUserPropert es> apply(Map<Long,  tastoreRow> response) {
            long latencyMs = System.currentT  M ll s() - start;
            Map<Long, ManhattanUserPropert es> resultMap =
                Maps.newHashMapW hExpectedS ze(user ds.s ze());

            for (Long user d : user ds) {
               tastoreRow row = response.get(user d);
              processT epCredColumn(user d, row, resultMap);
            }

            MANHATTAN_METASTORE_STATS.requestComplete(latencyMs, resultMap.s ze(), true);
            return resultMap;
          }
        })
        .handle(new Funct on<Throwable, Map<Long, ManhattanUserPropert es>>() {
          @Overr de
          publ c Map<Long, ManhattanUserPropert es> apply(Throwable t) {
            long latencyMs = System.currentT  M ll s() - start;
            LOG.error("Except on talk ng to  tastore after " + latencyMs + " ms.", t);

            MANHATTAN_METASTORE_STATS.requestComplete(latencyMs, 0, false);
            return Collect ons.emptyMap();
          }
        });
  }


  /**
   * Process t  T epCred column data returned from  tastore, takes T epCred, f lls  n t 
   * t  resultMap as appropr ate.
   */
  pr vate vo d processT epCredColumn(
      Long user d,
       tastoreRow  tastoreRow,
      Map<Long, ManhattanUserPropert es> resultMap) {
     tastoreValue<T epCred> t epCredValue =
         tastoreRow == null ? null :  tastoreRow.getValue( tastoreColumn.TWEEPCRED);
    ResponseCode responseCode = t epCredValue == null ? null : t epCredValue.getResponseCode();
    t epCredStats.track tastoreResponseCode(responseCode);

     f (responseCode == ResponseCode.OK) {
      try {
        T epCred t epCred = t epCredValue.getValue();
         f (t epCred != null && t epCred. sSetScore()) {
          ManhattanUserPropert es manhattanUserPropert es =
              getOrCreateManhattanUserPropert es(user d, resultMap);
          manhattanUserPropert es.setT epcred(t epCred.getScore());
        }
      } catch ( tastoreExcept on e) {
        // guaranteed not to be thrown  f ResponseCode.OK
        LOG.warn("Unexpected  tastoreExcept on pars ng user nfo column!", e);
      }
    }
  }

  pr vate stat c ManhattanUserPropert es getOrCreateManhattanUserPropert es(
      Long user d, Map<Long, ManhattanUserPropert es> resultMap) {

    ManhattanUserPropert es manhattanUserPropert es = resultMap.get(user d);
     f (manhattanUserPropert es == null) {
      manhattanUserPropert es = new ManhattanUserPropert es();
      resultMap.put(user d, manhattanUserPropert es);
    }

    return manhattanUserPropert es;
  }

  /**
   * Populates t  user propert es from t  g ven batch.
   */
  publ c  Future<Collect on< ngesterTw ter ssage>> populateUserPropert es(
      Collect on< ngesterTw ter ssage> batch) {
    Set<Long> user ds = new HashSet<>();
    for ( ngesterTw ter ssage  ssage : batch) {
       f (( ssage.getUserReputat on() ==  ngesterTw ter ssage.DOUBLE_F ELD_NOT_PRESENT)
          && ! ssage. sDeleted()) {
        Opt onal<Long> user d =  ssage.getFromUserTw ter d();
         f (user d. sPresent()) {
          user ds.add(user d.get());
        } else {
          LOG.error("No user  d present for t et {}",  ssage.get d());
        }
      }
    }
    L st<Long> un q ds = L sts.newArrayL st(user ds);
    Collect ons.sort(un q ds);   // for test ng pred ctab l y

    Future<Map<Long, ManhattanUserPropert es>> manhattanUserPropert esMap =
        getManhattanUserPropert es(un q ds);

    return manhattanUserPropert esMap.map(Funct on.func(map -> {
      for ( ngesterTw ter ssage  ssage : batch) {
         f ((( ssage.getUserReputat on() !=  ngesterTw ter ssage.DOUBLE_F ELD_NOT_PRESENT)
            && RelevanceS gnalConstants. sVal dUserReputat on(
            ( nt) Math.floor( ssage.getUserReputat on())))
            ||  ssage. sDeleted()) {
          cont nue;
        }
        Opt onal<Long> opt onalUser d =  ssage.getFromUserTw ter d();
         f (opt onalUser d. sPresent()) {
          long user d = opt onalUser d.get();
          ManhattanUserPropert es manhattanUserPropert es =  map.get(user d);

          f nal boolean  sTestUser = TestUserF lter. sTestUser d(user d);
           f ( sTestUser) {
            MESSAGE_FROM_TEST_USER. ncre nt();
          }

          // legacy sett ng of t epcred
          setT epCred( sTestUser, manhattanUserPropert es,  ssage);

          // set add  onal f elds
           f (setSens  veB s(manhattanUserPropert es,  ssage)) {
            SENS T VE_B TS_COUNTER. ncre nt();
          }
        }
      }
      return batch;
    }));
  }

  // good old t epcred
  pr vate vo d setT epCred(
      boolean  sTestUser,
      ManhattanUserPropert es manhattanUserPropert es,
      Tw ter ssage  ssage) {
    float score = RelevanceS gnalConstants.UNSET_REPUTAT ON_SENT NEL;
     f (manhattanUserPropert es == null) {
       f ( sTestUser) {
        SK PPED_REPUTAT ON_CHECK_COUNTER. ncre nt();
      } else {
        M SS NG_REPUTAT ON_COUNTER. ncre nt();
        DEFAULT_REPUTAT ON_COUNTER. ncre nt();
      }
    } else  f (!RelevanceS gnalConstants. sVal dUserReputat on(
        ( nt) Math.floor(manhattanUserPropert es.t epcred))) {
       f (! sTestUser) {
         NVAL D_REPUTAT ON_COUNTER. ncre nt();
        DEFAULT_REPUTAT ON_COUNTER. ncre nt();
      }
    } else {
      score = manhattanUserPropert es.t epcred;
      ACCEPTED_REPUTAT ON_COUNTER. ncre nt();
    }
     ssage.setUserReputat on(score);
  }

  // Sets sens  ve content, nsfw, and spam flags  n Tw ter ssage, furt r
  // sets t  follow ng b s  n encoded features:
  // Earlyb rdFeatureConf gurat on. S_SENS T VE_FLAG
  // Earlyb rdFeatureConf gurat on. S_USER_NSFW_FLAG
  // Earlyb rdFeatureConf gurat on. S_USER_SPAM_FLAG
  pr vate boolean setSens  veB s(
      ManhattanUserPropert es manhattanUserPropert es,
      Tw ter ssage  ssage) {
     f (manhattanUserPropert es == null) {
      return false;
    }

    f nal boolean  sUserSpam = manhattanUserPropert es.spamScore > SPAM_SCORE_THRESHOLD;
    // SEARCH-17413: Compute t  f eld w h g zmoduck data.
    f nal boolean  sUserNSFW = false;
    f nal boolean anySens  veB Set =  sUserSpam ||  sUserNSFW;

     f ( ssage. sSens  veContent()) {
      // or g nal json has poss bly_sens  ve = true, count  
       S_SENS T VE_FROM_JSON_COUNTER. ncre nt();
    }

     f ( sUserNSFW) {
      // set Earlyb rdFeatureConf gurat on. S_USER_NSFW_FLAG
      for (Pengu nVers on pengu nVers on :  ssage.getSupportedPengu nVers ons()) {
         ssage.getT etUserFeatures(pengu nVers on).setNsfw( sUserNSFW);
      }
       S_USER_NSFW_COUNTER. ncre nt();
    }
     f ( sUserSpam) {
      // set Earlyb rdFeatureConf gurat on. S_USER_SPAM_FLAG
      for (Pengu nVers on pengu nVers on :  ssage.getSupportedPengu nVers ons()) {
         ssage.getT etUserFeatures(pengu nVers on).setSpam( sUserSpam);
      }
       S_USER_SPAM_COUNTER. ncre nt();
    }

    //  f any of t  sens  ve b s are set,   return true
    return anySens  veB Set;
  }
}
