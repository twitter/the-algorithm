// libaudiofile b62c902
// https://github.com/mpruett/audiofile
// To simplify compilation, all files have been concatenated into one.
// Support for all formats except WAVE, AIFF(C) and RAW has been stripped out.

/*
                  GNU LESSER GENERAL PUBLIC LICENSE
                       Version 2.1, February 1999

 Copyright (C) 1991, 1999 Free Software Foundation, Inc.
 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 Everyone is permitted to copy and distribute verbatim copies
 of this license document, but changing it is not allowed.

[This is the first released version of the Lesser GPL.  It also counts
 as the successor of the GNU Library Public License, version 2, hence
 the version number 2.1.]

                            Preamble

  The licenses for most software are designed to take away your
freedom to share and change it.  By contrast, the GNU General Public
Licenses are intended to guarantee your freedom to share and change
free software--to make sure the software is free for all its users.

  This license, the Lesser General Public License, applies to some
specially designated software packages--typically libraries--of the
Free Software Foundation and other authors who decide to use it.  You
can use it too, but we suggest you first think carefully about whether
this license or the ordinary General Public License is the better
strategy to use in any particular case, based on the explanations below.

  When we speak of free software, we are referring to freedom of use,
not price.  Our General Public Licenses are designed to make sure that
you have the freedom to distribute copies of free software (and charge
for this service if you wish); that you receive source code or can get
it if you want it; that you can change the software and use pieces of
it in new free programs; and that you are informed that you can do
these things.

  To protect your rights, we need to make restrictions that forbid
distributors to deny you these rights or to ask you to surrender these
rights.  These restrictions translate to certain responsibilities for
you if you distribute copies of the library or if you modify it.

  For example, if you distribute copies of the library, whether gratis
or for a fee, you must give the recipients all the rights that we gave
you.  You must make sure that they, too, receive or can get the source
code.  If you link other code with the library, you must provide
complete object files to the recipients, so that they can relink them
with the library after making changes to the library and recompiling
it.  And you must show them these terms so they know their rights.

  We protect your rights with a two-step method: (1) we copyright the
library, and (2) we offer you this license, which gives you legal
permission to copy, distribute and/or modify the library.

  To protect each distributor, we want to make it very clear that
there is no warranty for the free library.  Also, if the library is
modified by someone else and passed on, the recipients should know
that what they have is not the original version, so that the original
author's reputation will not be affected by problems that might be
introduced by others.

  Finally, software patents pose a constant threat to the existence of
any free program.  We wish to make sure that a company cannot
effectively restrict the users of a free program by obtaining a
restrictive license from a patent holder.  Therefore, we insist that
any patent license obtained for a version of the library must be
consistent with the full freedom of use specified in this license.

  Most GNU software, including some libraries, is covered by the
ordinary GNU General Public License.  This license, the GNU Lesser
General Public License, applies to certain designated libraries, and
is quite different from the ordinary General Public License.  We use
this license for certain libraries in order to permit linking those
libraries into non-free programs.

  When a program is linked with a library, whether statically or using
a shared library, the combination of the two is legally speaking a
combined work, a derivative of the original library.  The ordinary
General Public License therefore permits such linking only if the
entire combination fits its criteria of freedom.  The Lesser General
Public License permits more lax criteria for linking other code with
the library.

  We call this license the "Lesser" General Public License because it
does Less to protect the user's freedom than the ordinary General
Public License.  It also provides other free software developers Less
of an advantage over competing non-free programs.  These disadvantages
are the reason we use the ordinary General Public License for many
libraries.  However, the Lesser license provides advantages in certain
special circumstances.

  For example, on rare occasions, there may be a special need to
encourage the widest possible use of a certain library, so that it becomes
a de-facto standard.  To achieve this, non-free programs must be
allowed to use the library.  A more frequent case is that a free
library does the same job as widely used non-free libraries.  In this
case, there is little to gain by limiting the free library to free
software only, so we use the Lesser General Public License.

  In other cases, permission to use a particular library in non-free
programs enables a greater number of people to use a large body of
free software.  For example, permission to use the GNU C Library in
non-free programs enables many more people to use the whole GNU
operating system, as well as its variant, the GNU/Linux operating
system.

  Although the Lesser General Public License is Less protective of the
users' freedom, it does ensure that the user of a program that is
linked with the Library has the freedom and the wherewithal to run
that program using a modified version of the Library.

  The precise terms and conditions for copying, distribution and
modification follow.  Pay close attention to the difference between a
"work based on the library" and a "work that uses the library".  The
former contains code derived from the library, whereas the latter must
be combined with the library in order to run.

                  GNU LESSER GENERAL PUBLIC LICENSE
   TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION

  0. This License Agreement applies to any software library or other
program which contains a notice placed by the copyright holder or
other authorized party saying it may be distributed under the terms of
this Lesser General Public License (also called "this License").
Each licensee is addressed as "you".

  A "library" means a collection of software functions and/or data
prepared so as to be conveniently linked with application programs
(which use some of those functions and data) to form executables.

  The "Library", below, refers to any such software library or work
which has been distributed under these terms.  A "work based on the
Library" means either the Library or any derivative work under
copyright law: that is to say, a work containing the Library or a
portion of it, either verbatim or with modifications and/or translated
straightforwardly into another language.  (Hereinafter, translation is
included without limitation in the term "modification".)

  "Source code" for a work means the preferred form of the work for
making modifications to it.  For a library, complete source code means
all the source code for all modules it contains, plus any associated
interface definition files, plus the scripts used to control compilation
and installation of the library.

  Activities other than copying, distribution and modification are not
covered by this License; they are outside its scope.  The act of
running a program using the Library is not restricted, and output from
such a program is covered only if its contents constitute a work based
on the Library (independent of the use of the Library in a tool for
writing it).  Whether that is true depends on what the Library does
and what the program that uses the Library does.

  1. You may copy and distribute verbatim copies of the Library's
complete source code as you receive it, in any medium, provided that
you conspicuously and appropriately publish on each copy an
appropriate copyright notice and disclaimer of warranty; keep intact
all the notices that refer to this License and to the absence of any
warranty; and distribute a copy of this License along with the
Library.

  You may charge a fee for the physical act of transferring a copy,
and you may at your option offer warranty protection in exchange for a
fee.

  2. You may modify your copy or copies of the Library or any portion
of it, thus forming a work based on the Library, and copy and
distribute such modifications or work under the terms of Section 1
above, provided that you also meet all of these conditions:

    a) The modified work must itself be a software library.

    b) You must cause the files modified to carry prominent notices
    stating that you changed the files and the date of any change.

    c) You must cause the whole of the work to be licensed at no
    charge to all third parties under the terms of this License.

    d) If a facility in the modified Library refers to a function or a
    table of data to be supplied by an application program that uses
    the facility, other than as an argument passed when the facility
    is invoked, then you must make a good faith effort to ensure that,
    in the event an application does not supply such function or
    table, the facility still operates, and performs whatever part of
    its purpose remains meaningful.

    (For example, a function in a library to compute square roots has
    a purpose that is entirely well-defined independent of the
    application.  Therefore, Subsection 2d requires that any
    application-supplied function or table used by this function must
    be optional: if the application does not supply it, the square
    root function must still compute square roots.)

These requirements apply to the modified work as a whole.  If
identifiable sections of that work are not derived from the Library,
and can be reasonably considered independent and separate works in
themselves, then this License, and its terms, do not apply to those
sections when you distribute them as separate works.  But when you
distribute the same sections as part of a whole which is a work based
on the Library, the distribution of the whole must be on the terms of
this License, whose permissions for other licensees extend to the
entire whole, and thus to each and every part regardless of who wrote
it.

Thus, it is not the intent of this section to claim rights or contest
your rights to work written entirely by you; rather, the intent is to
exercise the right to control the distribution of derivative or
collective works based on the Library.

In addition, mere aggregation of another work not based on the Library
with the Library (or with a work based on the Library) on a volume of
a storage or distribution medium does not bring the other work under
the scope of this License.

  3. You may opt to apply the terms of the ordinary GNU General Public
License instead of this License to a given copy of the Library.  To do
this, you must alter all the notices that refer to this License, so
that they refer to the ordinary GNU General Public License, version 2,
instead of to this License.  (If a newer version than version 2 of the
ordinary GNU General Public License has appeared, then you can specify
that version instead if you wish.)  Do not make any other change in
these notices.

  Once this change is made in a given copy, it is irreversible for
that copy, so the ordinary GNU General Public License applies to all
subsequent copies and derivative works made from that copy.

  This option is useful when you wish to copy part of the code of
the Library into a program that is not a library.

  4. You may copy and distribute the Library (or a portion or
derivative of it, under Section 2) in object code or executable form
under the terms of Sections 1 and 2 above provided that you accompany
it with the complete corresponding machine-readable source code, which
must be distributed under the terms of Sections 1 and 2 above on a
medium customarily used for software interchange.

  If distribution of object code is made by offering access to copy
from a designated place, then offering equivalent access to copy the
source code from the same place satisfies the requirement to
distribute the source code, even though third parties are not
compelled to copy the source along with the object code.

  5. A program that contains no derivative of any portion of the
Library, but is designed to work with the Library by being compiled or
linked with it, is called a "work that uses the Library".  Such a
work, in isolation, is not a derivative work of the Library, and
therefore falls outside the scope of this License.

  However, linking a "work that uses the Library" with the Library
creates an executable that is a derivative of the Library (because it
contains portions of the Library), rather than a "work that uses the
library".  The executable is therefore covered by this License.
Section 6 states terms for distribution of such executables.

  When a "work that uses the Library" uses material from a header file
that is part of the Library, the object code for the work may be a
derivative work of the Library even though the source code is not.
Whether this is true is especially significant if the work can be
linked without the Library, or if the work is itself a library.  The
threshold for this to be true is not precisely defined by law.

  If such an object file uses only numerical parameters, data
structure layouts and accessors, and small macros and small inline
functions (ten lines or less in length), then the use of the object
file is unrestricted, regardless of whether it is legally a derivative
work.  (Executables containing this object code plus portions of the
Library will still fall under Section 6.)

  Otherwise, if the work is a derivative of the Library, you may
distribute the object code for the work under the terms of Section 6.
Any executables containing that work also fall under Section 6,
whether or not they are linked directly with the Library itself.

  6. As an exception to the Sections above, you may also combine or
link a "work that uses the Library" with the Library to produce a
work containing portions of the Library, and distribute that work
under terms of your choice, provided that the terms permit
modification of the work for the customer's own use and reverse
engineering for debugging such modifications.

  You must give prominent notice with each copy of the work that the
Library is used in it and that the Library and its use are covered by
this License.  You must supply a copy of this License.  If the work
during execution displays copyright notices, you must include the
copyright notice for the Library among them, as well as a reference
directing the user to the copy of this License.  Also, you must do one
of these things:

    a) Accompany the work with the complete corresponding
    machine-readable source code for the Library including whatever
    changes were used in the work (which must be distributed under
    Sections 1 and 2 above); and, if the work is an executable linked
    with the Library, with the complete machine-readable "work that
    uses the Library", as object code and/or source code, so that the
    user can modify the Library and then relink to produce a modified
    executable containing the modified Library.  (It is understood
    that the user who changes the contents of definitions files in the
    Library will not necessarily be able to recompile the application
    to use the modified definitions.)

    b) Use a suitable shared library mechanism for linking with the
    Library.  A suitable mechanism is one that (1) uses at run time a
    copy of the library already present on the user's computer system,
    rather than copying library functions into the executable, and (2)
    will operate properly with a modified version of the library, if
    the user installs one, as long as the modified version is
    interface-compatible with the version that the work was made with.

    c) Accompany the work with a written offer, valid for at
    least three years, to give the same user the materials
    specified in Subsection 6a, above, for a charge no more
    than the cost of performing this distribution.

    d) If distribution of the work is made by offering access to copy
    from a designated place, offer equivalent access to copy the above
    specified materials from the same place.

    e) Verify that the user has already received a copy of these
    materials or that you have already sent this user a copy.

  For an executable, the required form of the "work that uses the
Library" must include any data and utility programs needed for
reproducing the executable from it.  However, as a special exception,
the materials to be distributed need not include anything that is
normally distributed (in either source or binary form) with the major
components (compiler, kernel, and so on) of the operating system on
which the executable runs, unless that component itself accompanies
the executable.

  It may happen that this requirement contradicts the license
restrictions of other proprietary libraries that do not normally
accompany the operating system.  Such a contradiction means you cannot
use both them and the Library together in an executable that you
distribute.

  7. You may place library facilities that are a work based on the
Library side-by-side in a single library together with other library
facilities not covered by this License, and distribute such a combined
library, provided that the separate distribution of the work based on
the Library and of the other library facilities is otherwise
permitted, and provided that you do these two things:

    a) Accompany the combined library with a copy of the same work
    based on the Library, uncombined with any other library
    facilities.  This must be distributed under the terms of the
    Sections above.

    b) Give prominent notice with the combined library of the fact
    that part of it is a work based on the Library, and explaining
    where to find the accompanying uncombined form of the same work.

  8. You may not copy, modify, sublicense, link with, or distribute
the Library except as expressly provided under this License.  Any
attempt otherwise to copy, modify, sublicense, link with, or
distribute the Library is void, and will automatically terminate your
rights under this License.  However, parties who have received copies,
or rights, from you under this License will not have their licenses
terminated so long as such parties remain in full compliance.

  9. You are not required to accept this License, since you have not
signed it.  However, nothing else grants you permission to modify or
distribute the Library or its derivative works.  These actions are
prohibited by law if you do not accept this License.  Therefore, by
modifying or distributing the Library (or any work based on the
Library), you indicate your acceptance of this License to do so, and
all its terms and conditions for copying, distributing or modifying
the Library or works based on it.

  10. Each time you redistribute the Library (or any work based on the
Library), the recipient automatically receives a license from the
original licensor to copy, distribute, link with or modify the Library
subject to these terms and conditions.  You may not impose any further
restrictions on the recipients' exercise of the rights granted herein.
You are not responsible for enforcing compliance by third parties with
this License.

  11. If, as a consequence of a court judgment or allegation of patent
infringement or for any other reason (not limited to patent issues),
conditions are imposed on you (whether by court order, agreement or
otherwise) that contradict the conditions of this License, they do not
excuse you from the conditions of this License.  If you cannot
distribute so as to satisfy simultaneously your obligations under this
License and any other pertinent obligations, then as a consequence you
may not distribute the Library at all.  For example, if a patent
license would not permit royalty-free redistribution of the Library by
all those who receive copies directly or indirectly through you, then
the only way you could satisfy both it and this License would be to
refrain entirely from distribution of the Library.

If any portion of this section is held invalid or unenforceable under any
particular circumstance, the balance of the section is intended to apply,
and the section as a whole is intended to apply in other circumstances.

It is not the purpose of this section to induce you to infringe any
patents or other property right claims or to contest validity of any
such claims; this section has the sole purpose of protecting the
integrity of the free software distribution system which is
implemented by public license practices.  Many people have made
generous contributions to the wide range of software distributed
through that system in reliance on consistent application of that
system; it is up to the author/donor to decide if he or she is willing
to distribute software through any other system and a licensee cannot
impose that choice.

This section is intended to make thoroughly clear what is believed to
be a consequence of the rest of this License.

  12. If the distribution and/or use of the Library is restricted in
certain countries either by patents or by copyrighted interfaces, the
original copyright holder who places the Library under this License may add
an explicit geographical distribution limitation excluding those countries,
so that distribution is permitted only in or among countries not thus
excluded.  In such case, this License incorporates the limitation as if
written in the body of this License.

  13. The Free Software Foundation may publish revised and/or new
versions of the Lesser General Public License from time to time.
Such new versions will be similar in spirit to the present version,
but may differ in detail to address new problems or concerns.

Each version is given a distinguishing version number.  If the Library
specifies a version number of this License which applies to it and
"any later version", you have the option of following the terms and
conditions either of that version or of any later version published by
the Free Software Foundation.  If the Library does not specify a
license version number, you may choose any version ever published by
the Free Software Foundation.

  14. If you wish to incorporate parts of the Library into other free
programs whose distribution conditions are incompatible with these,
write to the author to ask for permission.  For software which is
copyrighted by the Free Software Foundation, write to the Free
Software Foundation; we sometimes make exceptions for this.  Our
decision will be guided by the two goals of preserving the free status
of all derivatives of our free software and of promoting the sharing
and reuse of software generally.

                            NO WARRANTY

  15. BECAUSE THE LIBRARY IS LICENSED FREE OF CHARGE, THERE IS NO
WARRANTY FOR THE LIBRARY, TO THE EXTENT PERMITTED BY APPLICABLE LAW.
EXCEPT WHEN OTHERWISE STATED IN WRITING THE COPYRIGHT HOLDERS AND/OR
OTHER PARTIES PROVIDE THE LIBRARY "AS IS" WITHOUT WARRANTY OF ANY
KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
PURPOSE.  THE ENTIRE RISK AS TO THE QUALITY AND PERFORMANCE OF THE
LIBRARY IS WITH YOU.  SHOULD THE LIBRARY PROVE DEFECTIVE, YOU ASSUME
THE COST OF ALL NECESSARY SERVICING, REPAIR OR CORRECTION.

  16. IN NO EVENT UNLESS REQUIRED BY APPLICABLE LAW OR AGREED TO IN
WRITING WILL ANY COPYRIGHT HOLDER, OR ANY OTHER PARTY WHO MAY MODIFY
AND/OR REDISTRIBUTE THE LIBRARY AS PERMITTED ABOVE, BE LIABLE TO YOU
FOR DAMAGES, INCLUDING ANY GENERAL, SPECIAL, INCIDENTAL OR
CONSEQUENTIAL DAMAGES ARISING OUT OF THE USE OR INABILITY TO USE THE
LIBRARY (INCLUDING BUT NOT LIMITED TO LOSS OF DATA OR DATA BEING
RENDERED INACCURATE OR LOSSES SUSTAINED BY YOU OR THIRD PARTIES OR A
FAILURE OF THE LIBRARY TO OPERATE WITH ANY OTHER SOFTWARE), EVEN IF
SUCH HOLDER OR OTHER PARTY HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH
DAMAGES.

                     END OF TERMS AND CONDITIONS

           How to Apply These Terms to Your New Libraries

  If you develop a new library, and you want it to be of the greatest
possible use to the public, we recommend making it free software that
everyone can redistribute and change.  You can do so by permitting
redistribution under these terms (or, alternatively, under the terms of the
ordinary General Public License).

  To apply these terms, attach the following notices to the library.  It is
safest to attach them to the start of each source file to most effectively
convey the exclusion of warranty; and each file should have at least the
"copyright" line and a pointer to where the full notice is found.

    <one line to give the library's name and a brief idea of what it does.>
    Copyright (C) <year>  <name of author>

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA

Also add information on how to contact you by electronic and paper mail.

You should also get your employer (if you work as a programmer) or your
school, if any, to sign a "copyright disclaimer" for the library, if
necessary.  Here is a sample; alter the names:

  Yoyodyne, Inc., hereby disclaims all copyright interest in the
  library `Frob' (a library for tweaking knobs) written by James Random Hacker.

  <signature of Ty Coon>, 1 April 1990
  Ty Coon, President of Vice

That's all there is to it!

*/

#define HAVE_UNISTD_H 1
#if defined __BIG_ENDIAN__
# define WORDS_BIGENDIAN 1
#endif
#include <stdlib.h>

// file: Features.h
/*
	Audio File Library
	Copyright (C) 2013 Michael Pruett <michael@68k.org>

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

#ifndef Features_h
#define Features_h

#define ENABLE(FEATURE) (defined ENABLE_##FEATURE && ENABLE_##FEATURE)

#endif

// file: Compiler.h
/*
	Audio File Library
	Copyright (C) 2013 Michael Pruett <michael@68k.org>

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

#ifndef COMPILER_H
#define COMPILER_H

#if defined(__GNUC__) && !defined(__clang__)
#define GCC_VERSION (__GNUC__ * 10000 + __GNUC_MINOR__ * 100 + __GNUC_PATCHLEVEL__)
#define GCC_VERSION_AT_LEAST(major, minor, patch) \
	(GCC_VERSION >= (major * 10000 + minor * 100 + patch))
#if GCC_VERSION_AT_LEAST(4, 7, 0) && defined(__cplusplus) && __cplusplus >= 201103L
#define OVERRIDE override
#endif
#endif

#if defined(__clang__)
#if __has_extension(cxx_override_control)
#define OVERRRIDE override
#endif
#endif

#ifndef OVERRIDE
#define OVERRIDE
#endif

#endif

// file: error.h
/*
	Audio File Library
	Copyright (C) 1998, Michael Pruett <michael@68k.org>

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

#ifndef ERROR_H
#define ERROR_H

#ifdef __cplusplus
extern "C" {
#endif

#if !defined(__GNUC__) && !defined(__clang__) && !defined(__attribute__)
#define __attribute__(x)
#endif

void _af_error (int errorCode, const char *fmt, ...)
	__attribute__((format(printf, 2, 3)));

#ifdef __cplusplus
}
#endif

#endif

// file: extended.h
/*
	Audio File Library
	Copyright (C) 1998, Michael Pruett <michael@68k.org>

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

/*
	extended.h

	This file defines interfaces to Apple's extended floating-point
	conversion routines.
*/

#ifndef EXTENDED_H
#define EXTENDED_H

#ifdef __cplusplus
extern "C" {
#endif

void _af_convert_to_ieee_extended (double num, unsigned char *bytes);
double _af_convert_from_ieee_extended (const unsigned char *bytes);

#ifdef __cplusplus
}
#endif

#endif

// file: compression.h
/*
	Audio File Library
	Copyright (C) 1999, Michael Pruett <michael@68k.org>
	Copyright (C) 2000, Silicon Graphics, Inc.

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

/*
	compression.h
*/

#ifndef COMPRESSION_H
#define COMPRESSION_H

struct CompressionUnit;

const CompressionUnit *_af_compression_unit_from_id (int compressionid);

#endif

// file: aupvinternal.h
/*
	Audio File Library
	Copyright (C) 1998-2000, Michael Pruett <michael@68k.org>

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

/*
	aupvinternal.h

	This file contains the private data structures for the parameter
	value list data types.
*/

#ifndef AUPVINTERNAL_H
#define AUPVINTERNAL_H

struct _AUpvitem
{
	int	valid;
	int	type;
	int	parameter;

	union
	{
		long	l;
		double	d;
		void	*v;
	}
	value;
};

struct _AUpvlist
{
	int			valid;
	size_t			count;
	struct _AUpvitem	*items;
};

enum
{
	_AU_VALID_PVLIST = 30932,
	_AU_VALID_PVITEM = 30933
};

enum
{
	AU_BAD_PVLIST = -5,
	AU_BAD_PVITEM = -6,
	AU_BAD_PVTYPE = -7,
	AU_BAD_ALLOC = -8
};

enum
{
	_AU_FAIL = -1,
	_AU_SUCCESS = 0
};

#define _AU_NULL_PVITEM ((struct _AUpvitem *) NULL)

#endif

// file: aupvlist.h
/*
	Audio File Library
	Copyright (C) 1998-2000, Michael Pruett <michael@68k.org>

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

/*
	aupvlist.h

	This file contains the interface to the parameter value list data
	structures and routines.
*/

#ifndef AUPVLIST_H
#define AUPVLIST_H

#ifdef __cplusplus
extern "C" {
#endif

#if (defined(__GNUC__) && __GNUC__ >= 4) || defined(__clang__)
#define AFAPI __attribute__((visibility("default")))
#else
#define AFAPI
#endif

enum
{
	AU_PVTYPE_LONG = 1,
	AU_PVTYPE_DOUBLE = 2,
	AU_PVTYPE_PTR = 3
};

typedef struct _AUpvlist *AUpvlist;

#define AU_NULL_PVLIST ((struct _AUpvlist *) 0)

AFAPI AUpvlist AUpvnew (int maxItems);
AFAPI int AUpvgetmaxitems (AUpvlist);
AFAPI int AUpvfree (AUpvlist);
AFAPI int AUpvsetparam (AUpvlist, int item, int param);
AFAPI int AUpvsetvaltype (AUpvlist, int item, int type);
AFAPI int AUpvsetval (AUpvlist, int item, void *val);
AFAPI int AUpvgetparam (AUpvlist, int item, int *param);
AFAPI int AUpvgetvaltype (AUpvlist, int item, int *type);
AFAPI int AUpvgetval (AUpvlist, int item, void *val);

#undef AFAPI

#ifdef __cplusplus
}
#endif

#endif /* AUPVLIST_H */

// file: audiofile.h
/*
	Audio File Library
	Copyright (C) 1998-2000, 2010-2013 Michael Pruett <michael@68k.org>

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

/*
	audiofile.h

	This file contains the public interfaces to the Audio File Library.
*/

#ifndef AUDIOFILE_H
#define AUDIOFILE_H

#include <aupvlist.h>
#include <stdint.h>
#include <sys/types.h>

#define LIBAUDIOFILE_MAJOR_VERSION 0
#define LIBAUDIOFILE_MINOR_VERSION 3
#define LIBAUDIOFILE_MICRO_VERSION 6

#ifdef __cplusplus
extern "C" {
#endif

#if (defined(__GNUC__) && __GNUC__ >= 4) || defined(__clang__)
#define AFAPI __attribute__((visibility("default")))
#else
#define AFAPI
#endif

typedef struct _AFvirtualfile AFvirtualfile;

typedef struct _AFfilesetup *AFfilesetup;
typedef struct _AFfilehandle *AFfilehandle;
typedef void (*AFerrfunc)(long, const char *);

// Define AFframecount and AFfileoffset as 64-bit signed integers.
#if defined(__FreeBSD__) || \
	defined(__DragonFly__) || \
	defined(__NetBSD__) || \
	defined(__OpenBSD__) || \
	defined(__APPLE__) || \
	defined(__sgi) || \
	(defined(__linux__) && defined(__LP64__))
// BSD and IRIX systems define off_t as a 64-bit signed integer.
// Linux defines off_t as a 64-bit signed integer in LP64 mode.
typedef off_t AFframecount;
typedef off_t AFfileoffset;
#else
// For all other systems, use int64_t.
typedef int64_t AFframecount;
typedef int64_t AFfileoffset;
#endif

#define AF_NULL_FILESETUP	((struct _AFfilesetup *) 0)
#define AF_NULL_FILEHANDLE	((struct _AFfilehandle *) 0)

#define AF_ERR_BASE 3000

enum
{
	AF_DEFAULT_TRACK = 1001
};

enum
{
	AF_DEFAULT_INST = 2001
};

enum
{
	AF_NUM_UNLIMITED = 99999
};

enum
{
	AF_BYTEORDER_BIGENDIAN = 501,
	AF_BYTEORDER_LITTLEENDIAN = 502
};

enum
{
	AF_FILE_UNKNOWN = -1,
	AF_FILE_RAWDATA = 0,
	AF_FILE_AIFFC = 1,
	AF_FILE_AIFF = 2,
	AF_FILE_NEXTSND = 3,
	AF_FILE_WAVE = 4,
	AF_FILE_BICSF = 5,
	AF_FILE_IRCAM = AF_FILE_BICSF,
	AF_FILE_MPEG1BITSTREAM = 6,	/* not implemented */
	AF_FILE_SOUNDDESIGNER1 = 7,	/* not implemented */
	AF_FILE_SOUNDDESIGNER2 = 8,	/* not implemented */
	AF_FILE_AVR = 9,
	AF_FILE_IFF_8SVX = 10,
	AF_FILE_SAMPLEVISION = 11,
	AF_FILE_VOC = 12,
	AF_FILE_NIST_SPHERE = 13,
	AF_FILE_SOUNDFONT2 = 14,	/* not implemented */
	AF_FILE_CAF = 15,
	AF_FILE_FLAC = 16
};

enum
{
	AF_LOOP_MODE_NOLOOP = 0,
	AF_LOOP_MODE_FORW = 1,
	AF_LOOP_MODE_FORWBAKW = 2
};

enum
{
	AF_SAMPFMT_TWOSCOMP = 401, /* linear two's complement */
	AF_SAMPFMT_UNSIGNED = 402, /* unsigned integer */
	AF_SAMPFMT_FLOAT = 403, /* 32-bit IEEE floating-point */
	AF_SAMPFMT_DOUBLE = 404 /* 64-bit IEEE double-precision floating-point */
};

enum
{
	AF_INST_LOOP_OFF = 0,			/* no looping */
	AF_INST_LOOP_CONTINUOUS = 1,	/* loop continuously through decay */
	AF_INST_LOOP_SUSTAIN = 3		/* loop during sustain, then continue */
};

enum
{
	AF_INST_MIDI_BASENOTE = 301,
	AF_INST_NUMCENTS_DETUNE = 302,
	AF_INST_MIDI_LONOTE = 303,
	AF_INST_MIDI_HINOTE = 304,
	AF_INST_MIDI_LOVELOCITY = 305,
	AF_INST_MIDI_HIVELOCITY = 306,
	AF_INST_NUMDBS_GAIN = 307,
	AF_INST_SUSLOOPID = 308,		/* loop id for AIFF sustain loop */
	AF_INST_RELLOOPID = 309,		/* loop id for AIFF release loop */
	AF_INST_SAMP_STARTFRAME = 310,	/* start sample for this inst */
	AF_INST_SAMP_ENDFRAME = 311,	/* end sample for this inst */
	AF_INST_SAMP_MODE = 312,		/* looping mode for this inst */
	AF_INST_TRACKID = 313,
	AF_INST_NAME = 314,				/* name of this inst */
	AF_INST_SAMP_RATE = 315,		/* sample rate of this inst's sample */
	AF_INST_PRESETID = 316,			/* ID of preset containing this inst */
	AF_INST_PRESET_NAME = 317		/* name of preset containing this inst */
};

enum
{
	AF_MISC_UNRECOGNIZED = 0,	/* unrecognized data chunk */
	AF_MISC_COPY = 201,	/* copyright string */
	AF_MISC_AUTH = 202,	/* author string */
	AF_MISC_NAME = 203,	/* name string */
	AF_MISC_ANNO = 204,	/* annotation string */
	AF_MISC_APPL = 205,	/* application-specific data */
	AF_MISC_MIDI = 206,	/* MIDI exclusive data */
	AF_MISC_PCMMAP = 207,	/* PCM mapping information (future use) */
	AF_MISC_NeXT = 208,	/* misc binary data appended to NeXT header */
	AF_MISC_IRCAM_PEAKAMP = 209,	/* peak amplitude information */
	AF_MISC_IRCAM_COMMENT = 210,	/* BICSF text comment */
	AF_MISC_COMMENT = 210,	/* general text comment */

	AF_MISC_ICMT = AF_MISC_COMMENT,	/* comments chunk (WAVE format) */
	AF_MISC_ICRD = 211,  /* creation date (WAVE format) */
	AF_MISC_ISFT = 212  /* software name (WAVE format) */
};

enum
{
	/* supported compression schemes */
	AF_COMPRESSION_UNKNOWN = -1,
	AF_COMPRESSION_NONE = 0,
	AF_COMPRESSION_G722 = 501,
	AF_COMPRESSION_G711_ULAW = 502,
	AF_COMPRESSION_G711_ALAW = 503,

	/* Apple proprietary AIFF-C compression schemes (not supported) */
	AF_COMPRESSION_APPLE_ACE2 = 504,
	AF_COMPRESSION_APPLE_ACE8 = 505,
	AF_COMPRESSION_APPLE_MAC3 = 506,
	AF_COMPRESSION_APPLE_MAC6 = 507,

	AF_COMPRESSION_G726 = 517,
	AF_COMPRESSION_G728 = 518,
	AF_COMPRESSION_DVI_AUDIO = 519,
	AF_COMPRESSION_IMA = AF_COMPRESSION_DVI_AUDIO,
	AF_COMPRESSION_GSM = 520,
	AF_COMPRESSION_FS1016 = 521,
	AF_COMPRESSION_DV = 522,
	AF_COMPRESSION_MS_ADPCM = 523,

	AF_COMPRESSION_FLAC = 530,
	AF_COMPRESSION_ALAC = 540
};

/* tokens for afQuery() -- see the man page for instructions */
/* level 1 selectors */
enum
{
	AF_QUERYTYPE_INSTPARAM = 500,
	AF_QUERYTYPE_FILEFMT = 501,
	AF_QUERYTYPE_COMPRESSION = 502,
	AF_QUERYTYPE_COMPRESSIONPARAM = 503,
	AF_QUERYTYPE_MISC = 504,
	AF_QUERYTYPE_INST = 505,
	AF_QUERYTYPE_MARK = 506,
	AF_QUERYTYPE_LOOP = 507
};

/* level 2 selectors */
enum
{
	AF_QUERY_NAME = 600,	/* get name (1-3 words) */
	AF_QUERY_DESC = 601,	/* get description */
	AF_QUERY_LABEL = 602,	/* get 4- or 5-char label */
	AF_QUERY_TYPE = 603,	/* get type token */
	AF_QUERY_DEFAULT = 604,	/* dflt. value for param */
	AF_QUERY_ID_COUNT = 605,	/* get number of ids avail. */
	AF_QUERY_IDS = 606,	/* get array of id tokens */
	AF_QUERY_IMPLEMENTED = 613,	/* boolean */
	AF_QUERY_TYPE_COUNT = 607,	/* get number of types av. */
	AF_QUERY_TYPES = 608,	/* get array of types */
	AF_QUERY_NATIVE_SAMPFMT = 609,	/* for compression */
	AF_QUERY_NATIVE_SAMPWIDTH = 610,
	AF_QUERY_SQUISHFAC = 611,	/* 1.0 means variable */
	AF_QUERY_MAX_NUMBER = 612,	/* max allowed in file */
	AF_QUERY_SUPPORTED = 613	/* insts, loops, etc., supported? */
};

/* level 2 selectors which have sub-selectors */
enum
{
	AF_QUERY_TRACKS = 620,
	AF_QUERY_CHANNELS = 621,
	AF_QUERY_SAMPLE_SIZES = 622,
	AF_QUERY_SAMPLE_FORMATS = 623,
	AF_QUERY_COMPRESSION_TYPES = 624
};

/* level 3 sub-selectors */
enum
{
	AF_QUERY_VALUE_COUNT = 650,	/* number of values of the above */
	AF_QUERY_VALUES = 651	/* array of those values */
};


/*
	Old Audio File Library error codes. These are still returned by the
	AFerrorhandler calls, but are not used by the new digital media library
	error reporting routines. See the bottom of this file for the new error
	tokens.
*/

enum
{
	AF_BAD_NOT_IMPLEMENTED = 0,	/* not implemented yet */
	AF_BAD_FILEHANDLE = 1,	/* tried to use invalid filehandle */
	AF_BAD_OPEN = 3,	/* unix open failed */
	AF_BAD_CLOSE = 4,	/* unix close failed */
	AF_BAD_READ = 5,	/* unix read failed */
	AF_BAD_WRITE = 6,	/* unix write failed */
	AF_BAD_LSEEK = 7,	/* unix lseek failed */
	AF_BAD_NO_FILEHANDLE = 8,	/* failed to allocate a filehandle struct */
	AF_BAD_ACCMODE = 10,	/* unrecognized audio file access mode */
	AF_BAD_NOWRITEACC = 11,	/* file not open for writing */
	AF_BAD_NOREADACC = 12,	/* file not open for reading */
	AF_BAD_FILEFMT = 13,	/* unrecognized audio file format */
	AF_BAD_RATE = 14,	/* invalid sample rate */
	AF_BAD_CHANNELS = 15,	/* invalid number of channels*/
	AF_BAD_SAMPCNT = 16,	/* invalid sample count */
	AF_BAD_WIDTH = 17,	/* invalid sample width */
	AF_BAD_SEEKMODE = 18,	/* invalid seek mode */
	AF_BAD_NO_LOOPDATA = 19,	/* failed to allocate loop struct */
	AF_BAD_MALLOC = 20,	/* malloc failed somewhere */
	AF_BAD_LOOPID = 21,
	AF_BAD_SAMPFMT = 22,	/* bad sample format */
	AF_BAD_FILESETUP = 23,	/* bad file setup structure*/
	AF_BAD_TRACKID = 24,	/* no track corresponding to id */
	AF_BAD_NUMTRACKS = 25,	/* wrong number of tracks for file format */
	AF_BAD_NO_FILESETUP = 26,	/* failed to allocate a filesetup struct*/
	AF_BAD_LOOPMODE = 27,	/* unrecognized loop mode value */
	AF_BAD_INSTID = 28,	/* invalid instrument id */
	AF_BAD_NUMLOOPS = 29,	/* bad number of loops */
	AF_BAD_NUMMARKS = 30,	/* bad number of markers */
	AF_BAD_MARKID = 31,	/* bad marker id */
	AF_BAD_MARKPOS = 32,	/* invalid marker position value */
	AF_BAD_NUMINSTS = 33,	/* invalid number of instruments */
	AF_BAD_NOAESDATA = 34,
	AF_BAD_MISCID = 35,
	AF_BAD_NUMMISC = 36,
	AF_BAD_MISCSIZE = 37,
	AF_BAD_MISCTYPE = 38,
	AF_BAD_MISCSEEK = 39,
	AF_BAD_STRLEN = 40,	/* invalid string length */
	AF_BAD_RATECONV = 45,
	AF_BAD_SYNCFILE = 46,
	AF_BAD_CODEC_CONFIG = 47,	/* improperly configured codec */
	AF_BAD_CODEC_STATE = 48,	/* invalid codec state: can't recover */
	AF_BAD_CODEC_LICENSE = 49,	/* no license available for codec */
	AF_BAD_CODEC_TYPE = 50,	/* unsupported codec type */
	AF_BAD_COMPRESSION = AF_BAD_CODEC_CONFIG,	/* for back compat */
	AF_BAD_COMPTYPE = AF_BAD_CODEC_TYPE,	/* for back compat */

	AF_BAD_INSTPTYPE = 51,	/* invalid instrument parameter type */
	AF_BAD_INSTPID = 52,	/* invalid instrument parameter id */
	AF_BAD_BYTEORDER = 53,
	AF_BAD_FILEFMT_PARAM = 54,	/* unrecognized file format parameter */
	AF_BAD_COMP_PARAM = 55,	/* unrecognized compression parameter */
	AF_BAD_DATAOFFSET = 56,	/* bad data offset */
	AF_BAD_FRAMECNT = 57,	/* bad frame count */
	AF_BAD_QUERYTYPE = 58,	/* bad query type */
	AF_BAD_QUERY = 59,	/* bad argument to afQuery() */
	AF_WARNING_CODEC_RATE = 60,	/* using 8k instead of codec rate 8012 */
	AF_WARNING_RATECVT = 61,	/* warning about rate conversion used */

	AF_BAD_HEADER = 62,	/* failed to parse header */
	AF_BAD_FRAME = 63,	/* bad frame number */
	AF_BAD_LOOPCOUNT = 64,	/* bad loop count */
	AF_BAD_DMEDIA_CALL = 65,	/* error in dmedia subsystem call */

	/* AIFF/AIFF-C specific errors when parsing file header */
	AF_BAD_AIFF_HEADER = 108,	/* failed to parse chunk header */
	AF_BAD_AIFF_FORM = 109,	/* failed to parse FORM chunk */
	AF_BAD_AIFF_SSND = 110,	/* failed to parse SSND chunk */
	AF_BAD_AIFF_CHUNKID = 111,	/* unrecognized AIFF/AIFF-C chunk id */
	AF_BAD_AIFF_COMM = 112,	/* failed to parse COMM chunk */
	AF_BAD_AIFF_INST = 113,	/* failed to parse INST chunk */
	AF_BAD_AIFF_MARK = 114,	/* failed to parse MARK chunk */
	AF_BAD_AIFF_SKIP = 115,	/* failed to skip unsupported chunk */
	AF_BAD_AIFF_LOOPMODE = 116	/* unrecognized loop mode (forw, etc)*/
};

/* new error codes which may be retrieved via dmGetError() */
/* The old error tokens continue to be retrievable via the AFerrorhandler */
/* AF_ERR_BASE is #defined in dmedia/dmedia.h */

enum
{
	AF_ERR_NOT_IMPLEMENTED = 0+AF_ERR_BASE,	/* not implemented yet */
	AF_ERR_BAD_FILEHANDLE = 1+AF_ERR_BASE,	/* invalid filehandle */
	AF_ERR_BAD_READ = 5+AF_ERR_BASE,	/* unix read failed */
	AF_ERR_BAD_WRITE = 6+AF_ERR_BASE,	/* unix write failed */
	AF_ERR_BAD_LSEEK = 7+AF_ERR_BASE,	/* unix lseek failed */
	AF_ERR_BAD_ACCMODE = 10+AF_ERR_BASE,	/* unrecognized audio file access mode */
	AF_ERR_NO_WRITEACC = 11+AF_ERR_BASE,	/* file not open for writing */
	AF_ERR_NO_READACC = 12+AF_ERR_BASE,	/* file not open for reading */
	AF_ERR_BAD_FILEFMT = 13+AF_ERR_BASE,	/* unrecognized audio file format */
	AF_ERR_BAD_RATE = 14+AF_ERR_BASE,	/* invalid sample rate */
	AF_ERR_BAD_CHANNELS = 15+AF_ERR_BASE,	/* invalid # channels*/
	AF_ERR_BAD_SAMPCNT = 16+AF_ERR_BASE,	/* invalid sample count */
	AF_ERR_BAD_WIDTH = 17+AF_ERR_BASE,	/* invalid sample width */
	AF_ERR_BAD_SEEKMODE = 18+AF_ERR_BASE,	/* invalid seek mode */
	AF_ERR_BAD_LOOPID = 21+AF_ERR_BASE,	/* invalid loop id */
	AF_ERR_BAD_SAMPFMT = 22+AF_ERR_BASE,	/* bad sample format */
	AF_ERR_BAD_FILESETUP = 23+AF_ERR_BASE,	/* bad file setup structure*/
	AF_ERR_BAD_TRACKID = 24+AF_ERR_BASE,	/* no track corresponding to id */
	AF_ERR_BAD_NUMTRACKS = 25+AF_ERR_BASE,	/* wrong number of tracks for file format */
	AF_ERR_BAD_LOOPMODE = 27+AF_ERR_BASE,	/* unrecognized loop mode symbol */
	AF_ERR_BAD_INSTID = 28+AF_ERR_BASE,	/* invalid instrument id */
	AF_ERR_BAD_NUMLOOPS = 29+AF_ERR_BASE,	/* bad number of loops */
	AF_ERR_BAD_NUMMARKS = 30+AF_ERR_BASE,	/* bad number of markers */
	AF_ERR_BAD_MARKID = 31+AF_ERR_BASE,	/* bad marker id */
	AF_ERR_BAD_MARKPOS = 32+AF_ERR_BASE,	/* invalid marker position value */
	AF_ERR_BAD_NUMINSTS = 33+AF_ERR_BASE,	/* invalid number of instruments */
	AF_ERR_BAD_NOAESDATA = 34+AF_ERR_BASE,
	AF_ERR_BAD_MISCID = 35+AF_ERR_BASE,
	AF_ERR_BAD_NUMMISC = 36+AF_ERR_BASE,
	AF_ERR_BAD_MISCSIZE = 37+AF_ERR_BASE,
	AF_ERR_BAD_MISCTYPE = 38+AF_ERR_BASE,
	AF_ERR_BAD_MISCSEEK = 39+AF_ERR_BASE,
	AF_ERR_BAD_STRLEN = 40+AF_ERR_BASE,	/* invalid string length */
	AF_ERR_BAD_RATECONV = 45+AF_ERR_BASE,
	AF_ERR_BAD_SYNCFILE = 46+AF_ERR_BASE,
	AF_ERR_BAD_CODEC_CONFIG = 47+AF_ERR_BASE,	/* improperly configured codec */
	AF_ERR_BAD_CODEC_TYPE = 50+AF_ERR_BASE,	/* unsupported codec type */
	AF_ERR_BAD_INSTPTYPE = 51+AF_ERR_BASE,	/* invalid instrument parameter type */
	AF_ERR_BAD_INSTPID = 52+AF_ERR_BASE,	/* invalid instrument parameter id */

	AF_ERR_BAD_BYTEORDER = 53+AF_ERR_BASE,
	AF_ERR_BAD_FILEFMT_PARAM = 54+AF_ERR_BASE,	/* unrecognized file format parameter */
	AF_ERR_BAD_COMP_PARAM = 55+AF_ERR_BASE,	/* unrecognized compression parameter */
	AF_ERR_BAD_DATAOFFSET = 56+AF_ERR_BASE,	/* bad data offset */
	AF_ERR_BAD_FRAMECNT = 57+AF_ERR_BASE,	/* bad frame count */

	AF_ERR_BAD_QUERYTYPE = 58+AF_ERR_BASE,	/* bad query type */
	AF_ERR_BAD_QUERY = 59+AF_ERR_BASE,	/* bad argument to afQuery() */
	AF_ERR_BAD_HEADER = 62+AF_ERR_BASE,	/* failed to parse header */
	AF_ERR_BAD_FRAME = 63+AF_ERR_BASE,	/* bad frame number */
	AF_ERR_BAD_LOOPCOUNT = 64+AF_ERR_BASE,	/* bad loop count */

	/* AIFF/AIFF-C specific errors when parsing file header */

	AF_ERR_BAD_AIFF_HEADER = 66+AF_ERR_BASE,	/* failed to parse chunk header */
	AF_ERR_BAD_AIFF_FORM = 67+AF_ERR_BASE,	/* failed to parse FORM chunk */
	AF_ERR_BAD_AIFF_SSND = 68+AF_ERR_BASE,	/* failed to parse SSND chunk */
	AF_ERR_BAD_AIFF_CHUNKID = 69+AF_ERR_BASE,	/* unrecognized AIFF/AIFF-C chunk id */
	AF_ERR_BAD_AIFF_COMM = 70+AF_ERR_BASE,	/* failed to parse COMM chunk */
	AF_ERR_BAD_AIFF_INST = 71+AF_ERR_BASE,	/* failed to parse INST chunk */
	AF_ERR_BAD_AIFF_MARK = 72+AF_ERR_BASE,	/* failed to parse MARK chunk */
	AF_ERR_BAD_AIFF_SKIP = 73+AF_ERR_BASE,	/* failed to skip unsupported chunk */
	AF_ERR_BAD_AIFF_LOOPMODE = 74+AF_ERR_BASE	/* unrecognized loop mode (forw, etc) */
};


/* global routines */
AFAPI AFerrfunc afSetErrorHandler (AFerrfunc efunc);

/* query routines */
AFAPI AUpvlist afQuery (int querytype, int arg1, int arg2, int arg3, int arg4);
AFAPI long afQueryLong (int querytype, int arg1, int arg2, int arg3, int arg4);
AFAPI double afQueryDouble (int querytype, int arg1, int arg2, int arg3, int arg4);
AFAPI void *afQueryPointer (int querytype, int arg1, int arg2, int arg3, int arg4);

/* basic operations on file handles and file setups */
AFAPI AFfilesetup afNewFileSetup (void);
AFAPI void afFreeFileSetup (AFfilesetup);
AFAPI int afIdentifyFD (int);
AFAPI int afIdentifyNamedFD (int, const char *filename, int *implemented);

AFAPI AFfilehandle afOpenFile (const char *filename, const char *mode,
	AFfilesetup setup);
AFAPI AFfilehandle afOpenVirtualFile (AFvirtualfile *vfile, const char *mode,
	AFfilesetup setup);
AFAPI AFfilehandle afOpenFD (int fd, const char *mode, AFfilesetup setup);
AFAPI AFfilehandle afOpenNamedFD (int fd, const char *mode, AFfilesetup setup,
	const char *filename);

AFAPI void afSaveFilePosition (AFfilehandle file);
AFAPI void afRestoreFilePosition (AFfilehandle file);
AFAPI int afSyncFile (AFfilehandle file);
AFAPI int afCloseFile (AFfilehandle file);

AFAPI void afInitFileFormat (AFfilesetup, int format);
AFAPI int afGetFileFormat (AFfilehandle, int *version);

/* track */
AFAPI void afInitTrackIDs (AFfilesetup, const int *trackids, int trackCount);
AFAPI int afGetTrackIDs (AFfilehandle, int *trackids);

/* track data: reading, writng, seeking, sizing frames */
AFAPI int afReadFrames (AFfilehandle, int track, void *buffer, int frameCount);
AFAPI int afWriteFrames (AFfilehandle, int track, const void *buffer, int frameCount);
AFAPI AFframecount afSeekFrame (AFfilehandle, int track, AFframecount frameoffset);
AFAPI AFframecount afTellFrame (AFfilehandle, int track);
AFAPI AFfileoffset afGetTrackBytes (AFfilehandle, int track);
AFAPI float afGetFrameSize (AFfilehandle, int track, int expand3to4);
AFAPI float afGetVirtualFrameSize (AFfilehandle, int track, int expand3to4);

/* track data: AES data */
/* afInitAESChannelData is obsolete -- use afInitAESChannelDataTo() */
AFAPI void afInitAESChannelData (AFfilesetup, int track); /* obsolete */
AFAPI void afInitAESChannelDataTo (AFfilesetup, int track, int willBeData);
AFAPI int afGetAESChannelData (AFfilehandle, int track, unsigned char buf[24]);
AFAPI void afSetAESChannelData (AFfilehandle, int track, unsigned char buf[24]);

/* track data: byte order */
AFAPI void afInitByteOrder (AFfilesetup, int track, int byteOrder);
AFAPI int afGetByteOrder (AFfilehandle, int track);
AFAPI int afSetVirtualByteOrder (AFfilehandle, int track, int byteOrder);
AFAPI int afGetVirtualByteOrder (AFfilehandle, int track);

/* track data: number of channels */
AFAPI void afInitChannels (AFfilesetup, int track, int nchannels);
AFAPI int afGetChannels (AFfilehandle, int track);
AFAPI int afSetVirtualChannels (AFfilehandle, int track, int channelCount);
AFAPI int afGetVirtualChannels (AFfilehandle, int track);
AFAPI void afSetChannelMatrix (AFfilehandle, int track, double *matrix);

/* track data: sample format and sample width */
AFAPI void afInitSampleFormat (AFfilesetup, int track, int sampleFormat,
	int sampleWidth);
AFAPI void afGetSampleFormat (AFfilehandle file, int track, int *sampleFormat,
	int *sampleWidth);
AFAPI int afSetVirtualSampleFormat (AFfilehandle, int track,
	int sampleFormat, int sampleWidth);
AFAPI void afGetVirtualSampleFormat (AFfilehandle, int track,
	int *sampleFormat, int *sampleWidth);

/* track data: sampling rate */
AFAPI void afInitRate (AFfilesetup, int track, double rate);
AFAPI double afGetRate (AFfilehandle, int track);

#if 0
int afSetVirtualRate (AFfilehandle, int track, double rate);
double afGetVirtualRate (AFfilehandle, int track);
#endif

/* track data: compression */
AFAPI void afInitCompression (AFfilesetup, int track, int compression);
#if 0
void afInitCompressionParams (AFfilesetup, int track, int compression
	AUpvlist params, int parameterCount);
#endif

AFAPI int afGetCompression (AFfilehandle, int track);
#if 0
void afGetCompressionParams (AFfilehandle, int track, int *compression,
	AUpvlist params, int parameterCount);

int afSetVirtualCompression (AFfilesetup, int track, int compression);
void afSetVirtualCompressionParams (AFfilehandle, int track, int compression,
	AUpvlist params, int parameterCount);

int afGetVirtualCompression (AFfilesetup, int track, int compression);
void afGetVirtualCompressionParams (AFfilehandle, int track, int *compression,
	AUpvlist params, int parameterCount);
#endif

/* track data: pcm mapping */
AFAPI void afInitPCMMapping (AFfilesetup filesetup, int track,
	double slope, double intercept, double minClip, double maxClip);
AFAPI void afGetPCMMapping (AFfilehandle file, int track,
	double *slope, double *intercept, double *minClip, double *maxClip);
/* NOTE: afSetTrackPCMMapping() is special--it does not set the virtual  */
/* format; it changes what the AF thinks the track format is! Be careful. */
AFAPI int afSetTrackPCMMapping (AFfilehandle file, int track,
	double slope, double intercept, double minClip, double maxClip);
/* NOTE: afSetVirtualPCMMapping() is different from afSetTrackPCMMapping(): */
/* see comment for afSetTrackPCMMapping(). */
AFAPI int afSetVirtualPCMMapping (AFfilehandle file, int track,
	double slope, double intercept, double minClip, double maxClip);
AFAPI void afGetVirtualPCMMapping (AFfilehandle file, int track,
	double *slope, double *intercept, double *minClip, double *maxClip);

/* track data: data offset within the file */
/* initialize for raw reading only */
AFAPI void afInitDataOffset(AFfilesetup, int track, AFfileoffset offset);
AFAPI AFfileoffset afGetDataOffset (AFfilehandle, int track);

/* track data: count of frames in file */
AFAPI void afInitFrameCount (AFfilesetup, int track, AFframecount frameCount);
AFAPI AFframecount afGetFrameCount (AFfilehandle file, int track);

/* loop operations */
AFAPI void afInitLoopIDs (AFfilesetup, int instid, const int *ids, int nids);
AFAPI int afGetLoopIDs (AFfilehandle, int instid, int loopids[]);
AFAPI void afSetLoopMode (AFfilehandle, int instid, int loop, int mode);
AFAPI int afGetLoopMode (AFfilehandle, int instid, int loopid);
AFAPI int afSetLoopCount (AFfilehandle, int instid, int loop, int count);
AFAPI int afGetLoopCount (AFfilehandle, int instid, int loopid);
AFAPI void afSetLoopStart (AFfilehandle, int instid, int loopid, int markerid);
AFAPI int afGetLoopStart (AFfilehandle, int instid, int loopid);
AFAPI void afSetLoopEnd (AFfilehandle, int instid, int loopid, int markerid);
AFAPI int afGetLoopEnd (AFfilehandle, int instid, int loopid);

AFAPI int afSetLoopStartFrame (AFfilehandle, int instid, int loop,
	AFframecount startFrame);
AFAPI AFframecount afGetLoopStartFrame (AFfilehandle, int instid, int loop);
AFAPI int afSetLoopEndFrame (AFfilehandle, int instid, int loop,
	AFframecount startFrame);
AFAPI AFframecount afGetLoopEndFrame (AFfilehandle, int instid, int loop);

AFAPI void afSetLoopTrack (AFfilehandle, int instid, int loopid, int trackid);
AFAPI int afGetLoopTrack (AFfilehandle, int instid, int loopid);

/* marker operations */
AFAPI void afInitMarkIDs (AFfilesetup, int trackid, const int *ids, int nids);
AFAPI int afGetMarkIDs (AFfilehandle file, int trackid, int markids[]);
AFAPI void afSetMarkPosition (AFfilehandle file, int trackid, int markid,
	AFframecount markpos);
AFAPI AFframecount afGetMarkPosition (AFfilehandle file, int trackid, int markid);
AFAPI void afInitMarkName (AFfilesetup, int trackid, int marker, const char *name);
AFAPI void afInitMarkComment (AFfilesetup, int trackid, int marker,
	const char *comment);
AFAPI char *afGetMarkName (AFfilehandle file, int trackid, int markid);
AFAPI char *afGetMarkComment (AFfilehandle file, int trackid, int markid);

/* instrument operations */
AFAPI void afInitInstIDs (AFfilesetup, const int *ids, int nids);
AFAPI int afGetInstIDs (AFfilehandle file, int *instids);
AFAPI void afGetInstParams (AFfilehandle file, int instid, AUpvlist pvlist,
	int nparams);
AFAPI void afSetInstParams (AFfilehandle file, int instid, AUpvlist pvlist,
	int nparams);
AFAPI long afGetInstParamLong (AFfilehandle file, int instid, int param);
AFAPI void afSetInstParamLong (AFfilehandle file, int instid, int param, long value);

/* miscellaneous data operations */
AFAPI void afInitMiscIDs (AFfilesetup, const int *ids, int nids);
AFAPI int afGetMiscIDs (AFfilehandle, int *ids);
AFAPI void afInitMiscType (AFfilesetup, int miscellaneousid, int type);
AFAPI int afGetMiscType (AFfilehandle, int miscellaneousid);
AFAPI void afInitMiscSize (AFfilesetup, int miscellaneousid, int size);
AFAPI int afGetMiscSize (AFfilehandle, int miscellaneousid);
AFAPI int afWriteMisc (AFfilehandle, int miscellaneousid, const void *buf, int bytes);
AFAPI int afReadMisc (AFfilehandle, int miscellaneousid, void *buf, int bytes);
AFAPI int afSeekMisc (AFfilehandle, int miscellaneousid, int offset);

#undef AFAPI

#ifdef __cplusplus
}
#endif

#endif /* AUDIOFILE_H */

// file: afinternal.h
/*
	Audio File Library
	Copyright (C) 1998-2000, Michael Pruett <michael@68k.org>
	Copyright (C) 2000, Silicon Graphics, Inc.

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

/*
	afinternal.h

	This file defines the internal structures for the Audio File Library.
*/

#ifndef AFINTERNAL_H
#define AFINTERNAL_H

#include <sys/types.h>

enum status
{
	AF_SUCCEED = 0,
	AF_FAIL = -1
};

union AFPVu
{
	long	l;
	double	d;
	void	*v;
};

struct InstParamInfo
{
	int id;
	int type;
	const char *name;
	AFPVu defaultValue;
};

struct Loop
{
	int	id;
	int	mode;	/* AF_LOOP_MODE_... */
	int	count;	/* how many times the loop is played */
	int	beginMarker, endMarker;
	int	trackid;
};

struct LoopSetup
{
	int	id;
};

struct Miscellaneous
{
	int id;
	int type;
	int size;

	void *buffer;

	int position;	// offset within the miscellaneous chunk
};

struct MiscellaneousSetup
{
	int	id;
	int	type;
	int	size;
};

struct TrackSetup;

class File;
struct Track;

enum
{
	_AF_VALID_FILEHANDLE = 38212,
	_AF_VALID_FILESETUP = 38213
};

enum
{
	_AF_READ_ACCESS = 1,
	_AF_WRITE_ACCESS = 2
};

// The following are tokens for compression parameters in PV lists.
enum
{
	_AF_MS_ADPCM_NUM_COEFFICIENTS = 800,	/* type: long */
	_AF_MS_ADPCM_COEFFICIENTS = 801,		/* type: array of int16_t[2] */
	_AF_IMA_ADPCM_TYPE = 810,
	_AF_IMA_ADPCM_TYPE_WAVE = 1,
	_AF_IMA_ADPCM_TYPE_QT = 2,
	_AF_CODEC_DATA = 900,		// type: pointer
	_AF_CODEC_DATA_SIZE = 901	// type: long
};

/* NeXT/Sun sampling rate */
#define _AF_SRATE_CODEC (8012.8210513)

#endif

// file: byteorder.h
/*
	Audio File Library
	Copyright (C) 1998-1999, 2010-2011, Michael Pruett <michael@68k.org>

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

#ifndef BYTEORDER_H
#define BYTEORDER_H


#include <stdint.h>

#if WORDS_BIGENDIAN
	#define _AF_BYTEORDER_NATIVE (AF_BYTEORDER_BIGENDIAN)
#else
	#define _AF_BYTEORDER_NATIVE (AF_BYTEORDER_LITTLEENDIAN)
#endif

inline uint16_t _af_byteswap_int16 (uint16_t x)
{
	return (x >> 8) | (x << 8);
}

inline uint32_t _af_byteswap_int32 (uint32_t x)
{
	return ((x & 0x000000ffU) << 24) |
		((x & 0x0000ff00U) << 8) |
		((x & 0x00ff0000U) >> 8) |
		((x & 0xff000000U) >> 24);
}

inline uint64_t _af_byteswap_int64 (uint64_t x)
{
	return ((x & 0x00000000000000ffULL) << 56) |
		((x & 0x000000000000ff00ULL) << 40) |
		((x & 0x0000000000ff0000ULL) << 24) |
		((x & 0x00000000ff000000ULL) << 8) |
		((x & 0x000000ff00000000ULL) >> 8) |
		((x & 0x0000ff0000000000ULL) >> 24) |
		((x & 0x00ff000000000000ULL) >> 40) |
		((x & 0xff00000000000000ULL) >> 56);
}

inline float _af_byteswap_float32 (float x)
{
	union
	{
		uint32_t i;
		float f;
	} u;
	u.f = x;
	u.i = _af_byteswap_int32(u.i);
	return u.f;
}

inline double _af_byteswap_float64 (double x)
{
	union
	{
		uint64_t i;
		double f;
	} u;
	u.f = x;
	u.i = _af_byteswap_int64(u.i);
	return u.f;
}

inline uint64_t byteswap(uint64_t value) { return _af_byteswap_int64(value); }
inline int64_t byteswap(int64_t value) { return _af_byteswap_int64(value); }
inline uint32_t byteswap(uint32_t value) { return _af_byteswap_int32(value); }
inline int32_t byteswap(int32_t value) { return _af_byteswap_int32(value); }
inline uint16_t byteswap(uint16_t value) { return _af_byteswap_int16(value); }
inline int16_t byteswap(int16_t value) { return _af_byteswap_int16(value); }

inline double byteswap(double value) { return _af_byteswap_float64(value); }
inline float byteswap(float value) { return _af_byteswap_float32(value); }

template <typename T>
T bigToHost(T value)
{
	return _AF_BYTEORDER_NATIVE == AF_BYTEORDER_BIGENDIAN ? value : byteswap(value);
}

template <typename T>
T littleToHost(T value)
{
	return _AF_BYTEORDER_NATIVE == AF_BYTEORDER_LITTLEENDIAN ? value : byteswap(value);
}

template <typename T>
T hostToBig(T value)
{
	return _AF_BYTEORDER_NATIVE == AF_BYTEORDER_BIGENDIAN ? value : byteswap(value);
}

template <typename T>
T hostToLittle(T value)
{
	return _AF_BYTEORDER_NATIVE == AF_BYTEORDER_LITTLEENDIAN ? value : byteswap(value);
}

#endif

// file: AudioFormat.h
/*
	Audio File Library
	Copyright (C) 2010, Michael Pruett <michael@68k.org>

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

#ifndef AUDIOFORMAT_H
#define AUDIOFORMAT_H


#include <sys/types.h>
#include <string>

struct PCMInfo
{
	double slope, intercept, minClip, maxClip;
};

struct AudioFormat
{
	double sampleRate;		/* sampling rate in Hz */
	int sampleFormat;		/* AF_SAMPFMT_... */
	int sampleWidth;		/* sample width in bits */
	int byteOrder;		/* AF_BYTEORDER_... */

	PCMInfo pcm;		/* parameters of PCM data */

	int channelCount;		/* number of channels */

	int compressionType;	/* AF_COMPRESSION_... */
	AUpvlist compressionParams;	/* NULL if no compression */

	bool packed : 1;

	size_t framesPerPacket;
	size_t bytesPerPacket;

	size_t bytesPerSample(bool stretch3to4) const;
	size_t bytesPerFrame(bool stretch3to4) const;
	size_t bytesPerSample() const;
	size_t bytesPerFrame() const;
	bool isInteger() const;
	bool isSigned() const;
	bool isUnsigned() const;
	bool isFloat() const;
	bool isCompressed() const;
	bool isUncompressed() const;
	bool isPacked() const { return packed; }
	bool isByteOrderSignificant() const { return sampleWidth > 8; }

	void computeBytesPerPacketPCM();

	std::string description() const;
};

#endif

// file: debug.h
/*
	Audio File Library
	Copyright (C) 1998-2000, Michael Pruett <michael@68k.org>

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

/*
	debug.h

	This header file declares debugging functions for the Audio
	File Library.
*/

#ifndef DEBUG_H
#define DEBUG_H

#include <stdint.h>

void _af_print_filehandle (AFfilehandle filehandle);
void _af_print_tracks (AFfilehandle filehandle);
void _af_print_channel_matrix (double *matrix, int fchans, int vchans);
void _af_print_pvlist (AUpvlist list);

void _af_print_audioformat (AudioFormat *format);
void _af_print_frame (AFframecount frameno, double *frame, int nchannels,
	char *formatstring, int numberwidth,
	double slope, double intercept, double minclip, double maxclip);

#endif

// file: util.h
/*
	Audio File Library
	Copyright (C) 1998-2000, Michael Pruett <michael@68k.org>

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

/*
	util.h

	This file contains some general utility functions for the Audio
	File Library.
*/

#ifndef UTIL_H
#define UTIL_H

#include <stdint.h>
#include <stdlib.h>


struct AudioFormat;

bool _af_filesetup_ok (AFfilesetup setup);
bool _af_filehandle_ok (AFfilehandle file);

void *_af_malloc (size_t size);
void *_af_realloc (void *ptr, size_t size);
void *_af_calloc (size_t nmemb, size_t size);
char *_af_strdup (const char *s);

AUpvlist _af_pv_long (long val);
AUpvlist _af_pv_double (double val);
AUpvlist _af_pv_pointer (void *val);

bool _af_pv_getlong (AUpvlist pvlist, int param, long *l);
bool _af_pv_getdouble (AUpvlist pvlist, int param, double *d);
bool _af_pv_getptr (AUpvlist pvlist, int param, void **v);

bool _af_unique_ids (const int *ids, int nids, const char *idname, int iderr);

float _af_format_frame_size (const AudioFormat *format, bool stretch3to4);
int _af_format_frame_size_uncompressed (const AudioFormat *format, bool stretch3to4);
float _af_format_sample_size (const AudioFormat *format, bool stretch3to4);
int _af_format_sample_size_uncompressed (const AudioFormat *format, bool stretch3to4);

status _af_set_sample_format (AudioFormat *f, int sampleFormat, int sampleWidth);

#endif

// file: units.h
/*
	Audio File Library
	Copyright (C) 2000, Michael Pruett <michael@68k.org>

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

/*
	units.h

	This file defines the internal Unit and CompressionUnit
	structures for the Audio File Library.
*/

#ifndef UNIT_H
#define UNIT_H


struct AudioFormat;
class FileModule;

struct Unit
{
	int	fileFormat;	/* AF_FILEFMT_... */
	const char *name;		/* a 2-3 word name of the file format */
	const char *description;	/* a more descriptive name for the format */
	const char *label;		/* a 4-character label for the format */
	bool implemented;	/* if implemented */

	AFfilesetup (*completesetup) (AFfilesetup setup);
	bool (*recognize) (File *fh);

	int defaultSampleFormat;
	int defaultSampleWidth;

	int compressionTypeCount;
	const int *compressionTypes;

	int markerCount;

	int instrumentCount;
	int loopPerInstrumentCount;

	int instrumentParameterCount;
	const InstParamInfo *instrumentParameters;
};

struct CompressionUnit
{
	int	compressionID;	/* AF_COMPRESSION_... */
	bool implemented;
	const char *label;		/* 4-character (approximately) label */
	const char *shortname;	/* short name in English */
	const char *name;		/* long name in English */
	double squishFactor;	/* compression ratio */
	int	nativeSampleFormat;	/* AF_SAMPFMT_... */
	int	nativeSampleWidth;	/* sample width in bits */
	bool	needsRebuffer;	/* if there are chunk boundary requirements */
	bool	multiple_of;	/* can accept any multiple of chunksize */
	bool	(*fmtok) (AudioFormat *format);

	FileModule *(*initcompress) (Track *track, File *fh,
		bool seekok, bool headerless, AFframecount *chunkframes);
	FileModule *(*initdecompress) (Track *track, File *fh,
		bool seekok, bool headerless, AFframecount *chunkframes);
};

#define _AF_NUM_UNITS 17
#define _AF_NUM_COMPRESSION 7

extern const Unit _af_units[_AF_NUM_UNITS];
extern const CompressionUnit _af_compression[_AF_NUM_COMPRESSION];

#endif /* UNIT_H */

// file: UUID.h
/*
	Copyright (C) 2011, Michael Pruett. All rights reserved.

	Redistribution and use in source and binary forms, with or without
	modification, are permitted provided that the following conditions
	are met:

	1. Redistributions of source code must retain the above copyright
	notice, this list of conditions and the following disclaimer.

	2. Redistributions in binary form must reproduce the above copyright
	notice, this list of conditions and the following disclaimer in the
	documentation and/or other materials provided with the distribution.

	3. The name of the author may not be used to endorse or promote products
	derived from this software without specific prior written permission.

	THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
	IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
	OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
	IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
	INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
	NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
	DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
	THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
	(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
	THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

#ifndef UUID_H
#define UUID_H

#include <stdint.h>
#include <string>

struct UUID
{
	uint8_t data[16];

	bool operator==(const UUID &) const;
	bool operator!=(const UUID &) const;
	std::string name() const;
};

#endif

// file: Shared.h
/*
	Copyright (C) 2010, Michael Pruett. All rights reserved.

	Redistribution and use in source and binary forms, with or without
	modification, are permitted provided that the following conditions
	are met:

	1. Redistributions of source code must retain the above copyright
	notice, this list of conditions and the following disclaimer.

	2. Redistributions in binary form must reproduce the above copyright
	notice, this list of conditions and the following disclaimer in the
	documentation and/or other materials provided with the distribution.

	3. The name of the author may not be used to endorse or promote products
	derived from this software without specific prior written permission.

	THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
	IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
	OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
	IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
	INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
	NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
	DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
	THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
	(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
	THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

#ifndef SHARED_H
#define SHARED_H

template <typename T>
class Shared
{
public:
	Shared() : m_refCount(0)
	{
	}
	void retain() { m_refCount++; }
	void release() { if (--m_refCount == 0) delete static_cast<T *>(this); }

protected:
	~Shared()
	{
	}

private:
	int m_refCount;

	Shared(const Shared &);
	Shared &operator =(const Shared &);
};

template <typename T>
class SharedPtr
{
public:
	SharedPtr() : m_ptr(0)
	{
	}
	SharedPtr(T *ptr) : m_ptr(ptr)
	{
		if (m_ptr) m_ptr->retain();
	}
	SharedPtr(const SharedPtr &p) : m_ptr(p.m_ptr)
	{
		if (m_ptr) m_ptr->retain();
	}
	~SharedPtr()
	{
		if (T *p = m_ptr) p->release();
	}

	SharedPtr &operator =(T *ptr)
	{
		if (m_ptr != ptr)
		{
			if (ptr) ptr->retain();
			if (m_ptr) m_ptr->release();
			m_ptr = ptr;
		}
		return *this;
	}
	SharedPtr &operator =(const SharedPtr &p)
	{
		if (m_ptr != p.m_ptr)
		{
			if (p.m_ptr) p.m_ptr->retain();
			if (m_ptr) m_ptr->release();
			m_ptr = p.m_ptr;
		}
		return *this;
	}

	T *get() const { return m_ptr; }
	T &operator *() const { return *m_ptr; }
	T *operator ->() const { return m_ptr; }

	typedef T *SharedPtr::*UnspecifiedBoolType;
	operator UnspecifiedBoolType() const { return m_ptr ? &SharedPtr::m_ptr : 0; }

	bool operator !() const { return !m_ptr; }

private:
	T *m_ptr;
};

#endif

// file: Buffer.h
/*
	Audio File Library
	Copyright (C) 2013 Michael Pruett <michael@68k.org>

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

#ifndef Buffer_h
#define Buffer_h


#include <sys/types.h>

class Buffer : public Shared<Buffer>
{
public:
	Buffer();
	Buffer(size_t size);
	Buffer(const void *data, size_t size);
	~Buffer();

	void *data() { return m_data; }
	const void *data() const { return m_data; }

	size_t size() const { return m_size; }

private:
	void *m_data;
	size_t m_size;
};

#endif

// file: File.h
/*
	Copyright (C) 2010, Michael Pruett. All rights reserved.

	Redistribution and use in source and binary forms, with or without
	modification, are permitted provided that the following conditions
	are met:

	1. Redistributions of source code must retain the above copyright
	notice, this list of conditions and the following disclaimer.

	2. Redistributions in binary form must reproduce the above copyright
	notice, this list of conditions and the following disclaimer in the
	documentation and/or other materials provided with the distribution.

	3. The name of the author may not be used to endorse or promote products
	derived from this software without specific prior written permission.

	THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
	IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
	OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
	IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
	INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
	NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
	DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
	THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
	(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
	THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

#ifndef FILE_H
#define FILE_H

#include <sys/types.h>

typedef struct _AFvirtualfile AFvirtualfile;

class File : public Shared<File>
{
public:
	enum AccessMode
	{
		ReadAccess,
		WriteAccess
	};

	enum SeekOrigin
	{
		SeekFromBeginning,
		SeekFromCurrent,
		SeekFromEnd
	};

	static File *open(const char *path, AccessMode mode);
	static File *create(int fd, AccessMode mode);
	static File *create(AFvirtualfile *vf, AccessMode mode);

	virtual ~File();
	virtual int close() = 0;
	virtual ssize_t read(void *data, size_t nbytes) = 0;
	virtual ssize_t write(const void *data, size_t nbytes) = 0;
	virtual off_t length() = 0;
	virtual off_t seek(off_t offset, SeekOrigin origin) = 0;
	virtual off_t tell() = 0;

	bool canSeek();

	AccessMode accessMode() const { return m_accessMode; }

private:
	AccessMode m_accessMode;

protected:
	File(AccessMode mode) : m_accessMode(mode) { }
};

#endif // FILE_H

// file: FileHandle.h
/*
	Audio File Library
	Copyright (C) 2010-2011, Michael Pruett <michael@68k.org>

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

#ifndef FILEHANDLE_H
#define FILEHANDLE_H

#include <stdint.h>

class File;
class Tag;
struct Instrument;
struct Miscellaneous;
struct Track;

struct _AFfilehandle
{
	static _AFfilehandle *create(int fileFormat);

	int m_valid;	// _AF_VALID_FILEHANDLE
	int m_access;	// _AF_READ_ACCESS or _AF_WRITE_ACCESS

	bool m_seekok;

	File *m_fh;

	char *m_fileName;

	int m_fileFormat;

	int m_trackCount;
	Track *m_tracks;

	int m_instrumentCount;
	Instrument *m_instruments;

	int m_miscellaneousCount;
	Miscellaneous *m_miscellaneous;

private:
	int m_formatByteOrder;

	status copyTracksFromSetup(AFfilesetup setup);
	status copyInstrumentsFromSetup(AFfilesetup setup);
	status copyMiscellaneousFromSetup(AFfilesetup setup);

public:
	virtual ~_AFfilehandle();

	virtual int getVersion() { return 0; }
	virtual status readInit(AFfilesetup) = 0;
	virtual status writeInit(AFfilesetup) = 0;
	virtual status update() = 0;
	virtual bool isInstrumentParameterValid(AUpvlist, int) { return false; }

	bool checkCanRead();
	bool checkCanWrite();

	Track *allocateTrack();
	Track *getTrack(int trackID = AF_DEFAULT_TRACK);
	Instrument *getInstrument(int instrumentID);
	Miscellaneous *getMiscellaneous(int miscellaneousID);

protected:
	_AFfilehandle();

	status initFromSetup(AFfilesetup setup);

	void setFormatByteOrder(int byteOrder) { m_formatByteOrder = byteOrder; }

	bool readU8(uint8_t *);
	bool readS8(int8_t *);
	bool readU16(uint16_t *);
	bool readS16(int16_t *);
	bool readU32(uint32_t *);
	bool readS32(int32_t *);
	bool readU64(uint64_t *);
	bool readS64(int64_t *);
	bool readFloat(float *);
	bool readDouble(double *);

	bool writeU8(const uint8_t *);
	bool writeS8(const int8_t *);
	bool writeU16(const uint16_t *);
	bool writeS16(const int16_t *);
	bool writeU32(const uint32_t *);
	bool writeS32(const int32_t *);
	bool writeU64(const uint64_t *);
	bool writeS64(const int64_t *);
	bool writeFloat(const float *);
	bool writeDouble(const double *);

	bool readTag(Tag *t);
	bool writeTag(const Tag *t);
};

#endif

// file: Instrument.h
/*
	Audio File Library
	Copyright (C) 2000, Michael Pruett <michael@68k.org>

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

/*
	Instrument.h

	This file declares routines for dealing with instruments.
*/

#ifndef INSTRUMENT_H
#define INSTRUMENT_H


struct LoopSetup;
struct Loop;

struct InstrumentSetup
{
	int	id;

	int loopCount;
	LoopSetup *loops;

	bool loopSet;

	bool allocateLoops(int count);
	void freeLoops();
};

struct Instrument
{
	int id;

	int loopCount;
	Loop *loops;

	AFPVu *values;

	Loop *getLoop(int loopID);
};

void _af_instparam_get (AFfilehandle file, int instid, AUpvlist pvlist,
	int npv, bool forceLong);

void _af_instparam_set (AFfilehandle file, int instid, AUpvlist pvlist,
	int npv);

int _af_instparam_index_from_id (int fileFormat, int id);

#endif /* INSTRUMENT_H */

// file: Track.h
/*
	Audio File Library
	Copyright (C) 2000, Silicon Graphics, Inc.

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

/*
	track.h
*/

#ifndef TRACK_H
#define TRACK_H


class ModuleState;
class PacketTable;
struct Marker;
struct MarkerSetup;

struct TrackSetup
{
	int id;

	AudioFormat f;

	bool rateSet, sampleFormatSet, sampleWidthSet, byteOrderSet,
		channelCountSet, compressionSet, aesDataSet, markersSet,
		dataOffsetSet, frameCountSet;

	int markerCount;
	MarkerSetup *markers;

	AFfileoffset dataOffset;
	AFframecount frameCount;
};

struct Track
{
	Track();
	~Track();

	int	id;	/* usually AF_DEFAULT_TRACKID */

	AudioFormat f, v;	/* file and virtual audio formats */

	SharedPtr<PacketTable> m_packetTable;

	double *channelMatrix;

	int markerCount;
	Marker *markers;

	bool hasAESData;	/* Is AES nonaudio data present? */
	unsigned char aesData[24];	/* AES nonaudio data */

	AFframecount totalfframes;		/* frameCount */
	AFframecount nextfframe;		/* currentFrame */
	AFframecount frames2ignore;
	AFfileoffset fpos_first_frame;	/* dataStart */
	AFfileoffset fpos_next_frame;
	AFfileoffset fpos_after_data;
	AFframecount totalvframes;
	AFframecount nextvframe;
	AFfileoffset data_size;		/* trackBytes */

	SharedPtr<ModuleState> ms;

	double taper, dynamic_range;
	bool ratecvt_filter_params_set;

	bool filemodhappy;

	void print();

	Marker *getMarker(int markerID);
	status copyMarkers(TrackSetup *setup);

	void computeTotalFileFrames();
};

#endif

// file: Marker.h
/*
	Audio File Library
	Copyright (C) 2000, Silicon Graphics, Inc.

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

#ifndef MARKER_H
#define MARKER_H

struct MarkerSetup
{
	int id;
	char *name, *comment;
};

struct Marker
{
	short id;
	unsigned long position;
	char *name;
	char *comment;
};

struct Track;

Marker *_af_marker_new (int count);
Marker *_af_marker_find_by_id (Track *track, int id);

#endif /* MARKER_H */

// file: Setup.h
/*
	Audio File Library
	Copyright (C) 1998-2000, Michael Pruett <michael@68k.org>

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

#ifndef SETUP_H
#define SETUP_H


struct InstrumentSetup;
struct MiscellaneousSetup;
struct TrackSetup;

struct _AFfilesetup
{
	int	valid;

	int fileFormat;

	bool trackSet, instrumentSet, miscellaneousSet;

	int trackCount;
	TrackSetup *tracks;

	int instrumentCount;
	InstrumentSetup *instruments;

	int miscellaneousCount;
	MiscellaneousSetup *miscellaneous;

	TrackSetup *getTrack(int trackID = AF_DEFAULT_TRACK);
	InstrumentSetup *getInstrument(int instrumentID);
	MiscellaneousSetup *getMiscellaneous(int miscellaneousID);
};

void _af_setup_free_markers (AFfilesetup setup, int trackno);
void _af_setup_free_tracks (AFfilesetup setup);
void _af_setup_free_instruments (AFfilesetup setup);

AFfilesetup _af_filesetup_copy (const _AFfilesetup *setup,
	const _AFfilesetup *defaultSetup, bool copyMarks);

InstrumentSetup *_af_instsetup_new (int count);

#endif

// file: Tag.h
/*
	Audio File Library
	Copyright (C) 2011, Michael Pruett <michael@68k.org>

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

#ifndef TAG_H
#define TAG_H

#include <assert.h>
#include <stdint.h>
#include <string.h>
#include <string>

class Tag
{
public:
	Tag() : m_value(0) { }
	Tag(uint32_t value) : m_value(value) { }
	Tag(const char *s)
	{
		assert(strlen(s) == 4);
		memcpy(&m_value, s, 4);
	}

	uint32_t value() const { return m_value; }

	bool operator==(const Tag &t) const { return m_value == t.m_value; }
	bool operator!=(const Tag &t) const { return m_value != t.m_value; }

	std::string name() const
	{
		char s[5];
		memcpy(s, &m_value, 4);
		s[4] = '\0';
		return std::string(s);
	}

private:
	uint32_t m_value;
};

#endif

// file: PacketTable.h
/*
	Audio File Library
	Copyright (C) 2013 Michael Pruett <michael@68k.org>

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

#ifndef PacketTable_h
#define PacketTable_h


#include <audiofile.h>

#include <stdint.h>
#include <sys/types.h>
#include <vector>

class PacketTable : public Shared<PacketTable>
{
public:
	PacketTable();
	PacketTable(int64_t numValidFrames,
		int32_t primingFrames,
		int32_t remainderFrames);
	~PacketTable();

	size_t numPackets() const { return m_bytesPerPacket.size(); }
	int64_t numValidFrames() const { return m_numValidFrames; }
	void setNumValidFrames(int64_t numValidFrames);
	int32_t primingFrames() const { return m_primingFrames; }
	void setPrimingFrames(int32_t primingFrames);
	int32_t remainderFrames() const { return m_remainderFrames; }
	void setRemainderFrames(int32_t remainderFrames);

	void append(size_t bytesPerPacket);
	size_t bytesPerPacket(size_t packet) const { return m_bytesPerPacket[packet]; }
	AFfileoffset startOfPacket(size_t packet) const;

private:
	int64_t m_numValidFrames;
	int32_t m_primingFrames;
	int32_t m_remainderFrames;

	std::vector<size_t> m_bytesPerPacket;
};

#endif

// file: pcm.h
/*
	Audio File Library
	Copyright (C) 2000, Silicon Graphics, Inc.

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

/*
	pcm.h

	This file defines various constants for PCM mapping.
*/

#ifndef PCM_H
#define PCM_H

/*
	SLOPE_INTN = 2^(N-1)
*/
#define SLOPE_INT8 (128.0)
#define SLOPE_INT16 (32768.0)
#define SLOPE_INT24 (8388608.0)
#define SLOPE_INT32 (2147483648.0)

/*
	INTERCEPT_U_INTN = 2^(N-1)
*/
#define INTERCEPT_U_INT8 (128.0)
#define INTERCEPT_U_INT16 (32768.0)
#define INTERCEPT_U_INT24 (8388608.0)
#define INTERCEPT_U_INT32 (2147483648.0)

/*
	MIN_INTN = -(2^(N-1))
*/
#define MIN_INT8 (-128.0)
#define MIN_INT16 (-32768.0)
#define MIN_INT24 (-8388608.0)
#define MIN_INT32 (-2147483648.0)

/*
	MAX_INTN = 2^(N-1) - 1
*/
#define MAX_INT8 127.0
#define MAX_INT16 32767.0
#define MAX_INT24 8388607.0
#define MAX_INT32 2147483647.0

/*
	MAX_U_INTN = 2^N - 1
*/
#define MAX_U_INT8 255.0
#define MAX_U_INT16 65535.0
#define MAX_U_INT24 16777215.0
#define MAX_U_INT32 4294967295.0

extern const PCMInfo _af_default_signed_integer_pcm_mappings[];
extern const PCMInfo _af_default_unsigned_integer_pcm_mappings[];
extern const PCMInfo _af_default_float_pcm_mapping;
extern const PCMInfo _af_default_double_pcm_mapping;

#endif

// file: g711.h
/*
 * This source code is a product of Sun Microsystems, Inc. and is provided
 * for unrestricted use.  Users may copy or modify this source code without
 * charge.
 *
 * SUN SOURCE CODE IS PROVIDED AS IS WITH NO WARRANTIES OF ANY KIND INCLUDING
 * THE WARRANTIES OF DESIGN, MERCHANTIBILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE, OR ARISING FROM A COURSE OF DEALING, USAGE OR TRADE PRACTICE.
 *
 * Sun source code is provided with no support and without any obligation on
 * the part of Sun Microsystems, Inc. to assist in its use, correction,
 * modification or enhancement.
 *
 * SUN MICROSYSTEMS, INC. SHALL HAVE NO LIABILITY WITH RESPECT TO THE
 * INFRINGEMENT OF COPYRIGHTS, TRADE SECRETS OR ANY PATENTS BY THIS SOFTWARE
 * OR ANY PART THEREOF.
 *
 * In no event will Sun Microsystems, Inc. be liable for any lost revenue
 * or profits or other special, indirect and consequential damages, even if
 * Sun has been advised of the possibility of such damages.
 *
 * Sun Microsystems, Inc.
 * 2550 Garcia Avenue
 * Mountain View, California  94043
 */

/*
 * g711.h
 *
 * u-law, A-law and linear PCM conversions.
 */

#ifndef G711_H
#define G711_H

#ifdef __cplusplus
extern "C" {
#endif

/*
 * linear2alaw() - Convert a 16-bit linear PCM value to 8-bit A-law
 *
 * linear2alaw() accepts an 16-bit integer and encodes it as A-law data.
 *
 *		Linear Input Code	Compressed Code
 *	------------------------	---------------
 *	0000000wxyza			000wxyz
 *	0000001wxyza			001wxyz
 *	000001wxyzab			010wxyz
 *	00001wxyzabc			011wxyz
 *	0001wxyzabcd			100wxyz
 *	001wxyzabcde			101wxyz
 *	01wxyzabcdef			110wxyz
 *	1wxyzabcdefg			111wxyz
 *
 * For further information see John C. Bellamy's Digital Telephony, 1982,
 * John Wiley & Sons, pps 98-111 and 472-476.
 */

/* pcm_val is 2's complement (16-bit range) */
unsigned char _af_linear2alaw (int pcm_val);

/*
 * alaw2linear() - Convert an A-law value to 16-bit linear PCM
 *
 */

int _af_alaw2linear (unsigned char a_val);

/*
 * linear2ulaw() - Convert a linear PCM value to u-law
 *
 * In order to simplify the encoding process, the original linear magnitude
 * is biased by adding 33 which shifts the encoding range from (0 - 8158) to
 * (33 - 8191). The result can be seen in the following encoding table:
 *
 *	Biased Linear Input Code	Compressed Code
 *	------------------------	---------------
 *	00000001wxyza			000wxyz
 *	0000001wxyzab			001wxyz
 *	000001wxyzabc			010wxyz
 *	00001wxyzabcd			011wxyz
 *	0001wxyzabcde			100wxyz
 *	001wxyzabcdef			101wxyz
 *	01wxyzabcdefg			110wxyz
 *	1wxyzabcdefgh			111wxyz
 *
 * Each biased linear code has a leading 1 which identifies the segment
 * number. The value of the segment number is equal to 7 minus the number
 * of leading 0's. The quantization interval is directly available as the
 * four bits wxyz.  * The trailing bits (a - h) are ignored.
 *
 * Ordinarily the complement of the resulting code word is used for
 * transmission, and so the code word is complemented before it is returned.
 *
 * For further information see John C. Bellamy's Digital Telephony, 1982,
 * John Wiley & Sons, pps 98-111 and 472-476.
 */

/* pcm_val is 2's complement (16-bit range) */
unsigned char _af_linear2ulaw (int pcm_val);

/*
 * ulaw2linear() - Convert a u-law value to 16-bit linear PCM
 *
 * First, a biased linear code is derived from the code word. An unbiased
 * output can then be obtained by subtracting 33 from the biased code.
 *
 * Note that this function expects to be passed the complement of the
 * original code word. This is in keeping with ISDN conventions.
 */

int _af_ulaw2linear (unsigned char u_val);

#ifdef __cplusplus
}
#endif

#endif /* G711_H */

// file: af_vfs.h
/*
	Audio File Library
	Copyright (C) 1999, Elliot Lee <sopwith@redhat.com>

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

/*
	af_vfs.h

	Virtual file operations for the Audio File Library.
*/

#ifndef AUDIOFILE_VFS_H
#define AUDIOFILE_VFS_H

#include <audiofile.h>
#include <sys/types.h>

#ifdef __cplusplus
extern "C" {
#endif

#if (defined(__GNUC__) && __GNUC__ >= 4) || defined(__clang__)
#define AFAPI __attribute__((visibility("default")))
#else
#define AFAPI
#endif

struct _AFvirtualfile
{
	ssize_t (*read) (AFvirtualfile *vfile, void *data, size_t nbytes);
	AFfileoffset (*length) (AFvirtualfile *vfile);
	ssize_t (*write) (AFvirtualfile *vfile, const void *data, size_t nbytes);
	void (*destroy) (AFvirtualfile *vfile);
	AFfileoffset (*seek) (AFvirtualfile *vfile, AFfileoffset offset, int is_relative);
	AFfileoffset (*tell) (AFvirtualfile *vfile);

	void *closure;
};

AFAPI AFvirtualfile *af_virtual_file_new (void);
AFAPI void af_virtual_file_destroy (AFvirtualfile *vfile);

#undef AFAPI

#ifdef __cplusplus
}
#endif

#endif

// file: Raw.h
/*
	Audio File Library
	Copyright (C) 2000, Silicon Graphics, Inc.

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

/*
	Raw.h
*/

#ifndef RAW_H
#define RAW_H


#define _AF_RAW_NUM_COMPTYPES 2
extern const int _af_raw_compression_types[_AF_RAW_NUM_COMPTYPES];

class RawFile : public _AFfilehandle
{
public:
	static bool recognize(File *fh);
	static AFfilesetup completeSetup(AFfilesetup);

	status readInit(AFfilesetup setup) OVERRIDE;
	status writeInit(AFfilesetup setup) OVERRIDE;
	status update() OVERRIDE;
};

#endif

// file: WAVE.h
/*
	Audio File Library
	Copyright (C) 1998-2000, 2003, 2010-2012, Michael Pruett <michael@68k.org>
	Copyright (C) 2002-2003, Davy Durham
	Copyright (C) 2000-2001, Silicon Graphics, Inc.

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

/*
	WAVE.h

	This file contains structures and constants related to the RIFF
	WAVE sound file format.
*/

#ifndef WAVE_H
#define WAVE_H

#include <stdint.h>

#define _AF_WAVE_NUM_INSTPARAMS 7
extern const InstParamInfo _af_wave_inst_params[_AF_WAVE_NUM_INSTPARAMS];
#define _AF_WAVE_NUM_COMPTYPES 4
extern const int _af_wave_compression_types[_AF_WAVE_NUM_COMPTYPES];

struct UUID;

class WAVEFile : public _AFfilehandle
{
public:
	static bool recognize(File *fh);
	static AFfilesetup completeSetup(AFfilesetup);

	WAVEFile();

	status readInit(AFfilesetup) OVERRIDE;
	status writeInit(AFfilesetup) OVERRIDE;

	status update() OVERRIDE;

	bool isInstrumentParameterValid(AUpvlist, int) OVERRIDE;

private:
	AFfileoffset m_factOffset;	// start of fact (frame count) chunk
	AFfileoffset m_miscellaneousOffset;
	AFfileoffset m_markOffset;
	AFfileoffset m_dataSizeOffset;

	/*
		The index into the coefficient array is of type
		uint8_t, so we can safely limit msadpcmCoefficients to
		be 256 coefficient pairs.
	*/
	int m_msadpcmNumCoefficients;
	int16_t m_msadpcmCoefficients[256][2];

	status parseFrameCount(const Tag &type, uint32_t size);
	status parseFormat(const Tag &type, uint32_t size);
	status parseData(const Tag &type, uint32_t size);
	status parsePlayList(const Tag &type, uint32_t size);
	status parseCues(const Tag &type, uint32_t size);
	status parseADTLSubChunk(const Tag &type, uint32_t size);
	status parseINFOSubChunk(const Tag &type, uint32_t size);
	status parseList(const Tag &type, uint32_t size);
	status parseInstrument(const Tag &type, uint32_t size);

	status writeFormat();
	status writeFrameCount();
	status writeMiscellaneous();
	status writeCues();
	status writeData();

	bool readUUID(UUID *g);
	bool writeUUID(const UUID *g);

	bool writeZString(const char *);
	size_t zStringLength(const char *);

	void initCompressionParams();
	void initIMACompressionParams();
	void initMSADPCMCompressionParams();
};

#endif

// file: SampleVision.h
/*
	Audio File Library
	Copyright (C) 2012, Michael Pruett <michael@68k.org>

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

#ifndef SAMPLE_VISION_H
#define SAMPLE_VISION_H


class SampleVisionFile : public _AFfilehandle
{
public:
	SampleVisionFile();
	virtual ~SampleVisionFile();

	static bool recognize(File *fh);

	static AFfilesetup completeSetup(AFfilesetup);

	status readInit(AFfilesetup) OVERRIDE;
	status writeInit(AFfilesetup) OVERRIDE;

	status update() OVERRIDE;

private:
	AFfileoffset m_frameCountOffset;

	status parseLoops();
	status parseMarkers();
	status writeTrailer();
	status writeLoops();
	status writeMarkers();

	void addMiscellaneous(int type, const char *data);
};

#endif

// file: modules/Module.h
/*
	Audio File Library
	Copyright (C) 2000, Silicon Graphics, Inc.
	Copyright (C) 2010, Michael Pruett <michael@68k.org>

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

#ifndef MODULE_H
#define MODULE_H


#include <vector>

enum FormatCode
{
	kUndefined = -1,
	kInt8,
	kInt16,
	kInt24,
	kInt32,
	kFloat,
	kDouble,
};

class Chunk : public Shared<Chunk>
{
public:
	void *buffer;
	size_t frameCount;
	AudioFormat f;
	bool ownsMemory;

	Chunk() : buffer(NULL), frameCount(0), ownsMemory(false) { }
	~Chunk()
	{
		deallocate();
	}
	void allocate(size_t capacity)
	{
		deallocate();
		ownsMemory = true;
		buffer = ::operator new(capacity);
	}
	void deallocate()
	{
		if (ownsMemory)
			::operator delete(buffer);
		ownsMemory = false;
		buffer = NULL;
	}
};

class Module : public Shared<Module>
{
public:
	Module();
	virtual ~Module();

	void setSink(Module *);
	void setSource(Module *);
	Chunk *inChunk() const { return m_inChunk.get(); }
	void setInChunk(Chunk *chunk) { m_inChunk = chunk; }
	Chunk *outChunk() const { return m_outChunk.get(); }
	void setOutChunk(Chunk *chunk) { m_outChunk = chunk; }

	virtual const char *name() const;
	/*
		Set format of m_outChunk based on how this module transforms m_inChunk.
	*/
	virtual void describe();
	/*
		Set frame count of m_inChunk to the maximum number of frames needed to
		produce frame count of m_outChunk.
	*/
	virtual void maxPull();
	/*
		Set frame count of m_outChunk to the maximum number of frames needed to
		produce frame count of m_inChunk.
	*/
	virtual void maxPush();
	virtual void runPull();
	virtual void reset1() { }
	virtual void reset2() { }
	virtual void runPush();
	virtual void sync1() { }
	virtual void sync2() { }

protected:
	SharedPtr<Chunk> m_inChunk, m_outChunk;
	union
	{
		Module *m_sink;
		Module *m_source;
	};

	void pull(size_t frames);
	void push(size_t frames);
};

/*
	_AF_ATOMIC_NVFRAMES is NOT the maximum number of frames a module
	can be requested to produce.

	This IS the maximum number of virtual (user) frames that will
	be produced or processed per run of the modules.

	Modules can be requested more frames than this because of rate
	conversion and rebuffering.
*/

#define _AF_ATOMIC_NVFRAMES 1024

#endif // MODULE_H

// file: modules/ModuleState.h
/*
	Audio File Library
	Copyright (C) 2000, Silicon Graphics, Inc.
	Copyright (C) 2010, Michael Pruett <michael@68k.org>

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

#ifndef MODULESTATE_H
#define MODULESTATE_H

#include <vector>

class FileModule;
class Module;

class ModuleState : public Shared<ModuleState>
{
public:
	ModuleState();
	virtual ~ModuleState();

	bool isDirty() const { return m_isDirty; }
	void setDirty() { m_isDirty = true; }
	status init(AFfilehandle file, Track *track);
	status setup(AFfilehandle file, Track *track);
	status reset(AFfilehandle file, Track *track);
	status sync(AFfilehandle file, Track *track);

	int numModules() const { return m_modules.size(); }
	const std::vector<SharedPtr<Module> > &modules() const;
	const std::vector<SharedPtr<Chunk> > &chunks() const;

	bool mustUseAtomicNVFrames() const { return true; }

	void print();

	bool fileModuleHandlesSeeking() const;

private:
	std::vector<SharedPtr<Module> > m_modules;
	std::vector<SharedPtr<Chunk> > m_chunks;
	bool m_isDirty;

	SharedPtr<FileModule> m_fileModule;
	SharedPtr<Module> m_fileRebufferModule;

	status initFileModule(AFfilehandle file, Track *track);

	status arrange(AFfilehandle file, Track *track);

	void addModule(Module *module);

	void addConvertIntToInt(FormatCode input, FormatCode output);
	void addConvertIntToFloat(FormatCode input, FormatCode output);
	void addConvertFloatToInt(FormatCode input, FormatCode output,
		const PCMInfo &inputMapping, const PCMInfo &outputMapping);
	void addConvertFloatToFloat(FormatCode input, FormatCode output);
};

#endif

// file: modules/SimpleModule.h
/*
	Audio File Library
	Copyright (C) 2010, Michael Pruett <michael@68k.org>

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

#ifndef SIMPLE_MODULE_H
#define SIMPLE_MODULE_H



#include <algorithm>
#include <cassert>
#include <climits>
#include <functional>

class SimpleModule : public Module
{
public:
	virtual void runPull() OVERRIDE;
	virtual void runPush() OVERRIDE;
	virtual void run(Chunk &inChunk, Chunk &outChunk) = 0;
};

struct SwapModule : public SimpleModule
{
public:
	virtual const char *name() const OVERRIDE { return "swap"; }
	virtual void describe() OVERRIDE
	{
		m_outChunk->f.byteOrder = m_inChunk->f.byteOrder == AF_BYTEORDER_BIGENDIAN ?
			AF_BYTEORDER_LITTLEENDIAN : AF_BYTEORDER_BIGENDIAN;
	}
	virtual void run(Chunk &inChunk, Chunk &outChunk) OVERRIDE
	{
		switch (m_inChunk->f.bytesPerSample(false))
		{
			case 2:
				run<2, int16_t>(inChunk, outChunk); break;
			case 3:
				run<3, char>(inChunk, outChunk); break;
			case 4:
				run<4, int32_t>(inChunk, outChunk); break;
			case 8:
				run<8, int64_t>(inChunk, outChunk); break;
			default:
				assert(false); break;
		}
	}

private:
	template <int N, typename T>
	void run(Chunk &inChunk, Chunk &outChunk)
	{
		int sampleCount = inChunk.f.channelCount * inChunk.frameCount;
		runSwap<N, T>(reinterpret_cast<const T *>(inChunk.buffer),
			reinterpret_cast<T *>(outChunk.buffer),
			sampleCount);
	}
	template <int N, typename T>
	void runSwap(const T *input, T *output, int sampleCount)
	{
		for (int i=0; i<sampleCount; i++)
			output[i] = byteswap(input[i]);
	}
};

template <>
inline void SwapModule::runSwap<3, char>(const char *input, char *output, int count)
{
	for (int i=0; i<count; i++)
	{
		output[3*i] = input[3*i+2];
		output[3*i+1] = input[3*i+1];
		output[3*i+2] = input[3*i];
	}
}

template <typename UnaryFunction>
void transform(const void *srcData, void *dstData, size_t count)
{
	typedef typename UnaryFunction::argument_type InputType;
	typedef typename UnaryFunction::result_type OutputType;
	const InputType *src = reinterpret_cast<const InputType *>(srcData);
	OutputType *dst = reinterpret_cast<OutputType *>(dstData);
	std::transform(src, src + count, dst, UnaryFunction());
}

template <FormatCode>
struct IntTypes;

template <>
struct IntTypes<kInt8> { typedef int8_t SignedType; typedef uint8_t UnsignedType; };
template <>
struct IntTypes<kInt16> { typedef int16_t SignedType; typedef uint16_t UnsignedType; };
template <>
struct IntTypes<kInt24> { typedef int32_t SignedType; typedef uint32_t UnsignedType; };
template <>
struct IntTypes<kInt32> { typedef int32_t SignedType; typedef uint32_t UnsignedType; };

template <FormatCode Format>
struct signConverter
{
	typedef typename IntTypes<Format>::SignedType SignedType;
	typedef typename IntTypes<Format>::UnsignedType UnsignedType;

	static const int kScaleBits = (Format + 1) * CHAR_BIT - 1;
	static const int kMaxSignedValue = (((1 << (kScaleBits - 1)) - 1) << 1) + 1;
	static const int kMinSignedValue = -kMaxSignedValue - 1;

	struct signedToUnsigned : public std::unary_function<SignedType, UnsignedType>
	{
		UnsignedType operator()(SignedType x) { return x - kMinSignedValue; }
	};

	struct unsignedToSigned : public std::unary_function<SignedType, UnsignedType>
	{
		SignedType operator()(UnsignedType x) { return x + kMinSignedValue; }
	};
};

class ConvertSign : public SimpleModule
{
public:
	ConvertSign(FormatCode format, bool fromSigned) :
		m_format(format),
		m_fromSigned(fromSigned)
	{
	}
	virtual const char *name() const OVERRIDE { return "sign"; }
	virtual void describe() OVERRIDE
	{
		const int scaleBits = m_inChunk->f.bytesPerSample(false) * CHAR_BIT;
		m_outChunk->f.sampleFormat =
			m_fromSigned ? AF_SAMPFMT_UNSIGNED : AF_SAMPFMT_TWOSCOMP;
		double shift = -(1 << (scaleBits - 1));
		if (m_fromSigned)
			shift = -shift;
		m_outChunk->f.pcm.intercept += shift;
		m_outChunk->f.pcm.minClip += shift;
		m_outChunk->f.pcm.maxClip += shift;
	}
	virtual void run(Chunk &input, Chunk &output) OVERRIDE
	{
		size_t count = input.frameCount * m_inChunk->f.channelCount;
		if (m_fromSigned)
			convertSignedToUnsigned(input.buffer, output.buffer, count);
		else
			convertUnsignedToSigned(input.buffer, output.buffer, count);
	}

private:
	FormatCode m_format;
	bool m_fromSigned;

	template <FormatCode Format>
	static void convertSignedToUnsigned(const void *src, void *dst, size_t count)
	{
		transform<typename signConverter<Format>::signedToUnsigned>(src, dst, count);
	}
	void convertSignedToUnsigned(const void *src, void *dst, size_t count)
	{
		switch (m_format)
		{
			case kInt8:
				convertSignedToUnsigned<kInt8>(src, dst, count);
				break;
			case kInt16:
				convertSignedToUnsigned<kInt16>(src, dst, count);
				break;
			case kInt24:
				convertSignedToUnsigned<kInt24>(src, dst, count);
				break;
			case kInt32:
				convertSignedToUnsigned<kInt32>(src, dst, count);
				break;
			default:
				assert(false);
		}
	}

	template <FormatCode Format>
	static void convertUnsignedToSigned(const void *src, void *dst, size_t count)
	{
		transform<typename signConverter<Format>::unsignedToSigned>(src, dst, count);
	}
	void convertUnsignedToSigned(const void *src, void *dst, size_t count)
	{
		switch (m_format)
		{
			case kInt8:
				convertUnsignedToSigned<kInt8>(src, dst, count);
				break;
			case kInt16:
				convertUnsignedToSigned<kInt16>(src, dst, count);
				break;
			case kInt24:
				convertUnsignedToSigned<kInt24>(src, dst, count);
				break;
			case kInt32:
				convertUnsignedToSigned<kInt32>(src, dst, count);
				break;
			default:
				assert(false);
		}
	}
};

struct Expand3To4Module : public SimpleModule
{
public:
	Expand3To4Module(bool isSigned) : m_isSigned(isSigned)
	{
	}
	virtual const char *name() const OVERRIDE { return "expand3to4"; }
	virtual void describe() OVERRIDE
	{
		m_outChunk->f.packed = false;
	}
	virtual void run(Chunk &inChunk, Chunk &outChunk) OVERRIDE
	{
		int count = inChunk.f.channelCount * inChunk.frameCount;
		if (m_isSigned)
			run<int32_t>(reinterpret_cast<const uint8_t *>(inChunk.buffer),
				reinterpret_cast<int32_t *>(outChunk.buffer),
				count);
		else
			run<uint32_t>(reinterpret_cast<const uint8_t *>(inChunk.buffer),
				reinterpret_cast<uint32_t *>(outChunk.buffer),
				count);
	}

private:
	bool m_isSigned;

	template <typename T>
	void run(const uint8_t *input, T *output, int sampleCount)
	{
		for (int i=0; i<sampleCount; i++)
		{
			T t =
#ifdef WORDS_BIGENDIAN
				(input[3*i] << 24) |
				(input[3*i+1] << 16) |
				input[3*i+2] << 8;
#else
				(input[3*i+2] << 24) |
				(input[3*i+1] << 16) |
				input[3*i] << 8;
#endif
			output[i] = t >> 8;
		}
	}
};

struct Compress4To3Module : public SimpleModule
{
public:
	Compress4To3Module(bool isSigned) : m_isSigned(isSigned)
	{
	}
	virtual const char *name() const OVERRIDE { return "compress4to3"; }
	virtual void describe() OVERRIDE
	{
		m_outChunk->f.packed = true;
	}
	virtual void run(Chunk &inChunk, Chunk &outChunk) OVERRIDE
	{
		int count = inChunk.f.channelCount * inChunk.frameCount;
		if (m_isSigned)
			run<int32_t>(inChunk.buffer, outChunk.buffer, count);
		else
			run<uint32_t>(inChunk.buffer, outChunk.buffer, count);
	}

private:
	bool m_isSigned;

	template <typename T>
	void run(const void *input, void *output, int count)
	{
		const T *in = reinterpret_cast<const T *>(input);
		uint8_t *out = reinterpret_cast<uint8_t *>(output);
		for (int i=0; i<count; i++)
		{
			uint8_t c0, c1, c2;
			extract3(in[i], c0, c1, c2);
			out[3*i] = c0;
			out[3*i+1] = c1;
			out[3*i+2] = c2;
		}
	}
	template <typename T>
	void extract3(T in, uint8_t &c0, uint8_t &c1, uint8_t &c2)
	{
#ifdef WORDS_BIGENDIAN
			c0 = (in >> 16) & 0xff;
			c1 = (in >> 8) & 0xff;
			c2 = in & 0xff;
#else
			c2 = (in >> 16) & 0xff;
			c1 = (in >> 8) & 0xff;
			c0 = in & 0xff;
#endif
	}
};

template <typename Arg, typename Result>
struct intToFloat : public std::unary_function<Arg, Result>
{
	Result operator()(Arg x) const { return x; }
};

struct ConvertIntToFloat : public SimpleModule
{
	ConvertIntToFloat(FormatCode inFormat, FormatCode outFormat) :
		m_inFormat(inFormat), m_outFormat(outFormat)
	{
	}
	virtual const char *name() const OVERRIDE { return "intToFloat"; }
	virtual void describe() OVERRIDE
	{
		m_outChunk->f.sampleFormat = m_outFormat == kDouble ?
			AF_SAMPFMT_DOUBLE : AF_SAMPFMT_FLOAT;
		m_outChunk->f.sampleWidth = m_outFormat == kDouble ? 64 : 32;
	}
	virtual void run(Chunk &inChunk, Chunk &outChunk) OVERRIDE
	{
		const void *src = inChunk.buffer;
		void *dst = outChunk.buffer;
		int count = inChunk.frameCount * inChunk.f.channelCount;
		if (m_outFormat == kFloat)
		{
			switch (m_inFormat)
			{
				case kInt8:
					run<int8_t, float>(src, dst, count); break;
				case kInt16:
					run<int16_t, float>(src, dst, count); break;
				case kInt24:
				case kInt32:
					run<int32_t, float>(src, dst, count); break;
				default:
					assert(false);
			}
		}
		else if (m_outFormat == kDouble)
		{
			switch (m_inFormat)
			{
				case kInt8:
					run<int8_t, double>(src, dst, count); break;
				case kInt16:
					run<int16_t, double>(src, dst, count); break;
				case kInt24:
				case kInt32:
					run<int32_t, double>(src, dst, count); break;
				default:
					assert(false);
			}
		}
	}

private:
	FormatCode m_inFormat, m_outFormat;

	template <typename Arg, typename Result>
	static void run(const void *src, void *dst, int count)
	{
		transform<intToFloat<Arg, Result> >(src, dst, count);
	}
};

template <typename Arg, typename Result, unsigned shift>
struct lshift : public std::unary_function<Arg, Result>
{
	Result operator()(const Arg &x) const { return x << shift; }
};

template <typename Arg, typename Result, unsigned shift>
struct rshift : public std::unary_function<Arg, Result>
{
	Result operator()(const Arg &x) const { return x >> shift; }
};

struct ConvertInt : public SimpleModule
{
	ConvertInt(FormatCode inFormat, FormatCode outFormat) :
		m_inFormat(inFormat),
		m_outFormat(outFormat)
	{
		assert(isInteger(m_inFormat));
		assert(isInteger(m_outFormat));
	}
	virtual const char *name() const OVERRIDE { return "convertInt"; }
	virtual void describe() OVERRIDE
	{
		getDefaultPCMMapping(m_outChunk->f.sampleWidth,
			m_outChunk->f.pcm.slope,
			m_outChunk->f.pcm.intercept,
			m_outChunk->f.pcm.minClip,
			m_outChunk->f.pcm.maxClip);
	}
	virtual void run(Chunk &inChunk, Chunk &outChunk) OVERRIDE
	{
		const void *src = inChunk.buffer;
		void *dst = outChunk.buffer;
		size_t count = inChunk.frameCount * inChunk.f.channelCount;

#define MASK(N, M) (((N)<<3) | (M))
#define HANDLE(N, M) \
	case MASK(N, M): convertInt<N, M>(src, dst, count); break;
		switch (MASK(m_inFormat, m_outFormat))
		{
			HANDLE(kInt8, kInt16)
			HANDLE(kInt8, kInt24)
			HANDLE(kInt8, kInt32)
			HANDLE(kInt16, kInt8)
			HANDLE(kInt16, kInt24)
			HANDLE(kInt16, kInt32)
			HANDLE(kInt24, kInt8)
			HANDLE(kInt24, kInt16)
			HANDLE(kInt24, kInt32)
			HANDLE(kInt32, kInt8)
			HANDLE(kInt32, kInt16)
			HANDLE(kInt32, kInt24)
		}
#undef MASK
#undef HANDLE
	}

private:
	FormatCode m_inFormat, m_outFormat;

	void getDefaultPCMMapping(int &bits, double &slope, double &intercept,
		double &minClip, double &maxClip)
	{
		bits = (m_outFormat + 1) * CHAR_BIT;
		slope = (1LL << (bits - 1));
		intercept = 0;
		minClip = -(1 << (bits - 1));
		maxClip = (1LL << (bits - 1)) - 1;
	}

	static bool isInteger(FormatCode code)
	{
		return code >= kInt8 && code <= kInt32;
	}

	template <FormatCode Input, FormatCode Output, bool = (Input > Output)>
		struct shift;

	template <FormatCode Input, FormatCode Output>
	struct shift<Input, Output, true> :
		public rshift<typename IntTypes<Input>::SignedType,
			typename IntTypes<Output>::SignedType,
			(Input - Output) * CHAR_BIT>
	{
	};

	template <FormatCode Input, FormatCode Output>
	struct shift<Input, Output, false> :
		public lshift<typename IntTypes<Input>::SignedType,
			typename IntTypes<Output>::SignedType,
			(Output - Input) * CHAR_BIT>
	{
	};

	template <FormatCode Input, FormatCode Output>
	static void convertInt(const void *src, void *dst, int count)
	{
		transform<shift<Input, Output> >(src, dst, count);
	}
};

template <typename Arg, typename Result>
struct floatToFloat : public std::unary_function<Arg, Result>
{
	Result operator()(Arg x) const { return x; }
};

struct ConvertFloat : public SimpleModule
{
	ConvertFloat(FormatCode inFormat, FormatCode outFormat) :
		m_inFormat(inFormat), m_outFormat(outFormat)
	{
		assert((m_inFormat == kFloat && m_outFormat == kDouble) ||
			(m_inFormat == kDouble && m_outFormat == kFloat));
	}
	virtual const char *name() const OVERRIDE { return "convertFloat"; }
	virtual void describe() OVERRIDE
	{
		switch (m_outFormat)
		{
			case kFloat:
				m_outChunk->f.sampleFormat = AF_SAMPFMT_FLOAT;
				m_outChunk->f.sampleWidth = 32;
				break;
			case kDouble:
				m_outChunk->f.sampleFormat = AF_SAMPFMT_DOUBLE;
				m_outChunk->f.sampleWidth = 64;
				break;
			default:
				assert(false);
		}
	}
	virtual void run(Chunk &inChunk, Chunk &outChunk) OVERRIDE
	{
		const void *src = inChunk.buffer;
		void *dst = outChunk.buffer;
		size_t count = inChunk.frameCount * inChunk.f.channelCount;

		switch (m_outFormat)
		{
			case kFloat:
				transform<floatToFloat<double, float> >(src, dst, count);
				break;
			case kDouble:
				transform<floatToFloat<float, double> >(src, dst, count);
				break;
			default:
				assert(false);
		}
	}

private:
	FormatCode m_inFormat, m_outFormat;
};

struct Clip : public SimpleModule
{
	Clip(FormatCode format, const PCMInfo &outputMapping) :
		m_format(format),
		m_outputMapping(outputMapping)
	{
	}
	virtual const char *name() const OVERRIDE { return "clip"; }
	virtual void describe() OVERRIDE
	{
		m_outChunk->f.pcm = m_outputMapping;
	}
	virtual void run(Chunk &inChunk, Chunk &outChunk) OVERRIDE
	{
		const void *src = inChunk.buffer;
		void *dst = outChunk.buffer;
		int count = inChunk.frameCount * inChunk.f.channelCount;

		switch (m_format)
		{
			case kInt8:
				run<int8_t>(src, dst, count); break;
			case kInt16:
				run<int16_t>(src, dst, count); break;
			case kInt24:
			case kInt32:
				run<int32_t>(src, dst, count); break;
			case kFloat:
				run<float>(src, dst, count); break;
			case kDouble:
				run<double>(src, dst, count); break;
			default:
				assert(false);
		}
	}

private:
	FormatCode m_format;
	PCMInfo m_outputMapping;

	template <typename T>
	void run(const void *srcData, void *dstData, int count)
	{
		const T minValue = m_outputMapping.minClip;
		const T maxValue = m_outputMapping.maxClip;

		const T *src = reinterpret_cast<const T *>(srcData);
		T *dst = reinterpret_cast<T *>(dstData);

		for (int i=0; i<count; i++)
		{
			T t = src[i];
			t = std::min(t, maxValue);
			t = std::max(t, minValue);
			dst[i] = t;
		}
	}
};

struct ConvertFloatToIntClip : public SimpleModule
{
	ConvertFloatToIntClip(FormatCode inputFormat, FormatCode outputFormat,
		const PCMInfo &inputMapping, const PCMInfo &outputMapping) :
		m_inputFormat(inputFormat),
		m_outputFormat(outputFormat),
		m_inputMapping(inputMapping),
		m_outputMapping(outputMapping)
	{
		assert(m_inputFormat == kFloat || m_inputFormat == kDouble);
		assert(m_outputFormat == kInt8 ||
			m_outputFormat == kInt16 ||
			m_outputFormat == kInt24 ||
			m_outputFormat == kInt32);
	}
	virtual const char *name() const OVERRIDE { return "convertPCMMapping"; }
	virtual void describe() OVERRIDE
	{
		m_outChunk->f.sampleFormat = AF_SAMPFMT_TWOSCOMP;
		m_outChunk->f.sampleWidth = (m_outputFormat + 1) * CHAR_BIT;
		m_outChunk->f.pcm = m_outputMapping;
	}
	virtual void run(Chunk &inChunk, Chunk &outChunk) OVERRIDE
	{
		const void *src = inChunk.buffer;
		void *dst = outChunk.buffer;
		int count = inChunk.frameCount * inChunk.f.channelCount;

		if (m_inputFormat == kFloat)
		{
			switch (m_outputFormat)
			{
				case kInt8:
					run<float, int8_t>(src, dst, count); break;
				case kInt16:
					run<float, int16_t>(src, dst, count); break;
				case kInt24:
				case kInt32:
					run<float, int32_t>(src, dst, count); break;
				default:
					assert(false);
			}
		}
		else if (m_inputFormat == kDouble)
		{
			switch (m_outputFormat)
			{
				case kInt8:
					run<double, int8_t>(src, dst, count); break;
				case kInt16:
					run<double, int16_t>(src, dst, count); break;
				case kInt24:
				case kInt32:
					run<double, int32_t>(src, dst, count); break;
				default:
					assert(false);
			}
		}
	}

private:
	FormatCode m_inputFormat, m_outputFormat;
	PCMInfo m_inputMapping, m_outputMapping;

	template <typename Input, typename Output>
	void run(const void *srcData, void *dstData, int count)
	{
		const Input *src = reinterpret_cast<const Input *>(srcData);
		Output *dst = reinterpret_cast<Output *>(dstData);

		double m = m_outputMapping.slope / m_inputMapping.slope;
		double b = m_outputMapping.intercept - m * m_inputMapping.intercept;
		double minValue = m_outputMapping.minClip;
		double maxValue = m_outputMapping.maxClip;

		for (int i=0; i<count; i++)
		{
			double t = m * src[i] + b;
			t = std::min(t, maxValue);
			t = std::max(t, minValue);
			dst[i] = static_cast<Output>(t);
		}
	}
};

struct ApplyChannelMatrix : public SimpleModule
{
public:
	ApplyChannelMatrix(FormatCode format, bool isReading,
		int inChannels, int outChannels,
		double minClip, double maxClip, const double *matrix);
	virtual ~ApplyChannelMatrix();
	virtual const char *name() const OVERRIDE;
	virtual void describe() OVERRIDE;
	virtual void run(Chunk &inChunk, Chunk &outChunk) OVERRIDE;

private:
	FormatCode m_format;
	int m_inChannels, m_outChannels;
	double m_minClip, m_maxClip;
	double *m_matrix;

	void initDefaultMatrix();
	template <typename T>
		void run(const void *input, void *output, int frameCount);
};

struct Transform : public SimpleModule
{
public:
	Transform(FormatCode format,
		const PCMInfo &inputMapping,
		const PCMInfo &outputMapping) :
		m_format(format),
		m_inputMapping(inputMapping),
		m_outputMapping(outputMapping)
	{
		assert(m_format == kFloat || m_format == kDouble);
	}
	virtual const char *name() const OVERRIDE { return "transform"; }
	virtual void describe() OVERRIDE
	{
		m_outChunk->f.pcm = m_outputMapping;
	}
	virtual void run(Chunk &inChunk, Chunk &outChunk) OVERRIDE
	{
		int count = inChunk.frameCount * inChunk.f.channelCount;
		if (m_format == kFloat)
			run<float>(inChunk.buffer, outChunk.buffer, count);
		else if (m_format == kDouble)
			run<double>(inChunk.buffer, outChunk.buffer, count);
		else
			assert(false);
	}

private:
	FormatCode m_format;
	PCMInfo m_inputMapping, m_outputMapping;

	template <typename T>
	void run(const void *srcData, void *dstData, int count)
	{
		const T *src = reinterpret_cast<const T *>(srcData);
		T *dst = reinterpret_cast<T *>(dstData);

		double m = m_outputMapping.slope / m_inputMapping.slope;
		double b = m_outputMapping.intercept - m * m_inputMapping.intercept;

		for (int i=0; i<count; i++)
			dst[i] = m * src[i] + b;
	}
};

#endif // SIMPLE_MODULE_H

// file: modules/FileModule.h
/*
	Audio File Library
	Copyright (C) 2010-2012, Michael Pruett <michael@68k.org>

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

#ifndef FILE_MODULE_H
#define FILE_MODULE_H


class FileModule : public Module
{
public:
	virtual bool handlesSeeking() const { return false; }

	virtual int bufferSize() const;

protected:
	enum Mode { Compress, Decompress };
	FileModule(Mode, Track *, File *fh, bool canSeek);

	Mode mode() const { return m_mode; }
	bool canSeek() const { return m_canSeek; }

	ssize_t read(void *data, size_t nbytes);
	ssize_t write(const void *data, size_t nbytes);
	off_t seek(off_t offset);
	off_t tell();
	off_t length();

private:
	Mode m_mode;

protected:
	Track *m_track;

	void reportReadError(AFframecount framesRead, AFframecount framesRequested);
	void reportWriteError(AFframecount framesWritten, AFframecount framesRequested);

private:
	File *m_fh;
	bool m_canSeek;
};

#endif // FILE_MODULE_H

// file: modules/RebufferModule.h
/*
	Audio File Library
	Copyright (C) 2000, Silicon Graphics, Inc.
	Copyright (C) 2010, Michael Pruett <michael@68k.org>

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

#ifndef REBUFFER_MODULE_H
#define REBUFFER_MODULE_H


class RebufferModule : public Module
{
public:
	enum Direction
	{
		FixedToVariable,
		VariableToFixed
	};

	RebufferModule(Direction, int bytesPerFrame, int numFrames, bool multipleOf);
	virtual ~RebufferModule();

	virtual const char *name() const OVERRIDE { return "rebuffer"; }

	virtual void maxPull() OVERRIDE;
	virtual void maxPush() OVERRIDE;

	virtual void runPull() OVERRIDE;
	virtual void reset1() OVERRIDE;
	virtual void reset2() OVERRIDE;
	virtual void runPush() OVERRIDE;
	virtual void sync1() OVERRIDE;
	virtual void sync2() OVERRIDE;

private:
	Direction m_direction;
	int m_bytesPerFrame;
	int m_numFrames;
	bool m_multipleOf; // buffer to multiple of m_numFrames
	bool m_eof; // end of input stream reached
	bool m_sentShortChunk; // end of input stream indicated
	char *m_buffer;
	int m_offset;
	char *m_savedBuffer;
	int m_savedOffset;

	void initFixedToVariable();
	void initVariableToFixed();
};

#endif // REBUFFER_MODULE_H

// file: modules/BlockCodec.h
/*
	Audio File Library
	Copyright (C) 2013 Michael Pruett <michael@68k.org>

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

// BlockCodec is a base class for codecs with fixed-size packets.

#ifndef BlockCodec_h
#define BlockCodec_h


class BlockCodec : public FileModule
{
public:
	virtual void runPull() OVERRIDE;
	virtual void reset1() OVERRIDE;
	virtual void reset2() OVERRIDE;
	virtual void runPush() OVERRIDE;
	virtual void sync1() OVERRIDE;
	virtual void sync2() OVERRIDE;

protected:
	int m_bytesPerPacket, m_framesPerPacket;
	AFframecount m_framesToIgnore;
	AFfileoffset m_savedPositionNextFrame;
	AFframecount m_savedNextFrame;

	BlockCodec(Mode, Track *, File *, bool canSeek);

	virtual int decodeBlock(const uint8_t *encoded, int16_t *decoded) = 0;
	virtual int encodeBlock(const int16_t *decoded, uint8_t *encoded) = 0;
};

#endif

// file: modules/BlockCodec.cpp
/*
	Audio File Library
	Copyright (C) 2013 Michael Pruett <michael@68k.org>

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/



#include <assert.h>

BlockCodec::BlockCodec(Mode mode, Track *track, File *fh, bool canSeek) :
	FileModule(mode, track, fh, canSeek),
	m_bytesPerPacket(-1),
	m_framesPerPacket(-1),
	m_framesToIgnore(-1),
	m_savedPositionNextFrame(-1),
	m_savedNextFrame(-1)
{
	m_framesPerPacket = track->f.framesPerPacket;
	m_bytesPerPacket = track->f.bytesPerPacket;
}

void BlockCodec::runPull()
{
	AFframecount framesToRead = m_outChunk->frameCount;
	AFframecount framesRead = 0;

	assert(framesToRead % m_framesPerPacket == 0);
	int blockCount = framesToRead / m_framesPerPacket;

	// Read the compressed data.
	ssize_t bytesRead = read(m_inChunk->buffer, m_bytesPerPacket * blockCount);
	int blocksRead = bytesRead >= 0 ? bytesRead / m_bytesPerPacket : 0;

	// Decompress into m_outChunk.
	for (int i=0; i<blocksRead; i++)
	{
		decodeBlock(static_cast<const uint8_t *>(m_inChunk->buffer) + i * m_bytesPerPacket,
			static_cast<int16_t *>(m_outChunk->buffer) + i * m_framesPerPacket * m_track->f.channelCount);

		framesRead += m_framesPerPacket;
	}

	m_track->nextfframe += framesRead;

	assert(tell() == m_track->fpos_next_frame);

	if (framesRead < framesToRead)
		reportReadError(framesRead, framesToRead);

	m_outChunk->frameCount = framesRead;
}

void BlockCodec::reset1()
{
	AFframecount nextTrackFrame = m_track->nextfframe;
	m_track->nextfframe = (nextTrackFrame / m_framesPerPacket) *
		m_framesPerPacket;

	m_framesToIgnore = nextTrackFrame - m_track->nextfframe;
}

void BlockCodec::reset2()
{
	m_track->fpos_next_frame = m_track->fpos_first_frame +
		m_bytesPerPacket * (m_track->nextfframe / m_framesPerPacket);
	m_track->frames2ignore += m_framesToIgnore;

	assert(m_track->nextfframe % m_framesPerPacket == 0);
}

void BlockCodec::runPush()
{
	AFframecount framesToWrite = m_inChunk->frameCount;
	int channelCount = m_inChunk->f.channelCount;

	int blockCount = (framesToWrite + m_framesPerPacket - 1) / m_framesPerPacket;
	for (int i=0; i<blockCount; i++)
	{
		encodeBlock(static_cast<const int16_t *>(m_inChunk->buffer) + i * m_framesPerPacket * channelCount,
			static_cast<uint8_t *>(m_outChunk->buffer) + i * m_bytesPerPacket);
	}

	ssize_t bytesWritten = write(m_outChunk->buffer, m_bytesPerPacket * blockCount);
	ssize_t blocksWritten = bytesWritten >= 0 ? bytesWritten / m_bytesPerPacket : 0;
	AFframecount framesWritten = std::min((AFframecount) blocksWritten * m_framesPerPacket, framesToWrite);

	m_track->nextfframe += framesWritten;
	m_track->totalfframes = m_track->nextfframe;

	assert(tell() == m_track->fpos_next_frame);

	if (framesWritten < framesToWrite)
		reportWriteError(framesWritten, framesToWrite);
}

void BlockCodec::sync1()
{
	m_savedPositionNextFrame = m_track->fpos_next_frame;
	m_savedNextFrame = m_track->nextfframe;
}

void BlockCodec::sync2()
{
	assert(tell() == m_track->fpos_next_frame);
	m_track->fpos_after_data = tell();
	m_track->fpos_next_frame = m_savedPositionNextFrame;
	m_track->nextfframe = m_savedNextFrame;
}

// file: modules/FileModule.cpp
/*
	Audio File Library
	Copyright (C) 2010-2012, Michael Pruett <michael@68k.org>

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/



#include <errno.h>
#include <string.h>

FileModule::FileModule(Mode mode, Track *track, File *fh, bool canSeek) :
	m_mode(mode),
	m_track(track),
	m_fh(fh),
	m_canSeek(canSeek)
{
	track->fpos_next_frame = track->fpos_first_frame;
	track->frames2ignore = 0;
}

ssize_t FileModule::read(void *data, size_t nbytes)
{
	ssize_t bytesRead = m_fh->read(data, nbytes);
	if (bytesRead > 0)
	{
		m_track->fpos_next_frame += bytesRead;
	}
	return bytesRead;
}

ssize_t FileModule::write(const void *data, size_t nbytes)
{
	ssize_t bytesWritten = m_fh->write(data, nbytes);
	if (bytesWritten > 0)
	{
		m_track->fpos_next_frame += bytesWritten;
		m_track->data_size += bytesWritten;
	}
	return bytesWritten;
}

off_t FileModule::seek(off_t offset)
{
	return m_fh->seek(offset, File::SeekFromBeginning);
}

off_t FileModule::tell()
{
	return m_fh->tell();
}

off_t FileModule::length()
{
	return m_fh->length();
}

void FileModule::reportReadError(AFframecount framesRead,
	AFframecount framesToRead)
{
	// Report error if we haven't already.
	if (!m_track->filemodhappy)
		return;

	_af_error(AF_BAD_READ,
		"file missing data -- read %jd frames, should be %jd",
		static_cast<intmax_t>(m_track->nextfframe),
		static_cast<intmax_t>(m_track->totalfframes));
	m_track->filemodhappy = false;
}

void FileModule::reportWriteError(AFframecount framesWritten,
	AFframecount framesToWrite)
{
	// Report error if we haven't already.
	if (!m_track->filemodhappy)
		return;

	if (framesWritten < 0)
	{
		// Signal I/O error.
		_af_error(AF_BAD_WRITE,
			"unable to write data (%s) -- wrote %jd out of %jd frames",
			strerror(errno),
			static_cast<intmax_t>(m_track->nextfframe),
			static_cast<intmax_t>(m_track->nextfframe + framesToWrite));
	}
	else
	{
		// Signal disk full error.
		_af_error(AF_BAD_WRITE,
			"unable to write data (disk full) -- "
			"wrote %jd out of %jd frames",
			static_cast<intmax_t>(m_track->nextfframe + framesWritten),
			static_cast<intmax_t>(m_track->nextfframe + framesToWrite));
	}

	m_track->filemodhappy = false;
}

int FileModule::bufferSize() const
{
	if (mode() == Compress)
		return outChunk()->frameCount * inChunk()->f.bytesPerFrame(true);
	else
		return inChunk()->frameCount * outChunk()->f.bytesPerFrame(true);
}

// file: modules/G711.h
/*
	Audio File Library
	Copyright (C) 2000, Silicon Graphics, Inc.
	Copyright (C) 2010, Michael Pruett <michael@68k.org>

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

/*
	g711.h
*/

#ifndef MODULES_G711_H
#define MODULES_G711_H


class File;
class FileModule;
struct AudioFormat;
struct Track;

bool _af_g711_format_ok (AudioFormat *f);

FileModule *_AFg711initcompress (Track *, File *, bool canSeek,
	bool headerless, AFframecount *chunkframes);

FileModule *_AFg711initdecompress (Track *, File *, bool canSeek,
	bool headerless, AFframecount *chunkframes);

#endif /* MODULES_G711_H */

// file: modules/G711.cpp
/*
	Audio File Library
	Copyright (C) 2000-2001, Silicon Graphics, Inc.
	Copyright (C) 2010-2013, Michael Pruett <michael@68k.org>

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/


#include <assert.h>



static void ulaw2linear_buf (const uint8_t *ulaw, int16_t *linear, int nsamples)
{
	for (int i=0; i < nsamples; i++)
		linear[i] = _af_ulaw2linear(ulaw[i]);
}

static void linear2ulaw_buf (const int16_t *linear, uint8_t *ulaw, int nsamples)
{
	for (int i=0; i < nsamples; i++)
		ulaw[i] = _af_linear2ulaw(linear[i]);
}

static void alaw2linear_buf (const uint8_t *alaw, int16_t *linear, int nsamples)
{
	for (int i=0; i < nsamples; i++)
		linear[i] = _af_alaw2linear(alaw[i]);
}

static void linear2alaw_buf (const int16_t *linear, uint8_t *alaw, int nsamples)
{
	for (int i=0; i < nsamples; i++)
		alaw[i] = _af_linear2alaw(linear[i]);
}

bool _af_g711_format_ok (AudioFormat *f)
{
	if (f->sampleFormat != AF_SAMPFMT_TWOSCOMP || f->sampleWidth != 16)
	{
		_af_error(AF_BAD_COMPRESSION,
		       "G.711 compression requires 16-bit signed integer format");
		return false;
	}

	if (f->byteOrder != _AF_BYTEORDER_NATIVE)
	{
		_af_error(AF_BAD_COMPRESSION,
			"G.711 compression requires native byte order");
		return false;
	}

	return true;
}

class G711 : public FileModule
{
public:
	static G711 *createCompress(Track *trk, File *fh, bool canSeek,
		bool headerless, AFframecount *chunkframes);
	static G711 *createDecompress(Track *trk, File *fh, bool canSeek,
		bool headerless, AFframecount *chunkframes);

	virtual const char *name() const OVERRIDE
	{
		return mode() == Compress ? "g711compress" : "g711decompress";
	}
	virtual void describe() OVERRIDE;
	virtual void runPull() OVERRIDE;
	virtual void reset2() OVERRIDE;
	virtual void runPush() OVERRIDE;
	virtual void sync1() OVERRIDE;
	virtual void sync2() OVERRIDE;

private:
	G711(Mode mode, Track *track, File *fh, bool canSeek);

	AFfileoffset m_savedPositionNextFrame;
	AFframecount m_savedNextFrame;
};

G711::G711(Mode mode, Track *track, File *fh, bool canSeek) :
	FileModule(mode, track, fh, canSeek),
	m_savedPositionNextFrame(-1),
	m_savedNextFrame(-1)
{
	if (mode == Decompress)
		track->f.compressionParams = AU_NULL_PVLIST;
}

G711 *G711::createCompress(Track *track, File *fh,
	bool canSeek, bool headerless, AFframecount *chunkframes)
{
	return new G711(Compress, track, fh, canSeek);
}

void G711::runPush()
{
	AFframecount framesToWrite = m_inChunk->frameCount;
	AFframecount samplesToWrite = m_inChunk->frameCount * m_inChunk->f.channelCount;
	int framesize = m_inChunk->f.channelCount;

	assert(m_track->f.compressionType == AF_COMPRESSION_G711_ULAW ||
		m_track->f.compressionType == AF_COMPRESSION_G711_ALAW);

	/* Compress frames into i->outc. */

	if (m_track->f.compressionType == AF_COMPRESSION_G711_ULAW)
		linear2ulaw_buf(static_cast<const int16_t *>(m_inChunk->buffer),
			static_cast<uint8_t *>(m_outChunk->buffer), samplesToWrite);
	else
		linear2alaw_buf(static_cast<const int16_t *>(m_inChunk->buffer),
			static_cast<uint8_t *>(m_outChunk->buffer), samplesToWrite);

	/* Write the compressed data. */

	ssize_t bytesWritten = write(m_outChunk->buffer, framesize * framesToWrite);
	AFframecount framesWritten = bytesWritten >= 0 ? bytesWritten / framesize : 0;

	if (framesWritten != framesToWrite)
		reportWriteError(framesWritten, framesToWrite);

	m_track->nextfframe += framesWritten;
	m_track->totalfframes = m_track->nextfframe;

	assert(!canSeek() || (tell() == m_track->fpos_next_frame));
}

void G711::sync1()
{
	m_savedPositionNextFrame = m_track->fpos_next_frame;
	m_savedNextFrame = m_track->nextfframe;
}

void G711::sync2()
{
	/* sanity check. */
	assert(!canSeek() || (tell() == m_track->fpos_next_frame));

	/* We can afford to do an lseek just in case because sync2 is rare. */
	m_track->fpos_after_data = tell();

	m_track->fpos_next_frame = m_savedPositionNextFrame;
	m_track->nextfframe = m_savedNextFrame;
}

void G711::describe()
{
	if (mode() == Compress)
	{
		m_outChunk->f.compressionType = m_track->f.compressionType;
	}
	else
	{
		m_outChunk->f.byteOrder = _AF_BYTEORDER_NATIVE;
		m_outChunk->f.compressionType = AF_COMPRESSION_NONE;
	}
}

G711 *G711::createDecompress(Track *track, File *fh,
	bool canSeek, bool headerless, AFframecount *chunkframes)
{
	return new G711(Decompress, track, fh, canSeek);
}

void G711::runPull()
{
	AFframecount framesToRead = m_outChunk->frameCount;
	AFframecount samplesToRead = m_outChunk->frameCount * m_outChunk->f.channelCount;
	int framesize = m_outChunk->f.channelCount;

	/* Read the compressed frames. */

	ssize_t bytesRead = read(m_inChunk->buffer, framesize * framesToRead);
	AFframecount framesRead = bytesRead >= 0 ? bytesRead / framesize : 0;

	/* Decompress into i->outc. */

	if (m_track->f.compressionType == AF_COMPRESSION_G711_ULAW)
		ulaw2linear_buf(static_cast<const uint8_t *>(m_inChunk->buffer),
			static_cast<int16_t *>(m_outChunk->buffer), samplesToRead);
	else
		alaw2linear_buf(static_cast<const uint8_t *>(m_inChunk->buffer),
			static_cast<int16_t *>(m_outChunk->buffer), samplesToRead);

	m_track->nextfframe += framesRead;
	assert(!canSeek() || (tell() == m_track->fpos_next_frame));

	/*
		If we got EOF from read, then we return the actual amount read.

		Complain only if there should have been more frames in the file.
	*/

	if (m_track->totalfframes != -1 && framesRead != framesToRead)
		reportReadError(framesRead, framesToRead);

	m_outChunk->frameCount = framesRead;
}

void G711::reset2()
{
	int framesize = m_inChunk->f.channelCount;

	m_track->fpos_next_frame = m_track->fpos_first_frame +
		framesize * m_track->nextfframe;

	m_track->frames2ignore = 0;
}

FileModule *_AFg711initcompress(Track *track, File *fh, bool canSeek,
	bool headerless, AFframecount *chunkFrames)
{
	return G711::createCompress(track, fh, canSeek, headerless, chunkFrames);
}

FileModule *_AFg711initdecompress(Track *track, File *fh, bool canSeek,
	bool headerless, AFframecount *chunkFrames)
{
	return G711::createDecompress(track, fh, canSeek, headerless, chunkFrames);
}

// file: modules/Module.cpp
/*
	Audio File Library
	Copyright (C) 2000, Silicon Graphics, Inc.
	Copyright (C) 2010, Michael Pruett <michael@68k.org>

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/



Module::Module() :
	m_sink(NULL)
{
}

Module::~Module()
{
}

void Module::setSink(Module *module) { m_sink = module; }
void Module::setSource(Module *module) { m_source = module; }

const char *Module::name() const { return ""; }

void Module::describe()
{
}

void Module::maxPull()
{
	m_inChunk->frameCount = m_outChunk->frameCount;
}

void Module::maxPush()
{
	m_outChunk->frameCount = m_inChunk->frameCount;
}

void Module::runPull()
{
}

void Module::runPush()
{
}

void Module::pull(size_t frames)
{
	m_inChunk->frameCount = frames;
	m_source->runPull();
}

void Module::push(size_t frames)
{
	m_outChunk->frameCount = frames;
	m_sink->runPush();
}

// file: modules/ModuleState.cpp
/*
	Audio File Library
	Copyright (C) 2000, Silicon Graphics, Inc.
	Copyright (C) 2010-2013, Michael Pruett <michael@68k.org>

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/



#include <algorithm>
#include <cassert>
#include <cmath>
#include <functional>
#include <stdio.h>

ModuleState::ModuleState() :
	m_isDirty(true)
{
}

ModuleState::~ModuleState()
{
}

status ModuleState::initFileModule(AFfilehandle file, Track *track)
{
	const CompressionUnit *unit = _af_compression_unit_from_id(track->f.compressionType);
	if (!unit)
		return AF_FAIL;

	// Validate compression format and parameters.
	if (!unit->fmtok(&track->f))
		return AF_FAIL;

	if (file->m_seekok &&
		file->m_fh->seek(track->fpos_first_frame, File::SeekFromBeginning) !=
			track->fpos_first_frame)
	{
		_af_error(AF_BAD_LSEEK, "unable to position file handle at beginning of sound data");
		return AF_FAIL;
	}

	AFframecount chunkFrames;
	if (file->m_access == _AF_READ_ACCESS)
		m_fileModule = unit->initdecompress(track, file->m_fh, file->m_seekok,
			file->m_fileFormat == AF_FILE_RAWDATA, &chunkFrames);
	else
		m_fileModule = unit->initcompress(track, file->m_fh, file->m_seekok,
			file->m_fileFormat == AF_FILE_RAWDATA, &chunkFrames);

	if (unit->needsRebuffer)
	{
		assert(unit->nativeSampleFormat == AF_SAMPFMT_TWOSCOMP);

		RebufferModule::Direction direction =
			file->m_access == _AF_WRITE_ACCESS ?
				RebufferModule::VariableToFixed : RebufferModule::FixedToVariable;

		m_fileRebufferModule = new RebufferModule(direction,
			track->f.bytesPerFrame(false), chunkFrames,
			unit->multiple_of);
	}

	track->filemodhappy = true;

	return AF_SUCCEED;
}

status ModuleState::init(AFfilehandle file, Track *track)
{
	if (initFileModule(file, track) == AF_FAIL)
		return AF_FAIL;

	return AF_SUCCEED;
}

bool ModuleState::fileModuleHandlesSeeking() const
{
	return m_fileModule->handlesSeeking();
}

status ModuleState::setup(AFfilehandle file, Track *track)
{
	AFframecount fframepos = std::llrint(track->nextvframe * track->f.sampleRate / track->v.sampleRate);
	bool isReading = file->m_access == _AF_READ_ACCESS;

	if (!track->v.isUncompressed())
	{
		_af_error(AF_BAD_NOT_IMPLEMENTED,
			"library does not support compression in virtual format yet");
		return AF_FAIL;
	}

	if (arrange(file, track) == AF_FAIL)
		return AF_FAIL;

	track->filemodhappy = true;
	int maxbufsize = 0;
	if (isReading)
	{
		m_chunks.back()->frameCount = _AF_ATOMIC_NVFRAMES;
		for (int i=m_modules.size() - 1; i >= 0; i--)
		{
			SharedPtr<Chunk> inChunk = m_chunks[i];
			SharedPtr<Chunk> outChunk = m_chunks[i+1];
			int bufsize = outChunk->frameCount * outChunk->f.bytesPerFrame(true);
			if (bufsize > maxbufsize)
				maxbufsize = bufsize;
			if (i != 0)
				m_modules[i]->setSource(m_modules[i-1].get());
			m_modules[i]->maxPull();
		}

		if (!track->filemodhappy)
			return AF_FAIL;
		int bufsize = m_fileModule->bufferSize();
		if (bufsize > maxbufsize)
			maxbufsize = bufsize;
	}
	else
	{
		m_chunks.front()->frameCount = _AF_ATOMIC_NVFRAMES;
		for (size_t i=0; i<m_modules.size(); i++)
		{
			SharedPtr<Chunk> inChunk = m_chunks[i];
			SharedPtr<Chunk> outChunk = m_chunks[i+1];
			int bufsize = inChunk->frameCount * inChunk->f.bytesPerFrame(true);
			if (bufsize > maxbufsize)
				maxbufsize = bufsize;
			if (i != m_modules.size() - 1)
				m_modules[i]->setSink(m_modules[i+1].get());
			m_modules[i]->maxPush();
		}

		if (!track->filemodhappy)
			return AF_FAIL;

		int bufsize = m_fileModule->bufferSize();
		if (bufsize > maxbufsize)
			maxbufsize = bufsize;
	}

	for (size_t i=0; i<m_chunks.size(); i++)
	{
		if ((isReading && i==m_chunks.size() - 1) || (!isReading && i==0))
			continue;
		m_chunks[i]->allocate(maxbufsize);
	}

	if (isReading)
	{
		if (track->totalfframes == -1)
			track->totalvframes = -1;
		else
			track->totalvframes = std::llrint(track->totalfframes *
				(track->v.sampleRate / track->f.sampleRate));

		track->nextfframe = fframepos;
		track->nextvframe = std::llrint(fframepos * track->v.sampleRate / track->f.sampleRate);

		m_isDirty = false;

		if (reset(file, track) == AF_FAIL)
			return AF_FAIL;
	}
	else
	{
		track->nextvframe = track->totalvframes =
			(AFframecount) (fframepos * track->v.sampleRate / track->f.sampleRate);
		m_isDirty = false;
	}

	return AF_SUCCEED;
}

const std::vector<SharedPtr<Module> > &ModuleState::modules() const
{
	return m_modules;
}

const std::vector<SharedPtr<Chunk> > &ModuleState::chunks() const
{
	return m_chunks;
}

status ModuleState::reset(AFfilehandle file, Track *track)
{
	track->filemodhappy = true;
	for (std::vector<SharedPtr<Module> >::reverse_iterator i=m_modules.rbegin();
			i != m_modules.rend(); ++i)
		(*i)->reset1();
	track->frames2ignore = 0;
	if (!track->filemodhappy)
		return AF_FAIL;
	for (std::vector<SharedPtr<Module> >::iterator i=m_modules.begin();
			i != m_modules.end(); ++i)
		(*i)->reset2();
	if (!track->filemodhappy)
		return AF_FAIL;
	return AF_SUCCEED;
}

status ModuleState::sync(AFfilehandle file, Track *track)
{
	track->filemodhappy = true;
	for (std::vector<SharedPtr<Module> >::reverse_iterator i=m_modules.rbegin();
			i != m_modules.rend(); ++i)
		(*i)->sync1();
	if (!track->filemodhappy)
		return AF_FAIL;
	for (std::vector<SharedPtr<Module> >::iterator i=m_modules.begin();
			i != m_modules.end(); ++i)
		(*i)->sync2();
	return AF_SUCCEED;
}

static const PCMInfo * const intmappings[6] =
{
	&_af_default_signed_integer_pcm_mappings[1],
	&_af_default_signed_integer_pcm_mappings[2],
	&_af_default_signed_integer_pcm_mappings[3],
	&_af_default_signed_integer_pcm_mappings[4],
	NULL,
	NULL
};

static FormatCode getFormatCode(const AudioFormat &format)
{
	if (format.sampleFormat == AF_SAMPFMT_FLOAT)
		return kFloat;
	if (format.sampleFormat == AF_SAMPFMT_DOUBLE)
		return kDouble;
	if (format.isInteger())
	{
		switch (format.bytesPerSample(false))
		{
			case 1: return kInt8;
			case 2: return kInt16;
			case 3: return kInt24;
			case 4: return kInt32;
		}
	}

	/* NOTREACHED */
	assert(false);
	return kUndefined;
}

static bool isInteger(FormatCode code) { return code >= kInt8 && code <= kInt32; }
static bool isFloat(FormatCode code) { return code >= kFloat && code <= kDouble; }

static bool isTrivialIntMapping(const AudioFormat &format, FormatCode code)
{
	return intmappings[code] != NULL &&
		format.pcm.slope == intmappings[code]->slope &&
		format.pcm.intercept == intmappings[code]->intercept;
}

static bool isTrivialIntClip(const AudioFormat &format, FormatCode code)
{
	return intmappings[code] != NULL &&
		format.pcm.minClip == intmappings[code]->minClip &&
		format.pcm.maxClip == intmappings[code]->maxClip;
}

status ModuleState::arrange(AFfilehandle file, Track *track)
{
	bool isReading = file->m_access == _AF_READ_ACCESS;
	AudioFormat in, out;
	if (isReading)
	{
		in = track->f;
		out = track->v;
	}
	else
	{
		in = track->v;
		out = track->f;
	}

	FormatCode infc = getFormatCode(in);
	FormatCode outfc = getFormatCode(out);
	if (infc == kUndefined || outfc == kUndefined)
		return AF_FAIL;

	m_chunks.clear();
	m_chunks.push_back(new Chunk());
	m_chunks.back()->f = in;

	m_modules.clear();

	if (isReading)
	{
		addModule(m_fileModule.get());
		addModule(m_fileRebufferModule.get());
	}

	// Convert to native byte order.
	if (in.byteOrder != _AF_BYTEORDER_NATIVE)
	{
		size_t bytesPerSample = in.bytesPerSample(!isReading);
		if (bytesPerSample > 1 && in.compressionType == AF_COMPRESSION_NONE)
			addModule(new SwapModule());
		else
			in.byteOrder = _AF_BYTEORDER_NATIVE;
	}

	// Handle 24-bit integer input format.
	if (in.isInteger() && in.bytesPerSample(false) == 3)
	{
		if (isReading || in.compressionType != AF_COMPRESSION_NONE)
			addModule(new Expand3To4Module(in.isSigned()));
	}

	// Make data signed.
	if (in.isUnsigned())
		addModule(new ConvertSign(infc, false));

	in.pcm = m_chunks.back()->f.pcm;

	// Reverse the unsigned shift for output.
	if (out.isUnsigned())
	{
		const double shift = intmappings[outfc]->minClip;
		out.pcm.intercept += shift;
		out.pcm.minClip += shift;
		out.pcm.maxClip += shift;
	}

	// Clip input samples if necessary.
	if (in.pcm.minClip < in.pcm.maxClip && !isTrivialIntClip(in, infc))
		addModule(new Clip(infc, in.pcm));

	bool alreadyClippedOutput = false;
	bool alreadyTransformedOutput = false;
	// Perform range transformation if input and output PCM mappings differ.
	bool transforming = (in.pcm.slope != out.pcm.slope ||
		in.pcm.intercept != out.pcm.intercept) &&
		!(isTrivialIntMapping(in, infc) &&
		isTrivialIntMapping(out, outfc));

	// Range transformation requires input to be floating-point.
	if (isInteger(infc) && transforming)
	{
		if (infc == kInt32 || outfc == kDouble || outfc == kInt32)
		{
			addConvertIntToFloat(infc, kDouble);
			infc = kDouble;
		}
		else
		{
			addConvertIntToFloat(infc, kFloat);
			infc = kFloat;
		}
	}

	if (transforming && infc == kDouble && isFloat(outfc))
		addModule(new Transform(infc, in.pcm, out.pcm));

	// Add format conversion if needed.
	if (isInteger(infc) && isInteger(outfc))
		addConvertIntToInt(infc, outfc);
	else if (isInteger(infc) && isFloat(outfc))
		addConvertIntToFloat(infc, outfc);
	else if (isFloat(infc) && isInteger(outfc))
	{
		addConvertFloatToInt(infc, outfc, in.pcm, out.pcm);
		alreadyClippedOutput = true;
		alreadyTransformedOutput = true;
	}
	else if (isFloat(infc) && isFloat(outfc))
		addConvertFloatToFloat(infc, outfc);

	if (transforming && !alreadyTransformedOutput && infc != kDouble)
		addModule(new Transform(outfc, in.pcm, out.pcm));

	if (in.channelCount != out.channelCount)
		addModule(new ApplyChannelMatrix(outfc, isReading,
			in.channelCount, out.channelCount,
			in.pcm.minClip, in.pcm.maxClip,
			track->channelMatrix));

	// Perform clipping if necessary.
	if (!alreadyClippedOutput)
	{
		if (out.pcm.minClip < out.pcm.maxClip && !isTrivialIntClip(out, outfc))
			addModule(new Clip(outfc, out.pcm));
	}

	// Make data unsigned if necessary.
	if (out.isUnsigned())
		addModule(new ConvertSign(outfc, true));

	// Handle 24-bit integer output format.
	if (out.isInteger() && out.bytesPerSample(false) == 3)
	{
		if (!isReading || out.compressionType != AF_COMPRESSION_NONE)
			addModule(new Compress4To3Module(out.isSigned()));
	}

	if (out.byteOrder != _AF_BYTEORDER_NATIVE)
	{
		size_t bytesPerSample = out.bytesPerSample(isReading);
		if (bytesPerSample > 1 && out.compressionType == AF_COMPRESSION_NONE)
			addModule(new SwapModule());
		else
			out.byteOrder = _AF_BYTEORDER_NATIVE;
	}

	if (!isReading)
	{
		addModule(m_fileRebufferModule.get());
		addModule(m_fileModule.get());
	}

	return AF_SUCCEED;
}

void ModuleState::addModule(Module *module)
{
	if (!module)
		return;

	m_modules.push_back(module);
	module->setInChunk(m_chunks.back().get());
	Chunk *chunk = new Chunk();
	chunk->f = m_chunks.back()->f;
	m_chunks.push_back(chunk);
	module->setOutChunk(chunk);
	module->describe();
}

void ModuleState::addConvertIntToInt(FormatCode input, FormatCode output)
{
	if (input == output)
		return;

	assert(isInteger(input));
	assert(isInteger(output));
	addModule(new ConvertInt(input, output));
}

void ModuleState::addConvertIntToFloat(FormatCode input, FormatCode output)
{
	addModule(new ConvertIntToFloat(input, output));
}

void ModuleState::addConvertFloatToInt(FormatCode input, FormatCode output,
	const PCMInfo &inputMapping, const PCMInfo &outputMapping)
{
	addModule(new ConvertFloatToIntClip(input, output, inputMapping, outputMapping));
}

void ModuleState::addConvertFloatToFloat(FormatCode input, FormatCode output)
{
	if (input == output)
		return;

	assert((input == kFloat && output == kDouble) ||
		(input == kDouble && output == kFloat));
	addModule(new ConvertFloat(input, output));
}

void ModuleState::print()
{
	fprintf(stderr, "modules:\n");
	for (size_t i=0; i<m_modules.size(); i++)
		fprintf(stderr, " %s (%p) in %p out %p\n",
			m_modules[i]->name(), m_modules[i].get(),
			m_modules[i]->inChunk(),
			m_modules[i]->outChunk());
	fprintf(stderr, "chunks:\n");
	for (size_t i=0; i<m_chunks.size(); i++)
		fprintf(stderr, " %p %s\n",
			m_chunks[i].get(),
			m_chunks[i]->f.description().c_str());
}

// file: modules/MSADPCM.h
/*
	Audio File Library
	Copyright (C) 2001, Silicon Graphics, Inc.
	Copyright (C) 2010, Michael Pruett <michael@68k.org>

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

/*
	msadpcm.h

	This module declares the interface for the Microsoft ADPCM
	compression module.
*/

#ifndef MSADPCM_H
#define MSADPCM_H


class File;
class FileModule;
struct AudioFormat;
struct Track;

bool _af_ms_adpcm_format_ok (AudioFormat *f);

FileModule *_af_ms_adpcm_init_decompress(Track *, File *,
	bool canSeek, bool headerless, AFframecount *chunkframes);

FileModule *_af_ms_adpcm_init_compress(Track *, File *,
	bool canSeek, bool headerless, AFframecount *chunkframes);

#endif

// file: modules/MSADPCM.cpp
/*
	Audio File Library
	Copyright (C) 2010-2013, Michael Pruett <michael@68k.org>
	Copyright (C) 2001, Silicon Graphics, Inc.

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

/*
	This module implements Microsoft ADPCM compression.
*/


#include <assert.h>
#include <cstdlib>
#include <limits>
#include <string.h>


struct ms_adpcm_state
{
	uint8_t predictorIndex;
	int delta;
	int16_t sample1, sample2;

	ms_adpcm_state()
	{
		predictorIndex = 0;
		delta = 16;
		sample1 = 0;
		sample2 = 0;
	}
};

class MSADPCM : public BlockCodec
{
public:
	static MSADPCM *createDecompress(Track *, File *, bool canSeek,
		bool headerless, AFframecount *chunkFrames);
	static MSADPCM *createCompress(Track *, File *, bool canSeek,
		bool headerless, AFframecount *chunkFrames);

	virtual ~MSADPCM();

	bool initializeCoefficients();

	virtual const char *name() const OVERRIDE
	{
		return mode() == Compress ? "ms_adpcm_compress" : "ms_adpcm_decompress";
	}
	virtual void describe() OVERRIDE;

private:
	// m_coefficients is an array of m_numCoefficients ADPCM coefficient pairs.
	int m_numCoefficients;
	int16_t m_coefficients[256][2];

	ms_adpcm_state *m_state;

	MSADPCM(Mode mode, Track *track, File *fh, bool canSeek);

	int decodeBlock(const uint8_t *encoded, int16_t *decoded) OVERRIDE;
	int encodeBlock(const int16_t *decoded, uint8_t *encoded) OVERRIDE;
	void choosePredictorForBlock(const int16_t *decoded);
};

static inline int clamp(int x, int low, int high)
{
	if (x < low) return low;
	if (x > high) return high;
	return x;
}

static const int16_t adaptationTable[] =
{
	230, 230, 230, 230, 307, 409, 512, 614,
	768, 614, 512, 409, 307, 230, 230, 230
};

// Compute a linear PCM value from the given differential coded value.
static int16_t decodeSample(ms_adpcm_state &state,
	uint8_t code, const int16_t *coefficient)
{
	int linearSample = (state.sample1 * coefficient[0] +
		state.sample2 * coefficient[1]) >> 8;

	linearSample += ((code & 0x08) ? (code - 0x10) : code) * state.delta;

	linearSample = clamp(linearSample, MIN_INT16, MAX_INT16);

	int delta = (state.delta * adaptationTable[code]) >> 8;
	if (delta < 16)
		delta = 16;

	state.delta = delta;
	state.sample2 = state.sample1;
	state.sample1 = linearSample;

	return static_cast<int16_t>(linearSample);
}

// Compute a differential coded value from the given linear PCM sample.
static uint8_t encodeSample(ms_adpcm_state &state, int16_t sample,
	const int16_t *coefficient)
{
	int predictor = (state.sample1 * coefficient[0] +
		state.sample2 * coefficient[1]) >> 8;
	int code = sample - predictor;
	int bias = state.delta / 2;
	if (code < 0)
		bias = -bias;
	code = (code + bias) / state.delta;
	code = clamp(code, -8, 7) & 0xf;

	predictor += ((code & 0x8) ? (code - 0x10) : code) * state.delta;

	state.sample2 = state.sample1;
	state.sample1 = clamp(predictor, MIN_INT16, MAX_INT16);
	state.delta = (adaptationTable[code] * state.delta) >> 8;
	if (state.delta < 16)
		state.delta = 16;
	return code;
}

// Decode one block of MS ADPCM data.
int MSADPCM::decodeBlock(const uint8_t *encoded, int16_t *decoded)
{
	ms_adpcm_state decoderState[2];
	ms_adpcm_state *state[2];

	int channelCount = m_track->f.channelCount;

	// Calculate the number of bytes needed for decoded data.
	int outputLength = m_framesPerPacket * sizeof (int16_t) * channelCount;

	state[0] = &decoderState[0];
	if (channelCount == 2)
		state[1] = &decoderState[1];
	else
		state[1] = &decoderState[0];

	// Initialize block predictor.
	for (int i=0; i<channelCount; i++)
	{
		state[i]->predictorIndex = *encoded++;
		assert(state[i]->predictorIndex < m_numCoefficients);
	}

	// Initialize delta.
	for (int i=0; i<channelCount; i++)
	{
		state[i]->delta = (encoded[1]<<8) | encoded[0];
		encoded += sizeof (uint16_t);
	}

	// Initialize first two samples.
	for (int i=0; i<channelCount; i++)
	{
		state[i]->sample1 = (encoded[1]<<8) | encoded[0];
		encoded += sizeof (uint16_t);
	}

	for (int i=0; i<channelCount; i++)
	{
		state[i]->sample2 = (encoded[1]<<8) | encoded[0];
		encoded += sizeof (uint16_t);
	}

	const int16_t *coefficient[2] =
	{
		m_coefficients[state[0]->predictorIndex],
		m_coefficients[state[1]->predictorIndex]
	};

	for (int i=0; i<channelCount; i++)
		*decoded++ = state[i]->sample2;

	for (int i=0; i<channelCount; i++)
		*decoded++ = state[i]->sample1;

	/*
		The first two samples have already been 'decoded' in
		the block header.
	*/
	int samplesRemaining = (m_framesPerPacket - 2) * m_track->f.channelCount;

	while (samplesRemaining > 0)
	{
		uint8_t code;
		int16_t newSample;

		code = *encoded >> 4;
		newSample = decodeSample(*state[0], code, coefficient[0]);
		*decoded++ = newSample;

		code = *encoded & 0x0f;
		newSample = decodeSample(*state[1], code, coefficient[1]);
		*decoded++ = newSample;

		encoded++;
		samplesRemaining -= 2;
	}

	return outputLength;
}

int MSADPCM::encodeBlock(const int16_t *decoded, uint8_t *encoded)
{
	choosePredictorForBlock(decoded);

	int channelCount = m_track->f.channelCount;

	// Encode predictor.
	for (int c=0; c<channelCount; c++)
		*encoded++ = m_state[c].predictorIndex;

	// Encode delta.
	for (int c=0; c<channelCount; c++)
	{
		*encoded++ = m_state[c].delta & 0xff;
		*encoded++ = m_state[c].delta >> 8;
	}

	// Enccode first two samples.
	for (int c=0; c<channelCount; c++)
		m_state[c].sample2 = *decoded++;

	for (int c=0; c<channelCount; c++)
		m_state[c].sample1 = *decoded++;

	for (int c=0; c<channelCount; c++)
	{
		*encoded++ = m_state[c].sample1 & 0xff;
		*encoded++ = m_state[c].sample1 >> 8;
	}

	for (int c=0; c<channelCount; c++)
	{
		*encoded++ = m_state[c].sample2 & 0xff;
		*encoded++ = m_state[c].sample2 >> 8;
	}

	ms_adpcm_state *state[2] = { &m_state[0], &m_state[channelCount - 1] };
	const int16_t *coefficient[2] =
	{
		m_coefficients[state[0]->predictorIndex],
		m_coefficients[state[1]->predictorIndex]
	};

	int samplesRemaining = (m_framesPerPacket - 2) * m_track->f.channelCount;
	while (samplesRemaining > 0)
	{
		uint8_t code1 = encodeSample(*state[0], *decoded++, coefficient[0]);
		uint8_t code2 = encodeSample(*state[1], *decoded++, coefficient[1]);

		*encoded++ = (code1 << 4) | code2;
		samplesRemaining -= 2;
	}

	return m_bytesPerPacket;
}

void MSADPCM::choosePredictorForBlock(const int16_t *decoded)
{
	const int kPredictorSampleLength = 3;

	int channelCount = m_track->f.channelCount;

	for (int c=0; c<channelCount; c++)
	{
		int bestPredictorIndex = 0;
		int bestPredictorError = std::numeric_limits<int>::max();
		for (int k=0; k<m_numCoefficients; k++)
		{
			int a0 = m_coefficients[k][0];
			int a1 = m_coefficients[k][1];

			int currentPredictorError = 0;
			for (int i=2; i<2+kPredictorSampleLength; i++)
			{
				int error = std::abs(decoded[i*channelCount + c] -
					((a0 * decoded[(i-1)*channelCount + c] +
					a1 * decoded[(i-2)*channelCount + c]) >> 8));
				currentPredictorError += error;
			}

			currentPredictorError /= 4 * kPredictorSampleLength;

			if (currentPredictorError < bestPredictorError)
			{
				bestPredictorError = currentPredictorError;
				bestPredictorIndex = k;
			}

			if (!currentPredictorError)
				break;
		}

		if (bestPredictorError < 16)
			bestPredictorError = 16;

		m_state[c].predictorIndex = bestPredictorIndex;
		m_state[c].delta = bestPredictorError;
	}
}

void MSADPCM::describe()
{
	m_outChunk->f.byteOrder = _AF_BYTEORDER_NATIVE;
	m_outChunk->f.compressionType = AF_COMPRESSION_NONE;
	m_outChunk->f.compressionParams = AU_NULL_PVLIST;
}

MSADPCM::MSADPCM(Mode mode, Track *track, File *fh, bool canSeek) :
	BlockCodec(mode, track, fh, canSeek),
	m_numCoefficients(0),
	m_state(NULL)
{
	m_state = new ms_adpcm_state[m_track->f.channelCount];
}

MSADPCM::~MSADPCM()
{
	delete [] m_state;
}

bool MSADPCM::initializeCoefficients()
{
	AUpvlist pv = m_track->f.compressionParams;

	long l;
	if (_af_pv_getlong(pv, _AF_MS_ADPCM_NUM_COEFFICIENTS, &l))
	{
		m_numCoefficients = l;
	}
	else
	{
		_af_error(AF_BAD_CODEC_CONFIG, "number of coefficients not set");
		return false;
	}

	void *v;
	if (_af_pv_getptr(pv, _AF_MS_ADPCM_COEFFICIENTS, &v))
	{
		memcpy(m_coefficients, v, m_numCoefficients * 2 * sizeof (int16_t));
	}
	else
	{
		_af_error(AF_BAD_CODEC_CONFIG, "coefficient array not set");
		return false;
	}

	return true;
}

MSADPCM *MSADPCM::createDecompress(Track *track, File *fh,
	bool canSeek, bool headerless, AFframecount *chunkFrames)
{
	assert(fh->tell() == track->fpos_first_frame);

	MSADPCM *msadpcm = new MSADPCM(Decompress, track, fh, canSeek);

	if (!msadpcm->initializeCoefficients())
	{
		delete msadpcm;
		return NULL;
	}

	*chunkFrames = msadpcm->m_framesPerPacket;

	return msadpcm;
}

MSADPCM *MSADPCM::createCompress(Track *track, File *fh,
	bool canSeek, bool headerless, AFframecount *chunkFrames)
{
	assert(fh->tell() == track->fpos_first_frame);

	MSADPCM *msadpcm = new MSADPCM(Compress, track, fh, canSeek);

	if (!msadpcm->initializeCoefficients())
	{
		delete msadpcm;
		return NULL;
	}

	*chunkFrames = msadpcm->m_framesPerPacket;

	return msadpcm;
}

bool _af_ms_adpcm_format_ok (AudioFormat *f)
{
	if (f->channelCount != 1 && f->channelCount != 2)
	{
		_af_error(AF_BAD_COMPRESSION,
			"MS ADPCM compression requires 1 or 2 channels");
		return false;
	}

	if (f->sampleFormat != AF_SAMPFMT_TWOSCOMP || f->sampleWidth != 16)
	{
		_af_error(AF_BAD_COMPRESSION,
			"MS ADPCM compression requires 16-bit signed integer format");
		return false;
	}

	if (f->byteOrder != _AF_BYTEORDER_NATIVE)
	{
		_af_error(AF_BAD_COMPRESSION,
			"MS ADPCM compression requires native byte order");
		return false;
	}

	return true;
}

FileModule *_af_ms_adpcm_init_decompress (Track *track, File *fh,
	bool canSeek, bool headerless, AFframecount *chunkFrames)
{
	return MSADPCM::createDecompress(track, fh, canSeek, headerless, chunkFrames);
}

FileModule *_af_ms_adpcm_init_compress (Track *track, File *fh,
	bool canSeek, bool headerless, AFframecount *chunkFrames)
{
	return MSADPCM::createCompress(track, fh, canSeek, headerless, chunkFrames);
}

// file: modules/PCM.h
/*
	Audio File Library
	Copyright (C) 2000, Silicon Graphics, Inc.
	Copyright (C) 2010, Michael Pruett <michael@68k.org>

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

/*
	PCM.h
*/

#ifndef MODULES_PCM_H
#define MODULES_PCM_H


class File;
class FileModule;
struct AudioFormat;
struct Track;

bool _af_pcm_format_ok (AudioFormat *f);

FileModule *_AFpcminitcompress(Track *, File *, bool seekok,
	bool headerless, AFframecount *chunkframes);

FileModule *_AFpcminitdecompress(Track *, File *, bool seekok,
	bool headerless, AFframecount *chunkframes);

#endif /* MODULES_PCM_H */

// file: modules/PCM.cpp
/*
	Audio File Library
	Copyright (C) 2000, Silicon Graphics, Inc.
	Copyright (C) 2010, Michael Pruett <michael@68k.org>

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

/*
	PCM.cpp - read and file write module for uncompressed data
*/


#include <assert.h>
#include <math.h>


bool _af_pcm_format_ok (AudioFormat *f)
{
	assert(!isnan(f->pcm.slope));
	assert(!isnan(f->pcm.intercept));
	assert(!isnan(f->pcm.minClip));
	assert(!isnan(f->pcm.maxClip));

	return true;
}

class PCM : public FileModule
{
public:
	static PCM *createCompress(Track *track, File *fh, bool canSeek,
		bool headerless, AFframecount *chunkFrames);
	static PCM *createDecompress(Track *track, File *fh, bool canSeek,
		bool headerless, AFframecount *chunkFrames);

	virtual const char *name() const OVERRIDE { return "pcm"; }
	virtual void runPull() OVERRIDE;
	virtual void reset2() OVERRIDE;
	virtual void runPush() OVERRIDE;
	virtual void sync1() OVERRIDE;
	virtual void sync2() OVERRIDE;

private:
	int m_bytesPerFrame;

	/* saved_fpos_next_frame and saved_nextfframe apply only to writing. */
	int m_saved_fpos_next_frame;
	int m_saved_nextfframe;

	PCM(Mode, Track *, File *, bool canSeek);
};

PCM::PCM(Mode mode, Track *track, File *fh, bool canSeek) :
	FileModule(mode, track, fh, canSeek),
	m_bytesPerFrame(track->f.bytesPerFrame(false)),
	m_saved_fpos_next_frame(-1),
	m_saved_nextfframe(-1)
{
	if (mode == Decompress)
		track->f.compressionParams = AU_NULL_PVLIST;
}

PCM *PCM::createCompress(Track *track, File *fh, bool canSeek,
	bool headerless, AFframecount *chunkframes)
{
	return new PCM(Compress, track, fh, canSeek);
}

void PCM::runPush()
{
	AFframecount frames2write = m_inChunk->frameCount;
	AFframecount n;

	/*
		WARNING: due to the optimization explained at the end
		of arrangemodules(), the pcm file module cannot depend
		on the presence of the intermediate working buffer
		which _AFsetupmodules usually allocates for file
		modules in their input or output chunk (for reading or
		writing, respectively).

		Fortunately, the pcm module has no need for such a buffer.
	*/

	ssize_t bytesWritten = write(m_inChunk->buffer, m_bytesPerFrame * frames2write);
	n = bytesWritten >= 0 ? bytesWritten / m_bytesPerFrame : 0;

	if (n != frames2write)
		reportWriteError(n, frames2write);

	m_track->nextfframe += n;
	m_track->totalfframes = m_track->nextfframe;
	assert(!canSeek() || (tell() == m_track->fpos_next_frame));
}

void PCM::sync1()
{
	m_saved_fpos_next_frame = m_track->fpos_next_frame;
	m_saved_nextfframe = m_track->nextfframe;
}

void PCM::sync2()
{
	assert(!canSeek() || (tell() == m_track->fpos_next_frame));

	/* We can afford to seek because sync2 is rare. */
	m_track->fpos_after_data = tell();

	m_track->fpos_next_frame = m_saved_fpos_next_frame;
	m_track->nextfframe = m_saved_nextfframe;
}

PCM *PCM::createDecompress(Track *track, File *fh, bool canSeek,
	bool headerless, AFframecount *chunkframes)
{
	return new PCM(Decompress, track, fh, canSeek);
}

void PCM::runPull()
{
	AFframecount framesToRead = m_outChunk->frameCount;

	/*
		WARNING: Due to the optimization explained at the end of
		arrangemodules(), the pcm file module cannot depend on
		the presence of the intermediate working buffer which
		_AFsetupmodules usually allocates for file modules in
		their input or output chunk (for reading or writing,
		respectively).

		Fortunately, the pcm module has no need for such a buffer.
	*/

	/*
		Limit the number of frames to be read to the number of
		frames left in the track.
	*/
	if (m_track->totalfframes != -1 &&
		m_track->nextfframe + framesToRead > m_track->totalfframes)
	{
		framesToRead = m_track->totalfframes - m_track->nextfframe;
	}

	ssize_t bytesRead = read(m_outChunk->buffer, m_bytesPerFrame * framesToRead);
	AFframecount framesRead = bytesRead >= 0 ? bytesRead / m_bytesPerFrame : 0;

	m_track->nextfframe += framesRead;
	assert(!canSeek() || (tell() == m_track->fpos_next_frame));

	/*
		If we got EOF from read, then we return the actual amount read.

		Complain only if there should have been more frames in the file.
	*/

	if (framesRead != framesToRead && m_track->totalfframes != -1)
		reportReadError(framesRead, framesToRead);

	m_outChunk->frameCount = framesRead;
}

void PCM::reset2()
{
	m_track->fpos_next_frame = m_track->fpos_first_frame +
		m_bytesPerFrame * m_track->nextfframe;

	m_track->frames2ignore = 0;
}

FileModule *_AFpcminitcompress (Track *track, File *fh, bool canSeek,
	bool headerless, AFframecount *chunkFrames)
{
	return PCM::createCompress(track, fh, canSeek, headerless, chunkFrames);
}

FileModule *_AFpcminitdecompress (Track *track, File *fh, bool canSeek,
	bool headerless, AFframecount *chunkFrames)
{
	return PCM::createDecompress(track, fh, canSeek, headerless, chunkFrames);
}

// file: modules/SimpleModule.cpp
/*
	Audio File Library
	Copyright (C) 2010, Michael Pruett <michael@68k.org>

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/


#include <algorithm>

void SimpleModule::runPull()
{
	pull(m_outChunk->frameCount);
	run(*m_inChunk, *m_outChunk);
}

void SimpleModule::runPush()
{
	m_outChunk->frameCount = m_inChunk->frameCount;
	run(*m_inChunk, *m_outChunk);
	push(m_outChunk->frameCount);
}

ApplyChannelMatrix::ApplyChannelMatrix(FormatCode format, bool isReading,
	int inChannels, int outChannels,
	double minClip, double maxClip, const double *matrix) :
	m_format(format),
	m_inChannels(inChannels),
	m_outChannels(outChannels),
	m_minClip(minClip),
	m_maxClip(maxClip),
	m_matrix(NULL)
{
	m_matrix = new double[m_inChannels * m_outChannels];
	if (matrix)
	{
		if (isReading)
		{
			// Copy channel matrix for reading.
			std::copy(matrix, matrix + m_inChannels * m_outChannels, m_matrix);
		}
		else
		{
			// Transpose channel matrix for writing.
			for (int i=0; i < inChannels; i++)
				for (int j=0; j < outChannels; j++)
					m_matrix[j*inChannels + i] = matrix[i*outChannels + j];
		}
	}
	else
	{
		initDefaultMatrix();
	}
}

ApplyChannelMatrix::~ApplyChannelMatrix()
{
	delete [] m_matrix;
}

const char *ApplyChannelMatrix::name() const { return "channelMatrix"; }

void ApplyChannelMatrix::describe()
{
	m_outChunk->f.channelCount = m_outChannels;
	m_outChunk->f.pcm.minClip = m_minClip;
	m_outChunk->f.pcm.maxClip = m_maxClip;
}

void ApplyChannelMatrix::run(Chunk &inChunk, Chunk &outChunk)
{
	switch (m_format)
	{
		case kInt8:
			run<int8_t>(inChunk.buffer, outChunk.buffer, inChunk.frameCount);
			break;
		case kInt16:
			run<int16_t>(inChunk.buffer, outChunk.buffer, inChunk.frameCount);
			break;
		case kInt24:
		case kInt32:
			run<int32_t>(inChunk.buffer, outChunk.buffer, inChunk.frameCount);
			break;
		case kFloat:
			run<float>(inChunk.buffer, outChunk.buffer, inChunk.frameCount);
			break;
		case kDouble:
			run<double>(inChunk.buffer, outChunk.buffer, inChunk.frameCount);
			break;
		default:
			assert(false);
	}
}

template <typename T>
void ApplyChannelMatrix::run(const void *inputData, void *outputData, int frameCount)
{
	const T *input = reinterpret_cast<const T *>(inputData);
	T *output = reinterpret_cast<T *>(outputData);
	for (int frame=0; frame<frameCount; frame++)
	{
		const T *inputSave = input;
		const double *m = m_matrix;
		for (int outChannel=0; outChannel < m_outChannels; outChannel++)
		{
			input = inputSave;
			double t = 0;
			for (int inChannel=0; inChannel < m_inChannels; inChannel++)
				t += *input++ * *m++;
			*output++ = t;
		}
	}
}

void ApplyChannelMatrix::initDefaultMatrix()
{
	const double *matrix = NULL;
	if (m_inChannels==1 && m_outChannels==2)
	{
		static const double m[]={1,1};
		matrix = m;
	}
	else if (m_inChannels==1 && m_outChannels==4)
	{
		static const double m[]={1,1,0,0};
		matrix = m;
	}
	else if (m_inChannels==2 && m_outChannels==1)
	{
		static const double m[]={.5,.5};
		matrix = m;
	}
	else if (m_inChannels==2 && m_outChannels==4)
	{
		static const double m[]={1,0,0,1,0,0,0,0};
		matrix = m;
	}
	else if (m_inChannels==4 && m_outChannels==1)
	{
		static const double m[]={.5,.5,.5,.5};
		matrix = m;
	}
	else if (m_inChannels==4 && m_outChannels==2)
	{
		static const double m[]={1,0,1,0,0,1,0,1};
		matrix = m;
	}

	if (matrix)
	{
		std::copy(matrix, matrix + m_inChannels * m_outChannels, m_matrix);
	}
	else
	{
		for (int i=0; i < m_inChannels; i++)
			for (int j=0; j < m_outChannels; j++)
				m_matrix[j*m_inChannels + i] = (i==j) ? 1 : 0;
	}
}

// file: modules/RebufferModule.cpp
/*
	Audio File Library
	Copyright (C) 2000, Silicon Graphics, Inc.
	Copyright (C) 2010, Michael Pruett <michael@68k.org>

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/


#include <algorithm>
#include <assert.h>
#include <string.h>

RebufferModule::RebufferModule(Direction direction, int bytesPerFrame,
	int numFrames, bool multipleOf) :
	m_direction(direction),
	m_bytesPerFrame(bytesPerFrame),
	m_numFrames(numFrames),
	m_multipleOf(multipleOf),
	m_eof(false),
	m_sentShortChunk(false),
	m_buffer(NULL),
	m_offset(-1),
	m_savedBuffer(NULL),
	m_savedOffset(-1)
{
	if (m_direction == FixedToVariable)
		initFixedToVariable();
	else
		initVariableToFixed();
}

RebufferModule::~RebufferModule()
{
	delete [] m_buffer;
	delete [] m_savedBuffer;
}

void RebufferModule::initFixedToVariable()
{
	m_offset = m_numFrames;
	m_buffer = new char[m_numFrames * m_bytesPerFrame];
}

void RebufferModule::initVariableToFixed()
{
	m_offset = 0;
	m_buffer = new char[m_numFrames * m_bytesPerFrame];
	m_savedBuffer = new char[m_numFrames * m_bytesPerFrame];
}

void RebufferModule::maxPull()
{
	assert(m_direction == FixedToVariable);
	if (m_multipleOf)
		m_inChunk->frameCount = m_outChunk->frameCount + m_numFrames;
	else
		m_inChunk->frameCount = m_numFrames;
}

void RebufferModule::maxPush()
{
	assert(m_direction == VariableToFixed);
	if (m_multipleOf)
		m_outChunk->frameCount = m_inChunk->frameCount + m_numFrames;
	else
		m_outChunk->frameCount = m_numFrames;
}

void RebufferModule::runPull()
{
	int framesToPull = m_outChunk->frameCount;
	const char *inBuffer = static_cast<const char *>(m_inChunk->buffer);
	char *outBuffer = static_cast<char *>(m_outChunk->buffer);

	assert(m_offset > 0 && m_offset <= m_numFrames);

	/*
		A module should not pull more frames from its input
		after receiving a short chunk.
	*/
	assert(!m_sentShortChunk);

	if (m_offset < m_numFrames)
	{
		int buffered = m_numFrames - m_offset;
		int n = std::min(framesToPull, buffered);
		memcpy(outBuffer, m_buffer + m_offset * m_bytesPerFrame,
			n * m_bytesPerFrame);
		outBuffer += buffered * m_bytesPerFrame;
		framesToPull -= buffered;
		m_offset += n;
	}

	// Try to pull more frames from the source.
	while (!m_eof && framesToPull > 0)
	{
		int framesRequested;
		if (m_multipleOf)
			// Round framesToPull up to nearest multiple of m_numFrames.
			framesRequested = ((framesToPull - 1) / m_numFrames + 1) * m_numFrames;
		else
			framesRequested = m_numFrames;

		assert(framesRequested > 0);

		pull(framesRequested);

		int framesReceived = m_inChunk->frameCount;

		if (framesReceived != framesRequested)
			m_eof = true;

		memcpy(outBuffer, inBuffer,
			std::min(framesToPull, framesReceived) * m_bytesPerFrame);

		outBuffer += framesReceived * m_bytesPerFrame;
		framesToPull -= framesReceived;

		if (m_multipleOf)
			assert(m_eof || framesToPull <= 0);

		if (framesToPull < 0)
		{
			assert(m_offset == m_numFrames);

			m_offset = m_numFrames + framesToPull;

			assert(m_offset > 0 && m_offset <= m_numFrames);

			memcpy(m_buffer + m_offset * m_bytesPerFrame,
				inBuffer + (framesReceived + framesToPull) * m_bytesPerFrame,
				(m_numFrames - m_offset) * m_bytesPerFrame);
		}
		else
		{
			assert(m_offset == m_numFrames);
		}
	}

	if (m_eof && framesToPull > 0)
	{
		// Output short chunk.
		m_outChunk->frameCount -= framesToPull;
		m_sentShortChunk = true;
		assert(m_offset == m_numFrames);
	}
	else
	{
		assert(framesToPull <= 0);
		assert(m_offset == m_numFrames + framesToPull);
	}
	assert(m_offset > 0 && m_offset <= m_numFrames);
}

void RebufferModule::reset1()
{
	m_offset = m_numFrames;
	m_eof = false;
	m_sentShortChunk = false;
	assert(m_offset > 0 && m_offset <= m_numFrames);
}

void RebufferModule::reset2()
{
	assert(m_offset > 0 && m_offset <= m_numFrames);
}

void RebufferModule::runPush()
{
	int framesToPush = m_inChunk->frameCount;
	const char *inBuffer = static_cast<const char *>(m_inChunk->buffer);
	char *outBuffer = static_cast<char *>(m_outChunk->buffer);

	assert(m_offset >= 0 && m_offset < m_numFrames);

	// Check that we will be able to push even one block.
	if (m_offset + framesToPush >= m_numFrames)
	{
		if (m_offset > 0)
			memcpy(m_outChunk->buffer, m_buffer, m_offset * m_bytesPerFrame);

		if (m_multipleOf)
		{
			// Round down to nearest multiple of m_numFrames.
			int n = ((m_offset + framesToPush) / m_numFrames) * m_numFrames;

			assert(n > m_offset);
			memcpy(outBuffer + m_offset * m_bytesPerFrame,
				inBuffer,
				(n - m_offset) * m_bytesPerFrame);

			push(n);

			inBuffer += (n - m_offset) * m_bytesPerFrame;
			framesToPush -= n - m_offset;
			assert(framesToPush >= 0);
			m_offset = 0;
		}
		else
		{
			while (m_offset + framesToPush >= m_numFrames)
			{
				int n = m_numFrames - m_offset;
				memcpy(outBuffer + m_offset * m_bytesPerFrame,
					inBuffer,
					n * m_bytesPerFrame);

				push(m_numFrames);

				inBuffer += n * m_bytesPerFrame;
				framesToPush -= n;
				assert(framesToPush >= 0);
				m_offset = 0;
			}
		}

		assert(m_offset == 0);
	}

	assert(m_offset + framesToPush < m_numFrames);

	// Save remaining samples in buffer.
	if (framesToPush > 0)
	{
		memcpy(m_buffer + m_offset * m_bytesPerFrame,
			inBuffer,
			framesToPush * m_bytesPerFrame);
		m_offset += framesToPush;
	}

	assert(m_offset >= 0 && m_offset < m_numFrames);
}

void RebufferModule::sync1()
{
	assert(m_offset >= 0 && m_offset < m_numFrames);

	// Save all the frames and the offset so we can restore our state later.
	memcpy(m_savedBuffer, m_buffer, m_numFrames * m_bytesPerFrame);
	m_savedOffset = m_offset;
}

void RebufferModule::sync2()
{
	assert(m_offset >= 0 && m_offset < m_numFrames);

	memcpy(m_outChunk->buffer, m_buffer, m_offset * m_bytesPerFrame);

	push(m_offset);

	memcpy(m_buffer, m_savedBuffer, m_numFrames * m_bytesPerFrame);
	m_offset = m_savedOffset;

	assert(m_offset >= 0 && m_offset < m_numFrames);
}

// file: AIFF.h
/*
	Audio File Library
	Copyright (C) 1998-2000, 2003-2004, 2010-2012, Michael Pruett <michael@68k.org>

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

/*
	AIFF.h

	This file contains structures and constants related to the AIFF
	and AIFF-C formats.
*/

#ifndef AIFF_H
#define AIFF_H


#define _AF_AIFF_NUM_INSTPARAMS 9
extern const InstParamInfo _af_aiff_inst_params[_AF_AIFF_NUM_INSTPARAMS];
#define _AF_AIFFC_NUM_COMPTYPES 3
extern const int _af_aiffc_compression_types[_AF_AIFFC_NUM_COMPTYPES];

class AIFFFile : public _AFfilehandle
{
public:
	AIFFFile();

	static bool recognizeAIFF(File *fh);
	static bool recognizeAIFFC(File *fh);

	static AFfilesetup completeSetup(AFfilesetup);

	int getVersion() OVERRIDE;

	status readInit(AFfilesetup) OVERRIDE;
	status writeInit(AFfilesetup) OVERRIDE;

	status update() OVERRIDE;

	bool isInstrumentParameterValid(AUpvlist, int) OVERRIDE;

private:
	AFfileoffset m_miscellaneousPosition;
	AFfileoffset m_FVER_offset;
	AFfileoffset m_COMM_offset;
	AFfileoffset m_MARK_offset;
	AFfileoffset m_INST_offset;
	AFfileoffset m_AESD_offset;
	AFfileoffset m_SSND_offset;

	status parseFVER(const Tag &type, size_t size);
	status parseAESD(const Tag &type, size_t size);
	status parseMiscellaneous(const Tag &type, size_t size);
	status parseINST(const Tag &type, size_t size);
	status parseMARK(const Tag &type, size_t size);
	status parseCOMM(const Tag &type, size_t size);
	status parseSSND(const Tag &type, size_t size);

	status writeCOMM();
	status writeSSND();
	status writeMARK();
	status writeINST();
	status writeFVER();
	status writeAESD();
	status writeMiscellaneous();

	void initCompressionParams();
	void initIMACompressionParams();

	bool isAIFFC() const { return m_fileFormat == AF_FILE_AIFFC; }

	bool readPString(char s[256]);
	bool writePString(const char *);
};

#endif

// file: AIFF.cpp
/*
	Audio File Library
	Copyright (C) 1998-2000, 2003-2004, 2010-2013, Michael Pruett <michael@68k.org>
	Copyright (C) 2000-2001, Silicon Graphics, Inc.

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

/*
	AIFF.cpp

	This file contains routines for reading and writing AIFF and
	AIFF-C sound files.
*/


#include <assert.h>
#include <stdint.h>
#include <stdlib.h>
#include <string.h>


const InstParamInfo _af_aiff_inst_params[_AF_AIFF_NUM_INSTPARAMS] =
{
	{ AF_INST_MIDI_BASENOTE, AU_PVTYPE_LONG, "MIDI base note", {60} },
	{ AF_INST_NUMCENTS_DETUNE, AU_PVTYPE_LONG, "Detune in cents", {0} },
	{ AF_INST_MIDI_LOVELOCITY, AU_PVTYPE_LONG, "Low velocity", {1} },
	{ AF_INST_MIDI_HIVELOCITY, AU_PVTYPE_LONG, "High velocity", {127} },
	{ AF_INST_MIDI_LONOTE, AU_PVTYPE_LONG, "Low note", {0} },
	{ AF_INST_MIDI_HINOTE, AU_PVTYPE_LONG, "High note", {127} },
	{ AF_INST_NUMDBS_GAIN, AU_PVTYPE_LONG, "Gain in dB", {0} },
	{ AF_INST_SUSLOOPID, AU_PVTYPE_LONG, "Sustain loop id", {0} },
	{ AF_INST_RELLOOPID, AU_PVTYPE_LONG, "Release loop id", {0} }
};

const int _af_aiffc_compression_types[_AF_AIFFC_NUM_COMPTYPES] =
{
	AF_COMPRESSION_G711_ULAW,
	AF_COMPRESSION_G711_ALAW,
	AF_COMPRESSION_IMA
};

static const _AFfilesetup aiffDefaultFileSetup =
{
	_AF_VALID_FILESETUP,	/* valid */
	AF_FILE_AIFF,		/* fileFormat */
	true,			/* trackSet */
	true,			/* instrumentSet */
	true,			/* miscellaneousSet */
	1,			/* trackCount */
	NULL,			/* tracks */
	1,			/* instrumentCount */
	NULL,			/* instruments */
	0,			/* miscellaneousCount */
	NULL			/* miscellaneous */
};

#define AIFC_VERSION_1 0xa2805140

struct _INST
{
	uint8_t		baseNote;
	int8_t		detune;
	uint8_t		lowNote, highNote;
	uint8_t		lowVelocity, highVelocity;
	int16_t		gain;

	uint16_t	sustainLoopPlayMode;
	uint16_t	sustainLoopBegin;
	uint16_t	sustainLoopEnd;

	uint16_t	releaseLoopPlayMode;
	uint16_t	releaseLoopBegin;
	uint16_t	releaseLoopEnd;
};

AIFFFile::AIFFFile()
{
	setFormatByteOrder(AF_BYTEORDER_BIGENDIAN);

	m_miscellaneousPosition = 0;
	m_FVER_offset = 0;
	m_COMM_offset = 0;
	m_MARK_offset = 0;
	m_INST_offset = 0;
	m_AESD_offset = 0;
	m_SSND_offset = 0;
}

/*
	FVER chunks are only present in AIFF-C files.
*/
status AIFFFile::parseFVER(const Tag &type, size_t size)
{
	assert(type == "FVER");

	uint32_t timestamp;
	readU32(&timestamp);
	/* timestamp holds the number of seconds since January 1, 1904. */

	return AF_SUCCEED;
}

/*
	Parse AES recording data.
*/
status AIFFFile::parseAESD(const Tag &type, size_t size)
{
	unsigned char aesChannelStatusData[24];

	assert(type == "AESD");
	assert(size == 24);

	Track *track = getTrack();

	track->hasAESData = true;

	/*
		Try to read 24 bytes of AES nonaudio data from the file.
		Fail if the file disappoints.
	*/
	if (m_fh->read(aesChannelStatusData, 24) != 24)
		return AF_FAIL;

	memcpy(track->aesData, aesChannelStatusData, 24);

	return AF_SUCCEED;
}

/*
	Parse miscellaneous data chunks such as name, author, copyright,
	and annotation chunks.
*/
status AIFFFile::parseMiscellaneous(const Tag &type, size_t size)
{
	int misctype = AF_MISC_UNRECOGNIZED;

	assert(type == "NAME" ||
		type == "AUTH" ||
		type == "(c) " ||
		type == "ANNO" ||
		type == "APPL" ||
		type == "MIDI");

	/* Skip zero-length miscellaneous chunks. */
	if (size == 0)
		return AF_FAIL;

	m_miscellaneousCount++;
	m_miscellaneous = (Miscellaneous *) _af_realloc(m_miscellaneous,
		m_miscellaneousCount * sizeof (Miscellaneous));

	if (type == "NAME")
		misctype = AF_MISC_NAME;
	else if (type == "AUTH")
		misctype = AF_MISC_AUTH;
	else if (type == "(c) ")
		misctype = AF_MISC_COPY;
	else if (type == "ANNO")
		misctype = AF_MISC_ANNO;
	else if (type == "APPL")
		misctype = AF_MISC_APPL;
	else if (type == "MIDI")
		misctype = AF_MISC_MIDI;

	m_miscellaneous[m_miscellaneousCount - 1].id = m_miscellaneousCount;
	m_miscellaneous[m_miscellaneousCount - 1].type = misctype;
	m_miscellaneous[m_miscellaneousCount - 1].size = size;
	m_miscellaneous[m_miscellaneousCount - 1].position = 0;
	m_miscellaneous[m_miscellaneousCount - 1].buffer = _af_malloc(size);
	m_fh->read(m_miscellaneous[m_miscellaneousCount - 1].buffer, size);

	return AF_SUCCEED;
}

/*
	Parse instrument chunks, which contain information about using
	sound data as a sampled instrument.
*/
status AIFFFile::parseINST(const Tag &type, size_t size)
{
	uint8_t baseNote;
	int8_t detune;
	uint8_t lowNote, highNote, lowVelocity, highVelocity;
	int16_t gain;

	uint16_t sustainLoopPlayMode, sustainLoopBegin, sustainLoopEnd;
	uint16_t releaseLoopPlayMode, releaseLoopBegin, releaseLoopEnd;

	Instrument *instrument = (Instrument *) _af_calloc(1, sizeof (Instrument));
	instrument->id = AF_DEFAULT_INST;
	instrument->values = (AFPVu *) _af_calloc(_AF_AIFF_NUM_INSTPARAMS, sizeof (AFPVu));
	instrument->loopCount = 2;
	instrument->loops = (Loop *) _af_calloc(2, sizeof (Loop));

	m_instrumentCount = 1;
	m_instruments = instrument;

	readU8(&baseNote);
	readS8(&detune);
	readU8(&lowNote);
	readU8(&highNote);
	readU8(&lowVelocity);
	readU8(&highVelocity);
	readS16(&gain);

	instrument->values[0].l = baseNote;
	instrument->values[1].l = detune;
	instrument->values[2].l = lowVelocity;
	instrument->values[3].l = highVelocity;
	instrument->values[4].l = lowNote;
	instrument->values[5].l = highNote;
	instrument->values[6].l = gain;

	instrument->values[7].l = 1;	/* sustain loop id */
	instrument->values[8].l = 2;	/* release loop id */

	readU16(&sustainLoopPlayMode);
	readU16(&sustainLoopBegin);
	readU16(&sustainLoopEnd);

	readU16(&releaseLoopPlayMode);
	readU16(&releaseLoopBegin);
	readU16(&releaseLoopEnd);

	instrument->loops[0].id = 1;
	instrument->loops[0].mode = sustainLoopPlayMode;
	instrument->loops[0].beginMarker = sustainLoopBegin;
	instrument->loops[0].endMarker = sustainLoopEnd;
	instrument->loops[0].trackid = AF_DEFAULT_TRACK;

	instrument->loops[1].id = 2;
	instrument->loops[1].mode = releaseLoopPlayMode;
	instrument->loops[1].beginMarker = releaseLoopBegin;
	instrument->loops[1].endMarker = releaseLoopEnd;
	instrument->loops[1].trackid = AF_DEFAULT_TRACK;

	return AF_SUCCEED;
}

/*
	Parse marker chunks, which contain the positions and names of loop markers.
*/
status AIFFFile::parseMARK(const Tag &type, size_t size)
{
	assert(type == "MARK");

	Track *track = getTrack();

	uint16_t numMarkers;
	readU16(&numMarkers);

	track->markerCount = numMarkers;
	if (numMarkers)
		track->markers = _af_marker_new(numMarkers);

	for (unsigned i=0; i<numMarkers; i++)
	{
		uint16_t markerID = 0;
		uint32_t markerPosition = 0;
		uint8_t sizeByte = 0;
		char *markerName = NULL;

		readU16(&markerID);
		readU32(&markerPosition);
		m_fh->read(&sizeByte, 1);
		markerName = (char *) _af_malloc(sizeByte + 1);
		m_fh->read(markerName, sizeByte);

		markerName[sizeByte] = '\0';

		/*
			If sizeByte is even, then 1+sizeByte (the length
			of the string) is odd.	Skip an extra byte to
			make it even.
		*/

		if ((sizeByte % 2) == 0)
			m_fh->seek(1, File::SeekFromCurrent);

		track->markers[i].id = markerID;
		track->markers[i].position = markerPosition;
		track->markers[i].name = markerName;
		track->markers[i].comment = _af_strdup("");
	}

	return AF_SUCCEED;
}

/*
	Parse common data chunks, which contain information regarding the
	sampling rate, the number of sample frames, and the number of
	sound channels.
*/
status AIFFFile::parseCOMM(const Tag &type, size_t size)
{
	assert(type == "COMM");

	Track *track = getTrack();

	uint16_t numChannels;
	uint32_t numSampleFrames;
	uint16_t sampleSize;
	unsigned char sampleRate[10];

	readU16(&numChannels);
	track->f.channelCount = numChannels;

	if (!numChannels)
	{
		_af_error(AF_BAD_CHANNELS, "invalid file with 0 channels");
		return AF_FAIL;
	}

	readU32(&numSampleFrames);
	track->totalfframes = numSampleFrames;

	readU16(&sampleSize);
	track->f.sampleWidth = sampleSize;

	m_fh->read(sampleRate, 10);
	track->f.sampleRate = _af_convert_from_ieee_extended(sampleRate);

	track->f.compressionType = AF_COMPRESSION_NONE;
	track->f.sampleFormat = AF_SAMPFMT_TWOSCOMP;
	track->f.byteOrder = AF_BYTEORDER_BIGENDIAN;

	track->f.framesPerPacket = 1;

	if (isAIFFC())
	{
		Tag compressionID;
		// Pascal strings are at most 255 bytes long.
		char compressionName[256];

		readTag(&compressionID);

		// Read the Pascal-style string containing the name.
		readPString(compressionName);

		if (compressionID == "NONE" || compressionID == "twos")
		{
			track->f.compressionType = AF_COMPRESSION_NONE;
		}
		else if (compressionID == "in24")
		{
			track->f.compressionType = AF_COMPRESSION_NONE;
			track->f.sampleWidth = 24;
		}
		else if (compressionID == "in32")
		{
			track->f.compressionType = AF_COMPRESSION_NONE;
			track->f.sampleWidth = 32;
		}
		else if (compressionID == "ACE2" ||
			compressionID == "ACE8" ||
			compressionID == "MAC3" ||
			compressionID == "MAC6")
		{
			_af_error(AF_BAD_NOT_IMPLEMENTED, "AIFF-C format does not support Apple's proprietary %s compression format", compressionName);
			return AF_FAIL;
		}
		else if (compressionID == "ulaw" || compressionID == "ULAW")
		{
			track->f.compressionType = AF_COMPRESSION_G711_ULAW;
			track->f.byteOrder = _AF_BYTEORDER_NATIVE;
			track->f.sampleWidth = 16;
			track->f.bytesPerPacket = track->f.channelCount;
		}
		else if (compressionID == "alaw" || compressionID == "ALAW")
		{
			track->f.compressionType = AF_COMPRESSION_G711_ALAW;
			track->f.byteOrder = _AF_BYTEORDER_NATIVE;
			track->f.sampleWidth = 16;
			track->f.bytesPerPacket = track->f.channelCount;
		}
		else if (compressionID == "fl32" || compressionID == "FL32")
		{
			track->f.sampleFormat = AF_SAMPFMT_FLOAT;
			track->f.sampleWidth = 32;
			track->f.compressionType = AF_COMPRESSION_NONE;
		}
		else if (compressionID == "fl64" || compressionID == "FL64")
		{
			track->f.sampleFormat = AF_SAMPFMT_DOUBLE;
			track->f.sampleWidth = 64;
			track->f.compressionType = AF_COMPRESSION_NONE;
		}
		else if (compressionID == "sowt")
		{
			track->f.compressionType = AF_COMPRESSION_NONE;
			track->f.byteOrder = AF_BYTEORDER_LITTLEENDIAN;
		}
		else if (compressionID == "ima4")
		{
			track->f.sampleWidth = 16;
			track->f.sampleFormat = AF_SAMPFMT_TWOSCOMP;
			track->f.compressionType = AF_COMPRESSION_IMA;
			track->f.byteOrder = _AF_BYTEORDER_NATIVE;

			initIMACompressionParams();

			track->totalfframes *= 64;
		}
		else
		{
			_af_error(AF_BAD_NOT_IMPLEMENTED, "AIFF-C compression type '%s' not currently supported",
				compressionID.name().c_str());
			return AF_FAIL;
		}
	}

	if (track->f.isUncompressed())
		track->f.computeBytesPerPacketPCM();

	if (_af_set_sample_format(&track->f, track->f.sampleFormat, track->f.sampleWidth) == AF_FAIL)
		return AF_FAIL;

	return AF_SUCCEED;
}

/*
	Parse the stored sound chunk, which usually contains little more
	than the sound data.
*/
status AIFFFile::parseSSND(const Tag &type, size_t size)
{
	assert(type == "SSND");

	Track *track = getTrack();

	uint32_t offset, blockSize;
	readU32(&offset);
	readU32(&blockSize);

	track->data_size = size - 8 - offset;

	track->fpos_first_frame = m_fh->tell() + offset;

	return AF_SUCCEED;
}

status AIFFFile::readInit(AFfilesetup setup)
{
	uint32_t type, size, formtype;

	bool hasCOMM = false;
	bool hasFVER = false;
	bool hasSSND = false;

	m_fh->seek(0, File::SeekFromBeginning);

	m_fh->read(&type, 4);
	readU32(&size);
	m_fh->read(&formtype, 4);

	if (memcmp(&type, "FORM", 4) != 0 ||
		(memcmp(&formtype, "AIFF", 4) && memcmp(&formtype, "AIFC", 4)))
		return AF_FAIL;

	if (!allocateTrack())
		return AF_FAIL;

	/* Include the offset of the form type. */
	size_t index = 4;
	while (index < size)
	{
		Tag chunkid;
		uint32_t chunksize = 0;
		status result = AF_SUCCEED;

		readTag(&chunkid);
		readU32(&chunksize);

		if (chunkid == "COMM")
		{
			hasCOMM = true;
			result = parseCOMM(chunkid, chunksize);
		}
		else if (chunkid == "FVER")
		{
			hasFVER = true;
			parseFVER(chunkid, chunksize);
		}
		else if (chunkid == "INST")
		{
			parseINST(chunkid, chunksize);
		}
		else if (chunkid == "MARK")
		{
			parseMARK(chunkid, chunksize);
		}
		else if (chunkid == "AESD")
		{
			parseAESD(chunkid, chunksize);
		}
		else if (chunkid == "NAME" ||
			chunkid == "AUTH" ||
			chunkid == "(c) " ||
			chunkid == "ANNO" ||
			chunkid == "APPL" ||
			chunkid == "MIDI")
		{
			parseMiscellaneous(chunkid, chunksize);
		}
		/*
			The sound data chunk is required if there are more than
			zero sample frames.
		*/
		else if (chunkid == "SSND")
		{
			if (hasSSND)
			{
				_af_error(AF_BAD_AIFF_SSND, "AIFF file has more than one SSND chunk");
				return AF_FAIL;
			}
			hasSSND = true;
			result = parseSSND(chunkid, chunksize);
		}

		if (result == AF_FAIL)
			return AF_FAIL;

		index += chunksize + 8;

		/* all chunks must be aligned on an even number of bytes */
		if ((index % 2) != 0)
			index++;

		m_fh->seek(index + 8, File::SeekFromBeginning);
	}

	if (!hasCOMM)
	{
		_af_error(AF_BAD_AIFF_COMM, "bad AIFF COMM chunk");
	}

	if (isAIFFC() && !hasFVER)
	{
		_af_error(AF_BAD_HEADER, "FVER chunk is required in AIFF-C");
	}

	/* The file has been successfully parsed. */
	return AF_SUCCEED;
}

bool AIFFFile::recognizeAIFF(File *fh)
{
	uint8_t buffer[8];

	fh->seek(0, File::SeekFromBeginning);

	if (fh->read(buffer, 8) != 8 || memcmp(buffer, "FORM", 4) != 0)
		return false;
	if (fh->read(buffer, 4) != 4 || memcmp(buffer, "AIFF", 4) != 0)
		return false;

	return true;
}

bool AIFFFile::recognizeAIFFC(File *fh)
{
	uint8_t buffer[8];

	fh->seek(0, File::SeekFromBeginning);

	if (fh->read(buffer, 8) != 8 || memcmp(buffer, "FORM", 4) != 0)
		return false;
	if (fh->read(buffer, 4) != 4 || memcmp(buffer, "AIFC", 4) != 0)
		return false;

	return true;
}

AFfilesetup AIFFFile::completeSetup(AFfilesetup setup)
{
	bool	isAIFF = setup->fileFormat == AF_FILE_AIFF;

	if (setup->trackSet && setup->trackCount != 1)
	{
		_af_error(AF_BAD_NUMTRACKS, "AIFF/AIFF-C file must have 1 track");
		return AF_NULL_FILESETUP;
	}

	TrackSetup *track = setup->getTrack();
	if (!track)
		return AF_NULL_FILESETUP;

	if (track->sampleFormatSet)
	{
		if (track->f.sampleFormat == AF_SAMPFMT_UNSIGNED)
		{
			_af_error(AF_BAD_FILEFMT, "AIFF/AIFF-C format does not support unsigned data");
			return AF_NULL_FILESETUP;
		}
		else if (isAIFF && track->f.sampleFormat != AF_SAMPFMT_TWOSCOMP)
		{
			_af_error(AF_BAD_FILEFMT, "AIFF format supports only two's complement integer data");
			return AF_NULL_FILESETUP;
		}
	}
	else
		_af_set_sample_format(&track->f, AF_SAMPFMT_TWOSCOMP,
			track->f.sampleWidth);

	/* Check sample width if writing two's complement. Otherwise ignore. */
	if (track->f.sampleFormat == AF_SAMPFMT_TWOSCOMP &&
		(track->f.sampleWidth < 1 || track->f.sampleWidth > 32))
	{
		_af_error(AF_BAD_WIDTH,
			"invalid sample width %d for AIFF/AIFF-C file "
			"(must be 1-32)", track->f.sampleWidth);
		return AF_NULL_FILESETUP;
	}

	if (isAIFF && track->f.compressionType != AF_COMPRESSION_NONE)
	{
		_af_error(AF_BAD_FILESETUP,
			"AIFF does not support compression; use AIFF-C");
		return AF_NULL_FILESETUP;
	}

	if (track->f.compressionType != AF_COMPRESSION_NONE &&
		track->f.compressionType != AF_COMPRESSION_G711_ULAW &&
		track->f.compressionType != AF_COMPRESSION_G711_ALAW &&
		track->f.compressionType != AF_COMPRESSION_IMA)
	{
		_af_error(AF_BAD_NOT_IMPLEMENTED, "compression format not supported in AIFF-C");
		return AF_NULL_FILESETUP;
	}

	if (track->f.isUncompressed() &&
		track->byteOrderSet &&
		track->f.byteOrder != AF_BYTEORDER_BIGENDIAN &&
		track->f.isByteOrderSignificant())
	{
		_af_error(AF_BAD_BYTEORDER,
			"AIFF/AIFF-C format supports only big-endian data");
		return AF_NULL_FILESETUP;
	}

	if (track->f.isUncompressed())
		track->f.byteOrder = AF_BYTEORDER_BIGENDIAN;

	if (setup->instrumentSet)
	{
		if (setup->instrumentCount != 0 && setup->instrumentCount != 1)
		{
			_af_error(AF_BAD_NUMINSTS, "AIFF/AIFF-C file must have 0 or 1 instrument chunk");
			return AF_NULL_FILESETUP;
		}
		if (setup->instruments != 0 &&
			setup->instruments[0].loopCount != 2)
		{
			_af_error(AF_BAD_NUMLOOPS, "AIFF/AIFF-C file with instrument must also have 2 loops");
			return AF_NULL_FILESETUP;
		}
	}

	if (setup->miscellaneousSet)
	{
		for (int i=0; i<setup->miscellaneousCount; i++)
		{
			switch (setup->miscellaneous[i].type)
			{
				case AF_MISC_COPY:
				case AF_MISC_AUTH:
				case AF_MISC_NAME:
				case AF_MISC_ANNO:
				case AF_MISC_APPL:
				case AF_MISC_MIDI:
					break;

				default:
					_af_error(AF_BAD_MISCTYPE, "invalid miscellaneous type %d for AIFF/AIFF-C file", setup->miscellaneous[i].type);
					return AF_NULL_FILESETUP;
			}
		}
	}

	return _af_filesetup_copy(setup, &aiffDefaultFileSetup, true);
}

bool AIFFFile::isInstrumentParameterValid(AUpvlist list, int i)
{
	int param, type;

	AUpvgetparam(list, i, &param);
	AUpvgetvaltype(list, i, &type);
	if (type != AU_PVTYPE_LONG)
		return false;

	long lval;
	AUpvgetval(list, i, &lval);

	switch (param)
	{
		case AF_INST_MIDI_BASENOTE:
			return ((lval >= 0) && (lval <= 127));

		case AF_INST_NUMCENTS_DETUNE:
			return ((lval >= -50) && (lval <= 50));

		case AF_INST_MIDI_LOVELOCITY:
			return ((lval >= 1) && (lval <= 127));

		case AF_INST_MIDI_HIVELOCITY:
			return ((lval >= 1) && (lval <= 127));

		case AF_INST_MIDI_LONOTE:
			return ((lval >= 0) && (lval <= 127));

		case AF_INST_MIDI_HINOTE:
			return ((lval >= 0) && (lval <= 127));

		case AF_INST_NUMDBS_GAIN:
		case AF_INST_SUSLOOPID:
		case AF_INST_RELLOOPID:
			return true;

		default:
			return false;
			break;
	}

	return true;
}

int AIFFFile::getVersion()
{
	if (isAIFFC())
		return AIFC_VERSION_1;
	return 0;
}

status AIFFFile::writeInit(AFfilesetup setup)
{
	if (initFromSetup(setup) == AF_FAIL)
		return AF_FAIL;

	initCompressionParams();

	uint32_t fileSize = 0;
	m_fh->write("FORM", 4);
	writeU32(&fileSize);

	if (isAIFFC())
		m_fh->write("AIFC", 4);
	else
		m_fh->write("AIFF", 4);

	if (isAIFFC())
		writeFVER();

	writeCOMM();
	writeMARK();
	writeINST();
	writeAESD();
	writeMiscellaneous();
	writeSSND();

	return AF_SUCCEED;
}

status AIFFFile::update()
{
	/* Get the length of the file. */
	uint32_t length = m_fh->length();
	length -= 8;

	/* Set the length of the FORM chunk. */
	m_fh->seek(4, File::SeekFromBeginning);
	writeU32(&length);

	if (isAIFFC())
		writeFVER();

	writeCOMM();
	writeMARK();
	writeINST();
	writeAESD();
	writeMiscellaneous();
	writeSSND();

	return AF_SUCCEED;
}

status AIFFFile::writeCOMM()
{
	/*
		If COMM_offset hasn't been set yet, set it to the
		current offset.
	*/
	if (m_COMM_offset == 0)
		m_COMM_offset = m_fh->tell();
	else
		m_fh->seek(m_COMM_offset, File::SeekFromBeginning);

	Track *track = getTrack();

	Tag compressionTag;
	/* Pascal strings can occupy only 255 bytes (+ a size byte). */
	char compressionName[256];

	if (isAIFFC())
	{
		if (track->f.compressionType == AF_COMPRESSION_NONE)
		{
			if (track->f.sampleFormat == AF_SAMPFMT_TWOSCOMP)
			{
				compressionTag = "NONE";
				strcpy(compressionName, "not compressed");
			}
			else if (track->f.sampleFormat == AF_SAMPFMT_FLOAT)
			{
				compressionTag = "fl32";
				strcpy(compressionName, "32-bit Floating Point");
			}
			else if (track->f.sampleFormat == AF_SAMPFMT_DOUBLE)
			{
				compressionTag = "fl64";
				strcpy(compressionName, "64-bit Floating Point");
			}
			/*
				We disallow unsigned sample data for
				AIFF files in _af_aiff_complete_setup,
				so the next condition should never be
				satisfied.
			*/
			else if (track->f.sampleFormat == AF_SAMPFMT_UNSIGNED)
			{
				_af_error(AF_BAD_SAMPFMT,
					"AIFF/AIFF-C format does not support unsigned data");
				assert(0);
				return AF_FAIL;
			}
		}
		else if (track->f.compressionType == AF_COMPRESSION_G711_ULAW)
		{
			compressionTag = "ulaw";
			strcpy(compressionName, "CCITT G.711 u-law");
		}
		else if (track->f.compressionType == AF_COMPRESSION_G711_ALAW)
		{
			compressionTag = "alaw";
			strcpy(compressionName, "CCITT G.711 A-law");
		}
		else if (track->f.compressionType == AF_COMPRESSION_IMA)
		{
			compressionTag = "ima4";
			strcpy(compressionName, "IMA 4:1 compression");
		}
	}

	m_fh->write("COMM", 4);

	/*
		For AIFF-C files, the length of the COMM chunk is 22
		plus the length of the compression name plus the size
		byte.  If the length of the data is an odd number of
		bytes, add a zero pad byte at the end, but don't
		include the pad byte in the chunk's size.
	*/
	uint32_t chunkSize;
	if (isAIFFC())
		chunkSize = 22 + strlen(compressionName) + 1;
	else
		chunkSize = 18;
	writeU32(&chunkSize);

	/* number of channels, 2 bytes */
	uint16_t channelCount = track->f.channelCount;
	writeU16(&channelCount);

	/* number of sample frames, 4 bytes */
	uint32_t frameCount = track->totalfframes;
	if (track->f.compressionType == AF_COMPRESSION_IMA)
		frameCount = track->totalfframes / track->f.framesPerPacket;
	writeU32(&frameCount);

	/* sample size, 2 bytes */
	uint16_t sampleSize = track->f.sampleWidth;
	writeU16(&sampleSize);

	/* sample rate, 10 bytes */
	uint8_t sampleRate[10];
	_af_convert_to_ieee_extended(track->f.sampleRate, sampleRate);
	m_fh->write(sampleRate, 10);

	if (isAIFFC())
	{
		writeTag(&compressionTag);
		writePString(compressionName);
	}

	return AF_SUCCEED;
}

/*
	The AESD chunk contains information pertinent to audio recording
	devices.
*/
status AIFFFile::writeAESD()
{
	Track *track = getTrack();

	if (!track->hasAESData)
		return AF_SUCCEED;

	if (m_AESD_offset == 0)
		m_AESD_offset = m_fh->tell();
	else
		m_fh->seek(m_AESD_offset, File::SeekFromBeginning);

	if (m_fh->write("AESD", 4) < 4)
		return AF_FAIL;

	uint32_t size = 24;
	if (!writeU32(&size))
		return AF_FAIL;

	if (m_fh->write(track->aesData, 24) < 24)
		return AF_FAIL;

	return AF_SUCCEED;
}

status AIFFFile::writeSSND()
{
	Track *track = getTrack();

	if (m_SSND_offset == 0)
		m_SSND_offset = m_fh->tell();
	else
		m_fh->seek(m_SSND_offset, File::SeekFromBeginning);

	m_fh->write("SSND", 4);

	uint32_t chunkSize = track->data_size + 8;
	writeU32(&chunkSize);

	uint32_t zero = 0;
	/* data offset */
	writeU32(&zero);
	/* block size */
	writeU32(&zero);

	if (track->fpos_first_frame == 0)
		track->fpos_first_frame = m_fh->tell();

	return AF_SUCCEED;
}

status AIFFFile::writeINST()
{
	uint32_t length = 20;

	struct _INST instrumentdata;

	instrumentdata.sustainLoopPlayMode =
		afGetLoopMode(this, AF_DEFAULT_INST, 1);
	instrumentdata.sustainLoopBegin =
		afGetLoopStart(this, AF_DEFAULT_INST, 1);
	instrumentdata.sustainLoopEnd =
		afGetLoopEnd(this, AF_DEFAULT_INST, 1);

	instrumentdata.releaseLoopPlayMode =
		afGetLoopMode(this, AF_DEFAULT_INST, 2);
	instrumentdata.releaseLoopBegin =
		afGetLoopStart(this, AF_DEFAULT_INST, 2);
	instrumentdata.releaseLoopEnd =
		afGetLoopEnd(this, AF_DEFAULT_INST, 2);

	m_fh->write("INST", 4);
	writeU32(&length);

	instrumentdata.baseNote =
		afGetInstParamLong(this, AF_DEFAULT_INST, AF_INST_MIDI_BASENOTE);
	writeU8(&instrumentdata.baseNote);
	instrumentdata.detune =
		afGetInstParamLong(this, AF_DEFAULT_INST, AF_INST_NUMCENTS_DETUNE);
	writeS8(&instrumentdata.detune);
	instrumentdata.lowNote =
		afGetInstParamLong(this, AF_DEFAULT_INST, AF_INST_MIDI_LONOTE);
	writeU8(&instrumentdata.lowNote);
	instrumentdata.highNote =
		afGetInstParamLong(this, AF_DEFAULT_INST, AF_INST_MIDI_HINOTE);
	writeU8(&instrumentdata.highNote);
	instrumentdata.lowVelocity =
		afGetInstParamLong(this, AF_DEFAULT_INST, AF_INST_MIDI_LOVELOCITY);
	writeU8(&instrumentdata.lowVelocity);
	instrumentdata.highVelocity =
		afGetInstParamLong(this, AF_DEFAULT_INST, AF_INST_MIDI_HIVELOCITY);
	writeU8(&instrumentdata.highVelocity);

	instrumentdata.gain =
		afGetInstParamLong(this, AF_DEFAULT_INST, AF_INST_NUMDBS_GAIN);
	writeS16(&instrumentdata.gain);

	writeU16(&instrumentdata.sustainLoopPlayMode);
	writeU16(&instrumentdata.sustainLoopBegin);
	writeU16(&instrumentdata.sustainLoopEnd);

	writeU16(&instrumentdata.releaseLoopPlayMode);
	writeU16(&instrumentdata.releaseLoopBegin);
	writeU16(&instrumentdata.releaseLoopEnd);

	return AF_SUCCEED;
}

status AIFFFile::writeMARK()
{
	Track *track = getTrack();
	if (!track->markerCount)
		return AF_SUCCEED;

	if (m_MARK_offset == 0)
		m_MARK_offset = m_fh->tell();
	else
		m_fh->seek(m_MARK_offset, File::SeekFromBeginning);

	Tag markTag("MARK");
	uint32_t length = 0;

	writeTag(&markTag);
	writeU32(&length);

	AFfileoffset chunkStartPosition = m_fh->tell();

	uint16_t numMarkers = track->markerCount;
	writeU16(&numMarkers);

	for (unsigned i=0; i<numMarkers; i++)
	{
		uint16_t id = track->markers[i].id;
		writeU16(&id);

		uint32_t position = track->markers[i].position;
		writeU32(&position);

		const char *name = track->markers[i].name;
		assert(name);

		// Write the name as a Pascal-style string.
		writePString(name);
	}

	AFfileoffset chunkEndPosition = m_fh->tell();
	length = chunkEndPosition - chunkStartPosition;

	m_fh->seek(chunkStartPosition - 4, File::SeekFromBeginning);

	writeU32(&length);
	m_fh->seek(chunkEndPosition, File::SeekFromBeginning);

	return AF_SUCCEED;
}

/*
	The FVER chunk, if present, is always the first chunk in the file.
*/
status AIFFFile::writeFVER()
{
	uint32_t chunkSize, timeStamp;

	assert(isAIFFC());

	if (m_FVER_offset == 0)
		m_FVER_offset = m_fh->tell();
	else
		m_fh->seek(m_FVER_offset, File::SeekFromBeginning);

	m_fh->write("FVER", 4);

	chunkSize = 4;
	writeU32(&chunkSize);

	timeStamp = AIFC_VERSION_1;
	writeU32(&timeStamp);

	return AF_SUCCEED;
}

/*
	WriteMiscellaneous writes all the miscellaneous data chunks in a
	file handle structure to an AIFF or AIFF-C file.
*/
status AIFFFile::writeMiscellaneous()
{
	if (m_miscellaneousPosition == 0)
		m_miscellaneousPosition = m_fh->tell();
	else
		m_fh->seek(m_miscellaneousPosition, File::SeekFromBeginning);

	for (int i=0; i<m_miscellaneousCount; i++)
	{
		Miscellaneous *misc = &m_miscellaneous[i];
		Tag chunkType;
		uint32_t chunkSize;
		uint8_t padByte = 0;

		switch (misc->type)
		{
			case AF_MISC_NAME:
				chunkType = "NAME"; break;
			case AF_MISC_AUTH:
				chunkType = "AUTH"; break;
			case AF_MISC_COPY:
				chunkType = "(c) "; break;
			case AF_MISC_ANNO:
				chunkType = "ANNO"; break;
			case AF_MISC_MIDI:
				chunkType = "MIDI"; break;
			case AF_MISC_APPL:
				chunkType = "APPL"; break;
		}

		writeTag(&chunkType);

		chunkSize = misc->size;
		writeU32(&chunkSize);
		/*
			Write the miscellaneous buffer and then a pad byte
			if necessary.  If the buffer is null, skip the space
			for now.
		*/
		if (misc->buffer != NULL)
			m_fh->write(misc->buffer, misc->size);
		else
			m_fh->seek(misc->size, File::SeekFromCurrent);

		if (misc->size % 2 != 0)
			writeU8(&padByte);
	}

	return AF_SUCCEED;
}

void AIFFFile::initCompressionParams()
{
	Track *track = getTrack();
	if (track->f.compressionType == AF_COMPRESSION_IMA)
		initIMACompressionParams();
}

void AIFFFile::initIMACompressionParams()
{
	Track *track = getTrack();

	track->f.bytesPerPacket = 34 * track->f.channelCount;
	track->f.framesPerPacket = 64;

	AUpvlist pv = AUpvnew(1);
	AUpvsetparam(pv, 0, _AF_IMA_ADPCM_TYPE);
	AUpvsetvaltype(pv, 0, AU_PVTYPE_LONG);
	long l = _AF_IMA_ADPCM_TYPE_QT;
	AUpvsetval(pv, 0, &l);

	track->f.compressionParams = pv;
}

// Read a Pascal-style string.
bool AIFFFile::readPString(char s[256])
{
	uint8_t length;
	if (m_fh->read(&length, 1) != 1)
		return false;
	if (m_fh->read(s, length) != static_cast<ssize_t>(length))
		return false;
	s[length] = '\0';
	return true;
}

// Write a Pascal-style string.
bool AIFFFile::writePString(const char *s)
{
	size_t length = strlen(s);
	if (length > 255)
		return false;
	uint8_t sizeByte = static_cast<uint8_t>(length);
	if (m_fh->write(&sizeByte, 1) != 1)
		return false;
	if (m_fh->write(s, length) != (ssize_t) length)
		return false;
	/*
		Add a pad byte if the length of the Pascal-style string
		(including the size byte) is odd.
	*/
	if ((length % 2) == 0)
	{
		uint8_t zero = 0;
		if (m_fh->write(&zero, 1) != 1)
			return false;
	}
	return true;
}

// file: AudioFormat.cpp
/*
	Audio File Library
	Copyright (C) 2010, Michael Pruett <michael@68k.org>

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/


#include <assert.h>
#include <stdio.h>

size_t AudioFormat::bytesPerSample(bool stretch3to4) const
{
	switch (sampleFormat)
	{
		case AF_SAMPFMT_FLOAT:
			return sizeof (float);
		case AF_SAMPFMT_DOUBLE:
			return sizeof (double);
		default:
		{
			int size = (sampleWidth + 7) / 8;
			if (compressionType == AF_COMPRESSION_NONE &&
				size == 3 && stretch3to4)
				size = 4;
			return size;
		}
	}
}

size_t AudioFormat::bytesPerFrame(bool stretch3to4) const
{
	return bytesPerSample(stretch3to4) * channelCount;
}

size_t AudioFormat::bytesPerSample() const
{
	return bytesPerSample(!isPacked());
}

size_t AudioFormat::bytesPerFrame() const
{
	return bytesPerFrame(!isPacked());
}

bool AudioFormat::isInteger() const
{
	return sampleFormat == AF_SAMPFMT_TWOSCOMP ||
		sampleFormat == AF_SAMPFMT_UNSIGNED;
}

bool AudioFormat::isSigned() const
{
	return sampleFormat == AF_SAMPFMT_TWOSCOMP;
}

bool AudioFormat::isUnsigned() const
{
	return sampleFormat == AF_SAMPFMT_UNSIGNED;
}

bool AudioFormat::isFloat() const
{
	return sampleFormat == AF_SAMPFMT_FLOAT ||
		sampleFormat == AF_SAMPFMT_DOUBLE;
}

bool AudioFormat::isCompressed() const
{
	return compressionType != AF_COMPRESSION_NONE;
}

bool AudioFormat::isUncompressed() const
{
	return compressionType == AF_COMPRESSION_NONE;
}

void AudioFormat::computeBytesPerPacketPCM()
{
	assert(isUncompressed());
	int bytesPerSample = (sampleWidth + 7) / 8;
	bytesPerPacket = bytesPerSample * channelCount;
}

std::string AudioFormat::description() const
{
	std::string d;
	char s[1024];
	/* sampleRate, channelCount */
	sprintf(s, "{ %7.2f Hz %d ch ", sampleRate, channelCount);
	d += s;

	/* sampleFormat, sampleWidth */
	switch (sampleFormat)
	{
		case AF_SAMPFMT_TWOSCOMP:
			sprintf(s, "%db 2 ", sampleWidth);
			break;
		case AF_SAMPFMT_UNSIGNED:
			sprintf(s, "%db u ", sampleWidth);
			break;
		case AF_SAMPFMT_FLOAT:
			sprintf(s, "flt ");
			break;
		case AF_SAMPFMT_DOUBLE:
			sprintf(s, "dbl ");
			break;
		default:
			assert(false);
			break;
	}

	d += s;

	/* pcm */
	sprintf(s, "(%.30g+-%.30g [%.30g,%.30g]) ",
		pcm.intercept, pcm.slope,
		pcm.minClip, pcm.maxClip);
	d += s;

	/* byteOrder */
	switch (byteOrder)
	{
		case AF_BYTEORDER_BIGENDIAN:
			d += "big ";
			break;
		case AF_BYTEORDER_LITTLEENDIAN:
			d += "little ";
			break;
		default:
			assert(false);
			break;
	}

	if (isCompressed())
	{
		const CompressionUnit *unit = _af_compression_unit_from_id(compressionType);
		assert(unit);
		d += "compression: ";
		d += unit->label;
	}

	return d;
}

// file: Buffer.cpp
/*
	Audio File Library
	Copyright (C) 2013 Michael Pruett <michael@68k.org>

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/


#include <string.h>

Buffer::Buffer() : m_data(0), m_size(0)
{
}

Buffer::Buffer(size_t size) : m_data(0), m_size(0)
{
	if (size)
		m_data = ::operator new(size);
	if (m_data)
	{
		m_size = size;
	}
}

Buffer::Buffer(const void *data, size_t size) : m_data(0), m_size(0)
{
	if (size)
		m_data = ::operator new(size);
	if (m_data)
	{
		::memcpy(m_data, data, m_size);
		m_size = size;
	}
}

Buffer::~Buffer()
{
	::operator delete(m_data);
}

// file: File.cpp
/*
	Copyright (C) 2010, Michael Pruett. All rights reserved.

	Redistribution and use in source and binary forms, with or without
	modification, are permitted provided that the following conditions
	are met:

	1. Redistributions of source code must retain the above copyright
	notice, this list of conditions and the following disclaimer.

	2. Redistributions in binary form must reproduce the above copyright
	notice, this list of conditions and the following disclaimer in the
	documentation and/or other materials provided with the distribution.

	3. The name of the author may not be used to endorse or promote products
	derived from this software without specific prior written permission.

	THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
	IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
	OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
	IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
	INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
	NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
	DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
	THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
	(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
	THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/



#include <assert.h>
#include <fcntl.h>
#include <stdlib.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <unistd.h>
#include <stdio.h>

class FilePOSIX : public File
{
public:
	FilePOSIX(int fd, AccessMode mode) : File(mode), m_fd(fd) { }
	virtual ~FilePOSIX() { close(); }

	virtual int close() OVERRIDE;
	virtual ssize_t read(void *data, size_t nbytes) OVERRIDE;
	virtual ssize_t write(const void *data, size_t nbytes) OVERRIDE;
	virtual off_t length() OVERRIDE;
	virtual off_t seek(off_t offset, SeekOrigin origin) OVERRIDE;
	virtual off_t tell() OVERRIDE;

private:
	int m_fd;
};

class FileVF : public File
{
public:
	FileVF(AFvirtualfile *vf, AccessMode mode) : File(mode), m_vf(vf) { }
	virtual ~FileVF() { close(); }

	virtual int close() OVERRIDE;
	virtual ssize_t read(void *data, size_t nbytes) OVERRIDE;
	virtual ssize_t write(const void *data, size_t nbytes) OVERRIDE;
	virtual off_t length() OVERRIDE;
	virtual off_t seek(off_t offset, SeekOrigin origin) OVERRIDE;
	virtual off_t tell() OVERRIDE;

private:
	AFvirtualfile *m_vf;
};

File *File::open(const char *path, File::AccessMode mode)
{
	int flags = 0;
	if (mode == ReadAccess)
		flags = O_RDONLY;
	else if (mode == WriteAccess)
		flags = O_CREAT | O_WRONLY | O_TRUNC;
#if defined(WIN32) || defined(__CYGWIN__)
	flags |= O_BINARY;
#endif
	int fd = ::open(path, flags, 0666);
	if (fd == -1)
		return NULL;
	File *file = new FilePOSIX(fd, mode);
	return file;
}

File *File::create(int fd, File::AccessMode mode)
{
	return new FilePOSIX(fd, mode);
}

File *File::create(AFvirtualfile *vf, File::AccessMode mode)
{
	return new FileVF(vf, mode);
}

File::~File()
{
}

bool File::canSeek()
{
	return seek(0, File::SeekFromCurrent) != -1;
}

int FilePOSIX::close()
{
	if (m_fd == -1)
		return 0;

	int result = ::close(m_fd);
	m_fd = -1;
	return result;
}

ssize_t FilePOSIX::read(void *data, size_t nbytes)
{
	return ::read(m_fd, data, nbytes);
}

ssize_t FilePOSIX::write(const void *data, size_t nbytes)
{
	return ::write(m_fd, data, nbytes);
}

off_t FilePOSIX::length()
{
	off_t current = tell();
	if (current == -1)
		return -1;
	off_t length = seek(0, SeekFromEnd);
	if (length == -1)
		return -1;
	seek(current, SeekFromBeginning);
	return length;
}

off_t FilePOSIX::seek(off_t offset, File::SeekOrigin origin)
{
	int whence;
	switch (origin)
	{
		case SeekFromBeginning: whence = SEEK_SET; break;
		case SeekFromCurrent: whence = SEEK_CUR; break;
		case SeekFromEnd: whence = SEEK_END; break;
		default: assert(false); return -1;
	}
	return ::lseek(m_fd, offset, whence);
}

off_t FilePOSIX::tell()
{
	return seek(0, File::SeekFromCurrent);
}

int FileVF::close()
{
	if (m_vf)
		af_virtual_file_destroy(m_vf);
	m_vf = 0;
	return 0;
}

ssize_t FileVF::read(void *data, size_t nbytes)
{
	return m_vf->read(m_vf, data, nbytes);
}

ssize_t FileVF::write(const void *data, size_t nbytes)
{
	return m_vf->write(m_vf, data, nbytes);
}

off_t FileVF::length()
{
	return m_vf->length(m_vf);
}

off_t FileVF::seek(off_t offset, SeekOrigin origin)
{
	if (origin == SeekFromEnd)
		offset += length();
	return m_vf->seek(m_vf, offset, origin == SeekFromCurrent);
}

off_t FileVF::tell()
{
	return m_vf->tell(m_vf);
}

// file: FileHandle.cpp
/*
	Audio File Library
	Copyright (C) 2010-2012, Michael Pruett <michael@68k.org>
	Copyright (C) 2000-2001, Silicon Graphics, Inc.

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/


#include <stdlib.h>
#include <assert.h>



static void freeInstParams (AFPVu *values, int fileFormat)
{
	if (!values)
		return;

	int parameterCount = _af_units[fileFormat].instrumentParameterCount;

	for (int i=0; i<parameterCount; i++)
	{
		if (_af_units[fileFormat].instrumentParameters[i].type == AU_PVTYPE_PTR)
			free(values[i].v);
	}

	free(values);
}

_AFfilehandle *_AFfilehandle::create(int fileFormat)
{
	switch (fileFormat)
	{
		case AF_FILE_RAWDATA:
			return new RawFile();
		case AF_FILE_AIFF:
		case AF_FILE_AIFFC:
			return new AIFFFile();
		case AF_FILE_WAVE:
			return new WAVEFile();
		default:
			return NULL;
	}
}

_AFfilehandle::_AFfilehandle()
{
	m_valid = _AF_VALID_FILEHANDLE;
	m_access = 0;
	m_seekok = false;
	m_fh = NULL;
	m_fileName = NULL;
	m_fileFormat = AF_FILE_UNKNOWN;
	m_trackCount = 0;
	m_tracks = NULL;
	m_instrumentCount = 0;
	m_instruments = NULL;
	m_miscellaneousCount = 0;
	m_miscellaneous = NULL;
	m_formatByteOrder = 0;
}

_AFfilehandle::~_AFfilehandle()
{
	m_valid = 0;

	free(m_fileName);

	delete [] m_tracks;
	m_tracks = NULL;
	m_trackCount = 0;

	if (m_instruments)
	{
		for (int i=0; i<m_instrumentCount; i++)
		{
			free(m_instruments[i].loops);
			m_instruments[i].loops = NULL;
			m_instruments[i].loopCount = 0;

			freeInstParams(m_instruments[i].values, m_fileFormat);
			m_instruments[i].values = NULL;
		}

		free(m_instruments);
		m_instruments = NULL;
	}
	m_instrumentCount = 0;

	if (m_miscellaneous)
	{
		for (int i=0; i<m_miscellaneousCount; i++)
			free(m_miscellaneous[i].buffer);
		free(m_miscellaneous);
		m_miscellaneous = NULL;
	}
	m_miscellaneousCount = 0;
}

Track *_AFfilehandle::allocateTrack()
{
	assert(!m_trackCount);
	assert(!m_tracks);

	m_trackCount = 1;
	m_tracks = new Track[1];
	return m_tracks;
}

Track *_AFfilehandle::getTrack(int trackID)
{
	for (int i=0; i<m_trackCount; i++)
		if (m_tracks[i].id == trackID)
			return &m_tracks[i];

	_af_error(AF_BAD_TRACKID, "bad track id %d", trackID);

	return NULL;
}

bool _AFfilehandle::checkCanRead()
{
	if (m_access != _AF_READ_ACCESS)
	{
		_af_error(AF_BAD_NOREADACC, "file not opened for read access");
		return false;
	}

	return true;
}

bool _AFfilehandle::checkCanWrite()
{
	if (m_access != _AF_WRITE_ACCESS)
	{
		_af_error(AF_BAD_NOWRITEACC, "file not opened for write access");
		return false;
	}

	return true;
}

Instrument *_AFfilehandle::getInstrument(int instrumentID)
{
	for (int i = 0; i < m_instrumentCount; i++)
		if (m_instruments[i].id == instrumentID)
			return &m_instruments[i];

	_af_error(AF_BAD_INSTID, "invalid instrument id %d", instrumentID);
	return NULL;
}

Miscellaneous *_AFfilehandle::getMiscellaneous(int miscellaneousID)
{
	for (int i=0; i<m_miscellaneousCount; i++)
	{
		if (m_miscellaneous[i].id == miscellaneousID)
			return &m_miscellaneous[i];
	}

	_af_error(AF_BAD_MISCID, "bad miscellaneous id %d", miscellaneousID);

	return NULL;
}

status _AFfilehandle::initFromSetup(AFfilesetup setup)
{
	if (copyTracksFromSetup(setup) == AF_FAIL)
		return AF_FAIL;
	if (copyInstrumentsFromSetup(setup) == AF_FAIL)
		return AF_FAIL;
	if (copyMiscellaneousFromSetup(setup) == AF_FAIL)
		return AF_FAIL;
	return AF_SUCCEED;
}

status _AFfilehandle::copyTracksFromSetup(AFfilesetup setup)
{
	if ((m_trackCount = setup->trackCount) == 0)
	{
		m_tracks = NULL;
		return AF_SUCCEED;
	}

	m_tracks = new Track[m_trackCount];
	if (!m_tracks)
		return AF_FAIL;

	for (int i=0; i<m_trackCount; i++)
	{
		Track *track = &m_tracks[i];
		TrackSetup *trackSetup = &setup->tracks[i];

		track->id = trackSetup->id;
		track->f = trackSetup->f;

		if (track->copyMarkers(trackSetup) == AF_FAIL)
			return AF_FAIL;

		track->hasAESData = trackSetup->aesDataSet;
	}

	return AF_SUCCEED;
}

status _AFfilehandle::copyInstrumentsFromSetup(AFfilesetup setup)
{
	if ((m_instrumentCount = setup->instrumentCount) == 0)
	{
		m_instruments = NULL;
		return AF_SUCCEED;
	}

	m_instruments = static_cast<Instrument *>(_af_calloc(m_instrumentCount,
		sizeof (Instrument)));
	if (!m_instruments)
		return AF_FAIL;

	for (int i=0; i<m_instrumentCount; i++)
	{
		m_instruments[i].id = setup->instruments[i].id;

		// Copy loops.
		if ((m_instruments[i].loopCount = setup->instruments[i].loopCount) == 0)
		{
			m_instruments[i].loops = NULL;
		}
		else
		{
			m_instruments[i].loops =
				static_cast<Loop *>(_af_calloc(m_instruments[i].loopCount,
					sizeof (Loop)));
			if (!m_instruments[i].loops)
				return AF_FAIL;
			for (int j=0; j<m_instruments[i].loopCount; j++)
			{
				Loop *loop = &m_instruments[i].loops[j];
				loop->id = setup->instruments[i].loops[j].id;
				loop->mode = AF_LOOP_MODE_NOLOOP;
				loop->count = 0;
				loop->trackid = AF_DEFAULT_TRACK;
				loop->beginMarker = 2*j + 1;
				loop->endMarker = 2*j + 2;
			}
		}

		int instParamCount;
		// Copy instrument parameters.
		if ((instParamCount = _af_units[setup->fileFormat].instrumentParameterCount) == 0)
		{
			m_instruments[i].values = NULL;
		}
		else
		{
			m_instruments[i].values =
				static_cast<AFPVu *>(_af_calloc(instParamCount, sizeof (AFPVu)));
			if (!m_instruments[i].values)
				return AF_FAIL;
			for (int j=0; j<instParamCount; j++)
			{
				m_instruments[i].values[j] = _af_units[setup->fileFormat].instrumentParameters[j].defaultValue;
			}
		}
	}

	return AF_SUCCEED;
}

status _AFfilehandle::copyMiscellaneousFromSetup(AFfilesetup setup)
{
	if ((m_miscellaneousCount = setup->miscellaneousCount) == 0)
	{
		m_miscellaneous = NULL;
		return AF_SUCCEED;
	}

	m_miscellaneous = static_cast<Miscellaneous *>(_af_calloc(m_miscellaneousCount,
		sizeof (Miscellaneous)));
	if (!m_miscellaneous)
		return AF_FAIL;

	for (int i=0; i<m_miscellaneousCount; i++)
	{
		m_miscellaneous[i].id = setup->miscellaneous[i].id;
		m_miscellaneous[i].type = setup->miscellaneous[i].type;
		m_miscellaneous[i].size = setup->miscellaneous[i].size;
		m_miscellaneous[i].position = 0;
		m_miscellaneous[i].buffer = NULL;
	}

	return AF_SUCCEED;
}

template <typename T>
static bool readValue(File *f, T *value)
{
	return f->read(value, sizeof (*value)) == sizeof (*value);
}

template <typename T>
static bool writeValue(File *f, const T *value)
{
	return f->write(value, sizeof (*value)) == sizeof (*value);
}

template <typename T>
static T swapValue(T value, int order)
{
	if (order == AF_BYTEORDER_BIGENDIAN)
		return bigToHost(value);
	else if (order == AF_BYTEORDER_LITTLEENDIAN)
		return littleToHost(value);
	return value;
}

template <typename T>
static bool readSwap(File *f, T *value, int order)
{
	if (!readValue(f, value)) return false;
	*value = swapValue(*value, order);
	return true;
}

template <typename T>
static bool writeSwap(File *f, const T *value, int order)
{
	T t = swapValue(*value, order);
	return writeValue(f, &t);
}

bool _AFfilehandle::readU8(uint8_t *v) { return readValue(m_fh, v); }
bool _AFfilehandle::readS8(int8_t *v) { return readValue(m_fh, v); }

bool _AFfilehandle::readU16(uint16_t *v)
{
	return readSwap(m_fh, v, m_formatByteOrder);
}

bool _AFfilehandle::readS16(int16_t *v)
{
	return readSwap(m_fh, v, m_formatByteOrder);
}

bool _AFfilehandle::readU32(uint32_t *v)
{
	return readSwap(m_fh, v, m_formatByteOrder);
}

bool _AFfilehandle::readS32(int32_t *v)
{
	return readSwap(m_fh, v, m_formatByteOrder);
}

bool _AFfilehandle::readU64(uint64_t *v)
{
	return readSwap(m_fh, v, m_formatByteOrder);
}

bool _AFfilehandle::readS64(int64_t *v)
{
	return readSwap(m_fh, v, m_formatByteOrder);
}

bool _AFfilehandle::readFloat(float *v)
{
	return readSwap(m_fh, v, m_formatByteOrder);
}

bool _AFfilehandle::readDouble(double *v)
{
	return readSwap(m_fh, v, m_formatByteOrder);
}

bool _AFfilehandle::writeU8(const uint8_t *v) { return writeValue(m_fh, v); }
bool _AFfilehandle::writeS8(const int8_t *v) { return writeValue(m_fh, v); }

bool _AFfilehandle::writeU16(const uint16_t *v)
{
	return writeSwap(m_fh, v, m_formatByteOrder);
}

bool _AFfilehandle::writeS16(const int16_t *v)
{
	return writeSwap(m_fh, v, m_formatByteOrder);
}

bool _AFfilehandle::writeU32(const uint32_t *v)
{
	return writeSwap(m_fh, v, m_formatByteOrder);
}

bool _AFfilehandle::writeS32(const int32_t *v)
{
	return writeSwap(m_fh, v, m_formatByteOrder);
}

bool _AFfilehandle::writeU64(const uint64_t *v)
{
	return writeSwap(m_fh, v, m_formatByteOrder);
}

bool _AFfilehandle::writeS64(const int64_t *v)
{
	return writeSwap(m_fh, v, m_formatByteOrder);
}

bool _AFfilehandle::writeFloat(const float *v)
{
	return writeSwap(m_fh, v, m_formatByteOrder);
}

bool _AFfilehandle::writeDouble(const double *v)
{
	return writeSwap(m_fh, v, m_formatByteOrder);
}

bool _AFfilehandle::readTag(Tag *t)
{
	uint32_t v;
	if (m_fh->read(&v, sizeof (v)) == sizeof (v))
	{
		*t = Tag(v);
		return true;
	}
	return false;
}

bool _AFfilehandle::writeTag(const Tag *t)
{
	uint32_t v = t->value();
	return m_fh->write(&v, sizeof (v)) == sizeof (v);
}

// file: Instrument.cpp
/*
	Audio File Library
	Copyright (C) 1998-2000, Michael Pruett <michael@68k.org>
	Copyright (C) 2000, Silicon Graphics, Inc.

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

/*
	Instrument.cpp

	Info about instrument parameters:

	Each unit has an array of _InstParamInfo structures, one for
	each instrument parameter.  Each of these structures describes
	the inst parameters.

	id: a 4-byte id as in AIFF file
	type: data type AU_PVLIST_*
	name: text name
	defaultValue: default value, to which it is set when a file with
		instruments is first opened for writing.

	Each inst has only an array of values (_AFPVu's).  Each value in the
	instrument's array is the value of the corresponding index into the
	unit's instparaminfo array.

	So for a unit u and an instrument i, u.instparam[N] describes
	the parameter whose value is given in i.value[N].
*/



#include <stdlib.h>

bool InstrumentSetup::allocateLoops(int count)
{
	freeLoops();
	loops = (LoopSetup *) _af_calloc(count, sizeof (LoopSetup));
	if (loops)
	{
		loopCount = count;
		return true;
	}
	return false;
}

void InstrumentSetup::freeLoops()
{
	if (loops)
		free(loops);
	loops = NULL;
	loopCount = 0;
}

/*
	Initialize instrument id list for audio file.
*/
void afInitInstIDs (AFfilesetup setup, const int *instids, int ninsts)
{
	if (!_af_filesetup_ok(setup))
		return;

	if (!_af_unique_ids(instids, ninsts, "instrument", AF_BAD_INSTID))
		return;

	_af_setup_free_instruments(setup);

	setup->instrumentCount = ninsts;
	setup->instrumentSet = true;

	setup->instruments = _af_instsetup_new(setup->instrumentCount);

	for (int i=0; i < setup->instrumentCount; i++)
		setup->instruments[i].id = instids[i];
}

int afGetInstIDs (AFfilehandle file, int *instids)
{
	if (!_af_filehandle_ok(file))
		return -1;

	if (instids)
		for (int i=0; i < file->m_instrumentCount; i++)
			instids[i] = file->m_instruments[i].id;

	return file->m_instrumentCount;
}

/*
	This routine checks and sets instrument parameters.
	npv is number of valid AUpvlist pairs.
*/
void _af_instparam_set (AFfilehandle file, int instid, AUpvlist pvlist, int npv)
{
	if (!_af_filehandle_ok(file))
		return;

	if (!file->checkCanWrite())
		return;

	Instrument *instrument = file->getInstrument(instid);
	if (!instrument)
		return;

	if (AUpvgetmaxitems(pvlist) < npv)
	npv = AUpvgetmaxitems(pvlist);

	for (int i=0; i < npv; i++)
	{
		int	param;

		AUpvgetparam(pvlist, i, &param);

		int j;
		if ((j = _af_instparam_index_from_id(file->m_fileFormat, param)) == -1)
			/* no parameter with that id; ignore */
			continue;

		if (!file->isInstrumentParameterValid(pvlist, i))
			/* bad parameter value; ignore */
			continue;

		int	type = _af_units[file->m_fileFormat].instrumentParameters[j].type;

		switch (type)
		{
			case AU_PVTYPE_LONG:
				AUpvgetval(pvlist, i, &instrument->values[j].l);
				break;
			case AU_PVTYPE_DOUBLE:
				AUpvgetval(pvlist, i, &instrument->values[j].d);
				break;
			case AU_PVTYPE_PTR:
				AUpvgetval(pvlist, i, &instrument->values[j].v);
				break;
			default:
				return;
		}
	}
}

void afSetInstParams (AFfilehandle file, int instid, AUpvlist pvlist, int npv)
{
	_af_instparam_set(file, instid, pvlist, npv);
}

void afSetInstParamLong (AFfilehandle file, int instid, int param, long value)
{
	AUpvlist pvlist = AUpvnew(1);

	AUpvsetparam(pvlist, 0, param);
	AUpvsetvaltype(pvlist, 0, AU_PVTYPE_LONG);
	AUpvsetval(pvlist, 0, &value);

	_af_instparam_set(file, instid, pvlist, 1);

	AUpvfree(pvlist);
}

/*
	This routine gets instrument parameters.
	npv is number of valid AUpvlist pairs
*/
void _af_instparam_get (AFfilehandle file, int instid, AUpvlist pvlist, int npv,
	bool forceLong)
{
	if (!_af_filehandle_ok(file))
		return;

	Instrument *instrument = file->getInstrument(instid);
	if (!instrument)
		return;

	if (AUpvgetmaxitems(pvlist) < npv)
		npv = AUpvgetmaxitems(pvlist);

	for (int i=0; i < npv; i++)
	{
		int param;
		AUpvgetparam(pvlist, i, &param);

		int j;
		if ((j = _af_instparam_index_from_id(file->m_fileFormat, param)) == -1)
			/* no parameter with that id; ignore */
			continue;

		int type = _af_units[file->m_fileFormat].instrumentParameters[j].type;

		/*
			forceLong is true when this routine called by
			afGetInstParamLong().
		*/
		if (forceLong && type != AU_PVTYPE_LONG)
		{
			_af_error(AF_BAD_INSTPTYPE, "type of instrument parameter %d is not AU_PVTYPE_LONG", param);
			continue;
		}

		AUpvsetvaltype(pvlist, i, type);

		switch (type)
		{
			case AU_PVTYPE_LONG:
				AUpvsetval(pvlist, i, &instrument->values[j].l);
				break;
			case AU_PVTYPE_DOUBLE:
				AUpvsetval(pvlist, i, &instrument->values[j].d);
				break;
			case AU_PVTYPE_PTR:
				AUpvsetval(pvlist, i, &instrument->values[j].v);
				break;

			default:
				_af_error(AF_BAD_INSTPTYPE, "invalid instrument parameter type %d", type);
				return;
		}
	}
}

/*
	afGetInstParams -- get a parameter-value array containing
	instrument parameters for the specified instrument chunk
*/
void afGetInstParams (AFfilehandle file, int inst, AUpvlist pvlist, int npv)
{
	_af_instparam_get(file, inst, pvlist, npv, false);
}

long afGetInstParamLong (AFfilehandle file, int inst, int param)
{
	long val;
	AUpvlist pvlist = AUpvnew(1);

	AUpvsetparam(pvlist, 0, param);
	AUpvsetvaltype(pvlist, 0, AU_PVTYPE_LONG);

	_af_instparam_get(file, inst, pvlist, 1, true);

	AUpvgetval(pvlist, 0, &val);
	AUpvfree(pvlist);

	return(val);
}

/*
	Search _af_units[fileFormat].instrumentParameters for the instrument
	parameter with the specified id.

	Report an error and return -1 if no such instrument parameter
	exists.
*/

int _af_instparam_index_from_id (int filefmt, int id)
{
	int i;

	for (i = 0; i < _af_units[filefmt].instrumentParameterCount; i++)
		if (_af_units[filefmt].instrumentParameters[i].id == id)
			break;

	if (i == _af_units[filefmt].instrumentParameterCount)
	{
		_af_error(AF_BAD_INSTPID, "invalid instrument parameter id %d",
			id);
		return -1;
	}

	return i;
}

Loop *Instrument::getLoop(int loopID)
{
	for (int i=0; i<loopCount; i++)
		if (loops[i].id == loopID)
			return &loops[i];

	_af_error(AF_BAD_LOOPID, "no loop with id %d for instrument %d\n",
		loopID, id);
	return NULL;
}

// file: Loop.cpp
/*
	Audio File Library
	Copyright (C) 1998-2000, Michael Pruett <michael@68k.org>
	Copyright (C) 2000, Silicon Graphics, Inc.

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

/*
	Loop.cpp

	All routines that operate on loops.
*/



void afInitLoopIDs (AFfilesetup setup, int instid, const int *loopids, int nloops)
{
	if (!_af_filesetup_ok(setup))
		return;

	if (!_af_unique_ids(loopids, nloops, "loop", AF_BAD_LOOPID))
		return;

	InstrumentSetup *instrument = setup->getInstrument(instid);
	if (!instrument)
		return;

	instrument->freeLoops();
	if (!instrument->allocateLoops(nloops))
		return;

	for (int i=0; i < nloops; i++)
		instrument->loops[i].id = loopids[i];
}

int afGetLoopIDs (AFfilehandle file, int instid, int *loopids)
{
	if (!_af_filehandle_ok(file))
		return AF_FAIL;

	Instrument *instrument = file->getInstrument(instid);
	if (!instrument)
		return AF_FAIL;

	if (loopids)
		for (int i=0; i < instrument->loopCount; i++)
			loopids[i] = instrument->loops[i].id;

	return instrument->loopCount;
}

/*
	getLoop returns pointer to requested loop if it exists, and if
	mustWrite is true, only if handle is writable.
*/

static Loop *getLoop (AFfilehandle handle, int instid, int loopid,
	bool mustWrite)
{
	if (!_af_filehandle_ok(handle))
		return NULL;

	if (mustWrite && !handle->checkCanWrite())
		return NULL;

	Instrument *instrument = handle->getInstrument(instid);
	if (!instrument)
		return NULL;

	return instrument->getLoop(loopid);
}

/*
	Set loop mode (as in AF_LOOP_MODE_...).
*/
void afSetLoopMode (AFfilehandle file, int instid, int loopid, int mode)
{
	Loop *loop = getLoop(file, instid, loopid, true);

	if (!loop)
		return;

	if (mode != AF_LOOP_MODE_NOLOOP &&
		mode != AF_LOOP_MODE_FORW &&
		mode != AF_LOOP_MODE_FORWBAKW)
	{
		_af_error(AF_BAD_LOOPMODE, "unrecognized loop mode %d", mode);
		return;
	}

	loop->mode = mode;
}

/*
	Get loop mode (as in AF_LOOP_MODE_...).
*/
int afGetLoopMode (AFfilehandle file, int instid, int loopid)
{
	Loop *loop = getLoop(file, instid, loopid, false);

	if (!loop)
		return -1;

	return loop->mode;
}

/*
	Set loop count.
*/
int afSetLoopCount (AFfilehandle file, int instid, int loopid, int count)
{
	Loop *loop = getLoop(file, instid, loopid, true);

	if (!loop)
		return AF_FAIL;

	if (count < 1)
	{
		_af_error(AF_BAD_LOOPCOUNT, "invalid loop count: %d", count);
		return AF_FAIL;
	}

	loop->count = count;
	return AF_SUCCEED;
}

/*
	Get loop count.
*/
int afGetLoopCount(AFfilehandle file, int instid, int loopid)
{
	Loop *loop = getLoop(file, instid, loopid, false);

	if (!loop)
		return -1;

	return loop->count;
}

/*
	Set loop start marker id in the file structure
*/
void afSetLoopStart(AFfilehandle file, int instid, int loopid, int markid)
{
	Loop *loop = getLoop(file, instid, loopid, true);

	if (!loop)
		return;

	loop->beginMarker = markid;
}

/*
	Get loop start marker id.
*/
int afGetLoopStart (AFfilehandle file, int instid, int loopid)
{
	Loop *loop = getLoop(file, instid, loopid, false);

	if (!loop)
		return -1;

	return loop->beginMarker;
}

/*
	Set loop start frame in the file structure.
*/
int afSetLoopStartFrame (AFfilehandle file, int instid, int loopid, AFframecount startFrame)
{
	Loop *loop = getLoop(file, instid, loopid, true);

	if (!loop)
		return -1;

	if (startFrame < 0)
	{
		_af_error(AF_BAD_FRAME, "loop start frame must not be negative");
		return AF_FAIL;
	}

	int	trackid = loop->trackid;
	int beginMarker = loop->beginMarker;

	afSetMarkPosition(file, trackid, beginMarker, startFrame);
	return AF_SUCCEED;
}

/*
	Get loop start frame.
*/
AFframecount afGetLoopStartFrame (AFfilehandle file, int instid, int loopid)
{
	Loop *loop = getLoop(file, instid, loopid, false);
	if (!loop)
		return -1;

	int trackid = loop->trackid;
	int beginMarker = loop->beginMarker;

	return afGetMarkPosition(file, trackid, beginMarker);
}

/*
	Set loop track id.
*/
void afSetLoopTrack (AFfilehandle file, int instid, int loopid, int track)
{
	Loop *loop = getLoop(file, instid, loopid, true);

	if (!loop) return;

	loop->trackid = track;
}

/*
	Get loop track.
*/
int afGetLoopTrack (AFfilehandle file, int instid, int loopid)
{
	Loop *loop = getLoop(file, instid, loopid, false);

	if (!loop)
		return -1;

	return loop->trackid;
}

/*
	Set loop end frame marker id.
*/
void afSetLoopEnd (AFfilehandle file, int instid, int loopid, int markid)
{
	Loop *loop = getLoop(file, instid, loopid, true);

	if (!loop)
		return;

	loop->endMarker = markid;
}

/*
	Get loop end frame marker id.
*/
int afGetLoopEnd (AFfilehandle file, int instid, int loopid)
{
	Loop *loop = getLoop(file, instid, loopid, false);

	if (!loop)
		return -1;

	return loop->endMarker;
}

/*
	Set loop end frame.
*/
int afSetLoopEndFrame (AFfilehandle file, int instid, int loopid, AFframecount endFrame)
{
	Loop *loop = getLoop(file, instid, loopid, true);
	if (!loop)
		return -1;

	if (endFrame < 0)
	{
		_af_error(AF_BAD_FRAME, "loop end frame must not be negative");
		return AF_FAIL;
	}

	int trackid = loop->trackid;
	int endMarker = loop->endMarker;

	afSetMarkPosition(file, trackid, endMarker, endFrame);
	return AF_SUCCEED;
}

/*
	Get loop end frame.
*/

AFframecount afGetLoopEndFrame (AFfilehandle file, int instid, int loopid)
{
	Loop *loop = getLoop(file, instid, loopid, false);

	if (!loop)
		return -1;

	int trackid = loop->trackid;
	int endMarker = loop->endMarker;

	return afGetMarkPosition(file, trackid, endMarker);
}

// file: Marker.cpp
/*
	Audio File Library
	Copyright (C) 1998-2000, Michael Pruett <michael@68k.org>
	Copyright (C) 2000, Silicon Graphics, Inc.

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

/*
	Marker.cpp

	This file contains routines for dealing with loop markers.
*/


#include <string.h>
#include <stdlib.h>
#include <assert.h>


void afInitMarkIDs(AFfilesetup setup, int trackid, const int *markids, int nmarks)
{
	if (!_af_filesetup_ok(setup))
		return;

	TrackSetup *track = setup->getTrack(trackid);
	if (!track)
		return;

	if (track->markers != NULL)
	{
		for (int i=0; i<track->markerCount; i++)
		{
			if (track->markers[i].name != NULL)
				free(track->markers[i].name);
			if (track->markers[i].comment != NULL)
				free(track->markers[i].comment);
		}
		free(track->markers);
	}

	track->markers = (MarkerSetup *) _af_calloc(nmarks, sizeof (struct MarkerSetup));
	track->markerCount = nmarks;

	for (int i=0; i<nmarks; i++)
	{
		track->markers[i].id = markids[i];
		track->markers[i].name = _af_strdup("");
		track->markers[i].comment = _af_strdup("");
	}

	track->markersSet = true;
}

void afInitMarkName(AFfilesetup setup, int trackid, int markid,
	const char *namestr)
{
	int	markno;
	int	length;

	if (!_af_filesetup_ok(setup))
		return;

	TrackSetup *track = setup->getTrack(trackid);
	if (!track)
		return;

	for (markno=0; markno<track->markerCount; markno++)
	{
		if (track->markers[markno].id == markid)
			break;
	}

	if (markno == track->markerCount)
	{
		_af_error(AF_BAD_MARKID, "no marker id %d for file setup", markid);
		return;
	}

	length = strlen(namestr);
	if (length > 255)
	{
		_af_error(AF_BAD_STRLEN,
			"warning: marker name truncated to 255 characters");
		length = 255;
	}

	if (track->markers[markno].name)
		free(track->markers[markno].name);
	if ((track->markers[markno].name = (char *) _af_malloc(length+1)) == NULL)
		return;
	strncpy(track->markers[markno].name, namestr, length);
	/*
		The null terminator is not set by strncpy if
		strlen(namestr) > length.  Set it here.
	*/
	track->markers[markno].name[length] = '\0';
}

void afInitMarkComment(AFfilesetup setup, int trackid, int markid,
	const char *commstr)
{
	int	markno;
	int	length;

	if (!_af_filesetup_ok(setup))
		return;

	TrackSetup *track = setup->getTrack(trackid);
	if (!track)
		return;

	for (markno=0; markno<track->markerCount; markno++)
	{
		if (track->markers[markno].id == markid)
			break;
	}

	if (markno == track->markerCount)
	{
		_af_error(AF_BAD_MARKID, "no marker id %d for file setup", markid);
		return;
	}

	length = strlen(commstr);

	if (track->markers[markno].comment)
		free(track->markers[markno].comment);
	if ((track->markers[markno].comment = (char *) _af_malloc(length+1)) == NULL)
		return;
	strcpy(track->markers[markno].comment, commstr);
}

char *afGetMarkName (AFfilehandle file, int trackid, int markid)
{
	if (!_af_filehandle_ok(file))
		return NULL;

	Track *track = file->getTrack(trackid);
	if (!track)
		return NULL;

	Marker *marker = track->getMarker(markid);
	if (!marker)
		return NULL;

	return marker->name;
}

char *afGetMarkComment (AFfilehandle file, int trackid, int markid)
{
	if (!_af_filehandle_ok(file))
		return NULL;

	Track *track = file->getTrack(trackid);
	if (!track)
		return NULL;

	Marker *marker = track->getMarker(markid);
	if (!marker)
		return NULL;

	return marker->comment;
}

void afSetMarkPosition (AFfilehandle file, int trackid, int markid,
	AFframecount position)
{
	if (!_af_filehandle_ok(file))
		return;

	if (!file->checkCanWrite())
		return;

	Track *track = file->getTrack(trackid);
	if (!track)
		return;

	Marker *marker = track->getMarker(markid);
	if (!marker)
		return;

	if (position < 0)
	{
		_af_error(AF_BAD_MARKPOS, "invalid marker position %jd",
			static_cast<intmax_t>(position));
		position = 0;
	}

	marker->position = position;
}

int afGetMarkIDs (AFfilehandle file, int trackid, int markids[])
{
	if (!_af_filehandle_ok(file))
		return -1;

	Track *track = file->getTrack(trackid);
	if (!track)
		return -1;

	if (markids != NULL)
	{
		for (int i=0; i<track->markerCount; i++)
		{
			markids[i] = track->markers[i].id;
		}
	}

	return track->markerCount;
}

AFframecount afGetMarkPosition (AFfilehandle file, int trackid, int markid)
{
	if (!_af_filehandle_ok(file))
		return 0L;

	Track *track = file->getTrack(trackid);
	if (!track)
		return 0L;

	Marker *marker = track->getMarker(markid);
	if (!marker)
		return 0L;

	return marker->position;
}

Marker *_af_marker_new (int count)
{
	Marker	*markers = (Marker *) _af_calloc(count, sizeof (Marker));
	if (markers == NULL)
		return NULL;

	return markers;
}

// file: Miscellaneous.cpp
/*
	Audio File Library
	Copyright (C) 1998, Michael Pruett <michael@68k.org>

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

/*
	Miscellaneous.cpp

	This file contains routines for dealing with the Audio File
	Library's internal miscellaneous data types.
*/


#include <algorithm>
#include <stdint.h>
#include <stdlib.h>
#include <string.h>


void afInitMiscIDs (AFfilesetup setup, const int *ids, int nids)
{
	if (!_af_filesetup_ok(setup))
		return;

	if (setup->miscellaneous != NULL)
	{
		free(setup->miscellaneous);
	}

	setup->miscellaneousCount = nids;

	if (nids == 0)
		setup->miscellaneous = NULL;
	else
	{
		setup->miscellaneous = (MiscellaneousSetup *) _af_calloc(nids,
			sizeof (MiscellaneousSetup));

		if (setup->miscellaneous == NULL)
			return;

		for (int i=0; i<nids; i++)
		{
			setup->miscellaneous[i].id = ids[i];
			setup->miscellaneous[i].type = 0;
			setup->miscellaneous[i].size = 0;
		}
	}

	setup->miscellaneousSet = true;
}

int afGetMiscIDs (AFfilehandle file, int *ids)
{
	if (!_af_filehandle_ok(file))
		return -1;

	if (ids != NULL)
	{
		for (int i=0; i<file->m_miscellaneousCount; i++)
		{
			ids[i] = file->m_miscellaneous[i].id;
		}
	}

	return file->m_miscellaneousCount;
}

void afInitMiscType (AFfilesetup setup, int miscellaneousid, int type)
{
	if (!_af_filesetup_ok(setup))
		return;

	MiscellaneousSetup *miscellaneous = setup->getMiscellaneous(miscellaneousid);
	if (miscellaneous)
		miscellaneous->type = type;
}

int afGetMiscType (AFfilehandle file, int miscellaneousid)
{
	if (!_af_filehandle_ok(file))
		return -1;

	Miscellaneous *miscellaneous = file->getMiscellaneous(miscellaneousid);
	if (miscellaneous)
		return miscellaneous->type;
	return -1;
}

void afInitMiscSize (AFfilesetup setup, int miscellaneousid, int size)
{
	if (!_af_filesetup_ok(setup))
		return;

	MiscellaneousSetup *miscellaneous = setup->getMiscellaneous(miscellaneousid);
	if (miscellaneous)
		miscellaneous->size = size;
}

int afGetMiscSize (AFfilehandle file, int miscellaneousid)
{
	if (!_af_filehandle_ok(file))
		return -1;

	Miscellaneous *miscellaneous = file->getMiscellaneous(miscellaneousid);
	if (miscellaneous)
		return miscellaneous->size;
	return -1;
}

int afWriteMisc (AFfilehandle file, int miscellaneousid, const void *buf, int bytes)
{
	if (!_af_filehandle_ok(file))
		return -1;

	if (!file->checkCanWrite())
		return -1;

	Miscellaneous *miscellaneous = file->getMiscellaneous(miscellaneousid);
	if (!miscellaneous)
		return -1;

	if (bytes <= 0)
	{
		_af_error(AF_BAD_MISCSIZE, "invalid size (%d) for miscellaneous chunk", bytes);
		return -1;
	}

	if (miscellaneous->buffer == NULL && miscellaneous->size != 0)
	{
		miscellaneous->buffer = _af_malloc(miscellaneous->size);
		if (miscellaneous->buffer == NULL)
			return -1;
		memset(miscellaneous->buffer, 0, miscellaneous->size);
	}

	int localsize = std::min(bytes,
		miscellaneous->size - miscellaneous->position);
	memcpy((char *) miscellaneous->buffer + miscellaneous->position,
		buf, localsize);
	miscellaneous->position += localsize;
	return localsize;
}

int afReadMisc (AFfilehandle file, int miscellaneousid, void *buf, int bytes)
{
	if (!_af_filehandle_ok(file))
		return -1;

	if (!file->checkCanRead())
		return -1;

	Miscellaneous *miscellaneous = file->getMiscellaneous(miscellaneousid);
	if (!miscellaneous)
		return -1;

	if (bytes <= 0)
	{
		_af_error(AF_BAD_MISCSIZE, "invalid size (%d) for miscellaneous chunk", bytes);
		return -1;
	}

	int localsize = std::min(bytes,
		miscellaneous->size - miscellaneous->position);
	memcpy(buf, (char *) miscellaneous->buffer + miscellaneous->position,
		localsize);
	miscellaneous->position += localsize;
	return localsize;
}

int afSeekMisc (AFfilehandle file, int miscellaneousid, int offset)
{
	if (!_af_filehandle_ok(file))
		return -1;

	Miscellaneous *miscellaneous = file->getMiscellaneous(miscellaneousid);
	if (!miscellaneous)
		return -1;

	if (offset >= miscellaneous->size)
	{
		_af_error(AF_BAD_MISCSEEK,
			"offset %d too big for miscellaneous chunk %d "
			"(%d data bytes)",
			offset, miscellaneousid, miscellaneous->size);
		return -1;
	}

	miscellaneous->position = offset;

	return offset;
}

// file: PacketTable.cpp
/*
	Audio File Library
	Copyright (C) 2013 Michael Pruett <michael@68k.org>

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/


PacketTable::PacketTable(int64_t numValidFrames, int32_t primingFrames,
	int32_t remainderFrames) :
	m_numValidFrames(numValidFrames),
	m_primingFrames(primingFrames),
	m_remainderFrames(remainderFrames)
{
}

PacketTable::PacketTable()
{
	m_numValidFrames = 0;
	m_primingFrames = 0;
	m_remainderFrames = 0;
}

PacketTable::~PacketTable()
{
}

void PacketTable::setNumValidFrames(int64_t numValidFrames)
{
	m_numValidFrames = numValidFrames;
}

void PacketTable::setPrimingFrames(int32_t primingFrames)
{
	m_primingFrames = primingFrames;
}

void PacketTable::setRemainderFrames(int32_t remainderFrames)
{
	m_remainderFrames = remainderFrames;
}

void PacketTable::append(size_t bytesPerPacket)
{
	m_bytesPerPacket.push_back(bytesPerPacket);
}

AFfileoffset PacketTable::startOfPacket(size_t packet) const
{
	AFfileoffset offset = 0;
	for (size_t i=0; i<packet; i++)
		offset += m_bytesPerPacket[i];
	return offset;
}

// file: Raw.cpp
/*
	Audio File Library
	Copyright (C) 2000, Silicon Graphics, Inc.

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

/*
	Raw.cpp

	This file contains code for reading and writing raw audio
	data files.
*/



static const _AFfilesetup rawDefaultFileSetup =
{
	_AF_VALID_FILESETUP,	// valid
	AF_FILE_RAWDATA,		// fileFormat
	true,	// trackSet
	true,	// instrumentSet
	true,	// miscellaneousSet
	1,		// trackCount
	NULL,	// tracks
	0,		// instrumentCount
	NULL,	// instruments
	0,		// miscellaneousCount
	NULL	// miscellaneous
};

const int _af_raw_compression_types[_AF_RAW_NUM_COMPTYPES] =
{
	AF_COMPRESSION_G711_ULAW,
	AF_COMPRESSION_G711_ALAW
};

bool RawFile::recognize(File *fh)
{
	return false;
}

status RawFile::readInit(AFfilesetup fileSetup)
{
	if (!fileSetup)
	{
		_af_error(AF_BAD_FILESETUP, "a valid AFfilesetup is required for reading raw data");
		return AF_FAIL;
	}

	if (initFromSetup(fileSetup) == AF_FAIL)
		return AF_FAIL;

	TrackSetup *trackSetup = fileSetup->getTrack();
	if (!trackSetup)
		return AF_FAIL;

	Track *track = getTrack();

	/* Set the track's data offset. */
	if (trackSetup->dataOffsetSet)
		track->fpos_first_frame = trackSetup->dataOffset;
	else
		track->fpos_first_frame = 0;

	/* Set the track's frame count. */
	if (trackSetup->frameCountSet)
	{
		track->totalfframes = trackSetup->frameCount;
	}
	else
	{
		AFfileoffset filesize = m_fh->length();
		if (filesize == -1)
			track->totalfframes = -1;
		else
		{
			/* Ensure that the data offset is valid. */
			if (track->fpos_first_frame > filesize)
			{
				_af_error(AF_BAD_FILESETUP, "data offset is larger than file size");
				return AF_FAIL;
			}

			filesize -= track->fpos_first_frame;
			track->totalfframes = filesize / (int) _af_format_frame_size(&track->f, false);
		}
		track->data_size = filesize;
	}

	return AF_SUCCEED;
}

status RawFile::writeInit(AFfilesetup setup)
{
	if (initFromSetup(setup) == AF_FAIL)
		return AF_FAIL;

	TrackSetup *trackSetup = setup->getTrack();
	if (!trackSetup)
		return AF_FAIL;

	Track *track = getTrack();

	if (trackSetup->dataOffsetSet)
		track->fpos_first_frame = trackSetup->dataOffset;
	else
		track->fpos_first_frame = 0;

	return AF_SUCCEED;
}

status RawFile::update()
{
	return AF_SUCCEED;
}

AFfilesetup RawFile::completeSetup(AFfilesetup setup)
{
	AFfilesetup	newSetup;

	if (setup->trackSet && setup->trackCount != 1)
	{
		_af_error(AF_BAD_FILESETUP, "raw file must have exactly one track");
		return AF_NULL_FILESETUP;
	}

	TrackSetup *track = setup->getTrack();
	if (!track)
	{
		_af_error(AF_BAD_FILESETUP, "could not access track in file setup");
		return AF_NULL_FILESETUP;
	}

	if (track->aesDataSet)
	{
		_af_error(AF_BAD_FILESETUP, "raw file cannot have AES data");
		return AF_NULL_FILESETUP;
	}

	if (track->markersSet && track->markerCount != 0)
	{
		_af_error(AF_BAD_NUMMARKS, "raw file cannot have markers");
		return AF_NULL_FILESETUP;
	}

	if (setup->instrumentSet && setup->instrumentCount != 0)
	{
		_af_error(AF_BAD_NUMINSTS, "raw file cannot have instruments");
		return AF_NULL_FILESETUP;
	}

	if (setup->miscellaneousSet && setup->miscellaneousCount != 0)
	{
		_af_error(AF_BAD_NUMMISC, "raw file cannot have miscellaneous data");
		return AF_NULL_FILESETUP;
	}

	newSetup = (_AFfilesetup *) _af_malloc(sizeof (_AFfilesetup));
	*newSetup = rawDefaultFileSetup;

	newSetup->tracks = (TrackSetup *) _af_malloc(sizeof (TrackSetup));
	newSetup->tracks[0] = setup->tracks[0];
	newSetup->tracks[0].f.compressionParams = NULL;

	newSetup->tracks[0].markerCount = 0;
	newSetup->tracks[0].markers = NULL;

	return newSetup;
}

// file: Setup.cpp
/*
	Audio File Library
	Copyright (C) 2000, Silicon Graphics, Inc.

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

/*
	Setup.cpp
*/


#include <stdlib.h>
#include <string.h>


static const _AFfilesetup _af_default_file_setup =
{
	_AF_VALID_FILESETUP,	/* valid */
#if WORDS_BIGENDIAN
	AF_FILE_AIFFC,	/* file format */
#else
	AF_FILE_WAVE,	/* file format */
#endif
	false,		/* trackSet */
	false,		/* instrumentSet */
	false,		/* miscellaneousSet */
	1,		/* trackCount */
	NULL,		/* tracks */
	1,		/* instrumentCount */
	NULL,		/* instruments */
	0,		/* miscellaneousCount */
	NULL		/* miscellaneous */
};

static const InstrumentSetup _af_default_instrumentsetup =
{
	0,		/* id */
	2,		/* loopCount */
	NULL,		/* loops */
	false		/* loopSet */
};

static const TrackSetup _af_default_tracksetup =
{
	0,
	{
		44100.0,
		AF_SAMPFMT_TWOSCOMP,
		16,
		_AF_BYTEORDER_NATIVE,
		{ SLOPE_INT16, 0, MIN_INT16, MAX_INT16 },
		2,
		AF_COMPRESSION_NONE,
		NULL
	},
	false,		/* rateSet */
	false,		/* sampleFormatSet */
	false,		/* sampleWidthSet */
	false,		/* byteOrderSet */
	false,		/* channelCountSet */
	false,		/* compressionSet */
	false,		/* aesDataSet */
	false,		/* markersSet */
	false,		/* dataOffsetSet */
	false,		/* frameCountSet */

	4,		/* markerCount */
	NULL,		/* markers */
	0,		/* dataOffset */
	0		/* frameCount */
};

TrackSetup *_af_tracksetup_new (int trackCount)
{
	TrackSetup	*tracks;

	if (trackCount == 0)
		return NULL;

	tracks = (TrackSetup *) _af_calloc(trackCount, sizeof (TrackSetup));
	if (tracks == NULL)
		return NULL;

	for (int i=0; i<trackCount; i++)
	{
		tracks[i] = _af_default_tracksetup;

		tracks[i].id = AF_DEFAULT_TRACK + i;

		/* XXXmpruett deal with compression */

		_af_set_sample_format(&tracks[i].f, tracks[i].f.sampleFormat,
			tracks[i].f.sampleWidth);

		if (tracks[i].markerCount == 0)
			tracks[i].markers = NULL;
		else
		{
			tracks[i].markers = (MarkerSetup *) _af_calloc(tracks[i].markerCount,
				sizeof (MarkerSetup));

			if (tracks[i].markers == NULL)
				return NULL;

			for (int j=0; j<tracks[i].markerCount; j++)
			{
				tracks[i].markers[j].id = j+1;

				tracks[i].markers[j].name = _af_strdup("");
				if (tracks[i].markers[j].name == NULL)
					return NULL;

				tracks[i].markers[j].comment = _af_strdup("");
				if (tracks[i].markers[j].comment == NULL)
					return NULL;
			}
		}
	}

	return tracks;
}

InstrumentSetup *_af_instsetup_new (int instrumentCount)
{
	InstrumentSetup	*instruments;

	if (instrumentCount == 0)
		return NULL;
	instruments = (InstrumentSetup *) _af_calloc(instrumentCount, sizeof (InstrumentSetup));
	if (instruments == NULL)
		return NULL;

	for (int i=0; i<instrumentCount; i++)
	{
		instruments[i] = _af_default_instrumentsetup;
		instruments[i].id = AF_DEFAULT_INST + i;
		if (instruments[i].loopCount == 0)
			instruments[i].loops = NULL;
		else
		{
			instruments[i].loops = (LoopSetup *) _af_calloc(instruments[i].loopCount, sizeof (LoopSetup));
			if (instruments[i].loops == NULL)
				return NULL;

			for (int j=0; j<instruments[i].loopCount; j++)
				instruments[i].loops[j].id = j+1;
		}
	}

	return instruments;
}

AFfilesetup afNewFileSetup (void)
{
	AFfilesetup	setup;

	setup = (_AFfilesetup *) _af_malloc(sizeof (_AFfilesetup));
	if (setup == NULL) return AF_NULL_FILESETUP;

	*setup = _af_default_file_setup;

	setup->tracks = _af_tracksetup_new(setup->trackCount);

	setup->instruments = _af_instsetup_new(setup->instrumentCount);

	if (setup->miscellaneousCount == 0)
		setup->miscellaneous = NULL;
	else
	{
		setup->miscellaneous = (MiscellaneousSetup *) _af_calloc(setup->miscellaneousCount,
			sizeof (MiscellaneousSetup));
		for (int i=0; i<setup->miscellaneousCount; i++)
		{
			setup->miscellaneous[i].id = i+1;
			setup->miscellaneous[i].type = 0;
			setup->miscellaneous[i].size = 0;
		}
	}

	return setup;
}

/*
	Free the specified track's markers and their subfields.
*/
void _af_setup_free_markers (AFfilesetup setup, int trackno)
{
	if (setup->tracks[trackno].markerCount != 0)
	{
		for (int i=0; i<setup->tracks[trackno].markerCount; i++)
		{
			free(setup->tracks[trackno].markers[i].name);
			free(setup->tracks[trackno].markers[i].comment);
		}

		free(setup->tracks[trackno].markers);
	}

	setup->tracks[trackno].markers = NULL;
	setup->tracks[trackno].markerCount = 0;
}

/*
	Free the specified setup's tracks and their subfields.
*/
void _af_setup_free_tracks (AFfilesetup setup)
{
	if (setup->tracks)
	{
		for (int i=0; i<setup->trackCount; i++)
		{
			_af_setup_free_markers(setup, i);
		}

		free(setup->tracks);
	}

	setup->tracks = NULL;
	setup->trackCount = 0;
}

/*
	Free the specified setup's instruments and their subfields.
*/
void _af_setup_free_instruments (AFfilesetup setup)
{
	if (setup->instruments)
	{
		for (int i=0; i < setup->instrumentCount; i++)
			setup->instruments[i].freeLoops();

		free(setup->instruments);
	}

	setup->instruments = NULL;
	setup->instrumentCount = 0;
}

void afFreeFileSetup (AFfilesetup setup)
{
	if (!_af_filesetup_ok(setup))
		return;

	_af_setup_free_tracks(setup);

	_af_setup_free_instruments(setup);

	if (setup->miscellaneousCount)
	{
		free(setup->miscellaneous);
		setup->miscellaneous = NULL;
		setup->miscellaneousCount = 0;
	}

	memset(setup, 0, sizeof (_AFfilesetup));
	free(setup);
}

void afInitFileFormat (AFfilesetup setup, int filefmt)
{
	if (!_af_filesetup_ok(setup))
		return;

	if (filefmt < 0 || filefmt >= _AF_NUM_UNITS)
	{
		_af_error(AF_BAD_FILEFMT, "unrecognized file format %d",
			filefmt);
		return;
	}

	if (!_af_units[filefmt].implemented)
	{
		_af_error(AF_BAD_NOT_IMPLEMENTED,
			"%s format not currently supported",
			_af_units[filefmt].name);
		return;
	}

	setup->fileFormat = filefmt;
}

void afInitChannels (AFfilesetup setup, int trackid, int channels)
{
	if (!_af_filesetup_ok(setup))
		return;

	TrackSetup *track = setup->getTrack(trackid);
	if (!track)
		return;

	if (channels < 1)
	{
		_af_error(AF_BAD_CHANNELS, "invalid number of channels %d",
			channels);
		return;
	}

	track->f.channelCount = channels;
	track->channelCountSet = true;
}

void afInitSampleFormat (AFfilesetup setup, int trackid, int sampfmt, int sampwidth)
{
	if (!_af_filesetup_ok(setup))
		return;

	TrackSetup *track = setup->getTrack(trackid);
	if (!track)
		return;

	_af_set_sample_format(&track->f, sampfmt, sampwidth);

	track->sampleFormatSet = true;
	track->sampleWidthSet = true;
}

void afInitByteOrder (AFfilesetup setup, int trackid, int byteorder)
{
	if (!_af_filesetup_ok(setup))
		return;

	TrackSetup *track = setup->getTrack(trackid);
	if (!track)
		return;

	if (byteorder != AF_BYTEORDER_BIGENDIAN &&
		byteorder != AF_BYTEORDER_LITTLEENDIAN)
	{
		_af_error(AF_BAD_BYTEORDER, "invalid byte order %d", byteorder);
		return;
	}

	track->f.byteOrder = byteorder;
	track->byteOrderSet = true;
}

void afInitRate (AFfilesetup setup, int trackid, double rate)
{
	if (!_af_filesetup_ok(setup))
		return;

	TrackSetup *track = setup->getTrack(trackid);
	if (!track)
		return;

	if (rate <= 0.0)
	{
		_af_error(AF_BAD_RATE, "invalid sample rate %.30g", rate);
		return;
	}

	track->f.sampleRate = rate;
	track->rateSet = true;
}

/*
	track data: data offset within the file (initialized for raw reading only)
*/
void afInitDataOffset (AFfilesetup setup, int trackid, AFfileoffset offset)
{
	if (!_af_filesetup_ok(setup))
		return;

	TrackSetup *track = setup->getTrack(trackid);
	if (!track)
		return;

	if (offset < 0)
	{
		_af_error(AF_BAD_DATAOFFSET, "invalid data offset %jd",
			static_cast<intmax_t>(offset));
		return;
	}

	track->dataOffset = offset;
	track->dataOffsetSet = true;
}

/*
	track data: data offset within the file (initialized for raw reading only)
*/
void afInitFrameCount (AFfilesetup setup, int trackid, AFfileoffset count)
{
	if (!_af_filesetup_ok(setup))
		return;

	TrackSetup *track = setup->getTrack(trackid);
	if (!track)
		return;

	if (count < 0)
	{
		_af_error(AF_BAD_FRAMECNT, "invalid frame count %jd",
			static_cast<intmax_t>(count));
		return;
	}

	track->frameCount = count;
	track->frameCountSet = true;
}

#define alloccopy(type, n, var, copyfrom) \
{ \
	if (n == 0) \
		var = NULL; \
	else \
	{ \
		if ((var = (type *) _af_calloc(n, sizeof (type))) == NULL) \
			goto fail; \
		memcpy((var), (copyfrom), (n) * sizeof (type)); \
	} \
}

AFfilesetup _af_filesetup_copy (const _AFfilesetup *setup,
	const _AFfilesetup *defaultSetup, bool copyMarks)
{
	AFfilesetup newsetup;
	int instrumentCount, miscellaneousCount, trackCount;

	newsetup = (_AFfilesetup *) _af_malloc(sizeof (_AFfilesetup));
	if (newsetup == AF_NULL_FILESETUP)
		return AF_NULL_FILESETUP;

	*newsetup = *defaultSetup;

	newsetup->tracks = NULL;
	newsetup->instruments = NULL;
	newsetup->miscellaneous = NULL;

	/* Copy tracks. */
	trackCount = setup->trackSet ? setup->trackCount :
		newsetup->trackSet ? newsetup->trackCount : 0;
	alloccopy(TrackSetup, trackCount, newsetup->tracks, setup->tracks);
	newsetup->trackCount = trackCount;

	/* Copy instruments. */
	instrumentCount = setup->instrumentSet ? setup->instrumentCount :
		newsetup->instrumentSet ? newsetup->instrumentCount : 0;
	alloccopy(InstrumentSetup, instrumentCount, newsetup->instruments, setup->instruments);
	newsetup->instrumentCount = instrumentCount;

	/* Copy miscellaneous information. */
	miscellaneousCount = setup->miscellaneousSet ? setup->miscellaneousCount :
		newsetup->miscellaneousSet ? newsetup->miscellaneousCount : 0;
	alloccopy(MiscellaneousSetup, miscellaneousCount, newsetup->miscellaneous, setup->miscellaneous);
	newsetup->miscellaneousCount = miscellaneousCount;

	for (int i=0; i<setup->trackCount; i++)
	{
		TrackSetup	*track = &newsetup->tracks[i];

		/* XXXmpruett set compression information */

		if (!setup->tracks[i].markersSet && !copyMarks)
		{
			track->markers = NULL;
			track->markerCount = 0;
			continue;
		}

		alloccopy(MarkerSetup, setup->tracks[i].markerCount,
			track->markers, setup->tracks[i].markers);
		track->markerCount = setup->tracks[i].markerCount;

		for (int j=0; j<setup->tracks[i].markerCount; j++)
		{
			track->markers[j].name =
				_af_strdup(setup->tracks[i].markers[j].name);
			if (track->markers[j].name == NULL)
				goto fail;

			track->markers[j].comment =
				_af_strdup(setup->tracks[i].markers[j].comment);
			if (track->markers[j].comment == NULL)
				goto fail;
		}
	}

	for (int i=0; i<newsetup->instrumentCount; i++)
	{
		InstrumentSetup	*instrument = &newsetup->instruments[i];
		alloccopy(LoopSetup, setup->instruments[i].loopCount,
			instrument->loops, setup->instruments[i].loops);
	}

	return newsetup;

	fail:
		if (newsetup->miscellaneous)
			free(newsetup->miscellaneous);
		if (newsetup->instruments)
			free(newsetup->instruments);
		if (newsetup->tracks)
			free(newsetup->tracks);
		if (newsetup)
			free(newsetup);

	return AF_NULL_FILESETUP;
}

TrackSetup *_AFfilesetup::getTrack(int trackID)
{
	for (int i=0; i<trackCount; i++)
	{
		if (tracks[i].id == trackID)
			return &tracks[i];
	}

	_af_error(AF_BAD_TRACKID, "bad track id %d", trackID);
	return NULL;
}

InstrumentSetup *_AFfilesetup::getInstrument(int instrumentID)
{
	for (int i=0; i < instrumentCount; i++)
		if (instruments[i].id == instrumentID)
			return &instruments[i];

	_af_error(AF_BAD_INSTID, "invalid instrument id %d", instrumentID);
	return NULL;
}

MiscellaneousSetup *_AFfilesetup::getMiscellaneous(int miscellaneousID)
{
	for (int i=0; i<miscellaneousCount; i++)
	{
		if (miscellaneous[i].id == miscellaneousID)
			return &miscellaneous[i];
	}

	_af_error(AF_BAD_MISCID, "bad miscellaneous id %d", miscellaneousID);

	return NULL;
}

// file: Track.cpp
/*
	Audio File Library
	Copyright (C) 1998, Michael Pruett <michael@68k.org>

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

/*
	track.c

	This file contains functions for dealing with tracks within an
	audio file.
*/


#include <assert.h>
#include <stddef.h>
#include <stdio.h>
#include <string.h>


void afInitTrackIDs (AFfilesetup file, const int *trackids, int trackCount)
{
	assert(file);
	assert(trackids);
	assert(trackCount == 1);
	assert(trackids[0] == AF_DEFAULT_TRACK);
}

int afGetTrackIDs (AFfilehandle file, int *trackids)
{
	assert(file);

	if (trackids != NULL)
		trackids[0] = AF_DEFAULT_TRACK;

	return 1;
}

Track::Track()
{
	id = AF_DEFAULT_TRACK;

	f.compressionParams = NULL;
	v.compressionParams = NULL;

	channelMatrix = NULL;

	markerCount = 0;
	markers = NULL;

	hasAESData = false;
	memset(aesData, 0, 24);

	totalfframes = 0;
	nextfframe = 0;
	frames2ignore = 0;
	fpos_first_frame = 0;
	fpos_next_frame = 0;
	fpos_after_data = 0;
	totalvframes = 0;
	nextvframe = 0;
	data_size = 0;
}

Track::~Track()
{
	if (f.compressionParams)
	{
		AUpvfree(f.compressionParams);
		f.compressionParams = NULL;
	}

	if (v.compressionParams)
	{
		AUpvfree(v.compressionParams);
		v.compressionParams = NULL;
	}

	free(channelMatrix);
	channelMatrix = NULL;

	if (markers)
	{
		for (int j=0; j<markerCount; j++)
		{
			free(markers[j].name);
			markers[j].name = NULL;
			free(markers[j].comment);
			markers[j].comment = NULL;
		}

		free(markers);
		markers = NULL;
	}
}

void Track::print()
{
	fprintf(stderr, "totalfframes %jd\n", (intmax_t) totalfframes);
	fprintf(stderr, "nextfframe %jd\n", (intmax_t) nextfframe);
	fprintf(stderr, "frames2ignore %jd\n", (intmax_t) frames2ignore);
	fprintf(stderr, "fpos_first_frame %jd\n", (intmax_t) fpos_first_frame);
	fprintf(stderr, "fpos_next_frame %jd\n", (intmax_t) fpos_next_frame);
	fprintf(stderr, "fpos_after_data %jd\n", (intmax_t) fpos_after_data);
	fprintf(stderr, "totalvframes %jd\n", (intmax_t) totalvframes);
	fprintf(stderr, "nextvframe %jd\n", (intmax_t) nextvframe);
	fprintf(stderr, "data_size %jd\n", (intmax_t) data_size);
}

Marker *Track::getMarker(int markerID)
{
	for (int i=0; i<markerCount; i++)
		if (markers[i].id == markerID)
			return &markers[i];

	_af_error(AF_BAD_MARKID, "no marker with id %d found in track %d",
		markerID, id);

	return NULL;
}

status Track::copyMarkers(TrackSetup *setup)
{
	if ((markerCount = setup->markerCount) == 0)
	{
		markers = NULL;
		return AF_SUCCEED;
	}

	markers = _af_marker_new(markerCount);
	if (!markers)
		return AF_FAIL;

	for (int i=0; i<markerCount; i++)
	{
		markers[i].id = setup->markers[i].id;
		markers[i].name = _af_strdup(setup->markers[i].name);
		if (!markers[i].name)
			return AF_FAIL;

		markers[i].comment = _af_strdup(setup->markers[i].comment);
		if (!markers[i].comment)
			return AF_FAIL;
		markers[i].position = 0;
	}

	return AF_SUCCEED;
}

void Track::computeTotalFileFrames()
{
	if (f.bytesPerPacket && f.framesPerPacket)
		totalfframes = (data_size / f.bytesPerPacket) * f.framesPerPacket;
}

// file: UUID.cpp
/*
	Copyright (C) 2011, Michael Pruett. All rights reserved.

	Redistribution and use in source and binary forms, with or without
	modification, are permitted provided that the following conditions
	are met:

	1. Redistributions of source code must retain the above copyright
	notice, this list of conditions and the following disclaimer.

	2. Redistributions in binary form must reproduce the above copyright
	notice, this list of conditions and the following disclaimer in the
	documentation and/or other materials provided with the distribution.

	3. The name of the author may not be used to endorse or promote products
	derived from this software without specific prior written permission.

	THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
	IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
	OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
	IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
	INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
	NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
	DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
	THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
	(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
	THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/


#include <stdio.h>
#include <string.h>

bool UUID::operator==(const UUID &u) const
{
	return !memcmp(data, u.data, 16);
}

bool UUID::operator!=(const UUID &u) const
{
	return memcmp(data, u.data, 16) != 0;
}

std::string UUID::name() const
{
	char s[37];
	uint32_t u1 =
		(data[0] << 24) |
		(data[1] << 16) |
		(data[2] << 8) |
		data[3];
	uint16_t u2 =
		(data[4] << 8) |
		data[5];
	uint16_t u3 =
		(data[6] << 8) |
		data[7];
	uint16_t u4 =
		(data[8] << 8) |
		data[9];
	snprintf(s, 37, "%08x-%04x-%04x-%04x-%02x%02x%02x%02x%02x%02x",
		u1, u2, u3, u4,
		data[10], data[11], data[12], data[13], data[14], data[15]);
	return std::string(s);
}

// file: WAVE.cpp
/*
	Audio File Library
	Copyright (C) 1998-2000, 2003-2004, 2010-2013, Michael Pruett <michael@68k.org>
	Copyright (C) 2000-2002, Silicon Graphics, Inc.
	Copyright (C) 2002-2003, Davy Durham

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

/*
	WAVE.cpp

	This file contains code for reading and writing RIFF WAVE format
	sound files.
*/


#include <assert.h>
#include <math.h>
#include <stdint.h>
#include <stdlib.h>
#include <string.h>


/* These constants are from RFC 2361. */
enum
{
	WAVE_FORMAT_UNKNOWN = 0x0000,	/* Microsoft Unknown Wave Format */
	WAVE_FORMAT_PCM = 0x0001,	/* Microsoft PCM Format */
	WAVE_FORMAT_ADPCM = 0x0002,	/* Microsoft ADPCM Format */
	WAVE_FORMAT_IEEE_FLOAT = 0x0003,	/* IEEE Float */
	WAVE_FORMAT_VSELP = 0x0004,	/* Compaq Computer's VSELP */
	WAVE_FORMAT_IBM_CVSD = 0x0005,	/* IBM CVSD */
	WAVE_FORMAT_ALAW = 0x0006,	/* Microsoft ALAW */
	WAVE_FORMAT_MULAW = 0x0007,	/* Microsoft MULAW */
	WAVE_FORMAT_OKI_ADPCM = 0x0010,	/* OKI ADPCM */
	WAVE_FORMAT_DVI_ADPCM = 0x0011,	/* Intel's DVI ADPCM */
	WAVE_FORMAT_MEDIASPACE_ADPCM = 0x0012,	/* Videologic's MediaSpace ADPCM */
	WAVE_FORMAT_SIERRA_ADPCM = 0x0013,	/* Sierra ADPCM */
	WAVE_FORMAT_G723_ADPCM = 0x0014,	/* G.723 ADPCM */
	WAVE_FORMAT_DIGISTD = 0x0015,	/* DSP Solutions' DIGISTD */
	WAVE_FORMAT_DIGIFIX = 0x0016,	/* DSP Solutions' DIGIFIX */
	WAVE_FORMAT_DIALOGIC_OKI_ADPCM = 0x0017,	/* Dialogic OKI ADPCM */
	WAVE_FORMAT_MEDIAVISION_ADPCM = 0x0018,	/* MediaVision ADPCM */
	WAVE_FORMAT_CU_CODEC = 0x0019,	/* HP CU */
	WAVE_FORMAT_YAMAHA_ADPCM = 0x0020,	/* Yamaha ADPCM */
	WAVE_FORMAT_SONARC = 0x0021,	/* Speech Compression's Sonarc */
	WAVE_FORMAT_DSP_TRUESPEECH = 0x0022,	/* DSP Group's True Speech */
	WAVE_FORMAT_ECHOSC1 = 0x0023,	/* Echo Speech's EchoSC1 */
	WAVE_FORMAT_AUDIOFILE_AF36 = 0x0024,	/* Audiofile AF36 */
	WAVE_FORMAT_APTX = 0x0025,	/* APTX */
	WAVE_FORMAT_DOLBY_AC2 = 0x0030,	/* Dolby AC2 */
	WAVE_FORMAT_GSM610 = 0x0031,	/* GSM610 */
	WAVE_FORMAT_MSNAUDIO = 0x0032,	/* MSNAudio */
	WAVE_FORMAT_ANTEX_ADPCME = 0x0033,	/* Antex ADPCME */

	WAVE_FORMAT_MPEG = 0x0050,		/* MPEG */
	WAVE_FORMAT_MPEGLAYER3 = 0x0055,	/* MPEG layer 3 */
	WAVE_FORMAT_LUCENT_G723 = 0x0059,	/* Lucent G.723 */
	WAVE_FORMAT_G726_ADPCM = 0x0064,	/* G.726 ADPCM */
	WAVE_FORMAT_G722_ADPCM = 0x0065,	/* G.722 ADPCM */

	IBM_FORMAT_MULAW = 0x0101,
	IBM_FORMAT_ALAW = 0x0102,
	IBM_FORMAT_ADPCM = 0x0103,

	WAVE_FORMAT_CREATIVE_ADPCM = 0x0200,

	WAVE_FORMAT_EXTENSIBLE = 0xfffe
};

const int _af_wave_compression_types[_AF_WAVE_NUM_COMPTYPES] =
{
	AF_COMPRESSION_G711_ULAW,
	AF_COMPRESSION_G711_ALAW,
	AF_COMPRESSION_IMA,
	AF_COMPRESSION_MS_ADPCM
};

const InstParamInfo _af_wave_inst_params[_AF_WAVE_NUM_INSTPARAMS] =
{
	{ AF_INST_MIDI_BASENOTE, AU_PVTYPE_LONG, "MIDI base note", {60} },
	{ AF_INST_NUMCENTS_DETUNE, AU_PVTYPE_LONG, "Detune in cents", {0} },
	{ AF_INST_MIDI_LOVELOCITY, AU_PVTYPE_LONG, "Low velocity", {1} },
	{ AF_INST_MIDI_HIVELOCITY, AU_PVTYPE_LONG, "High velocity", {127} },
	{ AF_INST_MIDI_LONOTE, AU_PVTYPE_LONG, "Low note", {0} },
	{ AF_INST_MIDI_HINOTE, AU_PVTYPE_LONG, "High note", {127} },
	{ AF_INST_NUMDBS_GAIN, AU_PVTYPE_LONG, "Gain in dB", {0} }
};

static const _AFfilesetup waveDefaultFileSetup =
{
	_AF_VALID_FILESETUP,	/* valid */
	AF_FILE_WAVE,		/* fileFormat */
	true,			/* trackSet */
	true,			/* instrumentSet */
	true,			/* miscellaneousSet  */
	1,			/* trackCount */
	NULL,			/* tracks */
	0,			/* instrumentCount */
	NULL,			/* instruments */
	0,			/* miscellaneousCount */
	NULL			/* miscellaneous */
};

static const UUID _af_wave_guid_pcm =
{{
	0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x10, 0x00,
	0x80, 0x00, 0x00, 0xaa, 0x00, 0x38, 0x9b, 0x71
}};
static const UUID _af_wave_guid_ieee_float =
{{
	0x03, 0x00, 0x00, 0x00, 0x00, 0x00, 0x10, 0x00,
	0x80, 0x00, 0x00, 0xaa, 0x00, 0x38, 0x9b, 0x71
}};
static const UUID _af_wave_guid_ulaw =
{{
	0x07, 0x00, 0x00, 0x00, 0x00, 0x00, 0x10, 0x00,
	0x80, 0x00, 0x00, 0xaa, 0x00, 0x38, 0x9b, 0x71
}};
static const UUID _af_wave_guid_alaw =
{{
	0x06, 0x00, 0x00, 0x00, 0x00, 0x00, 0x10, 0x00,
	0x80, 0x00, 0x00, 0xaa, 0x00, 0x38, 0x9b, 0x71
}};

WAVEFile::WAVEFile()
{
	setFormatByteOrder(AF_BYTEORDER_LITTLEENDIAN);

	m_factOffset = 0;
	m_miscellaneousOffset = 0;
	m_markOffset = 0;
	m_dataSizeOffset = 0;

	m_msadpcmNumCoefficients = 0;
}

status WAVEFile::parseFrameCount(const Tag &id, uint32_t size)
{
	Track *track = getTrack();

	uint32_t totalFrames;
	readU32(&totalFrames);

	track->totalfframes = totalFrames;

	return AF_SUCCEED;
}

status WAVEFile::parseFormat(const Tag &id, uint32_t size)
{
	Track *track = getTrack();

	uint16_t formatTag;
	readU16(&formatTag);
	uint16_t channelCount;
	readU16(&channelCount);
	uint32_t sampleRate;
	readU32(&sampleRate);
	uint32_t averageBytesPerSecond;
	readU32(&averageBytesPerSecond);
	uint16_t blockAlign;
	readU16(&blockAlign);

	if (!channelCount)
	{
		_af_error(AF_BAD_CHANNELS, "invalid file with 0 channels");
		return AF_FAIL;
	}

	track->f.channelCount = channelCount;
	track->f.sampleRate = sampleRate;
	track->f.byteOrder = AF_BYTEORDER_LITTLEENDIAN;

	/* Default to uncompressed audio data. */
	track->f.compressionType = AF_COMPRESSION_NONE;
	track->f.framesPerPacket = 1;

	switch (formatTag)
	{
		case WAVE_FORMAT_PCM:
		{
			uint16_t bitsPerSample;
			readU16(&bitsPerSample);

			track->f.sampleWidth = bitsPerSample;

			if (bitsPerSample == 0 || bitsPerSample > 32)
			{
				_af_error(AF_BAD_WIDTH,
					"bad sample width of %d bits",
					bitsPerSample);
				return AF_FAIL;
			}

			if (bitsPerSample <= 8)
				track->f.sampleFormat = AF_SAMPFMT_UNSIGNED;
			else
				track->f.sampleFormat = AF_SAMPFMT_TWOSCOMP;
		}
		break;

		case WAVE_FORMAT_MULAW:
		case IBM_FORMAT_MULAW:
			track->f.sampleWidth = 16;
			track->f.sampleFormat = AF_SAMPFMT_TWOSCOMP;
			track->f.byteOrder = _AF_BYTEORDER_NATIVE;
			track->f.compressionType = AF_COMPRESSION_G711_ULAW;
			track->f.bytesPerPacket = track->f.channelCount;
			break;

		case WAVE_FORMAT_ALAW:
		case IBM_FORMAT_ALAW:
			track->f.sampleWidth = 16;
			track->f.sampleFormat = AF_SAMPFMT_TWOSCOMP;
			track->f.byteOrder = _AF_BYTEORDER_NATIVE;
			track->f.compressionType = AF_COMPRESSION_G711_ALAW;
			track->f.bytesPerPacket = track->f.channelCount;
			break;

		case WAVE_FORMAT_IEEE_FLOAT:
		{
			uint16_t bitsPerSample;
			readU16(&bitsPerSample);

			if (bitsPerSample == 64)
			{
				track->f.sampleWidth = 64;
				track->f.sampleFormat = AF_SAMPFMT_DOUBLE;
			}
			else
			{
				track->f.sampleWidth = 32;
				track->f.sampleFormat = AF_SAMPFMT_FLOAT;
			}
		}
		break;

		case WAVE_FORMAT_ADPCM:
		{
			uint16_t bitsPerSample, extraByteCount,
					samplesPerBlock, numCoefficients;

			if (track->f.channelCount != 1 &&
				track->f.channelCount != 2)
			{
				_af_error(AF_BAD_CHANNELS,
					"WAVE file with MS ADPCM compression "
					"must have 1 or 2 channels");
			}

			readU16(&bitsPerSample);
			readU16(&extraByteCount);
			readU16(&samplesPerBlock);
			readU16(&numCoefficients);

			/* numCoefficients should be at least 7. */
			assert(numCoefficients >= 7 && numCoefficients <= 255);

			m_msadpcmNumCoefficients = numCoefficients;

			for (int i=0; i<m_msadpcmNumCoefficients; i++)
			{
				readS16(&m_msadpcmCoefficients[i][0]);
				readS16(&m_msadpcmCoefficients[i][1]);
			}

			track->f.sampleWidth = 16;
			track->f.sampleFormat = AF_SAMPFMT_TWOSCOMP;
			track->f.compressionType = AF_COMPRESSION_MS_ADPCM;
			track->f.byteOrder = _AF_BYTEORDER_NATIVE;

			track->f.framesPerPacket = samplesPerBlock;
			track->f.bytesPerPacket = blockAlign;

			// Create the parameter list.
			AUpvlist pv = AUpvnew(2);
			AUpvsetparam(pv, 0, _AF_MS_ADPCM_NUM_COEFFICIENTS);
			AUpvsetvaltype(pv, 0, AU_PVTYPE_LONG);
			long l = m_msadpcmNumCoefficients;
			AUpvsetval(pv, 0, &l);

			AUpvsetparam(pv, 1, _AF_MS_ADPCM_COEFFICIENTS);
			AUpvsetvaltype(pv, 1, AU_PVTYPE_PTR);
			void *v = m_msadpcmCoefficients;
			AUpvsetval(pv, 1, &v);

			track->f.compressionParams = pv;
		}
		break;

		case WAVE_FORMAT_DVI_ADPCM:
		{
			uint16_t bitsPerSample, extraByteCount, samplesPerBlock;

			readU16(&bitsPerSample);
			readU16(&extraByteCount);
			readU16(&samplesPerBlock);

			if (bitsPerSample != 4)
			{
				_af_error(AF_BAD_NOT_IMPLEMENTED,
					"IMA ADPCM compression supports only 4 bits per sample");
			}

			int bytesPerBlock = (samplesPerBlock + 14) / 8 * 4 * channelCount;
			if (bytesPerBlock > blockAlign || (samplesPerBlock % 8) != 1)
			{
				_af_error(AF_BAD_CODEC_CONFIG,
					"Invalid samples per block for IMA ADPCM compression");
			}

			track->f.sampleWidth = 16;
			track->f.sampleFormat = AF_SAMPFMT_TWOSCOMP;
			track->f.compressionType = AF_COMPRESSION_IMA;
			track->f.byteOrder = _AF_BYTEORDER_NATIVE;

			initIMACompressionParams();

			track->f.framesPerPacket = samplesPerBlock;
			track->f.bytesPerPacket = blockAlign;
		}
		break;

		case WAVE_FORMAT_EXTENSIBLE:
		{
			uint16_t bitsPerSample;
			readU16(&bitsPerSample);
			uint16_t extraByteCount;
			readU16(&extraByteCount);
			uint16_t reserved;
			readU16(&reserved);
			uint32_t channelMask;
			readU32(&channelMask);
			UUID subformat;
			readUUID(&subformat);
			if (subformat == _af_wave_guid_pcm)
			{
				track->f.sampleWidth = bitsPerSample;

				if (bitsPerSample == 0 || bitsPerSample > 32)
				{
					_af_error(AF_BAD_WIDTH,
						"bad sample width of %d bits",
						bitsPerSample);
					return AF_FAIL;
				}

				// Use valid bits per sample if bytes per sample is the same.
				if (reserved <= bitsPerSample &&
					(reserved + 7) / 8 == (bitsPerSample + 7) / 8)
					track->f.sampleWidth = reserved;

				if (bitsPerSample <= 8)
					track->f.sampleFormat = AF_SAMPFMT_UNSIGNED;
				else
					track->f.sampleFormat = AF_SAMPFMT_TWOSCOMP;
			}
			else if (subformat == _af_wave_guid_ieee_float)
			{
				if (bitsPerSample == 64)
				{
					track->f.sampleWidth = 64;
					track->f.sampleFormat = AF_SAMPFMT_DOUBLE;
				}
				else
				{
					track->f.sampleWidth = 32;
					track->f.sampleFormat = AF_SAMPFMT_FLOAT;
				}
			}
			else if (subformat == _af_wave_guid_alaw ||
				subformat == _af_wave_guid_ulaw)
			{
				track->f.compressionType = subformat == _af_wave_guid_alaw ?
					AF_COMPRESSION_G711_ALAW : AF_COMPRESSION_G711_ULAW;
				track->f.sampleWidth = 16;
				track->f.sampleFormat = AF_SAMPFMT_TWOSCOMP;
				track->f.byteOrder = _AF_BYTEORDER_NATIVE;
				track->f.bytesPerPacket = channelCount;
			}
			else
			{
				_af_error(AF_BAD_NOT_IMPLEMENTED, "WAVE extensible data format %s is not currently supported", subformat.name().c_str());
				return AF_FAIL;
			}
		}
		break;

		case WAVE_FORMAT_YAMAHA_ADPCM:
		case WAVE_FORMAT_OKI_ADPCM:
		case WAVE_FORMAT_CREATIVE_ADPCM:
		case IBM_FORMAT_ADPCM:
			_af_error(AF_BAD_NOT_IMPLEMENTED, "WAVE ADPCM data format 0x%x is not currently supported", formatTag);
			return AF_FAIL;
			break;

		case WAVE_FORMAT_MPEG:
			_af_error(AF_BAD_NOT_IMPLEMENTED, "WAVE MPEG data format is not supported");
			return AF_FAIL;
			break;

		case WAVE_FORMAT_MPEGLAYER3:
			_af_error(AF_BAD_NOT_IMPLEMENTED, "WAVE MPEG layer 3 data format is not supported");
			return AF_FAIL;
			break;

		default:
			_af_error(AF_BAD_NOT_IMPLEMENTED, "WAVE file data format 0x%x not currently supported != 0xfffe ? %d, != EXTENSIBLE? %d", formatTag, formatTag != 0xfffe, formatTag != WAVE_FORMAT_EXTENSIBLE);
			return AF_FAIL;
			break;
	}

	if (track->f.isUncompressed())
		track->f.computeBytesPerPacketPCM();

	_af_set_sample_format(&track->f, track->f.sampleFormat, track->f.sampleWidth);

	return AF_SUCCEED;
}

status WAVEFile::parseData(const Tag &id, uint32_t size)
{
	Track *track = getTrack();

	track->fpos_first_frame = m_fh->tell();
	track->data_size = size;

	return AF_SUCCEED;
}

status WAVEFile::parsePlayList(const Tag &id, uint32_t size)
{
	uint32_t segmentCount;
	readU32(&segmentCount);

	if (segmentCount == 0)
	{
		m_instrumentCount = 0;
		m_instruments = NULL;
		return AF_SUCCEED;
	}

	for (unsigned segment=0; segment<segmentCount; segment++)
	{
		uint32_t startMarkID, loopLength, loopCount;

		readU32(&startMarkID);
		readU32(&loopLength);
		readU32(&loopCount);
	}

	return AF_SUCCEED;
}

status WAVEFile::parseCues(const Tag &id, uint32_t size)
{
	Track *track = getTrack();

	uint32_t markerCount;
	readU32(&markerCount);
	track->markerCount = markerCount;

	if (markerCount == 0)
	{
		track->markers = NULL;
		return AF_SUCCEED;
	}

	if ((track->markers = _af_marker_new(markerCount)) == NULL)
		return AF_FAIL;

	for (unsigned i=0; i<markerCount; i++)
	{
		uint32_t id, position, chunkid;
		uint32_t chunkByteOffset, blockByteOffset;
		uint32_t sampleFrameOffset;
		Marker *marker = &track->markers[i];

		readU32(&id);
		readU32(&position);
		readU32(&chunkid);
		readU32(&chunkByteOffset);
		readU32(&blockByteOffset);

		/*
			sampleFrameOffset represents the position of
			the mark in units of frames.
		*/
		readU32(&sampleFrameOffset);

		marker->id = id;
		marker->position = sampleFrameOffset;
		marker->name = _af_strdup("");
		marker->comment = _af_strdup("");
	}

	return AF_SUCCEED;
}

/* Parse an adtl sub-chunk within a LIST chunk. */
status WAVEFile::parseADTLSubChunk(const Tag &id, uint32_t size)
{
	Track *track = getTrack();

	AFfileoffset endPos = m_fh->tell() + size;

	while (m_fh->tell() < endPos)
	{
		Tag chunkID;
		uint32_t chunkSize;

		readTag(&chunkID);
		readU32(&chunkSize);

		if (chunkID == "labl" || chunkID == "note")
		{
			uint32_t id;
			long length=chunkSize-4;
			char *p = (char *) _af_malloc(length);

			readU32(&id);
			m_fh->read(p, length);

			Marker *marker = track->getMarker(id);

			if (marker)
			{
				if (chunkID == "labl")
				{
					free(marker->name);
					marker->name = p;
				}
				else if (chunkID == "note")
				{
					free(marker->comment);
					marker->comment = p;
				}
				else
					free(p);
			}
			else
				free(p);

			/*
				If chunkSize is odd, skip an extra byte
				at the end of the chunk.
			*/
			if ((chunkSize % 2) != 0)
				m_fh->seek(1, File::SeekFromCurrent);
		}
		else
		{
			/* If chunkSize is odd, skip an extra byte. */
			m_fh->seek(chunkSize + (chunkSize % 2), File::SeekFromCurrent);
		}
	}
	return AF_SUCCEED;
}

/* Parse an INFO sub-chunk within a LIST chunk. */
status WAVEFile::parseINFOSubChunk(const Tag &id, uint32_t size)
{
	AFfileoffset endPos = m_fh->tell() + size;

	while (m_fh->tell() < endPos)
	{
		int misctype = AF_MISC_UNRECOGNIZED;
		Tag miscid;
		uint32_t miscsize;

		readTag(&miscid);
		readU32(&miscsize);

		if (miscid == "IART")
			misctype = AF_MISC_AUTH;
		else if (miscid == "INAM")
			misctype = AF_MISC_NAME;
		else if (miscid == "ICOP")
			misctype = AF_MISC_COPY;
		else if (miscid == "ICMT")
			misctype = AF_MISC_ICMT;
		else if (miscid == "ICRD")
			misctype = AF_MISC_ICRD;
		else if (miscid == "ISFT")
			misctype = AF_MISC_ISFT;

		if (misctype != AF_MISC_UNRECOGNIZED)
		{
			char *string = (char *) _af_malloc(miscsize);

			m_fh->read(string, miscsize);

			m_miscellaneousCount++;
			m_miscellaneous = (Miscellaneous *) _af_realloc(m_miscellaneous, sizeof (Miscellaneous) * m_miscellaneousCount);

			m_miscellaneous[m_miscellaneousCount-1].id = m_miscellaneousCount;
			m_miscellaneous[m_miscellaneousCount-1].type = misctype;
			m_miscellaneous[m_miscellaneousCount-1].size = miscsize;
			m_miscellaneous[m_miscellaneousCount-1].position = 0;
			m_miscellaneous[m_miscellaneousCount-1].buffer = string;
		}
		else
		{
			m_fh->seek(miscsize, File::SeekFromCurrent);
		}

		/* Make the current position an even number of bytes.  */
		if (miscsize % 2 != 0)
			m_fh->seek(1, File::SeekFromCurrent);
	}
	return AF_SUCCEED;
}

status WAVEFile::parseList(const Tag &id, uint32_t size)
{
	Tag typeID;
	readTag(&typeID);
	size-=4;

	if (typeID == "adtl")
	{
		/* Handle adtl sub-chunks. */
		return parseADTLSubChunk(typeID, size);
	}
	else if (typeID == "INFO")
	{
		/* Handle INFO sub-chunks. */
		return parseINFOSubChunk(typeID, size);
	}
	else
	{
		/* Skip unhandled sub-chunks. */
		m_fh->seek(size, File::SeekFromCurrent);
		return AF_SUCCEED;
	}
	return AF_SUCCEED;
}

status WAVEFile::parseInstrument(const Tag &id, uint32_t size)
{
	uint8_t baseNote;
	int8_t detune, gain;
	uint8_t lowNote, highNote, lowVelocity, highVelocity;
	uint8_t padByte;

	readU8(&baseNote);
	readS8(&detune);
	readS8(&gain);
	readU8(&lowNote);
	readU8(&highNote);
	readU8(&lowVelocity);
	readU8(&highVelocity);
	readU8(&padByte);

	return AF_SUCCEED;
}

bool WAVEFile::recognize(File *fh)
{
	uint8_t buffer[8];

	fh->seek(0, File::SeekFromBeginning);

	if (fh->read(buffer, 8) != 8 || memcmp(buffer, "RIFF", 4) != 0)
		return false;
	if (fh->read(buffer, 4) != 4 || memcmp(buffer, "WAVE", 4) != 0)
		return false;

	return true;
}

status WAVEFile::readInit(AFfilesetup setup)
{
	Tag type, formtype;
	uint32_t size;
	uint32_t index = 0;

	bool hasFormat = false;
	bool hasData = false;
	bool hasFrameCount = false;

	Track *track = allocateTrack();

	m_fh->seek(0, File::SeekFromBeginning);

	readTag(&type);
	readU32(&size);
	readTag(&formtype);

	assert(type == "RIFF");
	assert(formtype == "WAVE");

	/* Include the offset of the form type. */
	index += 4;

	while (index < size)
	{
		Tag chunkid;
		uint32_t chunksize = 0;
		status result;

		readTag(&chunkid);
		readU32(&chunksize);

		if (chunkid == "fmt ")
		{
			result = parseFormat(chunkid, chunksize);
			if (result == AF_FAIL)
				return AF_FAIL;

			hasFormat = true;
		}
		else if (chunkid == "data")
		{
			/* The format chunk must precede the data chunk. */
			if (!hasFormat)
			{
				_af_error(AF_BAD_HEADER, "missing format chunk in WAVE file");
				return AF_FAIL;
			}

			result = parseData(chunkid, chunksize);
			if (result == AF_FAIL)
				return AF_FAIL;

			hasData = true;
		}
		else if (chunkid == "inst")
		{
			result = parseInstrument(chunkid, chunksize);
			if (result == AF_FAIL)
				return AF_FAIL;
		}
		else if (chunkid == "fact")
		{
			hasFrameCount = true;
			result = parseFrameCount(chunkid, chunksize);
			if (result == AF_FAIL)
				return AF_FAIL;
		}
		else if (chunkid == "cue ")
		{
			result = parseCues(chunkid, chunksize);
			if (result == AF_FAIL)
				return AF_FAIL;
		}
		else if (chunkid == "LIST" || chunkid == "list")
		{
			result = parseList(chunkid, chunksize);
			if (result == AF_FAIL)
				return AF_FAIL;
		}
		else if (chunkid == "INST")
		{
			result = parseInstrument(chunkid, chunksize);
			if (result == AF_FAIL)
				return AF_FAIL;
		}
		else if (chunkid == "plst")
		{
			result = parsePlayList(chunkid, chunksize);
			if (result == AF_FAIL)
				return AF_FAIL;
		}

		index += chunksize + 8;

		/* All chunks must be aligned on an even number of bytes */
		if ((index % 2) != 0)
			index++;

		m_fh->seek(index + 8, File::SeekFromBeginning);
	}

	/* The format chunk and the data chunk are required. */
	if (!hasFormat || !hasData)
	{
		return AF_FAIL;
	}

	/*
		At this point we know that the file has a format chunk and a
		data chunk, so we can assume that track->f and track->data_size
		have been initialized.
	*/
	if (!hasFrameCount)
	{
		if (track->f.bytesPerPacket && track->f.framesPerPacket)
		{
			track->computeTotalFileFrames();
		}
		else
		{
			_af_error(AF_BAD_HEADER, "Frame count required but not found");
			return AF_FAIL;
		}
	}

	return AF_SUCCEED;
}

AFfilesetup WAVEFile::completeSetup(AFfilesetup setup)
{
	if (setup->trackSet && setup->trackCount != 1)
	{
		_af_error(AF_BAD_NUMTRACKS, "WAVE file must have 1 track");
		return AF_NULL_FILESETUP;
	}

	TrackSetup *track = setup->getTrack();
	if (!track)
		return AF_NULL_FILESETUP;

	if (track->f.isCompressed())
	{
		if (!track->sampleFormatSet)
			_af_set_sample_format(&track->f, AF_SAMPFMT_TWOSCOMP, 16);
		else
			_af_set_sample_format(&track->f, track->f.sampleFormat, track->f.sampleWidth);
	}
	else if (track->sampleFormatSet)
	{
		switch (track->f.sampleFormat)
		{
			case AF_SAMPFMT_FLOAT:
				if (track->sampleWidthSet &&
					track->f.sampleWidth != 32)
				{
					_af_error(AF_BAD_WIDTH,
						"Warning: invalid sample width for floating-point WAVE file: %d (must be 32 bits)\n",
						track->f.sampleWidth);
					_af_set_sample_format(&track->f, AF_SAMPFMT_FLOAT, 32);
				}
				break;

			case AF_SAMPFMT_DOUBLE:
				if (track->sampleWidthSet &&
					track->f.sampleWidth != 64)
				{
					_af_error(AF_BAD_WIDTH,
						"Warning: invalid sample width for double-precision floating-point WAVE file: %d (must be 64 bits)\n",
						track->f.sampleWidth);
					_af_set_sample_format(&track->f, AF_SAMPFMT_DOUBLE, 64);
				}
				break;

			case AF_SAMPFMT_UNSIGNED:
				if (track->sampleWidthSet)
				{
					if (track->f.sampleWidth < 1 || track->f.sampleWidth > 32)
					{
						_af_error(AF_BAD_WIDTH, "invalid sample width for WAVE file: %d (must be 1-32 bits)\n", track->f.sampleWidth);
						return AF_NULL_FILESETUP;
					}
					if (track->f.sampleWidth > 8)
					{
						_af_error(AF_BAD_SAMPFMT, "WAVE integer data of more than 8 bits must be two's complement signed");
						_af_set_sample_format(&track->f, AF_SAMPFMT_TWOSCOMP, track->f.sampleWidth);
					}
				}
				else
				/*
					If the sample width is not set but the user requests
					unsigned data, set the width to 8 bits.
				*/
					_af_set_sample_format(&track->f, track->f.sampleFormat, 8);
				break;

			case AF_SAMPFMT_TWOSCOMP:
				if (track->sampleWidthSet)
				{
					if (track->f.sampleWidth < 1 || track->f.sampleWidth > 32)
					{
						_af_error(AF_BAD_WIDTH, "invalid sample width %d for WAVE file (must be 1-32)", track->f.sampleWidth);
						return AF_NULL_FILESETUP;
					}
					else if (track->f.sampleWidth <= 8)
					{
						_af_error(AF_BAD_SAMPFMT, "Warning: WAVE format integer data of 1-8 bits must be unsigned; setting sample format to unsigned");
						_af_set_sample_format(&track->f, AF_SAMPFMT_UNSIGNED, track->f.sampleWidth);
					}
				}
				else
				/*
					If no sample width was specified, we default to 16 bits
					for signed integer data.
				*/
					_af_set_sample_format(&track->f, track->f.sampleFormat, 16);
				break;
		}
	}
	/*
		Otherwise set the sample format depending on the sample
		width or set completely to default.
	*/
	else
	{
		if (!track->sampleWidthSet)
		{
			track->f.sampleWidth = 16;
			track->f.sampleFormat = AF_SAMPFMT_TWOSCOMP;
		}
		else
		{
			if (track->f.sampleWidth < 1 || track->f.sampleWidth > 32)
			{
				_af_error(AF_BAD_WIDTH, "invalid sample width %d for WAVE file (must be 1-32)", track->f.sampleWidth);
				return AF_NULL_FILESETUP;
			}
			else if (track->f.sampleWidth > 8)
				/* Here track->f.sampleWidth is in {1..32}. */
				track->f.sampleFormat = AF_SAMPFMT_TWOSCOMP;
			else
				/* Here track->f.sampleWidth is in {1..8}. */
				track->f.sampleFormat = AF_SAMPFMT_UNSIGNED;
		}
	}

	if (track->f.compressionType != AF_COMPRESSION_NONE &&
		track->f.compressionType != AF_COMPRESSION_G711_ULAW &&
		track->f.compressionType != AF_COMPRESSION_G711_ALAW &&
		track->f.compressionType != AF_COMPRESSION_IMA &&
		track->f.compressionType != AF_COMPRESSION_MS_ADPCM)
	{
		_af_error(AF_BAD_NOT_IMPLEMENTED, "compression format not supported in WAVE format");
		return AF_NULL_FILESETUP;
	}

	if (track->f.isUncompressed() &&
		track->byteOrderSet &&
		track->f.byteOrder != AF_BYTEORDER_LITTLEENDIAN &&
		track->f.isByteOrderSignificant())
	{
		_af_error(AF_BAD_BYTEORDER, "WAVE format only supports little-endian data");
		return AF_NULL_FILESETUP;
	}

	if (track->f.isUncompressed())
		track->f.byteOrder = AF_BYTEORDER_LITTLEENDIAN;

	if (track->aesDataSet)
	{
		_af_error(AF_BAD_FILESETUP, "WAVE files cannot have AES data");
		return AF_NULL_FILESETUP;
	}

	if (setup->instrumentSet)
	{
		if (setup->instrumentCount > 1)
		{
			_af_error(AF_BAD_NUMINSTS, "WAVE files can have 0 or 1 instrument");
			return AF_NULL_FILESETUP;
		}
		else if (setup->instrumentCount == 1)
		{
			if (setup->instruments[0].loopSet &&
				setup->instruments[0].loopCount > 0 &&
				(!track->markersSet || track->markerCount == 0))
			{
				_af_error(AF_BAD_NUMMARKS, "WAVE files with loops must contain at least 1 marker");
				return AF_NULL_FILESETUP;
			}
		}
	}

	/* Make sure the miscellaneous data is of an acceptable type. */
	if (setup->miscellaneousSet)
	{
		for (int i=0; i<setup->miscellaneousCount; i++)
		{
			switch (setup->miscellaneous[i].type)
			{
				case AF_MISC_COPY:
				case AF_MISC_AUTH:
				case AF_MISC_NAME:
				case AF_MISC_ICRD:
				case AF_MISC_ISFT:
				case AF_MISC_ICMT:
					break;
				default:
					_af_error(AF_BAD_MISCTYPE, "illegal miscellaneous type [%d] for WAVE file", setup->miscellaneous[i].type);
					return AF_NULL_FILESETUP;
			}
		}
	}

	/*
		Allocate an AFfilesetup and make all the unset fields correct.
	*/
	AFfilesetup	newsetup = _af_filesetup_copy(setup, &waveDefaultFileSetup, false);

	/* Make sure we do not copy loops if they are not specified in setup. */
	if (setup->instrumentSet && setup->instrumentCount > 0 &&
		setup->instruments[0].loopSet)
	{
		free(newsetup->instruments[0].loops);
		newsetup->instruments[0].loopCount = 0;
	}

	return newsetup;
}

bool WAVEFile::isInstrumentParameterValid(AUpvlist list, int i)
{
	int param, type;

	AUpvgetparam(list, i, &param);
	AUpvgetvaltype(list, i, &type);
	if (type != AU_PVTYPE_LONG)
		return false;

	long lval;
	AUpvgetval(list, i, &lval);

	switch (param)
	{
		case AF_INST_MIDI_BASENOTE:
			return ((lval >= 0) && (lval <= 127));

		case AF_INST_NUMCENTS_DETUNE:
			return ((lval >= -50) && (lval <= 50));

		case AF_INST_MIDI_LOVELOCITY:
			return ((lval >= 1) && (lval <= 127));

		case AF_INST_MIDI_HIVELOCITY:
			return ((lval >= 1) && (lval <= 127));

		case AF_INST_MIDI_LONOTE:
			return ((lval >= 0) && (lval <= 127));

		case AF_INST_MIDI_HINOTE:
			return ((lval >= 0) && (lval <= 127));

		case AF_INST_NUMDBS_GAIN:
			return true;

		default:
			return false;
	}

	return true;
}

status WAVEFile::writeFormat()
{
	uint16_t	formatTag, channelCount;
	uint32_t	sampleRate, averageBytesPerSecond;
	uint16_t	blockAlign;
	uint32_t	chunkSize;
	uint16_t	bitsPerSample;

	Track *track = getTrack();

	m_fh->write("fmt ", 4);

	switch (track->f.compressionType)
	{
		case AF_COMPRESSION_NONE:
			chunkSize = 16;
			if (track->f.sampleFormat == AF_SAMPFMT_FLOAT ||
				track->f.sampleFormat == AF_SAMPFMT_DOUBLE)
			{
				formatTag = WAVE_FORMAT_IEEE_FLOAT;
			}
			else if (track->f.sampleFormat == AF_SAMPFMT_TWOSCOMP ||
				track->f.sampleFormat == AF_SAMPFMT_UNSIGNED)
			{
				formatTag = WAVE_FORMAT_PCM;
			}
			else
			{
				_af_error(AF_BAD_COMPTYPE, "bad sample format");
				return AF_FAIL;
			}

			blockAlign = _af_format_frame_size(&track->f, false);
			bitsPerSample = 8 * _af_format_sample_size(&track->f, false);
			break;

		/*
			G.711 compression uses eight bits per sample.
		*/
		case AF_COMPRESSION_G711_ULAW:
			chunkSize = 18;
			formatTag = IBM_FORMAT_MULAW;
			blockAlign = track->f.channelCount;
			bitsPerSample = 8;
			break;

		case AF_COMPRESSION_G711_ALAW:
			chunkSize = 18;
			formatTag = IBM_FORMAT_ALAW;
			blockAlign = track->f.channelCount;
			bitsPerSample = 8;
			break;

		case AF_COMPRESSION_IMA:
			chunkSize = 20;
			formatTag = WAVE_FORMAT_DVI_ADPCM;
			blockAlign = track->f.bytesPerPacket;
			bitsPerSample = 4;
			break;

		case AF_COMPRESSION_MS_ADPCM:
			chunkSize = 50;
			formatTag = WAVE_FORMAT_ADPCM;
			blockAlign = track->f.bytesPerPacket;
			bitsPerSample = 4;
			break;

		default:
			_af_error(AF_BAD_COMPTYPE, "bad compression type");
			return AF_FAIL;
	}

	writeU32(&chunkSize);
	writeU16(&formatTag);

	channelCount = track->f.channelCount;
	writeU16(&channelCount);

	sampleRate = track->f.sampleRate;
	writeU32(&sampleRate);

	averageBytesPerSecond =
		track->f.sampleRate * _af_format_frame_size(&track->f, false);
	if (track->f.compressionType == AF_COMPRESSION_IMA ||
		track->f.compressionType == AF_COMPRESSION_MS_ADPCM)
		averageBytesPerSecond = track->f.sampleRate * track->f.bytesPerPacket /
			track->f.framesPerPacket;
	writeU32(&averageBytesPerSecond);

	writeU16(&blockAlign);

	writeU16(&bitsPerSample);

	if (track->f.compressionType == AF_COMPRESSION_G711_ULAW ||
		track->f.compressionType == AF_COMPRESSION_G711_ALAW)
	{
		uint16_t zero = 0;
		writeU16(&zero);
	}
	else if (track->f.compressionType == AF_COMPRESSION_IMA)
	{
		uint16_t extraByteCount = 2;
		writeU16(&extraByteCount);
		uint16_t samplesPerBlock = track->f.framesPerPacket;
		writeU16(&samplesPerBlock);
	}
	else if (track->f.compressionType == AF_COMPRESSION_MS_ADPCM)
	{
		uint16_t extraByteCount = 2 + 2 + m_msadpcmNumCoefficients * 4;
		writeU16(&extraByteCount);
		uint16_t samplesPerBlock = track->f.framesPerPacket;
		writeU16(&samplesPerBlock);

		uint16_t numCoefficients = m_msadpcmNumCoefficients;
		writeU16(&numCoefficients);

		for (int i=0; i<m_msadpcmNumCoefficients; i++)
		{
			writeS16(&m_msadpcmCoefficients[i][0]);
			writeS16(&m_msadpcmCoefficients[i][1]);
		}
	}

	return AF_SUCCEED;
}

status WAVEFile::writeFrameCount()
{
	uint32_t factSize = 4;
	uint32_t totalFrameCount;

	Track *track = getTrack();

	/* Omit the fact chunk only for uncompressed integer audio formats. */
	if (track->f.compressionType == AF_COMPRESSION_NONE &&
		(track->f.sampleFormat == AF_SAMPFMT_TWOSCOMP ||
		track->f.sampleFormat == AF_SAMPFMT_UNSIGNED))
		return AF_SUCCEED;

	/*
		If the offset for the fact chunk hasn't been set yet,
		set it to the file's current position.
	*/
	if (m_factOffset == 0)
		m_factOffset = m_fh->tell();
	else
		m_fh->seek(m_factOffset, File::SeekFromBeginning);

	m_fh->write("fact", 4);
	writeU32(&factSize);

	totalFrameCount = track->totalfframes;
	writeU32(&totalFrameCount);

	return AF_SUCCEED;
}

status WAVEFile::writeData()
{
	Track *track = getTrack();

	m_fh->write("data", 4);
	m_dataSizeOffset = m_fh->tell();

	uint32_t chunkSize = track->data_size;

	writeU32(&chunkSize);
	track->fpos_first_frame = m_fh->tell();

	return AF_SUCCEED;
}

status WAVEFile::update()
{
	Track *track = getTrack();

	if (track->fpos_first_frame != 0)
	{
		uint32_t dataLength, fileLength;

		// Update the frame count chunk if present.
		writeFrameCount();

		// Update the length of the data chunk.
		m_fh->seek(m_dataSizeOffset, File::SeekFromBeginning);
		dataLength = (uint32_t) track->data_size;
		writeU32(&dataLength);

		// Update the length of the RIFF chunk.
		fileLength = (uint32_t) m_fh->length();
		fileLength -= 8;

		m_fh->seek(4, File::SeekFromBeginning);
		writeU32(&fileLength);
	}

	/*
		Write the actual data that was set after initializing
		the miscellaneous IDs.	The size of the data will be
		unchanged.
	*/
	writeMiscellaneous();

	// Write the new positions; the size of the data will be unchanged.
	writeCues();

	return AF_SUCCEED;
}

/* Convert an Audio File Library miscellaneous type to a WAVE type. */
static bool misc_type_to_wave (int misctype, Tag *miscid)
{
	if (misctype == AF_MISC_AUTH)
		*miscid = "IART";
	else if (misctype == AF_MISC_NAME)
		*miscid = "INAM";
	else if (misctype == AF_MISC_COPY)
		*miscid = "ICOP";
	else if (misctype == AF_MISC_ICMT)
		*miscid = "ICMT";
	else if (misctype == AF_MISC_ICRD)
		*miscid = "ICRD";
	else if (misctype == AF_MISC_ISFT)
		*miscid = "ISFT";
	else
		return false;

	return true;
}

status WAVEFile::writeMiscellaneous()
{
	if (m_miscellaneousCount != 0)
	{
		uint32_t	miscellaneousBytes;
		uint32_t 	chunkSize;

		/* Start at 12 to account for 'LIST', size, and 'INFO'. */
		miscellaneousBytes = 12;

		/* Then calculate the size of the whole INFO chunk. */
		for (int i=0; i<m_miscellaneousCount; i++)
		{
			Tag miscid;

			// Skip miscellaneous data of an unsupported type.
			if (!misc_type_to_wave(m_miscellaneous[i].type, &miscid))
				continue;

			// Account for miscellaneous type and size.
			miscellaneousBytes += 8;
			miscellaneousBytes += m_miscellaneous[i].size;

			// Add a pad byte if necessary.
			if (m_miscellaneous[i].size % 2 != 0)
				miscellaneousBytes++;

			assert(miscellaneousBytes % 2 == 0);
		}

		if (m_miscellaneousOffset == 0)
			m_miscellaneousOffset = m_fh->tell();
		else
			m_fh->seek(m_miscellaneousOffset, File::SeekFromBeginning);

		/*
			Write the data.  On the first call to this
			function (from _af_wave_write_init), the
			data won't be available, fh->seek is used to
			reserve space until the data has been provided.
			On subseuent calls to this function (from
			_af_wave_update), the data will really be written.
		*/

		/* Write 'LIST'. */
		m_fh->write("LIST", 4);

		/* Write the size of the following chunk. */
		chunkSize = miscellaneousBytes-8;
		writeU32(&chunkSize);

		/* Write 'INFO'. */
		m_fh->write("INFO", 4);

		/* Write each miscellaneous chunk. */
		for (int i=0; i<m_miscellaneousCount; i++)
		{
			uint32_t miscsize = m_miscellaneous[i].size;
			Tag miscid;

			// Skip miscellaneous data of an unsupported type.
			if (!misc_type_to_wave(m_miscellaneous[i].type, &miscid))
				continue;

			writeTag(&miscid);
			writeU32(&miscsize);
			if (m_miscellaneous[i].buffer != NULL)
			{
				uint8_t	zero = 0;

				m_fh->write(m_miscellaneous[i].buffer, m_miscellaneous[i].size);

				// Pad if necessary.
				if ((m_miscellaneous[i].size%2) != 0)
					writeU8(&zero);
			}
			else
			{
				int	size;
				size = m_miscellaneous[i].size;

				// Pad if necessary.
				if ((size % 2) != 0)
					size++;
				m_fh->seek(size, File::SeekFromCurrent);
			}
		}
	}

	return AF_SUCCEED;
}

status WAVEFile::writeCues()
{
	Track *track = getTrack();

	if (!track->markerCount)
		return AF_SUCCEED;

	if (m_markOffset == 0)
		m_markOffset = m_fh->tell();
	else
		m_fh->seek(m_markOffset, File::SeekFromBeginning);

	Tag cue("cue ");
	writeTag(&cue);

	/*
		The cue chunk consists of 4 bytes for the number of cue points
		followed by 24 bytes for each cue point record.
	*/
	uint32_t cueChunkSize = 4 + track->markerCount * 24;
	writeU32(&cueChunkSize);
	uint32_t numCues = track->markerCount;
	writeU32(&numCues);

	// Write each marker to the file.
	for (int i=0; i<track->markerCount; i++)
	{
		uint32_t identifier = track->markers[i].id;
		writeU32(&identifier);

		uint32_t position = i;
		writeU32(&position);

		Tag data("data");
		writeTag(&data);

		/*
			For an uncompressed WAVE file which contains only one data chunk,
			chunkStart and blockStart are zero.
		*/
		uint32_t chunkStart = 0;
		writeU32(&chunkStart);

		uint32_t blockStart = 0;
		writeU32(&blockStart);

		AFframecount markPosition = track->markers[i].position;
		uint32_t sampleOffset = markPosition;
		writeU32(&sampleOffset);
	}

	// Now write the cue names and comments within a master list chunk.
	uint32_t listChunkSize = 4;
	for (int i=0; i<track->markerCount; i++)
	{
		const char *name = track->markers[i].name;
		const char *comment = track->markers[i].comment;

		/*
			Each 'labl' or 'note' chunk consists of 4 bytes for the chunk ID,
			4 bytes for the chunk data size, 4 bytes for the cue point ID,
			and then the length of the label as a null-terminated string.

			In all, this is 12 bytes plus the length of the string, its null
			termination byte, and a trailing pad byte if the length of the
			chunk is otherwise odd.
		*/
		listChunkSize += 12 + zStringLength(name);
		listChunkSize += 12 + zStringLength(comment);
	}

	Tag list("LIST");
	writeTag(&list);
	writeU32(&listChunkSize);
	Tag adtl("adtl");
	writeTag(&adtl);

	for (int i=0; i<track->markerCount; i++)
	{
		uint32_t cuePointID = track->markers[i].id;

		const char *name = track->markers[i].name;
		uint32_t labelSize = 4 + zStringLength(name);
		Tag lablTag("labl");
		writeTag(&lablTag);
		writeU32(&labelSize);
		writeU32(&cuePointID);
		writeZString(name);

		const char *comment = track->markers[i].comment;
		uint32_t noteSize = 4 + zStringLength(comment);
		Tag noteTag("note");
		writeTag(&noteTag);
		writeU32(&noteSize);
		writeU32(&cuePointID);
		writeZString(comment);
	}

	return AF_SUCCEED;
}

bool WAVEFile::writeZString(const char *s)
{
	ssize_t lengthPlusNull = strlen(s) + 1;
	if (m_fh->write(s, lengthPlusNull) != lengthPlusNull)
		return false;
	if (lengthPlusNull & 1)
	{
		uint8_t zero = 0;
		if (!writeU8(&zero))
			return false;
	}
	return true;
}

size_t WAVEFile::zStringLength(const char *s)
{
	size_t lengthPlusNull = strlen(s) + 1;
	return lengthPlusNull + (lengthPlusNull & 1);
}

status WAVEFile::writeInit(AFfilesetup setup)
{
	if (initFromSetup(setup) == AF_FAIL)
		return AF_FAIL;

	initCompressionParams();

	uint32_t zero = 0;

	m_fh->seek(0, File::SeekFromBeginning);
	m_fh->write("RIFF", 4);
	m_fh->write(&zero, 4);
	m_fh->write("WAVE", 4);

	writeMiscellaneous();
	writeCues();
	writeFormat();
	writeFrameCount();
	writeData();

	return AF_SUCCEED;
}

bool WAVEFile::readUUID(UUID *u)
{
	return m_fh->read(u->data, 16) == 16;
}

bool WAVEFile::writeUUID(const UUID *u)
{
	return m_fh->write(u->data, 16) == 16;
}

void WAVEFile::initCompressionParams()
{
	Track *track = getTrack();
	if (track->f.compressionType == AF_COMPRESSION_IMA)
		initIMACompressionParams();
	else if (track->f.compressionType == AF_COMPRESSION_MS_ADPCM)
		initMSADPCMCompressionParams();
}

void WAVEFile::initIMACompressionParams()
{
	Track *track = getTrack();

	track->f.framesPerPacket = 505;
	track->f.bytesPerPacket = 256 * track->f.channelCount;

	AUpvlist pv = AUpvnew(1);
	AUpvsetparam(pv, 0, _AF_IMA_ADPCM_TYPE);
	AUpvsetvaltype(pv, 0, AU_PVTYPE_LONG);
	long l = _AF_IMA_ADPCM_TYPE_WAVE;
	AUpvsetval(pv, 0, &l);

	track->f.compressionParams = pv;
}

void WAVEFile::initMSADPCMCompressionParams()
{
	const int16_t coefficients[7][2] =
	{
		{ 256, 0 },
		{ 512, -256 },
		{ 0, 0 },
		{ 192, 64 },
		{ 240, 0 },
		{ 460, -208 },
		{ 392, -232 }
	};
	memcpy(m_msadpcmCoefficients, coefficients, sizeof (int16_t) * 7 * 2);
	m_msadpcmNumCoefficients = 7;

	Track *track = getTrack();

	track->f.framesPerPacket = 500;
	track->f.bytesPerPacket = 256 * track->f.channelCount;

	AUpvlist pv = AUpvnew(2);
	AUpvsetparam(pv, 0, _AF_MS_ADPCM_NUM_COEFFICIENTS);
	AUpvsetvaltype(pv, 0, AU_PVTYPE_LONG);
	long l = m_msadpcmNumCoefficients;
	AUpvsetval(pv, 0, &l);

	AUpvsetparam(pv, 1, _AF_MS_ADPCM_COEFFICIENTS);
	AUpvsetvaltype(pv, 1, AU_PVTYPE_PTR);
	void *v = m_msadpcmCoefficients;
	AUpvsetval(pv, 1, &v);

	track->f.compressionParams = pv;
}

// file: aes.cpp
/*
	Audio File Library
	Copyright (C) 1998-1999, Michael Pruett <michael@68k.org>
	Copyright (C) 2000, Silicon Graphics, Inc.

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

/*
	aes.c

	This file contains routines for dealing with AES recording data.
*/


#include <string.h>
#include <assert.h>


void afInitAESChannelData (AFfilesetup setup, int trackid)
{
	if (!_af_filesetup_ok(setup))
		return;

	TrackSetup *track = setup->getTrack(trackid);
	if (!track)
		return;

	track->aesDataSet = true;
}

void afInitAESChannelDataTo (AFfilesetup setup, int trackid, int willBeData)
{
	if (!_af_filesetup_ok(setup))
		return;

	TrackSetup *track = setup->getTrack(trackid);
	if (!track)
		return;

	track->aesDataSet = willBeData;
}

int afGetAESChannelData (AFfilehandle file, int trackid, unsigned char buf[24])
{
	if (!_af_filehandle_ok(file))
		return -1;

	Track *track = file->getTrack(trackid);
	if (!track)
		return -1;

	if (!track->hasAESData)
	{
		if (buf)
			memset(buf, 0, 24);
		return 0;
	}

	if (buf)
		memcpy(buf, track->aesData, 24);

	return 1;
}

void afSetAESChannelData (AFfilehandle file, int trackid, unsigned char buf[24])
{
	if (!_af_filehandle_ok(file))
		return;

	Track *track = file->getTrack(trackid);
	if (!track)
		return;

	if (!file->checkCanWrite())
		return;

	if (track->hasAESData)
	{
		memcpy(track->aesData, buf, 24);
	}
	else
	{
		_af_error(AF_BAD_NOAESDATA,
			"unable to store AES channel status data for track %d",
			trackid);
	}
}

// file: af_vfs.cpp
/*
	Audio File Library
	Copyright (C) 1999, Elliot Lee <sopwith@redhat.com>

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

/*
	af_vfs.cpp

	Virtual file operations for the Audio File Library.
*/



#include <stdlib.h>

AFvirtualfile *af_virtual_file_new()
{
	return (AFvirtualfile *) calloc(sizeof (AFvirtualfile), 1);
}

void af_virtual_file_destroy(AFvirtualfile *vfile)
{
	vfile->destroy(vfile);

	free(vfile);
}

// file: aupv.c
/*
	Audio File Library
	Copyright (C) 1998-2000, Michael Pruett <michael@68k.org>

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

/*
	aupv.c

	This file contains an implementation of SGI's Audio Library parameter
	value list functions.
*/


#include <stdlib.h>
#include <string.h>
#include <assert.h>


AUpvlist AUpvnew (int maxitems)
{
	AUpvlist	aupvlist;
	int		i;

	if (maxitems <= 0)
		return AU_NULL_PVLIST;

	aupvlist = (AUpvlist) malloc(sizeof (struct _AUpvlist));
	assert(aupvlist);
	if (aupvlist == NULL)
		return AU_NULL_PVLIST;

	aupvlist->items = (struct _AUpvitem *)calloc(maxitems, sizeof (struct _AUpvitem));

	assert(aupvlist->items);
	if (aupvlist->items == NULL)
	{
		free(aupvlist);
		return AU_NULL_PVLIST;
	}

	/* Initialize the items in the list. */
	for (i=0; i<maxitems; i++)
	{
		aupvlist->items[i].valid = _AU_VALID_PVITEM;
		aupvlist->items[i].type = AU_PVTYPE_LONG;
		aupvlist->items[i].parameter = 0;
		memset(&aupvlist->items[i].value, 0, sizeof (aupvlist->items[i].value));
	}

	aupvlist->valid = _AU_VALID_PVLIST;
	aupvlist->count = maxitems;

	return aupvlist;
}

int AUpvgetmaxitems (AUpvlist list)
{
	assert(list);

	if (list == AU_NULL_PVLIST)
		return AU_BAD_PVLIST;
	if (list->valid != _AU_VALID_PVLIST)
		return AU_BAD_PVLIST;

	return list->count;
}

int AUpvfree (AUpvlist list)
{
	assert(list);
	assert(list->items);

	if (list == AU_NULL_PVLIST)
		return AU_BAD_PVLIST;
	if (list->valid != _AU_VALID_PVLIST)
		return AU_BAD_PVLIST;

	if ((list->items != _AU_NULL_PVITEM) &&
		(list->items[0].valid == _AU_VALID_PVITEM))
	{
		free(list->items);
	}

	free(list);

	return _AU_SUCCESS;
}

int AUpvsetparam (AUpvlist list, int item, int param)
{
	assert(list);
	assert(list->items);
	assert(item >= 0);
	assert(item < list->count);

	if (list == AU_NULL_PVLIST)
		return AU_BAD_PVLIST;
	if (list->valid != _AU_VALID_PVLIST)
		return AU_BAD_PVLIST;
	if ((item < 0) || (item > list->count - 1))
		return AU_BAD_PVITEM;
	if (list->items[item].valid != _AU_VALID_PVITEM)
		return AU_BAD_PVLIST;

	list->items[item].parameter = param;
	return _AU_SUCCESS;
}

int AUpvsetvaltype (AUpvlist list, int item, int type)
{
	assert(list);
	assert(list->items);
	assert(item >= 0);
	assert(item < list->count);

	if (list == AU_NULL_PVLIST)
		return AU_BAD_PVLIST;
	if (list->valid != _AU_VALID_PVLIST)
		return AU_BAD_PVLIST;
	if ((item < 0) || (item > list->count - 1))
		return AU_BAD_PVITEM;
	if (list->items[item].valid != _AU_VALID_PVITEM)
		return AU_BAD_PVLIST;

	list->items[item].type = type;
	return _AU_SUCCESS;
}

int AUpvsetval (AUpvlist list, int item, void *val)
{
	assert(list);
	assert(list->items);
	assert(item >= 0);
	assert(item < list->count);

	if (list == AU_NULL_PVLIST)
		return AU_BAD_PVLIST;
	if (list->valid != _AU_VALID_PVLIST)
		return AU_BAD_PVLIST;
	if ((item < 0) || (item > list->count - 1))
		return AU_BAD_PVITEM;
	if (list->items[item].valid != _AU_VALID_PVITEM)
		return AU_BAD_PVLIST;

	switch (list->items[item].type)
	{
		case AU_PVTYPE_LONG:
			list->items[item].value.l = *((long *) val);
			break;
		case AU_PVTYPE_DOUBLE:
			list->items[item].value.d = *((double *) val);
			break;
		case AU_PVTYPE_PTR:
			list->items[item].value.v = *((void **) val);
			break;
		default:
			assert(0);
			return AU_BAD_PVLIST;
	}

	return _AU_SUCCESS;
}

int AUpvgetparam (AUpvlist list, int item, int *param)
{
	assert(list);
	assert(list->items);
	assert(item >= 0);
	assert(item < list->count);

	if (list == AU_NULL_PVLIST)
		return AU_BAD_PVLIST;
	if (list->valid != _AU_VALID_PVLIST)
		return AU_BAD_PVLIST;
	if ((item < 0) || (item > list->count - 1))
		return AU_BAD_PVITEM;
	if (list->items[item].valid != _AU_VALID_PVITEM)
		return AU_BAD_PVLIST;

	*param = list->items[item].parameter;
	return _AU_SUCCESS;
}

int AUpvgetvaltype (AUpvlist list, int item, int *type)
{
	assert(list);
	assert(list->items);
	assert(item >= 0);
	assert(item < list->count);

	if (list == AU_NULL_PVLIST)
		return AU_BAD_PVLIST;
	if (list->valid != _AU_VALID_PVLIST)
		return AU_BAD_PVLIST;
	if ((item < 0) || (item > list->count - 1))
		return AU_BAD_PVITEM;
	if (list->items[item].valid != _AU_VALID_PVITEM)
		return AU_BAD_PVLIST;

	*type = list->items[item].type;
	return _AU_SUCCESS;
}

int AUpvgetval (AUpvlist list, int item, void *val)
{
	assert(list);
	assert(list->items);
	assert(item >= 0);
	assert(item < list->count);

	if (list == AU_NULL_PVLIST)
		return AU_BAD_PVLIST;
	if (list->valid != _AU_VALID_PVLIST)
		return AU_BAD_PVLIST;
	if ((item < 0) || (item > list->count - 1))
		return AU_BAD_PVITEM;
	if (list->items[item].valid != _AU_VALID_PVITEM)
		return AU_BAD_PVLIST;

	switch (list->items[item].type)
	{
		case AU_PVTYPE_LONG:
			*((long *) val) = list->items[item].value.l;
			break;
		case AU_PVTYPE_DOUBLE:
			*((double *) val) = list->items[item].value.d;
			break;
		case AU_PVTYPE_PTR:
			*((void **) val) = list->items[item].value.v;
			break;
	}

	return _AU_SUCCESS;
}

// file: compression.cpp
/*
	Audio File Library
	Copyright (C) 1999-2000, Michael Pruett <michael@68k.org>
	Copyright (C) 2000, Silicon Graphics, Inc.

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

/*
	compression.cpp

	This file contains routines for configuring compressed audio.
*/


#include <assert.h>


const CompressionUnit *_af_compression_unit_from_id (int compressionid)
{
	for (int i=0; i<_AF_NUM_COMPRESSION; i++)
		if (_af_compression[i].compressionID == compressionid)
			return &_af_compression[i];

	_af_error(AF_BAD_COMPTYPE, "compression type %d not available", compressionid);
	return NULL;
}

int afGetCompression (AFfilehandle file, int trackid)
{
	if (!_af_filehandle_ok(file))
		return -1;

	Track *track = file->getTrack(trackid);
	if (!track)
		return -1;

	return track->f.compressionType;
}

void afInitCompression (AFfilesetup setup, int trackid, int compression)
{
	if (!_af_filesetup_ok(setup))
		return;

	TrackSetup *track = setup->getTrack(trackid);
	if (!track)
		return;

	if (!_af_compression_unit_from_id(compression))
		return;

	track->compressionSet = true;
	track->f.compressionType = compression;
}

#if 0
int afGetCompressionParams (AFfilehandle file, int trackid,
	int *compression, AUpvlist pvlist, int numitems)
{
	assert(file);
	assert(trackid == AF_DEFAULT_TRACK);
}

void afInitCompressionParams (AFfilesetup setup, int trackid,
	int compression, AUpvlist pvlist, int numitems)
{
	assert(setup);
	assert(trackid == AF_DEFAULT_TRACK);
}
#endif

// file: data.cpp
/*
	Audio File Library
	Copyright (C) 1998-2000, Michael Pruett <michael@68k.org>
	Copyright (C) 2000, Silicon Graphics, Inc.

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

/*
	data.cpp
*/


#include <assert.h>
#include <stdlib.h>
#include <string.h>


int afWriteFrames (AFfilehandle file, int trackid, const void *samples,
	int nvframes2write)
{
	SharedPtr<Module> firstmod;
	SharedPtr<Chunk> userc;
	int bytes_per_vframe;
	AFframecount vframe;

	if (!_af_filehandle_ok(file))
		return -1;

	if (!file->checkCanWrite())
		return -1;

	Track *track = file->getTrack(trackid);
	if (!track)
		return -1;

	if (track->ms->isDirty() && track->ms->setup(file, track) == AF_FAIL)
		return -1;

	if (!track->ms->fileModuleHandlesSeeking() &&
		file->m_seekok &&
		file->m_fh->seek(track->fpos_next_frame, File::SeekFromBeginning) !=
			track->fpos_next_frame)
	{
		_af_error(AF_BAD_LSEEK, "unable to position write pointer at next frame");
		return -1;
	}

	bytes_per_vframe = _af_format_frame_size(&track->v, true);

	firstmod = track->ms->modules().front();
	userc = track->ms->chunks().front();

	track->filemodhappy = true;

	vframe = 0;
#ifdef UNLIMITED_CHUNK_NVFRAMES
	/*
		OPTIMIZATION: see the comment at the very end of
		arrangemodules() in modules.c for an explanation of this:
	*/
	if (!trk->ms->mustUseAtomicNVFrames())
	{
		userc->buffer = (char *) samples;
		userc->frameCount = nvframes2write;

		firstmod->runPush();

		/* Count this chunk if there was no i/o error. */
		if (trk->filemodhappy)
			vframe += userc->frameCount;
	}
	else
#else
	/* Optimization must be off. */
	assert(track->ms->mustUseAtomicNVFrames());
#endif
	{
		while (vframe < nvframes2write)
		{
			userc->buffer = (char *) samples + bytes_per_vframe * vframe;
			if (vframe <= nvframes2write - _AF_ATOMIC_NVFRAMES)
				userc->frameCount = _AF_ATOMIC_NVFRAMES;
			else
				userc->frameCount = nvframes2write - vframe;

			firstmod->runPush();

			if (!track->filemodhappy)
				break;

			vframe += userc->frameCount;
		}
	}

	track->nextvframe += vframe;
	track->totalvframes += vframe;

	return vframe;
}

int afReadFrames (AFfilehandle file, int trackid, void *samples,
	int nvframeswanted)
{
	SharedPtr<Module> firstmod;
	SharedPtr<Chunk> userc;
	AFframecount	nvframesleft, nvframes2read;
	int		bytes_per_vframe;
	AFframecount	vframe;

	if (!_af_filehandle_ok(file))
		return -1;

	if (!file->checkCanRead())
		return -1;

	Track *track = file->getTrack(trackid);
	if (!track)
		return -1;

	if (track->ms->isDirty() && track->ms->setup(file, track) == AF_FAIL)
		return -1;

	if (!track->ms->fileModuleHandlesSeeking() &&
		file->m_seekok &&
		file->m_fh->seek(track->fpos_next_frame, File::SeekFromBeginning) !=
			track->fpos_next_frame)
	{
		_af_error(AF_BAD_LSEEK, "unable to position read pointer at next frame");
		return -1;
	}

	if (track->totalvframes == -1)
		nvframes2read = nvframeswanted;
	else
	{
		nvframesleft = track->totalvframes - track->nextvframe;
		nvframes2read = (nvframeswanted > nvframesleft) ?
			nvframesleft : nvframeswanted;
	}
	bytes_per_vframe = _af_format_frame_size(&track->v, true);

	firstmod = track->ms->modules().back();
	userc = track->ms->chunks().back();

	track->filemodhappy = true;

	vframe = 0;

	if (!track->ms->mustUseAtomicNVFrames())
	{
		assert(track->frames2ignore == 0);
		userc->buffer = samples;
		userc->frameCount = nvframes2read;

		firstmod->runPull();
		if (track->filemodhappy)
			vframe += userc->frameCount;
	}
	else
	{
		bool	eof = false;

		if (track->frames2ignore != 0)
		{
			userc->frameCount = track->frames2ignore;
			userc->allocate(track->frames2ignore * bytes_per_vframe);
			if (!userc->buffer)
				return 0;

			firstmod->runPull();

			/* Have we hit EOF? */
			if (static_cast<ssize_t>(userc->frameCount) < track->frames2ignore)
				eof = true;

			track->frames2ignore = 0;

			userc->deallocate();
		}

		/*
			Now start reading useful frames, until EOF or
			premature EOF.
		*/

		while (track->filemodhappy && !eof && vframe < nvframes2read)
		{
			AFframecount	nvframes2pull;
			userc->buffer = (char *) samples + bytes_per_vframe * vframe;

			if (vframe <= nvframes2read - _AF_ATOMIC_NVFRAMES)
				nvframes2pull = _AF_ATOMIC_NVFRAMES;
			else
				nvframes2pull = nvframes2read - vframe;

			userc->frameCount = nvframes2pull;

			firstmod->runPull();

			if (track->filemodhappy)
			{
				vframe += userc->frameCount;
				if (static_cast<ssize_t>(userc->frameCount) < nvframes2pull)
					eof = true;
			}
		}
	}

	track->nextvframe += vframe;

	return vframe;
}

// file: debug.cpp
/*
	Audio File Library
	Copyright (C) 1998-2000, Michael Pruett <michael@68k.org>
	Copyright (C) 2000, Silicon Graphics, Inc.

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

/*
	debug.cpp

	This file contains debugging routines for the Audio File
	Library.
*/


#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <string.h>
#include <assert.h>



void _af_print_pvlist (AUpvlist list)
{
	assert(list);

	printf("list.valid: %d\n", list->valid);
	printf("list.count: %zu\n", list->count);

	for (unsigned i=0; i<list->count; i++)
	{
		printf("item %u valid %d, should be %d\n",
			i, list->items[i].valid, _AU_VALID_PVITEM);

		switch (list->items[i].type)
		{
			case AU_PVTYPE_LONG:
				printf("item #%u, parameter %d, long: %ld\n",
					i, list->items[i].parameter,
					list->items[i].value.l);
				break;
			case AU_PVTYPE_DOUBLE:
				printf("item #%u, parameter %d, double: %f\n",
					i, list->items[i].parameter,
					list->items[i].value.d);
				break;
			case AU_PVTYPE_PTR:
				printf("item #%u, parameter %d, pointer: %p\n",
					i, list->items[i].parameter,
					list->items[i].value.v);
				break;

			default:
				printf("item #%u, invalid type %d\n", i,
					list->items[i].type);
				assert(false);
				break;
		}
	}
}

void _af_print_audioformat (AudioFormat *fmt)
{
	/* sampleRate, channelCount */
	printf("{ %7.2f Hz %d ch ", fmt->sampleRate, fmt->channelCount);

	/* sampleFormat, sampleWidth */
	switch (fmt->sampleFormat)
	{
		case AF_SAMPFMT_TWOSCOMP:
			printf("%db 2 ", fmt->sampleWidth);
			break;
		case AF_SAMPFMT_UNSIGNED:
			printf("%db u ", fmt->sampleWidth);
			break;
		case AF_SAMPFMT_FLOAT:
			printf("flt ");
			break;
		case AF_SAMPFMT_DOUBLE:
			printf("dbl ");
			break;
		default:
			printf("%dsampfmt? ", fmt->sampleFormat);
	}

	/* pcm */
	printf("(%.30g+-%.30g [%.30g,%.30g]) ",
		fmt->pcm.intercept, fmt->pcm.slope,
		fmt->pcm.minClip, fmt->pcm.maxClip);

	/* byteOrder */
	switch (fmt->byteOrder)
	{
		case AF_BYTEORDER_BIGENDIAN:
			printf("big ");
			break;
		case AF_BYTEORDER_LITTLEENDIAN:
			printf("little ");
			break;
		default:
			printf("%dbyteorder? ", fmt->byteOrder);
			break;
	}

	/* compression */
	{
		const CompressionUnit *unit = _af_compression_unit_from_id(fmt->compressionType);
		if (!unit)
			printf("%dcompression?", fmt->compressionType);
		else if (fmt->compressionType == AF_COMPRESSION_NONE)
			printf("pcm");
		else
			printf("%s", unit->label);
	}

	printf(" }");
}

void _af_print_tracks (AFfilehandle filehandle)
{
	for (int i=0; i<filehandle->m_trackCount; i++)
	{
		Track *track = &filehandle->m_tracks[i];
		printf("track %d\n", i);
		printf(" id %d\n", track->id);
		printf(" sample format\n");
		_af_print_audioformat(&track->f);
		printf(" virtual format\n");
		_af_print_audioformat(&track->v);
		printf(" total file frames: %jd\n",
			(intmax_t) track->totalfframes);
		printf(" total virtual frames: %jd\n",
			(intmax_t) track->totalvframes);
		printf(" next file frame: %jd\n",
			(intmax_t) track->nextfframe);
		printf(" next virtual frame: %jd\n",
			(intmax_t) track->nextvframe);
		printf(" frames to ignore: %jd\n",
			(intmax_t) track->frames2ignore);

		printf(" data_size: %jd\n",
			(intmax_t) track->data_size);
		printf(" fpos_first_frame: %jd\n",
			(intmax_t) track->fpos_first_frame);
		printf(" fpos_next_frame: %jd\n",
			(intmax_t) track->fpos_next_frame);
		printf(" fpos_after_data: %jd\n",
			(intmax_t) track->fpos_after_data);

		printf(" channel matrix:");
		_af_print_channel_matrix(track->channelMatrix,
			track->f.channelCount, track->v.channelCount);
		printf("\n");

		printf(" marker count: %d\n", track->markerCount);
	}
}

void _af_print_filehandle (AFfilehandle filehandle)
{
	printf("file handle: 0x%p\n", filehandle);

	if (filehandle->m_valid == _AF_VALID_FILEHANDLE)
		printf("valid\n");
	else
		printf("invalid!\n");

	printf(" access: ");
	if (filehandle->m_access == _AF_READ_ACCESS)
		putchar('r');
	else
		putchar('w');

	printf(" fileFormat: %d\n", filehandle->m_fileFormat);

	printf(" instrument count: %d\n", filehandle->m_instrumentCount);
	printf(" instruments: 0x%p\n", filehandle->m_instruments);

	printf(" miscellaneous count: %d\n", filehandle->m_miscellaneousCount);
	printf(" miscellaneous: 0x%p\n", filehandle->m_miscellaneous);

	printf(" trackCount: %d\n", filehandle->m_trackCount);
	printf(" tracks: 0x%p\n", filehandle->m_tracks);
	_af_print_tracks(filehandle);
}

void _af_print_channel_matrix (double *matrix, int fchans, int vchans)
{
	int v, f;

	if (!matrix)
	{
		printf("NULL");
		return;
	}

	printf("{");
	for (v=0; v < vchans; v++)
	{
		if (v) printf(" ");
		printf("{");
		for (f=0; f < fchans; f++)
		{
			if (f) printf(" ");
			printf("%5.2f", *(matrix + v*fchans + f));
		}
		printf("}");
	}
	printf("}");
}

void _af_print_frame (AFframecount frameno, double *frame, int nchannels,
	char *formatstring, int numberwidth,
	double slope, double intercept, double minclip, double maxclip)
{
	char linebuf[81];
	int wavewidth = 78 - numberwidth*nchannels - 6;
	int c;

	memset(linebuf, ' ', 80);
	linebuf[0] = '|';
	linebuf[wavewidth-1] = '|';
	linebuf[wavewidth] = 0;

	printf("%05jd ", (intmax_t) frameno);

	for (c=0; c < nchannels; c++)
	{
		double pcm = frame[c];
		printf(formatstring, pcm);
	}
	for (c=0; c < nchannels; c++)
	{
		double pcm = frame[c], volts;
		if (maxclip > minclip)
		{
			if (pcm < minclip) pcm = minclip;
			if (pcm > maxclip) pcm = maxclip;
		}
		volts = (pcm - intercept) / slope;
		linebuf[(int)((volts/2 + 0.5)*(wavewidth-3)) + 1] = '0' + c;
	}
	printf("%s\n", linebuf);
}

// file: error.c
/*
	Audio File Library
	Copyright (C) 1998, Michael Pruett <michael@68k.org>

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

/*
	error.c

	This file contains the routines used in the Audio File Library's
	error handling.
*/


#include <stdio.h>
#include <stdarg.h>
#include <assert.h>

static void defaultErrorFunction (long error, const char *str);

static AFerrfunc errorFunction = defaultErrorFunction;

AFerrfunc afSetErrorHandler (AFerrfunc efunc)
{
	AFerrfunc	old;

	old = errorFunction;
	errorFunction = efunc;

	return old;
}

static void defaultErrorFunction (long error, const char *str)
{
	fprintf(stderr, "Audio File Library: ");
	fprintf(stderr, "%s", str);
	fprintf(stderr, " [error %ld]\n", error);
}

void _af_error (int errorCode, const char *fmt, ...)
{
	char	buf[1024];
	va_list	ap;

	va_start(ap, fmt);

	vsnprintf(buf, 1024, fmt, ap);

	va_end(ap);

	if (errorFunction != NULL)
		errorFunction(errorCode, buf);
}

// file: extended.c
/* Copyright (C) 1989-1991 Apple Computer, Inc.
 *
 * All rights reserved.
 *
 * Warranty Information
 *  Even though Apple has reviewed this software, Apple makes no warranty
 *  or representation, either express or implied, with respect to this
 *  software, its quality, accuracy, merchantability, or fitness for a
 *  particular purpose.  As a result, this software is provided "as is,"
 *  and you, its user, are assuming the entire risk as to its quality
 *  and accuracy.
 *
 * This code may be used and freely distributed as long as it includes
 * this copyright notice and the above warranty information.
 *
 * Machine-independent I/O routines for IEEE floating-point numbers.
 *
 * NaN's and infinities are converted to HUGE_VAL or HUGE, which
 * happens to be infinity on IEEE machines.  Unfortunately, it is
 * impossible to preserve NaN's in a machine-independent way.
 * Infinities are, however, preserved on IEEE machines.
 *
 * These routines have been tested on the following machines:
 *	Apple Macintosh, MPW 3.1 C compiler
 *	Apple Macintosh, THINK C compiler
 *	Silicon Graphics IRIS, MIPS compiler
 *	Cray X/MP and Y/MP
 *	Digital Equipment VAX
 *	Sequent Balance (Multiprocesor 386)
 *	NeXT
 *
 *
 * Implemented by Malcolm Slaney and Ken Turkowski.
 *
 * Malcolm Slaney contributions during 1988-1990 include big- and little-
 * endian file I/O, conversion to and from Motorola's extended 80-bit
 * floating-point format, and conversions to and from IEEE single-
 * precision floating-point format.
 *
 * In 1991, Ken Turkowski implemented the conversions to and from
 * IEEE double-precision format, added more precision to the extended
 * conversions, and accommodated conversions involving +/- infinity,
 * NaN's, and denormalized numbers.
 */

/****************************************************************
 * Extended precision IEEE floating-point conversion routines.
 * Extended is an 80-bit number as defined by Motorola,
 * with a sign bit, 15 bits of exponent (offset 16383?),
 * and a 64-bit mantissa, with no hidden bit.
 ****************************************************************/


#include <math.h>

#ifndef HUGE_VAL
#define HUGE_VAL HUGE
#endif

#define FloatToUnsigned(f) ((unsigned long) (((long) (f - 2147483648.0)) + 2147483647L) + 1)

void _af_convert_to_ieee_extended (double num, unsigned char *bytes)
{
	int				sign;
	int				expon;
	double			fMant, fsMant;
	unsigned long	hiMant, loMant;

	if (num < 0) {
		sign = 0x8000;
		num *= -1;
	} else {
		sign = 0;
	}

	if (num == 0) {
		expon = 0; hiMant = 0; loMant = 0;
	}
	else {
		fMant = frexp(num, &expon);
		if ((expon > 16384) || !(fMant < 1)) {	  /* Infinity or NaN */
			expon = sign|0x7FFF; hiMant = 0; loMant = 0; /* infinity */
		}
		else {	  /* Finite */
			expon += 16382;
			if (expon < 0) {	/* denormalized */
				fMant = ldexp(fMant, expon);
				expon = 0;
			}
			expon |= sign;
			fMant = ldexp(fMant, 32);
			fsMant = floor(fMant);
			hiMant = FloatToUnsigned(fsMant);
			fMant = ldexp(fMant - fsMant, 32);
			fsMant = floor(fMant);
			loMant = FloatToUnsigned(fsMant);
		}
	}

	bytes[0] = expon >> 8;
	bytes[1] = expon;
	bytes[2] = hiMant >> 24;
	bytes[3] = hiMant >> 16;
	bytes[4] = hiMant >> 8;
	bytes[5] = hiMant;
	bytes[6] = loMant >> 24;
	bytes[7] = loMant >> 16;
	bytes[8] = loMant >> 8;
	bytes[9] = loMant;
}

#define UnsignedToFloat(u) (((double) ((long) (u - 2147483647L - 1))) + 2147483648.0)

double _af_convert_from_ieee_extended (const unsigned char *bytes)
{
	double			f;
	int				expon;
	unsigned long	hiMant, loMant;

	expon = ((bytes[0] & 0x7F) << 8) | (bytes[1] & 0xFF);
	hiMant = ((unsigned long)(bytes[2] & 0xFF) << 24)
		| ((unsigned long) (bytes[3] & 0xFF) << 16)
		| ((unsigned long) (bytes[4] & 0xFF) << 8)
		| ((unsigned long) (bytes[5] & 0xFF));
	loMant = ((unsigned long) (bytes[6] & 0xFF) << 24)
		| ((unsigned long) (bytes[7] & 0xFF) << 16)
		| ((unsigned long) (bytes[8] & 0xFF) << 8)
		| ((unsigned long) (bytes[9] & 0xFF));

	if (expon == 0 && hiMant == 0 && loMant == 0) {
		f = 0;
	}
	else {
		if (expon == 0x7FFF) {	  /* Infinity or NaN */
			f = HUGE_VAL;
		}
		else {
			expon -= 16383;
			f  = ldexp(UnsignedToFloat(hiMant), expon-=31);
			f += ldexp(UnsignedToFloat(loMant), expon-=32);
		}
	}

	if (bytes[0] & 0x80)
		return -f;
	else
		return f;
}

// file: format.cpp
/*
	Audio File Library
	Copyright (C) 1998-2000, Michael Pruett <michael@68k.org>
	Copyright (C) 2000, Silicon Graphics, Inc.

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

/*
	audiofile.c

	This file implements many of the main interface routines of the
	Audio File Library.
*/


#include <assert.h>
#include <stdint.h>
#include <stdlib.h>
#include <string.h>


AFfileoffset afGetDataOffset (AFfilehandle file, int trackid)
{
	if (!_af_filehandle_ok(file))
		return -1;

	Track *track = file->getTrack(trackid);
	if (!track)
		return -1;

	return track->fpos_first_frame;
}

AFfileoffset afGetTrackBytes (AFfilehandle file, int trackid)
{
	if (!_af_filehandle_ok(file))
		return -1;

	Track *track = file->getTrack(trackid);
	if (!track)
		return -1;

	return track->data_size;
}

/*
	afGetFrameSize returns the size (in bytes) of a sample frame from
	the specified track of an audio file.

	stretch3to4 == true: size which user sees
	stretch3to4 == false: size used in file
*/
float afGetFrameSize (AFfilehandle file, int trackid, int stretch3to4)
{
	if (!_af_filehandle_ok(file))
		return -1;

	Track *track = file->getTrack(trackid);
	if (!track)
		return -1;

	return _af_format_frame_size(&track->f, stretch3to4);
}

float afGetVirtualFrameSize (AFfilehandle file, int trackid, int stretch3to4)
{
	if (!_af_filehandle_ok(file))
		return -1;

	Track *track = file->getTrack(trackid);
	if (!track)
		return -1;

	return _af_format_frame_size(&track->v, stretch3to4);
}

AFframecount afSeekFrame (AFfilehandle file, int trackid, AFframecount frame)
{
	if (!_af_filehandle_ok(file))
		return -1;

	if (!file->checkCanRead())
		return -1;

	Track *track = file->getTrack(trackid);
	if (!track)
		return -1;

	if (track->ms->isDirty() && track->ms->setup(file, track) == AF_FAIL)
		return -1;

	if (frame < 0)
		return track->nextvframe;

	/* Optimize the case of seeking to the current position. */
	if (frame == track->nextvframe)
		return track->nextvframe;

	/* Limit request to the number of frames in the file. */
	if (track->totalvframes != -1)
		if (frame > track->totalvframes)
			frame = track->totalvframes - 1;

	/*
		Now that the modules are not dirty and frame
		represents a valid virtual frame, we call
		_AFsetupmodules again after setting track->nextvframe.

		_AFsetupmodules will look at track->nextvframe and
		compute track->nextfframe in clever and mysterious
		ways.
	*/
	track->nextvframe = frame;

	if (track->ms->setup(file, track) == AF_FAIL)
		return -1;

	return track->nextvframe;
}

AFfileoffset afTellFrame (AFfilehandle file, int trackid)
{
	return afSeekFrame(file, trackid, -1);
}

int afSetVirtualByteOrder (AFfilehandle file, int trackid, int byteorder)
{
	if (!_af_filehandle_ok(file))
		return AF_FAIL;

	Track *track = file->getTrack(trackid);
	if (!track)
		return AF_FAIL;

	if (byteorder != AF_BYTEORDER_BIGENDIAN &&
		byteorder != AF_BYTEORDER_LITTLEENDIAN)
	{
		_af_error(AF_BAD_BYTEORDER, "invalid byte order %d", byteorder);
		return AF_FAIL;
	}

	track->v.byteOrder = byteorder;
	track->ms->setDirty();

	return AF_SUCCEED;
}

int afGetByteOrder (AFfilehandle file, int trackid)
{
	if (!_af_filehandle_ok(file))
		return -1;

	Track *track = file->getTrack(trackid);
	if (!track)
		return -1;

	return track->f.byteOrder;
}

int afGetVirtualByteOrder (AFfilehandle file, int trackid)
{
	if (!_af_filehandle_ok(file))
		return -1;

	Track *track = file->getTrack(trackid);
	if (!track)
		return -1;

	return track->v.byteOrder;
}

AFframecount afGetFrameCount (AFfilehandle file, int trackid)
{
	if (!_af_filehandle_ok(file))
		return -1;

	Track *track = file->getTrack(trackid);
	if (!track)
		return -1;

	if (track->ms->isDirty() && track->ms->setup(file, track) == AF_FAIL)
		return -1;

	return track->totalvframes;
}

double afGetRate (AFfilehandle file, int trackid)
{
	if (!_af_filehandle_ok(file))
		return -1;

	Track *track = file->getTrack(trackid);
	if (!track)
		return -1;

	return track->f.sampleRate;
}

int afGetChannels (AFfilehandle file, int trackid)
{
	if (!_af_filehandle_ok(file))
		return -1;

	Track *track = file->getTrack(trackid);
	if (!track)
		return -1;

	return track->f.channelCount;
}

void afGetSampleFormat (AFfilehandle file, int trackid, int *sampleFormat, int *sampleWidth)
{
	if (!_af_filehandle_ok(file))
		return;

	Track *track = file->getTrack(trackid);
	if (!track)
		return;

	if (sampleFormat)
		*sampleFormat = track->f.sampleFormat;

	if (sampleWidth)
		*sampleWidth = track->f.sampleWidth;
}

void afGetVirtualSampleFormat (AFfilehandle file, int trackid, int *sampleFormat, int *sampleWidth)
{
	if (!_af_filehandle_ok(file))
		return;

	Track *track = file->getTrack(trackid);
	if (!track)
		return;

	if (sampleFormat)
		*sampleFormat = track->v.sampleFormat;

	if (sampleWidth)
		*sampleWidth = track->v.sampleWidth;
}

int afSetVirtualSampleFormat (AFfilehandle file, int trackid,
	int sampleFormat, int sampleWidth)
{
	if (!_af_filehandle_ok(file))
		return -1;

	Track *track = file->getTrack(trackid);
	if (!track)
		return -1;

	if (_af_set_sample_format(&track->v, sampleFormat, sampleWidth) == AF_FAIL)
		return -1;

	track->ms->setDirty();

	return 0;
}

/* XXXmpruett fix the version */
int afGetFileFormat (AFfilehandle file, int *version)
{
	if (!_af_filehandle_ok(file))
		return -1;

	if (version != NULL)
		*version = file->getVersion();

	return file->m_fileFormat;
}

int afSetVirtualChannels (AFfilehandle file, int trackid, int channelCount)
{
	if (!_af_filehandle_ok(file))
		return -1;

	Track *track = file->getTrack(trackid);
	if (!track)
		return -1;

	track->v.channelCount = channelCount;
	track->ms->setDirty();

	if (track->channelMatrix)
		free(track->channelMatrix);
	track->channelMatrix = NULL;

	return 0;
}

double afGetVirtualRate (AFfilehandle file, int trackid)
{
	if (!_af_filehandle_ok(file))
		return -1;

	Track *track = file->getTrack(trackid);
	if (!track)
		return -1;

	return track->v.sampleRate;
}

int afSetVirtualRate (AFfilehandle file, int trackid, double rate)
{
	if (!_af_filehandle_ok(file))
		return -1;

	Track *track = file->getTrack(trackid);
	if (!track)
		return -1;

	if (rate < 0)
	{
		_af_error(AF_BAD_RATE, "invalid sampling rate %.30g", rate);
		return -1;
	}

	track->v.sampleRate = rate;
	track->ms->setDirty();

	return 0;
}

void afSetChannelMatrix (AFfilehandle file, int trackid, double* matrix)
{
	if (!_af_filehandle_ok(file))
		return;

	Track *track = file->getTrack(trackid);
	if (!track)
		return;

	if (track->channelMatrix != NULL)
		free(track->channelMatrix);
	track->channelMatrix = NULL;

	if (matrix != NULL)
	{
		int	i, size;

		size = track->v.channelCount * track->f.channelCount;

		track->channelMatrix = (double *) malloc(size * sizeof (double));

		for (i = 0; i < size; i++)
			track->channelMatrix[i] = matrix[i];
	}
}

int afGetVirtualChannels (AFfilehandle file, int trackid)
{
	if (!_af_filehandle_ok(file))
		return -1;

	Track *track = file->getTrack(trackid);
	if (!track)
		return -1;

	return track->v.channelCount;
}

// file: g711.c
/*
 * This source code is a product of Sun Microsystems, Inc. and is provided
 * for unrestricted use.  Users may copy or modify this source code without
 * charge.
 *
 * SUN SOURCE CODE IS PROVIDED AS IS WITH NO WARRANTIES OF ANY KIND INCLUDING
 * THE WARRANTIES OF DESIGN, MERCHANTIBILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE, OR ARISING FROM A COURSE OF DEALING, USAGE OR TRADE PRACTICE.
 *
 * Sun source code is provided with no support and without any obligation on
 * the part of Sun Microsystems, Inc. to assist in its use, correction,
 * modification or enhancement.
 *
 * SUN MICROSYSTEMS, INC. SHALL HAVE NO LIABILITY WITH RESPECT TO THE
 * INFRINGEMENT OF COPYRIGHTS, TRADE SECRETS OR ANY PATENTS BY THIS SOFTWARE
 * OR ANY PART THEREOF.
 *
 * In no event will Sun Microsystems, Inc. be liable for any lost revenue
 * or profits or other special, indirect and consequential damages, even if
 * Sun has been advised of the possibility of such damages.
 *
 * Sun Microsystems, Inc.
 * 2550 Garcia Avenue
 * Mountain View, California  94043
 */

#define SUPERCEDED

/*
 * g711.c
 *
 * u-law, A-law and linear PCM conversions.
 */
#define	SIGN_BIT	(0x80)		/* Sign bit for a A-law byte. */
#define	QUANT_MASK	(0xf)		/* Quantization field mask. */
#define	NSEGS		(8)		/* Number of A-law segments. */
#define	SEG_SHIFT	(4)		/* Left shift for segment number. */
#define	SEG_MASK	(0x70)		/* Segment field mask. */

/* see libst.h */
#ifdef	SUPERCEDED

static const short seg_end[8] = {0xFF, 0x1FF, 0x3FF, 0x7FF,
			    0xFFF, 0x1FFF, 0x3FFF, 0x7FFF};

static int search(int val, const short *table, int size)
{
	int		i;

	for (i = 0; i < size; i++) {
		if (val <= *table++)
			return (i);
	}
	return (size);
}

/*
 * linear2alaw() - Convert a 16-bit linear PCM value to 8-bit A-law
 *
 * linear2alaw() accepts an 16-bit integer and encodes it as A-law data.
 *
 *		Linear Input Code	Compressed Code
 *	------------------------	---------------
 *	0000000wxyza			000wxyz
 *	0000001wxyza			001wxyz
 *	000001wxyzab			010wxyz
 *	00001wxyzabc			011wxyz
 *	0001wxyzabcd			100wxyz
 *	001wxyzabcde			101wxyz
 *	01wxyzabcdef			110wxyz
 *	1wxyzabcdefg			111wxyz
 *
 * For further information see John C. Bellamy's Digital Telephony, 1982,
 * John Wiley & Sons, pps 98-111 and 472-476.
 */
unsigned char
_af_linear2alaw(int	pcm_val)
{
	int		mask;
	int		seg;
	unsigned char	aval;

	if (pcm_val >= 0) {
		mask = 0xD5;		/* sign (7th) bit = 1 */
	} else {
		mask = 0x55;		/* sign bit = 0 */
		pcm_val = -pcm_val - 8;
	}

	/* Convert the scaled magnitude to segment number. */
	seg = search(pcm_val, seg_end, 8);

	/* Combine the sign, segment, and quantization bits. */

	if (seg >= 8)		/* out of range, return maximum value. */
		return (0x7F ^ mask);
	else {
		aval = seg << SEG_SHIFT;
		if (seg < 2)
			aval |= (pcm_val >> 4) & QUANT_MASK;
		else
			aval |= (pcm_val >> (seg + 3)) & QUANT_MASK;
		return (aval ^ mask);
	}
}

/*
 * alaw2linear() - Convert an A-law value to 16-bit linear PCM
 *
 */
int
_af_alaw2linear(unsigned char	a_val)
{
	int		t;
	int		seg;

	a_val ^= 0x55;

	t = (a_val & QUANT_MASK) << 4;
	seg = ((unsigned)a_val & SEG_MASK) >> SEG_SHIFT;
	switch (seg) {
	case 0:
		t += 8;
		break;
	case 1:
		t += 0x108;
		break;
	default:
		t += 0x108;
		t <<= seg - 1;
	}
	return ((a_val & SIGN_BIT) ? t : -t);
}

#define	BIAS		(0x84)		/* Bias for linear code. */

/*
 * linear2ulaw() - Convert a linear PCM value to u-law
 *
 * In order to simplify the encoding process, the original linear magnitude
 * is biased by adding 33 which shifts the encoding range from (0 - 8158) to
 * (33 - 8191). The result can be seen in the following encoding table:
 *
 *	Biased Linear Input Code	Compressed Code
 *	------------------------	---------------
 *	00000001wxyza			000wxyz
 *	0000001wxyzab			001wxyz
 *	000001wxyzabc			010wxyz
 *	00001wxyzabcd			011wxyz
 *	0001wxyzabcde			100wxyz
 *	001wxyzabcdef			101wxyz
 *	01wxyzabcdefg			110wxyz
 *	1wxyzabcdefgh			111wxyz
 *
 * Each biased linear code has a leading 1 which identifies the segment
 * number. The value of the segment number is equal to 7 minus the number
 * of leading 0's. The quantization interval is directly available as the
 * four bits wxyz.  * The trailing bits (a - h) are ignored.
 *
 * Ordinarily the complement of the resulting code word is used for
 * transmission, and so the code word is complemented before it is returned.
 *
 * For further information see John C. Bellamy's Digital Telephony, 1982,
 * John Wiley & Sons, pps 98-111 and 472-476.
 */

/* 2's complement (16-bit range) */
unsigned char _af_linear2ulaw (int pcm_val)
{
	int		mask;
	int		seg;
	unsigned char	uval;

	/* Get the sign and the magnitude of the value. */
	if (pcm_val < 0) {
		pcm_val = BIAS - pcm_val;
		mask = 0x7F;
	} else {
		pcm_val += BIAS;
		mask = 0xFF;
	}

	/* Convert the scaled magnitude to segment number. */
	seg = search(pcm_val, seg_end, 8);

	/*
	 * Combine the sign, segment, quantization bits;
	 * and complement the code word.
	 */
	if (seg >= 8)		/* out of range, return maximum value. */
		return (0x7F ^ mask);
	else {
		uval = (seg << 4) | ((pcm_val >> (seg + 3)) & 0xF);
		return (uval ^ mask);
	}

}

/*
 * ulaw2linear() - Convert a u-law value to 16-bit linear PCM
 *
 * First, a biased linear code is derived from the code word. An unbiased
 * output can then be obtained by subtracting 33 from the biased code.
 *
 * Note that this function expects to be passed the complement of the
 * original code word. This is in keeping with ISDN conventions.
 */
int _af_ulaw2linear (unsigned char u_val)
{
	int		t;

	/* Complement to obtain normal u-law value. */
	u_val = ~u_val;

	/*
	 * Extract and bias the quantization bits. Then
	 * shift up by the segment number and subtract out the bias.
	 */
	t = ((u_val & QUANT_MASK) << 3) + BIAS;
	t <<= ((unsigned)u_val & SEG_MASK) >> SEG_SHIFT;

	return ((u_val & SIGN_BIT) ? (BIAS - t) : (t - BIAS));
}

#endif

// file: openclose.cpp
/*
	Audio File Library
	Copyright (C) 2000-2001, Silicon Graphics, Inc.

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/


#include <stdlib.h>
#include <assert.h>
#include <string.h>

#ifdef HAVE_UNISTD_H
#include <unistd.h>
#endif

#include <audiofile.h>


static status _afOpenFile (int access, File *f, const char *filename,
	AFfilehandle *file, AFfilesetup filesetup);

int _af_identify (File *f, int *implemented)
{
	if (!f->canSeek())
	{
		_af_error(AF_BAD_LSEEK, "Cannot seek in file");
		return AF_FILE_UNKNOWN;
	}

	AFfileoffset curpos = f->tell();

	for (int i=0; i<_AF_NUM_UNITS; i++)
	{
		if (_af_units[i].recognize &&
			_af_units[i].recognize(f))
		{
			if (implemented != NULL)
				*implemented = _af_units[i].implemented;
			f->seek(curpos, File::SeekFromBeginning);
			return _af_units[i].fileFormat;
		}
	}

	f->seek(curpos, File::SeekFromBeginning);

	if (implemented != NULL)
		*implemented = false;

	return AF_FILE_UNKNOWN;
}

int afIdentifyFD (int fd)
{
	/*
		Duplicate the file descriptor since otherwise the
		original file descriptor would get closed when we close
		the virtual file below.
	*/
	fd = dup(fd);
	File *f = File::create(fd, File::ReadAccess);

	int result = _af_identify(f, NULL);

	delete f;

	return result;
}

int afIdentifyNamedFD (int fd, const char *filename, int *implemented)
{
	/*
		Duplicate the file descriptor since otherwise the
		original file descriptor would get closed when we close
		the virtual file below.
	*/
	fd = dup(fd);

	File *f = File::create(fd, File::ReadAccess);
	if (!f)
	{
		_af_error(AF_BAD_OPEN, "could not open file '%s'", filename);
		return AF_FILE_UNKNOWN;
	}

	int result = _af_identify(f, implemented);

	delete f;

	return result;
}

AFfilehandle afOpenFD (int fd, const char *mode, AFfilesetup setup)
{
	if (!mode)
	{
		_af_error(AF_BAD_ACCMODE, "null access mode");
		return AF_NULL_FILEHANDLE;
	}

	int access;
	if (mode[0] == 'r')
	{
		access = _AF_READ_ACCESS;
	}
	else if (mode[0] == 'w')
	{
		access = _AF_WRITE_ACCESS;
	}
	else
	{
		_af_error(AF_BAD_ACCMODE, "unrecognized access mode '%s'", mode);
		return AF_NULL_FILEHANDLE;
	}

	File *f = File::create(fd, access == _AF_READ_ACCESS ?
		File::ReadAccess : File::WriteAccess);

	AFfilehandle filehandle = NULL;
	if (_afOpenFile(access, f, NULL, &filehandle, setup) != AF_SUCCEED)
	{
		delete f;
	}

	return filehandle;
}

AFfilehandle afOpenNamedFD (int fd, const char *mode, AFfilesetup setup,
	const char *filename)
{
	if (!mode)
	{
		_af_error(AF_BAD_ACCMODE, "null access mode");
		return AF_NULL_FILEHANDLE;
	}

	int access;
	if (mode[0] == 'r')
		access = _AF_READ_ACCESS;
	else if (mode[0] == 'w')
		access = _AF_WRITE_ACCESS;
	else
	{
		_af_error(AF_BAD_ACCMODE, "unrecognized access mode '%s'", mode);
		return AF_NULL_FILEHANDLE;
	}

	File *f = File::create(fd, access == _AF_READ_ACCESS ?
		File::ReadAccess : File::WriteAccess);

	AFfilehandle filehandle;
	if (_afOpenFile(access, f, filename, &filehandle, setup) != AF_SUCCEED)
	{
		delete f;
	}

	return filehandle;
}

AFfilehandle afOpenFile (const char *filename, const char *mode, AFfilesetup setup)
{
	if (!mode)
	{
		_af_error(AF_BAD_ACCMODE, "null access mode");
		return AF_NULL_FILEHANDLE;
	}

	int access;
	if (mode[0] == 'r')
	{
		access = _AF_READ_ACCESS;
	}
	else if (mode[0] == 'w')
	{
		access = _AF_WRITE_ACCESS;
	}
	else
	{
		_af_error(AF_BAD_ACCMODE, "unrecognized access mode '%s'", mode);
		return AF_NULL_FILEHANDLE;
	}

	File *f = File::open(filename,
		access == _AF_READ_ACCESS ? File::ReadAccess : File::WriteAccess);
	if (!f)
	{
		_af_error(AF_BAD_OPEN, "could not open file '%s'", filename);
		return AF_NULL_FILEHANDLE;
	}

	AFfilehandle filehandle;
	if (_afOpenFile(access, f, filename, &filehandle, setup) != AF_SUCCEED)
	{
		delete f;
	}

	return filehandle;
}

AFfilehandle afOpenVirtualFile (AFvirtualfile *vf, const char *mode,
	AFfilesetup setup)
{
	if (!vf)
	{
		_af_error(AF_BAD_OPEN, "null virtual file");
		return AF_NULL_FILEHANDLE;
	}

	if (!mode)
	{
		_af_error(AF_BAD_ACCMODE, "null access mode");
		return AF_NULL_FILEHANDLE;
	}

	int access;
	if (mode[0] == 'r')
	{
		access = _AF_READ_ACCESS;
	}
	else if (mode[0] == 'w')
	{
		access = _AF_WRITE_ACCESS;
	}
	else
	{
		_af_error(AF_BAD_ACCMODE, "unrecognized access mode '%s'", mode);
		return AF_NULL_FILEHANDLE;
	}

	File *f = File::create(vf,
		access == _AF_READ_ACCESS ? File::ReadAccess : File::WriteAccess);
	if (!f)
	{
		_af_error(AF_BAD_OPEN, "could not open virtual file");
		return AF_NULL_FILEHANDLE;
	}

	AFfilehandle filehandle;
	if (_afOpenFile(access, f, NULL, &filehandle, setup) != AF_SUCCEED)
	{
		delete f;
	}

	return filehandle;
}

static status _afOpenFile (int access, File *f, const char *filename,
	AFfilehandle *file, AFfilesetup filesetup)
{
	int	fileFormat = AF_FILE_UNKNOWN;
	int	implemented = true;

	int		userSampleFormat = 0;
	double		userSampleRate = 0.0;
	PCMInfo	userPCM = {0};
	bool		userFormatSet = false;

	AFfilehandle	filehandle = AF_NULL_FILEHANDLE;
	AFfilesetup	completesetup = AF_NULL_FILESETUP;

	*file = AF_NULL_FILEHANDLE;

	if (access == _AF_WRITE_ACCESS || filesetup != AF_NULL_FILESETUP)
	{
		if (!_af_filesetup_ok(filesetup))
			return AF_FAIL;

		fileFormat = filesetup->fileFormat;
		if (access == _AF_READ_ACCESS && fileFormat != AF_FILE_RAWDATA)
		{
			_af_error(AF_BAD_FILESETUP,
				"warning: opening file for read access: "
				"ignoring file setup with non-raw file format");
			filesetup = AF_NULL_FILESETUP;
			fileFormat = _af_identify(f, &implemented);
		}
	}
	else if (filesetup == AF_NULL_FILESETUP)
		fileFormat = _af_identify(f, &implemented);

	if (fileFormat == AF_FILE_UNKNOWN)
	{
		if (filename != NULL)
			_af_error(AF_BAD_NOT_IMPLEMENTED,
				"'%s': unrecognized audio file format",
				filename);
		else
			_af_error(AF_BAD_NOT_IMPLEMENTED,
				"unrecognized audio file format");
		return AF_FAIL;
	}

	const char *formatName = _af_units[fileFormat].name;

	if (!implemented)
	{
		_af_error(AF_BAD_NOT_IMPLEMENTED,
			"%s format not currently supported", formatName);
	}

	completesetup = NULL;
	if (filesetup != AF_NULL_FILESETUP)
	{
		userSampleFormat = filesetup->tracks[0].f.sampleFormat;
		userPCM = filesetup->tracks[0].f.pcm;
		userSampleRate = filesetup->tracks[0].f.sampleRate;
		userFormatSet = true;
		if ((completesetup = _af_units[fileFormat].completesetup(filesetup)) == NULL)
			return AF_FAIL;
	}

	filehandle = _AFfilehandle::create(fileFormat);
	if (!filehandle)
	{
		if (completesetup)
			afFreeFileSetup(completesetup);
		return AF_FAIL;
	}

	filehandle->m_fh = f;
	filehandle->m_access = access;
	filehandle->m_seekok = f->canSeek();
	if (filename != NULL)
		filehandle->m_fileName = _af_strdup(filename);
	else
		filehandle->m_fileName = NULL;
	filehandle->m_fileFormat = fileFormat;

	status result = access == _AF_READ_ACCESS ?
		filehandle->readInit(completesetup) :
		filehandle->writeInit(completesetup);

	if (result != AF_SUCCEED)
	{
		delete filehandle;
		filehandle = AF_NULL_FILEHANDLE;
		if (completesetup)
			afFreeFileSetup(completesetup);
		return AF_FAIL;
	}

	if (completesetup)
		afFreeFileSetup(completesetup);

	/*
		Initialize virtual format.
	*/
	for (int t=0; t<filehandle->m_trackCount; t++)
	{
		Track *track = &filehandle->m_tracks[t];

		track->v = track->f;

		if (userFormatSet)
		{
			track->v.sampleFormat = userSampleFormat;
			track->v.pcm = userPCM;
			track->v.sampleRate = userSampleRate;
		}

		track->v.compressionType = AF_COMPRESSION_NONE;
		track->v.compressionParams = NULL;

#if WORDS_BIGENDIAN
		track->v.byteOrder = AF_BYTEORDER_BIGENDIAN;
#else
		track->v.byteOrder = AF_BYTEORDER_LITTLEENDIAN;
#endif

		track->ms = new ModuleState();
		if (track->ms->init(filehandle, track) == AF_FAIL)
		{
			delete filehandle;
			return AF_FAIL;
		}
	}

	*file = filehandle;

	return AF_SUCCEED;
}

int afSyncFile (AFfilehandle handle)
{
	if (!_af_filehandle_ok(handle))
		return -1;

	if (handle->m_access == _AF_WRITE_ACCESS)
	{
		/* Finish writes on all tracks. */
		for (int trackno = 0; trackno < handle->m_trackCount; trackno++)
		{
			Track *track = &handle->m_tracks[trackno];

			if (track->ms->isDirty() && track->ms->setup(handle, track) == AF_FAIL)
				return -1;

			if (track->ms->sync(handle, track) != AF_SUCCEED)
				return -1;
		}

		/* Update file headers. */
		if (handle->update() != AF_SUCCEED)
			return AF_FAIL;
	}
	else if (handle->m_access == _AF_READ_ACCESS)
	{
		/* Do nothing. */
	}
	else
	{
		_af_error(AF_BAD_ACCMODE, "unrecognized access mode %d",
			handle->m_access);
		return AF_FAIL;
	}

	return AF_SUCCEED;
}

int afCloseFile (AFfilehandle file)
{
	int	err;

	if (!_af_filehandle_ok(file))
		return -1;

	afSyncFile(file);

	err = file->m_fh->close();
	if (err < 0)
		_af_error(AF_BAD_CLOSE, "close returned %d", err);

	delete file->m_fh;
	delete file;

	return 0;
}

// file: pcm.cpp
/*
	Audio File Library
	Copyright (C) 1999-2000, Michael Pruett <michael@68k.org>
	Copyright (C) 2000-2001, Silicon Graphics, Inc.

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

/*
	pcm.cpp

	This file declares default PCM mappings and defines routines
	for accessing and modifying PCM mappings in a track.
*/



extern const PCMInfo _af_default_signed_integer_pcm_mappings[] =
{
	{0, 0, 0, 0},
	{SLOPE_INT8, 0, MIN_INT8, MAX_INT8},
	{SLOPE_INT16, 0, MIN_INT16, MAX_INT16},
	{SLOPE_INT24, 0, MIN_INT24, MAX_INT24},
	{SLOPE_INT32, 0, MIN_INT32, MAX_INT32}
};

extern const PCMInfo _af_default_unsigned_integer_pcm_mappings[] =
{
	{0, 0, 0, 0},
	{SLOPE_INT8, INTERCEPT_U_INT8, 0, MAX_U_INT8},
	{SLOPE_INT16, INTERCEPT_U_INT16, 0, MAX_U_INT16},
	{SLOPE_INT24, INTERCEPT_U_INT24, 0, MAX_U_INT24},
	{SLOPE_INT32, INTERCEPT_U_INT32, 0, MAX_U_INT32}
};

extern const PCMInfo _af_default_float_pcm_mapping =
{1, 0, 0, 0};

extern const PCMInfo _af_default_double_pcm_mapping =
{1, 0, 0, 0};

/*
	Initialize the PCM mapping for a given track.
*/
void afInitPCMMapping (AFfilesetup setup, int trackid,
	double slope, double intercept, double minClip, double maxClip)
{
	if (!_af_filesetup_ok(setup))
		return;

	TrackSetup *track = setup->getTrack(trackid);
	if (!track)
		return;

	track->f.pcm.slope = slope;
	track->f.pcm.intercept = intercept;
	track->f.pcm.minClip = minClip;
	track->f.pcm.maxClip = maxClip;
}

int afSetVirtualPCMMapping (AFfilehandle file, int trackid,
	double slope, double intercept, double minClip, double maxClip)
{
	if (!_af_filehandle_ok(file))
		return -1;

	Track *track = file->getTrack(trackid);
	if (!track)
		return -1;

	track->v.pcm.slope = slope;
	track->v.pcm.intercept = intercept;
	track->v.pcm.minClip = minClip;
	track->v.pcm.maxClip = maxClip;

	track->ms->setDirty();

	return 0;
}

int afSetTrackPCMMapping (AFfilehandle file, int trackid,
	double slope, double intercept, double minClip, double maxClip)
{
	if (!_af_filehandle_ok(file))
		return -1;

	Track *track = file->getTrack(trackid);
	if (!track)
		return -1;

	/*
		NOTE: this is highly unusual: we don't ordinarily
		change track.f after the file is opened.

		PCM mapping is the exception because this information
		is not encoded in sound files' headers using today's
		formats, so the user will probably want to set this
		information on a regular basis.  The defaults may or
		may not be what the user wants.
	*/

	track->f.pcm.slope = slope;
	track->f.pcm.intercept = intercept;
	track->f.pcm.minClip = minClip;
	track->f.pcm.maxClip = maxClip;

	track->ms->setDirty();

	return 0;
}

void afGetPCMMapping (AFfilehandle file, int trackid,
	double *slope, double *intercept, double *minClip, double *maxClip)
{
	if (!_af_filehandle_ok(file))
		return;

	Track *track = file->getTrack(trackid);
	if (!track)
		return;

	if (slope)
		*slope = track->f.pcm.slope;
	if (intercept)
		*intercept = track->f.pcm.intercept;
	if (minClip)
		*minClip = track->f.pcm.minClip;
	if (maxClip)
		*maxClip = track->f.pcm.maxClip;
}

void afGetVirtualPCMMapping (AFfilehandle file, int trackid,
	double *slope, double *intercept, double *minClip, double *maxClip)
{
	if (!_af_filehandle_ok(file))
		return;

	Track *track = file->getTrack(trackid);
	if (!track)
		return;

	if (slope)
		*slope = track->v.pcm.slope;
	if (intercept)
		*intercept = track->v.pcm.intercept;
	if (minClip)
		*minClip = track->v.pcm.minClip;
	if (maxClip)
		*maxClip = track->v.pcm.maxClip;
}

// file: query.cpp
/*
	Audio File Library
	Copyright (C) 1998-2000, Michael Pruett <michael@68k.org>

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

/*
	query.cpp

	This file contains the implementation of the Audio File Library's
	query mechanism.  Querying through the afQuery calls can allow the
	programmer to determine the capabilities and characteristics of
	the Audio File Library implementation and its supported formats.
*/


#include <assert.h>
#include <stdlib.h>


AUpvlist _afQueryFileFormat (int arg1, int arg2, int arg3, int arg4);
AUpvlist _afQueryInstrument (int arg1, int arg2, int arg3, int arg4);
AUpvlist _afQueryInstrumentParameter (int arg1, int arg2, int arg3, int arg4);
AUpvlist _afQueryLoop (int arg1, int arg2, int arg3, int arg4);
AUpvlist _afQueryMarker (int arg1, int arg2, int arg3, int arg4);
AUpvlist _afQueryMiscellaneous (int arg1, int arg2, int arg3, int arg4);
AUpvlist _afQueryCompression (int arg1, int arg2, int arg3, int arg4);
AUpvlist _afQueryCompressionParameter (int arg1, int arg2, int arg3, int arg4);

AUpvlist afQuery (int querytype, int arg1, int arg2, int arg3, int arg4)
{
	switch (querytype)
	{
		case AF_QUERYTYPE_INST:
			return _afQueryInstrument(arg1, arg2, arg3, arg4);
		case AF_QUERYTYPE_INSTPARAM:
			return _afQueryInstrumentParameter(arg1, arg2, arg3, arg4);
		case AF_QUERYTYPE_LOOP:
			return _afQueryLoop(arg1, arg2, arg3, arg4);
		case AF_QUERYTYPE_FILEFMT:
			return _afQueryFileFormat(arg1, arg2, arg3, arg4);
		case AF_QUERYTYPE_COMPRESSION:
			return _afQueryCompression(arg1, arg2, arg3, arg4);
		case AF_QUERYTYPE_COMPRESSIONPARAM:
			/* FIXME: This selector is not implemented. */
			return AU_NULL_PVLIST;
		case AF_QUERYTYPE_MISC:
			/* FIXME: This selector is not implemented. */
			return AU_NULL_PVLIST;
		case AF_QUERYTYPE_MARK:
			return _afQueryMarker(arg1, arg2, arg3, arg4);
	}

	_af_error(AF_BAD_QUERYTYPE, "bad query type");
	return AU_NULL_PVLIST;
}

/* ARGSUSED3 */
AUpvlist _afQueryFileFormat (int arg1, int arg2, int arg3, int arg4)
{
	switch (arg1)
	{
		/* The following select only on arg1. */
		case AF_QUERY_ID_COUNT:
		{
			int	count = 0, idx;
			for (idx = 0; idx < _AF_NUM_UNITS; idx++)
				if (_af_units[idx].implemented)
					count++;
			return _af_pv_long(count);
		}
		/* NOTREACHED */
		break;

		case AF_QUERY_IDS:
		{
			int	count = 0, idx;
			int	*buffer;

			buffer = (int *) _af_calloc(_AF_NUM_UNITS, sizeof (int));
			if (buffer == NULL)
				return AU_NULL_PVLIST;

			for (idx = 0; idx < _AF_NUM_UNITS; idx++)
				if (_af_units[idx].implemented)
					buffer[count++] = idx;

			if (count == 0)
			{
				free(buffer);
				return AU_NULL_PVLIST;
			}

			return _af_pv_pointer(buffer);
		}
		/* NOTREACHED */
		break;

		/* The following select on arg2. */
		case AF_QUERY_LABEL:
			if (arg2 < 0 || arg2 >= _AF_NUM_UNITS)
				return AU_NULL_PVLIST;
			return _af_pv_pointer(const_cast<char *>(_af_units[arg2].label));

		case AF_QUERY_NAME:
			if (arg2 < 0 || arg2 >= _AF_NUM_UNITS)
				return AU_NULL_PVLIST;
			return _af_pv_pointer(const_cast<char *>(_af_units[arg2].name));

		case AF_QUERY_DESC:
			if (arg2 < 0 || arg2 >= _AF_NUM_UNITS)
				return AU_NULL_PVLIST;
			return _af_pv_pointer(const_cast<char *>(_af_units[arg2].description));

		case AF_QUERY_IMPLEMENTED:
			if (arg2 < 0 || arg2 >= _AF_NUM_UNITS)
				return _af_pv_long(0);
			return _af_pv_long(_af_units[arg2].implemented);

		/* The following select on arg3. */
		case AF_QUERY_SAMPLE_FORMATS:
			if (arg3 < 0 || arg3 >= _AF_NUM_UNITS)
				return AU_NULL_PVLIST;
			switch (arg2)
			{
				case AF_QUERY_DEFAULT:
					return _af_pv_long(_af_units[arg3].defaultSampleFormat);
				default:
					break;
			}
			/* NOTREACHED */
			break;

		case AF_QUERY_SAMPLE_SIZES:
			if (arg3 < 0 || arg3 >= _AF_NUM_UNITS)
				return AU_NULL_PVLIST;

			switch (arg2)
			{
				case AF_QUERY_DEFAULT:
					return _af_pv_long(_af_units[arg3].defaultSampleWidth);
				default:
					break;
			}
			/* NOTREACHED */
			break;

		case AF_QUERY_COMPRESSION_TYPES:
		{
			int	idx, count;
			int	*buffer;

			if (arg3 < 0 || arg3 >= _AF_NUM_UNITS)
			{
				_af_error(AF_BAD_QUERY,
					"unrecognized file format %d", arg3);
				return AU_NULL_PVLIST;
			}

			switch (arg2)
			{
				case AF_QUERY_VALUE_COUNT:
					count = _af_units[arg3].compressionTypeCount;
					return _af_pv_long(count);

				case AF_QUERY_VALUES:
					count = _af_units[arg3].compressionTypeCount;
					if (count == 0)
						return AU_NULL_PVLIST;

					buffer = (int *) _af_calloc(count, sizeof (int));
					if (buffer == NULL)
						return AU_NULL_PVLIST;

					for (idx = 0; idx < count; idx++)
					{
						buffer[idx] = _af_units[arg3].compressionTypes[idx];
					}

					return _af_pv_pointer(buffer);
			}
		}
		break;
	}

	_af_error(AF_BAD_QUERY, "bad query selector");
	return AU_NULL_PVLIST;
}

long afQueryLong (int querytype, int arg1, int arg2, int arg3, int arg4)
{
	AUpvlist	list;
	int		type;
	long		value;

	list = afQuery(querytype, arg1, arg2, arg3, arg4);
	if (list == AU_NULL_PVLIST)
		return -1;
	AUpvgetvaltype(list, 0, &type);
	if (type != AU_PVTYPE_LONG)
		return -1;
	AUpvgetval(list, 0, &value);
	AUpvfree(list);
	return value;
}

double afQueryDouble (int querytype, int arg1, int arg2, int arg3, int arg4)
{
	AUpvlist	list;
	int		type;
	double		value;

	list = afQuery(querytype, arg1, arg2, arg3, arg4);
	if (list == AU_NULL_PVLIST)
		return -1;
	AUpvgetvaltype(list, 0, &type);
	if (type != AU_PVTYPE_DOUBLE)
		return -1;
	AUpvgetval(list, 0, &value);
	AUpvfree(list);
	return value;
}

void *afQueryPointer (int querytype, int arg1, int arg2, int arg3, int arg4)
{
	AUpvlist	list;
	int		type;
	void		*value;

	list = afQuery(querytype, arg1, arg2, arg3, arg4);
	if (list == AU_NULL_PVLIST)
		return NULL;
	AUpvgetvaltype(list, 0, &type);
	if (type != AU_PVTYPE_PTR)
		return NULL;
	AUpvgetval(list, 0, &value);
	AUpvfree(list);
	return value;
}

/* ARGSUSED3 */
AUpvlist _afQueryInstrumentParameter (int arg1, int arg2, int arg3, int arg4)
{
	switch (arg1)
	{
		/* For the following query types, arg2 is the file format. */
		case AF_QUERY_SUPPORTED:
			if (arg2 < 0 || arg2 >= _AF_NUM_UNITS)
				return AU_NULL_PVLIST;
			return _af_pv_long(_af_units[arg2].instrumentParameterCount != 0);

		case AF_QUERY_ID_COUNT:
			if (arg2 < 0 || arg2 >= _AF_NUM_UNITS)
				return AU_NULL_PVLIST;
			return _af_pv_long(_af_units[arg2].instrumentParameterCount);

		case AF_QUERY_IDS:
		{
			int	count;
			int	*buffer;

			if (arg2 < 0 || arg2 >= _AF_NUM_UNITS)
				return AU_NULL_PVLIST;
			count = _af_units[arg2].instrumentParameterCount;
			if (count == 0)
				return AU_NULL_PVLIST;
			buffer = (int *) _af_calloc(count, sizeof (int));
			if (buffer == NULL)
				return AU_NULL_PVLIST;
			for (int i=0; i<count; i++)
				buffer[i] = _af_units[arg2].instrumentParameters[i].id;
			return _af_pv_pointer(buffer);
		}
		/* NOTREACHED */
		break;

		/*
			For the next few query types, arg2 is the file
			format and arg3 is the instrument parameter id.
		*/
		case AF_QUERY_TYPE:
		{
			int	idx;

			if (arg2 < 0 || arg2 >= _AF_NUM_UNITS)
				return AU_NULL_PVLIST;

			idx = _af_instparam_index_from_id(arg2, arg3);
			if (idx<0)
				return AU_NULL_PVLIST;
			return _af_pv_long(_af_units[arg2].instrumentParameters[idx].type);
		}

		case AF_QUERY_NAME:
		{
			int	idx;

			if (arg2 < 0 || arg2 >= _AF_NUM_UNITS)
				return AU_NULL_PVLIST;
			idx = _af_instparam_index_from_id(arg2, arg3);
			if (idx < 0)
				return AU_NULL_PVLIST;
			return _af_pv_pointer(const_cast<char *>(_af_units[arg2].instrumentParameters[idx].name));
		}

		case AF_QUERY_DEFAULT:
		{
			int	idx;

			if (arg2 < 0 || arg2 >= _AF_NUM_UNITS)
				return AU_NULL_PVLIST;
			idx = _af_instparam_index_from_id(arg2, arg3);
			if (idx >= 0)
			{
				AUpvlist	ret = AUpvnew(1);
				AUpvsetparam(ret, 0, _af_units[arg2].instrumentParameters[idx].id);
				AUpvsetvaltype(ret, 0, _af_units[arg2].instrumentParameters[idx].type);
				AUpvsetval(ret, 0, const_cast<AFPVu *>(&_af_units[arg2].instrumentParameters[idx].defaultValue));
				return ret;
			}
			return AU_NULL_PVLIST;
		}
	}

	_af_error(AF_BAD_QUERY, "bad query selector");
	return AU_NULL_PVLIST;
}

/* ARGSUSED2 */
AUpvlist _afQueryLoop (int arg1, int arg2, int arg3, int arg4)
{
	if (arg2 < 0 || arg2 >= _AF_NUM_UNITS)
		return AU_NULL_PVLIST;

	switch (arg1)
	{
		case AF_QUERY_SUPPORTED:
			return _af_pv_long(_af_units[arg2].loopPerInstrumentCount != 0);
		case AF_QUERY_MAX_NUMBER:
			return _af_pv_long(_af_units[arg2].loopPerInstrumentCount);
	}

	_af_error(AF_BAD_QUERY, "bad query selector");
	return AU_NULL_PVLIST;
}

/* ARGSUSED2 */
AUpvlist _afQueryInstrument (int arg1, int arg2, int arg3, int arg4)
{
	switch (arg1)
	{
		case AF_QUERY_SUPPORTED:
			if (arg2 < 0 || arg2 >= _AF_NUM_UNITS)
				return AU_NULL_PVLIST;
			return _af_pv_long(_af_units[arg2].instrumentCount != 0);

		case AF_QUERY_MAX_NUMBER:
			if (arg2 < 0 || arg2 >= _AF_NUM_UNITS)
				return AU_NULL_PVLIST;
			return _af_pv_long(_af_units[arg2].instrumentCount);
	}

	_af_error(AF_BAD_QUERY, "bad query selector");
	return AU_NULL_PVLIST;
}

/* ARGSUSED0 */
AUpvlist _afQueryMiscellaneous (int arg1, int arg2, int arg3, int arg4)
{
	_af_error(AF_BAD_NOT_IMPLEMENTED, "not implemented yet");
	return AU_NULL_PVLIST;
}

/* ARGSUSED2 */
AUpvlist _afQueryMarker (int arg1, int arg2, int arg3, int arg4)
{
	switch (arg1)
	{
		case AF_QUERY_SUPPORTED:
			return _af_pv_long(_af_units[arg2].markerCount != 0);
		case AF_QUERY_MAX_NUMBER:
			return _af_pv_long(_af_units[arg2].markerCount);
	}

	_af_error(AF_BAD_QUERY, "bad query selector");
	return AU_NULL_PVLIST;
}

/* ARGSUSED0 */
AUpvlist _afQueryCompression (int arg1, int arg2, int arg3, int arg4)
{
	const CompressionUnit *unit = NULL;

	switch (arg1)
	{
		case AF_QUERY_ID_COUNT:
		{
			int count = 0;
			for (int i = 0; i < _AF_NUM_COMPRESSION; i++)
				if (_af_compression[i].implemented)
					count++;
			return _af_pv_long(count);
		}

		case AF_QUERY_IDS:
		{
			int *buf = (int *) _af_calloc(_AF_NUM_COMPRESSION, sizeof (int));
			if (!buf)
				return AU_NULL_PVLIST;

			int count = 0;
			for (int i = 0; i < _AF_NUM_COMPRESSION; i++)
			{
				if (_af_compression[i].implemented)
					buf[count++] = _af_compression[i].compressionID;
			}
			return _af_pv_pointer(buf);
		}

		case AF_QUERY_IMPLEMENTED:
			unit = _af_compression_unit_from_id(arg2);
			if (!unit)
				return _af_pv_long(0);
			return _af_pv_long(unit->implemented);

		case AF_QUERY_NATIVE_SAMPFMT:
			unit = _af_compression_unit_from_id(arg2);
			if (!unit)
				return AU_NULL_PVLIST;
			return _af_pv_long(unit->nativeSampleFormat);

		case AF_QUERY_NATIVE_SAMPWIDTH:
			unit = _af_compression_unit_from_id(arg2);
			if (!unit)
				return AU_NULL_PVLIST;
			return _af_pv_long(unit->nativeSampleWidth);

		case AF_QUERY_LABEL:
			unit = _af_compression_unit_from_id(arg2);
			if (!unit)
				return AU_NULL_PVLIST;
			return _af_pv_pointer(const_cast<char *>(unit->label));

		case AF_QUERY_NAME:
			unit = _af_compression_unit_from_id(arg2);
			if (!unit)
				return AU_NULL_PVLIST;
			return _af_pv_pointer(const_cast<char *>(unit->shortname));

		case AF_QUERY_DESC:
			unit = _af_compression_unit_from_id(arg2);
			if (!unit)
				return AU_NULL_PVLIST;
			return _af_pv_pointer(const_cast<char *>(unit->name));
	}

	_af_error(AF_BAD_QUERY, "unrecognized query selector %d\n", arg1);
	return AU_NULL_PVLIST;
}

// file: units.cpp
/*
	Audio File Library
	Copyright (C) 2000-2001, Silicon Graphics, Inc.

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

/*
	units.cpp

	This file contains the file format units.
*/






const Unit _af_units[_AF_NUM_UNITS] =
{
	{
		AF_FILE_RAWDATA,
		"Raw Data", "Raw Sound Data", "raw",
		true,
		&RawFile::completeSetup,
		&RawFile::recognize,
		AF_SAMPFMT_TWOSCOMP, 16,
		_AF_RAW_NUM_COMPTYPES,
		_af_raw_compression_types,
		0,	/* maximum marker count */
		0,	/* maximum instrument count */
		0,	/* maxium number of loops per instrument */
		0, NULL,
	},
	{
		AF_FILE_AIFFC,
		"AIFF-C", "AIFF-C File Format", "aifc",
		true,
		AIFFFile::completeSetup,
		AIFFFile::recognizeAIFFC,
		AF_SAMPFMT_TWOSCOMP, 16,
		_AF_AIFFC_NUM_COMPTYPES,
		_af_aiffc_compression_types,
		65535,	/* maximum marker count */
		1,	/* maximum instrument count */
		2,	/* maximum number of loops per instrument */
		_AF_AIFF_NUM_INSTPARAMS,
		_af_aiff_inst_params
	},
	{
		AF_FILE_AIFF,
		"AIFF", "Audio Interchange File Format", "aiff",
		true,
		AIFFFile::completeSetup,
		AIFFFile::recognizeAIFF,
		AF_SAMPFMT_TWOSCOMP, 16,
		0,	/* supported compression types */
		NULL,
		65535,	/* maximum marker count */
		1,	/* maximum instrument count */
		2,	/* maximum number of loops per instrument */
		_AF_AIFF_NUM_INSTPARAMS,
		_af_aiff_inst_params
	},
	{
		AF_FILE_WAVE,
		"MS RIFF WAVE", "Microsoft RIFF WAVE Format", "wave",
		true,
		WAVEFile::completeSetup,
		WAVEFile::recognize,
		AF_SAMPFMT_TWOSCOMP, 16,
		_AF_WAVE_NUM_COMPTYPES,
		_af_wave_compression_types,
		AF_NUM_UNLIMITED,	/* maximum marker count */
		1,			/* maximum instrument count */
		AF_NUM_UNLIMITED,	/* maximum number of loops per instrument */
		_AF_WAVE_NUM_INSTPARAMS,
		_af_wave_inst_params
	},
};

const CompressionUnit _af_compression[_AF_NUM_COMPRESSION] =
{
	{
		AF_COMPRESSION_NONE,
		true,
		"none",	/* label */
		"none",	/* short name */
		"not compressed",
		1.0,
		AF_SAMPFMT_TWOSCOMP, 16,
		false,	/* needsRebuffer */
		false,	/* multiple_of */
		_af_pcm_format_ok,
		_AFpcminitcompress, _AFpcminitdecompress
	},
	{
		AF_COMPRESSION_G711_ULAW,
		true,
		"ulaw",	/* label */
		"CCITT G.711 u-law",	/* shortname */
		"CCITT G.711 u-law",
		2.0,
		AF_SAMPFMT_TWOSCOMP, 16,
		false,	/* needsRebuffer */
		false,	/* multiple_of */
		_af_g711_format_ok,
		_AFg711initcompress, _AFg711initdecompress
	},
	{
		AF_COMPRESSION_G711_ALAW,
		true,
		"alaw",	/* label */
		"CCITT G.711 A-law",	/* short name */
		"CCITT G.711 A-law",
		2.0,
		AF_SAMPFMT_TWOSCOMP, 16,
		false,	/* needsRebuffer */
		false,	/* multiple_of */
		_af_g711_format_ok,
		_AFg711initcompress, _AFg711initdecompress
	},
	{
		AF_COMPRESSION_MS_ADPCM,
		true,
		"msadpcm",	/* label */
		"MS ADPCM",	/* short name */
		"Microsoft ADPCM",
		4.0,
		AF_SAMPFMT_TWOSCOMP, 16,
		true,	/* needsRebuffer */
		false,	/* multiple_of */
		_af_ms_adpcm_format_ok,
		_af_ms_adpcm_init_compress, _af_ms_adpcm_init_decompress
	},
};

// file: util.cpp
/*
	Audio File Library
	Copyright (C) 1998-2000, Michael Pruett <michael@68k.org>
	Copyright (C) 2000, Silicon Graphics, Inc.

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the
	Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
	Boston, MA  02110-1301  USA
*/

/*
	util.c

	This file contains general utility routines for the Audio File
	Library.
*/


#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>



/*
	_af_filesetup_ok and _af_filehandle_ok are sanity check routines
	which are called at the beginning of every external subroutine.
*/
bool _af_filesetup_ok (AFfilesetup setup)
{
	if (setup == AF_NULL_FILESETUP)
	{
		_af_error(AF_BAD_FILESETUP, "null file setup");
		return false;
	}
	if (setup->valid != _AF_VALID_FILESETUP)
	{
		_af_error(AF_BAD_FILESETUP, "invalid file setup");
		return false;
	}
	return true;
}

bool _af_filehandle_ok (AFfilehandle file)
{
	if (file == AF_NULL_FILEHANDLE)
	{
		_af_error(AF_BAD_FILEHANDLE, "null file handle");
		return false;
	}
	if (file->m_valid != _AF_VALID_FILEHANDLE)
	{
		_af_error(AF_BAD_FILEHANDLE, "invalid file handle");
		return false;
	}
	return true;
}

void *_af_malloc (size_t size)
{
	void	*p;

	if (size <= 0)
	{
		_af_error(AF_BAD_MALLOC, "bad memory allocation size request %zd", size);
		return NULL;
	}

	p = malloc(size);

#ifdef AF_DEBUG
	if (p)
		memset(p, 0xff, size);
#endif

	if (p == NULL)
	{
		_af_error(AF_BAD_MALLOC, "allocation of %zd bytes failed", size);
		return NULL;
	}

	return p;
}

char *_af_strdup (const char *s)
{
	char *p = (char *) malloc(strlen(s) + 1);

	if (p)
		strcpy(p, s);

	return p;
}

void *_af_realloc (void *p, size_t size)
{
	if (size <= 0)
	{
		_af_error(AF_BAD_MALLOC, "bad memory allocation size request %zd", size);
		return NULL;
	}

	p = realloc(p, size);

	if (p == NULL)
	{
		_af_error(AF_BAD_MALLOC, "allocation of %zd bytes failed", size);
		return NULL;
	}

	return p;
}

void *_af_calloc (size_t nmemb, size_t size)
{
	void	*p;

	if (nmemb <= 0 || size <= 0)
	{
		_af_error(AF_BAD_MALLOC, "bad memory allocation size request "
			"%zd elements of %zd bytes each", nmemb, size);
		return NULL;
	}

	p = calloc(nmemb, size);

	if (p == NULL)
	{
		_af_error(AF_BAD_MALLOC, "allocation of %zd bytes failed",
			nmemb*size);
		return NULL;
	}

	return p;
}

AUpvlist _af_pv_long (long val)
{
	AUpvlist	ret = AUpvnew(1);
	AUpvsetparam(ret, 0, 0);
	AUpvsetvaltype(ret, 0, AU_PVTYPE_LONG);
	AUpvsetval(ret, 0, &val);
	return ret;
}

AUpvlist _af_pv_double (double val)
{
	AUpvlist	ret = AUpvnew(1);
	AUpvsetparam(ret, 0, 0);
	AUpvsetvaltype(ret, 0, AU_PVTYPE_DOUBLE);
	AUpvsetval(ret, 0, &val);
	return ret;
}

AUpvlist _af_pv_pointer (void *val)
{
	AUpvlist	ret = AUpvnew(1);
	AUpvsetparam(ret, 0, 0);
	AUpvsetvaltype(ret, 0, AU_PVTYPE_PTR);
	AUpvsetval(ret, 0, &val);
	return ret;
}

bool _af_pv_getlong (AUpvlist pvlist, int param, long *l)
{
	for (int i=0; i<AUpvgetmaxitems(pvlist); i++)
	{
		int	p, t;

		AUpvgetparam(pvlist, i, &p);

		if (p != param)
			continue;

		AUpvgetvaltype(pvlist, i, &t);

		/* Ensure that this parameter is of type AU_PVTYPE_LONG. */
		if (t != AU_PVTYPE_LONG)
			return false;

		AUpvgetval(pvlist, i, l);
		return true;
	}

	return false;
}

bool _af_pv_getdouble (AUpvlist pvlist, int param, double *d)
{
	for (int i=0; i<AUpvgetmaxitems(pvlist); i++)
	{
		int	p, t;

		AUpvgetparam(pvlist, i, &p);

		if (p != param)
			continue;

		AUpvgetvaltype(pvlist, i, &t);

		/* Ensure that this parameter is of type AU_PVTYPE_DOUBLE. */
		if (t != AU_PVTYPE_DOUBLE)
			return false;

		AUpvgetval(pvlist, i, d);
		return true;
	}

	return false;
}

bool _af_pv_getptr (AUpvlist pvlist, int param, void **v)
{
	for (int i=0; i<AUpvgetmaxitems(pvlist); i++)
	{
		int	p, t;

		AUpvgetparam(pvlist, i, &p);

		if (p != param)
			continue;

		AUpvgetvaltype(pvlist, i, &t);

		/* Ensure that this parameter is of type AU_PVTYPE_PTR. */
		if (t != AU_PVTYPE_PTR)
			return false;

		AUpvgetval(pvlist, i, v);
		return true;
	}

	return false;
}

int _af_format_sample_size_uncompressed (const AudioFormat *format, bool stretch3to4)
{
	int	size = 0;

	switch (format->sampleFormat)
	{
		case AF_SAMPFMT_FLOAT:
			size = sizeof (float);
			break;
		case AF_SAMPFMT_DOUBLE:
			size = sizeof (double);
			break;
		default:
			size = (int) (format->sampleWidth + 7) / 8;
			if (format->compressionType == AF_COMPRESSION_NONE &&
				size == 3 && stretch3to4)
				size = 4;
			break;
	}

	return size;
}

float _af_format_sample_size (const AudioFormat *fmt, bool stretch3to4)
{
	const CompressionUnit *unit = _af_compression_unit_from_id(fmt->compressionType);
	float squishFactor = unit->squishFactor;

	return _af_format_sample_size_uncompressed(fmt, stretch3to4) /
		squishFactor;
}

int _af_format_frame_size_uncompressed (const AudioFormat *fmt, bool stretch3to4)
{
	return _af_format_sample_size_uncompressed(fmt, stretch3to4) *
		fmt->channelCount;
}

float _af_format_frame_size (const AudioFormat *fmt, bool stretch3to4)
{
	const CompressionUnit *unit = _af_compression_unit_from_id(fmt->compressionType);
	float squishFactor = unit->squishFactor;

	return _af_format_frame_size_uncompressed(fmt, stretch3to4) /
		squishFactor;
}

/*
	Set the sampleFormat and sampleWidth fields in f, and set the
	PCM info to the appropriate default values for the given sample
	format and sample width.
*/
status _af_set_sample_format (AudioFormat *f, int sampleFormat, int sampleWidth)
{
	switch (sampleFormat)
	{
		case AF_SAMPFMT_UNSIGNED:
		case AF_SAMPFMT_TWOSCOMP:
		if (sampleWidth < 1 || sampleWidth > 32)
		{
			_af_error(AF_BAD_SAMPFMT,
				"illegal sample width %d for integer data",
				sampleWidth);
			return AF_FAIL;
		}
		else
		{
			int bytes;

			f->sampleFormat = sampleFormat;
			f->sampleWidth = sampleWidth;

			bytes = _af_format_sample_size_uncompressed(f, false);

			if (sampleFormat == AF_SAMPFMT_TWOSCOMP)
				f->pcm = _af_default_signed_integer_pcm_mappings[bytes];
			else
				f->pcm = _af_default_unsigned_integer_pcm_mappings[bytes];
		}
		break;

		case AF_SAMPFMT_FLOAT:
			f->sampleFormat = sampleFormat;
			f->sampleWidth = 32;
			f->pcm = _af_default_float_pcm_mapping;
			break;
		case AF_SAMPFMT_DOUBLE:
			f->sampleFormat = sampleFormat;
			f->sampleWidth = 64;      /*for convenience */
			f->pcm = _af_default_double_pcm_mapping;
			break;
		default:
			_af_error(AF_BAD_SAMPFMT, "unknown sample format %d",
				sampleFormat);
			return AF_FAIL;
	}

	return AF_SUCCEED;
}

/*
	Verify the uniqueness of the nids ids given.

	idname is the name of what the ids identify, as in "loop"
	iderr is an error as in AF_BAD_LOOPID
*/
bool _af_unique_ids (const int *ids, int nids, const char *idname, int iderr)
{
	for (int i = 0; i < nids; i++)
	{
		for (int j = 0; j < i; j++)
		{
			if (ids[i] == ids[j])
			{
				_af_error(iderr, "nonunique %s id %d", idname, ids[i]);
				return false;
			}
		}
	}

	return true;
}
