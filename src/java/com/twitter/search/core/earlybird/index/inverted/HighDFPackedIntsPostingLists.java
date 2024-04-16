package com.tw ter.search.core.earlyb rd. ndex. nverted;

 mport java. o. OExcept on;

 mport javax.annotat on.Nullable;

 mport org.apac .lucene. ndex.Post ngsEnum;
 mport org.apac .lucene.search.Doc dSet erator;

 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common.ut l. o.flushable.DataDeser al zer;
 mport com.tw ter.search.common.ut l. o.flushable.DataSer al zer;
 mport com.tw ter.search.common.ut l. o.flushable.Flush nfo;
 mport com.tw ter.search.common.ut l. o.flushable.Flushable;

/**
 * An opt m zed post ng l sts  mple ntat on stor ng doc deltas, doc freqs, and pos  ons as packed
 *  nts  n a 64  nts sl ce backed by {@l nk  ntBlockPool}.
 *
 * T re are three  nner data structures used to store values used by a post ng l sts  nstance:
 *
 * - Sk p l sts, used for fast {@l nk Post ngsEnum#advance( nt)}, are stored  n {@l nk #sk pL sts}
 *    nt block pool.
 * - Doc deltas and freqs are stored  n {@l nk #deltaFreqL sts}  nt block pool.
 * - Pos  ons are stored  n {@l nk #pos  onL sts}  nt block pool.
 *
 * For deta l la t and conf gurat on, please refer to t  Javadoc of {@l nk #sk pL sts},
 * {@l nk #deltaFreqL sts} and {@l nk #pos  onL sts}.
 *
 * <b>T   mple ntat on des gned for post ng l sts w h a LARGE number of post ngs.</b>
 *
 * < >Acknowledge nt</ >: t  concepts of sl ce based packed  nts encod ng/decod ng  s borro d
 *                         from {@code H ghDFCompressedPost ngl sts}, wh ch w ll be deprecated due
 *                         to not support ng pos  ons that are greater than 255.
 */
publ c class H ghDFPacked ntsPost ngL sts extends Opt m zedPost ngL sts {
  /**
   * A counter used to track w n pos  ons enum  s requ red and a post ng l sts  nstance  s set
   * to om  pos  ons.
   *
   * @see #post ngs( nt,  nt,  nt)
   */
  pr vate stat c f nal SearchCounter GETT NG_POS T ONS_W TH_OM T_POS T ONS =
      SearchCounter.export(
          "h gh_df_packed_ nts_post ng_l st_gett ng_pos  ons_w h_om _pos  ons");

  /**
   *  nformat on related to s ze of a sl ce.
   */
  stat c f nal  nt SL CE_S ZE_B T = 6;
  stat c f nal  nt SL CE_S ZE = 1 << SL CE_S ZE_B T;                 //   64  nts per block
  stat c f nal  nt NUM_B TS_PER_SL CE = SL CE_S ZE *  nteger.S ZE;   // 2048 b s per block

  /**
   * A sk p l st has ONE sk p l st  ader that conta ns 5  nts (4  nts  f pos  ons are om ted):
   * - 1st  nt: number of sk p entr es  n t  sk p l st.
   * - 2nd  nt: largest doc  D  n t  post ng l st.
   * - 3rd  nt: number of docs  n t  post ng l st.
   * - 4th  nt: po nter to t  start of t  delta-freq l st of t  post ng l st.
   * - 5th  nt: (OPT ONAL) po nter to t  start of t  pos  on l st of t  post ng l st.
   */
  stat c f nal  nt SK PL ST_HEADER_S ZE = 5;
  stat c f nal  nt SK PL ST_HEADER_S ZE_W THOUT_POS T ONS = SK PL ST_HEADER_S ZE - 1;

  /**
   * A sk p l st has MANY sk p entr es. Each sk p entry  s for one sl ce  n delta-freq l st.
   * T re are 3  nts  n every sk p entry (2  nts  f pos  ons are om ted):
   * - 1st  nt: last doc  D  n prev ous sl ce (0 for t  f rst sl ce), t   s ma nly used dur ng
   *            sk pp ng because deltas, not absolute doc  Ds, are stored  n a sl ce.
   * - 2nd  nt: encoded  tadata of t  correspond ng delta-freq sl ce. T re are 4 p ece of
   *             nformat on from t  LOWEST b s to H GHEST b s of t   nt:
   *            11 b s: number of docs (delta-freq pa rs)  n t  sl ce.
   *             5 b s: number of b s used to encode each freq.
   *             5 b s: number of b s used to encode each delta.
   *            11 b s: POS T ON SL CE OFFSET: an  ndex of number of pos  ons; t   s w re t 
   *                     f rst pos  on of t  f rst doc ( n t  delta-freq sl ce)  s  n t 
   *                     pos  on sl ce. T  pos  on sl ce  s  dent f ed by t  3rd  nt below.
   *                     T se two p ece  nformat on un quely  dent f ed t  locat on of t  start
   *                     pos  on of t  delta-freq sl ce. T  value  s always 0  f pos  on  s
   *                     om ted.
   * - 3rd  nt: (OPT ONAL) POS T ON SL CE  NDEX: an  ndex of of number of sl ces; t  value
   *             dent f es t  sl ce  n wh ch t  f rst pos  on of t  f rst doc ( n t 
   *            delta-freq sl ce) ex sts. T  exact locat on  ns de t  pos  on sl ce  s  dent f ed
   *            by POS T ON SL CE OFFSET that  s stored  n t  2nd  nt above.
   *            Not ce: t   s not t  absolute address  n t  block pool, but  nstead a relat ve
   *            offset ( n number of sl ces) on top of t  term's f rst pos  on sl ce.
   *            T  value DOES NOT EX ST  f pos  on  s om ted.
   */
  stat c f nal  nt SK PL ST_ENTRY_S ZE = 3;
  stat c f nal  nt SK PL ST_ENTRY_S ZE_W THOUT_POS T ONS = SK PL ST_ENTRY_S ZE - 1;

  /**
   * Sh fts and masks used to encode/decode  tadata from t  2nd  nt of a sk p l st entry.
   * @see #SK PL ST_ENTRY_S ZE
   * @see #encodeSk pL stEntry tadata( nt,  nt,  nt,  nt)
   * @see #getNumB sForDelta( nt)
   * @see #getNumB sForFreq( nt)
   * @see #getNumDocs nSl ce( nt)
   * @see #getPos  onOffset nSl ce( nt)
   */
  stat c f nal  nt SK PL ST_ENTRY_POS T ON_OFFSET_SH FT = 21;
  stat c f nal  nt SK PL ST_ENTRY_NUM_B TS_DELTA_SH FT = 16;
  stat c f nal  nt SK PL ST_ENTRY_NUM_B TS_FREQ_SH FT = 11;
  stat c f nal  nt SK PL ST_ENTRY_POS T ON_OFFSET_MASK = (1 << 11) - 1;
  stat c f nal  nt SK PL ST_ENTRY_NUM_B TS_DELTA_MASK = (1 << 5) - 1;
  stat c f nal  nt SK PL ST_ENTRY_NUM_B TS_FREQ_MASK = (1 << 5) - 1;
  stat c f nal  nt SK PL ST_ENTRY_NUM_DOCS_MASK = (1 << 11) - 1;

  /**
   * Each pos  on sl ce has a  ader that  s t  1st  nt  n t  pos  on sl ce. From LOWEST b s
   * to H GHEST b s, t re are 2 p eces of  nformat on encoded  n t  s ngle  nt:
   * 11 b s: number of pos  ons  n t  sl ce.
   *  5 b s: number of b s used to encode each pos  on.
   */
  stat c f nal  nt POS T ON_SL CE_HEADER_S ZE = 1;

  /**
   *  nformat on related to s ze of a pos  on sl ce. T  actual s ze  s t  sa  as
   * {@l nk #SL CE_S ZE}, but t re  s 1  nt used for pos  on sl ce  ader.
   */
  stat c f nal  nt POS T ON_SL CE_S ZE_W THOUT_HEADER = SL CE_S ZE - POS T ON_SL CE_HEADER_S ZE;
  stat c f nal  nt POS T ON_SL CE_NUM_B TS_W THOUT_HEADER =
      POS T ON_SL CE_S ZE_W THOUT_HEADER *  nteger.S ZE;

  /**
   * Sh fts and masks used to encode/decode  tadata from t  pos  on sl ce  ader.
   * @see #POS T ON_SL CE_HEADER_S ZE
   * @see #encodePos  onEntry ader( nt,  nt)
   * @see #getNumPos  ons nSl ce( nt)
   * @see #getNumB sForPos  on( nt)
   */
  stat c f nal  nt POS T ON_SL CE_HEADER_B TS_POS T ON_SH FT = 11;
  stat c f nal  nt POS T ON_SL CE_HEADER_B TS_POS T ON_MASK = (1 << 5) - 1;
  stat c f nal  nt POS T ON_SL CE_HEADER_NUM_POS T ONS_MASK = (1 << 11) - 1;

  /**
   * Stores sk p l st for each post ng l st.
   *
   * A sk p l st cons sts of ONE sk p l st  ader and MANY sk p l st entr es, and each sk p entry
   * corresponds to one delta-freq sl ce. Also, unl ke {@l nk #deltaFreqL sts} and
   * {@l nk #pos  onL sts}, values  n sk p l sts  nt pool are NOT stored  n un  of sl ces.
   *
   * Example:
   * H: sk p l st  ader  nt
   * E: sk p l st entry  nt
   * ':  nt boundary
   * |:  ader/entry boundary (also a boundary of  nt)
   *
   *  <----- sk p l st A -----> <- sk p l st B ->
   * |H'H'H'H'H|E'E|E'E|E'E|E'E|H'H'H'H'H|E'E|E'E|
   */
  pr vate f nal  ntBlockPool sk pL sts;

  /**
   * Stores delta-freq l st for each post ng l st.
   *
   * A delta-freq l st cons sts of MANY 64- nt sl ces, and delta-freq pa rs are stored compactly
   * w h a f xed number of b s w h n a s ngle sl ce. Each sl ce has a correspond ng sk p l st
   * entry  n {@l nk #sk pL sts} stor ng  tadata about t  sl ce.
   *
   * Example:
   * |: sl ce boundary
   *
   *  <----------------- delta-freq l st A -----------------> <--- delta-freq l st B --->
   * |64  nts sl ce|64  nts sl ce|64  nts sl ce|64  nts sl ce|64  nts sl ce|64  nts sl ce|
   */
  pr vate f nal  ntBlockPool deltaFreqL sts;

  /**
   * Stores pos  on l st for each post ng l st.
   *
   * A pos  on l st cons sts of MANY 64  nts sl ces, and pos  ons are stored compactly w h a
   * f xed number of b s w h n a s ngle sl ce. T  f rst  nt  n each sl ce  s used as a  ader to
   * store t   tadata about t  pos  on sl ce.
   *
   * Example:
   * H: pos  on  ader  nt
   * ':  nt boundary
   * |: sl ce boundary
   *
   *  <--------------- pos  on l st A ---------------> <---------- pos  on l st B ---------->
   * |H'63  nts|H'63  nts|H'63  nts|H'63  nts|H'63  nts|H'63  nts|H'63  nts|H'63  nts|H'63  nts|
   */
  pr vate f nal  ntBlockPool pos  onL sts;

  /**
   * W t r pos  ons are om ted  n t  opt m zed post ng l sts.
   */
  pr vate f nal boolean om Pos  ons;

  /**
   * Sk p l st  ader and entry s ze for t  post ng l sts, could be d fferent depends on w t r
   * pos  on  s om ted or not.
   *
   * @see #SK PL ST_HEADER_S ZE
   * @see #SK PL ST_HEADER_S ZE_W THOUT_POS T ONS
   * @see #SK PL ST_ENTRY_S ZE
   * @see #SK PL ST_ENTRY_S ZE_W THOUT_POS T ONS
   */
  pr vate f nal  nt sk pL st aderS ze;
  pr vate f nal  nt sk pl stEntryS ze;

  /**
   * Buffer used  n {@l nk #copyPost ngL st(Post ngsEnum,  nt)}
   * to queue up values needed for a sl ce.
   * Loaded post ng l sts have t m set as null.
   */
  pr vate f nal Post ngsBufferQueue docFreqQueue;
  pr vate f nal Post ngsBufferQueue pos  onQueue;

  /**
   * Packed  nts wr er used to wr e  nto delta-freq  nt pool and pos  on  nt pool.
   * Loaded post ng l sts have t m set as null.
   */
  pr vate f nal  ntBlockPoolPackedLongsWr er deltaFreqL stsWr er;
  pr vate f nal  ntBlockPoolPackedLongsWr er pos  onL stsWr er;

  /**
   * Default constructor.
   *
   * @param om Pos  ons w t r pos  ons w ll be om ted  n t se post ng l sts.
   */
  publ c H ghDFPacked ntsPost ngL sts(boolean om Pos  ons) {
    t (
        new  ntBlockPool("h gh_df_packed_ nts_sk p_l sts"),
        new  ntBlockPool("h gh_df_packed_ nts_delta_freq_l sts"),
        new  ntBlockPool("h gh_df_packed_ nts_pos  on_l sts"),
        om Pos  ons,
        new Post ngsBufferQueue(NUM_B TS_PER_SL CE),
        new Post ngsBufferQueue(POS T ON_SL CE_NUM_B TS_W THOUT_HEADER));
  }

  /**
   * Constructors used by loader.
   *
   * @param sk pL sts loaded  nt block pool represents sk p l sts
   * @param deltaFreqL sts loaded  nt block pool represents delta-freq l sts
   * @param pos  onL sts loaded  nt block pool represents pos  on l sts
   * @param om Pos  ons w t r pos  ons w ll be om ted  n t se post ng l sts
   * @param docFreqQueue buffer used to queue up values used for a doc freq sl ce, null  f loaded
   * @param pos  onQueue buffer used to queue up values used for a pos  on sl ce, null  f loaded
   * @see FlushHandler#doLoad(Flush nfo, DataDeser al zer)
   */
  pr vate H ghDFPacked ntsPost ngL sts(
       ntBlockPool sk pL sts,
       ntBlockPool deltaFreqL sts,
       ntBlockPool pos  onL sts,
      boolean om Pos  ons,
      @Nullable Post ngsBufferQueue docFreqQueue,
      @Nullable Post ngsBufferQueue pos  onQueue) {
    t .sk pL sts = sk pL sts;
    t .deltaFreqL sts = deltaFreqL sts;
    t .pos  onL sts = pos  onL sts;
    t .om Pos  ons = om Pos  ons;

    t .docFreqQueue = docFreqQueue;
    t .pos  onQueue = pos  onQueue;

    // docFreqQueue  s null  f t  post ngL sts  s loaded,
    //   don't need to create wr er at that case.
     f (docFreqQueue == null) {
      assert pos  onQueue == null;
      t .deltaFreqL stsWr er = null;
      t .pos  onL stsWr er = null;
    } else {
      t .deltaFreqL stsWr er = new  ntBlockPoolPackedLongsWr er(deltaFreqL sts);
      t .pos  onL stsWr er = new  ntBlockPoolPackedLongsWr er(pos  onL sts);
    }

     f (om Pos  ons) {
      sk pL st aderS ze = SK PL ST_HEADER_S ZE_W THOUT_POS T ONS;
      sk pl stEntryS ze = SK PL ST_ENTRY_S ZE_W THOUT_POS T ONS;
    } else {
      sk pL st aderS ze = SK PL ST_HEADER_S ZE;
      sk pl stEntryS ze = SK PL ST_ENTRY_S ZE;
    }
  }

  /**
   * A s mple wrapper around assorted states used w n cop ng pos  ons  n a post ng enum.
   * @see #copyPost ngL st(Post ngsEnum,  nt)
   */
  pr vate stat c class Pos  onsState {
    /** Max pos  on has been seen for t  current pos  on sl ce. */
    pr vate  nt maxPos  on = 0;

    /** B s needed to encode/decode pos  ons  n t  current pos  on sl ce. */
    pr vate  nt b sNeededForPos  on = 0;

    /** Total number of pos  on sl ces created for current post ng l st. */
    pr vate  nt numPos  onsSl ces = 0;

    /**
     * W never a sl ce of doc/freq pa rs  s wr ten, t  w ll po nt to t  f rst pos  on
     * assoc ated w h t  f rst doc  n t  doc/freq sl ce.
     */
    pr vate  nt currentPos  onsSl ce ndex = 0;
    pr vate  nt currentPos  onsSl ceOffset = 0;

    /**
     * W never a new docu nt  s processed, t  po nts to t  f rst pos  on for t  doc.
     * T   s used  f t  doc ends up be ng chosen as t  f rst doc  n a doc/freq sl ce.
     */
    pr vate  nt nextPos  onsSl ce ndex = 0;
    pr vate  nt nextPos  onsSl ceOffset = 0;
  }

  /**
   * Cop es post ngs  n t  g ven post ngs enum  nto t  post ng l sts  nstance.
   *
   * @param post ngsEnum enu rator of t  post ng l st that needs to be cop ed
   * @param numPost ngs number of post ngs  n t  post ng l st that needs to be cop ed
   * @return po nter to t  cop ed post ng l st  n t  post ng l sts  nstance
   */
  @Overr de
  publ c  nt copyPost ngL st(Post ngsEnum post ngsEnum,  nt numPost ngs) throws  OExcept on {
    assert docFreqQueue. sEmpty() : "each new post ng l st should start w h an empty queue";
    assert pos  onQueue. sEmpty() : "each new post ng l st should start w h an empty queue";

    f nal  nt sk pL stPo nter = sk pL sts.length();
    f nal  nt deltaFreqL stPo nter = deltaFreqL sts.length();
    f nal  nt pos  onL stPo nter = pos  onL sts.length();
    assert  sSl ceStart(deltaFreqL stPo nter) : "each new post ng l st should start at a new sl ce";
    assert  sSl ceStart(pos  onL stPo nter) : "each new post ng l st should start at a new sl ce";

    // Make room for sk p l st HEADER.
    for ( nt   = 0;   < sk pL st aderS ze;  ++) {
      sk pL sts.add(-1);
    }

     nt doc;
     nt prevDoc = 0;
     nt prevWr tenDoc = 0;

     nt maxDelta = 0;
     nt maxFreq = 0;

     nt b sNeededForDelta = 0;
     nt b sNeededForFreq = 0;

    // Keep track ng pos  ons related  nfo for t  post ng l st.
    Pos  onsState pos  onsState = new Pos  onsState();

     nt numDocs = 0;
     nt numDeltaFreqSl ces = 0;
    wh le ((doc = post ngsEnum.nextDoc()) != Doc dSet erator.NO_MORE_DOCS) {
      numDocs++;

       nt delta = doc - prevDoc;
      assert delta <= MAX_DOC_ D;

       nt newB sForDelta = b sNeededForDelta;
       f (delta > maxDelta) {
        maxDelta = delta;
        newB sForDelta = log(maxDelta, 2);
        assert newB sForDelta <= MAX_DOC_ D_B T;
      }

      /**
       * Opt m zat on: store freq - 1 s nce a freq must be pos  ve. Save b s and  mprove decod ng
       * speed. At read s de, t  read frequency w ll plus 1.
       * @see H ghDFPacked ntsDocsEnum#loadNextPost ng()
       */
       nt freq = post ngsEnum.freq() - 1;
      assert freq >= 0;

       nt newB sForFreq = b sNeededForFreq;
       f (freq > maxFreq) {
        maxFreq = freq;
        newB sForFreq = log(maxFreq, 2);
        assert newB sForFreq <= MAX_FREQ_B T;
      }

      // Wr e pos  ons for t  doc  f not om  pos  ons.
       f (!om Pos  ons) {
        wr ePos  onsForDoc(post ngsEnum, pos  onsState);
      }

       f ((newB sForDelta + newB sForFreq) * (docFreqQueue.s ze() + 1) > NUM_B TS_PER_SL CE) {
        //T  latest doc does not f   nto t  sl ce.
        assert (b sNeededForDelta + b sNeededForFreq) * docFreqQueue.s ze()
            <= NUM_B TS_PER_SL CE;

        prevWr tenDoc = wr eDeltaFreqSl ce(
            b sNeededForDelta,
            b sNeededForFreq,
            pos  onsState,
            prevWr tenDoc);
        numDeltaFreqSl ces++;

        maxDelta = delta;
        maxFreq = freq;
        b sNeededForDelta = log(maxDelta, 2);
        b sNeededForFreq = log(maxFreq, 2);
      } else {
        b sNeededForDelta = newB sForDelta;
        b sNeededForFreq = newB sForFreq;
      }

      docFreqQueue.offer(doc, freq);

      prevDoc = doc;
    }

    // So  pos  ons may be left  n t  buffer queue.
     f (!pos  onQueue. sEmpty()) {
      wr ePos  onSl ce(pos  onsState.b sNeededForPos  on);
    }

    // So  docs may be left  n t  buffer queue.
     f (!docFreqQueue. sEmpty()) {
      wr eDeltaFreqSl ce(
          b sNeededForDelta,
          b sNeededForFreq,
          pos  onsState,
          prevWr tenDoc);
      numDeltaFreqSl ces++;
    }

    // Wr e sk p l st  ader.
     nt sk pL st aderPo nter = sk pL stPo nter;
    f nal  nt numSk pL stEntr es =
        (sk pL sts.length() - (sk pL stPo nter + sk pL st aderS ze)) / sk pl stEntryS ze;
    assert numSk pL stEntr es == numDeltaFreqSl ces
        : "number of delta freq sl ces should be t  sa  as number of sk p l st entr es";
    sk pL sts.set(sk pL st aderPo nter++, numSk pL stEntr es);
    sk pL sts.set(sk pL st aderPo nter++, prevDoc);
    sk pL sts.set(sk pL st aderPo nter++, numDocs);
    sk pL sts.set(sk pL st aderPo nter++, deltaFreqL stPo nter);
     f (!om Pos  ons) {
      sk pL sts.set(sk pL st aderPo nter, pos  onL stPo nter);
    }

    return sk pL stPo nter;
  }

  /**
   * Wr e pos  ons for current doc  nto {@l nk #pos  onL sts}.
   *
   * @param post ngsEnum post ngs enu rator conta n ng t  pos  ons need to be wr ten
   * @param pos  onsState so  states about {@l nk #pos  onL sts} and {@l nk #pos  onQueue}
   * @see #copyPost ngL st(Post ngsEnum,  nt)
   */
  pr vate vo d wr ePos  onsForDoc(
      Post ngsEnum post ngsEnum,
      Pos  onsState pos  onsState) throws  OExcept on {
    assert !om Pos  ons : "t   thod should not be called  f pos  ons are om ted";

    for ( nt   = 0;   < post ngsEnum.freq();  ++) {
       nt pos = post ngsEnum.nextPos  on();

       nt newB sForPos  on = pos  onsState.b sNeededForPos  on;
       f (pos > pos  onsState.maxPos  on) {
        pos  onsState.maxPos  on = pos;
        newB sForPos  on = log(pos  onsState.maxPos  on, 2);
        assert newB sForPos  on <= MAX_POS T ON_B T;
      }

       f (newB sForPos  on * (pos  onQueue.s ze() + 1)
          > POS T ON_SL CE_NUM_B TS_W THOUT_HEADER
          || pos  onQueue. sFull()) {
        assert pos  onsState.b sNeededForPos  on * pos  onQueue.s ze()
            <= POS T ON_SL CE_NUM_B TS_W THOUT_HEADER;

        wr ePos  onSl ce(pos  onsState.b sNeededForPos  on);
        pos  onsState.numPos  onsSl ces++;

        pos  onsState.maxPos  on = pos;
        pos  onsState.b sNeededForPos  on = log(pos  onsState.maxPos  on, 2);
      } else {
        pos  onsState.b sNeededForPos  on = newB sForPos  on;
      }

      // Update f rst pos  on po nter  f t  pos  on  s t  f rst pos  on of a doc
       f (  == 0) {
        pos  onsState.nextPos  onsSl ce ndex = pos  onsState.numPos  onsSl ces;
        pos  onsState.nextPos  onsSl ceOffset = pos  onQueue.s ze();
      }

      // Stores a dum  doc -1 s nce doc  s unused  n pos  on l st.
      pos  onQueue.offer(-1, pos);
    }
  }

  /**
   * Wr e out all t  buffered pos  ons  n {@l nk #pos  onQueue}  nto a pos  on sl ce.
   *
   * @param b sNeededForPos  on number of b s used for each pos  on  n t  pos  on sl ce
   */
  pr vate vo d wr ePos  onSl ce(f nal  nt b sNeededForPos  on) {
    assert !om Pos  ons;
    assert 0 <= b sNeededForPos  on && b sNeededForPos  on <= MAX_POS T ON_B T;

    f nal  nt lengthBefore = pos  onL sts.length();
    assert  sSl ceStart(lengthBefore);

    // F rst  nt  n t  sl ce stores number of b s needed for pos  on
    // and number of pos  ons  n t  sl ce..
    pos  onL sts.add(encodePos  onEntry ader(b sNeededForPos  on, pos  onQueue.s ze()));

    pos  onL stsWr er.jumpTo nt(pos  onL sts.length(), b sNeededForPos  on);
    wh le (!pos  onQueue. sEmpty()) {
       nt pos = Post ngsBufferQueue.getSecondValue(pos  onQueue.poll());
      assert log(pos, 2) <= b sNeededForPos  on;

      pos  onL stsWr er.wr ePacked nt(pos);
    }

    // F ll up t  sl ce  n case    s only part ally f lled.
    wh le (pos  onL sts.length() < lengthBefore + SL CE_S ZE) {
      pos  onL sts.add(0);
    }

    assert pos  onL sts.length() - lengthBefore == SL CE_S ZE;
  }

  /**
   * Wr e out all t  buffered docs and frequenc es  n {@l nk #docFreqQueue}  nto a delta-freq
   * sl ce and update t  sk p l st entry of t  sl ce.
   *
   * @param b sNeededForDelta number of b s used for each delta  n t  delta-freq sl ce
   * @param b sNeededForFreq number of b s used for each freq  n t  delta-freq sl ce
   * @param pos  onsState so  states about {@l nk #pos  onL sts} and {@l nk #pos  onQueue}
   * @param prevWr tenDoc last doc wr ten  n prev ous sl ce
   * @return last doc wr ten  n t  sl ce
   */
  pr vate  nt wr eDeltaFreqSl ce(
      f nal  nt b sNeededForDelta,
      f nal  nt b sNeededForFreq,
      f nal Pos  onsState pos  onsState,
      f nal  nt prevWr tenDoc) {
    assert 0 <= b sNeededForDelta && b sNeededForDelta <= MAX_DOC_ D_B T;
    assert 0 <= b sNeededForFreq && b sNeededForFreq <= MAX_FREQ_B T;

    f nal  nt lengthBefore = deltaFreqL sts.length();
    assert  sSl ceStart(lengthBefore);

    wr eSk pL stEntry(prevWr tenDoc, b sNeededForDelta, b sNeededForFreq, pos  onsState);

    // Keep track of prev ous doc D so that   compute t  doc D deltas.
     nt prevDoc = prevWr tenDoc;

    // A <delta|freq> pa r  s stored as a packed value.
    f nal  nt b sPerPackedValue = b sNeededForDelta + b sNeededForFreq;
    deltaFreqL stsWr er.jumpTo nt(deltaFreqL sts.length(), b sPerPackedValue);
    wh le (!docFreqQueue. sEmpty()) {
      long value = docFreqQueue.poll();
       nt doc = Post ngsBufferQueue.getDoc D(value);
       nt delta = doc - prevDoc;
      assert log(delta, 2) <= b sNeededForDelta;

       nt freq = Post ngsBufferQueue.getSecondValue(value);
      assert log(freq, 2) <= b sNeededForFreq;

      // Cast t  delta to long before left sh ft to avo d overflow.
      f nal long deltaFreqPa r = (((long) delta) << b sNeededForFreq) + freq;
      deltaFreqL stsWr er.wr ePackedLong(deltaFreqPa r);
      prevDoc = doc;
    }

    // F ll up t  sl ce  n case    s only part ally f lled.
    wh le (deltaFreqL sts.length() <  lengthBefore + SL CE_S ZE) {
      deltaFreqL sts.add(0);
    }

    pos  onsState.currentPos  onsSl ce ndex = pos  onsState.nextPos  onsSl ce ndex;
    pos  onsState.currentPos  onsSl ceOffset = pos  onsState.nextPos  onsSl ceOffset;

    assert deltaFreqL sts.length() - lengthBefore == SL CE_S ZE;
    return prevDoc;
  }

  /**
   * Wr e t  sk p l st entry for a delta-freq sl ce.
   *
   * @param prevWr tenDoc last doc wr ten  n prev ous sl ce
   * @param b sNeededForDelta number of b s used for each delta  n t  delta-freq sl ce
   * @param b sNeededForFreq number of b s used for each freq  n t  delta-freq sl ce
   * @param pos  onsState so  states about {@l nk #pos  onL sts} and {@l nk #pos  onQueue}
   * @see #wr eDeltaFreqSl ce( nt,  nt, Pos  onsState,  nt)
   * @see #SK PL ST_ENTRY_S ZE
   */
  pr vate vo d wr eSk pL stEntry(
       nt prevWr tenDoc,
       nt b sNeededForDelta,
       nt b sNeededForFreq,
      Pos  onsState pos  onsState) {
    // 1st  nt: last wr ten doc  D  n prev ous sl ce
    sk pL sts.add(prevWr tenDoc);

    // 2nd  nt: encoded  tadata
    sk pL sts.add(
        encodeSk pL stEntry tadata(
            pos  onsState.currentPos  onsSl ceOffset,
            b sNeededForDelta,
            b sNeededForFreq,
            docFreqQueue.s ze()));

    // 3rd  nt: opt onal, pos  on sl ce  ndex
     f (!om Pos  ons) {
      sk pL sts.add(pos  onsState.currentPos  onsSl ce ndex);
    }
  }

  /**
   * Create and return a docs enu rator or docs-pos  ons enu rator based on  nput flag.
   *
   * @see org.apac .lucene. ndex.Post ngsEnum
   */
  @Overr de
  publ c Earlyb rdPost ngsEnum post ngs(
       nt post ngL stPo nter,  nt numPost ngs,  nt flags) throws  OExcept on {
    // Pos  ons are om ted but pos  on enu rator are requr ed.
     f (om Pos  ons && Post ngsEnum.featureRequested(flags, Post ngsEnum.POS T ONS)) {
      GETT NG_POS T ONS_W TH_OM T_POS T ONS. ncre nt();
    }

     f (!om Pos  ons && Post ngsEnum.featureRequested(flags, Post ngsEnum.POS T ONS)) {
      return new H ghDFPacked ntsDocsAndPos  onsEnum(
          sk pL sts,
          deltaFreqL sts,
          pos  onL sts,
          post ngL stPo nter,
          numPost ngs,
          false);
    } else {
      return new H ghDFPacked ntsDocsEnum(
          sk pL sts,
          deltaFreqL sts,
          post ngL stPo nter,
          numPost ngs,
          om Pos  ons);
    }
  }

  /******************************************************
   * Sk p l st entry encoded data encod ng and decod ng *
   ******************************************************/

  /**
   * Encode a sk p l st entry  tadata, wh ch  s stored  n t  2nd  nt of t  sk p l st entry.
   *
   * @see #SK PL ST_ENTRY_S ZE
   */
  pr vate stat c  nt encodeSk pL stEntry tadata(
       nt pos  onOffset nSl ce,  nt numB sForDelta,  nt numB sForFreq,  nt numDocs nSl ce) {
    assert 0 <= pos  onOffset nSl ce
        && pos  onOffset nSl ce < POS T ON_SL CE_NUM_B TS_W THOUT_HEADER;
    assert 0 <= numB sForDelta && numB sForDelta <= MAX_DOC_ D_B T;
    assert 0 <= numB sForFreq && numB sForFreq <= MAX_FREQ_B T;
    assert 0 < numDocs nSl ce && numDocs nSl ce <= NUM_B TS_PER_SL CE;
    return (pos  onOffset nSl ce << SK PL ST_ENTRY_POS T ON_OFFSET_SH FT)
        + (numB sForDelta << SK PL ST_ENTRY_NUM_B TS_DELTA_SH FT)
        + (numB sForFreq << SK PL ST_ENTRY_NUM_B TS_FREQ_SH FT)
        // stores numDocs nSl ce - 1 to avo d over flow s nce numDocs nSl ce ranges  n [1, 2048]
        // and 11 b s are used to store number docs  n sl ce
        + (numDocs nSl ce - 1);
  }

  /**
   * Decode POS T ON_SL CE_OFFSET of t  delta-freq sl ce hav ng t  g ven sk p entry encoded data.
   *
   * @see #SK PL ST_ENTRY_S ZE
   */
  stat c  nt getPos  onOffset nSl ce( nt sk pL stEntryEncoded tadata) {
    return (sk pL stEntryEncoded tadata >>> SK PL ST_ENTRY_POS T ON_OFFSET_SH FT)
        & SK PL ST_ENTRY_POS T ON_OFFSET_MASK;
  }

  /**
   * Decode number of b s used for delta  n t  sl ce hav ng t  g ven sk p entry encoded data.
   *
   * @see #SK PL ST_ENTRY_S ZE
   */
  stat c  nt getNumB sForDelta( nt sk pL stEntryEncoded tadata) {
    return (sk pL stEntryEncoded tadata >>> SK PL ST_ENTRY_NUM_B TS_DELTA_SH FT)
        & SK PL ST_ENTRY_NUM_B TS_DELTA_MASK;
  }

  /**
   * Decode number of b s used for freqs  n t  sl ce hav ng t  g ven sk p entry encoded data.
   *
   * @see #SK PL ST_ENTRY_S ZE
   */
  stat c  nt getNumB sForFreq( nt sk pL stEntryEncoded tadata) {
    return (sk pL stEntryEncoded tadata >>> SK PL ST_ENTRY_NUM_B TS_FREQ_SH FT)
        & SK PL ST_ENTRY_NUM_B TS_FREQ_MASK;
  }

  /**
   * Decode number of delta-freq pa rs stored  n t  sl ce hav ng t  g ven sk p entry encoded data.
   *
   * @see #SK PL ST_ENTRY_S ZE
   */
  stat c  nt getNumDocs nSl ce( nt sk pL stEntryEncoded tadata) {
    /**
     * Add 1 to t  decode value s nce t  stored value  s subtracted by 1.
     * @see #encodeSk pL stEntry tadata( nt,  nt,  nt,  nt)
     */
    return (sk pL stEntryEncoded tadata & SK PL ST_ENTRY_NUM_DOCS_MASK) + 1;
  }

  /*****************************************************
   * Pos  on sl ce entry  ader encod ng and decod ng *
   *****************************************************/

  /**
   * Encode a pos  on sl ce entry  ader.
   *
   * @param numB sForPos  on number of b s used to encode pos  ons  n t  sl ce.
   * @param numPos  ons nSl ce number of pos  ons  n t  sl ce.
   * @return an  nt as t  encoded  ader.
   * @see #POS T ON_SL CE_HEADER_S ZE
   */
  pr vate stat c  nt encodePos  onEntry ader( nt numB sForPos  on,  nt numPos  ons nSl ce) {
    assert 0 <= numB sForPos  on && numB sForPos  on <= MAX_POS T ON_B T;
    assert 0 < numPos  ons nSl ce && numPos  ons nSl ce <= POS T ON_SL CE_NUM_B TS_W THOUT_HEADER;
    return (numB sForPos  on << POS T ON_SL CE_HEADER_B TS_POS T ON_SH FT) + numPos  ons nSl ce;
  }

  /**
   * Decode number of b s used for pos  on  n t  sl ce hav ng t  g ven  ader.
   *
   * @param pos  onEntry ader entry  ader w ll be decoded.
   * @see #POS T ON_SL CE_HEADER_S ZE
   */
  stat c  nt getNumB sForPos  on( nt pos  onEntry ader) {
    return (pos  onEntry ader >>> POS T ON_SL CE_HEADER_B TS_POS T ON_SH FT)
        & POS T ON_SL CE_HEADER_B TS_POS T ON_MASK;
  }

  /**
   * Decode number of pos  ons stored  n t  sl ce hav ng t  g ven  ader.
   *
   * @param pos  onEntry ader entry  ader w ll be decoded.
   * @see #POS T ON_SL CE_HEADER_S ZE
   */
  stat c  nt getNumPos  ons nSl ce( nt pos  onEntry ader) {
    return pos  onEntry ader & POS T ON_SL CE_HEADER_NUM_POS T ONS_MASK;
  }

  /******************
   *  lper  thods *
   ******************/

  /**
   * C ck  f g ven po nter  s po nt ng to t  sl ce start.
   *
   * @param po nter t   ndex w ll be c cked.
   */
  stat c boolean  sSl ceStart( nt po nter) {
    return po nter % H ghDFPacked ntsPost ngL sts.SL CE_S ZE == 0;
  }

  /**
   * Ce l of log of x  n t  g ven base.
   *
   * @return x == 0 ? 0 : Math.ce l(Math.log(x) / Math.log(base))
   */
  pr vate stat c  nt log( nt x,  nt base) {
    assert base >= 2;
     f (x == 0) {
      return 0;
    }
     nt ret = 1;
    long n = base; // needs to be a long to avo d overflow
    wh le (x >= n) {
      n *= base;
      ret++;
    }
    return ret;
  }

  /**********************
   * For flush and load *
   **********************/

  @SuppressWarn ngs("unc cked")
  @Overr de
  publ c FlushHandler getFlushHandler() {
    return new FlushHandler(t );
  }

  publ c stat c class FlushHandler extends Flushable.Handler<H ghDFPacked ntsPost ngL sts> {
    pr vate stat c f nal Str ng OM T_POS T ONS_PROP_NAME = "om Pos  ons";
    pr vate stat c f nal Str ng SK P_L STS_PROP_NAME = "sk pL sts";
    pr vate stat c f nal Str ng DELTA_FREQ_L STS_PROP_NAME = "deltaFreqL sts";
    pr vate stat c f nal Str ng POS T ON_L STS_PROP_NAME = "pos  onL sts";

    publ c FlushHandler() {
      super();
    }

    publ c FlushHandler(H ghDFPacked ntsPost ngL sts objectToFlush) {
      super(objectToFlush);
    }

    @Overr de
    protected vo d doFlush(Flush nfo flush nfo, DataSer al zer out)
        throws  OExcept on {
      H ghDFPacked ntsPost ngL sts objectToFlush = getObjectToFlush();
      flush nfo.addBooleanProperty(OM T_POS T ONS_PROP_NAME, objectToFlush.om Pos  ons);
      objectToFlush.sk pL sts.getFlushHandler()
          .flush(flush nfo.newSubPropert es(SK P_L STS_PROP_NAME), out);
      objectToFlush.deltaFreqL sts.getFlushHandler()
          .flush(flush nfo.newSubPropert es(DELTA_FREQ_L STS_PROP_NAME), out);
      objectToFlush.pos  onL sts.getFlushHandler()
          .flush(flush nfo.newSubPropert es(POS T ON_L STS_PROP_NAME), out);
    }

    @Overr de
    protected H ghDFPacked ntsPost ngL sts doLoad(
        Flush nfo flush nfo, DataDeser al zer  n) throws  OExcept on {
       ntBlockPool sk pL sts = (new  ntBlockPool.FlushHandler())
          .load(flush nfo.getSubPropert es(SK P_L STS_PROP_NAME),  n);
       ntBlockPool deltaFreqL sts = (new  ntBlockPool.FlushHandler())
          .load(flush nfo.getSubPropert es(DELTA_FREQ_L STS_PROP_NAME),  n);
       ntBlockPool pos  onL sts = (new  ntBlockPool.FlushHandler())
          .load(flush nfo.getSubPropert es(POS T ON_L STS_PROP_NAME),  n);
      return new H ghDFPacked ntsPost ngL sts(
          sk pL sts,
          deltaFreqL sts,
          pos  onL sts,
          flush nfo.getBooleanProperty(OM T_POS T ONS_PROP_NAME),
          null,
          null);
    }
  }
}
