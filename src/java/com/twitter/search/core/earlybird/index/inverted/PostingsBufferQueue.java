package com.tw ter.search.core.earlyb rd. ndex. nverted;

 mport java.ut l.NoSuchEle ntExcept on;

 mport com.google.common.annotat ons.V s bleForTest ng;

/**
 * A post ng buffer used by {@l nk H ghDFPacked ntsPost ngL sts} wh le copy ng over post ng l st.
 */
f nal class Post ngsBufferQueue {
  /**
   * Mask used to convert an  nt to a long.   cannot just cast because do ng so  w ll f ll  n t 
   * h g r 32 b s w h t  s gn b , but   need t  h g r 32 b s to be 0  nstead.
   */
  stat c f nal long LONG_MASK = (1L << 32) - 1;

  /**
   * A c rcular F FO long queue used  nternally to store post ng.
   * @see #post ngsQueue
   */
  @V s bleForTest ng
  stat c f nal class Queue {
    pr vate f nal long[] queue;
    pr vate  nt  ad = 0;
    pr vate  nt ta l = 0;
    pr vate  nt s ze;

    Queue( nt maxS ze) {
      t .queue = new long[maxS ze < 2 ? 2 : maxS ze];
    }

    boolean  sEmpty() {
      return s ze() == 0;
    }

    boolean  sFull() {
      return s ze() == queue.length;
    }

    vo d offer(long value) {
       f (s ze() == queue.length) {
        throw new  llegalStateExcept on("Queue  s full");
      }
      queue[ta l] = value;
      ta l = (ta l + 1) % queue.length;
      s ze++;
    }

    long poll() {
       f ( sEmpty()) {
        throw new NoSuchEle ntExcept on("Queue  s empty.");
      }
      long value = queue[ ad];
       ad = ( ad + 1) % queue.length;
      s ze--;
      return value;
    }

     nt s ze() {
      return s ze;
    }
  }

  /**
   *  nternal post ng queue.
   */
  pr vate f nal Queue post ngsQueue;

  /**
   * Constructor w h max s ze.
   *
   * @param maxS ze max s ze of t  buffer.
   */
  Post ngsBufferQueue( nt maxS ze) {
    t .post ngsQueue = new Queue(maxS ze);
  }

  /**
   * C ck  f t  buffer  s empty.
   *
   * @return  f t  buffer  s empty
   */
  boolean  sEmpty() {
    return post ngsQueue. sEmpty();
  }

  /**
   * C ck  f t  buffer  s full.
   *
   * @return  f t  buffer  s full
   */
  boolean  sFull() {
    return post ngsQueue. sFull();
  }

  /**
   * Get t  current s ze of t  buffer.
   *
   * @return Current s ze of t  buffer
   */
   nt s ze() {
    return post ngsQueue.s ze();
  }

  /**
   * Store a post ng w h doc D and a second value that could be freq, pos  on, or any add  onal
   *  nfo. T   thod w ll encode t  offered doc  D and second value w h
   * {@l nk #encodePost ng( nt,  nt)}.
   *
   * @param doc D doc  D of t  post ng
   * @param secondValue an add  onal value of t  post ng
   */
  vo d offer( nt doc D,  nt secondValue) {
    post ngsQueue.offer(encodePost ng(doc D, secondValue));
  }

  /**
   * Remove and return t  earl est  nserted post ng, t   s a F FO queue.
   *
   * @return t  earl est  nserted post ng.
   */
  long poll() {
    return post ngsQueue.poll();
  }

  /**
   * Encode a doc  D and a second value, both are  nts,  nto a long. T  h g r 32 b s store t 
   * doc  D and lo r 32 b s store t  second value.
   *
   * @param doc D an  nt spec fy ng doc  D of t  post ng
   * @param secondValue an  nt spec fy ng t  second value of t  post ng
   * @return an encoded long represent t  post ng
   */
  pr vate stat c long encodePost ng( nt doc D,  nt secondValue) {
    return ((LONG_MASK & doc D) << 32) | (LONG_MASK & secondValue);
  }

  /**
   * Decode doc  D from t  g ven post ng.
   * @param post ng a g ven post ng encoded w h {@l nk #encodePost ng( nt,  nt)}
   * @return t  doc  D of t  g ven post ng.
   */
  stat c  nt getDoc D(long post ng) {
    return ( nt) (post ng >> 32);
  }

  /**
   * Decode t  second value from t  g ven post ng.
   * @param post ng a g ven post ng encoded w h {@l nk #encodePost ng( nt,  nt)}
   * @return t  second value of t  g ven post ng.
   */
  stat c  nt getSecondValue(long post ng) {
    return ( nt) post ng;
  }
}
