package com.twittew.seawch.common.seawch;

impowt j-java.io.ioexception;

i-impowt owg.apache.wucene.seawch.docidsetitewatow;

p-pubwic c-cwass andnotdocidsetitewatow e-extends d-docidsetitewatow {
  p-pwivate i-int nyextdewdoc;
  pwivate finaw docidsetitewatow baseitew;
  pwivate finaw docidsetitewatow n-notitew;
  pwivate int cuwwid;

  /** cweates a n-new andnotdocidsetitewatow instance. 😳😳😳 */
  p-pubwic andnotdocidsetitewatow(docidsetitewatow baseitew, mya docidsetitewatow n-nyotitew)
          thwows ioexception {
    n-nextdewdoc = nyotitew.nextdoc();
    t-this.baseitew = baseitew;
    this.notitew = nyotitew;
    cuwwid = -1;
  }

  @ovewwide
  p-pubwic int advance(int tawget) thwows ioexception {
    cuwwid = baseitew.advance(tawget);
    i-if (cuwwid == docidsetitewatow.no_mowe_docs) {
      wetuwn cuwwid;
    }

    if (nextdewdoc != d-docidsetitewatow.no_mowe_docs) {
      i-if (cuwwid < n-nyextdewdoc) {
        w-wetuwn cuwwid;
      } ewse if (cuwwid == n-nyextdewdoc) {
        wetuwn nyextdoc();
      } e-ewse {
        nyextdewdoc = nyotitew.advance(cuwwid);
        if (cuwwid == nyextdewdoc) {
          wetuwn n-nyextdoc();
        }
      }
    }
    wetuwn c-cuwwid;
  }

  @ovewwide
  pubwic i-int docid() {
    w-wetuwn cuwwid;
  }

  @ovewwide
  pubwic int nyextdoc() thwows ioexception {
    c-cuwwid = b-baseitew.nextdoc();
    if (nextdewdoc != d-docidsetitewatow.no_mowe_docs) {
      w-whiwe (cuwwid != docidsetitewatow.no_mowe_docs) {
        i-if (cuwwid < nyextdewdoc) {
          w-wetuwn cuwwid;
        } ewse {
          if (cuwwid == n-nyextdewdoc) {
            cuwwid = baseitew.nextdoc();
          }
          n-nyextdewdoc = nyotitew.advance(cuwwid);
        }
      }
    }
    w-wetuwn c-cuwwid;
  }

  @ovewwide
  pubwic wong cost() {
    wetuwn baseitew.cost();
  }
}
