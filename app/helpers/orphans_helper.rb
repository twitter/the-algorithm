module OrphansHelper
 
  # Renders the appropriate partial based on the class of object to be orphaned
  def render_orphan_partial(to_be_orphaned)
    if to_be_orphaned.is_a?(Series)
      render 'orphans/orphan_series', series: to_be_orphaned
    elsif to_be_orphaned.is_a?(Pseud)
      render 'orphans/orphan_pseud', pseud: to_be_orphaned
    elsif to_be_orphaned.is_a?(User)
      render 'orphans/orphan_user', user: to_be_orphaned
    else # either a single work or an array of works
      render 'orphans/orphan_work', works: [to_be_orphaned].flatten
    end
  end
end
