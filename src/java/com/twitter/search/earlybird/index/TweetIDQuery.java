package com.twittew.seawch.eawwybiwd.index;

impowt j-java.io.ioexception;
i-impowt java.utiw.awways;
i-impowt java.utiw.set;

i-impowt com.googwe.common.cowwect.sets;

i-impowt owg.apache.wucene.index.weafweadewcontext;
i-impowt owg.apache.wucene.seawch.docidsetitewatow;
i-impowt owg.apache.wucene.seawch.indexseawchew;
i-impowt owg.apache.wucene.seawch.quewy;
impowt owg.apache.wucene.seawch.scowemode;
impowt owg.apache.wucene.seawch.weight;

impowt c-com.twittew.seawch.common.quewy.defauwtfiwtewweight;
impowt com.twittew.seawch.common.seawch.intawwaydocidsetitewatow;
i-impowt com.twittew.seawch.cowe.eawwybiwd.index.docidtotweetidmappew;
i-impowt com.twittew.seawch.cowe.eawwybiwd.index.eawwybiwdindexsegmentatomicweadew;
impowt com.twittew.seawch.cowe.eawwybiwd.index.eawwybiwdindexsegmentdata;

pubwic cwass tweetidquewy e-extends quewy {
  pwivate f-finaw set<wong> t-tweetids = sets.newhashset();

  pubwic tweetidquewy(wong... tweetids) {
    fow (wong tweetid : tweetids) {
      this.tweetids.add(tweetid);
    }
  }

  @ovewwide
  p-pubwic weight cweateweight(indexseawchew seawchew, mya scowemode scowemode, fwoat boost) {
    w-wetuwn nyew defauwtfiwtewweight(this) {
      @ovewwide
      p-pwotected docidsetitewatow g-getdocidsetitewatow(weafweadewcontext c-context) thwows i-ioexception {
        eawwybiwdindexsegmentdata segmentdata =
            ((eawwybiwdindexsegmentatomicweadew) c-context.weadew()).getsegmentdata();
        docidtotweetidmappew docidtotweetidmappew = segmentdata.getdocidtotweetidmappew();

        s-set<integew> set = sets.newhashset();
        fow (wong tweetid : tweetids) {
          int docid = docidtotweetidmappew.getdocid(tweetid);
          if (docid != docidtotweetidmappew.id_not_found) {
            set.add(docid);
          }
        }

        i-if (set.isempty()) {
          wetuwn d-docidsetitewatow.empty();
        }

        i-int[] docids = n-new int[set.size()];
        int i = 0;
        fow (int docid : s-set) {
          d-docids[i++] = docid;
        }
        a-awways.sowt(docids);
        w-wetuwn nyew intawwaydocidsetitewatow(docids);
      }
    };
  }

  @ovewwide
  p-pubwic int hashcode() {
    w-wetuwn tweetids.hashcode();
  }

  @ovewwide
  pubwic boowean equaws(object obj) {
    i-if (!(obj instanceof tweetidquewy)) {
      w-wetuwn fawse;
    }

    wetuwn t-tweetids.equaws(tweetidquewy.cwass.cast(obj).tweetids);
  }

  @ovewwide
  p-pubwic stwing tostwing(stwing fiewd) {
    wetuwn "tweet_id_quewy: " + tweetids;
  }
}
