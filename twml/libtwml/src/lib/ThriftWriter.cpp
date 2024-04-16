# nclude " nternal/end anut ls.h"
# nclude " nternal/error.h"
# nclude " nternal/thr ft.h"

# nclude <twml/Thr ftWr er.h>
# nclude <twml/Error.h>
# nclude <twml/ o/ OError.h>

# nclude <cstr ng>

us ng na space twml:: o;

na space twml {

template <typena  T>  nl ne
u nt64_t Thr ftWr er::wr e(T val) {
   f (!m_dry_run) {
     f (m_bytes_wr ten + s zeof(T) > m_buffer_s ze)
      throw  OError( OError::DEST NAT ON_LARGER_THAN_CAPAC TY);
     mcpy(m_buffer, &val, s zeof(T));
    m_buffer += s zeof(T);
  }
  m_bytes_wr ten += s zeof(T);
  return s zeof(T);
}

TWMLAP  u nt64_t Thr ftWr er::getBytesWr ten() {
  return m_bytes_wr ten;
}

TWMLAP  u nt64_t Thr ftWr er::wr eStructF eld ader( nt8_t f eld_type,  nt16_t f eld_ d) {
  return wr e nt8(f eld_type) + wr e nt16(f eld_ d);
}

TWMLAP  u nt64_t Thr ftWr er::wr eStructStop() {
  return wr e nt8(stat c_cast< nt8_t>(TTYPE_STOP));
}

TWMLAP  u nt64_t Thr ftWr er::wr eL st ader( nt8_t ele nt_type,  nt32_t num_elems) {
  return wr e nt8(ele nt_type) + wr e nt32(num_elems);
}

TWMLAP  u nt64_t Thr ftWr er::wr eMap ader( nt8_t key_type,  nt8_t val_type,  nt32_t num_elems) {
  return wr e nt8(key_type) + wr e nt8(val_type) + wr e nt32(num_elems);
}

TWMLAP  u nt64_t Thr ftWr er::wr eDouble(double val) {
   nt64_t b n_value;
   mcpy(&b n_value, &val, s zeof( nt64_t));
  return wr e nt64(b n_value);
}

TWMLAP  u nt64_t Thr ftWr er::wr e nt8( nt8_t val) {
  return wr e(val);
}

TWMLAP  u nt64_t Thr ftWr er::wr e nt16( nt16_t val) {
  return wr e(betoh16(val));
}

TWMLAP  u nt64_t Thr ftWr er::wr e nt32( nt32_t val) {
  return wr e(betoh32(val));
}

TWMLAP  u nt64_t Thr ftWr er::wr e nt64( nt64_t val) {
  return wr e(betoh64(val));
}

TWMLAP  u nt64_t Thr ftWr er::wr eB nary(const u nt8_t *bytes,  nt32_t num_bytes) {
  wr e nt32(num_bytes);

   f (!m_dry_run) {
     f (m_bytes_wr ten + num_bytes > m_buffer_s ze)
      throw  OError( OError::DEST NAT ON_LARGER_THAN_CAPAC TY);
     mcpy(m_buffer, bytes, num_bytes);
    m_buffer += num_bytes;
  }
  m_bytes_wr ten += num_bytes;

  return 4 + num_bytes;
}

TWMLAP  u nt64_t Thr ftWr er::wr eStr ng(std::str ng str) {
  return wr eB nary(re nterpret_cast<const u nt8_t *>(str.data()), str.length());
}

TWMLAP  u nt64_t Thr ftWr er::wr eBool(bool val) {
  return wr e(val);
}

}  // na space twml
