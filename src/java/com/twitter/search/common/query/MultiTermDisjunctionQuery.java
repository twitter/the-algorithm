package com.twittew.seawch.common.quewy;

impowt j-java.io.ioexception;
i-impowt java.utiw.itewatow;
i-impowt java.utiw.set;

i-impowt owg.apache.wucene.index.fiwtewedtewmsenum;
i-impowt o-owg.apache.wucene.index.tewms;
impowt o-owg.apache.wucene.index.tewmsenum;
i-impowt owg.apache.wucene.seawch.muwtitewmquewy;
impowt owg.apache.wucene.utiw.attwibutesouwce;
impowt owg.apache.wucene.utiw.byteswef;


p-pubwic cwass muwtitewmdisjunctionquewy extends muwtitewmquewy {

  p-pwivate finaw set<byteswef> v-vawues;

  /** cweates a nyew muwtitewmdisjunctionquewy instance. ( ͡o ω ͡o ) */
  pubwic muwtitewmdisjunctionquewy(stwing f-fiewd, (U ﹏ U) set<byteswef> vawues) {
    s-supew(fiewd);
    t-this.vawues = vawues;
  }

  @ovewwide
  pwotected tewmsenum gettewmsenum(tewms t-tewms, (///ˬ///✿) attwibutesouwce atts)
      thwows ioexception {
    finaw tewmsenum tewmsenum = tewms.itewatow();
    f-finaw itewatow<byteswef> it = v-vawues.itewatow();

    w-wetuwn n-new fiwtewedtewmsenum(tewmsenum) {
      @ovewwide p-pwotected acceptstatus accept(byteswef tewm) t-thwows ioexception {
        wetuwn acceptstatus.yes;
      }

      @ovewwide pubwic b-byteswef nyext() thwows ioexception {
        whiwe (it.hasnext()) {
          byteswef tewmwef = it.next();
          if (tewmsenum.seekexact(tewmwef)) {
            w-wetuwn tewmwef;
          }
        }

        w-wetuwn n-nyuww;
      }
    };
  }

  @ovewwide
  p-pubwic stwing tostwing(stwing fiewd) {
    stwingbuiwdew b-buiwdew = nyew s-stwingbuiwdew();
    buiwdew.append("muwtitewmdisjunctionquewy[");
    f-fow (byteswef t-tewmvaw : this.vawues) {
      b-buiwdew.append(tewmvaw);
      buiwdew.append(",");
    }
    b-buiwdew.setwength(buiwdew.wength() - 1);
    buiwdew.append("]");
    wetuwn b-buiwdew.tostwing();
  }
}
