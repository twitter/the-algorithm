package com.tw ter.search.core.earlyb rd. ndex. nverted;

/**
 * Encodes and decodes term po nters.
 */
publ c abstract class TermPo nterEncod ng {
  /**
   * Returns t  start of t  text stored  n a {@l nk BaseByteBlockPool} of t  g ven term.
   */
  publ c abstract  nt getTextStart( nt termPo nter);

  /**
   * Returns true,  f t  g ven term stores a per-term payload.
   */
  publ c abstract boolean hasPayload( nt termPo nter);

  /**
   * Encodes and returns a po nter for a term stored at t  g ven textStart  n a
   * {@l nk BaseByteBlockPool}.
   */
  publ c abstract  nt encodeTermPo nter( nt textStart, boolean hasPayload);

  publ c stat c f nal TermPo nterEncod ng DEFAULT_ENCOD NG = new TermPo nterEncod ng() {
    @Overr de publ c  nt getTextStart( nt termPo nter) {
      return termPo nter >>> 1;
    }

    @Overr de publ c boolean hasPayload( nt termPo nter) {
      return (termPo nter & 1) != 0;
    }

    @Overr de
    publ c  nt encodeTermPo nter( nt textStart, boolean hasPayload) {
       nt code = textStart << 1;
      return hasPayload ? (code | 1) : code;
    }
  };
}
