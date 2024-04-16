package com.tw ter.cr_m xer.ut l

 mport com.tw ter.cr_m xer.model.RankedCand date
 mport com.tw ter.cr_m xer.model.S m lar yEng ne nfo
 mport com.tw ter.cr_m xer.model.S ce nfo
 mport com.tw ter.cr_m xer.thr ftscala. tr cTag
 mport com.tw ter.cr_m xer.thr ftscala.S m lar yEng neType
 mport com.tw ter.cr_m xer.thr ftscala.S ceType

object  tr cTagUt l {

  def bu ld tr cTags(cand date: RankedCand date): Seq[ tr cTag] = {
    val  nterested n tr cTag =  sFrom nterested n(cand date)

    val cg nfo tr cTags = cand date.potent alReasons
      .flatMap { cg nfo =>
        val s ce tr cTag = cg nfo.s ce nfoOpt.flatMap { s ce nfo =>
          to tr cTagFromS ce(s ce nfo.s ceType)
        }
        val s m lar yEng neTags = to tr cTagFromS m lar yEng ne(
          cg nfo.s m lar yEng ne nfo,
          cg nfo.contr but ngS m lar yEng nes)

        val comb ned tr cTag = cg nfo.s ce nfoOpt.flatMap { s ce nfo =>
          to tr cTagFromS ceAndS m lar yEng ne(s ce nfo, cg nfo.s m lar yEng ne nfo)
        }

        Seq(s ce tr cTag) ++ s m lar yEng neTags ++ Seq(comb ned tr cTag)
      }.flatten.toSet
    ( nterested n tr cTag ++ cg nfo tr cTags).toSeq
  }

  /***
   * match a s ceType to a  tr cTag
   */
  pr vate def to tr cTagFromS ce(s ceType: S ceType): Opt on[ tr cTag] = {
    s ceType match {
      case S ceType.T etFavor e => So ( tr cTag.T etFavor e) // Personal zed Top cs  n Ho 
      case S ceType.Ret et => So ( tr cTag.Ret et) // Personal zed Top cs  n Ho 
      case S ceType.Not f cat onCl ck =>
        So ( tr cTag.PushOpenOrNtabCl ck) //  alth F lter  n MR
      case S ceType.Or g nalT et =>
        So ( tr cTag.Or g nalT et)
      case S ceType.Reply =>
        So ( tr cTag.Reply)
      case S ceType.T etShare =>
        So ( tr cTag.T etShare)
      case S ceType.UserFollow =>
        So ( tr cTag.UserFollow)
      case S ceType.UserRepeatedProf leV s  =>
        So ( tr cTag.UserRepeatedProf leV s )
      case S ceType.Tw ceUser d =>
        So ( tr cTag.Tw ceUser d)
      case _ => None
    }
  }

  /***
   *  f t  SE nfo  s bu lt by a un f ed s m eng ne,   un-wrap t  contr but ng s m eng nes.
   *  f not,   log t  s m eng ne as usual.
   * @param se nfo (Cand dateGenerat on nfo.s m lar yEng ne nfo): S m lar yEng ne nfo,
   * @param cse nfo (Cand dateGenerat on nfo.contr but ngS m lar yEng nes): Seq[S m lar yEng ne nfo]
   */
  pr vate def to tr cTagFromS m lar yEng ne(
    se nfo: S m lar yEng ne nfo,
    cse nfo: Seq[S m lar yEng ne nfo]
  ): Seq[Opt on[ tr cTag]] = {
    se nfo.s m lar yEng neType match {
      case S m lar yEng neType.T etBasedUn f edS m lar yEng ne => // un-wrap t  un f ed s m eng ne
        cse nfo.map { contr but ngS mEng ne =>
          to tr cTagFromS m lar yEng ne(contr but ngS mEng ne, Seq.empty)
        }.flatten
      case S m lar yEng neType.ProducerBasedUn f edS m lar yEng ne => // un-wrap t  un f ed s m eng ne
        cse nfo.map { contr but ngS mEng ne =>
          to tr cTagFromS m lar yEng ne(contr but ngS mEng ne, Seq.empty)
        }.flatten
      // S mClustersANN can e  r be called on  s own, or be called under un f ed s m eng ne
      case S m lar yEng neType.S mClustersANN => // t  old "User nterested n" w ll be replaced by t . Also, Offl neTw ce
        Seq(So ( tr cTag.S mClustersANN), se nfo.model d.flatMap(to tr cTagFromModel d(_)))
      case S m lar yEng neType.Consu rEmbedd ngBasedTwH NANN =>
        Seq(So ( tr cTag.Consu rEmbedd ngBasedTwH NANN))
      case S m lar yEng neType.Twh nCollabF lter => Seq(So ( tr cTag.Twh nCollabF lter))
      //  n t  current  mple ntat on, T etBasedUserT etGraph/T etBasedTwH NANN has a tag w n
      //  's e  r a base SE or a contr but ng SE. But for now t y only show up  n contr but ng SE.
      case S m lar yEng neType.T etBasedUserT etGraph =>
        Seq(So ( tr cTag.T etBasedUserT etGraph))
      case S m lar yEng neType.T etBasedTwH NANN =>
        Seq(So ( tr cTag.T etBasedTwH NANN))
      case _ => Seq.empty
    }
  }

  /***
   * pass  n a model  d, and match   w h t   tr c tag type.
   */
  pr vate def to tr cTagFromModel d(
    model d: Str ng
  ): Opt on[ tr cTag] = {

    val pushOpenBasedModelRegex = "(.*_Model20m145k2020_20220819)".r

    model d match {
      case pushOpenBasedModelRegex(_*) =>
        So ( tr cTag.Request althF lterPushOpenBasedT etEmbedd ng)
      case _ => None
    }
  }

  pr vate def to tr cTagFromS ceAndS m lar yEng ne(
    s ce nfo: S ce nfo,
    se nfo: S m lar yEng ne nfo
  ): Opt on[ tr cTag] = {
    s ce nfo.s ceType match {
      case S ceType.Lookal ke
           f se nfo.s m lar yEng neType == S m lar yEng neType.Consu rsBasedUserT etGraph =>
        So ( tr cTag.Lookal keUTG)
      case _ => None
    }
  }

  /**
   * Spec al use case: used by Not f cat ons team to generate t  User nterested n CRT push copy.
   *
   *  f   have d fferent types of  nterested n (eg. User nterested n, Next nterested n),
   * t   f state nt w ll have to be refactored to conta n t  real User nterested n.
   * @return
   */
  pr vate def  sFrom nterested n(cand date: RankedCand date): Set[ tr cTag] = {
     f (cand date.reasonChosen.s ce nfoOpt. sEmpty
      && cand date.reasonChosen.s m lar yEng ne nfo.s m lar yEng neType == S m lar yEng neType.S mClustersANN) {
      Set( tr cTag.User nterested n)
    } else Set.empty
  }

}
