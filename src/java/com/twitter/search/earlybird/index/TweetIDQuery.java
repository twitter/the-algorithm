package com.tw ter.search.earlyb rd. ndex;

 mport java. o. OExcept on;
 mport java.ut l.Arrays;
 mport java.ut l.Set;

 mport com.google.common.collect.Sets;

 mport org.apac .lucene. ndex.LeafReaderContext;
 mport org.apac .lucene.search.Doc dSet erator;
 mport org.apac .lucene.search. ndexSearc r;
 mport org.apac .lucene.search.Query;
 mport org.apac .lucene.search.ScoreMode;
 mport org.apac .lucene.search.  ght;

 mport com.tw ter.search.common.query.DefaultF lter  ght;
 mport com.tw ter.search.common.search. ntArrayDoc dSet erator;
 mport com.tw ter.search.core.earlyb rd. ndex.Doc DToT et DMapper;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntAtom cReader;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntData;

publ c class T et DQuery extends Query {
  pr vate f nal Set<Long> t et Ds = Sets.newHashSet();

  publ c T et DQuery(long... t et Ds) {
    for (long t et D : t et Ds) {
      t .t et Ds.add(t et D);
    }
  }

  @Overr de
  publ c   ght create  ght( ndexSearc r searc r, ScoreMode scoreMode, float boost) {
    return new DefaultF lter  ght(t ) {
      @Overr de
      protected Doc dSet erator getDoc dSet erator(LeafReaderContext context) throws  OExcept on {
        Earlyb rd ndexSeg ntData seg ntData =
            ((Earlyb rd ndexSeg ntAtom cReader) context.reader()).getSeg ntData();
        Doc DToT et DMapper doc dToT et dMapper = seg ntData.getDoc DToT et DMapper();

        Set< nteger> set = Sets.newHashSet();
        for (long t et D : t et Ds) {
           nt doc D = doc dToT et dMapper.getDoc D(t et D);
           f (doc D != Doc DToT et DMapper. D_NOT_FOUND) {
            set.add(doc D);
          }
        }

         f (set. sEmpty()) {
          return Doc dSet erator.empty();
        }

         nt[] doc Ds = new  nt[set.s ze()];
         nt   = 0;
        for ( nt doc D : set) {
          doc Ds[ ++] = doc D;
        }
        Arrays.sort(doc Ds);
        return new  ntArrayDoc dSet erator(doc Ds);
      }
    };
  }

  @Overr de
  publ c  nt hashCode() {
    return t et Ds.hashCode();
  }

  @Overr de
  publ c boolean equals(Object obj) {
     f (!(obj  nstanceof T et DQuery)) {
      return false;
    }

    return t et Ds.equals(T et DQuery.class.cast(obj).t et Ds);
  }

  @Overr de
  publ c Str ng toStr ng(Str ng f eld) {
    return "TWEET_ D_QUERY: " + t et Ds;
  }
}
