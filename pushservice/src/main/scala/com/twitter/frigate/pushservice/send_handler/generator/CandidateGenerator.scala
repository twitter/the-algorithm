package com.tw ter.fr gate.pushserv ce.send_handler.generator

 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.RawCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.Target
 mport com.tw ter.fr gate.thr ftscala.Fr gateNot f cat on
 mport com.tw ter.ut l.Future

tra  Cand dateGenerator {

  /**
   * Bu ld RawCand date from Fr gateNot f cat on
   * @param target
   * @param fr gateNot f cat on
   * @return RawCand date
   */
  def getCand date(target: Target, fr gateNot f cat on: Fr gateNot f cat on): Future[RawCand date]
}
