package com.tw ter.search.earlyb rd.part  on;

 mport java. o.Closeable;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.search.earlyb rd.Earlyb rdServer;
 mport com.tw ter.search.earlyb rd.Earlyb rdStatus;
 mport com.tw ter.search.earlyb rd.except on.Earlyb rdStartupExcept on;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdStatusCode;

/**
 * Handles start ng and  ndex ng data for a part  on, us ng a Part  onManager.
 */
publ c class Part  onManagerStartup  mple nts Earlyb rdStartup {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Earlyb rdServer.class);

  pr vate f nal Clock clock;
  pr vate f nal Part  onManager part  onManager;

  publ c Part  onManagerStartup(
      Clock clock,
      Part  onManager part  onManager
  ) {
    t .clock = clock;
    t .part  onManager = part  onManager;
  }

  @Overr de
  publ c Closeable start() throws Earlyb rdStartupExcept on {
    part  onManager.sc dule();

     nt count = 0;

    wh le (Earlyb rdStatus.getStatusCode() != Earlyb rdStatusCode.CURRENT) {
       f (Earlyb rdStatus.getStatusCode() == Earlyb rdStatusCode.STOPP NG) {
        return part  onManager;
      }

      try {
        clock.wa For(1000);
      } catch ( nterruptedExcept on e) {
        LOG. nfo("Sleep  nterrupted, qu t ng earlyb rd");
        throw new Earlyb rdStartupExcept on("Sleep  nterrupted");
      }

      // Log every 120 seconds.
       f (count++ % 120 == 0) {
        LOG. nfo("Thr ft port closed unt l Earlyb rd, both  ndex ng and query cac ,  s current");
      }
    }

    return part  onManager;
  }
}
