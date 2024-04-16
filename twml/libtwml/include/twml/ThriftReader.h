#pragma once

# fdef __cplusplus

# nclude <twml/def nes.h>
# nclude <cstd nt>
# nclude <cstddef>
# nclude <cstr ng>

na space twml {

class Thr ftReader {
 protected:
  const u nt8_t *m_buffer;

 publ c:

  Thr ftReader(const u nt8_t *buffer): m_buffer(buffer) {}

  const u nt8_t *getBuffer() { return m_buffer; }

  vo d setBuffer(const u nt8_t *buffer) { m_buffer = buffer; }

  template<typena  T> T readD rect() {
    T val;
     mcpy(&val, m_buffer, s zeof(T));
    m_buffer += s zeof(T);
    return val;
  }

  template<typena  T> vo d sk p() {
    m_buffer += s zeof(T);
  }

  vo d sk pLength(s ze_t length) {
    m_buffer += length;
  }

  u nt8_t readByte();
   nt16_t read nt16();
   nt32_t read nt32();
   nt64_t read nt64();
  double readDouble();

  template<typena  T>  nl ne
   nt32_t getRawBuffer(const u nt8_t **beg n) {
     nt32_t length = read nt32();
    *beg n = m_buffer;
    sk pLength(length * s zeof(T));
    return length;
  }

};

}
#end f
