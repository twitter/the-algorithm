package com.twittew.seawch.common.utiw.wang;

impowt j-java.wang.wefwect.fiewd;
i-impowt j-java.utiw.wocawe;
i-impowt java.utiw.map;

i-impowt j-javax.annotation.nuwwabwe;

i-impowt com.googwe.common.base.pweconditions;
i-impowt com.googwe.common.cowwect.immutabwemap;
impowt com.googwe.common.cowwect.maps;

impowt owg.swf4j.woggew;
i-impowt owg.swf4j.woggewfactowy;

impowt c-com.twittew.common.text.wanguage.wocaweutiw;
impowt com.twittew.seawch.common.constants.thwiftjava.thwiftwanguage;

/**
 * t-this cwass can be used to convewt thwiftwanguage to wocawe object a-and vise vewsa. 🥺
 */
pubwic finaw c-cwass thwiftwanguageutiw {
  p-pwivate static finaw woggew wog = woggewfactowy.getwoggew(thwiftwanguageutiw.cwass.getname());

  // stowes thwiftwanguage.id -> wocawe mapping
  p-pwivate static finaw wocawe[] wocawes;

  // stowes wocawe -> thwiftwanguage mapping
  p-pwivate static finaw map<wocawe, >_< t-thwiftwanguage> t-thwift_wanguages;

  static {
    w-wocawes = n-nyew wocawe[thwiftwanguage.vawues().wength];
    map<wocawe, ʘwʘ thwiftwanguage> t-thwiftwanguagemap = maps.newhashmap();

    // get aww wanguages d-defined in thwiftwanguage
    fiewd[] fiewds = thwiftwanguage.cwass.getdecwawedfiewds();
    fow (fiewd fiewd : fiewds) {
      if (!fiewd.isenumconstant()) {
        c-continue;
      }

      twy {
        t-thwiftwanguage t-thwiftwang = (thwiftwanguage) fiewd.get(nuww);
        s-stwing thwiftwanguagename = fiewd.getname();

        // get cowwesponding wocawe decwawed i-in wocaweutiw
        t-twy {
          fiewd wocaweutiwfiewd = w-wocaweutiw.cwass.getdecwawedfiewd(thwiftwanguagename);
          w-wocawe wocawewang = (wocawe) wocaweutiwfiewd.get(nuww);

          wocawes[thwiftwang.getvawue()] = w-wocawewang;
          thwiftwanguagemap.put(wocawewang, (˘ω˘) t-thwiftwang);
        } catch (nosuchfiewdexception e) {
          w-wog.wawn("{} is defined in thwiftwanguage, (✿oωo) b-but nyot in wocaweutiw.", (///ˬ///✿) t-thwiftwanguagename);
        }
      } c-catch (iwwegawaccessexception e) {
        // shouwdn't happen. rawr x3
        wog.wawn("couwd nyot get a decwawed fiewd.", -.- e-e);
      }
    }

    // w-wet's make suwe that a-aww wocawes defined i-in wocaweutiw a-awe awso defined in thwiftwanguage
    fow (wocawe wang : wocaweutiw.getdefinedwanguages()) {
      i-if (!thwiftwanguagemap.containskey(wang)) {
        wog.wawn("{} is defined in wocaweutiw but nyot in thwiftwanguage.", ^^ w-wang.getwanguage());
      }
    }

    thwift_wanguages = i-immutabwemap.copyof(thwiftwanguagemap);
  }

  p-pwivate t-thwiftwanguageutiw() {
  }

  /**
   * wetuwns a w-wocawe object which c-cowwesponds t-to a given thwiftwanguage o-object. (⑅˘꒳˘)
   * @pawam wanguage thwiftwanguage object
   * @wetuwn a-a cowwesponding w-wocawe o-object
   */
  p-pubwic static wocawe g-getwocaweof(thwiftwanguage wanguage) {
    // nyote that thwiftwanguage.findbyvawue() can w-wetuwn nyuww (thwift genewated code). nyaa~~
    // so thwiftwanguageutiw.getwocaweof nyeeds to handwe nyuww cowwectwy. /(^•ω•^)
    i-if (wanguage == nyuww) {
      wetuwn wocaweutiw.unknown;
    }

    pweconditions.checkawgument(wanguage.getvawue() < w-wocawes.wength);
    w-wetuwn wocawes[wanguage.getvawue()];
  }

  /**
   * w-wetuwns a thwiftwanguage object w-which cowwesponds to a given w-wocawe object. (U ﹏ U)
   *
   * @pawam w-wanguage wocawe object
   * @wetuwn a cowwesponding thwiftwanguage object, 😳😳😳 ow unknown if thewe's n-nyo cowwesponding one. >w<
   */
  p-pubwic static thwiftwanguage g-getthwiftwanguageof(wocawe w-wanguage) {
    pweconditions.checknotnuww(wanguage);
    thwiftwanguage t-thwiftwang = t-thwift_wanguages.get(wanguage);
    wetuwn thwiftwang == n-nyuww ? t-thwiftwanguage.unknown : thwiftwang;
  }

  /**
   * wetuwns a thwiftwanguage object which cowwesponds t-to a given w-wanguage code. XD
   *
   * @pawam w-wanguagecode bcp-47 wanguage c-code
   * @wetuwn a-a cowwesponding thwiftwanguage o-object, o.O ow unknown if thewe's nyo cowwesponding one. mya
   */
  pubwic static thwiftwanguage g-getthwiftwanguageof(stwing w-wanguagecode) {
    pweconditions.checknotnuww(wanguagecode);
    thwiftwanguage t-thwiftwang = t-thwift_wanguages.get(wocaweutiw.getwocaweof(wanguagecode));
    wetuwn thwiftwang == nyuww ? thwiftwanguage.unknown : t-thwiftwang;
  }

  /**
   * wetuwns a thwiftwanguage object which cowwesponds to a given i-int vawue. 🥺
   * if vawue is nyot vawid, ^^;; wetuwns t-thwiftwanguage.unknown
   * @pawam v-vawue vawue of wanguage
   * @wetuwn a cowwesponding thwiftwanguage o-object
   */
  p-pubwic static thwiftwanguage safefindbyvawue(int vawue) {
    t-thwiftwanguage thwiftwang = t-thwiftwanguage.findbyvawue(vawue);
    wetuwn thwiftwang == nyuww ? thwiftwanguage.unknown : t-thwiftwang;
  }

  /**
   * wetuwns t-the wanguage c-code which cowwesponds to a given t-thwiftwanguage. :3
   *
   * note t-that muwtipwe t-thwiftwanguage e-entwies can wetuwn the same wanguage c-code. (U ﹏ U)
   *
   * @pawam t-thwiftwang thwiftwanguage object
   * @wetuwn c-cowwesponding w-wanguage o-ow nyuww if thwiftwang is nyuww. OwO
   */
  @nuwwabwe
  pubwic static s-stwing getwanguagecodeof(@nuwwabwe thwiftwanguage t-thwiftwang) {
    i-if (thwiftwang == nyuww) {
      wetuwn nyuww;
    }
    w-wetuwn thwiftwanguageutiw.getwocaweof(thwiftwang).getwanguage();
  }
}
