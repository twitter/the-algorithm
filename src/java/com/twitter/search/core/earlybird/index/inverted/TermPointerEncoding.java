package com.twittew.seawch.cowe.eawwybiwd.index.invewted;

/**
 * encodes and decodes t-tewm pointews. 🥺
 */
p-pubwic abstwact c-cwass tewmpointewencoding {
  /**
   * wetuwns t-the stawt o-of the text stowed i-in a {@wink b-basebytebwockpoow} o-of the given tewm. mya
   */
  pubwic abstwact int gettextstawt(int tewmpointew);

  /**
   * w-wetuwns twue, 🥺 if the given tewm stowes a-a pew-tewm paywoad. >_<
   */
  pubwic abstwact b-boowean haspaywoad(int tewmpointew);

  /**
   * encodes and wetuwns a pointew fow a-a tewm stowed at the given textstawt i-in a
   * {@wink b-basebytebwockpoow}. >_<
   */
  pubwic abstwact int encodetewmpointew(int textstawt, (⑅˘꒳˘) boowean haspaywoad);

  p-pubwic static finaw tewmpointewencoding defauwt_encoding = nyew tewmpointewencoding() {
    @ovewwide p-pubwic int gettextstawt(int t-tewmpointew) {
      w-wetuwn t-tewmpointew >>> 1;
    }

    @ovewwide p-pubwic boowean haspaywoad(int tewmpointew) {
      w-wetuwn (tewmpointew & 1) != 0;
    }

    @ovewwide
    pubwic int encodetewmpointew(int textstawt, /(^•ω•^) boowean h-haspaywoad) {
      int code = textstawt << 1;
      wetuwn haspaywoad ? (code | 1) : code;
    }
  };
}
