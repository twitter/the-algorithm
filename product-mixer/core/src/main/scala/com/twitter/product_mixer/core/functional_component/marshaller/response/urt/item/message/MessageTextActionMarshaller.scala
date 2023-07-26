package com.twittew.pwoduct_mixew.cowe.functionaw_component.mawshawwew.wesponse.uwt.item.message

impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.item.message.messagetextaction
i-impowt com.twittew.timewines.wendew.{thwiftscawa => u-uwt}
impowt j-javax.inject.inject
i-impowt j-javax.inject.singweton

@singweton
c-cwass messagetextactionmawshawwew @inject() (
  m-messageactionmawshawwew: m-messageactionmawshawwew) {

  def appwy(messagetextaction: messagetextaction): uwt.messagetextaction =
    uwt.messagetextaction(
      t-text = messagetextaction.text, >_<
      action = messageactionmawshawwew(messagetextaction.action)
    )
}
