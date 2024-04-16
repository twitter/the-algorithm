package com.tw ter.search.earlyb rd.search.quer es;

 mport java. o. OExcept on;

 mport org.apac .lucene. ndex.LeafReader;
 mport org.apac .lucene.search.Doc dSet;
 mport org.apac .lucene.search.Doc dSet erator;
 mport org.apac .lucene.ut l.B s;
 mport org.apac .lucene.ut l.RamUsageEst mator;

 mport com.tw ter.search.core.earlyb rd. ndex.ut l.AllDocs erator;

publ c f nal class MatchAllDoc dSet extends Doc dSet {
  pr vate f nal LeafReader reader;

  publ c MatchAllDoc dSet(LeafReader reader) {
    t .reader = reader;
  }

  @Overr de
  publ c Doc dSet erator  erator() throws  OExcept on {
    return new AllDocs erator(reader);
  }

  @Overr de
  publ c B s b s() throws  OExcept on {
    return new B s() {
      @Overr de
      publ c boolean get( nt  ndex) {
        return true;
      }

      @Overr de
      publ c  nt length() {
        return reader.maxDoc();
      }
    };
  }

  @Overr de
  publ c long ramBytesUsed() {
    return RamUsageEst mator.shallowS zeOf(t );
  }
}
