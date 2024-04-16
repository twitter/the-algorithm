package com.tw ter.search.core.earlyb rd. ndex. nverted;

 mport java. o. OExcept on;

 mport javax.annotat on.Nullable;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;

 mport org.apac .lucene. ndex.Post ngsEnum;
 mport org.apac .lucene.search.Doc dSet erator;
 mport org.apac .lucene.ut l.packed.Packed nts;

 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common.ut l. o.flushable.DataDeser al zer;
 mport com.tw ter.search.common.ut l. o.flushable.DataSer al zer;
 mport com.tw ter.search.common.ut l. o.flushable.Flush nfo;
 mport com.tw ter.search.common.ut l. o.flushable.Flushable;

/**
 * A post ng l st  ntended for low-df terms, terms that have a small number of post ngs.
 *
 * T  post ngs (docs and pos  ons) are stored  n Packed nts, packed based on t  largest doc d
 * and pos  on across all low-df terms  n a f eld.
 *
 * All doc ds are packed toget r  n t  r own Packed nts, and all pos  ons are stored toget r
 *  n t  r own Packed nts.
 *  - A doc d  s stored for every s ngle post ng, that  s  f a doc has a frequency of N,   w ll be
 * stored N t  s.
 * - For f elds that om Pos  ons, pos  ons are not stored at all.
 *
 * Example:
 * Post ngs  n t  form (doc d, pos  on):
 *   (1, 0), (1, 1), (2, 1), (2, 3), (2, 5), (4, 0), (5, 0)
 * W ll be stored as:
 *   packedDoc ds:    [1, 1, 2, 2, 2, 4, 5]
 *   packedPos  ons: [0, 1, 1, 3, 5, 0, 0]
 */
publ c class LowDFPacked ntsPost ngL sts extends Opt m zedPost ngL sts {
  pr vate stat c f nal SearchCounter GETT NG_POS T ONS_W TH_OM T_POS T ONS =
      SearchCounter.export("low_df_packed_ nts_post ng_l st_gett ng_pos  ons_w h_om _pos  ons");

  /**
   *  nternal class for h d ng Packed nts Readers and Wr ers. A Mutable  nstance of Packed nts  s
   * only requ red w n  're opt m z ng a new  ndex.
   * For t  read s de,   only need a Packed nts.Reader.
   * For loaded  ndexes,   also only need a Packed nts.Reader.
   */
  pr vate stat c f nal class Packed ntsWrapper {
    // W ll be null  f   are operat ng on a loaded  n read-only  ndex.
    @Nullable
    pr vate f nal Packed nts.Mutable mutablePacked nts;
    pr vate f nal Packed nts.Reader readerPacked nts;

    pr vate Packed ntsWrapper(Packed nts.Mutable mutablePacked nts) {
      t .mutablePacked nts = Precond  ons.c ckNotNull(mutablePacked nts);
      t .readerPacked nts = mutablePacked nts;
    }

    pr vate Packed ntsWrapper(Packed nts.Reader readerPacked nts) {
      t .mutablePacked nts = null;
      t .readerPacked nts = readerPacked nts;
    }

    publ c  nt s ze() {
      return readerPacked nts.s ze();
    }

    publ c Packed nts.Reader getReader() {
      return readerPacked nts;
    }

    publ c vo d set( nt  ndex, long value) {
      t .mutablePacked nts.set( ndex, value);
    }
  }

  pr vate f nal Packed ntsWrapper packedDoc ds;
  /**
   * W ll be null for f elds that om Pos  ons.
   */
  @Nullable
  pr vate f nal Packed ntsWrapper packedPos  ons;
  pr vate f nal boolean om Pos  ons;
  pr vate f nal  nt totalPost ngsAcrossTerms;
  pr vate f nal  nt maxPos  on;
  pr vate  nt currentPacked ntsPos  on;

  /**
   * Creates a new LowDFPacked ntsPost ngL sts.
   * @param om Pos  ons w t r pos  ons should be om ted or not.
   * @param totalPost ngsAcrossTerms how many post ngs across all terms t  f eld has.
   * @param maxPos  on t  largest pos  on used  n all t  post ngs for t  f eld.
   */
  publ c LowDFPacked ntsPost ngL sts(
      boolean om Pos  ons,
       nt totalPost ngsAcrossTerms,
       nt maxPos  on) {
    t (
        new Packed ntsWrapper(Packed nts.getMutable(
            totalPost ngsAcrossTerms,
            Packed nts.b sRequ red(MAX_DOC_ D),
            Packed nts.DEFAULT)),
        om Pos  ons
            ? null
            : new Packed ntsWrapper(Packed nts.getMutable(
            totalPost ngsAcrossTerms,
            Packed nts.b sRequ red(maxPos  on),
            Packed nts.DEFAULT)),
        om Pos  ons,
        totalPost ngsAcrossTerms,
        maxPos  on);
  }

  pr vate LowDFPacked ntsPost ngL sts(
      Packed ntsWrapper packedDoc ds,
      @Nullable
      Packed ntsWrapper packedPos  ons,
      boolean om Pos  ons,
       nt totalPost ngsAcrossTerms,
       nt maxPos  on) {
    t .packedDoc ds = packedDoc ds;
    t .packedPos  ons = packedPos  ons;
    t .om Pos  ons = om Pos  ons;
    t .totalPost ngsAcrossTerms = totalPost ngsAcrossTerms;
    t .maxPos  on = maxPos  on;
    t .currentPacked ntsPos  on = 0;
  }

  @Overr de
  publ c  nt copyPost ngL st(Post ngsEnum post ngsEnum,  nt numPost ngs) throws  OExcept on {
     nt po nter = currentPacked ntsPos  on;

     nt doc d;

    wh le ((doc d = post ngsEnum.nextDoc()) != Doc dSet erator.NO_MORE_DOCS) {
      assert doc d <= MAX_DOC_ D;
       nt freq = post ngsEnum.freq();
      assert freq <= numPost ngs;

      for ( nt   = 0;   < freq;  ++) {
        packedDoc ds.set(currentPacked ntsPos  on, doc d);
         f (packedPos  ons != null) {
           nt pos  on = post ngsEnum.nextPos  on();
          assert pos  on <= maxPos  on;
          packedPos  ons.set(currentPacked ntsPos  on, pos  on);
        }
        currentPacked ntsPos  on++;
      }
    }

    return po nter;
  }

  @Overr de
  publ c Earlyb rdPost ngsEnum post ngs(
       nt post ngL stPo nter,
       nt numPost ngs,
       nt flags) throws  OExcept on {

     f (Post ngsEnum.featureRequested(flags, Post ngsEnum.POS T ONS) && !om Pos  ons) {
      assert packedPos  ons != null;
      return new LowDFPacked ntsPost ngsEnum(
          packedDoc ds.getReader(),
          packedPos  ons.getReader(),
          post ngL stPo nter,
          numPost ngs);
    } else {
       f (Post ngsEnum.featureRequested(flags, Post ngsEnum.POS T ONS) && om Pos  ons) {
        GETT NG_POS T ONS_W TH_OM T_POS T ONS. ncre nt();
      }

      return new LowDFPacked ntsPost ngsEnum(
          packedDoc ds.getReader(),
          null, // no pos  ons
          post ngL stPo nter,
          numPost ngs);
    }
  }

  @V s bleForTest ng
   nt getPacked ntsS ze() {
    return packedDoc ds.s ze();
  }

  @V s bleForTest ng
   nt getMaxPos  on() {
    return maxPos  on;
  }

  @V s bleForTest ng
  boolean  sOm Pos  ons() {
    return om Pos  ons;
  }

  @SuppressWarn ngs("unc cked")
  @Overr de
  publ c FlushHandler getFlushHandler() {
    return new FlushHandler(t );
  }

  stat c class FlushHandler extends Flushable.Handler<LowDFPacked ntsPost ngL sts> {
    pr vate stat c f nal Str ng OM T_POS T ONS_PROP_NAME = "om Pos  ons";
    pr vate stat c f nal Str ng TOTAL_POST NGS_PROP_NAME = "totalPost ngsAcrossTerms";
    pr vate stat c f nal Str ng MAX_POS T ON_PROP_NAME = "maxPos  on";

    publ c FlushHandler() {
      super();
    }

    publ c FlushHandler(LowDFPacked ntsPost ngL sts objectToFlush) {
      super(objectToFlush);
    }

    @Overr de
    protected vo d doFlush(Flush nfo flush nfo, DataSer al zer out) throws  OExcept on {
      LowDFPacked ntsPost ngL sts objectToFlush = getObjectToFlush();

      flush nfo.addBooleanProperty(OM T_POS T ONS_PROP_NAME, objectToFlush.om Pos  ons);
      flush nfo.add ntProperty(TOTAL_POST NGS_PROP_NAME, objectToFlush.totalPost ngsAcrossTerms);
      flush nfo.add ntProperty(MAX_POS T ON_PROP_NAME, objectToFlush.maxPos  on);

      out.wr ePacked nts(objectToFlush.packedDoc ds.getReader());

       f (!objectToFlush.om Pos  ons) {
        assert objectToFlush.packedPos  ons != null;
        out.wr ePacked nts(objectToFlush.packedPos  ons.getReader());
      }
    }

    @Overr de
    protected LowDFPacked ntsPost ngL sts doLoad(
        Flush nfo flush nfo,
        DataDeser al zer  n) throws  OExcept on {

      boolean om Pos  ons = flush nfo.getBooleanProperty(OM T_POS T ONS_PROP_NAME);
       nt totalPost ngsAcrossTerms = flush nfo.get ntProperty(TOTAL_POST NGS_PROP_NAME);
       nt maxPos  on = flush nfo.get ntProperty(MAX_POS T ON_PROP_NAME);

      Packed ntsWrapper packedDoc ds = new Packed ntsWrapper( n.readPacked nts());

      Packed ntsWrapper packedPos  ons = null;
       f (!om Pos  ons) {
        packedPos  ons = new Packed ntsWrapper( n.readPacked nts());
      }

      return new LowDFPacked ntsPost ngL sts(
          packedDoc ds,
          packedPos  ons,
          om Pos  ons,
          totalPost ngsAcrossTerms,
          maxPos  on);
    }
  }
}
