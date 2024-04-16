package com.tw ter.un f ed_user_act ons.enr c r

 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch nt nstruct on
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch ntPlan
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch ntStage
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch ntStageStatus
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch ntStageType
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Act onType
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Author nfo
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Event tadata
 mport com.tw ter.un f ed_user_act ons.thr ftscala. em
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Mult T etNot f cat on
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Not f cat onContent
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Not f cat on nfo
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Prof le nfo
 mport com.tw ter.un f ed_user_act ons.thr ftscala.S ceL neage
 mport com.tw ter.un f ed_user_act ons.thr ftscala.T et nfo
 mport com.tw ter.un f ed_user_act ons.thr ftscala.T etNot f cat on
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Un f edUserAct on
 mport com.tw ter.un f ed_user_act ons.thr ftscala.UnknownNot f cat on
 mport com.tw ter.un f ed_user_act ons.thr ftscala.User dent f er

tra  Enr c rF xture {
  val part  onedTop c = "un f ed_user_act ons_keyed_dev"
  val t et nfoEnr ch ntPlan = Enr ch ntPlan(
    Seq(
      // f rst stage: to repart  on on t et  d -> done
      Enr ch ntStage(
        Enr ch ntStageStatus.Complet on,
        Enr ch ntStageType.Repart  on,
        Seq(Enr ch nt nstruct on.T etEnr ch nt),
        So (part  onedTop c)
      ),
      // next stage: to hydrate more  tadata based on t et  d ->  n  al zed
      Enr ch ntStage(
        Enr ch ntStageStatus. n  al zed,
        Enr ch ntStageType.Hydrat on,
        Seq(Enr ch nt nstruct on.T etEnr ch nt)
      )
    ))

  val t etNot f cat onEnr ch ntPlan = Enr ch ntPlan(
    Seq(
      // f rst stage: to repart  on on t et  d -> done
      Enr ch ntStage(
        Enr ch ntStageStatus.Complet on,
        Enr ch ntStageType.Repart  on,
        Seq(Enr ch nt nstruct on.Not f cat onT etEnr ch nt),
        So (part  onedTop c)
      ),
      // next stage: to hydrate more  tadata based on t et  d ->  n  al zed
      Enr ch ntStage(
        Enr ch ntStageStatus. n  al zed,
        Enr ch ntStageType.Hydrat on,
        Seq(Enr ch nt nstruct on.Not f cat onT etEnr ch nt),
      )
    ))

  def mkUUAT etEvent(t et d: Long, author: Opt on[Author nfo] = None): Un f edUserAct on = {
    Un f edUserAct on(
      User dent f er(user d = So (1L)),
       em =  em.T et nfo(T et nfo(act onT et d = t et d, act onT etAuthor nfo = author)),
      act onType = Act onType.Cl entT etReport,
      event tadata = Event tadata(1234L, 2345L, S ceL neage.ServerT etyp eEvents)
    )
  }

  def mkUUAT etNot f cat onEvent(t et d: Long): Un f edUserAct on = {
    mkUUAT etEvent(-1L).copy(
       em =  em.Not f cat on nfo(
        Not f cat on nfo(
          act onNot f cat on d = "123456",
          content = Not f cat onContent.T etNot f cat on(T etNot f cat on(t et d = t et d))))
    )
  }

  def mkUUAMult T etNot f cat onEvent(t et ds: Long*): Un f edUserAct on = {
    mkUUAT etEvent(-1L).copy(
       em =  em.Not f cat on nfo(
        Not f cat on nfo(
          act onNot f cat on d = "123456",
          content = Not f cat onContent.Mult T etNot f cat on(
            Mult T etNot f cat on(t et ds = t et ds))))
    )
  }

  def mkUUAT etNot f cat onUnknownEvent(): Un f edUserAct on = {
    mkUUAT etEvent(-1L).copy(
       em =  em.Not f cat on nfo(
        Not f cat on nfo(
          act onNot f cat on d = "123456",
          content = Not f cat onContent.UnknownNot f cat on(UnknownNot f cat on())))
    )
  }

  def mkUUAProf leEvent(user d: Long): Un f edUserAct on = {
    val event = mkUUAT etEvent(1L)
    event.copy( em =  em.Prof le nfo(Prof le nfo(user d)))
  }
}
