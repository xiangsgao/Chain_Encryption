# Text-Crypt
The purpose of this app is to encrypt your file into total gibberish, which is useful if you want something like a master file to store all your online passwords or send secret messages over email. The encryption is based on AES 128 bit standard.
So far it can support .txt files and ..... pretty much everything else. :-D
I should've name it to something else like universal crypt.

# Java veriosn instruction 
Java version is finished. Download the Text-Crypt.jar in java version folder and make it executable to run the application. Make sure java runtime enviorment from the oracle website is installed beforehand. I am not packaging the jar into a windows .exe executable because it defeats the whole purpose of cross platform language like java. You will need to know how to run a jar on your own.

Latest jar is thoughly tested on both windows and linux. Mac OS is not yet tested.

Bugs fixed: Changing the salted key length to 128 from 256 to ensure compatibility with older Java JRE.

# Android version instruction
Android version is finished! Download and install the Text-Crypt.apk in the Android version folder. Be sure to enable unknown sources first.  
THE ANDROID VERSION IS ON GOOGLE PLAY: https://play.google.com/store/apps/details?id=xgao.com.text_crypt_android


# QT version instruction
Working on it now, I will create a .exe executable for windows and an executable .sh for linux. I may even create an installer for both platform cuz why the hell not? No executable for Mac platform beacuse I have no mac and I don't know how to use a mac. Use java version if you wanna run the software on Mac OS.

# Bugs and features left to work on:
The app needs support for encytpion of file greater than 500mb which is the limit of the current implementation. This is beacuse my current loads files into ram for encytpion which is very fast for small files but as you can tell, laerge files will run into memoery issues. 

Performance optimization is needed. Encrytion is a lengthly and resource heavy proccess, espeically for huge files. I will need to optimized the prefomeance so the program does not freeze and stay responsive. 

No guranteed when I will do it beacuse this app is made mostly for my personal use and so far it fits my needs. This readme is for those few who finds this relevent.

This app will be rename, awful name currently.


