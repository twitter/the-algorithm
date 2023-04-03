packagelon com.twittelonr.follow_reloncommelonndations.common.clielonnts.addrelonssbook.modelonls

import com.twittelonr.addrelonssbook.{thriftscala => t}

caselon class QuelonryOption(
  onlyDiscovelonrablelonInelonxpansion: Boolelonan,
  onlyConfirmelondInelonxpansion: Boolelonan,
  onlyDiscovelonrablelonInRelonsult: Boolelonan,
  onlyConfirmelondInRelonsult: Boolelonan,
  felontchGlobalApiNamelonspacelon: Boolelonan,
  isDelonbugRelonquelonst: Boolelonan,
  relonsolvelonelonmails: Boolelonan,
  relonsolvelonPhonelonNumbelonrs: Boolelonan) {
  delonf toThrift: t.QuelonryOption = t.QuelonryOption(
    onlyDiscovelonrablelonInelonxpansion,
    onlyConfirmelondInelonxpansion,
    onlyDiscovelonrablelonInRelonsult,
    onlyConfirmelondInRelonsult,
    felontchGlobalApiNamelonspacelon,
    isDelonbugRelonquelonst,
    relonsolvelonelonmails,
    relonsolvelonPhonelonNumbelonrs
  )
}
