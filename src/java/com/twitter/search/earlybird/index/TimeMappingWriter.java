package com.tw ter.search.earlyb rd. ndex;

 mport java. o. OExcept on;

 mport org.apac .lucene.ut l.Attr buteS ce;

 mport com.tw ter.search.common.ut l.analys s. ntTermAttr bute;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rdRealt   ndexSeg ntWr er;

publ c class T  Mapp ngWr er  mple nts Earlyb rdRealt   ndexSeg ntWr er. nvertedDocConsu r {
  pr vate  ntTermAttr bute termAtt;
  pr vate f nal Realt  T  Mapper mapper;

  publ c T  Mapp ngWr er(Realt  T  Mapper mapper) {
    t .mapper = mapper;
  }

  @Overr de
  publ c f nal vo d start(Attr buteS ce attr buteS ce, boolean currentDoc sOffens ve) {
    termAtt = attr buteS ce.addAttr bute( ntTermAttr bute.class);
  }

  @Overr de
  publ c f nal vo d add( nt doc d,  nt pos  on) throws  OExcept on {
    f nal  nt t  Sec = termAtt.getTerm();
    mapper.addMapp ng(doc d, t  Sec);
  }

  @Overr de
  publ c vo d f n sh() {
  }
}
