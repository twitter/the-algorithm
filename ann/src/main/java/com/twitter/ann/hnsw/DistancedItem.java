package com.tw ter.ann.hnsw;

/**
 * An  em assoc ated w h a float d stance
 * @param <T> T  type of t   em.
 */
publ c class D stanced em<T> {
  pr vate f nal T  em;
  pr vate f nal float d stance;

  publ c D stanced em(T  em, float d stance) {
    t . em =  em;
    t .d stance = d stance;
  }

  publ c T get em() {
    return  em;
  }

  publ c float getD stance() {
    return d stance;
  }
}
