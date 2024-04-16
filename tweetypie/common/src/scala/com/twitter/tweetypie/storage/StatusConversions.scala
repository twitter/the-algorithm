package com.tw ter.t etyp e.storage

 mport com.tw ter.t etyp e.storage_ nternal.thr ftscala._
 mport com.tw ter.tb rd.{thr ftscala => tb rd}

object StatusConvers ons {

  /**
   * T   s used only  n Scr be.scala, w n scr b ng to tb rd_add_status
   * Once   remove that,   can also remove t .
   */
  def toTB rdStatus(t et: StoredT et): tb rd.Status =
    tb rd.Status(
       d = t et. d,
      user d = t et.user d.get,
      text = t et.text.get,
      createdV a = t et.createdV a.get,
      createdAtSec = t et.createdAtSec.get,
      reply = t et.reply.map(toTB rdReply),
      share = t et.share.map(toTB rdShare),
      contr butor d = t et.contr butor d,
      geo = t et.geo.map(toTB rdGeo),
      hasTakedown = t et.hasTakedown.getOrElse(false),
      nsfwUser = t et.nsfwUser.getOrElse(false),
      nsfwAdm n = t et.nsfwAdm n.getOrElse(false),
       d a = t et. d a.map(_.map(toTB rd d a)).getOrElse(Seq()),
      narrowcast = t et.narrowcast.map(toTB rdNarrowcast),
      nullcast = t et.nullcast.getOrElse(false),
      track ng d = t et.track ng d
    )

  /**
   * T   s only used  n a test, to ver fy that t  above  thod `toTB rdStatus`
   * works, so   can't remove   as long as t  above  thod ex sts.
   */
  def fromTB rdStatus(status: tb rd.Status): StoredT et = {
    StoredT et(
       d = status. d,
      user d = So (status.user d),
      text = So (status.text),
      createdV a = So (status.createdV a),
      createdAtSec = So (status.createdAtSec),
      reply = status.reply.map(fromTB rdReply),
      share = status.share.map(fromTB rdShare),
      contr butor d = status.contr butor d,
      geo = status.geo.map(fromTB rdGeo),
      hasTakedown = So (status.hasTakedown),
      nsfwUser = So (status.nsfwUser),
      nsfwAdm n = So (status.nsfwAdm n),
       d a = So (status. d a.map(fromTB rd d a)),
      narrowcast = status.narrowcast.map(fromTB rdNarrowcast),
      nullcast = So (status.nullcast),
      track ng d = status.track ng d
    )
  }

  pr vate def fromTB rdReply(reply: tb rd.Reply): StoredReply =
    StoredReply(
       nReplyToStatus d = reply. nReplyToStatus d,
       nReplyToUser d = reply. nReplyToUser d
    )

  pr vate def fromTB rdShare(share: tb rd.Share): StoredShare =
    StoredShare(
      s ceStatus d = share.s ceStatus d,
      s ceUser d = share.s ceUser d,
      parentStatus d = share.parentStatus d
    )

  pr vate def fromTB rdGeo(geo: tb rd.Geo): StoredGeo =
    StoredGeo(
      lat ude = geo.lat ude,
      long ude = geo.long ude,
      geoPrec s on = geo.geoPrec s on,
      ent y d = geo.ent y d
    )

  pr vate def fromTB rd d a( d a: tb rd. d aEnt y): Stored d aEnt y =
    Stored d aEnt y(
       d =  d a. d,
       d aType =  d a. d aType,
      w dth =  d a.w dth,
        ght =  d a.  ght
    )

  pr vate def fromTB rdNarrowcast(narrowcast: tb rd.Narrowcast): StoredNarrowcast =
    StoredNarrowcast(
      language = So (narrowcast.language),
      locat on = So (narrowcast.locat on),
       ds = So (narrowcast. ds)
    )

  pr vate def toTB rdReply(reply: StoredReply): tb rd.Reply =
    tb rd.Reply(
       nReplyToStatus d = reply. nReplyToStatus d,
       nReplyToUser d = reply. nReplyToUser d
    )

  pr vate def toTB rdShare(share: StoredShare): tb rd.Share =
    tb rd.Share(
      s ceStatus d = share.s ceStatus d,
      s ceUser d = share.s ceUser d,
      parentStatus d = share.parentStatus d
    )

  pr vate def toTB rdGeo(geo: StoredGeo): tb rd.Geo =
    tb rd.Geo(
      lat ude = geo.lat ude,
      long ude = geo.long ude,
      geoPrec s on = geo.geoPrec s on,
      ent y d = geo.ent y d,
      na  = geo.na 
    )

  pr vate def toTB rd d a( d a: Stored d aEnt y): tb rd. d aEnt y =
    tb rd. d aEnt y(
       d =  d a. d,
       d aType =  d a. d aType,
      w dth =  d a.w dth,
        ght =  d a.  ght
    )

  pr vate def toTB rdNarrowcast(narrowcast: StoredNarrowcast): tb rd.Narrowcast =
    tb rd.Narrowcast(
      language = narrowcast.language.getOrElse(N l),
      locat on = narrowcast.locat on.getOrElse(N l),
       ds = narrowcast. ds.getOrElse(N l)
    )
}
