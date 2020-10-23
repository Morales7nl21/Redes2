void error_c(int error,char *cadena){
    fprintf(stderr,"[-]Error %d: %s\n",error,cadena);
    exit(-1);
}