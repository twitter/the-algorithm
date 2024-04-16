package com.tw ter.s mclusters_v2.summ ngb rd.common

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.thr ft.Cl ent d
 mport com.tw ter.storehaus_ nternal. mcac .Connect onConf g
 mport com.tw ter.storehaus_ nternal. mcac . mcac Conf g
 mport com.tw ter.storehaus_ nternal.ut l.KeyPref x
 mport com.tw ter.storehaus_ nternal.ut l.TTL
 mport com.tw ter.strato.cl ent.Strato
 mport com.tw ter.strato.cl ent.{Cl ent => StratoCl ent}

object Cl entConf gs {

  com.tw ter.server. n () // necessary  n order to use W lyNS path

  f nal lazy val s mClustersCoreAltCac Path =
    "/srv#/prod/local/cac /s mclusters_core_alt"

  f nal lazy val s mClustersCoreAltL ghtCac Path =
    "/srv#/prod/local/cac /s mclusters_core_alt_l ght"

  f nal lazy val develS mClustersCoreCac Path =
    "/srv#/test/local/cac /t mcac _s mclusters_core"

  f nal lazy val develS mClustersCoreL ghtCac Path =
    "/srv#/test/local/cac /t mcac _s mclusters_core_l ght"

  f nal lazy val logFavBasedT et20M145K2020StratoPath =
    "recom ndat ons/s mclusters_v2/embedd ngs/logFavBasedT et20M145K2020Pers stent"

  f nal lazy val logFavBasedT et20M145K2020Uncac dStratoPath =
    "recom ndat ons/s mclusters_v2/embedd ngs/logFavBasedT et20M145K2020-UNCACHED"

  f nal lazy val develLogFavBasedT et20M145K2020StratoPath =
    "recom ndat ons/s mclusters_v2/embedd ngs/logFavBasedT et20M145K2020Devel"

  f nal lazy val ent yClusterScore mcac Conf g: (Str ng, Serv ce dent f er) =>  mcac Conf g = {
    (path: Str ng, serv ce dent f er: Serv ce dent f er) =>
      new  mcac Conf g {
        val connect onConf g: Connect onConf g = Connect onConf g(path, serv ce dent f er = serv ce dent f er)
        overr de val keyPref x: KeyPref x = KeyPref x(s"ecs_")
        overr de val ttl: TTL = TTL(8.h s)
      }
  }

  // note: t  should  n ded cated cac  for t et
  f nal lazy val t etTopKClusters mcac Conf g: (Str ng, Serv ce dent f er) =>  mcac Conf g = {
    (path: Str ng, serv ce dent f er: Serv ce dent f er) =>
      new  mcac Conf g {
        val connect onConf g: Connect onConf g =
          Connect onConf g(path, serv ce dent f er = serv ce dent f er)
        overr de val keyPref x: KeyPref x = KeyPref x(s"etk_")
        overr de val ttl: TTL = TTL(2.days)
      }
  }

  // note: t  should  n ded cated cac  for t et
  f nal lazy val clusterTopT ets mcac Conf g: (Str ng, Serv ce dent f er) =>  mcac Conf g = {
    (path: Str ng, serv ce dent f er: Serv ce dent f er) =>
      new  mcac Conf g {
        val connect onConf g: Connect onConf g =
          Connect onConf g(path, serv ce dent f er = serv ce dent f er)
        overr de val keyPref x: KeyPref x = KeyPref x(s"ctkt_")
        overr de val ttl: TTL = TTL(8.h s)
      }
  }

  f nal lazy val stratoCl ent: Serv ce dent f er => StratoCl ent = { serv ce dent f er =>
    Strato.cl ent
      .w hRequestT  out(2.seconds)
      .w hMutualTls(serv ce dent f er)
      .bu ld()
  }

  // thr ft cl ent  d
  pr vate f nal lazy val thr ftCl ent d: Str ng => Cl ent d = { env: Str ng =>
    Cl ent d(s"s mclusters_v2_summ ngb rd.$env")
  }

}
