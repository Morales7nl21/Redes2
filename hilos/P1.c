#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#include <time.h>
#include <pthread.h>
//#include "control_error.h"
#include "funcionesEsenciales.h"
#include "funcionesHilos.h"

//#include "funcioesHilos2.h"

int main(int argc, char **argv)
{
  mensajeUso(argc);
  int hilos = atoi(argv[1]);
  int tam_arreglo = atoi(argv[2]);
  //int value = atoi(argv[3]);
  printf("\n[+] Los hilos a crear son: %d y el tama?o del arreglo elegido es de: %d\n\n", hilos, tam_arreglo);
  muestra_rangos(hilos,999);
  crea_hilos(hilos, tam_arreglo);
  printf("\n[+] Se ha creado un archivo .txt con el nombre: resultadohilos");
  return 0;
}
