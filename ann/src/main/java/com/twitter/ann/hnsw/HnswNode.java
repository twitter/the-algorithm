package com.tw ter.ann.hnsw;

 mport org.apac .commons.lang.bu lder.EqualsBu lder;
 mport org.apac .commons.lang.bu lder.HashCodeBu lder;

publ c class HnswNode<T> {
  publ c f nal  nt level;
  publ c f nal T  em;

  publ c HnswNode( nt level, T  em) {
    t .level = level;
    t . em =  em;
  }

  /**
   * Create a hnsw node.
   */
  publ c stat c <T> HnswNode<T> from( nt level, T  em) {
    return new HnswNode<>(level,  em);
  }

  @Overr de
  publ c boolean equals(Object o) {
     f (o == t ) {
      return true;
    }
     f (!(o  nstanceof HnswNode)) {
      return false;
    }

    HnswNode<?> that = (HnswNode<?>) o;
    return new EqualsBu lder()
        .append(t . em, that. em)
        .append(t .level, that.level)
        . sEquals();
  }

  @Overr de
  publ c  nt hashCode() {
    return new HashCodeBu lder()
        .append( em)
        .append(level)
        .toHashCode();
  }
}
