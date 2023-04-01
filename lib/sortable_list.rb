class SortableList < Array
  def reorder_list(positions)
    list = self
    changed = {}
    positions.collect!(&:to_i).each_with_index do |new_position, old_position|
      if new_position != 0 && new_position <= list.length && !changed.has_key?(new_position)
        changed.merge!({new_position => list[old_position]})
      end
    end
    list -= changed.values
    changed.sort.each {|pair| pair.first > list.length ? list << pair.last : list.insert(pair.first-1, pair.last)}
    list.each_with_index {|list_item, index| list_item.update_attribute(:position, index + 1)}
  end
end 