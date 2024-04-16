package com.tw ter.search.common.sc ma.earlyb rd;

 mport java. o. OExcept on;
 mport java.ut l. erator;
 mport java.ut l.L st;

 mport com.google.common.collect. mmutableL st;

 mport com.tw ter.common.text.ut l.TokenStreamSer al zer;
 mport com.tw ter.search.common.sc ma.base. mmutableSc ma nterface;
 mport com.tw ter.search.common.sc ma.base.Thr ftDocu ntUt l;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftDocu nt;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftF eld;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftF eldData;
 mport com.tw ter.search.common.ut l.analys s. ntTermAttr buteSer al zer;
 mport com.tw ter.search.common.ut l.analys s.Tw terNormal zedM nEngage ntTokenStream;

/**
 * Ut l y AP s for Thr ftDocu nt used  n Earlyb rd.
 */
publ c f nal class Earlyb rdThr ftDocu ntUt l {
  pr vate stat c f nal Earlyb rdF eldConstants  D_MAPP NG = new Earlyb rdF eldConstants();

  pr vate stat c f nal Str ng F LTER_FORMAT_STR NG = "__f lter_%s";

  /**
   * Used to c ck w t r a thr ft docu nt has f lter nullcast  nternal f eld set.
   * @see # sNullcastF lterSet(Thr ftDocu nt)
   */
  pr vate stat c f nal Str ng NULLCAST_F LTER_TERM =
      formatF lter(Earlyb rdF eldConstant.NULLCAST_F LTER_TERM);

  pr vate stat c f nal Str ng SELF_THREAD_F LTER_TERM =
      formatF lter(Earlyb rdF eldConstant.SELF_THREAD_F LTER_TERM);

  pr vate stat c f nal Str ng D RECTED_AT_F LTER_TERM =
      formatF lter(Earlyb rdF eldConstant.D RECTED_AT_F LTER_TERM);

  pr vate Earlyb rdThr ftDocu ntUt l() {
    // Cannot  nstant ate.
  }

  /**
   * Formats a regular, s mple f lter term. T  'f lter' argu nt should correspond to a constant
   * from t  Operator class, match ng t  operand (f lter:l nks -> "l nks").
   */
  publ c stat c f nal Str ng formatF lter(Str ng f lter) {
    return Str ng.format(F LTER_FORMAT_STR NG, f lter);
  }

  /**
   * Get status  d.
   */
  publ c stat c long get D(Thr ftDocu nt docu nt) {
    return Thr ftDocu ntUt l.getLongValue(
        docu nt, Earlyb rdF eldConstant. D_F ELD.getF eldNa (),  D_MAPP NG);
  }

  /**
   * Get Card na .
   */
  publ c stat c Str ng getCardNa (Thr ftDocu nt docu nt) {
    return Thr ftDocu ntUt l.getStr ngValue(
        docu nt, Earlyb rdF eldConstant.CARD_NAME_F ELD.getF eldNa (),  D_MAPP NG);
  }

  /**
   * Get Card language.
   */
  publ c stat c Str ng getCardLang(Thr ftDocu nt docu nt) {
    return Thr ftDocu ntUt l.getStr ngValue(
        docu nt, Earlyb rdF eldConstant.CARD_LANG.getF eldNa (),  D_MAPP NG);
  }

  /**
   * Get Card language CSF.
   *
   * card language CSF  s represented  nternally as an  nteger  D for a Thr ftLanguage.
   */
  publ c stat c  nt getCardLangCSF(Thr ftDocu nt docu nt) {
    return Thr ftDocu ntUt l.get ntValue(
        docu nt, Earlyb rdF eldConstant.CARD_LANG_CSF.getF eldNa (),  D_MAPP NG);
  }

  /**
   * Get quoted t et  d.
   */
  publ c stat c long getQuotedT et D(Thr ftDocu nt docu nt) {
    return Thr ftDocu ntUt l.getLongValue(
        docu nt, Earlyb rdF eldConstant.QUOTED_TWEET_ D_F ELD.getF eldNa (),  D_MAPP NG);
  }

  /**
   * Get quoted t et user  d.
   */
  publ c stat c long getQuotedUser D(Thr ftDocu nt docu nt) {
    return Thr ftDocu ntUt l.getLongValue(
        docu nt, Earlyb rdF eldConstant.QUOTED_USER_ D_F ELD.getF eldNa (),  D_MAPP NG);
  }

  /**
   * Get d rected at user  d.
   */
  publ c stat c long getD rectedAtUser d(Thr ftDocu nt docu nt) {
    return Thr ftDocu ntUt l.getLongValue(
        docu nt, Earlyb rdF eldConstant.D RECTED_AT_USER_ D_F ELD.getF eldNa (),  D_MAPP NG);
  }

  /**
   * Get d rected at user  d CSF.
   */
  publ c stat c long getD rectedAtUser dCSF(Thr ftDocu nt docu nt) {
    return Thr ftDocu ntUt l.getLongValue(
        docu nt, Earlyb rdF eldConstant.D RECTED_AT_USER_ D_CSF.getF eldNa (),  D_MAPP NG);
  }

  /**
   * Get reference author  d CSF.
   */
  publ c stat c long getReferenceAuthor dCSF(Thr ftDocu nt docu nt) {
    return Thr ftDocu ntUt l.getLongValue(
        docu nt, Earlyb rdF eldConstant.REFERENCE_AUTHOR_ D_CSF.getF eldNa (),  D_MAPP NG);
  }

  /**
   * Get l nks.
   */
  publ c stat c L st<Str ng> getL nks(Thr ftDocu nt docu nt) {
    return getStr ngValues(docu nt, Earlyb rdF eldConstant.L NKS_F ELD);
  }

  /**
   * Get created at t  stamp  n sec.
   */
  publ c stat c  nt getCreatedAtSec(Thr ftDocu nt docu nt) {
    return Thr ftDocu ntUt l.get ntValue(
        docu nt, Earlyb rdF eldConstant.CREATED_AT_F ELD.getF eldNa (),  D_MAPP NG);
  }

  /**
   * Get created at t  stamp  n ms.
   */
  publ c stat c long getCreatedAtMs(Thr ftDocu nt docu nt) {
    long createdAtSec = (long) getCreatedAtSec(docu nt);
    return createdAtSec * 1000L;
  }

  /**
   * Get from user  d.
   */
  publ c stat c long getFromUser D(Thr ftDocu nt docu nt) {
    return Thr ftDocu ntUt l.getLongValue(
        docu nt, Earlyb rdF eldConstant.FROM_USER_ D_F ELD.getF eldNa (),  D_MAPP NG);
  }

  /**
   * Get from user.
   */
  publ c stat c Str ng getFromUser(Thr ftDocu nt docu nt) {
    return Thr ftDocu ntUt l.getStr ngValue(
        docu nt, Earlyb rdF eldConstant.FROM_USER_F ELD.getF eldNa (),  D_MAPP NG);
  }

  /**
   * Get token zed from user d splay na .
   */
  publ c stat c Str ng getFromUserD splayNa (Thr ftDocu nt docu nt) {
    return Thr ftDocu ntUt l.getStr ngValue(
        docu nt, Earlyb rdF eldConstant.TOKEN ZED_USER_NAME_F ELD.getF eldNa (),  D_MAPP NG);
  }

  /**
   * Get token zed from user.
   */
  publ c stat c Str ng getToken zedFromUser(Thr ftDocu nt docu nt) {
    return Thr ftDocu ntUt l.getStr ngValue(
        docu nt, Earlyb rdF eldConstant.TOKEN ZED_FROM_USER_F ELD.getF eldNa (),  D_MAPP NG);
  }

  /**
   * Get resolved l nks text.
   */
  publ c stat c Str ng getResolvedL nksText(Thr ftDocu nt docu nt) {
    return Thr ftDocu ntUt l.getStr ngValue(
        docu nt, Earlyb rdF eldConstant.RESOLVED_L NKS_TEXT_F ELD.getF eldNa (),  D_MAPP NG);
  }

  /**
   * Get  so language code.
   */
  publ c stat c L st<Str ng> get SOLanguage(Thr ftDocu nt docu nt) {
    return Thr ftDocu ntUt l.getStr ngValues(
        docu nt, Earlyb rdF eldConstant. SO_LANGUAGE_F ELD.getF eldNa (),  D_MAPP NG);
  }

  /**
   * F rst remove t  old t  stamp  f t y ex st.
   * T n add t  created at and created at csf f elds to t  g ven thr ft docu nt.
   */
  publ c stat c vo d replaceCreatedAtAndCreatedAtCSF(Thr ftDocu nt docu nt,  nt value) {
    removeF eld(docu nt, Earlyb rdF eldConstant.CREATED_AT_F ELD);
    removeF eld(docu nt, Earlyb rdF eldConstant.CREATED_AT_CSF_F ELD);

    add ntF eld(docu nt, Earlyb rdF eldConstant.CREATED_AT_F ELD, value);
    add ntF eld(docu nt, Earlyb rdF eldConstant.CREATED_AT_CSF_F ELD, value);
  }

  /**
   * Add t  g ven  nt value as t  g ven f eld  nto t  g ven docu nt.
   */
  publ c stat c Thr ftDocu nt add ntF eld(
      Thr ftDocu nt docu nt, Earlyb rdF eldConstant f eldConstant,  nt value) {
    Thr ftF eldData f eldData = new Thr ftF eldData().set ntValue(value);
    Thr ftF eld f eld =
        new Thr ftF eld().setF eldConf g d(f eldConstant.getF eld d()).setF eldData(f eldData);
    docu nt.addToF elds(f eld);
    return docu nt;
  }

  pr vate stat c Earlyb rdF eldConstant getFeatureF eld(Earlyb rdF eldConstant f eld) {
     f (f eld.getF eldNa ().startsW h(
        Earlyb rdF eldConstant.ENCODED_TWEET_FEATURES_F ELD.getF eldNa ())) {
      return Earlyb rdF eldConstant.ENCODED_TWEET_FEATURES_F ELD;
    } else  f (f eld.getF eldNa ().startsW h(
        Earlyb rdF eldConstant.EXTENDED_ENCODED_TWEET_FEATURES_F ELD.getF eldNa ())) {
      return Earlyb rdF eldConstant.EXTENDED_ENCODED_TWEET_FEATURES_F ELD;
    } else {
      throw new  llegalArgu ntExcept on("Not a feature f eld: " + f eld);
    }
  }

  /**
   * Get t  feature value of a f eld.
   */
  publ c stat c  nt getFeatureValue(
       mmutableSc ma nterface sc ma,
      Thr ftDocu nt docu nt,
      Earlyb rdF eldConstant f eld) {

    Earlyb rdF eldConstant featureF eld = getFeatureF eld(f eld);

    byte[] encodedFeaturesBytes =
        Thr ftDocu ntUt l.getBytesValue(docu nt, featureF eld.getF eldNa (),  D_MAPP NG);

     f (encodedFeaturesBytes == null) {
      // Treat t  feature value as 0  f t re  s no encoded feature f eld.
      return 0;
    } else {
      Earlyb rdEncodedFeatures encodedFeatures = Earlyb rdEncodedFeaturesUt l.fromBytes(
          sc ma, featureF eld, encodedFeaturesBytes, 0);
      return encodedFeatures.getFeatureValue(f eld);
    }
  }

  /**
   * C ck w t r t  feature flag  s set.
   */
  publ c stat c boolean  sFeatureB Set(
       mmutableSc ma nterface sc ma,
      Thr ftDocu nt docu nt,
      Earlyb rdF eldConstant f eld) {

    Earlyb rdF eldConstant featureF eld = getFeatureF eld(f eld);

    byte[] encodedFeaturesBytes =
        Thr ftDocu ntUt l.getBytesValue(docu nt, featureF eld.getF eldNa (),  D_MAPP NG);

     f (encodedFeaturesBytes == null) {
      // Treat t  b  as not set  f t re  s no encoded feature f eld.
      return false;
    } else {
      Earlyb rdEncodedFeatures encodedFeatures = Earlyb rdEncodedFeaturesUt l.fromBytes(
          sc ma, featureF eld, encodedFeaturesBytes, 0);
      return encodedFeatures. sFlagSet(f eld);
    }
  }

  /**
   * C ck w t r nullcast flag  s set  n t  encoded features f eld.
   */
  publ c stat c boolean  sNullcastB Set( mmutableSc ma nterface sc ma, Thr ftDocu nt docu nt) {
    return  sFeatureB Set(sc ma, docu nt, Earlyb rdF eldConstant. S_NULLCAST_FLAG);
  }

  /**
   * Remove all f elds w h t  g ven f eld constant  n a docu nt.
   */
  publ c stat c vo d removeF eld(Thr ftDocu nt docu nt, Earlyb rdF eldConstant f eldConstant) {
    L st<Thr ftF eld> f elds = docu nt.getF elds();
     f (f elds != null) {
       erator<Thr ftF eld> f elds erator = f elds. erator();
      wh le (f elds erator.hasNext()) {
         f (f elds erator.next().getF eldConf g d() == f eldConstant.getF eld d()) {
          f elds erator.remove();
        }
      }
    }
  }

  /**
   * Remove a str ng f eld w h g ven f eldConstant and value.
   */
  publ c stat c vo d removeStr ngF eld(
      Thr ftDocu nt docu nt, Earlyb rdF eldConstant f eldConstant, Str ng value) {
    L st<Thr ftF eld> f elds = docu nt.getF elds();
     f (f elds != null) {
      for (Thr ftF eld f eld : f elds) {
         f (f eld.getF eldConf g d() == f eldConstant.getF eld d()
            && f eld.getF eldData().getStr ngValue().equals(value)) {
          f elds.remove(f eld);
          return;
        }
      }
    }
  }

  /**
   * Adds a new TokenStream f eld for each engage nt counter  f normal zedNumEngage nts >= 1.
   */
  publ c stat c vo d addNormal zedM nEngage ntF eld(
      Thr ftDocu nt doc,
      Str ng f eldNa ,
       nt normal zedNumEngage nts) throws  OExcept on {
     f (normal zedNumEngage nts < 1) {
      return;
    }
    TokenStreamSer al zer ser al zer =
        new TokenStreamSer al zer( mmutableL st.of(new  ntTermAttr buteSer al zer()));
    Tw terNormal zedM nEngage ntTokenStream stream = new
        Tw terNormal zedM nEngage ntTokenStream(normal zedNumEngage nts);
    byte[] ser al zedStream = ser al zer.ser al ze(stream);
    Thr ftF eldData f eldData = new Thr ftF eldData().setTokenStreamValue(ser al zedStream);
    Thr ftF eld f eld = new Thr ftF eld().setF eldConf g d( D_MAPP NG.getF eld D(f eldNa ))
        .setF eldData(f eldData);
    doc.addToF elds(f eld);
  }

  publ c stat c L st<Str ng> getStr ngValues(
      Thr ftDocu nt docu nt, Earlyb rdF eldConstant f eld) {
    return Thr ftDocu ntUt l.getStr ngValues(docu nt, f eld.getF eldNa (),  D_MAPP NG);
  }

  publ c stat c boolean  sNullcastF lterSet(Thr ftDocu nt docu nt) {
    return  sF lterSet(docu nt, NULLCAST_F LTER_TERM);
  }

  publ c stat c boolean  sSelfThreadF lterSet(Thr ftDocu nt docu nt) {
    return  sF lterSet(docu nt, SELF_THREAD_F LTER_TERM);
  }

  publ c stat c Str ng getSelfThreadF lterTerm() {
    return SELF_THREAD_F LTER_TERM;
  }

  publ c stat c Str ng getD rectedAtF lterTerm() {
    return D RECTED_AT_F LTER_TERM;
  }

  publ c stat c boolean  sD rectedAtF lterSet(Thr ftDocu nt docu nt) {
    return  sF lterSet(docu nt, D RECTED_AT_F LTER_TERM);
  }

  /**
   * C ck w t r g ven f lter  s set  n t   nternal f eld.
   */
  pr vate stat c boolean  sF lterSet(Thr ftDocu nt docu nt, Str ng f lter) {
    L st<Str ng> terms = Thr ftDocu ntUt l.getStr ngValues(
        docu nt, Earlyb rdF eldConstant. NTERNAL_F ELD.getF eldNa (),  D_MAPP NG);
    for (Str ng term : terms) {
       f (f lter.equals(term)) {
        return true;
      }
    }
    return false;
  }
}
