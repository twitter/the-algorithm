package com.tw ter.search.core.earlyb rd. ndex.column;

 mport com.tw ter.search.common.encod ng.features. ntegerEncodedFeatures;
 mport com.tw ter.search.common.sc ma.base.FeatureConf gurat on;
 mport com.tw ter.search.common.sc ma.base.Sc ma;
 mport com.tw ter.search.core.earlyb rd. ndex.Doc DToT et DMapper;

/**
 * An  nt CSF v ew on top of an {@l nk AbstractColumnStr deMult  nt ndex}.
 *
 * Used for decod ng encoded packed features and expos ng t m as
 * {@l nk org.apac .lucene. ndex.Nu r cDocValues}.
 */
publ c class ColumnStr de ntV ew ndex extends ColumnStr deF eld ndex {
  pr vate stat c class  ntV ew ntegerEncodedFeatures extends  ntegerEncodedFeatures {
    pr vate f nal AbstractColumnStr deMult  nt ndex base ndex;
    pr vate f nal  nt doc D;

    publ c  ntV ew ntegerEncodedFeatures(AbstractColumnStr deMult  nt ndex base ndex,  nt doc D) {
      t .base ndex = base ndex;
      t .doc D = doc D;
    }

    @Overr de
    publ c  nt get nt( nt pos) {
      return base ndex.get(doc D, pos);
    }

    @Overr de
    publ c vo d set nt( nt pos,  nt value) {
      base ndex.setValue(doc D, pos, value);
    }

    @Overr de
    publ c  nt getNum nts() {
      return base ndex.getNum ntsPerF eld();
    }
  }

  pr vate f nal AbstractColumnStr deMult  nt ndex base ndex;
  pr vate f nal FeatureConf gurat on featureConf gurat on;

  /**
   * Creates a new ColumnStr de ntV ew ndex on top of an ex st ng AbstractColumnStr deMult  nt ndex.
   */
  publ c ColumnStr de ntV ew ndex(Sc ma.F eld nfo  nfo,
                                  AbstractColumnStr deMult  nt ndex base ndex) {
    super( nfo.getNa ());
    t .base ndex = base ndex;
    t .featureConf gurat on =  nfo.getF eldType().getCsfV ewFeatureConf gurat on();
  }

  @Overr de
  publ c long get( nt doc D) {
     ntegerEncodedFeatures encodedFeatures = new  ntV ew ntegerEncodedFeatures(base ndex, doc D);
    return encodedFeatures.getFeatureValue(featureConf gurat on);
  }

  @Overr de
  publ c vo d setValue( nt doc D, long value) {
     ntegerEncodedFeatures encodedFeatures = new  ntV ew ntegerEncodedFeatures(base ndex, doc D);
    encodedFeatures.setFeatureValue(featureConf gurat on, ( nt) value);
  }

  @Overr de
  publ c ColumnStr deF eld ndex opt m ze(
      Doc DToT et DMapper or g nalT et dMapper, Doc DToT et DMapper opt m zedT et dMapper) {
    throw new UnsupportedOperat onExcept on(
        "ColumnStr de ntV ew ndex  nstances do not support opt m zat on");
  }
}
