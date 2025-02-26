
# Trabalho Replicação Ativa.
### Feito por Alana Santos, Matheus Oliveira e George Neres.

## Trabalho da Disciplina Sistemas Distribuidos INF020.

Replicação ativa de banco de dados utilizando RabbitMQ, Docker e Spring boot. 
A replicação ativa de banco de dados é uma técnica que executa a mesma operação em cada réplica do banco de dados.

Para isso, foi utilzado o RabbitMQ para a comunicação em grupo entre os multiplos serviços do banco de dados, foi utilizado a fanout exchange para que todos os serviços recebam os comandos SQL simultaneamente. 
Foi utilizado o Docker para colocar os serviços e seus respectivos bancos de dados de forma distribuida. E o líder foi definido no DockerCompose, ele que é responsável por enviar as replicações dos comandos SQL.
Para a verificação de falhas e remoção de membros defeituosos do grupo foi utilizado a técnica  de Heartbeat, que consiste no envio de uma mensagem para que os receptores respondam, caso não haja resposta de um membro ele é removido.





## Detalhes da Implementação

Como atingimos a comunicação em grupo utilizando a fanout exchange do RabbitMQ onde cada membro do grupo cria uma fila para receber os comandos SQL que serão executados. Somente o líder do grupo envia mensagem para esta fila.
A fanout do RabbitMQ garante a entrega a todos os mebros do grupo.
Após eles executarem o comando SQL eles enviam mensagens ao líder, para informar se obteve sucesso ou não na execução.

A cada periodo de tempo o HeartBeat fica responsavel por enviar uma mensagem a todos do grupo para verificar se eles ainda estão ativos, caso não estejam suas filas são deletadas pelo líder.