package com.tw ter.search.earlyb rd.part  on;

 mport com.google.common.annotat ons.V s bleForTest ng;

 mport com.tw ter.search.common.part  on ng.base.Seg nt;

/**
 * Representat on for seg nt sync state, t  local and hdfs f le locat ons, as  ll as t 
 * current  n- mory sync states ma nta ned by earlyb rds.
 */
publ c class Seg ntSync nfo {
  //  s t  seg nt loaded from d sk?
  pr vate volat le boolean loaded = false;
  // Has t  seg nt been flus d to d sk, and uploaded to HDFS  f upload ng  s enabled?
  pr vate volat le boolean flus d = false;
  // T   w n t  seg nt was flus d to local d sk
  pr vate volat le long flushT  M ll s = 0;

  pr vate f nal Seg nt seg nt;
  pr vate f nal Seg ntSyncConf g syncConf g;
  pr vate f nal Str ng localSyncD r;
  pr vate f nal Str ng hdfsFlushD r;
  pr vate f nal Str ng hdfsSyncD rPref x;
  pr vate f nal Str ng hdfsUploadD rPref x;
  pr vate f nal Str ng hdfsTempFlushD r;

  @V s bleForTest ng
  publ c Seg ntSync nfo(Seg ntSyncConf g syncConf g, Seg nt seg nt) {
    t .seg nt = seg nt;
    t .syncConf g = syncConf g;
    t .localSyncD r = syncConf g.getLocalSyncD rNa (seg nt);
    t .hdfsSyncD rPref x = syncConf g.getHdfsSyncD rNa Pref x(seg nt);
    t .hdfsUploadD rPref x = syncConf g.getHdfsUploadD rNa Pref x(seg nt);
    t .hdfsFlushD r = syncConf g.getHdfsFlushD rNa (seg nt);
    t .hdfsTempFlushD r = syncConf g.getHdfsTempFlushD rNa (seg nt);
  }

  publ c boolean  sLoaded() {
    return loaded;
  }

  publ c boolean  sFlus d() {
    return flus d;
  }

  publ c long getFlushT  M ll s() {
    return flushT  M ll s;
  }

  publ c Str ng getLocalSyncD r() {
    return localSyncD r;
  }

  publ c Seg ntSyncConf g getSeg ntSyncConf g() {
    return syncConf g;
  }

  publ c Str ng getLocalLuceneSyncD r() {
    // For arch ve search t  na  depends on t  end date of t  seg nt, wh ch can change,
    // so   cannot pre-compute t   n t  constructor.
    // T  should only be used  n t  on-d sk arch ve.
    return syncConf g.getLocalLuceneSyncD rNa (seg nt);
  }

  publ c Str ng getHdfsFlushD r() {
    return hdfsFlushD r;
  }

  publ c Str ng getHdfsSyncD rPref x() {
    return hdfsSyncD rPref x;
  }

  publ c Str ng getHdfsUploadD rPref x() {
    return hdfsUploadD rPref x;
  }

  publ c Str ng getHdfsTempFlushD r() {
    return hdfsTempFlushD r;
  }

  publ c vo d setLoaded(boolean  sLoaded) {
    t .loaded =  sLoaded;
  }

  /**
   * Stores t  flush ng state for t  seg nt.
   */
  publ c vo d setFlus d(boolean  sFlus d) {
     f ( sFlus d) {
      t .flushT  M ll s = System.currentT  M ll s();
    }
    t .flus d =  sFlus d;
  }

  /**
   * Adds debug  nformat on about t  loaded and flus d status of t  seg nt to t  g ven
   * Str ngBu lder.
   */
  publ c vo d addDebug nfo(Str ngBu lder bu lder) {
    bu lder.append("[");
     nt startLength = bu lder.length();
     f (loaded) {
      bu lder.append("loaded, ");
    }
     f (flus d) {
      bu lder.append("flus d, ");
    }
     f (startLength < bu lder.length()) {
      bu lder.setLength(bu lder.length() - 2);
    }
    bu lder.append("]");
  }
}
