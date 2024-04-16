package com.tw ter.search.earlyb rd.queryparser;

 mport java.ut l.ArrayL st;
 mport java.ut l.L st;
 mport java.ut l.Set;

 mport com.google.common.collect.Sets;

/**
 * Used to store  nformat on relevant to process ng query groups for H ghFrequencyTermPa rExtractor
 * and H ghFrequencyTermPa rRewr er
 */
publ c class H ghFrequencyTermQueryGroup {
  protected f nal  nt group dx;
  protected f nal  nt parentGroup dx;
  // T  number of nodes  n t  group.
  protected  nt num mbers = 0;
  // For t  rewr e v s or:  ncre nted once at t  end of each of t  group's nodes' v s s.
  protected  nt numV s s = 0;

  // T  set of tokens that should be removed from t  query  f seen as an  nd v dual term and
  // rewr ten  n t  query as a hf term pa r.
  protected f nal Set<Str ng> hfTokens = Sets.newTreeSet();

  // Tokens that can be used to restr ct searc s but should not be scored. T y w ll be g ven a
  //   ght of 0.
  protected f nal Set<Str ng> preusedHFTokens = Sets.newTreeSet();

  // Set of phrases that should be removed from t  query  f seen as an  nd v dual phrase and
  // rewr ten  n t  query as a hf term phrase pa r.
  protected f nal Set<Str ng> hfPhrases = Sets.newTreeSet();

  // Phrases that can be used to restr ct searc s but should not be scored. T y w ll be g ven a
  //   ght of 0.
  protected f nal Set<Str ng> preusedHFPhrases = Sets.newTreeSet();

  // T  f rst found hf_term, or t  hf_term of an ancestor w h t  sa   sPos  ve value.
  protected Str ng d str but veToken = null;

  //  f    s a s ngle node group,  sPos  ve  s true  ff that node  s true.
  // Ot rw se,  sPos  ve  s false  ff t  root of t  group  s a d sjunct on.
  protected f nal boolean  sPos  ve;

  publ c H ghFrequencyTermQueryGroup( nt group dx, boolean pos  ve) {
    t (group dx, -1, pos  ve);
  }

  publ c H ghFrequencyTermQueryGroup( nt group dx,  nt parentGroup dx, boolean pos  ve) {
    t .group dx = group dx;
    t .parentGroup dx = parentGroup dx;
     sPos  ve = pos  ve;
  }

  publ c boolean hasPhrases() {
    return !hfPhrases. sEmpty() || !preusedHFPhrases. sEmpty();
  }

  protected L st<Str ng> tokensFromPhrases() {
     f (!hasPhrases()) {
      return null;
    }
    L st<Str ng> tokens = new ArrayL st<>();
    for (Str ng phrase : hfPhrases) {
      for (Str ng term : phrase.spl (" ")) {
        tokens.add(term);
      }
    }
    for (Str ng phrase : preusedHFPhrases) {
      for (Str ng term : phrase.spl (" ")) {
        tokens.add(term);
      }
    }
    return tokens;
  }

  protected vo d removePreusedTokens() {
    hfTokens.removeAll(preusedHFTokens);
    L st<Str ng> phraseTokens = tokensFromPhrases();
     f (phraseTokens != null) {
      hfTokens.removeAll(phraseTokens);
      preusedHFTokens.removeAll(phraseTokens);
    }
    hfPhrases.removeAll(preusedHFPhrases);
  }

  protected Str ng getTokenFromPhrase() {
    L st<Str ng> phraseTokens = tokensFromPhrases();
     f (phraseTokens != null) {
      return phraseTokens.get(0);
    } else {
      return null;
    }
  }
}
