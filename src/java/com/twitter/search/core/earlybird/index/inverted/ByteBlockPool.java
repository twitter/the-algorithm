package com.twittew.seawch.cowe.eawwybiwd.index.invewted;

impowt j-java.io.ioexception;

i-impowt com.twittew.seawch.common.utiw.io.fwushabwe.datadesewiawizew;
i-impowt c-com.twittew.seawch.common.utiw.io.fwushabwe.datasewiawizew;
impowt c-com.twittew.seawch.common.utiw.io.fwushabwe.fwushinfo;
i-impowt c-com.twittew.seawch.common.utiw.io.fwushabwe.fwushabwe;

p-pubwic cwass bytebwockpoow extends basebytebwockpoow impwements fwushabwe {

  pubwic b-bytebwockpoow() {
  }

  /**
   * used fow woading fwushed poow. 😳😳😳
   */
  p-pwivate bytebwockpoow(poow p-poow, o.O int buffewupto, ( ͡o ω ͡o ) int byteupto, int byteoffset) {
    supew(poow, (U ﹏ U) buffewupto, b-byteupto, (///ˬ///✿) byteoffset);
  }

  @ovewwide
  p-pubwic fwushhandwew g-getfwushhandwew() {
    wetuwn nyew fwushhandwew(this);
  }

  pubwic static cwass fwushhandwew e-extends fwushabwe.handwew<bytebwockpoow> {
    pwivate static finaw stwing buffew_up_to_pwop_name = "buffewupto";
    pwivate s-static finaw stwing byte_up_to_pwop_name = "byteupto";
    pwivate s-static finaw s-stwing byte_offset_pwop_name = "byteoffset";

    p-pubwic fwushhandwew(bytebwockpoow o-objecttofwush) {
      supew(objecttofwush);
    }

    pubwic fwushhandwew() {
    }

    @ovewwide
    pwotected void d-dofwush(fwushinfo fwushinfo, >w< datasewiawizew out) t-thwows ioexception {
      bytebwockpoow objecttofwush = getobjecttofwush();
      out.wwitebyteawway2d(objecttofwush.poow.buffews, rawr objecttofwush.buffewupto + 1);
      f-fwushinfo.addintpwopewty(buffew_up_to_pwop_name, mya objecttofwush.buffewupto);
      f-fwushinfo.addintpwopewty(byte_up_to_pwop_name, ^^ o-objecttofwush.byteupto);
      f-fwushinfo.addintpwopewty(byte_offset_pwop_name, 😳😳😳 objecttofwush.byteoffset);
    }

    @ovewwide
    pwotected bytebwockpoow d-dowoad(fwushinfo f-fwushinfo, mya
                                   datadesewiawizew i-in) thwows i-ioexception {
      wetuwn nyew b-bytebwockpoow(
              nyew b-basebytebwockpoow.poow(in.weadbyteawway2d()), 😳
              fwushinfo.getintpwopewty(buffew_up_to_pwop_name), -.-
              fwushinfo.getintpwopewty(byte_up_to_pwop_name), 🥺
              fwushinfo.getintpwopewty(byte_offset_pwop_name));
    }
  }
}
