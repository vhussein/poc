Reference:
http://itekblog.com/centos-7-virtualbox-guest-additions-installation-centos-minimal/

1. Update and reboot system 

# yum update
# reboot

2. Install prerequisites 

# yum install gcc make kernel-devel
# yum groupinstall "Development tools"
# yum install kernel-devel

3. Mount guest additions
Devices>>Insert Guest Additions CD image ...

4. Mount setting in Guest
# cd /mnt
# mkdir cdrom && mount /dev/cdrom /mnt/cdrom
# cd cdrom && /run/media/root/VBOXADDITIONS_5.0.10_104061/VBoxLinuxAdditions.run
(or cd cdrom and ./VBoxLinuxAdditions.run)
# reboot
