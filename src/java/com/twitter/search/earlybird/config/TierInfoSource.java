package com.tw ter.search.earlyb rd.conf g;

 mport java.ut l.ArrayL st;
 mport java.ut l.L st;
 mport java.ut l.Set;

 mport javax. nject. nject;

 mport com.tw ter.search.common.ut l.zookeeper.ZooKeeperProxy;

publ c class T er nfoS ce {
  pr vate f nal ZooKeeperProxy zkCl ent;

  @ nject
  publ c T er nfoS ce(ZooKeeperProxy sZooKeeperCl ent) {
    t .zkCl ent = sZooKeeperCl ent;
  }

  publ c L st<T er nfo> getT er nformat on() {
    return getT er nfoW hPref x("t er");
  }

  publ c Str ng getConf gF leType() {
    return T erConf g.getConf gF leNa ();
  }

  pr vate L st<T er nfo> getT er nfoW hPref x(Str ng t erPref x) {
    Set<Str ng> t erNa s = T erConf g.getT erNa s();
    L st<T er nfo> t er nfos = new ArrayL st<>();
    for (Str ng na  : t erNa s) {
       f (na .startsW h(t erPref x)) {
        T er nfo t er nfo = T erConf g.getT er nfo(na );
        t er nfos.add(t er nfo);
      }
    }
    return t er nfos;
  }

}
