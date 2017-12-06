#include <stdio.h>
#include <stdlib.h>
#include <math.h>

long double ieee754 (float entrada);

int main (){
  long double xk, fxk, dfxk, xk1, erro, precisao, entrada;
  int i = 0;
  precisao = 0.000000000000001;
  printf("Entrada: ");
  scanf(" %Lf", &entrada);
  xk1 = ieee754(entrada);
  do {
    printf("Iteração %i\n", i++);
    xk = xk1;
    printf("xk = %.20Lf\n", xk);
    fxk = (xk * xk) - entrada;
    printf("f(xk) = %.20Lf\n", fxk);
    dfxk = 2 * xk;
    printf("f'(Xk) = %.20Lf\n", dfxk);
    xk1 = xk - (fxk/dfxk);
    printf("xk1 = %.20Lf\n", xk1);
    erro = fabs(xk1 - xk);
    printf("Erro: %.25Lf\n\n\n", erro);
  } while(erro > precisao);

  printf("Precisão: %.15Lf\n", precisao);
  printf("xk = %.30Lf \n", xk);
}

long double ieee754 (float entrada){
    int expo;
    long double mantissa;

    expo = floor(log2(entrada));
    mantissa = entrada / pow(2,expo);
    expo = expo + 127;

    if(fmod(entrada, 2) !=0){
      mantissa = mantissa/2;
    }

    return mantissa;
}
