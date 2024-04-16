package com.tw ter.search.core.earlyb rd. ndex. nverted;

 mport java. o. OExcept on;
 mport java.ut l.Comparator;

 mport javax.annotat on.Nullable;

 mport com.google.common.base.Precond  ons;

 mport org.apac .lucene. ndex.Terms;
 mport org.apac .lucene. ndex.TermsEnum;
 mport org.apac .lucene.ut l.BytesRef;
 mport org.apac .lucene.ut l.Str ng lper;

 mport com.tw ter.search.common.hashtable.HashTable;
 mport com.tw ter.search.common.sc ma.base.Earlyb rdF eldType;
 mport com.tw ter.search.common.ut l.hash.KeysS ce;
 mport com.tw ter.search.common.ut l. o.flushable.DataDeser al zer;
 mport com.tw ter.search.common.ut l. o.flushable.DataSer al zer;
 mport com.tw ter.search.common.ut l. o.flushable.Flush nfo;
 mport com.tw ter.search.common.ut l. o.flushable.Flushable;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntAtom cReader;

publ c class  nvertedRealt   ndex extends  nverted ndex {
  publ c stat c f nal  nt F XED_HASH_SEED = 0;

  publ c f nal class TermHashTable extends HashTable<BytesRef> {

    pr vate f nal TermPo nterEncod ng termPo nterEncod ng;

    publ c TermHashTable( nt s ze, TermPo nterEncod ng termPo nterEncod ng) {
      super(s ze);
      t .termPo nterEncod ng = termPo nterEncod ng;
    }

    publ c TermHashTable( nt[] termsHash, TermPo nterEncod ng termPo nterEncod ng) {
      super(termsHash);
      t .termPo nterEncod ng = termPo nterEncod ng;
    }

    @Overr de
    publ c boolean match em(BytesRef term,  nt cand dateTerm D) {
      return ByteTermUt ls.post ngEquals(
          getTermPool(),
          termPo nterEncod ng.getTextStart(termsArray.termPo nters[cand dateTerm D]), term);
    }

    @Overr de
    publ c  nt hashCodeFor em( nt  em D) {
      return ByteTermUt ls.hashCode(
          getTermPool(), termPo nterEncod ng.getTextStart(termsArray.termPo nters[ em D]));
    }

    /*
     * Use a f xed hash seed to compute t  hash code for t  g ven  em. T   s necessary because
     *   want t  TermHashTable to be cons stent for lookups  n  ndexes that have been flus d and
     * loaded across restarts and redeploys.
     *
     * Note: prev ously   used  em.hashcode(), ho ver that hash funct on rel es on t  seed value
     * Str ng lper.GOOD_FAST_HASH_SEED, wh ch  s  n  al zed to System.currentT  M ll s() w n t 
     * JVM process starts up.
     */
    publ c long lookup em(BytesRef  em) {
       nt  emHashCode = Str ng lper.murmurhash3_x86_32( em, F XED_HASH_SEED);

      return super.lookup em( em,  emHashCode);
    }
  }


  /**
   * Sk p l st comparator used by {@l nk #termsSk pL st}. T  key would be t  bytesRef of t  term,
   *   and t  value would be t  term D of a term.
   *
   *   Not ce t  comparator  s keep ng states,
   *   so d fferent threads CANNOT share t  sa  comparator.
   */
  publ c stat c f nal class TermsSk pL stComparator  mple nts Sk pL stComparator<BytesRef> {
    pr vate stat c f nal Comparator<BytesRef> BYTES_REF_COMPARATOR = Comparator.naturalOrder();

    pr vate stat c f nal  nt SENT NEL_VALUE = HashTable.EMPTY_SLOT;

    //  n  al z ng two BytesRef to use for later compar sons.
    //   Not ce d fferent threads cannot share t  sa  comparator.
    pr vate f nal BytesRef bytesRef1 = new BytesRef();
    pr vate f nal BytesRef bytesRef2 = new BytesRef();

    /**
     *   have to pass each part of t   ndex  n s nce dur ng load process, t  comparator
     *   needs to be bu ld before t   ndex.
     */
    pr vate f nal  nvertedRealt   ndex  nverted ndex;

    publ c TermsSk pL stComparator( nvertedRealt   ndex  nverted ndex) {
      t . nverted ndex =  nverted ndex;
    }

    @Overr de
    publ c  nt compareKeyW hValue(BytesRef key,  nt targetValue,  nt targetPos  on) {
      // No key could represent SENT NEL_VALUE and SENT NEL_VALUE  s greatest.
       f (targetValue == SENT NEL_VALUE) {
        return -1;
      } else {
        getTerm(targetValue, bytesRef1);
        return BYTES_REF_COMPARATOR.compare(key, bytesRef1);
      }
    }

    @Overr de
    publ c  nt compareValues( nt v1,  nt v2) {
      // SENT NEL_VALUE  s greatest.
       f (v1 != SENT NEL_VALUE && v2 != SENT NEL_VALUE) {
        getTerm(v1, bytesRef1);
        getTerm(v2, bytesRef2);
        return BYTES_REF_COMPARATOR.compare(bytesRef1, bytesRef2);
      } else  f (v1 == SENT NEL_VALUE && v2 == SENT NEL_VALUE) {
        return 0;
      } else  f (v1 == SENT NEL_VALUE) {
        return 1;
      } else {
        return -1;
      }
    }

    @Overr de
    publ c  nt getSent nelValue() {
      return SENT NEL_VALUE;
    }

    /**
     * Get t  term spec f ed by t  term D.
     *   T   thod should be t  sa  as {@l nk  nvertedRealt   ndex#getTerm}
     */
    pr vate vo d getTerm( nt term D, BytesRef text) {
       nverted ndex.getTerm(term D, text);
    }
  }

  pr vate stat c f nal  nt HASHMAP_S ZE = 64 * 1024;

  pr vate Sk pL stConta ner<BytesRef> termsSk pL st;

  pr vate f nal TermPo nterEncod ng termPo nterEncod ng;
  pr vate f nal ByteBlockPool termPool;
  pr vate f nal Sk pL stPost ngL st post ngL st;

  pr vate  nt numTerms;
  pr vate  nt numDocs;
  pr vate  nt sumTotalTermFreq;
  pr vate  nt sumTermDocFreq;
  pr vate  nt maxPos  on;

  pr vate volat le TermHashTable hashTable;
  pr vate TermsArray termsArray;

  /**
   * Creates a new  n- mory real-t    nverted  ndex for t  g ven f eld.
   */
  publ c  nvertedRealt   ndex(Earlyb rdF eldType f eldType,
                               TermPo nterEncod ng termPo nterEncod ng,
                               Str ng f eldNa ) {
    super(f eldType);
    t .termPool = new ByteBlockPool();

    t .termPo nterEncod ng = termPo nterEncod ng;
    t .hashTable = new TermHashTable(HASHMAP_S ZE, termPo nterEncod ng);

    t .post ngL st = new Sk pL stPost ngL st(
        f eldType.hasPos  ons()
            ? Sk pL stConta ner.HasPos  ons.YES
            : Sk pL stConta ner.HasPos  ons.NO,
        f eldType. sStorePerPos  onPayloads()
            ? Sk pL stConta ner.HasPayloads.YES
            : Sk pL stConta ner.HasPayloads.NO,
        f eldNa );

    t .termsArray = new TermsArray(
        HASHMAP_S ZE, f eldType. sStoreFacetOffens veCounters());

    // Create termsSk pL st to ma nta n order  f f eld  s support ordered terms.
     f (f eldType. sSupportOrderedTerms()) {
      // Terms sk p l st does not support pos  on.
      t .termsSk pL st = new Sk pL stConta ner<>(
          new TermsSk pL stComparator(t ),
          Sk pL stConta ner.HasPos  ons.NO,
          Sk pL stConta ner.HasPayloads.NO,
          "terms");
      t .termsSk pL st.newSk pL st();
    } else {
      t .termsSk pL st = null;
    }
  }

  vo d setTermsSk pL st(Sk pL stConta ner<BytesRef> termsSk pL st) {
    t .termsSk pL st = termsSk pL st;
  }

  Sk pL stConta ner<BytesRef> getTermsSk pL st() {
    return termsSk pL st;
  }

  pr vate  nvertedRealt   ndex(
      Earlyb rdF eldType f eldType,
       nt numTerms,
       nt numDocs,
       nt sumTermDocFreq,
       nt sumTotalTermFreq,
       nt maxPos  on,
       nt[] termsHash,
      TermsArray termsArray,
      ByteBlockPool termPool,
      TermPo nterEncod ng termPo nterEncod ng,
      Sk pL stPost ngL st post ngL st) {
    super(f eldType);
    t .numTerms = numTerms;
    t .numDocs = numDocs;
    t .sumTermDocFreq = sumTermDocFreq;
    t .sumTotalTermFreq = sumTotalTermFreq;
    t .maxPos  on = maxPos  on;
    t .termsArray = termsArray;
    t .termPool = termPool;
    t .termPo nterEncod ng = termPo nterEncod ng;
    t .hashTable = new TermHashTable(termsHash, termPo nterEncod ng);
    t .post ngL st = post ngL st;
  }

  vo d  nsertToTermsSk pL st(BytesRef termBytesRef,  nt term D) {
     f (termsSk pL st != null) {
      // Use t  comparator passed  n wh le bu ld ng t  sk p l st s nce   only have one wr er.
      termsSk pL st. nsert(termBytesRef, term D, Sk pL stConta ner.F RST_L ST_HEAD);
    }
  }

  @Overr de
  publ c  nt getNumTerms() {
    return numTerms;
  }

  @Overr de
  publ c  nt getNumDocs() {
    return numDocs;
  }

  @Overr de
  publ c  nt getSumTotalTermFreq() {
    return sumTotalTermFreq;
  }

  @Overr de
  publ c  nt getSumTermDocFreq() {
    return sumTermDocFreq;
  }

  @Overr de
  publ c Terms createTerms( nt maxPubl s dPo nter) {
    return new Realt   ndexTerms(t , maxPubl s dPo nter);
  }

  @Overr de
  publ c TermsEnum createTermsEnum( nt maxPubl s dPo nter) {
    // Use Sk pL st n moryTermsEnum  f termsSk pL st  s not null, wh ch  nd cates f eld requ red
    // ordered term.
     f (termsSk pL st == null) {
      return new Realt   ndexTerms. n moryTermsEnum(t , maxPubl s dPo nter);
    } else {
      return new Realt   ndexTerms.Sk pL st n moryTermsEnum(t , maxPubl s dPo nter);
    }
  }

   nt getPost ngL stPo nter( nt term D) {
    return termsArray.getPost ngsPo nter(term D);
  }

  @Overr de
  publ c  nt getLargestDoc DForTerm( nt term D) {
     f (term D == Earlyb rd ndexSeg ntAtom cReader.TERM_NOT_FOUND) {
      return TermsArray. NVAL D;
    } else {
      return post ngL st.getDoc DFromPost ng(termsArray.largestPost ngs[term D]);
    }
  }

  @Overr de
  publ c  nt getDF( nt term D) {
     f (term D == HashTable.EMPTY_SLOT) {
      return 0;
    } else {
      return t .post ngL st.getDF(term D, termsArray);
    }
  }

  @Overr de
  publ c  nt getMaxPubl s dPo nter() {
    return t .post ngL st.getMaxPubl s dPo nter();
  }

  @Overr de
  publ c  nt lookupTerm(BytesRef term) {
    return HashTable.decode em d(hashTable.lookup em(term));
  }

  @Overr de
  publ c FacetLabelAccessor getLabelAccessor() {
    f nal TermsArray termsArrayCopy = t .termsArray;

    return new FacetLabelAccessor() {
      @Overr de protected boolean seek(long term D) {
         f (term D == HashTable.EMPTY_SLOT) {
          return false;
        }
         nt termPo nter = termsArrayCopy.termPo nters[( nt) term D];
        hasTermPayload = termPo nterEncod ng.hasPayload(termPo nter);
         nt textStart = termPo nterEncod ng.getTextStart(termPo nter);
         nt termPayloadStart = ByteTermUt ls.setBytesRef(termPool, termRef, textStart);
         f (hasTermPayload) {
          ByteTermUt ls.setBytesRef(termPool, termPayload, termPayloadStart);
        }
        offens veCount = termsArrayCopy.offens veCounters != null
            ? termsArrayCopy.offens veCounters[( nt) term D] : 0;

        return true;
      }
    };
  }

  @Overr de
  publ c boolean hasMaxPubl s dPo nter() {
    return true;
  }

  @Overr de
  publ c vo d getTerm( nt term D, BytesRef text) {
    getTerm(term D, text, termsArray, termPo nterEncod ng, termPool);
  }

  /**
   * Extract to  lper  thod so t  log c can be shared w h
   *   {@l nk TermsSk pL stComparator#getTerm}
   */
  pr vate stat c vo d getTerm( nt term D, BytesRef text,
                              TermsArray termsArray,
                              TermPo nterEncod ng termPo nterEncod ng,
                              ByteBlockPool termPool) {
     nt textStart = termPo nterEncod ng.getTextStart(termsArray.termPo nters[term D]);
    ByteTermUt ls.setBytesRef(termPool, text, textStart);
  }

  /**
   * Called w n post ngs hash  s too small (> 50% occup ed).
   */
  vo d rehashPost ngs( nt newS ze) {
    TermHashTable newTable = new TermHashTable(newS ze, termPo nterEncod ng);
    hashTable.rehash(newTable);
    hashTable = newTable;
  }

  /**
   * Returns per-term array conta n ng t  number of docu nts  ndexed w h that term that  re
   * cons dered to be offens ve.
   */
  @Nullable
   nt[] getOffens veCounters() {
    return t .termsArray.offens veCounters;
  }

  /**
   * Returns access to all t  terms  n t   ndex as a {@l nk KeysS ce}.
   */
  publ c KeysS ce getKeysS ce() {
    f nal  nt localNumTerms = t .numTerms;
    f nal TermsArray termsArrayCopy = t .termsArray;

    return new KeysS ce() {
      pr vate  nt term D = 0;
      pr vate BytesRef text = new BytesRef();

      @Overr de
      publ c  nt getNumberOfKeys() {
        return localNumTerms;
      }

      /** Must not be called more often than getNumberOfKeys() before rew nd()  s called */
      @Overr de
      publ c BytesRef nextKey() {
        Precond  ons.c ckState(term D < localNumTerms);
         nt textStart = termPo nterEncod ng.getTextStart(termsArrayCopy.termPo nters[term D]);
        ByteTermUt ls.setBytesRef(termPool, text, textStart);
        term D++;
        return text;
      }

      @Overr de
      publ c vo d rew nd() {
        term D = 0;
      }
    };
  }

  /**
   * Returns byte pool conta n ng term text for all terms  n t   ndex.
   */
  publ c ByteBlockPool getTermPool() {
    return t .termPool;
  }

  /**
   * Returns per-term array conta n ng po nters to w re t  text of each term  s stored  n t 
   * byte pool returned by {@l nk #getTermPool()}.
   */
  publ c  nt[] getTermPo nters() {
    return t .termsArray.termPo nters;
  }

  /**
   * Returns t  hash table used to look up terms  n t   ndex.
   */
   nvertedRealt   ndex.TermHashTable getHashTable() {
    return hashTable;
  }


  TermsArray getTermsArray() {
    return termsArray;
  }

  TermsArray growTermsArray() {
    termsArray = termsArray.grow();
    return termsArray;
  }

  @SuppressWarn ngs("unc cked")
  @Overr de
  publ c FlushHandler getFlushHandler() {
    return new FlushHandler(t );
  }

  TermPo nterEncod ng getTermPo nterEncod ng() {
    return termPo nterEncod ng;
  }

  Sk pL stPost ngL st getPost ngL st() {
    return post ngL st;
  }

  vo d  ncre ntNumTerms() {
    numTerms++;
  }

  vo d  ncre ntSumTotalTermFreq() {
    sumTotalTermFreq++;
  }

  publ c vo d  ncre ntSumTermDocFreq() {
    sumTermDocFreq++;
  }

  publ c vo d  ncre ntNumDocs() {
    numDocs++;
  }

  vo d setNumDocs( nt numDocs) {
    t .numDocs = numDocs;
  }

  vo d adjustMaxPos  on( nt pos  on) {
     f (pos  on > maxPos  on) {
      maxPos  on = pos  on;
    }
  }

   nt getMaxPos  on() {
    return maxPos  on;
  }

  publ c stat c class FlushHandler extends Flushable.Handler< nvertedRealt   ndex> {
    pr vate stat c f nal Str ng NUM_DOCS_PROP_NAME = "numDocs";
    pr vate stat c f nal Str ng SUM_TOTAL_TERM_FREQ_PROP_NAME = "sumTotalTermFreq";
    pr vate stat c f nal Str ng SUM_TERM_DOC_FREQ_PROP_NAME = "sumTermDocFreq";
    pr vate stat c f nal Str ng NUM_TERMS_PROP_NAME = "numTerms";
    pr vate stat c f nal Str ng POST NG_L ST_PROP_NAME = "post ngL st";
    pr vate stat c f nal Str ng TERMS_SK P_L ST_PROP_NAME = "termsSk pL st";
    pr vate stat c f nal Str ng MAX_POS T ON = "maxPos  on";

    protected f nal Earlyb rdF eldType f eldType;
    protected f nal TermPo nterEncod ng termPo nterEncod ng;

    publ c FlushHandler(Earlyb rdF eldType f eldType,
                        TermPo nterEncod ng termPo nterEncod ng) {
      t .f eldType = f eldType;
      t .termPo nterEncod ng = termPo nterEncod ng;
    }

    publ c FlushHandler( nvertedRealt   ndex objectToFlush) {
      super(objectToFlush);
      t .f eldType = objectToFlush.f eldType;
      t .termPo nterEncod ng = objectToFlush.getTermPo nterEncod ng();
    }

    @Overr de
    protected vo d doFlush(Flush nfo flush nfo, DataSer al zer out)
        throws  OExcept on {
       nvertedRealt   ndex objectToFlush = getObjectToFlush();
      flush nfo.add ntProperty(NUM_TERMS_PROP_NAME, objectToFlush.getNumTerms());
      flush nfo.add ntProperty(NUM_DOCS_PROP_NAME, objectToFlush.numDocs);
      flush nfo.add ntProperty(SUM_TERM_DOC_FREQ_PROP_NAME, objectToFlush.sumTermDocFreq);
      flush nfo.add ntProperty(SUM_TOTAL_TERM_FREQ_PROP_NAME, objectToFlush.sumTotalTermFreq);
      flush nfo.add ntProperty(MAX_POS T ON, objectToFlush.maxPos  on);

      out.wr e ntArray(objectToFlush.hashTable.slots());
      objectToFlush.termsArray.getFlushHandler()
          .flush(flush nfo.newSubPropert es("termsArray"), out);
      objectToFlush.getTermPool().getFlushHandler()
          .flush(flush nfo.newSubPropert es("termPool"), out);
      objectToFlush.getPost ngL st().getFlushHandler()
          .flush(flush nfo.newSubPropert es(POST NG_L ST_PROP_NAME), out);

       f (f eldType. sSupportOrderedTerms()) {
        Precond  ons.c ckNotNull(objectToFlush.termsSk pL st);

        objectToFlush.termsSk pL st.getFlushHandler()
            .flush(flush nfo.newSubPropert es(TERMS_SK P_L ST_PROP_NAME), out);
      }
    }

    @Overr de
    protected  nvertedRealt   ndex doLoad(Flush nfo flush nfo, DataDeser al zer  n)
        throws  OExcept on {
       nt[] termsHash =  n.read ntArray();
      TermsArray termsArray = (new TermsArray.FlushHandler())
          .load(flush nfo.getSubPropert es("termsArray"),  n);
      ByteBlockPool termPool = (new ByteBlockPool.FlushHandler())
          .load(flush nfo.getSubPropert es("termPool"),  n);
      Sk pL stPost ngL st post ngL st = (new Sk pL stPost ngL st.FlushHandler())
          .load(flush nfo.getSubPropert es(POST NG_L ST_PROP_NAME),  n);

       nvertedRealt   ndex  ndex = new  nvertedRealt   ndex(
          f eldType,
          flush nfo.get ntProperty(NUM_TERMS_PROP_NAME),
          flush nfo.get ntProperty(NUM_DOCS_PROP_NAME),
          flush nfo.get ntProperty(SUM_TERM_DOC_FREQ_PROP_NAME),
          flush nfo.get ntProperty(SUM_TOTAL_TERM_FREQ_PROP_NAME),
          flush nfo.get ntProperty(MAX_POS T ON),
          termsHash,
          termsArray,
          termPool,
          termPo nterEncod ng,
          post ngL st);

       f (f eldType. sSupportOrderedTerms()) {
        Sk pL stComparator<BytesRef> comparator = new TermsSk pL stComparator( ndex);
         ndex.setTermsSk pL st((new Sk pL stConta ner.FlushHandler<>(comparator))
            .load(flush nfo.getSubPropert es(TERMS_SK P_L ST_PROP_NAME),  n));
      }

      return  ndex;
    }
  }
}
