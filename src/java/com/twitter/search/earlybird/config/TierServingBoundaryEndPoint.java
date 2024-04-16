package com.tw ter.search.earlyb rd.conf g;

 mport java.ut l.Date;

 mport javax.annotat on.Nullable;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.search.common.part  on ng.snowflakeparser.Snowflake dParser;

/**
 * T  start or end boundary of a t er's serv ng range.
 * T   s used to add s nce_ d and max_ d operators onto search quer es.
 */
publ c class T erServ ngBoundaryEndPo nt {
  @V s bleForTest ng
  publ c stat c f nal Str ng  NFERRED_FROM_DATA_RANGE = " nferred_from_data_range";
  publ c stat c f nal Str ng RELAT VE_TO_CURRENT_T ME_MS = "relat ve_to_current_t  _ms";

  // E  r offsetToCurrentT  M ll s  s set or (absoluteT et d and t  BoundarySecondsFromEpoch)
  // are set.
  @Nullable
  pr vate f nal Long offsetToCurrentT  M ll s;
  @Nullable
  pr vate f nal Long absoluteT et d;
  @Nullable
  pr vate f nal Long t  BoundarySecondsFromEpoch;
  pr vate f nal Clock clock;

  T erServ ngBoundaryEndPo nt(Long absoluteT et d,
                              Long t  BoundarySecondsFromEpoch,
                              Long offsetToCurrentT  M ll s,
                              Clock clock) {
    t .offsetToCurrentT  M ll s = offsetToCurrentT  M ll s;
    t .absoluteT et d = absoluteT et d;
    t .t  BoundarySecondsFromEpoch = t  BoundarySecondsFromEpoch;
    t .clock = clock;
  }

  /**
   * Parse t  boundary str ng and construct a T erServ ngBoundaryEndPo nt  nstance.
   * @param boundaryStr ng boundary conf gurat on str ng. Val d values are:
   * <l >
   * " nferred_from_data_range"  nfers serv ng range from data range. T  only works after
   *                               Nov 2010 w n Tw ter sw c d to snowflake  Ds.
   *                               T   s t  default value.
   * </l >
   * <l >
   * "absolute_t et_ d_and_t  stamp_m ll s: d:t  stamp" a t et  D/t  stamp  s g ven
   *                                                       expl c ly as t  serv ng range
   *                                                       boundary.
   * </l >
   * <l >
   * "relat ve_to_current_t  _ms:offset" adds offset onto current t  stamp  n m ll s to
   *                                         compute serv ng range.
   * </l >
   *
   * @param boundaryDate t  data boundary. T   s used  n conjunct on w h
   *  nferred_from_data_date to determ ne t  serv ng boundary.
   * @param clock  Clock used to obta n current t  , w n relat ve_to_current_t  _ms  s used.
   *               Tests pass  n a FakeClock.
   */
  publ c stat c T erServ ngBoundaryEndPo nt newT erServ ngBoundaryEndPo nt(Str ng boundaryStr ng,
      Date boundaryDate,
      Clock clock) {
     f (boundaryStr ng == null || boundaryStr ng.tr m().equals(
         NFERRED_FROM_DATA_RANGE)) {
      return  nferBoundaryFromDataRange(boundaryDate, clock);
    } else  f (boundaryStr ng.tr m().startsW h(RELAT VE_TO_CURRENT_T ME_MS)) {
      return getRelat veBoundary(boundaryStr ng, clock);
    } else {
      throw new  llegalStateExcept on("Cannot parse serv ng range str ng: " + boundaryStr ng);
    }
  }

  pr vate stat c T erServ ngBoundaryEndPo nt  nferBoundaryFromDataRange(Date boundaryDate,
                                                                        Clock clock) {
    //  nfer from data range
    // handle default start date and end date,  n case t  dates are not spec f ed  n t  conf g
     f (boundaryDate.equals(T erConf g.DEFAULT_T ER_START_DATE)) {
      return new T erServ ngBoundaryEndPo nt(
          -1L, T erConf g.DEFAULT_T ER_START_DATE.getT  () / 1000, null, clock);
    } else  f (boundaryDate.equals(T erConf g.DEFAULT_T ER_END_DATE)) {
      return new T erServ ngBoundaryEndPo nt(
          Long.MAX_VALUE, T erConf g.DEFAULT_T ER_END_DATE.getT  () / 1000, null, clock);
    } else {
      // convert data start / end dates  nto s nce / max  D.
      long boundaryT  M ll s = boundaryDate.getT  ();
       f (!Snowflake dParser. sUsableSnowflakeT  stamp(boundaryT  M ll s)) {
        throw new  llegalStateExcept on("Serv ng t   range can not be determ ned, because "
            + boundaryDate + "  s before Tw ter sw c d to snowflake t et  Ds.");
      }
      // Earlyb rd s nce_ d  s  nclus ve and max_ d  s exclus ve.   substract 1  re.
      // Cons der example:
      //   full0:  5000 ( nclus ve) - 6000 (exclus ve)
      //   full1:  6000 ( nclus ve) - 7000 (exclus ve)
      // For t er full0,   should use max_ d 5999  nstead of 6000.
      // For t er full1,   should use s nce_ d 5999  nstead of 6000.
      //  nce   substract 1  re.
      long adjustedT et d =
        Snowflake dParser.generateVal dStatus d(boundaryT  M ll s, 0) - 1;
      Precond  ons.c ckState(adjustedT et d >= 0, "boundary t et  D must be non-negat ve");
      return new T erServ ngBoundaryEndPo nt(
          adjustedT et d, boundaryT  M ll s / 1000, null, clock);
    }
  }

  pr vate stat c T erServ ngBoundaryEndPo nt getRelat veBoundary(Str ng boundaryStr ng,
                                                                 Clock clock) {
    // An offset relat ve to current t    s g ven
    Str ng[] parts = boundaryStr ng.spl (":");
    Precond  ons.c ckState(parts.length == 2);
    long offset = Long.parseLong(parts[1]);
    return new T erServ ngBoundaryEndPo nt(null, null, offset, clock);
  }

  /**
   * Returns t  t et  D for t  t er boundary.  f t  t er boundary was created us ng a t et  D,
   * that t et  D  s returned. Ot rw se, a t et  D  s der ved from t  t   boundary.
   */
  @V s bleForTest ng
  publ c long getBoundaryT et d() {
    //  f absoluteT et d  s ava lable, use  .
     f (absoluteT et d != null) {
      return absoluteT et d;
    } else {
      Precond  ons.c ckNotNull(offsetToCurrentT  M ll s);
      long boundaryT   = clock.nowM ll s() + offsetToCurrentT  M ll s;
      return Snowflake dParser.generateVal dStatus d(boundaryT  , 0);
    }
  }

  /**
   * Returns t  t   boundary for t  t er boundary,  n seconds s nce epoch.
   */
  publ c long getBoundaryT  SecondsFromEpoch() {
     f (t  BoundarySecondsFromEpoch != null) {
      return t  BoundarySecondsFromEpoch;
    } else {
      Precond  ons.c ckNotNull(offsetToCurrentT  M ll s);
      return (clock.nowM ll s() + offsetToCurrentT  M ll s) / 1000;
    }
  }
}
