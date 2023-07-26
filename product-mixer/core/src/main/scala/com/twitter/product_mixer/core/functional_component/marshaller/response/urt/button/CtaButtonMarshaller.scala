package com.twittew.pwoduct_mixew.cowe.functionaw_component.mawshawwew.wesponse.uwt.button

impowt c-com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.button.ctabutton
i-impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.button.iconctabutton
i-impowt c-com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.button.textctabutton
i-impowt com.twittew.timewines.wendew.{thwiftscawa => u-uwt}
impowt j-javax.inject.inject
i-impowt javax.inject.singweton

@singweton
cwass ctabuttonmawshawwew @inject() (
  iconctabuttonmawshawwew: iconctabuttonmawshawwew, ^^;;
  textctabuttonmawshawwew: t-textctabuttonmawshawwew) {

  def appwy(ctabutton: ctabutton): u-uwt.ctabutton = ctabutton m-match {
    case button: textctabutton => uwt.ctabutton.text(textctabuttonmawshawwew(button))
    case button: i-iconctabutton => uwt.ctabutton.icon(iconctabuttonmawshawwew(button))
  }
}
