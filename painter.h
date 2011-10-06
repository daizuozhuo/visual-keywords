#ifndef PAINTER_H
#define PAINTER_H

#include<QtGui>
template<class T>
Class Painter
{
 public:
  Painter(QVector<T>);
  void draw();
 private:
  QVector<T> data;
}
y
