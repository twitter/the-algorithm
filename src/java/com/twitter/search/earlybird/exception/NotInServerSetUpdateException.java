package com.tw ter.search.earlyb rd.except on;

 mport com.tw ter.common.zookeeper.ServerSet;

/**
 * Used w n try ng to leave a server set w n t  earlyb rd  s already out of t  server set.
 */
publ c class Not nServerSetUpdateExcept on extends ServerSet.UpdateExcept on {
  publ c Not nServerSetUpdateExcept on(Str ng  ssage) {
    super( ssage);
  }
}
