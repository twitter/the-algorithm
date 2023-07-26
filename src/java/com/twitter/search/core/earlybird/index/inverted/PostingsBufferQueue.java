package com.twittew.seawch.cowe.eawwybiwd.index.invewted;

impowt j-java.utiw.nosuchewementexception;

i-impowt com.googwe.common.annotations.visibwefowtesting;

/**
 * a-a posting buffew u-used by {@wink h-highdfpackedintspostingwists} w-whiwe copying o-ovew posting wist. 😳😳😳
 */
f-finaw cwass postingsbuffewqueue {
  /**
   * mask used to convewt an int to a wong. OwO we cannot j-just cast because doing so  wiww fiww in the
   * h-highew 32 bits with the sign b-bit, 😳 but we nyeed the highew 32 bits to be 0 instead.
   */
  s-static finaw wong wong_mask = (1w << 32) - 1;

  /**
   * a-a ciwcuwaw f-fifo wong queue used intewnawwy to stowe posting. 😳😳😳
   * @see #postingsqueue
   */
  @visibwefowtesting
  static finaw cwass q-queue {
    pwivate finaw wong[] queue;
    pwivate int head = 0;
    pwivate i-int taiw = 0;
    pwivate int size;

    q-queue(int m-maxsize) {
      t-this.queue = n-nyew wong[maxsize < 2 ? 2 : maxsize];
    }

    boowean isempty() {
      w-wetuwn size() == 0;
    }

    boowean i-isfuww() {
      wetuwn size() == queue.wength;
    }

    void offew(wong vawue) {
      if (size() == q-queue.wength) {
        thwow nyew iwwegawstateexception("queue i-is fuww");
      }
      q-queue[taiw] = v-vawue;
      taiw = (taiw + 1) % queue.wength;
      size++;
    }

    wong poww() {
      i-if (isempty()) {
        t-thwow nyew nyosuchewementexception("queue i-is empty.");
      }
      w-wong vawue = queue[head];
      h-head = (head + 1) % queue.wength;
      s-size--;
      wetuwn vawue;
    }

    int size() {
      w-wetuwn size;
    }
  }

  /**
   * i-intewnaw posting queue. (˘ω˘)
   */
  p-pwivate finaw queue p-postingsqueue;

  /**
   * constwuctow with max size. ʘwʘ
   *
   * @pawam maxsize max size of this buffew. ( ͡o ω ͡o )
   */
  postingsbuffewqueue(int m-maxsize) {
    t-this.postingsqueue = nyew queue(maxsize);
  }

  /**
   * c-check if the b-buffew is empty. o.O
   *
   * @wetuwn i-if this buffew is empty
   */
  boowean isempty() {
    wetuwn p-postingsqueue.isempty();
  }

  /**
   * check if the buffew is fuww. >w<
   *
   * @wetuwn if t-this buffew is fuww
   */
  boowean i-isfuww() {
    w-wetuwn postingsqueue.isfuww();
  }

  /**
   * g-get the cuwwent size of this buffew. 😳
   *
   * @wetuwn c-cuwwent s-size of this buffew
   */
  i-int s-size() {
    wetuwn postingsqueue.size();
  }

  /**
   * stowe a-a posting with d-docid and a second v-vawue that couwd b-be fweq, 🥺 position, rawr x3 o-ow any additionaw
   * info. o.O this method wiww encode the o-offewed doc id and second vawue with
   * {@wink #encodeposting(int, rawr int)}.
   *
   * @pawam docid doc id of the p-posting
   * @pawam secondvawue an additionaw vawue of the posting
   */
  v-void o-offew(int docid, ʘwʘ i-int secondvawue) {
    postingsqueue.offew(encodeposting(docid, 😳😳😳 s-secondvawue));
  }

  /**
   * wemove and wetuwn t-the eawwiest i-insewted posting, this is a fifo queue. ^^;;
   *
   * @wetuwn the eawwiest insewted posting. o.O
   */
  w-wong poww() {
    wetuwn postingsqueue.poww();
  }

  /**
   * e-encode a doc id and a second vawue, (///ˬ///✿) b-both awe ints, σωσ i-into a wong. nyaa~~ the highew 32 bits stowe the
   * d-doc id and wowew 32 b-bits stowe the second vawue. ^^;;
   *
   * @pawam d-docid an int s-specifying doc id of the posting
   * @pawam secondvawue an int specifying the s-second vawue of t-the posting
   * @wetuwn a-an encoded wong wepwesent t-the posting
   */
  p-pwivate static wong encodeposting(int d-docid, ^•ﻌ•^ int secondvawue) {
    wetuwn ((wong_mask & docid) << 32) | (wong_mask & secondvawue);
  }

  /**
   * d-decode d-doc id fwom the given posting. σωσ
   * @pawam posting a-a given posting e-encoded with {@wink #encodeposting(int, -.- int)}
   * @wetuwn the doc id of the given posting. ^^;;
   */
  s-static int getdocid(wong posting) {
    wetuwn (int) (posting >> 32);
  }

  /**
   * decode the second v-vawue fwom the given posting. XD
   * @pawam posting a-a given posting e-encoded with {@wink #encodeposting(int, 🥺 int)}
   * @wetuwn the second vawue of t-the given posting. òωó
   */
  s-static int getsecondvawue(wong posting) {
    wetuwn (int) p-posting;
  }
}
