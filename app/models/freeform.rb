class Freeform < Tag

  NAME = ArchiveConfig.FREEFORM_CATEGORY_NAME

  def self.label_name
    "Additional Tags"
  end

  # Types of tags to which a freeform tag can belong via common taggings or meta taggings
  def parent_types
    ['Fandom', 'MetaTag']
  end
  def child_types
    ['SubTag', 'Merger']
  end

  def characters
    parents.select {|t| t.is_a? Character}.sort
  end

  def relationships
    parents.select {|t| t.is_a? Relationship}.sort
  end

  def freeforms
    (parents + children).select {|t| t.is_a? Freeform}.sort
  end

  def fandoms
    parents.select {|t| t.is_a? Fandom}.sort
  end

  def medias
    parents.select {|t| t.is_a? Media}.sort
  end

end
