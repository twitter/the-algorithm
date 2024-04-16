package com.tw ter.search.core.earlyb rd. ndex. nverted;

 mport java. o. OExcept on;
 mport java.ut l.Collect ons;
 mport java.ut l.L st;
 mport java.ut l.Map;

 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect.L sts;
 mport com.google.common.collect.Maps;

 mport org.apac .lucene. ndex.Post ngsEnum;
 mport org.apac .lucene.ut l.BytesRef;

 mport com.tw ter.search.core.earlyb rd. ndex.Doc DToT et DMapper;

/**
 * A Post ngsEnum that maps doc  Ds  n one Doc DToT et DMapper  nstance to doc  Ds  n anot r
 * Doc DToT et DMapper.
 *
 * Unopt m zed seg nts can use any Doc DToT et DMapper t y want, wh ch  ans that t re are no
 * guarantees on t  d str but on of t  doc  Ds  n t  mapper. Ho ver, opt m zed seg nts must
 * use an Opt m zedT et DMapper:   want to ass gn sequent al doc  Ds and use delta encond ngs  n
 * order to save space. So w n an Earlyb rd seg nt needs to be opt m zed,   m ght need to convert
 * t  doc  D space of t  unopt m zed t et  D mapper to t  doc  D space of t  opt m zed mapper.
 * Ho ver, once   do t , t  doc  Ds stored  n t  post ng l sts  n that seg nt w ll no longer
 * be val d, unless   remap t m too. So t  goal of t  class  s to prov de a way to do that.
 *
 * W n   want to opt m ze a post ng l st,   need to traverse   and pack  . T  class prov des
 * a wrapper around t  or g nal post ng l st that does t  doc  D remapp ng at traversal t  .
 */
publ c class Opt m z ngPost ngsEnumWrapper extends Post ngsEnum {
  pr vate f nal L st< nteger> doc ds = L sts.newArrayL st();
  pr vate f nal Map< nteger, L st< nteger>> pos  ons = Maps.newHashMap();

  pr vate  nt doc d ndex = -1;
  pr vate  nt pos  on ndex = -1;

  publ c Opt m z ngPost ngsEnumWrapper(Post ngsEnum s ce,
                                       Doc DToT et DMapper or g nalT et dMapper,
                                       Doc DToT et DMapper newT et dMapper) throws  OExcept on {
     nt doc d;
    wh le ((doc d = s ce.nextDoc()) != NO_MORE_DOCS) {
      long t et d = or g nalT et dMapper.getT et D(doc d);
       nt newDoc d = newT et dMapper.getDoc D(t et d);
      Precond  ons.c ckState(newDoc d != Doc DToT et DMapper. D_NOT_FOUND,
          "D d not f nd a mapp ng  n t  new t et  D mapper for t et  D %s, doc  D %s",
          t et d, doc d);

      doc ds.add(newDoc d);
      L st< nteger> docPos  ons = L sts.newArrayL stW hCapac y(s ce.freq());
      pos  ons.put(newDoc d, docPos  ons);
      for ( nt   = 0;   < s ce.freq(); ++ ) {
        docPos  ons.add(s ce.nextPos  on());
      }
    }
    Collect ons.sort(doc ds);
  }

  @Overr de
  publ c  nt nextDoc() {
    ++doc d ndex;
     f (doc d ndex >= doc ds.s ze()) {
      return NO_MORE_DOCS;
    }

    pos  on ndex = -1;
    return doc ds.get(doc d ndex);
  }

  @Overr de
  publ c  nt freq() {
    Precond  ons.c ckState(doc d ndex >= 0, "freq() called before nextDoc().");
    Precond  ons.c ckState(doc d ndex < doc ds.s ze(),
                             "freq() called after nextDoc() returned NO_MORE_DOCS.");
    return pos  ons.get(doc ds.get(doc d ndex)).s ze();
  }

  @Overr de
  publ c  nt nextPos  on() {
    Precond  ons.c ckState(doc d ndex >= 0, "nextPos  on() called before nextDoc().");
    Precond  ons.c ckState(doc d ndex < doc ds.s ze(),
                             "nextPos  on() called after nextDoc() returned NO_MORE_DOCS.");

    ++pos  on ndex;
    Precond  ons.c ckState(pos  on ndex < pos  ons.get(doc ds.get(doc d ndex)).s ze(),
                             "nextPos  on() called more than freq() t  s.");
    return pos  ons.get(doc ds.get(doc d ndex)).get(pos  on ndex);
  }

  // All ot r  thods are not supported.

  @Overr de
  publ c  nt advance( nt target) {
    throw new UnsupportedOperat onExcept on(
        "Opt m z ngPost ngsEnumWrapper.advance()  s not supported.");
  }

  @Overr de
  publ c long cost() {
    throw new UnsupportedOperat onExcept on(
        "Opt m z ngPost ngsEnumWrapper.cost()  s not supported.");
  }

  @Overr de
  publ c  nt doc D() {
    throw new UnsupportedOperat onExcept on(
        "Opt m z ngPost ngsEnumWrapper.doc D()  s not supported.");
  }

  @Overr de
  publ c  nt endOffset() {
    throw new UnsupportedOperat onExcept on(
        "Opt m z ngPost ngsEnumWrapper.endOffset()  s not supported.");
  }

  @Overr de
  publ c BytesRef getPayload() {
    throw new UnsupportedOperat onExcept on(
        "Opt m z ngPost ngsEnumWrapper.getPayload()  s not supported.");
  }

  @Overr de
  publ c  nt startOffset() {
    throw new UnsupportedOperat onExcept on(
        "Opt m z ngPost ngsEnumWrapper.startOffset()  s not supported.");
  }
}
