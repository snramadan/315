extern int fee (int, int);
extern int fi (int, int);
extern int foe (int, int);
extern int fum (int, int);

int funk (int a, int b, int c, int d, int e) {
  return fee(a, fi( b, foe( c, fum(d, e))));
}

