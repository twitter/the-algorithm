package com.tw ter.search.earlyb rd.search.facets;

 mport java.ut l.Arrays;
 mport java.ut l.Comparator;
 mport java.ut l.Pr or yQueue;

 mport com.tw ter.search.common.rank ng.thr ftjava.Thr ftFacetEarlyb rdSort ngMode;
 mport com.tw ter.search.core.earlyb rd.facets.FacetAccumulator;
 mport com.tw ter.search.core.earlyb rd.facets.FacetLabelProv der;
 mport com.tw ter.search.core.earlyb rd.facets.FacetLabelProv der.FacetLabelAccessor;
 mport com.tw ter.search.core.earlyb rd.facets.Language togram;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftFacetCount;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftFacetCount tadata;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftFacetF eldResults;

publ c class Hash ngAndPrun ngFacetAccumulator extends FacetAccumulator {
  pr vate stat c f nal  nt DEFAULT_HASH_S ZE = 4096;
  /**
   * 4 longs per entry accommodates long term Ds.
   * Although entr es could be encoded  n 3 bytes, 4 ensures that no entry  s spl 
   * across cac  l nes.
   */
  protected stat c f nal  nt LONGS_PER_ENTRY = 4;
  pr vate stat c f nal double LOAD_FACTOR = 0.5;
  pr vate stat c f nal long B TSH FT_MAX_TWEEPCRED = 32;
  pr vate stat c f nal long PENALTY_COUNT_MASK = (1L << B TSH FT_MAX_TWEEPCRED) - 1;

  protected stat c f nal long UNASS GNED = -1;

  protected Language togram language togram = new Language togram();

  protected stat c f nal class HashTable {
    protected f nal long[] hash;
    protected f nal  nt s ze;
    protected f nal  nt maxLoad;
    protected f nal  nt mask;

    publ c HashTable( nt s ze) {
      hash = new long[LONGS_PER_ENTRY * s ze];
      Arrays.f ll(hash, UNASS GNED);
      t .s ze = s ze;
      // Ensure al gn nt to LONGS_PER_ENTRY-byte boundar es
      t .mask = LONGS_PER_ENTRY * (s ze - 1);
      t .maxLoad = ( nt) (s ze * LOAD_FACTOR);
    }

    protected vo d reset() {
      Arrays.f ll(hash, UNASS GNED);
    }

    pr vate f nal Cursor cursor = new Cursor();

    publ c  nt f ndHashPos  on(long term D) {
       nt code = (new Long(term D)).hashCode();
       nt hashPos = code & mask;

       f (cursor.readFromHash(hashPos) && (cursor.term D != term D)) {
        f nal  nt  nc = ((code >> 8) + code) | 1;
        do {
          code +=  nc;
          hashPos = code & t .mask;
        } wh le (cursor.readFromHash(hashPos) && (cursor.term D != term D));
      }

      return hashPos;
    }

    /**
     * T  cursor can be used to access t  d fferent f elds of a hash entry.
     * Callers should always pos  on t  cursor w h readFromHash() before
     * access ng t   mbers.
     */
    pr vate f nal class Cursor {
      pr vate  nt s mpleCount;
      pr vate  nt   ghtedCount;
      pr vate  nt penaltyCount;
      pr vate  nt maxT epcred;
      pr vate long term D;

      publ c vo d wr eToHash( nt pos  on) {
        long payload = (((long) maxT epcred) << B TSH FT_MAX_TWEEPCRED)
                       | ((long) penaltyCount);

        assert  emPenaltyCount(payload) == penaltyCount : payload + ", "
                      +  emPenaltyCount(payload) + " != " + penaltyCount;
        assert  emMaxT epCred(payload) == maxT epcred;

        hash[pos  on] = term D;
        hash[pos  on + 1] = s mpleCount;
        hash[pos  on + 2] =   ghtedCount;
        hash[pos  on + 3] = payload;
      }

      /** Returns t   em  D, or UNASS GNED */
      publ c boolean readFromHash( nt pos  on) {
        long entry = hash[pos  on];
         f (entry == UNASS GNED) {
          term D = UNASS GNED;
          return false;
        }

        term D = entry;

        s mpleCount = ( nt) hash[pos  on + 1];
          ghtedCount = ( nt) hash[pos  on + 2];
        long payload = hash[pos  on + 3];

        penaltyCount =  emPenaltyCount(payload);
        maxT epcred =  emMaxT epCred(payload);

        return true;
      }
    }
  }

  protected stat c  nt  emPenaltyCount(long payload) {
    return ( nt) (payload & PENALTY_COUNT_MASK);
  }

  protected stat c  nt  emMaxT epCred(long payload) {
    return ( nt) (payload >>> B TSH FT_MAX_TWEEPCRED);
  }

  protected  nt num ems;
  protected f nal HashTable hashTable;
  protected f nal long[] sortBuffer;
  pr vate FacetLabelProv der facetLabelProv der;

  pr vate  nt totalS mpleCount;
  pr vate  nt total  ghtedCount;
  pr vate  nt totalPenalty;

  stat c f nal double DEFAULT_QUERY_ NDEPENDENT_PENALTY_WE GHT = 1.0;
  pr vate f nal double query ndependentPenalty  ght;

  pr vate f nal FacetComparator facetComparator;

  publ c Hash ngAndPrun ngFacetAccumulator(FacetLabelProv der facetLabelProv der,
          FacetComparator comparator) {
    t (DEFAULT_HASH_S ZE, facetLabelProv der,
            DEFAULT_QUERY_ NDEPENDENT_PENALTY_WE GHT, comparator);
  }

  publ c Hash ngAndPrun ngFacetAccumulator(FacetLabelProv der facetLabelProv der,
          double query ndependentPenalty  ght, FacetComparator comparator) {
    t (DEFAULT_HASH_S ZE, facetLabelProv der, query ndependentPenalty  ght, comparator);
  }

  /**
   * Creates a new, empty Hash ngAndPrun ngFacetAccumulator w h t  g ven  n  al s ze.
   * HashS ze w ll be rounded up to t  next po r-of-2 value.
   */
  publ c Hash ngAndPrun ngFacetAccumulator( nt hashS ze, FacetLabelProv der facetLabelProv der,
          double query ndependentPenalty  ght, FacetComparator comparator) {
     nt po rOfTwoS ze = 2;
    wh le (hashS ze > po rOfTwoS ze) {
      po rOfTwoS ze *= 2;
    }

    t .facetComparator  = comparator;
    hashTable = new HashTable(po rOfTwoS ze);
    sortBuffer = new long[LONGS_PER_ENTRY * ( nt) Math.ce l(LOAD_FACTOR * po rOfTwoS ze)];
    t .facetLabelProv der = facetLabelProv der;
    t .query ndependentPenalty  ght = query ndependentPenalty  ght;
  }

  @Overr de
  publ c vo d reset(FacetLabelProv der facetLabelProv derToReset) {
    t .facetLabelProv der = facetLabelProv derToReset;
    t .num ems = 0;
    t .hashTable.reset();
    t .totalS mpleCount = 0;
    t .totalPenalty = 0;
    t .total  ghtedCount = 0;
    language togram.clear();
  }


  @Overr de
  publ c  nt add(long term D,  nt   ghtedCounter ncre nt,  nt penalty ncre nt,  nt t epCred) {
     nt hashPos = hashTable.f ndHashPos  on(term D);

    totalPenalty += penalty ncre nt;
    totalS mpleCount++;
    total  ghtedCount +=   ghtedCounter ncre nt;

     f (hashTable.cursor.term D == UNASS GNED) {
      hashTable.cursor.term D = term D;
      hashTable.cursor.s mpleCount = 1;
      hashTable.cursor.  ghtedCount =   ghtedCounter ncre nt;
      hashTable.cursor.penaltyCount = penalty ncre nt;
      hashTable.cursor.maxT epcred = t epCred;
      hashTable.cursor.wr eToHash(hashPos);

      num ems++;
       f (num ems >= hashTable.maxLoad) {
        prune();
      }
      return 1;
    } else {

      hashTable.cursor.s mpleCount++;
      hashTable.cursor.  ghtedCount +=   ghtedCounter ncre nt;

       f (t epCred > hashTable.cursor.maxT epcred) {
        hashTable.cursor.maxT epcred = t epCred;
      }

      hashTable.cursor.penaltyCount += penalty ncre nt;
      hashTable.cursor.wr eToHash(hashPos);
      return hashTable.cursor.s mpleCount;
    }
  }

  @Overr de
  publ c vo d recordLanguage( nt language d) {
    language togram. ncre nt(language d);
  }

  @Overr de
  publ c Language togram getLanguage togram() {
    return language togram;
  }

  pr vate vo d prune() {
    copyToSortBuffer();
    hashTable.reset();

     nt targetNum ems = ( nt) (hashTable.maxLoad >> 1);

     nt m nCount = 2;
     nt nextM nCount =  nteger.MAX_VALUE;

    f nal  nt n = LONGS_PER_ENTRY * num ems;

    wh le (num ems > targetNum ems) {
      for ( nt   = 0;   < n;   += LONGS_PER_ENTRY) {
        long  em = sortBuffer[ ];
         f ( em != UNASS GNED) {
           nt count = ( nt) sortBuffer[  + 1];
           f (count < m nCount) {
            ev ct( );
          } else  f (count < nextM nCount) {
            nextM nCount = count;
          }
        }
      }
       f (m nCount == nextM nCount) {
        m nCount++;
      } else {
        m nCount = nextM nCount;
      }
      nextM nCount =  nteger.MAX_VALUE;
    }

    // rehash
    for ( nt   = 0;   < n;   += LONGS_PER_ENTRY) {
      long  em = sortBuffer[ ];
       f ( em != UNASS GNED) {
        f nal long term D =  em;
         nt hashPos = hashTable.f ndHashPos  on(term D);
        for ( nt j = 0; j < LONGS_PER_ENTRY; ++j) {
          hashTable.hash[hashPos + j] = sortBuffer[  + j];
        }
      }
    }
  }

  // overr dable for un  test
  protected vo d ev ct( nt  ndex) {
    sortBuffer[ ndex] = UNASS GNED;
    num ems--;
  }

  @Overr de
  publ c Thr ftFacetF eldResults getAllFacets() {
    return getTopFacets(num ems);
  }

  @Overr de
  publ c Thr ftFacetF eldResults getTopFacets(f nal  nt numRequested) {
     nt n = numRequested > num ems ? num ems : numRequested;

     f (n == 0) {
      return null;
    }

    Thr ftFacetF eldResults facetResults = new Thr ftFacetF eldResults();
    facetResults.setTotalCount(totalS mpleCount);
    facetResults.setTotalScore(total  ghtedCount);
    facetResults.setTotalPenalty(totalPenalty);

    copyToSortBuffer();

    // sort table us ng t  facet comparator
    Pr or yQueue< em> pq = new Pr or yQueue<>(num ems, facetComparator.getComparator(true));

    for ( nt   = 0;   < LONGS_PER_ENTRY * num ems;   += LONGS_PER_ENTRY) {
      pq.add(new  em(sortBuffer,  ));
    }

    FacetLabelAccessor accessor = facetLabelProv der.getLabelAccessor();

    for ( nt   = 0;   < n;  ++) {
       em  em = pq.poll();
      long  d =  em.getTerm d();

       nt penalty =  em.getPenaltyCount() + ( nt) (query ndependentPenalty  ght
              * accessor.getOffens veCount( d));
      Thr ftFacetCount result = new Thr ftFacetCount().setFacetLabel(accessor.getTermText( d));
      result.setPenaltyCount(penalty);
      result.setS mpleCount( em.getS mpleCount());
      result.set  ghtedCount( em.get  ghtedCount());
      result.set tadata(new Thr ftFacetCount tadata().setMaxT epCred( em.getMaxT etCred()));

      result.setFacetCount(result.get  ghtedCount());
      facetResults.addToTopFacets(result);
    }

    return facetResults;
  }

  // Compacts t  hashtable entr es  n place by remov ng empty has s.  After
  // t  operat on  's no longer a hash table but a array of entr es.
  pr vate vo d copyToSortBuffer() {
     nt upto = 0;

    for ( nt   = 0;   < hashTable.hash.length;   += LONGS_PER_ENTRY) {
       f (hashTable.hash[ ] != UNASS GNED) {
        for ( nt j = 0; j < LONGS_PER_ENTRY; ++j) {
          sortBuffer[upto + j] = hashTable.hash[  + j];
        }
        upto += LONGS_PER_ENTRY;
      }
    }
    assert upto == num ems * LONGS_PER_ENTRY;
  }

  /**
   * Sorts facets  n t  follow ng order:
   * 1) ascend ng by   ghtedCount
   * 2)  f   ghtedCount equal: ascend ng by s mpleCount
   * 3)  f   ghtedCount and s mpleCount equal: descend ng by penaltyCount
   */
  publ c stat c  nt compareFacetCounts( nt   ghtedCount1,  nt s mpleCount1,  nt penaltyCount1,
                                        nt   ghtedCount2,  nt s mpleCount2,  nt penaltyCount2,
                                       boolean s mpleCountPrecedence) {
     f (s mpleCountPrecedence) {
       f (s mpleCount1 < s mpleCount2) {
        return -1;
      } else  f (s mpleCount1 > s mpleCount2) {
        return 1;
      } else {
         f (  ghtedCount1 <   ghtedCount2) {
          return -1;
        } else  f (  ghtedCount1 >   ghtedCount2) {
          return 1;
        } else {
           f (penaltyCount1 < penaltyCount2) {
            // descend ng
            return 1;
          } else  f (penaltyCount1 > penaltyCount2) {
            return -1;
          } else {
            return 0;
          }
        }
      }
    } else {
       f (  ghtedCount1 <   ghtedCount2) {
        return -1;
      } else  f (  ghtedCount1 >   ghtedCount2) {
        return 1;
      } else {
         f (s mpleCount1 < s mpleCount2) {
          return -1;
        } else  f (s mpleCount1 > s mpleCount2) {
          return 1;
        } else {
           f (penaltyCount1 < penaltyCount2) {
            // descend ng
            return 1;
          } else  f (penaltyCount1 > penaltyCount2) {
            return -1;
          } else {
            return 0;
          }
        }
      }
    }
  }

  publ c stat c f nal class FacetComparator {
    pr vate f nal Comparator<Thr ftFacetCount> thr ftComparator;
    pr vate f nal Comparator< em> comparator;

    pr vate FacetComparator(Comparator<Thr ftFacetCount> thr ftComparator,
                            Comparator< em> comparator) {
      t .thr ftComparator = thr ftComparator;
      t .comparator = comparator;
    }

    publ c Comparator<Thr ftFacetCount> getThr ftComparator() {
      return getThr ftComparator(false);
    }

    publ c Comparator<Thr ftFacetCount> getThr ftComparator(boolean reverse) {
      return reverse ? getReverseComparator(thr ftComparator) : thr ftComparator;
    }

    pr vate Comparator< em> getComparator(boolean reverse) {
      return reverse ? getReverseComparator(comparator) : comparator;
    }
  }

  publ c stat c f nal FacetComparator S MPLE_COUNT_COMPARATOR = new FacetComparator(
      (facet1, facet2) -> compareFacetCounts(
          facet1.  ghtedCount, facet1.s mpleCount, facet1.penaltyCount,
          facet2.  ghtedCount, facet2.s mpleCount, facet2.penaltyCount,
          true),
      (facet1, facet2) -> compareFacetCounts(
          facet1.get  ghtedCount(), facet1.getS mpleCount(), facet1.getPenaltyCount(),
          facet2.get  ghtedCount(), facet2.getS mpleCount(), facet2.getPenaltyCount(),
          true));


  publ c stat c f nal FacetComparator WE GHTED_COUNT_COMPARATOR = new FacetComparator(
      (facet1, facet2) -> compareFacetCounts(
          facet1.  ghtedCount, facet1.s mpleCount, facet1.penaltyCount,
          facet2.  ghtedCount, facet2.s mpleCount, facet2.penaltyCount,
          false),
      (facet1, facet2) -> compareFacetCounts(
          facet1.get  ghtedCount(), facet1.getS mpleCount(), facet1.getPenaltyCount(),
          facet2.get  ghtedCount(), facet2.getS mpleCount(), facet2.getPenaltyCount(),
          false));

  /**
   * Returns t  appropr ate FacetComparator for t  spec f ed sort ngMode.
   */
  publ c stat c FacetComparator getComparator(Thr ftFacetEarlyb rdSort ngMode sort ngMode) {
    sw ch (sort ngMode) {
      case SORT_BY_WE GHTED_COUNT:
        return WE GHTED_COUNT_COMPARATOR;
      case SORT_BY_S MPLE_COUNT:
      default:
        return S MPLE_COUNT_COMPARATOR;
    }
  }

  pr vate stat c <T> Comparator<T> getReverseComparator(f nal Comparator<T> comparator) {
    return (t1, t2) -> -comparator.compare(t1, t2);
  }

  stat c f nal class  em {
    pr vate f nal long[] data;
    pr vate f nal  nt offset;

     em(long[] data,  nt offset) {
      t .data = data;
      t .offset = offset;
    }

    publ c long getTerm d() {
      return data[offset];
    }

    publ c  nt getS mpleCount() {
      return ( nt) data[offset + 1];
    }

    publ c  nt get  ghtedCount() {
      return ( nt) data[offset + 2];
    }

    publ c  nt getPenaltyCount() {
      return  emPenaltyCount(data[offset + 3]);
    }

    publ c  nt getMaxT etCred() {
      return  emMaxT epCred(data[offset + 3]);
    }

    @Overr de publ c  nt hashCode() {
      return ( nt) (31 * getTerm d());
    }

    @Overr de publ c boolean equals(Object o) {
      return getTerm d() == (( em) o).getTerm d();
    }

  }
}
