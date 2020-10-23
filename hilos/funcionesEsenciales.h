

void mensaje_uso(int);
int compara_enteros(const void *, const void *);
void imprime(const int *, int);
void muestra_rango(const int *);
int *arreglo_dividido(const int *, int, int, int);
void crea_arreglo_random(int *, int);
int tam_arreglo_dividido(const int *, int, int, int);

void mensajeUso(int argc)
{
  if (argc < 2)
  {
    printf("\n[-]Indique al ejecutar el numero de hilos a manejar\n");
    printf("[-]Indique ademas el numero de elementos a crear para el arreglo (el arreglo sera llenado  aleatoriamente)\n");
    printf("[+]Ejemplo:./'ejecutable' 5 100\n\n");
    exit(-1);
  }
}
int compara_enteros(const void *p, const void *q)
{
  int a, b;
  a = *(int *)p;
  b = *(int *)q;
  if (a < b)
    return -1;
  if (a == b)
    return 1;
}
void imprime(const int *arreglo, int tam)
{
  int cont = 0;
  for (int i = 0; i < tam; i++){
    printf("%i, ", arreglo[i]);
    //cont ++;
  }
  //printf("\n\n TOTAL [%d]\n\n",cont);
}
void muestra_rango(const int *arreglo)
{
  while (*arreglo)
  {
    printf("%i,", *(arreglo++));
  }
}

int *arreglo_dividido(const int *arreglo_base, int tam_arreglo, int idx, int divisiones)
{

  int rango = (999 / divisiones) + 1;
  int posicion = 0, this_idx = 0;

  for (int i = 0; i < tam_arreglo; i++)
  {
    if (arreglo_base[i] >= (idx * rango) && (arreglo_base[i] < ((idx + 1) * rango)))
    {
      posicion += 1;
    }
  }
  int *arreglo_div = calloc(posicion, sizeof(int));

  for (int i = 0; i < tam_arreglo; i++)
  {
    if (arreglo_base[i] >= (idx * rango) && (arreglo_base[i] < ((idx + 1) * rango)))
    {
      arreglo_div[this_idx] = arreglo_base[i];
      this_idx += 1;
    }
  }
  return arreglo_div;
}
int tam_arreglo_dividido(const int *arreglo_base, int tam_arreglo, int idx, int divisiones)
{

  int rango = (999 / divisiones) + 1;
  int posicion = 0, this_idx = 0;

  for (int i = 0; i < tam_arreglo; i++)
  {
    if (arreglo_base[i] >= (idx * rango) && (arreglo_base[i] < ((idx + 1) * rango)))
    {
      posicion += 1;
    }
  }
  return posicion;
}
void crea_arreglo_random(int *arreglo, int n)
{
  srand(time(NULL));
  for (int i = 0; i < n; i++)
    arreglo[i] = (0 + rand()) % 1000;
}
void muestra_rangos(int n, int value){
  int arr[n];
  int cont=0;
  int temp;
  printf("\n-------------------RANGOS---------------\n\n");
  for (int i = 0; i <= n; i++)
  {
    arr[i] = ((999 / n) * i) + i;
    if(i+1 < n)
      temp = ((999 / n) * (i+1)) + (i);
    else
      temp = 999;
    if(arr[i]<=999)
      printf("(%d - %d),",arr[i],temp);
    cont++;
  }
  printf("\n----------------------------------------\n\n");
  printf("\n");
}