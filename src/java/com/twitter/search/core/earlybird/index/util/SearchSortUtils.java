package com.tw ter.search.core.earlyb rd. ndex.ut l;

 mport com.google.common.base.Precond  ons;

publ c abstract class SearchSortUt ls {
  publ c  nterface Comparator<T> {
    /**
     *  Compares t   em represented by t  g ven  ndex w h t  prov ded value.
     */
     nt compare( nt  ndex, T value);
  }

  /**
   * Performs a b nary search us ng t  g ven comparator, and returns t   ndex of t   em that
   * was found.  f foundLow  s true, t  greatest  em that's lo r than t  prov ded key
   *  s returned. Ot rw se, t  lo st  em that's greater than t  prov ded key  s returned.
   */
  publ c stat c <T>  nt b narySearch(Comparator<T> comparator, f nal  nt beg n, f nal  nt end,
      f nal T key, boolean f ndLow) {
     nt low = beg n;
     nt h gh = end;
    Precond  ons.c ckState(comparator.compare(low, key) <= comparator.compare(h gh, key));
    wh le (low <= h gh) {
       nt m d = (low + h gh) >>> 1;
       nt result = comparator.compare(m d, key);
       f (result < 0) {
        low = m d + 1;
      } else  f (result > 0) {
        h gh = m d - 1;
      } else {
        return m d;
      } // key found
    }

    assert low > h gh;
     f (f ndLow) {
      return h gh < beg n ? beg n : h gh;
    } else {
      return low > end ? end : low;
    }
  }
}
