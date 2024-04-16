package com.tw ter.search.earlyb rd. ndex;

 mport java. o. OExcept on;

 mport com.google.common.base.Precond  ons;

 mport com.tw ter.search.common.sc ma.base.Earlyb rdF eldType;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntAtom cReader;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntData;
 mport com.tw ter.search.core.earlyb rd. ndex.column.ColumnStr deF eld ndex;
 mport com.tw ter.search.core.earlyb rd. ndex.extens ons.Earlyb rd ndexExtens onsData;

publ c class T etSearchLucene ndexExtens onsData  mple nts Earlyb rd ndexExtens onsData {
  @Overr de
  publ c vo d setupExtens ons(Earlyb rd ndexSeg ntAtom cReader atom cReader) throws  OExcept on {
    //  f   use stock lucene to back t  mappers and column str de f elds,
    //   need to  n  al ze t m
    Earlyb rd ndexSeg ntData seg ntData = atom cReader.getSeg ntData();
    DocValuesBasedT et DMapper t et DMapper =
        (DocValuesBasedT et DMapper) seg ntData.getDoc DToT et DMapper();
    t et DMapper. n  al zeW hLuceneReader(
        atom cReader,
        getColumnStr deF eld ndex(seg ntData, Earlyb rdF eldConstant. D_CSF_F ELD));

    DocValuesBasedT  Mapper t  Mapper =
        (DocValuesBasedT  Mapper) seg ntData.getT  Mapper();
    t  Mapper. n  al zeW hLuceneReader(
        atom cReader,
        getColumnStr deF eld ndex(seg ntData, Earlyb rdF eldConstant.CREATED_AT_CSF_F ELD));
  }

  pr vate ColumnStr deF eld ndex getColumnStr deF eld ndex(
      Earlyb rd ndexSeg ntData seg ntData, Earlyb rdF eldConstant csfF eld) {
    Str ng csfF eldNa  = csfF eld.getF eldNa ();
    Earlyb rdF eldType f eldType =
        seg ntData.getSc ma().getF eld nfo(csfF eldNa ).getF eldType();
    Precond  ons.c ckState(f eldType. sCsfLoad ntoRam());
    return seg ntData.getDocValuesManager().addColumnStr deF eld(csfF eldNa , f eldType);
  }
}
