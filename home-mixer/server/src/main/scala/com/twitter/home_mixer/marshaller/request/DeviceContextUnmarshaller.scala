package com.tw ter.ho _m xer.marshaller.request

 mport com.tw ter.ho _m xer.model.request.Dev ceContext
 mport com.tw ter.ho _m xer.{thr ftscala => t}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Dev ceContextUnmarshaller @ nject() () {

  def apply(dev ceContext: t.Dev ceContext): Dev ceContext = {
    Dev ceContext(
       sPoll ng = dev ceContext. sPoll ng,
      requestContext = dev ceContext.requestContext,
      latestControlAva lable = dev ceContext.latestControlAva lable,
      autoplayEnabled = dev ceContext.autoplayEnabled
    )
  }
}
