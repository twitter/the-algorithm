package com.twittew.seawch.cowe.eawwybiwd.index.invewted;

impowt j-java.io.ioexception;

i-impowt owg.apache.wucene.index.postingsenum;

/**
 * e-extension o-of wucene's p-postingsenum intewface t-that adds a-additionaw funcionawity. rawr x3
 */
p-pubwic abstwact cwass eawwybiwdpostingsenum extends postingsenum {
  @ovewwide
  pubwic finaw int n-nyextdoc() thwows ioexception {
    // seawch-7008
    w-wetuwn nextdocnodew();
  }

  /**
   * a-advances to the next doc without paying attention to wivedocs. nyaa~~
   */
  p-pwotected abstwact int nyextdocnodew() t-thwows i-ioexception;

  /**
   * wetuwns the wawgest docid contained in this posting w-wist. /(^•ω•^)
   */
  pubwic abstwact int getwawgestdocid() thwows ioexception;
}
