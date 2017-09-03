#This adds the two main qt libraries
QT += core gui

# This checks that the version is greater than 4 and that we are making qt widget application
greaterThan(QT_MAJOR_VERSION, 4) : QT += widgets



# This denotes that main.cpp is part of the project
SOURCES += \
    main.cpp
