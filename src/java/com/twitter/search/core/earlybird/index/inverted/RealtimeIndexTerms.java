package com.tw ter.search.core.earlyb rd. ndex. nverted;

 mport java.ut l. erator;
 mport java.ut l.TreeSet;

 mport com.google.common.base.Precond  ons;

 mport org.apac .lucene. ndex.BaseTermsEnum;
 mport org.apac .lucene. ndex. mpactsEnum;
 mport org.apac .lucene. ndex.Post ngsEnum;
 mport org.apac .lucene. ndex.Slow mpactsEnum;
 mport org.apac .lucene. ndex.Terms;
 mport org.apac .lucene. ndex.TermsEnum;
 mport org.apac .lucene.ut l.BytesRef;

 mport com.tw ter.search.common.hashtable.HashTable;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common.ut l.hash.KeysS ce;

publ c class Realt   ndexTerms extends Terms {
  // Call ng  n moryTermsEnum.next() creates a full copy of t  ent re term d ct onary, and can
  // be qu e expens ve.   don't expect t se calls to happen, and t y shpould not happen on t 
  // regular read path.   stat t m  re just  n case to see  f t re  s any unexpected usage.
  pr vate stat c f nal SearchCounter TERMS_ENUM_NEXT_CALLS =
      SearchCounter.export(" n_ mory_terms_enum_next_calls");
  pr vate stat c f nal SearchCounter TERMS_ENUM_CREATE_TERM_SET =
      SearchCounter.export(" n_ mory_terms_enum_next_create_term_set");
  pr vate stat c f nal SearchCounter TERMS_ENUM_CREATE_TERM_SET_S ZE =
      SearchCounter.export(" n_ mory_terms_enum_next_create_term_set_s ze");

  pr vate f nal  nvertedRealt   ndex  ndex;
  pr vate f nal  nt maxPubl s dPo nter;

  publ c Realt   ndexTerms( nvertedRealt   ndex  ndex,  nt maxPubl s dPo nter) {
    t . ndex =  ndex;
    t .maxPubl s dPo nter = maxPubl s dPo nter;
  }

  @Overr de
  publ c long s ze() {
    return  ndex.getNumTerms();
  }

  @Overr de
  publ c TermsEnum  erator() {
    return  ndex.createTermsEnum(maxPubl s dPo nter);
  }

  /**
   * T  TermsEnum use a tree set to support {@l nk TermsEnum#next()}  thod. Ho ver, t   s not
   * eff c ent enough to support realt   operat on. {@l nk TermsEnum#seekCe l}  s not fully
   * supported  n t  termEnum.
   */
  publ c stat c class  n moryTermsEnum extends BaseTermsEnum {
    pr vate f nal  nvertedRealt   ndex  ndex;
    pr vate f nal  nt maxPubl s dPo nter;
    pr vate  nt term D = -1;
    pr vate BytesRef bytesRef = new BytesRef();
    pr vate  erator<BytesRef> term er;
    pr vate TreeSet<BytesRef> termSet;

    publ c  n moryTermsEnum( nvertedRealt   ndex  ndex,  nt maxPubl s dPo nter) {
      t . ndex =  ndex;
      t .maxPubl s dPo nter = maxPubl s dPo nter;
      term er = null;
    }

    @Overr de
    publ c  nt docFreq() {
      return  ndex.getDF(term D);
    }

    @Overr de
    publ c Post ngsEnum post ngs(Post ngsEnum reuse,  nt flags) {
       nt post ngsPo nter =  ndex.getPost ngL stPo nter(term D);
      return  ndex.getPost ngL st().post ngs(post ngsPo nter, docFreq(), maxPubl s dPo nter);
    }

    @Overr de
    publ c  mpactsEnum  mpacts( nt flags) {
      return new Slow mpactsEnum(post ngs(null, flags));
    }

    @Overr de
    publ c SeekStatus seekCe l(BytesRef text) {
      // Null fy term er.
      term er = null;

      term D =  ndex.lookupTerm(text);

       f (term D == -1) {
        return SeekStatus.END;
      } else {
         ndex.getTerm(term D, bytesRef);
        return SeekStatus.FOUND;
      }
    }

    @Overr de
    publ c BytesRef next() {
      TERMS_ENUM_NEXT_CALLS. ncre nt();
       f (termSet == null) {
        termSet = new TreeSet<>();
        KeysS ce keys ce =  ndex.getKeysS ce();
        keys ce.rew nd();
         nt numTerms = keys ce.getNumberOfKeys();
        for ( nt   = 0;   < numTerms; ++ ) {
          BytesRef ref = keys ce.nextKey();
          //   need to clone t  ref s nce t  keys ce  s reus ng t  returned BytesRef
          //  nstance and   are stor ng  
          termSet.add(ref.clone());
        }
        TERMS_ENUM_CREATE_TERM_SET. ncre nt();
        TERMS_ENUM_CREATE_TERM_SET_S ZE.add(numTerms);
      }

      // Construct term er from t  subset.
       f (term er == null) {
        term er = termSet.ta lSet(bytesRef, true). erator();
      }

       f (term er.hasNext()) {
        bytesRef = term er.next();
        term D =  ndex.lookupTerm(bytesRef);
      } else {
        term D = -1;
        bytesRef = null;
      }
      return bytesRef;
    }

    @Overr de
    publ c long ord() {
      return term D;
    }

    @Overr de
    publ c vo d seekExact(long ord) {
      // Null fy term er.
      term er = null;

       f (ord <  ndex.getNumTerms()) {
        term D = ( nt) ord;
         ndex.getTerm(term D, bytesRef);
      }
    }

    @Overr de
    publ c BytesRef term() {
      return bytesRef;
    }

    @Overr de
    publ c long totalTermFreq() {
      return docFreq();
    }
  }

  /**
   * T  TermsEnum use a {@l nk Sk pL stConta ner} backed termsSk pL st prov ded by
   * {@l nk  nvertedRealt   ndex} to supported ordered terms operat ons l ke
   * {@l nk TermsEnum#next()} and {@l nk TermsEnum#seekCe l}.
   */
  publ c stat c class Sk pL st n moryTermsEnum extends BaseTermsEnum {
    pr vate f nal  nvertedRealt   ndex  ndex;

    pr vate  nt term D = -1;
    pr vate BytesRef bytesRef = new BytesRef();
    pr vate  nt nextTerm DPo nter;

    /**
     * {@l nk #nextTerm DPo nter}  s used to record po nter to next terms D to accelerate
     * {@l nk #next}. Ho ver, {@l nk #seekCe l} and {@l nk #seekExact} may jump to an arb rary
     * term so t  {@l nk #nextTerm DPo nter} may not be correct, and t  flag  s used to c ck  f
     * t  happens.  f t  flag  s false, {@l nk #correctNextTerm DPo nter} should be called to
     * correct t  value.
     */
    pr vate boolean  sNextTerm DPo nterCorrect;

    pr vate f nal Sk pL stConta ner<BytesRef> termsSk pL st;
    pr vate f nal  nvertedRealt   ndex.TermsSk pL stComparator termsSk pL stComparator;
    pr vate f nal  nt maxPubl s dPo nter;

    /**
     * Creates a new {@l nk TermsEnum} for a sk p l st-based sorted real-t   term d ct onary.
     */
    publ c Sk pL st n moryTermsEnum( nvertedRealt   ndex  ndex,  nt maxPubl s dPo nter) {
      Precond  ons.c ckNotNull( ndex.getTermsSk pL st());

      t . ndex =  ndex;
      t .termsSk pL st =  ndex.getTermsSk pL st();

      // Each Terms Enum shall have t  r own comparators to be thread safe.
      t .termsSk pL stComparator =
          new  nvertedRealt   ndex.TermsSk pL stComparator( ndex);
      t .nextTerm DPo nter =
          termsSk pL st.getNextPo nter(Sk pL stConta ner.F RST_L ST_HEAD);
      t . sNextTerm DPo nterCorrect = true;
      t .maxPubl s dPo nter = maxPubl s dPo nter;
    }

    @Overr de
    publ c  nt docFreq() {
      return  ndex.getDF(term D);
    }

    @Overr de
    publ c Post ngsEnum post ngs(Post ngsEnum reuse,  nt flags) {
       nt post ngsPo nter =  ndex.getPost ngL stPo nter(term D);
      return  ndex.getPost ngL st().post ngs(post ngsPo nter, docFreq(), maxPubl s dPo nter);
    }

    @Overr de
    publ c  mpactsEnum  mpacts( nt flags) {
      return new Slow mpactsEnum(post ngs(null, flags));
    }

    @Overr de
    publ c SeekStatus seekCe l(BytesRef text) {
      // Next term po nter  s not correct anymore s nce seek ce l
      //   w ll jump to an arb rary term.
       sNextTerm DPo nterCorrect = false;

      // Do ng prec se lookup f rst.
      term D =  ndex.lookupTerm(text);

      // Do ng ce l lookup  f not found, ot rw se   are good.
       f (term D == -1) {
        return seekCe lW hSk pL st(text);
      } else {
         ndex.getTerm(term D, bytesRef);
        return SeekStatus.FOUND;
      }
    }

    /**
     * Do ng ce l terms search w h terms sk p l st.
     */
    pr vate SeekStatus seekCe lW hSk pL st(BytesRef text) {
       nt term DPo nter = termsSk pL st.searchCe l(text,
          Sk pL stConta ner.F RST_L ST_HEAD,
          termsSk pL stComparator,
          null);

      // End reac d but st ll cannot found a ce l term.
       f (term DPo nter == Sk pL stConta ner.F RST_L ST_HEAD) {
        term D = HashTable.EMPTY_SLOT;
        return SeekStatus.END;
      }

      term D = termsSk pL st.getValue(term DPo nter);

      // Set next term D po nter and  s correct flag.
      nextTerm DPo nter = termsSk pL st.getNextPo nter(term DPo nter);
       sNextTerm DPo nterCorrect = true;

      // Found a ce l term but not t  prec se match.
       ndex.getTerm(term D, bytesRef);
      return SeekStatus.NOT_FOUND;
    }

    /**
     * {@l nk #nextTerm DPo nter}  s used to record t  po nter to next term D. T   thod  s used
     * to correct {@l nk #nextTerm DPo nter} to correct value after {@l nk #seekCe l} or
     * {@l nk #seekExact} dropped current term to arb rary po nt.
     */
    pr vate vo d correctNextTerm DPo nter() {
      f nal  nt curTerm DPo nter = termsSk pL st.search(
          bytesRef,
          Sk pL stConta ner.F RST_L ST_HEAD,
          termsSk pL stComparator,
          null);
      // Must be able to f nd t  exact term.
      assert term D == HashTable.EMPTY_SLOT
          || term D == termsSk pL st.getValue(curTerm DPo nter);

      nextTerm DPo nter = termsSk pL st.getNextPo nter(curTerm DPo nter);
       sNextTerm DPo nterCorrect = true;
    }

    @Overr de
    publ c BytesRef next() {
      // Correct nextTerm DPo nter f rst  f not correct due to seekExact or seekCe l.
       f (! sNextTerm DPo nterCorrect) {
        correctNextTerm DPo nter();
      }

      // Sk p l st  s exhausted.
       f (nextTerm DPo nter == Sk pL stConta ner.F RST_L ST_HEAD) {
        term D = HashTable.EMPTY_SLOT;
        return null;
      }

      term D = termsSk pL st.getValue(nextTerm DPo nter);

       ndex.getTerm(term D, bytesRef);

      // Set next term D Po nter.
      nextTerm DPo nter = termsSk pL st.getNextPo nter(nextTerm DPo nter);
      return bytesRef;
    }

    @Overr de
    publ c long ord() {
      return term D;
    }

    @Overr de
    publ c vo d seekExact(long ord) {
       f (ord <  ndex.getNumTerms()) {
        term D = ( nt) ord;
         ndex.getTerm(term D, bytesRef);

        // Next term po nter  s not correct anymore s nce seek exact
        //   just jump to an arb rary term.
         sNextTerm DPo nterCorrect = false;
      }
    }

    @Overr de
    publ c BytesRef term() {
      return bytesRef;
    }

    @Overr de
    publ c long totalTermFreq() {
      return docFreq();
    }
  }

  @Overr de
  publ c long getSumTotalTermFreq() {
    return  ndex.getSumTotalTermFreq();
  }

  @Overr de
  publ c long getSumDocFreq() {
    return  ndex.getSumTermDocFreq();
  }

  @Overr de
  publ c  nt getDocCount() {
    return  ndex.getNumDocs();
  }

  @Overr de
  publ c boolean hasFreqs() {
    return true;
  }

  @Overr de
  publ c boolean hasOffsets() {
    return false;
  }

  @Overr de
  publ c boolean hasPos  ons() {
    return true;
  }

  @Overr de
  publ c boolean hasPayloads() {
    return true;
  }
}
