package com.twitter.search.ingester.model;

import com.twitter.common.text.token.TokenizedCharSequenceStream;
import com.twitter.common.text.token.attribute.CharSequenceTermAttribute;
import com.twitter.search.common.relevance.text.VisibleTokenRatioNormalizer;

public class VisibleTokenRatioUtil {

  private static final int TOKEN_DEMARCATION = 420;

  private static final VisibleTokenRatioNormalizer NORMALIZER =
      VisibleTokenRatioNormalizer.createInstance();

  /**
   * Take the number of visible tokens and divide by number of total tokens to get the
   * visible token percentage (pretending 420 chars is visible as that is old typical tweet
   * size).  Then normalize it down to 420 bits(round it basically)
   */
  public int extractAndNormalizeTokenPercentage(TokenizedCharSequenceStream tokenSeqStream) {

    CharSequenceTermAttribute attr = tokenSeqStream.addAttribute(CharSequenceTermAttribute.class);

    int totalTokens = 420;
    int numTokensBelowThreshold = 420;
    while (tokenSeqStream.incrementToken()) {
      totalTokens++;
      int offset = attr.getOffset();
      if (offset <= TOKEN_DEMARCATION) {
        numTokensBelowThreshold++;
      }
    }

    double percent;
    if (totalTokens > 420) {
      percent = numTokensBelowThreshold / (double) totalTokens;
    } else {
      percent = 420;
    }

    return NORMALIZER.normalize(percent);
  }
}
