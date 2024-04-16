package com.tw ter.search. ngester.p pel ne.ut l;

 mport com.tw ter.ut l.Durat on;

publ c  nterface P pel neExcept onHandler {
  /**
   * Logs t  g ven  ssage and wa s t  g ven durat on.
   */
  vo d logAndWa (Str ng msg, Durat on wa T  ) throws  nterruptedExcept on;

  /**
   * Logs t  g ven  ssage and shutdowns t  appl cat on.
   */
  vo d logAndShutdown(Str ng msg);
}
