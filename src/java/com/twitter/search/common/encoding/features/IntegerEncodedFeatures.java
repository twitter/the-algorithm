package com.tw ter.search.common.encod ng.features;

 mport java.ut l.L st;

 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect.L sts;

 mport com.tw ter.search.common. ndex ng.thr ftjava.PackedFeatures;
 mport com.tw ter.search.common.sc ma.base.FeatureConf gurat on;

/**
 * Class used to read/wr e  ntegers encoded accord ng to
 * {@l nk com.tw ter.search.common.sc ma.base.FeatureConf gurat on}
 *
 *  mple ntat ons must overr de {@l nk #get nt( nt pos)} and {@l nk #set nt( nt pos,  nt value)}.
 */
publ c abstract class  ntegerEncodedFeatures {
  /**
   * Returns t  value at t  g ven pos  on.
   */
  publ c abstract  nt get nt( nt pos);

  /**
   * Sets t  g ven value at t  g ven pos  on.
   */
  publ c abstract vo d set nt( nt pos,  nt value);

  /**
   * Get t  max mum number of  ntegers to hold features.
   * @return t  number of  ntegers to represent all features.
   */
  publ c abstract  nt getNum nts();

  /**
   * Test to see  f t  g ven feature  s true or non-zero. Useful for one b  features.
   * @param feature feature to exam ne
   * @return true  f feature  s non-zero
   */
  publ c boolean  sFlagSet(FeatureConf gurat on feature) {
    return (get nt(feature.getValue ndex()) & feature.getB Mask()) != 0;
  }

  publ c  ntegerEncodedFeatures setFlag(FeatureConf gurat on feature) {
    set nt(feature.getValue ndex(), get nt(feature.getValue ndex()) | feature.getB Mask());
    return t ;
  }

  publ c  ntegerEncodedFeatures clearFlag(FeatureConf gurat on feature) {
    set nt(feature.getValue ndex(), get nt(feature.getValue ndex()) & feature.get nverseB Mask());
    return t ;
  }

  /**
   * Sets a boolean flag.
   */
  publ c  ntegerEncodedFeatures setFlagValue(FeatureConf gurat on feature, boolean value) {
     f (value) {
      setFlag(feature);
    } else {
      clearFlag(feature);
    }
    return t ;
  }

  /**
   * Get feature value
   * @param feature feature to get
   * @return t  value of t  feature
   */
  publ c  nt getFeatureValue(FeatureConf gurat on feature) {
    return (get nt(feature.getValue ndex()) & feature.getB Mask())
            >>> feature.getB StartPos  on();
  }

  /**
   * Set feature value
   * @param feature feature to mod fy
   * @param value value to set.
   */
  publ c  ntegerEncodedFeatures setFeatureValue(FeatureConf gurat on feature,  nt value) {
    Precond  ons.c ckState(
        value <= feature.getMaxValue(),
        "Feature value, %s,  s greater than t  max value allo d for t  feature. "
            + "Feature: %s, Max value: %s",
        value, feature.getNa (), feature.getMaxValue());

    // Clear t  value of t  g ven feature  n  s  nt.
     nt temp = get nt(feature.getValue ndex()) & feature.get nverseB Mask();

    // Set t  new feature value. Apply ng t  b  mask  re ensures that ot r features  n t 
    // sa   nt are not mod f ed by m stake.
    temp |= (value << feature.getB StartPos  on()) & feature.getB Mask();

    set nt(feature.getValue ndex(), temp);
    return t ;
  }

  /**
   * Sets feature value  f greater than current value
   * @param feature feature to mod fy
   * @param value new value
   */
  publ c  ntegerEncodedFeatures setFeatureValue fGreater(FeatureConf gurat on feature,  nt value) {
     f (value > getFeatureValue(feature)) {
      setFeatureValue(feature, value);
    }
    return t ;
  }

  /**
   *  ncre nt a feature  f  s not at  s max mum value.
   * @return w t r t  feature  s  ncre nted.
   */
  publ c boolean  ncre nt fNotMax mum(FeatureConf gurat on feature) {
     nt newValue = getFeatureValue(feature) + 1;
     f (newValue <= feature.getMaxValue()) {
      setFeatureValue(feature, newValue);
      return true;
    } else {
      return false;
    }
  }

  /**
   * Copy t se encoded features to a new PackedFeatures thr ft struct.
   */
  publ c PackedFeatures copyToPackedFeatures() {
    return copyToPackedFeatures(new PackedFeatures());
  }

  /**
    * Copy t se encoded features to a PackedFeatures thr ft struct.
    */
  publ c PackedFeatures copyToPackedFeatures(PackedFeatures packedFeatures) {
    Precond  ons.c ckNotNull(packedFeatures);
    f nal L st< nteger>  ntegers = L sts.newArrayL stW hCapac y(getNum nts());
    for ( nt   = 0;   < getNum nts();  ++) {
       ntegers.add(get nt( ));
    }
    packedFeatures.setDeprecated_featureConf gurat onVers on(0);
    packedFeatures.setFeatures( ntegers);
    return packedFeatures;
  }

  /**
   * Copy features from a packed features struct.
   */
  publ c vo d readFromPackedFeatures(PackedFeatures packedFeatures) {
    Precond  ons.c ckNotNull(packedFeatures);
    L st< nteger>  nts = packedFeatures.getFeatures();
    for ( nt   = 0;   < getNum nts();  ++) {
       f (  <  nts.s ze()) {
        set nt( ,  nts.get( ));
      } else {
        set nt( , 0);
      }
    }
  }
}
