package com.tw ter.search.earlyb rd.querycac ;

 mport java. o. OExcept on;

 mport org.apac .lucene.search.Doc dSet erator;

publ c class Cac dResultDoc dSet erator extends Doc dSet erator {
  // W h t  realt    ndex,   grow t  doc  d negat vely.
  //  nce t  smallest doc  d  s t   D t  latest/ne st docu nt  n t  cac .
  pr vate f nal  nt cac dSmallestDoc D;

  // Docu nts that  re  ndexed after t  last cac  update
  pr vate f nal Doc dSet erator freshDoc d erator;
  // Docu nts that  re cac d
  pr vate f nal Doc dSet erator cac dDoc d erator;

  pr vate  nt currentDoc d;
  pr vate boolean  n  al zed = false;

  publ c Cac dResultDoc dSet erator( nt cac dSmallestDoc D,
                                      Doc dSet erator freshDoc d erator,
                                      Doc dSet erator cac dDoc d erator) {
    t .cac dSmallestDoc D = cac dSmallestDoc D;

    t .freshDoc d erator = freshDoc d erator;
    t .cac dDoc d erator = cac dDoc d erator;
    t .currentDoc d = -1;
  }

  @Overr de
  publ c  nt doc D() {
    return currentDoc d;
  }

  @Overr de
  publ c  nt nextDoc() throws  OExcept on {
     f (currentDoc d < cac dSmallestDoc D) {
      currentDoc d = freshDoc d erator.nextDoc();
    } else  f (currentDoc d != NO_MORE_DOCS) {
       f (! n  al zed) {
        // t  f rst t     co   n  re, currentDoc d should be po nt ng to
        // so th ng >= cac dM nDoc D.   need to go to t  doc after cac dM nDoc D.
        currentDoc d = cac dDoc d erator.advance(currentDoc d + 1);
         n  al zed = true;
      } else {
        currentDoc d = cac dDoc d erator.nextDoc();
      }
    }
    return currentDoc d;
  }

  @Overr de
  publ c  nt advance( nt target) throws  OExcept on {
     f (target < cac dSmallestDoc D) {
      currentDoc d = freshDoc d erator.advance(target);
    } else  f (currentDoc d != NO_MORE_DOCS) {
       n  al zed = true;
      currentDoc d = cac dDoc d erator.advance(target);
    }

    return currentDoc d;
  }

  @Overr de
  publ c long cost() {
     f (currentDoc d < cac dSmallestDoc D) {
      return freshDoc d erator.cost();
    } else {
      return cac dDoc d erator.cost();
    }
  }
}
