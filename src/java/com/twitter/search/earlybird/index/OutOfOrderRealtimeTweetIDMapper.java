package com.tw ter.search.earlyb rd. ndex;

 mport java. o. OExcept on;
 mport java.ut l.Arrays;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common. tr cs.SearchRateCounter;
 mport com.tw ter.search.common.part  on ng.snowflakeparser.Snowflake dParser;
 mport com.tw ter.search.common.ut l. o.flushable.DataDeser al zer;
 mport com.tw ter.search.common.ut l. o.flushable.DataSer al zer;
 mport com.tw ter.search.common.ut l. o.flushable.Flush nfo;
 mport com.tw ter.search.common.ut l. o.flushable.Flushable;
 mport com.tw ter.search.core.earlyb rd. ndex.Doc DToT et DMapper;

 mport  .un m .ds .fastut l. nts. nt2ByteOpenHashMap;
 mport  .un m .ds .fastut l. nts. nt2LongMap;
 mport  .un m .ds .fastut l. nts. nt2LongOpenHashMap;

/**
 * A mapper that maps t et  Ds to doc  Ds based on t  t et t  stamps. T  mapper guarantees
 * that  f creat onT  (A) > creat onT  (B), t n doc d(A) < doc d(B), no matter  n wh ch order
 * t  t ets are added to t  mapper. Ho ver,  f creat onT  (A) == creat onT  (B), t n t re
 *  s no guarantee on t  order bet en doc d(A) and doc d(B).
 *
 * Essent ally, t  mapper guarantees that t ets w h a later creat on t   are mapped to smaller
 * doc  Ds, but   does not prov de any order ng for t ets w h t  sa  t  stamp (down to
 * m ll second granular y, wh ch  s what Snowflake prov des).   cla m  s that order ng t ets
 * w h t  sa  t  stamp  s not needed, because for t  purposes of realt   search, t  only
 * s gn f cant part of t  t et  D  s t  t  stamp. So any such order ng would just be an order ng
 * for t  Snowflake shards and/or sequence numbers, rat r than a t   based order ng for t ets.
 *
 * T  mapper uses t  follow ng sc   to ass gn doc Ds to t ets:
 *   +----------+-----------------------------+------------------------------+
 *   | B  0    | B s 1 - 27                 | B s 28 - 31                 |
 *   + ---------+-----------------------------+------------------------------+
 *   | s gn     | t et  D t  stamp -        | Allow 16 t ets to be posted |
 *   | always 0 | seg nt boundary t  stamp  | on t  sa  m ll second      |
 *   + ---------+-----------------------------+------------------------------+
 *
 *  mportant assumpt ons:
 *   * Snowflake  Ds have m ll second granular y. T refore, 27 b s  s enough to represent a t  
 *     per od of 2^27 / (3600 * 100) = ~37 h s, wh ch  s more than enough to cover one realt  
 *     seg nt (  realt   seg nts currently span ~13 h s).
 *   * At peak t  s, t  t et post ng rate  s less than 10,000 tps. G ven   current part  on ng
 *     sc   (22 part  ons), each realt   earlyb rd should expect to get less than 500 t ets per
 *     second, wh ch co s down to less than 1 t et per m ll second, assum ng t  part  on ng hash
 *     funct on d str butes t  t ets fa rly randomly  ndependent of t  r t  stamps. T refore,
 *     prov d ng space for 16 t ets (4 b s)  n every m ll second should be more than enough to
 *     accommodate t  current requ re nts, and any potent al future changes (h g r t et rate,
 *     fe r part  ons, etc.).
 *
 * How t  mapper works:
 *   * T  t et d -> doc d convers on  s  mpl c  (us ng t  t et's t  stamp).
 *   *   use a  ntToByteMap to store t  number of t ets for each t  stamp, so that   can
 *     allocate d fferent doc  Ds to t ets posted on t  sa  m ll second. T  s ze of t  map  s:
 *         seg ntS ze * 2 (load factor) * 1 (s ze of byte) = 16MB
 *   * T  doc d -> t et d mapp ngs are stored  n an  ntToLongMap. T  s ze of t  map  s:
 *         seg ntS ze * 2 (load factor) * 8 (s ze of long) = 128MB
 *   * T  mapper takes t  "seg nt boundary" (t  t  stamp of t  t  sl ce  D) as a para ter.
 *     T  seg nt boundary determ nes t  earl est t et that t  mapper can correctly  ndex
 *     (   s subtracted from t  t  stamp of all t ets added to t  mapper). T refore,  n order
 *     to correctly handle late t ets,   move back t  seg nt boundary by t lve h .
 *   * T ets created before (seg nt boundary - 12 h s) are stored as  f t  r t  stamp was t 
 *     seg nt boundary.
 *   * T  largest t  stamp that t  mapper can store  s:
 *         LARGEST_RELAT VE_T MESTAMP = (1 << T MESTAMP_B TS) - LUCENE_T MESTAMP_BUFFER.
 *     T ets created after (seg ntBoundaryT  stamp + LARGEST_RELAT VE_T MESTAMP) are stored as  f
 *     t  r t  stamp was (seg ntBoundaryT  stamp + LARGEST_RELAT VE_T MESTAMP).
 *   * W n a t et  s added,   compute  s doc  D as:
 *          nt relat veT  stamp = t etT  stamp - seg ntBoundaryT  stamp;
 *          nt doc dT  stamp = LARGEST_RELAT VE_T MESTAMP - relat veT  stamp;
 *          nt numT etsForT  stamp = t etsPerT  stamp.get(doc dT  stamp);
 *          nt doc d = (doc dT  stamp << DOC_ D_B TS)
 *             + MAX_DOCS_PER_T MESTAMP - numT etsForT  stamp - 1
 *
 * T  doc  D d str but on sc   guarantees that t ets created later w ll be ass gned smaller doc
 *  Ds (as long as   don't have more than 16 t ets created  n t  sa  m ll second). Ho ver,
 * t re  s no order ng guarantee for t ets created at t  sa  t  stamp -- t y are ass gned doc
 *  Ds  n t  order  n wh ch t y're added to t  mapper.
 *
 *  f   have more than 16 t ets created at t   T, t  mapper w ll st ll gracefully handle that
 * case: t  "extra" t ets w ll be ass gned doc  Ds from t  pool of doc  Ds for t  stamp (T + 1).
 * Ho ver, t  order ng guarantee m ght no longer hold for those "extra" t ets. Also, t  "extra"
 * t ets m ght be m ssed by certa n s nce_ d/max_ d quer es (t  f ndDoc dBound()  thod m ght not
 * be able to correctly work for t se t et  Ds).
 */
publ c class OutOfOrderRealt  T et DMapper extends T et DMapper {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(OutOfOrderRealt  T et DMapper.class);

  // T  number of b s used to represent t  t et t  stamp.
  pr vate stat c f nal  nt T MESTAMP_B TS = 27;

  // T  number of b s used to represent t  number of t ets w h a certa n t  stamp.
  @V s bleForTest ng
  stat c f nal  nt DOC_ D_B TS =  nteger.S ZE - T MESTAMP_B TS - 1;

  // T  max mum number of t ets/docs that   can store per t  stamp.
  @V s bleForTest ng
  stat c f nal  nt MAX_DOCS_PER_T MESTAMP = 1 << DOC_ D_B TS;

  // Lucene has so  log c that doesn't deal  ll w h doc  Ds close to  nteger.MAX_VALUE.
  // For example, BooleanScorer has a S ZE constant set to 2048, wh ch gets added to t  doc  Ds
  //  ns de t  score()  thod. So w n t  doc  Ds are close to  nteger.MAX_VALUE, t  causes an
  // overflow, wh ch can send Lucene  nto an  nf n e loop. T refore,   need to make sure that
  //   do not ass gn doc  Ds close to  nteger.MAX_VALUE.
  pr vate stat c f nal  nt LUCENE_T MESTAMP_BUFFER = 1 << 16;

  @V s bleForTest ng
  publ c stat c f nal  nt LATE_TWEETS_T ME_BUFFER_M LL S = 12 * 3600 * 1000;  // 12 h s

  // T  largest relat ve t  stamp that t  mapper can store.
  @V s bleForTest ng
  stat c f nal  nt LARGEST_RELAT VE_T MESTAMP = (1 << T MESTAMP_B TS) - LUCENE_T MESTAMP_BUFFER;

  pr vate f nal long seg ntBoundaryT  stamp;
  pr vate f nal  nt seg ntS ze;

  pr vate f nal  nt2LongOpenHashMap t et ds;
  pr vate f nal  nt2ByteOpenHashMap t etsPerT  stamp;

  pr vate stat c f nal SearchRateCounter BAD_BUCKET_RATE =
      SearchRateCounter.export("t ets_ass gned_to_bad_t  stamp_bucket");
  pr vate stat c f nal SearchRateCounter TWEETS_NOT_ASS GNED_RATE =
      SearchRateCounter.export("t ets_not_ass gned");
  pr vate stat c f nal SearchRateCounter OLD_TWEETS_DROPPED =
      SearchRateCounter.export("old_t ets_dropped");

  publ c OutOfOrderRealt  T et DMapper( nt seg ntS ze, long t  sl ce D) {
    long f rstT  stamp = Snowflake dParser.getT  stampFromT et d(t  sl ce D);
    // Leave a buffer so that   can handle t ets that are up to t lve h s late.
    t .seg ntBoundaryT  stamp = f rstT  stamp - LATE_TWEETS_T ME_BUFFER_M LL S;
    t .seg ntS ze = seg ntS ze;

    t et ds = new  nt2LongOpenHashMap(seg ntS ze);
    t et ds.defaultReturnValue( D_NOT_FOUND);

    t etsPerT  stamp = new  nt2ByteOpenHashMap(seg ntS ze);
    t etsPerT  stamp.defaultReturnValue((byte)  D_NOT_FOUND);
  }

  @V s bleForTest ng
   nt getDoc dT  stamp(long t et d) {
    long t etT  stamp = Snowflake dParser.getT  stampFromT et d(t et d);
     f (t etT  stamp < seg ntBoundaryT  stamp) {
      return  D_NOT_FOUND;
    }

    long relat veT  stamp = t etT  stamp - seg ntBoundaryT  stamp;
     f (relat veT  stamp > LARGEST_RELAT VE_T MESTAMP) {
      relat veT  stamp = LARGEST_RELAT VE_T MESTAMP;
    }

    return LARGEST_RELAT VE_T MESTAMP - ( nt) relat veT  stamp;
  }

  pr vate  nt getDoc dForT  stamp( nt doc dT  stamp, byte doc ndex nT  stamp) {
    return (doc dT  stamp << DOC_ D_B TS) + MAX_DOCS_PER_T MESTAMP - doc ndex nT  stamp;
  }

  @V s bleForTest ng
  long[] getT etsForDoc dT  stamp( nt doc dT  stamp) {
    byte numDocsForT  stamp = t etsPerT  stamp.get(doc dT  stamp);
     f (numDocsForT  stamp ==  D_NOT_FOUND) {
      // T  should never happen  n prod, but better to be safe.
      return new long[0];
    }

    long[] t et ds nBucket = new long[numDocsForT  stamp];
     nt start ngDoc d = (doc dT  stamp << DOC_ D_B TS) + MAX_DOCS_PER_T MESTAMP - 1;
    for ( nt   = 0;   < numDocsForT  stamp; ++ ) {
      t et ds nBucket[ ] = t et ds.get(start ngDoc d -  );
    }
    return t et ds nBucket;
  }

  pr vate  nt newDoc d(long t et d) {
     nt expectedDoc dT  stamp = getDoc dT  stamp(t et d);
     f (expectedDoc dT  stamp ==  D_NOT_FOUND) {
      LOG. nfo("Dropp ng t et {} because    s from before t  seg nt boundary t  stamp {}",
          t et d,
          seg ntBoundaryT  stamp);
      OLD_TWEETS_DROPPED. ncre nt();
      return  D_NOT_FOUND;
    }

     nt doc dT  stamp = expectedDoc dT  stamp;
    byte numDocsForT  stamp = t etsPerT  stamp.get(doc dT  stamp);

     f (numDocsForT  stamp == MAX_DOCS_PER_T MESTAMP) {
      BAD_BUCKET_RATE. ncre nt();
    }

    wh le ((doc dT  stamp > 0) && (numDocsForT  stamp == MAX_DOCS_PER_T MESTAMP)) {
      --doc dT  stamp;
      numDocsForT  stamp = t etsPerT  stamp.get(doc dT  stamp);
    }

     f (numDocsForT  stamp == MAX_DOCS_PER_T MESTAMP) {
      // T  relat ve t  stamp 0 already has MAX_DOCS_PER_T MESTAMP. Can't add more docs.
      LOG.error("T et {} could not be ass gned a doc  D  n any bucket, because t  bucket for "
          + "t  stamp 0  s already full: {}",
          t et d, Arrays.toStr ng(getT etsForDoc dT  stamp(0)));
      TWEETS_NOT_ASS GNED_RATE. ncre nt();
      return  D_NOT_FOUND;
    }

     f (doc dT  stamp != expectedDoc dT  stamp) {
      LOG.warn("T et {} could not be ass gned a doc  D  n t  bucket for  s t  stamp {}, "
               + "because t  bucket  s full.  nstead,   was ass gned a doc  D  n t  bucket for "
               + "t  stamp {}. T  t ets  n t  correct bucket are: {}",
               t et d,
               expectedDoc dT  stamp,
               doc dT  stamp,
               Arrays.toStr ng(getT etsForDoc dT  stamp(expectedDoc dT  stamp)));
    }

     f (numDocsForT  stamp ==  D_NOT_FOUND) {
      numDocsForT  stamp = 0;
    }
    ++numDocsForT  stamp;
    t etsPerT  stamp.put(doc dT  stamp, numDocsForT  stamp);

    return getDoc dForT  stamp(doc dT  stamp, numDocsForT  stamp);
  }

  @Overr de
  publ c  nt getDoc D(long t et d) {
     nt doc dT  stamp = getDoc dT  stamp(t et d);
    wh le (doc dT  stamp >= 0) {
       nt numDocsForT  stamp = t etsPerT  stamp.get(doc dT  stamp);
       nt start ngDoc d = (doc dT  stamp << DOC_ D_B TS) + MAX_DOCS_PER_T MESTAMP - 1;
      for ( nt doc d = start ngDoc d; doc d > start ngDoc d - numDocsForT  stamp; --doc d) {
         f (t et ds.get(doc d) == t et d) {
          return doc d;
        }
      }

      //  f   have MAX_DOCS_PER_T MESTAMP docs w h t  t  stamp, t n   m ght've m s-ass gned
      // a t et to t  prev ous doc dT  stamp bucket.  n that case,   need to keep search ng.
      // Ot rw se, t  t et  s not  n t   ndex.
       f (numDocsForT  stamp < MAX_DOCS_PER_T MESTAMP) {
        break;
      }

      --doc dT  stamp;
    }

    return  D_NOT_FOUND;
  }

  @Overr de
  protected  nt getNextDoc D nternal( nt doc d) {
    // C ck  f doc d + 1  s an ass gned doc  D  n t  mapper. T  m ght be t  case w n   have
    // mult ple t ets posted on t  sa  m ll second.
     f (t et ds.get(doc d + 1) !=  D_NOT_FOUND) {
      return doc d + 1;
    }

    //  f (doc d + 1)  s not ass gned, t n    ans   do not have any more t ets posted at t 
    // t  stamp correspond ng to doc d.   need to f nd t  next relat ve t  stamp for wh ch t 
    // mapper has t ets, and return t  f rst t et for that t  stamp. Note that  erat ng over
    // t  space of all poss ble t  stamps  s faster than  erat ng over t  space of all poss ble
    // doc  Ds ( 's MAX_DOCS_PER_T MESTAMP t  s faster).
     nt nextDoc dT  stamp = (doc d >> DOC_ D_B TS) + 1;
    byte numDocsForT  stamp = t etsPerT  stamp.get(nextDoc dT  stamp);
     nt maxDoc dT  stamp = getMaxDoc D() >> DOC_ D_B TS;
    wh le ((nextDoc dT  stamp <= maxDoc dT  stamp)
           && (numDocsForT  stamp ==  D_NOT_FOUND)) {
      ++nextDoc dT  stamp;
      numDocsForT  stamp = t etsPerT  stamp.get(nextDoc dT  stamp);
    }

     f (numDocsForT  stamp !=  D_NOT_FOUND) {
      return getDoc dForT  stamp(nextDoc dT  stamp, numDocsForT  stamp);
    }

    return  D_NOT_FOUND;
  }

  @Overr de
  protected  nt getPrev ousDoc D nternal( nt doc d) {
    // C ck  f doc d - 1  s an ass gned doc  D  n t  mapper. T  m ght be t  case w n   have
    // mult ple t ets posted on t  sa  m ll second.
     f (t et ds.get(doc d - 1) !=  D_NOT_FOUND) {
      return doc d - 1;
    }

    //  f (doc d - 1)  s not ass gned, t n    ans   do not have any more t ets posted at t 
    // t  stamp correspond ng to doc d.   need to f nd t  prev ous relat ve t  stamp for wh ch
    // t  mapper has t ets, and return t  f rst t et for that t  stamp. Note that  erat ng
    // over t  space of all poss ble t  stamps  s faster than  erat ng over t  space of all
    // poss ble doc  Ds ( 's MAX_DOCS_PER_T MESTAMP t  s faster).
     nt prev ousDoc dT  stamp = (doc d >> DOC_ D_B TS) - 1;
    byte numDocsForT  stamp = t etsPerT  stamp.get(prev ousDoc dT  stamp);
     nt m nDoc dT  stamp = getM nDoc D() >> DOC_ D_B TS;
    wh le ((prev ousDoc dT  stamp >= m nDoc dT  stamp)
           && (numDocsForT  stamp ==  D_NOT_FOUND)) {
      --prev ousDoc dT  stamp;
      numDocsForT  stamp = t etsPerT  stamp.get(prev ousDoc dT  stamp);
    }

     f (numDocsForT  stamp !=  D_NOT_FOUND) {
      return getDoc dForT  stamp(prev ousDoc dT  stamp, (byte) 1);
    }

    return  D_NOT_FOUND;
  }

  @Overr de
  publ c long getT et D( nt doc d) {
    return t et ds.get(doc d);
  }

  @Overr de
  protected  nt addMapp ng nternal(long t et d) {
     nt doc d = newDoc d(t et d);
     f (doc d ==  D_NOT_FOUND) {
      return  D_NOT_FOUND;
    }

    t et ds.put(doc d, t et d);
    return doc d;
  }

  @Overr de
  protected  nt f ndDoc DBound nternal(long t et d, boolean f ndMaxDoc d) {
    // Note that   would be  ncorrect to lookup t  doc  D for t  g ven t et  D and return that
    // doc  D, as   would sk p over t ets created  n t  sa  m ll second but w h a lo r doc  D.
     nt doc dT  stamp = getDoc dT  stamp(t et d);

    // T  doc dT  stamp  s  D_NOT_FOUND only  f t  t et  s from before t  seg nt boundary and
    // t  should never happen  re because T et DMapper.f ndDoc dBound ensures that t  t et  d
    // passed  nto t   thod  s >= m nT et D wh ch  ans t  t et  s from after t  seg nt
    // boundary.
    Precond  ons.c ckState(
        doc dT  stamp !=  D_NOT_FOUND,
        "Tr ed to f nd doc  d bound for t et %d wh ch  s from before t  seg nt boundary %d",
        t et d,
        seg ntBoundaryT  stamp);

    //  's OK to return a doc  D that doesn't correspond to any t et  D  n t   ndex,
    // as t  doc  D  s s mply used as a start ng po nt and end ng po nt for range quer es,
    // not a s ce of truth.
     f (f ndMaxDoc d) {
      // Return t  largest poss ble doc  D for t  t  stamp.
      return getDoc dForT  stamp(doc dT  stamp, (byte) 1);
    } else {
      // Return t  smallest poss ble doc  D for t  t  stamp.
      byte t ets nT  stamp = t etsPerT  stamp.getOrDefault(doc dT  stamp, (byte) 0);
      return getDoc dForT  stamp(doc dT  stamp, t ets nT  stamp);
    }
  }

  /**
   * Returns t  array of all t et  Ds stored  n t  mapper  n a sorted (descend ng) order.
   * Essent ally, t   thod remaps all t et  Ds stored  n t  mapper to a compressed doc  D
   * space of [0, numDocs).
   *
   * Note that t   thod  s not thread safe, and  's  ant to be called only at seg nt
   * opt m zat on t  .  f addMapp ng nternal()  s called dur ng t  execut on of t   thod,
   * t  behav or  s undef ned (  w ll most l kely return bad results or throw an except on).
   *
   * @return An array of all t et  Ds stored  n t  mapper,  n a sorted (descend ng) order.
   */
  publ c long[] sortT et ds() {
     nt numDocs = getNumDocs();
     f (numDocs == 0) {
      return new long[0];
    }

    // Add all t ets stored  n t  mapper to sortT et ds.
    long[] sortedT et ds = new long[numDocs];
     nt sortedT et ds ndex = 0;
    for ( nt doc d = getM nDoc D(); doc d !=  D_NOT_FOUND; doc d = getNextDoc D(doc d)) {
      sortedT et ds[sortedT et ds ndex++] = getT et D(doc d);
    }
    Precond  ons.c ckState(sortedT et ds ndex == numDocs,
                             "Could not traverse all docu nts  n t  mapper. Expected to f nd "
                             + numDocs + " docs, but found only " + sortedT et ds ndex);

    // Sort sortedT et ds ndex  n descend ng order. T re's no way to sort a pr m  ve array  n
    // descend ng order, so   have to sort    n ascend ng order and t n reverse  .
    Arrays.sort(sortedT et ds);
    for ( nt   = 0;   < numDocs / 2; ++ ) {
      long tmp = sortedT et ds[ ];
      sortedT et ds[ ] = sortedT et ds[numDocs - 1 -  ];
      sortedT et ds[numDocs - 1 -  ] = tmp;
    }

    return sortedT et ds;
  }

  @Overr de
  publ c Doc DToT et DMapper opt m ze() throws  OExcept on {
    return new Opt m zedT et DMapper(t );
  }

  /**
   * Returns t  largest T et  D that t  doc  D mapper could handle. T  returned T et  D
   * would be safe to put  nto t  mapper, but any larger ones would not be correctly handled.
   */
  publ c stat c long calculateMaxT et D(long t  sl ce D) {
    long numberOfUsableT  stamps = LARGEST_RELAT VE_T MESTAMP - LATE_TWEETS_T ME_BUFFER_M LL S;
    long f rstT  stamp = Snowflake dParser.getT  stampFromT et d(t  sl ce D);
    long lastT  stamp = f rstT  stamp + numberOfUsableT  stamps;
    return Snowflake dParser.generateVal dStatus d(
        lastT  stamp, Snowflake dParser.RESERVED_B TS_MASK);
  }

  /**
   * Evaluates w t r two  nstances of OutOfOrderRealt  T et DMapper are equal by value.    s
   * slow because   has to c ck every t et  D/doc  D  n t  map.
   */
  @V s bleForTest ng
  boolean verySlowEqualsForTests(OutOfOrderRealt  T et DMapper that) {
    return getM nT et D() == that.getM nT et D()
        && getMaxT et D() == that.getMaxT et D()
        && getM nDoc D() == that.getM nDoc D()
        && getMaxDoc D() == that.getMaxDoc D()
        && seg ntBoundaryT  stamp == that.seg ntBoundaryT  stamp
        && seg ntS ze == that.seg ntS ze
        && t etsPerT  stamp.equals(that.t etsPerT  stamp)
        && t et ds.equals(that.t et ds);
  }

  @Overr de
  publ c OutOfOrderRealt  T et DMapper.FlushHandler getFlushHandler() {
    return new OutOfOrderRealt  T et DMapper.FlushHandler(t );
  }

  pr vate OutOfOrderRealt  T et DMapper(
    long m nT et D,
    long maxT et D,
     nt m nDoc D,
     nt maxDoc D,
    long seg ntBoundaryT  stamp,
     nt seg ntS ze,
     nt[] doc Ds,
    long[] t et DL st
  ) {
    super(m nT et D, maxT et D, m nDoc D, maxDoc D, doc Ds.length);

    Precond  ons.c ckState(doc Ds.length == t et DL st.length);

    t .seg ntBoundaryT  stamp = seg ntBoundaryT  stamp;
    t .seg ntS ze = seg ntS ze;

    t et ds = new  nt2LongOpenHashMap(seg ntS ze);
    t et ds.defaultReturnValue( D_NOT_FOUND);

    t etsPerT  stamp = new  nt2ByteOpenHashMap(seg ntS ze);
    t etsPerT  stamp.defaultReturnValue((byte)  D_NOT_FOUND);

    for ( nt   = 0;   < doc Ds.length;  ++) {
       nt doc D = doc Ds[ ];
      long t et D = t et DL st[ ];
      t et ds.put(doc D, t et D);

       nt t  stampBucket = doc D >> DOC_ D_B TS;
       f (t etsPerT  stamp.conta nsKey(t  stampBucket)) {
        t etsPerT  stamp.addTo(t  stampBucket, (byte) 1);
      } else {
        t etsPerT  stamp.put(t  stampBucket, (byte) 1);
      }
    }
  }

  publ c stat c class FlushHandler extends Flushable.Handler<OutOfOrderRealt  T et DMapper> {
    pr vate stat c f nal Str ng M N_TWEET_ D_PROP_NAME = "M nT et D";
    pr vate stat c f nal Str ng MAX_TWEET_ D_PROP_NAME = "MaxT et D";
    pr vate stat c f nal Str ng M N_DOC_ D_PROP_NAME = "M nDoc D";
    pr vate stat c f nal Str ng MAX_DOC_ D_PROP_NAME = "MaxDoc D";
    pr vate stat c f nal Str ng SEGMENT_BOUNDARY_T MESTAMP_PROP_NAME = "Seg ntBoundaryT  stamp";
    pr vate stat c f nal Str ng SEGMENT_S ZE_PROP_NAME = "Seg ntS ze";

    publ c FlushHandler() {
      super();
    }

    publ c FlushHandler(OutOfOrderRealt  T et DMapper objectToFlush) {
      super(objectToFlush);
    }

    @Overr de
    protected vo d doFlush(Flush nfo flush nfo, DataSer al zer ser al zer) throws  OExcept on {
      OutOfOrderRealt  T et DMapper mapper = getObjectToFlush();

      flush nfo.addLongProperty(M N_TWEET_ D_PROP_NAME, mapper.getM nT et D());
      flush nfo.addLongProperty(MAX_TWEET_ D_PROP_NAME, mapper.getMaxT et D());
      flush nfo.add ntProperty(M N_DOC_ D_PROP_NAME, mapper.getM nDoc D());
      flush nfo.add ntProperty(MAX_DOC_ D_PROP_NAME, mapper.getMaxDoc D());
      flush nfo.addLongProperty(SEGMENT_BOUNDARY_T MESTAMP_PROP_NAME,
          mapper.seg ntBoundaryT  stamp);
      flush nfo.add ntProperty(SEGMENT_S ZE_PROP_NAME, mapper.seg ntS ze);

      ser al zer.wr e nt(mapper.t et ds.s ze());
      for ( nt2LongMap.Entry entry : mapper.t et ds. nt2LongEntrySet()) {
        ser al zer.wr e nt(entry.get ntKey());
        ser al zer.wr eLong(entry.getLongValue());
      }
    }

    @Overr de
    protected OutOfOrderRealt  T et DMapper doLoad(Flush nfo flush nfo, DataDeser al zer  n)
        throws  OExcept on {

       nt s ze =  n.read nt();
       nt[] doc ds = new  nt[s ze];
      long[] t et ds = new long[s ze];
      for ( nt   = 0;   < s ze;  ++) {
        doc ds[ ] =  n.read nt();
        t et ds[ ] =  n.readLong();
      }

      return new OutOfOrderRealt  T et DMapper(
          flush nfo.getLongProperty(M N_TWEET_ D_PROP_NAME),
          flush nfo.getLongProperty(MAX_TWEET_ D_PROP_NAME),
          flush nfo.get ntProperty(M N_DOC_ D_PROP_NAME),
          flush nfo.get ntProperty(MAX_DOC_ D_PROP_NAME),
          flush nfo.getLongProperty(SEGMENT_BOUNDARY_T MESTAMP_PROP_NAME),
          flush nfo.get ntProperty(SEGMENT_S ZE_PROP_NAME),
          doc ds,
          t et ds);
    }
  }
}
