package com.tw ter.search.earlyb rd.seg nt;

 mport java. o. OExcept on;
 mport java.ut l.ArrayL st;
 mport java.ut l.Collect ons;
 mport java.ut l.L st;
 mport java.ut l.Set;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.search.common.part  on ng.base.Seg nt;
 mport com.tw ter.search.common.ut l. o.dl.DLReaderWr erFactory;
 mport com.tw ter.search.common.ut l. o.dl.Seg ntDLUt l;
 mport com.tw ter.search.earlyb rd.Earlyb rd ndexConf g;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdConf g;

/**
 * An  mple ntat on of Seg ntDataProv der us ng D str butedLog.
 */
publ c class DLSeg ntDataProv der  mple nts Seg ntDataProv der {
  pr vate f nal  nt hashPart  on D;
  pr vate f nal DLReaderWr erFactory dlFactory;
  pr vate f nal Seg ntDataReaderSet readerSet;

  publ c DLSeg ntDataProv der(
       nt hashPart  on D,
      Earlyb rd ndexConf g earlyb rd ndexConf g,
      DLReaderWr erFactory dlReaderWr erFactory) throws  OExcept on {
    t (hashPart  on D, earlyb rd ndexConf g, dlReaderWr erFactory,
        Clock.SYSTEM_CLOCK);
  }

  publ c DLSeg ntDataProv der(
     nt hashPart  on D,
    Earlyb rd ndexConf g earlyb rd ndexConf g,
    DLReaderWr erFactory dlReaderWr erFactory,
    Clock clock) throws  OExcept on {
    t .hashPart  on D = hashPart  on D;
    t .dlFactory = dlReaderWr erFactory;
    t .readerSet = new DLSeg ntDataReaderSet(
        dlFactory,
        earlyb rd ndexConf g,
        clock);
  }

  @Overr de
  publ c Seg ntDataReaderSet getSeg ntDataReaderSet() {
    return readerSet;
  }

  @Overr de
  publ c L st<Seg nt> newSeg ntL st() throws  OExcept on {
    Set<Str ng> seg ntNa s = Seg ntDLUt l.getSeg ntNa s(dlFactory, null, hashPart  on D);
    L st<Seg nt> seg ntL st = new ArrayL st<>(seg ntNa s.s ze());
    for (Str ng seg ntNa  : seg ntNa s) {
      Seg nt seg nt = Seg nt.fromSeg ntNa (seg ntNa , Earlyb rdConf g.getMaxSeg ntS ze());
      seg ntL st.add(seg nt);
    }
    // Sort t  seg nts by  D.
    Collect ons.sort(seg ntL st);
    return seg ntL st;
  }
}
