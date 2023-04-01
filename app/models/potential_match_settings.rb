class PotentialMatchSettings < ApplicationRecord
  ALL = -1
  REQUIRED_MATCH_OPTIONS =  [
                              [ts("All"), ALL],
                              ["0", 0],
                              ["1", 1],
                              ["2", 2],
                              ["3", 3],
                              ["4", 4],
                              ["5", 5]
                            ]

  # VALIDATION
  REQUIRED_TAG_ATTRIBUTES = %w(num_required_fandoms num_required_characters num_required_relationships num_required_freeforms num_required_categories
     num_required_ratings num_required_archive_warnings)

  REQUIRED_TAG_ATTRIBUTES.each do |tag_limit_field|
      validates_inclusion_of tag_limit_field, in: REQUIRED_MATCH_OPTIONS.collect {|entry| entry[1]},
        message: "%{value} is not a valid match setting"
  end

  # must have at least one matching request
  validates_inclusion_of :num_required_prompts, in: REQUIRED_MATCH_OPTIONS.collect {|entry| entry[1]}.delete_if {|elem| elem == 0}, message: "%{value} is not a valid match setting"

  # are all settings 0
  def no_match_required?
    REQUIRED_TAG_ATTRIBUTES.all? {|attrib| self.send("#{attrib}") == 0}
  end

  def required_types
    TagSet::TAG_TYPES.select {|type| self.send("num_required_#{type.tableize}") != 0}
  end

  def topmost_required_type
    required_types.first
  end

  def include_optional?(type)
    send("include_optional_#{type.tableize}")
  end
end
