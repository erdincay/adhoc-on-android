# Introduction #

This is a small guide for manually creating an ad-hoc network for the <b>HTC Hero</b> through adb using the files provided in this project.

# Details #
Execute the following command lines in adb when you have the phone connected through the usb cable to your computer

<i>adb remount</i><br>
<i>adb push "\local\path\to\startstopadhoc" "/system/bin/"</i><br>
<i>adb shell</i><br>
# <i>mkdir "/data/local/bin/"</i><br>
<i>adb push "local\path\to\tiwlan.ini" "/data/local/bin/"</i><br>
<i>adb shell</i><br>
# <i>chmod 0755 "/system/bin/startstopadhoc"</i><br>


You can find the compiled startstopadhoc file under the downloads section and tiwlan.ini file located in this project source code section under the JNI folder. <b>Note</b> that the startstopadhoc.c is not the one you should copy. It must be compiled! <b>Also remember</b> to modify the SSID to your preferred name in the tiwlan.ini before pushing it to the phone.<br>

Now that you have done the above steps, you can create an ad-hoc network by executing the following command (if you are in the adb shell):<br>

# <i>startstopadhoc 1 start YOUR_STATIC_IP</i><br> where <i>YOUR_STATIC_IP</i> is an ip-address such as "192.168.2.1"<br>
<br>
Stop the ad-hoc network by executing<br>

# <i>startstopadhoc 1 stop YOUR_STATIC_IP</i><br>