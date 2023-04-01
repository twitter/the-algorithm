module Taggable
  extend ActiveSupport::Concern

  included do
    has_many :taggings, as: :taggable, inverse_of: :taggable, dependent: :destroy, autosave: true
    has_many :tags, through: :taggings, source: :tagger, source_type: "Tag"

    Tag::VISIBLE.each do |type|
      has_many type.underscore.pluralize.to_sym,
               -> { where(tags: { type: type }) },
               through: :taggings,
               source: :tagger,
               source_type: "Tag",
               before_remove: :destroy_tagging
    end
  end

  # Used in works and external works:
  Tag::VISIBLE.each do |type|
    klass = type.constantize
    underscore = type.underscore

    define_method("#{underscore}_strings") do
      tags_after_saving_of_type(klass).map(&:name)
    end

    define_method("#{underscore}_string") do
      tags_after_saving_of_type(klass).map(&:name).join(ArchiveConfig.DELIMITER_FOR_OUTPUT)
    end

    define_method("#{underscore}_string=") do |tag_string|
      tags = parse_tags_of_type(tag_string, klass)
      assign_tags_of_type(tags, klass)
    end

    alias_method "#{underscore}_strings=", "#{underscore}_string="
  end

  # Used in bookmarks and collections:
  def tag_strings
    tags_after_saving.map(&:name)
  end

  def tag_string
    tags_after_saving.map(&:name).join(ArchiveConfig.DELIMITER_FOR_OUTPUT)
  end

  def tag_string=(tag_string)
    tags = parse_tags(tag_string)
    assign_tags_of_type(tags, Tag)
  end

  alias tag_strings= tag_string=

  def tag_groups
    result = tags_after_saving.group_by(&:type)

    result["Fandom"] ||= []
    result["Rating"] ||= []
    result["ArchiveWarning"] ||= []
    result["Relationship"] ||= []
    result["Character"] ||= []
    result["Freeform"] ||= []

    result
  end

  # a work can only have one rating, so using first will work
  # should always have a rating, if it doesn't err conservatively
  def adult?
    self.ratings.blank? || self.ratings.first.adult?
  end

  # Returns the list of tags that this object will have after it's saved. This
  # takes into account taggings that are built (but not saved), and taggings
  # that have been marked for destruction.
  def tags_after_saving
    if taggings.target.empty?
      # The taggings aren't loaded, and no new taggings have been created, so
      # we can just resort to the base tags list for efficiency:
      tags.to_a
    else
      taggings.reject(&:marked_for_destruction?).map(&:tagger).compact
    end
  end

  # Returns the number of tags of type Fandom, Character, Relationship, or
  # Freeform.
  def user_defined_tags_count
    tags_after_saving.count do |tag|
      Tag::USER_DEFINED.include?(tag.type)
    end
  end

  private

  # Take the list of tags after saving, and filter by type:
  def tags_after_saving_of_type(klass)
    tags_after_saving.select { |tag| tag.is_a?(klass) }
  end

  # Split the tag string at each comma, and remove excess whitespace:
  def split_tag_string(tag_string)
    tag_array = if tag_string.is_a?(Array)
                  tag_string
                else
                  tag_string.gsub(/\uff0c|\u3001/, ",").split(",")
                end

    tag_array.map(&:squish).reject(&:blank?)
  end

  # Split the tag_string, and find/create an array of tags of the given type.
  def parse_tags_of_type(tag_string, klass)
    tag_names = split_tag_string(tag_string)

    tag_names.map do |tag_name|
      klass.find_or_create_by_name(tag_name)
    end.uniq
  end

  # Split the tag_string, and look up the tags for each of those tag names. We
  # ignore tag type for this one, and create UnsortedTags for any tag names
  # that we can't find.
  #
  # Used in bookmarks and collections.
  def parse_tags(tag_string)
    tag_names = split_tag_string(tag_string)

    tag_names.map do |tag_name|
      Tag.find_by_name(tag_name) || UnsortedTag.create(name: tag_name)
    end.uniq
  end

  # Mark taggings for destruction, and create new taggings, so that we will end
  # up with the specified set of tags after saving.
  #
  # Only deletes/checks tags of the given class.
  def assign_tags_of_type(tags, klass)
    missing = Set.new(tags)

    taggings.each do |tagging|
      tag = tagging.tagger

      next unless tag.is_a?(klass)

      if missing.include?(tag)
        missing.delete(tag)
        tagging.reload if tagging.marked_for_destruction?
      else
        tagging.mark_for_destruction
      end
    end

    missing.each do |tag|
      taggings.build(tagger: tag)
    end
  end

  def destroy_tagging(tag)
    taggings.find_by(tagger: tag)&.destroy
  end
end
