package com.tw ter.search. ngester.p pel ne.app;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search. ngester.p pel ne.ut l.P pel neExcept onHandler;
 mport com.tw ter.ut l.Durat on;

publ c class P pel neExcept on mpl  mple nts P pel neExcept onHandler {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(P pel neExcept on mpl.class);

  pr vate f nal  ngesterP pel neAppl cat on app;

  publ c P pel neExcept on mpl( ngesterP pel neAppl cat on app) {
    t .app = app;
  }

  @Overr de
  publ c vo d logAndWa (Str ng msg, Durat on wa T  ) throws  nterruptedExcept on {
    LOG. nfo(msg);
    long wa T   nM ll Second = wa T  . nM ll seconds();
    Thread.sleep(wa T   nM ll Second);
  }

  @Overr de
  publ c vo d logAndShutdown(Str ng msg) {
    LOG.error(msg);
    app.shutdown();
  }
}
