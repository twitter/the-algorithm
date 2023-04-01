class Admin::BannersController < Admin::BaseController

  # GET /admin/banners
  def index
    authorize(AdminBanner)

    @admin_banners = AdminBanner.order("id DESC").paginate(page: params[:page])
  end

  # GET /admin/banners/1
  def show
    @admin_banner = authorize AdminBanner.find(params[:id])
  end

  # GET /admin/banners/new
  def new
    @admin_banner = authorize AdminBanner.new
  end

  # GET /admin/banners/1/edit
  def edit
    @admin_banner = authorize AdminBanner.find(params[:id])
  end

  # POST /admin/banners
  def create
    @admin_banner = authorize AdminBanner.new(admin_banner_params)

    if @admin_banner.save
      if @admin_banner.active?
        AdminBanner.banner_on
        flash[:notice] = ts('Setting banner back on for all users. This may take some time.')
      else
        flash[:notice] = ts('Banner successfully created.')
      end
      redirect_to @admin_banner
    else
      render action: 'new'
    end
  end

  # PUT /admin/banners/1
  def update
    @admin_banner = authorize AdminBanner.find(params[:id])

    if !@admin_banner.update(admin_banner_params)
      render action: 'edit'
    elsif params[:admin_banner_minor_edit]
      flash[:notice] = ts('Updating banner for users who have not already dismissed it. This may take some time.')
      redirect_to @admin_banner
    else
      if @admin_banner.active?
        AdminBanner.banner_on
        flash[:notice] = ts('Setting banner back on for all users. This may take some time.')
      else
        flash[:notice] = ts('Banner successfully updated.')
      end
      redirect_to @admin_banner
    end
  end

  # GET /admin/banners/1/confirm_delete
  def confirm_delete
    @admin_banner = authorize AdminBanner.find(params[:id])
  end

  # DELETE /admin/banners/1
  def destroy
    @admin_banner = authorize AdminBanner.find(params[:id])
    @admin_banner.destroy

    flash[:notice] = ts('Banner successfully deleted.')
    redirect_to admin_banners_path
  end

  private

  def admin_banner_params
    params.require(:admin_banner).permit(:content, :banner_type, :active)
  end

end
