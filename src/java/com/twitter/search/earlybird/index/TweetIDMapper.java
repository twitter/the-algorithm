package com.tw ter.search.earlyb rd. ndex;

 mport java. o. OExcept on;

 mport com.tw ter.search.common.ut l. o.flushable.Flushable;
 mport com.tw ter.search.core.earlyb rd. ndex.Doc DToT et DMapper;

publ c abstract class T et DMapper  mple nts Doc DToT et DMapper, Flushable {
  pr vate long m nT et D;
  pr vate long maxT et D;
  pr vate  nt m nDoc D;
  pr vate  nt maxDoc D;
  pr vate  nt numDocs;

  protected T et DMapper() {
    t (Long.MAX_VALUE, Long.M N_VALUE,  nteger.MAX_VALUE,  nteger.M N_VALUE, 0);
  }

  protected T et DMapper(
      long m nT et D, long maxT et D,  nt m nDoc D,  nt maxDoc D,  nt numDocs) {
    t .m nT et D = m nT et D;
    t .maxT et D = maxT et D;
    t .m nDoc D = m nDoc D;
    t .maxDoc D = maxDoc D;
    t .numDocs = numDocs;
  }

  // Realt   updates m nT et D and maxT et D  n addMapp ng.
  // Arch ves updates m nT et D and maxT et D  n prepareToRead.
  protected vo d setM nT et D(long m nT et D) {
    t .m nT et D = m nT et D;
  }

  protected vo d setMaxT et D(long maxT et D) {
    t .maxT et D = maxT et D;
  }

  protected vo d setM nDoc D( nt m nDoc D) {
    t .m nDoc D = m nDoc D;
  }

  protected vo d setMaxDoc D( nt maxDoc D) {
    t .maxDoc D = maxDoc D;
  }

  protected vo d setNumDocs( nt numDocs) {
    t .numDocs = numDocs;
  }

  publ c long getM nT et D() {
    return t .m nT et D;
  }

  publ c long getMaxT et D() {
    return t .maxT et D;
  }

  publ c  nt getM nDoc D() {
    return m nDoc D;
  }

  publ c  nt getMaxDoc D() {
    return maxDoc D;
  }

  @Overr de
  publ c  nt getNumDocs() {
    return numDocs;
  }

  /**
   * G ven a t et d, f nd t  correspond ng doc  D to start, or end, a search.
   *
   *  n t  ordered, dense doc  D mappers, t  returns e  r t  doc  D ass gned to t  t et  D,
   * or doc  D of t  next lo st t et  D,  f t  t et  s not  n t   ndex.  n t  case
   * f ndMaxDoc D  s  gnored.
   *
   *  n {@l nk OutOfOrderRealt  T et DMapper}, doc  Ds are not ordered w h n a m ll second, so  
   * want to search t  ent re m ll second bucket for a f lter. To accompl sh t ,
   *  f f ndMaxDoc d  s true   return t  largest poss ble doc  D for that m ll second.
   *  f f ndMaxDoc d  s false,   return t  smallest poss ble doc  D for that m ll second.
   *
   * T  returned doc  D w ll be bet en smallestDoc D and largestDoc D ( nclus ve).
   * T  returned doc  D may not be  n t   ndex.
   */
  publ c  nt f ndDoc dBound(long t et D,
                            boolean f ndMaxDoc D,
                             nt smallestDoc D,
                             nt largestDoc D) throws  OExcept on {
     f (t et D > maxT et D) {
      return smallestDoc D;
    }
     f (t et D < m nT et D) {
      return largestDoc D;
    }

     nt  nternal D = f ndDoc DBound nternal(t et D, f ndMaxDoc D);

    return Math.max(smallestDoc D, Math.m n(largestDoc D,  nternal D));
  }

  @Overr de
  publ c f nal  nt getNextDoc D( nt doc D) {
     f (numDocs <= 0) {
      return  D_NOT_FOUND;
    }
     f (doc D < m nDoc D) {
      return m nDoc D;
    }
     f (doc D >= maxDoc D) {
      return  D_NOT_FOUND;
    }
    return getNextDoc D nternal(doc D);
  }

  @Overr de
  publ c f nal  nt getPrev ousDoc D( nt doc D) {
     f (numDocs <= 0) {
      return  D_NOT_FOUND;
    }
     f (doc D <= m nDoc D) {
      return  D_NOT_FOUND;
    }
     f (doc D > maxDoc D) {
      return maxDoc D;
    }
    return getPrev ousDoc D nternal(doc D);
  }

  @Overr de
  publ c  nt addMapp ng(f nal long t et D) {
     nt doc d = addMapp ng nternal(t et D);
     f (doc d !=  D_NOT_FOUND) {
      ++numDocs;
       f (t et D > maxT et D) {
        maxT et D = t et D;
      }
       f (t et D < m nT et D) {
        m nT et D = t et D;
      }
       f (doc d > maxDoc D) {
        maxDoc D = doc d;
      }
       f (doc d < m nDoc D) {
        m nDoc D = doc d;
      }
    }

    return doc d;
  }

  /**
   * Returns t  smallest val d doc  D  n t  mapper that's str ctly h g r than t  g ven doc  D.
   *  f no such doc  D ex sts,  D_NOT_FOUND must be returned.
   *
   * T  g ven doc D  s guaranteed to be  n t  range [m nDoc D, maxDoc D).
   *
   * @param doc D T  current doc  D.
   * @return T  smallest val d doc  D  n t  mapper that's str ctly h g r than t  g ven doc  D,
   *         or a negat ve number,  f no such doc  D ex sts.
   */
  protected abstract  nt getNextDoc D nternal( nt doc D);

  /**
   * Returns t  smallest val d doc  D  n t  mapper that's str ctly h g r than t  g ven doc  D.
   *  f no such doc  D ex sts,  D_NOT_FOUND must be returned.
   *
   * T  g ven doc D  s guaranteed to be  n t  range (m nDoc D, maxDoc D].
   *
   * @param doc D T  current doc  D.
   * @return T  smallest val d doc  D  n t  mapper that's str ctly h g r than t  g ven doc  D,
   *         or a negat ve number,  f no such doc  D ex sts.
   */
  protected abstract  nt getPrev ousDoc D nternal( nt doc D);

  protected abstract  nt addMapp ng nternal(f nal long t et D);

  /**
   * See {@l nk T et DMapper#f ndDoc dBound}.
   */
  protected abstract  nt f ndDoc DBound nternal(long t et D,
                                                boolean f ndMaxDoc D) throws  OExcept on;
}
