package com.tw ter.search.earlyb rd.part  on;

 mport java.sql.T  stamp;
 mport java.text.DateFormat;
 mport java.text.S mpleDateFormat;
 mport java.t  .Durat on;
 mport java.ut l.Date;
 mport java.ut l.Opt onal;

 mport com.google.common.annotat ons.V s bleForTest ng;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.search.common. tr cs.SearchT  r;
 mport com.tw ter.search.earlyb rd.Earlyb rdStatus;
 mport com.tw ter.search.earlyb rd.common.NonPag ngAssert;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdConf g;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdProperty;
 mport com.tw ter.search.earlyb rd.common.userupdates.UserScrubGeoMap;
 mport com.tw ter.search.earlyb rd.common.userupdates.UserTableBu lderFromSnapshot;
 mport com.tw ter.search.earlyb rd.common.userupdates.UserTable;
 mport com.tw ter.search.earlyb rd.factory.Earlyb rd ndexConf gUt l;

/**
 *  ndexer class respons ble for gett ng t  t  {@l nk UserTable} and {@l nk UserScrubGeoMap}
 *  ndexed up unt l t  current mo nt.
 */
publ c class StartupUserEvent ndexer {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(StartupUserEvent ndexer.class);
  pr vate stat c f nal Str ng LOAD_USER_UPDATE_SNAPSHOT =
      "load ng user update snapshot";
  pr vate stat c f nal Str ng  NDEX_ALL_USER_EVENTS =
      " ndex ng all user events";
  pr vate stat c f nal NonPag ngAssert FA LED_USER_TABLE_HDFS_LOAD
      = new NonPag ngAssert("fa led_user_table_hdfs_load");

  pr vate stat c f nal long MAX_RETRY_M LL S_FOR_SEEK_TO_T MESTAMP =
      Durat on.ofM nutes(1).toM ll s();
  pr vate stat c f nal long SLEEP_M LL S_BETWEEN_RETR ES_FOR_SEEK_TO_T MESTAMP =
      Durat on.ofSeconds(1).toM ll s();

  pr vate stat c f nal long M LL S_ N_FOURTEEN_DAYS = 1209600000;
  pr vate stat c f nal long M LL S_ N_ONE_DAY = 86400000;

  pr vate f nal Search ndex ng tr cSet search ndex ng tr cSet;
  pr vate f nal UserUpdatesStream ndexer userUpdatesStream ndexer;
  pr vate f nal UserScrubGeoEventStream ndexer userScrubGeoEventStream ndexer;
  pr vate f nal Seg ntManager seg ntManager;
  pr vate f nal Clock clock;

  publ c StartupUserEvent ndexer(
      Search ndex ng tr cSet search ndex ng tr cSet,
      UserUpdatesStream ndexer userUpdatesStream ndexer,
      UserScrubGeoEventStream ndexer userScrubGeoEventStream ndexer,
      Seg ntManager seg ntManager,
      Clock clock) {
    t .search ndex ng tr cSet = search ndex ng tr cSet;
    t .userUpdatesStream ndexer = userUpdatesStream ndexer;
    t .userScrubGeoEventStream ndexer = userScrubGeoEventStream ndexer;
    t .seg ntManager = seg ntManager;
    t .clock = clock;
  }

  /**
   *  ndex all user events.
   */
  publ c vo d  ndexAllEvents() {
    Earlyb rdStatus.beg nEvent(
         NDEX_ALL_USER_EVENTS, search ndex ng tr cSet.startup nUserEvent ndexer);

     ndexUserUpdates();
     f (Earlyb rdConf g.consu UserScrubGeoEvents()) {
       ndexUserScrubGeoEvents();
    }

    Earlyb rdStatus.endEvent(
         NDEX_ALL_USER_EVENTS, search ndex ng tr cSet.startup nUserEvent ndexer);
  }

  /**
   *  ndex user updates unt l current.
   */
  publ c vo d  ndexUserUpdates() {
    Earlyb rdStatus.beg nEvent(
        LOAD_USER_UPDATE_SNAPSHOT, search ndex ng tr cSet.startup nUserUpdates);

    Opt onal<UserTable> userTable = bu ldUserTable();
     f (userTable. sPresent()) {
      seg ntManager.getUserTable().setTable(userTable.get());
      LOG. nfo("Set new user table.");

       f (!seekToT  stampW hRetr es fNecessary(
          userTable.get().getLastRecordT  stamp(),
          userUpdatesStream ndexer)) {
        LOG.error("User Updates stream  ndexer unable to seek to t  stamp. "
            + "W ll seek to beg nn ng.");
        userUpdatesStream ndexer.seekToBeg nn ng();
      }
    } else {
      LOG. nfo("Fa led to load user update snapshot. W ll re ndex user updates from scratch.");
      FA LED_USER_TABLE_HDFS_LOAD.assertFa led();
      userUpdatesStream ndexer.seekToBeg nn ng();
    }

    userUpdatesStream ndexer.readRecordsUnt lCurrent();
    LOG. nfo("F n s d catch ng up on user updates v a Kafka");

    Earlyb rdStatus.endEvent(
        LOAD_USER_UPDATE_SNAPSHOT, search ndex ng tr cSet.startup nUserUpdates);
  }

  /**
   *  ndex UserScrubGeoEvents unt l current.
   */
  publ c vo d  ndexUserScrubGeoEvents() {
    seekUserScrubGeoEventKafkaConsu r();

    SearchT  r t  r = new SearchT  r();
    t  r.start();
    userScrubGeoEventStream ndexer.readRecordsUnt lCurrent();
    t  r.stop();

    LOG. nfo("F n s d catch ng up on user scrub geo events v a Kafka");
    LOG. nfo("UserScrubGeoMap conta ns {} users and f n s d  n {} m ll seconds",
        seg ntManager.getUserScrubGeoMap().getNumUsers nMap(), t  r.getElapsed());
  }

  /**
   * Seeks UserScrubGeoEventKafkaConsu r us ng t  stamp der ved from
   * getT  stampForUserScrubGeoEventKafkaConsu r().
   */
  @V s bleForTest ng
  publ c vo d seekUserScrubGeoEventKafkaConsu r() {
    long seekT  stamp = getT  stampForUserScrubGeoEventKafkaConsu r();
     f (seekT  stamp == -1) {
      userScrubGeoEventStream ndexer.seekToBeg nn ng();
    } else {
       f (!seekToT  stampW hRetr es fNecessary(seekT  stamp, userScrubGeoEventStream ndexer)) {
        LOG.error("User Scrub Geo stream  ndexer unable to seek to t  stamp. "
            + "W ll seek to beg nn ng.");
        userScrubGeoEventStream ndexer.seekToBeg nn ng();
      }
    }
  }

  /**
   * Get t  stamp to seek UserScrubGeoEventKafkaConsu r to.
   * @return
   */
  publ c long getT  stampForUserScrubGeoEventKafkaConsu r() {
     f (Earlyb rd ndexConf gUt l. sArch veSearch()) {
      return getT  stampForArch ve();
    } else {
      return getT  stampForRealt  ();
    }
  }

  /**
   * For arch ve: grab scrub gen from conf g f le and convert date  nto a t  stamp. Add buffer of
   * one day.   need all UserScrubGeoEvents s nce t  date of t  current scrub gen.
   *
   * See go/realt  -geo-f lter ng
   * @return
   */
  publ c long getT  stampForArch ve() {
    try {
      Str ng scrubGenStr ng = Earlyb rdProperty.EARLYB RD_SCRUB_GEN.get();

      DateFormat dateFormat = new S mpleDateFormat("yyyyMMdd");
      Date date = dateFormat.parse(scrubGenStr ng);
      return new T  stamp(date.getT  ()).getT  () - M LL S_ N_ONE_DAY;

    } catch (Except on e) {
      LOG.error("Could not der ve t  stamp from scrub gen. "
          + "W ll seek User Scrub Geo Kafka consu r to beg nn ng of top c");
    }
    return -1;
  }

  /**
   * For realt  /protected: Compute t  t  stamp 14 days from t  current t  . T  w ll account
   * for all events that have occurred dur ng t  l fecylce of t  current  ndex.
   *
   * See go/realt  -geo-f lter ng
   */
  publ c long getT  stampForRealt  () {
   return System.currentT  M ll s() - M LL S_ N_FOURTEEN_DAYS;
  }

  pr vate boolean seekToT  stampW hRetr es fNecessary(
      long lastRecordT  stamp,
      S mpleStream ndexer stream ndexer) {
    long  n  alT  M ll s = clock.nowM ll s();
     nt numFa lures = 0;
    wh le (shouldTrySeekToT  stamp( n  alT  M ll s, numFa lures)) {
      try {
        stream ndexer.seekToT  stamp(lastRecordT  stamp);
        LOG. nfo("Seeked consu r to t  stamp {} after {} fa lures",
            lastRecordT  stamp, numFa lures);
        return true;
      } catch (Except on e) {
        numFa lures++;
        LOG. nfo("Caught except on w n seek ng to t  stamp. Num fa lures: {}. Except on: {}",
            numFa lures, e);
        // Sleep before attempt ng to retry
        try {
          clock.wa For(SLEEP_M LL S_BETWEEN_RETR ES_FOR_SEEK_TO_T MESTAMP);
        } catch ( nterruptedExcept on  nterruptedExcept on) {
          LOG.warn(" nterrupted wh le sleep ng bet en seekToT  stamp retr es",
               nterruptedExcept on);
          // Preserve  nterrupt status.
          Thread.currentThread(). nterrupt();
          break;
        }
      }
    }
    // Fa led to seek to t  stamp
    return false;
  }

  pr vate boolean shouldTrySeekToT  stamp(long  n  alT  M ll s,  nt numFa lures) {
     f (numFa lures == 0) {
      // no attempts have been made yet, so   should try to seek to t  stamp
      return true;
    } else {
      return clock.nowM ll s() -  n  alT  M ll s < MAX_RETRY_M LL S_FOR_SEEK_TO_T MESTAMP;
    }
  }

  protected Opt onal<UserTable> bu ldUserTable() {
    UserTableBu lderFromSnapshot bu lder = new UserTableBu lderFromSnapshot();
    return bu lder.bu ld(seg ntManager.getUserTable().getUser dF lter());
  }
}
