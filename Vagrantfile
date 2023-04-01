# -*- mode: ruby -*-
# vi: set ft=ruby :

# This assumes that the host machine has r2 and all the reddit plugins checked
# out and in the correct directories--pay attention to both name and position
# relative to the r2 code:
#
# r2:         {ROOTDIR}/reddit
#
# plugins:
# about:      {ROOTDIR}/about
# gold:       {ROOTDIR}/gold
#
# All plugins are optional. A plugin will only be installed if it is listed
# in `plugins` AND it is located in a directory that both follows the plugin
# naming convention and is correctly located on the host machine. The general
# rule for naming each plugin directory is that "reddit-plugin-NAME" should be
# in the directory {ROOTDIR}/NAME.
#
# This VagrantFile allows for the creation of two VMs:
#   * default: the primary VM, with all services necessary to run reddit
#              locally against the local codebase.
#   * travis:  Testing-only VM suitable for running `nosetests` and debugging
#              issues encountered without having to wait for travis-ci to pick
#              up the build.  This will *not* be the same environment as
#              travis, but it should be useful for repairing broken tests.
#
# To start your vagrant box simply enter `vagrant up` from {ROOTDIR}/reddit.
# You can then ssh into it with `vagrant ssh`.
#
# avahi-daemon is installed on the guest VM so you can access your local install
# at https://reddit.local. If that fails you'll need to update your host
# machine's hosts file (/etc/hosts) to include the line:
# 192.168.56.111 reddit.local
#
# If you want to create additional vagrant boxes you can copy this file
# elsewhere, but be sure to update `code_share_host_path` to be the absolute
# path to {ROOTDIR}.

vagrant_user = "vagrant"

# code directories
this_path = File.absolute_path(__FILE__)
reddit_dir = File.expand_path("..", this_path)
code_share_host_path = File.expand_path("..", reddit_dir)
code_share_guest_path = "/media/reddit_code"
plugins = [
  "about",
  "gold",
]

# overlayfs directories
overlay_mount = "/home/#{vagrant_user}/src"
overlay_lower = code_share_guest_path
overlay_upper = "/home/#{vagrant_user}/.overlay"

# "default" vm config
guest_ip = "192.168.56.111"
guest_mem = "4096"
guest_swap = "4096"
hostname = "reddit.local"


Vagrant.configure(2) do |config|
  config.vm.box = "trusty-cloud-image"
  config.vm.box_url = "https://cloud-images.ubuntu.com/vagrant/trusty/current/trusty-server-cloudimg-amd64-vagrant-disk1.box"

  # mount the host shared folder
  config.vm.synced_folder code_share_host_path, code_share_guest_path, mount_options: ["ro"]

  config.vm.provider "virtualbox" do |vb|
    vb.memory = guest_mem
  end

  # ubuntu cloud image has no swapfile by default, set one up
  config.vm.provision "shell", inline: <<-SCRIPT
    if ! grep -q swapfile /etc/fstab; then
      echo 'swapfile not found. Adding swapfile.'
      fallocate -l #{guest_swap}M /swapfile
      chmod 600 /swapfile
      mkswap /swapfile
      swapon /swapfile
      echo '/swapfile none swap defaults 0 0' >> /etc/fstab
    else
      echo 'swapfile found. No changes made.'
    fi
  SCRIPT

  # set up the overlay filesystem
  config.vm.provision "shell", inline: <<-SCRIPT
    if [ ! -d #{overlay_mount} ]; then
      echo "creating overlay mount directory #{overlay_mount}"
      sudo -u #{vagrant_user} mkdir #{overlay_mount}
    fi

    if [ ! -d #{overlay_upper} ]; then
      echo "creating overlay upper directory #{overlay_upper}"
      sudo -u #{vagrant_user} mkdir #{overlay_upper}
    fi

    echo "mounting overlayfs (lower: #{overlay_lower}, upper: #{overlay_upper}, mount: #{overlay_mount})"
    mount -t overlayfs overlayfs -o lowerdir=#{overlay_lower},upperdir=#{overlay_upper} #{overlay_mount}
  SCRIPT

  # NOTE: This VM exists solely to assist in writing tests.  It does not actually
  # install travis but rather builds a minimal vm with only the services
  # available under a travis build to aid in test debugging (via `nosetests`)
  # To use:
  #     $ vagrant up travis
  #     $ vagrant ssh travis
  #     vagrant@travis$ cd src/reddit/r2 && nosetests
  config.vm.define "travis", autostart: false do |travis|
      travis.vm.hostname = "travis"
      # run install script
      travis.vm.provision "shell", inline: <<-SCRIPT
        if [ ! -f /var/local/reddit_installed ]; then
          echo "running install script"
          cd /home/#{vagrant_user}/src/reddit
          ./install/travis.sh vagrant
          touch /var/local/reddit_installed
        else
          echo "install script already run"
        fi
      SCRIPT
  end

  # NB: this is the primary VM. To build run
  #    $ vagrant up
  # [though 'vagrant up default' will also work, the 'default' is redudnant]
  # Once built, avahi-daemon should guarantee the instance will be accessible
  # from https://reddit.local/
  config.vm.define "default", primary: true do |redditlocal|
      redditlocal.vm.hostname = hostname
      # host-only network interface
      redditlocal.vm.network "private_network", ip: guest_ip

      # rabbitmq web interface
      config.vm.network "forwarded_port", guest: 15672, host: 15672

      # run install script
      plugin_string = plugins.join(" ")
      redditlocal.vm.provision "shell", inline: <<-SCRIPT
        if [ ! -f /var/local/reddit_installed ]; then
          echo "running install script"
          cd /home/#{vagrant_user}/src/reddit
          REDDIT_PLUGINS="#{plugin_string}" REDDIT_DOMAIN="#{hostname}" ./install/reddit.sh
          touch /var/local/reddit_installed
        else
          echo "install script already run"
        fi
      SCRIPT

      # inject test data
      redditlocal.vm.provision "shell", inline: <<-SCRIPT
        if [ ! -f /var/local/test_data_injected ]; then
          cd /home/#{vagrant_user}/src/reddit
          sudo -u #{vagrant_user} reddit-run scripts/inject_test_data.py -c 'inject_test_data()'
          touch /var/local/test_data_injected
        else
          echo "inject test data already run"
        fi

        # HACK: stop and start everything (otherwise sometimes there's an issue with
        # ports being in use?)
        reddit-stop
        reddit-start
      SCRIPT

      # additional setup
      redditlocal.vm.provision "shell", inline: <<-SCRIPT
        if [ ! -f /var/local/additional_setup ]; then
          apt-get install -y ipython avahi-daemon
          touch /var/local/additional_setup
        else
          echo "additional setup already run"
        fi
      SCRIPT

      # DONE: let this run whenever provision is run so that the user can see
      # how to proceed.
      redditlocal.vm.provision "shell", inline: <<-SCRIPT
        cd /home/#{vagrant_user}/src/reddit
        REDDIT_DOMAIN="#{hostname}" ./install/done.sh
      SCRIPT
  end
end
