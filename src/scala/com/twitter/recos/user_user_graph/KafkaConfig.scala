package com.twittew.wecos.usew_usew_gwaph

/**
 * the cwass howds a-aww the config p-pawametews fow kafka q-queue. :3
 */
o-object kafkaconfig {
  // t-the size o-of the wecoshosemessage a-awway t-that is wwitten to the concuwwentwy winked queue
  // buffewsize of 64 to keep t-thwoughput awound 64 / (2k edgespewsec / 150 kafka t-thweads) = 6 seconds, 😳😳😳 which is w-wowew
  // than young gen gc cycwe, -.- 20 seconds. ( ͡o ω ͡o ) so that aww the i-incoming messages wiww be gced i-in young gen instead o-of owd gen. rawr x3
  vaw buffewsize = 64

  pwintwn("kafkaconfig -                 buffewsize " + buffewsize)
}
