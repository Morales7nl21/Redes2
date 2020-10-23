#include "control_error.h"
//MIN 1.12;51

int control = 0;
typedef struct data
{
    int *arr, idx, tam, ctr;
} struct_hilo;
static void thread_cleanup_handler(void *args)
{
    struct_hilo *cuerpo_hilo = (struct_hilo *)args;
    free(cuerpo_hilo->arr);
}

void *funcion_hilo(void *args)
{

    struct_hilo *cuerpo_hilo = (struct_hilo *)args;
    int error, estado_ant, tipo_ant, detachstate, state_attr;
    pthread_attr_t *atributos = args;
    int band = 0, *ctrl = &control;
    do
    {
        if (cuerpo_hilo->idx == control)
        {
            error = pthread_setcancelstate(PTHREAD_CANCEL_DISABLE, &estado_ant);

            if (error)
                error_c(error, "pthread_setcancelstate");

            pthread_cleanup_push(thread_cleanup_handler, args);
            qsort(cuerpo_hilo->arr, cuerpo_hilo->tam, sizeof(int), compara_enteros);
            error = pthread_setcanceltype(PTHREAD_CANCEL_ENABLE, &tipo_ant);
            if (error)
                error_c(error, "pthread_setcanceltype");
            //printf("\nEL hilo: %d\n", cuerpo_hilo->idx);
            //printf("\nEl tam del hilo fue de  %d: \n", cuerpo_hilo->tam);
            imprime(cuerpo_hilo->arr, cuerpo_hilo->tam);
            *ctrl = *ctrl + 1;
            band = 1;
            if (error)
                error_c(error, "pthread_setcancelstate");
            pthread_cleanup_pop(1);
            pthread_exit(0);   
        }else
            sleep(0.001);
    } while (band != 1);
}
void crea_hilos(int num_hilos, const int tam_arreglo)
{

    printf("[+]Num hilos: %d y tama?o del arreglo  = %d\n\n", num_hilos, tam_arreglo);

    int idx = 0, i = 0, j = 0, init_attr[num_hilos], state_attr[num_hilos], status[num_hilos], error[num_hilos];
    int *arreglo_random = (int *)calloc(tam_arreglo, sizeof(int));

    pthread_t threads[num_hilos];
    pthread_attr_t estado_attr[num_hilos];
    struct_hilo cuerpo_hilo[num_hilos];

    crea_arreglo_random(arreglo_random, tam_arreglo);

    for (i = 0; i < num_hilos; i++)
    {
        init_attr[i] = pthread_attr_init(&estado_attr[i]);
        if (init_attr[i])
            error_c(init_attr[i], strerror(status[i]));
        
        state_attr[i] = pthread_attr_setdetachstate(&estado_attr[i], PTHREAD_CREATE_DETACHED);
        if (state_attr[i])
            error_c(state_attr[i], strerror(status[i]));

        cuerpo_hilo[i].idx = i;
        cuerpo_hilo[i].arr = arreglo_dividido(arreglo_random, tam_arreglo, i, num_hilos);
        cuerpo_hilo[i].tam = tam_arreglo_dividido(arreglo_random, tam_arreglo, i, num_hilos);
        
        status[i] = pthread_create(&threads[i], &estado_attr[i], funcion_hilo, (void *)&cuerpo_hilo[i]);
        if (status[i])
            error_c(status[i], strerror(status[i]));
    }
    free(arreglo_random);
    
    pthread_exit(NULL);
}