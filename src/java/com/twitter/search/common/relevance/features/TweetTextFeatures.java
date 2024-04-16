package com.tw ter.search.common.relevance.features;

 mport java.ut l.Collect on;
 mport java.ut l.L st;
 mport java.ut l.Set;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.collect.Sets;

 mport com.tw ter.common.text.token.Token zedCharSequence;

publ c class T etTextFeatures {
  // Bas c Features, always extracted.
  // normal zed, lo r cased t et text, w/o resolved urls
  pr vate Str ng normal zedText;

  // tokens from normal zedText, w/o resolved urls, lo r cased.
  pr vate L st<Str ng> tokens;

  // tokens from resolved urls, lo r cased.
  pr vate L st<Str ng> resolvedUrlsTokens;

  // tokens  n t  form of a Token zedCharSeq, NOT LOWER CASED
  pr vate Token zedCharSequence tokenSequence;

  // str ppedTokens above jo ned w h space
  pr vate Str ng normal zedStr ppedText;

  // normal zed, or g nal case tokens, w hout @ nt on, #hashtag or urls.
  pr vate L st<Str ng> str ppedTokens;

  // all hash tags, w hout "#", lo r cased
  pr vate Set<Str ng> hashtags = Sets.newHashSet();

  // all  nt ons, w hout "@", lo r cased
  pr vate Set<Str ng>  nt ons = Sets.newHashSet();

  // w t r t  t et has a quest on mark that's not  n url.
  pr vate boolean hasQuest onMark = false;

  pr vate boolean hasPos  veSm ley = false;
  pr vate boolean hasNegat veSm ley = false;

  // normal zed, or g nal case sm leys
  pr vate L st<Str ng> sm leys;

  // lo r cased, normal zed stock na s, w hout "$"
  pr vate L st<Str ng> stocks;

  // Extra features for text qual y evaluat on only.
  pr vate  nt s gnature = T et ntegerSh ngleS gnature.DEFAULT_NO_S GNATURE;
  pr vate Set<Str ng> trend ngTerms = Sets.newHashSet();
  pr vate  nt length;
  pr vate  nt caps;

  publ c Str ng getNormal zedText() {
    return normal zedText;
  }

  publ c vo d setNormal zedText(Str ng normal zedText) {
    t .normal zedText = normal zedText;
  }

  publ c L st<Str ng> getTokens() {
    return tokens;
  }

  publ c  nt getTokensS ze() {
    return tokens == null ? 0 : tokens.s ze();
  }

  publ c vo d setTokens(L st<Str ng> tokens) {
    t .tokens = tokens;
  }

  publ c L st<Str ng> getResolvedUrlTokens() {
    return resolvedUrlsTokens;
  }

  publ c  nt getResolvedUrlTokensS ze() {
    return resolvedUrlsTokens == null ? 0 : resolvedUrlsTokens.s ze();
  }

  publ c vo d setResolvedUrlTokens(L st<Str ng> tokensResolvedUrls) {
    t .resolvedUrlsTokens = tokensResolvedUrls;
  }

  publ c Token zedCharSequence getTokenSequence() {
    return tokenSequence;
  }

  publ c vo d setTokenSequence(Token zedCharSequence tokenSequence) {
    t .tokenSequence = tokenSequence;
  }

  publ c Str ng getNormal zedStr ppedText() {
    return normal zedStr ppedText;
  }

  publ c vo d setNormal zedStr ppedText(Str ng normal zedStr ppedText) {
    t .normal zedStr ppedText = normal zedStr ppedText;
  }

  publ c L st<Str ng> getStr ppedTokens() {
    return str ppedTokens;
  }

  publ c  nt getStr ppedTokensS ze() {
    return str ppedTokens == null ? 0 : str ppedTokens.s ze();
  }

  publ c vo d setStr ppedTokens(L st<Str ng> str ppedTokens) {
    t .str ppedTokens = str ppedTokens;
  }

  publ c Set<Str ng> getHashtags() {
    return hashtags;
  }

  publ c  nt getHashtagsS ze() {
    return hashtags.s ze();
  }

  publ c vo d setHashtags(Collect on<Str ng> hashtags) {
    t .hashtags = Sets.newHashSet(hashtags);
  }

  publ c Set<Str ng> get nt ons() {
    return  nt ons;
  }

  publ c  nt get nt onsS ze() {
    return  nt ons.s ze();
  }

  publ c vo d set nt ons(Collect on<Str ng>  nt ons) {
    t . nt ons = Sets.newHashSet( nt ons);
  }

  publ c boolean hasQuest onMark() {
    return hasQuest onMark;
  }

  publ c vo d setHasQuest onMark(boolean hasQuest onMark) {
    t .hasQuest onMark = hasQuest onMark;
  }

  publ c boolean hasPos  veSm ley() {
    return hasPos  veSm ley;
  }

  publ c vo d setHasPos  veSm ley(boolean hasPos  veSm ley) {
    t .hasPos  veSm ley = hasPos  veSm ley;
  }

  publ c boolean hasNegat veSm ley() {
    return hasNegat veSm ley;
  }

  publ c vo d setHasNegat veSm ley(boolean hasNegat veSm ley) {
    t .hasNegat veSm ley = hasNegat veSm ley;
  }

  publ c L st<Str ng> getSm leys() {
    return sm leys;
  }

  publ c  nt getSm leysS ze() {
    return sm leys == null ? 0 : sm leys.s ze();
  }

  publ c vo d setSm leys(L st<Str ng> sm leys) {
    t .sm leys = sm leys;
  }

  publ c L st<Str ng> getStocks() {
    return stocks;
  }

  publ c  nt getStocksS ze() {
    return stocks == null ? 0 : stocks.s ze();
  }

  publ c vo d setStocks(L st<Str ng> stocks) {
    t .stocks = stocks;
  }

  publ c  nt getS gnature() {
    return s gnature;
  }

  publ c vo d setS gnature( nt s gnature) {
    t .s gnature = s gnature;
  }

  /** Returns t  trend ng terms. */
  publ c Set<Str ng> getTrend ngTerms() {
    return trend ngTerms;
  }

  publ c  nt getTrend ngTermsS ze() {
    return trend ngTerms.s ze();
  }

  @V s bleForTest ng
  publ c vo d setTrend ngTerms(Set<Str ng> trend ngTerms) {
    t .trend ngTerms = trend ngTerms;
  }

  publ c  nt getLength() {
    return length;
  }

  publ c vo d setLength( nt length) {
    t .length = length;
  }

  publ c  nt getCaps() {
    return caps;
  }

  publ c vo d setCaps( nt caps) {
    t .caps = caps;
  }
}
