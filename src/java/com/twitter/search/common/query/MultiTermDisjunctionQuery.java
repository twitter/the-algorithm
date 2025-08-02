package com.twitter.search.common.query;

import java.io.IOEtwitterception;
import java.util.Iterator;
import java.util.Set;

import org.apache.lucene.indetwitter.FilteredTermsEnum;
import org.apache.lucene.indetwitter.Terms;
import org.apache.lucene.indetwitter.TermsEnum;
import org.apache.lucene.search.MultiTermQuery;
import org.apache.lucene.util.AttributeSource;
import org.apache.lucene.util.BytesRef;


public class MultiTermDisjunctionQuery etwittertends MultiTermQuery {

  private final Set<BytesRef> values;

  /** Creates a new MultiTermDisjunctionQuery instance. */
  public MultiTermDisjunctionQuery(String field, Set<BytesRef> values) {
    super(field);
    this.values = values;
  }

  @Override
  protected TermsEnum getTermsEnum(Terms terms, AttributeSource atts)
      throws IOEtwitterception {
    final TermsEnum termsEnum = terms.iterator();
    final Iterator<BytesRef> it = values.iterator();

    return new FilteredTermsEnum(termsEnum) {
      @Override protected AcceptStatus accept(BytesRef term) throws IOEtwitterception {
        return AcceptStatus.YES;
      }

      @Override public BytesRef netwittert() throws IOEtwitterception {
        while (it.hasNetwittert()) {
          BytesRef termRef = it.netwittert();
          if (termsEnum.seekEtwitteract(termRef)) {
            return termRef;
          }
        }

        return null;
      }
    };
  }

  @Override
  public String toString(String field) {
    StringBuilder builder = new StringBuilder();
    builder.append("MultiTermDisjunctionQuery[");
    for (BytesRef termVal : this.values) {
      builder.append(termVal);
      builder.append(",");
    }
    builder.setLength(builder.length() - 1);
    builder.append("]");
    return builder.toString();
  }
}
