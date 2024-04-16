package com.tw ter.v s b l y. nterfaces.blender

 mport com.tw ter.t etyp e.thr ftscala.T et
 mport com.tw ter.v s b l y.models.SafetyLevel
 mport com.tw ter.v s b l y.models.V e rContext
 mport com.tw ter.v s b l y. nterfaces.common.blender.BlenderVFRequestContext

case class BlenderV s b l yRequest(
  t et: T et,
  quotedT et: Opt on[T et],
  ret etS ceT et: Opt on[T et] = None,
   sRet et: Boolean,
  safetyLevel: SafetyLevel,
  v e rContext: V e rContext,
  blenderVFRequestContext: BlenderVFRequestContext) {

  def getT et D: Long = t et. d

  def hasQuotedT et: Boolean = {
    quotedT et.nonEmpty
  }
  def hasS ceT et: Boolean = {
    ret etS ceT et.nonEmpty
  }

  def getQuotedT et d: Long = {
    quotedT et match {
      case So (qT et) =>
        qT et. d
      case None =>
        -1
    }
  }
  def getS ceT et d: Long = {
    ret etS ceT et match {
      case So (s ceT et) =>
        s ceT et. d
      case None =>
        -1
    }
  }
}
