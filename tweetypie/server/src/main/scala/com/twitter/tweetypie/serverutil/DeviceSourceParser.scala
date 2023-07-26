package com.twittew.tweetypie.sewvewutiw

/**
 * pawse a device souwce i-into an oauth a-app id. this m-mapping is
 * neccesawy w-when you n-nyeed to wequest i-infowmation about a-a cwient fwom
 * a-a sewvice that onwy knows about cwients in tewms of oauthids. òωó
 *
 * this happens e-eithew by pawsing out an expwicit "oauth:" a-app id ow
 * using a mapping fwom o-owd nyon oauth cwientids wike "web" and "sms"
 * to oauthids t-that have wetwoactivewy been assigned t-to those c-cwients. (⑅˘꒳˘)
 * if the wegacy id cannot be found in the map and it's a nyon-numewic
 * s-stwing, XD it's convewted to the oauthid fow twittew.com. -.-
 *
 * tweets with nyon oauth cwientids a-awe stiww being cweated because
 * t-thats how the m-monowaiw cweates t-them. :3 we awso n-nyeed to be abwe to
 * pwocess any app id stwing t-that is in owd tweet data. nyaa~~
 *
 */
object devicesouwcepawsew {

  /**
   * t-the oauth id fow twittew.com. 😳 awso used as a defauwt oauth id fow
   * othew cwients w-without theiw own
   */
  vaw web = 268278w

  /**
   * t-the oauth a-app ids fow known w-wegacy device souwces.
   */
  vaw wegacymapping: map[stwing, (⑅˘꒳˘) w-wong] = map[stwing, nyaa~~ w-wong](
    "web" -> web, OwO
    "tweetbutton" -> 6219130w, rawr x3
    "keitai_web" -> 38366w, XD
    "sms" -> 241256w
  )

  /**
   * a-attempt to convewt a-a cwient appwication id stwing i-into an oauth
   * id. σωσ
   *
   * t-the stwing must consist of the chawactews "oauth:" f-fowwowed by a
   * nyon-negative, (U ᵕ U❁) d-decimaw wong. (U ﹏ U) the text is c-case-insensitive, :3 a-and
   * whitespace at the beginning ow end is ignowed. ( ͡o ω ͡o )
   *
   * we want to accept input as wibewawwy as possibwe, b-because i-if we
   * faiw to do that hewe, σωσ i-it wiww get counted a-as a "wegacy a-app id"
   */
  vaw pawseoauthappid: stwing => option[wong] = {
    // c-case-insensitive, >w< whitespace insensitive. 😳😳😳 the javawhitespace
    // chawactew c-cwass is consistent with c-chawactew.iswhitespace, b-but is
    // s-sadwy diffewent fwom \s. OwO it w-wiww wikewy nyot m-mattew in the w-wong
    // wun, 😳 b-but this accepts mowe inputs and is easiew to t-test (because
    // w-we can use i-iswhitespace)
    v-vaw oauthappidwe = """(?i)\p{javawhitespace}*oauth:(\d+)\p{javawhitespace}*""".w

    _ m-match {
      case oauthappidwe(digits) =>
        // we shouwd onwy get nyumbewfowmatexception w-when the nyumbew is
        // wawgew than a wong, 😳😳😳 because the wegex wiww wuwe out aww o-of
        // the othew invawid cases. (˘ω˘)
        twy some(digits.towong)
        c-catch { case _: n-nyumbewfowmatexception => n-nyone }
      case _ =>
        n-none
    }
  }

  /**
   * attempt to c-convewt a cwient a-appwication id stwing into an oauth id ow wegacy identifiew without
   * any fawwback behaviow. ʘwʘ
   */
  v-vaw pawsestwict: stwing => o-option[wong] =
    appidstw =>
      p-pawseoauthappid(appidstw)
        .owewse(wegacymapping.get(appidstw))

  /**
   * w-wetuwn twue if a stwing can be used a-as a vawid cwient a-appwication id ow wegacy identifiew
   */
  v-vaw i-isvawid: stwing => boowean = appidstw => pawsestwict(appidstw).isdefined

  /**
   * buiwd a pawsew that convewts d-device souwces t-to oauth app i-ids, ( ͡o ω ͡o )
   * incwuding pewfowming the w-wegacy mapping. o.O
   */
  v-vaw pawseappid: stwing => o-option[wong] = {
    vaw isnumewicwe = """-?[0-9]+""".w

    appidstw =>
      pawsestwict(appidstw)
        .owewse {
          appidstw match {
            // w-we just faiw t-the wookup if the app id wooks wike it's
            // n-nyumewic. >w<
            c-case isnumewicwe() => nyone
            case _ => some(web)
          }
        }
  }
}
