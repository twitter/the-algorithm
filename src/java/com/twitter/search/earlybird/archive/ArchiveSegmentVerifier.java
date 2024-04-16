package com.tw ter.search.earlyb rd.arch ve;

 mport java. o. OExcept on;
 mport java.ut l.L st;

 mport com.google.common.annotat ons.V s bleForTest ng;

 mport org.apac .lucene. ndex.D rectoryReader;
 mport org.apac .lucene. ndex.LeafReader;
 mport org.apac .lucene. ndex.LeafReaderContext;
 mport org.apac .lucene.store.D rectory;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.earlyb rd.part  on.Seg nt nfo;

publ c f nal class Arch veSeg ntVer f er {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Arch veSeg ntVer f er.class);

  pr vate Arch veSeg ntVer f er() {
  }

  @V s bleForTest ng
  stat c boolean shouldVer fySeg nt(Seg nt nfo seg nt nfo) {
     f (seg nt nfo. s ndex ng()) {
      LOG.warn("Arch veSeg ntVer f er got seg nt st ll  ndex ng.");
      return false;
    }

     f (!seg nt nfo. sComplete()) {
      LOG.warn("Arch veSeg ntVer fyer got  ncomplete seg nt.");
      return false;
    }

     f (!seg nt nfo. sOpt m zed()) {
      LOG.warn("Arch veSeg ntVer fyer got unopt m zed seg nt.");
      return false;
    }

    return true;
  }

  /**
   * Ver f es an arch ve seg nt has a sane number of leaves.
   */
  publ c stat c boolean ver fySeg nt(Seg nt nfo seg nt nfo) {
     f (!shouldVer fySeg nt(seg nt nfo)) {
      return false;
    }
    D rectory d rectory = seg nt nfo.get ndexSeg nt().getLuceneD rectory();
    return ver fyLucene ndex(d rectory);
  }

  pr vate stat c boolean ver fyLucene ndex(D rectory d rectory) {
    try {
      D rectoryReader  ndexerReader = D rectoryReader.open(d rectory);
      L st<LeafReaderContext> leaves =  ndexerReader.getContext().leaves();
       f (leaves.s ze() != 1) {
        LOG.warn("Lucene  ndex does not have exactly one seg nt: " + leaves.s ze() + " != 1. "
            + "Lucene seg nts should have been  rged dur ng opt m zat on.");
        return false;
      }

      LeafReader reader = leaves.get(0).reader();
       f (reader.numDocs() <= 0) {
        LOG.warn("Lucene  ndex has no docu nt: " + reader);
        return false;
      }
      return true;
    } catch ( OExcept on e) {
      LOG.warn("Found bad lucene  ndex at: " + d rectory);
      return false;
    }
  }
}
