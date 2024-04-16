package com.tw ter.search.earlyb rd.common.userupdates;

 mport java. o.BufferedReader;
 mport java. o. OExcept on;
 mport java. o. nputStreamReader;
 mport java.ut l.Arrays;
 mport java.ut l. erator;
 mport java.ut l.L st;
 mport java.ut l.NoSuchEle ntExcept on;
 mport java.ut l.Opt onal;
 mport java.ut l.Spl erator;
 mport java.ut l.Spl erators;
 mport java.ut l.concurrent.T  Un ;
 mport java.ut l.funct on.Pred cate;
 mport java.ut l.stream.Collectors;
 mport java.ut l.stream.Stream;
 mport java.ut l.stream.StreamSupport;
 mport javax.annotat on.Nullable;

 mport org.apac .hadoop.conf.Conf gurat on;
 mport org.apac .hadoop.fs.F leSystem;
 mport org.apac .hadoop.fs.Path;
 mport org.apac .hadoop.hdfs.HdfsConf gurat on;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common_ nternal.hadoop.HdfsUt ls;
 mport com.tw ter.scald ng.DateRange;
 mport com.tw ter.scald ng.H s;
 mport com.tw ter.scald ng.R chDate;
 mport com.tw ter.search.user_table.s ces.MostRecentGoodSafetyUserStateS ce;
 mport com.tw ter.search.common. ndex ng.thr ftjava.SafetyUserState;
 mport com.tw ter.search.common.ut l. o.LzoThr ftBlockF leReader;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdConf g;
 mport com.tw ter.ut l.Durat on;
 mport com.tw ter.ut l.T  ;

/**
 * Bu lds a user table from a user safety snapshot on HDFS.
 */
publ c class UserTableBu lderFromSnapshot {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(UserTableBu lderFromSnapshot.class);

  pr vate stat c f nal  nt MAX_DAYS_TO_CHECK = 7;
  publ c stat c f nal Str ng DATA_D R = "user_states";
  publ c stat c f nal Str ng METADATA_D R = "last_updated_ms";

  pr vate f nal Str ng snapshotBaseD r;

  pr vate Str ng snapshotDataPath;
  pr vate Str ng snapshot taDataPath;
  pr vate UserTable userTable;

  pr vate long nsfwCount;
  pr vate long ant soc alCount;
  pr vate long  sProtectedCount;

  publ c UserTableBu lderFromSnapshot() {
    snapshotBaseD r =
        Earlyb rdConf g.getStr ng(Earlyb rdConf g.USER_SNAPSHOT_BASE_D R, null);

    LOG. nfo("Conf gured user snapshot d rectory: " + snapshotBaseD r);
  }

  pr vate stat c f nal class UserUpdate {
    publ c f nal long user d;
    @Nullable publ c f nal Boolean ant soc al;
    @Nullable publ c f nal Boolean nsfw;
    @Nullable publ c f nal Boolean  sProtected;

    pr vate UserUpdate(long user d,
                       @Nullable Boolean ant soc al,
                       @Nullable Boolean nsfw,
                       @Nullable Boolean  sProtected) {
      t .user d = user d;
      t .ant soc al = ant soc al;
      t .nsfw = nsfw;
      t . sProtected =  sProtected;
    }

    publ c stat c UserUpdate fromUserState(SafetyUserState safetyUserState) {
      long user d = safetyUserState.getUser D();
      @Nullable Boolean ant soc al = null;
      @Nullable Boolean nsfw = null;
      @Nullable Boolean  sProtected = null;

       f (safetyUserState. s sAnt soc al()) {
        ant soc al = true;
      }
       f (safetyUserState. s sNsfw()) {
        nsfw = true;
      }
       f (safetyUserState. sSet sProtected() && safetyUserState. s sProtected()) {
         sProtected = true;
      }

      return new UserUpdate(user d, ant soc al, nsfw,  sProtected);
    }
  }

  /**
   * Bu lds a user table from an HDFS user snapshot.
   * @return T  table, or noth ng  f so th ng  nt wrong.
   */
  publ c Opt onal<UserTable> bu ld(Pred cate<Long> userF lter) {
    userTable = UserTable.newTableW hDefaultCapac yAndPred cate(userF lter);
    nsfwCount = 0;
    ant soc alCount = 0;
     sProtectedCount = 0;

     f (snapshotBaseD r == null || snapshotBaseD r. sEmpty()) {
      LOG. nfo("No snapshot d rectory. Can't bu ld user table.");
      return Opt onal.empty();
    }

    LOG. nfo("Start ng to bu ld user table.");

    Stream<UserUpdate> stream = null;

    try {
      setSnapshotPath();

      stream = getUserUpdates();
      stream.forEach(t :: nsertUser);
    } catch ( OExcept on e) {
      LOG.error(" OExcept on wh le bu ld ng table: {}", e.get ssage(), e);

      return Opt onal.empty();
    } f nally {
       f (stream != null) {
        stream.close();
      }
    }

    LOG. nfo("Bu lt user table w h {} users, {} nsfw, {} ant soc al and {} protected.",
        userTable.getNumUsers nTable(),
        nsfwCount,
        ant soc alCount,
         sProtectedCount);

    try {
      userTable.setLastRecordT  stamp(readT  stampOfLastSeenUpdateFromSnapshot());
    } catch ( OExcept on e) {
      LOG.error(" OExcept on read ng t  stamp of last update: {}", e.get ssage(), e);
      return Opt onal.empty();
    }

    LOG. nfo("Sett ng last record t  stamp to {}.", userTable.getLastRecordT  stamp());

    return Opt onal.of(userTable);
  }

  pr vate vo d setSnapshotPath() {
    snapshotDataPath =
        new MostRecentGoodSafetyUserStateS ce(
            snapshotBaseD r,
            DATA_D R,
            METADATA_D R,
            DateRange.apply(
                R chDate.now().$m nus(H s.apply(MAX_DAYS_TO_CHECK * 24)),
                R chDate.now())
        ).part  onHdfsPaths(new HdfsConf gurat on())
         ._1()
         . ad()
         .replaceAll("\\*$", "");
    snapshot taDataPath = snapshotDataPath.replace(DATA_D R, METADATA_D R);

    LOG. nfo("Snapshot data path: {}", snapshotDataPath);
    LOG. nfo("Snapshot  tadata path: {}", snapshot taDataPath);
  }

  pr vate Stream<UserUpdate> getUserUpdates() throws  OExcept on {
    F leSystem fs = F leSystem.get(new Conf gurat on());
    L st<Str ng> lzoF les =
        Arrays.stream(fs.l stStatus(new Path(snapshotDataPath),
                                    path -> path.getNa ().startsW h("part-")))
              .map(f leStatus -> Path.getPathW houtSc  AndAuthor y(f leStatus.getPath())
                                     .toStr ng())
              .collect(Collectors.toL st());

    f nal LzoThr ftBlockF leReader<SafetyUserState> thr ftReader =
        new LzoThr ftBlockF leReader<>(lzoF les, SafetyUserState.class, null);

     erator<UserUpdate>  er = new  erator<UserUpdate>() {
      pr vate SafetyUserState next;

      @Overr de
      publ c boolean hasNext() {
         f (next != null) {
          return true;
        }

        do {
          try {
            next = thr ftReader.readNext();
          } catch ( OExcept on e) {
            throw new Runt  Except on(e);
          }
        } wh le (next == null && !thr ftReader. sExhausted());
        return next != null;
      }

      @Overr de
      publ c UserUpdate next() {
         f (next != null || hasNext()) {
          UserUpdate userUpdate = UserUpdate.fromUserState(next);
          next = null;
          return userUpdate;
        }
        throw new NoSuchEle ntExcept on();
      }
    };

    return StreamSupport
        .stream(
            Spl erators.spl eratorUnknownS ze( er, Spl erator.ORDERED | Spl erator.NONNULL),
            false)
        .onClose(thr ftReader::stop);
  }

  pr vate long readT  stampOfLastSeenUpdateFromSnapshot() throws  OExcept on {
    Str ng t  stampF le = snapshot taDataPath + "part-00000";
    BufferedReader buffer = new BufferedReader(new  nputStreamReader(
        HdfsUt ls.get nputStreamSuppl er(t  stampF le).openStream()));

    long t  stampM ll s = Long.parseLong(buffer.readL ne());
    LOG. nfo("read t  stamp {} from HDFS:{}", t  stampM ll s, t  stampF le);

    T   t   = T  .fromM ll seconds(t  stampM ll s)
                    .m nus(Durat on.fromT  Un (10, T  Un .M NUTES));
    return t  . nM ll seconds();
  }

  pr vate vo d  nsertUser(UserUpdate userUpdate) {
     f (userUpdate == null) {
      return;
    }

     f (userUpdate.ant soc al != null) {
      userTable.set(
          userUpdate.user d,
          UserTable.ANT SOC AL_B T,
          userUpdate.ant soc al);
      ant soc alCount++;
    }

     f (userUpdate.nsfw != null) {
      userTable.set(
          userUpdate.user d,
          UserTable.NSFW_B T,
          userUpdate.nsfw);
      nsfwCount++;
    }

     f (userUpdate. sProtected != null) {
      userTable.set(
          userUpdate.user d,
          UserTable. S_PROTECTED_B T,
          userUpdate. sProtected);
       sProtectedCount++;
    }
  }
}
