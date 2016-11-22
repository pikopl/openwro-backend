# -*- mode: ruby -*-
# vi: set ft=ruby :

Vagrant.configure("2") do |config|

if (/cygwin|mswin|mingw|bccwin|wince|emx/ =~ RUBY_PLATFORM) != nil 
    print "Loading windows environment identified by "
    puts RUBY_PLATFORM
    config.vm.box = "merikan/centos-7-64-puppet"
    config.vm.boot_timeout = 3600
else
    print "Loading linux environment identified by"
    puts RUBY_PLATFORM
    config.vm.box = "merikan/centos-7-64-puppet"
    config.vm.boot_timeout = 3600
end

config.vm.network "public_network"
config.vm.network "forwarded_port", guest: 80, host: 8010

config.vm.define "vmbox-smartcity" do |vmbox|
end

config.vm.provider :virtualbox do |vb|
config.vm.synced_folder "deployments/web", "/var/deployment/html/", :nfs => true 
config.vm.synced_folder "deployments/war", "/opt/wildfly/standalone/deployments/"

vb.name = "vmbox-smartcity"
vb.memory = 2048
vb.cpus = 2

end

config.vm.provision "puppet" do |puppet|
    puts "Start puppet provisioning"

    puppet.options = "--verbose --debug"
    puppet.manifests_path = "manifests"
    puppet.module_path = "modules"

    #Ensures that postgreSQL gets installed...

    puppet.manifest_file = "site.pp"

end

#config.vm.provision "shell", privileged: false, run: "always", inline: <<-SHELL
#  sudo /etc/init.d/httpd start
#  sudo /etc/init.d/postgresql start
#  SHELL


#Push test of SQL dump TO DO after https://github.com/mitchellh/vagrant/issues/5695 is corrected
#config.push.define "local-exec" do |push|
#  push.inline = <<-SCRIPT
#    scp push_test /tmp
#  SCRIPT
#end


end