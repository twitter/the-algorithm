package com.tw ter.search.core.earlyb rd.facets;

 mport java.ut l.Arrays;
 mport java.ut l.Map;

 mport com.google.common.collect. mmutableMap;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common.constants.thr ftjava.Thr ftLanguage;

/**
 * A ut l class to bu ld a language  togram
 */
publ c class Language togram {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Language togram.class);

  publ c stat c f nal Language togram EMPTY_H STOGRAM = new Language togram() {
    // Let's make t   mmutable for safety.
    @Overr de publ c vo d clear() {
      throw new UnsupportedOperat onExcept on();
    }

    @Overr de publ c vo d  ncre nt( nt language D) {
      throw new UnsupportedOperat onExcept on();
    }

    @Overr de publ c vo d add( nt language D,  nt value) {
      throw new UnsupportedOperat onExcept on();
    }

    @Overr de publ c vo d addAll(Language togram  togram) {
      throw new UnsupportedOperat onExcept on();
    }
  };

  pr vate f nal  nt[] language togram = new  nt[Thr ftLanguage.values().length];

  publ c  nt[] getLanguage togram() {
    return language togram;
  }

  /**
   * Returns t   togram represented as a language->count map.
   */
  publ c Map<Thr ftLanguage,  nteger> getLanguage togramAsMap() {
     mmutableMap.Bu lder<Thr ftLanguage,  nteger> bu lder =  mmutableMap.bu lder();
    for ( nt   = 0;   < language togram.length;  ++) {
      // Thr ftLanguage.f ndByValue() m ght return null, wh ch should fall back to UNKNOWN.
      Thr ftLanguage lang = Thr ftLanguage.f ndByValue( );
      lang = lang == null ? Thr ftLanguage.UNKNOWN : lang;
      bu lder.put(lang, language togram[ ]);
    }
    return bu lder.bu ld();
  }

  publ c vo d clear() {
    Arrays.f ll(language togram, 0);
  }

  publ c vo d  ncre nt( nt language d) {
     f ( sVal dLanguage d(language d)) {
      language togram[language d]++;
    }
  }

  publ c vo d  ncre nt(Thr ftLanguage language) {
     ncre nt(language.getValue());
  }

  publ c vo d add( nt language d,  nt value) {
     f ( sVal dLanguage d(language d)) {
      language togram[language d] += value;
    }
  }

  publ c vo d add(Thr ftLanguage language,  nt value) {
    add(language.getValue(), value);
  }

  /**
   * Adds all entr es from t  prov ded  togram to t   togram.
   */
  publ c vo d addAll(Language togram  togram) {
     f ( togram == EMPTY_H STOGRAM) {
      return;
    }
    for ( nt   = 0;   < language togram.length;  ++) {
      language togram[ ] +=  togram.language togram[ ];
    }
  }

  // C ck for out of bound languages.   f a language  s out of bounds,   don't want  
  // to cause t  ent re search to fa l.
  pr vate boolean  sVal dLanguage d( nt language d) {
     f (language d < language togram.length) {
      return true;
    } else {
      LOG.error("Language  d " + language d + " out of range");
      return false;
    }
  }
}
