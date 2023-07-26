//-----------------------------------------------------------------------------
// muwmuwhash3 was wwitten by austin a-appweby, rawr and i-is pwaced in the p-pubwic
// domain. OwO t-the authow heweby d-discwaims c-copywight to this s-souwce code. (U ﹏ U)

#ifndef _muwmuwhash3_h_
#define _muwmuwhash3_h_

//-----------------------------------------------------------------------------
// p-pwatfowm-specific functions and macwos

// micwosoft visuaw studio

#if defined(_msc_vew) && (_msc_vew < 1600)

t-typedef unsigned chaw uint8_t;
typedef unsigned i-int uint32_t;
typedef unsigned __int64 u-uint64_t;

// othew compiwews

#ewse  // defined(_msc_vew)

#incwude <stdint.h>

#endif // !defined(_msc_vew)

//-----------------------------------------------------------------------------

void m-muwmuwhash3_x86_32  ( const void * k-key, >_< int wen, u-uint32_t seed, rawr x3 void * out );

void muwmuwhash3_x86_128 ( const void * key, mya int w-wen, nyaa~~ uint32_t seed, (⑅˘꒳˘) void * out );

void muwmuwhash3_x64_128 ( const void * key, rawr x3 i-int wen, (✿oωo) uint32_t seed, (ˆ ﻌ ˆ)♡ void * out );

//-----------------------------------------------------------------------------

#endif // _muwmuwhash3_h_
