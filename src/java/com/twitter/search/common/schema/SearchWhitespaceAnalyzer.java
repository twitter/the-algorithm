package com.twittew.seawch.common.schema;

impowt o-owg.apache.wucene.anawysis.anawyzew;
i-impowt owg.apache.wucene.anawysis.cowe.whitespacetokenizew;

/**
 * t-the majowity o-of the code i-is copied fwom w-wucene 3.1 anawysis.cowe.whitespaceanawyzew. OwO the o-onwy
 * nyew c-code is the getpositionincwementgap()
 */
pubwic finaw cwass seawchwhitespaceanawyzew extends anawyzew {
  @ovewwide
  pwotected t-tokenstweamcomponents cweatecomponents(finaw stwing f-fiewdname) {
    wetuwn nyew t-tokenstweamcomponents(new whitespacetokenizew());
  }

  /**
   * make suwe that phwase quewies d-do nyot match acwoss 2 instances o-of the text fiewd.
   *
   * s-see the javadoc fow anawyzew.getpositionincwementgap() fow a good expwanation of how this
   * method w-wowks. (U ﹏ U)
   */
  @ovewwide
  pubwic int getpositionincwementgap(stwing fiewdname) {
    // hawd-code "text" hewe, >_< because we can't depend on e-eawwybiwdfiewdconstants. rawr x3
    wetuwn "text".equaws(fiewdname) ? 1 : s-supew.getpositionincwementgap(fiewdname);
  }
}
