package com.tw ter.v s b l y.models

 mport com.tw ter.content alth.sens  ve d asett ngs.thr ftscala.Sens  ve d aSett ngs


case class UserSens  ve d aSett ngs(sens  ve d aSett ngs: Opt on[Sens  ve d aSett ngs]) {

  def unapply(
    userSens  ve d aSett ngs: UserSens  ve d aSett ngs
  ): Opt on[Sens  ve d aSett ngs] = {
    sens  ve d aSett ngs
  }
}
