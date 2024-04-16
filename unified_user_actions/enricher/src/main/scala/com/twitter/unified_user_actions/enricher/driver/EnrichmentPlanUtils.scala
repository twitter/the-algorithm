package com.tw ter.un f ed_user_act ons.enr c r.dr ver

 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch ntPlan
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch ntStage
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch ntStageStatus.Complet on
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch ntStageStatus. n  al zed

object Enr ch ntPlanUt ls {
   mpl c  class Enr ch ntPlanStatus(plan: Enr ch ntPlan) {

    /**
     * C ck each stage of t  plan to know  f   are done
     */
    def  sEnr ch ntComplete: Boolean = {
      plan.stages.forall(stage => stage.status == Complet on)
    }

    /**
     * Get t  next stage  n t  enr ch nt process. Note,  f t re  s none t  w ll throw
     * an except on.
     */
    def getCurrentStage: Enr ch ntStage = {
      val next = plan.stages.f nd(stage => stage.status ==  n  al zed)
      next match {
        case So (stage) => stage
        case None => throw new  llegalStateExcept on("c ck for plan complet on f rst")
      }
    }
    def getLastCompletedStage: Enr ch ntStage = {
      val completed = plan.stages.reverse.f nd(stage => stage.status == Complet on)
      completed match {
        case So (stage) => stage
        case None => throw new  llegalStateExcept on("c ck for plan complet on f rst")
      }
    }

    /**
     * Copy t  current plan w h t  requested stage marked as complete
     */
    def markStageCompletedW hOutputTop c(
      stage: Enr ch ntStage,
      outputTop c: Str ng
    ): Enr ch ntPlan = {
      plan.copy(
        stages = plan.stages.map(s =>
           f (s == stage) s.copy(status = Complet on, outputTop c = So (outputTop c)) else s)
      )
    }

    def markStageCompleted(
      stage: Enr ch ntStage
    ): Enr ch ntPlan = {
      plan.copy(
        stages = plan.stages.map(s =>  f (s == stage) s.copy(status = Complet on) else s)
      )
    }

    /**
     * Copy t  current plan w h t  last stage marked as necessary
     */
    def markLastStageCompletedW hOutputTop c(
      outputTop c: Str ng
    ): Enr ch ntPlan = {
      val last = plan.stages.last
      plan.copy(
        stages = plan.stages.map(s =>
           f (s == last) s.copy(status = Complet on, outputTop c = So (outputTop c)) else s)
      )
    }
  }
}
