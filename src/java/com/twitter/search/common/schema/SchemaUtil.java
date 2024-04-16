package com.tw ter.search.common.sc ma;

 mport com.google.common.base.Precond  ons;

 mport org.apac .lucene. ndex.DocValuesType;
 mport org.apac .lucene. ndex. ndexOpt ons;
 mport org.apac .lucene.ut l.BytesRef;

 mport com.tw ter.search.common.sc ma.base.Earlyb rdF eldType;
 mport com.tw ter.search.common.sc ma.base. mmutableSc ma nterface;
 mport com.tw ter.search.common.sc ma.base. ndexedNu r cF eldSett ngs;
 mport com.tw ter.search.common.sc ma.base.Sc ma;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftCSFType;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftNu r cType;
 mport com.tw ter.search.common.ut l.analys s. ntTermAttr bute mpl;
 mport com.tw ter.search.common.ut l.analys s.LongTermAttr bute mpl;
 mport com.tw ter.search.common.ut l.analys s.SortableLongTermAttr bute mpl;

publ c f nal class Sc maUt l {
  pr vate Sc maUt l() {
  }

  /**
   * Get t  a f xed CSF f eld's number of values per doc.
   * @param sc ma t  Sc ma for t   ndex
   * @param f eld d t  f eld  d t  CSF f eld - t  f eld must be of b nary  nteger type and
   *                 n f xed s ze
   * @return t  number of values per doc
   */
  publ c stat c  nt getCSFF eldF xedLength( mmutableSc ma nterface sc ma,  nt f eld d) {
    f nal Sc ma.F eld nfo f eld nfo = Precond  ons.c ckNotNull(sc ma.getF eld nfo(f eld d));
    return getCSFF eldF xedLength(f eld nfo);
  }

  /**
   * Get t  a f xed CSF f eld's number of values per doc.
   * @param sc ma t  Sc ma for t   ndex
   * @param f eldNa  t  f eld na  of t  CSF f eld - t  f eld must be of b nary  nteger type
   *                  and  n f xed s ze
   * @return t  number of values per doc
   */
  publ c stat c  nt getCSFF eldF xedLength( mmutableSc ma nterface sc ma, Str ng f eldNa ) {
    f nal Sc ma.F eld nfo f eld nfo = Precond  ons.c ckNotNull(sc ma.getF eld nfo(f eldNa ));
    return getCSFF eldF xedLength(f eld nfo);
  }

  /**
   * Get t  a f xed CSF f eld's number of values per doc.
   * @param f eld nfo t  f eld of t  CSF f eld - t  f eld must be of b nary  nteger type
   *                  and  n f xed s ze
   * @return t  number of values per doc
   */
  publ c stat c  nt getCSFF eldF xedLength(Sc ma.F eld nfo f eld nfo) {
    f nal Earlyb rdF eldType f eldType = f eld nfo.getF eldType();
    Precond  ons.c ckState(f eldType.docValuesType() == DocValuesType.B NARY
        && f eldType.getCsfType() == Thr ftCSFType. NT);
    return f eldType.getCsfF xedLengthNumValuesPerDoc();
  }

  /** Converts t  g ven value to a BytesRef  nstance, accord ng to t  type of t  g ven f eld. */
  publ c stat c BytesRef toBytesRef(Sc ma.F eld nfo f eld nfo, Str ng value) {
    Earlyb rdF eldType f eldType = f eld nfo.getF eldType();
    Precond  ons.c ckArgu nt(f eldType. ndexOpt ons() !=  ndexOpt ons.NONE);
     ndexedNu r cF eldSett ngs nu r cSett ng = f eldType.getNu r cF eldSett ngs();
     f (nu r cSett ng != null) {
       f (!nu r cSett ng. sUseTw terFormat()) {
        throw new UnsupportedOperat onExcept on(
            "Nu r c f eld not us ng Tw ter format: cannot dr ll down.");
      }

      Thr ftNu r cType nu r cType = nu r cSett ng.getNu r cType();
      sw ch (nu r cType) {
        case  NT:
          try {
            return  ntTermAttr bute mpl.copy ntoNewBytesRef( nteger.parse nt(value));
          } catch (NumberFormatExcept on e) {
            throw new UnsupportedOperat onExcept on(
                Str ng.format("Cannot parse value for  nt f eld %s: %s",
                              f eld nfo.getNa (), value),
                e);
          }
        case LONG:
          try {
            return nu r cSett ng. sUseSortableEncod ng()
                ? SortableLongTermAttr bute mpl.copy ntoNewBytesRef(Long.parseLong(value))
                : LongTermAttr bute mpl.copy ntoNewBytesRef(Long.parseLong(value));
          } catch (NumberFormatExcept on e) {
            throw new UnsupportedOperat onExcept on(
                Str ng.format("Cannot parse value for long f eld %s: %s",
                              f eld nfo.getNa (), value),
                e);
          }
        default:
          throw new UnsupportedOperat onExcept on(
              Str ng.format("Unsupported nu r c type for f eld %s: %s",
                            f eld nfo.getNa (), nu r cType));
      }
    }

    return new BytesRef(value);
  }
}
