#include <stdio.h>
#include <stdlib.h>
#include <math.h>

long double ieee754 (float entrada);

int main (){
  long double resposta, b, c, d, erro, precisao, entrada;
  int i = 0;

  precisao = 0.000000000000001;
  printf("Entrada: ");
  scanf(" %Lf", &entrada);
  d = ieee754(entrada);
  do {
    printf("Iteração %i\n", i++);
    resposta = d;
    b = (resposta * resposta) - entrada;
    c = 2 * resposta;
    d = resposta - b/c;
    erro = fabs(d - resposta);
  } while(erro > precisao);

  printf("Precisão: %.15Lf\n", precisao);
  printf("Resposta = %.30Lf \n", resposta);
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
