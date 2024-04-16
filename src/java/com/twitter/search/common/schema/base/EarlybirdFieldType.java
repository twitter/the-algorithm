package com.tw ter.search.common.sc ma.base;

 mport javax.annotat on.Nullable;

 mport org.apac .commons.lang.Str ngUt ls;
 mport org.apac .lucene.docu nt.F eldType;
 mport org.apac .lucene. ndex.DocValuesType;
 mport org.apac .lucene. ndex. ndexOpt ons;

 mport com.tw ter.common.text.ut l.TokenStreamSer al zer;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftCSFType;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftCSFV ewSett ngs;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftFeatureUpdateConstra nt;

/**
 * An extens on of Lucene's {@l nk F eldType} that conta ns add  onal Earlyb rd-spec f c sett ngs.
 * Lucene  ndex ngCha ns can downcast t  F eldType object to access t se add  onal sett ngs.
 */
publ c class Earlyb rdF eldType extends F eldType {

  publ c stat c f nal Earlyb rdF eldType LONG_CSF_F ELD_TYPE = new Earlyb rdF eldType();
  publ c stat c f nal Earlyb rdF eldType  NT_CSF_F ELD_TYPE = new Earlyb rdF eldType();
  publ c stat c f nal Earlyb rdF eldType BYTE_CSF_F ELD_TYPE = new Earlyb rdF eldType();

  stat c {
    LONG_CSF_F ELD_TYPE.setCsfType(Thr ftCSFType.LONG);
    LONG_CSF_F ELD_TYPE.setDocValuesType(DocValuesType.NUMER C);
    LONG_CSF_F ELD_TYPE.setCsfLoad ntoRam(true);
    LONG_CSF_F ELD_TYPE.freeze();

     NT_CSF_F ELD_TYPE.setCsfType(Thr ftCSFType. NT);
     NT_CSF_F ELD_TYPE.setDocValuesType(DocValuesType.NUMER C);
     NT_CSF_F ELD_TYPE.setCsfLoad ntoRam(true);
     NT_CSF_F ELD_TYPE.freeze();

    BYTE_CSF_F ELD_TYPE.setCsfType(Thr ftCSFType.BYTE);
    BYTE_CSF_F ELD_TYPE.setDocValuesType(DocValuesType.NUMER C);
    BYTE_CSF_F ELD_TYPE.setCsfLoad ntoRam(true);
    BYTE_CSF_F ELD_TYPE.freeze();
  }


  pr vate boolean storePerPos  onPayloads;
  pr vate  nt defaultPayloadLength;
  // T   s true for f elds that beco   mmutable after opt m zat on
  pr vate boolean beco s mmutable = true;
  pr vate boolean supportOrderedTerms;
  pr vate boolean supportTermTextLookup;
  pr vate boolean  ndexHFTermPa rs;

  /**
   * T  flag turns on t et spec f c normal zat ons.
   * T  turns on t  follow ng two token processors:
   * {@l nk com.tw ter.search.common.ut l.text.spl ter.Hashtag nt onPunctuat onSpl ter}
   * {@l nk com.tw ter.search.common.ut l.text.f lter.Normal zedTokenF lter}
   *
   * Hashtag nt onPunctuat onSpl ter would break a  nt on or hashtag l ke @ab_cd or #ab_cd  nto
   * tokens {ab, cd}.
   * Normal zedTokenF lter str ps out t  # @ $ from t  tokens.
   *
   *
   * @deprecated   should remove t  flag.    s confus ng to have Earlyb rd apply add  onal
   * token zat on on top of what  ngester produced.
   */
  @Deprecated
  pr vate boolean useT etSpec f cNormal zat on;

  @Nullable
  pr vate TokenStreamSer al zer.Bu lder tokenStreamSer al zerProv der = null;

  // csf type sett ngs
  pr vate Thr ftCSFType csfType;
  pr vate boolean csfVar ableLength;
  pr vate  nt csfF xedLengthNumValuesPerDoc;
  pr vate boolean csfF xedLengthUpdateable;
  pr vate boolean csfLoad ntoRam;
  pr vate boolean csfDefaultValueSet;
  pr vate long csfDefaultValue;
  // True  f t   s a CSF f eld wh ch  s a v ew on top of a d fferent CSF f eld
  pr vate boolean csfV ewF eld;
  //  f t  f eld  s a csf v ew, t   s t   D of t  CSF f eld back ng t  v ew
  pr vate  nt csfV ewBaseF eld d;
  pr vate FeatureConf gurat on csfV ewFeatureConf gurat on;

  // facet f eld sett ngs
  pr vate Str ng facetNa ;
  pr vate boolean storeFacetSk pl st;
  pr vate boolean storeFacetOffens veCounters;
  pr vate boolean useCSFForFacetCount ng;

  // Determ nes  f t  f eld  s  ndexed
  pr vate boolean  ndexedF eld = false;

  // search f eld sett ngs
  // w t r a f eld should be searc d by default
  pr vate boolean textSearchableByDefault = false;
  pr vate float textSearchableF eld  ght = 1.0f;

  // For  ndexed nu r cal f elds
  pr vate  ndexedNu r cF eldSett ngs nu r cF eldSett ngs = null;

  publ c boolean  sStorePerPos  onPayloads() {
    return storePerPos  onPayloads;
  }

  publ c vo d setStorePerPos  onPayloads(boolean storePerPos  onPayloads) {
    c ck fFrozen();
    t .storePerPos  onPayloads = storePerPos  onPayloads;
  }

  publ c  nt getDefaultPayloadLength() {
    return defaultPayloadLength;
  }

  publ c vo d setDefaultPayloadLength( nt defaultPayloadLength) {
    c ck fFrozen();
    t .defaultPayloadLength = defaultPayloadLength;
  }

  publ c boolean beco s mmutable() {
    return beco s mmutable;
  }

  publ c vo d setBeco s mmutable(boolean beco s mmutable) {
    c ck fFrozen();
    t .beco s mmutable = beco s mmutable;
  }

  publ c boolean  sSupportOrderedTerms() {
    return supportOrderedTerms;
  }

  publ c vo d setSupportOrderedTerms(boolean supportOrderedTerms) {
    c ck fFrozen();
    t .supportOrderedTerms = supportOrderedTerms;
  }

  publ c boolean  sSupportTermTextLookup() {
    return supportTermTextLookup;
  }

  publ c vo d setSupportTermTextLookup(boolean supportTermTextLookup) {
    t .supportTermTextLookup = supportTermTextLookup;
  }

  @Nullable
  publ c TokenStreamSer al zer getTokenStreamSer al zer() {
    return tokenStreamSer al zerProv der == null ? null : tokenStreamSer al zerProv der.safeBu ld();
  }

  publ c vo d setTokenStreamSer al zerBu lder(TokenStreamSer al zer.Bu lder prov der) {
    c ck fFrozen();
    t .tokenStreamSer al zerProv der = prov der;
  }

  publ c Thr ftCSFType getCsfType() {
    return csfType;
  }

  publ c vo d setCsfType(Thr ftCSFType csfType) {
    c ck fFrozen();
    t .csfType = csfType;
  }

  publ c boolean  sCsfVar ableLength() {
    return csfVar ableLength;
  }

  publ c  nt getCsfF xedLengthNumValuesPerDoc() {
    return csfF xedLengthNumValuesPerDoc;
  }

  publ c vo d setCsfVar ableLength() {
    c ck fFrozen();
    t .csfVar ableLength = true;
  }

  /**
   * Make t  f eld a f xed length CSF, w h t  g ven length.
   */
  publ c vo d setCsfF xedLengthSett ngs( nt csfF xedLengthNumValuesPerDocu nt,
                                        boolean  sCsfF xedLengthUpdateable) {
    c ck fFrozen();
    t .csfVar ableLength = false;
    t .csfF xedLengthNumValuesPerDoc = csfF xedLengthNumValuesPerDocu nt;
    t .csfF xedLengthUpdateable =  sCsfF xedLengthUpdateable;
  }

  publ c boolean  sCsfF xedLengthUpdateable() {
    return csfF xedLengthUpdateable;
  }

  publ c boolean  sCsfLoad ntoRam() {
    return csfLoad ntoRam;
  }

  publ c vo d setCsfLoad ntoRam(boolean csfLoad ntoRam) {
    c ck fFrozen();
    t .csfLoad ntoRam = csfLoad ntoRam;
  }

  publ c vo d setCsfDefaultValue(long defaultValue) {
    c ck fFrozen();
    t .csfDefaultValue = defaultValue;
    t .csfDefaultValueSet = true;
  }

  publ c long getCsfDefaultValue() {
    return csfDefaultValue;
  }

  publ c boolean  sCsfDefaultValueSet() {
    return csfDefaultValueSet;
  }

  publ c Str ng getFacetNa () {
    return facetNa ;
  }

  publ c vo d setFacetNa (Str ng facetNa ) {
    c ck fFrozen();
    t .facetNa  = facetNa ;
  }

  publ c boolean  sStoreFacetSk pl st() {
    return storeFacetSk pl st;
  }

  publ c vo d setStoreFacetSk pl st(boolean storeFacetSk pl st) {
    c ck fFrozen();
    t .storeFacetSk pl st = storeFacetSk pl st;
  }

  publ c boolean  sStoreFacetOffens veCounters() {
    return storeFacetOffens veCounters;
  }

  publ c vo d setStoreFacetOffens veCounters(boolean storeFacetOffens veCounters) {
    c ck fFrozen();
    t .storeFacetOffens veCounters = storeFacetOffens veCounters;
  }

  publ c boolean  sUseCSFForFacetCount ng() {
    return useCSFForFacetCount ng;
  }

  publ c vo d setUseCSFForFacetCount ng(boolean useCSFForFacetCount ng) {
    c ck fFrozen();
    t .useCSFForFacetCount ng = useCSFForFacetCount ng;
  }

  publ c boolean  sFacetF eld() {
    return facetNa  != null && !Str ngUt ls. sEmpty(facetNa );
  }

  publ c boolean  s ndexHFTermPa rs() {
    return  ndexHFTermPa rs;
  }

  publ c vo d set ndexHFTermPa rs(boolean  ndexHFTermPa rs) {
    c ck fFrozen();
    t . ndexHFTermPa rs =  ndexHFTermPa rs;
  }

  publ c boolean acceptPretoken zedF eld() {
    return tokenStreamSer al zerProv der != null;
  }

  /**
   * set t  f eld to use add  onal tw ter spec f c token zat on.
   * @deprecated should avo d do ng add  onal token zat ons on top of what  ngester produced.
   */
  @Deprecated
  publ c boolean useT etSpec f cNormal zat on() {
    return useT etSpec f cNormal zat on;
  }

  /**
   * test w t r t  f eld uses add  onal tw ter spec f c token zat on.
   * @deprecated should avo d do ng add  onal token zat ons on top of what  ngester produced.
   */
  @Deprecated
  publ c vo d setUseT etSpec f cNormal zat on(boolean useT etSpec f cNormal zat on) {
    c ck fFrozen();
    t .useT etSpec f cNormal zat on = useT etSpec f cNormal zat on;
  }

  publ c boolean  s ndexedF eld() {
    return  ndexedF eld;
  }

  publ c vo d set ndexedF eld(boolean  ndexedF eld) {
    t . ndexedF eld =  ndexedF eld;
  }

  publ c boolean  sTextSearchableByDefault() {
    return textSearchableByDefault;
  }

  publ c vo d setTextSearchableByDefault(boolean textSearchableByDefault) {
    c ck fFrozen();
    t .textSearchableByDefault = textSearchableByDefault;
  }

  publ c float getTextSearchableF eld  ght() {
    return textSearchableF eld  ght;
  }

  publ c vo d setTextSearchableF eld  ght(float textSearchableF eld  ght) {
    c ck fFrozen();
    t .textSearchableF eld  ght = textSearchableF eld  ght;
  }

  /**
   * Conven ence  thod to f nd out  f t  f eld stores pos  ons. {@l nk # ndexOpt ons()} can also
   * be used to determ ne t   ndex opt ons for t  f eld.
   */
  publ c f nal boolean hasPos  ons() {
    return  ndexOpt ons() ==  ndexOpt ons.DOCS_AND_FREQS_AND_POS T ONS
            ||  ndexOpt ons() ==  ndexOpt ons.DOCS_AND_FREQS_AND_POS T ONS_AND_OFFSETS;
  }

  publ c boolean  sCsfV ewF eld() {
    return csfV ewF eld;
  }

  publ c  nt getCsfV ewBaseF eld d() {
    return csfV ewBaseF eld d;
  }

  publ c FeatureConf gurat on getCsfV ewFeatureConf gurat on() {
    return csfV ewFeatureConf gurat on;
  }

  /**
   * Set t  CSF v ew sett ngs. A CSF v ew  s a port on of an anot r CSF.
   */
  publ c vo d setCsfV ewSett ngs(Str ng f eldNa ,
                                 Thr ftCSFV ewSett ngs csfV ewSett ngs,
                                 Sc ma.F eld nfo baseF eld) {
    c ck fFrozen();
    t .csfV ewF eld = true;
    t .csfV ewBaseF eld d = csfV ewSett ngs.getBaseF eldConf g d();
    FeatureConf gurat on.Bu lder bu lder = FeatureConf gurat on.bu lder()
            .w hNa (f eldNa )
            .w hType(csfV ewSett ngs.csfType)
            .w hB Range(csfV ewSett ngs.getValue ndex(),
                csfV ewSett ngs.getB StartPos  on(),
                csfV ewSett ngs.getB Length())
            .w hBaseF eld(baseF eld.getNa ());
     f (csfV ewSett ngs. sSetOutputCSFType()) {
      bu lder.w hOutputType(csfV ewSett ngs.getOutputCSFType());
    }
     f (csfV ewSett ngs. sSetNormal zat onType()) {
      bu lder.w hFeatureNormal zat onType(csfV ewSett ngs.getNormal zat onType());
    }
     f (csfV ewSett ngs. sSetFeatureUpdateConstra nts()) {
      for (Thr ftFeatureUpdateConstra nt c : csfV ewSett ngs.getFeatureUpdateConstra nts()) {
        bu lder.w hFeatureUpdateConstra nt(c);
      }
    }

    t .csfV ewFeatureConf gurat on = bu lder.bu ld();
  }

  publ c  ndexedNu r cF eldSett ngs getNu r cF eldSett ngs() {
    return nu r cF eldSett ngs;
  }

  publ c vo d setNu r cF eldSett ngs( ndexedNu r cF eldSett ngs nu r cF eldSett ngs) {
    c ck fFrozen();
    t .nu r cF eldSett ngs = nu r cF eldSett ngs;
  }
}
