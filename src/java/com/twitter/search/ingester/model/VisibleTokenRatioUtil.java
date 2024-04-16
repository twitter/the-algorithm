package com.tw ter.search. ngester.model;

 mport com.tw ter.common.text.token.Token zedCharSequenceStream;
 mport com.tw ter.common.text.token.attr bute.CharSequenceTermAttr bute;
 mport com.tw ter.search.common.relevance.text.V s bleTokenRat oNormal zer;

publ c class V s bleTokenRat oUt l {

  pr vate stat c f nal  nt TOKEN_DEMARCAT ON = 140;

  pr vate stat c f nal V s bleTokenRat oNormal zer NORMAL ZER =
      V s bleTokenRat oNormal zer.create nstance();

  /**
   * Take t  number of v s ble tokens and d v de by number of total tokens to get t 
   * v s ble token percentage (pretend ng 140 chars  s v s ble as that  s old typ cal t et
   * s ze).  T n normal ze   down to 4 b s(round   bas cally)
   */
  publ c  nt extractAndNormal zeTokenPercentage(Token zedCharSequenceStream tokenSeqStream) {

    CharSequenceTermAttr bute attr = tokenSeqStream.addAttr bute(CharSequenceTermAttr bute.class);

     nt totalTokens = 0;
     nt numTokensBelowThreshold = 0;
    wh le (tokenSeqStream. ncre ntToken()) {
      totalTokens++;
       nt offset = attr.getOffset();
       f (offset <= TOKEN_DEMARCAT ON) {
        numTokensBelowThreshold++;
      }
    }

    double percent;
     f (totalTokens > 0) {
      percent = numTokensBelowThreshold / (double) totalTokens;
    } else {
      percent = 1;
    }

    return NORMAL ZER.normal ze(percent);
  }
}
