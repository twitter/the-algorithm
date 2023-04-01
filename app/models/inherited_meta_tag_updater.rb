# frozen_string_literal: true

# A helper class for calculating inherited meta taggings.
class InheritedMetaTagUpdater
  attr_reader :base, :boundary, :all

  def initialize(base)
    @base = base
    @boundary = [base.id]
    @all = [base.id]
  end

  # Advance to the next depth of our breadth-first search.
  def advance
    return if done?

    @boundary = MetaTagging.where(
      direct: true,
      sub_tag_id: @boundary
    ).pluck(:meta_tag_id) - @all

    @all += @boundary
  end

  # Check whether we're done finding all of our inherited metatags.
  def done?
    @boundary.empty?
  end

  # Go through the breadth-first search steps to figure out what this tag's
  # inherited metatags should be.
  def calculate
    advance until done?
  end

  # Generate the missing inherited meta taggings and delete the ones that are
  # no longer needed. Returns true if any meta taggings were created/deleted,
  # and false otherwise.
  def update
    calculate

    # Keep track of whether the meta taggings were modified:
    modified = false

    missing = Set.new(all)
    missing.delete(base.id)

    # Delete the unnecessary meta taggings.
    base.meta_taggings.each do |mt|
      # If the metatag ID is in the list of IDs we're looking for, we don't
      # need to modify the meta tagging at all -- we just need to mark that
      # we've seen it by removing it from the list of missing metatag IDs.
      #
      # We also shouldn't modify the meta tagging if it's direct.
      next if missing.delete?(mt.meta_tag_id) || mt.direct

      # The inherited metatag isn't one of the ones we're expecting to see,
      # which means that it shouldn't be here:
      mt.destroy
      modified = true
    end

    # Build the missing meta taggings.
    Tag.where(id: missing.to_a).each do |tag|
      base.meta_taggings.create(direct: false, meta_tag: tag)
      modified = true
    end

    modified
  end

  # Fixes inherited metatags for all tags with at least one meta tagging.
  def self.update_all
    Tag.joins(:meta_taggings).distinct.find_each do |tag|
      new(tag).update

      # Yield each tag to allow for progress messages.
      yield tag if block_given?
    end
  end
end
