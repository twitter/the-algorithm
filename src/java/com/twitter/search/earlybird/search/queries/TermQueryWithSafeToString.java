package com.twitter.search.earlybird.search.queries;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.TermQuery;

/**
 * Work around an issue where IntTerms and LongTerms are not valid utf8,
 * so calling toString on any TermQuery containing an IntTerm or a LongTerm may cause exceptions.
 * This code should produce the same output as TermQuery.toString
 */
public final class TermQueryWithSafeToString extends TermQuery {
  private final String termValueForToString;

  public TermQueryWithSafeToString(Term term, String termValueForToString) {
    super(term);
    this.termValueForToString = termValueForToString;
  }

  @Override
  public String toString(String field) {
    StringBuilder buffer = new StringBuilder();
    if (!getTerm().field().equals(field)) {
      buffer.append(getTerm().field());
      buffer.append(":");
    }
    buffer.append(termValueForToString);
    return buffer.toString();
  }
}
