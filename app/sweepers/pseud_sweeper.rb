class PseudSweeper < ActionController::Caching::Sweeper
  observe User, Pseud

  def after_create(record)
    record.add_to_autocomplete if record.is_a?(Pseud)
  end

  def before_update(record)
    if record.changed.include?("name") || record.changed.include?("login")
      if record.is_a?(User)
        record.pseuds.each(&:remove_stale_from_autocomplete_before_save)
      else
        if record.user.saved_changes.any?
          # In this case, `remove_stale_from_autocomplete` needs to look at the
          # changed attributes on the pseud's user as if it were an after_*
          # callback on the user instead of a before_* callback on the pseud.
          record.remove_stale_from_autocomplete
        else
          record.remove_stale_from_autocomplete_before_save
        end
      end
    end
  end

  def after_update(record)
    if record.saved_changes.keys.include?("name") || record.saved_changes.keys.include?("login")
      if record.is_a?(User)
        record.pseuds.each do |pseud|
          # have to reload the pseud from the db otherwise it has the outdated login
          pseud.reload
          pseud.add_to_autocomplete
        end
      else
        record.add_to_autocomplete
      end
    end
  end

  def before_destroy(record)
    record.remove_from_autocomplete if record.is_a?(Pseud)
  end

end
