package com.tw ter.search.common.sc ma.earlyb rd;

 mport com.google.common.base.Precond  ons;

 mport com.tw ter.search.common.encod ng.features. ntegerEncodedFeatures;
 mport com.tw ter.search.common. ndex ng.thr ftjava.PackedFeatures;
 mport com.tw ter.search.common. ndex ng.thr ftjava.Vers onedT etFeatures;
 mport com.tw ter.search.common.sc ma.Sc maUt l;
 mport com.tw ter.search.common.sc ma.base.FeatureConf gurat on;
 mport com.tw ter.search.common.sc ma.base. mmutableSc ma nterface;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant;

/**
 * A class for encod ng earlyb rd features  n  ntegers
 */
publ c abstract class Earlyb rdEncodedFeatures extends  ntegerEncodedFeatures {
  pr vate f nal  mmutableSc ma nterface sc ma;
  pr vate f nal Earlyb rdF eldConstant baseF eld;

  publ c Earlyb rdEncodedFeatures( mmutableSc ma nterface sc ma,
                                  Earlyb rdF eldConstant baseF eld) {
    t .sc ma = sc ma;
    t .baseF eld = baseF eld;
  }

  /**
   * Wr e t  object  nto packedFeatures of t  g ven Vers onedT etFeatures.
   */
  publ c vo d wr eFeaturesToVers onedT etFeatures(
      Vers onedT etFeatures vers onedT etFeatures) {
     f (!vers onedT etFeatures. sSetPackedFeatures()) {
      vers onedT etFeatures.setPackedFeatures(new PackedFeatures());
    }
    copyToPackedFeatures(vers onedT etFeatures.getPackedFeatures());
  }

  /**
   * Wr e t  object  nto extendedPackedFeatures of t  g ven Vers onedT etFeatures.
   */
  publ c vo d wr eExtendedFeaturesToVers onedT etFeatures(
      Vers onedT etFeatures vers onedT etFeatures) {
     f (!vers onedT etFeatures. sSetExtendedPackedFeatures()) {
      vers onedT etFeatures.setExtendedPackedFeatures(new PackedFeatures());
    }
    copyToPackedFeatures(vers onedT etFeatures.getExtendedPackedFeatures());
  }

  @Overr de
  publ c Str ng toStr ng() {
    Str ngBu lder ret = new Str ngBu lder();
    ret.append("T et features: \n");
    for (FeatureConf gurat on feature
        : Earlyb rdSc maCreateTool.FEATURE_CONF GURAT ON_MAP.values()) {
      ret.append(feature.getNa ()).append(": ").append(getFeatureValue(feature)).append("\n");
    }
    return ret.toStr ng();
  }

  publ c boolean  sFlagSet(Earlyb rdF eldConstant f eld) {
    return  sFlagSet(sc ma.getFeatureConf gurat onBy d(f eld.getF eld d()));
  }

  publ c  nt getFeatureValue(Earlyb rdF eldConstant f eld) {
    return getFeatureValue(sc ma.getFeatureConf gurat onBy d(f eld.getF eld d()));
  }

  publ c Earlyb rdEncodedFeatures setFlag(Earlyb rdF eldConstant f eld) {
    setFlag(sc ma.getFeatureConf gurat onBy d(f eld.getF eld d()));
    return t ;
  }

  publ c Earlyb rdEncodedFeatures clearFlag(Earlyb rdF eldConstant f eld) {
    clearFlag(sc ma.getFeatureConf gurat onBy d(f eld.getF eld d()));
    return t ;
  }

  publ c Earlyb rdEncodedFeatures setFlagValue(Earlyb rdF eldConstant f eld,
                                               boolean value) {
    setFlagValue(sc ma.getFeatureConf gurat onBy d(f eld.getF eld d()), value);
    return t ;
  }

  publ c Earlyb rdEncodedFeatures setFeatureValue(Earlyb rdF eldConstant f eld,
                                                   nt value) {
    setFeatureValue(sc ma.getFeatureConf gurat onBy d(f eld.getF eld d()), value);
    return t ;
  }

  publ c Earlyb rdEncodedFeatures setFeatureValue fGreater(Earlyb rdF eldConstant f eld,
                                                            nt value) {
    setFeatureValue fGreater(sc ma.getFeatureConf gurat onBy d(f eld.getF eld d()), value);
    return t ;
  }

  publ c boolean  ncre nt fNotMax mum(Earlyb rdF eldConstant f eld) {
    return  ncre nt fNotMax mum(sc ma.getFeatureConf gurat onBy d(f eld.getF eld d()));
  }

  pr vate stat c f nal class ArrayEncodedT etFeatures extends Earlyb rdEncodedFeatures {
    pr vate f nal  nt[] encoded nts;

    pr vate ArrayEncodedT etFeatures( mmutableSc ma nterface sc ma,
                                      Earlyb rdF eldConstant baseF eld) {
      super(sc ma, baseF eld);

      f nal  nt num ntegers = Sc maUt l.getCSFF eldF xedLength(sc ma, baseF eld.getF eld d());
      Precond  ons.c ckState(num ntegers > 0);
      t .encoded nts = new  nt[num ntegers];
    }

    @Overr de
    publ c  nt getNum nts() {
      return encoded nts.length;
    }

    @Overr de
    publ c  nt get nt( nt pos) {
      return encoded nts[pos];
    }

    @Overr de
    publ c vo d set nt( nt pos,  nt value) {
      encoded nts[pos] = value;
    }
  }

  /**
   * Create a new {@l nk Earlyb rdEncodedFeatures} object based on sc ma and base f eld.
   * @param sc ma t  sc ma for all f elds
   * @param baseF eld base f eld's constant value
   */
  publ c stat c Earlyb rdEncodedFeatures newEncodedT etFeatures(
       mmutableSc ma nterface sc ma, Earlyb rdF eldConstant baseF eld) {
    return new ArrayEncodedT etFeatures(sc ma, baseF eld);
  }

  /**
   * Create a new {@l nk Earlyb rdEncodedFeatures} object based on sc ma and base f eld na .
   * @param sc ma t  sc ma for all f elds
   * @param baseF eldNa  base f eld's na 
   */
  publ c stat c Earlyb rdEncodedFeatures newEncodedT etFeatures(
       mmutableSc ma nterface sc ma, Str ng baseF eldNa ) {
    Earlyb rdF eldConstant baseF eld = Earlyb rdF eldConstants.getF eldConstant(baseF eldNa );
    Precond  ons.c ckNotNull(baseF eld);
    return newEncodedT etFeatures(sc ma, baseF eld);
  }
}
