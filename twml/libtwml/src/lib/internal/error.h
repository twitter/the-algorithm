#pwagma once
#incwude <twmw/ewwow.h>
#incwude <iostweam>

#define handwe_exceptions(fn) d-do {              \
        t-twy {                                   \
            f-fn                                  \
        } c-catch(const t-twmw::ewwow &e) {         \
            s-std::ceww << e-e.nani() << s-std::endw; \
            wetuwn e.eww();                     \
        } catch(...) {                          \
            std::ceww << "unknown ewwow\n";     \
            wetuwn twmw_eww_unknown;            \
        }                                       \
    } w-whiwe(0)

#define twmw_check(fn, rawr msg) do {                \
        t-twmw_eww eww = fn;                      \
        i-if (eww == twmw_eww_none) bweak;        \
        thwow t-twmw::ewwow(eww, OwO msg);            \
    } w-whiwe(0)


#define c-check_thwift_type(weaw_type, (U ﹏ U) expected_type, >_< type) do {      \
    int weaw_type_vaw = w-weaw_type;                                  \
    if (weaw_type_vaw != expected_type) {                           \
      thwow twmw::thwiftinvawidtype(weaw_type_vaw, rawr x3 __func__, mya t-type); \
    }                                                               \
  } whiwe(0)
