# == Class: wildfly
#
# Installs wildfly server from yum-repo and ensures it's running state

class wildfly{

#package { ['wildfly']:
#    ensure => present;
#}

user {['wildfly user']:
    name => "wildfly",
    gid => "vagrant";
}

service { "wildfly":
    ensure => "running",
    hasstatus => "true",
    require => [User['wildfly user']],
    subscribe => File["/opt/wildfly/standalone/configuration/standalone.xml"];
}

file { '/opt/wildfly-install.sh' :
      source  => 'puppet:///modules/wildfly/wildfly-install.sh',
      owner => "wildfly",
      group => "wildfly",
      mode => 644;
}

file { '/opt/wildfly/standalone/configuration/standalone.xml' :
      source  => 'puppet:///modules/wildfly/standalone.xml',
      owner => "wildfly",
      group => "wildfly",
      mode => 644;
}

exec { 'install wildfly as root':
    command => '/opt/wildfly-install.sh',
    provider => "shell",
    path    => ['/usr/bin','/usr/sbin','/bin','/sbin'],
    user => 'root',
    subscribe => File["/opt/wildfly-install.sh"];
}
#Waits for management port to be ready - only indidcation that wildfly is really running
exec {"wait for wildfly":
  require => Service["wildfly"],
  command => "/usr/bin/wget --spider --tries 10 --retry-connrefused --no-check-certificate --no-proxy http://localhost:8080 && sleep 5",
}

exec { 'set up pass for wildfly user':
    command => '/opt/wildfly/bin/add-user.sh -s wildfly wildfly',
    require => Exec['wait for wildfly'],
    provider => "shell",
    onlyif  => 'test ! `cat /opt/wildfly/standalone/configuration/mgmt-users.properties | grep "wildfly="`',
    path    => ['/usr/bin','/usr/sbin','/bin','/sbin'],
    user => 'root'
}








}
