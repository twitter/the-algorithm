package com.tw ter.search.common.relevance.ent  es;

 mport org.apac .commons.lang3.bu lder.EqualsBu lder;
 mport org.apac .commons.lang3.bu lder.HashCodeBu lder;
 mport org.apac .commons.lang3.bu lder.ToStr ngBu lder;

/**
 * T  object for quoted  ssage
  */
publ c class Tw terQuoted ssage {
  pr vate f nal long quotedStatus d;
  pr vate f nal long quotedUser d;

  publ c Tw terQuoted ssage(long quotedStatus d, long quotedUser d) {
    t .quotedStatus d = quotedStatus d;
    t .quotedUser d = quotedUser d;
  }

  publ c long getQuotedStatus d() {
    return quotedStatus d;
  }

  publ c long getQuotedUser d() {
    return quotedUser d;
  }

  @Overr de
  publ c boolean equals(Object o) {
    return EqualsBu lder.reflect onEquals(t , o);
  }

  @Overr de
  publ c  nt hashCode() {
    return HashCodeBu lder.reflect onHashCode(t );
  }

  @Overr de
  publ c Str ng toStr ng() {
    return ToStr ngBu lder.reflect onToStr ng(t );
  }
}
