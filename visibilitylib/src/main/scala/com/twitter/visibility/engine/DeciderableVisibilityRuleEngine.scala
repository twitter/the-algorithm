package com.tw ter.v s b l y.eng ne

 mport com.tw ter.servo.ut l.Gate
 mport com.tw ter.spam.rtf.thr ftscala.{SafetyLevel => Thr ftSafetyLevel}
 mport com.tw ter.st ch.St ch
 mport com.tw ter.v s b l y.bu lder.V s b l yResult
 mport com.tw ter.v s b l y.bu lder.V s b l yResultBu lder
 mport com.tw ter.v s b l y.models.SafetyLevel
 mport com.tw ter.v s b l y.rules.Evaluat onContext
 mport com.tw ter.v s b l y.rules.Rule

tra  Dec derableV s b l yRuleEng ne {
  def apply(
    evaluat onContext: Evaluat onContext,
    safetyLevel: SafetyLevel,
    v s b l yResultBu lder: V s b l yResultBu lder,
    enableShortC rcu  ng: Gate[Un ] = Gate.True,
    preprocessedRules: Opt on[Seq[Rule]] = None
  ): St ch[V s b l yResult]

  def apply(
    evaluat onContext: Evaluat onContext,
    thr ftSafetyLevel: Thr ftSafetyLevel,
    v s b l yResultBu lder: V s b l yResultBu lder
  ): St ch[V s b l yResult]
}
