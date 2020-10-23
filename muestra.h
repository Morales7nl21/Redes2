#include "funcionesHilos.h"

void general()
{
    int arr[] = {10, 11, 323, 1, 23, 2, 4, 1, 980, 340, 250, 100, 770, 776, 734, 389, 199, 830, 850, 870, 999, 2, 445, 400};
    int tamarr;
    tamarr = (sizeof(arr) / sizeof(arr[0]));

    int *q = arreglo_dividido(arr, tamarr, 3, 5);
    // Arreglo random
    int tam = 20;
    int *arreglo_random = (int *)calloc(tam, sizeof(int));
    crea_arreglo_random(arreglo_random, tam);

    //RANGOS
    printf("\n------------------RANGOS------------\n");
    int a = ((999 / 5) * 1) + 1;
    int b = ((999 / 5) * 2) + 2;
    int c = ((999 / 5) * 3) + 3;
    int d = ((999 / 5) * 4) + 4;
    int e = ((999 / 5) * 5) + 5;

    printf("%i\n", a);
    printf("%i\n", b);
    printf("%i\n", c);
    printf("%i\n", d);
    printf("%i\n", e);

    // printf("\n------------------QSORT------------\n");
    // imprime(arr, tamarr);
    // printf("\n");
    // qsort(arr, tamarr, sizeof(int), compara_enteros);
    // imprime(arr, tamarr);
    // printf("\n\n");
    printf("\n------------------RANGO VALORES------------\n");
    muestra_rango(q);
    printf("\n\n");
    free(q);

    printf("\n------------------QSORT RANDOM------------\n");
    muestra_rango(arreglo_random);
    printf("\n");
    qsort(arreglo_random, tam, sizeof(int), compara_enteros);
    muestra_rango(arreglo_random);
}
/**
    for (j = 0; j < num_hilos; j++)
    {
        error[j]=pthread_join(threads[j], NULL);
        if (error[j])
        {
            fprintf(stderr,"[-]Error %d: %s en: %d\n",error[j],strerror(error[j]),j);
            exit(-1);
        }
    }
    
    **/