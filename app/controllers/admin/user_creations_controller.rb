class Admin::UserCreationsController < Admin::BaseController
  before_action :get_creation
  before_action :can_be_marked_as_spam, only: [:set_spam]

  def get_creation
    raise "Redshirt: Attempted to constantize invalid class initialize #{params[:creation_type]}" unless %w(Bookmark ExternalWork Series Work).include?(params[:creation_type])
    @creation_class = params[:creation_type].constantize
    @creation = @creation_class.find(params[:id])
  end
  
  def can_be_marked_as_spam
    unless @creation_class && @creation_class == Work
      flash[:error] = ts("You can only mark works as spam currently.")
      redirect_to polymorphic_path(@creation) and return
    end
  end
  
  # Removes an object from public view
  def hide
    authorize @creation
    @creation.hidden_by_admin = (params[:hidden] == "true")
    @creation.save(validate: false)
    action = @creation.hidden_by_admin? ? "hide" : "unhide"
    AdminActivity.log_action(current_admin, @creation, action: action)
    flash[:notice] = @creation.hidden_by_admin? ?
                        ts("Item has been hidden.") :
                        ts("Item is no longer hidden.")
    if @creation_class == ExternalWork || @creation_class == Bookmark
      redirect_to(request.env["HTTP_REFERER"] || root_path)
    else
      redirect_to polymorphic_path(@creation)
    end
  end  
  
  def set_spam
    authorize @creation
    action = "mark as " + (params[:spam] == "true" ? "spam" : "not spam")
    AdminActivity.log_action(current_admin, @creation, action: action, summary: @creation.inspect)    
    if params[:spam] == "true"
      @creation.mark_as_spam!
      @creation.update_attribute(:hidden_by_admin, true)
      flash[:notice] = ts("Work was marked as spam and hidden.")
    else
      @creation.mark_as_ham!
      @creation.update_attribute(:hidden_by_admin, false)
      flash[:notice] = ts("Work was marked not spam and unhidden.")
    end
    redirect_to polymorphic_path(@creation)
  end

  def destroy
    authorize @creation
    AdminActivity.log_action(current_admin, @creation, action: "destroy", summary: @creation.inspect)
    @creation.destroy
    flash[:notice] = ts("Item was successfully deleted.")
    if @creation_class == Bookmark || @creation_class == ExternalWork
      redirect_to bookmarks_path
    else
      redirect_to works_path
    end
  end
end
