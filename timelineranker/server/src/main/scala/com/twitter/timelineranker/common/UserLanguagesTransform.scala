package com.tw ter.t  l neranker.common

 mport com.tw ter.search.common.constants.thr ftscala.Thr ftLanguage
 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.t  l neranker.model.RecapQuery
 mport com.tw ter.t  l nes.cl ents.manhattan.LanguageUt ls
 mport com.tw ter.t  l nes.cl ents.manhattan.User tadataCl ent
 mport com.tw ter.t  l nes.ut l.Fa lOpenHandler
 mport com.tw ter.ut l.Future
 mport com.tw ter.serv ce. tastore.gen.thr ftscala.UserLanguages

object UserLanguagesTransform {
  val EmptyUserLanguagesFuture: Future[UserLanguages] =
    Future.value(User tadataCl ent.EmptyUserLanguages)
}

/**
 * FutureArrow wh ch fetc s user languages
 *   should be run  n parallel w h t  ma n p pel ne wh ch fetc s and hydrates Cand dateT ets
 */
class UserLanguagesTransform(handler: Fa lOpenHandler, user tadataCl ent: User tadataCl ent)
    extends FutureArrow[RecapQuery, Seq[Thr ftLanguage]] {
  overr de def apply(request: RecapQuery): Future[Seq[Thr ftLanguage]] = {
     mport UserLanguagesTransform._

    handler {
      user tadataCl ent.getUserLanguages(request.user d)
    } { _: Throwable => EmptyUserLanguagesFuture }
  }.map(LanguageUt ls.computeLanguages(_))
}
