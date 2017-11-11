# Chain Encryption

The purpose of this app is to encrypt your file into total gibberish, which is useful if you want something like a master file to store all your online passwords or send secret messages over email. The encryption is based on AES 128 bit standard. So far it can support .txt files and ..... pretty much everything else. :-D 

# Java veriosn instruction

Java version is finished. Download the chain_encryption.jar in java version folder and make it executable to run the application. Make sure java runtime enviorment from the oracle website is installed beforehand. I am not packaging the jar into a windows .exe executable because it defeats the whole purpose of cross platform language like java. You will need to know how to run a jar on your own.

Latest jar is thoughly tested on both windows and linux. Mac OS is not yet tested.

# Android version instruction

Android version is finished! Download and install the chain_ecnryption.apk in the Android version folder. Be sure to enable unknown sources first. 

# QT version instruction

QT version is scrapped. I found out c++ encyrption library like Crypto++ has different implementation than java's default library. Particularly, salting the passwords is one issue, making file encyption compatibility between this, Android, and Java versoins incomptible. It can convert files, just not files converted by the Java and Andorid version. This version thus is canceled.

# Known Bugs or issues:

The app needs support for encytpion of file greater than 500mb which is the limit of the current implementation. This is beacuse my current code will load files into ram for encytpion which is very fast for small files but as you can tell, large files will run into memory issues.
