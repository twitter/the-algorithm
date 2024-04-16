package com.tw ter.search.earlyb rd_root;

 mport javax. nject. nject;
 mport javax. nject.S ngleton;

 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.search.common.root.SearchRootServer;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdServ ce;
 mport com.tw ter.search.earlyb rd_root.f lters.QueryToken zerF lter;
 mport com.tw ter.search.queryparser.query.QueryParserExcept on;

@S ngleton
publ c class SuperRootServer extends SearchRootServer<Earlyb rdServ ce.Serv ce face> {
  pr vate f nal QueryToken zerF lter queryToken zerF lter;

  @ nject
  publ c SuperRootServer(
      SuperRootServ ce svc,
      Serv ce<byte[], byte[]> byteSvc,
      QueryToken zerF lter queryToken zerF lter) {
    super(svc, byteSvc);

    t .queryToken zerF lter = queryToken zerF lter;
  }

  @Overr de
  publ c vo d warmup() {
    super.warmup();

    try {
      queryToken zerF lter.performExpens ve n  al zat on();
    } catch (QueryParserExcept on e) {
      throw new Runt  Except on(e);
    }
  }
}
