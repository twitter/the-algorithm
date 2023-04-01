# Controller for Serial Works
class SerialWorksController < ApplicationController

  before_action :load_serial_work
  before_action :check_ownership

  def load_serial_work
    @serial_work = SerialWork.find(params[:id])
    @check_ownership_of = @serial_work.series
  end

  # DELETE /related_works/1
  # Updated so if last work in series is deleted redirects to current user works listing instead of throwing 404
  def destroy
    last_work = (@serial_work.series.works.count <= 1)

    @serial_work.destroy

    if last_work
      redirect_to current_user
    else
      redirect_to series_path(@serial_work.series)
    end
  end
end
