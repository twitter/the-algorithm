package com.tw ter.search.earlyb rd_root;

 mport javax. nject. nject;
 mport javax. nject.S ngleton;

 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.search.common.root.SearchRootServer;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdServ ce;

@S ngleton
publ c class Realt  CgRootServer extends SearchRootServer<Earlyb rdServ ce.Serv ce face> {

  @ nject
  publ c Realt  CgRootServer(Realt  CgRootServ ce svc, Serv ce<byte[], byte[]> byteSvc) {
    super(svc, byteSvc);
  }

}
