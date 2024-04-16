package com.tw ter.fr gate.pushserv ce.model.ntab

 mport com.tw ter.fr gate.common.ut l.MRNtabCopy
 mport com.tw ter.fr gate.common.ut l.MrNtabCopyObjects
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.take. nval dNtabCopy dExcept on
 mport com.tw ter.fr gate.pushserv ce.take.NtabCopy dNotFoundExcept on

tra  Cand dateNTabCopy {
  self: PushCand date =>

  def ntabCopy: MRNtabCopy =
    ntabCopy d
      .map(getNtabCopyFromCopy d).getOrElse(
        throw new NtabCopy dNotFoundExcept on(s"NtabCopy d not found for $commonRecType"))

  pr vate def getNtabCopyFromCopy d(ntabCopy d:  nt): MRNtabCopy =
    MrNtabCopyObjects
      .getCopyFrom d(ntabCopy d).getOrElse(
        throw new  nval dNtabCopy dExcept on(s"Unknown NTab Copy  D: $ntabCopy d"))
}
