package com.tw ter.search.earlyb rd_root.cach ng;

 mport javax. nject. nject;

 mport com.tw ter.common.base.Suppl er;
 mport com.tw ter.search.common.dec der.SearchDec der;

/**
 * A cac  m ss dec der backed by a dec der key.
 */
publ c class DefaultForcedCac M ssDec der  mple nts Suppl er<Boolean> {
  pr vate stat c f nal Str ng DEC DER_KEY = "default_forced_cac _m ss_rate";
  pr vate f nal SearchDec der dec der;

  @ nject
  publ c DefaultForcedCac M ssDec der(SearchDec der dec der) {
    t .dec der = dec der;
  }

  @Overr de
  publ c Boolean get() {
    return dec der. sAva lable(DEC DER_KEY);
  }
}
