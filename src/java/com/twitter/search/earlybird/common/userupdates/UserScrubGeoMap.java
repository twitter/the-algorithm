package com.tw ter.search.earlyb rd.common.userupdates;

 mport java.ut l.concurrent.ConcurrentHashMap;
 mport java.ut l.concurrent.T  Un ;

 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common. tr cs.SearchCustomGauge;
 mport com.tw ter.search.common. tr cs.SearchT  rStats;
 mport com.tw ter.search.common.part  on ng.snowflakeparser.Snowflake dParser;
 mport com.tw ter.t etyp e.thr ftjava.UserScrubGeoEvent;

/**
 * Map of users who have act oned to delete locat on data from t  r t ets. User D's are mapped
 * to t  maxT et d that w ll eventually be scrubbed from t   ndex (user d -> maxT et d).
 *
 * ConcurrentHashMap  s thread safe w hout synchron z ng t  whole map. Reads can happen very fast
 * wh le wr es are done w h a lock. T   s  deal s nce many Earlyb rd Searc r threads could
 * be read ng from t  map at once, w reas   w ll only be add ng to t  map v a kafka.
 *
 * T  map  s c cked aga nst to f lter out t ets that should not be returned to geo quer es.
 * See: go/realt  -geo-f lter ng
 */
publ c class UserScrubGeoMap {
  // T  number of geo events that conta n a user  D already present  n t  map. T  count  s used
  // to ver fy t  number of users  n t  map aga nst t  number of events consu d from kafka.
  pr vate stat c f nal SearchCounter USER_SCRUB_GEO_EVENT_EX ST NG_USER_COUNT =
      SearchCounter.export("user_scrub_geo_event_ex st ng_user_count");
  publ c stat c f nal SearchT  rStats USER_SCRUB_GEO_EVENT_LAG_STAT =
      SearchT  rStats.export("user_scrub_geo_event_lag",
          T  Un .M LL SECONDS,
          false,
          true);
  pr vate ConcurrentHashMap<Long, Long> map;

  publ c UserScrubGeoMap() {
    map = new ConcurrentHashMap<>();
    SearchCustomGauge.export("num_users_ n_geo_map", t ::getNumUsers nMap);
  }

  /**
   * Ensure that t  max_t et_ d  n t  userScrubGeoEvent  s greater than t  one already stored
   *  n t  map for t  g ven user  d ( f any) before updat ng t  entry for t  user.
   * T  w ll protect Earlyb rds from potent al  ssues w re out of date UserScrubGeoEvents
   * appear  n t   ncom ng Kafka stream.
   *
   * @param userScrubGeoEvent
   */
  publ c vo d  ndexUserScrubGeoEvent(UserScrubGeoEvent userScrubGeoEvent) {
    long user d = userScrubGeoEvent.getUser_ d();
    long newMaxT et d = userScrubGeoEvent.getMax_t et_ d();
    long oldMaxT et d = map.getOrDefault(user d, 0L);
     f (map.conta nsKey(user d)) {
      USER_SCRUB_GEO_EVENT_EX ST NG_USER_COUNT. ncre nt();
    }
    map.put(user d, Math.max(oldMaxT et d, newMaxT et d));
    USER_SCRUB_GEO_EVENT_LAG_STAT.t  r ncre nt(computeEventLag(newMaxT et d));
  }

  /**
   * A t et  s geo scrubbed  f    s older than t  max t et  d that  s scrubbed for t  t et's
   * author.
   *  f t re  s no entry for t  t et's author  n t  map, t n t  t et  s not geo scrubbed.
   *
   * @param t et d
   * @param fromUser d
   * @return
   */
  publ c boolean  sT etGeoScrubbed(long t et d, long fromUser d) {
    return t et d <= map.getOrDefault(fromUser d, 0L);
  }

  /**
   * T  lag ( n m ll seconds) from w n a UserScrubGeoEvent  s created, unt l    s appl ed to t 
   * UserScrubGeoMap. Take t  maxT et d found  n t  current event and convert   to a t  stamp.
   * T  maxT et d w ll g ve us a t  stamp closest to w n T etyp e processes macaw-geo requests.
   *
   * @param maxT et d
   * @return
   */
  pr vate long computeEventLag(long maxT et d) {
    long eventCreatedAtT   = Snowflake dParser.getT  stampFromT et d(maxT et d);
    return System.currentT  M ll s() - eventCreatedAtT  ;
  }

  publ c long getNumUsers nMap() {
    return map.s ze();
  }

  publ c ConcurrentHashMap<Long, Long> getMap() {
    return map;
  }

  publ c boolean  sEmpty() {
    return map. sEmpty();
  }

  publ c boolean  sSet(long user d) {
    return map.conta nsKey(user d);
  }
}
