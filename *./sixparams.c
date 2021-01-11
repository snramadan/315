extern int f (int, int);

/* Function with 16 parameters */
int func1 (int a, int b, int c, int d, int e, int g){
  return 1+f(e, g);
  //return 1+f(a, f(b, f(c, f(d, f(e, g)))));
}

