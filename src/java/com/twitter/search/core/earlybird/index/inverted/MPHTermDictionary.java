package com.tw ter.search.core.earlyb rd. ndex. nverted;

 mport java. o. OExcept on;

 mport org.apac .lucene. ndex.BaseTermsEnum;
 mport org.apac .lucene. ndex. mpactsEnum;
 mport org.apac .lucene. ndex.Post ngsEnum;
 mport org.apac .lucene. ndex.Slow mpactsEnum;
 mport org.apac .lucene. ndex.TermsEnum;
 mport org.apac .lucene.ut l.BytesRef;
 mport org.apac .lucene.ut l.packed.Packed nts;

 mport com.tw ter.search.common.ut l.hash.BDZAlgor hm;
 mport com.tw ter.search.common.ut l. o.flushable.DataDeser al zer;
 mport com.tw ter.search.common.ut l. o.flushable.DataSer al zer;
 mport com.tw ter.search.common.ut l. o.flushable.Flush nfo;
 mport com.tw ter.search.common.ut l. o.flushable.Flushable;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntAtom cReader;

publ c class MPHTermD ct onary  mple nts TermD ct onary, Flushable {
  pr vate f nal BDZAlgor hm termsHashFunct on;
  pr vate f nal Packed nts.Reader termPo nters;
  pr vate f nal ByteBlockPool termPool;
  pr vate f nal TermPo nterEncod ng termPo nterEncod ng;
  pr vate f nal  nt numTerms;

  MPHTermD ct onary( nt numTerms, BDZAlgor hm termsHashFunct on,
      Packed nts.Reader termPo nters, ByteBlockPool termPool,
      TermPo nterEncod ng termPo nterEncod ng) {
    t .numTerms = numTerms;
    t .termsHashFunct on = termsHashFunct on;
    t .termPo nters = termPo nters;
    t .termPool = termPool;
    t .termPo nterEncod ng = termPo nterEncod ng;
  }

  @Overr de
  publ c  nt getNumTerms() {
    return numTerms;
  }

  @Overr de
  publ c  nt lookupTerm(BytesRef term) {
     nt term D = termsHashFunct on.lookup(term);
     f (term D >= getNumTerms() || term D < 0) {
      return Earlyb rd ndexSeg ntAtom cReader.TERM_NOT_FOUND;
    }

     f (ByteTermUt ls.post ngEquals(termPool, termPo nterEncod ng
            .getTextStart(( nt) termPo nters.get(term D)), term)) {
      return term D;
    } else {
      return Earlyb rd ndexSeg ntAtom cReader.TERM_NOT_FOUND;
    }
  }

  @Overr de
  publ c boolean getTerm( nt term D, BytesRef text, BytesRef termPayload) {
     nt termPo nter = ( nt) termPo nters.get(term D);
    boolean hasTermPayload = termPo nterEncod ng.hasPayload(termPo nter);
     nt textStart = termPo nterEncod ng.getTextStart(termPo nter);
    // setBytesRef sets t  passed  n BytesRef "text" to t  term  n t  termPool.
    // As a s de effect   returns t  offset of t  next entry  n t  pool after t  term,
    // wh ch may opt onally be used  f t  term has a payload.
     nt termPayloadStart = ByteTermUt ls.setBytesRef(termPool, text, textStart);
     f (termPayload != null && hasTermPayload) {
      ByteTermUt ls.setBytesRef(termPool, termPayload, termPayloadStart);
    }

    return hasTermPayload;
  }

  @Overr de
  publ c TermsEnum createTermsEnum(Opt m zed mory ndex  ndex) {
    return new MPHTermsEnum( ndex);
  }

  publ c stat c class MPHTermsEnum extends BaseTermsEnum {
    pr vate  nt term D;
    pr vate f nal BytesRef bytesRef = new BytesRef();
    pr vate f nal Opt m zed mory ndex  ndex;

    MPHTermsEnum(Opt m zed mory ndex  ndex) {
      t . ndex =  ndex;
    }

    @Overr de
    publ c  nt docFreq() {
      return  ndex.getDF(term D);
    }

    @Overr de
    publ c Post ngsEnum post ngs(Post ngsEnum reuse,  nt flags) throws  OExcept on {
       nt post ngsPo nter =  ndex.getPost ngL stPo nter(term D);
       nt numPost ngs =  ndex.getNumPost ngs(term D);
      return  ndex.getPost ngL sts().post ngs(post ngsPo nter, numPost ngs, flags);
    }

    @Overr de
    publ c  mpactsEnum  mpacts( nt flags) throws  OExcept on {
      return new Slow mpactsEnum(post ngs(null, flags));
    }

    @Overr de
    publ c SeekStatus seekCe l(BytesRef text) throws  OExcept on {
      term D =  ndex.lookupTerm(text);

       f (term D == -1) {
        return SeekStatus.END;
      } else {
        return SeekStatus.FOUND;
      }
    }

    @Overr de
    publ c BytesRef next() {
      return null;
    }

    @Overr de
    publ c long ord() {
      return term D;
    }

    @Overr de
    publ c vo d seekExact(long ord) {
       f (ord <  ndex.getNumTerms()) {
        term D = ( nt) ord;
         ndex.getTerm(term D, bytesRef, null);
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

  @SuppressWarn ngs("unc cked")
  @Overr de
  publ c FlushHandler getFlushHandler() {
    return new FlushHandler(t );
  }

  publ c stat c class FlushHandler extends Flushable.Handler<MPHTermD ct onary> {
    stat c f nal Str ng NUM_TERMS_PROP_NAME = "numTerms";
    pr vate f nal TermPo nterEncod ng termPo nterEncod ng;

    publ c FlushHandler(TermPo nterEncod ng termPo nterEncod ng) {
      super();
      t .termPo nterEncod ng = termPo nterEncod ng;
    }

    publ c FlushHandler(MPHTermD ct onary objectToFlush) {
      super(objectToFlush);
      t .termPo nterEncod ng = objectToFlush.termPo nterEncod ng;
    }

    @Overr de
    protected vo d doFlush(Flush nfo flush nfo, DataSer al zer out)
        throws  OExcept on {
      MPHTermD ct onary objectToFlush = getObjectToFlush();
      flush nfo.add ntProperty(NUM_TERMS_PROP_NAME, objectToFlush.getNumTerms());

      out.wr ePacked nts(objectToFlush.termPo nters);
      objectToFlush.termPool.getFlushHandler().flush(flush nfo.newSubPropert es("termPool"), out);
      objectToFlush.termsHashFunct on.getFlushHandler()
              .flush(flush nfo.newSubPropert es("termsHashFunct on"), out);
    }

    @Overr de
    protected MPHTermD ct onary doLoad(Flush nfo flush nfo,
        DataDeser al zer  n) throws  OExcept on {
       nt numTerms = flush nfo.get ntProperty(NUM_TERMS_PROP_NAME);
      Packed nts.Reader termPo nters =  n.readPacked nts();
      ByteBlockPool termPool = (new ByteBlockPool.FlushHandler()).load(
              flush nfo.getSubPropert es("termPool"),  n);
      BDZAlgor hm termsHashFunct on = (new BDZAlgor hm.FlushHandler()).load(
              flush nfo.getSubPropert es("termsHashFunct on"),  n);

      return new MPHTermD ct onary(numTerms, termsHashFunct on, termPo nters,
              termPool, termPo nterEncod ng);
    }
  }
}
