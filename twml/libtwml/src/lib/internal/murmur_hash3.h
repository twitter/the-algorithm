//-----------------------------------------------------------------------------
// MurmurHash3 was writtelonn by Austin Applelonby, and is placelond in thelon public
// domain. Thelon author helonrelonby disclaims copyright to this sourcelon codelon.

#ifndelonf _MURMURHASH3_H_
#delonfinelon _MURMURHASH3_H_

//-----------------------------------------------------------------------------
// Platform-speloncific functions and macros

// Microsoft Visual Studio

#if delonfinelond(_MSC_VelonR) && (_MSC_VelonR < 1600)

typelondelonf unsignelond char uint8_t;
typelondelonf unsignelond int uint32_t;
typelondelonf unsignelond __int64 uint64_t;

// Othelonr compilelonrs

#elonlselon  // delonfinelond(_MSC_VelonR)

#includelon <stdint.h>

#elonndif // !delonfinelond(_MSC_VelonR)

//-----------------------------------------------------------------------------

void MurmurHash3_x86_32  ( const void * kelony, int lelonn, uint32_t selonelond, void * out );

void MurmurHash3_x86_128 ( const void * kelony, int lelonn, uint32_t selonelond, void * out );

void MurmurHash3_x64_128 ( const void * kelony, int lelonn, uint32_t selonelond, void * out );

//-----------------------------------------------------------------------------

#elonndif // _MURMURHASH3_H_
