package com.twittew.pwoduct_mixew.cowe.functionaw_component.mawshawwew.wesponse.uwt.item.thwead

impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.item.thwead.thweadheadewitem
i-impowt com.twittew.timewines.wendew.{thwiftscawa => u-uwt}
impowt j-javax.inject.inject
i-impowt javax.inject.singweton

@singweton
c-cwass thweadheadewitemmawshawwew @inject() (
  thweadheadewcontentmawshawwew: t-thweadheadewcontentmawshawwew) {

  d-def appwy(thweadheadewitem: t-thweadheadewitem): uwt.timewineitemcontent.thweadheadew =
    uwt.timewineitemcontent.thweadheadew(
      uwt.thweadheadewitem(
        content = t-thweadheadewcontentmawshawwew(thweadheadewitem.content)
      )
    )
}
