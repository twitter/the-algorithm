# The contents of this file are subject to the Common Public Attribution
# License Version 1.0. (the "License"); you may not use this file except in
# compliance with the License. You may obtain a copy of the License at
# http://code.reddit.com/LICENSE. The License is based on the Mozilla Public
# License Version 1.1, but Sections 14 and 15 have been added to cover use of
# software over a computer network and provide for limited attribution for the
# Original Developer. In addition, Exhibit A has been modified to be consistent
# with Exhibit B.
#
# Software distributed under the License is distributed on an "AS IS" basis,
# WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for
# the specific language governing rights and limitations under the License.
#
# The Original Code is reddit.
#
# The Original Developer is the Initial Developer.  The Initial Developer of
# the Original Code is reddit Inc.
#
# All portions of the code written by reddit are Copyright (c) 2006-2015 reddit
# Inc. All Rights Reserved.
###############################################################################


import snappy
import struct


class HadoopStreamDecompressor(object):
    """This class implements the decompressor-side of the hadoop framing
    format.

    Hadoop fraiming format consists of one or more blocks, each of which is
    composed of one or more compressed subblocks. The block size is the
    uncompressed size, while the subblock size is the size of the compressed
    data.

    https://github.com/andrix/python-snappy/pull/35/files
    """

    __slots__ = ["_buf", "_block_size", "_block_read", "_subblock_size"]

    def __init__(self):
        self._buf = b""
        self._block_size = None
        self._block_read = 0
        self._subblock_size = None

    def decompress(self, data):
        self._buf += data
        output = b""
        while True:
            # decompress block will attempt to decompress  any subblocks if it
            # has already read the block size and subblock size.
            buf = self._decompress_block()
            if len(buf) > 0:
                output += buf
            else:
                break
        return output

    def _decompress_block(self):
        if self._block_size is None:
            if len(self._buf) <= 4:
                return b""
            self._block_size = struct.unpack(">i", self._buf[:4])[0]
            self._buf = self._buf[4:]
        output = b""
        while self._block_read < self._block_size:
            buf = self._decompress_subblock()
            if len(buf) > 0:
                output += buf
            else:
                # Buffer doesn't contain full subblock
                break
        if self._block_read == self._block_size:
            # We finished reading this block, so reinitialize.
            self._block_read = 0
            self._block_size = None
        return output

    def _decompress_subblock(self):
        if self._subblock_size is None:
            if len(self._buf) <= 4:
                return b""
            self._subblock_size = struct.unpack(">i", self._buf[:4])[0]
            self._buf = self._buf[4:]
        # Only attempt to decompress complete subblocks.
        if len(self._buf) < self._subblock_size:
            return b""
        compressed = self._buf[:self._subblock_size]
        self._buf = self._buf[self._subblock_size:]
        uncompressed = snappy.uncompress(compressed)
        self._block_read += len(uncompressed)
        self._subblock_size = None
        return uncompressed

    def flush(self):
        if self._buf != b"":
            raise snappy.UncompressError("chunk truncated")
        return b""

    def copy(self):
        copy = HadoopStreamDecompressor()
        copy._buf = self._buf
        copy._block_size = self._block_size
        copy._block_read = self._block_read
        copy._subblock_size = self._subblock_size
        return copy


def hadoop_decompress(src, dst, blocksize=snappy._STREAM_TO_STREAM_BLOCK_SIZE):
    decompressor = HadoopStreamDecompressor()
    while True:
        buf = src.read(blocksize)
        if not buf:
            break
        buf = decompressor.decompress(buf)
        if buf:
            dst.write(buf)
    decompressor.flush()  # makes sure the stream ended well
