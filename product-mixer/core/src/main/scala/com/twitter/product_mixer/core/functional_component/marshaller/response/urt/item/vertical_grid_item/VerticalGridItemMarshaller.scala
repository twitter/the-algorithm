package com.twittew.pwoduct_mixew.cowe.functionaw_component.mawshawwew.wesponse.uwt.item.vewticaw_gwid_item

impowt c-com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.item.vewticaw_gwid_item.vewticawgwiditem
i-impowt c-com.twittew.timewines.wendew.{thwiftscawa => u-uwt}
i-impowt javax.inject.inject
i-impowt j-javax.inject.singweton

@singweton
c-cwass vewticawgwiditemmawshawwew @inject() (
  vewticawgwiditemcontentmawshawwew: vewticawgwiditemcontentmawshawwew) {

  def appwy(vewticawgwiditem: vewticawgwiditem): u-uwt.timewineitemcontent =
    uwt.timewineitemcontent.vewticawgwiditem(
      uwt.vewticawgwiditem(
        content = vewticawgwiditemcontentmawshawwew(vewticawgwiditem)
      )
    )
}
