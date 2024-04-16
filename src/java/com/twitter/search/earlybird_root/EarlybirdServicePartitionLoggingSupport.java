package com.tw ter.search.earlyb rd_root;

 mport java.ut l.Map;
 mport java.ut l.Random;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common.root.Part  onLogg ngSupport;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;

publ c class Earlyb rdServ cePart  onLogg ngSupport
    extends Part  onLogg ngSupport.DefaultPart  onLogg ngSupport<Earlyb rdRequestContext> {
  pr vate stat c f nal Logger PART T ON_LOG = LoggerFactory.getLogger("part  onLogger");

  pr vate stat c f nal long LATENCY_LOG_PART T ONS_THRESHOLD_MS = 500;
  pr vate stat c f nal double FRACT ON_OF_REQUESTS_TO_LOG = 1.0 / 500.0;

  pr vate f nal Random random = new Random();

  @Overr de
  publ c vo d logPart  onLatenc es(Earlyb rdRequestContext requestContext,
                                    Str ng t erNa ,
                                    Map< nteger, Long> part  onLatenc esM cros,
                                    long latencyMs) {
    Str ng logReason = null;

     f (random.nextFloat() <= FRACT ON_OF_REQUESTS_TO_LOG) {
      logReason = "randomSample";
    } else  f (latencyMs > LATENCY_LOG_PART T ONS_THRESHOLD_MS) {
      logReason = "slow";
    }

    Earlyb rdRequest request = requestContext.getRequest();
     f (logReason != null && request. sSetSearchQuery()) {
      PART T ON_LOG. nfo("{};{};{};{};{};{}", t erNa , logReason, latencyMs,
          part  onLatenc esM cros, request.getCl entRequest D(),
          request.getSearchQuery().getSer al zedQuery());
    }
  }
}
