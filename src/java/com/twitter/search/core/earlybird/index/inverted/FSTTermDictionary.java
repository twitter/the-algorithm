package com.tw ter.search.core.earlyb rd. ndex. nverted;

 mport java. o. OExcept on;
 mport java.ut l.Comparator;

 mport org.apac .lucene. ndex.BaseTermsEnum;
 mport org.apac .lucene. ndex. mpactsEnum;
 mport org.apac .lucene. ndex.Post ngsEnum;
 mport org.apac .lucene. ndex.Slow mpactsEnum;
 mport org.apac .lucene. ndex.TermsEnum;
 mport org.apac .lucene.ut l.BytesRef;
 mport org.apac .lucene.ut l. nPlace rgeSorter;
 mport org.apac .lucene.ut l. ntsRefBu lder;
 mport org.apac .lucene.ut l.fst.BytesRefFSTEnum;
 mport org.apac .lucene.ut l.fst.FST;
 mport org.apac .lucene.ut l.fst.Pos  ve ntOutputs;
 mport org.apac .lucene.ut l.fst.Ut l;
 mport org.apac .lucene.ut l.packed.Packed nts;

 mport com.tw ter.search.common.ut l. o.flushable.DataDeser al zer;
 mport com.tw ter.search.common.ut l. o.flushable.DataSer al zer;
 mport com.tw ter.search.common.ut l. o.flushable.Flush nfo;
 mport com.tw ter.search.common.ut l. o.flushable.Flushable;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntAtom cReader;

publ c class FSTTermD ct onary  mple nts TermD ct onary, Flushable {
  pr vate f nal FST<Long> fst;

  pr vate f nal Packed nts.Reader termPo nters;
  pr vate f nal ByteBlockPool termPool;
  pr vate f nal TermPo nterEncod ng termPo nterEncod ng;
  pr vate  nt numTerms;

  FSTTermD ct onary( nt numTerms, FST<Long> fst,
                    ByteBlockPool termPool, Packed nts.Reader termPo nters,
                    TermPo nterEncod ng termPo nterEncod ng) {
    t .numTerms = numTerms;
    t .fst = fst;
    t .termPool = termPool;
    t .termPo nters = termPo nters;
    t .termPo nterEncod ng = termPo nterEncod ng;
  }

  @Overr de
  publ c  nt getNumTerms() {
    return numTerms;
  }

  @Overr de
  publ c  nt lookupTerm(BytesRef term) throws  OExcept on {
     f (fst == null) {
      return Earlyb rd ndexSeg ntAtom cReader.TERM_NOT_FOUND;
    }
    f nal BytesRefFSTEnum<Long> fstEnum = new BytesRefFSTEnum<>(fst);

    f nal BytesRefFSTEnum. nputOutput<Long> result = fstEnum.seekExact(term);
     f (result != null && result. nput.equals(term)) {
      // -1 because 0  s not supported by t  fst
      return result.output. ntValue() - 1;
    } else {
      return Earlyb rd ndexSeg ntAtom cReader.TERM_NOT_FOUND;
    }
  }

  stat c FSTTermD ct onary bu ldFST(
      f nal ByteBlockPool termPool,
       nt[] termPo nters,
       nt numTerms,
      f nal Comparator<BytesRef> comp,
      boolean supportTermTextLookup,
      f nal TermPo nterEncod ng termPo nterEncod ng) throws  OExcept on {
    f nal  ntsRefBu lder scratch ntsRef = new  ntsRefBu lder();

    f nal  nt[] compact = new  nt[numTerms];
    for ( nt   = 0;   < numTerms;  ++) {
      compact[ ] =  ;
    }

    // f rst sort t  terms
    new  nPlace rgeSorter() {
      pr vate BytesRef scratch1 = new BytesRef();
      pr vate BytesRef scratch2 = new BytesRef();

      @Overr de
      protected vo d swap( nt  ,  nt j) {
        f nal  nt o = compact[ ];
        compact[ ] = compact[j];
        compact[j] = o;
      }

      @Overr de
      protected  nt compare( nt  ,  nt j) {
        f nal  nt ord1 = compact[ ];
        f nal  nt ord2 = compact[j];
        ByteTermUt ls.setBytesRef(termPool, scratch1,
                                  termPo nterEncod ng.getTextStart(termPo nters[ord1]));
        ByteTermUt ls.setBytesRef(termPool, scratch2,
                                  termPo nterEncod ng.getTextStart(termPo nters[ord2]));
        return comp.compare(scratch1, scratch2);
      }

    }.sort(0, compact.length);

    f nal Pos  ve ntOutputs outputs = Pos  ve ntOutputs.getS ngleton();

    f nal org.apac .lucene.ut l.fst.Bu lder<Long> bu lder =
        new org.apac .lucene.ut l.fst.Bu lder<>(FST. NPUT_TYPE.BYTE1, outputs);

    f nal BytesRef term = new BytesRef();
    for ( nt term D : compact) {
      ByteTermUt ls.setBytesRef(termPool, term,
              termPo nterEncod ng.getTextStart(termPo nters[term D]));
      // +1 because 0  s not supported by t  fst
      bu lder.add(Ut l.to ntsRef(term, scratch ntsRef), (long) term D + 1);
    }

     f (supportTermTextLookup) {
      Packed nts.Reader packedTermPo nters = Opt m zed mory ndex.getPacked nts(termPo nters);
      return new FSTTermD ct onary(
          numTerms,
          bu lder.f n sh(),
          termPool,
          packedTermPo nters,
          termPo nterEncod ng);
    } else {
      return new FSTTermD ct onary(
          numTerms,
          bu lder.f n sh(),
          null, // termPool
          null, // termPo nters
          termPo nterEncod ng);
    }
  }

  @Overr de
  publ c boolean getTerm( nt term D, BytesRef text, BytesRef termPayload) {
     f (termPool == null) {
      throw new UnsupportedOperat onExcept on(
              "T  d ct onary does not support term lookup by term D");
    } else {
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
  }

  @Overr de
  publ c TermsEnum createTermsEnum(Opt m zed mory ndex  ndex) {
    return new BaseTermsEnum() {
      pr vate f nal BytesRefFSTEnum<Long> fstEnum = fst != null ? new BytesRefFSTEnum<>(fst) : null;
      pr vate BytesRefFSTEnum. nputOutput<Long> current;

      @Overr de
      publ c SeekStatus seekCe l(BytesRef term)
          throws  OExcept on {
         f (fstEnum == null) {
          return SeekStatus.END;
        }

        current = fstEnum.seekCe l(term);
         f (current != null && current. nput.equals(term)) {
          return SeekStatus.FOUND;
        } else {
          return SeekStatus.END;
        }
      }

      @Overr de
      publ c boolean seekExact(BytesRef text) throws  OExcept on {
        current = fstEnum.seekExact(text);
        return current != null;
      }

      //  n   case t  ord  s t  term d.
      @Overr de
      publ c vo d seekExact(long ord) {
        current = new BytesRefFSTEnum. nputOutput<>();
        current. nput = null;
        // +1 because 0  s not supported by t  fst
        current.output = ord + 1;

         f (termPool != null) {
          BytesRef bytesRef = new BytesRef();
           nt term d = ( nt) ord;
          assert term d == ord;
          FSTTermD ct onary.t .getTerm(term d, bytesRef, null);
          current. nput = bytesRef;
        }
      }

      @Overr de
      publ c BytesRef next() throws  OExcept on {
        current = fstEnum.next();
         f (current == null) {
          return null;
        }
        return current. nput;
      }

      @Overr de
      publ c BytesRef term() {
        return current. nput;
      }

      //  n   case t  ord  s t  term d.
      @Overr de
      publ c long ord() {
        // -1 because 0  s not supported by t  fst
        return current.output - 1;
      }

      @Overr de
      publ c  nt docFreq() {
        return  ndex.getDF(( nt) ord());
      }

      @Overr de
      publ c long totalTermFreq() {
        return docFreq();
      }

      @Overr de
      publ c Post ngsEnum post ngs(Post ngsEnum reuse,  nt flags) throws  OExcept on {
         nt term D = ( nt) ord();
         nt post ngsPo nter =  ndex.getPost ngL stPo nter(term D);
         nt numPost ngs =  ndex.getNumPost ngs(term D);
        return  ndex.getPost ngL sts().post ngs(post ngsPo nter, numPost ngs, flags);
      }

      @Overr de
      publ c  mpactsEnum  mpacts( nt flags) throws  OExcept on {
        return new Slow mpactsEnum(post ngs(null, flags));
      }
    };
  }

  @SuppressWarn ngs("unc cked")
  @Overr de
  publ c FlushHandler getFlushHandler() {
    return new FlushHandler(t );
  }

  publ c stat c class FlushHandler extends Flushable.Handler<FSTTermD ct onary> {
    pr vate stat c f nal Str ng NUM_TERMS_PROP_NAME = "numTerms";
    pr vate stat c f nal Str ng SUPPORT_TERM_TEXT_LOOKUP_PROP_NAME = "supportTermTextLookup";
    pr vate f nal TermPo nterEncod ng termPo nterEncod ng;

    publ c FlushHandler(TermPo nterEncod ng termPo nterEncod ng) {
      super();
      t .termPo nterEncod ng = termPo nterEncod ng;
    }

    publ c FlushHandler(FSTTermD ct onary objectToFlush) {
      super(objectToFlush);
      t .termPo nterEncod ng = objectToFlush.termPo nterEncod ng;
    }

    @Overr de
    protected vo d doFlush(Flush nfo flush nfo, DataSer al zer out)
        throws  OExcept on {
      FSTTermD ct onary objectToFlush = getObjectToFlush();
      flush nfo.add ntProperty(NUM_TERMS_PROP_NAME, objectToFlush.getNumTerms());
      flush nfo.addBooleanProperty(SUPPORT_TERM_TEXT_LOOKUP_PROP_NAME,
              objectToFlush.termPool != null);
       f (objectToFlush.termPool != null) {
        out.wr ePacked nts(objectToFlush.termPo nters);
        objectToFlush.termPool.getFlushHandler().flush(flush nfo.newSubPropert es("termPool"), out);
      }
      objectToFlush.fst.save(out.get ndexOutput());
    }

    @Overr de
    protected FSTTermD ct onary doLoad(Flush nfo flush nfo,
        DataDeser al zer  n) throws  OExcept on {
       nt numTerms = flush nfo.get ntProperty(NUM_TERMS_PROP_NAME);
      boolean supportTermTextLookup =
              flush nfo.getBooleanProperty(SUPPORT_TERM_TEXT_LOOKUP_PROP_NAME);
      Packed nts.Reader termPo nters = null;
      ByteBlockPool termPool = null;
       f (supportTermTextLookup) {
        termPo nters =  n.readPacked nts();
        termPool = (new ByteBlockPool.FlushHandler())
                .load(flush nfo.getSubPropert es("termPool"),  n);
      }
      f nal Pos  ve ntOutputs outputs = Pos  ve ntOutputs.getS ngleton();
      return new FSTTermD ct onary(numTerms, new FST<>( n.get ndex nput(), outputs),
              termPool, termPo nters, termPo nterEncod ng);
    }
  }
}
