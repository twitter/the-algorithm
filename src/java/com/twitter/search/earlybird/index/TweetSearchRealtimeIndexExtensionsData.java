package com.tw ter.search.earlyb rd. ndex;

 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntAtom cReader;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rdRealt   ndexSeg ntWr er. nvertedDocConsu rBu lder;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rdRealt   ndexSeg ntWr er.StoredF eldsConsu rBu lder;
 mport com.tw ter.search.core.earlyb rd. ndex.extens ons.Earlyb rdRealt   ndexExtens onsData;

publ c class T etSearchRealt   ndexExtens onsData
     mple nts Earlyb rdRealt   ndexExtens onsData {
  @Overr de
  publ c vo d createStoredF eldsConsu r(StoredF eldsConsu rBu lder bu lder) {
    // no extens ons necessary  re
  }

  @Overr de
  publ c vo d create nvertedDocConsu r( nvertedDocConsu rBu lder bu lder) {
     f (Earlyb rdF eldConstant. D_F ELD.getF eldNa ().equals(bu lder.getF eldNa ())) {
      // T  t et  D should've already been added to t  t et  D <-> doc  D mapper.
      bu lder.setUseDefaultConsu r(false);
    }

     f (Earlyb rdF eldConstant.CREATED_AT_F ELD.getF eldNa ().equals(bu lder.getF eldNa ())) {
      Realt  T  Mapper t  Mapper = (Realt  T  Mapper) bu lder.getSeg ntData().getT  Mapper();
      bu lder.addConsu r(new T  Mapp ngWr er(t  Mapper));
      bu lder.setUseDefaultConsu r(false);
    }
  }

  @Overr de
  publ c vo d setupExtens ons(Earlyb rd ndexSeg ntAtom cReader atom cReader) {
  }
}
