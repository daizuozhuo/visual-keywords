#include <QtGui>

struct word
{
  QString str;
  int count;
  bool operator< (const word& w) {return count < w.count;} 
};

class Catcher
{
public: 
  Catcher();
  QVector<word>* get_word() {return &vector;}
  

private: 
  QVector<word> vector;
};
