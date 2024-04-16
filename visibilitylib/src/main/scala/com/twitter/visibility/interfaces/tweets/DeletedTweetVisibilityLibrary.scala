package com.tw ter.v s b l y. nterfaces.t ets

 mport com.tw ter.dec der.Dec der
 mport com.tw ter.st ch.St ch
 mport com.tw ter.v s b l y.V s b l yL brary
 mport com.tw ter.v s b l y.bu lder.V s b l yResult
 mport com.tw ter.v s b l y.features.T etDeleteReason
 mport com.tw ter.v s b l y.features.T et s nnerQuotedT et
 mport com.tw ter.v s b l y.features.T et sRet et
 mport com.tw ter.v s b l y.generators.TombstoneGenerator
 mport com.tw ter.v s b l y.models.Content d.DeleteT et d
 mport com.tw ter.v s b l y.models.SafetyLevel
 mport com.tw ter.v s b l y.models.T etDeleteReason.T etDeleteReason
 mport com.tw ter.v s b l y.models.V e rContext

object DeletedT etV s b l yL brary {
  type Type = DeletedT etV s b l yL brary.Request => St ch[V s b l yResult]

  case class Request(
    t et d: Long,
    safetyLevel: SafetyLevel,
    v e rContext: V e rContext,
    t etDeleteReason: T etDeleteReason,
     sRet et: Boolean,
     s nnerQuotedT et: Boolean,
  )

  def apply(
    v s b l yL brary: V s b l yL brary,
    dec der: Dec der,
    tombstoneGenerator: TombstoneGenerator,
  ): Type = {
    val vfEng neCounter = v s b l yL brary.statsRece ver.counter("vf_eng ne_requests")

    (request: Request) => {
      vfEng neCounter. ncr()
      val content d = DeleteT et d(request.t et d)
      val language = request.v e rContext.requestLanguageCode.getOrElse("en")

      val featureMap =
        v s b l yL brary.featureMapBu lder(
          Seq(
            _.w hConstantFeature(T et s nnerQuotedT et, request. s nnerQuotedT et),
            _.w hConstantFeature(T et sRet et, request. sRet et),
            _.w hConstantFeature(T etDeleteReason, request.t etDeleteReason)
          )
        )

      v s b l yL brary
        .runRuleEng ne(
          content d,
          featureMap,
          request.v e rContext,
          request.safetyLevel
        )
        .map(tombstoneGenerator(_, language))
    }
  }
}
