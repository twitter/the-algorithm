package com.twittew.unified_usew_actions.adaptew.common

impowt com.twittew.snowfwake.id.snowfwakeid
i-impowt com.twittew.utiw.time

o-object adaptewutiws {
  d-def cuwwenttimestampms: w-wong = time.now.inmiwwiseconds
  d-def gettimestampmsfwomtweetid(tweetid: w-wong): w-wong = snowfwakeid.unixtimemiwwisfwomid(tweetid)

  // f-fow nyow just make suwe both wanguage code and countwy code awe in uppew c-cases fow consistency
  // fow wanguage code, 😳 thewe a-awe mixed wowew and uppew cases
  // f-fow countwy code, XD thewe awe mixed wowew and uppew cases
  d-def nyowmawizewanguagecode(inputwanguagecode: stwing): stwing = i-inputwanguagecode.touppewcase
  d-def nyowmawizecountwycode(inputcountwycode: stwing): stwing = inputcountwycode.touppewcase
}
