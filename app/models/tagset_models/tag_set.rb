class TagSet < ApplicationRecord
  # a complete match is numerically represented with ALL
  ALL = -1

  TAG_TYPES = %w(fandom character relationship freeform category rating archive_warning)
  TAG_TYPES_INITIALIZABLE = %w(fandom character relationship freeform)
  TAG_TYPES_RESTRICTED_TO_FANDOM = %w(character relationship)
  TAGS_AS_CHECKBOXES = %w(category rating archive_warning)

  attr_accessor :from_owned_tag_set

  has_many :set_taggings, dependent: :destroy
  has_many :tags, through: :set_taggings

  has_one :owned_tag_set

  has_one :prompt, autosave: false

  # how this works: we don't want to set the actual "tags" variable initially because that will
  # create SetTaggings even if the tags are not canonical or wrong. So we need to create a temporary
  # virtual attribute "tagnames" to use instead until after validation.
  attr_writer :tagnames
  def tagnames
    @tagnames || tags.select('tags.name').order('tags.name').collect(&:name).join(ArchiveConfig.DELIMITER_FOR_OUTPUT)
  end

  def taglist
    @tagnames ? tagnames_to_list(@tagnames) : tags
  end

  attr_writer :tagnames_to_remove
  def tagnames_to_remove
    @tagnames_to_remove || ""
  end

  # this code just sets up functions fandom_tagnames/fandom_tagnames=, character_tagnames... etc
  # that work like tagnames above, except on separate types.
  #
  # NOTE: you can't use both these individual
  # setters and tagnames in the same form -- ie, if you set tagnames and then you set fandom_tagnames, you
  # will wipe out the fandom tagnames set in tagnames.
  #
  TAG_TYPES.each do |type|
    attr_writer "#{type}_tagnames".to_sym

    define_method("#{type}_tagnames") do
      self.instance_variable_get("@#{type}_tagnames") || (self.new_record? ? self.tags.select {|t| t.type == type.classify}.collect(&:name).join(ArchiveConfig.DELIMITER_FOR_OUTPUT) :
                                                                             self.tags.with_type(type.classify).select('tags.name').order('tags.name').collect(&:name).join(ArchiveConfig.DELIMITER_FOR_OUTPUT))
    end

    define_method("#{type}_taglist") do
      self.instance_variable_get("@#{type}_tagnames") ? tagnames_to_list(self.instance_variable_get("@#{type}_tagnames"), type) : with_type(type)
    end

    # _to_add/remove only
    attr_writer "#{type}_tagnames_to_add".to_sym
    define_method("#{type}_tagnames_to_add") do
      self.instance_variable_get("@#{type}_tagnames_to_add") || ""
    end

    attr_writer "#{type}_tags_to_remove".to_sym
    define_method("#{type}_tags_to_remove") do
      self.instance_variable_get("@#{type}_tags_to_remove") || ""
    end

  end

  # this actually runs and saves the tags and updates the autocomplete
  # NOTE: if more than one is set, the precedence is as follows:
  # tagnames=
  # tagnames_to_add/_remove
  # [type]_tagnames
  # [type]_tagnames_to_add/_remove
  after_save :assign_tags
  def assign_tags
    tags_to_add = []
    tags_to_remove = []

    TAG_TYPES.each do |type|
      if self.instance_variable_get("@#{type}_tagnames")
        # explicitly set the list of type_tagnames
        new_tags = self.send("#{type}_taglist")
        old_tags = self.with_type(type.classify)
        tags_to_add += (new_tags - old_tags)
        tags_to_remove += (old_tags - new_tags)
      else
        #
        unless self.instance_variable_get("@#{type}_tagnames_to_add").blank?
          tags_to_add += tagnames_to_list(self.instance_variable_get("@#{type}_tagnames_to_add"), type)
        end
        unless self.instance_variable_get("@#{type}_tags_to_remove").blank?
          tagclass = type.classify.constantize # Safe constantize itterating over TAG_TYPES
          tags_to_remove += (self.instance_variable_get("@#{type}_tags_to_remove").map {|tag_id| tag_id.blank? ? nil : tagclass.find(tag_id)}.compact)
        end
      end
    end

    # This overrides the type-specific
    if @tagnames_to_remove.present?
      tags_to_remove = @tagnames_to_remove.split(ArchiveConfig.DELIMITER_FOR_INPUT).map {|tname| Tag.find_by_name(tname.squish)}.compact
    end

    # And this overrides the add/remove-specific
    if @tagnames
      new_tags = tagnames_to_list(@tagnames)
      tags_to_add = new_tags - self.tags
      tags_to_remove = (self.tags - new_tags)
    end

    # actually remove and add the tags, and update autocomplete
    remove_from_set(tags_to_remove.uniq)
    add_to_set(tags_to_add.uniq)
  end

  def remove_from_set(tags_to_remove)
    return unless tags_to_remove.present?
    self.set_taggings.where(tag_id: tags_to_remove.map(&:id)).delete_all
    remove_tags_from_autocomplete(tags_to_remove)
    owned_tag_set&.touch
  end

  def add_to_set(tags_to_add)
    return unless tags_to_add.present?
    existing_ids = self.set_taggings.where(tag_id: tags_to_add.map(&:id)).pluck(:tag_id)
    tags_to_add.each do |tag|
      next if existing_ids.include?(tag.id)
      self.set_taggings.create(tag_id: tag.id)
    end
    add_tags_to_autocomplete(tags_to_add)
    owned_tag_set&.touch
  end

  # Tags must already exist unless they are being added to an owned tag set
  validate :tagnames_must_exist, unless: :from_owned_tag_set
  def tagnames_must_exist
    nonexist = []
    if @tagnames
      nonexist += @tagnames.split(ArchiveConfig.DELIMITER_FOR_INPUT).select {|t| !Tag.where(name: t.squish).exists?}
    end
    if owned_tag_set.nil?
      TAG_TYPES.each do |type|
        if (tagnames = self.instance_variable_get("@#{type}_tagnames_to_add"))
          tagnames = (tagnames.is_a?(Array) ? tagnames : tagnames.split(ArchiveConfig.DELIMITER_FOR_INPUT)).map {|t| t.squish}
          nonexist += tagnames.select {|t| !t.blank? && !Tag.where(name: t).exists?}
        end
      end
    end

    unless nonexist.empty?
      errors.add(:tagnames, ts("^The following tags don't exist and can't be used: %{taglist}", taglist: nonexist.join(", ") ))
    end
  end

  ### Various utility methods

  def +(other)
    TagSet.new(tags: (self.tags + other.tags))
  end

  def -(other)
    TagSet.new(tags: (self.tags - other.tags))
  end

  def with_type(type)
    # this is required because otherwise tag sets created on the fly (eg with + during potential match generation)
    # that are not saved in the database will return empty list.
    # We use Tag.where so that we can still chain this with other AR queries
    return self.new_record? ? Tag.where(id: self.tags.select {|t| t.type == type.classify}.collect(&:id)) : self.tags.with_type(type)
  end

  def has_type?(type)
    with_type(type).exists?
  end

  def empty?
    self.tags.empty?
  end

  ### Matching

  # Computes the "match rank" of the two arrays. The match rank is ALL if the
  # request tags are a subset of (or equal to) the offer tags, and otherwise is
  # equal to the size of the overlap between the two.
  def self.match_array_rank(request, offer)
    return ALL if request.nil? || request.empty?
    return 0 if offer.nil? || offer.empty?
    overlap = request & offer
    overlap.size == request.size ? ALL : overlap.size
  end

  # Creates a hash mapping from tag types (lowercase) to lists of tag ids.
  # Stores the result as an instance variable, to make sure that we only
  # perform the work once. (This is only used by the matching algorithm, which
  # doesn't update any tag set information, so it's okay to cache the results
  # of this computation. Particularly since we want matching to go as fast as
  # possible.)
  def tag_ids_by_type
    if @tag_ids_by_type.nil?
      @tag_ids_by_type = tags.group_by { |tag| tag.type.underscore }
      @tag_ids_by_type.each_value { |tag_list| tag_list.map!(&:id) }
    end

    @tag_ids_by_type
  end

  # Computes the match rank of the two tag sets, in the given type. (Or in all
  # types, if type is nil.)
  def match_rank(another, type = nil)
    request = type ? tag_ids_by_type[type] : tags.map(&:id)
    offer = type ? another.tag_ids_by_type[type] : another.tags.map(&:id)
    TagSet.match_array_rank(request, offer)
  end

  ### protected

  protected
    def tagnames_to_list(taglist, type=nil)
      taglist = (taglist.kind_of?(String) ? taglist.split(ArchiveConfig.DELIMITER_FOR_INPUT) : taglist).uniq
      if type
        raise "Redshirt: Attempted to constantize invalid class initialize tagnames_to_list #{type}" unless TAG_TYPES.include?(type)
        if Tag::USER_DEFINED.include?(type.classify)
          # allow users to create these
          taglist.reject {|tagname| tagname.blank? }.map {|tagname| (type.classify.constantize).find_or_create_by_name(tagname.squish)} # Safe constantize checked above
        else
          taglist.reject {|tagname| tagname.blank? }.map {|tagname| (type.classify.constantize).find_by(name: tagname.squish)}.compact # Safe constantize checked above
        end
      else
        taglist.reject {|tagname| tagname.blank? }.map {|tagname| Tag.find_by_name(tagname.squish) || Freeform.find_or_create_by_name(tagname.squish)}
      end
    end

  ### autocomplete
  public

  # set up autocomplete and override some methods
  include AutocompleteSource

  def autocomplete_prefixes
    prefixes = [ ]
    prefixes
  end

  def add_to_autocomplete(score = nil)
    add_tags_to_autocomplete(self.tags)
  end

  def remove_from_autocomplete
    REDIS_AUTOCOMPLETE.del("autocomplete_tagset_#{self.id}")
  end

  def add_tags_to_autocomplete(tags_to_add)
    tags_to_add.each do |tag|
      value = tag.autocomplete_value
      REDIS_AUTOCOMPLETE.zadd("autocomplete_tagset_all_#{self.id}", 0, value)
      REDIS_AUTOCOMPLETE.zadd("autocomplete_tagset_#{tag.type.downcase}_#{self.id}", 0, value)
    end
  end

  def remove_tags_from_autocomplete(tags_to_remove)
    tags_to_remove.each do |tag|
      value = tag.autocomplete_value
      REDIS_AUTOCOMPLETE.zrem("autocomplete_tagset_all_#{self.id}", value)
      REDIS_AUTOCOMPLETE.zrem("autocomplete_tagset_#{tag.type.downcase}_#{self.id}", value)
    end
  end

  # returns tags that are in ANY or ALL of the specified tag sets
  def self.autocomplete_lookup(options={})
    options.reverse_merge!({term: "", tag_type: "all", tag_set: "", in_any: true})
    tag_type = options[:tag_type]
    search_param = options[:term]
    tag_sets = TagSet.get_search_terms(options[:tag_set])

    combo_key = "autocomplete_tagset_combo_#{tag_sets.join('_')}"

    # get the intersection of the wrangled fandom and the associations from the various tag sets
    keys_to_lookup = tag_sets.map {|set| "autocomplete_tagset_#{tag_type}_#{set}"}.flatten

    if options[:in_any]
      # get the union since we want tags in ANY of these sets
      REDIS_AUTOCOMPLETE.zunionstore(combo_key, keys_to_lookup, aggregate: :max)
    else
      # take the intersection of ALL of these sets
      REDIS_AUTOCOMPLETE.zinterstore(combo_key, keys_to_lookup, aggregate: :max)
    end
    results = REDIS_AUTOCOMPLETE.zrevrange(combo_key, 0, -1)
    # expire fast
    REDIS_AUTOCOMPLETE.expire combo_key, 1

    unless search_param.blank?
      search_regex = Tag.get_search_regex(search_param)
      return results.select {|tag| tag.match(search_regex)}
    else
      return results
    end
  end
end
