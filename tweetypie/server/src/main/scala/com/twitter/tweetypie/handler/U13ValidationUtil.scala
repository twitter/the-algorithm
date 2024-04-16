package com.tw ter.t etyp e.handler

 mport com.tw ter.compl ance.userconsent.compl ance.b rthdate.GlobalB rthdateUt l
 mport com.tw ter.g zmoduck.thr ftscala.User
 mport com.tw ter.t etyp e.thr ftscala.DeletedT et
 mport org.joda.t  .DateT  

/*
 * As part of GDPR U13 work,   want to block t ets created from w n a user
 * was < 13 from be ng restored.
 */

pr vate[handler] object U13Val dat onUt l {
  def wasT etCreatedBeforeUserTurned13(user: User, deletedT et: DeletedT et): Boolean =
    deletedT et.createdAtSecs match {
      case None =>
        throw NoCreatedAtT  Except on
      case So (createdAtSecs) =>
        GlobalB rthdateUt l. sUnderSo Age(13, new DateT  (createdAtSecs * 1000L), user)
    }
}
