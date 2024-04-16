package com.tw ter.ann.hnsw;

 mport java. o. OExcept on;
 mport java.n o.ByteBuffer;
 mport java.ut l.ArrayL st;
 mport java.ut l.Collect ons;
 mport java.ut l.HashMap;
 mport java.ut l.HashSet;
 mport java.ut l.L st;
 mport java.ut l.Map;
 mport java.ut l.Objects;
 mport java.ut l.Opt onal;
 mport java.ut l.Random;
 mport java.ut l.Set;
 mport java.ut l.concurrent.ConcurrentHashMap;
 mport java.ut l.concurrent.atom c.Atom cReference;
 mport java.ut l.concurrent.locks.Lock;
 mport java.ut l.concurrent.locks.ReadWr eLock;
 mport java.ut l.concurrent.locks.ReentrantLock;
 mport java.ut l.concurrent.locks.ReentrantReadWr eLock;
 mport java.ut l.funct on.Funct on;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect. mmutableL st;

 mport org.apac .thr ft.TExcept on;

 mport com.tw ter.ann.common. ndexOutputF le;
 mport com.tw ter.ann.common.thr ftjava.Hnsw nternal ndex tadata;
 mport com.tw ter.b ject on. nject on;
 mport com.tw ter.logg ng.Logger;
 mport com.tw ter. d aserv ces.commons.codec.ArrayByteBufferCodec;
 mport com.tw ter.search.common.f le.AbstractF le;

/**
 * Typed mult hreaded HNSW  mple ntat on support ng creat on/query ng of approx mate nearest ne ghb 
 * Paper: https://arx v.org/pdf/1603.09320.pdf
 * Mult hread ng  mpl based on NMSL B vers on : https://g hub.com/nmsl b/hnsw/blob/master/hnswl b/hnswalg.h
 *
 * @param <T> T  type of  ems  nserted / searc d  n t  HNSW  ndex.
 * @param <Q> T  type of KNN query.
 */
publ c class Hnsw ndex<T, Q> {
  pr vate stat c f nal Logger LOG = Logger.get(Hnsw ndex.class);
  pr vate stat c f nal Str ng METADATA_F LE_NAME = "hnsw_ nternal_ tadata";
  pr vate stat c f nal Str ng GRAPH_F LE_NAME = "hnsw_ nternal_graph";
  pr vate stat c f nal  nt MAP_S ZE_FACTOR = 5;

  pr vate f nal D stanceFunct on<T, T> d stFn ndex;
  pr vate f nal D stanceFunct on<Q, T> d stFnQuery;
  pr vate f nal  nt efConstruct on;
  pr vate f nal  nt maxM;
  pr vate f nal  nt maxM0;
  pr vate f nal double levelMult pl er;
  pr vate f nal Atom cReference<Hnsw ta<T>> graph ta = new Atom cReference<>();
  pr vate f nal Map<HnswNode<T>,  mmutableL st<T>> graph;
  // To take lock on vertex level
  pr vate f nal ConcurrentHashMap<T, ReadWr eLock> locks;
  // To take lock on whole graph only  f vertex add  on  s on layer above t  current maxLevel
  pr vate f nal ReentrantLock globalLock;
  pr vate f nal Funct on<T, ReadWr eLock> lockProv der;

  pr vate f nal RandomProv der randomProv der;

  // Probab l y of reevaluat ng connect ons of an ele nt  n t  ne ghborhood dur ng an update
  // Can be used as a knob to adjust update_speed/search_speed tradeoff.
  pr vate f nal float updateNe ghborProbab l y;

  /**
   * Creates  nstance of hnsw  ndex.
   *
   * @param d stFn ndex      Any d stance  tr c/non  tr c that spec f es s m lar y bet en two  ems for  ndex ng.
   * @param d stFnQuery      Any d stance  tr c/non  tr c that spec f es s m lar y bet en  em for wh ch nearest ne ghb s quer ed for and already  ndexed  em.
   * @param efConstruct on   Prov de speed vs  ndex qual y tradeoff, h g r t  value better t  qual y and h g r t  t   to create  ndex.
   *                         Val d range of efConstruct on can be anyw re bet en 1 and tens of thousand. Typ cally,   should be set so that a search of M
   *                         ne ghbors w h ef=efConstruct on should end  n recall>0.95.
   * @param maxM             Max mum connect ons per layer except 0th level.
   *                         Opt mal values bet en 5-48.
   *                         Smaller M generally produces better result for lo r recalls and/ or lo r d  ns onal data,
   *                         wh le b gger M  s better for h gh recall and/ or h gh d  ns onal, data on t  expense of more  mory/d sk usage
   * @param expectedEle nts Approx mate number of ele nts to be  ndexed
   */
  protected Hnsw ndex(
      D stanceFunct on<T, T> d stFn ndex,
      D stanceFunct on<Q, T> d stFnQuery,
       nt efConstruct on,
       nt maxM,
       nt expectedEle nts,
      RandomProv der randomProv der
  ) {
    t (d stFn ndex,
        d stFnQuery,
        efConstruct on,
        maxM,
        expectedEle nts,
        new Hnsw ta<>(-1, Opt onal.empty()),
        new ConcurrentHashMap<>(MAP_S ZE_FACTOR * expectedEle nts),
        randomProv der
    );
  }

  pr vate Hnsw ndex(
      D stanceFunct on<T, T> d stFn ndex,
      D stanceFunct on<Q, T> d stFnQuery,
       nt efConstruct on,
       nt maxM,
       nt expectedEle nts,
      Hnsw ta<T> graph ta,
      Map<HnswNode<T>,  mmutableL st<T>> graph,
      RandomProv der randomProv der
  ) {
    t .d stFn ndex = d stFn ndex;
    t .d stFnQuery = d stFnQuery;
    t .efConstruct on = efConstruct on;
    t .maxM = maxM;
    t .maxM0 = 2 * maxM;
    t .levelMult pl er = 1.0 / Math.log(1.0 * maxM);
    t .graph ta.set(graph ta);
    t .graph = graph;
    t .locks = new ConcurrentHashMap<>(MAP_S ZE_FACTOR * expectedEle nts);
    t .globalLock = new ReentrantLock();
    t .lockProv der = key -> new ReentrantReadWr eLock();
    t .randomProv der = randomProv der;
    t .updateNe ghborProbab l y = 1.0f;
  }

  /**
   * w reConnect onForAllLayers f nds connect ons for a new ele nt and creates b -d rect on l nks.
   * T   thod assu s us ng a reentrant lock to l nk l st reads.
   *
   * @param entryPo nt t  global entry po nt
   * @param  em       t   em for wh ch t  connect ons are found
   * @param  emLevel  t  level of t  added  em (max mum layer  n wh ch   w re t  connect ons)
   * @param maxLayer   t  level of t  entry po nt
   */
  pr vate vo d w reConnect onForAllLayers(f nal T entryPo nt, f nal T  em, f nal  nt  emLevel,
                                          f nal  nt maxLayer, f nal boolean  sUpdate) {
    T curObj = entryPo nt;
     f ( emLevel < maxLayer) {
      curObj = bestEntryPo ntUnt lLayer(curObj,  em, maxLayer,  emLevel, d stFn ndex);
    }
    for ( nt level = Math.m n( emLevel, maxLayer); level >= 0; level--) {
      f nal D stanced emQueue<T, T> cand dates =
          searchLayerForCand dates( em, curObj, efConstruct on, level, d stFn ndex,  sUpdate);
      curObj = mutuallyConnectNewEle nt( em, cand dates, level,  sUpdate);
    }
  }

  /**
   *  nsert t   em  nto HNSW  ndex.
   */
  publ c vo d  nsert(f nal T  em) throws  llegalDupl cate nsertExcept on {
    f nal Lock  emLock = locks.compute fAbsent( em, lockProv der).wr eLock();
     emLock.lock();
    try {
      f nal Hnsw ta<T>  tadata = graph ta.get();
      //  f t  graph already have t   em, should not re- nsert   aga n
      // Need to c ck entry po nt  n case   re nsert f rst  em w re  s are no graph
      // but only a entry po nt
       f (graph.conta nsKey(HnswNode.from(0,  em))
          || ( tadata.getEntryPo nt(). sPresent()
          && Objects.equals( tadata.getEntryPo nt().get(),  em))) {
        throw new  llegalDupl cate nsertExcept on(
            "Dupl cate  nsert on  s not supported: " +  em);
      }
      f nal  nt curLevel = getRandomLevel();
      Opt onal<T> entryPo nt =  tadata.getEntryPo nt();
      // T  global lock prevents two threads from mak ng changes to t  entry po nt. T  lock
      // should get taken very  nfrequently. So th ng l ke log-base-levelMult pl er(num  ems)
      // For a full explanat on of lock ng see t  docu nt: http://go/hnsw-lock ng
       nt maxLevelCopy =  tadata.getMaxLevel();
       f (curLevel > maxLevelCopy) {
        globalLock.lock();
        // Re  n  al ze t  entryPo nt and maxLevel  n case t se are changed by any ot r thread
        // No need to c ck t  cond  on aga n s nce,
        //    s already c cked at t  end before updat ng entry po nt struct
        // No need to unlock for opt m zat on and keep ng as  s  f cond  on fa ls s nce threads
        // w ll not be enter ng t  sect on a lot.
        f nal Hnsw ta<T> temp = graph ta.get();
        entryPo nt = temp.getEntryPo nt();
        maxLevelCopy = temp.getMaxLevel();
      }

       f (entryPo nt. sPresent()) {
        w reConnect onForAllLayers(entryPo nt.get(),  em, curLevel, maxLevelCopy, false);
      }

       f (curLevel > maxLevelCopy) {
        Precond  ons.c ckState(globalLock. s ldByCurrentThread(),
            "Global lock not  ld before updat ng entry po nt");
        graph ta.set(new Hnsw ta<>(curLevel, Opt onal.of( em)));
      }
    } f nally {
       f (globalLock. s ldByCurrentThread()) {
        globalLock.unlock();
      }
       emLock.unlock();
    }
  }

  /**
   * set connect ons of an ele nt w h synchron zat on
   * T  only ot r place that should have t  lock for wr  ng  s dur ng
   * t  ele nt  nsert on
   */
  pr vate vo d setConnect onL st(f nal T  em,  nt layer, L st<T> connect ons) {
    f nal Lock cand dateLock = locks.compute fAbsent( em, lockProv der).wr eLock();
    cand dateLock.lock();
    try {
      graph.put(
          HnswNode.from(layer,  em),
           mmutableL st.copyOf(connect ons)
      );
    } f nally {
      cand dateLock.unlock();
    }
  }

  /**
   * Re nsert t   em  nto HNSW  ndex.
   * T   thod updates t  l nks of an ele nt assum ng
   * t  ele nt's d stance funct on  s changed externally (e.g. by updat ng t  features)
   */

  publ c vo d re nsert(f nal T  em) {
    f nal Hnsw ta<T>  tadata = graph ta.get();

    Opt onal<T> entryPo nt =  tadata.getEntryPo nt();

    Precond  ons.c ckState(entryPo nt. sPresent(),
        "Update cannot be perfor d  f entry po nt  s not present");

    // T   s a c ck for t  s ngle ele nt case
     f (entryPo nt.get().equals( em) && graph. sEmpty()) {
      return;
    }

    Precond  ons.c ckState(graph.conta nsKey(HnswNode.from(0,  em)),
        "Graph does not conta n t   em to be updated at level 0");

     nt curLevel = 0;

     nt maxLevelCopy =  tadata.getMaxLevel();

    for ( nt layer = maxLevelCopy; layer >= 0; layer--) {
       f (graph.conta nsKey(HnswNode.from(layer,  em))) {
        curLevel = layer;
        break;
      }
    }

    // Updat ng t  l nks of t  ele nts from t  1-hop rad us of t  updated ele nt

    for ( nt layer = 0; layer <= curLevel; layer++) {

      // F ll ng t  ele nt sets for cand dates and updated ele nts
      f nal HashSet<T> setCand = new HashSet<T>();
      f nal HashSet<T> setNe gh = new HashSet<T>();
      f nal L st<T> l stOneHop = getConnect onL stForRead( em, layer);

       f (l stOneHop. sEmpty()) {
        LOG.debug("No l nks for t  updated ele nt. Empty dataset?");
        cont nue;
      }

      setCand.add( em);

      for (T elOneHop : l stOneHop) {
        setCand.add(elOneHop);
         f (randomProv der.get().nextFloat() > updateNe ghborProbab l y) {
          cont nue;
        }
        setNe gh.add(elOneHop);
        f nal L st<T> l stTwoHop = getConnect onL stForRead(elOneHop, layer);

         f (l stTwoHop. sEmpty()) {
          LOG.debug("No l nks for t  updated ele nt. Empty dataset?");
        }

        for (T oneHopEl : l stTwoHop) {
          setCand.add(oneHopEl);
        }
      }
      // No need to update t   em  self, so remove  
      setNe gh.remove( em);

      // Updat ng t  l nk l sts of ele nts from setNe gh:
      for (T ne gh : setNe gh) {
        f nal HashSet<T> setCopy = new HashSet<T>(setCand);
        setCopy.remove(ne gh);
         nt keepEle ntsNum = Math.m n(efConstruct on, setCopy.s ze());
        f nal D stanced emQueue<T, T> cand dates = new D stanced emQueue<>(
            ne gh,
             mmutableL st.of(),
            false,
            d stFn ndex
        );
        for (T cand : setCopy) {
          f nal float d stance = d stFn ndex.d stance(ne gh, cand);
           f (cand dates.s ze() < keepEle ntsNum) {
            cand dates.enqueue(cand, d stance);
          } else {
             f (d stance < cand dates.peek().getD stance()) {
              cand dates.dequeue();
              cand dates.enqueue(cand, d stance);
            }
          }
        }
        f nal  mmutableL st<T> ne ghb s = selectNearestNe ghb sBy ur st c(
            cand dates,
            layer == 0 ? maxM0 : maxM
        );

        f nal L st<T> temp = getConnect onL stForRead(ne gh, layer);
         f (temp. sEmpty()) {
          LOG.debug("ex st ng l nksl st  s empty. Corrupt  ndex");
        }
         f (ne ghb s. sEmpty()) {
          LOG.debug("pred cted l nksl st  s empty. Corrupt  ndex");
        }
        setConnect onL st(ne gh, layer, ne ghb s);

      }


    }
    w reConnect onForAllLayers( tadata.getEntryPo nt().get(),  em, curLevel, maxLevelCopy, true);
  }

  /**
   * T   thod can be used to get t  graph stat st cs, spec f cally
   *   pr nts t   togram of  nbound connect ons for each ele nt.
   */
  pr vate Str ng getStats() {
     nt  togramMaxB ns = 50;
     nt[]  togram = new  nt[ togramMaxB ns];
    HashMap<T,  nteger> mmap = new HashMap<T,  nteger>();
    for (HnswNode<T> key : graph.keySet()) {
       f (key.level == 0) {
        L st<T> l nkL st = getConnect onL stForRead(key. em, key.level);
        for (T node : l nkL st) {
           nt a = mmap.compute fAbsent(node, k -> 0);
          mmap.put(node, a + 1);

        }
      }
    }

    for (T key : mmap.keySet()) {
       nt  nd = mmap.get(key) <  togramMaxB ns - 1 ? mmap.get(key) :  togramMaxB ns - 1;
       togram[ nd]++;
    }
     nt m nNonZero ndex;
    for (m nNonZero ndex =  togramMaxB ns - 1; m nNonZero ndex >= 0; m nNonZero ndex--) {
       f ( togram[m nNonZero ndex] > 0) {
        break;
      }
    }

    Str ng output = "";
    for ( nt   = 0;   <= m nNonZero ndex;  ++) {
      output += "" +   + "\t" +  togram[ ] / (0.01f * mmap.keySet().s ze()) + "\n";
    }

    return output;
  }

  pr vate  nt getRandomLevel() {
    return ( nt) (-Math.log(randomProv der.get().nextDouble()) * levelMult pl er);
  }

  /**
   * Note that to avo d deadlocks    s  mportant that t   thod  s called after all t  searc s
   * of t  graph have completed.  f   take a lock on any  ems d scovered  n t  graph after
   * t ,   may get stuck wa  ng on a thread that  s wa  ng for  em to be fully  nserted.
   * <p>
   * Note: w n us ng concurrent wr ers   can m ss connect ons that   would ot rw se get.
   * T  w ll reduce t  recall.
   * <p>
   * For a full explanat on of lock ng see t  docu nt: http://go/hnsw-lock ng
   * T   thod returns t  closest nearest ne ghbor (can be used as an enter po nt)
   */
  pr vate T mutuallyConnectNewEle nt(
      f nal T  em,
      f nal D stanced emQueue<T, T> cand dates, // Max queue
      f nal  nt level,
      f nal boolean  sUpdate
  ) {

    // Us ng maxM  re.  s  mple ntat on  s amb guous  n HNSW paper,
    // so us ng t  way    s gett ng used  n Hnsw l b.
    f nal  mmutableL st<T> ne ghb s = selectNearestNe ghb sBy ur st c(cand dates, maxM);
    setConnect onL st( em, level, ne ghb s);
    f nal  nt M = level == 0 ? maxM0 : maxM;
    for (T nn : ne ghb s) {
       f (nn.equals( em)) {
        cont nue;
      }
      f nal Lock curLock = locks.compute fAbsent(nn, lockProv der).wr eLock();
      curLock.lock();
      try {
        f nal HnswNode<T> key = HnswNode.from(level, nn);
        f nal  mmutableL st<T> connect ons = graph.getOrDefault(key,  mmutableL st.of());
        f nal boolean  s emAlreadyPresent =
             sUpdate && connect ons. ndexOf( em) != -1 ? true : false;

        //  f ` em`  s already present  n t  ne ghbor ng connect ons,
        // t n no need to mod fy any connect ons or run t  search  ur st cs.
         f ( s emAlreadyPresent) {
          cont nue;
        }

        f nal  mmutableL st<T> updatedConnect ons;
         f (connect ons.s ze() < M) {
          f nal L st<T> temp = new ArrayL st<>(connect ons);
          temp.add( em);
          updatedConnect ons =  mmutableL st.copyOf(temp. erator());
        } else {
          // Max Queue
          f nal D stanced emQueue<T, T> queue = new D stanced emQueue<>(
              nn,
              connect ons,
              false,
              d stFn ndex
          );
          queue.enqueue( em);
          updatedConnect ons = selectNearestNe ghb sBy ur st c(queue, M);
        }
         f (updatedConnect ons. sEmpty()) {
          LOG.debug(" nternal error: pred cted l nksl st  s empty");
        }

        graph.put(key, updatedConnect ons);
      } f nally {
        curLock.unlock();
      }
    }
    return ne ghb s.get(0);
  }

  /*
   *  bestEntryPo ntUnt lLayer starts t  graph search for  em from t  entry po nt
   *  unt l t  searc s reac s t  selectedLayer layer.
   *  @return a po nt from selectedLayer layer, was t  closest on t  (selectedLayer+1) layer
   */
  pr vate <K> T bestEntryPo ntUnt lLayer(
      f nal T entryPo nt,
      f nal K  em,
       nt maxLayer,
       nt selectedLayer,
      D stanceFunct on<K, T> d stFn
  ) {
    T curObj = entryPo nt;
     f (selectedLayer < maxLayer) {
      float curD st = d stFn.d stance( em, curObj);
      for ( nt level = maxLayer; level > selectedLayer; level--) {
        boolean changed = true;
        wh le (changed) {
          changed = false;
          f nal L st<T> l st = getConnect onL stForRead(curObj, level);
          for (T nn : l st) {
            f nal float tempD st = d stFn.d stance( em, nn);
             f (tempD st < curD st) {
              curD st = tempD st;
              curObj = nn;
              changed = true;
            }
          }
        }
      }
    }

    return curObj;
  }


  @V s bleForTest ng
  protected  mmutableL st<T> selectNearestNe ghb sBy ur st c(
      f nal D stanced emQueue<T, T> cand dates, // Max queue
      f nal  nt maxConnect ons
  ) {
    Precond  ons.c ckState(!cand dates. sM nQueue(),
        "cand dates  n selectNearestNe ghb sBy ur st c should be a max queue");

    f nal T baseEle nt = cand dates.getOr g n();
     f (cand dates.s ze() <= maxConnect ons) {
      L st<T> l st = cand dates.toL stW h em();
      l st.remove(baseEle nt);
      return  mmutableL st.copyOf(l st);
    } else {
      f nal L st<T> resSet = new ArrayL st<>(maxConnect ons);
      // M n queue for closest ele nts f rst
      f nal D stanced emQueue<T, T> m nQueue = cand dates.reverse();
      wh le (m nQueue.nonEmpty()) {
         f (resSet.s ze() >= maxConnect ons) {
          break;
        }
        f nal D stanced em<T> cand date = m nQueue.dequeue();

        //   do not want to creates loops:
        // Wh le  ur st c  s used only for creat ng t  l nks
         f (cand date.get em().equals(baseEle nt)) {
          cont nue;
        }

        boolean to nclude = true;
        for (T e : resSet) {
          // Do not  nclude cand date  f t  d stance from cand date to any of ex st ng  em  n
          // resSet  s closer to t  d stance from t  cand date to t   em. By do ng t , t 
          // connect on of graph w ll be more d verse, and  n case of h ghly clustered data set,
          // connect ons w ll be made bet en clusters  nstead of all be ng  n t  sa  cluster.
          f nal float d st = d stFn ndex.d stance(e, cand date.get em());
           f (d st < cand date.getD stance()) {
            to nclude = false;
            break;
          }
        }

         f (to nclude) {
          resSet.add(cand date.get em());
        }
      }
      return  mmutableL st.copyOf(resSet);
    }
  }

  /**
   * Search t   ndex for t  ne ghb s.
   *
   * @param query           Query
   * @param numOfNe ghb s Number of ne ghb s to search for.
   * @param ef              T  param controls t  accuracy of t  search.
   *                        B gger t  ef better t  accuracy on t  expense of latency.
   *                        Keep   atleast number of ne ghb s to f nd.
   * @return Ne ghb s
   */
  publ c L st<D stanced em<T>> searchKnn(f nal Q query, f nal  nt numOfNe ghb s, f nal  nt ef) {
    f nal Hnsw ta<T>  tadata = graph ta.get();
     f ( tadata.getEntryPo nt(). sPresent()) {
      T entryPo nt = bestEntryPo ntUnt lLayer( tadata.getEntryPo nt().get(),
          query,  tadata.getMaxLevel(), 0, d stFnQuery);
      // Get t  actual ne ghb s from 0th layer
      f nal L st<D stanced em<T>> ne ghb s =
          searchLayerForCand dates(query, entryPo nt, Math.max(ef, numOfNe ghb s),
              0, d stFnQuery, false).dequeueAll();
      Collect ons.reverse(ne ghb s);
      return ne ghb s.s ze() > numOfNe ghb s
          ? ne ghb s.subL st(0, numOfNe ghb s) : ne ghb s;
    } else {
      return Collect ons.emptyL st();
    }
  }

  // T   thod  s currently not used
  //    s needed for debugg ng purposes only
  pr vate vo d c ck ntegr y(Str ng  ssage) {
    f nal Hnsw ta<T>  tadata = graph ta.get();
    for (HnswNode<T> node : graph.keySet()) {
      L st<T> l nkL st = graph.get(node);

      for (T el : l nkL st) {
         f (el.equals(node. em)) {
          LOG.debug( ssage);
          throw new Runt  Except on(" ntegr y c ck fa led");
        }
      }
    }
  }

  pr vate <K> D stanced emQueue<K, T> searchLayerForCand dates(
      f nal K  em,
      f nal T entryPo nt,
      f nal  nt ef,
      f nal  nt level,
      f nal D stanceFunct on<K, T> d stFn,
      boolean  sUpdate
  ) {
    // M n queue
    f nal D stanced emQueue<K, T> cQueue = new D stanced emQueue<>(
         em,
        Collect ons.s ngletonL st(entryPo nt),
        true,
        d stFn
    );
    // Max Queue
    f nal D stanced emQueue<K, T> wQueue = cQueue.reverse();
    f nal Set<T> v s ed = new HashSet<>();
    float lo rBoundD stance = wQueue.peek().getD stance();
    v s ed.add(entryPo nt);

    wh le (cQueue.nonEmpty()) {
      f nal D stanced em<T> cand date = cQueue.peek();
       f (cand date.getD stance() > lo rBoundD stance) {
        break;
      }

      cQueue.dequeue();
      f nal L st<T> l st = getConnect onL stForRead(cand date.get em(), level);
      for (T nn : l st) {
         f (!v s ed.conta ns(nn)) {
          v s ed.add(nn);
          f nal float d stance = d stFn.d stance( em, nn);
           f (wQueue.s ze() < ef || d stance < wQueue.peek().getD stance()) {
            cQueue.enqueue(nn, d stance);

             f ( sUpdate &&  em.equals(nn)) {
              cont nue;
            }

            wQueue.enqueue(nn, d stance);
             f (wQueue.s ze() > ef) {
              wQueue.dequeue();
            }

            lo rBoundD stance = wQueue.peek().getD stance();
          }
        }
      }
    }

    return wQueue;
  }

  /**
   * Ser al ze hnsw  ndex
   */
  publ c vo d toD rectory( ndexOutputF le  ndexOutputF le,  nject on<T, byte[]>  nject on)
    throws  OExcept on, TExcept on {
  f nal  nt totalGraphEntr es = Hnsw ndex OUt l.saveHnswGraphEntr es(
      graph,
       ndexOutputF le.createF le(GRAPH_F LE_NAME).getOutputStream(),
       nject on);

  Hnsw ndex OUt l.save tadata(
      graph ta.get(),
      efConstruct on,
      maxM,
      totalGraphEntr es,
       nject on,
       ndexOutputF le.createF le(METADATA_F LE_NAME).getOutputStream());
}

  /**
   * Load hnsw  ndex
   */
  publ c stat c <T, Q> Hnsw ndex<T, Q> loadHnsw ndex(
      D stanceFunct on<T, T> d stFn ndex,
      D stanceFunct on<Q, T> d stFnQuery,
      AbstractF le d rectory,
       nject on<T, byte[]>  nject on,
      RandomProv der randomProv der) throws  OExcept on, TExcept on {
    f nal AbstractF le graphF le = d rectory.getCh ld(GRAPH_F LE_NAME);
    f nal AbstractF le  tadataF le = d rectory.getCh ld(METADATA_F LE_NAME);
    f nal Hnsw nternal ndex tadata  tadata = Hnsw ndex OUt l.load tadata( tadataF le);
    f nal Map<HnswNode<T>,  mmutableL st<T>> graph =
        Hnsw ndex OUt l.loadHnswGraph(graphF le,  nject on,  tadata.numEle nts);
    f nal ByteBuffer entryPo ntBB =  tadata.entryPo nt;
    f nal Hnsw ta<T> graph ta = new Hnsw ta<>(
         tadata.maxLevel,
        entryPo ntBB == null ? Opt onal.empty()
            : Opt onal.of( nject on. nvert(ArrayByteBufferCodec.decode(entryPo ntBB)).get())
    );
    return new Hnsw ndex<>(
        d stFn ndex,
        d stFnQuery,
         tadata.efConstruct on,
         tadata.maxM,
         tadata.numEle nts,
        graph ta,
        graph,
        randomProv der
    );
  }

  pr vate L st<T> getConnect onL stForRead(T node,  nt level) {
    f nal Lock curLock = locks.compute fAbsent(node, lockProv der).readLock();
    curLock.lock();
    f nal L st<T> l st;
    try {
      l st = graph
          .getOrDefault(HnswNode.from(level, node),  mmutableL st.of());
    } f nally {
      curLock.unlock();
    }

    return l st;
  }

  @V s bleForTest ng
  Atom cReference<Hnsw ta<T>> getGraph ta() {
    return graph ta;
  }

  @V s bleForTest ng
  Map<T, ReadWr eLock> getLocks() {
    return locks;
  }

  @V s bleForTest ng
  Map<HnswNode<T>,  mmutableL st<T>> getGraph() {
    return graph;
  }

  publ c  nterface RandomProv der {
    /**
     * RandomProv der  nterface made publ c for scala 2.12 compat
     */
    Random get();
  }
}
