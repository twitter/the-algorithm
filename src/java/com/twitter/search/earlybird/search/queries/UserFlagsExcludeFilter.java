package com.tw ter.search.earlyb rd.search.quer es;

 mport java. o. OExcept on;

 mport org.apac .lucene. ndex.LeafReader;
 mport org.apac .lucene. ndex.LeafReaderContext;
 mport org.apac .lucene. ndex.Nu r cDocValues;
 mport org.apac .lucene.search.BooleanClause;
 mport org.apac .lucene.search.BooleanQuery;
 mport org.apac .lucene.search.Doc dSet erator;
 mport org.apac .lucene.search. ndexSearc r;
 mport org.apac .lucene.search.Query;
 mport org.apac .lucene.search.ScoreMode;
 mport org.apac .lucene.search.  ght;

 mport com.tw ter.search.common.query.DefaultF lter  ght;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant;
 mport com.tw ter.search.core.earlyb rd. ndex.ut l.AllDocs erator;
 mport com.tw ter.search.core.earlyb rd. ndex.ut l.RangeF lterD S ;
 mport com.tw ter.search.earlyb rd.common.userupdates.UserTable;

publ c f nal class UserFlagsExcludeF lter extends Query {
  /**
   * Returns a query that f lters h s based on t  r author flags.
   *
   * @param excludeAnt soc al Determ nes  f t  f lter should exclude h s from ant soc al users.
   * @param excludeOffens ve Determ nes  f t  f lter should exclude h s from offens ve users.
   * @param excludeProtected Determ nes  f t  f lter should exclude h s from protected users
   * @return A query that f lters h s based on t  r author flags.
   */
  publ c stat c Query getUserFlagsExcludeF lter(UserTable userTable,
                                                boolean excludeAnt soc al,
                                                boolean excludeOffens ve,
                                                boolean excludeProtected) {
    return new BooleanQuery.Bu lder()
        .add(new UserFlagsExcludeF lter(
                userTable, excludeAnt soc al, excludeOffens ve, excludeProtected),
            BooleanClause.Occur.F LTER)
        .bu ld();
  }

  pr vate f nal UserTable userTable;
  pr vate f nal boolean excludeAnt soc al;
  pr vate f nal boolean excludeOffens ve;
  pr vate f nal boolean excludeProtected;

  pr vate UserFlagsExcludeF lter(
      UserTable userTable,
      boolean excludeAnt soc al,
      boolean excludeOffens ve,
      boolean excludeProtected) {
    t .userTable = userTable;
    t .excludeAnt soc al = excludeAnt soc al;
    t .excludeOffens ve = excludeOffens ve;
    t .excludeProtected = excludeProtected;
  }

  @Overr de
  publ c  nt hashCode() {
    return (excludeAnt soc al ? 13 : 0) + (excludeOffens ve ? 1 : 0) + (excludeProtected ? 2 : 0);
  }

  @Overr de
  publ c boolean equals(Object obj) {
     f (!(obj  nstanceof UserFlagsExcludeF lter)) {
      return false;
    }

    UserFlagsExcludeF lter f lter = UserFlagsExcludeF lter.class.cast(obj);
    return (excludeAnt soc al == f lter.excludeAnt soc al)
        && (excludeOffens ve == f lter.excludeOffens ve)
        && (excludeProtected == f lter.excludeProtected);
  }

  @Overr de
  publ c Str ng toStr ng(Str ng f eld) {
    return "UserFlagsExcludeF lter";
  }

  @Overr de
  publ c   ght create  ght( ndexSearc r searc r, ScoreMode scoreMode, float boost) {
    return new DefaultF lter  ght(t ) {
      @Overr de
      protected Doc dSet erator getDoc dSet erator(LeafReaderContext context) throws  OExcept on {
        LeafReader reader = context.reader();
         f (userTable == null) {
          return new AllDocs erator(reader);
        }

        f nal  nt b s =
            (excludeAnt soc al ? UserTable.ANT SOC AL_B T : 0)
                | (excludeOffens ve ? UserTable.OFFENS VE_B T | UserTable.NSFW_B T : 0)
                | (excludeProtected ? UserTable. S_PROTECTED_B T : 0);
         f (b s != 0) {
          return new UserFlagsExcludeDoc dSet erator(reader, userTable) {
            @Overr de
            protected boolean c ckUserFlags(UserTable table, long user D) {
              return !table. sSet(user D, b s);
            }
          };
        }

        return new AllDocs erator(reader);
      }
    };
  }

  pr vate abstract stat c class UserFlagsExcludeDoc dSet erator extends RangeF lterD S  {
    pr vate f nal UserTable userTable;
    pr vate f nal Nu r cDocValues fromUser D;

    publ c UserFlagsExcludeDoc dSet erator(
        LeafReader  ndexReader, UserTable table) throws  OExcept on {
      super( ndexReader);
      userTable = table;
      fromUser D =
           ndexReader.getNu r cDocValues(Earlyb rdF eldConstant.FROM_USER_ D_CSF.getF eldNa ());
    }

    @Overr de
    protected boolean shouldReturnDoc() throws  OExcept on {
      return fromUser D.advanceExact(doc D())
          && c ckUserFlags(userTable, fromUser D.longValue());
    }

    protected abstract boolean c ckUserFlags(UserTable table, long user D);
  }
}
