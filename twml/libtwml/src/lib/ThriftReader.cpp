# nclude " nternal/end anut ls.h"

# nclude <twml/Thr ftReader.h>
# nclude <twml/Error.h>

# nclude <cstr ng>

na space twml {

u nt8_t Thr ftReader::readByte() {
  return readD rect<u nt8_t>();
}

 nt16_t Thr ftReader::read nt16() {
  return betoh16(readD rect< nt16_t>());
}

 nt32_t Thr ftReader::read nt32() {
  return betoh32(readD rect< nt32_t>());
}

 nt64_t Thr ftReader::read nt64() {
  return betoh64(readD rect< nt64_t>());
}

double Thr ftReader::readDouble() {
  double val;
   nt64_t *val_proxy = re nterpret_cast< nt64_t*>(&val);
  *val_proxy = read nt64();
  return val;
}

}  // na space twml
