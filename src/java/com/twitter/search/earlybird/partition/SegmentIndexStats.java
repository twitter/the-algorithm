package com.tw ter.search.earlyb rd.part  on;

 mport java.ut l.Opt onal;
 mport java.ut l.concurrent.atom c.Atom c nteger;
 mport java.ut l.concurrent.atom c.Atom cLong;

 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntData;

publ c class Seg nt ndexStats {
  pr vate Earlyb rd ndexSeg ntData seg ntData;

  pr vate f nal Atom cLong  ndexS zeOnD sk nBytes = new Atom cLong(0);
  pr vate f nal Atom c nteger part alUpdateCount = new Atom c nteger(0);
  pr vate f nal Atom c nteger outOfOrderUpdateCount = new Atom c nteger(0);

  pr vate Opt onal< nteger> savedStatusCount = Opt onal.empty();
  pr vate Opt onal< nteger> savedDeletesCount = Opt onal.empty();

  publ c vo d setSeg ntData(Earlyb rd ndexSeg ntData seg ntData) {
    t .seg ntData = seg ntData;
  }

  /**
   *  'd l ke to be able to return t  last counts after   unload a seg nt from  mory.
   */
  publ c vo d unsetSeg ntDataAndSaveCounts() {
    savedStatusCount = Opt onal.of(getStatusCount());
    savedDeletesCount = Opt onal.of(getDeleteCount());
    seg ntData = null;
  }

  /**
   * Returns t  number of deletes processed by t  seg nt.
   */
  publ c  nt getDeleteCount() {
     f (seg ntData != null) {
      return seg ntData.getDeletedDocs().numDelet ons();
    } else {
      return savedDeletesCount.orElse(0);
    }
  }

  /**
   * Return t  number of docu nts  n t  seg nt.
   */
  publ c  nt getStatusCount() {
     f (seg ntData != null) {
      return seg ntData.numDocs();
    } else {
      return savedStatusCount.orElse(0);
    }
  }

  publ c long get ndexS zeOnD sk nBytes() {
    return  ndexS zeOnD sk nBytes.get();
  }

  publ c vo d set ndexS zeOnD sk nBytes(long value) {
     ndexS zeOnD sk nBytes.set(value);
  }

  publ c  nt getPart alUpdateCount() {
    return part alUpdateCount.get();
  }

  publ c vo d  ncre ntPart alUpdateCount() {
    part alUpdateCount. ncre ntAndGet();
  }

  publ c vo d setPart alUpdateCount( nt value) {
    part alUpdateCount.set(value);
  }

  publ c  nt getOutOfOrderUpdateCount() {
    return outOfOrderUpdateCount.get();
  }

  publ c vo d  ncre ntOutOfOrderUpdateCount() {
    outOfOrderUpdateCount. ncre ntAndGet();
  }

  publ c vo d setOutOfOrderUpdateCount( nt value) {
    outOfOrderUpdateCount.set(value);
  }

  @Overr de
  publ c Str ng toStr ng() {
    Str ngBu lder sb = new Str ngBu lder();
    sb.append(" ndexed ").append(getStatusCount()).append(" docu nts, ");
    sb.append(getDeleteCount()).append(" deletes, ");
    sb.append(getPart alUpdateCount()).append(" part al updates, ");
    sb.append(getOutOfOrderUpdateCount()).append(" out of order udpates. ");
    sb.append(" ndex s ze: ").append(get ndexS zeOnD sk nBytes());
    return sb.toStr ng();
  }
}
