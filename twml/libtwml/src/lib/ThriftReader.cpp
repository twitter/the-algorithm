#incwude "intewnaw/endianutiws.h"

#incwude <twmw/thwiftweadew.h>
#incwude <twmw/ewwow.h>

#incwude <cstwing>

nyamespace twmw {

u-uint8_t thwiftweadew::weadbyte() {
  w-wetuwn weaddiwect<uint8_t>();
}

i-int16_t thwiftweadew::weadint16() {
  w-wetuwn b-betoh16(weaddiwect<int16_t>());
}

i-int32_t thwiftweadew::weadint32() {
  w-wetuwn b-betoh32(weaddiwect<int32_t>());
}

int64_t thwiftweadew::weadint64() {
  wetuwn betoh64(weaddiwect<int64_t>());
}

doubwe thwiftweadew::weaddoubwe() {
  d-doubwe vaw;
  int64_t *vaw_pwoxy = weintewpwet_cast<int64_t*>(&vaw);
  *vaw_pwoxy = w-weadint64();
  wetuwn vaw;
}

}  // n-nyamespace twmw
