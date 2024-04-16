package com.tw ter.search.core.earlyb rd. ndex. nverted;

 mport com.google.common.base.Precond  ons;

 mport org.apac .lucene.analys s.tokenattr butes.PayloadAttr bute;
 mport org.apac .lucene.analys s.tokenattr butes.TermToBytesRefAttr bute;
 mport org.apac .lucene.ut l.Attr buteS ce;
 mport org.apac .lucene.ut l.BytesRef;

 mport com.tw ter.search.common.hashtable.HashTable;
 mport com.tw ter.search.common.ut l.analys s.TermPayloadAttr bute;
 mport com.tw ter.search.core.earlyb rd.facets.FacetCount ngArrayWr er;
 mport com.tw ter.search.core.earlyb rd.facets.Facet DMap.FacetF eld;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rdRealt   ndexSeg ntWr er;

publ c class  nvertedRealt   ndexWr er
     mple nts Earlyb rdRealt   ndexSeg ntWr er. nvertedDocConsu r {
  pr vate f nal  nvertedRealt   ndex  nverted ndex;
  pr vate f nal FacetCount ngArrayWr er facetArray;
  pr vate f nal FacetF eld facetF eld;

  pr vate TermToBytesRefAttr bute termAtt;
  pr vate TermPayloadAttr bute termPayloadAtt;
  pr vate PayloadAttr bute payloadAtt;
  pr vate boolean currentDoc sOffens ve;

  /**
   * Creates a new wr er for wr  ng to an  nverted  n- mory real-t    ndex.
   */
  publ c  nvertedRealt   ndexWr er(
           nvertedRealt   ndex  ndex,
          FacetF eld facetF eld,
          FacetCount ngArrayWr er facetArray) {
    super();
    t . nverted ndex =  ndex;
    t .facetArray = facetArray;
    t .facetF eld = facetF eld;
  }

  @Overr de
  publ c vo d start(Attr buteS ce attr buteS ce, boolean doc sOffens ve) {
    termAtt = attr buteS ce.addAttr bute(TermToBytesRefAttr bute.class);
    termPayloadAtt = attr buteS ce.addAttr bute(TermPayloadAttr bute.class);
    payloadAtt = attr buteS ce.addAttr bute(PayloadAttr bute.class);
    currentDoc sOffens ve = doc sOffens ve;
  }

  /**
   * Adds a post ng to t  prov ded  nverted  ndex.
   *
   * @param termBytesRef  s a payload that  s stored w h t  term.    s only stored once for each
   *                     term.
   * @param post ngPayload  s a byte payload that w ll be stored separately for every post ng.
   * @return term  d of t  added post ng.
   */
  publ c stat c  nt  ndexTerm( nvertedRealt   ndex  nverted ndex, BytesRef termBytesRef,
       nt doc D,  nt pos  on, BytesRef termPayload,
      BytesRef post ngPayload, TermPo nterEncod ng termPo nterEncod ng) {

     nvertedRealt   ndex.TermHashTable hashTable =  nverted ndex.getHashTable();
    BaseByteBlockPool termPool =  nverted ndex.getTermPool();

    TermsArray termsArray =  nverted ndex.getTermsArray();

    long hashTable nfoForBytesRef = hashTable.lookup em(termBytesRef);
     nt term D = HashTable.decode em d(hashTable nfoForBytesRef);
     nt hashTableSlot = HashTable.decodeHashPos  on(hashTable nfoForBytesRef);

     nverted ndex.adjustMaxPos  on(pos  on);

     f (term D == HashTable.EMPTY_SLOT) {
      // F rst t     are see ng t  token s nce   last flus d t  hash.
      // t  LSB  n textStart denotes w t r t  term has a term payload
       nt textStart = ByteTermUt ls.copyToTermPool(termPool, termBytesRef);
      boolean hasTermPayload = termPayload != null;
       nt termPo nter = termPo nterEncod ng.encodeTermPo nter(textStart, hasTermPayload);

       f (hasTermPayload) {
        ByteTermUt ls.copyToTermPool(termPool, termPayload);
      }

      term D =  nverted ndex.getNumTerms();
       nverted ndex. ncre ntNumTerms();
       f (term D >= termsArray.getS ze()) {
        termsArray =  nverted ndex.growTermsArray();
      }

      termsArray.termPo nters[term D] = termPo nter;

      Precond  ons.c ckState(hashTable.slots()[hashTableSlot] == HashTable.EMPTY_SLOT);
      hashTable.setSlot(hashTableSlot, term D);

       f ( nverted ndex.getNumTerms() * 2 >= hashTable.numSlots()) {
         nverted ndex.rehashPost ngs(2 * hashTable.numSlots());
      }

      //  nsert term D  nto termsSk pL st.
       nverted ndex. nsertToTermsSk pL st(termBytesRef, term D);
    }

     nverted ndex. ncre ntSumTotalTermFreq();
     nverted ndex.getPost ngL st()
        .appendPost ng(term D, termsArray, doc D, pos  on, post ngPayload);

    return term D;
  }

  /**
   * Delete a post ng that was  nserted out of order.
   *
   * T  funct on needs work before    s used  n product on:
   * -   should take an  sDocOffens ve para ter so   can decre nt t  offens ve
   *   docu nt count for t  term.
   * -   doesn't allow t  sa  concurrency guarantees that t  ot r post ng  thods do.
   */
  publ c stat c vo d deletePost ng(
       nvertedRealt   ndex  nverted ndex, BytesRef termBytesRef,  nt doc D) {

    long hashTable nfoForBytesRef =  nverted ndex.getHashTable().lookup em(termBytesRef);
     nt term D = HashTable.decode em d(hashTable nfoForBytesRef);

     f (term D != HashTable.EMPTY_SLOT) {
      // Have seen t  term before, and t  f eld that supports deletes.
       nverted ndex.getPost ngL st().deletePost ng(term D,  nverted ndex.getTermsArray(), doc D);
    }
  }

  @Overr de
  publ c vo d add( nt doc D,  nt pos  on) {
    f nal BytesRef payload;
     f (payloadAtt == null) {
      payload = null;
    } else {
      payload = payloadAtt.getPayload();
    }

    BytesRef termPayload = termPayloadAtt.getTermPayload();

     nt term D =  ndexTerm( nverted ndex, termAtt.getBytesRef(),
        doc D, pos  on, termPayload, payload,
         nverted ndex.getTermPo nterEncod ng());

     f (term D == -1) {
      return;
    }

    TermsArray termsArray =  nverted ndex.getTermsArray();

     f (currentDoc sOffens ve && termsArray.offens veCounters != null) {
      termsArray.offens veCounters[term D]++;
    }

     f (facetF eld != null) {
      facetArray.addFacet(doc D, facetF eld.getFacet d(), term D);
    }
  }

  @Overr de
  publ c vo d f n sh() {
    payloadAtt = null;
    termPayloadAtt = null;
  }
}
