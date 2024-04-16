package com.tw ter.search.earlyb rd.part  on;

 mport java. o.Closeable;

 mport com.tw ter.search.earlyb rd.except on.Earlyb rdStartupExcept on;

/**
 * Handles start ng and  ndex ng data for an Earlyb rd.
 */
@Funct onal nterface
publ c  nterface Earlyb rdStartup {
  /**
   * Handles  ndex ng T ets, T et Updates and user updates. Blocks unt l current, and forks a
   * thread to keep t   ndex current.
   */
  Closeable start() throws Earlyb rdStartupExcept on;
}
