Sound in SM64 consists of three parts: samples, sound banks, and sequences.

Samples represent raw sound data, given as AIFF files with a custom ADPCM-based
compression format that reduces file sizes by ~70% compared to uncompressed
AIFF (with 16-bit samples). The build system automatically converts
uncompressed AIFF files into this format.

Samples are collected into banks, given by directories. The order in which each
bank's samples end up in the final binary is determined by their file names.
Thus, to keep the ROM OK, sample files should be prefixed by a number to
maintain sort order. (Bank directories are ordered by their usages in way
that automatically makes the ROM match.)

Samples cannot be referred to directly from sequence files. Instead, there is
an indirect step in the form of sound banks. Each sound bank refers to a single
sample bank, and amends it with extra information in the form of a JSON file.
This JSON file contains a set of "instruments", which describe how note values
(pitches) map to samples, which pitch corrections to perform, and what ADSR
envelopes to use. The `instrument_list` key maps indices to instruments; these
indices are what sequence files can refer to. A special instrument `percussion`
is also supported, as an array with (usually) 64 different entries, covering
note values 0..63. It is referred to as instrument ID 0x7f.

C-style comments are supported in the JSON. Similar to samples, the sound banks
come in alphabetical order in the ROM, so they should be prefixed to maintain
sort order.

Sequence files are what actually controls the audio. The are in .m64 format,
which is similar to MIDI, but Turing complete. An .m64 file has a sequence
script that can spawn channels, which have channel scripts that can spawn
layers, which have layer scripts that can play notes. Each note is performed
using an instrument from a sound bank. A sequence file can use multiple banks;
`sequences.json` describes the mapping from sequences to sound banks. Channels
can switch between banks using a command. However, in practice most sequences
limit themselves to a single sound bank. The main exception is sequence 0,
which is responsible for sound effects.

Like samples and sound banks, sequence files are included in the ROM in
alphabetical file name order. They can be located in either `sound/sequences/`
or `sound/sequences/<version>/`, and can optionally be given in disassembled
form -- see `include/seq_macros.inc` for more details on the format.

The repo gitignores .m64 and .aiff files by default, unless they include
"custom" somewhere in the name (including in a directory name). Thus, for new
custom-made samples and sequences it is advisable to include that substring
in the file name (this also helps distinguish custom sounds from ones from
the game). `git add -f` also works for adding edited existing files to git.
