package com.tw ter.search.earlyb rd.search;

 mport java. o. OExcept on;
 mport java.ut l.Comparator;
 mport java.ut l.HashSet;
 mport java.ut l.Set;
 mport java.ut l.SortedSet;
 mport java.ut l.TreeSet;

 mport com.google.common.annotat ons.V s bleForTest ng;

 mport org.apac .commons.lang.mutable.Mutable nt;
 mport org.apac .lucene. ndex. ndexReader;
 mport org.apac .lucene. ndex.Nu r cDocValues;
 mport org.apac .lucene. ndex.Term;
 mport org.apac .lucene.search.Query;
 mport org.apac .lucene.search.ScoreMode;

 mport com.tw ter.common_ nternal.collect ons.RandomAccessPr or yQueue;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant;
 mport com.tw ter.search.common.search.Tw ter ndexSearc r;
 mport com.tw ter.search.common.ut l.analys s.LongTermAttr bute mpl;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntAtom cReader;

publ c class Ant Gam ngF lter {
  pr vate  nterface Acceptor {
    boolean accept( nt  nternalDoc D) throws  OExcept on;
  }

  pr vate Nu r cDocValues userReputat on;
  pr vate Nu r cDocValues fromUser Ds;

  pr vate f nal Query luceneQuery;

  pr vate boolean termsExtracted = false;
  pr vate f nal Set<Term> queryTerms;

  //    gnore t se user  ds for ant -gam ng f lter ng, because t y  re expl c ly quer ed for
  pr vate Set<Long> seg ntUser DWh el st = null;
  //   gat r t  wh el sted user Ds from all seg nts  re
  pr vate Set<Long> globalUser DWh el st = null;

  /**
   * Used to track t  number of occurrences of a part cular user.
   */
  pr vate stat c f nal class UserCount
       mple nts RandomAccessPr or yQueue.S gnatureProv der<Long> {
    pr vate long user D;
    pr vate  nt count;

    @Overr de
    publ c Long getS gnature() {
      return user D;
    }

    @Overr de
    publ c vo d clear() {
      user D = 0;
      count = 0;
    }
  }

  pr vate stat c f nal Comparator<UserCount> USER_COUNT_COMPARATOR =
      (d1, d2) -> d1.count == d2.count ? Long.compare(d1.user D, d2.user D) : d1.count - d2.count;

  pr vate f nal RandomAccessPr or yQueue<UserCount, Long> pr or yQueue =
      new RandomAccessPr or yQueue<UserCount, Long>(1024, USER_COUNT_COMPARATOR) {
    @Overr de
    protected UserCount getSent nelObject() {
      return new UserCount();
    }
  };

  pr vate f nal Acceptor acceptor;
  pr vate f nal  nt maxH sPerUser;

  /**
   * Creates an Ant Gam ngF lter that e  r accepts or rejects t ets from all users.
   * T   thod should only be called  n tests.
   *
   * @param alwaysValue Determ nes  f t ets should always be accepted or rejected.
   * @return An Ant Gam ngF lter that e  r accepts or rejects t ets from all users.
   */
  @V s bleForTest ng
  publ c stat c Ant Gam ngF lter newMock(boolean alwaysValue) {
    return new Ant Gam ngF lter(alwaysValue) {
      @Overr de
      publ c vo d startSeg nt(Earlyb rd ndexSeg ntAtom cReader reader) {
      }
    };
  }

  pr vate Ant Gam ngF lter(boolean alwaysValue) {
    acceptor =  nternalDoc D -> alwaysValue;
    maxH sPerUser =  nteger.MAX_VALUE;
    termsExtracted = true;
    luceneQuery = null;
    queryTerms = null;
  }

  publ c Ant Gam ngF lter( nt maxH sPerUser,  nt maxT epCred, Query luceneQuery) {
    t .maxH sPerUser = maxH sPerUser;
    t .luceneQuery = luceneQuery;

     f (maxT epCred != -1) {
      t .acceptor =  nternalDoc D -> {
        long userReputat onVal =
            userReputat on.advanceExact( nternalDoc D) ? userReputat on.longValue() : 0L;
        return ((byte) userReputat onVal > maxT epCred) || acceptUser( nternalDoc D);
      };
    } else {
      t .acceptor = t ::acceptUser;
    }

    t .queryTerms = new HashSet<>();
  }

  publ c Set<Long> getUser DWh el st() {
    return globalUser DWh el st;
  }

  pr vate boolean acceptUser( nt  nternalDoc D) throws  OExcept on {
    f nal long fromUser D = getUser d( nternalDoc D);
    f nal Mutable nt freq = new Mutable nt();
    // try to  ncre nt UserCount for an user already ex st  n t  pr or y queue.
    boolean  ncre nted = pr or yQueue. ncre ntEle nt(
        fromUser D, ele nt -> freq.setValue(++ele nt.count));

    //  f not  ncre nted,    ans t  user node does not ex st  n t  pr or y queue yet.
     f (! ncre nted) {
      pr or yQueue.updateTop(ele nt -> {
        ele nt.user D = fromUser D;
        ele nt.count = 1;
        freq.setValue(ele nt.count);
      });
    }

     f (freq. ntValue() <= maxH sPerUser) {
      return true;
    } else  f (seg ntUser DWh el st == null) {
      return false;
    }
    return seg ntUser DWh el st.conta ns(fromUser D);
  }

  /**
   *  n  al zes t  f lter w h t  new feature s ce. T   thod should be called every t   an
   * earlyb rd searc r starts search ng  n a new seg nt.
   *
   * @param reader T  reader for t  new seg nt.
   */
  publ c vo d startSeg nt(Earlyb rd ndexSeg ntAtom cReader reader) throws  OExcept on {
     f (!termsExtracted) {
      extractTerms(reader);
    }

    fromUser Ds =
        reader.getNu r cDocValues(Earlyb rdF eldConstant.FROM_USER_ D_CSF.getF eldNa ());

    // f ll t   d wh el st for t  current seg nt.   n  al ze laz ly.
    seg ntUser DWh el st = null;

    SortedSet< nteger> sortedFromUserDoc ds = new TreeSet<>();
    for (Term t : queryTerms) {
       f (t.f eld().equals(Earlyb rdF eldConstant.FROM_USER_ D_F ELD.getF eldNa ())) {
        // Add t  operand of t  from_user_ d operator to t  wh el st
        long fromUser D = LongTermAttr bute mpl.copyBytesRefToLong(t.bytes());
        addUserToWh el sts(fromUser D);
      } else  f (t.f eld().equals(Earlyb rdF eldConstant.FROM_USER_F ELD.getF eldNa ())) {
        // For a [from X] f lter,   need to f nd a docu nt that has t  from_user f eld set to X,
        // and t n   need to get t  value of t  from_user_ d f eld for that docu nt and add  
        // to t  wh el st.   can get t  from_user_ d value from t  fromUser Ds Nu r cDocValues
        //  nstance, but   need to traverse    n  ncreas ng order of doc  Ds. So   add a doc  D
        // for each term to a sorted set for now, and t n   traverse    n  ncreas ng doc  D order
        // and add t  from_user_ d values for those docs to t  wh el st.
         nt f rst nternalDoc D = reader.getNe stDoc D(t);
         f (f rst nternalDoc D != Earlyb rd ndexSeg ntAtom cReader.TERM_NOT_FOUND) {
          sortedFromUserDoc ds.add(f rst nternalDoc D);
        }
      }
    }

    for ( nt fromUserDoc d : sortedFromUserDoc ds) {
      addUserToWh el sts(getUser d(fromUserDoc d));
    }

    userReputat on =
        reader.getNu r cDocValues(Earlyb rdF eldConstant.USER_REPUTAT ON.getF eldNa ());

    // Reset t  fromUser Ds Nu r cDocValues so that t  acceptor can use   to  erate over docs.
    fromUser Ds =
        reader.getNu r cDocValues(Earlyb rdF eldConstant.FROM_USER_ D_CSF.getF eldNa ());
  }

  pr vate vo d extractTerms( ndexReader reader) throws  OExcept on {
    Query query = luceneQuery;
    for (Query rewr tenQuery = query.rewr e(reader); rewr tenQuery != query;
         rewr tenQuery = query.rewr e(reader)) {
      query = rewr tenQuery;
    }

    // Create a new Tw ter ndexSearc r  nstance  re  nstead of an  ndexSearc r  nstance, to use
    // t  Tw ter ndexSearc r.collect onStat st cs()  mple ntat on.
    query.create  ght(new Tw ter ndexSearc r(reader), ScoreMode.COMPLETE, 1.0f)
        .extractTerms(queryTerms);
    termsExtracted = true;
  }

  publ c boolean accept( nt  nternalDoc D) throws  OExcept on {
    return acceptor.accept( nternalDoc D);
  }

  pr vate vo d addUserToWh el sts(long user D) {
     f (t .seg ntUser DWh el st == null) {
      t .seg ntUser DWh el st = new HashSet<>();
    }
     f (t .globalUser DWh el st == null) {
      t .globalUser DWh el st = new HashSet<>();
    }
    t .seg ntUser DWh el st.add(user D);
    t .globalUser DWh el st.add(user D);
  }

  @V s bleForTest ng
  protected long getUser d( nt  nternalDoc d) throws  OExcept on {
    return fromUser Ds.advanceExact( nternalDoc d) ? fromUser Ds.longValue() : 0L;
  }
}
