class Character < Tag

  NAME = ArchiveConfig.CHARACTER_CATEGORY_NAME

  # Types of tags to which a character tag can belong via common taggings or meta taggings
  def parent_types
    ['Fandom', 'MetaTag']
  end
  def child_types
    ['Relationship', 'SubTag', 'Merger']
  end

  def characters
    (children + parents).select {|t| t.is_a? Character}.sort
  end

  def relationships
    children.by_type('Relationship').by_name
  end

  def freeforms
    children.by_type('Freeform').by_name
  end

  def fandoms
    parents.by_type('Fandom').by_name
  end

  def medias
    parents.by_type('Media').by_name
  end
end
