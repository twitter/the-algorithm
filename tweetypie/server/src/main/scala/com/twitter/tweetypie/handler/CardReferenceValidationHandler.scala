package com.tw ter.t etyp e
package handler

 mport com.tw ter.expandodo.thr ftscala.Attach ntEl g b l yResponses
 mport com.tw ter.expandodo.{thr ftscala => expandodo}
 mport com.tw ter.t etyp e.backends.Expandodo
 mport com.tw ter.tw tertext.Extractor
 mport scala.ut l.control.NoStackTrace
 mport scala.ut l.control.NonFatal
 mport java.net.UR 

object CardReferenceVal dat onFa ledExcept on extends Except on w h NoStackTrace

object CardReferenceVal dat onHandler {
  type Type = FutureArrow[(User d, CardUr ), CardUr ]

  def apply(c ckEl g b l y: Expandodo.C ckAttach ntEl g b l y): Type = {
    def val dateAttach ntForUser(user d: User d, cardUr : CardUr ): Future[CardUr ] = {
      val request = Seq(expandodo.Attach ntEl g b l yRequest(cardUr , user d))
      c ckEl g b l y(request)
        .flatMap(val datedCardUr )
        .rescue {
          case NonFatal(_) => Future.except on(CardReferenceVal dat onFa ledExcept on)
        }
    }

    FutureArrow {
      case (user d, cardUr ) =>
         f (shouldSk pVal dat on(cardUr )) {
          Future.value(cardUr )
        } else {
          val dateAttach ntForUser(user d, cardUr )
        }
    }
  }

  pr vate[t ] def val datedCardUr (responses: Attach ntEl g b l yResponses) = {
    responses.results. adOpt on match {
      case So (
            expandodo.Attach ntEl g b l yResult
              .Success(expandodo.Val dCardUr (val datedCardUr ))
          ) =>
        Future.value(val datedCardUr )
      case _ =>
        Future.except on(CardReferenceVal dat onFa ledExcept on)
    }
  }

  //  're not chang ng state bet en calls, so  's safe to share among threads
  pr vate[t ] val extractor = {
    val extractor = new Extractor
    extractor.setExtractURLW houtProtocol(false)
    extractor
  }

  // Card References w h t se UR s don't need val dat on s nce cards referenced by UR s  n t se
  // sc  s are publ c and  nce not subject to restr ct ons.
  pr vate[handler] val  sWh el stedSc ma = Set("http", "https", "tombstone")

  // NOTE: http://www. etf.org/rfc/rfc2396.txt
  pr vate[t ] def hasWh el stedSc  (cardUr : CardUr ) =
    Try(new UR (cardUr )).toOpt on
      .map(_.getSc  )
      .ex sts( sWh el stedSc ma)

  // Even though UR  spec  s techn cally  s a superset of http:// and https:// URLs,   have to
  // resort to us ng a Regex based parser  re as a fallback because many URLs found  n t  w ld
  // have unescaped components that would fa l java.net.UR  pars ng, yet are st ll cons dered acceptable.
  pr vate[t ] def  sTw terUrlEnt y(cardUr : CardUr ) =
    extractor.extractURLs(cardUr ).s ze == 1

  pr vate[t ] def shouldSk pVal dat on(cardUr : CardUr ) =
    hasWh el stedSc  (cardUr ) ||  sTw terUrlEnt y(cardUr )
}
