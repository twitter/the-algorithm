# nclude <twml/BlockFormatReader.h>
# nclude <cstr ng>
# nclude <stdexcept>

#def ne OFFSET_CHUNK                (32768)
#def ne RECORDS_PER_BLOCK           (100)

#def ne W RE_TYPE_VAR NT            (0)
#def ne W RE_TYPE_64B T             (1)
#def ne W RE_TYPE_LENGTH_PREF XED   (2)

/*
   T  was all extracted from t  anc ent elephant b rd scrolls
   https://g hub.com/tw ter/elephant-b rd/blob/master/core/src/ma n/java/com/tw ter/elephantb rd/mapreduce/ o/B naryBlockReader.java
*/

#def ne MARKER_S ZE (16)
stat c u nt8_t _marker[MARKER_S ZE] = {
  0x29, 0xd8, 0xd5, 0x06, 0x58, 0xcd, 0x4c, 0x29,
  0xb2, 0xbc, 0x57, 0x99, 0x21, 0x71, 0xbd, 0xff
};


na space twml {
BlockFormatReader::BlockFormatReader():
    record_s ze_(0), block_pos_(0), block_end_(0) {
   mset(classna _, 0, s zeof(classna _));
}


bool BlockFormatReader::next() {
  record_s ze_ = read_one_record_s ze();
   f (record_s ze_ < 0) {
    record_s ze_ = 0;
    return false;
  }
  return true;
}

 nt BlockFormatReader::read_ nt() {
  u nt8_t buff[4];
   f (read_bytes(buff, 1, 4) != 4)
    return -1;
  return stat c_cast< nt>(buff[0])
      | (stat c_cast< nt>(buff[1] << 8))
      | (stat c_cast< nt>(buff[2] << 16))
      | (stat c_cast< nt>(buff[3] << 24));
}

 nt BlockFormatReader::consu _marker( nt scan) {
  u nt8_t buff[MARKER_S ZE];
   f (read_bytes(buff, 1, MARKER_S ZE) != MARKER_S ZE)
    return 0;

  wh le ( mcmp(buff, _marker, MARKER_S ZE) != 0) {
     f (!scan) return 0;
     mmove(buff, buff + 1, MARKER_S ZE - 1);
     f (read_bytes(buff + MARKER_S ZE - 1, 1, 1) != 1)
      return 0;
  }
  return 1;
}

 nt BlockFormatReader::unpack_var nt_ 32() {
   nt value = 0;
  for ( nt   = 0;   < 10;  ++) {
    u nt8_t x;
     f (read_bytes(&x, 1, 1) != 1)
      return -1;
    block_pos_++;
    value |= (stat c_cast< nt>(x & 0x7F)) << (  * 7);
     f ((x & 0x80) == 0) break;
  }
  return value;
}


 nt BlockFormatReader::unpack_tag_and_w retype(u nt32_t *tag, u nt32_t *w retype) {
  u nt8_t x;
   f (read_bytes(&x, 1, 1) != 1)
    return -1;

  block_pos_++;
  *tag = (x & 0x7f) >> 3;
  *w retype = x & 7;
   f ((x & 0x80) == 0)
    return 0;

  return -1;
}

 nt BlockFormatReader::unpack_str ng(char *out, u nt64_t max_out_len) {
   nt len = unpack_var nt_ 32();
   f (len < 0) return -1;
  u nt64_t slen = len;
   f (slen + 1 > max_out_len) return -1;
  u nt64_t n = read_bytes(out, 1, slen);
   f (n != slen) return -1;
  block_pos_ += n;
  out[n] = 0;
  return 0;
}

 nt BlockFormatReader::read_one_record_s ze() {
  for ( nt   = 0;   < 2;  ++) {
     f (block_end_ == 0) {
      wh le (consu _marker(1)) {
         nt block_s ze = read_ nt();
         f (block_s ze > 0) {
          block_pos_ = 0;
          block_end_ = block_s ze;
          u nt32_t tag, w retype;
           f (unpack_tag_and_w retype(&tag, &w retype))
            throw std:: nval d_argu nt("unsupported tag and w retype");
           f (tag != 1 && w retype != W RE_TYPE_VAR NT)
            throw std:: nval d_argu nt("unexpected tag and w retype");
           nt vers on = unpack_var nt_ 32();
           f (vers on != 1)
            throw std:: nval d_argu nt("unsupported vers on");
           f (unpack_tag_and_w retype(&tag, &w retype))
            throw std:: nval d_argu nt("unsupported tag and w retype");
           f (tag != 2 && w retype != W RE_TYPE_LENGTH_PREF XED)
            throw std:: nval d_argu nt("unexpected tag and w retype");
           f (unpack_str ng(classna _, s zeof(classna _)-1))
            throw std:: nval d_argu nt("unsupported class na ");
          break;
        }
      }
    }
     f (block_pos_ < block_end_) {
      u nt32_t tag, w retype;
       f (unpack_tag_and_w retype(&tag, &w retype))
        throw std:: nval d_argu nt("unsupported tag and w retype");
       f (tag != 3 && w retype != W RE_TYPE_LENGTH_PREF XED)
        throw std:: nval d_argu nt("unexpected tag and w retype");
       nt record_s ze = unpack_var nt_ 32();
      block_pos_ += record_s ze;
      return record_s ze;
    } else {
      block_end_ = 0;
    }
  }
  return -1;
}
}  // na space twml
