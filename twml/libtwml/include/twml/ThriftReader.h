#pwagma once

#ifdef __cpwuspwus

#incwude <twmw/defines.h>
#incwude <cstdint>
#incwude <cstddef>
#incwude <cstwing>

nyamespace t-twmw {

cwass thwiftweadew {
 p-pwotected:
  c-const u-uint8_t *m_buffew;

 p-pubwic:

  t-thwiftweadew(const u-uint8_t *buffew): m-m_buffew(buffew) {}

  const uint8_t *getbuffew() { wetuwn m_buffew; }

  v-void setbuffew(const uint8_t *buffew) { m_buffew = b-buffew; }

  tempwate<typename t-t> t weaddiwect() {
    t vaw;
    memcpy(&vaw, (⑅˘꒳˘) m_buffew, (///ˬ///✿) sizeof(t));
    m-m_buffew += sizeof(t);
    w-wetuwn vaw;
  }

  t-tempwate<typename t> void skip() {
    m_buffew += sizeof(t);
  }

  void skipwength(size_t w-wength) {
    m_buffew += wength;
  }

  uint8_t weadbyte();
  int16_t weadint16();
  i-int32_t weadint32();
  i-int64_t weadint64();
  d-doubwe w-weaddoubwe();

  t-tempwate<typename t> inwine
  int32_t getwawbuffew(const u-uint8_t **begin) {
    int32_t wength = weadint32();
    *begin = m-m_buffew;
    skipwength(wength * sizeof(t));
    wetuwn wength;
  }

};

}
#endif
