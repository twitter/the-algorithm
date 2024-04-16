package com.tw ter.search.core.earlyb rd. ndex. nverted;

 mport java. o. OExcept on;

 mport com.google.common.annotat ons.V s bleForTest ng;

 mport org.apac .lucene. ndex.Post ngsEnum;

 mport com.tw ter.search.common.ut l. o.flushable.DataDeser al zer;
 mport com.tw ter.search.common.ut l. o.flushable.DataSer al zer;
 mport com.tw ter.search.common.ut l. o.flushable.Flush nfo;
 mport com.tw ter.search.common.ut l. o.flushable.Flushable;

publ c class Mult Post ngL sts extends Opt m zedPost ngL sts {

  @V s bleForTest ng
  publ c stat c f nal  nt DEFAULT_DF_THRESHOLD = 1000;

  pr vate f nal Opt m zedPost ngL sts lowDF;
  pr vate f nal Opt m zedPost ngL sts h ghDF;

  pr vate f nal  nt dfThreshold;

  /**
   * G ven t  number of post ngs  n each term ( n t  f eld), sum up t  number of post ngs  n
   * t  low df f elds.
   * @param numPost ngsPerTerm number of post ngs  n each term  n t  f eld.
   * @param dfThreshold t  low/h gh df threshold.
   */
  pr vate stat c  nt numPost ngs nLowDfTerms( nt[] numPost ngsPerTerm,  nt dfThreshold) {
     nt sumOfAllPost ngs = 0;
    for ( nt numPost ngs nATerm : numPost ngsPerTerm) {
       f (numPost ngs nATerm < dfThreshold) {
        sumOfAllPost ngs += numPost ngs nATerm;
      }
    }
    return sumOfAllPost ngs;
  }

  /**
   * Creates a new post ng l st delegat ng to e  r lowDF or h ghDF post ng l st.
   * @param om Pos  ons w t r pos  ons should be om ted or not.
   * @param numPost ngsPerTerm number of post ngs  n each term  n t  f eld.
   * @param maxPos  on t  largest pos  on used  n all t  post ngs for t  f eld.
   */
  publ c Mult Post ngL sts(
      boolean om Pos  ons,
       nt[] numPost ngsPerTerm,
       nt maxPos  on) {
    t (
        new LowDFPacked ntsPost ngL sts(
            om Pos  ons,
            numPost ngs nLowDfTerms(numPost ngsPerTerm, DEFAULT_DF_THRESHOLD),
            maxPos  on),
        new H ghDFPacked ntsPost ngL sts(om Pos  ons),
        DEFAULT_DF_THRESHOLD);
  }

  pr vate Mult Post ngL sts(
      Opt m zedPost ngL sts lowDF,
      Opt m zedPost ngL sts h ghDF,
       nt dfThreshold) {
    t .lowDF = lowDF;
    t .h ghDF = h ghDF;
    t .dfThreshold = dfThreshold;
  }

  @Overr de
  publ c  nt copyPost ngL st(Post ngsEnum post ngsEnum,  nt numPost ngs)
      throws  OExcept on {
    return numPost ngs < dfThreshold
          ? lowDF.copyPost ngL st(post ngsEnum, numPost ngs)
          : h ghDF.copyPost ngL st(post ngsEnum, numPost ngs);
  }

  @Overr de
  publ c Earlyb rdPost ngsEnum post ngs( nt post ngsPo nter,  nt numPost ngs,  nt flags)
      throws  OExcept on {
    return numPost ngs < dfThreshold
        ? lowDF.post ngs(post ngsPo nter, numPost ngs, flags)
        : h ghDF.post ngs(post ngsPo nter, numPost ngs, flags);
  }

  @SuppressWarn ngs("unc cked")
  @Overr de
  publ c FlushHandler getFlushHandler() {
    return new FlushHandler(t );
  }

  @V s bleForTest ng
  Opt m zedPost ngL sts getLowDfPost ngsL st() {
    return lowDF;
  }

  @V s bleForTest ng
  Opt m zedPost ngL sts getH ghDfPost ngsL st() {
    return h ghDF;
  }

  publ c stat c class FlushHandler extends Flushable.Handler<Mult Post ngL sts> {
    pr vate stat c f nal Str ng DF_THRESHOLD_PROP_NAME = "dfThresHold";

    publ c FlushHandler() {
      super();
    }

    publ c FlushHandler(Mult Post ngL sts objectToFlush) {
      super(objectToFlush);
    }

    @Overr de
    protected vo d doFlush(Flush nfo flush nfo, DataSer al zer out)
        throws  OExcept on {
      Mult Post ngL sts objectToFlush = getObjectToFlush();
      flush nfo.add ntProperty(DF_THRESHOLD_PROP_NAME, objectToFlush.dfThreshold);
      objectToFlush.lowDF.getFlushHandler().flush(
              flush nfo.newSubPropert es("lowDFPost ngl sts"), out);
      objectToFlush.h ghDF.getFlushHandler().flush(
              flush nfo.newSubPropert es("h ghDFPost ngl sts"), out);
    }

    @Overr de
    protected Mult Post ngL sts doLoad(Flush nfo flush nfo,
        DataDeser al zer  n) throws  OExcept on {
      Opt m zedPost ngL sts lowDF = new LowDFPacked ntsPost ngL sts.FlushHandler()
            .load(flush nfo.getSubPropert es("lowDFPost ngl sts"),  n);
      Opt m zedPost ngL sts h ghDF = new H ghDFPacked ntsPost ngL sts.FlushHandler()
          .load(flush nfo.getSubPropert es("h ghDFPost ngl sts"),  n);
      return new Mult Post ngL sts(
          lowDF,
          h ghDF,
          flush nfo.get ntProperty(DF_THRESHOLD_PROP_NAME));
    }
  }
}
