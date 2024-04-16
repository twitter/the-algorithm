package com.tw ter.search.core.earlyb rd. ndex. nverted;

/**
 * Comparator  nterface for {@l nk Sk pL stConta ner},
 * see sample  mple ntat on {@l nk Sk pL st ntegerComparator}.
 *
 * Not ce: less/equal/greater  re refer to t  order precedence,  nstead of nu r cal value.
 */
publ c  nterface Sk pL stComparator<K> {

  /**
   * Determ ne t  order bet en t  g ven key and t  key of t  g ven targetValue.
   * Not ce, usually key of a value could be der ved from t  value along.
   *
   *  mple ntat on of t   thod should cons der sent nel value, see {@l nk #getSent nelValue()}.
   *
   * Can  nclude pos  on data (pr mar ly for text post ng l sts). Pos  on should be  gnored  f
   * t  sk p l st was constructed w hout pos  ons enabled.
   *
   * @return negat ve, zero, or pos  ve to  nd cate  f f rst value  s
   *         less than, equal to, or greater than t  second value, respect vely.
   */
   nt compareKeyW hValue(K key,  nt targetValue,  nt targetPos  on);

  /**
   * Determ ne t  order of two g ven values based on t  r keys.
   * Not ce, usually key of a value could be der ved from t  value along.
   *
   *  mple ntat on of t   thod should cons der sent nel value, see {@l nk #getSent nelValue()}.
   *
   * @return negat ve, zero, or pos  ve to  nd cate  f f rst value  s
   *         less than, equal to, or greater than t  second value, respect vely.
   */
   nt compareValues( nt v1,  nt v2);

  /**
   * Return a sent nel value, sent nel value should be cons dered by t  comparator
   * as an ADV SORY GREATEST value, wh ch should NOT be actually  nserted  nto t  sk p l st.
   *
   * @return t  sent nel value.
   */
   nt getSent nelValue();
}
