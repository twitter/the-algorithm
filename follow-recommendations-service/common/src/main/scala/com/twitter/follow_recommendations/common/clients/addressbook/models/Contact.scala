packagelon com.twittelonr.follow_reloncommelonndations.common.clielonnts.addrelonssbook.modelonls

import com.twittelonr.addrelonssbook.{thriftscala => t}
import com.twittelonr.util.Timelon

caselon class Contact(
  id: Long,
  elonmails: Option[Selont[String]],
  phonelonNumbelonrs: Option[Selont[String]],
  firstNamelon: Option[String],
  lastNamelon: Option[String],
  namelon: Option[String],
  appId: Option[Long],
  appIds: Option[Selont[Long]],
  importelondTimelonstamp: Option[Timelon])

objelonct Contact {
  delonf fromThrift(thriftContact: t.Contact): Contact = Contact(
    thriftContact.id,
    thriftContact.elonmails.map(_.toSelont),
    thriftContact.phonelonNumbelonrs.map(_.toSelont),
    thriftContact.firstNamelon,
    thriftContact.lastNamelon,
    thriftContact.namelon,
    thriftContact.appId,
    thriftContact.appIds.map(_.toSelont),
    thriftContact.importelondTimelonstamp.map(Timelon.fromMilliselonconds)
  )
}
