package com.tw ter.search.core.earlyb rd. ndex;

 mport java. o. OExcept on;

 mport com.google.common.base.Precond  ons;

 mport org.apac .lucene. ndex.B naryDocValues;
 mport org.apac .lucene. ndex.F eld nfos;
 mport org.apac .lucene. ndex.F lterLeafReader;
 mport org.apac .lucene. ndex.Leaf taData;
 mport org.apac .lucene. ndex.LeafReader;
 mport org.apac .lucene. ndex.Nu r cDocValues;
 mport org.apac .lucene. ndex.Po ntValues;
 mport org.apac .lucene. ndex.Post ngsEnum;
 mport org.apac .lucene. ndex.SortedDocValues;
 mport org.apac .lucene. ndex.SortedNu r cDocValues;
 mport org.apac .lucene. ndex.SortedSetDocValues;
 mport org.apac .lucene. ndex.StoredF eldV s or;
 mport org.apac .lucene. ndex.Term;
 mport org.apac .lucene. ndex.Terms;
 mport org.apac .lucene. ndex.TermsEnum;
 mport org.apac .lucene.search.Doc dSet erator;
 mport org.apac .lucene.store.D rectory;
 mport org.apac .lucene.ut l.B s;
 mport org.apac .lucene.ut l.BytesRef;

 mport com.tw ter.search.common.encod ng.docvalues.CSFTypeUt l;
 mport com.tw ter.search.common.encod ng.features. ntegerEncodedFeatures;
 mport com.tw ter.search.common.sc ma.base.Earlyb rdF eldType;
 mport com.tw ter.search.common.sc ma.base.FeatureConf gurat on;
 mport com.tw ter.search.common.sc ma.base.Sc ma.F eld nfo;
 mport com.tw ter.search.core.earlyb rd. ndex.column.ColumnStr deF eldDocValues;
 mport com.tw ter.search.core.earlyb rd. ndex.column.ColumnStr deF eld ndex;

publ c f nal class Earlyb rdLucene ndexSeg ntAtom cReader
    extends Earlyb rd ndexSeg ntAtom cReader {
  pr vate abstract stat c class Doc dSet eratorWrapper extends Nu r cDocValues {
    pr vate f nal Doc dSet erator delegate;

    publ c Doc dSet eratorWrapper(Doc dSet erator delegate) {
      t .delegate = Precond  ons.c ckNotNull(delegate);
    }

    @Overr de
    publ c  nt doc D() {
      return delegate.doc D();
    }

    @Overr de
    publ c  nt nextDoc() throws  OExcept on {
      return delegate.nextDoc();
    }

    @Overr de
    publ c  nt advance( nt target) throws  OExcept on {
      return delegate.advance(target);
    }

    @Overr de
    publ c long cost() {
      return delegate.cost();
    }
  }

  pr vate stat c class BytesRefBased ntegerEncodedFeatures extends  ntegerEncodedFeatures {
    pr vate f nal BytesRef bytesRef;
    pr vate f nal  nt num nts;

    publ c BytesRefBased ntegerEncodedFeatures(BytesRef bytesRef,  nt num nts) {
      t .bytesRef = bytesRef;
      t .num nts = num nts;
    }

    @Overr de
    publ c  nt get nt( nt pos) {
      return CSFTypeUt l.convertFromBytes(bytesRef.bytes, bytesRef.offset, pos);
    }

    @Overr de
    publ c vo d set nt( nt pos,  nt value) {
      throw new UnsupportedOperat onExcept on();
    }

    @Overr de
    publ c  nt getNum nts() {
      return num nts;
    }
  }

  pr vate stat c f nal  nt OLDEST_DOC_SK P_ NTERVAL = 256;

  pr vate f nal LeafReader delegate;

  /**
   * Do not add publ c constructors to t  class. Earlyb rdLucene ndexSeg ntAtom cReader  nstances
   * should be created only by call ng Earlyb rdLucene ndexSeg ntData.createAtom cReader(), to make
   * sure everyth ng  s set up properly (such as CSF readers).
   */
  Earlyb rdLucene ndexSeg ntAtom cReader(
      Earlyb rd ndexSeg ntData seg ntData, D rectory d rectory) throws  OExcept on {
    super(seg ntData);
    t .delegate = getDelegateReader(d rectory);
  }

  pr vate LeafReader getDelegateReader(D rectory d rectory) throws  OExcept on {
    LeafReader d rectoryReader =
        Earlyb rd ndexSeg ntData.getLeafReaderFromOpt m zedD rectory(d rectory);
    return new F lterLeafReader(d rectoryReader) {
      @Overr de
      publ c Nu r cDocValues getNu r cDocValues(Str ng f eld) throws  OExcept on {
        Earlyb rdF eldType type = getSc ma().getF eld nfo(f eld).getF eldType();
         f ((type == null) || !type. sCsfV ewF eld()) {
          return  n.getNu r cDocValues(f eld);
        }

        // Compute as many th ngs as poss ble once, outs de t  Nu r cDocValues.get() call.
        Str ng baseF eldNa  = getSc ma().getF eld nfo(type.getCsfV ewBaseF eld d()).getNa ();
        F eld nfo baseF eld nfo =
            Precond  ons.c ckNotNull(getSc ma().getF eld nfo(baseF eldNa ));
        Earlyb rdF eldType baseF eldType = baseF eld nfo.getF eldType();
        Precond  ons.c ckState(!baseF eldType. sCsfVar ableLength());
         nt num nts = baseF eldType.getCsfF xedLengthNumValuesPerDoc();
        FeatureConf gurat on featureConf gurat on =
            Precond  ons.c ckNotNull(type.getCsfV ewFeatureConf gurat on());
        Precond  ons.c ckArgu nt(featureConf gurat on.getValue ndex() < num nts);

         f (num nts == 1) {
          // All encoded t et features are encoded  n a s ngle  nteger.
          Nu r cDocValues nu r cDocValues =  n.getNu r cDocValues(baseF eldNa );
          return new Doc dSet eratorWrapper(nu r cDocValues) {
            @Overr de
            publ c long longValue() throws  OExcept on {
              return (nu r cDocValues.longValue() & featureConf gurat on.getB Mask())
                  >> featureConf gurat on.getB StartPos  on();
            }

            @Overr de
            publ c boolean advanceExact( nt target) throws  OExcept on {
              return nu r cDocValues.advanceExact(target);
            }
          };
        }

        B naryDocValues b naryDocValues =
            Precond  ons.c ckNotNull( n.getB naryDocValues(baseF eldNa ));
        return new Doc dSet eratorWrapper(b naryDocValues) {
          @Overr de
          publ c long longValue() throws  OExcept on {
            BytesRef data = b naryDocValues.b naryValue();
             ntegerEncodedFeatures encodedFeatures =
                new BytesRefBased ntegerEncodedFeatures(data, num nts);
            return encodedFeatures.getFeatureValue(featureConf gurat on);
          }

          @Overr de
          publ c boolean advanceExact( nt target) throws  OExcept on {
            return b naryDocValues.advanceExact(target);
          }
        };
      }

      @Overr de
      publ c Cac  lper getCoreCac  lper() {
        return  n.getCoreCac  lper();
      }

      @Overr de
      publ c Cac  lper getReaderCac  lper() {
        return  n.getReaderCac  lper();
      }
    };
  }

  pr vate TermsEnum getTermsEnumAtTerm(Term term) throws  OExcept on {
    Terms terms = terms(term.f eld());
     f (terms == null) {
      return null;
    }

    TermsEnum termsEnum = terms. erator();
    return termsEnum.seekExact(term.bytes()) ? termsEnum : null;
  }

  @Overr de
  publ c  nt getOldestDoc D(Term term) throws  OExcept on {
    TermsEnum termsEnum = getTermsEnumAtTerm(term);
     f (termsEnum == null) {
      return Earlyb rd ndexSeg ntAtom cReader.TERM_NOT_FOUND;
    }

    Post ngsEnum td = termsEnum.post ngs(null);
     nt oldestDoc D = td.nextDoc();
     f (oldestDoc D == Doc dSet erator.NO_MORE_DOCS) {
      return Earlyb rd ndexSeg ntAtom cReader.TERM_NOT_FOUND;
    }

    f nal  nt docFreq = termsEnum.docFreq();
     f (docFreq > OLDEST_DOC_SK P_ NTERVAL * 16) {
      f nal  nt sk pS ze = docFreq / OLDEST_DOC_SK P_ NTERVAL;
      do {
        oldestDoc D = td.doc D();
      } wh le (td.advance(oldestDoc D + sk pS ze) != Doc dSet erator.NO_MORE_DOCS);

      td = delegate.post ngs(term);
      td.advance(oldestDoc D);
    }

    do {
      oldestDoc D = td.doc D();
    } wh le (td.nextDoc() != Doc dSet erator.NO_MORE_DOCS);

    return oldestDoc D;
  }

  @Overr de
  publ c  nt getTerm D(Term term) throws  OExcept on {
    TermsEnum termsEnum = getTermsEnumAtTerm(term);
    return termsEnum != null
        ? ( nt) termsEnum.ord()
        : Earlyb rd ndexSeg ntAtom cReader.TERM_NOT_FOUND;
  }

  @Overr de
  publ c Terms terms(Str ng f eld) throws  OExcept on {
    return delegate.terms(f eld);
  }

  @Overr de
  publ c F eld nfos getF eld nfos() {
    return delegate.getF eld nfos();
  }

  @Overr de
  publ c B s getL veDocs() {
    return getDeletesV ew().getL veDocs();
  }

  @Overr de
  publ c  nt numDocs() {
    return delegate.numDocs();
  }

  @Overr de
  publ c  nt maxDoc() {
    return delegate.maxDoc();
  }

  @Overr de
  publ c vo d docu nt( nt doc D, StoredF eldV s or v s or) throws  OExcept on {
    delegate.docu nt(doc D, v s or);
  }

  @Overr de
  publ c boolean hasDelet ons() {
    return getDeletesV ew().hasDelet ons();
  }

  @Overr de
  protected vo d doClose() throws  OExcept on {
    delegate.close();
  }

  @Overr de
  publ c Nu r cDocValues getNu r cDocValues(Str ng f eld) throws  OExcept on {
    F eld nfo f eld nfo = getSeg ntData().getSc ma().getF eld nfo(f eld);
     f (f eld nfo == null) {
      return null;
    }

    //  f t  f eld  s a CSF v ew f eld or  f  's not loaded  n  mory, get t  Nu r cDocValues
    // from t  delegate.
    Earlyb rdF eldType f eldType = f eld nfo.getF eldType();
     f (f eldType. sCsfV ewF eld() || !f eld nfo.getF eldType(). sCsfLoad ntoRam()) {
      Nu r cDocValues delegateVals = delegate.getNu r cDocValues(f eld);
       f (delegateVals != null) {
        return delegateVals;
      }
    }

    // T  f eld  s e  r loaded  n  mory, or t  delegate doesn't have Nu r cDocValues for  .
    // Return t  Nu r cDocValues for t  f eld stored  n t  DocValuesManager.
    ColumnStr deF eld ndex csf =
        getSeg ntData().getDocValuesManager().getColumnStr deF eld ndex(f eld);
    return csf != null ? new ColumnStr deF eldDocValues(csf, t ) : null;
  }

  @Overr de
  publ c B naryDocValues getB naryDocValues(Str ng f eld) throws  OExcept on {
    return delegate.getB naryDocValues(f eld);
  }

  @Overr de
  publ c SortedDocValues getSortedDocValues(Str ng f eld) throws  OExcept on {
    return delegate.getSortedDocValues(f eld);
  }

  @Overr de
  publ c SortedSetDocValues getSortedSetDocValues(Str ng f eld) throws  OExcept on {
    return delegate.getSortedSetDocValues(f eld);
  }

  @Overr de
  publ c Nu r cDocValues getNormValues(Str ng f eld) throws  OExcept on {
    return delegate.getNormValues(f eld);
  }

  @Overr de
  publ c SortedNu r cDocValues getSortedNu r cDocValues(Str ng f eld) throws  OExcept on {
    return delegate.getSortedNu r cDocValues(f eld);
  }

  @Overr de
  publ c vo d c ck ntegr y() throws  OExcept on {
    delegate.c ck ntegr y();
  }

  @Overr de
  publ c Po ntValues getPo ntValues(Str ng f eld) throws  OExcept on {
    return delegate.getPo ntValues(f eld);
  }

  @Overr de
  publ c Leaf taData get taData() {
    return delegate.get taData();
  }

  @Overr de
  publ c Cac  lper getCoreCac  lper() {
    return delegate.getCoreCac  lper();
  }

  @Overr de
  publ c Cac  lper getReaderCac  lper() {
    return delegate.getReaderCac  lper();
  }
}
