package com.tw ter.search.common.sc ma.base;

 mport java.ut l.Collect on;
 mport java.ut l.Map;

 mport javax.annotat on.Nullable;

 mport com.google.common.base.Pred cate;
 mport com.google.common.collect. mmutableCollect on;
 mport com.google.common.collect. mmutableMap;

 mport org.apac .lucene.analys s.Analyzer;
 mport org.apac .lucene.facet.FacetsConf g;
 mport org.apac .lucene. ndex.F eld nfos;

 mport com.tw ter.search.common.features.thr ft.Thr ftSearchFeatureSc ma;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftAnalyzer;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftCSFType;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftF eldConf gurat on;

/**
 * Search Sc ma.
 */
publ c  nterface Sc ma {
  /**
   * Certa n Sc ma  mple ntat ons can evolve at run t  .  T  call returns a snapshot of
   * of t  sc ma wh ch  s guaranteed to not change.
   */
   mmutableSc ma nterface getSc maSnapshot();

  /**
   * Returns a str ng descr b ng t  current sc ma vers on.
   */
  Str ng getVers onDescr pt on();

  /**
   * Returns w t r t  sc ma vers on  s off c al. Only off c al seg nts are uploaded to HDFS.
   */
  boolean  sVers onOff c al();

  /**
   * Returns t  sc ma's major vers on.
   */
   nt getMajorVers onNumber();

  /**
   * Returns t  sc ma's m nor vers on.
   */
   nt getM norVers onNumber();

  /**
   * Returns t  default analyzer. T  analyzer  s used w n none  s spec f ed on t  f eld  nfo.
   */
  Analyzer getDefaultAnalyzer(Thr ftAnalyzer overr de);

  /**
   * Returns w t r t  g ven f eld  s conf gured  n t  sc ma.
   */
  boolean hasF eld( nt f eldConf g d);

  /**
   * Returns w t r t  g ven f eld  s conf gured  n t  sc ma.
   */
  boolean hasF eld(Str ng f eldNa );

  /**
   * Get t  f eld na  correspond ng to t  g ven f eld  d.
   */
  Str ng getF eldNa ( nt f eldConf g d);

  /**
   * Return t  F eld nfo of all f elds.
   */
   mmutableCollect on<F eld nfo> getF eld nfos();

  /**
   * Get t  f eld  nfo for t  g ven f eld  d.  f an overr de  s g ven, attempt to  rge t 
   * base f eld  nfo w h t  overr de conf g.
   */
  F eld nfo getF eld nfo( nt f eldConf g d, Thr ftF eldConf gurat on overr de);


  /**
   * Get t  f eld  nfo for t  g ven f eld  d. No overr de.
   */
  @Nullable
  F eld nfo getF eld nfo( nt f eldConf g d);

  /**
   * Get t  f eld  nfo for t  g ven f eld na . No overr de.
   */
  @Nullable
  F eld nfo getF eld nfo(Str ng f eldNa );

  /**
   * Bu lds a lucene F eld nfos  nstance, usually used for  ndex ng.
   */
  F eld nfos getLuceneF eld nfos(Pred cate<Str ng> acceptedF elds);

  /**
   * Returns t  number of facet f elds  n t  sc ma.
   */
   nt getNumFacetF elds();

  /**
   * Return facet conf gurat ons.
   */
  FacetsConf g getFacetsConf g();

  /**
   * Get t  facet f eld's f eld  nfo by facet na .
   */
  F eld nfo getFacetF eldByFacetNa (Str ng facetNa );

  /**
   * Get t  facet f eld's f eld  nfo by f eld na .
   */
  F eld nfo getFacetF eldByF eldNa (Str ng f eldNa );

  /**
   * Get t  f eld  nfos for all facet f elds.
   */
  Collect on<F eld nfo> getFacetF elds();

  /**
   * Get t  f eld  nfos for all facet f elds backed by column str de f elds.
   */
  Collect on<F eld nfo> getCsfFacetF elds();

  /**
   * Get t  f eld   ght map for text searchable f elds.
   */
  Map<Str ng, F eld  ghtDefault> getF eld  ghtMap();

  /**
   * Get scor ng feature conf gurat on by feature na .
   */
  FeatureConf gurat on getFeatureConf gurat onByNa (Str ng featureNa );

  /**
   * Get scor ng feature conf gurat on by feature f eld  d.  T  feature conf gurat on  s
   * guaranteed to be not null, or a NullPo nterExcept on w ll be thrown out.
   */
  FeatureConf gurat on getFeatureConf gurat onBy d( nt featureF eld d);

  /**
   * Returns t  Thr ftCSFType for a CSF f eld.
   * Note: for non-CSF f eld, null w ll be returned.
   */
  @Nullable
  Thr ftCSFType getCSFF eldType(Str ng f eldNa );

  /**
   * Get t  search result feature sc ma for all poss ble features  n all search results.
   *
   * T  returned value  s not really  mmutable (because  's a pre-generated thr ft struct).
   *   want to return   d rectly because   want to pre-bu ld   once and return w h t  thr ft
   * search results as  s.
   */
  Thr ftSearchFeatureSc ma getSearchFeatureSc ma();

  /**
   * Get t  mapp ng from feature  d to feature conf gurat on.
   */
   mmutableMap< nteger, FeatureConf gurat on> getFeature dToFeatureConf g();

  /**
   * Get t  mapp ng from feature na  to feature conf gurat on.
   */
   mmutableMap<Str ng, FeatureConf gurat on> getFeatureNa ToFeatureConf g();

  /**
   * F eld conf gurat on for a s ngle f eld.
   */
  f nal class F eld nfo {
    pr vate f nal  nt f eld d;
    pr vate f nal Str ng na ;
    pr vate f nal Earlyb rdF eldType luceneF eldType;

    publ c F eld nfo( nt f eld d, Str ng na , Earlyb rdF eldType luceneF eldType) {
      t .f eld d = f eld d;
      t .na  = na ;
      t .luceneF eldType = luceneF eldType;
    }

    publ c  nt getF eld d() {
      return f eld d;
    }

    publ c Str ng getNa () {
      return na ;
    }

    publ c Earlyb rdF eldType getF eldType() {
      return luceneF eldType;
    }

    publ c Str ng getDescr pt on() {
      return Str ng.format(
          "(F eld nfo [f eld d: %d, na : %s, luceneF eldType: %s])",
          f eld d, na , luceneF eldType.getFacetNa ()
      );
    }

    @Overr de
    publ c boolean equals(Object obj) {
       f (!(obj  nstanceof F eld nfo)) {
        return false;
      }
      return f eld d == ((F eld nfo) obj).f eld d;
    }

    @Overr de
    publ c  nt hashCode() {
      return f eld d;
    }
  }

  /**
   * Except on thrown w n errors or  ncons stences are detected  n a search sc ma.
   */
  f nal class Sc maVal dat onExcept on extends Except on {
    publ c Sc maVal dat onExcept on(Str ng msg) {
      super(msg);
    }

    publ c Sc maVal dat onExcept on(Str ng msg, Except on e) {
      super(msg, e);
    }
  }
}
