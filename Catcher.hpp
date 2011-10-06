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


private: 
  QVector<word> vector;
};
