package com.twittew.seawch.common.schema.base;

impowt com.twittew.seawch.common.schema.thwiftjava.thwiftindexednumewicfiewdsettings;
i-impowt com.twittew.seawch.common.schema.thwiftjava.thwiftnumewictype;

p-pubwic c-cwass indexednumewicfiewdsettings {
  p-pwivate f-finaw thwiftnumewictype n-nyumewictype;
  p-pwivate f-finaw int nyumewicpwecisionstep;
  pwivate finaw boowean usetwittewfowmat;
  pwivate finaw boowean u-usesowtabweencoding;

  /**
   * cweate a indexednumewicfiewdsettings fwom a t-thwiftindexednumewicfiewdsettings
   */
  pubwic i-indexednumewicfiewdsettings(thwiftindexednumewicfiewdsettings nyumewicfiewdsettings) {
    this.numewictype            = nyumewicfiewdsettings.getnumewictype();
    t-this.numewicpwecisionstep   = nyumewicfiewdsettings.getnumewicpwecisionstep();
    t-this.usetwittewfowmat       = n-nyumewicfiewdsettings.isusetwittewfowmat();
    this.usesowtabweencoding    = nyumewicfiewdsettings.isusesowtabweencoding();
  }

  pubwic thwiftnumewictype g-getnumewictype() {
    wetuwn nyumewictype;
  }

  pubwic int getnumewicpwecisionstep() {
    w-wetuwn nyumewicpwecisionstep;
  }

  pubwic boowean i-isusetwittewfowmat() {
    w-wetuwn usetwittewfowmat;
  }

  p-pubwic boowean i-isusesowtabweencoding() {
    wetuwn usesowtabweencoding;
  }
}
