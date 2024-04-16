package com.tw ter.search.earlyb rd_root.val dators;

 mport com.tw ter.ut l.Future;

publ c  nterface Serv ceResponseVal dator<R> {
  /**
   *  nterface for val dat ng Serv ce responses
   */
  Future<R> val date(R response);
}
