package com.tw ter.search.earlyb rd. ndex;

 mport com.tw ter.search.core.earlyb rd. ndex.T  Mapper;
 mport com.tw ter.search.core.earlyb rd. ndex. nverted. ntBlockPool;
 mport com.tw ter.search.core.earlyb rd. ndex.ut l.SearchSortUt ls;
 mport com.tw ter.search.earlyb rd.search.quer es.S nceUnt lF lter;

publ c abstract class Abstract n moryT  Mapper  mple nts T  Mapper {
  // Reverse map: t  stamp to f rst doc  D seen w h that t  stamp.
  // T   s two arrays: t  t  stamps (sorted), and t  doc  ds.
  protected f nal  ntBlockPool reverseMapT  s;
  protected f nal  ntBlockPool reverseMap ds;
  protected volat le  nt reverseMapLast ndex;

  publ c Abstract n moryT  Mapper() {
    t .reverseMapT  s = new  ntBlockPool( LLEGAL_T ME, "t  _mapper_t  s");
    t .reverseMap ds = new  ntBlockPool( LLEGAL_T ME, "t  _mapper_ ds");
    t .reverseMapLast ndex = -1;
  }

  protected Abstract n moryT  Mapper( nt reverseMapLast ndex,
                                        ntBlockPool reverseMapT  s,
                                        ntBlockPool reverseMap ds) {
    t .reverseMapT  s = reverseMapT  s;
    t .reverseMap ds = reverseMap ds;
    t .reverseMapLast ndex = reverseMapLast ndex;
  }

  @Overr de
  publ c f nal  nt getLastT  () {
    return reverseMapLast ndex == -1 ?  LLEGAL_T ME : reverseMapT  s.get(reverseMapLast ndex);
  }

  @Overr de
  publ c f nal  nt getF rstT  () {
    return reverseMapLast ndex == -1 ?  LLEGAL_T ME : reverseMapT  s.get(0);
  }

  @Overr de
  publ c f nal  nt f ndF rstDoc d( nt t  Seconds,  nt smallestDoc D) {
     f (t  Seconds == S nceUnt lF lter.NO_F LTER || reverseMapLast ndex == -1) {
      return smallestDoc D;
    }

    f nal  nt  ndex = SearchSortUt ls.b narySearch(
        new  ntArrayComparator(), 0, reverseMapLast ndex, t  Seconds, false);

     f ( ndex == reverseMapLast ndex && reverseMapT  s.get( ndex) < t  Seconds) {
      // Spec al case for out of bounds t  .
      return smallestDoc D;
    }

    return reverseMap ds.get( ndex);
  }

  protected abstract vo d setT  ( nt doc D,  nt t  Seconds);

  protected vo d doAddMapp ng( nt doc D,  nt t  Seconds) {
    setT  (doc D, t  Seconds);
     nt lastT   = getLastT  ();
     f (t  Seconds > lastT  ) {
      // Found a t  stamp ne r than any t  stamp  've seen before.
      // Add a reverse mapp ng to t  t et (t  f rst seen w h t  t  stamp).
      //
      // W n  ndex ng out of order t ets,   could have gaps  n t  t  stamps recorded  n
      // reverseMapT  s. For example,  f   get 3 t ets w h t  stamp T0, T0 + 5, T0 + 3, t n  
      // w ll only record T0 and T0 + 5  n reverseMapT  s. Ho ver, t  should not be an  ssue,
      // because reverseMapT  s  s only used by f ndF rstDoc d(), and  's OK for that  thod to
      // return a smaller doc  D than str ctly necessary ( n t  case, f ndF rstDoc d(T0 + 3) w ll
      // return t  doc  D of t  second t et,  nstead of return ng t  doc  D of t  th rd t et).
      reverseMapT  s.add(t  Seconds);
      reverseMap ds.add(doc D);
      reverseMapLast ndex++;
    }
  }

  pr vate class  ntArrayComparator  mple nts SearchSortUt ls.Comparator< nteger> {
    @Overr de
    publ c  nt compare( nt  ndex,  nteger value) {
      return  nteger.compare(reverseMapT  s.get( ndex), value);
    }
  }
}
