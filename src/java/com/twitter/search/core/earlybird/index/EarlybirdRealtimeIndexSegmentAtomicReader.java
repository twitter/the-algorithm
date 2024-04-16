package com.tw ter.search.core.earlyb rd. ndex;

 mport java. o. OExcept on;

 mport org.apac .lucene. ndex.B naryDocValues;
 mport org.apac .lucene. ndex.F elds;
 mport org.apac .lucene. ndex.Leaf taData;
 mport org.apac .lucene. ndex.Nu r cDocValues;
 mport org.apac .lucene. ndex.Po ntValues;
 mport org.apac .lucene. ndex.SortedDocValues;
 mport org.apac .lucene. ndex.SortedNu r cDocValues;
 mport org.apac .lucene. ndex.SortedSetDocValues;
 mport org.apac .lucene. ndex.StoredF eldV s or;
 mport org.apac .lucene. ndex.Term;
 mport org.apac .lucene. ndex.Terms;
 mport org.apac .lucene.search.Sort;
 mport org.apac .lucene.ut l.B s;
 mport org.apac .lucene.ut l.Vers on;

 mport com.tw ter.search.core.earlyb rd.facets.Earlyb rdFacetDocValueSet;
 mport com.tw ter.search.core.earlyb rd. ndex.column.ColumnStr deF eldDocValues;
 mport com.tw ter.search.core.earlyb rd. ndex.column.ColumnStr deF eld ndex;
 mport com.tw ter.search.core.earlyb rd. ndex. nverted. n moryF elds;
 mport com.tw ter.search.core.earlyb rd. ndex. nverted. nverted ndex;

publ c f nal class Earlyb rdRealt   ndexSeg ntAtom cReader
    extends Earlyb rd ndexSeg ntAtom cReader {
  pr vate f nal F elds f elds;
  pr vate f nal  nt maxDoc d;
  pr vate f nal  nt numDocs;

  /**
   * Creates a new real-t   reader for t  g ven seg nt. Do not add publ c constructors to t 
   * class. Earlyb rdRealt   ndexSeg ntAtom cReader  nstances should be created only by call ng
   * Earlyb rdRealt   ndexSeg ntData.createAtom cReader(), to make sure everyth ng  s set up
   * properly (such as CSF readers).
   */
  Earlyb rdRealt   ndexSeg ntAtom cReader(Earlyb rdRealt   ndexSeg ntData seg ntData) {
    super(seg ntData);

    t .f elds = new  n moryF elds(seg ntData.getPerF eldMap(), syncData.get ndexPo nters());

    //   cac  t  h g st doc  D and t  number of docs, because t  reader must return t  sa 
    // values for  s ent re l fet  , and t  seg nt w ll get more t ets over t  .
    // T se values could be sl ghtly out of sync w h 'f elds', because   don't update t se
    // values atom cally w h t  f elds.
    t .maxDoc d = seg ntData.getDoc DToT et DMapper().getPrev ousDoc D( nteger.MAX_VALUE);
    t .numDocs = seg ntData.getDoc DToT et DMapper().getNumDocs();
  }

  @Overr de
  publ c  nt maxDoc() {
    return maxDoc d + 1;
  }

  @Overr de
  publ c  nt numDocs() {
    return numDocs;
  }

  @Overr de
  protected vo d doClose() {
    // noth ng to do
  }

  @Overr de
  publ c vo d docu nt( nt doc D, StoredF eldV s or v s or) {
    // not supported
  }

  @Overr de
  publ c  nt getOldestDoc D(Term t) throws  OExcept on {
     nverted ndex perF eld = getSeg ntData().getPerF eldMap().get(t.f eld());
     f (perF eld == null) {
      return TERM_NOT_FOUND;
    }
    return perF eld.getLargestDoc DForTerm(t.bytes());
  }

  @Overr de
  publ c  nt getTerm D(Term t) throws  OExcept on {
     nverted ndex perF eld = getSeg ntData().getPerF eldMap().get(t.f eld());
     f (perF eld == null) {
      return TERM_NOT_FOUND;
    }
    return perF eld.lookupTerm(t.bytes());
  }

  @Overr de
  publ c B s getL veDocs() {
    // l veDocs conta ns  nverted (decreas ng) doc Ds.
    return getDeletesV ew().getL veDocs();
  }

  @Overr de
  publ c boolean hasDelet ons() {
    return getDeletesV ew().hasDelet ons();
  }

  @Overr de
  publ c Terms terms(Str ng f eld) throws  OExcept on {
    return f elds.terms(f eld);
  }

  @Overr de
  publ c Nu r cDocValues getNu r cDocValues(Str ng f eld) throws  OExcept on {
    ColumnStr deF eld ndex csf =
        getSeg ntData().getDocValuesManager().getColumnStr deF eld ndex(f eld);
    return csf != null ? new ColumnStr deF eldDocValues(csf, t ) : null;
  }

  @Overr de
  publ c boolean hasDocs() {
    // smallestDoc D  s t  smallest docu nt  D that was ava lable w n t  reader was created.
    // So   need to c ck  s value  n order to dec de  f t  reader can see any docu nts,
    // because  n t   ant   ot r docu nts m ght've been added to t  t et  D mapper.
    return getSmallestDoc D() !=  nteger.MAX_VALUE;
  }

  @Overr de
  publ c B naryDocValues getB naryDocValues(Str ng f eld) {
    return null;
  }

  @Overr de
  publ c SortedDocValues getSortedDocValues(Str ng f eld) {
    return null;
  }

  @Overr de
  publ c SortedSetDocValues getSortedSetDocValues(Str ng f eld) {
    // spec al handl ng for facet f eld
     f (Earlyb rdFacetDocValueSet.F ELD_NAME.equals(f eld)) {
      return ((Earlyb rdRealt   ndexSeg ntData) getSeg ntData()).getFacetDocValueSet();
    }

    return null;
  }

  @Overr de
  publ c Nu r cDocValues getNormValues(Str ng f eld) throws  OExcept on {
    ColumnStr deF eld ndex csf = getSeg ntData().getNorm ndex(f eld);
    return csf != null ? new ColumnStr deF eldDocValues(csf, t ) : null;
  }

  @Overr de
  publ c SortedNu r cDocValues getSortedNu r cDocValues(Str ng f eld) {
    return null;
  }

  @Overr de
  publ c vo d c ck ntegr y() {
    // noth ng to do
  }

  @Overr de
  publ c Po ntValues getPo ntValues(Str ng f eld) {
    return null;
  }

  @Overr de
  publ c Leaf taData get taData() {
    return new Leaf taData(Vers on.LATEST.major, Vers on.LATEST, Sort.RELEVANCE);
  }

  @Overr de
  publ c Cac  lper getCoreCac  lper() {
    return null;
  }

  @Overr de
  publ c Cac  lper getReaderCac  lper() {
    return null;
  }
}
