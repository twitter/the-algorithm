package com.tw ter.t etyp e
package handler

 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l neserv ce.{thr ftscala => tls}
 mport com.tw ter.t etyp e.backends.T  l neServ ce
 mport com.tw ter.t etyp e.repos ory.T etQuery
 mport com.tw ter.t etyp e.repos ory.T etRepos ory
 mport com.tw ter.t etyp e.thr ftscala.CardReference
 mport com.tw ter.t etyp e.thr ftscala.Conversat onControl
 mport com.tw ter.t etyp e.thr ftscala.Conversat onControlBy nv at on
 mport com.tw ter.t etyp e.thr ftscala.Conversat onControlCommun y
 mport com.tw ter.t etyp e.thr ftscala.Conversat onControlFollo rs
 mport com.tw ter.t etyp e.thr ftscala.Ed Control
 mport com.tw ter.t etyp e.thr ftscala.Ed Opt ons
 mport com.tw ter.t etyp e.thr ftscala.NoteT etOpt ons
 mport com.tw ter.t etyp e.thr ftscala.PostT etRequest
 mport com.tw ter.t etyp e.thr ftscala.T etCreateConversat onControl
 mport com.tw ter.t etyp e.ut l.Conversat onControls
 mport com.tw ter.t etyp e.ut l.Ed ControlUt l
 mport com.tw ter.ut l.T  

/**
 * Used at t et creat on t   to determ ne w t r t  t et creat on
 * request should be cons dered a dupl cate of an ex st ng t et.
 */
object Dupl cateT etF nder {

  /**
   * Return t   ds of any t ets that are found to be dupl cates of
   * t  request.
   */
  type Type = Request nfo => Future[Opt on[T et d]]

  f nal case class Sett ngs(
    // T  number of t ets that are loaded from t  user's t  l ne
    // for t   ur st c dupl cate c ck
    numT etsToC ck:  nt,
    // T  oldest that a t et can be to st ll be cons dered a
    // dupl cate by t   ur st c dupl cate c ck
    maxDupl cateAge: Durat on)

  // Takes a Conversat onControl from a T et and converts to t  equ valent
  // T etCreateConversat onControl. Note: t   s a lossy convers on because t 
  // Conversat onControl conta ns add  onal data from t  T et.
  def toT etCreateConversat onControl(
    conversat onControl: Conversat onControl
  ): T etCreateConversat onControl =
    conversat onControl match {
      case Conversat onControl.By nv at on(
            Conversat onControlBy nv at on(_, _,  nv eV a nt on)) =>
        Conversat onControls.Create.by nv at on( nv eV a nt on)
      case Conversat onControl.Commun y(Conversat onControlCommun y(_, _,  nv eV a nt on)) =>
        Conversat onControls.Create.commun y( nv eV a nt on)
      case Conversat onControl.Follo rs(Conversat onControlFollo rs(_, _,  nv eV a nt on)) =>
        Conversat onControls.Create.follo rs( nv eV a nt on)
      case _ => throw new  llegalArgu ntExcept on
    }

  /**
   * T  parts of t  request that   need  n order to perform
   * dupl cate detect on.
   */
  f nal case class Request nfo(
    user d: User d,
     sNarrowcast: Boolean,
     sNullcast: Boolean,
    text: Str ng,
    replyToT et d: Opt on[T et d],
     d aUpload ds: Seq[ d a d],
    cardReference: Opt on[CardReference],
    conversat onControl: Opt on[T etCreateConversat onControl],
    underly ngCreat vesConta ner: Opt on[Creat vesConta ner d],
    ed Opt ons: Opt on[Ed Opt ons] = None,
    noteT etOpt ons: Opt on[NoteT etOpt ons] = None) {

    def  sDupl cateOf(t et: T et, oldestAcceptableT  stamp: T  ): Boolean = {
      val createdAt = getT  stamp(t et)
      val  sDupl cateText = text == getText(t et)
      val  sDupl cateReplyToT et d = replyToT et d == getReply(t et).flatMap(_. nReplyToStatus d)
      val  sDupl cate d a = get d a(t et).map(_. d a d) ==  d aUpload ds
      val  sDupl cateCardReference = getCardReference(t et) == cardReference
      val  sDupl cateConversat onControl =
        t et.conversat onControl.map(toT etCreateConversat onControl) == conversat onControl
      val  sDupl cateConversat onConta ner d = {
        t et.underly ngCreat vesConta ner d == underly ngCreat vesConta ner
      }

      val  sDupl cate fEd Request =  f (ed Opt ons. sDef ned) {
        //   do not count an  ncom ng ed  request as creat ng a dupl cate t et  f:
        // 1) T  t et that  s cons dered a dupl cate  s a prev ous vers on of t  t et OR
        // 2) T  t et that  s cons dered a dupl cate  s ot rw se stale.
        val t etEd Cha n = t et.ed Control match {
          case So (Ed Control. n  al( n  al)) =>
             n  al.ed T et ds
          case So (Ed Control.Ed (ed )) =>
            ed .ed Control n  al.map(_.ed T et ds).getOrElse(N l)
          case _ => N l
        }
        val t et sAPrev ousVers on =
          ed Opt ons.map(_.prev ousT et d).ex sts(t etEd Cha n.conta ns)

        val t et sStale = Ed ControlUt l. sLatestEd (t et.ed Control, t et. d) match {
          case Return(false) => true
          case _ => false
        }

        !(t et sStale || t et sAPrev ousVers on)
      } else {
        //  f not an ed  request, t  cond  on  s true as dupl cat on c ck ng  s not blocked
        true
      }

      // Note that t  does not prevent   from t et ng t  sa 
      //  mage tw ce w h d fferent text, or t  sa  text tw ce w h
      // d fferent  mages, because  f   upload t  sa   d a tw ce,
      //   w ll store two cop es of  , each w h a d fferent  d a
      // URL and thus d fferent t.co URL, and s nce t  text that
      //  're c ck ng  re has that t.co URL added to   already,  
      //  s necessar ly d fferent.
      //
      //   shouldn't have to c ck t  user  d or w t r  's a
      // ret et, because   loaded t  t ets from t  user's
      // (non-ret et) t  l nes, but   doesn't hurt and protects
      // aga nst poss ble future changes.
      (oldestAcceptableT  stamp <= createdAt) &&
      getShare(t et). sEmpty &&
      (getUser d(t et) == user d) &&
       sDupl cateText &&
       sDupl cateReplyToT et d &&
       sDupl cate d a &&
       sDupl cateCardReference &&
       sDupl cateConversat onControl &&
       sDupl cateConversat onConta ner d &&
       sDupl cate fEd Request &&
      noteT etOpt ons. sEmpty // Sk p dupl cate c cks for NoteT ets
    }
  }

  object Request nfo {

    /**
     * Extract t   nformat on relevant to t  Dupl cateT etF nder
     * from t  PostT etRequest.
     */
    def fromPostT etRequest(req: PostT etRequest, processedText: Str ng): Request nfo =
      Request nfo(
        user d = req.user d,
         sNarrowcast = req.narrowcast.nonEmpty,
         sNullcast = req.nullcast,
        text = processedText,
        replyToT et d = req. nReplyToT et d,
         d aUpload ds = req. d aUpload ds.getOrElse[Seq[ d a d]](Seq.empty),
        cardReference = req.add  onalF elds.flatMap(_.cardReference),
        conversat onControl = req.conversat onControl,
        underly ngCreat vesConta ner = req.underly ngCreat vesConta ner d,
        ed Opt ons = req.ed Opt ons,
        noteT etOpt ons = req.noteT etOpt ons
      )
  }

  /**
   * Encapsulates t  external  nteract ons that   need to do for
   * dupl cate c ck ng.
   */
  tra  T etS ce {
    def loadT ets(t et ds: Seq[T et d]): Future[Seq[T et]]
    def loadUserT  l ne ds(user d: User d, maxCount:  nt): Future[Seq[T et d]]
    def loadNarrowcastT  l ne ds(user d: User d, maxCount:  nt): Future[Seq[T et d]]
  }

  object T etS ce {

    /**
     * Use t  prov ded serv ces to access t ets.
     */
    def fromServ ces(
      t etRepo: T etRepos ory.Opt onal,
      getStatusT  l ne: T  l neServ ce.GetStatusT  l ne
    ): T etS ce =
      new T etS ce {
        // only f elds needed by Request nfo. sDupl cateOf()
        pr vate[t ] val t etQueryOpt on =
          T etQuery.Opt ons(
            T etQuery. nclude(
              t etF elds = Set(
                T et.CoreDataF eld. d,
                T et. d aF eld. d,
                T et.Conversat onControlF eld. d,
                T et.Ed ControlF eld. d
              ),
              pasted d a = true
            )
          )

        pr vate[t ] def loadT  l ne(query: tls.T  l neQuery): Future[Seq[Long]] =
          getStatusT  l ne(Seq(query)).map(_. ad.entr es.map(_.status d))

        overr de def loadUserT  l ne ds(user d: User d, maxCount:  nt): Future[Seq[Long]] =
          loadT  l ne(
            tls.T  l neQuery(
              t  l neType = tls.T  l neType.User,
              t  l ne d = user d,
              maxCount = maxCount.toShort
            )
          )

        overr de def loadNarrowcastT  l ne ds(user d: User d, maxCount:  nt): Future[Seq[Long]] =
          loadT  l ne(
            tls.T  l neQuery(
              t  l neType = tls.T  l neType.Narrowcasted,
              t  l ne d = user d,
              maxCount = maxCount.toShort
            )
          )

        overr de def loadT ets(t et ds: Seq[T et d]): Future[Seq[T et]] =
           f (t et ds. sEmpty) {
            Future.value(Seq[T et]())
          } else {
            St ch
              .run(
                St ch.traverse(t et ds) { t et d => t etRepo(t et d, t etQueryOpt on) }
              )
              .map(_.flatten)
          }
      }
  }

  def apply(sett ngs: Sett ngs, t etS ce: T etS ce): Type = { req nfo =>
     f (req nfo. sNullcast) {
      //  ff nullcast,   bypass dupl cat on log c all toget r
      Future.None
    } else {
      val oldestAcceptableT  stamp = T  .now - sett ngs.maxDupl cateAge
      val userT et dsFut =
        t etS ce.loadUserT  l ne ds(req nfo.user d, sett ngs.numT etsToC ck)

      // C ck t  narrowcast t  l ne  ff t   s a narrowcasted t et
      val narrowcastT et dsFut =
         f (req nfo. sNarrowcast) {
          t etS ce.loadNarrowcastT  l ne ds(req nfo.user d, sett ngs.numT etsToC ck)
        } else {
          Future.value(Seq.empty)
        }

      for {
        userT et ds <- userT et dsFut
        narrowcastT et ds <- narrowcastT et dsFut
        cand dateT ets <- t etS ce.loadT ets(userT et ds ++ narrowcastT et ds)
      } y eld cand dateT ets.f nd(req nfo. sDupl cateOf(_, oldestAcceptableT  stamp)).map(_. d)
    }
  }
}
