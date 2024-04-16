package com.tw ter.search.earlyb rd_root;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;

publ c f nal class Except onHandler {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Except onHandler.class);

  pr vate Except onHandler() {
  }

  publ c stat c vo d logExcept on(Earlyb rdRequest request, Throwable e) {
    LOG.error("Except on wh le handl ng request: {}", request, e);
  }
}
