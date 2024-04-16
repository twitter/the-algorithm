package com.tw ter.fr gate.pushserv ce.conf g

 mport com.tw ter.fr gate.common.ut l.Exper  nts

object Exper  ntsW hStats {

  /**
   * Add an exper  nt  re to collect deta led pushserv ce stats.
   *
   * !  mportant !
   * Keep t  set small and remove exper  nts w n   don't need t  stats anymore.
   */
  f nal val PushExper  nts: Set[Str ng] = Set(
    Exper  nts.MRAndro d nl neAct onHoldback.exptNa ,
  )
}
