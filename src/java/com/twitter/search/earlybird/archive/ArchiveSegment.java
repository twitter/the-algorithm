package com.tw ter.search.earlyb rd.arch ve;

 mport java. o. OExcept on;
 mport java.ut l.Date;

 mport com.google.common.base.Pred cate;
 mport com.google.common.base.Pred cates;

 mport com.tw ter.search.common.part  on ng.base.Seg nt;
 mport com.tw ter.search.common.part  on ng.base.T  Sl ce;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ft ndex ngEvent;
 mport com.tw ter.search.common.ut l. o.recordreader.RecordReader;
 mport com.tw ter.search.earlyb rd.arch ve.Arch veT  Sl cer.Arch veT  Sl ce;
 mport com.tw ter.search.earlyb rd.docu nt.Docu ntFactory;
 mport com.tw ter.search.earlyb rd.docu nt.T etDocu nt;

publ c class Arch veSeg nt extends Seg nt {
  pr vate f nal Arch veT  Sl ce arch veT  Sl ce;

  publ c stat c f nal Pred cate<Date> MATCH_ALL_DATE_PRED CATE =  nput -> true;

  // Constructor used for  ndex ng an arch ve seg nt
  publ c Arch veSeg nt(Arch veT  Sl ce arch veT  Sl ce,
                         nt hashPart  on D,
                         nt maxSeg ntS ze) {
    super(new T  Sl ce(arch veT  Sl ce.getM nStatus D(hashPart  on D),
            maxSeg ntS ze, hashPart  on D,
            arch veT  Sl ce.getNumHashPart  ons()),
        arch veT  Sl ce.getEndDate().getT  ());
    t .arch veT  Sl ce = arch veT  Sl ce;
  }

  /**
   * Constructor used for load ng a flus d seg nt. Only be used by Seg ntBu lder; Earlyb rd
   * does not use t .
   */
  Arch veSeg nt(long t  Sl ce d,
                  nt maxSeg ntS ze,
                  nt part  ons,
                  nt hashPart  on D,
                 Date dataEndDate) {
    super(new T  Sl ce(t  Sl ce d, maxSeg ntS ze, hashPart  on D, part  ons),
        dataEndDate.getT  ());
    // No arch ve t  sl ce  s needed for load ng.
    t .arch veT  Sl ce = null;
  }

  /**
   * Returns t  t ets reader for t  seg nt.
   *
   * @param docu ntFactory T  factory that converts Thr ftDocu nts to Lucene docu nts.
   */
  publ c RecordReader<T etDocu nt> getStatusRecordReader(
      Docu ntFactory<Thr ft ndex ngEvent> docu ntFactory) throws  OExcept on {
    return getStatusRecordReader(docu ntFactory, Pred cates.<Date>alwaysTrue());
  }

  /**
   * Returns t  t ets reader for t  seg nt.
   *
   * @param docu ntFactory T  factory that converts Thr ftDocu nts to Lucene docu nts.
   * @param f lter A pred cate that f lters t ets based on t  date t y  re created on.
   */
  publ c RecordReader<T etDocu nt> getStatusRecordReader(
      Docu ntFactory<Thr ft ndex ngEvent> docu ntFactory,
      Pred cate<Date> f lter) throws  OExcept on {
     f (arch veT  Sl ce != null) {
      return arch veT  Sl ce.getStatusReader(t , docu ntFactory, f lter);
    } else {
      throw new  llegalStateExcept on("Arch veSeg nt has no assoc ated Arch veT  sl ce."
          + "T  Arch veSeg nt can only be used for load ng flus d seg nts.");
    }
  }

  publ c Date getDataEndDate() {
    return arch veT  Sl ce == null
        ? new Date(getDataEndDate nclus veM ll s()) : arch veT  Sl ce.getEndDate();
  }

  publ c Arch veT  Sl ce getArch veT  Sl ce() {
    return arch veT  Sl ce;
  }

  @Overr de
  publ c Str ng toStr ng() {
    return super.toStr ng() + " " + arch veT  Sl ce.getDescr pt on();
  }
}
