Inicio da transformação AFN->AFD(v2):
Guarde o vetor de estados originais (vetEstadoAFN)
Para cada estado de vetEstadoAFN
    Guarde o vetor de transicoes (vetTransicoesAFN)
Coloque o estado inicial (sem transicoes) em um vetor de estados global (vetGlob)
Para cada estado de vetGlob
    unirTransicoes(estado atual.vetTransicoesAFN, estado atual)
FIM da transformação AFN->AFD(v2)


Inicio de unirTransicoes
Recebe um vetor de transicoes (vetTransicoes)
Recebe um estado origem (origem)
Determine todos os simbolos que sao utilizados em todas as transicoes de vetTransicoes
    Para cada simbolo utilizado  
        crie um vetor de estados destinos (vetDestinos)
        determine para vetTransicoes todas as transicoes que utilizam o simbolo analisado
        adicione ao vetDestinos todos os estados destinos (do AFN) das transicoes determinadas no passo anterior
        crie um novo estado (estadoGrupo) que contenha todos os estados (AFD) de vetDestino
        se estadoGrupo não existe em vetGlob, adicione-o.
        crie uma unica transicao partindo de origem para o estadoGrupo com o simbolo analisado
        adicione na origem a transicao gerada no passo anterior
FIM de unirTransicoes


Inicio da criacao de um estadoGrupo:
Guarde todos os estados do grupo em uma variavel (vet)
Determine todas as transicoes existentes partindo de cada estado de vet (vetTransicoesIniciais)
Crie um estado (estadoFinal) que representara o estadoGrupo
unirTransicoes(vetTransicoesIniciais, estadoFinal)
FIM da criacao de um estadoGrupo