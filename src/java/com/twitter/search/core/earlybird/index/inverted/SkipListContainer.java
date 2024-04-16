package com.tw ter.search.core.earlyb rd. ndex. nverted;

 mport java. o. OExcept on;
 mport java.ut l.Random;

 mport javax.annotat on.Nullable;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;

 mport com.tw ter.search.common.ut l. o.flushable.DataDeser al zer;
 mport com.tw ter.search.common.ut l. o.flushable.DataSer al zer;
 mport com.tw ter.search.common.ut l. o.flushable.Flush nfo;
 mport com.tw ter.search.common.ut l. o.flushable.Flushable;

 mport stat c com.tw ter.search.core.earlyb rd. ndex. nverted.PayloadUt l.EMPTY_PAYLOAD;

/**
 * T   s a sk p l st conta ner  mple ntat on backed by {@l nk  ntBlockPool}.
 *
 * Sk p l st  s a data structure s m lar to l nked l st, but w h a h erarchy of l sts
 * each sk pp ng over fe r ele nts, and t  bottom h erarchy does NOT sk p any ele nts.
 * @see <a href="http://en.w k ped a.org/w k /Sk p_l st">Sk p L st W k ped a</a>
 *
 * T   mple ntat on  s lock free and thread safe w h ONE wr er thread and MULT PLE reader
 * threads.
 *
 * T   mple ntat on could conta n one or more sk p l sts, and t y are all backed by
 * t  sa  {@l nk  ntBlockPool}.
 *
 * Values are actually stored as  ntegers; ho ver search key  s  mple nted as a gener c type.
 *  nserts of values that already ex st are stored as subsequent ele nts. T   s used to support
 * pos  ons and term frequency.
 *
 * Also reserve t   nteger after value to store next ord nal po nter  nformat on.   avo d stor ng
 * po nters to t  next ele nt  n t  to r by allocat ng t m cont guously. To descend t  to r,
 *   just  ncre nt t  po nter.
 *
 * T  sk p l st can also store pos  ons as  ntegers.   allocates t m before   allocates t 
 * value (t  value  s a doc  D  f   are us ng pos  ons). T   ans that   can access t 
 * pos  on by s mply decre nt ng t  value po nter.
 *
 * To understand how t  sk p l st works, f rst understand how  nsert works, t n t  rest w ll be
 * more compre ndable.
 *
 * A sk p l st w ll be  mple nted  n a c rcle l nked way:
 *   - t  l st  ad node w ll have t  sent nel value, wh ch  s t  adv sory greatest value
 *     prov ded by comparator.
 *   - Real f rst value w ll be po nted by t  l st  ad node.
 *   - Real last value w ll po nt to t  l st  ad.
 *
 * Constra nts:
 *   - Does NOT support negat ve value.
 *
 * S mple V z:
 *
 * Empty l st w h max to r   ght 5. S = Sent nel value,   =  n  al value.
 *    | s| 0| 0| 0| 0| 0|  |  |  |  |  |  |  |  |  |  |
 *
 * One poss ble s uat on after  nsert ng 4, 6, 5.
 *    | s| 6| 6| 9| 0| 0| 4|13|13| 6| 0| 0| 0| 5| 9| 9|
 */
publ c class Sk pL stConta ner<K>  mple nts Flushable {
  /**
   * T  l st  ad of f rst sk p l st  n t  conta ner, t   s for conven ent usage,
   * so appl cat on use only one sk p l st does not need to keep track of t  l st  ad.
   */
  stat c f nal  nt F RST_L ST_HEAD = 0;

  /**
   *  n  al value used w n  n  al ze  nt block pool. Not ce -1  s not used  re  n order to g ve
   * appl cat on more freedom because -1  s a spec al value w n do ng b  man pulat ons.
   */
  stat c f nal  nt  N T AL_VALUE = -2;

  /**
   *  Max mum to r   ght of t  sk p l st and chance to grow to r by level.
   *
   *  Not ce t se two values could affect t   mory usage and t  performance.
   *   deally t y should be calculated based on t  potent al s ze of t  sk p l st.
   *
   *  G ven n  s t  number of ele nts  n t  sk p l st, t   mory usage  s  n O(n).
   *
   *  More prec sely,
   *
   *  t   mory  s ma nly used for t  follow ng data:
   *
   *   ader_to r  = O(maxTo r  ght + 1)
   *  value         = O(n)
   *  next_po nters = O(n * (1 - growTo rChance^(maxTo r  ght + 1)) / (1 - growTo rChance))
   *
   * thus, t  total  mory usage  s  n O( ader_to r + value + next_po nters).
   *
   * Default value for max mum to r   ght and grow to r chance, t se two numbers are chosen
   * arb rar ly now.
   */
  @V s bleForTest ng
  publ c stat c f nal  nt MAX_TOWER_HE GHT = 10;
  pr vate stat c f nal float GROW_TOWER_CHANCE = 0.2f;

  publ c enum HasPos  ons {
    YES,
    NO
  }

  publ c enum HasPayloads {
    YES,
    NO
  }

  stat c f nal  nt  NVAL D_POS T ON = -3;

  /**  mory barr er. */
  pr vate volat le  nt maxPoolPo nter;

  /** Actual storage data structure. */
  pr vate f nal  ntBlockPool blockPool;

  /**
   * Default comparator used to determ ne t  order bet en two g ven values or bet en one key and
   * anot r value.
   *
   * Not ce t  comparator  s shared by all threads us ng t  sk p l st, so    s not thread safe
   *  f    s ma nta n ng so  states. Ho ver, {@l nk #search}, {@l nk # nsert}, and
   * {@l nk #searchCe l} support passed  n comparator as a para ter, wh ch should be thread safe  f
   * managed by t  caller properly.
   */
  pr vate f nal Sk pL stComparator<K> defaultComparator;

  /** Random generator used to dec de  f to grow to r by one level or not. */
  pr vate f nal Random random = new Random();

  /**
   * Used by wr er thread to record last po nters at each level. Not ce    s ok to have   as an
   *  nstance f eld because   would only have one wr er thread.
   */
  pr vate f nal  nt[] lastPo nters;

  /**
   * W t r t  sk p l st conta ns pos  ons. Used for text f elds.
   */
  pr vate f nal HasPos  ons hasPos  ons;

  pr vate f nal HasPayloads hasPayloads;

  /**
   * Creates a new probab l st c sk p l st, us ng t  prov ded comparator to compare keys
   * of type K.
   *
   * @param comparator a comparator used to compare  nteger values.
   */
  publ c Sk pL stConta ner(
      Sk pL stComparator<K> comparator,
      HasPos  ons hasPos  ons,
      HasPayloads hasPayloads,
      Str ng na 
  ) {
    t (comparator, new  ntBlockPool( N T AL_VALUE, na ), hasPos  ons, hasPayloads);
  }

  /**
   * Base constructor, also used by flush handler.
   */
  pr vate Sk pL stConta ner(
      Sk pL stComparator<K> comparator,
       ntBlockPool blockPool,
      HasPos  ons hasPos  ons,
      HasPayloads hasPayloads) {
    // Sent nel value spec f ed by t  comparator cannot equal to  N T AL_VALUE.
    Precond  ons.c ckArgu nt(comparator.getSent nelValue() !=  N T AL_VALUE);

    t .defaultComparator = comparator;
    t .lastPo nters = new  nt[MAX_TOWER_HE GHT];
    t .blockPool = blockPool;
    t .hasPos  ons = hasPos  ons;
    t .hasPayloads = hasPayloads;
  }

  /**
   * Search for t   ndex of t  greatest value wh ch has key less than or equal to t  g ven key.
   *
   * T   s more l ke a floor search funct on. See {@l nk #searchCe l} for ce l search.
   *
   * @param key target key w ll be searc d.
   * @param sk pL st ad  ndex of t   ader to r of t  sk p l st w ll be searc d.
   * @param comparator comparator used for compar son w n travers ng through t  sk p l st.
   * @param searchF nger {@l nk Sk pL stSearchF nger} to accelerate search speed,
   *                     not ce t  search f nger must be before t  key.
   * @return t   ndex of t  greatest value wh ch  s less than or equal to g ven value,
   *         w ll return sk pL st ad  f g ven value has no greater or equal values.
   */
  publ c  nt search(
      K key,
       nt sk pL st ad,
      Sk pL stComparator<K> comparator,
      @Nullable Sk pL stSearchF nger searchF nger) {
    assert comparator != null;
    // Start at t   ader to r.
     nt currentPo nter = sk pL st ad;

    //  nstant ate nextPo nter and nextValue outs de of t  for loop so   can use t  value
    // d rectly after for loop.
     nt nextPo nter = getForwardPo nter(currentPo nter, MAX_TOWER_HE GHT - 1);
     nt nextValue = getValue(nextPo nter);

    // Top down traversal.
    for ( nt currentLevel = MAX_TOWER_HE GHT - 1; currentLevel >= 0; currentLevel--) {
      nextPo nter = getForwardPo nter(currentPo nter, currentLevel);
      nextValue = getValue(nextPo nter);

      // Jump to search f nger at current level.
       f (searchF nger != null) {
        f nal  nt f ngerPo nter = searchF nger.getPo nter(currentLevel);
         assert searchF nger. s n  alPo nter(f ngerPo nter)
            || comparator.compareKeyW hValue(key, getValue(f ngerPo nter),  NVAL D_POS T ON) >= 0;

         f (!searchF nger. s n  alPo nter(f ngerPo nter)
            && comparator.compareValues(getValue(f ngerPo nter), nextValue) >= 0) {
          currentPo nter = f ngerPo nter;
          nextPo nter = getForwardPo nter(currentPo nter, currentLevel);
          nextValue = getValue(nextPo nter);
        }
      }

      // Move forward.
      wh le (comparator.compareKeyW hValue(key, nextValue,  NVAL D_POS T ON) > 0) {
        currentPo nter = nextPo nter;

        nextPo nter = getForwardPo nter(currentPo nter, currentLevel);
        nextValue = getValue(nextPo nter);
      }

      // Advance search f nger.
       f (searchF nger != null && currentPo nter != sk pL st ad) {
        f nal  nt currentValue = getValue(currentPo nter);
        f nal  nt f ngerPo nter = searchF nger.getPo nter(currentLevel);

         f (searchF nger. s n  alPo nter(f ngerPo nter)
            || comparator.compareValues(currentValue, getValue(f ngerPo nter)) > 0) {
          searchF nger.setPo nter(currentLevel, currentPo nter);
        }
      }
    }

    // Return next po nter  f next value matc s searc d value; ot rw se return currentPo nter.
    return comparator.compareKeyW hValue(key, nextValue,  NVAL D_POS T ON) == 0
        ? nextPo nter : currentPo nter;
  }

  /**
   * Perform search w h {@l nk #defaultComparator}.
   * Not ce {@l nk #defaultComparator}  s not thread safe  f    s keep ng so  states.
   */
  publ c  nt search(K key,  nt sk pL st ad, @Nullable Sk pL stSearchF nger searchF nger) {
    return search(key, sk pL st ad, t .defaultComparator, searchF nger);
  }

  /**
   * Ce l search on g ven {@param key}.
   *
   * @param key target key w ll be searc d.
   * @param sk pL st ad  ndex of t   ader to r of t  sk p l st w ll be searc d.
   * @param comparator comparator used for compar son w n travers ng through t  sk p l st.
   * @param searchF nger {@l nk Sk pL stSearchF nger} to accelerate search speed.
   * @return  ndex of t  smallest value w h key greater or equal to t  g ven key.
   */
  publ c  nt searchCe l(
      K key,
       nt sk pL st ad,
      Sk pL stComparator<K> comparator,
      @Nullable Sk pL stSearchF nger searchF nger) {
    assert comparator != null;

    // Perform regular search.
    f nal  nt foundPo nter = search(key, sk pL st ad, comparator, searchF nger);

    // Return foundPo nter  f    s not t  l st  ad and t  po nted value has key equal to t 
    // g ven key; ot rw se, return next po nter.
     f (foundPo nter != sk pL st ad
        && comparator.compareKeyW hValue(key, getValue(foundPo nter),  NVAL D_POS T ON) == 0) {
      return foundPo nter;
    } else {
      return getNextPo nter(foundPo nter);
    }
  }

  /**
   * Perform searchCe l w h {@l nk #defaultComparator}.
   * Not ce {@l nk #defaultComparator}  s not thread safe  f    s keep ng so  states.
   */
  publ c  nt searchCe l(
      K key,  nt sk pL st ad, @Nullable Sk pL stSearchF nger searchF nger) {
    return searchCe l(key, sk pL st ad, t .defaultComparator, searchF nger);
  }

  /**
   *  nsert a new value  nto t  sk p l st.
   *
   * Not ce  nsert ng supports dupl cate keys and dupl cate values.
   *
   * Dupl cate keys w h d fferent values or pos  ons w ll be  nserted consecut vely.
   * Duplc ate keys w h  dent cal values w ll be  gnored, and t  dupl cate w ll not be stored  n
   * t  post ng l st.
   *
   * @param key  s t  key of t  g ven value.
   * @param value  s t  value w ll be  nserted, cannot be {@l nk #getSent nelValue()}.
   * @param sk pL st ad  ndex of t   ader to r of t  sk p l st w ll accept t  new value.
   * @param comparator comparator used for compar son w n travers ng through t  sk p l st.
   * @return w t r t  value ex sts  n t  post ng l st. Note that t  w ll return true even
   *  f    s a new pos  on.
   */
  publ c boolean  nsert(K key,  nt value,  nt pos  on,  nt[] payload,  nt sk pL st ad,
                    Sk pL stComparator<K> comparator) {
    Precond  ons.c ckArgu nt(comparator != null);
    Precond  ons.c ckArgu nt(value != getSent nelValue());

    // Start at t   ader to r.
     nt currentPo nter = sk pL st ad;

    //  n  al ze lastPo nters.
    for ( nt   = 0;   < MAX_TOWER_HE GHT;  ++) {
      t .lastPo nters[ ] =  N T AL_VALUE;
    }
     nt nextPo nter =  N T AL_VALUE;

    // Top down traversal.
    for ( nt currentLevel = MAX_TOWER_HE GHT - 1; currentLevel >= 0; currentLevel--) {
      nextPo nter = getForwardPo nter(currentPo nter, currentLevel);
       nt nextValue = getValue(nextPo nter);

       nt nextPos  on = getPos  on(nextPo nter);
      wh le (comparator.compareKeyW hValue(key, nextValue, nextPos  on) > 0) {
        currentPo nter = nextPo nter;

        nextPo nter = getForwardPo nter(currentPo nter, currentLevel);
        nextValue = getValue(nextPo nter);
        nextPos  on = getPos  on(nextPo nter);
      }

      // Store last po nters.
      lastPo nters[currentLevel] = currentPo nter;
    }

    //   use  sDupl cateValue to determ ne  f a value already ex sts  n a post ng l st (even  f  
    //  s a new pos  on).   need to c ck both current po nter and next po nter  n case t   s
    // t  largest pos  on   have seen for t  value  n t  sk p l st.  n that case, nextPo nter
    // w ll po nt to a larger value, but   want to c ck t  smaller one to see  f    s t  sa 
    // value. For example,  f   have [(1, 2), (2, 4)] and   want to  nsert (1, 3), t n
    // nextPo nter w ll po nt to (2, 4), but   want to c ck t  doc  D of (1, 2) to see  f   has
    // t  sa  docu nt  D.
    boolean  sDupl cateValue = getValue(currentPo nter) == value || getValue(nextPo nter) == value;

     f (comparator.compareKeyW hValue(key, getValue(nextPo nter), getPos  on(nextPo nter)) != 0) {
       f (hasPayloads == HasPayloads.YES) {
        Precond  ons.c ckNotNull(payload);
        //  f t  sk p l st has payloads,   store t  payload  m d ately before t  docu nt  D
        // and pos  on ( ff t  pos  on ex sts)  n t  block pool.   store payloads before
        // pos  ons because t y are var able length, and read ng past t m would requ re know ng
        // t  s ze of t  payload.   don't store payloads after t  doc  D because   have a
        // var able number of po nters after t  doc  D, and   would have no  dea w re t 
        // po nters stop and t  payload starts.
        for ( nt n : payload) {
          t .blockPool.add(n);
        }
      }

       f (hasPos  ons == HasPos  ons.YES) {
        //  f t  sk p l st has pos  ons,   store t  pos  on before t  docu nt  D  n t 
        // block pool.
        t .blockPool.add(pos  on);
      }

      //  nsert value.
      f nal  nt  nsertedPo nter = t .blockPool.add(value);

      //  nsert outgo ng po nters.
      f nal  nt   ght = getRandomTo r  ght();
      for ( nt currentLevel = 0; currentLevel <   ght; currentLevel++) {
        t .blockPool.add(getForwardPo nter(lastPo nters[currentLevel], currentLevel));
      }

      t .sync();

      // Update  ncom ng po nters.
      for ( nt currentLevel = 0; currentLevel <   ght; currentLevel++) {
        setForwardPo nter(lastPo nters[currentLevel], currentLevel,  nsertedPo nter);
      }

      t .sync();
    }

    return  sDupl cateValue;
  }

  /**
   * Delete a g ven key from sk p l st
   *
   * @param key t  key of t  g ven value
   * @param sk pL st ad  ndex of t   ader to r of t  sk p l st w ll accept t  new value
   * @param comparator comparator used for compar son w n travers ng through t  sk p l st
   * @return smallest value  n t  conta ner. Returns {@l nk # N T AL_VALUE}  f t 
   * key does not ex st.
   */
  publ c  nt delete(K key,  nt sk pL st ad, Sk pL stComparator<K> comparator) {
    boolean foundKey = false;

    for ( nt currentLevel = MAX_TOWER_HE GHT - 1; currentLevel >= 0; currentLevel--) {
       nt currentPo nter = sk pL st ad;
       nt nextValue = getValue(getForwardPo nter(currentPo nter, currentLevel));

      // F rst   sk p over all t  nodes that are smaller than   key.
      wh le (comparator.compareKeyW hValue(key, nextValue,  NVAL D_POS T ON) > 0) {
        currentPo nter = getForwardPo nter(currentPo nter, currentLevel);
        nextValue = getValue(getForwardPo nter(currentPo nter, currentLevel));
      }

      Precond  ons.c ckState(currentPo nter !=  N T AL_VALUE);

      //  f   don't f nd t  node at t  level that's OK, keep search ng on a lo r one.
       f (comparator.compareKeyW hValue(key, nextValue,  NVAL D_POS T ON) != 0) {
        cont nue;
      }

      //   found an ele nt to delete.
      foundKey = true;

      // Ot rw se, save t  current po nter. R ght now, current po nter po nts to t  f rst ele nt
      // that has t  sa  value as key.
       nt savedPo nter = currentPo nter;

      currentPo nter = getForwardPo nter(currentPo nter, currentLevel);
      // T n, walk over every ele nt that  s equal to t  key.
      wh le (comparator.compareKeyW hValue(key, getValue(currentPo nter),  NVAL D_POS T ON) == 0) {
        currentPo nter = getForwardPo nter(currentPo nter, currentLevel);
      }

      // update t  saved po nter to po nt to t  f rst non-equal ele nt of t  sk p l st.
      setForwardPo nter(savedPo nter, currentLevel, currentPo nter);
    }

    // So th ng has changed, need to sync up  re.
     f (foundKey) {
      t .sync();
      // return smallest value, m ght be used as f rst post ngs later
      return getSmallestValue(sk pL st ad);
    }

    return  N T AL_VALUE;
  }

  /**
   * Perform  nsert w h {@l nk #defaultComparator}.
   * Not ce {@l nk #defaultComparator}  s not thread safe  f    s keep ng so  states.
   */
  publ c boolean  nsert(K key,  nt value,  nt sk pL st ad) {
    return  nsert(key, value,  NVAL D_POS T ON, EMPTY_PAYLOAD, sk pL st ad,
        t .defaultComparator);
  }

  publ c boolean  nsert(K key,  nt value,  nt pos  on,  nt[] payload,  nt sk pL st ad) {
    return  nsert(key, value, pos  on, payload, sk pL st ad, t .defaultComparator);
  }

  /**
   * Perform delete w h {@l nk #defaultComparator}.
   * Not ce {@l nk #defaultComparator}  s not thread safe  f    s keep ng so  states.
   */
  publ c  nt delete(K key,  nt sk pL st ad) {
    return delete(key, sk pL st ad, t .defaultComparator);
  }

  /**
   * Get t  po nter of next value po nted by t  g ven po nter.
   *
   * @param po nter reference to t  current value.
   * @return po nter of next value.
   */
  publ c  nt getNextPo nter( nt po nter) {
    return getForwardPo nter(po nter, 0);
  }

  /**
   * Get t  value po nted by a po nter, t   s a dereference process.
   *
   * @param po nter  s an array  ndex on t .blockPool.
   * @return value po nted po nted by t  po nter.
   */
  publ c  nt getValue( nt po nter) {
     nt value = blockPool.get(po nter);

    // V s b l y race
     f (value ==  N T AL_VALUE) {
      // Volat le read to cross t   mory barr er aga n.
      f nal boolean  sSafe =  sPo nterSafe(po nter);
      assert  sSafe;

      // Re-read t  po nter aga n
      value = blockPool.get(po nter);
    }

    return value;
  }

  publ c  nt getSmallestValue( nt sk pL st ader) {
    return getValue(getForwardPo nter(sk pL st ader, 0));
  }

  /**
   * Bu lder of a forward search f nger w h  ader to r  ndex.
   *
   * @return a new {@l nk Sk pL stSearchF nger} object.
   */
  publ c Sk pL stSearchF nger bu ldSearchF nger() {
    return new Sk pL stSearchF nger(MAX_TOWER_HE GHT);
  }

  /**
   * Added anot r sk p l st  nto t   nt pool.
   *
   * @return  ndex of t   ader to r of t  newly created sk p l st.
   */
  publ c  nt newSk pL st() {
    // V rtual value of  ader.
    f nal  nt sent nelValue = getSent nelValue();
     f (hasPos  ons == HasPos  ons.YES) {
      t .blockPool.add( NVAL D_POS T ON);
    }
    f nal  nt sk pL st ad = t .blockPool.add(sent nelValue);

    // Bu ld  ader to r,  n  ally po nt all t  po nters to
    //    self s nce no value has been  nserted.
    for ( nt   = 0;   < MAX_TOWER_HE GHT;  ++) {
      t .blockPool.add(sk pL st ad);
    }

    t .sync();

    return sk pL st ad;
  }

  /**
   * C ck  f t  block pool has been  n  ated by {@l nk #newSk pL st}.
   */
  publ c boolean  sEmpty() {
    return t .blockPool.length() == 0;
  }

  /**
   * Wr e to t  volat le var able to cross  mory barr er. maxPoolPo nter  s t   mory barr er
   * for new appends.
   */
  pr vate vo d sync() {
    t .maxPoolPo nter = t .blockPool.length();
  }

  /**
   * Read from volat le var able to cross  mory barr er.
   *
   * @param po nter  s an block pool  ndex.
   * @return boolean  nd cate  f g ven po nter  s w h n t  range of max pool po nter.
   */
  pr vate boolean  sPo nterSafe( nt po nter) {
    return po nter <= t .maxPoolPo nter;
  }

  /**
   * Get t  pos  on assoc ated w h t  doc  D po nted to by po nter.
   * @param po nter aka doc  D po nter.
   * @return T  value of t  pos  on for that doc  D. Returns  NVAL D_POS T ON  f t  sk p l st
   * does not have pos  ons, or  f t re  s no pos  on for that po nter.
   */
  publ c  nt getPos  on( nt po nter) {
     f (hasPos  ons == HasPos  ons.NO) {
      return  NVAL D_POS T ON;
    }
    //  f t  sk p l st has pos  ons, t  pos  on w ll always be  nserted  nto t  block pool
    //  m d ately before t  doc  D.
    return getValue(po nter - 1);
  }

  /**
   * Get t  payload po nter from a normal po nter (e.g. one returned from t  {@l nk t #search}
   *  thod).
   */
  publ c  nt getPayloadPo nter( nt po nter) {
    Precond  ons.c ckState(hasPayloads == HasPayloads.YES,
        "getPayloadPo nter() should only be called on a sk p l st that supports payloads.");

    //  f t  sk p l st has payloads, t  payload w ll always be  nserted  nto t  block pool
    // before t  doc  D, and before t  pos  on  f t re  s a pos  on.
     nt pos  onOffset = hasPos  ons == HasPos  ons.YES ? 1 : 0;

    return po nter - 1 - pos  onOffset;
  }


   nt getPoolS ze() {
    return t .blockPool.length();
  }


   ntBlockPool getBlockPool() {
    return blockPool;
  }

  publ c HasPayloads getHasPayloads() {
    return hasPayloads;
  }

  /******************
   *  lper  thods *
   ******************/

  /**
   * Get t  next forward po nter on a g ven level.
   *
   * @param po nter  s an array  ndex on t .blockPool, m ght be SENT NEL_VALUE.
   * @param level  nd cates t  level of t  forward po nter w ll be acqu red.    s zero  ndexed.
   * @return next forward po nter on t  g ven level, m ght be SENT NEL_VALUE.
   */
  pr vate  nt getForwardPo nter( nt po nter,  nt level) {
    f nal  nt po nter ndex = po nter + level + 1;

     nt forwardPo nter = blockPool.get(po nter ndex);

    // V s b l y race
     f (forwardPo nter ==  N T AL_VALUE) {
      // Volat le read to cross t   mory barr er aga n.
      f nal boolean  sSafe =  sPo nterSafe(po nter ndex);
      assert  sSafe;

      // Re-read t  po nter aga n
      forwardPo nter = blockPool.get(po nter ndex);
    }

    return forwardPo nter;
  }

 /**
   * Set t  next forward po nter on a g ven level.
   *
   * @param po nter po nts to t  value, of wh ch t  po nter value w ll be updated.
   * @param level  nd cates t  level of t  forward po nter w ll be set.    s zero  ndexed.
   * @param target t  value fo t  target po nter wh ch w ll be set.
   */
  pr vate vo d setForwardPo nter( nt po nter,  nt level,  nt target) {
    // Update  ader to r  f g ven po nter po nts to  aderTo r.
    setPo nter(po nter + level + 1, target);
  }

  /**
   * Set t  value po nted by po nter
   * @param po nter po nt to t  actual pos  on  n t  pool
   * @param target t  value   are go ng to set
   */
  pr vate vo d setPo nter( nt po nter,  nt target) {
    blockPool.set(po nter, target);
  }

  /**
   * Getter of t  sent nel value used by t  sk p l st. T  sent nel value should be prov ded
   * by t  comparator.
   *
   * @return sent nel value used by t  sk p l st.
   */
   nt getSent nelValue() {
    return defaultComparator.getSent nelValue();
  }

  /**
   * Return a   ght h  n range [1, maxTo r  ght], each number w h chance
   * growTo rChance ^ (h - 1).
   *
   * @return a  nteger  nd cat ng   ght.
   */
  pr vate  nt getRandomTo r  ght() {
     nt   ght = 1;
    wh le (  ght < MAX_TOWER_HE GHT && random.nextFloat() < GROW_TOWER_CHANCE) {
        ght++;
    }
    return   ght;
  }

  @SuppressWarn ngs("unc cked")
  @Overr de
  publ c FlushHandler<K> getFlushHandler() {
    return new FlushHandler<>(t );
  }

  publ c stat c class FlushHandler<K> extends Flushable.Handler<Sk pL stConta ner<K>> {
    pr vate f nal Sk pL stComparator<K> comparator;
    pr vate stat c f nal Str ng BLOCK_POOL_PROP_NAME = "blockPool";
    pr vate stat c f nal Str ng HAS_POS T ONS_PROP_NAME = "hasPos  ons";
    pr vate stat c f nal Str ng HAS_PAYLOADS_PROP_NAME = "hasPayloads";

    publ c FlushHandler(Sk pL stConta ner<K> objectToFlush) {
      super(objectToFlush);
      t .comparator = objectToFlush.defaultComparator;
    }

    publ c FlushHandler(Sk pL stComparator<K> comparator) {
      t .comparator = comparator;
    }

    @Overr de
    protected vo d doFlush(Flush nfo flush nfo, DataSer al zer out) throws  OExcept on {
      long startT   = getClock().nowM ll s();
      Sk pL stConta ner<K> objectToFlush = getObjectToFlush();
      flush nfo.addBooleanProperty(HAS_POS T ONS_PROP_NAME,
          objectToFlush.hasPos  ons == HasPos  ons.YES);
      flush nfo.addBooleanProperty(HAS_PAYLOADS_PROP_NAME,
          objectToFlush.hasPayloads == HasPayloads.YES);

      objectToFlush.blockPool.getFlushHandler()
          .flush(flush nfo.newSubPropert es(BLOCK_POOL_PROP_NAME), out);
      getFlushT  rStats().t  r ncre nt(getClock().nowM ll s() - startT  );
    }

    @Overr de
    protected Sk pL stConta ner<K> doLoad(Flush nfo flush nfo, DataDeser al zer  n)
        throws  OExcept on {
      long startT   = getClock().nowM ll s();
       ntBlockPool blockPool = (new  ntBlockPool.FlushHandler()).load(
          flush nfo.getSubPropert es(BLOCK_POOL_PROP_NAME),  n);
      getLoadT  rStats().t  r ncre nt(getClock().nowM ll s() - startT  );

      HasPos  ons hasPos  ons = flush nfo.getBooleanProperty(HAS_POS T ONS_PROP_NAME)
          ? HasPos  ons.YES : HasPos  ons.NO;
      HasPayloads hasPayloads = flush nfo.getBooleanProperty(HAS_PAYLOADS_PROP_NAME)
          ? HasPayloads.YES : HasPayloads.NO;

      return new Sk pL stConta ner<>(
          t .comparator,
          blockPool,
          hasPos  ons,
          hasPayloads);
    }
  }
}
