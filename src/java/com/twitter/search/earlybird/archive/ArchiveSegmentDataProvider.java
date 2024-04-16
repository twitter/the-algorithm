package com.tw ter.search.earlyb rd.arch ve;

 mport java. o. OExcept on;
 mport java.ut l.L st;

 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect.L sts;

 mport com.tw ter.search.common.part  on ng.base.Seg nt;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ft ndex ngEvent;
 mport com.tw ter.search.common.ut l. o.recordreader.RecordReader;
 mport com.tw ter.search.earlyb rd.Earlyb rd ndexConf g;
 mport com.tw ter.search.earlyb rd.arch ve.Arch veT  Sl cer.Arch veT  Sl ce;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdConf g;
 mport com.tw ter.search.earlyb rd.docu nt.Docu ntFactory;
 mport com.tw ter.search.earlyb rd.docu nt.T etDocu nt;
 mport com.tw ter.search.earlyb rd.part  on.Dynam cPart  onConf g;
 mport com.tw ter.search.earlyb rd.part  on.Seg nt nfo;
 mport com.tw ter.search.earlyb rd.seg nt.EmptySeg ntDataReaderSet;
 mport com.tw ter.search.earlyb rd.seg nt.Seg ntDataProv der;
 mport com.tw ter.search.earlyb rd.seg nt.Seg ntDataReaderSet;

publ c class Arch veSeg ntDataProv der  mple nts Seg ntDataProv der {
  pr vate stat c f nal org.slf4j.Logger LOG =
      org.slf4j.LoggerFactory.getLogger(Arch veSeg ntDataProv der.class);

  pr vate Dynam cPart  onConf g dynam cPart  onConf g;
  pr vate f nal Arch veT  Sl cer t  Sl cer;

  pr vate f nal Docu ntFactory<Thr ft ndex ngEvent> docu ntFactory;

  pr vate f nal Seg ntDataReaderSet readerSet;

  publ c Arch veSeg ntDataProv der(
      Dynam cPart  onConf g dynam cPart  onConf g,
      Arch veT  Sl cer t  Sl cer,
      Earlyb rd ndexConf g earlyb rd ndexConf g) throws  OExcept on {
    t .dynam cPart  onConf g = dynam cPart  onConf g;
    t .t  Sl cer = t  Sl cer;
    t .readerSet = createSeg ntDataReaderSet();
    t .docu ntFactory = earlyb rd ndexConf g.createDocu ntFactory();
  }

  @Overr de
  publ c L st<Seg nt> newSeg ntL st() throws  OExcept on {
    L st<Arch veT  Sl ce> t  Sl ces = t  Sl cer.getT  Sl ces nT erRange();
     f (t  Sl ces == null || t  Sl ces. sEmpty()) {
      return L sts.newArrayL st();
    }
    L st<Seg nt> seg nts = L sts.newArrayL stW hCapac y(t  Sl ces.s ze());
    for (Arch veT  Sl ce t  Sl ce : t  Sl ces) {
      seg nts.add(newArch veSeg nt(t  Sl ce));
    }
    return seg nts;
  }

  /**
   * Creates a new Seg nt  nstance for t  g ven t  sl ce.
   */
  publ c Arch veSeg nt newArch veSeg nt(Arch veT  Sl ce arch veT  Sl ce) {
    return new Arch veSeg nt(
        arch veT  Sl ce,
        dynam cPart  onConf g.getCurrentPart  onConf g().get ndex ngHashPart  on D(),
        Earlyb rdConf g.getMaxSeg ntS ze());
  }

  @Overr de
  publ c Seg ntDataReaderSet getSeg ntDataReaderSet() {
    return readerSet;
  }

  pr vate EmptySeg ntDataReaderSet createSeg ntDataReaderSet() throws  OExcept on {
    return new EmptySeg ntDataReaderSet() {

      @Overr de
      publ c RecordReader<T etDocu nt> newDocu ntReader(Seg nt nfo seg nt nfo)
          throws  OExcept on {
        Seg nt seg nt = seg nt nfo.getSeg nt();
        Precond  ons.c ckArgu nt(seg nt  nstanceof Arch veSeg nt);
        return ((Arch veSeg nt) seg nt).getStatusRecordReader(docu ntFactory);
      }
    };
  }
}
