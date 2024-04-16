#pragma once

# fdef __cplusplus

# nclude <twml/def nes.h>
# nclude <cstd nt>
# nclude <cstddef>
# nclude <cstr ng>

na space twml {

// A low-level b nary Thr ft wr er that can also compute output s ze
//  n dry run mode w hout copy ng  mory. See also https://g . o/vNP v
//
// WARN NG: Users of t  class are respons ble for generat ng val d Thr ft
// by follow ng t  Thr ft b nary protocol (https://g . o/vNP v).
class TWMLAP  Thr ftWr er {
  protected:
    bool m_dry_run;
    u nt8_t *m_buffer;
    s ze_t m_buffer_s ze;
    s ze_t m_bytes_wr ten;

    template <typena  T>  nl ne u nt64_t wr e(T val);

  publ c:
    // buffer:        mory to wr e t  b nary Thr ft to.
    // buffer_s ze:  Length of t  buffer.
    // dry_run:       f true, just count bytes 'wr ten' but do not copy  mory.
    //                f false, wr e b nary Thr ft to t  buffer normally.
    //               Useful to determ ne output s ze for TensorFlow allocat ons.
    Thr ftWr er(u nt8_t *buffer, s ze_t buffer_s ze, bool dry_run = false) :
        m_dry_run(dry_run),
        m_buffer(buffer),
        m_buffer_s ze(buffer_s ze),
        m_bytes_wr ten(0) {}

    // total bytes wr ten to t  buffer s nce object creat on
    u nt64_t getBytesWr ten();

    // encode  aders and values  nto t  buffer
    u nt64_t wr eStructF eld ader( nt8_t f eld_type,  nt16_t f eld_ d);
    u nt64_t wr eStructStop();
    u nt64_t wr eL st ader( nt8_t ele nt_type,  nt32_t num_elems);
    u nt64_t wr eMap ader( nt8_t key_type,  nt8_t val_type,  nt32_t num_elems);
    u nt64_t wr eDouble(double val);
    u nt64_t wr e nt8( nt8_t val);
    u nt64_t wr e nt16( nt16_t val);
    u nt64_t wr e nt32( nt32_t val);
    u nt64_t wr e nt64( nt64_t val);
    u nt64_t wr eB nary(const u nt8_t *bytes,  nt32_t num_bytes);
    // cl ents expect UTF-8-encoded str ngs per t  Thr ft protocol
    // (often t   s just used to send bytes, not real str ngs though)
    u nt64_t wr eStr ng(std::str ng str);
    u nt64_t wr eBool(bool val);
};

}
#end f
