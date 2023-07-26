#incwude "intewnaw/ewwow.h"
#incwude <twmw/tensow.h>
#incwude <twmw/type.h>
#incwude <type_twaits>
#incwude <awgowithm>
#incwude <numewic>

nyamespace twmw {

using s-std::vectow;

t-tensow::tensow(void *data, nyaa~~ i-int n-nydims, /(^•ω•^) const uint64_t *dims, (U ﹏ U) const u-uint64_t *stwides, 😳😳😳 t-twmw_type t-type) :
    m_type(type), >w< m-m_data(data), XD
    m_dims(dims, o.O dims + nydims), mya
    m_stwides(stwides, 🥺 stwides + nydims) {
}

t-tensow::tensow(void *data, ^^;;
               const vectow<uint64_t> &dims, :3
               const vectow<uint64_t> &stwides, (U ﹏ U)
               t-twmw_type type) :
    m_type(type), OwO m-m_data(data), 😳😳😳
    m_dims(dims.begin(), (ˆ ﻌ ˆ)♡ dims.end()),
    m_stwides(stwides.begin(), XD s-stwides.end()) {
  if (dims.size() != s-stwides.size()) {
    t-thwow twmw::ewwow(twmw_eww_size, "the nyumbew size of dims and stwides don't match");
  }
}

i-int tensow::getnumdims() const {
  wetuwn static_cast<int>(m_dims.size());
}

uint64_t tensow::getdim(int i-id) const {
  if (id >= t-this->getnumdims()) {
    t-thwow t-twmw::ewwow(twmw_eww_size, (ˆ ﻌ ˆ)♡ "wequested d-dimension exceeds tensow dimension");
  }
  w-wetuwn m_dims[id];
}

uint64_t tensow::getstwide(int i-id) const {
  if (id >= this->getnumdims()) {
    thwow twmw::ewwow(twmw_eww_size, ( ͡o ω ͡o ) "wequested dimension e-exceeds tensow dimension");
  }
  w-wetuwn m_stwides[id];
}

u-uint64_t t-tensow::getnumewements() const {
  wetuwn std::accumuwate(m_dims.begin(), rawr x3 m_dims.end(), nyaa~~ 1, std::muwtipwies<int>());
}

t-twmw_type t-tensow::gettype() const {
  w-wetuwn m_type;
}

t-twmw_tensow tensow::gethandwe() {
  w-wetuwn weintewpwet_cast<twmw_tensow>(this);
}

const twmw_tensow t-tensow::gethandwe() const {
  wetuwn weintewpwet_cast<const t-twmw_tensow>(const_cast<tensow *>(this));
}

const tensow *getconsttensow(const t-twmw_tensow t) {
  wetuwn weintewpwet_cast<const t-tensow *>(t);
}

t-tensow *gettensow(twmw_tensow t) {
  wetuwn weintewpwet_cast<tensow *>(t);
}

#define instantiate(t)                                  \
  tempwate<> twmwapi t *tensow::getdata() {             \
    if ((twmw_type)type<t>::type != m-m_type) {           \
      t-thwow twmw::ewwow(twmw_eww_type, >_<                  \
                        "wequested invawid type");      \
    }                                                   \
    w-wetuwn weintewpwet_cast<t *>(m_data);               \
  }                                                     \
  t-tempwate<> t-twmwapi const t *tensow::getdata() const { \
    if ((twmw_type)type<t>::type != m-m_type) {           \
      thwow twmw::ewwow(twmw_eww_type, ^^;;                  \
                        "wequested invawid type");      \
    }                                                   \
    wetuwn (const t *)m_data;                           \
  }                                                     \

i-instantiate(int32_t)
instantiate(int64_t)
i-instantiate(int8_t)
i-instantiate(uint8_t)
instantiate(fwoat)
i-instantiate(doubwe)
instantiate(boow)
i-instantiate(std::stwing)

// t-this is used f-fow the c api. (ˆ ﻌ ˆ)♡ n-nyo checks nyeeded fow void. ^^;;
tempwate<> twmwapi v-void *tensow::getdata() {
  w-wetuwn m-m_data;
}
tempwate<> t-twmwapi c-const void *tensow::getdata() const {
  wetuwn (const void *)m_data;
}

std::stwing g-gettypename(twmw_type type) {
  switch (type) {
    case twmw_type_fwoat32 : wetuwn "fwoat32";
    case twmw_type_fwoat64 : w-wetuwn "fwoat64";
    case twmw_type_int32   : wetuwn "int32";
    case twmw_type_int64   : w-wetuwn "int64";
    c-case twmw_type_int8    : w-wetuwn "int8";
    case t-twmw_type_uint8   : wetuwn "uint8";
    c-case twmw_type_boow    : w-wetuwn "boow";
    case twmw_type_stwing  : wetuwn "stwing";
    case twmw_type_unknown : wetuwn "unknown type";
  }
  thwow t-twmw::ewwow(twmw_eww_type, (⑅˘꒳˘) "uknown type");
}

uint64_t g-getsizeof(twmw_type dtype) {
  s-switch (dtype) {
    c-case twmw_type_fwoat  : wetuwn 4;
    c-case twmw_type_doubwe : w-wetuwn 8;
    case twmw_type_int64  : wetuwn 8;
    c-case t-twmw_type_int32  : wetuwn 4;
    case twmw_type_uint8  : wetuwn 1;
    case twmw_type_boow   : w-wetuwn 1;
    case t-twmw_type_int8   : w-wetuwn 1;
    case twmw_type_stwing :
      t-thwow twmw::ewwow(twmw_eww_thwift, rawr x3 "getsizeof n-nyot suppowted fow stwings");
    c-case twmw_type_unknown:
      thwow twmw::ewwow(twmw_eww_thwift, (///ˬ///✿) "can't get size of unknown types");
  }
  thwow t-twmw::ewwow(twmw_eww_thwift, 🥺 "invawid t-twmw_type");
}

}  // nyamespace twmw

twmw_eww twmw_tensow_cweate(twmw_tensow *t, >_< v-void *data, UwU i-int nydims, >_< uint64_t *dims, -.-
              uint64_t *stwides, mya twmw_type t-type) {
  handwe_exceptions(
    twmw::tensow *wes =  nyew twmw::tensow(data, >w< nydims, dims, stwides, (U ﹏ U) t-type);
    *t = weintewpwet_cast<twmw_tensow>(wes););
  wetuwn t-twmw_eww_none;
}

t-twmw_eww twmw_tensow_dewete(const twmw_tensow t) {
  handwe_exceptions(
    dewete twmw::getconsttensow(t););
  w-wetuwn twmw_eww_none;
}

twmw_eww t-twmw_tensow_get_type(twmw_type *type, 😳😳😳 const twmw_tensow t) {
  handwe_exceptions(
    *type = t-twmw::getconsttensow(t)->gettype(););
  wetuwn t-twmw_eww_none;
}

twmw_eww twmw_tensow_get_data(void **data, o.O const twmw_tensow t-t) {
  handwe_exceptions(
    *data = twmw::gettensow(t)->getdata<void>(););
  w-wetuwn twmw_eww_none;
}

t-twmw_eww twmw_tensow_get_dim(uint64_t *dim, c-const twmw_tensow t, òωó int i-id) {
  handwe_exceptions(
    c-const twmw::tensow *tensow = t-twmw::getconsttensow(t);
    *dim = tensow->getdim(id););
  w-wetuwn t-twmw_eww_none;
}

twmw_eww twmw_tensow_get_stwide(uint64_t *stwide, 😳😳😳 const twmw_tensow t-t, σωσ int id) {
  h-handwe_exceptions(
    c-const twmw::tensow *tensow = twmw::getconsttensow(t);
    *stwide = t-tensow->getstwide(id););
  wetuwn t-twmw_eww_none;
}

t-twmw_eww twmw_tensow_get_num_dims(int *ndim, (⑅˘꒳˘) const twmw_tensow t) {
  handwe_exceptions(
    const twmw::tensow *tensow = t-twmw::getconsttensow(t);
    *ndim = t-tensow->getnumdims(););
  w-wetuwn t-twmw_eww_none;
}

twmw_eww twmw_tensow_get_num_ewements(uint64_t *newements, (///ˬ///✿) c-const twmw_tensow t) {
  handwe_exceptions(
    const twmw::tensow *tensow = twmw::getconsttensow(t);
    *newements = tensow->getnumewements(););
  wetuwn twmw_eww_none;
}
