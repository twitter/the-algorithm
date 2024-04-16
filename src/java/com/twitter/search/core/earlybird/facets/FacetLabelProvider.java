package com.tw ter.search.core.earlyb rd.facets;

 mport org.apac .lucene.ut l.BytesRef;

 mport com.tw ter.search.common.hashtable.HashTable;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common.ut l.analys s. ntTermAttr bute mpl;
 mport com.tw ter.search.common.ut l.analys s.LongTermAttr bute mpl;
 mport com.tw ter.search.common.ut l.analys s.SortableLongTermAttr bute mpl;
 mport com.tw ter.search.core.earlyb rd. ndex. nverted. nverted ndex;

/**
 * G ven a term D t  accessor can be used to retr eve t  term bytesref and text
 * that corresponds to t  term D.
 */
publ c  nterface FacetLabelProv der {
  /**
   * Returns a {@l nk FacetLabelAccessor} for t  prov der.
   */
  FacetLabelAccessor getLabelAccessor();

  abstract class FacetLabelAccessor {
    pr vate  nt currentTerm D = -1;

    protected f nal BytesRef termRef = new BytesRef();
    protected boolean hasTermPayload = false;
    protected f nal BytesRef termPayload = new BytesRef();
    protected  nt offens veCount = 0;

    protected f nal boolean maybeSeek(long term D) {
       f (term D == currentTerm D) {
        return true;
      }

       f (seek(term D)) {
        currentTerm D = ( nt) term D;
        return true;
      } else {
        currentTerm D = -1;
        return false;
      }
    }

    // Seek to term  d prov ded.  Returns true  f term found.  Should update termRef,
    // hasTermPayload, and termPayload as appropr ate.
    protected abstract boolean seek(long term D);

    publ c f nal BytesRef getTermRef(long term D) {
      return maybeSeek(term D) ? termRef : null;
    }

    publ c Str ng getTermText(long term D) {
      return maybeSeek(term D) ? termRef.utf8ToStr ng() : null;
    }

    publ c f nal BytesRef getTermPayload(long term D) {
      return maybeSeek(term D) && hasTermPayload ? termPayload : null;
    }

    publ c f nal  nt getOffens veCount(long term D) {
      return maybeSeek(term D) ? offens veCount : 0;
    }
  }

  /**
   * Assu s t  term  s stored as an  ntTermAttr bute, and uses t  to convert
   * t  term bytesref to an  nteger str ng facet label.
   */
  class  ntTermFacetLabelProv der  mple nts FacetLabelProv der {
      pr vate f nal  nverted ndex  nverted ndex;

    publ c  ntTermFacetLabelProv der( nverted ndex  nverted ndex) {
      t . nverted ndex =  nverted ndex;
    }

    @Overr de
    publ c FacetLabelAccessor getLabelAccessor() {
      return new FacetLabelAccessor() {
        @Overr de
        protected boolean seek(long term D) {
           f (term D != HashTable.EMPTY_SLOT) {
             nverted ndex.getTerm(( nt) term D, termRef);
            return true;
          }
          return false;
        }

        @Overr de
        publ c Str ng getTermText(long term D) {
          return maybeSeek(term D)
                 ?  nteger.toStr ng( ntTermAttr bute mpl.copyBytesRefTo nt(termRef))
                 : null;
        }
      };
    }
  }

  /**
   * Assu s t  term  s stored as an LongTermAttr bute, and uses t  to convert
   * t  term bytesref to an long str ng facet label.
   */
  class LongTermFacetLabelProv der  mple nts FacetLabelProv der {
    pr vate f nal  nverted ndex  nverted ndex;

    publ c LongTermFacetLabelProv der( nverted ndex  nverted ndex) {
      t . nverted ndex =  nverted ndex;
    }

    @Overr de
    publ c FacetLabelAccessor getLabelAccessor() {
      return new FacetLabelAccessor() {
        @Overr de
        protected boolean seek(long term D) {
           f (term D != HashTable.EMPTY_SLOT) {
             nverted ndex.getTerm(( nt) term D, termRef);
            return true;
          }
          return false;
        }

        @Overr de
        publ c Str ng getTermText(long term D) {
          return maybeSeek(term D)
                 ? Long.toStr ng(LongTermAttr bute mpl.copyBytesRefToLong(termRef))
                 : null;
        }
      };
    }
  }

  class SortedLongTermFacetLabelProv der  mple nts FacetLabelProv der {
    pr vate f nal  nverted ndex  nverted ndex;

    publ c SortedLongTermFacetLabelProv der( nverted ndex  nverted ndex) {
      t . nverted ndex =  nverted ndex;
    }

    @Overr de
    publ c FacetLabelAccessor getLabelAccessor() {
      return new FacetLabelAccessor() {
        @Overr de
        protected boolean seek(long term D) {
           f (term D != HashTable.EMPTY_SLOT) {
             nverted ndex.getTerm(( nt) term D, termRef);
            return true;
          }
          return false;
        }

        @Overr de
        publ c Str ng getTermText(long term D) {
          return maybeSeek(term D)
              ? Long.toStr ng(SortableLongTermAttr bute mpl.copyBytesRefToLong(termRef))
              : null;
        }
      };
    }
  }

  class  dent yFacetLabelProv der  mple nts FacetLabelProv der {
    @Overr de
    publ c FacetLabelAccessor getLabelAccessor() {
      return new FacetLabelAccessor() {
        @Overr de
        protected boolean seek(long term D) {
          return true;
        }

        @Overr de
        publ c Str ng getTermText(long term D) {
          return Long.toStr ng(term D);
        }
      };
    }
  }

  /**
   * T   thods on t  prov der should NOT be called under normal c rcumstances!
   *
   * W n a facet m sses  nverted  ndex and does not use CSF, t   naccess bleFacetLabelProv der
   * w ll be used as a dum  prov der. T n, unexptectedFacetLabelAccess counter w ll be
   *  ncre nted w n t  prov der  s used later.
   *
   * Also see:
   * {@l nk FacetUt l}
   */
  class  naccess bleFacetLabelProv der  mple nts FacetLabelProv der {
    pr vate f nal SearchCounter unexptectedFacetLabelAccess;

    publ c  naccess bleFacetLabelProv der(Str ng f eldNa ) {
      t .unexptectedFacetLabelAccess =
          SearchCounter.export("unexpected_facet_label_access_for_f eld_" + f eldNa );
    }

    @Overr de
    publ c FacetLabelAccessor getLabelAccessor() {
      return new FacetLabelAccessor() {
        @Overr de
        protected boolean seek(long term D) {
          unexptectedFacetLabelAccess. ncre nt();
          return false;
        }
      };
    }
  }
}
