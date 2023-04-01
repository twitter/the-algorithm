class OrphansController < ApplicationController
  # You must be logged in to orphan works - relies on current_user data 
  before_action :users_only, except: [:index]
  
  before_action :load_pseuds, only: [:create]
  before_action :load_orphans, only: [:create]

  def index
    @user = User.orphan_account
    @works = @user.works
  end

  def new
    if params[:work_id]
      @to_be_orphaned = Work.find(params[:work_id])
      check_one_owned(@to_be_orphaned, current_user.works)
    elsif params[:work_ids]
      @to_be_orphaned = Work.where(id: params[:work_ids]).to_a
      check_all_owned(@to_be_orphaned, current_user.works)
    elsif params[:series_id]
      @to_be_orphaned = Series.find(params[:series_id])
      check_one_owned(@to_be_orphaned, current_user.series)
    elsif params[:pseud_id]
      @to_be_orphaned = Pseud.find(params[:pseud_id])
      check_one_owned(@to_be_orphaned, current_user.pseuds)
    else
      @to_be_orphaned = current_user
    end
  end

  def create
    use_default = params[:use_default] == "true"
    Creatorship.orphan(@pseuds, @orphans, use_default)
    flash[:notice] = ts("Orphaning was successful.")
    redirect_to(current_user)
  end

  protected

  def show_orphan_permission_error
    flash[:error] = ts("You don't have permission to orphan that!")
    redirect_to root_path
  end

  # Given an ActiveRecord item and an ActiveRecord relation, check whether the
  # item is in the relation. If not, show a flash error.
  def check_one_owned(chosen_item, all_owned_items)
    show_orphan_permission_error unless all_owned_items.exists?(chosen_item.id)
  end

  # Given a collection of ActiveRecords and an ActiveRecord relation, check
  # whether all items in the collection are contained in the relation. If not,
  # show a flash error.
  def check_all_owned(chosen_items, all_owned_items)
    chosen_ids = chosen_items.map(&:id)
    owned_ids = all_owned_items.where(id: chosen_ids).pluck(:id)
    unowned_ids = chosen_ids - owned_ids
    show_orphan_permission_error if unowned_ids.any?
  end

  # Load the list of works or series into the @orphans variable, and verify
  # that the current user owns the works/series in question.
  def load_orphans
    if params[:work_ids]
      @orphans = Work.where(id: params[:work_ids]).to_a
      check_all_owned(@orphans, current_user.works)
    elsif params[:series_id]
      @orphans = Series.where(id: params[:series_id]).to_a
      check_all_owned(@orphans, current_user.series)
    else
      flash[:error] = ts("What did you want to orphan?")
      redirect_to current_user
    end
  end

  # If a pseud_id is specified, load it and check that it belongs to the
  # current user. Otherwise, assume that the user wants to orphan with all of
  # their pseuds.
  def load_pseuds
    if params[:pseud_id]
      @pseuds = Pseud.where(id: params[:pseud_id]).to_a
      check_all_owned(@pseuds, current_user.pseuds)
    else
      @pseuds = current_user.pseuds
      # We don't need to check ownership here because these pseuds are
      # guaranteed to be owned by the current user.
    end
  end
end
