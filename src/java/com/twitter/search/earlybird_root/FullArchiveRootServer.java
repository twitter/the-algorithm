package com.tw ter.search.earlyb rd_root;

 mport javax. nject. nject;

 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.search.common.root.SearchRootServer;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdServ ce;

publ c class FullArch veRootServer extends SearchRootServer<Earlyb rdServ ce.Serv ce face> {

  @ nject
  publ c FullArch veRootServer(FullArch veRootServ ce svc, Serv ce<byte[], byte[]> byteSvc) {
    super(svc, byteSvc);
  }

}
