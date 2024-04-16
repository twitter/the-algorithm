package com.tw ter.search.earlyb rd_root.val dators;

 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.ut l.Future;

/** A no-op Serv ceResponseVal dator. */
publ c class PassThroughResponseVal dator  mple nts Serv ceResponseVal dator<Earlyb rdResponse> {
  @Overr de
  publ c Future<Earlyb rdResponse> val date(Earlyb rdResponse response) {
    return Future.value(response);
  }
}
