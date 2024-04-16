package com.tw ter.search.common.relevance.ent  es;

 mport java.ut l.Locale;

 mport com.google.common.base.Precond  ons;

 mport org.apac .commons.lang.Str ngUt ls;

 mport com.tw ter.common_ nternal.text.vers on.Pengu nVers on;
 mport com.tw ter.search.common. ndex ng.thr ftjava.Potent alLocat on;
 mport com.tw ter.search.common.ut l.text.Language dent f er lper;
 mport com.tw ter.search.common.ut l.text.Normal zer lper;
 mport com.tw ter.search.common.ut l.text.Token zer lper;

/**
 * An  mmutable tuple to wrap a country code, reg on and local y. Based on t  Potent alLocat on
 * struct  n status.thr ft.
 */
publ c class Potent alLocat onObject {
  pr vate f nal Str ng countryCode;
  pr vate f nal Str ng reg on;
  pr vate f nal Str ng local y;

  /**
   * Creates a new Potent alLocat onObject  nstance.
   *
   * @param countryCode T  country code.
   * @param reg on T  reg on.
   * @param local y T  local y.
   */
  publ c Potent alLocat onObject(Str ng countryCode, Str ng reg on, Str ng local y) {
    t .countryCode = countryCode;
    t .reg on = reg on;
    t .local y = local y;
  }

  publ c Str ng getCountryCode() {
    return countryCode;
  }

  publ c Str ng getReg on() {
    return reg on;
  }

  publ c Str ng getLocal y() {
    return local y;
  }

  /**
   * Converts t  Potent alLocat onObject  nstance to a Potent alLocat on thr ft struct.
   *
   * @param pengu nVers on T  pengu n vers on to use for normal zat on and token zat on.
   */
  publ c Potent alLocat on toThr ftPotent alLocat on(Pengu nVers on pengu nVers on) {
    Precond  ons.c ckNotNull(pengu nVers on);

    Str ng normal zedCountryCode = null;
     f (countryCode != null) {
      Locale countryCodeLocale = Language dent f er lper. dent fyLanguage(countryCode);
      normal zedCountryCode =
          Normal zer lper.normal ze(countryCode, countryCodeLocale, pengu nVers on);
    }

    Str ng token zedReg on = null;
     f (reg on != null) {
      Locale reg onLocale = Language dent f er lper. dent fyLanguage(reg on);
      Str ng normal zedReg on = Normal zer lper.normal ze(reg on, reg onLocale, pengu nVers on);
      token zedReg on = Str ngUt ls.jo n(
          Token zer lper.token zeQuery(normal zedReg on, reg onLocale, pengu nVers on), " ");
    }

    Str ng token zedLocal y = null;
     f (local y != null) {
      Locale local yLocale = Language dent f er lper. dent fyLanguage(local y);
      Str ng normal zedLocal y =
          Normal zer lper.normal ze(local y, local yLocale, pengu nVers on);
      token zedLocal y =
          Str ngUt ls.jo n(Token zer lper.token zeQuery(
                               normal zedLocal y, local yLocale, pengu nVers on), " ");
    }

    return new Potent alLocat on()
        .setCountryCode(normal zedCountryCode)
        .setReg on(token zedReg on)
        .setLocal y(token zedLocal y);
  }

  @Overr de
  publ c  nt hashCode() {
    return ((countryCode == null) ? 0 : countryCode.hashCode())
        + 13 * ((reg on == null) ? 0 : reg on.hashCode())
        + 19 * ((local y == null) ? 0 : local y.hashCode());
  }

  @Overr de
  publ c boolean equals(Object obj) {
     f (!(obj  nstanceof Potent alLocat onObject)) {
      return false;
    }

    Potent alLocat onObject entry = (Potent alLocat onObject) obj;
    return (countryCode == null
            ? entry.countryCode == null
            : countryCode.equals(entry.countryCode))
        && (reg on == null
            ? entry.reg on == null
            : reg on.equals(entry.reg on))
        && (local y == null
            ? entry.local y == null
            : local y.equals(entry.local y));
  }

  @Overr de
  publ c Str ng toStr ng() {
    return new Str ngBu lder("Potent alLocat onObject {")
        .append("countryCode=").append(countryCode)
        .append(", reg on=").append(reg on)
        .append(", local y=").append(local y)
        .append("}")
        .toStr ng();
  }
}
