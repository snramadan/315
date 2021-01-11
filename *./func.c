extern int swap (int*, int*);  //extern is always implied in function prototypes
extern int sum (int, int);  //extern optional

int func (int a, int b)
{ 
  swap(&a, &b);
  return sum(a, b);
}

