package com.tw ter.search.core.earlyb rd. ndex. nverted;

 mport java. o. OExcept on;
 mport java.ut l.Arrays;

 mport org.apac .lucene.ut l.ArrayUt l;

 mport com.tw ter.search.common.ut l. o.flushable.DataDeser al zer;
 mport com.tw ter.search.common.ut l. o.flushable.DataSer al zer;
 mport com.tw ter.search.common.ut l. o.flushable.Flush nfo;
 mport com.tw ter.search.common.ut l. o.flushable.Flushable;

/**
 * TermsArray prov des  nformat on on each term  n t  post ng l st.
 *
 *   does not prov de any concurrency guarantees. T  wr er must ensure that all updates are
 * v s ble to readers w h an external  mory barr er.
 */
publ c class TermsArray  mple nts Flushable {
  pr vate stat c f nal  nt BYTES_PER_POST NG = 5 *  nteger.BYTES;
  publ c stat c f nal  nt  NVAL D = -1;

  pr vate f nal  nt s ze;

  publ c f nal  nt[] termPo nters;
  pr vate f nal  nt[] post ngsPo nters;

  // Der ved data. Not atom c and not rel able.
  publ c f nal  nt[] largestPost ngs;
  publ c f nal  nt[] docu ntFrequency;
  publ c f nal  nt[] offens veCounters;

  TermsArray( nt s ze, boolean useOffens veCounters) {
    t .s ze = s ze;

    termPo nters = new  nt[s ze];
    post ngsPo nters = new  nt[s ze];

    largestPost ngs = new  nt[s ze];
    docu ntFrequency = new  nt[s ze];

     f (useOffens veCounters) {
      offens veCounters = new  nt[s ze];
    } else {
      offens veCounters = null;
    }

    Arrays.f ll(post ngsPo nters,  NVAL D);
    Arrays.f ll(largestPost ngs,  NVAL D);
  }

  pr vate TermsArray(TermsArray oldArray,  nt newS ze) {
    t (newS ze, oldArray.offens veCounters != null);
    copyFrom(oldArray);
  }

  pr vate TermsArray(
       nt s ze,
       nt[] termPo nters,
       nt[] post ngsPo nters,
       nt[] largestPost ngs,
       nt[] docu ntFrequency,
       nt[] offens veCounters) {
    t .s ze = s ze;

    t .termPo nters = termPo nters;
    t .post ngsPo nters = post ngsPo nters;

    t .largestPost ngs = largestPost ngs;
    t .docu ntFrequency = docu ntFrequency;
    t .offens veCounters = offens veCounters;
  }

  TermsArray grow() {
     nt newS ze = ArrayUt l.overs ze(s ze + 1, BYTES_PER_POST NG);
    return new TermsArray(t , newS ze);
  }


  pr vate vo d copyFrom(TermsArray from) {
    copy(from.termPo nters, termPo nters);
    copy(from.post ngsPo nters, post ngsPo nters);

    copy(from.largestPost ngs, largestPost ngs);
    copy(from.docu ntFrequency, docu ntFrequency);

     f (from.offens veCounters != null) {
      copy(from.offens veCounters, offens veCounters);
    }
  }

  pr vate vo d copy( nt[] from,  nt[] to) {
    System.arraycopy(from, 0, to, 0, from.length);
  }

  /**
   * Returns t  s ze of t  array.
   */
  publ c  nt getS ze() {
    return s ze;
  }

  /**
   * Wr e s de operat on for updat ng t  po nter to t  last post ng for a g ven term.
   */
  publ c vo d updatePost ngsPo nter( nt term D,  nt newPo nter) {
    post ngsPo nters[term D] = newPo nter;
  }

  /**
   * T  returned po nter  s guaranteed to be  mory safe to follow to  s target. T  data
   * structure   po nts to w ll be cons stent and safe to traverse. T  post ng l st may conta n
   * doc  Ds that t  current reader should not see, and t  reader should sk p over t se doc  Ds
   * to ensure that t  readers prov de an  mmutable v ew of t  doc  Ds  n a post ng l st.
   */
  publ c  nt getPost ngsPo nter( nt term D) {
    return post ngsPo nters[term D];
  }

  publ c  nt[] getDocu ntFrequency() {
    return docu ntFrequency;
  }

  /**
   * Gets t  array conta n ng t  f rst post ng for each  ndexed term.
   */
  publ c  nt[] getLargestPost ngs() {
    return largestPost ngs;
  }

  @SuppressWarn ngs("unc cked")
  @Overr de
  publ c FlushHandler getFlushHandler() {
    return new FlushHandler(t );
  }

  publ c stat c class FlushHandler extends Flushable.Handler<TermsArray> {
    pr vate stat c f nal Str ng S ZE_PROP_NAME = "s ze";
    pr vate stat c f nal Str ng HAS_OFFENS VE_COUNTERS_PROP_NAME = "hasOffens veCounters";

    publ c FlushHandler(TermsArray objectToFlush) {
      super(objectToFlush);
    }

    publ c FlushHandler() {
    }

    @Overr de
    protected vo d doFlush(Flush nfo flush nfo, DataSer al zer out) throws  OExcept on {
      TermsArray objectToFlush = getObjectToFlush();
      flush nfo.add ntProperty(S ZE_PROP_NAME, objectToFlush.s ze);
      boolean hasOffens veCounters = objectToFlush.offens veCounters != null;
      flush nfo.addBooleanProperty(HAS_OFFENS VE_COUNTERS_PROP_NAME, hasOffens veCounters);

      out.wr e ntArray(objectToFlush.termPo nters);
      out.wr e ntArray(objectToFlush.post ngsPo nters);

      out.wr e ntArray(objectToFlush.largestPost ngs);
      out.wr e ntArray(objectToFlush.docu ntFrequency);

       f (hasOffens veCounters) {
        out.wr e ntArray(objectToFlush.offens veCounters);
      }
    }

    @Overr de
    protected TermsArray doLoad(
        Flush nfo flush nfo, DataDeser al zer  n) throws  OExcept on {
       nt s ze = flush nfo.get ntProperty(S ZE_PROP_NAME);
      boolean hasOffens veCounters = flush nfo.getBooleanProperty(HAS_OFFENS VE_COUNTERS_PROP_NAME);

       nt[] termPo nters =  n.read ntArray();
       nt[] post ngsPo nters =  n.read ntArray();

       nt[] largestPost ngs =  n.read ntArray();
       nt[] docu ntFrequency =  n.read ntArray();

       nt[] offens veCounters = hasOffens veCounters ?  n.read ntArray() : null;

      return new TermsArray(
          s ze,
          termPo nters,
          post ngsPo nters,
          largestPost ngs,
          docu ntFrequency,
          offens veCounters);
    }
  }
}
