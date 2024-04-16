package com.tw ter.search.common.search;

 mport java. o. OExcept on;

 mport org.apac .lucene.search.Doc dSet erator;

publ c class AndNotDoc dSet erator extends Doc dSet erator {
  pr vate  nt nextDelDoc;
  pr vate f nal Doc dSet erator base er;
  pr vate f nal Doc dSet erator not er;
  pr vate  nt curr D;

  /** Creates a new AndNotDoc dSet erator  nstance. */
  publ c AndNotDoc dSet erator(Doc dSet erator base er, Doc dSet erator not er)
          throws  OExcept on {
    nextDelDoc = not er.nextDoc();
    t .base er = base er;
    t .not er = not er;
    curr D = -1;
  }

  @Overr de
  publ c  nt advance( nt target) throws  OExcept on {
    curr D = base er.advance(target);
     f (curr D == Doc dSet erator.NO_MORE_DOCS) {
      return curr D;
    }

     f (nextDelDoc != Doc dSet erator.NO_MORE_DOCS) {
       f (curr D < nextDelDoc) {
        return curr D;
      } else  f (curr D == nextDelDoc) {
        return nextDoc();
      } else {
        nextDelDoc = not er.advance(curr D);
         f (curr D == nextDelDoc) {
          return nextDoc();
        }
      }
    }
    return curr D;
  }

  @Overr de
  publ c  nt doc D() {
    return curr D;
  }

  @Overr de
  publ c  nt nextDoc() throws  OExcept on {
    curr D = base er.nextDoc();
     f (nextDelDoc != Doc dSet erator.NO_MORE_DOCS) {
      wh le (curr D != Doc dSet erator.NO_MORE_DOCS) {
         f (curr D < nextDelDoc) {
          return curr D;
        } else {
           f (curr D == nextDelDoc) {
            curr D = base er.nextDoc();
          }
          nextDelDoc = not er.advance(curr D);
        }
      }
    }
    return curr D;
  }

  @Overr de
  publ c long cost() {
    return base er.cost();
  }
}
