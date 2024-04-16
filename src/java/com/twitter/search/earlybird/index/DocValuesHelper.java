package com.tw ter.search.earlyb rd. ndex;

 mport java. o. OExcept on;

 mport org.apac .lucene. ndex.LeafReader;
 mport org.apac .lucene. ndex.Terms;
 mport org.apac .lucene. ndex.TermsEnum;
 mport org.apac .lucene.search.Doc dSet erator;
 mport org.apac .lucene.ut l.BytesRef;

publ c f nal class DocValues lper {
  pr vate DocValues lper() {
  }

  /**
   * Reverse lookup. G ven a value, returns t  f rst doc  D w h t  value. T  requ res a f eld
   * that  ndexes t  values.
   *
   * @param reader T  reader to use to look up f eld values.
   * @param value T  value to lookup.
   * @param  ndexF eld T  f eld conta n ng an  ndex of t  values.
   */
  publ c stat c  nt getF rstDoc dW hValue(
      LeafReader reader, Str ng  ndexF eld, BytesRef value) throws  OExcept on {
    TermsEnum termsEnum = getTermsEnum(reader,  ndexF eld);
     f (termsEnum == null || !termsEnum.seekExact(value)) {
      return Doc dSet erator.NO_MORE_DOCS;
    }

    Doc dSet erator docs erator = termsEnum.post ngs(null);
    return docs erator.nextDoc();
  }

  /**
   * Reverse lookup. Sa  as getF rstDoc dW hValue(), but  f no docu nt w h t  g ven value
   * ex sts, t  next b gger value  s used for look ng up t  f rst doc  D.
   *
   *  f t re are mult ple docu nts that match t  value, all docu nts w ll be scanned, and t 
   * largest doc  D that matc s w ll be returned.
   *
   * @param reader T  reader to use to look up f eld values.
   * @param value T  value to lookup.
   * @param  ndexF eld T  f eld conta n ng an  ndex of t  values.
   */
  publ c stat c  nt getLargestDoc dW hCe lOfValue(
      LeafReader reader, Str ng  ndexF eld, BytesRef value) throws  OExcept on {
    TermsEnum termsEnum = getTermsEnum(reader,  ndexF eld);
     f (termsEnum == null) {
      return Doc dSet erator.NO_MORE_DOCS;
    }
     f (termsEnum.seekCe l(value) == TermsEnum.SeekStatus.END) {
      return Doc dSet erator.NO_MORE_DOCS;
    }

    Doc dSet erator docs erator = termsEnum.post ngs(null);
     nt doc d = docs erator.nextDoc();
    wh le (docs erator.nextDoc() != Doc dSet erator.NO_MORE_DOCS) {
      doc d = docs erator.doc D();
    }
    return doc d;
  }

  pr vate stat c TermsEnum getTermsEnum(LeafReader reader, Str ng  ndexF eld) throws  OExcept on {
    Terms terms = reader.terms( ndexF eld);
     f (terms == null) {
      return null;
    }
    return terms. erator();
  }
}
