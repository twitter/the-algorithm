class Admin::SettingsController < Admin::BaseController
  before_action :load_admin_setting

  def index
    authorize @admin_setting
  end

  # PUT /admin_settings/1
  def update
    authorize @admin_setting
    if @admin_setting.update(permitted_attributes(@admin_setting).merge(last_updated: current_admin))
      flash[:notice] = t(".success")
      redirect_to admin_settings_path
    else
      render :index
    end
  end

  private

  def load_admin_setting
    @admin_setting = AdminSetting.first || AdminSetting.create(last_updated_by: Admin.first)
  end
end
