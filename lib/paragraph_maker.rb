# frozen_string_literals: true

# Traverse a Nokogiri document tree recursively in order to insert linebreaks.
module ParagraphMaker
  extend self

  # Tags that will be stripped by the sanitizer that are inline, and should be
  # wrapped in paragraphs and have their contents left untouched:
  INLINE_INVALID_TAGS = %w[button input label map select textarea].freeze

  # Tags that will be stripped by the sanitizer that are block tags, and should
  # have nearby whitespace stripped:
  BLOCK_INVALID_TAGS = %w[fieldset form].freeze

  # Tags that will be completely removed by the sanitizer, and should have
  # nearby whitespace removed, and their contents left untouched:
  REMOVED_INVALID_TAGS = Sanitize::Config::ARCHIVE[:remove_contents]

  # Tags whose content we don't touch
  TAG_NAMES_TO_SKIP = (%w[
    a abbr acronym address audio dl embed figure h1 h2 h3 h4 h5 h6 hr img ol
    object p pre source summary table track video ul
  ] + INLINE_INVALID_TAGS + REMOVED_INVALID_TAGS).freeze

  # Tags that need to go inside p tags
  TAG_NAMES_TO_WRAP = (%w[
    a abbr acronym b big br cite code del dfn em i img ins kbd q rp rt ruby
    s samp small span strike strong sub sup tt u var
  ] + INLINE_INVALID_TAGS).freeze

  # Tags that can't be inside p tags
  TAG_NAMES_TO_UNWRAP = %w[
    audio details dl figure h1 h2 h3 h4 h5 h6 hr ol p pre source summary table track ul video
  ].freeze

  # Tags before and after which we don't want to convert linebreaks
  # into br's and p's
  TAG_NAMES_STRIP_WHITESPACE = (%w[
    audio blockquote br center details dl div figure figcaption h1 h2 h3 h4 h5 h6 hr ol
    p pre source summary table track ul video
  ] + BLOCK_INVALID_TAGS + REMOVED_INVALID_TAGS).freeze

  private

  # Traverse all nodes from the given root, yielding each node to the given
  # block. Processes nodes that are on the list of TAG_NAMES_TO_SKIP, but
  # doesn't process any of their children.
  def traverse(node, &block)
    block.call(node)

    return if TAG_NAMES_TO_SKIP.include?(node.name)

    node.children.each do |child|
      traverse(child, &block)
    end
  end

  # Checks whether the given node is in a paragraph. This includes nodes where
  # the paragraph is many layers up.
  def in_paragraph?(node)
    return false if node.parent.nil?
    return true if node.parent.name == "p"

    in_paragraph?(node.parent)
  end

  # Strip whitespace before and after tags in the TAG_NAMES_STRIP_WHITESPACE
  # list.
  def strip_whitespace(root)
    traverse(root) do |node|
      next unless node.text? || node.cdata?

      # This text node immediately follows either the closing tag of its
      # previous sibling, or the opening tag of its parent if it has no
      # previous sibling. If we're supposed to ignore whitespace after that
      # tag, we should lstrip to get rid of it.
      node.content = node.content.lstrip if TAG_NAMES_STRIP_WHITESPACE.include?(node.previous_sibling&.name || node.parent&.name)

      # This text node is immediately followed by either the opening tag of its
      # next sibling, or the closing tag of its parent. If that tag is one in
      # the TAG_NAMES_STRIP_WHITESPACE list, we want to rstrip this node.
      node.content = node.content.rstrip if TAG_NAMES_STRIP_WHITESPACE.include?(node.next_sibling&.name || node.parent&.name)

      node.unlink if node.content.empty?
    end
  end

  # Split text nodes when they have newlines:
  def split_text_at_newlines(root)
    traverse(root) do |node|
      next unless node.text?

      pieces = node.to_s.split(/([[:space:]]*\n[[:space:]]*)/)
      next unless pieces.length > 1

      pieces.each do |piece|
        next if piece.empty?

        case piece.count("\n")
        when 0
          # No newlines, it's just a normal piece of text:
          node.add_previous_sibling(piece)
        when 1
          # One newline, replace it with a <br>.
          node.add_previous_sibling("<br>")
        when 2
          # Two newlines, replace it with a special tag (to be processed later)
          # that marks the paragraph to be split at this location.
          node.add_previous_sibling("<split>")
        else
          # Three or more newlines, replace it with a special tag (to be
          # processed later) that marks the paragraph to be split at this
          # location, with a <p>&nbsp;</p> paragraph inserted to increase the
          # spacing between lines.
          node.add_previous_sibling("<split long>")
        end
      end

      node.unlink
    end
  end

  # Traverse the tree in search of sequences of <br> tags. We want to find the
  # last <br> tag in the sequence, so that we don't process the sequence more
  # than once. Once we've found the <br> tag that has a prior <br> tag, but no
  # following <br> tag, we can yank out all of the <br> tags in the sequence,
  # counting them as we go.
  def merge_br_tags(root)
    traverse(root) do |node|
      next unless node.name == "br" &&
                  node.next_sibling&.name != "br" &&
                  node.previous_sibling&.name == "br"

      break_count = 1
      while node.previous_sibling&.name == "br"
        node.previous_sibling.unlink
        break_count += 1
      end

      if break_count == 2
        # Replace the <br> sequence with a special tag (to be processed later)
        # that indicates that whatever paragraph we're in needs to be split at
        # this point. This corresponds to having two <br> tags in a row.
        node.replace("<split>")
      else
        # Replace the <br> sequence with a special tag (to be processed later)
        # that indicates that whatever paragraph we're in needs to be split at
        # this point, and there needs to be a <p>&nbsp;</p> paragraph inserted
        # into the split. This corresponds to having 3+ <br> tags in a row.
        node.replace("<split long>")
      end
    end
  end

  # Go through all nodes that should be inside paragraph tags, and make sure
  # they're wrapped:
  def wrap_all(root)
    # We don't use traverse(root) here to handle the traversal because we're
    # doing something much more complex than usual with our children.
    return if TAG_NAMES_TO_SKIP.include?(root.name) || in_paragraph?(root) || root.name == "p"

    # Divide up the list of children into sublists based on whether the node
    # needs to have a paragraph wrapped around it, or not.
    chunks = root.children.chunk do |node|
      node.text? || node.cdata? || TAG_NAMES_TO_WRAP.include?(node.name)
    end

    chunks.each do |should_wrap, children|
      if should_wrap
        # Create a paragraph object, and then transfer all of the children in
        # this chunk to it.
        paragraph = root.document.create_element("p")
        children.first.add_previous_sibling(paragraph)
        children.each do |node|
          paragraph.add_child(node)
        end
      else
        # These nodes don't need to be wrapped in a paragraph node, but some of
        # their children might, so we have to process them.
        children.each do |node|
          wrap_all(node)
        end
      end
    end
  end

  # Given a node, "split" its parent at that node and move the node upwards one
  # level -- that is, split its siblings by whether they're left/right of the
  # node, and split the parent into two copies, one containing the left siblings
  # and one containing the right siblings.
  #
  # For example, if we have something like this:
  #   <ul class="actions">
  #     <li>First</li>
  #     <li>Second</li>
  #     <li>Third</li>
  #   </ul>
  # Then calling split_parent on the second list item will produce this:
  #   <ul class="actions">
  #     <li>First</li>
  #   </ul>
  #   <li>Second</li>
  #   <ul class="actions">
  #     <li>Third</li>
  #   </ul>
  def split_parent(node)
    # The easy cases: if this node is the first or last node in its parent, then
    # there's no point in a proper split, because one of the two tags would just
    # be empty. Instead, we rearrange ourselves relative to our parent.
    return node.parent.add_previous_sibling(node) if node.previous_sibling.nil?
    return node.parent.add_next_sibling(node) if node.next_sibling.nil?

    # Create a shallow copy of the parent:
    new_parent = node.parent.dup(0)

    # Copy over all of the attributes:
    node.parent.attributes.each do |name, attribute|
      new_parent.set_attribute(name, attribute.value)
    end

    # Put the new parent in the right place:
    node.parent.add_next_sibling(new_parent)

    # Move over all of the siblings:
    new_parent.add_child(node.next_sibling) until node.next_sibling.nil?

    # Put the node in the right place:
    node.parent.add_next_sibling(node)
  end

  # Remove the given node from its containing paragraph tag, if it has one.
  def extract_from_paragraph(node)
    split_parent(node) while in_paragraph?(node)
  end

  # Go through all nodes that shouldn't be inside paragraph tags, and remove them
  # from paragraph tags.
  def unwrap_all(root)
    traverse(root) do |node|
      extract_from_paragraph(node) if TAG_NAMES_TO_UNWRAP.include?(node.name)
    end
  end

  # Process the <split> tags inserted by split_text_at_newlines and
  # merge_br_tags. Either way, we use extract_from_paragraph to split its
  # parents until we reach the paragraph tag that contains it. If the split is
  # marked as "long," we need to insert a <p>&nbsp;</p> blank paragraph.
  # Otherwise, just delete it.
  def replace_splits(root)
    root.css("split").each do |node|
      extract_from_paragraph(node)

      if node.attribute("long")
        node.replace("<p>&nbsp;</p>")
      else
        node.unlink
      end
    end
  end

  # Traverse the tree and delete any paragraph nodes that have no children.
  def delete_empty_paragraphs(root)
    traverse(root) do |node|
      node.unlink if node.name == "p" && node.children.empty?
    end
  end

  public

  # Process the given node to add paragraphs to it and all of its children.
  def process(root)
    strip_whitespace(root)
    split_text_at_newlines(root)
    merge_br_tags(root)
    wrap_all(root)
    unwrap_all(root)
    replace_splits(root)
    delete_empty_paragraphs(root)
  end
end
