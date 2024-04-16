package com.tw ter.search.earlyb rd_root. rgers;

/**
 * Tracks what s uat ons are encountered w n tr mm ng results
 */
class Tr mStats {
  protected stat c f nal Tr mStats EMPTY_STATS = new Tr mStats();

  pr vate  nt max dF lterCount = 0;
  pr vate  nt m n dF lterCount = 0;
  pr vate  nt removedDupsCount = 0;
  pr vate  nt resultsTruncatedFromTa lCount = 0;

   nt getM n dF lterCount() {
    return m n dF lterCount;
  }

   nt getRemovedDupsCount() {
    return removedDupsCount;
  }

   nt getResultsTruncatedFromTa lCount() {
    return resultsTruncatedFromTa lCount;
  }

  vo d decreaseMax dF lterCount() {
    max dF lterCount--;
  }

  vo d decreaseM n dF lterCount() {
    m n dF lterCount--;
  }

  publ c vo d clearMax dF lterCount() {
    t .max dF lterCount = 0;
  }

  publ c vo d clearM n dF lterCount() {
    t .m n dF lterCount = 0;
  }

  vo d  ncreaseMax dF lterCount() {
    max dF lterCount++;
  }

  vo d  ncreaseM n dF lterCount() {
    m n dF lterCount++;
  }

  vo d  ncreaseRemovedDupsCount() {
    removedDupsCount++;
  }

  vo d setResultsTruncatedFromTa lCount( nt resultsTruncatedFromTa lCount) {
    t .resultsTruncatedFromTa lCount = resultsTruncatedFromTa lCount;
  }

  @Overr de
  publ c Str ng toStr ng() {
    Str ngBu lder bu lder = new Str ngBu lder();

    bu lder.append("Tr mStats{");
    bu lder.append("max dF lterCount=").append(max dF lterCount);
    bu lder.append(", m n dF lterCount=").append(m n dF lterCount);
    bu lder.append(", removedDupsCount=").append(removedDupsCount);
    bu lder.append(", resultsTruncatedFromTa lCount=").append(resultsTruncatedFromTa lCount);
    bu lder.append("}");

    return bu lder.toStr ng();
  }
}
