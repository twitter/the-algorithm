packagelon com.twittelonr.follow_reloncommelonndations.common.modelonls

import com.twittelonr.finaglelon.tracing.Tracelon

objelonct Selonssion {

  /**
   * Thelon selonssionId in FRS is thelon finaglelon tracelon id which is static within thelon lifelontimelon of a singlelon
   * relonquelonst.
   *
   * It is uselond whelonn gelonnelonrating pelonr-candidatelon tokelonns (in TrackingTokelonnTransform) and is also passelond
   * in to downstrelonam Optimus rankelonr relonquelonsts.
   *
   */
  delonf gelontSelonssionId: Long = Tracelon.id.tracelonId.toLong
}
