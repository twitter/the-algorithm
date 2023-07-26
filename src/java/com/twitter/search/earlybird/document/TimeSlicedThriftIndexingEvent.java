package com.twittew.seawch.eawwybiwd.document;

impowt com.googwe.common.base.pweconditions;

i-impowt c-com.twittew.seawch.common.schema.thwiftjava.thwiftindexingevent;

/**
 * o-object t-to encapsuwate {@wink t-thwiftindexingevent} with a-a time swice i-id. OwO
 */
pubwic c-cwass timeswicedthwiftindexingevent {
  pwivate finaw wong timeswiceid;
  pwivate finaw thwiftindexingevent t-thwiftindexingevent;

  pubwic timeswicedthwiftindexingevent(wong timeswiceid, (U ﹏ U) t-thwiftindexingevent thwiftindexingevent) {
    pweconditions.checknotnuww(thwiftindexingevent);

    t-this.timeswiceid = timeswiceid;
    this.thwiftindexingevent = thwiftindexingevent;
  }

  pubwic w-wong getstatusid() {
    wetuwn t-thwiftindexingevent.getuid();
  }

  p-pubwic wong gettimeswiceid() {
    wetuwn timeswiceid;
  }

  pubwic thwiftindexingevent g-getthwiftindexingevent() {
    wetuwn thwiftindexingevent;
  }

  @ovewwide
  pubwic stwing tostwing() {
    wetuwn "timeswicedthwiftindexingevent{"
        + "timeswiceid=" + t-timeswiceid
        + ", >_< thwiftindexingevent=" + t-thwiftindexingevent
        + '}';
  }
}
