# frozen_string_literal: true

# A datastructure representing a batch of prompts. Used in order to speed up
# matching.
class PromptBatch
  ALL = -1

  attr_reader :signups, :prompt_type
  attr_reader :prompts

  def initialize(signups, prompt_type, index_tag_type, index_optional)
    @signups = signups
    @prompt_type = prompt_type
    @index_tag_type = index_tag_type
    @index_optional = index_optional

    @prompts = if @prompt_type == :requests
                 @signups.flat_map(&:requests)
               else
                 @signups.flat_map(&:offers)
               end
  end

  private

  # For the given prompt, get the list of all tags of the indexed tag type.
  def indexed_tags_for_prompt(prompt)
    if @index_optional
      prompt.full_tag_set.tag_ids_by_type[@index_tag_type] || []
    else
      prompt.tag_set.tag_ids_by_type[@index_tag_type] || []
    end
  end

  # Build a list of prompts that accept "any" for the indexed tag type.
  def build_prompts_with_any
    @prompts_with_any = @prompts.select do |prompt|
      prompt.accepts_any?(@index_tag_type)
    end
  end

  # Build a mapping from tag IDs to lists of prompts that want that tag.
  def build_prompts_with_tag
    @prompts_with_tag = {}

    @prompts.each do |prompt|
      indexed_tags_for_prompt(prompt).each do |tag|
        @prompts_with_tag[tag] ||= []
        @prompts_with_tag[tag] << prompt
      end
    end
  end

  # Returns a list of prompts that accept "any" for the indexed tag type.
  # Calls build_prompts_with_any if the list doesn't already exist.
  def prompts_with_any
    build_prompts_with_any if @prompts_with_any.nil?
    @prompts_with_any
  end

  # Returns a list of prompts that have the given tag.
  # Calls build_prompts_with_tag if the hash table doesn't already exist.
  def prompts_with_tag(tag)
    build_prompts_with_tag if @prompts_with_tag.nil?
    @prompts_with_tag[tag] || []
  end

  public

  # Computes the prompts in this set that are "candidates" for matching the
  # passed-in prompt -- that is, prompts that share a tag, or prompts with any.
  # If the passed-in prompt has no tags of the indexed type, or accepts any for
  # the indexed type, this returns all prompts.
  def candidates_for_matching(prompt)
    tags = indexed_tags_for_prompt(prompt)

    if tags.empty? || prompt.accepts_any?(@index_tag_type)
      @prompts
    else
      candidates = tags.flat_map { |tag_id| prompts_with_tag(tag_id) }
      candidates += prompts_with_any
      candidates.uniq
    end
  end
end
