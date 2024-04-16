package com.tw ter.search.earlyb rd.part  on;

 mport java. o. OExcept on;

 mport org.apac .hadoop.conf.Conf gurat on;
 mport org.apac .hadoop.fs.F leStatus;
 mport org.apac .hadoop.fs.F leSystem;
 mport org.apac .hadoop.fs.Path;

publ c f nal class HdfsUt l {
  pr vate HdfsUt l() {
  }

  publ c stat c F leSystem getHdfsF leSystem() throws  OExcept on {
    Conf gurat on conf g = new Conf gurat on();
    // S nce earlyb rd uses hdfs from d fferent threads, and closes t  F leSystem from
    // t m  ndependently,   want each thread to have  s own, new F leSystem.
    return F leSystem.new nstance(conf g);
  }

  /**
   * C cks  f t  g ven seg nt  s present on HDFS
   */
  publ c stat c boolean seg ntEx stsOnHdfs(F leSystem fs, Seg nt nfo seg nt nfo)
      throws  OExcept on {
    Str ng hdfsBaseD rPref x = seg nt nfo.getSync nfo().getHdfsUploadD rPref x();
    F leStatus[] statuses = fs.globStatus(new Path(hdfsBaseD rPref x));
    return statuses != null && statuses.length > 0;
  }
}
