package com.tw ter.search.common.relevance.features;

 mport java.ut l.Map;

publ c class T etUserFeatures {
  pr vate Str ng lang;
  pr vate double langConf dence;
  pr vate  nt follo rs;
  pr vate  nt follow ng;
  pr vate  nt reputat on;
  pr vate  nt t ets;
  pr vate  nt ret ets;
  pr vate  nt ret eted;
  pr vate Map<Str ng, Double> knownForTop cs;
  pr vate boolean  sSpam;
  pr vate boolean  sNsfw;
  pr vate boolean  sBot;

  publ c Str ng getLang() {
    return lang;
  }

  publ c vo d setLang(Str ng lang) {
    t .lang = lang;
  }

  publ c double getLangConf dence() {
    return langConf dence;
  }

  publ c vo d setLangConf dence(double langConf dence) {
    t .langConf dence = langConf dence;
  }

  publ c  nt getFollo rs() {
    return follo rs;
  }

  publ c vo d setFollo rs( nt follo rs) {
    t .follo rs = follo rs;
  }

  publ c  nt getFollow ng() {
    return follow ng;
  }

  publ c vo d setFollow ng( nt follow ng) {
    t .follow ng = follow ng;
  }

  publ c  nt getReputat on() {
    return reputat on;
  }

  publ c vo d setReputat on( nt reputat on) {
    t .reputat on = reputat on;
  }

  publ c  nt getT ets() {
    return t ets;
  }

  publ c vo d setT ets( nt t ets) {
    t .t ets = t ets;
  }

  publ c  nt getRet ets() {
    return ret ets;
  }

  publ c vo d setRet ets( nt ret ets) {
    t .ret ets = ret ets;
  }

  publ c  nt getRet eted() {
    return ret eted;
  }

  publ c vo d setRet eted( nt ret eted) {
    t .ret eted = ret eted;
  }

  publ c Map<Str ng, Double> getKnownForTop cs() {
    return knownForTop cs;
  }

  publ c vo d setKnownForTop cs(Map<Str ng, Double> knownForTop cs) {
    t .knownForTop cs = knownForTop cs;
  }

  publ c boolean  sSpam() {
    return  sSpam;
  }

  publ c vo d setSpam(boolean spam) {
     sSpam = spam;
  }

  publ c boolean  sNsfw() {
    return  sNsfw;
  }

  publ c vo d setNsfw(boolean nsfw) {
     sNsfw = nsfw;
  }

  publ c boolean  sBot() {
    return  sBot;
  }

  publ c vo d setBot(boolean bot) {
     sBot = bot;
  }
}
