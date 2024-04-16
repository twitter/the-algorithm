package com.tw ter.search.earlyb rd.ut l;

 mport java.text.ParseExcept on;
 mport java.ut l.Date;

 mport org.apac .commons.lang3.t  .FastDateFormat;

publ c f nal class ScrubGenUt l {
  publ c stat c f nal FastDateFormat SCRUB_GEN_DATE_FORMAT = FastDateFormat.get nstance("yyyyMMdd");

  pr vate ScrubGenUt l() { }

  /**
   *  lper  thod to parse a scrub gen from Str ng to date
   *
   * @param scrubGen
   * @return scrubGen  n Date type
   */
  publ c stat c Date parseScrubGenToDate(Str ng scrubGen) {
    try {
      return SCRUB_GEN_DATE_FORMAT.parse(scrubGen);
    } catch (ParseExcept on e) {
      Str ng msg = "Malfor d scrub gen date: " + scrubGen;
      //  f   are runn ng a scrub gen and t  date  s bad   should qu  and not cont nue.
      throw new Runt  Except on(msg, e);
    }
  }
}
