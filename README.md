# ListPad
## Projeto integrado das disciplinas de Programação de Dispositivos Android 1 (PA1) e Banco de Dados para Computação Móvel (BDM).

O projeto consiste no desenvolvimento de um aplicativo simples de lista em lista com uso de persistência. 
O aplicativo permite criar, apagar e gerenciar listas (no plural) de Tarefas, que chamaremos aqui de lista de itens.
A tela principal do aplicativo mostra as listas de Tarefas cadastradas, tem opções para que sejam adicionadas novas, para visualizar detalhes (isto é, os itens que fazem parte) e para apagar listas de itens existentes.
Ainda possui uma Tag para sere clicacada como Concuida.

Cada lista de itens é composta de zero ou mais itens.
Cada item é composto de uma descrição (que deve ser única) e uma flag (que diz se aquele item foi cumprido ou não). 
Os itens de uma lista devem ser visualizados na tela de detalhes da lista de itens a qual pertencem.
Nessa tela o usuário pode adicionar, excluir e checar itens (alterar estado da flag) como cumpridos ou não.

A persistência foi implementada usando SQLITE.
