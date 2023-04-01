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

import os
import re
import hashlib
from PIL import Image
import subprocess

from r2.lib.static import generate_static_name

SPRITE_PADDING = 6
sprite_line = re.compile(
    r"""background-image:\ *
        url\((?P<filename>.*)\)\ *
        .*
        /\*\ *SPRITE\ *
        (?P<stretch>stretch-x)?\ *
        (?:pixel-ratio=(?P<pixel_ratio>\d))?\ *
        \*/
     """, re.X)


def optimize_png(filename):
    with open(os.path.devnull, 'w') as devnull:
        subprocess.check_call(("/usr/bin/optipng", filename), stdout=devnull)


def _extract_css_info(match):
    image_filename = match.group("filename")
    should_stretch_property = match.group("stretch")
    pixel_ratio_property = match.group("pixel_ratio")
    image_filename = image_filename.strip('"\'')
    should_stretch = (should_stretch_property == 'stretch-x')
    if pixel_ratio_property:
        pixel_ratio = int(pixel_ratio_property)
    else:
        pixel_ratio = 1
    return image_filename, should_stretch, pixel_ratio


class SpritableImage(object):
    def __init__(self, image, should_stretch=False):
        self.image = image
        self.stretch = should_stretch
        self.filenames = []

    @property
    def width(self):
        return self.image.size[0]

    @property
    def height(self):
        return self.image.size[1]

    def stretch_to_width(self, width):
        self.image = self.image.resize((width, self.height))


class SpriteBin(object):
    def __init__(self, bounding_box):
        # the bounding box is a tuple of
        # top-left-x, top-left-y, bottom-right-x, bottom-right-y
        self.bounding_box = bounding_box
        self.offset = 0
        self.height = bounding_box[3] - bounding_box[1]

    def has_space_for(self, image):
        return (self.offset + image.width <= self.bounding_box[2] and
                self.height >= image.height)

    def add_image(self, image):
        image.sprite_location = (self.offset, self.bounding_box[1])
        self.offset += image.width + SPRITE_PADDING


def _load_spritable_images(css_filename):
    css_location = os.path.dirname(os.path.abspath(css_filename))

    images = {}
    with open(css_filename, 'r') as f:
        for line in f:
            m = sprite_line.search(line)
            if not m:
                continue

            image_filename, should_stretch, pixel_ratio = _extract_css_info(m)
            image = Image.open(os.path.join(css_location, image_filename))
            image_hash = hashlib.md5(image.convert("RGBA").tostring()).hexdigest()

            if image_hash not in images:
                images[image_hash] = SpritableImage(image, should_stretch)
            else:
                assert images[image_hash].stretch == should_stretch
            images[image_hash].filenames.append(image_filename)

    # Sort images by filename to group the layout by names when possible.
    return sorted(images.values(), key=lambda i: i.filenames[0])


def _generate_sprite(images, sprite_path):
    sprite_width = max(i.width for i in images)
    sprite_height = 0

    # put all the max-width and stretch-x images together at the top
    small_images = []
    for image in images:
        if image.width == sprite_width or image.stretch:
            if image.stretch:
                image.stretch_to_width(sprite_width)
            image.sprite_location = (0, sprite_height)
            sprite_height += image.height + SPRITE_PADDING
        else:
            small_images.append(image)

    # lay out the remaining images -- done with a greedy algorithm
    small_images.sort(key=lambda i: i.height, reverse=True)
    bins = []

    for image in small_images:
        # find a bin to fit in
        for bin in bins:
            if bin.has_space_for(image):
                break
        else:
            # or give up and create a new bin
            bin = SpriteBin((0, sprite_height, sprite_width, sprite_height + image.height))
            sprite_height += image.height + SPRITE_PADDING
            bins.append(bin)

        bin.add_image(image)

    # generate the image
    sprite_dimensions = (sprite_width, sprite_height)
    background_color = (255, 69, 0, 0)  # transparent orangered
    sprite = Image.new('RGBA', sprite_dimensions, background_color)

    for image in images:
        sprite.paste(image.image, image.sprite_location)

    sprite.save(sprite_path, optimize=True)
    optimize_png(sprite_path)

    # give back the sprite and mangled name
    sprite_base, sprite_name = os.path.split(sprite_path)
    return (sprite, generate_static_name(sprite_name, base=sprite_base))


def _rewrite_css(css_filename, sprite_path, images, sprite_size):
    # map filenames to coordinates
    locations = {}
    for image in images:
        for filename in image.filenames:
            locations[filename] = image.sprite_location

    def rewrite_sprite_reference(match):
        image_filename, should_stretch, pixel_ratio = _extract_css_info(match)
        position = locations[image_filename]

        if pixel_ratio > 1:
            position = (position[0] / pixel_ratio, position[1] / pixel_ratio)

        css_properties = [
            'background-image: url(%s);' % sprite_path,
            'background-position: -%dpx -%dpx;' % position,
            'background-repeat: %s;' % ('repeat' if should_stretch else 'no-repeat'),
        ]

        if pixel_ratio > 1:
            size = (sprite_size[0] / pixel_ratio, sprite_size[1] / pixel_ratio)
            css_properties.append(
                'background-size: %dpx %dpx;' % size,
            )

        return ''.join(css_properties)

    # read in the css and replace sprite references
    with open(css_filename, 'r') as f:
        css = f.read()
    return sprite_line.sub(rewrite_sprite_reference, css)


def spritify(css_filename, sprite_path):
    images = _load_spritable_images(css_filename)
    sprite, sprite_path = _generate_sprite(images, sprite_path)
    return _rewrite_css(css_filename, sprite_path, images, sprite.size)


if __name__ == '__main__':
    import sys
    print spritify(sys.argv[1], sys.argv[2])
