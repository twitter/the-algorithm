class PseudsController < ApplicationController
  cache_sweeper :pseud_sweeper

  before_action :load_user
  before_action :check_ownership, only: [:create, :edit, :destroy, :new, :update]
  before_action :check_user_status, only: [:new, :create, :edit, :update]

  def load_user
    @user = User.find_by(login: params[:user_id])
    @check_ownership_of = @user
  end

  # GET /pseuds
  # GET /pseuds.xml
  def index
    if @user
      @pseuds = @user.pseuds
      @rec_counts = Pseud.rec_counts_for_pseuds(@pseuds)
      @work_counts = Pseud.work_counts_for_pseuds(@pseuds)
      @page_subtitle = @user.login
    else
      redirect_to search_people_path
    end
  end

  # GET /users/:user_id/pseuds/:id
  def show
    if @user.blank?
      raise ActiveRecord::RecordNotFound, "Couldn't find user '#{params[:user_id]}'"
    end
    @pseud = @user.pseuds.find_by(name: params[:id])
    unless @pseud
      raise ActiveRecord::RecordNotFound, "Couldn't find pseud '#{params[:id]}'"
    end
    @page_subtitle = @pseud.name

    # very similar to show under users - if you change something here, change it there too
    if !(logged_in? || logged_in_as_admin?)
      visible_works = @pseud.works.visible_to_all
      visible_series = @pseud.series.visible_to_all
      visible_bookmarks = @pseud.bookmarks.visible_to_all
    else
      visible_works = @pseud.works.visible_to_registered_user
      visible_series = @pseud.series.visible_to_registered_user
      visible_bookmarks = @pseud.bookmarks.visible_to_registered_user
    end

    visible_works = visible_works.revealed.non_anon
    visible_series = visible_series.exclude_anonymous

    @fandoms = \
      Fandom.select("tags.*, count(DISTINCT works.id) as work_count").
      joins(:filtered_works).group("tags.id").merge(visible_works).
      where(filter_taggings: { inherited: false }).
      order('work_count DESC').load

    @works = visible_works.order("revised_at DESC").limit(ArchiveConfig.NUMBER_OF_ITEMS_VISIBLE_IN_DASHBOARD)
    @series = visible_series.order("updated_at DESC").limit(ArchiveConfig.NUMBER_OF_ITEMS_VISIBLE_IN_DASHBOARD)
    @bookmarks = visible_bookmarks.order("updated_at DESC").limit(ArchiveConfig.NUMBER_OF_ITEMS_VISIBLE_IN_DASHBOARD)

    if current_user.respond_to?(:subscriptions)
      @subscription = current_user.subscriptions.where(subscribable_id: @user.id,
                                                       subscribable_type: 'User').first ||
                      current_user.subscriptions.build(subscribable: @user)
    end
  end

  # GET /pseuds/new
  # GET /pseuds/new.xml
  def new
    @pseud = @user.pseuds.build
  end

  # GET /pseuds/1/edit
  def edit
    @pseud = @user.pseuds.find_by(name: params[:id])
  end

  # POST /pseuds
  # POST /pseuds.xml
  def create
    @pseud = Pseud.new(pseud_params)
    if @user.pseuds.where(name: @pseud.name).blank?
      @pseud.user_id = @user.id
      old_default = @user.default_pseud
      if @pseud.save
        flash[:notice] = ts('Pseud was successfully created.')
        if @pseud.is_default
          # if setting this one as default, unset the attribute of the current default pseud
          old_default.update_attribute(:is_default, false)
        end
        redirect_to([@user, @pseud])
      else
        render action: "new"
      end
    else
      # user tried to add pseud he already has
      flash[:error] = ts('You already have a pseud with that name.')
      render action: "new"
    end
  end

  # PUT /pseuds/1
  # PUT /pseuds/1.xml
  def update
    @pseud = @user.pseuds.find_by(name: params[:id])
    default = @user.default_pseud
    if @pseud.update(pseud_params)
      # if setting this one as default, unset the attribute of the current default pseud
      if @pseud.is_default and not(default == @pseud)
        # if setting this one as default, unset the attribute of the current active pseud
        default.update_attribute(:is_default, false)
      end
      flash[:notice] = ts('Pseud was successfully updated.')
     redirect_to([@user, @pseud])
    else
      render action: "edit"
    end
  end

  # DELETE /pseuds/1
  # DELETE /pseuds/1.xml
  def destroy
    @hide_dashboard = true
    if params[:cancel_button]
      flash[:notice] = ts("The pseud was not deleted.")
      redirect_to(user_pseuds_path(@user)) && return
    end

    @pseud = @user.pseuds.find_by(name: params[:id])
    if @pseud.is_default
      flash[:error] = ts("You cannot delete your default pseudonym, sorry!")
    elsif @pseud.name == @user.login
      flash[:error] = ts("You cannot delete the pseud matching your user name, sorry!")
    elsif params[:bookmarks_action] == "transfer_bookmarks"
      @pseud.change_bookmarks_ownership
      @pseud.replace_me_with_default
      flash[:notice] = ts("The pseud was successfully deleted.")
    elsif params[:bookmarks_action] == "delete_bookmarks" || @pseud.bookmarks.empty?
      @pseud.replace_me_with_default
      flash[:notice] = ts("The pseud was successfully deleted.")
    else
      render 'delete_preview' and return
    end

    redirect_to(user_pseuds_path(@user))
  end

  private

  def pseud_params
    params.require(:pseud).permit(
      :name, :description, :is_default, :icon, :delete_icon,
      :icon_alt_text, :icon_comment_text
    )
  end

end
