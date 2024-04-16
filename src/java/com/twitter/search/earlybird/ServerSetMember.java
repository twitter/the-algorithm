package com.tw ter.search.earlyb rd;

 mport org.apac .zookeeper.KeeperExcept on;

 mport com.tw ter.common.zookeeper.ServerSet;
 mport com.tw ter.common.zookeeper.ZooKeeperCl ent;

/**
 * Represents a server that can add and remove  self from a server set.
 */
publ c  nterface ServerSet mber {
  /**
   * Makes t  server jo n  s server set.
   *
   * @throws ServerSet.UpdateExcept on
   * @param requestS ce
   */
  vo d jo nServerSet(Str ng requestS ce) throws ServerSet.UpdateExcept on;

  /**
   * Makes t  server leave  s server set.
   *
   * @throws ServerSet.UpdateExcept on
   * @param requestS ce
   */
  vo d leaveServerSet(Str ng requestS ce) throws ServerSet.UpdateExcept on;

  /**
   * Gets and returns t  current number of  mbers  n t  server's server set.
   *
   * @return number of  mbers currently  n t  host's server set.
   * @throws  nterruptedExcept on
   * @throws ZooKeeperCl ent.ZooKeeperConnect onExcept on
   * @throws KeeperExcept on
   */
   nt getNumberOfServerSet mbers() throws  nterruptedExcept on,
      ZooKeeperCl ent.ZooKeeperConnect onExcept on, KeeperExcept on;

  /**
   * C cks  f t  earlyb rd  s  n t  server set.
   *
   * @return true  f    s, false ot rw se.
   */
  boolean  s nServerSet();

  /**
   * Should only be called for Arch ve Earlyb rds.
   *
   * Jo n ServerSet for Serv ceProxy w h a na d adm n port and w h a zookeeper path that Serv ce
   * Proxy can translate to a doma n na  label that  s less than 64 characters (due to t  s ze
   * l m  for doma n na  labels descr bed  re: https://tools. etf.org/html/rfc1035)
   * T  w ll allow us to access Earlyb rds that are not on  sos v a Serv ceProxy.
   */
  vo d jo nServerSetForServ ceProxy();
}
