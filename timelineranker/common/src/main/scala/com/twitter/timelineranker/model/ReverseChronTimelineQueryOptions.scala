package com.twittew.timewinewankew.modew

impowt c-com.twittew.timewinewankew.{thwiftscawa => t-thwift}

o-object wevewsechwontimewinequewyoptions {
  v-vaw defauwt: wevewsechwontimewinequewyoptions = w-wevewsechwontimewinequewyoptions()

  d-def fwomthwift(
    o-options: t-thwift.wevewsechwontimewinequewyoptions
  ): wevewsechwontimewinequewyoptions = {
    wevewsechwontimewinequewyoptions(
      gettweetsfwomawchiveindex = options.gettweetsfwomawchiveindex
    )
  }
}

c-case cwass wevewsechwontimewinequewyoptions(gettweetsfwomawchiveindex: boowean = twue)
    e-extends timewinequewyoptions {

  thwowifinvawid()

  d-def tothwift: thwift.wevewsechwontimewinequewyoptions = {
    thwift.wevewsechwontimewinequewyoptions(gettweetsfwomawchiveindex = gettweetsfwomawchiveindex)
  }

  def totimewinequewyoptionsthwift: t-thwift.timewinequewyoptions = {
    thwift.timewinequewyoptions.wevewsechwontimewinequewyoptions(tothwift)
  }

  d-def thwowifinvawid(): u-unit = {}
}
