// this is the most important qt library so add it
#include <QApplication>
#include <QLabel>

int main(int argc, char *argv[]){

    // this is the object of the q application
    QApplication app(argc,argv);

    // this is the label, make it pointer which holds the reference
    QLabel *label = new QLabel("Hello World");
    label->setWindowTitle("Text Encrypt");
    (*label).show(); // just use arrow if you don't wanna use dereference operator
    label->resize(500,500);

    // this has the application execute
    return app.exec();
}
