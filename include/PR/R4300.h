/**************************************************************************
 *									  *
 *		 Copyright (C) 1995, Silicon Graphics, Inc.		  *
 *									  *
 *  These coded instructions, statements, and computer programs  contain  *
 *  unpublished  proprietary  information of Silicon Graphics, Inc., and  *
 *  are protected by Federal copyright law.  They  may  not be disclosed  *
 *  to  third  parties  or copied or duplicated in any form, in whole or  *
 *  in part, without the prior written consent of Silicon Graphics, Inc.  *
 *									  *
 **************************************************************************/

/**************************************************************************
 *
 *  $Revision: 1.13 $
 *  $Date: 1997/02/11 08:15:34 $
 *  $Source: /disk6/Master/cvsmdev2/PR/include/R4300.h,v $
 *
 **************************************************************************/

#ifndef __R4300_H__
#define __R4300_H__

#include <PR/ultratypes.h>

/*
 * Segment base addresses and sizes
 */
#define	KUBASE		0
#define	KUSIZE		0x80000000
#define	K0BASE		0x80000000
#define	K0SIZE		0x20000000
#define	K1BASE		0xA0000000
#define	K1SIZE		0x20000000
#define	K2BASE		0xC0000000
#define	K2SIZE		0x20000000

/*
 * Exception vectors
 */
#define SIZE_EXCVEC	0x80			/* Size of an exc. vec */
#define	UT_VEC		K0BASE			/* utlbmiss vector */
#define	R_VEC		(K1BASE+0x1fc00000)	/* reset vector */
#define	XUT_VEC		(K0BASE+0x80)		/* extended address tlbmiss */
#define	ECC_VEC		(K0BASE+0x100)		/* Ecc exception vector */
#define	E_VEC		(K0BASE+0x180)		/* Gen. exception vector */

/*
 * Address conversion macros
 */
#ifdef _LANGUAGE_ASSEMBLY

#define	K0_TO_K1(x)	((x)|0xA0000000)	/* kseg0 to kseg1 */
#define	K1_TO_K0(x)	((x)&0x9FFFFFFF)	/* kseg1 to kseg0 */
#define	K0_TO_PHYS(x)	((x)&0x1FFFFFFF)	/* kseg0 to physical */
#define	K1_TO_PHYS(x)	((x)&0x1FFFFFFF)	/* kseg1 to physical */
#define	KDM_TO_PHYS(x)	((x)&0x1FFFFFFF)	/* direct mapped to physical */
#define	PHYS_TO_K0(x)	((x)|0x80000000)	/* physical to kseg0 */
#define	PHYS_TO_K1(x)	((x)|0xA0000000)	/* physical to kseg1 */

#else /* _LANGUAGE_C */

#define	K0_TO_K1(x)	((u32)(x)|0xA0000000)	/* kseg0 to kseg1 */
#define	K1_TO_K0(x)	((u32)(x)&0x9FFFFFFF)	/* kseg1 to kseg0 */
#define	K0_TO_PHYS(x)	((u32)(x)&0x1FFFFFFF)	/* kseg0 to physical */
#define	K1_TO_PHYS(x)	((u32)(x)&0x1FFFFFFF)	/* kseg1 to physical */
#define	KDM_TO_PHYS(x)	((u32)(x)&0x1FFFFFFF)	/* direct mapped to physical */
#define	PHYS_TO_K0(x)	((u32)(x)|0x80000000)	/* physical to kseg0 */
#define	PHYS_TO_K1(x)	((u32)(x)|0xA0000000)	/* physical to kseg1 */

#endif	/* _LANGUAGE_ASSEMBLY */

/*
 * Address predicates
 */
#define	IS_KSEG0(x)	((u32)(x) >= K0BASE && (u32)(x) < K1BASE)
#define	IS_KSEG1(x)	((u32)(x) >= K1BASE && (u32)(x) < K2BASE)
#define	IS_KSEGDM(x)	((u32)(x) >= K0BASE && (u32)(x) < K2BASE)
#define	IS_KSEG2(x)	((u32)(x) >= K2BASE && (u32)(x) < KPTE_SHDUBASE)
#define	IS_KPTESEG(x)	((u32)(x) >= KPTE_SHDUBASE)
#define	IS_KUSEG(x)	((u32)(x) < K0BASE)

/*
 * TLB size constants
 */

#define	NTLBENTRIES	31	/* entry 31 is reserved by rdb */

#define	TLBHI_VPN2MASK		0xffffe000
#define	TLBHI_VPN2SHIFT		13
#define	TLBHI_PIDMASK		0xff
#define	TLBHI_PIDSHIFT		0
#define	TLBHI_NPID		255		/* 255 to fit in 8 bits */

#define	TLBLO_PFNMASK		0x3fffffc0
#define	TLBLO_PFNSHIFT		6
#define	TLBLO_CACHMASK		0x38		/* cache coherency algorithm */
#define TLBLO_CACHSHIFT		3
#define TLBLO_UNCACHED		0x10		/* not cached */
#define TLBLO_NONCOHRNT		0x18		/* Cacheable non-coherent */
#define TLBLO_EXLWR		0x28		/* Exclusive write */
#define	TLBLO_D			0x4		/* writeable */
#define	TLBLO_V			0x2		/* valid bit */
#define	TLBLO_G			0x1		/* global access bit */

#define	TLBINX_PROBE		0x80000000
#define	TLBINX_INXMASK		0x3f
#define	TLBINX_INXSHIFT		0

#define	TLBRAND_RANDMASK	0x3f
#define	TLBRAND_RANDSHIFT	0

#define	TLBWIRED_WIREDMASK	0x3f

#define	TLBCTXT_BASEMASK	0xff800000
#define	TLBCTXT_BASESHIFT	23
#define TLBCTXT_BASEBITS	9

#define	TLBCTXT_VPNMASK		0x7ffff0
#define	TLBCTXT_VPNSHIFT	4

#define TLBPGMASK_4K		0x0
#define TLBPGMASK_16K		0x6000
#define TLBPGMASK_64K		0x1e000

/*
 * Status register
 */
#define	SR_CUMASK	0xf0000000	/* coproc usable bits */

#define	SR_CU3		0x80000000	/* Coprocessor 3 usable */
#define	SR_CU2		0x40000000	/* Coprocessor 2 usable */
#define	SR_CU1		0x20000000	/* Coprocessor 1 usable */
#define	SR_CU0		0x10000000	/* Coprocessor 0 usable */
#define	SR_RP		0x08000000	/* Reduced power (quarter speed) */
#define	SR_FR		0x04000000	/* MIPS III FP register mode */
#define	SR_RE		0x02000000	/* Reverse endian */
#define	SR_ITS		0x01000000	/* Instruction trace support */
#define	SR_BEV		0x00400000	/* Use boot exception vectors */
#define	SR_TS		0x00200000	/* TLB shutdown */
#define	SR_SR		0x00100000	/* Soft reset occured */
#define	SR_CH		0x00040000	/* Cache hit for last 'cache' op */
#define	SR_CE		0x00020000	/* Create ECC */
#define	SR_DE		0x00010000	/* ECC of parity does not cause error */

/*
 * Interrupt enable bits
 * (NOTE: bits set to 1 enable the corresponding level interrupt)
 */
#define	SR_IMASK	0x0000ff00	/* Interrupt mask */
#define	SR_IMASK8	0x00000000	/* mask level 8 */
#define	SR_IMASK7	0x00008000	/* mask level 7 */
#define	SR_IMASK6	0x0000c000	/* mask level 6 */
#define	SR_IMASK5	0x0000e000	/* mask level 5 */
#define	SR_IMASK4	0x0000f000	/* mask level 4 */
#define	SR_IMASK3	0x0000f800	/* mask level 3 */
#define	SR_IMASK2	0x0000fc00	/* mask level 2 */
#define	SR_IMASK1	0x0000fe00	/* mask level 1 */
#define	SR_IMASK0	0x0000ff00	/* mask level 0 */

#define	SR_IBIT8	0x00008000	/* bit level 8 */
#define	SR_IBIT7	0x00004000	/* bit level 7 */
#define	SR_IBIT6	0x00002000	/* bit level 6 */
#define	SR_IBIT5	0x00001000	/* bit level 5 */
#define	SR_IBIT4	0x00000800	/* bit level 4 */
#define	SR_IBIT3	0x00000400	/* bit level 3 */
#define	SR_IBIT2	0x00000200	/* bit level 2 */
#define	SR_IBIT1	0x00000100	/* bit level 1 */

#define	SR_IMASKSHIFT	8

#define	SR_KX		0x00000080	/* extended-addr TLB vec in kernel */
#define	SR_SX		0x00000040	/* xtended-addr TLB vec supervisor */
#define	SR_UX		0x00000020	/* xtended-addr TLB vec in user mode */
#define	SR_KSU_MASK	0x00000018	/* mode mask */
#define	SR_KSU_USR	0x00000010	/* user mode */
#define	SR_KSU_SUP	0x00000008	/* supervisor mode */
#define	SR_KSU_KER	0x00000000	/* kernel mode */
#define	SR_ERL		0x00000004	/* Error level, 1=>cache error */
#define	SR_EXL		0x00000002	/* Exception level, 1=>exception */
#define	SR_IE		0x00000001	/* interrupt enable, 1=>enable */

/*
 * Cause Register
 */
#define	CAUSE_BD	0x80000000	/* Branch delay slot */
#define	CAUSE_CEMASK	0x30000000	/* coprocessor error */
#define	CAUSE_CESHIFT	28

/* Interrupt pending bits */
#define	CAUSE_IP8	0x00008000	/* External level 8 pending - COMPARE */
#define	CAUSE_IP7	0x00004000	/* External level 7 pending - INT4 */
#define	CAUSE_IP6	0x00002000	/* External level 6 pending - INT3 */
#define	CAUSE_IP5	0x00001000	/* External level 5 pending - INT2 */
#define	CAUSE_IP4	0x00000800	/* External level 4 pending - INT1 */
#define	CAUSE_IP3	0x00000400	/* External level 3 pending - INT0 */
#define	CAUSE_SW2	0x00000200	/* Software level 2 pending */
#define	CAUSE_SW1	0x00000100	/* Software level 1 pending */

#define	CAUSE_IPMASK	0x0000FF00	/* Pending interrupt mask */
#define	CAUSE_IPSHIFT	8

#define	CAUSE_EXCMASK	0x0000007C	/* Cause code bits */

#define	CAUSE_EXCSHIFT	2

/* Cause register exception codes */

#define	EXC_CODE(x)	((x)<<2)

/* Hardware exception codes */
#define	EXC_INT		EXC_CODE(0)	/* interrupt */
#define	EXC_MOD		EXC_CODE(1)	/* TLB mod */
#define	EXC_RMISS	EXC_CODE(2)	/* Read TLB Miss */
#define	EXC_WMISS	EXC_CODE(3)	/* Write TLB Miss */
#define	EXC_RADE	EXC_CODE(4)	/* Read Address Error */
#define	EXC_WADE	EXC_CODE(5)	/* Write Address Error */
#define	EXC_IBE		EXC_CODE(6)	/* Instruction Bus Error */
#define	EXC_DBE		EXC_CODE(7)	/* Data Bus Error */
#define	EXC_SYSCALL	EXC_CODE(8)	/* SYSCALL */
#define	EXC_BREAK	EXC_CODE(9)	/* BREAKpoint */
#define	EXC_II		EXC_CODE(10)	/* Illegal Instruction */
#define	EXC_CPU		EXC_CODE(11)	/* CoProcessor Unusable */
#define	EXC_OV		EXC_CODE(12)	/* OVerflow */
#define	EXC_TRAP	EXC_CODE(13)	/* Trap exception */
#define	EXC_VCEI	EXC_CODE(14)	/* Virt. Coherency on Inst. fetch */
#define	EXC_FPE		EXC_CODE(15)	/* Floating Point Exception */
#define	EXC_WATCH	EXC_CODE(23)	/* Watchpoint reference */
#define	EXC_VCED	EXC_CODE(31)	/* Virt. Coherency on data read */

/* C0_PRID Defines */
#define	C0_IMPMASK	0xff00
#define	C0_IMPSHIFT	8
#define C0_REVMASK	0xff
#define C0_MAJREVMASK	0xf0
#define	C0_MAJREVSHIFT	4
#define C0_MINREVMASK	0xf

/*
 * Coprocessor 0 operations
 */
#define	C0_READI  0x1		/* read ITLB entry addressed by C0_INDEX */
#define	C0_WRITEI 0x2		/* write ITLB entry addressed by C0_INDEX */
#define	C0_WRITER 0x6		/* write ITLB entry addressed by C0_RAND */
#define	C0_PROBE  0x8		/* probe for ITLB entry addressed by TLBHI */
#define	C0_RFE	  0x10		/* restore for exception */

/*
 * 'cache' instruction definitions
 */

/* Target cache */
#define	CACH_PI		0x0	/* specifies primary inst. cache */
#define	CACH_PD		0x1	/* primary data cache */
#define	CACH_SI		0x2	/* secondary instruction cache */
#define	CACH_SD		0x3	/* secondary data cache */

/* Cache operations */
#define	C_IINV		0x0	/* index invalidate (inst, 2nd inst) */
#define	C_IWBINV	0x0	/* index writeback inval (d, sd) */
#define	C_ILT		0x4	/* index load tag (all) */
#define	C_IST		0x8	/* index store tag (all) */
#define	C_CDX		0xc	/* create dirty exclusive (d, sd) */
#define	C_HINV		0x10	/* hit invalidate (all) */
#define	C_HWBINV	0x14	/* hit writeback inv. (d, sd) */
#define	C_FILL		0x14	/* fill (i) */
#define	C_HWB		0x18	/* hit writeback (i, d, sd) */
#define	C_HSV		0x1c	/* hit set virt. (si, sd) */

/*
 * Cache size definitions
 */
#define	ICACHE_SIZE		0x4000			/* 16K */
#define	ICACHE_LINESIZE		32			/* 8 words */
#define	ICACHE_LINEMASK		(ICACHE_LINESIZE-1)

#define	DCACHE_SIZE		0x2000			/* 8K */
#define	DCACHE_LINESIZE		16			/* 4 words */
#define	DCACHE_LINEMASK		(DCACHE_LINESIZE-1)

/*
 * C0_CONFIG register definitions
 */
#define	CONFIG_CM	0x80000000	/* 1 == Master-Checker enabled */
#define	CONFIG_EC	0x70000000	/* System Clock ratio */
#define	CONFIG_EC_1_1	0x6		/* System Clock ratio 1 :1 */
#define	CONFIG_EC_3_2	0x7		/* System Clock ratio 1.5 :1 */
#define	CONFIG_EC_2_1	0x0		/* System Clock ratio 2 :1 */
#define	CONFIG_EC_3_1	0x1		/* System Clock ratio 3 :1 */
#define	CONFIG_EP	0x0f000000	/* Transmit Data Pattern */
#define	CONFIG_SB	0x00c00000	/* Secondary cache block size */

#define	CONFIG_SS	0x00200000	/* Split scache: 0 == I&D combined */
#define	CONFIG_SW	0x00100000	/* scache port: 0==128, 1==64 */
#define	CONFIG_EW	0x000c0000	/* System Port width: 0==64, 1==32 */
#define	CONFIG_SC	0x00020000	/* 0 -> 2nd cache present */
#define	CONFIG_SM	0x00010000	/* 0 -> Dirty Shared Coherency enabled*/
#define	CONFIG_BE	0x00008000	/* Endian-ness: 1 --> BE */
#define	CONFIG_EM	0x00004000	/* 1 -> ECC mode, 0 -> parity */
#define	CONFIG_EB	0x00002000	/* Block order:1->sequent,0->subblock */

#define	CONFIG_IC	0x00000e00	/* Primary Icache size */
#define	CONFIG_DC	0x000001c0	/* Primary Dcache size */
#define	CONFIG_IB	0x00000020	/* Icache block size */
#define	CONFIG_DB	0x00000010	/* Dcache block size */
#define	CONFIG_CU	0x00000008	/* Update on Store-conditional */
#define	CONFIG_K0	0x00000007	/* K0SEG Coherency algorithm */

#define	CONFIG_UNCACHED		0x00000002	/* K0 is uncached */
#define	CONFIG_NONCOHRNT	0x00000003
#define	CONFIG_COHRNT_EXLWR	0x00000005
#define	CONFIG_SB_SHFT	22		/* shift SB to bit position 0 */
#define	CONFIG_IC_SHFT	9		/* shift IC to bit position 0 */
#define	CONFIG_DC_SHFT	6		/* shift DC to bit position 0 */
#define	CONFIG_BE_SHFT	15		/* shift BE to bit position 0 */

/*
 * C0_TAGLO definitions for setting/getting cache states and physaddr bits
 */
#define SADDRMASK  	0xFFFFE000	/* 31..13 -> scache paddr bits 35..17 */
#define SVINDEXMASK	0x00000380	/* 9..7: prim virt index bits 14..12 */
#define SSTATEMASK	0x00001c00	/* bits 12..10 hold scache line state */
#define SINVALID	0x00000000	/* invalid --> 000 == state 0 */
#define SCLEANEXCL	0x00001000	/* clean exclusive --> 100 == state 4 */
#define SDIRTYEXCL	0x00001400	/* dirty exclusive --> 101 == state 5 */
#define SECC_MASK	0x0000007f	/* low 7 bits are ecc for the tag */
#define SADDR_SHIFT	4		/* shift STagLo (31..13) to 35..17 */

#define PADDRMASK	0xFFFFFF00	/* PTagLo31..8->prim paddr bits35..12 */
#define PADDR_SHIFT	4		/* roll bits 35..12 down to 31..8 */
#define PSTATEMASK	0x00C0		/* bits 7..6 hold primary line state */
#define PINVALID	0x0000		/* invalid --> 000 == state 0 */
#define PCLEANEXCL	0x0080		/* clean exclusive --> 10 == state 2 */
#define PDIRTYEXCL	0x00C0		/* dirty exclusive --> 11 == state 3 */
#define PPARITY_MASK	0x0001		/* low bit is parity bit (even). */

/*
 * C0_CACHE_ERR definitions.
 */
#define	CACHERR_ER		0x80000000	/* 0: inst ref, 1: data ref */
#define	CACHERR_EC		0x40000000	/* 0: primary, 1: secondary */
#define	CACHERR_ED		0x20000000	/* 1: data error */
#define	CACHERR_ET		0x10000000	/* 1: tag error */
#define	CACHERR_ES		0x08000000	/* 1: external ref, e.g. snoop*/
#define	CACHERR_EE		0x04000000	/* error on SysAD bus */
#define	CACHERR_EB		0x02000000	/* complicated, see spec. */
#define	CACHERR_EI		0x01000000	/* complicated, see spec. */
#define	CACHERR_SIDX_MASK	0x003ffff8	/* secondary cache index */
#define	CACHERR_PIDX_MASK	0x00000007	/* primary cache index */
#define CACHERR_PIDX_SHIFT	12		/* bits 2..0 are paddr14..12 */

/* R4000 family supports hardware watchpoints:
 *   C0_WATCHLO:
 *     bits 31..3 are bits 31..3 of physaddr to watch
 *     bit 2:  reserved; must be written as 0.
 *     bit 1:  when set causes a watchpoint trap on load accesses to paddr.
 *     bit 0:  when set traps on stores to paddr;
 *   C0_WATCHHI
 *     bits 31..4 are reserved and must be written as zeros.
 *     bits 3..0 are bits 35..32 of the physaddr to watch
 */
#define WATCHLO_WTRAP           0x00000001
#define WATCHLO_RTRAP           0x00000002
#define WATCHLO_ADDRMASK        0xfffffff8
#define WATCHLO_VALIDMASK       0xfffffffb
#define WATCHHI_VALIDMASK       0x0000000f

/*
 * Coprocessor 0 registers
 */
#ifdef _LANGUAGE_ASSEMBLY
#define	C0_INX		$0
#define	C0_RAND		$1
#define	C0_ENTRYLO0	$2
#define	C0_ENTRYLO1	$3
#define	C0_CONTEXT	$4
#define	C0_PAGEMASK	$5		/* page mask */
#define	C0_WIRED	$6		/* # wired entries in tlb */
#define	C0_BADVADDR	$8
#define	C0_COUNT	$9		/* free-running counter */
#define	C0_ENTRYHI	$10
#define	C0_SR		$12
#define	C0_CAUSE	$13
#define	C0_EPC		$14
#define	C0_PRID		$15		/* revision identifier */
#define	C0_COMPARE	$11		/* counter comparison reg. */
#define	C0_CONFIG	$16		/* hardware configuration */
#define	C0_LLADDR	$17		/* load linked address */
#define	C0_WATCHLO	$18		/* watchpoint */
#define	C0_WATCHHI	$19		/* watchpoint */
#define	C0_ECC		$26		/* S-cache ECC and primary parity */
#define	C0_CACHE_ERR	$27		/* cache error status */
#define	C0_TAGLO	$28		/* cache operations */
#define	C0_TAGHI	$29		/* cache operations */
#define	C0_ERROR_EPC	$30		/* ECC error prg. counter */

# else	/* ! _LANGUAGE_ASSEMBLY */

#define	C0_INX		0
#define	C0_RAND		1
#define	C0_ENTRYLO0	2
#define	C0_ENTRYLO1	3
#define	C0_CONTEXT	4
#define	C0_PAGEMASK	5		/* page mask */
#define	C0_WIRED	6		/* # wired entries in tlb */
#define	C0_BADVADDR	8
#define	C0_COUNT	9		/* free-running counter */
#define	C0_ENTRYHI	10
#define	C0_SR		12
#define	C0_CAUSE	13
#define	C0_EPC		14
#define	C0_PRID		15		/* revision identifier */
#define	C0_COMPARE	11		/* counter comparison reg. */
#define	C0_CONFIG	16		/* hardware configuration */
#define	C0_LLADDR	17		/* load linked address */
#define	C0_WATCHLO	18		/* watchpoint */
#define	C0_WATCHHI	19		/* watchpoint */
#define	C0_ECC		26		/* S-cache ECC and primary parity */
#define	C0_CACHE_ERR	27		/* cache error status */
#define	C0_TAGLO	28		/* cache operations */
#define	C0_TAGHI	29		/* cache operations */
#define	C0_ERROR_EPC	30		/* ECC error prg. counter */

#endif	/* _LANGUAGE_ASSEMBLY */

/*
 * floating-point status register 
 */
#define FPCSR_FS	0x01000000	/* flush denorm to zero */
#define	FPCSR_C		0x00800000	/* condition bit */	
#define	FPCSR_CE	0x00020000	/* cause: unimplemented operation */
#define	FPCSR_CV	0x00010000	/* cause: invalid operation */
#define	FPCSR_CZ	0x00008000	/* cause: division by zero */
#define	FPCSR_CO	0x00004000	/* cause: overflow */
#define	FPCSR_CU	0x00002000	/* cause: underflow */
#define	FPCSR_CI	0x00001000	/* cause: inexact operation */
#define	FPCSR_EV	0x00000800	/* enable: invalid operation */
#define	FPCSR_EZ	0x00000400	/* enable: division by zero */
#define	FPCSR_EO	0x00000200	/* enable: overflow */
#define	FPCSR_EU	0x00000100	/* enable: underflow */
#define	FPCSR_EI	0x00000080	/* enable: inexact operation */
#define	FPCSR_FV	0x00000040	/* flag: invalid operation */
#define	FPCSR_FZ	0x00000020	/* flag: division by zero */
#define	FPCSR_FO	0x00000010	/* flag: overflow */
#define	FPCSR_FU	0x00000008	/* flag: underflow */
#define	FPCSR_FI	0x00000004	/* flag: inexact operation */
#define	FPCSR_RM_MASK	0x00000003	/* rounding mode mask */
#define	FPCSR_RM_RN	0x00000000	/* round to nearest */
#define	FPCSR_RM_RZ	0x00000001	/* round to zero */
#define	FPCSR_RM_RP	0x00000002	/* round to positive infinity */
#define	FPCSR_RM_RM	0x00000003	/* round to negative infinity */

#endif /* __R4300_H */
