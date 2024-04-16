package com.tw ter.search.common.ut l.lang;

 mport java.lang.reflect.F eld;
 mport java.ut l.Locale;
 mport java.ut l.Map;

 mport javax.annotat on.Nullable;

 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect. mmutableMap;
 mport com.google.common.collect.Maps;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common.text.language.LocaleUt l;
 mport com.tw ter.search.common.constants.thr ftjava.Thr ftLanguage;

/**
 * T  class can be used to convert Thr ftLanguage to Locale object and v se versa.
 */
publ c f nal class Thr ftLanguageUt l {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Thr ftLanguageUt l.class.getNa ());

  // stores Thr ftLanguage. d -> Locale mapp ng
  pr vate stat c f nal Locale[] LOCALES;

  // stores Locale -> Thr ftLanguage mapp ng
  pr vate stat c f nal Map<Locale, Thr ftLanguage> THR FT_LANGUAGES;

  stat c {
    LOCALES = new Locale[Thr ftLanguage.values().length];
    Map<Locale, Thr ftLanguage> thr ftLanguageMap = Maps.newHashMap();

    // get all languages def ned  n Thr ftLanguage
    F eld[] f elds = Thr ftLanguage.class.getDeclaredF elds();
    for (F eld f eld : f elds) {
       f (!f eld. sEnumConstant()) {
        cont nue;
      }

      try {
        Thr ftLanguage thr ftLang = (Thr ftLanguage) f eld.get(null);
        Str ng thr ftLanguageNa  = f eld.getNa ();

        // get correspond ng Locale declared  n LocaleUt l
        try {
          F eld localeUt lF eld = LocaleUt l.class.getDeclaredF eld(thr ftLanguageNa );
          Locale localeLang = (Locale) localeUt lF eld.get(null);

          LOCALES[thr ftLang.getValue()] = localeLang;
          thr ftLanguageMap.put(localeLang, thr ftLang);
        } catch (NoSuchF eldExcept on e) {
          LOG.warn("{}  s def ned  n Thr ftLanguage, but not  n LocaleUt l.", thr ftLanguageNa );
        }
      } catch ( llegalAccessExcept on e) {
        // shouldn't happen.
        LOG.warn("Could not get a declared f eld.", e);
      }
    }

    // Let's make sure that all Locales def ned  n LocaleUt l are also def ned  n Thr ftLanguage
    for (Locale lang : LocaleUt l.getDef nedLanguages()) {
       f (!thr ftLanguageMap.conta nsKey(lang)) {
        LOG.warn("{}  s def ned  n LocaleUt l but not  n Thr ftLanguage.", lang.getLanguage());
      }
    }

    THR FT_LANGUAGES =  mmutableMap.copyOf(thr ftLanguageMap);
  }

  pr vate Thr ftLanguageUt l() {
  }

  /**
   * Returns a Locale object wh ch corresponds to a g ven Thr ftLanguage object.
   * @param language Thr ftLanguage object
   * @return a correspond ng Locale object
   */
  publ c stat c Locale getLocaleOf(Thr ftLanguage language) {
    // Note that Thr ftLanguage.f ndByValue() can return null (thr ft generated code).
    // So Thr ftLanguageUt l.getLocaleOf needs to handle null correctly.
     f (language == null) {
      return LocaleUt l.UNKNOWN;
    }

    Precond  ons.c ckArgu nt(language.getValue() < LOCALES.length);
    return LOCALES[language.getValue()];
  }

  /**
   * Returns a Thr ftLanguage object wh ch corresponds to a g ven Locale object.
   *
   * @param language Locale object
   * @return a correspond ng Thr ftLanguage object, or UNKNOWN  f t re's no correspond ng one.
   */
  publ c stat c Thr ftLanguage getThr ftLanguageOf(Locale language) {
    Precond  ons.c ckNotNull(language);
    Thr ftLanguage thr ftLang = THR FT_LANGUAGES.get(language);
    return thr ftLang == null ? Thr ftLanguage.UNKNOWN : thr ftLang;
  }

  /**
   * Returns a Thr ftLanguage object wh ch corresponds to a g ven language code.
   *
   * @param languageCode BCP-47 language code
   * @return a correspond ng Thr ftLanguage object, or UNKNOWN  f t re's no correspond ng one.
   */
  publ c stat c Thr ftLanguage getThr ftLanguageOf(Str ng languageCode) {
    Precond  ons.c ckNotNull(languageCode);
    Thr ftLanguage thr ftLang = THR FT_LANGUAGES.get(LocaleUt l.getLocaleOf(languageCode));
    return thr ftLang == null ? Thr ftLanguage.UNKNOWN : thr ftLang;
  }

  /**
   * Returns a Thr ftLanguage object wh ch corresponds to a g ven  nt value.
   *  f value  s not val d, returns Thr ftLanguage.UNKNOWN
   * @param value value of language
   * @return a correspond ng Thr ftLanguage object
   */
  publ c stat c Thr ftLanguage safeF ndByValue( nt value) {
    Thr ftLanguage thr ftLang = Thr ftLanguage.f ndByValue(value);
    return thr ftLang == null ? Thr ftLanguage.UNKNOWN : thr ftLang;
  }

  /**
   * Returns t  language code wh ch corresponds to a g ven Thr ftLanguage.
   *
   * Note that mult ple Thr ftLanguage entr es can return t  sa  language code.
   *
   * @param thr ftLang Thr ftLanguage object
   * @return Correspond ng language or null  f thr ftLang  s null.
   */
  @Nullable
  publ c stat c Str ng getLanguageCodeOf(@Nullable Thr ftLanguage thr ftLang) {
     f (thr ftLang == null) {
      return null;
    }
    return Thr ftLanguageUt l.getLocaleOf(thr ftLang).getLanguage();
  }
}
