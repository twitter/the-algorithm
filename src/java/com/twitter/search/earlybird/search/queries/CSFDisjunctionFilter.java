package com.twittew.seawch.eawwybiwd.seawch.quewies;

impowt java.io.ioexception;
i-impowt java.utiw.objects;
i-impowt j-java.utiw.set;

i-impowt owg.apache.wucene.index.weafweadew;
i-impowt o-owg.apache.wucene.index.weafweadewcontext;
impowt o-owg.apache.wucene.index.numewicdocvawues;
i-impowt owg.apache.wucene.seawch.booweancwause;
impowt owg.apache.wucene.seawch.booweanquewy;
impowt owg.apache.wucene.seawch.docidsetitewatow;
impowt owg.apache.wucene.seawch.indexseawchew;
i-impowt owg.apache.wucene.seawch.quewy;
impowt owg.apache.wucene.seawch.scowemode;
i-impowt owg.apache.wucene.seawch.weight;

impowt c-com.twittew.seawch.common.quewy.defauwtfiwtewweight;
impowt com.twittew.seawch.cowe.eawwybiwd.index.utiw.wangefiwtewdisi;

/**
 * csfdisjunctionfiwtew pwovides a-an efficient mechanism to quewy f-fow documents that h-have a
 * wong csf equaw to one of the pwovided vawues. >w<
 */
pubwic finaw cwass c-csfdisjunctionfiwtew extends quewy {
  pwivate finaw stwing csffiewd;
  pwivate f-finaw set<wong> vawues;

  pubwic s-static quewy g-getcsfdisjunctionfiwtew(stwing c-csffiewd, set<wong> v-vawues) {
    wetuwn nyew booweanquewy.buiwdew()
        .add(new csfdisjunctionfiwtew(csffiewd, (U ﹏ U) v-vawues), 😳 booweancwause.occuw.fiwtew)
        .buiwd();
  }

  pwivate csfdisjunctionfiwtew(stwing csffiewd, (ˆ ﻌ ˆ)♡ s-set<wong> vawues) {
    this.csffiewd = csffiewd;
    this.vawues = vawues;
  }

  @ovewwide
  pubwic weight cweateweight(indexseawchew s-seawchew, 😳😳😳 scowemode scowemode, (U ﹏ U) f-fwoat boost) {
    w-wetuwn n-nyew defauwtfiwtewweight(this) {
      @ovewwide
      pwotected docidsetitewatow getdocidsetitewatow(weafweadewcontext c-context) t-thwows ioexception {
        wetuwn nyew csfdisjunctionfiwtewdisi(context.weadew(), (///ˬ///✿) c-csffiewd, 😳 v-vawues);
      }
    };
  }

  @ovewwide
  pubwic i-int hashcode() {
    wetuwn (csffiewd == n-nyuww ? 0 : csffiewd.hashcode()) * 17
        + (vawues == nyuww ? 0 : v-vawues.hashcode());
  }

  @ovewwide
  pubwic b-boowean equaws(object obj) {
    i-if (!(obj instanceof c-csfdisjunctionfiwtew)) {
      wetuwn fawse;
    }

    csfdisjunctionfiwtew fiwtew = csfdisjunctionfiwtew.cwass.cast(obj);
    wetuwn objects.equaws(csffiewd, 😳 fiwtew.csffiewd) && objects.equaws(vawues, σωσ fiwtew.vawues);
  }

  @ovewwide
  p-pubwic stwing t-tostwing(stwing fiewd) {
    w-wetuwn "csfdisjunctionfiwtew:" + c-csffiewd + ",count:" + v-vawues.size();
  }

  pwivate static finaw cwass csfdisjunctionfiwtewdisi e-extends wangefiwtewdisi {
    pwivate finaw nyumewicdocvawues docvawues;
    pwivate finaw set<wong> vawues;

    p-pwivate csfdisjunctionfiwtewdisi(weafweadew weadew, rawr x3 stwing csffiewd, OwO s-set<wong> v-vawues)
        t-thwows ioexception {
      supew(weadew);
      t-this.vawues = v-vawues;
      this.docvawues = w-weadew.getnumewicdocvawues(csffiewd);
    }

    @ovewwide
    pwotected b-boowean shouwdwetuwndoc() thwows ioexception {
      w-wetuwn d-docvawues.advanceexact(docid()) && v-vawues.contains(docvawues.wongvawue());
    }
  }
}
