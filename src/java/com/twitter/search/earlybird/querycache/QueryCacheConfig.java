package com.tw ter.search.earlyb rd.querycac ;

 mport java. o.F le;
 mport java. o.F leNotFoundExcept on;
 mport java. o.F leReader;
 mport java. o.Reader;
 mport java.ut l.ArrayL st;
 mport java.ut l.L st;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;
 mport org.yaml.snakeyaml.TypeDescr pt on;
 mport org.yaml.snakeyaml.Yaml;
 mport org.yaml.snakeyaml.constructor.Constructor;

 mport com.tw ter.search.common.conf g.Conf g;
 mport com.tw ter.search.common. tr cs.SearchStatsRece ver;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdConf g;

// QueryCac Conf g  s not thread safe. *Do not* attempt to create mult ple QueryCac Conf g
//  n d fferent threads
publ c class QueryCac Conf g {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(QueryCac Conf g.class);
  pr vate stat c f nal Str ng DEFAULT_CONF G_F LE = "querycac .yml";
  pr vate f nal SearchStatsRece ver statsRece ver;

  pr vate L st<QueryCac F lter> f lters;

  publ c QueryCac Conf g(SearchStatsRece ver statsRece ver) {
    t (locateConf gF le(Earlyb rdConf g.getStr ng("query_cac _conf g_f le_na ",
                                                    DEFAULT_CONF G_F LE)), statsRece ver);
  }

  // package protected constructor for un  test only
  QueryCac Conf g(Reader reader, SearchStatsRece ver statsRece ver) {
    t .statsRece ver = statsRece ver;
     f (reader == null) {
      throw new Runt  Except on("Query cac  conf g not loaded");
    }
    loadConf g(reader);
  }

  publ c L st<QueryCac F lter> f lters() {
    return f lters;
  }

   nt getF lterS ze() {
    return f lters.s ze();
  }

  pr vate stat c F leReader locateConf gF le(Str ng conf gF leNa ) {
    F le conf gF le = null;
    Str ng d r = Conf g.locateSearchConf gD r(Earlyb rdConf g.EARLYB RD_CONF G_D R, conf gF leNa );
     f (d r != null) {
      conf gF le = openConf gF le(d r + "/" + conf gF leNa );
    }
     f (conf gF le != null) {
      try {
        return new F leReader(conf gF le);
      } catch (F leNotFoundExcept on e) {
        // T  should not happen as t  caller should make sure that t  f le ex sts before
        // call ng t  funct on.
        LOG.error("Unexpected except on", e);
        throw new Runt  Except on("Query cac  conf g f le not loaded!", e);
      }
    }
    return null;
  }

  pr vate stat c F le openConf gF le(Str ng conf gF lePath) {
    F le conf gF le = new F le(conf gF lePath);
     f (!conf gF le.ex sts()) {
      LOG.warn("QueryCac  conf g f le [" + conf gF le + "] not found");
      conf gF le = null;
    } else {
      LOG. nfo("Opened QueryCac F lter conf g f le [" + conf gF le + "]");
    }
    return conf gF le;
  }

  pr vate vo d loadConf g(Reader reader) {
    TypeDescr pt on qcEntryDescr pt on = new TypeDescr pt on(QueryCac F lter.class);
    Constructor constructor = new Constructor(qcEntryDescr pt on);
    Yaml yaml = new Yaml(constructor);

    f lters = new ArrayL st<>();

    for (Object data : yaml.loadAll(reader)) {
      QueryCac F lter cac F lter = (QueryCac F lter) data;
      try {
        cac F lter.san yC ck();
      } catch (QueryCac F lter. nval dEntryExcept on e) {
        throw new Runt  Except on(e);
      }
      cac F lter.createQueryCounter(statsRece ver);
      f lters.add(cac F lter);
      LOG. nfo("Loaded f lter from conf g {}", cac F lter.toStr ng());
    }
    LOG. nfo("Total f lters loaded: {}", f lters.s ze());
  }
}
