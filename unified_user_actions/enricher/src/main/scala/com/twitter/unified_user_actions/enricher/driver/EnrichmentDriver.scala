package com.tw ter.un f ed_user_act ons.enr c r.dr ver

 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch ntEnvelop
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch ntKey
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch ntStageType.Hydrat on
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch ntStageType.Repart  on
 mport com.tw ter.ut l.Future
 mport Enr ch ntPlanUt ls._
 mport com.tw ter.un f ed_user_act ons.enr c r.Except ons
 mport com.tw ter.un f ed_user_act ons.enr c r. mple ntat onExcept on
 mport com.tw ter.un f ed_user_act ons.enr c r.hydrator.Hydrator
 mport com.tw ter.un f ed_user_act ons.enr c r.part  oner.Part  oner

/**
 * A dr ver that w ll execute on a key, value tuple and produce an output to a Kafka top c.
 *
 * T  output Kafka top c w ll depend on t  current enr ch nt plan.  n one scenar o, t  dr ver
 * w ll output to a part  oned Kafka top c  f t  output needs to be repart  oned (after   has
 * been hydrated 0 or more t  s as necessary).  n anot r scenar o, t  dr ver w ll output to
 * t  f nal top c  f t re's no more work to be done.
 *
 * @param f nalOutputTop c T  f nal output Kafka top c
 * @param part  onedTop c T   nter d ate Kafka top c used for repart  on ng based on [[Enr ch ntKey]]
 * @param hydrator A hydrator that knows how to populate t   tadata based on t  current plan /  nstruct on.
 * @param part  oner A part  oner that knows how to transform t  current uua event  nto an [[Enr ch ntKey]].
 */
class Enr ch ntDr ver(
  f nalOutputTop c: Opt on[Str ng],
  part  onedTop c: Str ng,
  hydrator: Hydrator,
  part  oner: Part  oner) {

  /**
   * A dr ver that does t  follow ng w n be ng executed.
   *    c cks  f   are done w h enr ch nt plan,  f not:
   *  -  s t  current stage repart  on ng?
   *    -> remap t  output key, update plan accord ngly t n return w h t  new part  on key
   *  -  s t  current stage hydrat on?
   *    -> use t  hydrator to hydrate t  envelop, update t  plan accord ngly, t n proceed
   *    recurs vely unless t  next stage  s repart  on ng or t   s t  last stage.
   */
  def execute(
    key: Opt on[Enr ch ntKey],
    envelop: Future[Enr ch ntEnvelop]
  ): Future[(Opt on[Enr ch ntKey], Enr ch ntEnvelop)] = {
    envelop.flatMap { envelop =>
      val plan = envelop.plan
       f (plan. sEnr ch ntComplete) {
        val top c = f nalOutputTop c.getOrElse(
          throw new  mple ntat onExcept on(
            "A f nal output Kafka top c  s supposed to be used but " +
              "no f nal output top c was prov ded."))
        Future.value((key, envelop.copy(plan = plan.markLastStageCompletedW hOutputTop c(top c))))
      } else {
        val currentStage = plan.getCurrentStage

        currentStage.stageType match {
          case Repart  on =>
            Except ons.requ re(
              currentStage. nstruct ons.s ze == 1,
              s"re-part  on ng needs exactly 1  nstruct on but ${currentStage. nstruct ons.s ze} was prov ded")

            val  nstruct on = currentStage. nstruct ons. ad
            val outputKey = part  oner.repart  on( nstruct on, envelop)
            val outputValue = envelop.copy(
              plan = plan.markStageCompletedW hOutputTop c(
                stage = currentStage,
                outputTop c = part  onedTop c)
            )
            Future.value((outputKey, outputValue))
          case Hydrat on =>
            Except ons.requ re(
              currentStage. nstruct ons.nonEmpty,
              "hydrat on needs at least one  nstruct on")

            // Hydrat on  s e  r  n  al zed or completed after t , fa lure state
            // w ll have to be handled upstream. Any unhandled except on w ll abort t  ent re
            // stage.
            // T   s so that  f t  error  n unrecoverable, t  hydrator can choose to return an
            // un-hydrated envelop to tolerate t  error.
            val f nalEnvelop = currentStage. nstruct ons.foldLeft(Future.value(envelop)) {
              (curEnvelop,  nstruct on) =>
                curEnvelop.flatMap(e => hydrator.hydrate( nstruct on, key, e))
            }

            val outputValue = f nalEnvelop.map(e =>
              e.copy(
                plan = plan.markStageCompleted(stage = currentStage)
              ))

            // cont nue execut ng ot r stages  f   can (locally) unt l a term nal state
            execute(key, outputValue)
          case _ =>
            throw new  mple ntat onExcept on(s" nval d / unsupported stage type $currentStage")
        }
      }
    }
  }
}
