package com.tw ter.search.earlyb rd.arch ve;

 mport java. o. OExcept on;
 mport java.ut l.Calendar;
 mport java.ut l.Date;
 mport java.ut l.regex.Matc r;
 mport java.ut l.regex.Pattern;

 mport org.apac .commons. o. OUt ls;
 mport org.apac .hadoop.fs.F leStatus;
 mport org.apac .hadoop.fs.F leSystem;
 mport org.apac .hadoop.fs.Path;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common.part  on ng.base.Seg nt;
 mport com.tw ter.search.earlyb rd.part  on.HdfsUt l;
 mport com.tw ter.search.earlyb rd.part  on.Seg nt nfo;
 mport com.tw ter.search.earlyb rd.part  on.Seg ntSyncConf g;


publ c f nal class Arch veHDFSUt ls {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Arch veHDFSUt ls.class);

  pr vate stat c f nal Pattern SEGMENT_NAME_PATTERN =
      Pattern.comp le("_start_([0-9]+)_p_([0-9]+)_of_([0-9]+)_([0-9]{14}+)_");
  pr vate stat c f nal  nt MATCHER_GROUP_END_DATE = 4;

  pr vate Arch veHDFSUt ls() {
  }

  /**
   * C ck  f a g ven seg nt already has  s  nd ces bu lt on hdfs.
   * @return true  f t   nd ces ex st on hdfs; ot rw se, false.
   */
  publ c stat c boolean hasSeg nt nd cesOnHDFS(Seg ntSyncConf g sync, Seg nt nfo seg nt) {
    LOG. nfo("c ck ng seg nt on hdfs: " + seg nt
        + " enabled: " + sync. sSeg ntLoadFromHdfsEnabled());
    F leSystem fs = null;
    try {
      fs = HdfsUt l.getHdfsF leSystem();
      Str ng hdfsBaseD rPref x = seg nt.getSync nfo()
          .getHdfsSyncD rPref x();
      F leStatus[] statuses = fs.globStatus(new Path(hdfsBaseD rPref x));
      return statuses != null && statuses.length > 0;
    } catch ( OExcept on ex) {
      LOG.error("Fa led c ck ng seg nt on hdfs: " + seg nt, ex);
      return false;
    } f nally {
       OUt ls.closeQu etly(fs);
    }
  }

  /**
   * Delete t  seg nt  ndex d rector es on t  HDFS.  f 'deleteCurrentD r'  s true, t 
   *  ndex d rectory w h t  end date match ng 'seg nt' w ll be deleted.  f 'deleteOlderD rs',
   * t   ndex d rector es w h t  end date earl er than t  t  seg nt enddate w ll be deleted.
   *
   */
  publ c stat c vo d deleteHdfsSeg ntD r(Seg ntSyncConf g sync, Seg nt nfo seg nt,
                                          boolean deleteCurrentD r, boolean deleteOlderD rs) {
    F leSystem fs = null;
    try {
      fs = HdfsUt l.getHdfsF leSystem();
      Str ng hdfsFlushD r = seg nt.getSync nfo().getHdfsFlushD r();
      Str ng hdfsBaseD rPref x = seg nt.getSync nfo()
          .getHdfsSyncD rPref x();
      Str ng endDateStr = extractEndDate(hdfsBaseD rPref x);
       f (endDateStr != null) {
        hdfsBaseD rPref x = hdfsBaseD rPref x.replace(endDateStr, "*");
      }
      Str ng[] hdfsD rs = {seg nt.getSync nfo().getHdfsTempFlushD r(),
          hdfsBaseD rPref x};
      for (Str ng hdfsD r : hdfsD rs) {
        F leStatus[] statuses = fs.globStatus(new Path(hdfsD r));
         f (statuses != null && statuses.length > 0) {
          for (F leStatus status : statuses) {
             f (status.getPath().toStr ng().endsW h(hdfsFlushD r)) {
               f (deleteCurrentD r) {
                fs.delete(status.getPath(), true);
                LOG. nfo("Deleted seg nt: " + status.getPath());
              }
            } else {
               f (deleteOlderD rs) {
                fs.delete(status.getPath(), true);
                LOG. nfo("Deleted seg nt: " + status.getPath());
              }
            }
          }
        }
      }
    } catch ( OExcept on e) {
      LOG.error("Error delete Seg nt D r :" + seg nt, e);
    } f nally {
       OUt ls.closeQu etly(fs);
    }
  }

  /**
   * G ven a seg nt, c ck  f t re  s any  nd ces bu lt on HDFS;  f yes, return t  end date
   * of t   ndex bu lt on HDFS; ot rw se, return null.
   */
  publ c stat c Date getSeg ntEndDateOnHdfs(Seg ntSyncConf g sync, Seg nt nfo seg nt) {
     f (sync. sSeg ntLoadFromHdfsEnabled()) {
      LOG. nfo("About to c ck seg nt on hdfs: " + seg nt
          + " enabled: " + sync. sSeg ntLoadFromHdfsEnabled());

      F leSystem fs = null;
      try {
        Str ng hdfsBaseD rPref x = seg nt.getSync nfo()
            .getHdfsSyncD rPref x();
        Str ng endDateStr = extractEndDate(hdfsBaseD rPref x);
         f (endDateStr == null) {
          return null;
        }
        hdfsBaseD rPref x = hdfsBaseD rPref x.replace(endDateStr, "*");

        fs = HdfsUt l.getHdfsF leSystem();
        F leStatus[] statuses = fs.globStatus(new Path(hdfsBaseD rPref x));
         f (statuses != null && statuses.length > 0) {
          Path hdfsSyncPath = statuses[statuses.length - 1].getPath();
          Str ng hdfsSyncPathNa  = hdfsSyncPath.getNa ();
          endDateStr = extractEndDate(hdfsSyncPathNa );
          return Seg nt.getSeg ntEndDate(endDateStr);
        }
      } catch (Except on ex) {
        LOG.error("Fa led gett ng seg nt from hdfs: " + seg nt, ex);
        return null;
      } f nally {
         OUt ls.closeQu etly(fs);
      }
    }
    return null;
  }

  pr vate stat c Str ng extractEndDate(Str ng seg ntD rPattern) {
    Matc r matc r = SEGMENT_NAME_PATTERN.matc r(seg ntD rPattern);
     f (!matc r.f nd()) {
      return null;
    }

    try {
      return matc r.group(MATCHER_GROUP_END_DATE);
    } catch ( llegalStateExcept on e) {
      LOG.error("Match operat on fa led: " + seg ntD rPattern, e);
      return null;
    } catch ( ndexOutOfBoundsExcept on e) {
      LOG.error(" No group  n t  pattern w h t  g ven  ndex : " + seg ntD rPattern, e);
      return null;
    }
  }

  /**
   * Converts t  g ven date to a path, us ng t  g ven separator. For example,  f t  sate  s
   * January 5, 2019, and t  separator  s "/", t   thod w ll return "2019/01/05".
   */
  publ c stat c Str ng dateToPath(Date date, Str ng separator) {
    Str ngBu lder bu lder = new Str ngBu lder();
    Calendar cal = Calendar.get nstance();
    cal.setT  (date);
    bu lder.append(cal.get(Calendar.YEAR))
           .append(separator)
           .append(padd ng(cal.get(Calendar.MONTH) + 1, 2))
           .append(separator)
           .append(padd ng(cal.get(Calendar.DAY_OF_MONTH), 2));
    return bu lder.toStr ng();
  }

  pr vate stat c Str ng padd ng( nt value,  nt len) {
    return Str ng.format("%0" + len + "d", value);
  }
}
