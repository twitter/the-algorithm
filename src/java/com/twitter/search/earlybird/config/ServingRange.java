package com.tw ter.search.earlyb rd.conf g;

/**
 * An  nterface for abstract ng a t er's serv ng range.
 */
publ c  nterface Serv ngRange {
  /**
   * Returns t  serv ng range's lo st t et  D.
   */
  long getServ ngRangeS nce d();

  /**
   * Returns t  serv ng range's h g st t et  D.
   */
  long getServ ngRangeMax d();

  /**
   * Returns t  serv ng range's earl est t  ,  n seconds s nce epoch.
   */
  long getServ ngRangeS nceT  SecondsFromEpoch();

  /**
   * Returns t  serv ng range's latest t  ,  n seconds s nce epoch.
   */
  long getServ ngRangeUnt lT  SecondsFromEpoch();
}
